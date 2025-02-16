**STOCK SCREENER**

**OVERVIEW**

The Stock Screener is a Spring Boot application that allows users to screen stocks based on financial parameters such as:
Debt-to-Equity Ratio

Return on Capital Employed (ROCE)

Return on Equity (ROE)

Market Capitalization

Price-to-Earnings (P/E) Ratio

The application integrates with the Financial Modeling Prep (FMP) API to fetch stock data and provides a RESTful API for screening stocks based on user-defined criteria.

**FEATURES**

Stock Screening:

Filter stocks by financial ratios and metrics.

Supports parameters like Debt-to-Equity, ROCE, ROE, Market Cap, and P/E Ratio.

Caching:

Uses Caffeine for in-memory caching to reduce API calls and improve performance.

RESTful API:

Exposes endpoints for screening stocks and fetching stock data.

Pagination:

Supports pagination for large datasets.

Authentication:

JWT-based authentication for secure access to endpoints.

**TECHNOLOGIES USED**

Spring Boot: Backend framework.

Spring Security: For authentication and authorization.

JWT: JSON Web Tokens for secure authentication.

Financial Modeling Prep API: For fetching stock data.

Caffeine: For in-memory caching.

Maven: Build tool.

H2 Database: In-memory database for development.

Setup Instructions
Prerequisites
Java 17 or higher.

Maven 3.x.

Financial Modeling Prep API key (sign up here).

**STEPS**

Clone the Repository:

git clone https://github.com/your-username/stock-screener.git
cd stock-screener
Configure API Key:

Add your FMP API key to application.properties:

properties

fmp.api.key=your_api_key
Build the Project:


mvn clean install
Run the Application:

Copy
mvn spring-boot:run
Access the Application:

The application will run on http://localhost:8080.

API Endpoints
Authentication
Sign Up:

POST /api/auth/signup
Request Body:

json
Copy
{
  "username": "user",
  "password": "password"
}
Sign In:

POST /api/auth/signin
Request Body:

json

{
  "username": "user",
  "password": "password"
}
Response:

json

{
  "token": "jwt_token"
}
Stock Screening
**Screen Stocks:**


GET /api/screen
Query Parameters:

maxDebtToEquity: Maximum Debt-to-Equity Ratio.

minROCE: Minimum Return on Capital Employed (ROCE).

minROE: Minimum Return on Equity (ROE).

minMarketCap: Minimum Market Capitalization.

maxPERatio: Maximum Price-to-Earnings (P/E) Ratio.

**Example:**


GET /api/screen?maxDebtToEquity=1&minROCE=15&minROE=10
Response:

[
  {
    "symbol": "AAPL",
    "name": "Apple Inc.",
    "price": 150.0,
    "marketCap": 2500000000000.0,
    "debtToEquity": 0.8,
    "roce": 20.5,
    "roe": 25.3,
    "peRatio": 28.0
  },
  {
    "symbol": "MSFT",
    "name": "Microsoft Corporation",
    "price": 300.0,
    "marketCap": 2200000000000.0,
    "debtToEquity": 0.5,
    "roce": 18.7,
    "roe": 22.1,
    "peRatio": 30.5
  }
]
Caching
The application uses Caffeine for in-memory caching.

Stock data is cached for 1 hour to reduce API calls.

To manually evict the cache:

POST /api/admin/evict-stocks-cache
Pagination
The /api/screen endpoint supports pagination.

Use the following query parameters:

page: Page number (default: 0).

size: Number of items per page (default: 50).

sortBy: Field to sort by (e.g., marketCap).

sortDir: Sort direction (asc or desc).

Example:

Copy
GET /api/screen?page=0&size=10&sortBy=marketCap&sortDir=desc
Error Handling
The application uses a global exception handler to manage errors.

Common errors include:

400 Bad Request: Invalid input parameters.

401 Unauthorized: Missing or invalid JWT token.

500 Internal Server Error: Unexpected server errors.

Future Enhancements
Additional Filters:

Add support for more financial ratios (e.g., dividend yield, EPS).

Scheduled Data Refresh:

Periodically refresh stock data using @Scheduled.

User Roles:

Implement role-based access control (e.g., admin, user).

Frontend Integration:

Build a React or Angular frontend for a user-friendly interface.

Contributing
Contributions are welcome! Please follow these steps:

Fork the repository.

Create a new branch (git checkout -b feature/your-feature).

Commit your changes (git commit -m 'Add your feature').

Push to the branch (git push origin feature/your-feature).

Open a pull request.

License
This project is licensed under the MIT License. See the LICENSE file for details.

Contact
For questions or feedback, please contact:

Pratik
Email: pratikmjain17@gmail.com
GitHub: pratikjain17

This README provides a clear overview of your project, setup instructions, API documentation, and future plans. You can customize it further based on your project's specific needs. Let me know if you need help with any section!
