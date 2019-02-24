package es.grapecheck.plataforma.webaction.bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import es.grapecheck.plataforma.persistencia.vo.DocAdjuntoVO;
import es.grapecheck.plataforma.persistencia.vo.ParcelasVO;
import es.grapecheck.plataforma.persistencia.vo.UsuarioVO;
import es.grapecheck.plataforma.utiles.Constantes;
import es.grapecheck.plataforma.utiles.FacesUtils;
import es.grapecheck.plataforma.utiles.FlashContext;
import es.grapecheck.plataforma.utiles.TipoBean;
import es.grapecheck.plataforma.utiles.TratamientoDeDatos;
import es.grapecheck.plataforma.webaction.form.BotoneraPrimefacesForm;

public class ConsultaAccesosParcelasBean extends BotoneraPrimefacesForm<DocAdjuntoVO>{

	private UsuarioVO usuarioVO;

	public UsuarioVO getUsuarioVO() {
		return usuarioVO;
	}

	public void setUsuarioVO(UsuarioVO usuarioVO) {
		this.usuarioVO = usuarioVO;
	}
	
	List<DocAdjuntoVO> listaAccesos = new ArrayList<DocAdjuntoVO>();
	
	public List<DocAdjuntoVO> getListaAccesos() {
		return listaAccesos;
	}

	public void setListaAccesos(List<DocAdjuntoVO> listaAccesos) {
		this.listaAccesos = listaAccesos;
	}

	@PostConstruct
	private void init() {

		try {

			UsuarioVO usuarioVO = (UsuarioVO) FacesUtils
					.getSessionParameter(Constantes.USUARIO_LOGIN);
			setUsuarioVO(usuarioVO);

			DocAdjuntoVO docAdjuntoVO = new DocAdjuntoVO();
			docAdjuntoVO.setNombre("Finca Acceso");
			
			getListaAccesos().add(docAdjuntoVO);
			
			//List<DocAdjuntoVO> listaDocAdjunto = getDocumentoService().getDocAdjuntoByUsuario(usuarioVO);
			//setListaDocAdjunto(listaDocAdjunto);
			this.getBotonAyuda().setOnclick("PF('modalAyuda').show()"); 	 	
			this.setRutaAyuda("../ayudas/ayudaSinAyuda.xhtml");
			if (!TratamientoDeDatos.esVONullVacio(docAdjuntoVO)) {
				// this.getMapper().map(docAdjuntoVO, this.getUsuarioVO());

				// PerfilesVO perfilesVO = usuarioVO.getFkIdePerfiles();

				/*
				 * if (!TratamientoDeDatos.esVONullVacio(perfilesVO)) {
				 * setTextoComboPerfil(perfilesVO.getNombre()); }
				 */

				this.montarBotoneraMantenimientoComun(TipoBean.DETALLE);
			} else {
				this.montarBotoneraMantenimientoComun(TipoBean.ALTA);
			}

			/* rsanchez deshabilitar el boton de impresión */
			this.getBotonImprimir().setDisabled(true);
			
		} catch (Exception e) {
			// RequestContext.getCurrentInstance().execute("PF('Excepcion').show()");
			// juanlu 10/02/2016
			mostrarConfirmGenericoSinCancelar(
					FacesUtils
							.getValueMensajesProperties("etiqueta.mensaje.exception"),
					FacesUtils
							.getValueMensajesProperties("etiqueta.cabecera.exception"),
					Constantes.SEVERITY_ALERT, this
							.getActionListenerCancelarConfirmGenerico());

		}
	}
	
	public String goNuevoAcceso(){
		
		
		
		FacesUtils.setSessionParameter("pagAnterior", FacesUtils.getURLActual());
		
		
		return "/accesos/solicitudAcceso.xhtml";
	}
	
	
	
}
