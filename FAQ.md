# Legend Cloud 常见问题（FAQ）

> 面向第一次部署 Legend Cloud 的开发者。内容按「环境 → 启动 → 配置 → 运行期报错 → 前端」排列。
>
> 本文只写通用排查思路与已确认的版本/结构信息。**涉及具体类名、配置项 key、端口号的地方，一律以仓库实际代码与 Nacos 上的配置为准**；文中若出现「以实际代码为准」，即表示该值会随版本调整，请勿直接照抄。

---

## 0. 先确认环境版本

后端要求 **JDK 17+**（项目基于 Spring Boot 3.x，JDK 8/11 无法启动）。以下是仓库声明的主要组件版本：

| 组件 | 版本 |
|------|------|
| Spring | 6.0.13 |
| Spring Boot | 3.1.5 |
| Spring Cloud | 2022.0.4 |
| Spring Cloud Alibaba | 2022.0.0.0 |
| Spring Security | 6.1.4 |
| Spring Authorization Server | 1.1.3 |
| Nacos | 2.2.1 |
| Sentinel | 1.8.6 |
| Seata | 1.7.0 |
| Knife4j | 4.3.0 |
| Xxl-Job | 2.4.0 |
| MySQL | 8.0.17 |
| Elasticsearch | 8.8.1 |

其他依赖：MySQL / Redis / RabbitMQ（默认消息队列）/ Elasticsearch / MinIO 或阿里云 OSS。

