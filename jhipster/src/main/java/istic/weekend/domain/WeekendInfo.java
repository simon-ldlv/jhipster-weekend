package istic.weekend.domain;

public class WeekendInfo {
	
	private String villeName;
	private String activiteName;
	private String weatherName;
	private Double celsiusMin;
	private Double celsiusMax;
	private Double celsiusAverage;
	
	
	
	public WeekendInfo(String villeName, String activiteName, String weatherName, Double celsiusMin, Double celsiusMax,
			Double celsiusAverage) {
		super();
		this.villeName = villeName;
		this.activiteName = activiteName;
		this.weatherName = weatherName;
		this.celsiusMin = celsiusMin;
		this.celsiusMax = celsiusMax;
		this.celsiusAverage = celsiusAverage;
	}
	
	
	public String getVilleName() {
		return villeName;
	}
	public void setVilleName(String villeName) {
		this.villeName = villeName;
	}
	public String getActiviteName() {
		return activiteName;
	}
	public void setActiviteName(String activiteName) {
		this.activiteName = activiteName;
	}
	public String getWeatherName() {
		return weatherName;
	}
	public void setWeatherName(String weatherName) {
		this.weatherName = weatherName;
	}
	public Double getCelsiusMin() {
		return celsiusMin;
	}
	public void setCelsiusMin(Double celsiusMin) {
		this.celsiusMin = celsiusMin;
	}
	public Double getCelsiusMax() {
		return celsiusMax;
	}
	public void setCelsiusMax(Double celsiusMax) {
		this.celsiusMax = celsiusMax;
	}
	public Double getCelsiusAverage() {
		return celsiusAverage;
	}
	public void setCelsiusAverage(Double celsiusAverage) {
		this.celsiusAverage = celsiusAverage;
	}
	
	

}
