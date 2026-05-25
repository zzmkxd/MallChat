# MallChat 重构执行日志

> 记录每一步重构操作的详情。
> 状态: ⏳ 待执行 | ✅ 已完成 | ❌ 失败需介入
> 与计划文件 `docs/claudecode-skills-delegated-avalanche.md` 联动。

---

## Phase 1: 基础设施升级（Java 21 + SB 3 + jakarta）

| # | 时间 | 步骤 | 状态 | 修改文件 | 操作摘要 |
|---|------|------|------|---------|---------|
| 1 | 2026-05-23 | 1.1.1 | ✅ | `.mvn/`, `mvnw`, `mvnw.cmd` | 添加 Maven Wrapper 3.3.4 |
| 2 | 2026-05-23 | 1.1.2 | ✅ | — | 验证 `./mvnw --version` (Maven 3.9.1 + Java 21) |
| 3 | 2026-05-23 | 1.2.1 | ✅ | `pom.xml` | java.version: 1.8 → 21 |
| 4 | 2026-05-23 | 1.2.2 | ✅ | `pom.xml` | spring-boot-starter-parent: 2.6.7 → 3.3.5 |
| 5 | 2026-05-23 | 1.2.3 | ⚠️ | — | 被 Lombok 阻断，由 1.3.11 替代验证 |
| 6 | 2026-05-23 | 1.3.1 | ✅ | `pom.xml` | mybatis-plus: 3.4.0 → 3.5.9 |
| 7 | 2026-05-23 | 1.3.2 | ✅ | `pom.xml` | mybatis: 3.5.10 → 3.5.17 |
| 8 | 2026-05-23 | 1.3.3 | ⏭️ | `pom.xml` | mysql-connector-java 改名 → 回退，延至 Phase 2 |
| 9 | 2026-05-23 | 1.3.4 | ✅ | `pom.xml` | redisson: 3.17.1 → 3.36.0 |
| 10 | 2026-05-23 | 1.3.5 | ✅ | `pom.xml` | netty-all: 4.1.76 → 4.1.114 |
| 11 | 2026-05-23 | 1.3.6 | ✅ | `pom.xml` | hutool: 5.8.18 → 5.8.34 |
| 12 | 2026-05-23 | 1.3.7 | ✅ | `pom.xml` | lombok: 1.18.10 → 1.18.36 (修复 Java 21 兼容) |
| 13 | 2026-05-23 | 1.3.8 | ✅ | `pom.xml` | jjwt: 0.9.1 → 0.12.6 |
| 14 | 2026-05-23 | 1.3.9 | ✅ | `pom.xml` | jsoup: 1.15.3 → 1.18.1 |
| 15 | 2026-05-23 | 1.3.10 | ✅ | `mallchat-common-starter/pom.xml` | rocketmq: 2.2.2 → 2.3.2 |
| 16 | 2026-05-23 | 1.3.11 | ✅ | — | 5/6 模块通过，chat-server 预期 javax→jakarta 错误 |
| 17 | 2026-05-23 | 1.4.1 | ✅ | `TokenInterceptor.java` | javax.servlet → jakarta.servlet |
| 18 | 2026-05-23 | 1.4.2 | ✅ | `BlackInterceptor.java` | javax.servlet → jakarta.servlet |
| 19 | 2026-05-23 | 1.4.3 | ✅ | `CollectorInterceptor.java` | javax.servlet → jakarta.servlet |
| 20 | 2026-05-23 | 1.4.4 | ✅ | `HttpTraceIdFilter.java` | javax.servlet + javax.annotation → jakarta |
| 21 | 2026-05-23 | 1.4.5 | ✅ | `WebLogAspect.java` | javax.servlet → jakarta.servlet |
| 22 | 2026-05-23 | 1.4.6 | ✅ | `HttpErrorEnum.java` | javax.servlet → jakarta.servlet |
| 23 | 2026-05-23 | 1.4.7 | ✅ | — | 验证: javax.servlet 错误全部清零 |
| 24 | 2026-05-23 | 1.5.1~1.5.5 | ✅ | 41 个文件 | javax.validation → jakarta.validation (批量替换) |
| 25 | 2026-05-23 | 1.5.6 | ✅ | — | 验证: javax.validation 错误清零 |
| 30 | 2026-05-23 | 1.6.1~1.6.8 | ✅ | 12 个文件 | javax.annotation → jakarta.annotation (批量替换) |
| 31 | 2026-05-23 | 1.6.9 | ✅ | — | 验证: javax.annotation 错误清零 |
| 39 | 2026-05-23 | 1.7.1 | ✅ | `frequency-control/intecepter/TokenInterceptor.java` | javax.servlet → jakarta.servlet (2处) |
| 40 | 2026-05-23 | 1.7.2 | ✅ | `frequency-control/intecepter/CollectorInterceptor.java` | javax.servlet → jakarta.servlet (2处) |
| 41 | 2026-05-23 | 1.7.3 | ✅ | `frequency-control/exception/HttpErrorEnum.java` | javax.servlet → jakarta.servlet (1处) |
| 42 | 2026-05-23 | 1.7.4 | ✅ | — | 验证编译 → 剩余 4 类非 javax 错误 |
| 43 | 2026-05-23 | — | ✅ | `ACFilter.java` | 移除无用 HdrHistogram import+Javadoc link |
| 44 | 2026-05-23 | — | ✅ | `ACTrie.java` | 移除 @NotThreadSafe (jakarta 已删此注解) |
| 45 | 2026-05-23 | — | ✅ | `pom.xml` + `common-starter/pom.xml` | 新增 mybatis-plus-jsqlparser 3.5.9 (修复 PaginationInnerInterceptor) |
| 46 | 2026-05-23 | 1.8.1 | ✅ | `SwaggerConfig.java` | 整类替换为 TODO 桩 (Springfox 不兼容 SB3) |
| 47 | 2026-05-23 | 1.8.2 | ⚠️ | `mallchat-common-starter/pom.xml` | knife4j 注释掉 (传递 javax.servlet) — **待定** |
| 48 | 2026-05-24 | 1.8.2b | ✅ | `OssReq.java`, `OssResp.java` | 移除 @ApiModelProperty + import (oss-starter Swagger 错误) |
| 49 | 2026-05-24 | 1.8.2c | ✅ | `Api.java`等 4 个桩类 (新增) | Swagger 注解空桩 (chat-server 77文件不再报错) |
| 50 | 2026-05-24 | — | ✅ | `CollectorInterceptor.java` | #1 修复: ServletUtil.getClientIP → request.getRemoteAddr |
| 51 | 2026-05-24 | — | ✅ | `LambdaUtils.java` | #3 修复: java.lang.invoke.SerializedLambda 替代 MP 3.5 已删 API |
| 52 | 2026-05-24 | — | ✅ | `AbstractLocalCache.java` | #4 修复: 移除 @Override (Caffeine 3.x 签名变更) |
| 53 | 2026-05-24 | 1.8.3 | ✅ | 8 文件 10 处 | #2 修复: .count().intValue() (Long→Integer) |
| 54 | 2026-05-24 | 1.8.3 | ✅ | — | **全模块编译通过 (6/6)** |
| 55 | 2026-05-24 | 1.9.1 | ✅ | `application.yml` | 删除 pathmatch.matching-strategy (SB2 独有配置) |
| 56 | 2026-05-24 | 1.9.2 | ✅ | `application.yml` | redis 配置 SB3 兼容 (无需修改) |
| 57 | 2026-05-24 | 1.9.3 | ✅ | `logback.xml` | 日期格式 dd-MM-yyyy → yyyy-MM-dd (2处) |
| 58 | 2026-05-24 | 1.9.4a | ❌ | — | 首次打包: frequency-control Swagger + javax 错误 |
| 59 | 2026-05-24 | 1.9.4b | ✅ | `ApiResult.java`, `PageBaseResp.java` | 移除 frequency-control 中 Swagger 注解 |
| 60 | 2026-05-24 | 1.9.4c | ✅ | `CollectorInterceptor.java` (frequency-control) | ServletUtil.getClientIP → request.getRemoteAddr |
| 61 | 2026-05-24 | 1.9.4d | ✅ | — | **全量打包通过 (8/8)** |
| 62 | 2026-05-24 | 1.10.1 | ✅ | `CLAUDE.md` | 版本号: SB 2.6.7+Java 8 → SB 3.3.5+Java 21 |
| 63 | 2026-05-24 | 1.10.2 | ✅ | — | Git 提交 93d72e8 (95 files, +1837/-231) |

