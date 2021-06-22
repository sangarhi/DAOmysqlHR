package com.proyecto.dao.interfaces;

import java.util.List;

import com.proyecto.dao.exceptions.DAOException;

public interface IDAO<T,K> {
	
		public List<T> listar() throws DAOException;
		
		public T buscar(K id) throws DAOException;
	
		public void create(T element) throws DAOException;
		
		public void modificar(T element) throws DAOException;
		
		public void borrar(K id) throws DAOException;
		
}
