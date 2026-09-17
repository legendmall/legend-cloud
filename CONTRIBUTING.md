# 贡献指南

感谢你关注 Legend Cloud！欢迎以任何形式参与项目：提 Issue、改进文档、提交代码、分享使用经验。

## 一、提问之前

为节省彼此时间，提问前建议先按顺序排查：

1. **README 的「快速开始」章节** —— 多数启动问题源于环境配置（Nacos 地址、中间件未就绪等）
2. **已关闭 Issue 中搜索关键词** —— 常见问题大多已有结论
3. **确认提问的仓库是否正确** —— 本项目按端拆分为多个生态仓：

| 仓库 | 说明 |
|------|------|
| [legend-cloud](https://gitee.com/legendmall/legend-cloud) | **后端主仓**（Spring Cloud Alibaba 微服务） |
| legend-cloud-admin-ui | 平台管理端（Vue3 + Vite） |
| legend-cloud-shop-ui | 商家端（Vue3 + Vite） |
| legend-cloud-user-ui | 用户端 H5（Vue3 + Vite） |
| legendshop | 经典单体版 |
| legendshop-ai-mall | AI 电商能力仓 |

前端问题请到对应 UI 仓提，能更快得到响应。

不想本地部署？可直接使用[五端在线演示](https://bbc7-pc.legendshop.cn)（体验账号见主仓 README）。

## 二、提 Issue

请使用仓库内置的 Issue 模板（问题反馈 / 使用咨询），并尽量提供：

- 版本号、JDK 版本、部署方式（Docker Compose / 源码启动）、数据库版本
- 完整的复现步骤
- 错误日志或截图（**请粘贴文本而非仅截图**，便于检索）

我们承诺**工作日 24 小时内首次响应**。

## 三、提交 PR

1. Fork 本仓库，基于 `master` 创建特性分支
2. 分支命名建议：`feat/xxx`、`fix/xxx`、`docs/xxx`
3. 提交信息遵循 Conventional Commits：

```
feat: 新增功能
fix: 修复缺陷
docs: 文档更新
refactor: 重构
test: 测试相关
chore: 构建或辅助工具变动
```

4. 提交前请确认：
   - 代码可编译通过
   - 不引入与本次改动无关的格式化变更
   - 涉及数据库变更的，同步提交对应 SQL 到 `db/` 目录
5. 发起 PR 时，请在描述中说明**改动动机**与**影响范围**

## 四、本地开发环境

| 组件 | 版本要求 |
|------|---------|
| JDK | 17+ |
| Maven | 3.6+ |
| MySQL | 8.0+ |
| Redis | 6.0+ |
| Nacos | 2.x |
| RabbitMQ | 3.8+ |
| Node.js | 16+（前端三端） |

V7.1.0 起提供 Docker Compose 一键启动，详见仓库 Release 说明。

## 五、代码规范

- 后端遵循阿里巴巴 Java 开发手册
- 统一使用 Lombok 简化代码（配置见 `lombok.config`）
- 禁止在代码中硬编码密钥、密码等敏感信息
- 新增配置项需同步更新配置文件示例与文档

## 六、安全问题

**请勿通过公开 Issue 报告安全漏洞。** 如发现安全风险，请直接联系维护团队处理。

## 七、许可证

本项目采用 AGPL-3.0 协议。提交 PR 即表示你同意你的贡献以相同协议授权。

---

**技术交流群**：QQ 一群 96642931 / QQ 二群 190339088
