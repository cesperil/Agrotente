package es.grapecheck.plataforma.webaction.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import es.grapecheck.plataforma.persistencia.vo.UsuarioVO;
import es.grapecheck.plataforma.utiles.Constantes;
import es.grapecheck.plataforma.utiles.FacesUtils;
import es.grapecheck.plataforma.utiles.FlashContext;
import es.grapecheck.plataforma.utiles.TratamientoDeDatos;
import es.grapecheck.plataforma.utiles.TratamientoFechas;
import es.grapecheck.plataforma.webaction.component.CalendarViogen;


public class ComunView<T> implements Serializable {
	
	//Tamaño de la lista elementList
	private Integer numRegistros;
	private Integer selectedIndex;
	private Mapper mapper;
	private Date fechaAlta;
	private Date fechaBaja;
	private Date fechaModificacion;
	private Integer id;
	private String usuario;//Usuario que realiza la última modificación en el registro.
	private Object elementoSeleccionado; //elemento seleccionado de div_busqueda
	
	//Variables para plantillas
	private String textoComboPlantillasExp;
	private String textoComboPlantillasGrupos;
	
	private List<T> elementList;

	public void setElementList(List<T> elementList) {
		this.elementList = elementList;
		if (TratamientoDeDatos.esListaNullVacia(elementList))
			setNumRegistros(0);
		else
			setNumRegistros(elementList.size());
	}

	public List<T> getElementList() {
		return elementList;
	}

	private List<T> selectedItems;

	public void setSelectedItems(List<T> selectedItems) {
		this.selectedItems = selectedItems;
	}

	public List<T> getSelectedItems() {
		return selectedItems;
	}

	
//	@EJB 
//	private FicherosImpresionService ficherosImpresionService;
//	
//	public FicherosImpresionService getFicherosImpresionService() {
//		return ficherosImpresionService;
//	}
//
//	public void setFicherosImpresionService(FicherosImpresionService ficherosImpresionService) {
//		this.ficherosImpresionService = ficherosImpresionService;
//	}
//	
//	@EJB 
//	private ParametrosGeneralesService parametrosGeneralesService;
//	
//	public ParametrosGeneralesService getParametrosGeneralesService() {
//		return parametrosGeneralesService;
//	}
//
//	public void setParametrosGeneralesService(ParametrosGeneralesService parametrosGeneralesService) {
//		this.parametrosGeneralesService = parametrosGeneralesService;
//	}
	
	//msalguero Estilos y ruta de la ayuda
	private String rutaAyuda;
	private String estiloAyuda;
	
	/***********/
	//rsanchez 20160203 Atributos para el confirmGenerico
	private String messageConfirmGenerico;
	private String headerConfirmGenerico;
	private String severityConfirmGenerico;
	private String updateAceptarConfirmGenerico;
	private String actionListenerAceptarConfirmGenerico;
	private String immediateAceptarConfirmGenerico;
	private String updateCancelarConfirmGenerico;
	private String immediateCancelarConfirmGenerico;
	private String actionListenerCancelarConfirmGenerico;
	private String renderedCancelarConfirmGenerico;
	private CommandButton bindingAceptarConfirmGenerico;
	
	private String paginaRedigir;
	private Object objetoRedirigir;
	
	public Object getObjetoRedirigir() {
		return objetoRedirigir;
	}
	public void setObjetoRedirigir(Object objetoRedirigir) {
		this.objetoRedirigir = objetoRedirigir;
	}

