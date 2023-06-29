# Makalu HRM

# Deployment

### Environment Variables
- SPRING_PROFILES_ACTIVE
~~~~
stage for QC
prod for production
~~~~
- SPRING_DATASOURCE_PASSWORD
- SPRING_DATASOURCE_URL
- SPRING_DATASOURCE_USERNAME

### War
~~~~
1. command to generate the war: gradle clean build
2. war location: /core/build/libs/core-0.0.1.war
~~~~
### Requirements
- jdk(java) 11

# Auto Deploy
~~~~
We have confugured for the auto deployement in dev/qc server. In this we will auto generates the docker image and push that to our docuker image hub
~~~~
#### Command
~~~~
./gradlew release
~~~~
#### Environment Variables
~~~~
DOCKER_PUBLISH=true;DOCKER_REGISTRY_TOKEN=c21hdGRldnM6JG1hdERldnMyMDIw;DOCKER_REGISTRY=smatio
~~~~
- DOCKER_PUBLISH
~~~~
true if we need to generate the image
~~~~
- DOCKER_REGISTRY_TOKEN
~~~~
DOCKER REGISTRY username and passowrd which needs to be Base64 encoded. for eg  Base64.encoder.encodeToString("username:password".bytes)
~~~~
- DOCKER_REGISTRY
~~~~
docker registry name
~~~~

# Run docker image in locally

1. Use this command if you have mysql db running inside docker or have accessible mysql database
~~~~
   docker run  -e SPRING_PROFILES_ACTIVE=prod -e SPRING_DATASOURCE_PASSWORD="R00t@Dhiraj" -e SPRING_DATASOURCE_URL="jdbc:mysql://localhost:3306/smat_crypto"  -e SPRING_DATASOURCE_USERNAME=root -p 8080:8080 -t dhirajbadu/makalu/makalu_hrm:latest
~~~~


2. Use this command if you do not have database access. It will use h2 memory database by default
~~~~
docker run  -e SPRING_PROFILES_ACTIVE=prod -e AUTH0_ISSUER_URI="https://dev-yztvar0b.us.auth0.com/" -e AUTH0_AUDIENCE="localhost:8080" -p 8080:8080 -t dhirajbadu/makalu/makalu_hrm:latest
~~~~
"# hrm" 
