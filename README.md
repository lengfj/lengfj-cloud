# lengfj-cloud

A modern microservices scaffold based on Spring Boot 3.0, Spring Cloud, and Spring Cloud Alibaba.

基于Spring Boot 3.0、Spring Cloud、Spring Cloud Alibaba的现代化微服务脚手架。

## 🚀 Features

- **Modern Tech Stack**: Built with Spring Boot 3.0, Spring Cloud, and Spring Cloud Alibaba
- **Java 17**: Leverages the latest Java LTS version for optimal performance
- **Microservices Architecture**: Modular design with clear separation of concerns
- **API Gateway**: Centralized routing and load balancing with Spring Cloud Gateway
- **Monitoring & Operations**: Integrated monitoring, job scheduling, and admin tools
- **Developer Friendly**: Comprehensive tooling and documentation support

## 🏗️ Architecture

This project follows a microservices architecture with the following core modules:

### Core Modules

- **[lengfj-gateway](./lengfj-gateway/)** - API Gateway service for routing and load balancing
- **[lengfj-system](./lengfj-system/)** - System management and administration module
- **[lengfj-osme](./lengfj-osme/)** - Business logic module
- **[lengfj-common](./lengfj-common/)** - Shared utilities and common components
- **[lengfj-ops](./lengfj-ops/)** - Operations and monitoring tools
- **[lengfj-dynamic-pagination](./lengfj-dynamic-pagination/)** - Dynamic pagination service

### Operations & Monitoring

The [`lengfj-ops`](./lengfj-ops/) module includes:
- **lengfj-monitor-admin** - Application monitoring dashboard
- **lengfj-xxl-job-admin** - Distributed job scheduling
- **lengfj-sentinel-admin** - Circuit breaker and flow control
- **lengfj-leaf** - Distributed ID generation service

## 🛠️ Technology Stack

| Component | Technology |
|-----------|------------|
| **Framework** | Spring Boot 3.0 |
| **Microservices** | Spring Cloud |
| **Service Discovery** | Spring Cloud Alibaba |
| **API Gateway** | Spring Cloud Gateway |
| **Documentation** | Knife4j (Swagger) |
| **Build Tool** | Maven |
| **Java Version** | Java 17 |

## 🚦 Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6+
- Your favorite IDE (IntelliJ IDEA recommended)

### Quick Start

1. **Clone the repository**
   ```bash
   git clone https://github.com/lengfj/lengfj-cloud.git
   cd lengfj-cloud
   ```

2. **Build the project**
   ```bash
   mvn clean install
   ```

3. **Start the services**
   
   Start the gateway service:
   ```bash
   cd lengfj-gateway
   mvn spring-boot:run
   ```
   
   Start the system service:
   ```bash
   cd lengfj-system
   mvn spring-boot:run
   ```

4. **Access the application**
   - Gateway: `http://localhost:8080`
   - API Documentation: `http://localhost:8080/doc.html`

## 📁 Project Structure

```
lengfj-cloud/
├── lengfj-bom/                    # Bill of Materials for dependency management
├── lengfj-common/                 # Common utilities and shared components
│   ├── lengfj-common-core/        # Core utilities
│   ├── lengfj-common-feign/       # Feign client configurations
│   ├── lengfj-common-mybatis/     # MyBatis configurations
│   ├── lengfj-common-redis-lock/  # Redis distributed lock
│   └── ...
├── lengfj-gateway/                # API Gateway service
├── lengfj-system/                 # System management module
├── lengfj-osme/                   # Business module
├── lengfj-ops/                    # Operations and monitoring
│   ├── lengfj-monitor-admin/      # Monitoring dashboard
│   ├── lengfj-xxl-job-admin/      # Job scheduling
│   ├── lengfj-sentinel-admin/     # Circuit breaker
│   └── lengfj-leaf/               # ID generation service
└── lengfj-dynamic-pagination/     # Dynamic pagination service
```

## 🤝 Contributing

We welcome contributions! Please feel free to submit a Pull Request.

## 📄 License

This project is open source and available under the [MIT License](LICENSE).

## 📞 Support

If you have any questions or need help, please:
- Open an issue on GitHub
- Check the documentation in each module's directory
- Review the configuration files for setup examples
