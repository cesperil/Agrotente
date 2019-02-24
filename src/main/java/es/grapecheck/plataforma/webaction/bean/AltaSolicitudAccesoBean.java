package es.grapecheck.plataforma.webaction.bean;

import javax.annotation.PostConstruct;

import es.grapecheck.plataforma.persistencia.vo.TrabajadorVO;
import es.grapecheck.plataforma.persistencia.vo.TrabajosRealizadosVO;
import es.grapecheck.plataforma.persistencia.vo.UsuarioVO;
import es.grapecheck.plataforma.utiles.Constantes;
import es.grapecheck.plataforma.utiles.FacesUtils;
import es.grapecheck.plataforma.utiles.TipoBean;
import es.grapecheck.plataforma.webaction.form.BotoneraPrimefacesForm;

public class AltaSolicitudAccesoBean  extends BotoneraPrimefacesForm<TrabajadorVO>{

	@PostConstruct
	private void init() {

		try {
			
			this.getBotonAyuda().setOnclick("PF('modalAyuda').show()");
			this.setRutaAyuda("../ayudas/ayudaSinAyuda.xhtml");
			this.montarBotoneraMantenimientoComun(TipoBean.ALTA);
			this.getBotonImprimir().setDisabled(true);
			String accionSalirPorDefecto = "#{gestionEconomicaBean.goVolver}";
			this.meterAccionBoton(this.getBotonSalir(), accionSalirPorDefecto);
			/*setTrabajoRealizadoVO(new TrabajosRealizadosVO());
			setListaParcelas(this.getParcelasService().getParcelasByUsuario((UsuarioVO)FacesUtils.getSessionParameter(Constantes.USUARIO_LOGIN)));
			setListaTrabajadores(this.getTrabajadoresService().getTrabajadores());*/
		} catch (Exception e) {
			mostrarConfirmGenericoSinCancelar(FacesUtils.getValueMensajesProperties("etiqueta.mensaje.exception"),FacesUtils.getValueMensajesProperties("etiqueta.cabecera.exception"),Constantes.SEVERITY_ALERT,this.getActionListenerCancelarConfirmGenerico());
		}
	}
	
	
}
