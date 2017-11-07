package istic.weekend.web.rest;

import com.codahale.metrics.annotation.Timed;
import istic.weekend.domain.VillePreferee;

import istic.weekend.repository.VillePrefereeRepository;
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
 * REST controller for managing VillePreferee.
 */
@RestController
@RequestMapping("/api")
public class VillePrefereeResource {

    private final Logger log = LoggerFactory.getLogger(VillePrefereeResource.class);

    private static final String ENTITY_NAME = "villePreferee";

    private final VillePrefereeRepository villePrefereeRepository;

    public VillePrefereeResource(VillePrefereeRepository villePrefereeRepository) {
        this.villePrefereeRepository = villePrefereeRepository;
    }

    /**
     * POST  /ville-preferees : Create a new villePreferee.
     *
     * @param villePreferee the villePreferee to create
     * @return the ResponseEntity with status 201 (Created) and with body the new villePreferee, or with status 400 (Bad Request) if the villePreferee has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ville-preferees")
    @Timed
    public ResponseEntity<VillePreferee> createVillePreferee(@RequestBody VillePreferee villePreferee) throws URISyntaxException {
        log.debug("REST request to save VillePreferee : {}", villePreferee);
        if (villePreferee.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new villePreferee cannot already have an ID")).body(null);
        }
        VillePreferee result = villePrefereeRepository.save(villePreferee);
        return ResponseEntity.created(new URI("/api/ville-preferees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ville-preferees : Updates an existing villePreferee.
     *
     * @param villePreferee the villePreferee to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated villePreferee,
     * or with status 400 (Bad Request) if the villePreferee is not valid,
     * or with status 500 (Internal Server Error) if the villePreferee couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ville-preferees")
    @Timed
    public ResponseEntity<VillePreferee> updateVillePreferee(@RequestBody VillePreferee villePreferee) throws URISyntaxException {
        log.debug("REST request to update VillePreferee : {}", villePreferee);
        if (villePreferee.getId() == null) {
            return createVillePreferee(villePreferee);
        }
        VillePreferee result = villePrefereeRepository.save(villePreferee);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, villePreferee.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ville-preferees : get all the villePreferees.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of villePreferees in body
     */
    @GetMapping("/ville-preferees")
    @Timed
    public List<VillePreferee> getAllVillePreferees() {
        log.debug("REST request to get all VillePreferees");
        return villePrefereeRepository.findAll();
        }

    /**
     * GET  /ville-preferees/:id : get the "id" villePreferee.
     *
     * @param id the id of the villePreferee to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the villePreferee, or with status 404 (Not Found)
     */
    @GetMapping("/ville-preferees/{id}")
    @Timed
    public ResponseEntity<VillePreferee> getVillePreferee(@PathVariable Long id) {
        log.debug("REST request to get VillePreferee : {}", id);
        VillePreferee villePreferee = villePrefereeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(villePreferee));
    }

    /**
     * DELETE  /ville-preferees/:id : delete the "id" villePreferee.
     *
     * @param id the id of the villePreferee to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ville-preferees/{id}")
    @Timed
    public ResponseEntity<Void> deleteVillePreferee(@PathVariable Long id) {
        log.debug("REST request to delete VillePreferee : {}", id);
        villePrefereeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
