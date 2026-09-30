# 部署指南：Docker / Docker Compose 一键搭建开发环境

> 目标：帮第一次接触本项目的开发者把 Nacos、MySQL、Redis、RabbitMQ、Elasticsearch、MinIO 等中间件跑起来。
>
> **本文只描述仓库中真实存在的文件与已声明的版本**。涉及镜像 tag、端口映射、账号口令的地方，请先打开对应文件核对后再执行；文中标注「以实际为准」的项会随版本调整，请勿直接照抄。

---

## 0. 动手之前：先看清仓库现状

这一节很重要，建议先读完再操作，能省掉大量无效尝试。

### 0.1 相关文件位置

```
doc/环境搭建/
├── README.md                       环境搭建总说明（含访问地址与默认口令）
├── Centos安装Docker.md             CentOS 安装 Docker CE 的步骤
└── docker-compse一键安装/          一键安装目录（目录名拼写为 compse，是仓库原文）
    ├── docker-compose.yaml
    ├── Centos安装Docker.md
    ├── elasticsearch/
    │   └── config/elasticsearch.yml
    ├── minio/
    │   └── data/
    ├── mysql/
    │   └── conf.d/docker.cnf
    └── nacos/
        └── logs/
```

### 0.2 ⚠️ `docker-compose.yaml` 当前是空文件

该文件在仓库中**实际大小为 0 字节**，即占位文件。直接执行 `docker-compose up -d` **不会拉起任何服务**，也不会报错，表现为"命令成功但什么都没起来"。

所以本文给出的是**逐组件启动**的可靠路径，而不是依赖这个空文件。

> 各组件 `data/`、`logs/` 目录下的 `readme.md` 同样是 0 字节，作用只是让 Git 保留空目录，属正常现象。

### 0.3 ⚠️ `doc/环境搭建/README.md` 存在历史遗留内容

该文件描述的搭建步骤可用，但混入了与当前代码不符的旧内容，照做前请注意：

| 位置 | 遗留内容 | 说明 |
|------|---------|------|
| 授权命令 | `chmod -R 777 ./rocketmq/...` | 当前项目消息队列默认使用 **RabbitMQ**（见主 README），rocketmq 相关目录在仓库中并不存在 |
| MinIO 建桶 | 桶名 `mall4cloud` | 疑为其他项目遗留的桶名，**请勿直接照抄**，桶名以你实际配置为准 |
| 全文 | 硬编码 IP `192.168.1.46` | 需全局替换为你的服务器 IP，这是该文档的设计，不是错误 |
| ES 索引 | 引用 `../es/order.md`、`../es/product.md` | `doc/环境搭建/` 下**没有 `es/` 目录**，这两个引用当前是失效的 |
| 版本表（`doc/README.md`） | Redis `3.9.6`、RabbitMQ 版本为空、多处下载链接指向 Maven 官网 | 该版本表有误（`3.9.6` 是 Maven 的版本号），**请以主 `README.md` 的版本表为准** |

### 0.4 版本以主 README 为准

主 `README.md` 声明的中间件版本：

| 组件 | 版本 |
|------|------|
| Nacos | 2.2.1 |
| MySQL | 8.0.17 |
| Elasticsearch | 8.8.1 |
| Seata | 1.7.0 |
| Xxl-Job | 2.4.0 |

Redis、RabbitMQ、MinIO 的版本**仓库中未声明**，请自行选定并固定版本，不要在生产环境使用 `latest`。

---

## 1. 前置条件

### 1.1 安装 Docker

- CentOS：照 `doc/环境搭建/docker-compse一键安装/Centos安装Docker.md` 操作（含卸载旧版本、配置镜像源等步骤）。
- 其他系统：参考 Docker 官方文档。
- 需要同时安装 **Docker Compose**（v2 用法为 `docker compose`，v1 为 `docker-compose`，命令略有差异）。

验证：

```bash
docker -v
docker compose version    # 或 docker-compose -v
```

### 1.2 资源建议

| 项目 | 建议 |
|------|------|
| 内存 | ≥ 8 GB（Elasticsearch 单独就要 1~2 GB，堆内存不足会直接 OOM 退出） |
| 磁盘 | ≥ 20 GB（镜像 + 数据卷） |
| 系统 | Linux 推荐；Windows / macOS 用 Docker Desktop，注意内存分配上限 |

### 1.3 端口占用

下表为各组件**常用默认端口**，实际以你启动时配置的映射为准。启动前先确认本机未被占用：

| 组件 | 默认端口 |
|------|---------|
| MySQL | 3306 |
| Redis | 6379 |
| Nacos | 8848（HTTP）、9848 / 9849（gRPC） |
| Elasticsearch | 9200（HTTP）、9300（集群通信） |
| Kibana | 5601 |
| MinIO | 9000（API）、控制台端口以所使用镜像版本为准 |
| RabbitMQ | 5672（AMQP）、15672（管理台） |
| Seata | 以实际配置为准 |

> **Nacos 2.x 必须开放 9848 / 9849**。这是最常见的坑：只映射 8848 时，控制台能打开，但服务注册失败，且错误信息通常不指向端口问题。

