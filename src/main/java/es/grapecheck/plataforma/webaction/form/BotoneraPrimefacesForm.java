package es.grapecheck.plataforma.webaction.form;

import java.io.Serializable;
import java.lang.reflect.Method;

import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.component.html.HtmlGraphicImage;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;

import org.primefaces.component.commandbutton.CommandButton;

import es.grapecheck.plataforma.utiles.Constantes;
import es.grapecheck.plataforma.utiles.FacesUtils;
import es.grapecheck.plataforma.utiles.TipoBean;
import es.grapecheck.plataforma.utiles.TratamientoDeDatos;
import es.grapecheck.plataforma.webaction.bean.ComunView;


/**
 * Clase que contiene todos los botones de la botonera superior.
 * 
 * @author cesperilla
 */
@SuppressWarnings("serial")
public class BotoneraPrimefacesForm<T> extends ComunView implements Serializable  {

	
	private CommandButton botonAceptarAdvertencia; 
	
	private CommandButton botonAceptarConfirmEliBus;

	/**************************************************************************************************************/
	/******* BOTONES PARA LOS CONFIRMS *******/
	/**************************************************************************************************************/
	private CommandButton botonAceptarConfirmEliDet;

	private CommandButton botonAceptarConfirmSalirSinGuardar;

	/**
	 * Boton guardar en modificación (AJAX) - Muestra mensaje de ok.
	 */
	// private CommandButton botonGuardarModificar;
	/**
	 * Boton añadir - Navega a la pantalla de alta.
	 */
	private CommandButton botonAnadir;

	/**
	 * Boton de navegacion en detalle (AJAX).
	 */
	private CommandButton botonAnterior;
	/**
	 * Boton de ayuda (AJAX).
	 */
	private CommandButton botonAyuda;

	/**
	 * Boton buscar (AJAX).
	 */
	private CommandButton botonBuscar;
	/**
	 * Atributo botonCancelar.
	 */
	private CommandButton botonCancelar;
	/**
	 * Boton de confirmacion del mensaje editable.
	 */
	private CommandButton botonConfirmMensaje;
	/**
	 * Boton de confirmacion del mensaje editable, antes de guardar.
	 */
	private CommandButton botonConfirmMensajeGuardar;
	/**
	 * Boton eliminar en búsqueda (AJAX) - elimina los elementos seleccionados
	 * de la lista de busqueda.
	 */
	private CommandButton botonEliminarEnBusqueda;
	/**
	 * Boton eliminar en detalle - Pide confirm antes de eliminar y despues
	 * navega a la consulta.
	 */
	private CommandButton botonEliminarEnDetalleConfirm;
	/**************************************************************************************************************/
	/******* BOTONES BOTONERA *******/
	/**************************************************************************************************************/
	/**
	 * Boton guardar para un alta - Navega al guardar.
	 */
	private CommandButton botonGuardar;
	/**
	 * Boton de imprimir (AJAX).
	 */
	private CommandButton botonImprimir;
	/**
	 * Boton limpiar (AJAX).
	 */
	private CommandButton botonLimpiar;
	/**
	 * Boton OK.
	 */
	private CommandButton botonOK;
	private CommandButton botonOkYVolver;
	/**
	 * Boton de navegacion en detalle (AJAX).
	 */
	private CommandButton botonPrimero;
	/**
	 * Boton salir - Navega hasta la pantalla que se le indique en el faces.
	 */
	private CommandButton botonSalir;

	/**
	 * Boton de navegacion en detalle (AJAX).
	 */
	private CommandButton botonSiguiente;

	/**
	 * Boton de navegacion en detalle (AJAX).
	 */
	private CommandButton botonUltimo;

	private CommandButton botonVolverOkDeAlta;

	private CommandButton botonVolverOkDetalle;

	
	/**
	 * Imagen de la cabecera.
	 */
	private HtmlGraphicImage imagenHeaderBoton;
	
	/**
	 * Mensaje de la confirmacion.
	 */
	private HtmlOutputText mensajeBoton;
	/**
	 * Mensaje de la cabecera.
	 */
	private HtmlOutputText mensajeHeaderBoton;
	/**
	 * Mensaje ok.
	 */
	private String mensajeOKinfo;

	private String msgOkYVolver = "CAMBIAR";

	

	

	public BotoneraPrimefacesForm() {
		botonGuardar = new CommandButton();
		botonAnadir = new CommandButton();
		botonEliminarEnDetalleConfirm = new CommandButton();
		botonEliminarEnBusqueda = new CommandButton();
		botonBuscar = new CommandButton();
		botonLimpiar = new CommandButton();
		botonPrimero = new CommandButton();
		botonAnterior = new CommandButton();
		botonSiguiente = new CommandButton();
		botonUltimo = new CommandButton();
		botonImprimir = new CommandButton();
		botonAyuda = new CommandButton();
		botonSalir = new CommandButton();
		botonAceptarConfirmEliDet = new CommandButton();
		botonAceptarConfirmEliBus = new CommandButton();
		botonVolverOkDetalle = new CommandButton();
		botonAceptarAdvertencia = new CommandButton();
		botonVolverOkDeAlta = new CommandButton();

		botonAceptarConfirmSalirSinGuardar = new CommandButton();
		botonOK = new CommandButton();
		botonOK.setStyleClass(Constantes.ESTILO_BOTON);
		botonOK.setValue(Constantes.ACEPTAR);
		// boton ok para volver desde una pantalla
		botonOkYVolver = new CommandButton();
		mensajeBoton = new HtmlOutputText();
		botonCancelar = new CommandButton();
		// boton aceptar confirm
		botonConfirmMensaje = new CommandButton();
		botonConfirmMensaje.setStyleClass(Constantes.ESTILO_BOTON);
		botonConfirmMensaje.setValue(Constantes.ACEPTAR);
		botonConfirmMensaje.setImage(null);

		// boton aceptar confirm antes de guardar
		botonConfirmMensajeGuardar = new CommandButton();
		botonConfirmMensajeGuardar.setStyleClass(Constantes.ESTILO_BOTON);
		botonConfirmMensajeGuardar.setValue(Constantes.ACEPTAR);
	}

