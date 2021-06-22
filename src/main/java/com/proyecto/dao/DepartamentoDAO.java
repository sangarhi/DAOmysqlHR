package com.proyecto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.proyecto.dao.exceptions.DAOException;
import com.proyecto.dao.exceptions.TipoException;
import com.proyecto.dao.interfaces.IDAO;
import com.proyecto.data.Departamento;
import com.proyecto.data.Direccion;
import com.proyecto.data.Pais;
import com.proyecto.data.Region;

public class DepartamentoDAO implements IDAO<Departamento, Long> {
	
	private static final Logger log = Logger.getLogger(DepartamentoDAO.class);
	
	DriverManagerMySQL driverManagerMySQL;
	
	public DepartamentoDAO() {
		this.driverManagerMySQL = DriverManagerMySQL.getDriverManagerMySQL();
	}
	
	public List<Departamento> listar() throws DAOException {
		log.debug("listar");

		// Bloque de inicialización de variables locales
		List<Departamento> departamentos = new ArrayList<Departamento>();
		
		// Bloque try-catch, el último bloque catch recogerá Exception
		try (Connection con = driverManagerMySQL.getConexion();
			 Statement st = con.createStatement();)
		{
			String query = "SELECT DEPARTMENT_ID,DEPARTMENT_NAME,LOCATION_ID,MANAGER_ID "
						 + "FROM DEPARTMENTS";
			ResultSet rs = st.executeQuery(query);
			while( rs.next()) {
				Departamento departamento = new Departamento();
				departamento.setId(rs.getLong("DEPARTMENT_ID"));
				departamento.setNombre(rs.getString("DEPARTMENT_NAME"));
				if (rs.getObject("LOCATION_ID") != null) {
					departamento.setDireccion(new Direccion());
					departamento.getDireccion().setId(rs.getLong("LOCATION_ID"));
				}
					
				if (rs.getObject("MANAGER_ID") != null)
					departamento.setIdManager(rs.getLong("MANAGER_ID"));
				
				departamentos.add(departamento);				
			}
			rs.close();
		
			return departamentos;
			
		} catch (SQLException sqle) {
			log.error(sqle.getMessage(), sqle);
			throw new DAOException(TipoException.EXCEPCION_SQL);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new DAOException(TipoException.EXCEPCION_GENERAL);
		}
	}

	@Override
	public Departamento buscar(Long id) throws DAOException {
		
		log.debug("buscar");
		Departamento departamento = null;
		String query = "SELECT D.DEPARTMENT_ID,D.DEPARTMENT_NAME,L.LOCATION_ID,D.MANAGER_ID,"
				+ " L.STREET_ADDRESS,L.POSTAL_CODE, L.CITY,L.STATE_PROVINCE, L.COUNTRY_ID,"
				+ " C.COUNTRY_NAME, R.REGION_ID, R.REGION_NAME FROM DEPARTMENTS D"
				+ " NATURAL JOIN LOCATIONS L NATURAL JOIN COUNTRIES C NATURAL JOIN REGIONS R WHERE DEPARTMENT_ID=?";
		
		try(Connection con = driverManagerMySQL.getConexion();
			PreparedStatement ps = con.prepareStatement(query)) 
		{
			ps.setLong(1,id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				departamento = new Departamento();
				departamento.setId(rs.getLong("DEPARTMENT_ID"));
				departamento.setNombre(rs.getString("DEPARTMENT_NAME"));
				Direccion direccion = new Direccion();
				if (rs.getObject("LOCATION_ID") != null)
					direccion.setId(rs.getLong("LOCATION_ID"));
				if (rs.getObject("MANAGER_ID") != null)
					departamento.setIdManager(rs.getLong("MANAGER_ID"));
				direccion.setCalle(rs.getString("STREET_ADDRESS"));
				direccion.setCodPostal(rs.getString("POSTAL_CODE"));
				direccion.setCiudad(rs.getString("CITY"));
				direccion.setEstado(rs.getString("STATE_PROVINCE"));
				Pais pais = new Pais();
				pais.setPaisId(rs.getString("COUNTRY_ID"));
				pais.setNombre(rs.getString("COUNTRY_NAME"));
				Region region = new Region();
				region.setId(rs.getInt("REGION_ID"));
				region.setNombre(rs.getString("REGION_NAME"));
				pais.setRegion(region);
				direccion.setPais(pais);
				departamento.setDireccion(direccion);
			} else {
				throw new DAOException(TipoException.ELEMENTO_NO_ENCONTRADO);
			}
			if (rs.next()) {
				throw new DAOException(TipoException.ELEMENTO_DUPLICADO);
			}
			rs.close();
			return departamento;
		} catch (SQLException sqle) {
			log.error(sqle.getMessage(), sqle);
			throw new DAOException(TipoException.EXCEPCION_SQL);
		} catch (DAOException daoe){
			if (daoe.getTipoExcepcion() == TipoException.ELEMENTO_DUPLICADO) {
				log.fatal(daoe.getMessage(),daoe);
			} else {
				log.error(daoe.getMessage(), daoe);
			}
			throw daoe;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new DAOException(TipoException.EXCEPCION_GENERAL);
		}
	}

