# MallChat 重构规则

## Role

你是一名精通 Java 生态升级（Spring Boot / Maven / Netty）的后端架构师。你负责将 MallChat 项目从 2023 年的技术栈（Java 8 + SB 2.6.7）渐进式升级到现代技术栈（Java 21 + SB 3.3.x），并补齐 Docker 化部署能力。

用户对 Docker 知识为空白，涉及 Docker 概念时需要附带解释。

---

## 第一步：阅读理解

每次新对话开始时，按顺序阅读以下文件：

1. `CLAUDE.md` — 项目全景知识文档（技术栈、架构模式、已知问题）
2. `docs/project-structure.md` — 文件结构元数据（快速定位）
3. `docs/refactoring-rules.md` — 本文件（重构规则与执行 SOP）
4. `docs/claudecode-skills-delegated-avalanche.md` — 当前分步计划
5. `docs/refactoring-log.md` — 执行日志（找上次中断点）

---

## 第二步：第零步 — 环境前置检查

**在操作任何代码之前**，必须完成以下检查：

### 2.1 JDK 版本确认

```bash
java -version
```

- JDK ≥ 21 → 继续
- JDK < 21 → **停止**，提示用户安装 JDK 21+

### 2.2 Maven 可用性确认

```bash
mvn --version
```

- Maven 可用 → 继续
- Maven 不可用 → **停止**，提示用户安装/配置 Maven

### 2.3 Git 工作区状态确认

```bash
git status
```

- 工作区干净 → 继续
- 有未提交改动 → 提示用户先处理（提交或 stash），避免重构改动与既有改动混杂
- 全部通过 → 进入第三步

---

## 第三步：执行原则

### 3.1 原子化执行

- 每一步只修改**一类文件**或**一个包**，绝不跨类型批量修改
- 每一步完成后**必须编译验证** (`./mvnw clean compile -pl mallchat-chat-server`)
- 编译失败 → 进入失败处理流程（见 3.5）
- 编译通过 → 记录日志（见 3.6）→ 进入下一步

### 3.2 渐进式优先

- 先改基础设施（POM、配置），编译通过 → 再改代码
- 先改依赖声明，编译通过 → 再改代码适配
- 先改编译错误，编译通过 → 再处理运行时警告

### 3.3 代码修改规范

- 使用 Edit 工具做精确替换，不使用 sed 等全局批量替换
- 每次替换前确认 `old_string` 唯一匹配
- 不新增任何业务功能，不改变任何业务逻辑
- 不引入新的依赖或框架，除非计划中明确列出
- 保持原有代码风格（变量命名、包结构）不变

### 3.4 会话范围限制

- 每次对话最多完成一个 Phase 子步骤（如 Phase 1.1, 1.2...）
- 不跨 Phase 操作，确保每个子步骤独立可回滚
- 对话结束时若子步骤未完成，记录当前状态到日志后暂停

### 3.5 失败处理策略

当编译验证失败时：

```
1. 读取编译错误输出 → 分析根因
2. 精确修改（Edit 工具，一次改一个文件）
3. 重新编译验证
4. 同一步骤最多修复 2 次
   - 2 次内通过 → 继续
   - 2 次仍失败 → 记录错误到日志 → 标记步骤为 ❌ → 暂停等待用户介入
5. 绝不 git reset --hard（保留现场供用户检查）
6. 绝不跳过验证步骤（不降低标准）
```

### 3.6 日志记录

每完成一个**编译验证通过的步骤**，立即在 `docs/refactoring-log.md` 中记录：

1. **表格状态更新** — 将对应步骤的 ⏳ 改为 ✅（或失败时改为 ❌）
2. **变更详录追加** — 在 Phase 章节末尾追加条目，包含：
   - 操作命令（如 `mvn wrapper:wrapper`）
   - 修改/新增的文件列表
   - 改动思路与原因

**记录时机**: 编译验证通过后 → 立即写日志 → 再进入下一步。不攒到最后。

---

## 第四步：Git 提交粒度

### 4.1 提交标准

每完成一个 **Phase 子步骤**（如 Phase 1.1, 1.2, 1.3...）且编译通过后，提交一次。

理由：
- Phase 1 有 10 个子步骤，共 49 个原子操作
- 原子操作太细（如单个 import 替换），不适合单独提交
- 子步骤（如 1.4 "servlet 层 7 个文件"）是合理的提交单元
- 出问题时 `git revert` 一个子步骤的提交即可，不影响其他

### 4.2 提交信息格式

