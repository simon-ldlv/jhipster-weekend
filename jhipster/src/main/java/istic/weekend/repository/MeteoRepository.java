package istic.weekend.repository;

import istic.weekend.domain.Meteo;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Meteo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MeteoRepository extends JpaRepository<Meteo, Long> {

}
