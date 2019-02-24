package es.grapecheck.plataforma.persistencia.dao;

import java.util.List;

import es.grapecheck.plataforma.persistencia.vo.TrabajadorVO;

public class TrabajadorDAO extends GenericDAOImpl<TrabajadorVO> {

	
	public List<TrabajadorVO> getListaTrabajadores(){
		
		List<TrabajadorVO> listaTrabajadores = (List<TrabajadorVO>) listarHQL("Select t from TrabajadorVO t WHERE t.fechaBaja is null");
		return listaTrabajadores;
	}
	
}
