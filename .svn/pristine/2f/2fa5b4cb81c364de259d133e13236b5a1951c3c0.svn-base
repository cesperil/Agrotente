package es.grapecheck.plataforma.webaction.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;



//import es.gpex.cliente.ClienteSeguridadWeb;

//import es.gpex.asistente.persistencia.vo.PerfilesVO;
import es.grapecheck.plataforma.negocio.service.PerfilesService;
import es.grapecheck.plataforma.negocio.service.UsuarioService;
import es.grapecheck.plataforma.persistencia.exception.PersistenciaException;
import es.grapecheck.plataforma.persistencia.vo.PerfilesVO;
import es.grapecheck.plataforma.persistencia.vo.UsuarioVO;
import es.grapecheck.plataforma.utiles.Constantes;
import es.grapecheck.plataforma.utiles.DialogView;
import es.grapecheck.plataforma.utiles.FacesUtils;
import es.grapecheck.plataforma.utiles.LogAsistente;
import es.grapecheck.plataforma.utiles.TratamientoDeDatos;

/**
 * @author rsanchez
 *
 */


@ManagedBean
@ViewScoped
public class LoginBean extends ComunView implements Serializable {
	private static final long serialVersionUID = -2152389656664659476L;
	
	@EJB
	private UsuarioService usuarioService;
	
	@EJB
	private PerfilesService perfilesService;
	
	public PerfilesService getPerfilesService() {
		return perfilesService;
	}

	public void setPerfilesService(PerfilesService perfilesService) {
		this.perfilesService = perfilesService;
	}

	private String username;
	private String password;
	private boolean logeado = false;
	//private Mapper mapper;
	
	public boolean estaLogeado() {
		return logeado;
	} 
	
	public String getUsername() {
		return username ;
	} 

	public void setUsername(String username) {
		this.username = username;
	} 

	public String getPassword() {
		return password; 
	}
	
	public void setPassword(String password) {
			this.password = password;
	}
	
