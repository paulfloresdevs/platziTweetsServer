# API Documentation - TweetsApp


## User Endpoints



## **1. User Registration Endpoint: POST /users/register**


**Request Body:**

*{  
"email": "user@example.com",  
"fullName": "John Doe",  
"password": "securepassword"  
}*

**Response:**

**Success (HTTP Status: 200)  
{  
"email": "user@example.com",  
"names": "John Doe",  
"nickname": "usJo"  
}*

> Error (HTTP Status: 400 or 500 depending on the failure)

## **2. User Authentication (Login) Endpoint: POST /users/auth**



**Request Body:**



*{  
"email": "user@example.com",  
"password": "securepassword"  
}*

**Response:**

*Success (HTTP Status: 200)  
{  
"email": "user@example.com",  
"names": "John Doe",  
"nickname": "usJo"  
}*

> Error (HTTP Status: 400 or 500 depending on the failure)

## 3. Get User by ID Endpoint: GET /users/user


**Request Parameters:**


> id: User's ID (Long)   Example:      /users/user?id=1


**Response:**


*Success (HTTP Status: 200)  
{  
"id": 1,  
"email": "user@example.com",  
"fullName": "John Doe",  
"tweets": [  
{  
"id": "1",  
"author": {  
"email": "user@example.com",  
"names": "John Doe",  
"nickname": "usJo"  
},  
"text": "This is a tweet",  
"imageUrl": "http://image.url",  
"videoUrl": "http://video.url",  
"location": {  
"altitude": 20.0,  
"longitude": 50.0  
},  
"createdAt": "2025-01-01 10:00:00"  
}  
]  
}*

> Error (HTTP Status: 404 if the user is not found)

## 4. Get All Users

*Endpoint: GET /users/all*

**Response:**

*Success (HTTP Status: 200)  
[  
{  
"email": "user1@example.com",  
"names": "User One",  
"nickname": "usOn"  
},  
{  
"email": "user2@example.com",  
"names": "User Two",  
"nickname": "usTw"  
}  
]*

## Tweet Endpoints



## 1. Post a Tweet Endpoint: POST /tweets/post


**Request Body:**

*{  
"text": "This is a tweet",  
"imageUrl": "http://image.url",  
"videoUrl": "http://video.url",  
"latitude": 20.0,  
"longitude": 50.0,  
"userId": 1  
}*  
**Response:**  
Success (HTTP Status: 200)

*{  
"id": "1",  
"author": {  
"email": "user@example.com",  
"names": "John Doe",  
"nickname": "usJo"  
},  
"text": "This is a tweet",  
"imageUrl": "http://image.url",  
"videoUrl": "http://video.url",  
"location": {  
"altitude": 20.0,  
"longitude": 50.0  
},  
"createdAt": "2025-01-01 10:00:00"  
}*  
**Error (HTTP Status: 400 or 500 depending on the failure)**

## 2. Get All Tweets Endpoint: GET /tweets

**Response:**  
Success (HTTP Status: 200)

*[  
{  
"id": "1",  
"author": {  
"email": "user@example.com",  
"names": "John Doe",  
"nickname": "usJo"  
},  
"text": "This is a tweet",  
"imageUrl": "http://image.url",  
"videoUrl": "http://video.url",  
"location": {  
"altitude": 20.0,  
"longitude": 50.0  
},  
"createdAt": "2025-01-01 10:00:00"  
}  
]*

## 3. Delete a Tweet

**Endpoint:** *DELETE /tweets/delete/{idDelete}*

**Request Parameters:**


> idDelete: Tweet's ID (Long)  
>  Example: /tweets/delete/1

**Response:**  
Success (HTTP Status: 200)

*{  
"deleted": true,  
"message": "Tweet deleted successfully"  
}*

**Error (HTTP Status: 404 or 500 depending on the failure)**

## Models




## User Model


id (Long)  
email (String)  
fullName (String)  
password (String)  
tweets (List of Tweets)

## Tweet Model

id (Long)  
text (String)  
imageUrl (String)  
videoUrl (String)  
location (Location)  
createdAt (String, date format: "yyyy-MM-dd HH:mm:ss")  
user (User)  
Location Model  
altitude (Double)  
longitude (Double)