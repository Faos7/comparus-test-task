# UserApi

All URIs are relative to *http://localhost*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**getUsers**](UserApi.md#getUsers) | **GET** /users | Get info for all users in connected databases |


<a name="getUsers"></a>
# **getUsers**
> List getUsers(filter)

Get info for all users in connected databases

### Parameters

| Name        | Type                                  | Description                                    | Notes                        |
|-------------|---------------------------------------|------------------------------------------------|------------------------------|
| **filter**  | [UserSearchFilter](#usersearchfilter) | Aggregate object to hold possible query params | [optional] [default to null] |

### Return type

[List](#userdata)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json

<a name="usersearchfilter"></a>
## UserSearchFilter 
<details><summary>Properties</summary>

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
| **id** | **String** | User ID | [optional] [default to null] |
| **username** | **String** | Username | [optional] [default to null] |
| **name** | **String** | User&#39;s first name | [optional] [default to null] |
| **surname** | **String** | User&#39;s last name | [optional] [default to null] |
</details>

<a name="userdata"></a>
## UserData
<details><summary>Properties</summary>

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
| **id** | **String** | User ID | [optional] [default to null] |
| **username** | **String** | Username | [optional] [default to null] |
| **name** | **String** | User&#39;s first name | [optional] [default to null] |
| **surname** | **String** | User&#39;s last name | [optional] [default to null] |

</details>
