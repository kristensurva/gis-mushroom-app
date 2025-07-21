package net.kristen.gis_app;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MushroomLocationService {

    private final MushroomLocationRepository repository;

    public MushroomLocationService(MushroomLocationRepository repository) {
        this.repository = repository;
    }

    public List<GeoJsonLocation> getAllAsGeoJson() {
        List<MushroomLocation> locations = repository.findAll();
        return locations.stream()
                .map(this::toGeoJson).toList();
    }

    public void add(MushroomLocation location) {
        if (repository.existsByGeoJsonId(location.getGeoJsonId())) {
            throw new DuplicateKeyException("Location with id '" + location.getGeoJsonId() + "' already exists");
        }
        repository.save(location);
    }

    @Transactional
    public boolean delete(String id) {
        if (!repository.existsByGeoJsonId(id)) return false;
        repository.deleteByGeoJsonId(id);
        return true;
    }

    public boolean update(String currentID, MushroomLocation location) {
        // Check if current exists
        Optional<MushroomLocation> existingOpt = repository.findByGeoJsonId(currentID);
        if (existingOpt.isEmpty()) {
            return false;
        }
        // Check if new geoJsonId is used by a different location
        if (repository.existsByGeoJsonId(location.getGeoJsonId())) {
            throw new DuplicateKeyException("Location with the new id '" + location.getGeoJsonId() + "' already exists!");
        }
        MushroomLocation existingLocation = existingOpt.get();
        // Set the existing entity's PK id on the new location
        location.setId(existingLocation.getId());
        repository.save(location);
        return true;
    }

    public MushroomLocation fromGeoJson(GeoJsonLocation geoJsonLocation) {
        String id = geoJsonLocation.getId();
        String description = geoJsonLocation.getProperties().getDescription();
        double[] coords = geoJsonLocation.getGeometry().getCoordinates();
        return new MushroomLocation(id, description, coords[0], coords[1]);
    }

    public GeoJsonLocation toGeoJson(MushroomLocation location) {
        GeoJsonLocation.Geometry geometry = new GeoJsonLocation.Geometry("Point", new double[]{location.getLongitude(), location.getLatitude()});
        GeoJsonLocation.Properties props = new GeoJsonLocation.Properties(location.getDescription());
        return new GeoJsonLocation("Feature", location.getGeoJsonId(), geometry, props);
    }
}
