# GIS-APP
Spring Boot API seente leiukohtade haldamiseks GeoJSON-i ja PostgreSQL-i abil koos lihtsa interaktiivse Leaflet-kaardiga.

## Eeldused
- Java 17+
- PostgreSQL server
- (Optional) Maven Wrapper (`./mvnw`) included

## Steps to run
1. Hangi repositoorium
```
git clone https://github.com/kristensurva/gis-mushroom-app.git
cd gis-mushroom-app
```
2. Veendu, et PostgresSQL server töötab
3. Loo andmebaas ja käivita schema (psql asukoht võiks olla system pathis olemas)
```bash
# Create database (only if not created)
psql -U postgres -c "CREATE DATABASE gis_db;"

# Run schema to create tables
psql -U postgres -d gis_db -f db/schema.sql
```
4. Vajadusel konfigureeri application.properties faili
5. Build app
`
mvnw clean package
`
6. Run app
`
java -jar target/gis-app.jar
`
### Endpoints:
* GET http://localhost:8080/api - tagastab kõik leiukohad
* POST http://localhost:8080/api - GEOJSON kehaga, loob uue leiukoha
* DELETE http://localhost:8080/api/{geoJsonId} - antud identifikaatoriga leiukoht kustutatakse
* PATCH http://localhost:8080/api/{geoJsonId} - antud identifikaatoriga leiukoht muudetakse (On vajalik terve geoJSON body, kaasa arvatud uus id)


### Boonus:
Kaardi veebirakendus - http://localhost:8080/

## Põhjendus

Ülesande teksti tõlgendasin niimoodi, et üks geoJSON objekt võiks näha välja niimoodi:
```json
{
    "type": "Feature",
    "id": 1,
    "geometry": {
        "type": "Point",
        "coordinates": [58.279, 26.439]
    },
    "properties": {
        "description": "Kukeseened!"
    }
}
```
Id on identifikaator, mis on antud kasutaja poolt (andmebaasis GeoJsonId). Andmebaasis on ka automaatselt genereeritud primary key ID, mis on eraldi kasutaja poolt antud 'identifikaatorist'.

Idealseelt oleksin implementeerinud rohkem "bad input" checke, kui kasutaja saadab halba infot. Praegu selleks jäi aega vähe. Samuti peaks ettevaatlik olema error sõnumite tagastamisel kasutajale. Seda annaks paremini teha.
