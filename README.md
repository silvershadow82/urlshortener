# URL Shortener
URL Shortener Service is Spring Boot service that is used to shorten URLs, similar to (bitly.com)

Currently, service implements two endpoints:

## Create a short URL
```
POST /create 
Content-Type: application/json
{
  "url": "http://some.host.com/someUrl"
}

Response:

{
  "originalURL":"http://some.host.com/someUrl",
  "shortURL":"http://localhost:8080/go/gdMey"
}

```

## Follow short URL to original URL:
```
GET /go/{url}

Response:

HTTP/1.1 302
Location: http://some.host.com/someUrl
```

To Test, run
```
$> ./gradlew build && java -jar build/libs/url-shortener-0.1.0.jar
$>  curl -X POST -d "{ \"url\":\"http://some.host.com/someUrl\"}" -H "Content-Type: application/json"  http://localhost:8080/create
$>  curl -vk http://localhost:/go/gdMey

```
