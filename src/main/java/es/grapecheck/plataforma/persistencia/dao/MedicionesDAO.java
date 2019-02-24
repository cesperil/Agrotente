/**
 * 
 */
package es.grapecheck.plataforma.persistencia.dao;

import javax.ejb.LocalBean;
import javax.inject.Named;

import java.util.List;
import es.grapecheck.plataforma.persistencia.vo.MedicionesVO;
import es.grapecheck.plataforma.persistencia.vo.SensoresVO;

/**
 * Consultas realcionadas con las mediciones de un determinado sensor.
 * Plataforma 'GrapeCheck'
 * @author Alejandro Visiga Hernández
 * @since 29/01/2017
 */
@Named
@LocalBean
public class MedicionesDAO extends GenericDAOImpl<MedicionesVO> {

	/**
	 * Metodo para listar todos las mediciones realizadas por un sensor.
	 * @param sensoresVO Sensor que ha realizado todas las mediciones.
	 * @return Lista que contiene las mediciones.
	 */
	public List<MedicionesVO> getMedicionesBySensor(SensoresVO sensoresVO){
		
		List<MedicionesVO> listaMediciones = (List<MedicionesVO>) listarHQL("select p from MedicionesVO p where p.fkIdeParcela = " + sensoresVO.getId());
		return listaMediciones;
	}
	
}
