# Gear Gurus

MaaS, an acronym for Mobility as a Service, is an innovative concept that aims to offer integrated transport solutions through a digital platform. In this model, users have access to different forms of transport, such as bicycles, scooters, car sharing, public transport, among others, through a single application or platform.

![Login](https://github.com/techGabrielBr/gear-gurus-kotlin/assets/89689001/ff21b688-874d-4234-8acb-82c644394422)

The our MaaS project, which be called “Gear Gurus”, aims to solve the low supply of urban micromobility rental services and the high occupancy of roads due to an excess of cars. To this end, an application will be created focusing on renting transport modes such as bicycles and scooters.

# Run

You have 3 different ways to run the application:

- Download the repository: 

        1°: Extract the generated .zip.

        2°: Now inside android studio click on file> new > import project and select the project folder.

        3°: It's important to ensure that build.gradle is in sync.

        4°: And finally, click on run to start running the app.

- Clone the repository:

        1°: Now, ensure that build.gradle is in sync.

        2°: Click on run to start the build process and running app.

- Download the apk: 

        Download the .apk present in this link: https://www.mediafire.com/file/62dbf2grvrincyl/appGearGurus.apk/file and install it on your android smartphone.

# Login

On the login screen, use the following data to access all content:

- Email: `email@email.com`
- Password: `Teste23`

# App Design

[![Figma](https://skillicons.dev/icons?i=figma&theme=dark)](https://skillicons.dev)

You can check all the UX work done in figma through the link below: https://www.figma.com/file/E3Xhivjj6Oi8CKnTSDbH1Q/Gear-Gurus?type=design&node-id=0-1&mode=design

# Technologies Used

[![Kotlin](https://skillicons.dev/icons?i=kotlin&theme=dark)](https://skillicons.dev)
Kotlin (Mobile app)

[![Java](https://skillicons.dev/icons?i=java&theme=dark)](https://skillicons.dev)
Java (Backend - Still in development)

# TomTom Map API 

![Home](https://github.com/techGabrielBr/gear-gurus-kotlin/assets/89689001/ea46901c-81d2-4577-ae89-a9a045a9c91a)

To use the maps and route planning feature, we chose to use the TomTom Map API.

Check the documentation here: https://developer.tomtom.com/

# Project Arquitecture

![image](https://github.com/techGabrielBr/gear-gurus-kotlin/assets/89689001/fab36a36-96d9-4d0d-ac61-4e250e696a85)

In order to avoid direct calls to each microservice, an intermediate level (gateway) will be created that will serve as the users' only gateway to the application.

At first, the gateway will be responsible for mediating the user authentication process (which will occur in the auth service) and for routing requests to the requested service.
Regarding microservices, they were designed using the “Decompose by business” and “Database per Service” patterns, that is, each service corresponds to a business resource and will have its own database, avoiding coupling.

In this application, some microservices need to communicate to avoid multiple calls from the client to the server. Therefore, the “API Composition” standard will be adopted where microservices communicate through REST calls to build a complete response body, in a single client request (in the diagram this flow occurs between the rental and station services, for example).

And finally, to avoid gigantic transactions that maintain locks in the database, and guarantee the atomicity, consistency and durability of the data, the saga development pattern and its “choreography” implementation form were adopted.

# App Security

To maintain application security, we follow guidelines from Palo Alto Networks (https://paloaltonetworks.com) and focus on three pillars:

- Authentication:
        We will use JWT tokens to securely authenticate our users to the server and allow them to enjoy the desired resources.
- Encryption: 
        Because we pass credit card information and passwords through the server, we will use secrets to protect all content.
- Monitoring: 
        After several analyses, we realized that the most committed malicious attacks are brute force and DDOS. 
        Therefore, we will be implementing a monitoring system to identify suspicious movements. 