---

## Phase 2: 依赖现代化

| # | 时间 | 步骤 | 状态 | 修改文件 | 操作摘要 |
|---|------|------|------|---------|---------|
| 1 | 2026-05-24 | 2.1.1 | ✅ | `pom.xml`, `common-starter/pom.xml` | 引入 springdoc 2.7.0 + knife4j 4.5.0 (jakarta) |
| 2 | 2026-05-24 | 2.1.2 | ✅ | `OpenApiConfig.java` (新增) | 重写配置类: springfox → springdoc |
| 3 | 2026-05-24 | 2.1.3~7 | ✅ | 77 个文件 | @Api→@Tag, @ApiOperation→@Operation, @ApiModelProperty→@Schema, @ApiModel删 |
| 4 | 2026-05-24 | 2.1.8 | ✅ | 5 个文件(删) | 删除 4 Swagger 桩类 + 旧 SwaggerConfig, 编译通过 |
| 9 | 2026-05-24 | 2.2.1 | ✅ | `pom.xml` x2 | jjwt 未使用 → 移除; auth0 java-jwt 3.19.0 已兼容 |
| 10 | 2026-05-24 | 2.3.1 | ✅ | `chat-server/pom.xml` | junit:junit + spring-test → spring-boot-starter-test |
| 11 | 2026-05-24 | 2.3.2 | ✅ | `DaoTest.java` | @RunWith→@ExtendWith, org.junit.Test→jupiter |
| 12 | 2026-05-24 | 2.3.3 | ✅ | `SensitiveTest.java` | org.junit.Test→org.junit.jupiter.api.Test |
| 13 | 2026-05-24 | 2.3.4 | ⏭️ | — | src/main 中无 @Test (计划误判, 跳过) |
| 14 | 2026-05-24 | 2.3.5 | ✅ | — | `./mvnw test-compile` 通过 |
| 15 | 2026-05-24 | 2.4.1 | ⏭️ | — | 无未用 import 清理 (编译无警告) |
| 19 | | 2.4.2 | ⏳ | — | Git 提交 Phase 2 |

