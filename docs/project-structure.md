# MallChat 项目结构元数据

> 独立于主文档的文件结构索引，方便快速定位。
> 与 CLAUDE.md 互补——本文件专注"在哪"，CLAUDE.md 专注"是什么"。

---

## 根目录

```
MallChat/
├── pom.xml                          # 根 POM (多模块父级, SB 2.6.7, Java 1.8)
├── CLAUDE.md                        # 项目全景知识文档
├── README.md                        # 项目中文介绍 (含技术栈表)
├── .gitignore
├── docs/
│   ├── mallchat.sql                 # 18张表建表DDL
│   ├── version/                     # 增量迁移SQL (2023-06 ~ 2023-08)
│   └── image/                       # 架构图截图
├── mallchat-chat-server/            # 主应用模块
└── mallchat-tools/                  # 工具模块父级
    ├── mallchat-common-starter/     # 共享基础设施
    ├── mallchat-oss-starter/        # MinIO对象存储
    ├── mallchat-transaction/        # 事务安全调用
    ├── mallchat-frequency-control/  # 频率控制/限流
    └── mallchat-redis/              # 空模块占位
```

---

## 主应用包结构 (`mallchat-chat-server`)

```
com.abin.mallchat
├── MallchatCustomApplication.java   # 入口 (@SpringBootApplication, @MapperScan)
│
├── common.chat/                     # ==================== 聊天域 ====================
│   ├── controller/
│   │   ├── ChatController.java      # /capi/chat    消息发送/撤回/标记/已读
│   │   ├── ContactController.java   # /capi/contact 会话列表
│   │   └── RoomController.java      # /capi/room    群组房间CRUD
│   ├── consumer/
│   │   └── MsgSendConsumer.java     # RocketMQ消费者-消息推送
│   ├── dao/                         # 8个DAO (ContactDao, MessageDao, RoomDao, RoomFriendDao,
│   │                                #          RoomGroupDao, GroupMemberDao, MessageMarkDao, WxMsgDao)
│   ├── mapper/                      # MyBatis-Plus Mapper 接口
│   │   └── xml/                     # Mapper XML 映射文件
│   ├── domain/
│   │   ├── entity/                  # Contact, GroupMember, Message
│   │   │   └── msg/                 # BaseFileDTO 等消息类型文件DTO
│   │   ├── dto/                     # ChatMessageMarkDTO, ChatMsgRecallDTO, MsgReadInfoDTO, RoomBaseInfo
│   │   ├── enums/                   # ChatActiveStatusEnum, GroupRoleEnum, MarkEnum, MessageTypeEnum等
│   │   └── vo/
│   │       ├── request/             # CursorPageBaseReq, ChatMessageReq 等
│   │       │   ├── admin/           # 群管理入参
│   │       │   ├── member/          # 群成员入参
│   │       │   └── msg/             # 消息相关入参
│   │       └── response/            # ChatMessageResp 等
│   │           └── msg/             # 消息响应
│   ├── service/
│   │   ├── impl/                    # ChatServiceImpl, ContactServiceImpl, RoomServiceImpl等
│   │   ├── adapter/                 # MessageAdapter, ContactAdapter (Entity↔VO)
│   │   ├── cache/                   # GroupMemberCache, RoomCache, RoomFriendCache等
│   │   ├── helper/                  # ChatAuthHelper
│   │   └── strategy/
│   │       ├── msg/                 # 消息处理策略 (8种消息类型)
│   │       └── mark/                # 消息标记策略 (LikeStrategy, DisLikeStrategy)
│   └── constant/
│       └── GroupConst.java
│
├── common.chatai/                   # ==================== AI聊天机器人 ====================
│   ├── handler/                     # ChatGPTHandler, ChatGLM2Handler, AbstractChatAIHandler
│   ├── properties/                  # ChatGPTProperties, ChatGLM2Properties
│   ├── service/impl/                # ChatAIServiceImpl
│   ├── domain/builder/              # 消息构建器
│   ├── dto/                         # AI消息DTO
│   ├── enums/                       # ChatAIStatusEnum
│   └── utils/                       # AI工具类
│
├── common.common/                   # ==================== 公共基础设施 ====================
│   ├── config/
│   │   ├── CacheConfig.java         # Caffeine本地缓存
│   │   ├── InterceptorConfig.java   # 拦截器注册(/capi/**)
│   │   ├── RedissonConfig.java      # Redisson分布式锁
│   │   ├── RedisConfig.java         # RedisTemplate配置
│   │   ├── SwaggerConfig.java       # Knife4j/Swagger (待迁移)
│   │   ├── ThreadPoolConfig.java    # 3个线程池
│   │   └── SensitiveWordConfig.java # 敏感词过滤器
│   ├── intecepter/
│   │   ├── TokenInterceptor.java    # JWT校验 (order=-2)
│   │   ├── CollectorInterceptor.java # 请求信息收集→ThreadLocal (order=1)
│   │   ├── BlackInterceptor.java    # 黑名单校验 (order=2)
│   │   ├── HttpTraceIdFilter.java   # TraceId过滤器
│   │   └── WebLogAspect.java        # Web日志切面
│   ├── exception/
│   │   ├── GlobalExceptionHandler.java # @RestControllerAdvice
│   │   ├── BusinessException.java
│   │   └── HttpErrorEnum.java
│   ├── event/listener/              # 10+个Spring事件监听器
│   ├── algorithm/sensitiveWord/     # AC自动机 + DFA 敏感词过滤
│   ├── service/cache/               # 公共缓存服务
│   ├── service/frequencycontrol/    # 频控服务实现
│   ├── utils/
│   │   ├── DateUtils.java           # 日期工具 (java.util.Calendar)
│   │   ├── JwtUtils.java            # JWT (com.auth0:java-jwt)
│   │   ├── JsonUtils.java, RedisUtils.java
│   │   ├── RequestHolder.java       # ThreadLocal上下文
│   │   └── discover/                # URL扫描工具
│   ├── domain/
│   │   ├── dto/                     # RequestInfo, IpDetail等
│   │   ├── enums/                   # YesOrNoEnum, IdTypeEnum等
│   │   └── vo/request/response/     # PageBaseReq, ApiResult, CursorPageBaseReq
│   ├── annotation/                  # 自定义注解
│   ├── aspect/                      # AOP切面
│   ├── constant/                    # 公共常量 (RedisKey)
│   ├── factory/                     # 工厂类
│   └── handler/                     # GlobalUncaughtExceptionHandler
│
├── common.sensitive/                # ==================== 敏感词域 ====================
│   ├── dao/SensitiveWordDao.java
│   ├── domain/SensitiveWord.java
│   └── mapper/SensitiveWordMapper.java
│
├── common.user/                     # ==================== 用户域 ====================
│   ├── controller/
│   │   ├── UserController.java      # /capi/user    用户信息/徽章/拉黑
│   │   ├── FriendController.java    # /capi/friend  好友申请/列表
│   │   ├── OssController.java       # /capi/oss     文件上传URL
│   │   ├── UserEmojiController.java # /capi/emoji    表情管理
│   │   └── WxPortalController.java  # 微信OAuth回调
│   ├── consumer/                    # RocketMQ消费者 (MsgLoginConsumer, PushConsumer, ScanSuccessConsumer)
│   ├── dao/                         # UserDao, UserFriendDao, UserApplyDao, ItemConfigDao, BlackDao等
│   ├── mapper/                      # MyBatis-Plus Mapper
│   ├── domain/
│   │   ├── entity/                  # User, UserFriend, UserBackpack, IpInfo, Black, ItemConfig, Role等
│   │   ├── dto/                     # IpResult, WsChannelExtra等
│   │   ├── enums/                   # RoleEnum, BlackTypeEnum, WSReqTypeEnum, ItemEnum等
│   │   └── vo/request/response/     # 子目录: friend/, oss/, user/, ws/
│   └── service/
│       ├── impl/                    # LoginServiceImpl, UserServiceImpl, FriendServiceImpl, IpServiceImpl等
│       ├── adapter/                 # UserAdapter
│       ├── cache/                   # UserCache, UserInfoCache
│       └── handler/                 # WeChat消息处理器 (ScanHandler, SubscribeHandler)
│
└── common.websocket/                # ==================== Netty WebSocket ====================
    ├── NettyWebSocketServer.java    # 服务启动器 (@PostConstruct, 端口8090)
    ├── NettyWebSocketServerHandler.java # 消息处理器 (@Sharable)
    └── HttpHeadersHandler.java      # HTTP握手处理 (提取token/IP)
```

