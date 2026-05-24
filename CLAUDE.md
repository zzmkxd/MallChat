# MallChat

集成了即时通讯的电商系统后端。Spring Boot 3.3.5 + Java 21 + Netty WebSocket + MyBatis-Plus + Redis + RocketMQ。

Git: `origin` = 个人 fork (`zzmkxd/MallChat`), `upstream` = 原始仓库 (`zongzibinbin/MallChat`)。

## 常用命令

```bash
./mvnw clean compile -pl mallchat-chat-server    # 编译主模块（推荐日常使用）
./mvnw clean package -DskipTests                  # 全量打包
./mvnw test-compile                               # 测试编译（Phase 2+）
```

## 架构约定

### 请求处理链路

```
Filter(TraceId) → Interceptor(Token→Collector→Black) → Controller
```

- **无 Spring Security**，全部自定义实现
- JWT 由 `com.auth0:java-jwt` 生成，密钥来自 `jwt.secret`，5 天过期
- Token 存 Redis 实现单设备登录（新登录挤旧）
- 公开 API 通过 `/capi/**/public/**` 路径规则放行
- `RequestHolder` (ThreadLocal) 持有当前请求的 uid 和 IP

### WebSocket (Netty :8090)

```
IdleStateHandler(30s) → HttpServerCodec → ChunkedWriteHandler
→ HttpObjectAggregator(8K) → HttpHeadersHandler(提取token/IP)
→ WebSocketServerProtocolHandler(握手升级)
→ NettyWebSocketServerHandler(@Sharable, 业务处理)
```

Token 通过 URL 参数 `?token=xxx` 传递。

### 消息发送流程

```
POST /capi/chat/msg → Token校验 → 三层频控(5s/30s/60s)
→ AC自动机敏感词过滤 → MySQL持久化
→ RocketMQ MsgSendConsumer → WebSocket推送给在线用户
```

### 设计模式

- **策略模式**: `MsgHandlerFactory` → `AbstractMsgHandler`（Text/Img/File/Sound/Video/Emoji/Recall/System）
- **策略模式**: `MsgMarkFactory` → `AbstractMsgMarkStrategy`（Like/DisLike）
- **策略模式**: `FrequencyControlStrategyFactory` → `AbstractFrequencyControlService`（固定窗口/滑动窗口/令牌桶）
- **事件驱动**: 10+ Spring Event（`MessageSendEvent`, `UserOnlineEvent`, `UserOfflineEvent`, `GroupMemberAddEvent`…）
- **事务外调用**: `@SecureInvoke` → AOP → 本地消息表 + `@Scheduled` 定时重试
- **适配器**: `adapter` 包下 Entity ↔ VO 转换

### 线程池

| 名称 | 核心/最大 | 队列 | 拒绝策略 |
|------|----------|------|---------|
| `mallchatExecutor` | 10/10 | 200 | CallerRunsPolicy |
| `websocketExecutor` | 16/16 | 1000 | DiscardPolicy |
| `aichatExecutor` | 10/10 | 15 | DiscardPolicy |

### 环境配置文件

| 文件 | Git 追踪 | 用途 |
|------|----------|------|
| `application.yml` | ✅ | 主配置，profile=local |
| `application-local.properties` | ❌ gitignored | 本地开发 |
| `application-test.properties` | ✅ | 测试环境（⚠️含真实凭据） |
| `application-pro.properties` | ✅ | 生产环境（占位符） |

## 模块导航

入口类: `MallchatCustomApplication.java`
- `@SpringBootApplication(scanBasePackages = "com.abin.mallchat")`
- `@MapperScan("com.abin.mallchat.common.**.mapper")`

| 包 `com.abin.mallchat.common.` | 职责 |
|------|------|
| `chat/` | 聊天核心：消息 CRUD、群组管理、会话列表 |
| `chatai/` | AI 聊天：ChatGPT + ChatGLM2 对接 |
| `common/` | 公共：拦截器、配置、异常、JWT、缓存、频控 |
| `sensitive/` | 敏感词：AC 自动机算法实现 |
| `user/` | 用户：登录鉴权、好友、表情、OAuth |
| `websocket/` | Netty WebSocket 服务端（端口 8090） |
| `mallchat-tools/` | 工具模块：OSS Starter、事务、频控 |

## 文档索引

| 需要什么 | 去哪里 |
|---------|--------|
| 文件/类在哪个包 | `docs/project-structure.md` |
| 数据库表结构 | `docs/mallchat.sql`（18 张表） |
| 重构计划与规则 | `docs/claudecode-skills-delegated-avalanche.md` + `docs/refactoring-rules.md` |
| 重构执行日志 | `docs/refactoring-log.md` |
