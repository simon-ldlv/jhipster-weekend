package istic.weekend.web.rest;

public class UpdateWeatherOutput {
	
	private Integer nbError;
	private String libelle;
	
	public UpdateWeatherOutput() {
		
	}

	public UpdateWeatherOutput(Integer nbError, String libelle) {
		this.nbError = nbError;
		this.libelle = libelle;
	}
	
	public Integer getNbError() {
		return nbError;
	}

	public void setNbError(Integer nbError) {
		this.nbError = nbError;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}



	
	
	
}
