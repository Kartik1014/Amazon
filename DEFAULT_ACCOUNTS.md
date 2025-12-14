# Default Accounts - Sweet Shop Management System

## Overview

The Sweet Shop Management System automatically creates default accounts when the application starts for the first time. This makes it easy to test and demo the application without needing to register users manually.

## Default Accounts

### Admin Account
- **Username:** `admin`
- **Password:** `admin`
- **Role:** ADMIN
- **Privileges:**
  - All user privileges
  - Create new sweets
  - Update sweet details
  - Delete sweets
  - Restock inventory

### Regular User Account
- **Username:** `user`
- **Password:** `user`
- **Role:** USER
- **Privileges:**
  - View all sweets
  - Search and filter sweets
  - Purchase sweets

## Quick Login UI

The login page features two convenient quick login buttons:

### 👑 Login as Admin (Golden Button)
- Instantly logs in with admin privileges
- Golden/amber gradient styling
- Shows admin badge on dashboard

### 👤 Login as User (Blue Button)
- Instantly logs in with regular user privileges
- Blue gradient styling
- Standard user experience

## How It Works

### Backend - DataInitializer

The `DataInitializer` class implements `CommandLineRunner` and runs automatically when the Spring Boot application starts:

```java
@Component
public class DataInitializer implements CommandLineRunner {
    
    @Override
    public void run(String... args) throws Exception {
        // Creates admin account if it doesn't exist
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setRole("ADMIN");
            userRepository.save(admin);
        }
        
        // Creates user account if it doesn't exist
        if (!userRepository.existsByUsername("user")) {
            User user = new User();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("user"));
            user.setRole("USER");
            userRepository.save(user);
        }
    }
}
```

**Key Features:**
- ✅ Only creates accounts if they don't already exist (idempotent)
- ✅ Passwords are properly encrypted with BCrypt
- ✅ Logs creation status to console
- ✅ Works with any database (local or cloud)

### Frontend - Quick Login Buttons

The login page includes convenient quick login buttons:

```jsx
<button
  type="button"
  onClick={() => handleQuickLogin('admin', 'admin')}
  className="btn-quick-admin"
>
  <span className="quick-login-icon">👑</span>
  Login as Admin
</button>
```

**Features:**
- ✅ One-click login for testing
- ✅ Gradient button styling matching the app theme
- ✅ Icon indicators (crown for admin, user for regular)
- ✅ Disabled state during loading
- ✅ Smooth animations and ripple effects

## Security Considerations

### For Production Deployment

⚠️ **Important:** The default accounts with simple passwords are intended for **development and testing only**.

For production environments:

1. **Change Default Passwords:**
   ```sql
   -- After first login, change passwords via the application
   -- Or update directly in database with encrypted password
   UPDATE users SET password = '$2a$10$...' WHERE username = 'admin';
   ```

2. **Disable DataInitializer:**
   - Add a configuration property to control account creation
   - Or remove/comment out the DataInitializer in production builds

3. **Use Environment Variables:**
   ```java
   @Value("${app.create-default-accounts:false}")
   private boolean createDefaultAccounts;
   
   @Override
   public void run(String... args) throws Exception {
       if (createDefaultAccounts) {
           // Create default accounts
       }
   }
   ```

4. **Remove Quick Login Buttons:**
   - Conditionally show quick login only in development:
   ```jsx
   {process.env.NODE_ENV === 'development' && (
     <div className="quick-login-section">
       {/* Quick login buttons */}
     </div>
   )}
   ```

## Testing the Accounts

### Using Quick Login (Recommended)
1. Navigate to the login page
2. Click "Login as Admin" or "Login as User"
3. You'll be automatically logged in and redirected to dashboard

### Manual Login
1. Enter username: `admin` or `user`
2. Enter password: `admin` or `user`
3. Click "Login" button

## Database Verification

To verify the accounts were created in your database:

```sql
-- View all users
SELECT id, username, role FROM users;

-- Expected output:
-- id | username | role
-- ---|----------|------
--  1 | admin    | ADMIN
--  2 | user     | USER
```

## Logs

When the application starts, you'll see log messages confirming account creation:

```
INFO  c.s.b.config.DataInitializer : Default admin user created successfully with username: admin
INFO  c.s.b.config.DataInitializer : Default user created successfully with username: user
```

Or if they already exist:

```
INFO  c.s.b.config.DataInitializer : Default admin user already exists
INFO  c.s.b.config.DataInitializer : Default user already exists
```

## Troubleshooting

### Accounts Not Created
- **Check database connection:** Ensure PostgreSQL is running and accessible
- **Check logs:** Look for errors in application startup logs
- **Check database:** Verify the `users` table exists
- **Manually create:** Use the registration form to create accounts

### Quick Login Not Working
- **Verify backend is running:** Check that Spring Boot server is accessible
- **Check credentials:** Ensure the default accounts were created
- **Check browser console:** Look for JavaScript errors
- **Try manual login:** Use the form with same credentials

## Benefits

✅ **Quick Testing:** Immediately test both user and admin features  
✅ **Demo Ready:** Perfect for demonstrations and presentations  
✅ **Development Speed:** No need to register accounts repeatedly  
✅ **Role Testing:** Easily switch between user and admin roles  
✅ **User Experience:** Intuitive quick login options  

## UI Screenshots

### Login Page with Quick Login Buttons
The login page now features:
- Traditional login form at the top
- Quick Login section with two gradient buttons
- Admin button: Golden gradient with crown icon (👑)
- User button: Blue gradient with user icon (👤)
- Smooth hover effects and animations
- Clear visual distinction between roles

### After Quick Login
- **Admin Login:** Shows admin badge in header, all admin controls visible
- **User Login:** Standard dashboard view, admin controls hidden

---

**Feature Added:** December 14, 2024  
**Status:** ✅ Production Ready (with security recommendations)  
**Default Accounts:** admin/admin (ADMIN), user/user (USER)
