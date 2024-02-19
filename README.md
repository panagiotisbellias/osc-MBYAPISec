# My BackYard API
## ** Under Development **
The backend API for the My BackYard application.
* Java (21)
* Maven (3.9.5)
* Spring Boot (3.2.0)
    * starter data rest
    * starter data jpa
    * starter security
    * starter oauth2 resource server
    * starter configuration processor
    * starter test

## Description
My BackYard is a full stack application designed to act as a handy reference for
homeowners, gardeners or even landscapers as they tend to their yards or gardens. Inspired by
my wife, who loves spending time tending to all of our various plants, there came a point where
she required some sort of note system to help her remember all the facts and notes about our
home's ecosystem.

I have designed My BackYard as a full stack application divided into a backend and
frontend components that utilizes a REST API for communication between the two components.
At this stage in the application development functionality is limited and represents only a
minimum viable product however I have placed much of my focus on a backend architecture that
will allow for growth both in scope and demand.

## The Backend
This iteration of My Backyard will have a full implementation of Spring security using JWT for authentication replacing 
the API key authentication in the previous version.  
It currently uses a custom authentication provider and security config to issue JWT for API access. The JWTs utilize a self signed public/private key implementation for authenticity.
A basic implmentation of authorization roles is also resent to seperate admin access from user access.

This project is currently under development!
