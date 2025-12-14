# Sweet Shop Management System - Architecture Documentation

## Overview

This document provides a detailed architectural overview of the Sweet Shop Management System, explaining the design decisions, technologies used, and how different components interact.

## System Architecture

### High-Level Architecture

```
┌─────────────────────────────────────────────────────────┐
│                    Client Layer                          │
│  ┌────────────────────────────────────────────────┐    │
│  │  React Application (SPA)                        │    │
│  │  - React Router (Navigation)                    │    │
│  │  - Context API (State Management)               │    │
│  │  - Axios (HTTP Client)                          │    │
│  └────────────────────────────────────────────────┘    │
└─────────────────────────────────────────────────────────┘
                           │
                           │ HTTPS/REST API
                           │ JWT Authentication
                           ▼
┌─────────────────────────────────────────────────────────┐
│                 Application Layer                        │
│  ┌────────────────────────────────────────────────┐    │
│  │  Spring Boot Application                        │    │
│  │  ┌──────────────────────────────────────┐     │    │
│  │  │  Controllers (REST Endpoints)        │     │    │
│  │  │  - AuthController                     │     │    │
│  │  │  - SweetController                    │     │    │
│  │  └──────────────────────────────────────┘     │    │
│  │  ┌──────────────────────────────────────┐     │    │
│  │  │  Security Layer                       │     │    │
│  │  │  - JWT Filter                         │     │    │
│  │  │  - Security Config                    │     │    │
│  │  └──────────────────────────────────────┘     │    │
│  │  ┌──────────────────────────────────────┐     │    │
│  │  │  Services (Business Logic)           │     │    │
│  │  │  - AuthService                        │     │    │
│  │  │  - SweetService                       │     │    │
│  │  └──────────────────────────────────────┘     │    │
│  │  ┌──────────────────────────────────────┐     │    │
│  │  │  Repositories (Data Access)          │     │    │
│  │  │  - UserRepository                     │     │    │
│  │  │  - SweetRepository                    │     │    │
│  │  └──────────────────────────────────────┘     │    │
│  └────────────────────────────────────────────────┘    │
└─────────────────────────────────────────────────────────┘
                           │
                           │ JDBC/JPA
                           ▼
┌─────────────────────────────────────────────────────────┐
│                   Data Layer                             │
│  ┌────────────────────────────────────────────────┐    │
│  │  PostgreSQL Database                            │    │
│  │  - users table                                  │    │
│  │  - sweets table                                 │    │
│  └────────────────────────────────────────────────┘    │
└─────────────────────────────────────────────────────────┘
```

## Backend Architecture

### Layer Breakdown

#### 1. Controller Layer
**Responsibility:** Handle HTTP requests and responses

**Components:**
- `AuthController`: Manages authentication endpoints
  - POST `/api/auth/register` - User registration
  - POST `/api/auth/login` - User login
  
- `SweetController`: Manages sweet-related operations
  - GET `/api/sweets` - List all sweets
  - GET `/api/sweets/search` - Search sweets
  - POST `/api/sweets` - Create sweet
  - PUT `/api/sweets/{id}` - Update sweet
  - DELETE `/api/sweets/{id}` - Delete sweet (Admin only)
  - POST `/api/sweets/{id}/purchase` - Purchase sweet
  - POST `/api/sweets/{id}/restock` - Restock sweet (Admin only)

**Design Patterns:**
- RESTful API design
- HTTP status codes for responses
- Request/Response DTOs for data transfer

#### 2. Security Layer
**Responsibility:** Authentication and authorization

**Components:**
- `JwtUtil`: JWT token generation and validation
- `JwtAuthenticationFilter`: Intercepts requests to validate tokens
- `CustomUserDetailsService`: Loads user details for authentication
- `SecurityConfig`: Spring Security configuration

**Flow:**
1. User provides credentials
2. System validates and generates JWT token
3. Client includes token in Authorization header
4. Filter validates token on each request
5. SecurityContext is populated with user details

**Security Features:**
- BCrypt password encoding
- JWT token-based authentication
- Role-based access control (USER, ADMIN)
- CORS configuration for frontend access
- Stateless session management

#### 3. Service Layer
**Responsibility:** Business logic and validation

**Components:**
- `AuthService`: User registration and login logic
  - Username uniqueness validation
  - Password encoding
  - JWT token generation
  
- `SweetService`: Sweet management business logic
  - CRUD operations
  - Purchase validation (quantity check)
  - Restock logic
  - Search and filter implementation

