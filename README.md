# Movie Manager


A simple system to fetch popular movies from themoviedb.org.

## Installation

To install the movie manager in your machine, simply clone the repo, build the project and run the docker-compose as shown in the example below.

`cd project-root && ./gradlew bootJar && docker-compsoe up -d`

Once installed the system can be accessed using the url `http://localhost/index.html`

## Design Specifications 

### Polling infinite scroller

This is the main feature of this system. The TMDB api returns 20 results in a single API call, and we have to make subsequent API calls to fetch genre. To limit the
number of requests, the polling infinite scroller does something clever:

* It calls the TMDB movie api and fetches 20 results. 
* Then it makes subsequent calls and fetches genre for the first 5 results and shows them on screen.
* When user clicks on the load more button, it grabs next 5 results and makes subsequent calls to fetch genre and displays them on UI. 
* Once all 20 results are shown on the screen, it makes another call to grab 20 more results and repeats the process.

The number of records to show on page (5 currently) is configurable.

#### Note: IMDB movie api throttles requests, to handle this the system will retry 3 times, with specific delay between each request. Both the number of retries and delay are configurable.

### Security

This API utilises spring security package and has the following features. 

* JWT token bases authentication.
* 30 minute token expiry.
* Encrypted passwords.
* Supports two user roles, admin and user. Admins can login and see the sensitive configurations. 

### Config

* Configuration is stored as key/value pair to provide flexibility in storing different values. 
* Note: Right now the value can only be stored in string format, but we can create different columns (like integer, float, boolean...etc) in database and then store the value in 
  corresponding column to provide type safety. 

## Tech stack

* Spring boot 2.7.4
* H2 Db (File based)
* Docker-ce v20
* Gradle 7.5
* Java 11
* Angular 14
* Bootstrap 5
* SCSS
* Node >= 16
* Angular cli 14.2.4

## Future enhancements 

* Write test cases.
* Allow users to register as admins.
* Allow users to edit configuration. 
* Handle more error codes / responses returned from the TMDB API, so we can show meaningful errors to users. 
* Provide search / filter capabilities.
* Right now when users click on the image, it opens a bigger version of the image in a popup, add some highlighting effects on image on hover so users know, its clickable.




