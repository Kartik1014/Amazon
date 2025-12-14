# Sweet Shop Management System - Setup Guide

This guide provides step-by-step instructions to set up and run the Sweet Shop Management System on your local machine.

## Quick Start (Using Docker)

If you have Docker installed, you can start the PostgreSQL database with one command:

```bash
docker-compose up -d
```

This will start PostgreSQL on port 5432 with:
- Database: `sweetshop`
- Username: `postgres`
- Password: `postgres`

## Manual Setup

### 1. Prerequisites Installation

#### Install Java 17
**macOS:**
```bash
brew install openjdk@17
```

**Ubuntu/Debian:**
```bash
sudo apt update
sudo apt install openjdk-17-jdk
```

**Windows:**
Download from [Oracle](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) or [Adoptium](https://adoptium.net/)

Verify installation:
```bash
java -version
```

#### Install Maven
**macOS:**
```bash
brew install maven
```

**Ubuntu/Debian:**
```bash
sudo apt install maven
```

**Windows:**
Download from [Apache Maven](https://maven.apache.org/download.cgi)

Verify installation:
```bash
mvn -version
```

#### Install Node.js and npm
**macOS:**
```bash
brew install node
```

**Ubuntu/Debian:**
```bash
curl -fsSL https://deb.nodesource.com/setup_20.x | sudo -E bash -
sudo apt install -y nodejs
```

**Windows:**
Download from [Node.js website](https://nodejs.org/)

Verify installation:
```bash
node -v
npm -v
```

#### Install PostgreSQL
**macOS:**
```bash
brew install postgresql@15
brew services start postgresql@15
```

**Ubuntu/Debian:**
```bash
sudo apt install postgresql postgresql-contrib
sudo systemctl start postgresql
```

**Windows:**
Download from [PostgreSQL website](https://www.postgresql.org/download/windows/)

### 2. Database Setup

#### Create Database
```bash
# Access PostgreSQL
psql -U postgres

# Create database
CREATE DATABASE sweetshop;

# Verify creation
\l

# Exit
\q
```

#### Update Database Credentials (if different)
Edit `backend/src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/sweetshop
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
```

### 3. Backend Setup

```bash
# Navigate to backend directory
cd backend

# Install dependencies and run tests
mvn clean install

# Run the application
mvn spring-boot:run
```

The backend will start on `http://localhost:8080`

You should see:
```
Started SweetShopApplication in X.XXX seconds
```

### 4. Frontend Setup

Open a new terminal:

```bash
# Navigate to frontend directory
cd frontend

# Install dependencies
npm install

# Start development server
npm run dev
```

The frontend will start on `http://localhost:5173` (or the next available port)

You should see:
```
VITE vX.X.X  ready in XXX ms
➜  Local:   http://localhost:5173/
```

### 5. Access the Application

Open your browser and go to: `http://localhost:5173`

## Testing the Application

### Backend Tests

```bash
cd backend
mvn test
```

Expected output:
```
Tests run: 23, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

### Frontend Build

```bash
cd frontend
npm run build
```

This ensures the frontend can be built for production.

## Creating Your First Admin User

1. Register a regular user through the UI
2. Connect to PostgreSQL and update the user role:

```bash
psql -U postgres -d sweetshop
```

```sql
-- View all users
SELECT * FROM users;

-- Update user to admin
UPDATE users SET role = 'ADMIN' WHERE username = 'your_username';

-- Verify
SELECT username, role FROM users;

-- Exit
\q
```

3. Log out and log back in to see admin features

## Common Issues and Solutions

### Issue: Port 8080 already in use
**Solution:** Either stop the service using port 8080 or change the backend port:
```properties
# In application.properties
server.port=8081
```
Also update frontend API URL in `frontend/src/services/api.js`

### Issue: Port 5432 (PostgreSQL) already in use
**Solution:** Either use the existing PostgreSQL instance or change the port in both docker-compose.yml and application.properties

### Issue: Database connection refused
**Solution:** 
1. Ensure PostgreSQL is running:
   ```bash
   # macOS
   brew services list
   
   # Linux
   sudo systemctl status postgresql
   ```
2. Verify database exists:
   ```bash
   psql -U postgres -l
   ```

### Issue: "Cannot find module" errors in frontend
**Solution:**
```bash
cd frontend
rm -rf node_modules package-lock.json
npm install
```

### Issue: Maven dependencies download slowly
**Solution:** Maven downloads dependencies on first run. This is normal and only happens once. Subsequent runs will be much faster.

### Issue: JWT token expired
**Solution:** Log out and log back in to get a fresh token. Token expiration is set to 24 hours by default.

## Environment Variables (Optional)

You can use environment variables instead of hardcoding values:

### Backend (.env or system environment)
```bash
export DB_URL=jdbc:postgresql://localhost:5432/sweetshop
export DB_USERNAME=postgres
export DB_PASSWORD=postgres
export JWT_SECRET=your-secret-key
export JWT_EXPIRATION=86400000
```

### Frontend (.env.local)
```bash
VITE_API_URL=http://localhost:8080
```

## Production Deployment Notes

### Backend
1. Build the JAR file:
   ```bash
   cd backend
   mvn clean package -DskipTests
   ```
   
2. Run the JAR:
   ```bash
   java -jar target/backend-1.0.0.jar
   ```

### Frontend
1. Build for production:
   ```bash
   cd frontend
   npm run build
   ```
   
2. Serve the `dist` folder using a web server (Nginx, Apache, or hosting service)

### Database
Use a managed PostgreSQL service (AWS RDS, Heroku Postgres, etc.) and update the connection string

## Next Steps

1. Register a user account
2. Create an admin user (follow instructions above)
3. Log in and start adding sweets
4. Test the search and filter functionality
5. Try purchasing sweets
6. Test admin features (add, update, delete, restock)

## Support

If you encounter issues not covered here:
1. Check the main README.md
2. Review the console/terminal output for error messages
3. Verify all prerequisites are correctly installed
4. Ensure all services are running

## Development Tips

### Hot Reload
- Frontend: Changes are automatically reflected (Vite HMR)
- Backend: Use Spring Boot DevTools for hot reload (add to pom.xml)

### API Testing
Use tools like:
- Postman
- Insomnia
- cURL
- Thunder Client (VS Code extension)

Example cURL request:
```bash
# Register
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","password":"password123"}'

# Login
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","password":"password123"}'

# Get sweets (with token)
curl -X GET http://localhost:8080/api/sweets \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

## Useful Commands

```bash
# Backend
cd backend
mvn clean              # Clean build artifacts
mvn compile            # Compile code
mvn test               # Run tests
mvn package            # Build JAR
mvn spring-boot:run    # Run application

# Frontend  
cd frontend
npm install            # Install dependencies
npm run dev            # Start dev server
npm run build          # Build for production
npm run preview        # Preview production build
npm run lint           # Run linter

# Database
psql -U postgres -d sweetshop    # Connect to database
\dt                              # List tables
\d users                         # Describe users table
\d sweets                        # Describe sweets table

# Docker
docker-compose up -d              # Start services
docker-compose down               # Stop services
docker-compose logs -f postgres   # View PostgreSQL logs
docker-compose ps                 # List running services
```

---

Happy coding! 🍬