---

## 2. 目录与配置文件说明

### 2.1 `mysql/conf.d/docker.cnf`

该文件已配置好关键项，挂载进容器即可生效，主要内容：

| 配置项 | 值 | 作用 |
|--------|-----|------|
| `character-set-server` | `utf8mb4` | 支持 emoji 与生僻字 |
| `collation-server` | `utf8mb4_general_ci` | 与业务库保持一致 |
| `default-authentication-plugin` | `mysql_native_password` | 兼容旧客户端（MySQL 8 默认是 `caching_sha2_password`） |
| `default-time-zone` | `+8:00` | 避免时间字段差 8 小时 |
| `binlog-format` | `ROW` | 为数据同步 / 订阅预留 |
| `max_connections` | 1000 | 微服务连接数较多 |

字符集请务必保持 `utf8mb4` 且与业务库一致，否则会出现联表索引失效、特殊字符入库失败等问题。

### 2.2 `elasticsearch/config/elasticsearch.yml`

当前内容为：

```yaml
http.host: 0.0.0.0
```

仅放开监听地址。ES 8.x 默认开启安全认证（TLS + 账号密码），若按此文件启动后客户端连不上，需在配置中处理认证方式（关闭安全校验仅可用于本地开发）。**ES 8.x 与 7.x 的客户端配置差异很大，不要直接套用 7.x 的连接方式。**

### 2.3 数据目录

`elasticsearch/data`、`minio/data`、`nacos/logs` 用于持久化。容器首次启动时若报权限不足，按 `doc/环境搭建/README.md` 中的 `chmod` 命令授权；**不要长期以 777 权限运行生产环境**。

---

## 3. 启动中间件

以下为逐个启动的示例。请把 `<TAG>` 替换为你要固定的版本，`<你的IP>` 替换为实际地址。

### 3.1 MySQL

```bash
docker run -d --name legend-mysql \
  -p 3306:3306 \
  -e MYSQL_ROOT_PASSWORD=<改成你自己的密码> \
  -v $(pwd)/mysql/conf.d:/etc/mysql/conf.d \
  -v $(pwd)/mysql/data:/var/lib/mysql \
  mysql:8.0.17
```

### 3.2 Nacos

```bash
docker run -d --name legend-nacos \
  -p 8848:8848 -p 9848:9848 -p 9849:9849 \
  -e MODE=standalone \
  -v $(pwd)/nacos/logs:/home/nacos/logs \
  nacos/nacos-server:v2.2.1
```

单机调试用 `MODE=standalone`；集群模式必须额外配置集群文件，否则会反复选主失败。

### 3.3 Redis / RabbitMQ / MinIO

仓库未声明这三个组件的版本，请自行选定并固定：

```bash
docker run -d --name legend-redis  -p 6379:6379   redis:<TAG>
docker run -d --name legend-mq     -p 5672:5672 -p 15672:15672 rabbitmq:<TAG>
docker run -d --name legend-minio  -p 9000:9000 -p <控制台端口>:<控制台端口> \
  -v $(pwd)/minio/data:/data minio/minio:<TAG> server /data
```

> RabbitMQ 建议直接拉取带 `management` 标签的镜像，否则 15672 管理台不可用。

### 3.4 Elasticsearch + Kibana

```bash
docker run -d --name legend-es \
  -p 9200:9200 -p 9300:9300 \
  -e "discovery.type=single-node" \
  -v $(pwd)/elasticsearch/config/elasticsearch.yml:/usr/share/elasticsearch/config/elasticsearch.yml \
  -v $(pwd)/elasticsearch/data:/usr/share/elasticsearch/data \
  elasticsearch:8.8.1

docker run -d --name legend-kibana -p 5601:5601 kibana:8.8.1
```

ES 与 Kibana 的**版本必须完全一致**，否则 Kibana 会拒绝启动。

### 3.5 若你补齐了 compose 文件

当 `docker-compose.yaml` 有实际内容后，按仓库说明执行即可（首次拉取镜像约 7 GB，耗时较长）：

```bash
chmod -R 777 ./elasticsearch/data
chmod -R 666 ./minio/data
docker compose up -d --build
```

---

## 4. 初始化

### 4.1 导入数据库

`db/` 下四个 SQL，建议建四个独立 schema 分别导入，不要混在一个库里：

| 文件 | 用途 |
|------|------|
| `legend_cloud.sql` | 业务主库，**第一个执行** |
| `legend_cloud_nacos.sql` | Nacos 配置中心库 |
| `legend_cloud_seata.sql` | Seata 分布式事务表（含回滚日志表） |
| `legend_cloud_xxl_job.sql` | Xxl-Job 调度中心库 |

字符集统一 `utf8mb4`。

### 4.2 导入 Nacos 配置

把配置导入到**正确的命名空间**，并与应用侧读取的 namespace 对齐。命名空间不一致是"改了配置不生效"的第一大原因。

### 4.3 MinIO 建桶

