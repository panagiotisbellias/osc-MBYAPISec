# My BackYard API

## ** Under Development **

**Languages**
* Java (21)
* Maven (3.9.5)
* Spring Boot (3.2.0)
    * starter web
    * starter data jpa
    * starter security
    * starter oauth2 resource server
    * starter configuration processor
    * starter test

**Current build workflow**
1. Docker
2. Google Cloud Build
3. Google Artifact Registry
4. Google Cloud Run

## Description
My BackYard is a full stack application designed to act as a handy reference for
homeowners, gardeners or even landscapers as they tend to their yards or gardens. Inspired by
my wife, who loves spending time tending to all of our various plants, there came a point where
she required some sort of note system to help her remember all the information about our
home's ecosystem.

I have designed My BackYard as two components divided into a backend and
frontend that utilizes a REST API for communication between the two components.
At this stage in the application development functionality is limited and represents only a
small portion of the eventual functionality. However, I have placed much of my focus on a backend architecture that
will allow for growth both in scope and demand.

## The Backend
This iteration of My Backyard has a full implementation of Spring security using JWT for authentication replacing 
the API key authentication in the previous version. It currently uses a custom authentication provider and security 
config to issue the JWT for API access. The JWT authentication system utilizes a self-signed public/private key 
implementation. A basic implementation of authorization roles is also present to separate admin access from user access 
as well as method level security for returning user data.

## The Frontend
Please find the frontend component here:  
https://github.com/marcuslull/MBYAngular