	public String getMessageConfirmGenerico() {
		return messageConfirmGenerico;
	}
	public void setMessageConfirmGenerico(String messageConfirmGenerico) {
		this.messageConfirmGenerico = messageConfirmGenerico;
	}
	public String getHeaderConfirmGenerico() {
		return headerConfirmGenerico;
	}
	public void setHeaderConfirmGenerico(String headerConfirmGenerico) {
		this.headerConfirmGenerico = headerConfirmGenerico;
	}
	public String getSeverityConfirmGenerico() {
		return severityConfirmGenerico;
	}
	public void setSeverityConfirmGenerico(String severityConfirmGenerico) {
		this.severityConfirmGenerico = severityConfirmGenerico;
	}
	public String getUpdateAceptarConfirmGenerico() {
		return updateAceptarConfirmGenerico;
	}
	public void setUpdateAceptarConfirmGenerico(String updateAceptarConfirmGenerico) {
		this.updateAceptarConfirmGenerico = updateAceptarConfirmGenerico;
	}
	public String getActionListenerAceptarConfirmGenerico() {
		return actionListenerAceptarConfirmGenerico;
	}
	public void setActionListenerAceptarConfirmGenerico(
			String actionListenerAceptarConfirmGenerico) {
		this.actionListenerAceptarConfirmGenerico = actionListenerAceptarConfirmGenerico;
	}
	public String getUpdateCancelarConfirmGenerico() {
		return updateCancelarConfirmGenerico;
	}
	public void setUpdateCancelarConfirmGenerico(
			String updateCancelarConfirmGenerico) {
		this.updateCancelarConfirmGenerico = updateCancelarConfirmGenerico;
	}
	public String getImmediateCancelarConfirmGenerico() {
		return immediateCancelarConfirmGenerico;
	}
	public void setImmediateCancelarConfirmGenerico(
			String immediateCancelarConfirmGenerico) {
		this.immediateCancelarConfirmGenerico = immediateCancelarConfirmGenerico;
	}
	public String getActionListenerCancelarConfirmGenerico() {
		return actionListenerCancelarConfirmGenerico;
	}
	public void setActionListenerCancelarConfirmGenerico(
			String actionListenerCancelarConfirmGenerico) {
		this.actionListenerCancelarConfirmGenerico = actionListenerCancelarConfirmGenerico;
	}
	public String getRenderedCancelarConfirmGenerico() {
		return renderedCancelarConfirmGenerico;
	}
	public void setRenderedCancelarConfirmGenerico(String renderedCancelarConfirmGenerico) {
		this.renderedCancelarConfirmGenerico = renderedCancelarConfirmGenerico;
	}
	public CommandButton getBindingAceptarConfirmGenerico() {
		return bindingAceptarConfirmGenerico;
	}
	public void setBindingAceptarConfirmGenerico(
			CommandButton bindingAceptarConfirmGenerico) {
		this.bindingAceptarConfirmGenerico = bindingAceptarConfirmGenerico;
	}
	
	public String getPaginaRedigir() {
		return paginaRedigir;
	}

	public void setPaginaRedigir(String paginaRedigir) {
		this.paginaRedigir = paginaRedigir;
	}

	/********/


	public ComunView() {
		// TODO Auto-generated constructor stub
	}


	public Object getElementoSeleccionado() {
		return elementoSeleccionado;
	}
	public void setElementoSeleccionado(Object elementoSeleccionado) {
		this.elementoSeleccionado = elementoSeleccionado;
	}
	
	public String getTextoComboPlantillasExp() {
		return textoComboPlantillasExp;
	}

	public void setTextoComboPlantillasExp(String textoComboPlantillasExp) {
		this.textoComboPlantillasExp = textoComboPlantillasExp;
	}

	public String getTextoComboPlantillasGrupos() {
		return textoComboPlantillasGrupos;
	}

