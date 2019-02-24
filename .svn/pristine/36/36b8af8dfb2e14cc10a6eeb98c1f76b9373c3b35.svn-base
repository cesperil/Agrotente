package es.grapecheck.plataforma.negocio.service;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.grapecheck.plataforma.persistencia.dao.UsuarioDAO;
import es.grapecheck.plataforma.persistencia.exception.PersistenciaException;
import es.grapecheck.plataforma.persistencia.vo.Estado;
import es.grapecheck.plataforma.persistencia.vo.UsuarioVO;
import es.grapecheck.plataforma.utiles.TratamientoDeDatos;

/**
 * @author rsanchez
 *
 */

@Stateless
@LocalBean
public class UsuarioService {


	private static final Logger LOG = LoggerFactory.getLogger(UsuarioService.class);

	@Inject
	private UsuarioDAO usuarioDAO;


	public UsuarioDAO getUsuarioDAO() {
		return usuarioDAO;
	}
	
	public void setExpedienteDAO(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}


	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void addUsuario(UsuarioVO usuarioVO) throws PersistenciaException {
		getUsuarioDAO().create(usuarioVO);
	}
	
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateUsuario(UsuarioVO usuarioVO) throws PersistenciaException {
		getUsuarioDAO().update(usuarioVO);
	}
	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public UsuarioVO getUsuarioById(Integer codigo) throws PersistenciaException {
	
		UsuarioVO usuarioVO = null;
		if (!TratamientoDeDatos.esNullCeroVacio(codigo)) {
			usuarioVO = new UsuarioVO();
			usuarioVO.setId(codigo);
			List<UsuarioVO> lista = getUsuarioDAO().findByCriteria(usuarioVO,Estado.ACTIVO, false);
			if (!TratamientoDeDatos.esListaNullVacia(lista)) {
				usuarioVO = lista.get(0);
			}
		} else {
			//LOG.error("El código de modelo es vacio, devolviendo modelo nulo.");
		}
		return usuarioVO;
	}
	
	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public UsuarioVO getUsuarioByUsernameyPassword(String username, String password) throws PersistenciaException {
	
		UsuarioVO usuarioVO = null;
		
		return getUsuarioDAO().getUsuarioByUsernameyPassword(username, password);		
	}
	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<UsuarioVO> getUsuarioByFitros(String username, String apellido1) throws PersistenciaException {
	
		UsuarioVO usuarioVO = null;
		usuarioVO = new UsuarioVO();
		
		if (!TratamientoDeDatos.esNullCeroVacio(username)) {
			usuarioVO.setUsername(username);
		}
		if (!TratamientoDeDatos.esNullCeroVacio(apellido1)) {
			usuarioVO.setApellido1(apellido1);
		}

		return  getUsuarioDAO().findByCriteria(usuarioVO,Estado.ACTIVO, false);
	}


	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteUsuario(UsuarioVO usuarioVO)throws PersistenciaException {
		getUsuarioDAO().delete(UsuarioVO.class,usuarioVO.getId());
	}
	
	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<UsuarioVO> getAllUsuarios()  throws PersistenciaException{
		return getUsuarioDAO().getAllList(UsuarioVO.class);
	}

	
	public List<UsuarioVO> findUsuarioViolenciaByCriteria(UsuarioVO usuarioVO)throws PersistenciaException {
		return this.getUsuarioDAO().findByCriteria(usuarioVO);
	}
	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<UsuarioVO> getAllUsuariosActivos() throws PersistenciaException {
			return getUsuarioDAO().getAllActivo(UsuarioVO.class);
	}
	
	public List<UsuarioVO> getAllUsuariosFiltros(String username, String apellido1) throws PersistenciaException{
		if (TratamientoDeDatos.esNullCeroVacio(username) && TratamientoDeDatos.esNullCeroVacio(apellido1)) {
			return getAllUsuariosActivos();
		}
		else {
			return getUsuarioByFitros(username,apellido1);
		}
	}

}
