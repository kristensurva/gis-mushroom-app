package net.kristen.gis_app;

import java.util.Arrays;


public class GeoJsonLocation {
    private String type;
    private String id;
    private Geometry geometry;
    private Properties properties;


    public GeoJsonLocation() {
    }

    public GeoJsonLocation(String type, String id, Geometry geometry, Properties properties) {
        this.type = type;
        this.id = id;
        this.geometry = geometry;
        this.properties = properties;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static class Geometry {
        private String type;
        private double[] coordinates;
        public double[] getCoordinates() { return coordinates; }
        public void setCoordinates(double[] coordinates) { this.coordinates = coordinates; }

        public String getType() {
            return type;
        }

        public Geometry(String type, double[] coordinates) {
            this.type = type;
            this.coordinates = coordinates;
        }

        @Override
        public String toString() {
            return "Geometry{" +
                    "coordinates=" + Arrays.toString(coordinates) +
                    '}';
        }
    }

    public static class Properties {
        private String description;

        public Properties(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        @Override
        public String toString() {
            return "Properties{" +
                    "description='" + description + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "GeoJsonLocation{" +
                "geometry=" + geometry.toString() +
                ", properties=" + properties.toString() +
                '}';
    }
}
