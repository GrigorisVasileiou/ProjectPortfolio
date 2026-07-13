# ProjectPortfolio

ProjectPortfolio is a web application developed for the Software Engineering course.

The application helps developers organize software projects by managing use cases and CRC cards while supporting the generation of UML diagrams.

## Live Demo

🌐 **Live Website:** https://projectportfolio.up.railway.app

## Features

### User Management
- User registration
- User login/logout
- Profile management
- Password update

### Project Management
- Create projects
- View project list
- Delete projects

### Use Cases
- Create use cases
- Edit use cases
- Delete use cases
- View use case list

### CRC Cards
- Create CRC cards
- Edit CRC cards
- Delete CRC cards
- Link CRC cards with use cases

### UML Generation
- Generate PlantUML scripts
- Generate Nomnoml scripts
- Generate Use Case diagrams
- Generate Class diagrams

## Technologies

- Java 17
- Spring Boot 3
- Spring Security
- Spring Data JPA
- Thymeleaf
- MySQL
- Gradle
- Railway

## Installation

Clone the repository

```bash
git clone https://github.com/GrigorisVasileiou/ProjectPortfolio.git
```

Configure the database using environment variables:

```
DB_URL
DB_USERNAME
DB_PASSWORD
```

Run the application

```bash
./gradlew bootRun
```

or

```bash
gradlew.bat bootRun
```

The application will be available at

```
http://localhost:8080
```

## Documentation

The complete project report is available in the Report.pdf.

The project report contains:
- Use Case descriptions
- CRC Cards
- UML diagrams
- Project timeline
- Sprint progress and project management details

## Author

Grigoris Rafail Vasileiou
University of Ioannina
Software Engineering Course
