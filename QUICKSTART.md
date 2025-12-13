# Quick Start Guide 🚀

Get the Sweet Shop Management System up and running in 5 minutes!

## Prerequisites Check

Ensure you have these installed:
```bash
java -version    # Should show Java 17+
mvn -version     # Should show Maven 3.6+
node -v          # Should show Node 18+
npm -v           # Should show npm 8+
```

## Option 1: Quick Start with Docker (Recommended)

### Step 1: Start PostgreSQL
```bash
docker-compose up -d
```

### Step 2: Start Backend
```bash
cd backend
mvn spring-boot:run
```

### Step 3: Start Frontend
Open a new terminal:
```bash
cd frontend
npm install
npm run dev
```

### Step 4: Access Application
Open browser: `http://localhost:5173`

✅ Done! Register a new account and start using the app.

---

## Option 2: Manual Setup (No Docker)

### Step 1: Install PostgreSQL
**macOS:** `brew install postgresql@15 && brew services start postgresql@15`  
**Ubuntu:** `sudo apt install postgresql && sudo systemctl start postgresql`  
**Windows:** Download from postgresql.org

### Step 2: Create Database
```bash
psql -U postgres
CREATE DATABASE sweetshop;
\q
```

### Step 3: Start Backend
```bash
cd backend
mvn spring-boot:run
```

### Step 4: Start Frontend
```bash
cd frontend
npm install
npm run dev
```

### Step 5: Access Application
Open browser: `http://localhost:5173`

---

## First Time Setup

### 1. Register a User
Click "Register here" and create your account.

### 2. Create Sample Data (Optional)
If you want pre-populated data:

```bash
# Connect to database
psql -U postgres -d sweetshop

# Run sample data script
\i backend/sample-data.sql

# Exit
\q
```

**Sample credentials after loading data:**
- Username: `john_doe`, Password: `user123` (Regular User)
- Username: `admin`, Password: `admin123` (Admin User)

### 3. Make Your User an Admin (Optional)

To access admin features:

```bash
psql -U postgres -d sweetshop
UPDATE users SET role = 'ADMIN' WHERE username = 'your_username';
\q
```

Log out and log back in to see admin features.

---

## Verify Everything Works

### Backend Health Check
```bash
curl http://localhost:8080/api/sweets
# Should return 401 (authentication required) - this is correct!
```

### Frontend Check
Open `http://localhost:5173` - you should see the login page.

### Run Tests
```bash
cd backend
mvn test
# Should show: Tests run: 23, Failures: 0
```

---

## Common Issues

**Port 8080 in use?**
```bash
# Change backend port in application.properties
server.port=8081
# Update frontend API URL in src/services/api.js
```

**Port 5173 in use?**
Frontend will automatically use the next available port (5174, 5175, etc.)

**Database connection error?**
Check PostgreSQL is running:
```bash
# macOS
brew services list

# Linux
sudo systemctl status postgresql

# Docker
docker-compose ps
```

**"Cannot find module" in frontend?**
```bash
cd frontend
rm -rf node_modules package-lock.json
npm install
```

---

## What's Next?

1. ✅ Register and log in
2. ✅ Browse available sweets (if you loaded sample data)
3. ✅ Try the search functionality
4. ✅ Purchase some sweets
5. ✅ If admin: Add, update, delete, and restock items

---

## Stopping the Application

**Stop Frontend:** Press `Ctrl+C` in the terminal

**Stop Backend:** Press `Ctrl+C` in the terminal

**Stop Database (Docker):**
```bash
docker-compose down
```

**Stop Database (Manual):**
```bash
# macOS
brew services stop postgresql@15

# Linux
sudo systemctl stop postgresql
```

---

## Development Commands

```bash
# Backend
cd backend
mvn clean test          # Run tests
mvn clean install       # Build project
mvn spring-boot:run     # Run application

# Frontend
cd frontend
npm run dev            # Development server
npm run build          # Production build
npm run preview        # Preview production build
```

---

## Need More Help?

- 📖 **Detailed Setup:** See [SETUP_GUIDE.md](SETUP_GUIDE.md)
- 📋 **Full Documentation:** See [README.md](README.md)
- 🧪 **Test Reports:** See [TEST_REPORT.md](TEST_REPORT.md)
- 🐛 **Issues:** Check console logs or error messages

---

## Architecture at a Glance

```
Frontend (React)     ←→     Backend (Spring Boot)     ←→     Database (PostgreSQL)
localhost:5173              localhost:8080                    localhost:5432
```

---

**Happy Coding! 🍬**