	public UsuarioService getUsuarioService() {
		return usuarioService;
	}
	
	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}
	
	//Hace login contra la BD. Modificado rsanchez para que devuelva el usuario completo
	public UsuarioVO loginBaseDatos(UsuarioVO usuarioVO){
		UsuarioVO usuarioLogeado  = null;
		List<UsuarioVO> listaUsuarioVO = new ArrayList<UsuarioVO>();
		try {
			//listaUsuarioVO = getUsuarioService().findUsuarioViolenciaByCriteria(usuarioVO);
			usuarioLogeado = getUsuarioService().getUsuarioByUsernameyPassword(usuarioVO.getUsername(), usuarioVO.getPassword());
		} catch (PersistenciaException e) {
			LogAsistente.error("LoginBean- loginBaseDatos ", e);
			e.printStackTrace();
		}
		
//		if(TratamientoDeDatos.esListaConElementos(listaUsuarioVO)){
//			for (UsuarioVO usuarioBD : listaUsuarioVO) {
//				if (TratamientoDeDatos.sNoNull(usuarioVO.getPassword()).equals(TratamientoDeDatos.sNoNull(usuarioBD.getPassword()))) {
//					usuario = usuarioBD;
//				}				
//			}
//			
//		}
		 return usuarioLogeado;
	}

	public String login() {
		
	//	RequestContext  context = RequestContext.getCurrentInstance();
	//	FacesMessage msg = null;
		
		//prueba para comprobar que cada proyecto coge su aslias.pasword de su clientKeystore_administrados.properties
		//String pasword= ResourceBundle.getBundle("clientKeystore_administrados").getString("alias.password");
		
		PerfilesVO idPerfil = new PerfilesVO();
//		try {
//			idPerfil = getPerfilesService().getPerfilesById(1);
//		} catch (PersistenciaException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		
		
		String activarSeguridad=null;
		UsuarioVO usuarioVO = this.getMapper().map(this,UsuarioVO.class);
		
		/*
		  `fk_perfiles` int(11) NOT NULL,
		  `username` varchar(50) NOT NULL,
		  `password` varchar(50) NOT NULL,
		  `nombre` varchar(50) NOT NULL,
		  `apellido1` varchar(50) NOT NULL,
		  `apellido2` varchar(50) NOT NULL,
		  `dni` varchar(10) NOT NULL,
		  `telefono` varchar(11) DEFAULT NULL,
		  `correo_electronico` varchar(50) DEFAULT NULL,
		  */
//		UsuarioVO user = new UsuarioVO();
//		
//		user.setUsername("alex");
//		user.setPassword("alex");
//		user.setNombre("Alejandro");
//		user.setApellido1("Visiga");
//		user.setApellido2("Hernandez");
//		user.setDni("12345678X");
//		user.setTelefono("685321478");
//		user.setCorreo("correo@correo.com");
//		user.setFkIdePerfiles(idPerfil);
//		
//		try {
//			usuarioService.addUsuario(user);
//		} catch (PersistenciaException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
		
		LogAsistente.info("Autenticacion base de datos activa");
		usuarioVO=loginBaseDatos(usuarioVO);
		if (!TratamientoDeDatos.esNullCeroVacio(usuarioVO)) {
			//rsanchez obtener el perfil del usuario
			if (!TratamientoDeDatos.esNullVacio(usuarioVO.getFkIdePerfiles())) {
				idPerfil = usuarioVO.getFkIdePerfiles();
			}
		}
		
//		try {
//			activarSeguridad = TratamientoDeDatos.sNoNull(FacesUtils.getParameterConfg("ACTIVAR_SEGURIDAD"));
//		} catch (Exception e) {
//			LogAsistente.error("LoginBean- login ", e);
//			//luisma_pendiente: gestion de excepciones. mostramos mensaje exception, pulsamos aceptar, navegar a pantalla de error y en el boton cerrar, vamoe al login (algo así)
//			//LogContingencias.error("Se ha producido un error al obtener ACTIVAR_SEGURIDAD del Contingencias.properties", e);
//			mostrarConfirmGenericoSinCancelar(FacesUtils.getValueMensajesProperties("etiqueta.mensaje.exception"),FacesUtils.getValueMensajesProperties("etiqueta.cabecera.exception"),Constantes.SEVERITY_ALERT,this.getActionListenerCancelarConfirmGenerico());
//			return null;
//		}
		
//		if (activarSeguridad.equals("1")) {
////			LogContingencias.info("Autenticacion seguridad web activa");
//			idPerfil=loginServicoWebSeguridad(usuarioVO);
//		} else {
////			LogContingencias.info("Autenticacion base de datos activa");
//			usuarioVO=loginBaseDatos(usuarioVO);
//			if (!TratamientoDeDatos.esVONullVacio(usuarioVO)) {
//				//rsanchez obtener el perfil del usuario
//				if (!TratamientoDeDatos.esNullVacio(usuarioVO.getFkIdePerfiles())) {
//					idPerfil = usuarioVO.getFkIdePerfiles();
//				}
//			}
//		}
		
		//si existe perfil, es que el usuario esta logado correctamente
		if (!TratamientoDeDatos.esVONullVacio(idPerfil)) {
			usuarioVO.setFkIdePerfiles(idPerfil);
			
//			LogContingencias.info("Logueado como : " + usuarioVO.getUsername());
			FacesUtils.setSessionParameter(Constantes.USUARIO_LOGIN, usuarioVO);
			
			return "Principal?faces-redirect=true";
			
		} else {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "CREDENCIALES NO VÁLIDAS", " Usuario/contraseña inválidos ");
			FacesContext.getCurrentInstance().addMessage(null, message);
			
			return null;
		}
	} 
	
	
	public void logout() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		session.invalidate();
		logeado = false;
	} 
	
	public void mostrarModal(){
		DialogView.addMessageInfo(getPassword(), getUsername());
	}

}