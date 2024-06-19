# hnbproductservice

# Product Management Service

## Overview

This is a simple REST web service built using Spring Boot that manages a list of products. It provides endpoints to create a product, view a specific product, and view a list of products. The service also converts product prices from EUR to USD using the HNB (Croatian National Bank) API.

## Technology Stack

- **Java 17+**
- **Maven**
- **Spring Boot**
- **Spring MVC**
- **PostgreSQL**
- **Thymeleaf** (for the web front-end)
- **HNB API** (for currency conversion - https://api.hnb.hr/)

## Features

- **Create Product**: Add a new product to the database.
- **View Product**: Retrieve details of a specific product.
- **List Products**: Retrieve a list of all products.
- **Validation**: Ensures product code is unique and exactly 10 characters long.
- **Currency Conversion**: Automatically converts the price from EUR to USD using the HNB API.
- **Web Interface**: Simple web interface to input and view products using Thymeleaf.

## Prerequisites

- Java 17 or higher
- Maven
- PostgreSQL
- Git

## Setup Instructions

1. Clone the Repository

```bash
git clone [<repository-url>](https://github.com/Domagoj-Ozimec/hnbproductservice.git)
```

2. Install PostgreSQL, create and set up a new database with Pg Admin, (productdb default db name)

3. Configure the database connection in `src/main/resources/application.properties`.

4. Build the project:
   ```bash
   mvn clean install
   ```

5. Run the application:
   ```bash
   mvn spring-boot:run
   ```

## API Endpoints

- **POST /api/products**: Create a new product
```
JSON Post Example:

{
    "code": "ABC1234567",
    "name": "Product Name",
    "priceEur": 100.0,
    "isAvailable": true
}
```

- **GET /api/products/{id}**: View a specific product
```
JSON Get Response:

{
    "id": 1,
    "code": "ABC1234567",
    "name": "Product Name",
    "priceEur": 100.0,
    "priceUsd": 120.0,
    "isAvailable": true
}
```

- **GET /api/products**: View a list of products
