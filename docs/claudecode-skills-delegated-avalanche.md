# MallChat 渐进式重构计划（细粒度版）

> **执行日志**: `docs/refactoring-log.md` — 每一步操作都记录在此
> **重构规则**: `docs/refactoring-rules.md` — 执行 SOP

## Context

MallChat: Spring Boot 2.6.7 + Java 8 + Netty WebSocket 即时通讯项目，最后活跃 2024-01。
目标：Java 21 + SB 3.3.x + Docker 化，原子化步骤，每步编译验证。

**规则文件**: `docs/refactoring-rules.md`
**结构索引**: `docs/project-structure.md`

---

## Phase 1: 基础设施升级（Java 21 + SB 3 + jakarta）

### 1.1 Maven Wrapper
- [x] 1.1.1 添加 Maven Wrapper (`mvn wrapper:wrapper`)
- [x] 1.1.2 验证: `./mvnw --version`

### 1.2 根 POM 版本升级
- [x] 1.2.1 `<java.version>`: 1.8 → 21
- [x] 1.2.2 `spring-boot-starter-parent`: 2.6.7 → 3.3.5
- [~] 1.2.3 验证被 1.3.11 替代（Lombok 阻断，升级后 5/6 模块通过）

### 1.3 依赖版本对齐（根 POM <properties>）
- [x] 1.3.1 `mybatis-plus-boot-starter`: 3.4.0 → 3.5.9
- [x] 1.3.2 `mybatis`: 3.5.10 → 3.5.17
- [~] 1.3.3 `mysql-connector-java` → `mysql-connector-j` (延至 Phase 2)
- [x] 1.3.4 `redisson-spring-boot-starter`: 3.17.1 → 3.36.0
- [x] 1.3.5 `netty-all`: 4.1.76 → 4.1.114
- [x] 1.3.6 `hutool-all`: 5.8.18 → 5.8.34
- [x] 1.3.7 `lombok`: 1.18.10 → 1.18.36
- [x] 1.3.8 `jjwt`: 0.9.1 → 0.12.6
- [x] 1.3.9 `jsoup`: 1.15.3 → 1.18.1
- [x] 1.3.10 `rocketmq-spring-boot-starter`: 2.2.2 → 2.3.2
- [x] 1.3.11 验证: `./mvnw clean compile` (5/6 模块通过)

### 1.4 javax → jakarta: servlet 层（拦截器）
- [x] 1.4.1 `TokenInterceptor.java`: javax.servlet → jakarta.servlet
- [x] 1.4.2 `BlackInterceptor.java`: javax.servlet → jakarta.servlet
- [x] 1.4.3 `CollectorInterceptor.java`: javax.servlet → jakarta.servlet
- [x] 1.4.4 `HttpTraceIdFilter.java`: javax.servlet + javax.annotation → jakarta
- [x] 1.4.5 `WebLogAspect.java`: javax.servlet → jakarta.servlet
- [x] 1.4.6 `HttpErrorEnum.java`: javax.servlet → jakarta.servlet
- [x] 1.4.7 验证: javax.servlet 错误清零 | chat-server 仍有 annotation+validation 错误

### 1.5 javax → jakarta: validation 层（DTO 校验注解）
- [x] 1.5.1~1.5.5 批量替换 41 文件: javax.validation → jakarta.validation
- [x] 1.5.6 验证: javax.validation 错误清零 | 剩余 annotation 层错误

### 1.6 javax → jakarta: annotation 层
- [x] 1.6.1~1.6.8 批量 12 文件: javax.annotation → jakarta.annotation
- [x] 1.6.9 验证: javax.annotation 错误清零

### 1.7 工具模块 javax → jakarta
- [x] 1.7.1 `mallchat-frequency-control/` intecepter 目录: javax.servlet → jakarta.servlet
- [x] 1.7.2 `mallchat-frequency-control/` exception 目录: javax.servlet → jakarta.servlet
- [x] 1.7.3 验证: `./mvnw clean compile` → 4 类非 javax 错误残留