	public void activarBotonSinAccion(HtmlCommandButton boton) {
		// activar boton eliminar
		boton.setStyleClass(Constantes.ESTILO_BOTON_ACTIVADO);
		boton.setDisabled(false);
		// Habilitar la imagen del boton
	//	setImagenBoton(boton, "on");
	}

	public void activarBotonSinAccion(CommandButton boton) {
		// activar boton eliminar
		boton.setStyleClass(Constantes.ESTILO_BOTON_ACTIVADO);
		boton.setDisabled(false);
		boton.setStyle("cursor: pointer");
		// Habilitar la imagen del boton
	//	this.setImagenBotonAjax(boton,"ButtonActivo");
	}

	/**
	 * @author rquiros
	 * @param accionEliminar
	 *            - String
	 * @param objetoRecargarEliminar
	 *            - String (objeto que recargar)
	 */
	public void activarEliminarConfirmEnBusqueda(String objetoRecargarEliminar) {
		// activar el boton eliminar
		String bean = getNombreBeanActual();
		String accionEliminar = "#{" + bean + ".delete}";
		this.activarBotonSinAccion(this.getBotonEliminarEnBusqueda());
		// meter la accion al boton aceptar del confirm
		this.setAccion(this.getBotonAceptarConfirmEliBus(), accionEliminar);
		// ocultar el modalPanel al aceptar
		String panelAOcultar = "document.getElementById('formulario:panelConfirmEB').component.hide();";
		this.getBotonAceptarConfirmEliBus().setOnclick(panelAOcultar);
		// indicar el componente que recarga
		if (!TratamientoDeDatos.esNullCeroVacio(objetoRecargarEliminar))
			//this.getBotonAceptarConfirmEliBus().setRender(objetoRecargarEliminar);
			//lmpaz.primefaces
		    this.getBotonAceptarConfirmEliBus().setUpdate(objetoRecargarEliminar);
	}

	private void activarEliminarConfirmEnDetalle(String accionEliminar) {
		this.activarBotonSinAccion(this.getBotonEliminarEnDetalleConfirm());
		this.setAccion(this.getBotonAceptarConfirmEliDet(), accionEliminar);
	}

	public void activarImprimir() {
		this.getBotonImprimir().setImmediate(false);
	}

	/**
	 * Actualizar el campo Render.
	 * 
	 * @author rquiros
	 * @param boton- CommandButton
	 * @param idCampoActualizar - String
	 */
	public void actualizarRender(CommandButton boton, String idCampoActualizar) {
		String actualizar = idCampoActualizar;
		if (!TratamientoDeDatos.esNullCeroVacio(/*boton.getRender()*/boton.getUpdate())) {
			//luisma_pendiente . creo que la coma se cambia por un espacio para indicar mas campos de render
			actualizar = actualizar + Constantes.COMA+ TratamientoDeDatos.sNoNull(/*boton.getRender()*/boton.getUpdate());
		}
		//boton.setRender(actualizar);
		//lmpaz. primefaces
		boton.setUpdate(idCampoActualizar);
	}

	

	/**
	 * Acción para realizar una busqueda común. 1.- Habilitar eliminar 2 2.-
	 * Habilitar limpiar.
	 * 
	 * @author rquiros
	 * @param accionEliminar
	 *            - String (accion que hace el boton eliminar)
	 */
	public void busquedaComun(String objetoRecargarEliminar) {
		this.busquedaComun(objetoRecargarEliminar+ Constantes.RECARGAS_HIGHLIGHT, null);
	}

	/**
	 * Actualiza la botonera después de pulsar el botón buscar. 1 1.- Habilitar
	 * eliminar 2.- Habilitar limpiar.
	 * 
	 * @author rquiros
	 * @param accionEliminar
	 *            - String (accion que hace el boton eliminar)
	 * @param accionLimpiar
	 *            - String (accion que hace el boton limpiar)
	 */
	public void busquedaComun(String objetoRecargarEliminar,
			String accionLimpiar) {

		// activar el boton limpiar
		if (TratamientoDeDatos.esNullCeroVacio(accionLimpiar)) {
			String bean = getNombreBeanActual();
			// montar el boton limpiar genérico
			accionLimpiar = "#{" + bean + ".goLimpiar}";
		}

		this.meterAccionBotonAjax(this.getBotonLimpiar(),accionLimpiar,objetoRecargarEliminar+ ",botonEliminarEnBusqueda,botonLimpiar,criteriosBusqueda");

	}

	//luisma_pendiente
	private void detallesBotonesNavegacionDetalle() {
		// boton primero
		/*this.getBotonPrimero().setRender(Constantes.PANEL_CONTENIDO_CENTRAL);
		this.getBotonPrimero().setImmediate(true);
		this.getBotonPrimero().setExecute("@this");
		this.getBotonPrimero().setStatus(Constantes.STATUS_MOSTRAR_WAIT);
		// boton anterior
		this.getBotonAnterior().setRender(Constantes.PANEL_CONTENIDO_CENTRAL);
		this.getBotonAnterior().setImmediate(true);
		this.getBotonAnterior().setExecute("@this");
		this.getBotonAnterior().setStatus(Constantes.STATUS_MOSTRAR_WAIT);
		// boton siguiente
		this.getBotonSiguiente().setRender(Constantes.PANEL_CONTENIDO_CENTRAL);
		this.getBotonSiguiente().setImmediate(true);
		this.getBotonSiguiente().setExecute("@this");
		this.getBotonSiguiente().setStatus(Constantes.STATUS_MOSTRAR_WAIT);
		// boton ultimo
		this.getBotonUltimo().setRender(Constantes.PANEL_CONTENIDO_CENTRAL);
		this.getBotonUltimo().setImmediate(true);
		this.getBotonUltimo().setExecute("@this");
		this.getBotonUltimo().setStatus(Constantes.STATUS_MOSTRAR_WAIT);
		*/
	}

	public CommandButton getBotonAceptarAdvertencia() {
		return botonAceptarAdvertencia;
	}

	public CommandButton getBotonAceptarConfirmEliBus() {
		botonAceptarConfirmEliBus.setId("botonAceptarConfirmEliBus");
		return botonAceptarConfirmEliBus;
	}

