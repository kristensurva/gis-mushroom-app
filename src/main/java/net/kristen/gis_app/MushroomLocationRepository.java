package net.kristen.gis_app;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MushroomLocationRepository extends JpaRepository<MushroomLocation, Long> {
    boolean existsByGeoJsonId(String geoJsonId);
    void deleteByGeoJsonId(String geoJsonId);
}
