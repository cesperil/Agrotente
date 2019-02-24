package es.grapecheck.plataforma.persistencia.dao;

import java.util.List;

import es.grapecheck.plataforma.persistencia.vo.TipoSensoresVO;
import es.grapecheck.plataforma.persistencia.vo.TrabajosRealizadosVO;
import es.grapecheck.plataforma.utiles.TratamientoDeDatos;

public class TrabajoRealizadoDAO extends GenericDAOImpl<TrabajosRealizadosVO> {

	public List<TrabajosRealizadosVO> getTrabajos(Integer idUsuario){
		
		List<TrabajosRealizadosVO> listaTrabajosRealizados = 
				(List<TrabajosRealizadosVO>) listarHQL("select tr from TrabajosRealizadosVO tr inner join tr.fkParcela p WHERE tr.fkParcela = p.id and  p.fkIdeUsuario = " + idUsuario);
		//SELECT * FROM `com_trabajos_realizados` tr inner join com_parcelas p on tr.fk_parcela = p.id and p.fk_ide_usuario = 1

		return listaTrabajosRealizados;
	}
	
}
