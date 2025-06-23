# lengfj-cloud

基于Spring Boot 3.0、Spring Cloud、Spring Cloud Alibaba的现代化微服务脚手架

## 🚀 项目特性

- **现代化技术栈**: 基于Spring Boot 3.0、Spring Cloud、Spring Cloud Alibaba构建
- **Java 17**: 采用最新的Java LTS版本，性能优异
- **微服务架构**: 模块化设计，职责分离清晰
- **API网关**: 基于Spring Cloud Gateway的统一路由和负载均衡
- **监控运维**: 集成监控、任务调度和管理工具
- **开发友好**: 完善的工具链和文档支持

## 🏗️ 系统架构

本项目采用微服务架构，包含以下核心模块：

### 核心模块

- **[lengfj-gateway](./lengfj-gateway/)** - API网关服务，负责路由和负载均衡
- **[lengfj-system](./lengfj-system/)** - 系统管理模块
- **[lengfj-osme](./lengfj-osme/)** - 业务逻辑模块
- **[lengfj-common](./lengfj-common/)** - 公共工具和组件
- **[lengfj-ops](./lengfj-ops/)** - 运维监控工具
- **[lengfj-dynamic-pagination](./lengfj-dynamic-pagination/)** - 动态分页服务

### 运维监控

[`lengfj-ops`](./lengfj-ops/) 模块包含：
- **lengfj-monitor-admin** - 应用监控面板
- **lengfj-xxl-job-admin** - 分布式任务调度
- **lengfj-sentinel-admin** - 熔断限流控制
- **lengfj-leaf** - 分布式ID生成服务

## 🛠️ 技术栈

| 组件 | 技术 |
|------|------|
| **框架** | Spring Boot 3.0 |
| **微服务** | Spring Cloud |
| **服务发现** | Spring Cloud Alibaba |
| **API网关** | Spring Cloud Gateway |
| **接口文档** | Knife4j (Swagger) |
| **构建工具** | Maven |
| **Java版本** | Java 17 |

## 🚦 快速开始

### 环境要求

- Java 17 或更高版本
- Maven 3.6+
- 推荐使用 IntelliJ IDEA

### 快速启动

1. **克隆项目**
   ```bash
   git clone https://github.com/lengfj/lengfj-cloud.git
   cd lengfj-cloud
   ```

2. **构建项目**
   ```bash
   mvn clean install
   ```

3. **启动服务**
   
   启动网关服务：
   ```bash
   cd lengfj-gateway
   mvn spring-boot:run
   ```
   
   启动系统服务：
   ```bash
   cd lengfj-system
   mvn spring-boot:run
   ```

4. **访问应用**
   - 网关地址: `http://localhost:8080`
   - 接口文档: `http://localhost:8080/doc.html`

## 📁 项目结构

```
lengfj-cloud/
├── lengfj-bom/                    # 依赖管理BOM
├── lengfj-common/                 # 公共工具和组件
│   ├── lengfj-common-core/        # 核心工具
│   ├── lengfj-common-feign/       # Feign客户端配置
│   ├── lengfj-common-mybatis/     # MyBatis配置
│   ├── lengfj-common-redis-lock/  # Redis分布式锁
│   └── ...
├── lengfj-gateway/                # API网关服务
├── lengfj-system/                 # 系统管理模块
├── lengfj-osme/                   # 业务模块
├── lengfj-ops/                    # 运维监控
│   ├── lengfj-monitor-admin/      # 监控面板
│   ├── lengfj-xxl-job-admin/      # 任务调度
│   ├── lengfj-sentinel-admin/     # 熔断器
│   └── lengfj-leaf/               # ID生成服务
└── lengfj-dynamic-pagination/     # 动态分页服务
```

## 🤝 参与贡献

欢迎提交 Pull Request 来改进项目！

## 📄 开源协议

本项目基于 [MIT License](LICENSE) 开源协议。

## 📞 技术支持

如果您有任何问题或需要帮助，请：
- 在GitHub上提交issue
- 查看各模块目录下的文档
- 参考配置文件中的示例
