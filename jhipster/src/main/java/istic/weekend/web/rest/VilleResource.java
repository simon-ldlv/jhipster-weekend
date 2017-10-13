package istic.weekend.web.rest;

import com.codahale.metrics.annotation.Timed;
import istic.weekend.domain.Ville;

import istic.weekend.repository.VilleRepository;
import istic.weekend.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Ville.
 */
@RestController
@RequestMapping("/api")
public class VilleResource {

    private final Logger log = LoggerFactory.getLogger(VilleResource.class);

    private static final String ENTITY_NAME = "ville";

    private final VilleRepository villeRepository;

    public VilleResource(VilleRepository villeRepository) {
        this.villeRepository = villeRepository;
    }

    /**
     * POST  /villes : Create a new ville.
     *
     * @param ville the ville to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ville, or with status 400 (Bad Request) if the ville has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/villes")
    @Timed
    public ResponseEntity<Ville> createVille(@Valid @RequestBody Ville ville) throws URISyntaxException {
        log.debug("REST request to save Ville : {}", ville);
        if (ville.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new ville cannot already have an ID")).body(null);
        }
        Ville result = villeRepository.save(ville);
        return ResponseEntity.created(new URI("/api/villes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /villes : Updates an existing ville.
     *
     * @param ville the ville to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ville,
     * or with status 400 (Bad Request) if the ville is not valid,
     * or with status 500 (Internal Server Error) if the ville couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/villes")
    @Timed
    public ResponseEntity<Ville> updateVille(@Valid @RequestBody Ville ville) throws URISyntaxException {
        log.debug("REST request to update Ville : {}", ville);
        if (ville.getId() == null) {
            return createVille(ville);
        }
        Ville result = villeRepository.save(ville);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ville.getId().toString()))
            .body(result);
    }

    /**
     * GET  /villes : get all the villes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of villes in body
     */
    @GetMapping("/villes")
    @Timed
    public List<Ville> getAllVilles() {
        log.debug("REST request to get all Villes");
        return villeRepository.findAllWithEagerRelationships();
        }

    /**
     * GET  /villes/:id : get the "id" ville.
     *
     * @param id the id of the ville to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ville, or with status 404 (Not Found)
     */
    @GetMapping("/villes/{id}")
    @Timed
    public ResponseEntity<Ville> getVille(@PathVariable Long id) {
        log.debug("REST request to get Ville : {}", id);
        Ville ville = villeRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ville));
    }

    /**
     * DELETE  /villes/:id : delete the "id" ville.
     *
     * @param id the id of the ville to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/villes/{id}")
    @Timed
    public ResponseEntity<Void> deleteVille(@PathVariable Long id) {
        log.debug("REST request to delete Ville : {}", id);
        villeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
