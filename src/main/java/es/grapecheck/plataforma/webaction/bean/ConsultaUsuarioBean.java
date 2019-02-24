package es.grapecheck.plataforma.webaction.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import es.grapecheck.plataforma.negocio.service.UsuarioService;
import es.grapecheck.plataforma.persistencia.exception.PersistenciaException;
import es.grapecheck.plataforma.persistencia.vo.UsuarioVO;
import es.grapecheck.plataforma.utiles.Constantes;
import es.grapecheck.plataforma.utiles.FacesUtils;
import es.grapecheck.plataforma.utiles.TipoBean;
import es.grapecheck.plataforma.utiles.TratamientoDeDatos;
import es.grapecheck.plataforma.webaction.form.BotoneraPrimefacesForm;

/**
 * @author cesperilla
 *
 */

@ManagedBean
@ViewScoped
// entre request y session, el bean existirá mientras la petición se envíe a la
// misma vista.
public class ConsultaUsuarioBean extends BotoneraPrimefacesForm<UsuarioVO> {

	private static final long serialVersionUID = -1479162331630831185L;

	private UsuarioVO usuarioVOSeleccionado;

	public UsuarioVO getUsuarioVOSeleccionado() {
		return usuarioVOSeleccionado;
	}

	public void setUsuarioVOSeleccionado(UsuarioVO usuarioVOSeleccionado) {
		this.usuarioVOSeleccionado = usuarioVOSeleccionado;
	}

	private UsuarioVO usuarioVO;

	public UsuarioVO getUsuarioVO() {
		return usuarioVO;
	}

	public void setUsuarioVO(UsuarioVO usuarioVO) {
		this.usuarioVO = usuarioVO;
	}

	public ConsultaUsuarioBean() {
		super();
	}

	@EJB
	private UsuarioService usuarioService;

	public UsuarioService getUsuarioService() {
		return usuarioService;
	}

	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	private Integer numRegistros;

	public Integer getNumRegistros() {
		return numRegistros;
	}

	public void setNumRegistros(Integer numRegistros) {
		this.numRegistros = numRegistros;
	}

	private List<UsuarioVO> elementList;

	

	public List<UsuarioVO> getElementList() {
		return elementList;
	}

	private List<UsuarioVO> selectedItems;



	public List<UsuarioVO> getSelectedItems() {
		return selectedItems;
	}

	@PostConstruct
	private void init() {
		this.montarBotoneraMantenimientoComun(TipoBean.CONSULTA);
		System.out.println("++Ejecucion método @PostConstruct");
		this.getBotonAyuda().setOnclick("PF('modalAyuda').show()");
		this.setRutaAyuda("../ayudas/ayudaSinAyuda.xhtml");
		if (TratamientoDeDatos.esVONullVacio(getUsuarioVO())) {
			setUsuarioVO(new UsuarioVO());
		}

		findElements();
		// if(TratamientoDeDatos.esVONullVacio(getUsuarioBusqueda())){
		// setUsuarioBusqueda(new UsuarioVO());
		// }

		// //cesperilla 27-10-2015 cojer parametro de sesión para hacer la
		// busqueda automáticamente.
		// if(UtilidadesContingencia.getBusquedaAutomatica()){
		// findElements();
		// UtilidadesContingencia.setBusquedaAutomatica(false);
		// }
	}

	@PreDestroy
	private void fin() {
		System.out.println("++Ejecucion método @PreDestroy");
	}

