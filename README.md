# Small Core Banking Solution
This project is a small core banking solution designed to manage and process fundamental banking operations, such as account creation and transactions.

## Deployment Instructions and Requirements for the Application
Docker Desktop and Git must be installed and run.
  - [Docker for Windows, Apple Chip and Intel Chip](https://www.docker.com/products/docker-desktop/)
  - [Git for Windows](https://gitforwindows.org/)
  - [Git for Mac](https://sourceforge.net/projects/git-osx-installer/)
  
  or
  by using Homebrew:
  
  ````
  brew install git
  ````
  
**Step 1:** Clone the repo to your local device
````
git clone https://github.com/markpetrovv/SmallCoreBankingSolution.git
````

**Step 2:** Acces the folder where you cloned the project with Terminal/Command Prompt Terminal
````
cd pathToTheFolderWhereTheClonedProjectIsLocated
````

**Step 3:** Use the following command to create a local image and start the build
````
docker build -t localimage .
````
**Step 4:** After the successful build, start the docker containers, it will take a few minutes
````
docker-compose up -d
````
**Step 5:** Make sure all containers (3) are running
In case if they are not, run this command, it will stop and delete containers:
````
docker-compose down
````
and then build them again:
````
docker-compose up -d
````

## Application Test Scenarios
**Test 1:** Create an Account (allowed currencies are USD, EUR, GBP and SEK)
````
curl -X POST -H "Content-Type: application/json" -d "{\"customerId\": 1, \"country\": \"EST\", 
\"currencies\": [\"EUR\", \"USD\"]}" http://localhost:8080/accounts
````
**Test 2:** Get an Information About Account

After creating an account, you will get the "accountId". Use this number insted of (accountId) in the following command
````
curl -X GET http://localhost:8080/accounts/(accountId)
````
**Test 3:** Make a Transaction

IN transaction:
````
curl -X POST -H "Content-Type: application/json" -d "{\"accountId\": (accountId), \"amount\": 
10000, \"currency\": \"EUR\", \"direction\": \"IN\", \"description\": \"Deposit\"}" 
http://localhost:8080/transactions
````
OUT transaction:
````
curl -X POST -H "Content-Type: application/json" -d "{\"accountId\": (accountId), \"amount\": 
5000, \"currency\": \"EUR\", \"direction\": \"OUT\", \"description\": \"Withdrawal\"}" 
http://localhost:8080/transactions
````
**Test 4:** Get Information About Transactions
````
curl -X GET http://localhost:8080/transactions/(accountId)
````

## Endpoints
| Service  | Endpoint | Method  | Description |
| ------------- | ------------- | ------------- | ------------- |
| AccountService  | /accounts  | POST | Creating an account |
| AccountService  | /accounts/(accountId)  | GET | Retrieving information about the account |
| TransactionService  | /transactions  | POST | Making a transactin (IN and OUT) |
| TransactionService  | /transactions/(accountId)  | GET | Getting all the transactions for this account |

## Important Choices
  - The project is structured as a standard Java project using Gradle as the build tool.
  - The project uses Spring Boot as the application framework.
  - PostgreSQL is used as the database for this project, which is a powerful, open-source relational database system.
  - The project uses MyBatis, an SQL mapping framework, for mapping SQL statements and results to Java objects.
  - The project utilizes Docker and docker-compose for building, packaging, and running the application and its dependencies (PostgreSQL and RabbitMQ) as containers.
  - Java version: 17
  - The project uses RabbitMQ for messaging, which is a widely used, open-source message broker.
  - The project uses JUnit and Mockito for unit testing and mocking, which are popular and widely used testing libraries in the Java

## Horizontal Scaling
Before implementing the horizontal scaling you have to make sure that the application is stateless, so 
the instance will not rely on local data dependencies, while processing the request. Also, message 
queuing system has to be used in order to manage frequently accessed data and manage other 
concurrent background tasks more effectively.

## Transactions Per Second (TPS)
For this purpose I used Gatling benchmark tool. I tested my application using different amount of users: 
10, 100, 300. Here is the screenshot of 300 concurrent users using the application. We can see that the 
average TPS for my application is 260 transaction per second.

![alt text](https://github.com/markpetrovv/SmallCoreBankingSolution/blob/main/screenshots/TPS.png?raw=true)
