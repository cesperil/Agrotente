package es.grapecheck.plataforma.persistencia.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.inject.Named;

import es.grapecheck.plataforma.persistencia.vo.PerfilesVO;
import es.grapecheck.plataforma.utiles.TratamientoDeDatos;

/**
 * @author rsanchez
 *
 */

@Named
@LocalBean
public class PerfilesDAO extends GenericDAOImpl<PerfilesVO> {

	
	public PerfilesVO getPerfilByIde(Integer id){
		
		PerfilesVO perfiles = new PerfilesVO();
		List<PerfilesVO> listaPerfiles = (List<PerfilesVO>) listarHQL("select p from PerfilesVO p where p.id=" + id);
		//List<PerfilesVO> listaPerfiles = (List<PerfilesVO>) listarNatural("select * from com_perfiles where id=" + id);
		if(TratamientoDeDatos.esListaConElementos(listaPerfiles)){
			perfiles =  listaPerfiles.get(0);
		}
		return perfiles;
	}
	
	public PerfilesVO getPerfilByNombre(String nombrePerfil){
		
		PerfilesVO perfiles = new PerfilesVO();
		List<PerfilesVO> listaPerfiles = (List<PerfilesVO>) listarHQL("select p from PerfilesVO p where p.nombre ='" + nombrePerfil + "'");
		//List<PerfilesVO> listaPerfiles = (List<PerfilesVO>) listarNatural("select * from com_perfiles where id=" + id);
		if(TratamientoDeDatos.esListaConElementos(listaPerfiles)){
			perfiles =  listaPerfiles.get(0);
		}
		return perfiles;
		
	}
	
}
