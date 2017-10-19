package istic.weekend.web.rest;

import istic.weekend.ProjetWeekendApp;

import istic.weekend.domain.Meteo;
import istic.weekend.repository.MeteoRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MeteoResource REST controller.
 *
 * @see MeteoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProjetWeekendApp.class)
public class MeteoResourceIntTest {

    private static final LocalDate DEFAULT_UPDATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_CELSIUS_AVERAGE = 1D;
    private static final Double UPDATED_CELSIUS_AVERAGE = 2D;

    @Autowired
    private MeteoRepository meteoRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMeteoMockMvc;

    private Meteo meteo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MeteoResource meteoResource = new MeteoResource(meteoRepository);
        this.restMeteoMockMvc = MockMvcBuilders.standaloneSetup(meteoResource)
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
    public static Meteo createEntity(EntityManager em) {
        Meteo meteo = new Meteo()
            .updated(DEFAULT_UPDATED)
            .celsiusAverage(DEFAULT_CELSIUS_AVERAGE);
        return meteo;
    }

    @Before
    public void initTest() {
        meteo = createEntity(em);
    }

    @Test
    @Transactional
    public void createMeteo() throws Exception {
        int databaseSizeBeforeCreate = meteoRepository.findAll().size();

        // Create the Meteo
        restMeteoMockMvc.perform(post("/api/meteos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meteo)))
            .andExpect(status().isCreated());

        // Validate the Meteo in the database
        List<Meteo> meteoList = meteoRepository.findAll();
        assertThat(meteoList).hasSize(databaseSizeBeforeCreate + 1);
        Meteo testMeteo = meteoList.get(meteoList.size() - 1);
        assertThat(testMeteo.getUpdated()).isEqualTo(DEFAULT_UPDATED);
        assertThat(testMeteo.getCelsiusAverage()).isEqualTo(DEFAULT_CELSIUS_AVERAGE);
    }

    @Test
    @Transactional
    public void createMeteoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = meteoRepository.findAll().size();

        // Create the Meteo with an existing ID
        meteo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMeteoMockMvc.perform(post("/api/meteos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meteo)))
            .andExpect(status().isBadRequest());

        // Validate the Meteo in the database
        List<Meteo> meteoList = meteoRepository.findAll();
        assertThat(meteoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMeteos() throws Exception {
        // Initialize the database
        meteoRepository.saveAndFlush(meteo);

        // Get all the meteoList
        restMeteoMockMvc.perform(get("/api/meteos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(meteo.getId().intValue())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].celsiusAverage").value(hasItem(DEFAULT_CELSIUS_AVERAGE.doubleValue())));
    }

    @Test
    @Transactional
    public void getMeteo() throws Exception {
        // Initialize the database
        meteoRepository.saveAndFlush(meteo);

        // Get the meteo
        restMeteoMockMvc.perform(get("/api/meteos/{id}", meteo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(meteo.getId().intValue()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()))
            .andExpect(jsonPath("$.celsiusAverage").value(DEFAULT_CELSIUS_AVERAGE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMeteo() throws Exception {
        // Get the meteo
        restMeteoMockMvc.perform(get("/api/meteos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMeteo() throws Exception {
        // Initialize the database
        meteoRepository.saveAndFlush(meteo);
        int databaseSizeBeforeUpdate = meteoRepository.findAll().size();

        // Update the meteo
        Meteo updatedMeteo = meteoRepository.findOne(meteo.getId());
        updatedMeteo
            .updated(UPDATED_UPDATED)
            .celsiusAverage(UPDATED_CELSIUS_AVERAGE);

        restMeteoMockMvc.perform(put("/api/meteos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMeteo)))
            .andExpect(status().isOk());

        // Validate the Meteo in the database
        List<Meteo> meteoList = meteoRepository.findAll();
        assertThat(meteoList).hasSize(databaseSizeBeforeUpdate);
        Meteo testMeteo = meteoList.get(meteoList.size() - 1);
        assertThat(testMeteo.getUpdated()).isEqualTo(UPDATED_UPDATED);
        assertThat(testMeteo.getCelsiusAverage()).isEqualTo(UPDATED_CELSIUS_AVERAGE);
    }

    @Test
    @Transactional
    public void updateNonExistingMeteo() throws Exception {
        int databaseSizeBeforeUpdate = meteoRepository.findAll().size();

        // Create the Meteo

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMeteoMockMvc.perform(put("/api/meteos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meteo)))
            .andExpect(status().isCreated());

        // Validate the Meteo in the database
        List<Meteo> meteoList = meteoRepository.findAll();
        assertThat(meteoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMeteo() throws Exception {
        // Initialize the database
        meteoRepository.saveAndFlush(meteo);
        int databaseSizeBeforeDelete = meteoRepository.findAll().size();

        // Get the meteo
        restMeteoMockMvc.perform(delete("/api/meteos/{id}", meteo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Meteo> meteoList = meteoRepository.findAll();
        assertThat(meteoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Meteo.class);
        Meteo meteo1 = new Meteo();
        meteo1.setId(1L);
        Meteo meteo2 = new Meteo();
        meteo2.setId(meteo1.getId());
        assertThat(meteo1).isEqualTo(meteo2);
        meteo2.setId(2L);
        assertThat(meteo1).isNotEqualTo(meteo2);
        meteo1.setId(null);
        assertThat(meteo1).isNotEqualTo(meteo2);
    }
}
