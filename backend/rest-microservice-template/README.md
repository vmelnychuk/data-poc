## rest-microservice-template -- Template for REST Microcervice

### Quick start

```shell
./gradlew clean bootRunDev
curl -X GET http://localhost:8389/ping
```

### Usage
#### Request
```shellcurl
-X GET
-H 'Accept: application/json'
-H 'Accept-Charset: utf-8'
'http://localhost:8389/applications/35c5a95e229e42cea5a605472cb86e84'
```
#### Response
- if application is present
```json
{
    "industry": "GAS_HEATING_AIR_CON"
}
```
- if field industry if empty
```json
{
    "industry": "null"
}
```
- if application is not found: response code `404`
