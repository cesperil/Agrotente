package es.grapecheck.plataforma.utiles;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.el.MethodExpression;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.omnifaces.util.Ajax;
import org.omnifaces.util.Components;
import org.omnifaces.util.Messages;
import org.primefaces.component.commandbutton.CommandButton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import com.sun.faces.component.visit.FullVisitContext;

/**
 * <p>
 * FacesUtils
 * </p>
 * Clase estática que implementa métodos para ayudar a utilizar FacesContext.
 * 08/02/2010
 * 
 * @author rsanchez
 */
public class FacesUtils {

	private static final Logger LOG = LoggerFactory.getLogger(FacesUtils.class);

	
    /*  luisma no compila
 	@SuppressWarnings("unchecked")
	public static Collection<String> obtenerRenderIds() {
		PartialViewContext partialViewContext = FacesContext
				.getCurrentInstance().getPartialViewContext();
		Collection<String> renderIds = partialViewContext.getRenderIds();

		if (renderIds.isEmpty() && partialViewContext instanceof ExtendedPartialViewContextImpl) {
			try {
				Field componentRenderIds = ExtendedPartialViewContextImpl.class
						.getDeclaredField("componentRenderIds");
				componentRenderIds.setAccessible(true);
				renderIds = (Collection<String>) componentRenderIds
						.get(partialViewContext);
			} catch (Exception e) {
				// Handle.
			}
		}
		return renderIds;
	}*/

	/**
	 * cesperilla 16-12-2015 cambia el valor de un atributo  de un componente
	 * @param idComponente
	 */
	public static void cambiarAtributoComponente(String idComponente, String nombreAtributo, String valorAtributo){
		 UIViewRoot view = FacesContext.getCurrentInstance().getViewRoot(); 
		 UIComponent comp= view.findComponent(idComponente); 
		 if(!TratamientoDeDatos.esNullCeroVacio(comp)){
			 Map<String, Object> attrMap = comp.getAttributes(); 
			 String className = (String)attrMap.put(nombreAtributo,valorAtributo);
		 }
		
	}
	
	
	
	/**
     * Borrar inicialmente los bean de session, por si el usuario salio cerrando
     * la pantalla y vuelve a entrar en la misma session.
     */
    public static void borrarBeanInicioSession() {
    	
	HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

	// obtener el nombre de los atributos de
	Enumeration itAtribSession = req.getSession().getAttributeNames();
	// buscar un nombre en los parametros de la request que empiece por
	// "consulta" o "alta" y contenga "bean"
	while (itAtribSession.hasMoreElements()) {
	    String nomAtribSession = TratamientoDeDatos.sNoNull(itAtribSession.nextElement());
	    if ((nomAtribSession.toLowerCase().startsWith("consulta") || nomAtribSession.toLowerCase().startsWith("alta"))&& nomAtribSession.toLowerCase().contains("bean")) {
		// si es un bean que esta en sessiï¿½n - BORRARLO
		deleteSession(nomAtribSession);
	    }
	  }//while
    }
    
    /**
     * rsanchez Método similar al anterior pero que mantenga el UsuarioBean en la session
     * Borrar inicialmente los bean de session, por si el usuario salio cerrando
     * la pantalla y vuelve a entrar en la misma session.
     */
    public static void borrarBeanSessionMenosUsuario() {
    	
	HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

	// obtener el nombre de los atributos de
	Enumeration itAtribSession = req.getSession().getAttributeNames();
	// buscar un nombre en los parametros de la request que empiece por
	// "consulta" o "alta" y contenga "bean"
	while (itAtribSession.hasMoreElements()) {
	    String nomAtribSession = TratamientoDeDatos.sNoNull(itAtribSession.nextElement());
	    if (((nomAtribSession.toLowerCase().startsWith("consulta") || nomAtribSession.toLowerCase().startsWith("alta")) && nomAtribSession.toLowerCase().contains("bean")) || nomAtribSession.contains("selectedItem")) {
			// si es un bean que esta en session - BORRARLO
			deleteSession(nomAtribSession);
	    }
	  }//while
    }
	
