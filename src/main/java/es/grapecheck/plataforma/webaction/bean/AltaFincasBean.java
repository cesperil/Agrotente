package es.grapecheck.plataforma.webaction.bean;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.jfree.util.Log;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import es.grapecheck.plataforma.negocio.service.ParcelasService;
import es.grapecheck.plataforma.persistencia.exception.PersistenciaException;
import es.grapecheck.plataforma.persistencia.vo.ParcelasVO;
import es.grapecheck.plataforma.persistencia.vo.PerfilesVO;
import es.grapecheck.plataforma.persistencia.vo.UsuarioVO;
import es.grapecheck.plataforma.utiles.Constantes;
import es.grapecheck.plataforma.utiles.FacesUtils;
import es.grapecheck.plataforma.utiles.FlashContext;
import es.grapecheck.plataforma.utiles.TipoBean;
import es.grapecheck.plataforma.utiles.TratamientoDeDatos;
import es.grapecheck.plataforma.webaction.form.BotoneraPrimefacesForm;

@ManagedBean
@ViewScoped
/**
 * Clase que crea una finca 
 * @author Carlos
 *
 */
public class AltaFincasBean extends BotoneraPrimefacesForm<ParcelasVO> {

	private static final long serialVersionUID = -1479162331630831185L;

	private ParcelasVO parcelasVO;

	private Double latitud;

	private Double longitud;

	private String nombre;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Double getLatitud() {
		return latitud;
	}

	public void setLatitud(Double latitud) {
		this.latitud = latitud;
	}

	public Double getLongitud() {
		return longitud;
	}

	public void setLongitud(Double longitud) {
		this.longitud = longitud;
	}

	public ParcelasVO getParcelasVO() {
		return parcelasVO;
	}

	public void setParcelasVO(ParcelasVO parcelasVO) {
		this.parcelasVO = parcelasVO;
	}

	@EJB
	private ParcelasService parcelasService;

	public ParcelasService getParcelasService() {
		return parcelasService;
	}

	public void setParcelasService(ParcelasService parcelasService) {
		this.parcelasService = parcelasService;
	}

	public AltaFincasBean() {
		super();
	}

	private MapModel emptyModel;

	public MapModel getEmptyModel() {
		return emptyModel;
	}

	public void setEmptyModel(MapModel emptyModel) {
		this.emptyModel = emptyModel;
	}

	public void add() {

		ParcelasVO parcelasVO = this.getMapper().map(this.getParcelasVO(),
				ParcelasVO.class);

		try {

			// Guardar
			Log.info("Guardar Parcela");

			if (TratamientoDeDatos.esNullCeroVacio(parcelasVO.getId())) {
				// el usuario es el de login de momento.
				parcelasVO.setFkIdeUsuario((UsuarioVO) FacesUtils
						.getSessionParameter(Constantes.USUARIO_LOGIN));
				this.getParcelasService().addParcelas(parcelasVO);
			} else {

				this.getParcelasService().updateParcelas(parcelasVO);
			}

			// UtilidadesContingencia.setBusquedaAutomatica(true);
			// MensajeGenericoNavegando(FacesUtils.getValueMensajesProperties("etiqueta.cabecera.informacion"),
			// FacesUtils.getValueMensajesProperties("etiqueta.mensaje.usuarioguardado"),
			// Constantes.SEVERITY_INFO);
			mostrarConfirmGenericoSinCancelar("Guardado correctamente",
					Constantes.SEVERITY_INFO, "");
			// cesperilla 27-10-2015 seteo para que busque automáticamente al
			// volver a consulta

		} catch (PersistenciaException e) {
			// RequestContext.getCurrentInstance().execute("PF('Excepcion').show()");
			// juanlu 10/02/2016
			e.printStackTrace();
			mostrarConfirmGenericoSinCancelar(
					FacesUtils
							.getValueMensajesProperties("etiqueta.mensaje.exception"),
					FacesUtils
							.getValueMensajesProperties("etiqueta.cabecera.exception"),
					Constantes.SEVERITY_ALERT, this
							.getActionListenerCancelarConfirmGenerico());

		}

	}

	public void delete() {

	}

	@PostConstruct
	private void init() {

		try {
			ParcelasVO parcelasVO = (ParcelasVO) FlashContext
					.get("selectedItem");
			emptyModel = new DefaultMapModel();
			if (!TratamientoDeDatos.esNullCeroVacio(parcelasVO)) {
				setParcelasVO(parcelasVO);
			} else {
				parcelasVO = new ParcelasVO();
			}
			this.getBotonAyuda().setOnclick("PF('modalAyuda').show()");
			this.setRutaAyuda("../ayudas/ayudaSinAyuda.xhtml");
			this.montarBotoneraMantenimientoComun(TipoBean.ALTA);
			this.getBotonImprimir().setDisabled(true);
			this.getBotonGuardar().setDisabled(true);
			String accionSalirPorDefecto = "#{altaFincasBean.goVolver}";
			this.meterAccionBoton(this.getBotonSalir(), accionSalirPorDefecto);

		} catch (Exception e) {
			mostrarConfirmGenericoSinCancelar(
					FacesUtils
							.getValueMensajesProperties("etiqueta.mensaje.exception"),
					FacesUtils
							.getValueMensajesProperties("etiqueta.cabecera.exception"),
					Constantes.SEVERITY_ALERT, this
							.getActionListenerCancelarConfirmGenerico());
		}
	}

	public String goVolver() {
		FlashContext.put("selectedItem", getParcelasVO());
		String pagAnterior = FacesUtils.getSessionParameter("pagAnterior")
				+ "?faces-redirect=true";
		FacesUtils
				.setSessionParameter("pagAnterior", FacesUtils.getURLActual());
		return pagAnterior;
	}

	public void addMarker() {
		try {
			if (TratamientoDeDatos.esNullCeroVacio(getParcelasVO())) {
				setParcelasVO(new ParcelasVO());
			}
			getParcelasVO().setNombre(this.getNombre());
			getParcelasVO().setLatitud(
					TratamientoDeDatos.sNoNull(this.getLatitud()));
			getParcelasVO().setLongitud(
					TratamientoDeDatos.sNoNull(this.getLongitud()));
			Marker marker = new Marker(new LatLng(this.getLatitud(),
					this.getLongitud()), this.getNombre());
			emptyModel.addOverlay(marker);
			// Guardar
			Log.info("Guardar Parcela");

			if (TratamientoDeDatos.esNullCeroVacio(parcelasVO.getId())) {
				// el usuario es el de login de momento.
				parcelasVO.setFkIdeUsuario((UsuarioVO) FacesUtils
						.getSessionParameter(Constantes.USUARIO_LOGIN));

				this.getParcelasService().addParcelas(parcelasVO);

			} else {

				this.getParcelasService().updateParcelas(parcelasVO);
			}
			mostrarConfirmGenericoSinCancelar("Guardado correctamente",
					Constantes.SEVERITY_INFO, "");
		} catch (PersistenciaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}