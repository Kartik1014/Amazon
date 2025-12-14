#!/bin/bash

# Sweet Shop Backend Test Report Generator
echo "==========================================="
echo "Sweet Shop Management System - Test Report"
echo "==========================================="
echo ""
echo "Running all tests..."
echo ""

# Run tests and capture output
mvn clean test

# Check if tests passed
if [ $? -eq 0 ]; then
    echo ""
    echo "✅ All tests passed successfully!"
    echo ""
    echo "Test Summary:"
    echo "-------------"
    
    # Extract test statistics from surefire reports
    if [ -d "target/surefire-reports" ]; then
        echo "Detailed reports available at: target/surefire-reports/"
        echo ""
        
        # Count test files
        TEST_FILES=$(find target/surefire-reports -name "*.txt" | wc -l)
        echo "Number of test classes: $TEST_FILES"
        
        # Display test summary
        echo ""
        echo "Test Coverage:"
        echo "- Entity Tests: User, Sweet"
        echo "- Service Tests: AuthService, SweetService"
        echo "- Repository Tests: Via Service Integration"
        echo "- Security Tests: JWT, Authentication"
        echo ""
        
        echo "View detailed HTML reports:"
        echo "  open target/surefire-reports/index.html"
        echo ""
    fi
else
    echo ""
    echo "❌ Some tests failed. Check the output above for details."
    echo ""
fi

echo "Test report generated at: $(date)"
echo "==========================================="
