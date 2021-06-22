# ICO_VRP

Projeto desenvolvido no âmbito da Unidade Curricular Inteligência Computacional e Otimização

----------------------------------------------------------------------------------------------------------------

Docentes: Vítor Manuel Basto Fernandes e Maria do Rosário Domingos Laureano

Alunos: 

* Diogo Santos, nº 96332 
* Filipe Loureiro, nº 82485 
* João Miguel Faria, nº 78980 
* Tiago Farinha, nº 82358

ISCTE - Instituto Universitário de Lisboa

---------------------------------------------------------------------------------------

Projeto composto por duas componentes:

* Backend - Java, Spring, Jnetics
* Frontend - HTML, CSS, Javascript, Bootstrap
* Comunicacão Frontend/Backend atraves de uma REST API

--------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------

# Getting started
Clone this repository to your local machine and open cmd in root directory.

Run `docker-compose build` followed by `docker-compose up` and open http://localhost:80 

You can run each container independently:

/frontend
```
cd frontend
docker build -t moozdzn/frontend .
docker run -p 80:80 moozdzn/frontend
```

/backend
```
cd backend
docker build -t moozdzn/backend .
docker run -p 8080:8080 moozdzn/backend
```
# Server API Endpoints
<table>
    <tr>
        <th>Method</th>
        <th>URL</th>
        <th>Request</th>
        <th>Response</th>
    </tr>
    <tr>
        <th>POST</th>
        <th>/request</th>
        <th>{"clients": [{"{"name": "Cliente 1","latitude": 38.74,"longitude": -9.14,"quantity": 10,"price": 10,"timeWindow":["10:00", "20:00"]}"}],"veiculos": [{"custoHora": 10,"custoDist": 10,"vehicleId": 1,"armazemPartida": {"latitude": 38.72,"longitude": -9.13}}]}</th>
        <th>{"results": [{"vehicleId": 0,"clients": [{"latitude": 38.72,"longitude": -9.13,"timeWindow": [0, 0]},]}]}</th>
    </tr>
</table>

# Project Architecture

![Project Architecture](https://raw.githubusercontent.com/jmjmfaria/ICO_VRP/master/Architecture.png)

# Development

## Client
Files can be found inside `/frontend`

## Server
Server developed with [Spring](https://spring.io/) and Java Version 11

Run `mvn clean install` to install all maven dependencies

Run `mvn spring-boot:run` and open http://localhost:8080 .