	public CommandButton getBotonAceptarConfirmEliDet() {
		botonAceptarConfirmEliDet.setId("botonAceptarConfirmEliDet");
		return botonAceptarConfirmEliDet;
	}

	public CommandButton getBotonAceptarConfirmSalirSinGuardar() {
		return botonAceptarConfirmSalirSinGuardar;
	}

	public CommandButton getBotonAnadir() {
		botonAnadir.setId("botonAnadir");
		return botonAnadir;
	}

	public CommandButton getBotonAnterior() {
		botonAnterior.setId("botonAnterior");
		return botonAnterior;
	}

	public CommandButton getBotonAyuda() {
		botonAyuda.setId("botonAyuda");
		return botonAyuda;
	}

	public CommandButton getBotonBuscar() {
		botonBuscar.setId("botonBuscar");
		return botonBuscar;
	}

	public CommandButton getBotonCancelar() {
		return botonCancelar;
	}

	public CommandButton getBotonConfirmMensaje() {
		return botonConfirmMensaje;
	}

	public CommandButton getBotonConfirmMensajeGuardar() {
		return botonConfirmMensajeGuardar;
	}

	public CommandButton getBotonEliminarEnBusqueda() {
		botonEliminarEnBusqueda.setId("botonEliminarEnBusqueda");
		return botonEliminarEnBusqueda;
	}

	public CommandButton getBotonEliminarEnDetalleConfirm() {
		botonEliminarEnDetalleConfirm.setId("botonEliminarEnDetalleConfirm");
		return botonEliminarEnDetalleConfirm;
	}

	public CommandButton getBotonGuardar() {
		botonGuardar.setId("botonGuardar");
		return botonGuardar;
	}

	public CommandButton getBotonImprimir() {
		botonImprimir.setId("botonImprimir");
		return botonImprimir;
	}

	public CommandButton getBotonLimpiar() {
		botonLimpiar.setId("botonLimpiar");
		return botonLimpiar;
	}

	public CommandButton getBotonOK() {
		return botonOK;
	}

	public CommandButton getBotonOkYVolver() {
		return botonOkYVolver;
	}

	public CommandButton getBotonPrimero() {
		botonPrimero.setId("botonPrimero");
		return botonPrimero;
	}

	public CommandButton getBotonSalir() {
		botonSalir.setId("botonSalir");
		return botonSalir;
	}

	public CommandButton getBotonSiguiente() {
		botonSiguiente.setId("botonSiguiente");
		return botonSiguiente;
	}

	public CommandButton getBotonUltimo() {
		botonUltimo.setId("botonUltimo");
		return botonUltimo;
	}

	public CommandButton getBotonVolverOkDeAlta() {
		return botonVolverOkDeAlta;
	}

	public CommandButton getBotonVolverOkDetalle() {
		return botonVolverOkDetalle;
	}


	public HtmlGraphicImage getImagenHeaderBoton() {
		return imagenHeaderBoton;
	}
	/****************************************************************************************************************************/
	

	public HtmlOutputText getMensajeBoton() {
		return mensajeBoton;
	}

	public HtmlOutputText getMensajeHeaderBoton() {
		return mensajeHeaderBoton;
	}
	
	public String getMensajeOKinfo() {
		return mensajeOKinfo;
	}

	public String getMsgOkYVolver() {
		return msgOkYVolver;
	}


	
	/**
	 * Se define un nuevo metodo para cuando haya que redefinir el goLimpiar, se
	 * pueda usar este.
	 * 
	 * @author rquiros
	 */
	public void goLimpiar() {
		// desactivar el boton eliminar
		this.quitarAccionBotonAjax(this.getBotonEliminarEnBusqueda());

		// desactivar el boton limpiar
		this.quitarAccionBotonAjax(this.getBotonLimpiar());

		// desactivar el boton imprimir
		this.quitarAccionBotonAjax(this.getBotonImprimir());
		
		// limpiar el resultado de la busqueda
		this.setElementList(null);

		// limpiar completamente el formulario los criterios de b�squeda
		this.limpiarCamposFormulario();

		this.limpiarCamposFormBusqueda();

	}


	/**
	 * Insertar accion para un boton.
	 * 
	 * @author rquiros
	 * @param boton
	 *            - HtmlCommandButton
	 * @param action
	 *            - String
	 */
	public void meterAccionBoton(HtmlCommandButton boton, String action) {
		this.meterAccionBoton(boton, action, true);
	}

	/**
	 * Insertar accion para un boton.
	 * 
	 * @author rquiros
	 * @param boton
	 *            - HtmlCommandButton
	 * @param action
	 *            - String
	 */
	public void meterAccionBoton(HtmlCommandButton boton, String action,
			Boolean botonMenu) {
		setAccion(boton, action);
		// estilo
		boton.setStyleClass(Constantes.ESTILO_BOTON_ACTIVADO);
		// habilitar (por si estaba deshabilitado)
		boton.setDisabled(false);
		// indicar que el cursor sea una mano
		boton.setStyle("cursor: pointer");
		
		//lmpaz 21/10/2015. la nueva botonera solo se le cambia DISABLED
		// Habilitar la imagen del boton
		/*if (botonMenu) {
			setImagenBoton(boton, "on");
		}*/
	}

	/**
	 * Insertar accion para un boton AJAX. Sin indicarle objeto que debe
	 * recargar.
	 * 
	 * @author rquiros
	 * @param boton
	 *            - CommandButton
	 * @param action
	 *            - String
	 */
	public void meterAccionBoton(CommandButton boton, String action) {
		meterAccionBoton(boton, action, null, true);
	}

	/**
	 * Insertar accion para un boton AJAX. Sin indicarle objeto que debe
	 * recargar.
	 * 
	 * @author rquiros
	 * @param boton
	 *            - CommandButton
	 * @param action
	 *            - String
	 */
	public void meterAccionBoton(CommandButton boton, String action,
			Boolean botonMenu) {
		meterAccionBoton(boton, action, null, botonMenu);
	}

