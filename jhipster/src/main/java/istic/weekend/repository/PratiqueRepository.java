package istic.weekend.repository;

import istic.weekend.domain.Pratique;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Pratique entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PratiqueRepository extends JpaRepository<Pratique, Long> {

    @Query("select pratique from Pratique pratique where pratique.owner.login = ?#{principal.username}")
    List<Pratique> findByOwnerIsCurrentUser();

}
