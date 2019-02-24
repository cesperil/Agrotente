package es.grapecheck.plataforma.persistencia.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.inject.Named;

import es.grapecheck.plataforma.persistencia.vo.ParcelasVO;
import es.grapecheck.plataforma.persistencia.vo.UsuarioVO;
import es.grapecheck.plataforma.utiles.Constantes;
import es.grapecheck.plataforma.utiles.FacesUtils;
import es.grapecheck.plataforma.utiles.TratamientoDeDatos;

@Named
@LocalBean
public class ParcelasDAO extends GenericDAOImpl<ParcelasVO>{

	/**
	 * cesperilla 27/01/2017 consulta parcelas by ide usuario
	 * @param usuarioVO
	 * @return
	 */
	public List<ParcelasVO> getParcelasByUsuario(UsuarioVO usuarioVO){
		
		
		List<ParcelasVO> lista = (List<ParcelasVO>) listarHQL("select p from ParcelasVO p where p.fkIdeUsuario = " + usuarioVO.getId());
		
		return lista;
		
	}

	
	/**
	 * cesperilla 27/01/2017 consulta parcelas by ide usuario
	 * @param usuarioVO
	 * @return
	 */
	public List<ParcelasVO> getParcelasByUsuarioByCoordenadas(Double latitud, Double longitud){
		
		UsuarioVO usuarioVO = (UsuarioVO) FacesUtils.getSessionParameter(Constantes.USUARIO_LOGIN);
		
		List<ParcelasVO> lista = (List<ParcelasVO>) listarHQL("select p from ParcelasVO p where p.fkIdeUsuario = " + usuarioVO.getId() +" and latitud '" + latitud + "' and longitud '" + longitud + "'");
		
		return lista;
		
	}
	
	
	/**
	 * cesperilla 27/01/2017 consulta parcelas by ide usuario, solo es valido si mantenemos que no se repita el nombre de la parcela para el usuario
	 * @param usuarioVO
	 * @return
	 */
	public ParcelasVO getParcelaByUsuarioByNombre(String nombreParcela){
		
		UsuarioVO usuarioVO = (UsuarioVO) FacesUtils.getSessionParameter(Constantes.USUARIO_LOGIN);
		ParcelasVO parcelasVO = new ParcelasVO();
		
		List<ParcelasVO> lista = (List<ParcelasVO>) listarHQL("select p from ParcelasVO p where p.fkIdeUsuario = " + usuarioVO.getId() +" and nombre = '" + nombreParcela + "'");
		
		if(TratamientoDeDatos.esListaConElementos(lista)){
			parcelasVO = lista.get(0);
		}
		
		return parcelasVO;
		
	}
	
	
}
