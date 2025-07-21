package net.kristen.gis_app;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MushroomLocationRepository extends JpaRepository<MushroomLocation, Long> {
    Optional<MushroomLocation> findByGeoJsonId(String geoJsonId);
    boolean existsByGeoJsonId(String geoJsonId);
    void deleteByGeoJsonId(String geoJsonId);
}
