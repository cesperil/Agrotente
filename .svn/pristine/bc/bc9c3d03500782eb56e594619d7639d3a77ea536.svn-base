
/**
 * 
 */
package es.grapecheck.plataforma.webaction.bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.jfree.util.Log;
import org.primefaces.context.RequestContext;

import es.grapecheck.plataforma.negocio.service.PerfilesService;
import es.grapecheck.plataforma.negocio.service.UsuarioService;
import es.grapecheck.plataforma.persistencia.exception.PersistenciaException;
import es.grapecheck.plataforma.persistencia.vo.PerfilesVO;
import es.grapecheck.plataforma.persistencia.vo.UsuarioVO;
import es.grapecheck.plataforma.utiles.Constantes;
import es.grapecheck.plataforma.utiles.FacesUtils;
import es.grapecheck.plataforma.utiles.FlashContext;
import es.grapecheck.plataforma.utiles.TipoBean;
import es.grapecheck.plataforma.utiles.TratamientoDeDatos;
import es.grapecheck.plataforma.webaction.form.BotoneraPrimefacesForm;



/**
 * @author cesperilla
 */


/*
 * -@ManagedBean
 * una página JSF puede acceder a él utilizando el nombre userBean ya que, si no indicamos ningún nombre concreto, 
 * por defecto se le asigna el nombre de la clase con la primera letra en minúscula.
 * -@ViewScoped	El bean administrado se creará al recibirse una petición HTTP y se destruirá al cambiar de vista.
 */

@ManagedBean
@ViewScoped
public class AltaUsuarioBean extends BotoneraPrimefacesForm<UsuarioVO>{
	
	private static final long serialVersionUID = -1479162331630831185L;
	
	@EJB
	private PerfilesService perfilesService;

	public PerfilesService getPerfilesService() {
		return perfilesService;
	}

	public void setPerfilesService(PerfilesService perfilesService) {
		this.perfilesService = perfilesService;
	}

	private String textoComboPerfil;

	public String getTextoComboPerfil() {
		return textoComboPerfil;
	}

	public void setTextoComboPerfil(String textoComboPerfil) {
		this.textoComboPerfil = textoComboPerfil;
	}

	@EJB
	private UsuarioService usuarioService;
	
