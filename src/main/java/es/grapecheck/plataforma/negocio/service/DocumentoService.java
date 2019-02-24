package es.grapecheck.plataforma.negocio.service;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.grapecheck.plataforma.persistencia.dao.DocumentoDAO;
import es.grapecheck.plataforma.persistencia.exception.PersistenciaException;
import es.grapecheck.plataforma.persistencia.vo.DocAdjuntoVO;
import es.grapecheck.plataforma.persistencia.vo.MedicionesVO;
import es.grapecheck.plataforma.persistencia.vo.ParcelasVO;
import es.grapecheck.plataforma.persistencia.vo.UsuarioVO;

@Stateless
@LocalBean
public class DocumentoService {
	private static final Logger LOG = LoggerFactory.getLogger(DocumentoService.class);
	
	@Inject
	private DocumentoDAO documentoDAO;

	public DocumentoDAO getDocumentoDAO() {
		return documentoDAO;
	}

	public void setDocumentoDAO(DocumentoDAO documentoDAO) {
		this.documentoDAO = documentoDAO;
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void addDocumento(DocAdjuntoVO docAdjuntoVO) throws PersistenciaException {
		getDocumentoDAO().create(docAdjuntoVO);
	}

	public List<DocAdjuntoVO> getDocAdjuntoByUsuario(UsuarioVO usuarioVO){
		return getDocumentoDAO().getDocumentosByIdUsuario(usuarioVO);
	}
	
}