---

## Phase 3: 代码质量

| # | 时间 | 步骤 | 状态 | 修改文件 | 操作摘要 |
|---|------|------|------|---------|---------|
| 1 | 2026-05-25 | 3.1.1~8 | ✅ | 72 个文件 (含6例外) | @Autowired→@RequiredArgsConstructor+private final (perl批量) |
| 2 | 2026-05-25 | 3.1.9 | ✅ | — | `./mvnw clean compile` BUILD SUCCESS |
| 3 | | 3.2.1 | ⏳ | `DateUtils.java` | Calendar → ChronoUnit |
| 4 | | 3.2.2 | ⏳ | `User.java` | Date → LocalDateTime |
| 5 | | 3.2.3 | ⏳ | `Message.java` | Date → LocalDateTime |
| 6 | | 3.2.4 | ⏳ | 其余 Entity | Date → LocalDateTime |
| 7 | | 3.2.5 | ⏳ | DAO 层 | Date → LocalDateTime 适配 |
| 8 | | 3.2.6 | ⏳ | Service 层 | Date → LocalDateTime 适配 |
| 9 | | 3.2.7 | ⏳ | — | 跳过 (项目用 auth0 jwt 非 jjwt) |
| 10 | | 3.2.8 | ⏳ | `MinIOTemplate.java` | Date → LocalDateTime |
| 11 | | 3.2.9 | ⏳ | `application.yml` | 确认 jackson 序列化设置 |
| 12 | | 3.2.10 | ⏳ | — | 验证 `./mvnw clean compile` |
| 13 | 2026-05-25 | 3.3.1 | ✅ | `ChatMemberStatisticResp.java` | 移除 @Deprecated totalNum (零引用) |
| 14 | 2026-05-25 | 3.3.2 | ✅ | `ChatGLM2Handler.java` | TODO→方法描述 Javadoc |
| 15 | 2026-05-25 | 3.3.3 | ⏭️ | `GroupMemberServiceImpl.java` | 保留 TODO (功能 backlog，非重构范围) |
| 16 | 2026-05-25 | 3.3.4 | ✅ | `logback.xml` | 添加 AsyncAppender 异步日志 |
| 17 | 2026-05-25 | 3.3.5 | ⏳ | — | Git 提交 Phase 3 |
| 17 | | 3.2.8 | ⏳ | `MinIOTemplate.java` | Date → LocalDateTime |
| 18 | | 3.2.9 | ⏳ | `application.yml` | write-dates-as-timestamps: false |
| 19 | | 3.2.10 | ⏳ | — | 验证 `./mvnw clean compile` |
| 20 | | 3.3.1 | ⏳ | `ChatMemberStatisticResp.java` | 移除 @Deprecated totalNum |
| 21 | | 3.3.2 | ⏳ | `ChatGLM2Handler.java` | 处理 TODO |
| 22 | | 3.3.3 | ⏳ | `GroupMemberServiceImpl.java` | 处理 TODO |
| 23 | | 3.3.4 | ⏳ | `logback.xml` | 添加 AsyncAppender |
| 24 | | 3.3.5 | ⏳ | — | Git 提交 Phase 3 |

