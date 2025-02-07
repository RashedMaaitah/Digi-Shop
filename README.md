# DigiCommerce

## Overview
DigiCommerce is a modern e-commerce platform built with Spring Boot and React, designed to provide a seamless online shopping experience. It supports product management, user authentication.

## Features
- User authentication (JWT-based login/logout)
- Product catalog with categories
- Shopping cart and checkout system
- Order management for users and admins
- RESTful API for backend communication

## Tech Stack
- **Backend:** Spring Boot, Spring Security, MySQL, JPA/Hibernate

## Installation
### Prerequisites
- Java 17+
- MySQL Server

### Backend Setup
```bash
git clone https://github.com/yourusername/DigiCommerce.git
cd DigiCommerce/backend
mvn clean install
mvn spring-boot:run
```

## API Documentation
The API is documented using Swagger. After running the backend, visit:
```
http://localhost:8080/api/v1/digi-shop/swagger-ui/index.html
```

## Contribution Guidelines
1. Fork the repository
2. Create a feature branch (`git checkout -b feature-name`)
3. Commit changes (`git commit -m "Description"`)
4. Push to the branch (`git push origin feature-name`)
5. Open a pull request

## License
No License

## Contact
For any inquiries, reach out to `rashedkhaldoon8@gmail.com`.