### 1.8 Swagger 临时处理
- [x] 1.8.1 `SwaggerConfig.java`: 注释掉 `@EnableSwagger2WebMvc` 及相关 import (Phase 2 替换)
- [x] 1.8.2 `mallchat-tools/mallchat-common-starter/pom.xml`: 暂时排除 knife4j/swagger 依赖
- [ ] 1.8.3 验证: `./mvnw clean compile` ← **剩余 4 类编译错误待修复**

### 1.9 Spring Boot 3 配置迁移
- [ ] 1.9.1 `application.yml`: 删除 `spring.mvc.pathmatch.matching-strategy`
- [ ] 1.9.2 `application.yml`: 检查 `spring.redis` 配置兼容性
- [ ] 1.9.3 `logback.xml`: 修正日期格式 `dd-MM-yyyy` → `yyyy-MM-dd`
- [ ] 1.9.4 验证: `./mvnw clean package -DskipTests`

### 1.10 收尾
- [ ] 1.10.1 `CLAUDE.md`: 更新版本号
- [ ] 1.10.2 Git 提交: `refactor(phase1): Java 21 + Spring Boot 3.3 + jakarta migration`

---

## Phase 2: 依赖现代化

### 2.1 Swagger/Springfox → Springdoc OpenAPI
- [ ] 2.1.1 引入依赖: `springdoc-openapi-starter-webmvc-ui` + `knife4j-openapi3-jakarta-spring-boot-starter`
- [ ] 2.1.2 重写 `SwaggerConfig.java` → `OpenApiConfig.java`
- [ ] 2.1.3 ~ 2.1.8 逐包替换注解: `@Api`→`@Tag`, `@ApiOperation`→`@Operation`, `@ApiModelProperty`→`@Schema`
  - Controller 层 (7个)
  - Chat 域 VO (request + response)
  - User 域 VO (request + response)
  - Common 域 VO
  - Entity 层
  - 验证: `./mvnw clean compile`

### 2.2 JWT 库迁移
- [ ] 2.2.1 `JwtUtils.java`: jjwt 0.9.1 API → 0.12.6 API 重写
- [ ] 2.2.2 移除 `com.auth0:java-jwt` (如存在)
- [ ] 2.2.3 `LoginServiceImpl.java`: 适配新 JWT API
- [ ] 2.2.4 验证: `./mvnw clean compile`

### 2.3 JUnit 4 → JUnit 5
- [ ] 2.3.1 从 POM 排除 `junit-vintage-engine`，引入 `junit-jupiter`
- [ ] 2.3.2 `DaoTest.java`: `@RunWith`→`@ExtendWith`, `@Test` 包路径
- [ ] 2.3.3 `SensitiveTest.java`: 同上
- [ ] 2.3.4 `ScanHandler.java` + `SubscribeHandler.java`: 移除 `src/main` 中的 `@Test`（不规范）
- [ ] 2.3.5 验证: `./mvnw test-compile`

### 2.4 收尾
- [ ] 2.4.1 全局清理未使用的旧 import
- [ ] 2.4.2 Git 提交: `refactor(phase2): Swagger→Springdoc, JWT upgrade, JUnit 5`

---

## Phase 3: 代码质量

### 3.1 @Autowired 字段注入 → 构造器注入
- [x] 3.1.1~8 全模块改造 (72/78 文件迁移，6 文件保留):
  - `@Autowired\n private Type field` → `private final Type field` + `@RequiredArgsConstructor`
  - **保留字段注入 (6文件):** `AbstractMsgHandler`, `AbstractChatAIHandler`, `AbstractMsgMarkStrategy` (父类子类兼容) + `GroupMemberDao`, `UserBackpackServiceImpl`, `OssServiceImpl` (@Lazy 循环依赖)
- [x] 3.1.9 验证: `./mvnw clean compile` ✅

