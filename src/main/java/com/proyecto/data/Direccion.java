package com.proyecto.data;

public class Direccion {
	
	private Long id;
	private String calle;
	private String codPostal;
	private String ciudad;
	private String estado;
	private Pais pais;
	
	public Direccion() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getCodPostal() {
		return codPostal;
	}

	public void setCodPostal(String codPostal) {
		this.codPostal = codPostal;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	@Override
	public String toString() {
		return "Direccion [id=" + id + ", calle=" + calle + ", codPostal=" + codPostal + ", ciudad=" + ciudad
				+ ", estado=" + estado + ", pais=" + pais + "]";
	}

	
	

}