	@Override
	public void create(Departamento element) throws DAOException {
		log.debug("create");
		
		String query = "INSERT INTO DEPARTMENTS (DEPARTMENT_ID,DEPARTMENT_NAME,LOCATION_ID,MANAGER_ID) VALUES(?,?,?,?)";
		
		try(Connection con = driverManagerMySQL.getConexion();
				PreparedStatement ps = con.prepareStatement(query)) 
			{
			ps.setLong(1, element.getId());
			ps.setString(2, element.getNombre());
			ps.setLong(3, element.getDireccion().getId());
			if (element.getIdManager() != null) {
				ps.setLong(4, element.getIdManager());
			} else {
				ps.setNull(4, Types.INTEGER);;
			}
			
			int numFilas = ps.executeUpdate();
			
			if (numFilas == 0) {
				throw new DAOException(TipoException.ELEMENTO_NO_CREADO);
			} else if (numFilas > 1) {
				throw new DAOException(TipoException.ELEMENTO_DUPLICADO);
			}
			
			}catch (SQLException sqle) {
				log.error(sqle.getMessage(), sqle);
				throw new DAOException(TipoException.EXCEPCION_SQL);
			} catch (DAOException daoe){
				if (daoe.getTipoExcepcion() == TipoException.ELEMENTO_DUPLICADO) {
					log.fatal(daoe.getMessage(), daoe);
				} else {
					log.error(daoe.getMessage(), daoe);
				}
				throw daoe;
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				throw new DAOException(TipoException.EXCEPCION_GENERAL);
			}
	}

	@Override
	public void modificar(Departamento element) throws DAOException {
		log.debug("modificar");
		
		String query = "UPDATE DEPARTMENTS SET DEPARTMENT_NAME=?, LOCATION_ID=?, MANAGER_ID=? WHERE DEPARTMENT_ID=?";
		
		try(Connection con = driverManagerMySQL.getConexion();
				PreparedStatement ps = con.prepareStatement(query)) 
			{			
			ps.setString(1, element.getNombre());
			ps.setLong(2, element.getDireccion().getId());
			if (element.getIdManager() != null) {
				ps.setLong(3, element.getIdManager());
			} else {
				ps.setNull(3, Types.INTEGER);;
			}
			ps.setLong(4, element.getId());
			
			int numFilas = ps.executeUpdate();
			
			if (numFilas == 0) {
				throw new DAOException(TipoException.ELEMENTO_NO_ACTUALIZADO);
			} else if (numFilas > 1) {
				throw new DAOException(TipoException.ELEMENTO_DUPLICADO);
			}
			
			}catch (SQLException sqle) {
				log.error(sqle.getMessage(), sqle);
				throw new DAOException(TipoException.EXCEPCION_SQL);
			} catch (DAOException daoe){
				if (daoe.getTipoExcepcion() == TipoException.ELEMENTO_DUPLICADO) {
					log.fatal(daoe.getMessage(), daoe);
				} else {
					log.error(daoe.getMessage(), daoe);
				}
				throw daoe;
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				throw new DAOException(TipoException.EXCEPCION_GENERAL);
			}
		
	}

	@Override
	public void borrar(Long id) throws DAOException {
		log.debug("borrar");
		
		String query = "DELETE FROM DEPARTMENTS WHERE DEPARTMENT_ID=?";
		
		try(Connection con = driverManagerMySQL.getConexion();
				PreparedStatement ps = con.prepareStatement(query)) 
			{
			ps.setLong(1, id);
			
			int numFilas = ps.executeUpdate();
			
			if (numFilas == 0) {
				throw new DAOException(TipoException.ELEMENTO_NO_ELIMINADO);
			} else if (numFilas > 1) {
				throw new DAOException(TipoException.EXCEPCION_DAO);
			}
			
			}catch (SQLException sqle) {
				log.error(sqle.getMessage(), sqle);
				throw new DAOException(TipoException.EXCEPCION_SQL);
			} catch (DAOException daoe){
				if (daoe.getTipoExcepcion() == TipoException.EXCEPCION_DAO) {
					log.fatal(daoe.getMessage(), daoe);
				} else {
					log.error(daoe.getMessage(), daoe);
				}
				throw daoe;
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				throw new DAOException(TipoException.EXCEPCION_GENERAL);
			}
		
	}

}