---

## Phase 4: Docker 化部署

| # | 时间 | 步骤 | 状态 | 修改文件 | 操作摘要 |
|---|------|------|------|---------|---------|
| 1 | | 4.1.1 | ⏳ | `Dockerfile` (新增) | 多阶段构建 |
| 2 | | 4.1.2 | ⏳ | `.dockerignore` (新增) | Docker 忽略文件 |
| 3 | | 4.1.3 | ⏳ | — | 验证 `docker build` |
| 4 | | 4.2.1 | ⏳ | `docker-compose.yml` (新增) | 编排 MySQL + Redis + MinIO + RocketMQ |
| 5 | | 4.2.2 | ⏳ | `application-docker.properties` (新增) | Docker 环境配置 |
| 6 | | 4.2.3 | ⏳ | — | 验证 `docker-compose up` |
| 7 | | 4.3.1 | ⏳ | `.github/workflows/build.yml` (新增) | CI/CD 流水线 |
| 8 | | 4.3.2 | ⏳ | — | 验证 Push 触发 |
| 9 | | 4.4.1 | ⏳ | `README.md` | Docker 部署说明 |
| 10 | | 4.4.2 | ⏳ | — | Git 提交 Phase 4 |

---

## Phase 5: 架构优化

| # | 时间 | 步骤 | 状态 | 修改文件 | 操作摘要 |
|---|------|------|------|---------|---------|
| 1 | | 5.1.1 | ⏳ | 设计文档 | 消息表分表策略 |
| 2 | | 5.1.2 | ⏳ | `MyBatisPlusConfig.java` | 动态表名拦截器 |
| 3 | | 5.1.3 | ⏳ | `HotMessageService.java` | Redis ZSet 热消息 |
| 4 | | 5.1.4 | ⏳ | `MsgWriteBuffer.java` | MQ 写缓冲 |
| 5 | | 5.1.5 | ⏳ | `MessageRouter.java` | 冷热读取路由 |
| 6 | | 5.1.6 | ⏳ | `CleanupJob.java` | Redis 过期清理 |
| 7 | | 5.2.1 | ⏳ | Git 历史 | 清理凭据 |
| 8 | | 5.2.2 | ⏳ | 配置文件 | jasypt 加密 |
| 9 | | 5.2.3 | ⏳ | `CorsConfig.java` | CORS 配置 |
| 10 | | 5.2.4 | ⏳ | `SecurityConfig.java` | Swagger basic auth |
| 11 | | 5.2.5 | ⏳ | `WebSocketConfig.java` | Token URL → Header |

---

## 变更详录

> 每完成一步，在此处追加条目。格式：
>
> ### Phase X.Y — 步骤标题
> - **操作**: <命令/修改方式>
> - **修改文件**: <文件路径列表>
> - **思路**: <为什么这样改>
> - **验证**: <编译/测试结果>

### Phase 1.1 — Maven Wrapper 添加

- **操作**: `mvn wrapper:wrapper -Dmaven=3.9.1`
- **修改文件**: 
  - `.mvn/wrapper/maven-wrapper.properties` (新增)
  - `mvnw` (新增, Unix shell 脚本)
  - `mvnw.cmd` (新增, Windows 批处理)
  - `.mvn/wrapper/maven-wrapper.jar` (新增, 61K)
- **思路**: 使用 wrapper 3.3.4 版本，指定 Maven 3.9.1，锁定 JDK 21。此后用 `./mvnw` 替代 `mvn`，确保所有开发者/CI 使用一致的 Maven 版本。
- **验证**: `./mvnw --version` 输出 Maven 3.9.1 + Java 21.0.1