	public UsuarioService getUsuarioService() {
		return usuarioService;
	}

	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}
	
	private UsuarioVO usuarioVO;
	

	public UsuarioVO getUsuarioVO() {
		return usuarioVO;
	}

	public void setUsuarioVO(UsuarioVO usuarioVO) {
		this.usuarioVO = usuarioVO;
	}
	
	private List<PerfilesVO> filteredPerfiles;

	public List<PerfilesVO> getFilteredPerfiles() {
		return filteredPerfiles;
	}

	public void setFilteredPerfiles(List<PerfilesVO> filteredPerfiles) {
		this.filteredPerfiles = filteredPerfiles;
	}

	public AltaUsuarioBean() {
		super();
	}


	public void add() {
	//	System.out.println("ENTRA EN AÑADIR!!");
		
		UsuarioVO usuarioVO = this.getMapper().map(this.getUsuarioVO(),UsuarioVO.class);
		
		try {
			if(TratamientoDeDatos.esNullCeroVacio(usuarioVO.getId())){
				//rsanchez
				PerfilesVO listaPerfiles = perfilesService.getPerfilByNombre(this.getTextoComboPerfil());
					usuarioVO.setFkIdePerfiles(listaPerfiles);
				
				//Guardar
				Log.info("Guardar Usuario");
				this.getUsuarioService().addUsuario(usuarioVO);
				//UtilidadesContingencia.setBusquedaAutomatica(true);
				//MensajeGenericoNavegando(FacesUtils.getValueMensajesProperties("etiqueta.cabecera.informacion"), FacesUtils.getValueMensajesProperties("etiqueta.mensaje.usuarioguardado"), Constantes.SEVERITY_INFO);
				mostrarConfirmGenericoSinCancelarYVueltaAtras(FacesUtils.getValueMensajesProperties("etiqueta.mensaje.usuarioguardado"), Constantes.SEVERITY_INFO, "", this.getBotonVolverOkDeAlta());
				//cesperilla 27-10-2015 seteo para que busque automáticamente al volver a consulta
				
			}

		} catch (PersistenciaException e) {
			//RequestContext.getCurrentInstance().execute("PF('Excepcion').show()");
			//juanlu 10/02/2016
			e.printStackTrace();
			mostrarConfirmGenericoSinCancelar(FacesUtils.getValueMensajesProperties("etiqueta.mensaje.exception"),FacesUtils.getValueMensajesProperties("etiqueta.cabecera.exception"),Constantes.SEVERITY_ALERT,this.getActionListenerCancelarConfirmGenerico());
			
		}
	}


	public void delete() {
		try {
			this.getUsuarioService().deleteUsuario(this.getUsuarioService().getUsuarioById(this.getUsuarioVO().getId()));
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
	

	@PostConstruct
	private void init() {

		try {
			//rsanchez 20160107 Inicializacion del usuarioVO del form
			setUsuarioVO(new UsuarioVO());
			
			UsuarioVO usuarioVO = (UsuarioVO) FlashContext.get("selectedItem");
			this.getBotonAyuda().setOnclick("PF('modalAyuda').show()");
			this.setRutaAyuda("../ayudas/ayudaSinAyuda.xhtml");
			if (!TratamientoDeDatos.esVONullVacio(usuarioVO)) {
				this.getMapper().map(usuarioVO, this.getUsuarioVO());
				
				PerfilesVO perfilesVO = usuarioVO.getFkIdePerfiles();
				
				if (!TratamientoDeDatos.esVONullVacio(perfilesVO)) {
					setTextoComboPerfil(perfilesVO.getNombre());
				}				
				
				this.montarBotoneraMantenimientoComun(TipoBean.DETALLE);
			} else {
				this.montarBotoneraMantenimientoComun(TipoBean.ALTA);
			}
			
			/* rsanchez deshabilitar el boton de impresión */
			this.getBotonImprimir().setDisabled(true);
			
		} catch (Exception e) {
			//RequestContext.getCurrentInstance().execute("PF('Excepcion').show()");
			//juanlu 10/02/2016
			mostrarConfirmGenericoSinCancelar(FacesUtils.getValueMensajesProperties("etiqueta.mensaje.exception"),FacesUtils.getValueMensajesProperties("etiqueta.cabecera.exception"),Constantes.SEVERITY_ALERT,this.getActionListenerCancelarConfirmGenerico());
			
		}
	}

	public void update() {
		UsuarioVO usuarioVO = this.getMapper().map(this.getUsuarioVO(),UsuarioVO.class);
		try {
			//rsanchez
			List<PerfilesVO> listaPerfiles = perfilesService.getAllPerfilessFiltros(this.getTextoComboPerfil());
			if (listaPerfiles.size() > 0) {
				usuarioVO.setFkIdePerfiles(listaPerfiles.get(0));
			}
			
			this.getUsuarioService().updateUsuario(usuarioVO);
			//MensajeGenerico(FacesUtils.getValueMensajesProperties("etiqueta.cabecera.informacion"), FacesUtils.getValueMensajesProperties("etiqueta.mensaje.usuarioguardado"), Constantes.SEVERITY_INFO);
			mostrarConfirmGenericoSinCancelarYVueltaAtras(FacesUtils.getValueMensajesProperties("etiqueta.mensaje.usuarioguardado"),FacesUtils.getValueMensajesProperties("etiqueta.cabecera.informacion"), Constantes.SEVERITY_INFO, this.getBotonVolverOkDeAlta() );

		} catch (PersistenciaException e) {
			//RequestContext.getCurrentInstance().execute("PF('Excepcion').show()");
			//juanlu 10/02/2016
			mostrarConfirmGenericoSinCancelar(FacesUtils.getValueMensajesProperties("etiqueta.mensaje.exception"),FacesUtils.getValueMensajesProperties("etiqueta.cabecera.exception"),Constantes.SEVERITY_ALERT,this.getActionListenerCancelarConfirmGenerico());
			
		}

	}
	
	public List<String> completarPerfil(String query) {

		List<String> filteredPerfiles = new ArrayList<String>();

		try {
			if(!TratamientoDeDatos.esListaConElementos(getFilteredPerfiles())){
				setFilteredPerfiles(perfilesService.getAllPerfilessActivos());
			}
			
			//juanlu 08-02-2016 no funcionaba porque no cogía el valor del input de la pantalla 
			//setF perfilesService.getAllPerfilessFiltros(query);
			for (PerfilesVO autoPerfil : getFilteredPerfiles()) {
				if (autoPerfil.getNombre().toLowerCase().startsWith(query.toLowerCase())) {
					filteredPerfiles.add(autoPerfil.getNombre());
				}
			}
		} catch (PersistenciaException e) {
			FacesUtils.putMessageErrorCustom(e.getDescription());
		}
		return filteredPerfiles;
	}
	
}
