package es.grapecheck.plataforma.negocio.service;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.grapecheck.plataforma.persistencia.dao.PerfilesDAO;
import es.grapecheck.plataforma.persistencia.exception.PersistenciaException;
import es.grapecheck.plataforma.persistencia.vo.Estado;
import es.grapecheck.plataforma.persistencia.vo.PerfilesVO;
import es.grapecheck.plataforma.utiles.TratamientoDeDatos;

/**
 * @author rsanchez
 *
 */

@Stateless
@LocalBean
/*rsanchez Con esta Anotacion LocalBean estamos indicando que la interfaz de este DAO 
(esta clase en concreto sería la tipica clase DAOImpl) se genere sola, por tanto 
NO la tenemos que crear ni incluir el implements en la declaración de esta clase DAO*/


public class PerfilesService {

	private static final Logger LOG = LoggerFactory.getLogger(PerfilesService.class);

	@Inject
	private PerfilesDAO perfilesDAO;

	public PerfilesDAO getPerfilesDAO() {
		return perfilesDAO;
	}
	
	public void setPerfilesDAO(PerfilesDAO perfilesDAO) {
		this.perfilesDAO = perfilesDAO;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void addPerfiles(PerfilesVO perfilesVO) throws PersistenciaException {
		getPerfilesDAO().create(perfilesVO);
	}
	
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updatePerfiles(PerfilesVO perfilesVO) throws PersistenciaException {
		getPerfilesDAO().update(perfilesVO);
	}
	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public PerfilesVO getPerfilesById(Integer codigo) throws PersistenciaException {
	
		PerfilesVO perfilesVO = null;
		if (!TratamientoDeDatos.esNullCeroVacio(codigo)) {
			perfilesVO = new PerfilesVO();
			perfilesVO = getPerfilesDAO().getPerfilByIde(codigo);
			
		}
		return perfilesVO;
	}
	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<PerfilesVO> getPerfilesByFitros(String nomCorto) throws PersistenciaException {
	
		PerfilesVO perfilesVO = null;
		perfilesVO = new PerfilesVO();
		
		if (!TratamientoDeDatos.esNullCeroVacio(nomCorto)) {
			perfilesVO.setNombre(nomCorto);
		}

		return  getPerfilesDAO().findByCriteria(perfilesVO,Estado.ACTIVO, false);
	}


	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deletePerfiles(PerfilesVO perfilesVO)throws PersistenciaException {
		getPerfilesDAO().delete(PerfilesVO.class,perfilesVO.getId());
	}
	
	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<PerfilesVO> getAllPerfiless()  throws PersistenciaException{
		return getPerfilesDAO().getAllList(PerfilesVO.class);
	}

	
	public List<PerfilesVO> findPerfilessByCriteria(PerfilesVO perfilesVO)throws PersistenciaException {
		return this.getPerfilesDAO().findByCriteria(perfilesVO);
	}
	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<PerfilesVO> getAllPerfilessActivos() throws PersistenciaException {
			return getPerfilesDAO().getAllActivo(PerfilesVO.class);
	}
	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<PerfilesVO> getAllPerfilessFiltros(String nomCorto) throws PersistenciaException{
		if (TratamientoDeDatos.esNullCeroVacio(nomCorto)) {
			return getAllPerfilessActivos();
		}
		else {
			return getPerfilesByFitros(nomCorto);
		}
	}
	
	public PerfilesVO getPerfilByNombre(String nombrePerfil){
		return getPerfilesDAO().getPerfilByNombre(nombrePerfil);
	}
	
	
}
