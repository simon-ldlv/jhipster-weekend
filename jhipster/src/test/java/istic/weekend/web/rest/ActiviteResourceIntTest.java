package istic.weekend.web.rest;

import istic.weekend.ProjetWeekendApp;

import istic.weekend.domain.Activite;
import istic.weekend.repository.ActiviteRepository;
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
 * Test class for the ActiviteResource REST controller.
 *
 * @see ActiviteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProjetWeekendApp.class)
public class ActiviteResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_COLLECTIF = false;
    private static final Boolean UPDATED_COLLECTIF = true;

    private static final Boolean DEFAULT_ENSOLEILLE = false;
    private static final Boolean UPDATED_ENSOLEILLE = true;

    private static final Boolean DEFAULT_NUAGEUX = false;
    private static final Boolean UPDATED_NUAGEUX = true;

    private static final Boolean DEFAULT_ENNEIGE = false;
    private static final Boolean UPDATED_ENNEIGE = true;

    private static final Boolean DEFAULT_PLUVIEUX = false;
    private static final Boolean UPDATED_PLUVIEUX = true;

    private static final Double DEFAULT_CELSIUS_MIN = 1D;
    private static final Double UPDATED_CELSIUS_MIN = 2D;

    private static final Double DEFAULT_CELSIUS_MAX = 1D;
    private static final Double UPDATED_CELSIUS_MAX = 2D;

    @Autowired
    private ActiviteRepository activiteRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restActiviteMockMvc;

    private Activite activite;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ActiviteResource activiteResource = new ActiviteResource(activiteRepository);
        this.restActiviteMockMvc = MockMvcBuilders.standaloneSetup(activiteResource)
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
    public static Activite createEntity(EntityManager em) {
        Activite activite = new Activite()
            .name(DEFAULT_NAME)
            .collectif(DEFAULT_COLLECTIF)
            .ensoleille(DEFAULT_ENSOLEILLE)
            .nuageux(DEFAULT_NUAGEUX)
            .enneige(DEFAULT_ENNEIGE)
            .pluvieux(DEFAULT_PLUVIEUX)
            .celsiusMin(DEFAULT_CELSIUS_MIN)
            .celsiusMax(DEFAULT_CELSIUS_MAX);
        return activite;
    }

    @Before
    public void initTest() {
        activite = createEntity(em);
    }

    @Test
    @Transactional
    public void createActivite() throws Exception {
        int databaseSizeBeforeCreate = activiteRepository.findAll().size();

        // Create the Activite
        restActiviteMockMvc.perform(post("/api/activites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activite)))
            .andExpect(status().isCreated());

        // Validate the Activite in the database
        List<Activite> activiteList = activiteRepository.findAll();
        assertThat(activiteList).hasSize(databaseSizeBeforeCreate + 1);
        Activite testActivite = activiteList.get(activiteList.size() - 1);
        assertThat(testActivite.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testActivite.isCollectif()).isEqualTo(DEFAULT_COLLECTIF);
        assertThat(testActivite.isEnsoleille()).isEqualTo(DEFAULT_ENSOLEILLE);
        assertThat(testActivite.isNuageux()).isEqualTo(DEFAULT_NUAGEUX);
        assertThat(testActivite.isEnneige()).isEqualTo(DEFAULT_ENNEIGE);
        assertThat(testActivite.isPluvieux()).isEqualTo(DEFAULT_PLUVIEUX);
        assertThat(testActivite.getCelsiusMin()).isEqualTo(DEFAULT_CELSIUS_MIN);
        assertThat(testActivite.getCelsiusMax()).isEqualTo(DEFAULT_CELSIUS_MAX);
    }

    @Test
    @Transactional
    public void createActiviteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = activiteRepository.findAll().size();

        // Create the Activite with an existing ID
        activite.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restActiviteMockMvc.perform(post("/api/activites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activite)))
            .andExpect(status().isBadRequest());

        // Validate the Activite in the database
        List<Activite> activiteList = activiteRepository.findAll();
        assertThat(activiteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllActivites() throws Exception {
        // Initialize the database
        activiteRepository.saveAndFlush(activite);

        // Get all the activiteList
        restActiviteMockMvc.perform(get("/api/activites?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(activite.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].collectif").value(hasItem(DEFAULT_COLLECTIF.booleanValue())))
            .andExpect(jsonPath("$.[*].ensoleille").value(hasItem(DEFAULT_ENSOLEILLE.booleanValue())))
            .andExpect(jsonPath("$.[*].nuageux").value(hasItem(DEFAULT_NUAGEUX.booleanValue())))
            .andExpect(jsonPath("$.[*].enneige").value(hasItem(DEFAULT_ENNEIGE.booleanValue())))
            .andExpect(jsonPath("$.[*].pluvieux").value(hasItem(DEFAULT_PLUVIEUX.booleanValue())))
            .andExpect(jsonPath("$.[*].celsiusMin").value(hasItem(DEFAULT_CELSIUS_MIN.doubleValue())))
            .andExpect(jsonPath("$.[*].celsiusMax").value(hasItem(DEFAULT_CELSIUS_MAX.doubleValue())));
    }

    @Test
    @Transactional
    public void getActivite() throws Exception {
        // Initialize the database
        activiteRepository.saveAndFlush(activite);

        // Get the activite
        restActiviteMockMvc.perform(get("/api/activites/{id}", activite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(activite.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.collectif").value(DEFAULT_COLLECTIF.booleanValue()))
            .andExpect(jsonPath("$.ensoleille").value(DEFAULT_ENSOLEILLE.booleanValue()))
            .andExpect(jsonPath("$.nuageux").value(DEFAULT_NUAGEUX.booleanValue()))
            .andExpect(jsonPath("$.enneige").value(DEFAULT_ENNEIGE.booleanValue()))
            .andExpect(jsonPath("$.pluvieux").value(DEFAULT_PLUVIEUX.booleanValue()))
            .andExpect(jsonPath("$.celsiusMin").value(DEFAULT_CELSIUS_MIN.doubleValue()))
            .andExpect(jsonPath("$.celsiusMax").value(DEFAULT_CELSIUS_MAX.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingActivite() throws Exception {
        // Get the activite
        restActiviteMockMvc.perform(get("/api/activites/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateActivite() throws Exception {
        // Initialize the database
        activiteRepository.saveAndFlush(activite);
        int databaseSizeBeforeUpdate = activiteRepository.findAll().size();

        // Update the activite
        Activite updatedActivite = activiteRepository.findOne(activite.getId());
        updatedActivite
            .name(UPDATED_NAME)
            .collectif(UPDATED_COLLECTIF)
            .ensoleille(UPDATED_ENSOLEILLE)
            .nuageux(UPDATED_NUAGEUX)
            .enneige(UPDATED_ENNEIGE)
            .pluvieux(UPDATED_PLUVIEUX)
            .celsiusMin(UPDATED_CELSIUS_MIN)
            .celsiusMax(UPDATED_CELSIUS_MAX);

        restActiviteMockMvc.perform(put("/api/activites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedActivite)))
            .andExpect(status().isOk());

        // Validate the Activite in the database
        List<Activite> activiteList = activiteRepository.findAll();
        assertThat(activiteList).hasSize(databaseSizeBeforeUpdate);
        Activite testActivite = activiteList.get(activiteList.size() - 1);
        assertThat(testActivite.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testActivite.isCollectif()).isEqualTo(UPDATED_COLLECTIF);
        assertThat(testActivite.isEnsoleille()).isEqualTo(UPDATED_ENSOLEILLE);
        assertThat(testActivite.isNuageux()).isEqualTo(UPDATED_NUAGEUX);
        assertThat(testActivite.isEnneige()).isEqualTo(UPDATED_ENNEIGE);
        assertThat(testActivite.isPluvieux()).isEqualTo(UPDATED_PLUVIEUX);
        assertThat(testActivite.getCelsiusMin()).isEqualTo(UPDATED_CELSIUS_MIN);
        assertThat(testActivite.getCelsiusMax()).isEqualTo(UPDATED_CELSIUS_MAX);
    }

    @Test
    @Transactional
    public void updateNonExistingActivite() throws Exception {
        int databaseSizeBeforeUpdate = activiteRepository.findAll().size();

        // Create the Activite

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restActiviteMockMvc.perform(put("/api/activites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activite)))
            .andExpect(status().isCreated());

        // Validate the Activite in the database
        List<Activite> activiteList = activiteRepository.findAll();
        assertThat(activiteList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteActivite() throws Exception {
        // Initialize the database
        activiteRepository.saveAndFlush(activite);
        int databaseSizeBeforeDelete = activiteRepository.findAll().size();

        // Get the activite
        restActiviteMockMvc.perform(delete("/api/activites/{id}", activite.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Activite> activiteList = activiteRepository.findAll();
        assertThat(activiteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Activite.class);
        Activite activite1 = new Activite();
        activite1.setId(1L);
        Activite activite2 = new Activite();
        activite2.setId(activite1.getId());
        assertThat(activite1).isEqualTo(activite2);
        activite2.setId(2L);
        assertThat(activite1).isNotEqualTo(activite2);
        activite1.setId(null);
        assertThat(activite1).isNotEqualTo(activite2);
    }
}
