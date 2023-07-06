# Bookings Application
Bookings is an application that you can make some operations related to bookings and the booked properties.

The operations you can do with Bookings application are as follows;

## Booking Operations
* Create Booking
* Update an active Booking
* Cancel an active Booking
* List all Bookings regardless of their status
* Read a Booking regardless of its status 

## Block Operations
* Create a Block on a Property
* Update an active Block on a Property
* Cancel an active Block on a Property
* List all Blocks regardless of their status
* Read a Block regardless of its status

The use cases that belong to the operations above are thoroughly considered in Wiki pages. 

The URL to the Wiki page is below.

https://github.com/gokhanustuner/bookings/wiki

## Installation Instructions

Please clone the repository. All permissions for reading the repository have been enabled.

```git
git clone git@github.com:gokhanustuner/bookings.git
```

Go to the project's root directory. Run the project.

```ssh
./mvnw spring-boot:run
```

With integration tests, all major use cases and application behavior have been tested. 

Run the command below from the project's root directory.

```ssh
./mvnw test
```