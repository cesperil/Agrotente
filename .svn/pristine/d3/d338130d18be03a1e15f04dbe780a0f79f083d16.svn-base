package es.grapecheck.plataforma.webaction.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.jfree.util.Log;

import es.grapecheck.plataforma.negocio.service.TipoSensoresService;
import es.grapecheck.plataforma.persistencia.exception.PersistenciaException;
import es.grapecheck.plataforma.persistencia.vo.PerfilesVO;
import es.grapecheck.plataforma.persistencia.vo.TipoSensoresVO;
import es.grapecheck.plataforma.persistencia.vo.UsuarioVO;
import es.grapecheck.plataforma.utiles.Constantes;
import es.grapecheck.plataforma.utiles.FacesUtils;
import es.grapecheck.plataforma.utiles.FlashContext;
import es.grapecheck.plataforma.utiles.TipoBean;
import es.grapecheck.plataforma.utiles.TratamientoDeDatos;
import es.grapecheck.plataforma.webaction.form.BotoneraPrimefacesForm;

@ManagedBean
@ViewScoped
public class AltaTiposSensoresBean extends BotoneraPrimefacesForm<TipoSensoresVO> {

	private static final long serialVersionUID = -1479162331630831185L;
	
	@EJB
	private TipoSensoresService tiposSensoresService;
	
	public TipoSensoresService getTiposSensoresService() {
		return tiposSensoresService;
	}

	public void setTiposSensoresService(TipoSensoresService tiposSensoresService) {
		this.tiposSensoresService = tiposSensoresService;
	}

	private TipoSensoresVO tiposSensoresVO;
	
	public TipoSensoresVO getTiposSensoresVO() {
		return tiposSensoresVO;
	}

	public void setTiposSensoresVO(TipoSensoresVO tiposSensoresVO) {
		this.tiposSensoresVO = tiposSensoresVO;
	}

	public AltaTiposSensoresBean() {
		super();
	}
	
	@PostConstruct
	private void init() {

		try {
			setTiposSensoresVO(new TipoSensoresVO());
			TipoSensoresVO tiposSensoresVO = (TipoSensoresVO) FlashContext.get("selectedItem");
			
			this.getBotonAyuda().setOnclick("PF('modalAyuda').show()");
			this.setRutaAyuda("../ayudas/ayudaSinAyuda.xhtml");
			if (!TratamientoDeDatos.esVONullVacio(tiposSensoresVO)) {
				this.getMapper().map(tiposSensoresVO, this.getTiposSensoresVO());
				this.montarBotoneraMantenimientoComun(TipoBean.DETALLE);
			} else {
				this.montarBotoneraMantenimientoComun(TipoBean.ALTA);
			}
			this.getBotonImprimir().setDisabled(true);
		} catch (Exception e) {
			//RequestContext.getCurrentInstance().execute("PF('Excepcion').show()");
			//juanlu 10/02/2016
			mostrarConfirmGenericoSinCancelar(FacesUtils.getValueMensajesProperties("etiqueta.mensaje.exception"),FacesUtils.getValueMensajesProperties("etiqueta.cabecera.exception"),Constantes.SEVERITY_ALERT,this.getActionListenerCancelarConfirmGenerico());
		}
	}
	
	public void delete() {
		try {
			this.getTiposSensoresService().deleteTipoSensores(getTiposSensoresVO());
			//cesperilla 27-10-2015 busqueda automática al volver a la pantalla de consulta tras eliminar
			//MensajeGenericoNavegando(FacesUtils.getValueMensajesProperties("etiqueta.cabecera.informacion"), FacesUtils.getValueMensajesProperties("etiqueta.mensaje.infoeliminar.usuario"), Constantes.SEVERITY_INFO);
			mostrarConfirmGenericoSinCancelarYVueltaAtras(FacesUtils.getValueMensajesProperties("etiqueta.mensaje.infoeliminar.usuario"), Constantes.SEVERITY_INFO, "", this.getBotonVolverOkDeAlta());
			//UtilidadesContingencia.setBusquedaAutomatica(true);
		
		} catch (PersistenciaException e) {
			//RequestContext.getCurrentInstance().execute("PF('Excepcion').show()");
			//juanlu 10/02/2016
			mostrarConfirmGenericoSinCancelar(FacesUtils.getValueMensajesProperties("etiqueta.mensaje.exception"),FacesUtils.getValueMensajesProperties("etiqueta.cabecera.exception"),Constantes.SEVERITY_ALERT,this.getActionListenerCancelarConfirmGenerico());
			
		}
	}
	
	public void add() {
			TipoSensoresVO tiposSensoresVO = this.getMapper().map(this.getTiposSensoresVO(),TipoSensoresVO.class);
			try {
				if(TratamientoDeDatos.esNullCeroVacio(tiposSensoresVO.getId())){
					Log.info("Guardar Usuario");
					this.getTiposSensoresService().addTipoSensores(tiposSensoresVO);
					mostrarConfirmGenericoSinCancelarYVueltaAtras(FacesUtils.getValueMensajesProperties("etiqueta.mensaje.usuarioguardado"), Constantes.SEVERITY_INFO, "", this.getBotonVolverOkDeAlta());
				}
			} catch (PersistenciaException e) {
				e.printStackTrace();
				mostrarConfirmGenericoSinCancelar(FacesUtils.getValueMensajesProperties("etiqueta.mensaje.exception"),FacesUtils.getValueMensajesProperties("etiqueta.cabecera.exception"),Constantes.SEVERITY_ALERT,this.getActionListenerCancelarConfirmGenerico());
				
			}
		}
	
	public void update() {
		TipoSensoresVO tiposSensoresVO = this.getMapper().map(this.getTiposSensoresVO(),TipoSensoresVO.class);
		try {
			this.getTiposSensoresService().updateTipoSensores(tiposSensoresVO);
			mostrarConfirmGenericoSinCancelarYVueltaAtras(FacesUtils.getValueMensajesProperties("etiqueta.mensaje.usuarioguardado"),FacesUtils.getValueMensajesProperties("etiqueta.cabecera.informacion"), Constantes.SEVERITY_INFO, this.getBotonVolverOkDeAlta() );
		} catch (PersistenciaException e) {
			mostrarConfirmGenericoSinCancelar(FacesUtils.getValueMensajesProperties("etiqueta.mensaje.exception"),FacesUtils.getValueMensajesProperties("etiqueta.cabecera.exception"),Constantes.SEVERITY_ALERT,this.getActionListenerCancelarConfirmGenerico());
		}
	}
	
	
}
