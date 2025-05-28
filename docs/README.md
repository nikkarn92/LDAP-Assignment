# 🔐 LDAP MFA Authentication Wrapper

### Author: Nikhil Karn

This is a **client-installable, platform-independent** Spring Boot wrapper that provides:

- ✅ LDAP authentication using Bind DN + search filter
- 🔁 Multi-tenant LDAP support with failover and pooling
- 🔒 MFA (Email-based OTP via pluggable provider APIs)
- 📊 Rate limiting and account lockout features
- 🧠 Intelligent auditing and brute-force attack detection
- 📦 Deployable as a WAR or standalone JAR
- 🚀 Redis-based OTP caching for stateless scaling
- 🐳 Docker-ready packaging for any environment
- 🧪 90%+ JUnit test coverage target with CI/CD ready setup
- 📝 Auto-generated Swagger API documentation

---

## 🔧 Build & Run

### Option 1: Run as JAR
```bash
./mvnw clean package
java -jar target/ldap-auth-wrapper.war
```

### Option 2: Run with Docker
```bash
docker build -t ldap-auth-wrapper .
docker run -p 8080:8080 ldap-auth-wrapper
```

---

## 🧪 Sample API Usage

### 1. Authenticate and Trigger OTP
```bash
curl -X POST http://localhost:8080/api/authenticate \
  -H "Content-Type: application/json" \
  -d '{"username": "einstein", "password": "password"}'
```

### 2. Verify OTP
```bash
curl -X POST http://localhost:8080/api/verify-otp \
  -H "Content-Type: application/json" \
  -d '{"username": "einstein", "otp": "123456"}'
```

---

## 📜 Configuration (application.yml)

```yaml
ldap:
  urls:
    - ldap://ldap.forumsys.com:389
  base-dn: dc=example,dc=com
  bind-dn: cn=read-only-admin,dc=example,dc=com
  bind-password: password
  user-search-filter: (uid={0})

otp:
  provider: redis
  redis:
    ttl-seconds: 300
    host: localhost
    port: 6379
  email:
    sender: otp@authwrapper.com
    subject: Your OTP Code
    body-template: Hello {username}, your OTP is {otp}.
```

---

## 🧠 Diagrams

### Class Diagram
![Authentication Flow](/class_diagram.png)

### Authentication & MFA Flow
![Authentication Flow](/auth_flow.png)

### Advanced Flow – MFA + Rate Limiting + Lockout
![MFA Security Flow](/mfa_security_flow.png)

---

## 📘 Swagger Docs

📘 Swagger UI: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

---

## 📁 Project Structure

```
src/main/java/com/nikhilkarn/authwrapper
├── config/
│   ├── LdapConfig.java
│   ├── OtpConfig.java
│   └── SecurityConfig.java
│
├── controller/
│   └── AuthController.java
│
├── exception/
│   ├── GlobalExceptionHandler.java
│   └── UserNotFoundException.java
│
├── model/
│   ├── AuthRequest.java
│   ├── AuthResponse.java
│   ├── OtpRequest.java
│   └── ApiResponse.java
│
├── provider/
│   ├── OtpProvider.java
│   ├── RedisOtpProvider.java
│   └── ExternalOtpApiProvider.java
│
├── service/
│   ├── LdapAuthService.java
│   ├── OtpService.java
│   ├── RateLimitService.java
│   └── LoginAuditService.java
│
└── LdapAuthWrapperApplication.java
```

---

## 🚧 Features Roadmap

| Feature                      | Status     |
|-----------------------------|------------|
| LDAP Bind Auth              | ✅ Done     |
| Multi-server failover       | ✅ Done     |
| Pluggable OTP Provider      | ✅ Done     |
| Redis OTP backend           | ✅ Done     |
| Swagger + JUnit             | ✅ Done     |
| Account Lockout             | ✅ Done     |
| CI/CD Integration           | 🟡 Planned  |
| SMS MFA via external API    | 🟡 Planned  |
| Admin Dashboard             | 🔜 Future   |

---

## 🔐 Security

- Secure LDAP bind (use StartTLS or LDAPS in production)
- Passwords cleared from memory after use
- OTPs expire in Redis; never stored in DB
- Rate limiting and account lockout policies
- All unexpected exceptions handled with generic responses

---

## 📫 Contact

If you'd like to extend this project or need consulting on IAM and security implementations:

**Email:** nikhil.karn@yourdomain.com  
**LinkedIn:** [linkedin.com/in/nikhilkarn](#)