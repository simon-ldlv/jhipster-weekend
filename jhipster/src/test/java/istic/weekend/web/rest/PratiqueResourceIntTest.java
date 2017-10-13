package istic.weekend.web.rest;

import istic.weekend.ProjetWeekendApp;

import istic.weekend.domain.Pratique;
import istic.weekend.repository.PratiqueRepository;
import istic.weekend.repository.UserRepository;
import istic.weekend.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PratiqueResource REST controller.
 *
 * @see PratiqueResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProjetWeekendApp.class)
public class PratiqueResourceIntTest {

    @Autowired
    private PratiqueRepository pratiqueRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPratiqueMockMvc;

    private Pratique pratique;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PratiqueResource pratiqueResource = new PratiqueResource(pratiqueRepository, userRepository);
        this.restPratiqueMockMvc = MockMvcBuilders.standaloneSetup(pratiqueResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pratique createEntity(EntityManager em) {
        Pratique pratique = new Pratique();
        return pratique;
    }

    @Before
    public void initTest() {
        pratique = createEntity(em);
    }

    @Test
    @Transactional
    public void createPratique() throws Exception {
        int databaseSizeBeforeCreate = pratiqueRepository.findAll().size();

        // Create the Pratique
        restPratiqueMockMvc.perform(post("/api/pratiques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pratique)))
            .andExpect(status().isCreated());

        // Validate the Pratique in the database
        List<Pratique> pratiqueList = pratiqueRepository.findAll();
        assertThat(pratiqueList).hasSize(databaseSizeBeforeCreate + 1);
        Pratique testPratique = pratiqueList.get(pratiqueList.size() - 1);
    }

    @Test
    @Transactional
    public void createPratiqueWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pratiqueRepository.findAll().size();

        // Create the Pratique with an existing ID
        pratique.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPratiqueMockMvc.perform(post("/api/pratiques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pratique)))
            .andExpect(status().isBadRequest());

        // Validate the Pratique in the database
        List<Pratique> pratiqueList = pratiqueRepository.findAll();
        assertThat(pratiqueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPratiques() throws Exception {
        // Initialize the database
        pratiqueRepository.saveAndFlush(pratique);

        // Get all the pratiqueList
        restPratiqueMockMvc.perform(get("/api/pratiques?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pratique.getId().intValue())));
    }

    @Test
    @Transactional
    public void getPratique() throws Exception {
        // Initialize the database
        pratiqueRepository.saveAndFlush(pratique);

        // Get the pratique
        restPratiqueMockMvc.perform(get("/api/pratiques/{id}", pratique.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pratique.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPratique() throws Exception {
        // Get the pratique
        restPratiqueMockMvc.perform(get("/api/pratiques/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePratique() throws Exception {
        // Initialize the database
        pratiqueRepository.saveAndFlush(pratique);
        int databaseSizeBeforeUpdate = pratiqueRepository.findAll().size();

        // Update the pratique
        Pratique updatedPratique = pratiqueRepository.findOne(pratique.getId());

        restPratiqueMockMvc.perform(put("/api/pratiques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPratique)))
            .andExpect(status().isOk());

        // Validate the Pratique in the database
        List<Pratique> pratiqueList = pratiqueRepository.findAll();
        assertThat(pratiqueList).hasSize(databaseSizeBeforeUpdate);
        Pratique testPratique = pratiqueList.get(pratiqueList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingPratique() throws Exception {
        int databaseSizeBeforeUpdate = pratiqueRepository.findAll().size();

        // Create the Pratique

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPratiqueMockMvc.perform(put("/api/pratiques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pratique)))
            .andExpect(status().isCreated());

        // Validate the Pratique in the database
        List<Pratique> pratiqueList = pratiqueRepository.findAll();
        assertThat(pratiqueList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePratique() throws Exception {
        // Initialize the database
        pratiqueRepository.saveAndFlush(pratique);
        int databaseSizeBeforeDelete = pratiqueRepository.findAll().size();

        // Get the pratique
        restPratiqueMockMvc.perform(delete("/api/pratiques/{id}", pratique.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Pratique> pratiqueList = pratiqueRepository.findAll();
        assertThat(pratiqueList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pratique.class);
        Pratique pratique1 = new Pratique();
        pratique1.setId(1L);
        Pratique pratique2 = new Pratique();
        pratique2.setId(pratique1.getId());
        assertThat(pratique1).isEqualTo(pratique2);
        pratique2.setId(2L);
        assertThat(pratique1).isNotEqualTo(pratique2);
        pratique1.setId(null);
        assertThat(pratique1).isNotEqualTo(pratique2);
    }
}