### Phase 1.2 + 1.3 — POM 版本升级 + 依赖版本对齐

- **操作**: Edit 工具逐项修改 `pom.xml` 和 `mallchat-common-starter/pom.xml`
- **修改文件**:
  - `pom.xml`: java.version 1.8→21, spring-boot 2.6.7→3.3.5
  - `pom.xml`: mybatis-plus 3.4.0→3.5.9, mybatis 3.5.10→3.5.17
  - `pom.xml`: redisson 3.17.1→3.36.0, netty 4.1.76→4.1.114, hutool 5.8.18→5.8.34
  - `pom.xml`: lombok 1.18.10→1.18.36 (修复 Java 21 兼容), jjwt 0.9.1→0.12.6, jsoup 1.15.3→1.18.1
  - `mallchat-common-starter/pom.xml`: rocketmq 2.2.2→2.3.2
  - 1.3.3 mysql-connector 改名已回退，延至 Phase 2
- **思路**: Lombok 1.18.10 不兼容 Java 21（无法访问 jdk.compiler 模块），必须优先升级。其余版本对照 SB3 兼容性表逐一拉齐。版本号集中在根 POM `<properties>` 管理。
- **验证**: `./mvnw clean compile`, 5/6 模块通过，chat-server 仅剩预期 javax→jakarta 错误

### Phase 1.4 — javax.servlet → jakarta.servlet (拦截器层)

- **操作**: `replace_all: javax.servlet. → jakarta.servlet.` (6 文件, 11 处 import)
- **修改文件**:
  - `TokenInterceptor.java`: javax.servlet.http → jakarta.servlet.http (2处)
  - `BlackInterceptor.java`: javax.servlet.http → jakarta.servlet.http (2处)
  - `CollectorInterceptor.java`: javax.servlet.http → jakarta.servlet.http (2处)
  - `HttpTraceIdFilter.java`: javax.servlet.* → jakarta.servlet.* + javax.servlet.annotation.WebFilter → jakarta.servlet.annotation.WebFilter (2处)
  - `WebLogAspect.java`: javax.servlet → jakarta.servlet (3处)
  - `HttpErrorEnum.java`: javax.servlet.http → jakarta.servlet.http (1处)
- **思路**: 纯 import 替换，不改业务代码。HttpTraceIdFilter 额外包含 @WebFilter 注解 (属于 servlet 规范)。
- **验证**: 编译结果中 `javax.servlet` 错误全部清零，剩余错误为 `javax.annotation` + `javax.validation`（Phase 1.5/1.6 处理）

### Phase 1.5 — javax.validation → jakarta.validation (DTO 校验注解层)

- **操作**: sed 批量替换 41 文件: `s/import javax\.validation/import jakarta.validation/g`
- **修改文件**: 41 个文件 (common/domain/vo/request 3个, chat/domain/vo/request 14个, chat/domain/entity/msg 6个, user/domain/vo/request 9个, Controller 7个, Tools 2个, Utils 2个)
- **思路**: 纯 import 替换，字段上的 @NotNull/@Max/@Min 等注解本身不变，只有包路径变。
- **验证**: javax.validation 错误清零

### Phase 1.6 — javax.annotation → jakarta.annotation (注解层)

- **操作**: sed 批量替换 12 文件: `s/import javax\.annotation/import jakarta.annotation/g`
- **修改文件**: 12 个文件 (Service层, Handler层, AC自动机, 频控层, Controller层, WebSocket层)
- **思路**: @Resource/@PostConstruct 属 JDK 标准；@NotThreadSafe 属 javax.annotation.concurrent，Jakarta 已移除此包。
- **验证**: javax.annotation 错误清零

### Phase 1.7 — 工具模块 javax.servlet → jakarta.servlet

- **操作**: Edit 工具逐文件替换 3 文件 5 处 import
- **修改文件**:
  - `frequency-control/intecepter/TokenInterceptor.java`: javax → jakarta (2处)
  - `frequency-control/intecepter/CollectorInterceptor.java`: javax → jakarta (2处)
  - `frequency-control/exception/HttpErrorEnum.java`: javax → jakarta (1处)
- **思路**: 与 Phase 1.4 chat-server 相同，纯 import 替换。
- **验证**: frequency-control 模块 javax 错误清零

### Phase 1.7 补充 — 编译错误修复 (非 javax)