	// rsanchez 20151026 Añadido método validarRegistros seleccionados y delete
	// ActionListener que vamos a ejecutar al pulsar eliminar
	// Si NO hay un registro seleccionado mostrar el panel
	// "debe seleccionar un elemento"
	// Si hay un elemento seleccionado muestra el confirm de
	// "desea elemiminar los registros seleccionados?"
	// luisma_NOTA: si le ponemos el parametro event no nos funciona ya que le
	// hemos puesto al CommandButton el action desde java con el método
	// createMethodExpression y no le ponemos parametros
	// luisma_NOTA: si el boton eliminar tiene immediate=true, no actualiza la
	// lista selectedExp. OK
	public void validarRegistrosSeleccionados() {

		if (TratamientoDeDatos.esListaNullVacia(getSelectedItems())
				&& TratamientoDeDatos.esVONullVacio(this
						.getElementoSeleccionado())) {
			// RequestContext.getCurrentInstance().execute("PF('advertenciaSeleccRegistro').show()");
			mostrarConfirmGenericoSinCancelar(
					FacesUtils
							.getValueMensajesProperties("etiqueta.mensaje.convocatoriaeliminar"),
					Constantes.SEVERITY_ALERT, "");
		} else {// si tiene elementos seleccionados muestra en confirm
		// ConfirmGenerico(FacesUtils.getValueMensajesProperties("etiqueta.cabecera.confirmacion"),
		// FacesUtils.getValueMensajesProperties("etiqueta.mensaje.confirmeliminarconsultausuario"),
		// Constantes.SEVERITY_ALERT);
			mostrarConfirmGenerico(
					FacesUtils
							.getValueMensajesProperties("etiqueta.mensaje.confirmeliminarconsultausuario"),
					Constantes.SEVERITY_ALERT, "#{consultaUsuarioBean.delete}");
			RequestContext.getCurrentInstance().execute(
					"PF('confirmDeleteDetalle').show();");
		}
	}

	public void delete() {

		System.out.println("++ deleteElement");
		if (!TratamientoDeDatos.esListaNullVacia(getSelectedItems())) {
			for (int i = 0; i < getSelectedItems().size(); i++) {
				try {
					this.getUsuarioService().deleteUsuario(
							(UsuarioVO) this.getSelectedItems().get(i));
					// MensajeGenerico(FacesUtils.getValueMensajesProperties("etiqueta.cabecera.informacion"),
					// FacesUtils.getValueMensajesProperties("etiqueta.mensaje.infoeliminar.usuario"),
					// Constantes.SEVERITY_INFO);
					mostrarConfirmGenericoSinCancelar(
							FacesUtils
									.getValueMensajesProperties("etiqueta.mensaje.infoeliminar.usuario"),
							FacesUtils
									.getValueMensajesProperties("etiqueta.cabecera.informacion"),
							Constantes.SEVERITY_ALERT, this
									.getActionListenerCancelarConfirmGenerico());

				} catch (PersistenciaException e) {

					e.printStackTrace();
				}
			}
		} else {
			// RequestContext.getCurrentInstance().execute("PF('Info').show()");
			// juanlu 10/02/2016
			mostrarConfirmGenericoSinCancelar(
					FacesUtils
							.getValueMensajesProperties("etiqueta.operacionexito"),
					FacesUtils
							.getValueMensajesProperties("etiqueta.cabecera.consulta"),
					Constantes.SEVERITY_ALERT, this
							.getActionListenerAceptarConfirmGenerico()/*
																	 * this.
																	 * getActionListenerCancelarConfirmGenerico
																	 * ()
																	 */);

			return;
		}

		// rsanchez 20151026 Consultar de nuevo la lista asiganda a la tabla de
		// consulta
		try {
			List<UsuarioVO> list = this.getUsuarioService()
					.getAllUsuariosFiltros(this.getUsuarioVO().getUsername(),
							this.getUsuarioVO().getApellido1());
			this.setElementList(list);
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
	}// fin delete

	public void findElements() {

		// rsanchez
		try {
			List<UsuarioVO> list = this.getUsuarioService()
					.getAllUsuariosFiltros(this.getUsuarioVO().getUsername(),
							this.getUsuarioVO().getApellido1());
			this.setElementList(list);

			// luisma_pendiente:meter esto en un método de
			// botoneraPrimefacesForm. Ejemplo
			// this.ActualizarBotoneraBuesqueda();
			if (!TratamientoDeDatos.esListaNullVacia(this.getElementList())) {
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
				this.getBotonEliminarEnBusqueda().setDisabled(false);
				this.getBotonEliminarEnBusqueda().setStyle("cursor: pointer");
				// Habilitar la imagen del boton
				String accionListenerEliminarBusqueda = "#{"
						+ this.getNombreBeanActual()
						+ ".validarRegistrosSeleccionados}";
				this.meterAccionListenerBoton(
						this.getBotonEliminarEnBusqueda(),
						accionListenerEliminarBusqueda,
						Constantes.TABLA_DIV_BUSQUEDA, true);
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

	/**
	 * Limpiar los campos del formulario de busqueda.
	 */
	@Override
	public void limpiarCamposFormBusqueda() {
		this.getUsuarioVO().setUsername("");
		this.getUsuarioVO().setApellido1("");
	}

}