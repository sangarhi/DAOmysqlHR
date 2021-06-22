package com.proyecto;


import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.proyecto.dao.DepartamentoDAO;
import com.proyecto.dao.exceptions.DAOException;
import com.proyecto.dao.interfaces.IDAO;
import com.proyecto.data.Departamento;
import com.proyecto.data.Direccion;

public class App {
	
	private static final Logger log = Logger.getLogger(App.class);

	public static void main(String[] args) throws DAOException {
		IDAO<Departamento, Long> dao = new DepartamentoDAO();
		List<Departamento> departamentos= new ArrayList<Departamento>();
		try {
			departamentos = dao.listar();
		} catch (DAOException e) {
			log.error(e.getTipoExcepcion().getMensaje());
		}
		departamentos.forEach(System.out::println);
		System.out.println("--------------------");
		System.out.println(dao.buscar(200l));
		System.out.println("--------------------");
		Departamento departamento = new Departamento();
		departamento.setId(280l);
		departamento.setNombre("MiDepartamento");
		
		Direccion direccion = new Direccion();
		direccion.setId(1700l);
		//departamento.setIdDireccion(1700l);
		departamento.setDireccion(direccion);
		System.out.println("Creación del departamento");
		dao.create(departamento);
		

		try {
			departamentos = dao.listar();
		} catch (DAOException e) {
			log.error(e.getTipoExcepcion().getMensaje());
		}
		departamentos.forEach(System.out::println);
		System.out.println("---------------------");
		System.out.println("Actualización del departamento");
		departamento.setNombre("Nuevo nombre");
		dao.modificar(departamento);	
		try {
			departamentos = dao.listar();
		} catch (DAOException e) {
			log.error(e.getTipoExcepcion().getMensaje());
		}
		departamentos.forEach(System.out::println);
		System.out.println("Borramos el departamento");
		dao.borrar(280l);
		try {
			departamentos = dao.listar();
		} catch (DAOException e) {
			log.error(e.getTipoExcepcion().getMensaje());
		}
		departamentos.forEach(System.out::println);
	}

}