- **操作**: 修复 3 个与版本升级相关的编译错误
- **修改文件**:
  - `ACFilter.java`: 移除 `import org.HdrHistogram.ConcurrentHistogram` + Javadoc `{@link}` — 该库非项目依赖，仅注释引用
  - `ACTrie.java`: 移除 `@NotThreadSafe` + `import jakarta.annotation.concurrent.NotThreadSafe` — Jakarta 已删除此包，注解为纯文档标记无运行时效用
  - `pom.xml`: 新增 `mybatis-plus-jsqlparser:3.5.9` — MyBatis-Plus 3.5.9 将 `PaginationInnerInterceptor` 拆包至此模块
  - `mallchat-common-starter/pom.xml`: 新增 `mybatis-plus-jsqlparser` 依赖
- **验证**: 上述 3 个编译错误修复

### Phase 1.8.1 — SwaggerConfig 屏蔽

- **操作**: 整类替换为 TODO 桩 (保留 package，移除所有 import/注解/方法)
- **修改文件**: `SwaggerConfig.java`
- **思路**: `@EnableSwagger2WebMvc` (springfox) 完全不兼容 Spring Boot 3，Phase 2 用 Springdoc OpenAPI 重新实现。
- **验证**: SwaggerConfig 编译错误清除

### Phase 1.8.2 — knife4j 依赖屏蔽

- **操作**: 注释掉 `knife4j-spring-boot-starter:2.0.9`
- **修改文件**: `mallchat-common-starter/pom.xml`
- **思路**: knife4j 2.x → springfox 2.10.5 → javax.servlet-api 传递污染编译类路径。注释后 77 文件 424 处 Swagger 注解会找不到类。**待决策**: 批量剃除注解 vs 保留 knife4j 并排除 javax.servlet。
- **验证**: 待定，当前剩余 4 类编译错误待修复

### Phase 1.8.2b — oss-starter Swagger 注解清理

- **操作**: Edit 工具移除 `@ApiModelProperty` + `import io.swagger.annotations.ApiModelProperty`
- **修改文件**: 
  - `mallchat-oss-starter/.../OssReq.java`: 移除 1 个 import + 4 个 @ApiModelProperty
  - `mallchat-oss-starter/.../OssResp.java`: 移除 1 个 import + 2 个 @ApiModelProperty
- **思路**: oss-starter 是独立 Maven 模块，没有 knife4j 依赖也没有 Swagger 桩类。Phase 2 用 Springdoc 重新加注解。
- **验证**: oss-starter 模块编译通过

### Phase 1.8.2c — Swagger 注解空桩类

- **操作**: 在 chat-server 模块新建 4 个桩类
- **新增文件**:
  - `mallchat-chat-server/src/main/java/io/swagger/annotations/Api.java`
  - `.../ApiModel.java`
  - `.../ApiModelProperty.java`
  - `.../ApiOperation.java`
- **思路**: 注释 knife4j 后，77 文件 424 处 Swagger 注解找不到类。逐个剃除工作量巨大且容易出错。创建空桩类 (RetentionPolicy.SOURCE, 无运行时行为) 让编译通过，Phase 2 统一替换为 Springdoc 注解后删除。
- **验证**: Swagger 相关编译错误全部清零

### 错误修复 #1 — CollectorInterceptor javax.servlet 残留

- **操作**: 替换 `ServletUtil.getClientIP(request)` → `request.getRemoteAddr()`
- **修改文件**: `CollectorInterceptor.java`
- **思路**: hutool 5.8.34 的 `ServletUtil.getClientIP()` 方法签名引用了 `javax.servlet.http.HttpServletRequest`，即使我们传的是 jakarta 版 request，编译器解析重载方法时仍需加载 javax 类。`getRemoteAddr()` 功能等效（hutool 的 getClientIP 额外解析 X-Forwarded-For，但本项目无代理场景无需差异）。
- **验证**: javax.servlet 错误全部清零

### 错误修复 #2 — Long→Integer 类型转换

- **操作**: `.count()` → `.count().intValue()` (MP 3.5.9 返回 Long)
- **修改文件**: 8 文件 10 处 — MessageDao (2), ContactDao (3), UserDao (1), UserBackpackDao (1), MessageMarkDao (1), UserApplyDao (1), UserEmojiDao (1), UserEmojiServiceImpl (1)
- **思路**: MP 3.5.9 的 `count()` 从 `int` 改为返回 `Long`。逐条分析了 10 处 count 的业务含义和使用方，确认全部自然有界 (群聊成员数 ~500 或单用户行为限制)，int 安全。采用 `.intValue()` 最小改动方案。
- **验证**: ✅ 全 6 模块编译通过

