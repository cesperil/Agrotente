package es.grapecheck.plataforma.webaction.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import es.grapecheck.plataforma.negocio.service.TrabajadoresService;
import es.grapecheck.plataforma.negocio.service.TrabajosService;
import es.grapecheck.plataforma.persistencia.exception.PersistenciaException;
import es.grapecheck.plataforma.persistencia.vo.TrabajadorVO;
import es.grapecheck.plataforma.persistencia.vo.TrabajosRealizadosVO;
import es.grapecheck.plataforma.persistencia.vo.UsuarioVO;
import es.grapecheck.plataforma.utiles.Constantes;
import es.grapecheck.plataforma.utiles.FacesUtils;
import es.grapecheck.plataforma.utiles.FlashContext;
import es.grapecheck.plataforma.utiles.TipoBean;
import es.grapecheck.plataforma.webaction.form.BotoneraPrimefacesForm;

@ManagedBean
@ViewScoped
public class GestionTrabajadoresBean extends BotoneraPrimefacesForm<TrabajadorVO> { 

	@EJB
	private TrabajadoresService trabajadoresService;
	
	public TrabajadoresService getTrabajadoresService() {
		return trabajadoresService;
	}

	public void setTrabajadoresService(TrabajadoresService trabajadoresService) {
		this.trabajadoresService = trabajadoresService;
	}
	
	private List<TrabajadorVO> listaTrabajadores;

	public List<TrabajadorVO> getListaTrabajadores() {
		return listaTrabajadores;
	}

	public void setListaTrabajadores(List<TrabajadorVO> listaTrabajadores) {
		this.listaTrabajadores = listaTrabajadores;
	}

	private TrabajadorVO trabajadorVO;
	
	
	public TrabajadorVO getTrabajadorVO() {
		return trabajadorVO;
	}

	public void setTrabajadorVO(TrabajadorVO trabajadorVO) {
		this.trabajadorVO = trabajadorVO;
	}

	@PostConstruct
	private void init() {

		try {
			
			this.getBotonAyuda().setOnclick("PF('modalAyuda').show()");
			this.setRutaAyuda("../ayudas/ayudaSinAyuda.xhtml");
			this.montarBotoneraMantenimientoComun(TipoBean.ALTA);
			this.getBotonImprimir().setDisabled(true);
			String accionSalirPorDefecto = "#{gestionTrabajadoresBean.goVolver}";
			this.meterAccionBoton(this.getBotonSalir(), accionSalirPorDefecto);
			setListaTrabajadores(this.getTrabajadoresService().getTrabajadores());
			setTrabajadorVO(new TrabajadorVO());
		} catch (Exception e) {
			mostrarConfirmGenericoSinCancelar(FacesUtils.getValueMensajesProperties("etiqueta.mensaje.exception"),FacesUtils.getValueMensajesProperties("etiqueta.cabecera.exception"),Constantes.SEVERITY_ALERT,this.getActionListenerCancelarConfirmGenerico());
		}
	}
	
	
	public String goVolver() {
		//FlashContext.put("selectedItem", getParcelasVO());
		String pagAnterior = "principal.xhtml"
				+ "?faces-redirect=true";
		FacesUtils.setSessionParameter("pagAnterior", FacesUtils.getURLActual());
		return pagAnterior;
	}
	
}
	
