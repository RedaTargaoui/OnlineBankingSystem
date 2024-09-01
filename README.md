# Online Bank System

This project is an online banking system web application built with Spring Boot,
HTML/CSS, JavaScript, and MySQL. The application allows users to perform various 
banking operations such as logging in, depositing money, withdrawing money, 
transferring money, viewing transaction history, and managing their profiles.

## Features

- **User Authentication:**
    - login system for users.

- **Deposit Money:**
    - Users can deposit money into their accounts.

- **Withdraw Money:**
    - Users can withdraw money from their accounts.

- **Transfer Money:**
    - Users can transfer money between accounts.

- **Transaction History:**
    - Users can view a detailed history of all transactions.

- **Profile Management:**
    - Users can view and edit their profile information.

## Technologies Used

- **Backend:**
    - Java
    - Spring Framework (Spring Boot, Spring MVC, Spring Data JPA, Thymeleaf)

- **Frontend:**
    - HTML
    - CSS
    - JavaScript

- **Database:**
    - MySQL

## Prerequisites

- Java Development Kit (JDK) 8 or higher
- Maven
- MySQL
- IDE (IntelliJ, Eclipse, etc.)
- Web browser (Chrome, Firefox, etc.)

## Database Schema
Here is a brief overview of the database schema:

- **Users**: Contains user details such as ID, username, password etc.
- **Accounts**: Contains account details like account number, balance, user ID (foreign key).
- **Transactions**: Stores transaction details like ID, account number, amount, type (deposit, withdrawal, transfer), timestamp, etc.

## Set up the MySQL Database

- **Create a new database in MySQL**
    - CREATE DATABASE bank;

- **Update the application.properties file with your MySQL credentials**
    - spring.datasource.url=jdbc:mysql://localhost:3306/bank
    - spring.datasource.username=your-username
    - spring.datasource.password=your-password