**Design Patterns:**
- Service facade pattern
- Transaction management
- Exception handling

#### 4. Repository Layer
**Responsibility:** Data access and persistence

**Components:**
- `UserRepository`: User data operations
  - `findByUsername(String username)`
  - `existsByUsername(String username)`
  
- `SweetRepository`: Sweet data operations
  - `findByNameContainingIgnoreCase(String name)`
  - `findByCategory(String category)`
  - `findByPriceBetween(Double min, Double max)`
  - `searchSweets(...)` - Complex search with multiple parameters

**Technology:**
- Spring Data JPA for data access
- JPQL for complex queries
- Automatic query derivation from method names

#### 5. Model Layer
**Responsibility:** Data entities and domain objects

**Entities:**
- `User`: Represents system users
  - id (Primary Key)
  - username (Unique)
  - password (Encrypted)
  - role (USER/ADMIN)
  
- `Sweet`: Represents products
  - id (Primary Key)
  - name
  - category
  - price
  - quantity

**Annotations:**
- `@Entity` - JPA entity
- `@Table` - Database table mapping
- `@Id` - Primary key
- `@GeneratedValue` - Auto-increment
- `@Column` - Column mapping
- Lombok annotations for boilerplate code

### Backend Technologies

| Technology | Purpose | Version |
|------------|---------|---------|
| Java | Programming Language | 17 |
| Spring Boot | Application Framework | 3.1.5 |
| Spring Security | Security Framework | 6.x |
| Spring Data JPA | Data Access | 3.x |
| PostgreSQL | Database | 15+ |
| H2 | Test Database | Latest |
| JWT | Authentication | 0.11.5 |
| Maven | Build Tool | 3.9+ |
| JUnit 5 | Testing Framework | 5.x |
| Mockito | Mocking Framework | 5.x |
| Lombok | Code Generation | Latest |

## Frontend Architecture

### Component Structure

```
src/
├── components/          # Reusable UI components
│   ├── SweetCard       # Displays individual sweet
│   ├── SearchBar       # Search and filter interface
│   ├── AddSweetModal   # Modal for adding sweets
│   └── PrivateRoute    # Route protection wrapper
├── pages/              # Page-level components
│   ├── Login          # Login page
│   ├── Register       # Registration page
│   └── Dashboard      # Main dashboard
├── context/           # Global state management
│   └── AuthContext    # Authentication context
├── services/          # API integration
│   └── api.js         # Axios configuration and API calls
└── App.jsx            # Root component with routing
```

### State Management

**React Context API:**
- `AuthContext` manages global authentication state
  - Current user information
  - JWT token
  - Login/logout functions
  - Admin role checking

**Local State:**
- Component-specific state using `useState`
- Form data
- Loading states
- Error messages

### Routing

**React Router v6:**
- `/login` - Public route
- `/register` - Public route
- `/dashboard` - Protected route (requires authentication)
- `/` - Redirects to login

**Route Protection:**
- `PrivateRoute` component checks authentication
- Redirects to login if not authenticated
- Uses `AuthContext` for user state

### API Integration

**Axios Configuration:**
- Base URL: `http://localhost:8080/api`
- Request interceptor adds JWT token
- Response interceptor handles errors
- Centralized API service functions

**API Service Structure:**
```javascript
authService.register()
authService.login()
sweetService.getAllSweets()
sweetService.searchSweets()
sweetService.createSweet()
sweetService.updateSweet()
sweetService.deleteSweet()
sweetService.purchaseSweet()
sweetService.restockSweet()
```

### Frontend Technologies

| Technology | Purpose | Version |
|------------|---------|---------|
| React | UI Library | 19.x |
| Vite | Build Tool | 7.x |
| React Router | Routing | 7.x |
| Axios | HTTP Client | 1.12.0 |
| CSS3 | Styling | - |

## Data Flow

### Authentication Flow

```
1. User enters credentials
   ↓
2. Frontend sends POST to /api/auth/login
   ↓
3. Backend validates credentials
   ↓
4. Backend generates JWT token
   ↓
5. Frontend stores token in localStorage
   ↓
6. Frontend includes token in subsequent requests
   ↓
7. Backend validates token on each request
```

### Purchase Flow

```
1. User clicks purchase button
   ↓
2. Frontend sends POST to /api/sweets/{id}/purchase
   ↓
3. Backend validates:
   - User is authenticated
   - Sweet exists
   - Sufficient quantity available
   ↓
4. Backend updates sweet quantity
   ↓
5. Backend returns updated sweet
   ↓
6. Frontend updates UI with new quantity
```

