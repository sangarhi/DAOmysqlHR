package com.proyecto;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.proyecto.dao.DireccionDAO;
import com.proyecto.dao.exceptions.DAOException;
import com.proyecto.dao.interfaces.IDAO;
import com.proyecto.data.Direccion;
import com.proyecto.data.Pais;
import com.proyecto.data.Region;

public class AppDireccion {
	
	private static final Logger log = Logger.getLogger(App.class);

	public static void main(String[] args) throws DAOException {
		IDAO<Direccion, Long> dao = new DireccionDAO();
		List<Direccion> direcciones= new ArrayList<Direccion>();
		try {
			direcciones = dao.listar();
		} catch (DAOException e) {
			log.error(e.getTipoExcepcion().getMensaje());
		}
		direcciones.forEach(System.out::println);
		System.out.println("--------------------");
		System.out.println(dao.buscar(2000l));
		System.out.println("--------------------");
		Direccion direccion = new Direccion();
		direccion.setId(3900l);
		direccion.setCalle("MiCalle");
		direccion.setCodPostal("00000");;
		direccion.setCiudad("MiCiudad");
		direccion.setEstado("MiEstado");
		
		//Region region = new Region();
		//region.setId(1);
		
		Pais pais = new Pais();
		pais.setPaisId("AR");
		//pais.setRegion(region);
		direccion.setPais(pais);
		System.out.println("Creación de la direccion");
		dao.create(direccion);
		

		try {
			direcciones = dao.listar();
		} catch (DAOException e) {
			log.error(e.getTipoExcepcion().getMensaje());
		}
		direcciones.forEach(System.out::println);
		System.out.println("---------------------");
		System.out.println("Actualización de la direccion");
		direccion.setCalle("Mi otra calle");
		dao.modificar(direccion);	
		try {
			direcciones = dao.listar();
		} catch (DAOException e) {
			log.error(e.getTipoExcepcion().getMensaje());
		}
		direcciones.forEach(System.out::println);
		System.out.println("Borramos la direccion");
		dao.borrar(3900l);
		try {
			direcciones = dao.listar();
		} catch (DAOException e) {
			log.error(e.getTipoExcepcion().getMensaje());
		}
		direcciones.forEach(System.out::println);
	}

}
