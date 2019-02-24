package es.grapecheck.plataforma.persistencia.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.inject.Named;

import es.grapecheck.plataforma.persistencia.vo.ActivadoresVO;
import es.grapecheck.plataforma.persistencia.vo.ParcelasVO;
import es.grapecheck.plataforma.persistencia.vo.SensoresVO;

@Named
@LocalBean
public class ActivadoresDAO extends GenericDAOImpl<ActivadoresVO>{

	public List<ActivadoresVO> getActivadoresByIdParcela(ParcelasVO parcelaVO){
		List<ActivadoresVO> lista = (List<ActivadoresVO>) listarHQL("select a from ActivadoresVO a where a.fkIdeParcela = " + parcelaVO.getId());
		return lista;
	}
	
}
