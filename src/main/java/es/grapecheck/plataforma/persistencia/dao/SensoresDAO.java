package es.grapecheck.plataforma.persistencia.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.inject.Named;

import es.grapecheck.plataforma.persistencia.vo.ParcelasVO;
import es.grapecheck.plataforma.persistencia.vo.SensoresVO;
import es.grapecheck.plataforma.persistencia.vo.TipoSensoresVO;
import es.grapecheck.plataforma.utiles.TratamientoDeDatos;

/**
 * Consultas realcionadas con los sensores contenidos en una determinada parcela.
 * Plataforma 'GrapeCheck'
 * @author Alejandro Visiga Hernández.
 * @since 29/01/2017
 */
@Named
@LocalBean
public class SensoresDAO extends GenericDAOImpl<SensoresVO> {
	
	/**
	 * Metodo para listar todos los sensores contenidos en una parcela.
	 * @param parcelaVO -> Parcela donde se encuentran los sensores.
	 * @return una lista con todos los sensores contenidos en la parcela.
	 */
	public List<SensoresVO> findSensoresByParcela(ParcelasVO parcelaVO){
		
		List<SensoresVO> lista = (List<SensoresVO>) listarHQL("select p from SensoresVO p where p.fkIdeParcela = " + parcelaVO.getId());
		return lista;	
		
	}
	
	public List<SensoresVO> getAllSensoresFiltros (SensoresVO sensoresVO){
		
		String query = "select p from SensoresVO p where fechaBaja is null";
		
		if(!TratamientoDeDatos.esNullCeroVacio(sensoresVO.getFkIdeParcela())){
			query += " and p.fkIdeParcela = '" + sensoresVO.getFkIdeParcela().getId() + "'";
		}
		
		if(!TratamientoDeDatos.esNullCeroVacio(sensoresVO.getFkIdeTipoSensor())){
			query += " and p.fkIdeTipoSensor = " + sensoresVO.getFkIdeTipoSensor().getId();
		}
		
		List<SensoresVO> listaSensores = (List<SensoresVO>) listarHQL(query);
		//List<PerfilesVO> listaPerfiles = (List<PerfilesVO>) listarNatural("select * from com_perfiles where id=" + id);
		
		return listaSensores;
	}
}
