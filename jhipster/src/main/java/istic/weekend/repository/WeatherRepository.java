package istic.weekend.repository;

import istic.weekend.domain.Weather;
import istic.weekend.domain.WeekendInfo;

import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;

import javax.persistence.NamedNativeQuery;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;


/**
 * Spring Data JPA repository for the Weather entity.
*/
@SuppressWarnings("unused")
@Repository
public interface WeatherRepository extends JpaRepository<Weather, Long> {

	
	@Query(nativeQuery=true,name="getWeekendQuery",value=
			"SELECT ville.name AS villeName, activite.name AS activiteName, weather.name AS weatherName"+
			    ", activite.celsius_min AS celsiusMin, activite.celsius_max AS celsiusMax"+
				", meteo.celsius_average AS celsiusAverage" + 
			" FROM ville, activite, ville_activites, activite_weathers, weather, meteo, "+
				"pratique, jhi_user, ville_preferee" + 
			" WHERE ville.id=ville_activites.villes_id" + 
			" AND ville_activites.activites_id = activite.id" + 
			" AND activite_weathers.weathers_id = weather.id" + 
			" AND activite_weathers.activites_id=activite.id" + 
			" AND ville.meteo_id = meteo.id" + 
			" AND meteo.weather_id = weather.id" + 
			" AND activite.id = pratique.activite_id" + 
			" AND pratique.owner_id = jhi_user.id" + 
			" AND ville_preferee.owner_id = jhi_user.id" + 
			" AND ville_preferee.ville_id = ville.id" + 
			" AND jhi_user.login=?#{principal.username}" + 
			" AND activite.celsius_min<=meteo.celsius_average" + 
			" AND activite.celsius_max>=meteo.celsius_average")
	List<Object[]> getWeekendNative ();

}
