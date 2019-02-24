package es.grapecheck.plataforma.negocio.service;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.grapecheck.plataforma.persistencia.dao.TrabajoRealizadoDAO;
import es.grapecheck.plataforma.persistencia.exception.PersistenciaException;
import es.grapecheck.plataforma.persistencia.vo.TipoSensoresVO;
import es.grapecheck.plataforma.persistencia.vo.TrabajosRealizadosVO;
import es.grapecheck.plataforma.persistencia.vo.UsuarioVO;

@Stateless
@LocalBean
public class TrabajosService {

	private static final Logger LOG = LoggerFactory
			.getLogger(TipoSensoresService.class);

	@Inject
	private TrabajoRealizadoDAO trabajoRealizadoDAO;

	public TrabajoRealizadoDAO getTrabajoRealizadoDAO() {
		return trabajoRealizadoDAO;
	}

	public void setTrabajoRealizadoDAO(TrabajoRealizadoDAO trabajoRealizadoDAO) {
		this.trabajoRealizadoDAO = trabajoRealizadoDAO;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<TrabajosRealizadosVO> getTrabajosFiltros(UsuarioVO usuarioVO)
			throws PersistenciaException {
		return getTrabajoRealizadoDAO().getTrabajos(usuarioVO.getId());
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void addTrabajo(TrabajosRealizadosVO trabajoRealizadoVO)
			throws PersistenciaException {
		getTrabajoRealizadoDAO().create(trabajoRealizadoVO);
	}

}
