# Bitcoin Tracker Application

The application is a bitcoin tracker which can show current and historical prices.
The data is provided via the free coindesk API.

It makes use of the following technologies:

* SpringBoot
* React-JS 
  * axois
  * boostrap/reactstrap
  * chart.js/react-chartjs-2
  * redux/redux-thunk
  * jest/enzyme (test)

The application provides the user with the following charts:

* Current Bitcoin Price which gets updated every minute
* Historical Bitcoin Price which retrieves the last 30 days of prices.

## Building and running application (server and front-end)
This will package up the entire application into an executable jar.
```
mvn clean install
java -jar target/bitcoin-tracker*.jar
```
Then navigate to: http://localhost:8080

## Running server standalone
```
mvn spring-boot:run
```
REST services available
http://localhost:8080/prices/latest
http://localhost:8080/prices/hisorical

## Running front-end standalone
```
cd src/main/js/frontend
npm install
npm start
```
Then navigate to: http://localhost:3000

## Pull and run from docker

Pull image from docker hub:
```
docker pull wesleyarchbell/bitcoin-tracker:0.1
```

Run docker image:
```
docker run -p 8080:8080 wesleyarchbell/bitcoin-tracker:0.1
```
Then navigate to: http://localhost:8080
