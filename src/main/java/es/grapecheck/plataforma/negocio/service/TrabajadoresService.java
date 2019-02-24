package es.grapecheck.plataforma.negocio.service;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.fest.assertions.ListAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.grapecheck.plataforma.persistencia.dao.TrabajadorDAO;
import es.grapecheck.plataforma.persistencia.dao.TrabajoRealizadoDAO;
import es.grapecheck.plataforma.persistencia.exception.PersistenciaException;
import es.grapecheck.plataforma.persistencia.vo.TrabajadorVO;
import es.grapecheck.plataforma.utiles.TratamientoDeDatos;
import es.grapecheck.plataforma.utiles.TratamientoFechas;

@Stateless
@LocalBean
public class TrabajadoresService {

	private static final Logger LOG = LoggerFactory.getLogger(TrabajadoresService.class);
	
	@Inject
	private TrabajadorDAO trabajadorDAO;

	public TrabajadorDAO getTrabajadorDAO() {
		return trabajadorDAO;
	}

	public void setTrabajadorDAO(TrabajadorDAO trabajadorDAO) {
		this.trabajadorDAO = trabajadorDAO;
	}

	public void altaTrabajador(TrabajadorVO trabajadorVO) throws PersistenciaException{
		getTrabajadorDAO().create(trabajadorVO);
	}
	
	public List<TrabajadorVO> getTrabajadores() throws PersistenciaException{
		List<TrabajadorVO> listaTrabajores = getTrabajadorDAO().getListaTrabajadores();
		
		for(int i = 0; i< listaTrabajores.size(); i++){
			//TrabajadorVO trabajador = listaTrabajores.get(i);
			if(!TratamientoDeDatos.esNullCeroVacio(listaTrabajores.get(i)) && !TratamientoDeDatos.esNullCeroVacio(listaTrabajores.get(i).getFechaNacimiento())){
				listaTrabajores.get(i).setTextoFecha(listaTrabajores.get(i).getFechaNacimiento().toString());
			}
		}
		return listaTrabajores;
	}
	
}