	/**
	 * Insertar accion para un boton AJAX.
	 * 
	 * @author rquiros
	 * @param boton
	 *            - HtmlCommandButton
	 * @param action
	 *            - String
	 * @param objetoRecargar
	 *            - String
	 */
	public void meterAccionBoton(CommandButton boton, String action,
			String objetoRecargar) {
		this.meterAccionBoton(boton, action, objetoRecargar, true);
	}

	public void meterAccionListenerBoton(CommandButton boton, String action,
			String objetoRecargar, Boolean botonMenu) {
		// meter accion al boton
		//this.setAccion(boton, action);
		this.setAccionListener(boton, action); //primefaces lmpaz
		
		// indicar el componente que recarga
		if (!TratamientoDeDatos.esNullCeroVacio(objetoRecargar)) {
			// respetar lo que traiga el Render
			String actualizar = objetoRecargar;
			if (!TratamientoDeDatos.esNullCeroVacio(/*boton.getRender()*/boton.getUpdate())) {
				actualizar = actualizar + Constantes.COMA+ TratamientoDeDatos.sNoNull(/*boton.getRender()*/boton.getUpdate());
			}
			//boton.setRender(actualizar);
			boton.setUpdate(actualizar);
		}
		// estilo
		//boton.setStyleClass(Constantes.ESTILO_BOTON_ACTIVADO);
		boton.setStyleClass("botonera");
		boton.setDisabled(false);
		boton.setStyle("cursor: pointer");
		// Habilitar la imagen del boton
		
		/* //lmpaz 21/10/2015. la nueva botonera solo se le cambia DISABLED
		if (botonMenu) {
			//setImagenBotonAjax(boton, "on");
			setImagenBotonAjax(boton,"ButtonActivo");
		}*/
	}
	
	/**
	 * Insertar accion para un boton AJAX.
	 * 
	 * @author lmpaz
	 * @param boton- HtmlCommandButton
	 * @param action- String. Metodo que devuelve String o un string de navegacion. JSF 2.0 action=navegacion
	 * @param objetoRecargar
	 *            - String
	 */
	public void meterAccionBoton(CommandButton boton, String action,String objetoRecargar, Boolean botonMenu) {
		// meter accion al boton
		this.setAccion(boton, action);
		//this.setAccionListener(boton, action); //primefaces lmpaz
		
		// indicar el componente que recarga
		if (!TratamientoDeDatos.esNullCeroVacio(objetoRecargar)) {
			// respetar lo que traiga el Render
			String actualizar = objetoRecargar;
			if (!TratamientoDeDatos.esNullCeroVacio(/*boton.getRender()*/boton.getUpdate())) {
				actualizar = actualizar + Constantes.COMA+ TratamientoDeDatos.sNoNull(/*boton.getRender()*/boton.getUpdate());
			}
			//boton.setRender(actualizar);
			boton.setUpdate(actualizar);
		}
		// estilo
		//boton.setStyleClass(Constantes.ESTILO_BOTON_ACTIVADO);
		if(botonMenu.equals(true)){
			boton.setStyleClass("botonera");
			boton.setStyle("cursor: pointer");
		}
		boton.setDisabled(false);
		
		
		//lmpaz 21/10/2015. la nueva botonera solo se le cambia DISABLED
		// Habilitar la imagen del boton
		/*if (botonMenu) {
			//setImagenBotonAjax(boton, "on");
			setImagenBotonAjax(boton,"ButtonActivo");
		}*/
	}

	public void meterAccionBotonAjax(CommandButton boton, String action) {
		meterAccionBoton(boton, action, null, true);
	}

	public void meterAccionBotonAjax(CommandButton boton, String action, String objetoRecargar) {
		meterAccionBoton(boton, action, objetoRecargar);
	}

	/**
	 * Montar la botonera para una pantalla de consulta comun. BOTONES: (A�ADIR)
	 * (BUSCAR) (IMPRIMIR) (AYUDA) (SALIR - BORRANDO)
	 * 
	 * @author rquiros
	 */
	private void montarBotoneraConsultaMantenimientoComun(TipoBean tipoBean) {

		String bean = this.getNombreBeanActual();

		this.montarBotoneraInicial(tipoBean);
		
		//BUSCAR
		//this.getBotonBuscar().setProcess("@this"); Con esto no envia los valores de los criterios busqeda al bean
		String accionListenerBuscar = "#{" + bean + ".findElements}"; 
		this.meterAccionListenerBoton(this.getBotonBuscar(),accionListenerBuscar,"panelContenidoCentral botonEliminarEnBusqueda botonLimpiar botonImprimir",true);
		//luisma_doc: tenemos que poner que al buscar actualice(update) panelContenidoCentral(esta en defaultLayout) y asi podemos ponerle a la tabla div_busqueda rendered=xxxx. 
		//            Si ponemos en el update el div_busqueda siempre lo va a mostrar se la pela la condicion rendered='xxxx'
		
		
		
		//Añadir: nada se lo he puesto directamente en la botoneraPrimefaces.xhtml   action=bean.goalta
		String accionAlta = "#{" + bean + "." + Constantes.METODO_ANIADIR + "}";
		this.meterAccionBoton(this.getBotonAnadir(), accionAlta);
		

		
		//SALIR : nada se lo he puesto directamente en la botoneraPrimefaces.xhtml   action=bean.goSalirConsulta
		String accionSalirPorDefecto = "#{" + bean + ".goSalirConsulta}";
		this.meterAccionBoton(this.getBotonSalir(), accionSalirPorDefecto);
		this.getBotonSalir().setImmediate(true);
		//this.getBotonSalir().setProcess("@this");
		
		
		/*
		 IMPRIMIR
		String accionImprimirConsulta = "#{" + bean + ".goImprimirConsulta}";
		this.getBotonImprimir().setImmediate(true);
		this.getBotonImprimir().setOnclick(null);
		this.meterAccionBoton(this.getBotonImprimir(), accionImprimirConsulta);
        */
		
		/*this.getBotonEliminarEnBusqueda()
				.setOnclick(
						"if(comprobarCheckListaBusqueda() == false){ Richfaces.showModalPanel('panelObjSelBusqueda'); return false;} else {document.getElementById('formulario:panelConfirmEB').component.show(); return false;}");
				*/
	}

