package es.grapecheck.plataforma.persistencia.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.inject.Named;

import es.grapecheck.plataforma.persistencia.vo.UsuarioVO;
import es.grapecheck.plataforma.utiles.TratamientoDeDatos;


/**
 * @author rsanchez
 *
 */

// @Stateless /* Indica que el Bean de Sesión es sin estado */
@Named
@LocalBean
public class UsuarioDAO extends GenericDAOImpl<UsuarioVO> {

	public UsuarioVO getUsuarioByUsernameyPassword(String username, String password){
		
		UsuarioVO usuario= new UsuarioVO();
		
		List<UsuarioVO> lista = (List<UsuarioVO>) listarHQL("select u from UsuarioVO u where u.username='"+username+"' and u.password='" + password + "'");
		
		if(TratamientoDeDatos.esListaConElementos(lista)){
			usuario = lista.get(0);
		}
		
		return usuario;
		
	}

}
