package es.grapecheck.plataforma.negocio.service;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.grapecheck.plataforma.persistencia.dao.ConsultaSqlLogDAO;
import es.grapecheck.plataforma.persistencia.exception.PersistenciaException;

/**
 * @author rsanchez
 *
 */


@Stateless
@LocalBean
/*rsanchez Con esta Anotacion LocalBean estamos indicando que la interfaz de este DAO 
(esta clase en concreto sería la tipica clase DAOImpl) se genere sola, por tanto 
NO la tenemos que crear ni incluir el implements en la declaración de esta clase DAO*/

/*rsanchez estas dos anotaciones estamos forzando a que este service Bean se ejecute y 
 * esté en el ámbito de la sesion desde el inicio de la sesion y antes de cualquier solicitud
 * Necesario para poder recuperar el valor del comboConsultaSqlLog y así se pueda realizar la 
 * actualización de la lista de los municipios*/
@ManagedBean(name="consultaSqlLogService", eager = true)
@ApplicationScoped
public class ConsultaSqlLogService {

	private static final Logger LOG = LoggerFactory.getLogger(ConsultaSqlLogService.class);

	@Inject
	private ConsultaSqlLogDAO consultaSqlLogDAO;

	public ConsultaSqlLogDAO getConsultaSqlLogDAO() {
		return consultaSqlLogDAO;
	}

	public void setConsultaSqlLogDAO(ConsultaSqlLogDAO consultaSqlLogDAO) {
		this.consultaSqlLogDAO = consultaSqlLogDAO;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<?> listarHQL(String consulta) throws PersistenciaException {
		return getConsultaSqlLogDAO().listarHQL(consulta);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public int updateDeleteHQL(String consulta) throws PersistenciaException {
		return getConsultaSqlLogDAO().updateHQL(consulta);
	}	
	
}