	/**
	 * Montar la botonera para una pantalla de detalle comun. Activa los
	 * botones: <br>
	 * ALTA: (GUARDAR) ... (IMPRIMIR) (AYUDA) (SALIR) <br>
	 * DETALLE: (GUARDAR_MODIFICAR) (ELIMINAR) ... (IMPRIMIR) (AYUDA) (SALIR)
	 * 
	 * @author rquiros
	 */
	private void montarBotoneraDetalleMantenimientoComun(TipoBean tipoBean) {
		String bean = getNombreBeanActual();

		this.montarBotoneraInicial(tipoBean);
		
		//BOTON SALIR
		String accionSalirPorDefecto = "#{" + bean + ".goSalirDetalle}";
		this.meterAccionBoton(this.getBotonSalir(), accionSalirPorDefecto);
		this.getBotonSalir().setImmediate(true); //luisma_pendiente:Tengo dudas de si es necesario
		//this.getBotonSalir().setExecute("@this");

		//BOTON GUARDAR
		if (tipoBean.equals(TipoBean.DETALLE)) {
			
			String accionGuardarDetalle = "#{" + bean + ".update}";
			this.meterAccionListenerBoton(this.getBotonGuardar(), accionGuardarDetalle, "", true); 
			//this.getBotonGuardar().setProcess("@this"); //lmpaz.26/10/2015. Con el @this no recoge los datos en el backing bean
			
			//BOTON ELIMINAR
			//activamos el boton EliminarBusqueda de la botonera y cual pulse mostramos confirm. El aceptar del confirm será  accionEliminarEnDetalle
			this.getBotonEliminarEnBusqueda().setDisabled(false);
			this.getBotonEliminarEnBusqueda().setStyle("cursor: pointer");
			this.getBotonEliminarEnBusqueda().setOnclick("PF('confirmDeleteDetalle').show();");
			
			//BOTON MENSAJE INFO-DETALLE. Volvemos a la consulta depúes de eliminar.
			String accionVolverOkDeAlta = "#{" + bean + ".goSalirDetalle}";
			this.meterAccionBoton(this.getBotonVolverOkDeAlta(),accionVolverOkDeAlta,null,false);//luisma_ojo: se poner que el boton NO es botonera, sino pone el estilo mal
			
			//IMPRIMIR en Detalle: comprobar si hay algun registro seleccionado, si no mensajito. si hay alguno seleccionado ventana impresion
			//String accionListenerImprimir = "#{" + this.getNombreBeanActual() + ".imprimirPlantilla}";
			//this.meterAccionListenerBoton(this.getBotonImprimir(),accionListenerImprimir,"",true);
			this.getBotonImprimir().setDisabled(false);
			
			if(bean.contains("Grupos")){
				this.getBotonImprimir().setOnclick("PF('modalImpresionGrupos').show()");
			}else{
				this.getBotonImprimir().setOnclick("PF('modalImpresion').show()");
			}
			
		} else {
			String accionGuardar = "#{" + bean + ".add}";
			this.meterAccionListenerBoton(this.getBotonGuardar(), accionGuardar, "", true); 
			//this.getBotonGuardar().setProcess("@this");  //lmpaz.26/10/2015. Con el @this no recoge los datos en el backing bean
			
			//BOTON MENSAJE INFO-DETALLE. Volvemos a la consulta depúes de guardar nuevo.
			String accionVolverOkDeAlta = "#{" + bean + ".goSalirDetalle}";
			this.meterAccionBoton(this.getBotonVolverOkDeAlta(),accionVolverOkDeAlta,null,false);//luisma_ojo:si poner que el boton no es botonera
		}

	}

	/**
	 * d Inicializa la botonera. Pinta toda la botonera con los botones
	 * deshabilitados menos: - Imprimir: #{bean.goImprimir} - solo lo activa
	 * para la búsqueda y modicación, no se activa para las pantallas de alta -
	 * Ayuda: #{bean.goAyuda} - Salir: #{bean.goSalir}
	 * 
	 * @author rquiros
	 */
	private void montarBotoneraInicial(TipoBean tipoBean) {
		String bean = this.getNombreBeanActual();
		this.quitarAccionBoton(this.getBotonGuardar());
		// ********** ANIADIR **********
		this.quitarAccionBoton(this.getBotonAnadir());
		// Para que no valide al añadir
		//this.getBotonAnadir().setImmediate(true);
		
		//IMPRIMIR. Lo desactivamos aki
		this.quitarAccionBoton(this.getBotonImprimir());
		
		// ELIMINAR BUSQUEDA
		this.quitarAccionBotonAjax(this.getBotonEliminarEnBusqueda());
		// quitar validación al boton eliminar en consulta
		//this.getBotonEliminarEnBusqueda().setImmediate(true); lmpaz 23/10/2015
		
		/*
		// eliminar en pantalla detalle
		this.quitarAccionBotonAjax(this.getBotonEliminarEnDetalleConfirm());
		// quitar validación al boton eliminar en detalle
		this.getBotonEliminarEnDetalleConfirm().setImmediate(true);
		
		// ********** ELIMINAR **********
		if (tipoBean.equals(TipoBean.ALTA) || tipoBean.equals(TipoBean.DETALLE)) {
			// ELIMINAR EN DETALLE
			this.getBotonEliminarEnDetalleConfirm().setStyleClass(
					Constantes.ESTILO_BOTON);
			this.getBotonEliminarEnBusqueda().setStyleClass(
					Constantes.ESTILO_OCULTAR_BOTON);
		} else {
			// ELIMINAR EN BUSQUEDA
			this.getBotonEliminarEnBusqueda().setStyleClass(
					Constantes.ESTILO_BOTON);
			// ELIMINAR EN DETALLE
			this.getBotonEliminarEnDetalleConfirm().setStyleClass(
					Constantes.ESTILO_OCULTAR_BOTON);
		}*/
		
		// ********** BUSCAR **********
		this.quitarAccionBotonAjax(this.getBotonBuscar());
		// ********** LIMPIAR **********
		this.quitarAccionBotonAjax(this.getBotonLimpiar());
		// quitar validación al boton limpiar
		//this.getBotonLimpiar().setImmediate(true); lmpaz 23/10/2015
		
		
		/*
		// ********** PRIMERO primero
		this.quitarAccionBotonAjax(this.getBotonPrimero());
		this.getBotonPrimero().setImmediate(true);
		// ********** ANTERIOR **********
		this.quitarAccionBotonAjax(this.getBotonAnterior());
		this.getBotonAnterior().setImmediate(true);
		// ********** SIGUIENTE **********
		this.quitarAccionBotonAjax(this.getBotonSiguiente());
		this.getBotonSiguiente().setImmediate(true);
		
		
		// ********** ULTIMO **********
		this.quitarAccionBotonAjax(this.getBotonUltimo());
		this.getBotonUltimo().setImmediate(true);
		// ********** IMPRIMIR **********
		boolean activarImprimir = true;
		// comprobar si es pantalla de alta --> no activar el imprimir
		if (!TratamientoDeDatos.esNullCeroVacio(bean)
				&& tipoBean.equals(TipoBean.ALTA)) {
			activarImprimir = false;
		}
		if (activarImprimir) {
			this.activarBotonSinAccion(this.getBotonImprimir());
			this.getBotonImprimir().setOnclick("window.print(); return false;");
		} else {
			this.quitarAccionBoton(this.getBotonImprimir());
		}
		// ********** AYUDA **********
		this.activarBotonSinAccion(this.getBotonAyuda());
		String accionBotonAyuda = "#{" + bean + ".goAyuda}";
		this.meterAccionBoton(this.getBotonAyuda(), accionBotonAyuda);

		// montar los botones de navegaci�n
		this.detallesBotonesNavegacionDetalle();
		*/
	}