	public static String addValidatorErrorSinNombre(String idCampo,
			String message) {
		UIInput component = Components.findComponent("formulario:" + idCampo);

		return FacesUtils.addValidatorError(component, null, message);
	}

	public static String addValidatorError(String idCampo,
			String nombreCasilla, String message) {
		UIInput component = Components.findComponent("formulario:" + idCampo);

		return FacesUtils.addValidatorError(component, nombreCasilla, message);
	}

	public static void addValidatorError(String idCampo, String nombreCasilla) {
		UIInput component = Components.findComponent("formulario:" + idCampo);

		FacesUtils.addValidatorError(component, nombreCasilla);

	}

	public static String addValidatorError(UIInput component,
			String nombreCasilla, String message) {

		StringBuffer mensajeGenerado = new StringBuffer();
		if (component != null) {
			if (nombreCasilla != null) {
				mensajeGenerado.append("[");
				mensajeGenerado.append(nombreCasilla);
				mensajeGenerado.append("] ");
			}
			mensajeGenerado.append(message);
			Messages.addError(component.getClientId(),
					mensajeGenerado.toString());
			component.setValid(false);
			Ajax.update(component.getClientId());

			LOG.info("Componente " + component.getClientId()
					+ " error validacion");

		}

		return mensajeGenerado.toString();
	}

	public static void addValidatorError(UIInput component, String nombreCasilla) {

		if (component != null) {
			component.setValid(false);
			Ajax.update(component.getClientId());

			LOG.info("Componente " + component.getClientId()
					+ " error validacion");

		}

	}

	public static void activarMensajeErrorValidacion() {
		FacesContext.getCurrentInstance().validationFailed();
		Ajax.update(":errorPanel");
	}

	public static void addRender(String... codCasilla) {
		for (String codigo : codCasilla) {
			Ajax.update("formulario:cas" + codigo);
		}
	}

	public static void addRender(Integer... codCasilla) {
		for (Integer codigo : codCasilla) {
			Ajax.update("formulario:cas" + codigo);
		}
	}

	/**
	 * Método que borra los datos de sesión indicados.
	 * 
	 * @author rsanchez
	 * @param key
	 *            clave de la sesi�n que se va a borrar.
	 */
	public static void deleteSession(String key) {
		 HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		 session.removeAttribute(key);
		//FacesContext.getCurrentInstance().getExternalContext().getSessionMap().  remove(key);
	}

	/**
	 * Comprobar si la ruta de la request es de una pantalla de consulta.
	 * Comprueba que la ruta contenga consulta.jsf, porque todas las pantallas
	 * de consulta deben acabar *_consulta.jspx
	 * 
	 * @author rquiros
	 * @return boolean
	 */
	public static boolean esRutaPantallaConsulta() {
		boolean esPantallaConsulta = false;

		HttpServletRequest req = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();

		// comprobar si es una pagina de detalle o de consulta (lo comprobamos
		// en la ruta)
		if (TratamientoDeDatos.sNoNull(req.getRequestURL()).toLowerCase()
				.contains("consulta.jsf")) {
			esPantallaConsulta = true;
		}

		return esPantallaConsulta;
	}

	public static UIComponent getComponentePantalla(String id) {
		UIViewRoot root = FacesContext.getCurrentInstance().getViewRoot();
		return root.findComponent(id);

	}

	/*
	public static UIComponent findComponent(final String id) {

		FacesContext context = FacesContext.getCurrentInstance();
		UIViewRoot root = context.getViewRoot();
		final UIComponent[] found = new UIComponent[1];
		root.visitTree(new FullVisitContext(context, null), new VisitCallback() {//luisma no compila
			@Override
			public VisitResult visit(VisitContext context, UIComponent component) {
				if (component.getId().equals(id)) {
					found[0] = component;
					return VisitResult.COMPLETE;
				}
				return VisitResult.ACCEPT;
			}
		});

		return found[0];

	}*/