	public void setTextoComboPlantillasGrupos(String textoComboPlantillasGrupos) {
		this.textoComboPlantillasGrupos = textoComboPlantillasGrupos;
	}

	
	public Date getFechaAlta() {
		return fechaAlta;
	}
	public Date getFechaBaja() {
		return fechaBaja;
	}
	public Date getFechaModificacion() {
		return fechaModificacion;
	}
	public Integer getId() {
		return id;
	}
	public Mapper getMapper() {
		if (this.mapper == null) {
			return new DozerBeanMapper();
		}
		return this.mapper;
	}
	public Integer getNumRegistros() {
		return numRegistros;
	}
	public Integer getSelectedIndex() {
		return selectedIndex;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	public void setMapper(Mapper mapper) {
		this.mapper = mapper;
	}
	public void setNumRegistros(Integer numRegistros) {
		this.numRegistros = numRegistros;
	}

	public void setSelectedIndex(Integer selectedIndex) {
		this.selectedIndex = selectedIndex;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	public String getImmediateAceptarConfirmGenerico() {
		return immediateAceptarConfirmGenerico;
	}

	public void setImmediateAceptarConfirmGenerico(
			String immediateAceptarConfirmGenerico) {
		this.immediateAceptarConfirmGenerico = immediateAceptarConfirmGenerico;
	}

	/**
	 * Borra de session el bean que se esta usando.
	 */
	public void borrarBeanActual() {
		// borrar el bean(controller) que se esta utilizando.
		String bean = this.getNombreBeanActual();
		// borrar el bean de session
		if (!TratamientoDeDatos.esNullCeroVacio(bean)) {
			FacesUtils.deleteSession(bean);
		}
	}
	public String goSalirConsulta() {
		FacesUtils.deleteSession("consultaExpedienteBean");
		borrarBeanActual();
		return "goMenu";
	}
	
	
	/**
	 * Obtener el nombre del bean actual.
	 * @return String nombreBean
	 */
	public String getNombreBeanActual() {
		String nombre = this.getClass().getAnnotation(ManagedBean.class).name();
		if (TratamientoDeDatos.esNullCeroVacio(nombre)) {
			nombre = this.getClass().getSimpleName().substring(0, 1).toLowerCase()+ this.getClass().getSimpleName().substring(1);
		}
		return nombre;
	}

	public String goSalirDetalle() {
		
		//rsanchez_doc añadido remove en flash para que borre el selecteditem del contexto, no debería ser necesario, hay que revisarlo
				FlashContext.remove("selectedItem");
				FacesUtils.deleteSession("selectedItem");
				if(TratamientoDeDatos.esNullCeroVacio(this.getPaginaRedigir())){
					FacesContext ctx = FacesContext.getCurrentInstance();
					String viewId = ctx.getViewRoot().getViewId();
					viewId = viewId.substring(viewId.lastIndexOf('/') + 1);
					viewId = viewId.replace("alta", "consulta");
					viewId = viewId.substring(0, viewId.indexOf('.'));
					return viewId + "?faces-redirect=true";
				}else{
					String viewId = this.getPaginaRedigir();
					FlashContext.remove("paginaRedirigir");
					FacesUtils.deleteSession("paginaRedirigir");
					FlashContext.put("objetoRedirigir", this.getObjetoRedirigir());
					return viewId + "?faces-redirect=true&amp;includeViewParams=true";
				}
	}

	/**
	 * Limpiar los campos del formulario de busqueda.
	 */
	public void limpiarCamposFormBusqueda() {
	}

	public void limpiarCamposFormulario() {
	}

	
	//Imprimes las plantillas ODT. Estará sobrescrito en las clases que corresponda.Hay que diferenciar entre impresion masiva o individual
	public void imprimirPlantillas() {
		System.out.println("Imprimir plantillas comunView");
		//ImprimirPlantillasOdt.imprimirPlantilla();
		
	}
	
public String goAlta() {
		
		FacesContext ctx = FacesContext.getCurrentInstance();
		String viewId = ctx.getViewRoot().getViewId();
		viewId = viewId.substring(viewId.lastIndexOf('/') + 1);
		viewId = viewId.replace("consulta", "alta");
		viewId = viewId.substring(0, viewId.indexOf('.'));
		
		//rsanchez_doc DEBEMOS borrar selectedItem para que los alta-detalle esten vacios
		//FlashContext.remove("selectedItem");
		return viewId + "?faces-redirect=true";
	}

	
	public String goDetalle() {
   	    
		FlashContext.put("selectedItem", this.getElementoSeleccionado());
		//FlashContext.setRedirect(true);  para que sirve???		
		
		//FacesUtils.setSessionParameter("selectedItem", this.getExpedienteVOSeleccionado());
		FacesContext ctx = FacesContext.getCurrentInstance();
		String viewId = ctx.getViewRoot().getViewId();
		viewId = viewId.substring(viewId.lastIndexOf('/') + 1);
		viewId = viewId.replace("consulta", "alta");
		viewId = viewId.substring(0, viewId.indexOf('.'));
		return viewId + "?faces-redirect=true&amp;includeViewParams=true";
	
	}


	
	/**
	 * JoseCortes 05-02-2016
	 * cesperilla 03-02-2016 cambio estilos segun sea correcta o no
	 * @param fecha
	 * @param componente
	 */
	public void isFechaCorrecta(Date fecha, UIComponent componente){
		if(TratamientoFechas.esFechaValida(fecha)){
			FacesUtils.cambiarAtributoComponente(componente.getClientId(), "styleClass", "");
		}else{ 
			FacesUtils.cambiarAtributoComponente(componente.getClientId(), "styleClass", Constantes.CSS_CALENDAR_ERROR);
		}
		RequestContext.getCurrentInstance().update(componente.getClientId());
	}
	
	/**
	 *  JoseCortes 05-02-2016
	 * cesperilla 02-02-2016 validar que el numero introducido en el input corresponda con una fecha real.
	 * @param evento
	 */
	public void validarFechas(AjaxBehaviorEvent evento)
	 {
		FacesContext context = FacesContext.getCurrentInstance();
		UIInput theInput = (UIInput)evento.getSource();
	    CalendarViogen a=(CalendarViogen)theInput;
	    isFechaCorrecta((Date) a.getValue(), evento.getComponent());
	 }
	
	/**
	 * JoseCortes 05-02-2016
	 * cesperilla 03-02-2016 validar que la fecha introducida sea correcta
	 * @param event
	 */
	public void dateChange(SelectEvent event) {
		Date date = (Date) event.getObject();
		isFechaCorrecta(date, event.getComponent());
	}
	
	
	/**
	 * rsanchez 20160203 ConfirmGenerico parametizable y generico para la aplicacion
	 */
	public void mostrarConfirmGenerico (String messageConfirmGenerico, String headerConfirmGenerico, 
										String severityConfirmGenerico,	String updateAceptarConfirmGenerico, 
										String actionListenerAceptarConfirmGenerico, CommandButton bindingAceptarConfirmGenerico,
										String updateCancelarConfirmGenerico, String immediateCancelarConfirmGenerico,
										String actionListenerCancelarConfirmGenerico, String renderedCancelarConfirmGenerico){
		
		this.setMessageConfirmGenerico(TratamientoDeDatos.sNoNull(messageConfirmGenerico));
		this.setHeaderConfirmGenerico(TratamientoDeDatos.sNoNull(headerConfirmGenerico));
		this.setSeverityConfirmGenerico(TratamientoDeDatos.sNoNull(severityConfirmGenerico));
		this.setUpdateAceptarConfirmGenerico(TratamientoDeDatos.sNoNull(updateAceptarConfirmGenerico));
		this.setActionListenerAceptarConfirmGenerico(TratamientoDeDatos.sNoNull(actionListenerAceptarConfirmGenerico));
		
		if (!TratamientoDeDatos.esNullVacio(bindingAceptarConfirmGenerico)) {
			this.setBindingAceptarConfirmGenerico(bindingAceptarConfirmGenerico);
		}
		this.setUpdateCancelarConfirmGenerico(TratamientoDeDatos.sNoNull(updateCancelarConfirmGenerico));
		this.setImmediateCancelarConfirmGenerico(TratamientoDeDatos.sNoNull(immediateCancelarConfirmGenerico));
		this.setActionListenerCancelarConfirmGenerico(TratamientoDeDatos.sNoNull(actionListenerCancelarConfirmGenerico));
		this.setRenderedCancelarConfirmGenerico(TratamientoDeDatos.sNoNull(renderedCancelarConfirmGenerico));
		
		//Si los botones tienen actionListener se lo añadimos
		UIViewRoot view = FacesContext.getCurrentInstance().getViewRoot();
		if (!TratamientoDeDatos.esNullCeroVacio(this.getActionListenerAceptarConfirmGenerico())) {
			UIComponent comp = view.findComponent("formulario:confirmGenericoButtonAceptar"); 
			//El actionListener del boton Aceptar se realiza a continuación, ya que da problemas, no se recupera de forma correcta el bean al que hace referencia
			FacesUtils.setAccionListener((CommandButton) comp, this.getActionListenerAceptarConfirmGenerico());
		}
		
		if (!TratamientoDeDatos.esNullCeroVacio(this.getActionListenerCancelarConfirmGenerico())) {
			UIComponent comp2 = view.findComponent("formulario:confirmGenericoButtonCancelar");
			//El actionListener del boton Aceptar se realiza a continuación, ya que da problemas, no se recupera de forma correcta el bean al que hace referencia
			FacesUtils.setAccionListener((CommandButton) comp2, this.getActionListenerCancelarConfirmGenerico());
		}
		
		//Actualizar los botones del confirm y el propio confirm
		RequestContext.getCurrentInstance().update("formulario:confirmGenericoButtonAceptar");
		RequestContext.getCurrentInstance().update("formulario:confirmGenericoButtonCancelar");
		RequestContext.getCurrentInstance().update("formulario:confirmGenerico");
		
	
		//Mostrar el confirm
		RequestContext.getCurrentInstance().execute("PF('confirmGenerico').show()");
	}
	
	/**
	 * rsanchez 20160203 ConfirmGenerico parametizable con el header generico
	 */
	public void mostrarConfirmGenerico (String messageConfirmGenerico,
										String severityConfirmGenerico,	String updateAceptarConfirmGenerico, 
										String actionListenerAceptarConfirmGenerico, CommandButton bindingAceptarConfirmGenerico,
										String updateCancelarConfirmGenerico, String immediateCancelarConfirmGenerico,
										String actionListenerCancelarConfirmGenerico, String renderedCancelarConfirmGenerico){
		
		mostrarConfirmGenerico(messageConfirmGenerico, FacesUtils.getValueMensajesProperties("etiqueta.cabecera.confirmacion"), severityConfirmGenerico,
				updateAceptarConfirmGenerico, actionListenerAceptarConfirmGenerico, bindingAceptarConfirmGenerico,
				updateCancelarConfirmGenerico, immediateCancelarConfirmGenerico,
				actionListenerCancelarConfirmGenerico, renderedCancelarConfirmGenerico);
	}
	
	/**
	 * rsanchez 20160203 ConfirmGenerico parametizable con el header generico y sin acciones en el boton Cancelar
	 */
	public void mostrarConfirmGenerico (String messageConfirmGenerico, String severityConfirmGenerico,
										String updateAceptarConfirmGenerico, String actionListenerAceptarConfirmGenerico){
		
		mostrarConfirmGenerico(messageConfirmGenerico, FacesUtils.getValueMensajesProperties("etiqueta.cabecera.confirmacion"), severityConfirmGenerico,
				updateAceptarConfirmGenerico, actionListenerAceptarConfirmGenerico,null,
				"", "false","","true");
	}
	
	/**
	 * rsanchez 20160203 ConfirmGenerico parametizable con el header generico y sin acciones en el boton Cancelar y con el update del boton aceptar como panelContenidoCentral
	 */
	public void mostrarConfirmGenerico (String messageConfirmGenerico, String severityConfirmGenerico,
										String actionListenerAceptarConfirmGenerico){
		
		mostrarConfirmGenerico(messageConfirmGenerico, FacesUtils.getValueMensajesProperties("etiqueta.cabecera.confirmacion"), severityConfirmGenerico,
				"panelContenidoCentral", actionListenerAceptarConfirmGenerico,null,
				"", "false","","true");
	}
	
	/**
	 * rsanchez 20160210 ConfirmGenerico parametizable sin acciones en el boton Cancelar y no visible y con el update del boton aceptar como panelContenidoCentral
	 */
	public void mostrarConfirmGenericoSinCancelar (String messageConfirmGenerico, String headerConfirmGenerico,
													String severityConfirmGenerico,	String actionListenerAceptarConfirmGenerico){
		
		mostrarConfirmGenerico(messageConfirmGenerico, headerConfirmGenerico, severityConfirmGenerico,
				"panelContenidoCentral", actionListenerAceptarConfirmGenerico,null,
				"", "false","","false");
	}
	
	/**
	 * rsanchez 20160203 ConfirmGenerico parametizable con el header generico y sin acciones en el boton Cancelar y no visible y con el update del boton aceptar como panelContenidoCentral
	 */
	public void mostrarConfirmGenericoSinCancelar (String messageConfirmGenerico, String severityConfirmGenerico,
										String actionListenerAceptarConfirmGenerico){
		
		mostrarConfirmGenerico(messageConfirmGenerico, FacesUtils.getValueMensajesProperties("etiqueta.cabecera.confirmacion"), severityConfirmGenerico,
				"panelContenidoCentral", actionListenerAceptarConfirmGenerico,null,
				"", "false","","false");
	}
	
	/**
	 * rsanchez 20160203 ConfirmGenerico parametizable con el header generico y sin acciones en el boton Cancelar y no visible y con el update del boton aceptar como panelContenidoCentral, además hay que pasarle el boton de botonVolverOkDeAlta
	 */
	public void mostrarConfirmGenericoSinCancelarYVueltaAtras (String messageConfirmGenerico, String severityConfirmGenerico,
															   String actionListenerAceptarConfirmGenerico, CommandButton botonVolverOkDeAlta){
		
		mostrarConfirmGenerico(messageConfirmGenerico, FacesUtils.getValueMensajesProperties("etiqueta.cabecera.informacion"), severityConfirmGenerico,
				"panelContenidoCentral", actionListenerAceptarConfirmGenerico, botonVolverOkDeAlta,
				"", "false","","false");
	}
	
	/**
	 * rsanchez 20160203 ConfirmGenerico parametizable con el header generico y sin acciones en el boton Cancelar y no visible y con el update del boton aceptar como panelContenidoCentral, además hay que pasarle el boton de botonVolverOkDeAlta
	 */
	public void mostrarConfirmGenericoSinCancelarYVueltaAtras (String messageConfirmGenerico, String severityConfirmGenerico,
															   String actionListenerAceptarConfirmGenerico, CommandButton botonVolverOkDeAlta, String immediateAceptarConfirmGenerico){
		this.setImmediateAceptarConfirmGenerico(immediateAceptarConfirmGenerico);
		mostrarConfirmGenerico(messageConfirmGenerico, FacesUtils.getValueMensajesProperties("etiqueta.cabecera.informacion"), severityConfirmGenerico,
				"panelContenidoCentral", actionListenerAceptarConfirmGenerico, botonVolverOkDeAlta,
				"", "false","","false");
			
	}
	
	/**
	 * rsanchez 20160203 ConfirmGenerico parametizable con el header generico y sin acciones en el boton Aceptar
	 */
	public void mostrarConfirmGenerico (String messageConfirmGenerico, String severityConfirmGenerico,
										String updateCancelarConfirmGenerico, String immediateCancelarConfirmGenerico, String actionListenerCancelarConfirmGenerico){
		
		mostrarConfirmGenerico(messageConfirmGenerico, FacesUtils.getValueMensajesProperties("etiqueta.cabecera.confirmacion"), severityConfirmGenerico,
				"", "",null,
				updateCancelarConfirmGenerico, immediateCancelarConfirmGenerico,actionListenerCancelarConfirmGenerico,"true");
	}
	public String getRutaAyuda() {
		return rutaAyuda;
	}
	public void setRutaAyuda(String rutaAyuda) {
		this.rutaAyuda = rutaAyuda;
	}
	
	/**
	 * cesperilla 23-11-2015 carga los valores de parametros generales necesarios para escribir en las plantillas.
	 * @return
	 */
	public HashMap<String, String> cargarValoresParametrosGeneralesPlantillasGrupos(){
		HashMap<String, String> valoresParametros = new HashMap<String, String>();
//		try {
	
//		String cargofirmantepr =parametrosGeneralesService.getParametroGeneralByCodigo("CARPR").getValor();
//		String nombrefirmantepr =parametrosGeneralesService.getParametroGeneralByCodigo("NOMBPR").getValor();
//		String cargofirmanteresol =parametrosGeneralesService.getParametroGeneralByCodigo("CARRESOL").getValor();
//		String nombrefirmanteresol =parametrosGeneralesService.getParametroGeneralByCodigo("NOMBRESOL").getValor();
//		valoresParametros.put("CARPR", cargofirmantepr);
//		valoresParametros.put("NOMBPR", nombrefirmantepr);
//		valoresParametros.put("CARRESOL", cargofirmanteresol);
//		valoresParametros.put("NOMBRESOL", nombrefirmanteresol);
//		} catch (PersistenciaException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		return valoresParametros;
	}
	
	/*public List<String> completarPlantillasExp(String query) {

		List<String> filteredPlantillas = new ArrayList<String>();
		List<FicherosImpresionVO> ficherosImpresion = getFicherosImpresionService().getListFicherosImpresionExpedientes();
		if(TratamientoDeDatos.esListaConElementos(ficherosImpresion)){
			for (FicherosImpresionVO autoPlantillas : ficherosImpresion) {
				if (autoPlantillas.getNombreCombo().toLowerCase().startsWith(query)) {
					filteredPlantillas.add(autoPlantillas.getNombreCombo());
				}
			}
		}
		return filteredPlantillas;
	}
	*/

	
	
	/**
	 * cesperilla 23-11-2015 carga los valores de parametros generales necesarios para escribir en las plantillas.
	 * @return
	 */
	public HashMap<String, String> cargarValoresParametrosGeneralesPlantillas (){
		 
		HashMap<String, String> valoresParametros = new HashMap<String, String>();
		
		//consultar el domicilio, las provincias y la localidad de un expediente
		
		
//		try {
//			String cargoJefaServicio = parametrosGeneralesService.getParametroGeneralByCodigo("CJS").getValor();
//			String nombreAyuntamiento = parametrosGeneralesService.getParametroGeneralByCodigo("ASSASB").getValor();
//			String decreto = parametrosGeneralesService.getParametroGeneralByCodigo("DEC").getValor();
//			String nombreJefaServicio = parametrosGeneralesService.getParametroGeneralByCodigo("NJS").getValor();
//			String cargoJefeSeccion = parametrosGeneralesService.getParametroGeneralByCodigo("CJSECCION").getValor();
//			String nombreJefeSeccion = parametrosGeneralesService.getParametroGeneralByCodigo("NJSECCION").getValor();
//			String cargoConsejero = parametrosGeneralesService.getParametroGeneralByCodigo("CCONSEJERO").getValor();
//			String seccionDirectora = parametrosGeneralesService.getParametroGeneralByCodigo("SDIRECTORA").getValor();
//			String nombreDirectora = parametrosGeneralesService.getParametroGeneralByCodigo("NDIRECTORA").getValor();
//			
//			String delegacionProp = parametrosGeneralesService.getParametroGeneralByCodigo("DELEGAPROP").getValor();
//			String delegacionResol = parametrosGeneralesService.getParametroGeneralByCodigo("DELEGARESOL").getValor();
//			String fechaDelegaResol = parametrosGeneralesService.getParametroGeneralByCodigo("FECDELEGARESOL").getValor();
//			String fechaDelegaProp = parametrosGeneralesService.getParametroGeneralByCodigo("FECDELEGPROP").getValor();
//			String firmanteProp = parametrosGeneralesService.getParametroGeneralByCodigo("FIRMAPROP").getValor();
//			String firmanteResol = parametrosGeneralesService.getParametroGeneralByCodigo("FIRMARESOL").getValor();
//			
//			
//			
//			String importeMensual=parametrosGeneralesService.getParametroGeneralByCodigo("ImpMensualS").getValor();
			
			
//			valoresParametros.put("DELEGAPROP", delegacionProp);
//			valoresParametros.put("DELEGARESOL", delegacionResol);
//			valoresParametros.put("FECDELEGARESOL", fechaDelegaResol);
//			valoresParametros.put("FECDELEGPROP", fechaDelegaProp);
//			valoresParametros.put("FIRMAPROP", firmanteProp);
//			valoresParametros.put("FIRMARESOL", firmanteResol);
//			
//			
//			valoresParametros.put("CJS", cargoJefaServicio);
//			valoresParametros.put("DEC", decreto);
//			valoresParametros.put("NJS", nombreJefaServicio);
//			valoresParametros.put("ASSASB", nombreAyuntamiento);
//			valoresParametros.put("CJSECCION", cargoJefeSeccion);
//			valoresParametros.put("NJSECCION", nombreJefeSeccion);
//			valoresParametros.put("CCONSEJERO", cargoConsejero);
//			valoresParametros.put("SDIRECTORA", seccionDirectora);
//			valoresParametros.put("NDIRECTORA", nombreDirectora);
//			
//			valoresParametros.put("ImpMensualS", importeMensual);
//		} catch (PersistenciaException e) {		
//			LogAsistente.error("ComunView- cargarValoresParametrosGeneralesPlantillas", e);
//			e.printStackTrace();
//		}
		
		return valoresParametros;
	}

	public String getEstiloAyuda() {
		return estiloAyuda;
	}

	public void setEstiloAyuda(String estiloAyuda) {
		this.estiloAyuda = estiloAyuda;
	}
	
}