	public void montarBotoneraMantenimientoComun(TipoBean tipoBean) {
		if (tipoBean.equals(TipoBean.CONSULTA)) {
			this.montarBotoneraConsultaMantenimientoComun(tipoBean);
		} else {
			this.montarBotoneraDetalleMantenimientoComun(tipoBean);

		}
		// this.obtenerRutaSalirDirecta();
	}

	public void mostrarMensajeActualiazdoCorrectamente() {
		FacesUtils.putMessageInfo("mensaje.comun.guardado.correctamente");
		this.meterAccionBoton(this.getBotonConfirmMensaje(),"#{altaTipoDocumentalBean.goSalirDetalle}", false);
	}

	public void mostrarMensajeGuardadoCorrectamente() {
		FacesUtils.putMessageInfo("mensaje.comun.guardado.correctamente");
		this.meterAccionBoton(this.getBotonConfirmMensaje(),"#{altaTipoDocumentalBean.goSalirDetalle}", false);
	}

	public String obtenerMetodo(String name) {
		String nombreMetodo = null;
		// Obtener los metodos del objeto.
		Method[] methods = this.getClass().getMethods();
		for (Method meth : methods) {
			if (meth.getName().toLowerCase().startsWith(name)) {
				nombreMetodo = meth.getName();
			}
		}
		return nombreMetodo;
	}

	public String obtenerRutaSalirDirecta() {
		String rutaDirecta = "";
		UIComponent component = FacesUtils.getComponentePantalla("formulario:rutaSalir");

		if (!TratamientoDeDatos.esNullCeroVacio(component)) {
			String value = (String) component.getAttributes().get("value");
			if (!TratamientoDeDatos.esNullCeroVacio(value)) {
				rutaDirecta = value;
			}
		}
		return rutaDirecta;
	}

	/**
	 * Ocultar boton de la botonera.
	 * 
	 * @param boton
	 *            - HtmlCommandButton
	 */
	public void ocultarBoton(HtmlCommandButton boton) {
		boton.setStyleClass(Constantes.ESTILO_OCULTAR_BOTON);
	}

	public void ocultarBoton(CommandButton boton) {
		ocultarBotonAjax(boton);
	}

	/**
	 * Ocultar boton ajax de la botonera.
	 * 
	 * @param boton
	 *            - CommandButton
	 */
	public void ocultarBotonAjax(CommandButton boton) {
		boton.setStyleClass(Constantes.ESTILO_OCULTAR_BOTON);
	}

	/**
	 * Quitar la acción a un boton.
	 * 
	 * @author rquiros
	 * @param boton
	 *            - HtmlCommandButton
	 */
	public void quitarAccionBoton(HtmlCommandButton boton) {
		// eliminar la acción
		boton.setActionExpression(null);
		// deshabilitar el boton
		boton.setDisabled(true);
		// pinta el botón como oculto
		boton.setStyleClass(Constantes.ESTILO_BOTON);
		// cursor por defecto
		boton.setStyle("cursor: default");
		// Deshabilitar la imagen del boton (pintar el boton en gris)
		
		//lmpaz 21/10/2015. la nueva botonera solo se le cambia DISABLED
		//setImagenBoton(boton, "off");
	}

	/**
	 * Quitar la acción a un boton primefaces.
	 * @author rquiros and lmpaz
	 * @param boton- CommandButton
	 */
	public void quitarAccionBoton(CommandButton boton) {
		boton.setActionExpression(null);
		boton.setUpdate(null);
		//setAccionListener(boton, "");
		
		// boton.setRender(null);
		//lmpaz. quitamos disbled a ver si asi cambia la imagen
		boton.setDisabled(true); 
		//boton.setStyleClass(Constantes.ESTILO_BOTON);
		
		// cursor por defecto
		boton.setStyle("cursor: default");
		
		//lmpaz 21/10/2015. la nueva botonera solo se le cambia DISABLED
		//setImagenBotonAjax(boton, "off");
		//setImagenBotonAjax(boton, "ButtonNoActivo");
	}

	public void quitarAccionBotonAjax(CommandButton boton) {
		quitarAccionBoton(boton);
	}

	/**
	 * Settear la acci�n a un boton.
	 * @param boton- CommandButton
	 * @param action- String
	 */
	public void setAccion(HtmlCommandButton boton, String action) {
		// preparar el methodExpression
		MethodExpression methodExpression = FacesContext
				.getCurrentInstance()
				.getApplication()
				.getExpressionFactory()
				.createMethodExpression(
						FacesContext.getCurrentInstance().getELContext(),
						action, null, new Class<?>[0]);
		// meter accion al boton
		boton.setActionExpression(methodExpression);
	}