	public static ExternalContext getExternalContext() {
		FacesContext faces = getFacesContext();
		ExternalContext context = faces.getExternalContext();
		return context;
	}

	/*
	 * Faces Object Helpers
	 */
	public static FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}

	/**
	 * Obitene el valor de un par�metro de configuraci�n, para ello lee la ruta
	 * del fichero de configuraci�n en el properties ruta_parametros.
	 * 
	 * @param name
	 *            par�metro que se quiere leer.
	 * @return valor del par�metro.
	 */
	public static String getParameterConfg(String name)throws Exception {
		InputStreamReader reader;
		FileInputStream fis;
		String valorParametro = new String();
		String rutaParametros = ResourceBundle.getBundle(Constantes.FILE_RUTA_CONF).getString(Constantes.PARAM_RUTA);
		try {
			File filePa = new File(rutaParametros);
			if (filePa.isFile()) { // Also checks for existance

				fis = new FileInputStream(filePa);
				reader = new InputStreamReader(fis, Charset.forName("UTF-8"));
				PropertyResourceBundle bundle;
				bundle = new PropertyResourceBundle(reader);
				valorParametro = bundle.getString(name);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return valorParametro;
	}

	/**
	 * Obtiene el valor de la revisión del código para mostrarlo en la pantalla
	 * de inicio.
	 */
	public static String getRevision() {
		return TratamientoDeDatos.sNoNull(ResourceBundle.getBundle("revision")
				.getString("revision"));
	}

	/**
	 * Obitene el valor de un par�metro de configuraci�n, para ello lee la ruta
	 * del fichero de configuraci�n en el properties ruta_parametros.
	 * 
	 * @param name
	 *            par�metro que se quiere leer.
	 * @return valor del par�metro.
	 */
	public static String getParameterConfg(String name, Charset charset) {
		InputStreamReader reader;
		FileInputStream fis;
		String valorParametro = new String();
		String rutaParametros = ResourceBundle.getBundle("ruta_parametros")
				.getString("ruta");
		try {
			File filePa = new File(rutaParametros);
			if (filePa.isFile()) { // Also checks for existance

				fis = new FileInputStream(filePa);
				reader = new InputStreamReader(fis, charset);
				PropertyResourceBundle bundle;
				bundle = new PropertyResourceBundle(reader);
				valorParametro = bundle.getString(name);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return valorParametro;
	}

	// funcion para obtener la ruta del war este en el entorno que esté.
	// se usa para obtener la ruta de las plantillas de la carpeta \documentos,
	// en ejecuciones batch.
	public static String getRealPathDehesaWar() {
		String path = "";
		// Mirar si está desplegado en deploy
		URL currentFile = Thread.currentThread().getContextClassLoader()
				.getResource("labels.properties");
		File deployFile = new File(currentFile.getFile());
		try {
			FileInputStream fjspx = new FileInputStream(deployFile);
			fjspx.close();
			path = deployFile.getParentFile().getParentFile().getParentFile()
					.getAbsolutePath();
		} catch (FileNotFoundException e) {
			// DEPLOY NO EXISTE
			path = System.getProperty("jboss.server.temp.dir");

			File tmpDir = new File(path);

			if (tmpDir.isDirectory()) {
				for (File file : tmpDir.listFiles()) {
					if (file.isDirectory()) {
						if (file.listFiles().length == 1) {
							if (file.listFiles()[0].getName().equals(
									"DEHESA.war")) {
								path = file.listFiles()[0].getAbsolutePath();
								break;
							}
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return path;
	}

	/**
	 * Metodo que devuelve el path real de una ruta dada.
	 * 
	 * @param path
	 *            ruta dentro del directorio web a encontrar.
	 * @return String. ruta real de la direcci�n web
	 */
	public static String getRealPath(String path) {
		try {
			HttpSession session = (HttpSession) FacesContext
					.getCurrentInstance().getExternalContext().getSession(true);
			return session.getServletContext().getRealPath(path);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Obtiene la Petición del HttpServletRequest
	 * 
	 * @return HttpServletRequest
	 */
	public static HttpServletRequest getRequest() {
		ExternalContext context = FacesContext.getCurrentInstance()
				.getExternalContext();
		HttpServletRequest request = (HttpServletRequest) context.getRequest();
		return request;
	}

	/**
	 * Método que devuelve un valor de la request.
	 * 
	 * @author rsanchez
	 * @param name
	 *            nombre del identificador que se desea coger de la request.
	 * @return Object.
	 */
	public static Object getRequestParameter(String name) {

		try {
			return FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get(name);

		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Método que devuelve el valor de un parámetro de la request. Indicado para
	 * usarlo cuando se intenta recuperar el valor de un parámetro que proviene
	 * de de una jspx (ya que el nombre es modificado, ej:
	 * formulario.id_30:Localidad)
	 * 
	 * @author brosco
	 * @param name
	 *            nombre del identificador que se desea coger de la request.
	 * @return Object.
	 */

	public static Object getRequestParameterJspx(String containsName) {

		try {
			String valor = null;
			ExternalContext context = FacesContext.getCurrentInstance()
					.getExternalContext();
			Iterator<String> iterator = context.getRequestParameterNames();

			while (iterator.hasNext()) {

				String aux = iterator.next();
				if (aux.toLowerCase().contains(containsName.toLowerCase())) {
					String nombreCompleto = aux;

					int pos = nombreCompleto.indexOf("InputCurrentDate");
					// si es un campo fecha y contiene InputCurrentDate
					// borrarselo
					if (pos != -1) {
						nombreCompleto = nombreCompleto.substring(0, pos)
								+ "InputDate";
					}
					valor = context.getRequestParameterMap()
							.get(nombreCompleto);
				}
			}

			return valor;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Método que devuelve un valor de la session.
	 * 
	 * @author mgraham
	 * @param name
	 *            nombre del identificador que se desea coger de la sesion.
	 * @return Object.
	 */
	public static Object getSessionParameter(String name) {
		try {
			return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(name);
		} catch (Exception e) {
			return null;
		}

	}

	public static String getValueMensajesProperties(String name) {
		return TratamientoDeDatos.sNoNull(ResourceBundle.getBundle("mensajes")
				.getString(name));
	}

	/**
	 * cesperilla 22-01-2016 obtiene del fichero log4j.properties la ruta donde se almacena el log
	 * @return
	 */
	public static String getRutaLog4jProperties() {
		return TratamientoDeDatos.sNoNull(ResourceBundle.getBundle("log4j")
				.getString("log4j.appender.asistente.File"));
	}
	
	
	/**
	 * Comprueba si hay mensajes en el context.
	 * 
	 * @author rquiros
	 * @return boolean
	 */
	public static boolean hayMensajes() {
		boolean hay = false;
		if (!TratamientoDeDatos.esNullVacio(FacesContext.getCurrentInstance().getMaximumSeverity())) {
			hay = true;
		}
		return hay;
	}

	public static FacesMessage obtenerMensajeProperties(FacesMessage.Severity tipoMensaje, String codigoMensaje,String valorCampo) {
		FacesMessage m = new FacesMessage(tipoMensaje, ResourceBundle.getBundle("mensajes").getString(codigoMensaje).replace(" ", " ")+ valorCampo, null);
		return m;
	}

	public static FacesMessage obtenerMensajeValidatorProperties(FacesMessage.Severity tipoMensaje, String codigoMensaje,String valorCampo) {
		FacesMessage m = new FacesMessage(tipoMensaje, ResourceBundle.getBundle("ValidatorMessages").getString(codigoMensaje).replace(" ", " ")+ valorCampo, null);
		return m;
	}

	/**
	 * Método que insertar un valor en la request.
	 * 
	 * @author rsanchez
	 * @param name
	 *            identificador en la request.
	 * @param object
	 *            valor del identificador.
	 */
	public static void setRequestParameter(String name, Object object) {
		FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put(name, object);
	}

	/**
	 * Método que insertar un valor en la sesion.
	 * 
	 * @author mgraham
	 * @param name
	 *            identificador en la sesion.
	 * @param object
	 *            valor del identificador.
	 */
	public static void setSessionParameter(String name, Object object) {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(name, object);
	}

	/**
	 * Application Control Methods
	 */

	public void doForward(final String url) throws Exception {
		try {
			FacesContext context = getFacesContext();
			context.getExternalContext().dispatch(url);
		} catch (IOException e) {
			throw new Exception(e);
		}
	}

	/**
	 * Send a client HTTP redirect and halt further Faces Life-cycle processing
	 */
	public void doRedirect(final String url) throws Exception {
		try {
			FacesContext context = getFacesContext();
			context.getExternalContext().redirect(url);
			context.responseComplete();
		} catch (IOException e) {
			throw new Exception(e);
		}
	}

	private UIComponent findChildComponent(final FacesContext facesContext,
			final UIComponent component, final String clientId) {
		if (component == null || component.getChildCount() == 0) {
			return null;
		}

		UIComponent result = null;
		for (UIComponent c : component.getChildren()) {
			if (c.getClientId(facesContext).equals(clientId)) {
				result = c;
				break;
			} else {
				result = findChildComponent(facesContext, c, clientId);
				if (result != null
						&& clientId.equals(result.getClientId(facesContext))) {
					break;
				}
			}
		}
		return result;
	}

	public UIComponent getComponent(final String clientId) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		UIViewRoot viewRoot = facesContext.getViewRoot();
		UIComponent component = viewRoot.findComponent(clientId);
		if (component == null) {
			component = findChildComponent(facesContext, viewRoot, clientId);
		}
		return component;
	}

	/**
	 * Application Helper Methods
	 */
	public String getContextRoot() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		return facesContext.getExternalContext().getRequestContextPath();
	}

	public Object getFormValue(final UIComponent component,
			final String fieldName) {
		String componentId = (String) component.getAttributes().get(fieldName);
		UIInput input = (UIInput) getFacesContext().getViewRoot()
				.findComponent(componentId);
		return input.getValue();
	}

	/**
	 * Servlet Utility Methods
	 */
	public HttpServletRequest getHttpServletRequest() {
		ExternalContext context = getExternalContext();
		return (HttpServletRequest) context.getRequest();
	}

	public HttpServletResponse getHttpServletResponse() {
		ExternalContext context = getExternalContext();
		return (HttpServletResponse) context.getResponse();
	}

	public HttpSession getSession() {
		ExternalContext context = getExternalContext();
		HttpSession session = (HttpSession) context.getSession(false);
		return session;
	}

	/**
	 * Returns true if the provided clientId has messages in the current
	 * FacesContext
	 * 
	 * @param clientId
	 * @return boolean
	 */
	public boolean hasMessages(final String clientId) {
		Iterator<String> iterator = getFacesContext()
				.getClientIdsWithMessages();
		while (iterator.hasNext()) {
			String id = iterator.next();
			if (id.equals(clientId)) {
				return true;
			}
		}
		return false;
	}

	public void setErrorMessage(final String summary) {
		getFacesContext().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null));
	}

	public void setFatalMessage(final String summary) {
		getFacesContext().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_FATAL, summary, null));
	}

	/*
	 * Faces Message Helper Methods
	 */
	public void setInfoMessage(final String summary) {
		getFacesContext().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null));
	}

	public void setInfoMessage(final UIComponent component, final String summary) {
		getFacesContext().addMessage(component.getClientId(getFacesContext()),
				new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null));
	}

	public void setWarningMessage(final String summary) {
		getFacesContext().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_WARN, summary, null));
	}

	public void show404() {
		try {
			FacesContext context = getFacesContext();
			HttpServletResponse response = (HttpServletResponse) context
					.getExternalContext().getResponse();
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			context.responseComplete();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Comprobar si una URL es valida.
	 * 
	 * @param url
	 *            - String
	 * @return boolean
	 */
	public static boolean esURLValida(String url) {
		// boolean urlOK = false;
		// try {
		// URL u = new URL(url);
		// HttpURLConnection huc = (HttpURLConnection) u.openConnection();
		// huc.setRequestMethod("GET");
		// huc.connect();
		// if (huc.getResponseCode() == 200) {
		// urlOK = true;
		// }
		// } catch (Exception e) {
		// urlOK = false;
		// }
		return true;
	}

	public static String getURLActual() {
		ExternalContext context = FacesContext.getCurrentInstance()
				.getExternalContext();
		return context.getRequestServletPath();

	}

	public static void putMessageError(String claveMensaje) {

		FacesUtils.putMessageError(claveMensaje, false);
	}

	public static void putMessageError(String claveMensaje, Boolean navegar) {

		FacesMessage m = obtenerMensajeProperties(FacesMessage.SEVERITY_ERROR,
				claveMensaje, new String());
		Messages.addGlobal(m);

		if (navegar) {
			FacesUtils.setRequestParameter("navegar", 1);
		}
	}

	public static void putMessageWarning(String claveMensaje) {
		FacesUtils.putMessageWarning(claveMensaje, false);
	}

	public static void putMessageWarning(String claveMensaje, Boolean navegar) {

		FacesMessage m = obtenerMensajeProperties(FacesMessage.SEVERITY_WARN,claveMensaje, new String());
		Messages.addGlobal(m);

		if (navegar) {
			FacesUtils.setRequestParameter("navegar", 1);
		}
	}

	public static void putMessageInfo(String claveMensaje) {
		FacesUtils.putMessageInfo(claveMensaje, false);

	}

	public static void putMessageInfo(String claveMensaje, Boolean navegar) {

		FacesMessage m = obtenerMensajeProperties(FacesMessage.SEVERITY_INFO, claveMensaje, new String());
		Messages.addGlobal(m);

		if (navegar) {
			FacesUtils.setRequestParameter("navegar", 1);
		}
	}

	public static void putMessageErrorCustom(String mensaje) {
		FacesUtils.putMessageErrorCustom(mensaje, false);
	}

	public static void putMessageErrorCustom(String mensaje, Boolean navegar) {
		Messages.addGlobal(FacesMessage.SEVERITY_ERROR, mensaje);

		if (navegar) {
			FacesUtils.setRequestParameter("navegar", 1);
		}

	}

	public static void putMessageWarningCustom(String mensaje) {
		FacesUtils.putMessageWarningCustom(mensaje, false);
	}

	public static void putMessageWarningCustom(String mensaje, Boolean navegar) {

		Messages.addGlobal(FacesMessage.SEVERITY_WARN, mensaje);

		if (navegar) {
			FacesUtils.setRequestParameter("navegar", 1);
		}
	}

	public static void putMessageInfoCustom(String mensaje) {
		FacesUtils.putMessageInfoCustom(mensaje, false);
	}

	public static void putMessageInfoCustom(String mensaje, Boolean navegar) {
		Messages.addGlobal(FacesMessage.SEVERITY_INFO, mensaje);
		if (navegar) {
			FacesUtils.setRequestParameter("navegar", 1);
		}
	}
	
	public static MethodExpression createMethodExpression(String methodExpression, Class<?> valueType) {
	      return FacesContext.getCurrentInstance().getApplication().getExpressionFactory().createMethodExpression(FacesContext.getCurrentInstance().getELContext(),methodExpression, valueType, new Class<?>[0]);
	 }
	

	public static void setAccionListener(CommandButton boton, String actionListener) {
		
		MethodExpression methodExpression = createMethodExpression(actionListener, String.class);
		boton.setActionExpression(methodExpression);
		
		//luisma_doc. con esto lo que hacemos es darle un binding
		//rsanchez 20151023 Comento la siguiente linea, ya incluimos la accionListener mediante el metodo anterior
		//boton.setValueExpression("actionListener",createValueExpression(actionListener,String.class));
	}
	
	
	
}