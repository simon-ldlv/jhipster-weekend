package istic.weekend.web.rest;

import com.codahale.metrics.annotation.Timed;

import istic.weekend.domain.Authority;
import istic.weekend.domain.Pratique;
import istic.weekend.domain.User;
import istic.weekend.repository.PratiqueRepository;
import istic.weekend.repository.UserRepository;
import istic.weekend.security.SecurityUtils;
import istic.weekend.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Pratique.
 */
@RestController
@RequestMapping("/api")
public class PratiqueResource {

    private final Logger log = LoggerFactory.getLogger(PratiqueResource.class);

    private static final String ENTITY_NAME = "pratique";

    private final PratiqueRepository pratiqueRepository;

    private final UserRepository userRepository;

    public PratiqueResource(PratiqueRepository pratiqueRepository, UserRepository userRepository) {
        this.pratiqueRepository = pratiqueRepository;
        this.userRepository = userRepository;
    }

    /**
     * POST  /pratiques : Create a new pratique.
     *
     * @param pratique the pratique to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pratique, or with status 400 (Bad Request) if the pratique has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pratiques")
    @Timed
    public ResponseEntity<Pratique> createPratique(@RequestBody Pratique pratique, Principal principal) throws URISyntaxException {
        log.debug("REST request to save Pratique : {}", pratique);
        Pratique result = null;
        if (pratique.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new pratique cannot already have an ID")).body(null);
        }
        
        Optional<User> theUserOp = userRepository.findOneByLogin(principal.getName());
        User myUser = theUserOp.get();
        
        // Si la pratique n'a pas d'owner alors l'owner par defaut est l'user connecté
        if(pratique.getOwner()==null) {
        	pratique.setOwner(myUser);
        }
        
        // On verifie que la pratique de ce sport n'est pas deja existante pour cette utilisateur
        if(pratiqueRepository.findOneByActiviteByOwner(pratique.getActivite(), pratique.getOwner()) == null){
        	
	        if(SecurityUtils.isCurrentUserInRole("ROLE_ADMIN")) {
	            result = pratiqueRepository.save(pratique);
	    	} else { // if role is ROLE_USER
	
	            if(myUser.equals(pratique.getOwner())) {
	                result = pratiqueRepository.save(pratique);
	            }
	    	}
        }
        
        return ResponseEntity.created(new URI("/api/pratiques/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pratiques : Updates an existing pratique.
     *
     * @param pratique the pratique to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pratique,
     * or with status 400 (Bad Request) if the pratique is not valid,
     * or with status 500 (Internal Server Error) if the pratique couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pratiques")
    @Timed
    public ResponseEntity<Pratique> updatePratique(@RequestBody Pratique pratique, Principal principal) throws URISyntaxException {
        log.debug("REST request to update Pratique : {}", pratique);
        Pratique result = null;
        if(pratiqueRepository.findOneByActiviteByOwner(pratique.getActivite(), pratique.getOwner()) == null){
        	
	        if (pratique.getId() == null) {
	            return createPratique(pratique, principal);
	        }
	        
	        if(SecurityUtils.isCurrentUserInRole("ROLE_ADMIN")) {
	            result = pratiqueRepository.save(pratique);
	    	} else { // if role is ROLE_USER
	            Optional<User> theUserOp = userRepository.findOneByLogin(principal.getName());
	            User myUser = theUserOp.get();
	            if(myUser.equals(pratique.getOwner())) {
	                result = pratiqueRepository.save(pratique);
	            }
	    	}  
        }else {
        	return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
        }
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pratique.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pratiques : get all the pratiques.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of pratiques in body
     */
    @GetMapping("/pratiques")
    @Timed
    public List<Pratique> getAllPratiques() {
    	log.debug("REST request to get all Pratiques");
    
    	if(SecurityUtils.isCurrentUserInRole("ROLE_ADMIN")) {
    		return pratiqueRepository.findAll();
    	} else {
    		return pratiqueRepository.findByOwnerIsCurrentUser();
    	}
        
    }

    /**
     * GET  /pratiques/:id : get the "id" pratique.
     *
     * @param id the id of the pratique to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pratique, or with status 404 (Not Found)
     */
    @GetMapping("/pratiques/{id}")
    @Timed
    public ResponseEntity<Pratique> getPratique(@PathVariable Long id) {
        log.debug("REST request to get Pratique : {}", id);
        Pratique pratique = null;
    	if(SecurityUtils.isCurrentUserInRole("ROLE_ADMIN")) {
    		pratique = pratiqueRepository.findOne(id);

    	} else {
    		pratique = pratiqueRepository.findOneByOwnerIsCurrentUser(id);
    	}
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(pratique));
    }

    /**
     * DELETE  /pratiques/:id : delete the "id" pratique.
     *
     * @param id the id of the pratique to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pratiques/{id}")
    @Timed
    public ResponseEntity<Void> deletePratique(@PathVariable Long id , Principal principal) {
        log.debug("REST request to delete Pratique : {}", id);
        Optional<User> theUserOp = userRepository.findOneByLogin(principal.getName());
        User myUser = theUserOp.get();
    	if(SecurityUtils.isCurrentUserInRole("ROLE_ADMIN")) {
            pratiqueRepository.delete(id);
    	} else { // if role is ROLE_USER
            pratiqueRepository.deleteForUser(id, myUser);
    	}
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