	/**
	 * Settear la acci�ón a un boton. Debe devolver un string de navegacion
	 * @author rquiros and lmpaz
	 * @param boton - CommandButton
	 * @param action - String
	 */
	public void setAccion(CommandButton boton, String action) {
		// preparar el methodExpression
		MethodExpression methodExpression = FacesContext
				.getCurrentInstance()
				.getApplication()
				.getExpressionFactory()
				.createMethodExpression(
						FacesContext.getCurrentInstance().getELContext(),
						action, null, new Class<?>[0]);
		// meter accion al boton
		boton.setActionExpression(methodExpression);
	}
	
	public static MethodExpression createMethodExpression(String methodExpression, Class<?> valueType) {
	      return FacesContext.getCurrentInstance().getApplication().getExpressionFactory().createMethodExpression(FacesContext.getCurrentInstance().getELContext(),methodExpression, valueType, new Class<?>[0]);
	   }
	
	/*private MethodExpression createMethodExpression(String action) {
	  final Class<?>[] paramTypes = new Class<?>[0];
	  MethodExpression methodExpression = FacesContext.getCurrentInstance().getApplication().getExpressionFactory().createMethodExpression(FacesContext.getCurrentInstance().getELContext(),action, null, paramTypes);
	  return methodExpression;
		}
*/
	private ValueExpression createValueExpression(String binding,Class<String> clazz) {
	  final ValueExpression ve = FacesContext.getCurrentInstance().getApplication().getExpressionFactory().createValueExpression(FacesContext.getCurrentInstance().getELContext(), binding, String.class);
	  return ve;
		}
	
	
	
	public void setAccionListener(CommandButton boton, String actionListener) {
		
		MethodExpression methodExpression = createMethodExpression(actionListener, String.class);
		boton.setActionExpression(methodExpression);
		
		//luisma_doc. con esto lo que hacemos es darle un binding
		//rsanchez 20151023 Comento la siguiente linea, ya incluimos la accionListener mediante el metodo anterior
		//boton.setValueExpression("actionListener",createValueExpression(actionListener,String.class));
	}
	
	/**
	 * cesperilla 07-01-2015
	 * se ejecutará tras realizar una búsqueda con resultados en una pantalla de consulta
	 * activa el botón limpiar y el boton eliminar 
	 */
	public void actualizarBotoneraBusqueda(){ 
		if(!TratamientoDeDatos.esListaNullVacia(this.getElementList())){
			
			//BOTON LIMPIAR
		    String accionListenerLimpiar = "#{" + this.getNombreBeanActual() + ".goLimpiar}";
			this.meterAccionListenerBoton(this.getBotonLimpiar(),accionListenerLimpiar,"panelContenidoCentral,botonLimpiar,criteriosBusqueda,botonEliminarEnBusqueda,botonImprimir",true);
						
			//BOTON ELIMINAR CONSULTA
			//cesperilla 07-01-2015 si el tipo de usuario es consultor no permitimos eliminar en búsqueda
			this.getBotonEliminarEnBusqueda().setDisabled(false);
			this.getBotonEliminarEnBusqueda().setStyle("cursor: pointer");
			String accionListenerEliminarBusqueda = "#{" + this.getNombreBeanActual() + ".validarRegistrosSeleccionados}";
			this.meterAccionListenerBoton(this.getBotonEliminarEnBusqueda(),accionListenerEliminarBusqueda,Constantes.TABLA_DIV_BUSQUEDA,true);
			
			//IMPRIMIR en consulta: comprobar si hay algun registro seleccionado, si no mensajito. si hay alguno seleccionado ventana impresion
			String accionListenerImprimir = "#{" + this.getNombreBeanActual() + ".validarRegistrosSeleccionados}";
			this.meterAccionListenerBoton(this.getBotonImprimir(),accionListenerImprimir,"",true);
		}
		else{
			//mostramos mensajito no hay registros
			mostrarConfirmGenericoSinCancelar(FacesUtils.getValueMensajesProperties("etiqueta.mensaje.inforegistro"), FacesUtils.getValueMensajesProperties("etiqueta.cabecera.consulta"), Constantes.SEVERITY_ALERT, this.getActionListenerAceptarConfirmGenerico());
		}
	
	}
	

	/**
	 * Activar el boton de eliminar en detalle, pide confirmacion antes de
	 * eliminar. 1.- El boton eliminar solamente activa el modalPanel que pedirá
	 * la confirmación. 2.- El aceptar del modalPanel será el que realize la
	 * acción indicada. 3.- En el método que se indique en el parametro action,
	 * una vez finalizado se indica: // mostrar el mensaje de ok para luego
	 * navegar a la pantalla de búsqueda this.mostrarMensajeOkNavegando(); 4.-
	 * Muestra un modalPanel indicando que la operación se realizo con exito y
	 * un boton ok para navegar.
	 * 
	 * @author rquiros
	 * @param action
	 *            - String
	 */
	public void setAccionEliminarDetalle(String action) {
		// activar el boton eliminar en detalle para insertarle acción
		activarBotonSinAccion(this.getBotonEliminarEnDetalleConfirm());
		// meter accion al boton
		setAccion(this.getBotonAceptarConfirmEliDet(), action);
		// estilo
		this.getBotonAceptarConfirmEliDet().setStyleClass(
				Constantes.ESTILO_BOTON_ACTIVADO);
		this.getBotonAceptarConfirmEliDet().setDisabled(false);
		// String panelAOcultar =
		// "#{rich:component('panelConfirmEDet')}.hide(); return false";
		// this.getBotonAceptarConfirmEliDet().setOnclick(panelAOcultar);
	}

	public void setBotonAceptarAdvertencia(
			CommandButton botonAceptarAdvertencia) {
		this.botonAceptarAdvertencia = botonAceptarAdvertencia;
	}

	public void setBotonAceptarConfirmEliBus(
			CommandButton botonAceptarConfirmEliBus) {
		this.botonAceptarConfirmEliBus = botonAceptarConfirmEliBus;
	}

