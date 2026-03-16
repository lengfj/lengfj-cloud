# AGENTS.md

## Cursor Cloud specific instructions

### Project Overview

lengfj-cloud is a Spring Cloud microservices scaffold (Java 17, Spring Boot 3.0, Spring Cloud 2022.0.4, Spring Cloud Alibaba 2022.0.0.0). See `README.md` for module structure. Build with Maven: `mvn clean install -DskipTests`.

### Build Gotchas

- `lengfj-bom` is commented out from the root reactor but imported by other modules via `dependencyManagement`. It must be installed first: `cd lengfj-bom && mvn clean install -DskipTests`.
- `lengfj-common-all` is not in `lengfj-common/pom.xml` modules list, but is required by `lengfj-leaf`, `lengfj-system`, `lengfj-osme`, etc. After building the root project, install it: `cd lengfj-common/lengfj-common-all && mvn clean install -DskipTests`.
- Full build order: `lengfj-bom` → root project (`mvn clean install -DskipTests`) → `lengfj-common-all`.

### Infrastructure

| Service | Port | Notes |
|---------|------|-------|
| Nacos | 8848 | Required. Standalone mode: `/tmp/nacos/bin/startup.sh -m standalone` |
| MySQL | 3306 | Required for ops modules (Leaf, XXL-Job). Core gateway/system can start without it. |
| Redis | 6379 | Optional. Needed for distributed locking (Redisson). |

### Running Services

Services don't have `spring-boot-maven-plugin` in their own pom.xml (except ops modules). Use `mvn exec:java` to run:

```bash
# Gateway (port 8081)
cd lengfj-gateway && mvn exec:java -Dexec.mainClass="com.lengfj.cloud.gateway.GatewayApplication"

# System API (port 8888)
cd lengfj-system && mvn exec:java -Dexec.mainClass="com.lengfj.cloud.system.SystemApplication"
```

### Test

No unit tests exist yet. `mvn test` passes across all modules with no tests to run. The login endpoint at `/system-api/user/doLogin?username=zhang&password=123456` is a quick sanity test (returns "登录成功").

### Nacos Download

Nacos 2.2.3 is compatible with this project. Download and extract to `/tmp/nacos`:
```bash
curl -L -o /tmp/nacos-server-2.2.3.tar.gz 'https://github.com/alibaba/nacos/releases/download/2.2.3/nacos-server-2.2.3.tar.gz'
tar xzf /tmp/nacos-server-2.2.3.tar.gz -C /tmp/
```
