package es.grapecheck.plataforma.negocio.service;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.grapecheck.plataforma.persistencia.dao.ActivadoresDAO;
import es.grapecheck.plataforma.persistencia.exception.PersistenciaException;
import es.grapecheck.plataforma.persistencia.vo.ActivadoresVO;
import es.grapecheck.plataforma.persistencia.vo.ParcelasVO;
import es.grapecheck.plataforma.persistencia.vo.SensoresVO;

@Stateless
@LocalBean
public class ActivadoresService {
	
	private static final Logger LOG = LoggerFactory.getLogger(SensoresService.class);
	
	@Inject
	private ActivadoresDAO activadoresDAO;

	public ActivadoresDAO getActivadoresDAO() {
		return activadoresDAO;
	}

	public void setActivadoresDAO(ActivadoresDAO activadoresDAO) {
		this.activadoresDAO = activadoresDAO;
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void addActivadores(ActivadoresVO activadoresVO) throws PersistenciaException {
		getActivadoresDAO().create(activadoresVO);
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<ActivadoresVO> getActivadoresByIdParcela(ParcelasVO parcela) throws PersistenciaException {
		return getActivadoresDAO().getActivadoresByIdParcela(parcela);
	}
	

}
