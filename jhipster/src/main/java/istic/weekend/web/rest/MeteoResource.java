package istic.weekend.web.rest;

import com.codahale.metrics.annotation.Timed;
import istic.weekend.domain.Meteo;

import istic.weekend.repository.MeteoRepository;
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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing Meteo.
 */
@RestController
@RequestMapping("/api")
public class MeteoResource {

    private final Logger log = LoggerFactory.getLogger(MeteoResource.class);

    private static final String ENTITY_NAME = "meteo";

    private final MeteoRepository meteoRepository;

    public MeteoResource(MeteoRepository meteoRepository) {
        this.meteoRepository = meteoRepository;
    }

    /**
     * POST  /meteos : Create a new meteo.
     *
     * @param meteo the meteo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new meteo, or with status 400 (Bad Request) if the meteo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/meteos")
    @Timed
    public ResponseEntity<Meteo> createMeteo(@RequestBody Meteo meteo) throws URISyntaxException {
        log.debug("REST request to save Meteo : {}", meteo);
        if (meteo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new meteo cannot already have an ID")).body(null);
        }
        Meteo result = meteoRepository.save(meteo);
        return ResponseEntity.created(new URI("/api/meteos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /meteos : Updates an existing meteo.
     *
     * @param meteo the meteo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated meteo,
     * or with status 400 (Bad Request) if the meteo is not valid,
     * or with status 500 (Internal Server Error) if the meteo couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/meteos")
    @Timed
    public ResponseEntity<Meteo> updateMeteo(@RequestBody Meteo meteo) throws URISyntaxException {
        log.debug("REST request to update Meteo : {}", meteo);
        if (meteo.getId() == null) {
            return createMeteo(meteo);
        }
        Meteo result = meteoRepository.save(meteo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, meteo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /meteos : get all the meteos.
     *
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of meteos in body
     */
    @GetMapping("/meteos")
    @Timed
    public List<Meteo> getAllMeteos(@RequestParam(required = false) String filter) {
        if ("ville-is-null".equals(filter)) {
            log.debug("REST request to get all Meteos where ville is null");
            return StreamSupport
                .stream(meteoRepository.findAll().spliterator(), false)
                .filter(meteo -> meteo.getVille() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all Meteos");
        return meteoRepository.findAll();
        }

    /**
     * GET  /meteos/:id : get the "id" meteo.
     *
     * @param id the id of the meteo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the meteo, or with status 404 (Not Found)
     */
    @GetMapping("/meteos/{id}")
    @Timed
    public ResponseEntity<Meteo> getMeteo(@PathVariable Long id) {
        log.debug("REST request to get Meteo : {}", id);
        Meteo meteo = meteoRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(meteo));
    }

    /**
     * DELETE  /meteos/:id : delete the "id" meteo.
     *
     * @param id the id of the meteo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/meteos/{id}")
    @Timed
    public ResponseEntity<Void> deleteMeteo(@PathVariable Long id) {
        log.debug("REST request to delete Meteo : {}", id);
        meteoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
