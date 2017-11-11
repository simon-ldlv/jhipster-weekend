package istic.weekend.repository;

import istic.weekend.domain.Ville;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Ville entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VilleRepository extends JpaRepository<Ville, Long> {
    @Query("select distinct ville from Ville ville left join fetch ville.activites order by ville.name")
    List<Ville> findAllWithEagerRelationships();

    @Query("select ville from Ville ville left join fetch ville.activites where ville.id =:id")
    Ville findOneWithEagerRelationships(@Param("id") Long id);

}
