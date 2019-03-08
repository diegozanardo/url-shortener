# Url Shortener

Most of us are familiar with seeing URLs like bit.ly or t.co on our Twitter or Facebook feeds. These are examples of shortened URLs, which are a short alias or pointer to a longer page link. For example, I can send you the shortened URL http://bit.ly/SaaYw5 that will forward you to a very long Google URL with search results on how to iron a shirt.

## Architecture

The application uses Spring Boot 2.0 and POSTGRES.
Both are configured in the docker-compose.

### Generating the Url Shortener 

The application insert a new record in the database and the id that the database generated is used for encode a url key.
The application uses this key for decode on the id and get the full url for redirect the request.

### Endpoints

To get the documentation about the endpoints, you just need to access the swagger.
The link: http://localhost:8080/swagger-ui.html

## Getting Started

### Prerequisites

To run the application you need:

* JDK8
* Maven
* Docker
* Docker Compose

### Installing

To run the application you just need execute the `run-application.sh`.

### Samples curls

#### Generate new Url Shortener

Request:
```
curl -X POST "http://localhost:8080/shortener" -H "accept: application/json" -H "Content-Type: application/json" -d "{ \"url\": \"https://facebook.com\"}"
```

Response:
```
{
    "url":"http://localhost:8080/shortener/b"
}
```

#### Statistics from an Url Shortener

Request:
```
curl -X GET "http://localhost:8080/statistics/b" -H "accept: */*"
```

Response:
```
{
  "originalUrl": "http://facebook.com",
  "createdAt": "2019-03-08T00:01:29.944",
  "lastHitAt": "2019-03-08T00:02:45.506",
  "hits": 2
}```