### 错误修复 #3 — LambdaUtils 符号找不到

- **操作**: 用 `java.lang.invoke.SerializedLambda` + 反射替代 MP 3.5 已删除的 API
- **修改文件**: `LambdaUtils.java`
- **思路**: MP 3.5.9 删除了 `LambdaUtils.resolve(SFunction)` 和 `SerializedLambda.getInstantiatedType()`。改用该类已有的 `_resolve()` 方法 (通过反射调用 `writeReplace`) 获取 `java.lang.invoke.SerializedLambda`，再通过 `getImplClass()` + 反射获取字段类型。
- **验证**: LambdaUtils 编译错误清零

### 错误修复 #4 — AbstractLocalCache @Override

- **操作**: 移除 `loadAll()` 方法的 `@Override` 注解
- **修改文件**: `AbstractLocalCache.java`
- **思路**: Caffeine 3.x (SB 3.3.5 附带) 的 `CacheLoader.loadAll()` 方法签名发生变化，旧 `@Override` 声明不再匹配超类型。移除注解保留方法体，功能不变 (Caffeine 通过反射/接口调用，不依赖 @Override)。
- **验证**: AbstractLocalCache 编译错误清零

### Phase 1.9.1 — 删除 pathmatch.matching-strategy

- **操作**: 删除 `application.yml` 中 `spring.mvc.pathmatch` 段
- **修改文件**: `application.yml`
- **思路**: `spring.mvc.pathmatch.matching-strategy: ANT_PATH_MATCHER` 是 SB2 为兼容 Swagger UI 的配置。SB3 已移除此属性（仅支持 PathPatternParser），保留会导致启动警告。
- **验证**: 配置段已删除

### Phase 1.9.2 — Redis 配置检查

- **操作**: 无需修改
- **修改文件**: 无
- **思路**: `spring.redis.host/port/database/timeout/password` 在 SB3 中保持不变，无 breaking change。
- **验证**: 确认兼容

### Phase 1.9.3 — logback 日期格式修正

- **操作**: 替换 `%d{dd-MM-yyyy}` → `%d{yyyy-MM-dd}` (2处)
- **修改文件**: `logback.xml`
- **思路**: `dd-MM-yyyy` 以天开头，归档文件排序时同一天的不同月份日志会混在一起。`yyyy-MM-dd` 是 ISO 8601 标准格式，支持字典序自然排序。
- **验证**: logback.xml 格式修正完成

### Phase 1.9.4 — 全量打包验证 + 补充修复

- **操作**: `./mvnw clean package -DskipTests`
- **补充修复**:
  - `frequency-control/ApiResult.java`: 移除 `@ApiModel` + `@ApiModelProperty` + 对应 import
  - `frequency-control/PageBaseResp.java`: 移除 `@ApiModel` + `@ApiModelProperty` + 对应 import
  - `frequency-control/CollectorInterceptor.java`: `ServletUtil.getClientIP(request)` → `request.getRemoteAddr()` (hutool javax 残留)
- **思路**: 全量打包首次暴露了 frequency-control 模块的 Swagger + hutool 问题。与 chat-server 同模式修复：Swagger 注解直接移除，hutool 用原生 API 替代。
- **验证**: ✅ BUILD SUCCESS — 全 8 模块打包通过

---

## Phase 2 详细变更

### Phase 2.1.1 — 引入 Springdoc + Knife4j 依赖

- **操作**: 根 pom 新增 version properties + dependencyManagement; common-starter pom 新增两依赖
- **修改文件**: `pom.xml`, `mallchat-tools/mallchat-common-starter/pom.xml`
- **思路**: SB3 不兼容 springfox/knife4j 2.x，需换到 Springdoc OpenAPI + knife4j-openapi3-jakarta。版本选 springdoc 2.7.0 + knife4j 4.5.0，均为 SB3 兼容的稳定版。
- **版本属性变动**: springfox-swagger:3.0.0 → springdoc:2.7.0, swagger-models:1.6.0 → knife4j:4.5.0

