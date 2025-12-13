# Screenshots Directory

This directory will contain screenshots of the Sweet Shop Management System in action.

## Screenshots to Capture

Once the application is running with a real database, capture the following:

1. **login.png** - Login page interface
2. **register.png** - Registration page
3. **dashboard-user.png** - User dashboard view with sweets
4. **dashboard-admin.png** - Admin dashboard with extra controls
5. **search.png** - Search and filter functionality
6. **add-sweet.png** - Admin modal for adding new sweet
7. **out-of-stock.png** - Sweet card showing out of stock status
8. **purchase-success.png** - Successful purchase flow

## How to Capture Screenshots

1. Start PostgreSQL (via Docker or local installation)
2. Run the backend: `cd backend && mvn spring-boot:run`
3. Run the frontend: `cd frontend && npm run dev`
4. Open browser at `http://localhost:5173`
5. Create test data:
   - Register users (both regular and admin)
   - Add sweets via admin interface
   - Test various scenarios
6. Capture screenshots using browser tools or OS screenshot utility
7. Save images in this directory with appropriate names
8. Update main README.md with screenshot references

## Screenshot Guidelines

- Use consistent browser window size (1920x1080 recommended)
- Ensure good test data (realistic product names, prices)
- Show different states (empty, populated, errors)
- Highlight key features in each screenshot
- Use high-quality PNG format
- Keep file sizes reasonable (< 500KB each)

## Current Status

📸 **Screenshots pending** - These will be captured during manual testing phase with a running database instance.
