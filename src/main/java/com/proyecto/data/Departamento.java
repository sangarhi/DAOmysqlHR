package com.proyecto.data;

/**
 * Bean Departamento
 * 
 * @author MARIA
 *
 */
public class Departamento {
	private Long id;
	private String nombre;
	private Long idManager;
	private Direccion direccion;

	public Departamento() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Long getIdManager() {
		return idManager;
	}

	public void setIdManager(Long idManager) {
		this.idManager = idManager;
	}

	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}

	@Override
	public String toString() {
		return "Departamento [id=" + id + ", nombre=" + nombre + ", idManager=" + idManager + ", direccion=" + direccion
				+ "]";
	}

}
