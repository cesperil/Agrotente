package es.grapecheck.plataforma.negocio.service;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.grapecheck.plataforma.persistencia.dao.MedicionesDAO;
import es.grapecheck.plataforma.persistencia.dao.MedicionesMaxMinDAO;
import es.grapecheck.plataforma.persistencia.exception.PersistenciaException;
import es.grapecheck.plataforma.persistencia.vo.MedicionesMinMaxTempVO;
import es.grapecheck.plataforma.persistencia.vo.MedicionesMinMaxVO;
import es.grapecheck.plataforma.persistencia.vo.ParcelasVO;

@Stateless
@LocalBean
public class MedicionesMinMaxService {

	private static final Logger LOG = LoggerFactory.getLogger(MedicionesMinMaxService.class);
	
	@Inject
	private MedicionesMaxMinDAO medicionesMaxMinDAO;

	public MedicionesMaxMinDAO getMedicionesMaxMinDAO() {
		return medicionesMaxMinDAO;
	}

	public void setMedicionesMaxMinDAO(MedicionesMaxMinDAO medicionesMaxMinDAO) {
		this.medicionesMaxMinDAO = medicionesMaxMinDAO;
	}

	public List<MedicionesMinMaxTempVO> getMedicionesTemperaturaByIdParcela(ParcelasVO parcelasVO) throws PersistenciaException{
		return getMedicionesMaxMinDAO().getMedicionesTemperaturaByIdParcela(parcelasVO);
	}
	
}
