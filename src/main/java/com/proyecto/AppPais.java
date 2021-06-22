package com.proyecto;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.proyecto.dao.PaisDAO;
import com.proyecto.dao.exceptions.DAOException;
import com.proyecto.dao.interfaces.IDAO;
import com.proyecto.data.Pais;
import com.proyecto.data.Region;

public class AppPais {

	private static final Logger log = Logger.getLogger(App.class);
	
	public static void main(String[] args) throws DAOException {
		IDAO<Pais, String> dao = new PaisDAO();
		List<Pais> paises= new ArrayList<Pais>();
		try {
			paises = dao.listar();
		} catch (DAOException e) {
			log.error(e.getTipoExcepcion().getMensaje());
		}
		paises.forEach(System.out::println);
		System.out.println("--------------------");
		System.out.println(dao.buscar("BR"));
		System.out.println("--------------------");
		Pais pais = new Pais();
		pais.setPaisId("ES");
		pais.setNombre("España");
		
		Region region = new Region();
		region.setId(1);
		
		pais.setRegion(region);
		System.out.println("Creación del país");
		dao.create(pais);
		

		try {
			paises = dao.listar();
		} catch (DAOException e) {
			log.error(e.getTipoExcepcion().getMensaje());
		}
		paises.forEach(System.out::println);
		System.out.println("---------------------");
		System.out.println("Actualización de un país");
		pais.setNombre("Espanya");
		dao.modificar(pais);	
		try {
			paises = dao.listar();
		} catch (DAOException e) {
			log.error(e.getTipoExcepcion().getMensaje());
		}
		paises.forEach(System.out::println);
		System.out.println("Borramos el pais");
		dao.borrar("ES");
		try {
			paises = dao.listar();
		} catch (DAOException e) {
			log.error(e.getTipoExcepcion().getMensaje());
		}
		paises.forEach(System.out::println);
	}
}