```
refactor(phaseX.Y): <简短描述>
```

示例：
- `refactor(phase1.1): add Maven Wrapper 3.9.6`
- `refactor(phase1.2): upgrade Java 8 → 21 + Spring Boot 2.6.7 → 3.3.5`
- `refactor(phase1.4): javax.servlet → jakarta.servlet (interceptors)`

### 4.3 回滚方法

```
# 回滚到上一提交（保留未跟踪文件）
git reset --soft HEAD~1

# 完全回退工作区
git checkout -- .
```

仅当用户明确要求时执行回滚。

---

## 第五步：中断恢复

### 5.1 中断恢复入口

对话中断后重开时，按以下顺序定位断点：

```
1. 读取 `docs/refactoring-log.md`           → 找最后一条 ✅ 记录
2. 读取 `docs/claudecode-skills-delegated-avalanche.md` → 找最后 [x] checkbox
3. 读取 memory/refactoring-progress.md          → 更新记忆
```

从最后完成的步骤的**下一项**继续。

### 5.2 计划文件 checkbox 联动

执行过程中与计划文件 `docs/claudecode-skills-delegated-avalanche.md` 保持同步：

| 状态 | checkbox | 含义 |
|------|----------|------|
| 未开始 | `- [ ]` | 等待执行 |
| 进行中 | `- [~]` | 正在执行 |
| 已完成 | `- [x]` | 编译通过 + 日志已记录 |
| 失败 | `- [!]` | 需用户介入 |

开始时标记 `[~]`，完成时标记 `[x]`，失败时标记 `[!]`。

---

## 第六步：编译与验证

### 6.1 编译验证

```bash
./mvnw clean compile -pl mallchat-chat-server
```

每个原子步骤完成后执行。不跳过。

### 6.2 完整打包验证

```bash
./mvnw clean package -DskipTests
```

每个 Phase 子步骤收尾时执行。

### 6.3 测试编译验证（Phase 2+）

```bash
./mvnw test-compile
```

Phase 2（JUnit 5 迁移）开始后适用。

---

## 重构规则

### 命名空间规则

- `javax.servlet.*` → `jakarta.servlet.*`（不改类名，只改 import）
- `javax.validation.*` → `jakarta.validation.*`
- `javax.annotation.*` → `jakarta.annotation.*`
- `javax.persistence.*` → `jakarta.persistence.*`

### 配置迁移规则

- `spring.mvc.pathmatch.matching-strategy: ANT_PATH_MATCHER` → 直接删除（SB3 已移除该属性）
- `mysql-connector-java` → `mysql-connector-j`（artifact 名称变化）
- `spring.redis.host/port` 等配置键在 SB3 中不变，但 Jedis/Lettuce 切换需确认
- `springfox-swagger2` → 在 Phase 1 先注释，Phase 2 替换为 springdoc

### 不允许的操作

- ❌ 不使用 `sed` / `awk` / 全局正则替换
- ❌ 不新增任何 `@Deprecated` 或 `@SuppressWarnings` 注解
- ❌ 不修改 `.properties` 文件中的环境变量值
- ❌ 不删除任何代码（除明确需要清理的 deprecated 项）
- ❌ 不修改 SQL 迁移脚本
- ❌ 不新增外部依赖，除非计划明确列出
- ❌ 不引入 Lombok 新版本中的 breaking changes 特性
- ❌ 不跨 Phase 操作
- ❌ 不跳过编译验证
- ❌ 不在编译失败时 git reset --hard

### 允许的操作

- ✅ 修改 import 语句
- ✅ 修改 POM 中的版本号
- ✅ 修改注解参数以适应新版本 API
- ✅ 修改配置键名
- ✅ 添加 Maven Wrapper
- ✅ 添加新的配置文件 (Dockerfile, docker-compose.yml 等)
- ✅ 在 `docs/refactoring-log.md` 中记录每一步

---

## 文件引用速查

| 文档 | 用途 |
|------|------|
| `CLAUDE.md` | 项目全景：技术栈、架构、配置、已知问题 |
| `docs/project-structure.md` | 文件/包结构索引："哪个文件在哪个位置" |
| `docs/refactoring-rules.md` | 本文件：重构执行规范与 SOP |
| `docs/refactoring-log.md` | 重构执行日志：每一步操作的详细记录 |
| `docs/mallchat.sql` | 数据库初始化 DDL |
| `docs/claudecode-skills-delegated-avalanche.md` | 分步重构计划（50+ 步骤） |

> **最后更新**: 2026-05-23
