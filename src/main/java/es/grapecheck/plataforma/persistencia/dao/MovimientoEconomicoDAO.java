package es.grapecheck.plataforma.persistencia.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.inject.Named;

import es.grapecheck.plataforma.persistencia.vo.MedicionesVO;
import es.grapecheck.plataforma.persistencia.vo.MovimientoEconomicoVO;
import es.grapecheck.plataforma.persistencia.vo.ParcelasVO;
import es.grapecheck.plataforma.persistencia.vo.UsuarioVO;
import es.grapecheck.plataforma.utiles.Constantes;
import es.grapecheck.plataforma.utiles.FacesUtils;
import es.grapecheck.plataforma.utiles.TratamientoDeDatos;

@Named
@LocalBean
public class MovimientoEconomicoDAO extends GenericDAOImpl<MovimientoEconomicoVO> {

	public List<MovimientoEconomicoVO> findListaMovimientosEconomicos(ParcelasVO parcela){
		UsuarioVO usuarioVO = (UsuarioVO) FacesUtils.getSessionParameter(Constantes.USUARIO_LOGIN);
		
		String listaHql = "select m from MovimientoEconomicoVO m where m.fkIdeUsuario = " + usuarioVO.getId();
		
		if(!TratamientoDeDatos.esNullCeroVacio(parcela)){
			listaHql += " and m.fkIdeParcela = " + parcela.getId();
		}
		
		List<MovimientoEconomicoVO> lista = (List<MovimientoEconomicoVO>) listarHQL(listaHql);
		
		return lista;
	}
	
}
