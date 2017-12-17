# promotion-api

## Description
A web application to control campaigns

## Endpoints

| Path                   | Description             |
|------------------------|-------------------------|
| POST /campaigns        | Insert campaign         |
| PUT /campaigns/{id}    | Update campaign         |
| DELETE /campaigns/{id} | Delete campaign         |
| GET /campaigns/{id}    | Show campaign           |
| GET /campaigns         | List campaigns          |
| GET /team/{teamId}     | Get campaigns by teamId |

## How to run
You need to start our dependencies before start through docker-compose. Run the below command
```
docker-compose up -d
```
Let's run!
```
mvn spring-boot:run
```

## Examples
### Insert
```
curl -X POST \
  http://localhost:8080/campaigns \
  -H 'content-type: application/json' \
  -d '{
	"name": "Happy Campaign",
	"teamId": "chapeco",
	"start": "2017-01-01",
	"end": "2018-01-01"
}'
```

### Update
```
curl -X PUT \
  http://localhost:8080/campaigns/{id} \
  -H 'content-type: application/json' \
  -d '{
	"name": "Updated Happy Campaign",
	"teamId": "chapeco",
	"start": "2017-01-01",
	"end": "2010-01-01"
}'
```

### Show
```
curl -X GET http://localhost:8080/campaigns/{id}
```

### List
```
curl -X GET http://localhost:8080/campaigns
```

### Delete
```
curl -X DELETE http://localhost:8080/campaigns/1ef27161-9760-46f0-91c5-e3dcb3688e8e
```

### Find by teamId
```
curl -X GET http://localhost:8080/campaigns/team/chapeco
```

## RabbitMQ
Visit the page [http://localhost:15672/] to see the RabbitMQ admin running and type:
```
user: guest
pass: guest
```

### Update Notifications
Every update will be sent to RabbitMQ
### Exchanges
- UPDATE_CAMPAIGN_MESSAGE_EXCHANGE
### Queue
- UPDATE_CAMPAIGN_QUEUE

## Redis
The endpoint **/campaign/team/{teamId}** will be cached during 60 seconds
