package istic.weekend.repository;

import istic.weekend.domain.Activite;
import istic.weekend.domain.Pratique;
import istic.weekend.domain.User;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Pratique entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PratiqueRepository extends JpaRepository<Pratique, Long> {

    @Query("select pratique from Pratique pratique where pratique.owner.login = ?#{principal.username}")
    List<Pratique> findByOwnerIsCurrentUser();
    
    @Query("select pratique from Pratique pratique where pratique.id = :id and pratique.owner.login = ?#{principal.username}")
    Pratique findOneByOwnerIsCurrentUser(@Param("id")Long id);
    
    @Query("select pratique from Pratique pratique where pratique.activite = :activite and pratique.owner = :owner")
    Pratique findOneByActiviteByOwner(@Param("activite")Activite activite, @Param("owner")User owner);
    
    @Transactional
    @Modifying
    @Query("DELETE FROM Pratique pratique WHERE pratique.id = :idPrat AND pratique.owner = :user")
	void deleteForUser(@Param("idPrat")Long idPrat, @Param("user")User user);

    
}
