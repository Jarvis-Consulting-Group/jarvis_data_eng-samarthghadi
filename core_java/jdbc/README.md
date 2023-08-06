# Introduction
I have created a JDBC application that interacts with a local PostgreSQL database. To ensure a clean separation between data access logic and business logic, the application adheres to the Data Access Object (DAO) and Data Transfer Object (DTO) design patterns. As a result, CRUD operations (Create, Read, Update, and Delete) can be performed efficiently on the RDBMS database.

The key features and technologies utilized in this application are as follows:
- Postgres SQL
- PSQL Client Tool
- Maven
- Docker to create psql container
- Dbeaver
# Implementaiton
## ER Diagram
![ER-Diagram.png](jdbc%2Fassets%2FER-Diagram.png)

## Design Patterns
The DAO Design Pattern comprises three main components, with an optional HTTP helper class. The driver component manages the database connection. The DAO class handles CRUD operations, abstracting SQL queries. The DTO object acts as a container for data exchange between the application and the database. An optional HTTP helper class facilitates communication with external HTTP-based services.

On the other hand, the Repository Design Pattern involves two layers: the data layer (repository) and the business layer (Service). The repository manages data collection and storage, abstracting the data source. The business layer handles business logic and orchestrates data layer operations. The Repository Pattern is straightforward, promoting separation of concerns. However, it may struggle with complex data access requirements.

In summary, the DAO Pattern simplifies database interactions, while the Repository Pattern streamlines data and business logic separation, ensuring better code organization.

# Test
The DBeaver workbench was used to set up the scripts and worked as an interface to create sql statements to see if the CRUD operations that were sent through the JDBC app was matched with the results from the database.