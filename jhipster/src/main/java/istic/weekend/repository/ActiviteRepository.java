package istic.weekend.repository;

import istic.weekend.domain.Activite;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Activite entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActiviteRepository extends JpaRepository<Activite, Long> {
    @Query("select distinct activite from Activite activite left join fetch activite.weathers order by activite.name")
    List<Activite> findAllWithEagerRelationships();

    @Query("select activite from Activite activite left join fetch activite.weathers where activite.id =:id")
    Activite findOneWithEagerRelationships(@Param("id") Long id);

}
