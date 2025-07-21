package net.kristen.gis_app;


import jakarta.persistence.*;

@Entity
public class MushroomLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String geoJsonId;
    private String description;
    private double latitude;
    private double longitude;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getGeoJsonId() {
        return geoJsonId;
    }

    public void setGeoJsonId(String geoJsonId) {
        this.geoJsonId = geoJsonId;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getDescription() {
        return description;
    }

    public MushroomLocation() {

    }
    public MushroomLocation(String geoJsonId, String description, double latitude, double longitude) {
        this.geoJsonId = geoJsonId;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}