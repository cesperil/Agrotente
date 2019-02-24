
package es.grapecheck.plataforma.persistencia.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.inject.Named;

import es.grapecheck.plataforma.persistencia.vo.TipoSensoresVO;
import es.grapecheck.plataforma.utiles.TratamientoDeDatos;

/**
 * 
 * @author Alejandro
 *
 */
@Named
@LocalBean
public class TipoSensoresDAO extends GenericDAOImpl<TipoSensoresVO> {
	
public TipoSensoresVO getTipoSensorByIde(Integer id){
		
		TipoSensoresVO sensores = new TipoSensoresVO();
		List<TipoSensoresVO> listaSensores = (List<TipoSensoresVO>) listarHQL("select p from TipoSensoresVO p where p.id=" + id);
		//List<PerfilesVO> listaPerfiles = (List<PerfilesVO>) listarNatural("select * from com_perfiles where id=" + id);
		if(TratamientoDeDatos.esListaConElementos(listaSensores)){
			sensores =  listaSensores.get(0);
		}
		return sensores;
	}



public List<TipoSensoresVO> getAllTipoSensoresFiltros (TipoSensoresVO tSensoresVO){
	
	String query = "select p from TipoSensoresVO p where fechaBaja is null";
	
	if(!TratamientoDeDatos.esNullCeroVacio(tSensoresVO.getNombre())){
		query += " and p.nombre = '" + tSensoresVO.getNombre() + "'";
	}
	
	if(!TratamientoDeDatos.esNullCeroVacio(tSensoresVO.getDescripcion())){
		query += " and p.descripcion = " + tSensoresVO.getDescripcion();
	}
	
	List<TipoSensoresVO> listaSensores = (List<TipoSensoresVO>) listarHQL(query);
	//List<PerfilesVO> listaPerfiles = (List<PerfilesVO>) listarNatural("select * from com_perfiles where id=" + id);
	
	return listaSensores;
}


}
