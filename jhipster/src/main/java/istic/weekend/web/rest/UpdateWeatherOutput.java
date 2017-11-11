package istic.weekend.web.rest;

public class UpdateWeatherOutput {
	
	private Integer code;
	private String libelle;
	
	public UpdateWeatherOutput() {
		
	}

	public UpdateWeatherOutput(Integer code, String libelle) {
		this.code = code;
		this.libelle = libelle;
	}
	
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}



	
	
	
}
