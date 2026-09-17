# 更新日志

本项目所有值得记录的变更都会写在这里，格式参考 [Keep a Changelog](https://keepachangelog.com/)。

## [V7.1.0] - 2026-09-16

### 安全
- 移除 README 中的本地部署默认账号（原 `admin` 等明文口令）。克隆下来即为弱口令存在安全风险，改为首次部署自行初始化管理员账号
- 统一开源协议名为 `AGPL-3.0`（此前多处误写为 `APGL 3.0`）

### 文档
- 新增「🎮 在线演示」章节：五端体验地址与账号（平台端 / 商家端 / 用户端 H5 / 供应链开放平台）
- 新增差异化定位说明，明确项目面向企业级 B2B 供应链场景
- 新增「生态矩阵」章节，补全 6 个生态仓的互链导航
- 新增「标杆客户」章节
- 主仓及三个前端仓 README 同步补充生态互链

### 工程
- 提供 Docker Compose 一键启动方案，覆盖 Nacos / MySQL / Redis / RabbitMQ 编排
- 新增 Issue 模板（问题反馈 / 使用咨询）与标签体系
- 新增贡献指南 CONTRIBUTING.md

### 社区
- 清理历史遗留 Issue：对已解答的提问补充答复并关闭

## [V7.0.0] - 2025-01-13

- 微服务架构版本发布
- 基于 Spring Cloud Alibaba，JDK 17
- 多租户支持

---

> 更完整的发布说明见 [Releases](https://gitee.com/legendmall/legend-cloud/releases)
