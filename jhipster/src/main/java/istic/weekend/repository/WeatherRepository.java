package istic.weekend.repository;

import istic.weekend.domain.Weather;
import istic.weekend.domain.WeekendInfo;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;


/**
 * Spring Data JPA repository for the Weather entity.
*/
@SuppressWarnings("unused")
@Repository
public interface WeatherRepository extends JpaRepository<Weather, Long> {

	@Query("SELECT NEW istic.weekend.domain.WeekendInfo("+
			"ville.name, activite.name, weather.name, activite.celsiusMin, activite.celsiusMax, meteo.celsiusAverage) "+
			"FROM Ville AS ville, Activite AS activite, Weather AS weather, Meteo AS meteo,Pratique as pratique, User AS user "+
			"WHERE user.login = :login "+
    		"AND activite.celsiusMin<=meteo.celsiusAverage " + 
    		"AND activite.celsiusMax>=meteo.celsiusAverage")
	List<WeekendInfo> getWeekend(@Param("login")String login);
	
}