### Admin Operations Flow

```
1. Admin user adds/updates/deletes sweet
   ↓
2. Frontend sends request with JWT token
   ↓
3. Backend validates:
   - User is authenticated
   - User has ADMIN role
   ↓
4. Backend performs operation
   ↓
5. Backend returns result
   ↓
6. Frontend refreshes sweet list
```

## Database Schema

### Users Table
```sql
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'USER'
);
```

### Sweets Table
```sql
CREATE TABLE sweets (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    category VARCHAR(100) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    quantity INTEGER NOT NULL
);
```

### Indexes
- Primary keys on `id` columns (automatic)
- Unique index on `users.username`
- Potential indexes on `sweets.category` and `sweets.price` for performance

## Security Architecture

### Authentication
- **Method:** JWT (JSON Web Tokens)
- **Token Storage:** localStorage (frontend)
- **Token Expiration:** 24 hours
- **Password Encryption:** BCrypt with salt

### Authorization
- **Role-Based Access Control (RBAC)**
  - USER: Can view, search, and purchase sweets
  - ADMIN: All USER permissions + CRUD operations + restock

### API Security
- All `/api/sweets/*` endpoints require authentication
- DELETE and RESTOCK require ADMIN role
- CORS configured for specific origins
- Stateless session management

### Vulnerabilities Addressed
- ✅ SQL Injection: Prevented by JPA parameterized queries
- ✅ XSS: React escapes content by default
- ✅ CSRF: Stateless JWT authentication
- ✅ Password Storage: BCrypt hashing
- ✅ Authentication: JWT token validation
- ✅ Authorization: Role-based access control

## Design Patterns Used

### Backend
1. **Layered Architecture**: Clear separation of concerns
2. **Repository Pattern**: Data access abstraction
3. **Service Facade**: Business logic encapsulation
4. **DTO Pattern**: Data transfer objects
5. **Dependency Injection**: Spring IoC container
6. **Filter Chain**: JWT authentication filter
7. **Builder Pattern**: JWT token creation

### Frontend
1. **Component-Based Architecture**: Reusable UI components
2. **Container/Presentational Pattern**: Smart and dumb components
3. **Context Pattern**: Global state management
4. **Higher-Order Component**: PrivateRoute wrapper
5. **Service Layer**: Centralized API calls

## Testing Strategy

### Unit Tests
- Entity tests (User, Sweet)
- Service tests with mocked dependencies
- Isolated component testing

### Integration Tests
- Service + Repository integration
- H2 in-memory database for testing
- No external dependencies in tests

### Test-Driven Development
- Write failing test first (Red)
- Implement minimal code to pass (Green)
- Refactor while keeping tests green (Refactor)

## Performance Considerations

### Backend
- Database connection pooling
- JPA lazy loading for relationships
- Indexed queries for search operations
- Stateless architecture for horizontal scaling

### Frontend
- Code splitting with Vite
- Lazy loading of routes
- Optimized bundle size
- Efficient re-rendering with React

## Scalability

### Current Architecture Supports:
- Horizontal scaling of backend (stateless)
- Database replication for read operations
- CDN for frontend static files
- Load balancing for multiple backend instances

### Future Enhancements:
- Redis for session caching
- Message queue for async operations
- Microservices architecture
- Database sharding

## Deployment Architecture

### Development
```
Developer Machine
├── PostgreSQL (Docker or local)
├── Backend (mvn spring-boot:run)
└── Frontend (npm run dev)
```

### Production (Suggested)
```
Cloud Provider (AWS/GCP/Azure)
├── Application Load Balancer
├── Backend (EC2/Container)
│   └── Environment variables for config
├── Frontend (S3 + CloudFront / Netlify)
└── Database (RDS PostgreSQL)
```

## Monitoring and Logging

### Backend Logging
- Spring Boot Logback configuration
- DEBUG level for development
- INFO level for production
- Separate log files for different components

### Error Handling
- Global exception handler
- Meaningful error messages
- HTTP status codes for different errors

## Conclusion

This architecture provides:
- ✅ Clear separation of concerns
- ✅ Scalable design
- ✅ Secure authentication and authorization
- ✅ Testable components
- ✅ Maintainable codebase
- ✅ Modern technology stack
- ✅ RESTful API design
- ✅ Responsive frontend

The architecture follows industry best practices and provides a solid foundation for future enhancements.
