package istic.weekend.web.rest;

import istic.weekend.ProjetWeekendApp;

import istic.weekend.domain.VillePreferee;
import istic.weekend.repository.VillePrefereeRepository;
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
 * Test class for the VillePrefereeResource REST controller.
 *
 * @see VillePrefereeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProjetWeekendApp.class)
public class VillePrefereeResourceIntTest {

    @Autowired
    private VillePrefereeRepository villePrefereeRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVillePrefereeMockMvc;

    private VillePreferee villePreferee;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VillePrefereeResource villePrefereeResource = new VillePrefereeResource(villePrefereeRepository);
        this.restVillePrefereeMockMvc = MockMvcBuilders.standaloneSetup(villePrefereeResource)
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
    public static VillePreferee createEntity(EntityManager em) {
        VillePreferee villePreferee = new VillePreferee();
        return villePreferee;
    }

    @Before
    public void initTest() {
        villePreferee = createEntity(em);
    }

    @Test
    @Transactional
    public void createVillePreferee() throws Exception {
        int databaseSizeBeforeCreate = villePrefereeRepository.findAll().size();

        // Create the VillePreferee
        restVillePrefereeMockMvc.perform(post("/api/ville-preferees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(villePreferee)))
            .andExpect(status().isCreated());

        // Validate the VillePreferee in the database
        List<VillePreferee> villePrefereeList = villePrefereeRepository.findAll();
        assertThat(villePrefereeList).hasSize(databaseSizeBeforeCreate + 1);
        VillePreferee testVillePreferee = villePrefereeList.get(villePrefereeList.size() - 1);
    }

    @Test
    @Transactional
    public void createVillePrefereeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = villePrefereeRepository.findAll().size();

        // Create the VillePreferee with an existing ID
        villePreferee.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVillePrefereeMockMvc.perform(post("/api/ville-preferees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(villePreferee)))
            .andExpect(status().isBadRequest());

        // Validate the VillePreferee in the database
        List<VillePreferee> villePrefereeList = villePrefereeRepository.findAll();
        assertThat(villePrefereeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllVillePreferees() throws Exception {
        // Initialize the database
        villePrefereeRepository.saveAndFlush(villePreferee);

        // Get all the villePrefereeList
        restVillePrefereeMockMvc.perform(get("/api/ville-preferees?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(villePreferee.getId().intValue())));
    }

    @Test
    @Transactional
    public void getVillePreferee() throws Exception {
        // Initialize the database
        villePrefereeRepository.saveAndFlush(villePreferee);

        // Get the villePreferee
        restVillePrefereeMockMvc.perform(get("/api/ville-preferees/{id}", villePreferee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(villePreferee.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingVillePreferee() throws Exception {
        // Get the villePreferee
        restVillePrefereeMockMvc.perform(get("/api/ville-preferees/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVillePreferee() throws Exception {
        // Initialize the database
        villePrefereeRepository.saveAndFlush(villePreferee);
        int databaseSizeBeforeUpdate = villePrefereeRepository.findAll().size();

        // Update the villePreferee
        VillePreferee updatedVillePreferee = villePrefereeRepository.findOne(villePreferee.getId());

        restVillePrefereeMockMvc.perform(put("/api/ville-preferees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVillePreferee)))
            .andExpect(status().isOk());

        // Validate the VillePreferee in the database
        List<VillePreferee> villePrefereeList = villePrefereeRepository.findAll();
        assertThat(villePrefereeList).hasSize(databaseSizeBeforeUpdate);
        VillePreferee testVillePreferee = villePrefereeList.get(villePrefereeList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingVillePreferee() throws Exception {
        int databaseSizeBeforeUpdate = villePrefereeRepository.findAll().size();

        // Create the VillePreferee

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVillePrefereeMockMvc.perform(put("/api/ville-preferees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(villePreferee)))
            .andExpect(status().isCreated());

        // Validate the VillePreferee in the database
        List<VillePreferee> villePrefereeList = villePrefereeRepository.findAll();
        assertThat(villePrefereeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteVillePreferee() throws Exception {
        // Initialize the database
        villePrefereeRepository.saveAndFlush(villePreferee);
        int databaseSizeBeforeDelete = villePrefereeRepository.findAll().size();

        // Get the villePreferee
        restVillePrefereeMockMvc.perform(delete("/api/ville-preferees/{id}", villePreferee.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<VillePreferee> villePrefereeList = villePrefereeRepository.findAll();
        assertThat(villePrefereeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VillePreferee.class);
        VillePreferee villePreferee1 = new VillePreferee();
        villePreferee1.setId(1L);
        VillePreferee villePreferee2 = new VillePreferee();
        villePreferee2.setId(villePreferee1.getId());
        assertThat(villePreferee1).isEqualTo(villePreferee2);
        villePreferee2.setId(2L);
        assertThat(villePreferee1).isNotEqualTo(villePreferee2);
        villePreferee1.setId(null);
        assertThat(villePreferee1).isNotEqualTo(villePreferee2);
    }
}
