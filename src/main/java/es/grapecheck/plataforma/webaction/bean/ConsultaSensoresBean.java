package es.grapecheck.plataforma.webaction.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import es.grapecheck.plataforma.negocio.service.SensoresService;
import es.grapecheck.plataforma.persistencia.exception.PersistenciaException;
import es.grapecheck.plataforma.persistencia.vo.SensoresVO;
import es.grapecheck.plataforma.persistencia.vo.TipoSensoresVO;
import es.grapecheck.plataforma.utiles.Constantes;
import es.grapecheck.plataforma.utiles.FacesUtils;
import es.grapecheck.plataforma.utiles.FlashContext;
import es.grapecheck.plataforma.utiles.TipoBean;
import es.grapecheck.plataforma.utiles.TratamientoDeDatos;
import es.grapecheck.plataforma.webaction.form.BotoneraPrimefacesForm;

@ManagedBean
@ViewScoped
public class ConsultaSensoresBean extends BotoneraPrimefacesForm<SensoresVO>{

	private static final long serialVersionUID = -1479162331630831185L;

	private SensoresVO sensoresVO;
	
	public SensoresVO getSensoresVO() {
		return sensoresVO;
	}


	public void setSensoresVO(SensoresVO sensoresVO) {
		this.sensoresVO = sensoresVO;
	}
	
	@EJB
	private SensoresService sensoresService;


	public SensoresService getSensoresService() {
		return sensoresService;
	}


	public void setSensoresService(SensoresService sensoresService) {
		this.sensoresService = sensoresService;
	}
	
	private List<SensoresVO> listaSensores;


	public List<SensoresVO> getListaSensores() {
		return listaSensores;
	}


	public void setListaSensores(List<SensoresVO> listaSensores) {
		this.listaSensores = listaSensores;
	}


	@PostConstruct
	private void init() {
		this.montarBotoneraMantenimientoComun(TipoBean.CONSULTA);
		System.out.println("++Ejecucion método @PostConstruct");
		this.getBotonAyuda().setOnclick("PF('modalAyuda').show()");
		this.setRutaAyuda("../ayudas/ayudaSinAyuda.xhtml");
		if (TratamientoDeDatos.esVONullVacio(getSensoresVO())) {
			setSensoresVO(new SensoresVO());
		}

		//findElements();
	
	}
	

	public void findElements() {

		try {
			List<SensoresVO> list = this.getSensoresService().getAllSensoresFiltros(this.getSensoresVO());
			this.setListaSensores(list);

			// luisma_pendiente:meter esto en un método de
			// botoneraPrimefacesForm. Ejemplo
			// this.ActualizarBotoneraBuesqueda();
			if (!TratamientoDeDatos.esListaNullVacia(this.getListaSensores())) {
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
	
			
	public String goDetalle(SensoresVO sensoresVO){
		
		
		FlashContext.put("selectedItem", sensoresVO);
		
		
		return "/mantenimientos/altaSensores.xhtml";

	}
	
}
