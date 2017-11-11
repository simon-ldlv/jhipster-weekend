package istic.weekend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import istic.weekend.domain.User;
import istic.weekend.domain.Ville;
import istic.weekend.domain.VillePreferee;

/**
 * Spring Data JPA repository for the VillePreferee entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VillePrefereeRepository extends JpaRepository<VillePreferee, Long> {

    @Query("select ville_preferee from VillePreferee ville_preferee where ville_preferee.owner.login = ?#{principal.username}")
    List<VillePreferee> findByOwnerIsCurrentUser();
    
    
    @Query("select ville_preferee from VillePreferee ville_preferee where ville_preferee.id = :id and ville_preferee.owner.login = ?#{principal.username}")
    VillePreferee findOneByOwnerIsCurrentUser(@Param("id")Long id);
    
    @Query("select ville_preferee from VillePreferee ville_preferee where ville_preferee.ville = :ville and ville_preferee.owner = :owner")
    VillePreferee findOneByVilleByOwner(@Param("ville")Ville ville, @Param("owner")User owner);
    
    @Transactional
    @Modifying
    @Query("DELETE FROM VillePreferee ville_preferee WHERE ville_preferee.id = :idVille AND ville_preferee.owner = :user")
    void deleteForUser(@Param("idVille")Long idVille, @Param("user")User user);
}
