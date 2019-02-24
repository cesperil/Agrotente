package es.grapecheck.plataforma.webaction.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import es.grapecheck.plataforma.negocio.service.TrabajosService;
import es.grapecheck.plataforma.persistencia.vo.MovimientoEconomicoVO;
import es.grapecheck.plataforma.persistencia.vo.TrabajosRealizadosVO;
import es.grapecheck.plataforma.persistencia.vo.UsuarioVO;
import es.grapecheck.plataforma.utiles.Constantes;
import es.grapecheck.plataforma.utiles.FacesUtils;
import es.grapecheck.plataforma.utiles.TipoBean;
import es.grapecheck.plataforma.webaction.form.BotoneraPrimefacesForm;

@ManagedBean
@ViewScoped
public class GestionTrabajosBean extends BotoneraPrimefacesForm<TrabajosRealizadosVO> {

	private static final long serialVersionUID = -1479162331630831185L;
	
	private List<TrabajosRealizadosVO> listaTrabajos;
	
	public List<TrabajosRealizadosVO> getListaTrabajos() {
		return listaTrabajos;
	}

	public void setListaTrabajos(List<TrabajosRealizadosVO> listaTrabajos) {
		this.listaTrabajos = listaTrabajos;
	}

	@EJB
	private TrabajosService trabajosService;


	public TrabajosService getTrabajosService() {
		return trabajosService;
	}

	public void setTrabajosService(TrabajosService trabajosService) {
		this.trabajosService = trabajosService;
	}

	@PostConstruct
	private void init() {

		try {
			
			this.getBotonAyuda().setOnclick("PF('modalAyuda').show()");
			this.setRutaAyuda("../ayudas/ayudaSinAyuda.xhtml");
			this.montarBotoneraMantenimientoComun(TipoBean.ALTA);
			this.getBotonImprimir().setDisabled(true);
			String accionSalirPorDefecto = "#{gestionTrabajosBean.goVolver}";
			this.meterAccionBoton(this.getBotonSalir(), accionSalirPorDefecto);
			
			setListaTrabajos(this.getTrabajosService().getTrabajosFiltros((UsuarioVO)FacesUtils.getSessionParameter(Constantes.USUARIO_LOGIN)));
		} catch (Exception e) {
			mostrarConfirmGenericoSinCancelar(FacesUtils.getValueMensajesProperties("etiqueta.mensaje.exception"),FacesUtils.getValueMensajesProperties("etiqueta.cabecera.exception"),Constantes.SEVERITY_ALERT,this.getActionListenerCancelarConfirmGenerico());
		}
	}
	
}
