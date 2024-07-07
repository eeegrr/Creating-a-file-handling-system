# Tourism Agency Information System

## Description
This console application processes information about trips for a tourism agency. Trip details include the trip identifier, city, date, number of days, price, and type of vehicle. The information is stored in a text file `db.csv` located in the current directory. The application allows viewing, adding, deleting, editing, sorting, finding trips, and calculating the average price of trips.

## Author Information
- **Student ID**: [Insert Student ID]
- **Name**: [Insert Name]
- **Group**: [Insert Group]

## Data Format
- Each trip is stored in a separate line in `db.csv` with fields separated by semicolons.
- The fields are in the order: identifier, city, date, number of days, price, vehicle type.
- Identifier: A three-digit integer, unique for each trip.
- City: A string with words capitalized.
- Date: Format DD/MM/YYYY.
- Number of days: An integer.
- Price: A real number with two decimal places.
- Vehicle type: One of "PLANE", "BUS", "TRAIN", "BOAT".

## Commands
The application processes the following commands:

### `add`
Adds a new trip to the file.
#### Format
add id;city;date;days;price;vehicle
- `id`: Three-digit integer.
- `city`: String, can contain spaces.
- `date`: Format DD/MM/YYYY.
- `days`: Integer.
- `price`: Real number.
- `vehicle`: One of "PLANE", "BUS", "BOAT", "TRAIN".

#### Actions
- Validate all fields.
  - If fields are missing, display `wrong field count`.
  - If the identifier is not unique or invalid, display `wrong id`.
  - Format the city name.
  - Validate the date. If invalid, display `wrong date`.
  - Validate the number of days. If invalid, display `wrong day count`.
  - Validate the price. If invalid, display `wrong price`.
  - Validate the vehicle type. If invalid, display `wrong vehicle`.
- If all fields are valid, add the trip to the database and display `added`.

### `del`
Deletes a trip from the file.
#### Format
del id
- `id`: Three-digit integer.

#### Actions
- Validate the identifier.
  - If invalid or not found, display `wrong id`.
  - If valid, delete the trip and display `deleted`.

### `edit`
Edits an existing trip in the file.
#### Format
edit id;city;date;days;price;vehicle
- `id`: Three-digit integer.
- Other fields can be empty to skip updating.

#### Actions
- Validate the identifier.
  - If invalid or not found, display `wrong id`.
- Validate other fields as in `add` command.
  - If invalid, display appropriate error message.
- If valid, update the fields and display `changed`.

### `print`
Displays the content of the file in a table format.
#### Format
print

### `sort`
Sorts the trips by date in ascending order.
#### Format
sort
- Display `sorted` after sorting.

### `find`
Finds trips with a price not exceeding a given value.
#### Format
find price
- `price`: Real number.

#### Actions
- Validate the price.
  - If invalid, display `wrong price`.
  - If valid, display trips with a price not exceeding the given value.

### `avg`
Calculates and displays the average price of trips.
#### Format
avg
- Display `average=[average price]` with two decimal places.

### `exit`
Exits the program, saving changes to the file.
#### Format
exit

## Error Handling
- For unknown commands, display `wrong command`.
- Validate input formats and handle errors appropriately.

## Execution Flow
The program runs in a loop, accepting and executing commands until the `exit` command is entered.

## Example Usage
print
Output: (Formatted table of trips)

add 200;Ventspils;05/12/2021;2;100;bus
Output: added

del 105
Output: deleted

edit 101;Rezekne;;;;bus
Output: changed

sort
Output: sorted

find 300
Output: (Formatted table of trips with price <= 300)

avg
Output: average=440.20

exit
Output: Program terminates