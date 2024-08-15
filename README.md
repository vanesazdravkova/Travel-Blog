# Welcome to Travel Blog!

**<span style="font-size: larger;">The project depends on the [Future Trips Rest API](https://github.com/vanesazdravkova/Future-Trips)</span>**

Travel Blog is your ultimate destination for sharing and discovering travel experiences. Our platform allows you to effortlessly upload and showcase your trips, from breathtaking landscapes to hidden gems and unforgettable adventures. Whether you're a seasoned traveler or a weekend explorer, you can create detailed posts, upload photos, and see the trips of like-minded wanderers.
<img src="src/main/resources/static/images/home-page.jpg" alt="Home Page"/>

<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#perequisites">Prerequisites</a>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
  </ol>
</details>

## Getting Started

Please make sure you follow the steps, step by step!

### Prerequisites

* JDK 17
* Apache Maven 4.0.0+
* MySQL

### Installation

You can run the Travel Blog project either with Docker or manually.

#### Run with Docker
In order to run the Travel Blog project with Docker:
1. Navigate to /docker directory where you can find images to start the project and the REST API
2. Execute one of the following commands:
   - docker-compose up
   - docker-compose -f docker-compose-amd.yaml up

#### Run manually
In order to run the Travel Blog project you need to:
1. Download the Travel Blog repo.
2. Connect to MySQL database.
3. Setup the following environment variables:
   - CLAUDINARY_API_KEY=147754227731364; 
   - CLAUDINARY_API_SECRET=xMsZw9MU8kEj6l2b52AZgdONO_A; 
   - EMAIL_PASSWORD=gomy snlo mrsx jcso
4. Start the project on localhost:8080
5. Download the Future Trips REST API.
6. Connect to MySQL database.
7. Setup the already mention variables.
8. Start the REST API on localhost:8081