	public void setBotonAceptarConfirmEliDet(
			CommandButton botonAceptarConfirmEliDet) {
		this.botonAceptarConfirmEliDet = botonAceptarConfirmEliDet;
	}

	public void setBotonAceptarConfirmSalirSinGuardar(
			CommandButton botonAceptarConfirmSalirSinGuardar) {
		this.botonAceptarConfirmSalirSinGuardar = botonAceptarConfirmSalirSinGuardar;
	}

	public void setBotonAnadir(CommandButton botonAnadir) {
		this.botonAnadir = botonAnadir;
	}

	public void setBotonAnterior(CommandButton botonAnterior) {
		this.botonAnterior = botonAnterior;
	}

	public void setBotonAyuda(CommandButton botonAyuda) {
		this.botonAyuda = botonAyuda;
	}

	public void setBotonBuscar(CommandButton botonBuscar) {
		this.botonBuscar = botonBuscar;
	}

	public void setBotonCancelar(CommandButton botonCancelar) {
		this.botonCancelar = botonCancelar;
	}

	public void setBotonConfirmMensaje(CommandButton botonConfirmMensaje) {
		this.botonConfirmMensaje = botonConfirmMensaje;
	}

	public void setBotonConfirmMensajeGuardar(
			CommandButton botonConfirmMensajeGuardar) {
		this.botonConfirmMensajeGuardar = botonConfirmMensajeGuardar;
	}

	public void setBotonEliminarEnBusqueda(
			CommandButton botonEliminarEnBusqueda) {
		this.botonEliminarEnBusqueda = botonEliminarEnBusqueda;
	}

	public void setBotonEliminarEnDetalleConfirm(
			CommandButton botonEliminarEnDetalleConfirm) {
		this.botonEliminarEnDetalleConfirm = botonEliminarEnDetalleConfirm;
	}

	/**
	 * Activar los botones de navegacion en la pantalla de detalle.
	 * 
	 * @param anteriores
	 *            - boolean
	 * @param siguientes
	 *            - boolean
	 * @param beanAlta
	 *            - String
	 */
	public void setBotonesPaginacion(boolean anteriores, boolean siguientes,
			String beanAlta) {
		if (anteriores) {
			/** Final de la lista */
			this.quitarAccionBotonAjax(this.getBotonSiguiente());
			this.quitarAccionBotonAjax(this.getBotonUltimo());
		} else {
			this.meterAccionBotonAjax(this.getBotonSiguiente(), "#{" + beanAlta
					+ ".goSiguiente}", null);
			this.meterAccionBotonAjax(this.getBotonUltimo(), "#{" + beanAlta
					+ ".goUltimo}", null);
			this.getBotonSiguiente().setOnclick(
					"if (salirSinGuardarSiguiente()) {return false;}");
			this.getBotonSiguiente().setOncomplete(
					"onLoadCargarFormulario (); ponerFocoPrimerCampo ();");
			this.getBotonUltimo().setOnclick(
					"if (salirSinGuardarUltimo()) {return false;}");
			this.getBotonUltimo().setOncomplete(
					"onLoadCargarFormulario (); ponerFocoPrimerCampo ();");
		}
		if (siguientes) {
			/** Final de la lista */
			this.quitarAccionBotonAjax(this.getBotonPrimero());
			this.quitarAccionBotonAjax(this.getBotonAnterior());
		} else {
			this.meterAccionBotonAjax(this.getBotonPrimero(), "#{" + beanAlta
					+ ".goPrimero}", null);
			this.meterAccionBotonAjax(this.getBotonAnterior(), "#{" + beanAlta
					+ ".goAnterior}", null);
			this.getBotonPrimero().setOnclick(
					"if (salirSinGuardarPrimero()) {return false;}");
			this.getBotonPrimero().setOncomplete(
					"onLoadCargarFormulario (); ponerFocoPrimerCampo ();");
			this.getBotonAnterior().setOnclick(
					"if (salirSinGuardarAnterior()) {return false;}");
			this.getBotonAnterior().setOncomplete(
					"onLoadCargarFormulario (); ponerFocoPrimerCampo ();");
		}
	}

	public void setBotonGuardar(CommandButton botonGuardar) {
		this.botonGuardar = botonGuardar;
	}
	public void setBotonImprimir(CommandButton botonImprimir) {
		this.botonImprimir = botonImprimir;
	}
	public void setBotonLimpiar(CommandButton botonLimpiar) {
		this.botonLimpiar = botonLimpiar;
	}
	public void setBotonOK(CommandButton botonOK) {
		this.botonOK = botonOK;
	}
	public void setBotonOkYVolver(CommandButton botonOkYVolver) {
		this.botonOkYVolver = botonOkYVolver;
	}
	public void setBotonPrimero(CommandButton botonPrimero) {
		this.botonPrimero = botonPrimero;
	}
	public void setBotonSalir(CommandButton botonSalir) {
		this.botonSalir = botonSalir;
	}

	public void setBotonSiguiente(CommandButton botonSiguiente) {
		this.botonSiguiente = botonSiguiente;
	}

	public void setBotonUltimo(CommandButton botonUltimo) {
		this.botonUltimo = botonUltimo;
	}

	public void setBotonVolverOkDeAlta(CommandButton botonVolverOkDeAlta) {
		this.botonVolverOkDeAlta = botonVolverOkDeAlta;
	}

	public void setBotonVolverOkDetalle(CommandButton botonVolverOkDetalle) {
		this.botonVolverOkDetalle = botonVolverOkDetalle;
	}

	public void setImagenHeaderBoton(HtmlGraphicImage imagenHeaderBoton) {
		this.imagenHeaderBoton = imagenHeaderBoton;
	}

	public void setMensajeBoton(HtmlOutputText mensajeBoton) {
		this.mensajeBoton = mensajeBoton;
	}
	public void setMensajeHeaderBoton(HtmlOutputText mensajeHeaderBoton) {
		this.mensajeHeaderBoton = mensajeHeaderBoton;
	}
	public void setMensajeOKinfo(String mensajeOKinfo) {
		this.mensajeOKinfo = mensajeOKinfo;
	}
	public void setMsgOkYVolver(String msgOkYVolver) {
		this.msgOkYVolver = msgOkYVolver;
	}

}