> 版本对应关系以 [spring-cloud-alibaba 官方版本说明](https://github.com/alibaba/spring-cloud-alibaba/wiki/版本说明) 为准，跨大版本混用是绝大多数启动失败的根源。

---

## 1. 环境搭建

### 1.1 中间件必须先用 Docker 起起来吗？

不必，但推荐。仓库已提供一键编排文件：

- `doc/环境搭建/docker-compse一键安装/docker-compose.yaml`
- 同目录下另有 `elasticsearch/`、`minio/`、`mysql/`、`nacos/` 分项说明
- CentOS 装 Docker：`doc/环境搭建/Centos安装Docker.md`

用之前**先打开 yaml 确认端口映射和初始密码**，与本机已有服务（尤其是 3306、6379、8848、9200）冲突时改映射端口。

### 1.2 MySQL 装好但连不上

最常见的两类问题（初始化残留 data 目录、root 未设置密码）在下面这份文档里已有图文步骤，直接照做：

- `doc/常见问题与处理/mysql.md`

快速自检顺序：服务是否真的在跑 → 端口是否通 → 账号密码及 `mysql_native_password` 认证方式 → 是否授权了非 localhost 访问。

---

## 2. 数据库初始化

### 2.1 `db/` 下四个 SQL 分别是干什么的？

| 文件 | 用途 |
|------|------|
| `legend_cloud.sql` | 业务主库，商城全部业务表，**第一个执行** |
| `legend_cloud_nacos.sql` | Nacos 配置中心库，存储配置与命名空间数据 |
| `legend_cloud_seata.sql` | Seata 分布式事务所需表（含回滚日志表） |
| `legend_cloud_xxl_job.sql` | Xxl-Job 调度中心库 |

建议建四个独立 schema 分别导入，不要全部塞进同一个库，后续排查和配置隔离都更省事。

### 2.2 少导一个 SQL 会怎样？

- 没导 nacos 库 → 配置中心起不来或配置读不到，服务启动即报数据源/配置缺失；
- 没导 seata 库 → 涉及分布式事务的下单链路会报回滚日志表不存在；
- 没导 xxl_job 库 → 调度中心不可用，`legendshop-task` 中的定时任务无法注册。

### 2.3 字符集

统一用 `utf8mb4` + `utf8mb4_general_ci`（或 `0900_ai_ci`）。字符集不一致会导致 emoji、生僻字入库失败，以及联表时索引失效。

---

## 3. 启动顺序

微服务项目的启动顺序直接决定第一次能否跑通。建议顺序：

1. **基础中间件**：MySQL → Redis → RabbitMQ → Nacos（Nacos 依赖上一步导入的 `legend_cloud_nacos.sql`）
2. **导入 Nacos 配置**：把配置导入到正确的命名空间（见第 4 节）
3. **基础设施服务**：`legendshop-id`（分布式 ID）→ `legendshop-auth`（认证）
4. **网关**：`legendshop-gateway`
5. **业务服务**：`legendshop-basic` → `legendshop-product` → `legendshop-order` → `legendshop-user`
6. **任务服务**：`legendshop-task`
7. **前端三端**：平台端 / 商家端 / 用户端

服务清单来自仓库 `business/` 目录：

```
business
├── legendshop-auth      登录认证服务
├── legendshop-basic     系统基础服务
├── legendshop-gateway   Gateway 路由模块
├── legendshop-id        分布式 ID 生成服务
├── legendshop-order     订单服务
├── legendshop-product   商品服务
├── legendshop-task      定时任务
└── legendshop-user      用户服务
```

> 严格的依赖顺序以各服务的注册与调用关系为准；上面给出的是实践中不会出错的顺序，不是强制约束。

---

## 4. Nacos 配置

### 4.1 改了 Nacos 配置但不生效

按顺序排查：

1. **命名空间（namespace）对不上** —— 这是第一高频原因。应用侧配置的 namespace 与你在控制台编辑的那个必须一致，否则改的是另一份配置。
2. **group / dataId 不匹配** —— 确认应用读取的 dataId 与你编辑的文件名完全一致（含后缀）。
3. **配置没发布** —— 编辑后需要发布；只看不发布不会下发。
4. **应用没开动态刷新** —— 部分配置（数据源、端口等）**本来就不支持热更新**，改完必须重启服务。
5. **本地配置覆盖了远端** —— 如果应用本地配置文件里存在同名 key，优先级由配置加载顺序决定，以实际代码为准。

### 4.2 Nacos 起不来 / 服务注册不上

- Nacos 2.x 除 8848 外还占用 **9848、9849**（gRPC），Docker 部署时这几个端口都要映射出来，只映射 8848 会出现「控制台能开但服务注册失败」。
- 服务注册不上优先看：Nacos 地址是否正确 → namespace 是否一致 → 网络是否可达 → 服务名是否与他人冲突。
- 单机调试可用 standalone 模式；集群模式必须配置集群文件，否则会反复选主失败。

---

## 5. 认证与鉴权

### 5.1 登录报 401 / 403

项目基于 **Spring Authorization Server 1.1.3**，适配 OAuth 2.1。排查顺序：

1. 请求是否携带 token，请求头格式是否正确；
2. token 是否过期；OAuth 2.1 下刷新令牌的使用方式与 2.0 有差异，以实际代码为准；
3. 该路径是否在网关鉴权白名单内（登录、验证码、公开商品等通常需要放行）；
4. token 中的租户 / 用户标识与当前请求上下文是否匹配 —— 多租户场景下这是高频坑。

### 5.2 验证码不显示 / 校验失败

验证码模块在 `common-private/legendshop-common-captcha`。若前后端分离部署，注意验证码需要会话或缓存配合，跨域场景下要保证相关 Cookie / 请求头能正常传递。

### 5.3 演示账号

在线演示五端（平台端、商家端、用户端 PC / H5、供应链开放平台）的地址与体验账号见 [README](./README.md)。

> 商城四端体验密码统一为 `Aa123456`。**演示环境为示例数据、不承载真实交易，请勿录入真实业务或支付信息。**
>
> 供应链开放平台不提供测试账号，需自行注册并创建应用以获取 `client_id` / `client_secret`（见 [legendshop-open-api](https://gitee.com/legendmall/legendshop-open-api)）。
>
> 本地部署后请**立即修改所有初始账号密码**，生产环境使用强密码。

---

## 6. 运行期常见报错

### 6.1 网关 404 / 路由不通

- 目标服务是否已在 Nacos 注册成功（能在服务列表里看到）；
- 网关路由配置的 predicate 与前端实际请求路径是否一致（前缀、版本号最容易对不上）；
- 后端服务刚启动、尚未完成注册就发请求，会短暂 503，等几秒重试。

### 6.2 RabbitMQ 队列 / 交换机找不到

项目默认使用 RabbitMQ，公共集成在 `common/legendshop-common-rabbitmq`。

- 队列与交换机通常由消费端在启动时声明，**消费者所在服务未启动 = 队列不存在**，先启动对应服务再看；
- 交换机、队列、routing key 的具体名称与绑定关系以实际代码为准，本文不列举；
- 消息堆积先看消费者是否在线、是否有异常反复重试。

### 6.3 Seata 分布式事务报错

- 确认已导入 `db/legend_cloud_seata.sql`；
- **每个参与事务的业务库都需要回滚日志表**，只导一次、只在一个库建表会报「表不存在」；
- Seata 服务端与客户端版本需匹配（当前为 1.7.0）。

### 6.4 Elasticsearch 搜不到商品

- ES 版本为 8.8.1，注意 8.x 默认开启安全认证，客户端配置要带上认证信息；
- 首次部署后需要初始化索引并做一次全量数据同步，否则索引为空，表现为「接口 200 但结果为空」；
- 索引 mapping 一旦确定，改动需重建索引。

### 6.5 定时任务不执行

- `legendshop-task` 是否启动；
- 是否已导入 `db/legend_cloud_xxl_job.sql` 并正确配置调度中心地址；
- 执行器的 appname 是否与调度中心配置的一致；
- 任务是否在调度中心配置了启用状态与正确的时间表达式。

### 6.6 端口被占用

改服务端口或停掉占用进程。Windows：

```bash
netstat -ano | findstr :<端口>
taskkill /PID <PID> /F
```

Linux / macOS：

```bash
lsof -i :<端口>
kill -9 <PID>
```

---

## 7. 构建与编译

### 7.1 编译报 `javax.*` 找不到

Spring Boot 3.x 起 `javax.*` 已全面迁移为 `jakarta.*`。若你引入了第三方依赖或复制了旧版代码，会出现 `javax.servlet` / `javax.persistence` 找不到，替换为 `jakarta.*` 对应包即可。

### 7.2 Lombok 相关报错

仓库根目录有 `lombok.config`。需确认：IDE 已安装 Lombok 插件、已开启注解处理（Annotation Processing），且插件版本支持当前 JDK 17。

### 7.3 Maven 依赖拉不下来

- 检查是否使用了国内镜像源；
- 部分模块（`common-private` 下的能力）可能来自私有仓库或需手动安装到本地仓库，若 `mvn clean install` 报某模块找不到，先在仓库根目录整体构建一次。

### 7.4 构建命令

在仓库根目录执行：

```bash
mvn clean install -DskipTests
```

首次构建耗时较长，需耐心等待。

---

## 8. 前端三端

前端为 **Vue 3 + Vite**，三个独立仓库：

| 端 | 仓库 |
|----|------|
| 平台端 | [legend-cloud-admin-ui](https://gitee.com/legendmall/legend-cloud-admin-ui) |
| 商家端 | [legend-cloud-shop-ui](https://gitee.com/legendmall/legend-cloud-shop-ui) |
| 用户端 | [legend-cloud-user-ui](https://gitee.com/legendmall/legend-cloud-user-ui) |

### 8.1 跨域

开发环境用 Vite 的 dev server 代理转发到网关，**不要直接把网关地址写死在请求里**，否则必然跨域。代理目标地址以各前端仓库的配置文件为准。

### 8.2 页面能开但接口全部 404

代理目标地址指向了未启动的服务，或漏了网关的路由前缀。

### 8.3 登录后立刻退出

通常是 token 存储/读取不一致，或前后端域名不同导致 Cookie 未携带。检查前端 token 存取逻辑与代理配置。

---

## 9. 多租户

系统为多租户架构，数据隔离依赖租户标识贯穿全链路。

- 请求上下文中缺失租户标识，是最常见的「数据查不到 / 查到别人数据」原因；
- 新增业务表时，请一并考虑租户字段与隔离策略，避免破坏整体隔离；
- 具体的租户字段命名、隔离实现、豁免机制（如平台端跨租户查询）**以实际代码为准**。

---

## 10. 提 Issue 前请准备

把下面信息贴出来，能省掉好几轮来回：

1. 操作系统、JDK 版本（`java -version`）、Maven 版本；
2. 各中间件版本与部署方式（Docker / 原生）；
3. 报错服务的完整启动日志（**不要只截最后一行**，上下文里的 `Caused by` 才是关键）；
4. 复现步骤；
5. 已尝试过的排查动作。

提问前建议先搜索 [已有 Issue](https://gitee.com/legendmall/legend-cloud/issues)，重复问题大概率已有人提过。

---

## 11. 相关文档

| 内容 | 位置 |
|------|------|
| 运行环境与版本 | `doc/README.md` |
| Docker 一键安装 | `doc/环境搭建/docker-compse一键安装/` |
| MySQL 常见问题 | `doc/常见问题与处理/mysql.md` |
| 后端快速启动 | [Wiki · 快速启动（后端项目）](https://gitee.com/legendmall/legend-cloud/wikis/pages2/preview?sort_id=9258245&doc_id=4914160) |
| 贡献指南 | [CONTRIBUTING.md](./CONTRIBUTING.md) |
| 更新日志 | [CHANGELOG.md](./CHANGELOG.md) |
| 供应链开放平台 | [legendshop-open-api](https://gitee.com/legendmall/legendshop-open-api) |
| AI 商城 | [legendshop-ai-mall](https://gitee.com/legendmall/legendshop-ai-mall) |

---

> 本文件由项目维护团队维护，如有错漏欢迎提 Issue 指正。
