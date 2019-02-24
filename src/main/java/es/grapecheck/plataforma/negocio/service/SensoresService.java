package es.grapecheck.plataforma.negocio.service;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.grapecheck.plataforma.persistencia.dao.SensoresDAO;
import es.grapecheck.plataforma.persistencia.exception.PersistenciaException;
import es.grapecheck.plataforma.persistencia.vo.ParcelasVO;
import es.grapecheck.plataforma.persistencia.vo.SensoresVO;
import es.grapecheck.plataforma.persistencia.vo.TipoSensoresVO;

/**
 * 
 * @author Alejandro
 *
 */
@Stateless
@LocalBean
public class SensoresService {
	
	private static final Logger LOG = LoggerFactory.getLogger(SensoresService.class);
	
	@Inject
	private SensoresDAO sensoresDAO;

	public SensoresDAO getSensoresDAO() {
		return sensoresDAO;
	}

	public void setSensoresDAO(SensoresDAO sensoresDAO) {
		this.sensoresDAO = sensoresDAO;
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void addSensores(SensoresVO sensoresVO) throws PersistenciaException {
		getSensoresDAO().create(sensoresVO);
	}
	
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateSensores(SensoresVO sensoresVO) throws PersistenciaException {
		getSensoresDAO().update(sensoresVO);
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<SensoresVO> findSensoresByParcela(ParcelasVO parcelaVO) throws PersistenciaException {
		return getSensoresDAO().findSensoresByParcela(parcelaVO);
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<SensoresVO> getAllSensoresFiltros (SensoresVO sensoresVO) throws PersistenciaException {
		return getSensoresDAO().getAllSensoresFiltros(sensoresVO);
	}

}
