package istic.weekend.web.rest;

import com.codahale.metrics.annotation.Timed;
import istic.weekend.domain.Pratique;

import istic.weekend.repository.PratiqueRepository;
import istic.weekend.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

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

    public PratiqueResource(PratiqueRepository pratiqueRepository) {
        this.pratiqueRepository = pratiqueRepository;
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
    public ResponseEntity<Pratique> createPratique(@RequestBody Pratique pratique) throws URISyntaxException {
        log.debug("REST request to save Pratique : {}", pratique);
        if (pratique.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new pratique cannot already have an ID")).body(null);
        }
        Pratique result = pratiqueRepository.save(pratique);
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
    public ResponseEntity<Pratique> updatePratique(@RequestBody Pratique pratique) throws URISyntaxException {
        log.debug("REST request to update Pratique : {}", pratique);
        if (pratique.getId() == null) {
            return createPratique(pratique);
        }
        Pratique result = pratiqueRepository.save(pratique);
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
        return pratiqueRepository.findAll();
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
        Pratique pratique = pratiqueRepository.findOne(id);
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
    public ResponseEntity<Void> deletePratique(@PathVariable Long id) {
        log.debug("REST request to delete Pratique : {}", id);
        pratiqueRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
