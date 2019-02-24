package es.grapecheck.plataforma.persistencia.dao;

import java.util.List;

import es.grapecheck.plataforma.persistencia.exception.PersistenciaException;
import es.grapecheck.plataforma.persistencia.vo.MedicionesMinMaxTempVO;
import es.grapecheck.plataforma.persistencia.vo.MedicionesMinMaxVO;
import es.grapecheck.plataforma.persistencia.vo.MedicionesVO;
import es.grapecheck.plataforma.persistencia.vo.ParcelasVO;
import es.grapecheck.plataforma.persistencia.vo.SensoresVO;

public class MedicionesMaxMinDAO extends GenericDAOImpl<MedicionesMinMaxVO> {

	/**
	 * Metodo para listar todos las mediciones realizadas por un sensor.
	 * @param sensoresVO Sensor que ha realizado todas las mediciones.
	 * @return Lista que contiene las mediciones.
	 */
	public List<MedicionesMinMaxVO> getMedicionesUltimaSemanaBySensor(SensoresVO sensoresVO){
		
		
		List<MedicionesMinMaxVO> listaMediciones = (List<MedicionesMinMaxVO>) listarHQL("select p "
				+ " from MedicionesMinMaxVO p where p.fkIdeParcela = " + sensoresVO.getId());
		return listaMediciones;
	}
	
	/**
	 * Metodo para listar todos las mediciones realizadas por un sensor.
	 * @param sensoresVO Sensor que ha realizado todas las mediciones.
	 * @return Lista que contiene las mediciones.
	 * @throws PersistenciaException 
	 */
	public List<MedicionesMinMaxTempVO> getMedicionesTemperaturaByIdParcela(ParcelasVO parcelasVO) throws PersistenciaException{
		/*
		 * SELECT com_med_max_min.id, com_med_max_min.min, com_med_max_min.max, com_med_max_min.fecha_dia FROM com_med_max_min 
 INNER join com_sensor on com_sensor.id = com_med_max_min.fk_ide_sensor and com_sensor.fk_ide_parcela = 1 
		 */
		
		
		List<MedicionesMinMaxTempVO> listaMediciones = (List<MedicionesMinMaxTempVO>) listarHQL("select p "
				+ " from MedicionesMinMaxTempVO p where p.fkIdeParcela = " + parcelasVO.getId()); 
		
		return listaMediciones;
	}
	
	
}
