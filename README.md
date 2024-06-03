# ComparusTestTask

## Documentation
Service for aggregating users data from multiple databases. Provides a single rest endpoint for selecting user data, aggregated from all data sources defined in application.yaml file. Only RDBS as data sources are supported.


To simplify manual testing docker-compose-pg.yml is provided.
Docker instalation is required to use it.
Please run docker compose on docker-compose-pg.yml to create containers with initialized databases

```
docker compose -f /testTask/docker-compose-pg.yml -p testtask up -d

```
This will create 3 docker containers: 2 postresql and 1 mysql.

______

<a name="documentation-for-api-endpoints"></a>
## Documentation for API Endpoints

All URIs are relative to *http://localhost*

| Class     | Method                              | HTTP request      | Description                                   |
|-----------|-------------------------------------|-------------------|-----------------------------------------------|
| *UserApi* | [**getUsers**](UserApi.md#getusers) | **GET** /users    | Get info for all users in connected databases |
|           |                                     | **GET** /api-docs | Get spring doc, which describes all endpoints |

<a name="documentation-for-authorization"></a>
## Documentation for Authorization

All endpoints do not require authorization.
