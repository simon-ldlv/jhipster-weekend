package istic.weekend.web.rest;

import com.codahale.metrics.annotation.Timed;

import istic.weekend.domain.Activite;
import istic.weekend.domain.Meteo;
import istic.weekend.domain.Pratique;
import istic.weekend.domain.User;
import istic.weekend.domain.Ville;
import istic.weekend.domain.VillePreferee;
import istic.weekend.domain.Weather;
import istic.weekend.domain.WeekendInfo;
import istic.weekend.web.rest.RestConsumer;
import istic.weekend.repository.MeteoRepository;
import istic.weekend.repository.PratiqueRepository;
import istic.weekend.repository.UserRepository;
import istic.weekend.repository.VillePrefereeRepository;
import istic.weekend.repository.VilleRepository;
import istic.weekend.repository.WeatherRepository;
import istic.weekend.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Access;
import javax.persistence.EntityManager;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * REST controller for managing Weather.
 */
@RestController
@RequestMapping("/api")
public class WeatherResource {

    private final Logger log = LoggerFactory.getLogger(WeatherResource.class);

    private static final String ENTITY_NAME = "weather";

    private final WeatherRepository weatherRepository;
    private final UserRepository userRepository;
    private final MeteoRepository meteoRepository;
    private final VilleRepository villeRepository;
    private final PratiqueRepository pratiqueRepository;
    private final VillePrefereeRepository villePrefereeRepository;


    
    public WeatherResource(WeatherRepository weatherRepository, UserRepository userRepository, MeteoRepository meteoRepository, VilleRepository villeRepository, PratiqueRepository pratiqueRepository, VillePrefereeRepository villePrefereeRepository) {
        this.weatherRepository = weatherRepository;
        this.userRepository = userRepository;
        this.meteoRepository = meteoRepository;
        this.villeRepository = villeRepository;
        this.pratiqueRepository = pratiqueRepository;
        this.villePrefereeRepository = villePrefereeRepository;

    }

    /**
     * POST  /weathers : Create a new weather.
     *
     * @param weather the weather to create
     * @return the ResponseEntity with status 201 (Created) and with body the new weather, or with status 400 (Bad Request) if the weather has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/weathers")
    @Timed
    public ResponseEntity<Weather> createWeather(@Valid @RequestBody Weather weather) throws URISyntaxException {
        log.debug("REST request to save Weather : {}", weather);
        if (weather.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new weather cannot already have an ID")).body(null);
        }
        Weather result = weatherRepository.save(weather);
        return ResponseEntity.created(new URI("/api/weathers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /weathers : Updates an existing weather.
     *
     * @param weather the weather to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated weather,
     * or with status 400 (Bad Request) if the weather is not valid,
     * or with status 500 (Internal Server Error) if the weather couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/weathers")
    @Timed
    public ResponseEntity<Weather> updateWeather(@Valid @RequestBody Weather weather) throws URISyntaxException {
        log.debug("REST request to update Weather : {}", weather);
        if (weather.getId() == null) {
            return createWeather(weather);
        }
        Weather result = weatherRepository.save(weather);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, weather.getId().toString()))
            .body(result);
    }

    /**
     * GET  /weathers : get all the weathers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of weathers in body
     */
    @GetMapping("/weathers")
    @Timed
    public List<Weather> getAllWeathers() {
        log.debug("REST request to get all Weathers");
        return weatherRepository.findAll();
        }


    /**
     * GET  /weathers/:id : get the "id" weather.
     *
     * @param id the id of the weather to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the weather, or with status 404 (Not Found)
     */
    @GetMapping("/weathers/{id}")
    @Timed
    public ResponseEntity<Weather> getWeather(@PathVariable Long id) {
        log.debug("REST request to get Weather : {}", id);
        Weather weather = weatherRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(weather));
    }

    /**
     * DELETE  /weathers/:id : delete the "id" weather.
     *
     * @param id the id of the weather to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/weathers/{id}")
    @Timed
    public ResponseEntity<Void> deleteWeather(@PathVariable Long id) {
        log.debug("REST request to delete Weather : {}", id);
        weatherRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    
    @GetMapping("/weekend")
    @Timed
    public  List<WeekendInfo> getWeekend(Principal principal) {
        log.debug("\nGET WEEKEND INFO !\n");
        
        List<Object[]> myInfos = weatherRepository.getWeekendNative();
        List<WeekendInfo> myWeekends = new ArrayList<WeekendInfo>();
        String ret = "";
        for(Object[] obj : myInfos) {
        	WeekendInfo weekend = new WeekendInfo((String) obj[0], (String) obj[1], (String) obj[2], 
        									  (Double) obj[3], (Double) obj[4], (Double) obj[5]);
        	myWeekends.add(weekend);
        }
        return myWeekends;
   }

    @GetMapping("/updateWeather")
    @Timed
   public void updateWeather() {
        log.debug("\nREST Consumer to OpenWeatherMap\n");

    	List<Ville> listVille = villeRepository.findAll();
    	List<Weather> listWeather = weatherRepository.findAll();
	   for(Ville villeCurrent : listVille) {
		   String villeName = villeCurrent.getName();
		   Meteo newmeteo = new Meteo();
		   newmeteo.setCelsiusAverage(RestConsumer.getMoyenTemp(RestConsumer.restWeather(villeName+", FR")));
		   String newMeteoWeather = RestConsumer.getMeteo(RestConsumer.restWeather(villeName+", FR"));
		   newMeteoWeather = newMeteoWeather.toUpperCase().replace(" ", "_");
		   for(Weather weather : listWeather) {
		        log.debug("\nCompare : ["+weather.getName()+"] == ["+newMeteoWeather+"] \n");
			   if(weather.getName().equals(newMeteoWeather)) {
				   newmeteo.setWeather(weather);
				   break;
			   }
		   }
		   
		   newmeteo.setUpdated(LocalDate.now());
		   newmeteo.setVille(villeCurrent);
		   villeCurrent.setMeteo(newmeteo);
		   meteoRepository.save(newmeteo);
		   villeRepository.save(villeCurrent);
		   
	   }
   }
   
}
