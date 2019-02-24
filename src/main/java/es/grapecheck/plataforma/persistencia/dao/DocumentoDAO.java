package es.grapecheck.plataforma.persistencia.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.inject.Named;

import es.grapecheck.plataforma.persistencia.vo.DocAdjuntoVO;
import es.grapecheck.plataforma.persistencia.vo.ParcelasVO;
import es.grapecheck.plataforma.persistencia.vo.UsuarioVO;

@Named
@LocalBean
public class DocumentoDAO extends GenericDAOImpl<DocAdjuntoVO>{

	/**
	 * cesperilla  consulta documentos by id usuario
	 * @param usuarioVO
	 * @return
	 */
	public List<DocAdjuntoVO> getDocumentosByIdUsuario(UsuarioVO usuarioVO){
		
		
		List<DocAdjuntoVO> lista = (List<DocAdjuntoVO>) listarHQL("select d from DocAdjuntoVO d where d.fkIdeUsuario = " + usuarioVO.getId());
		
		return lista;
		
	}
	
	
}