1. 打开 MinIO 控制台（端口以实际映射为准）；
2. 创建桶，**桶名以你实际配置为准**（`doc/环境搭建/README.md` 中的 `mall4cloud` 疑为遗留值）；
3. 若要让前端能直接读取图片，需将该桶的访问策略设为公开读；
4. 在应用配置中填写对应的 endpoint、accessKey、secretKey、桶名。

### 4.4 Elasticsearch 索引

`doc/环境搭建/README.md` 提到需创建 `order`、`product` 两个索引，但其引用的 `../es/order.md`、`../es/product.md` **在当前仓库中不存在**。

索引结构请以实际代码中的实体定义与索引初始化逻辑为准；首次部署后还需做一次全量数据同步，否则索引存在但为空，表现为"接口 200 但查不到结果"。

---

## 5. 默认访问地址与口令

以下为 `doc/环境搭建/README.md` 中给出的**开发环境默认值**，把 IP 换成你的服务器地址：

| 组件 | 地址 | 默认账号 |
|------|------|---------|
| MySQL | `ip:3306` | `root` / 文档默认口令 |
| MinIO | `http://ip:9000` | `admin` / 文档默认口令 |
| Redis | `ip:6379` | 无密码 |
| Nacos | `http://ip:8848/nacos` | `nacos` / `nacos` |
| Kibana | `http://ip:5601` | — |

> 🔒 **安全提醒**：这些都是弱口令，**仅可用于本地开发环境**。任何对外可访问的环境（含测试服务器）都必须修改，并限制端口仅内网可达。默认口令属于公开信息，暴露在公网上等同于不设防。
>
> 在线演示环境的体验账号见主 `README.md`；供应链开放平台不提供测试账号，需自行注册并创建应用。

---

## 6. 验证是否就绪

按顺序自检，任一项不通就先解决再往下走：

```bash
# 容器状态（STATUS 应为 Up，而非 Restarting / Exited）
docker ps -a

# 逐个探活
curl http://<IP>:8848/nacos/          # Nacos 控制台
curl http://<IP>:9200                  # ES（8.x 需带认证信息）
curl http://<IP>:5601                  # Kibana
docker logs <容器名>                    # 起不来时看日志
```

- Nacos 控制台能看到服务列表为空 = 正常，等后端启动后应能看到注册上来的服务；
- 容器反复 `Restarting`：优先看 `docker logs`，ES 多半是内存不足，MySQL 多半是数据目录残留或权限问题。

---

## 7. 常见故障

| 现象 | 常见原因 |
|------|---------|
| `docker compose up -d` 成功但什么都没起来 | 当前 `docker-compose.yaml` 是 0 字节空文件（见 0.2） |
| Nacos 控制台能开，服务注册失败 | 9848 / 9849 未映射；或 namespace 不一致 |
| ES 启动后立刻退出 | 内存不足，或 `vm.max_map_count` 过小 |
| Kibana 报版本不匹配 | ES 与 Kibana 版本必须一致 |
| MySQL 客户端连不上 | 认证插件（`caching_sha2_password` vs `mysql_native_password`）、端口、用户授权 host |
| 改了 Nacos 配置不生效 | namespace / dataId 不一致、未发布、或该配置本身不支持热更新 |
| 前端能开但接口全 404 | 代理目标指向了未启动的服务，或漏了网关路由前缀 |

MySQL 无法启动 / 无法连接的图文排查步骤见 `doc/常见问题与处理/mysql.md`；更多后端启动问题见 [FAQ.md](../FAQ.md)。

---

## 8. 已知遗留问题（待修复）

记录在此，方便后续集中处理，也欢迎提 PR：

| # | 问题 | 位置 |
|---|------|------|
| 1 | `docker-compose.yaml` 为 0 字节空文件，一键启动实际不可用 | `doc/环境搭建/docker-compse一键安装/` |
| 2 | `README.md` 仍描述 rocketmq，与当前 RabbitMQ 不符 | `doc/环境搭建/README.md` |
| 3 | MinIO 桶名写成 `mall4cloud`，疑为其他项目遗留 | `doc/环境搭建/README.md` |
| 4 | 引用的 `../es/order.md`、`../es/product.md` 不存在 | `doc/环境搭建/README.md` |
| 5 | 版本表错误：Redis 写成 `3.9.6`、RabbitMQ 缺失、多处下载链接指向 Maven 官网 | `doc/README.md` |
| 6 | 目录名 `docker-compse` 拼写疑似笔误 | `doc/环境搭建/` |

---

## 9. 相关文档

| 内容 | 位置 |
|------|------|
| 运行环境与版本总表 | `doc/README.md`（版本有误，以主 README 为准） |
| CentOS 安装 Docker | `doc/环境搭建/docker-compse一键安装/Centos安装Docker.md` |
| MySQL 常见问题 | `doc/常见问题与处理/mysql.md` |
| 后端启动与常见报错 | [FAQ.md](../FAQ.md) |
| 后端快速启动 | [Wiki · 快速启动（后端项目）](https://gitee.com/legendmall/legend-cloud/wikis/pages2/preview?sort_id=9258245&doc_id=4914160) |

---

> 本文件由项目维护团队维护，如有错漏欢迎提 Issue 指正。
