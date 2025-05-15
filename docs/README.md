# LDAP MFA Authentication Wrapper

### Author: Nikhil Karn

This Maven-based Spring Boot project implements:
- LDAP authentication (multi-tenant)
- Email-based OTP MFA
- Swagger documentation
- In-memory and Redis-compatible caching
- WAR deployable and Docker-ready
- CI/CD and 90%+ JUnit test coverage target

### Build & Run

```bash
./mvnw clean package
java -jar target/ldap-auth-wrapper.war
```

### API Docs

Visit: `http://localhost:8080/swagger-ui/index.html`
