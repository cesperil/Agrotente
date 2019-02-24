/**
 * 
 */
package es.grapecheck.plataforma.negocio.service;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.grapecheck.plataforma.persistencia.dao.MedicionesDAO;
import es.grapecheck.plataforma.persistencia.dao.ParcelasDAO;
import es.grapecheck.plataforma.persistencia.exception.PersistenciaException;
import es.grapecheck.plataforma.persistencia.vo.MedicionesVO;
import es.grapecheck.plataforma.persistencia.vo.ParcelasVO;

/**
 * @author Alejandro
 *
 */
public class MedicionesService {
	
private static final Logger LOG = LoggerFactory.getLogger(ParcelasService.class);
	
	@Inject
	private MedicionesDAO medicionesDAO;

	public MedicionesDAO getMedicionesDAO() {
		return medicionesDAO;
	}

	public void setParcelasDAO(MedicionesDAO medicionesDAO) {
		this.medicionesDAO = medicionesDAO;
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void addMedicion(MedicionesVO medicionesVO) throws PersistenciaException {
		getMedicionesDAO().create(medicionesVO);
	}
	
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateMediciones(MedicionesVO medicionesVO) throws PersistenciaException {
		getMedicionesDAO().update(medicionesVO);
	}

}
