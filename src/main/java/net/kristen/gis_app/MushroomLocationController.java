package net.kristen.gis_app;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api")
public class MushroomLocationController {

    MushroomLocationService service;

    public MushroomLocationController(MushroomLocationService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllLocations() {
        List<GeoJsonLocation> locations = service.getAllAsGeoJson();
        Map<String, Object> response = new HashMap<>();
        response.put("type", "FeatureCollection");
        response.put("features", locations);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Void> postLocation(@RequestBody GeoJsonLocation geoJsonLocation) {
        try {
            service.add(service.fromGeoJson(geoJsonLocation));
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (DuplicateKeyException e) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Location with id '" + geoJsonLocation.getId() + "' already exists."
            );
        }
    }

    @DeleteMapping("/{geoJsonId}")
    public ResponseEntity<Void> deleteLocation(@PathVariable String geoJsonId) {
        if (service.delete(geoJsonId)) {
            return ResponseEntity.noContent().build();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Location with id '"+geoJsonId+"' doesn't exist.");
        }
    }

    @PatchMapping("/{geoJsonId}")
    public ResponseEntity<Void> patchLocation(@PathVariable String geoJsonId, @RequestBody GeoJsonLocation geoJsonLocation) {
        MushroomLocation location = service.fromGeoJson(geoJsonLocation);
        location.setGeoJsonId(geoJsonId);
        if (service.update(location)) {
            return ResponseEntity.accepted().build();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Location with id '"+geoJsonLocation.getId()+"' doesn't exist.");
        }
    }
}
