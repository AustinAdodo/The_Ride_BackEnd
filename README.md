# TheRideBackEnd

This project was generated completely written with Java (Spring MVC).

## Development server

Run the project in development mode on JetBrains or you Preferred IDE and Navigate to `http://localhost:8080/`. The application will automatically reload if you change any of the source files.
It is advisable to have the bck-end running first before the fron end.
RabbitMQ uses `port 5672` on localhost and wbsocket endpoint is `http://localhost:8080/ws` .I strongly advise you leave this port open and have TCP enabled on your local machine or development server.

## RabbitMQ Integration

Ensure Erlang and rRabbitMQ are both installed on yor development machine and the installation is done with the administrator account to avoid complexities, by convention Erlang is installed before RabbitMQ.
