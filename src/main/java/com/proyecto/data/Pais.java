package com.proyecto.data;

public class Pais {
	private String paisId;
	private String nombre;
	private Region region;
	
	
	public Pais() {
		super();
	}

	

	public String getPaisId() {
		return paisId;
	}

	public void setPaisId(String paisId) {
		this.paisId = paisId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Region getRegion() {
		return region;
	}



	public void setRegion(Region region) {
		this.region = region;
	}



	@Override
	public String toString() {
		return "Pais [paisId=" + paisId + ", nombre=" + nombre + ", region=" + region + "]";
	}




}