---

## 工具模块包结构 (`mallchat-tools`)

```
mallchat-tools/
├── mallchat-common-starter/src/main/java/com/abin/mallchat/
│   ├── common/
│   │   ├── FrequencyControlConstant.java
│   │   └── MDCKey.java
│   └── utils/
│       ├── JsonUtils.java
│       ├── RedisUtils.java
│       └── SpElUtils.java
│
├── mallchat-oss-starter/src/main/java/com/abin/mallchat/oss/
│   ├── MinIOConfiguration.java      # 自动配置类
│   ├── MinIOTemplate.java           # MinIO操作模板
│   ├── OssProperties.java           # 配置属性
│   ├── OssType.java                 # OSS类型枚举
│   ├── OssFile.java                 # 文件元数据
│   └── domain/
│       ├── OssReq.java
│       └── OssResp.java
│
├── mallchat-transaction/src/main/java/com/abin/mallchat/transaction/
│   ├── annotation/
│   │   ├── SecureInvoke.java        # @SecureInvoke 注解
│   │   └── SecureInvokeConfigurer.java
│   ├── aspect/
│   │   └── SecureInvokeAspect.java  # AOP切面
│   ├── config/
│   │   └── TransactionAutoConfiguration.java # @EnableScheduling
│   ├── dao/
│   │   └── SecureInvokeRecordDao.java
│   ├── domain/
│   │   ├── dto/SecureInvokeDTO.java
│   │   └── entity/SecureInvokeRecord.java
│   ├── mapper/
│   │   └── SecureInvokeRecordMapper.java
│   └── service/
│       ├── MQProducer.java          # RocketMQ生产者封装
│       ├── SecureInvokeHolder.java  # ThreadLocal持有者
│       └── SecureInvokeService.java # @Scheduled重试 (每5秒)
│
├── mallchat-frequency-control/src/main/java/com/abin/frequencycontrol/
│   ├── annotation/                  # @FrequencyControl, @FrequencyControlContainer
│   ├── aspect/                      # FrequencyControlAspect
│   ├── config/                      # InterceptorConfig (工具模块的拦截器)
│   ├── domain/dto/                  # FrequencyControlDTO, FixedWindowDTO, SlidingWindowDTO, TokenBucketDTO, RequestInfo
│   ├── domain/vo/response/          # ApiResult, PageBaseResp
│   ├── exception/                   # BusinessException, FrequencyControlException, HttpErrorEnum等
│   ├── intecepter/                  # TokenInterceptor, CollectorInterceptor (工具模块副本)
│   ├── mannager/                    # TokenBucketManager
│   ├── service/frequencycontrol/
│   │   ├── AbstractFrequencyControlService.java
│   │   ├── FrequencyControlStrategyFactory.java
│   │   ├── FrequencyControlUtil.java
│   │   ├── strategy/                # TotalCountWithInFixTime, SlidingWindow, TokenBucket
│   │   └── single/                  # FixWindow, SlideWindow, LeakyBucket, TokenBucket(单机版)
│   └── util/                        # AssertUtil, RequestHolder
│
└── mallchat-redis/                  # 空模块 (无Java代码)
```

---

## 配置文件位置

| 文件 | 路径 |
|------|------|
| 主配置 | `mallchat-chat-server/src/main/resources/application.yml` |
| 本地环境 | `mallchat-chat-server/src/main/resources/application-local.properties` (gitignored) |
| 测试环境 | `mallchat-chat-server/src/main/resources/application-test.properties` |
| 生产环境 | `mallchat-chat-server/src/main/resources/application-pro.properties` |
| SQL日志 | `mallchat-chat-server/src/main/resources/spy.properties` |
| Logback | `mallchat-chat-server/src/main/resources/logback.xml` |

---

## 数据库迁移文件

| 文件 | 日期 | 内容 |
|------|------|------|
| `docs/mallchat.sql` | 全量 | 18张表完整DDL |
| `docs/version/2023-06-04.sql` | 2023-06-04 | 初始版本 |
| `docs/version/2023-06-17.sql` | 2023-06-17 | — |
| `docs/version/2023-07-01.sql` | 2023-07-01 | — |
| `docs/version/2023-07-09.sql` | 2023-07-09 | — |
| `docs/version/2023-07-17.sql` | 2023-07-17 | — |
| `docs/version/2023-08-13.sql` | 2023-08-13 | — |
