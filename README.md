# TheRideBackEnd

This project was generated completely written with Java (Spring MVC).

## Development server

Run the project in development mode on JetBrains or you Preferred IDE and Navigate to `http://localhost:8080/`. The application will automatically reload if you change any of the source files.
It is advisable to have the back-end running first before the front end.
RabbitMQ uses `port 5672` on localhost and websocket endpoint is `http://localhost:8080/ws` .I strongly advise you leave this port open and have TCP enabled on your local machine or development server.

## RabbitMQ Integration

Ensure Erlang and rRabbitMQ are both installed on yor development machine and the installation is done with the administrator account to avoid complexities, by convention Erlang is installed before RabbitMQ.

## Testing
It is important to note that H2 databases do not support UUID primary key persistence, MSSQL was utilised for testing for this reason.
   - ### Unit Tests
     Unit Tests were configured using the inbuilt `@SpringBootTest` also ensure you use `@TestInstance(TestInstance.Lifecycle.PER_CLASS)`
     to handle test class instances more efficiently. Mocking was utilised for full isolation of the repository class.
     The test below simply checks if the `IsOnlyMySexAllowed` in the `Customer` class is correctly populated.
     ![The Ride Unit Test](https://drive.google.com/uc?export=view&id=1zD2tfFyFns76Vxaihx_iKkrEK8XSzE_W)