### 3.2 java.util.Date → java.time (35 文件)
- [x] 3.2.1 `jackson-datatype-jsr310` — SB 3.3.5 已内置, 无需额外依赖
- [x] 3.2.2 18 个 Entity: `Date` → `LocalDateTime` (MyBatis-Plus 3.5.9 内置 LocalDateTimeTypeHandler)
- [x] 3.2.3 DAO 层 (3): 参数/返回类型跟随 Entity
- [x] 3.2.4 VO/DTO 层 (6): 类型跟随 (1 个 Long lastModifyTime 保留, 来自前端API)
- [x] 3.2.5 `DateUtils.getEndTimeByToday()` → `ChronoUnit.MILLIS.between()` 重写
- [x] 3.2.6 Service/Util 层 (8): 适配 `.getTime()`→epoch, `DateUtil`→`ChronoUnit`, `Duration` 替换
- [x] 3.2.7 **跳过** — `JwtUtils` 恢复为 Date (auth0 java-jwt API 要求)
- [x] 3.2.8 **跳过** — MinIOTemplate 未见 Date 使用
- [x] 3.2.9 **跳过** — `write-dates-as-timestamps: true` 保持
- [x] 3.2.10 验证: `./mvnw clean compile + test-compile` ✅

### 3.3 清理
- [x] 3.3.1 移除 `ChatMemberStatisticResp.java` `@Deprecated` totalNum (零引用)
- [x] 3.3.2 `ChatGLM2Handler.java`: TODO → 方法描述 Javadoc
- [x] 3.3.3 `GroupMemberServiceImpl.java`: 保留 TODO (功能 backlog, 非重构范围)
- [x] 3.3.4 `logback.xml`: 添加 AsyncAppender 异步日志
- [x] 3.3.5 Git 提交: `1da4fde` — constructor injection + cleanup (3.2 单独提交)

---

## Phase 4: Docker 化部署

### 4.1 Dockerfile
- [ ] 4.1.1 创建多阶段 `Dockerfile` (Maven构建 + JDK 21 运行时)
- [ ] 4.1.2 创建 `.dockerignore`
- [ ] 4.1.3 验证: `docker build -t mallchat:latest .`

### 4.2 docker-compose.yml
- [ ] 4.2.1 编写 `docker-compose.yml` (MySQL 8.0 + Redis 7 + MinIO + RocketMQ + App)
- [ ] 4.2.2 编写 `application-docker.properties`
- [ ] 4.2.3 验证: `docker-compose up -d`

### 4.3 CI/CD
- [ ] 4.3.1 创建 `.github/workflows/build.yml` (编译+测试)
- [ ] 4.3.2 验证: Push 触发 CI

### 4.4 收尾
- [ ] 4.4.1 更新 README.md Docker 部署说明
- [ ] 4.4.2 Git 提交: `feat(phase4): Docker + docker-compose + CI/CD`

---

## Phase 5: 架构优化

### 5.1 消息持久化（Redis热 + MySQL分表冷 + MQ写缓冲）
- [ ] 5.1.1 设计消息表分表策略 (按 roomId 哈希 或 按月)
- [ ] 5.1.2 实现 MyBatis-Plus 动态表名拦截器
- [ ] 5.1.3 实现 Redis 热消息存储 (ZSet, TTL 8天)
- [ ] 5.1.4 实现 MQ 写缓冲: Producer → RocketMQ → Consumer 批量写
- [ ] 5.1.5 实现冷热读取路由 (7天内 Redis / 超7天 MySQL)
- [ ] 5.1.6 实现定时任务: 清理 Redis 过期消息

### 5.2 安全加固
- [ ] 5.2.1 清理 git 历史中的凭据
- [ ] 5.2.2 引入 jasypt 配置加密 或 全部环境变量注入
- [ ] 5.2.3 添加 CORS 配置
- [ ] 5.2.4 Swagger UI 添加 basic auth 限制
- [ ] 5.2.5 WebSocket Token: URL 传参 → Header 传递

---

## 验证清单

每个 Phase 完成后必须通过：

```bash
# Phase 1-3: 编译
./mvnw clean compile -pl mallchat-chat-server

# Phase 1-3 收尾: 完整打包
./mvnw clean package -DskipTests

# Phase 2: 测试编译
./mvnw test-compile

# Phase 4: Docker
docker build -t mallchat:latest .
docker-compose up -d
curl http://localhost:8080/actuator/health
```

---

## 相关文档

| 文档 | 路径 |
|------|------|
| 项目全景 | `CLAUDE.md` |
| 结构索引 | `docs/project-structure.md` |
| 重构规则 | `docs/refactoring-rules.md` |
| 执行日志 | `docs/refactoring-log.md` |
| 细粒度计划 | 本文件 |
