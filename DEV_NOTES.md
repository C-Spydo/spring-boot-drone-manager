### Developer Notes

### Database
H2 Database

### Models
#### Drone
- serial_number: VARCHAR (100)
- model: ENUM (Lightweight, Middleweight, Cruiserweight, Heavyweight);
- weight_limit: DECIMAL (6,3);
- battery_capacity: INT
- state: ENUM (IDLE, LOADING, LOADED, DELIVERING, DELIVERED, RETURNING).

#### Medication:
- name: VARCHAR (255)
- weight:  DECIMAL (10,3)
- code: VARCHAR(200)
- image: BLOB

#### Dispatch:

#### DispatchItem:

### Endpoints
- Register Drone: /drone/register (POST)
- loading a drone with medication items: /drone/load (POST)
- checking loaded medication items for a given drone: /drone/check-load/{drone_id} (GET)
- checking available drones for loading: /drone/available (GET)
- check drone battery level for a given drone: /drone/battery-level/{drone_id} (GET)