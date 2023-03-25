# Delivery fee calculator

Spring Boot Maven Project

Calculate the delivery fee based on regional base fee, vehicle type, and weather conditions.

## Maven packages used
* Spring Web
* Spring Data JPA
* Spring Boot Starter Test
* ModelMapper
* H2 Database
* Lombok
* JDOM

## API schema

|Method|Route|Description
|-|-|-|
| GET	| /api/fee/\<city\>\<vehicle\>       	| Get delivery fee based on latest weather data
| GET	| /api/fee/\<city\>\<vehicle\>\<time\>	| Get delivery fee based on weather data for the time specified