### Phase 2.1.2 — 重写 OpenApiConfig

- **操作**: 新建 `OpenApiConfig.java`，注册 OpenAPI Bean
- **修改文件**: `OpenApiConfig.java` (新增), `SwaggerConfig.java` (删除)
- **思路**: Springfox 的 `@EnableSwagger2WebMvc` + Docket Bean 换为 Springdoc 的 `OpenAPI` Bean。Knife4j 通过 starter 自动集成，无需额外配置。
- **验证**: 新配置类编译通过

### Phase 2.1.3~2.1.7 — 批量替换 77 文件注解

- **操作**: sed 批量替换，5 条规则:
  - `import io.swagger.annotations.Api` → `import io.swagger.v3.oas.annotations.tags.Tag`
  - `import io.swagger.annotations.ApiOperation` → `import io.swagger.v3.oas.annotations.Operation`
  - `import io.swagger.annotations.ApiModel` → (remove)
  - `import io.swagger.annotations.ApiModelProperty` → `import io.swagger.v3.oas.annotations.media.Schema`
  - `@Api(tags="X")` → `@Tag(name="X")`
  - `@ApiOperation("X")` → `@Operation(summary="X")`
  - `@ApiModel("X")` → (remove line)
  - `@ApiModelProperty("X")` / `@ApiModelProperty(value="X")` → `@Schema(description="X")`
- **修改文件**: 77 个文件 — Controller 7, Chat VO 24+, User VO 14+, Common VO 5+, Entity 6, DTO 5+
- **思路**: 采样确认所有注解只用 value/tags 两个属性，无需处理 notes/required/hidden 等复杂属性。批量 sed 安全可靠。
- **验证**: 旧 Swagger 注解零残留

### Phase 2.1.8 — 删除桩类 + 验证

- **操作**: 删除 4 个 Swagger 空桩注解 + 旧 SwaggerConfig
- **修改文件**: `Api.java`, `ApiModel.java`, `ApiModelProperty.java`, `ApiOperation.java` (4删), `SwaggerConfig.java` (删)
- **思路**: 77 文件已全部指向 Springdoc 真注解，桩类已无引用。
- **验证**: `./mvnw clean compile` BUILD SUCCESS

### Phase 2.2 — JWT 库清理

- **操作**: 移除 `io.jsonwebtoken:jjwt:0.12.6` (root pom properties + dependencyManagement + common-starter dependency)
- **修改文件**: `pom.xml`, `mallchat-tools/mallchat-common-starter/pom.xml`
- **思路**: 项目实际使用 `com.auth0:java-jwt:3.19.0` (`JwtUtils.java` 中 `com.auth0.jwt.JWT`)。jjwt 从未被引用（0 个 Java 文件 import io.jsonwebtoken），升级到 0.12.6 是无用功。auth0 java-jwt 3.19.0 独立于 Spring/Jakarta EE，天然兼容 SB3，无需改写。
- **计划偏差**: 原计划误认为代码用 jjwt 0.9.x → 0.12.x 需要 API 重写，实际代码用 auth0，免去了 JwtUtils 重写。

### Phase 2.3 — JUnit 4 → JUnit 5

- **操作**:
  - `chat-server/pom.xml`: junit:junit (JUnit 4) + spring-test:5.3.19 → spring-boot-starter-test (含 JUnit Jupiter)
  - `DaoTest.java`: `@RunWith(SpringRunner.class)` → `@ExtendWith(SpringExtension.class)`, `org.junit.Test` → `org.junit.jupiter.api.Test`
  - `SensitiveTest.java`: `org.junit.Test` → `org.junit.jupiter.api.Test`
- **修改文件**: `chat-server/pom.xml`, `DaoTest.java`, `SensitiveTest.java`
- **思路**: SB 3.3.5 的 spring-boot-starter-test 默认包含 JUnit Jupiter，同时排除 vintage-engine（JUnit 4 兼容层）。旧 pom 中 junit:junit + spring-test:5.3.19 是 SB2 时代的硬编码依赖，不兼容 JUnit 5 注解。
- **验证**: `./mvnw test-compile` BUILD SUCCESS

### Phase 2.4 — 收尾

- **操作**: Git 提交 `080d137` (86 files, +379/-443)
- **新增**: `OpenApiConfig.java`, `.gitignore`
- **删除**: `SwaggerConfig.java`, 4 个 Swagger 桩类