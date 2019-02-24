package es.grapecheck.plataforma.webaction.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import es.grapecheck.plataforma.negocio.service.TipoSensoresService;
import es.grapecheck.plataforma.persistencia.exception.PersistenciaException;
import es.grapecheck.plataforma.persistencia.vo.ParcelasVO;
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
public class ConsultaTipoSensoresBean extends BotoneraPrimefacesForm<TipoSensoresVO> {

	private static final long serialVersionUID = -1479162331630831185L;

	
	private TipoSensoresVO tiposSensoresVO;
	
	public TipoSensoresVO getTiposSensoresVO() {
		return tiposSensoresVO;
	}

	public void setTiposSensoresVO(TipoSensoresVO tiposSensoresVO) {
		this.tiposSensoresVO = tiposSensoresVO;
	}
	
	@EJB
	private TipoSensoresService tiposSensoresService;
	
	public TipoSensoresService getTiposSensoresService() {
		return tiposSensoresService;
	}

	public void setTiposSensoresService(TipoSensoresService tiposSensoresService) {
		this.tiposSensoresService = tiposSensoresService;
	}

	private List<TipoSensoresVO> listaTiposSensores;
	
	public List<TipoSensoresVO> getListaTiposSensores() {
		return listaTiposSensores;
	}

	public void setListaTiposSensores(List<TipoSensoresVO> listaTiposSensores) {
		this.listaTiposSensores = listaTiposSensores;
	}

	@PostConstruct
	private void init() {
		this.montarBotoneraMantenimientoComun(TipoBean.CONSULTA);
		System.out.println("++Ejecucion método @PostConstruct");
		this.getBotonAyuda().setOnclick("PF('modalAyuda').show()");
		this.setRutaAyuda("../ayudas/ayudaSinAyuda.xhtml");
		if (TratamientoDeDatos.esVONullVacio(getTiposSensoresVO())) {
			setTiposSensoresVO(new TipoSensoresVO());
		}

		findElements();
	
	}

	public void findElements() {

		try {
			List<TipoSensoresVO> list = this.getTiposSensoresService().getAllTipoSensoresFiltros(this.getTiposSensoresVO());
			this.setListaTiposSensores(list);

			// luisma_pendiente:meter esto en un método de
			// botoneraPrimefacesForm. Ejemplo
			// this.ActualizarBotoneraBuesqueda();
			if (!TratamientoDeDatos.esListaNullVacia(this.getListaTiposSensores())) {
				// BOTON LIMPIAR
				String accionListenerLimpiar = "#{"
						+ this.getNombreBeanActual() + ".goLimpiar}";
				this.meterAccionListenerBoton(
						this.getBotonLimpiar(),
						accionListenerLimpiar,
						"panelContenidoCentral,botonLimpiar,criteriosBusqueda,botonEliminarEnBusqueda",
						true);

				// BOTON ELIMINAR CONSULTA
				// estilo
				// this.getBotonEliminarEnBusqueda().setStyleClass(Constantes.ESTILO_BOTON_ACTIVADO);
				this.getBotonEliminarEnBusqueda().setDisabled(true);
				
			} else {
				// mostramos mensajito no hay registros
				// RequestContext.getCurrentInstance().execute("PF('InfoSinRegistros').show()");
				// juanlu 10-02-2016
				mostrarConfirmGenericoSinCancelar(
						FacesUtils
								.getValueMensajesProperties("etiqueta.mensaje.inforegistro"),
						FacesUtils
								.getValueMensajesProperties("etiqueta.cabecera.consulta"),
						Constantes.SEVERITY_ALERT, this
								.getActionListenerAceptarConfirmGenerico());

			}

		} catch (PersistenciaException e) {
			FacesUtils.putMessageErrorCustom(e.getDescription());
		}

	}
	
			
	public String goDetalle(TipoSensoresVO tipoSensoresVO){
		
		
		FlashContext.put("selectedItem", tipoSensoresVO);
		
		
		return "/mantenimientos/altaTiposSensores.xhtml";

	}
	
	
	
}
