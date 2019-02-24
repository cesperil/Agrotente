package es.grapecheck.plataforma.webaction.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import es.grapecheck.plataforma.negocio.service.ParcelasService;
import es.grapecheck.plataforma.persistencia.vo.ParcelasVO;
import es.grapecheck.plataforma.persistencia.vo.UsuarioVO;
import es.grapecheck.plataforma.utiles.Constantes;
import es.grapecheck.plataforma.utiles.FacesUtils;
import es.grapecheck.plataforma.utiles.FlashContext;
import es.grapecheck.plataforma.utiles.TratamientoDeDatos;

/**
 * 
 * Bean creado para controlar las acciones que puede realizar el menu como eliminar Bean en session excepto el bean del usuario
 * 
 */

@ManagedBean
@ViewScoped
public class MenuBean {

	
	private MapModel simpleModel;
	
	public MapModel getSimpleModel() {
		return simpleModel;
	}

	public void setSimpleModel(MapModel simpleModel) {
		this.simpleModel = simpleModel;
	}

	public MenuBean() {
		super();
	}
	
	
	@EJB
	private ParcelasService parcelasService;
	
	
	public ParcelasService getParcelasService() {
		return parcelasService;
	}

	public void setParcelasService(ParcelasService parcelasService) {
		this.parcelasService = parcelasService;
	}

	private Marker marker;;
	
	
	public Marker getMarker() {
		return marker;
	}

	public void setMarker(Marker marker) {
		this.marker = marker;
	}

	/*
	 * Obtiene la lista de parcelas y las muestra en el mapa
	 */
	@PostConstruct
	public void init(){
		
		simpleModel = new DefaultMapModel();
		
		
		
		List<ParcelasVO> listaParcelas = getParcelasService().getParcelasByUsuario((UsuarioVO) FacesUtils.getSessionParameter(Constantes.USUARIO_LOGIN));
		
		if(TratamientoDeDatos.esListaConElementos(listaParcelas)){
			for (int i = 0; i<listaParcelas.size(); i++){
				ParcelasVO parcelasVO = listaParcelas.get(i);
				LatLng coordenadas = new LatLng(TratamientoDeDatos.doubleNoNull(parcelasVO.getLatitud()), TratamientoDeDatos.doubleNoNull(parcelasVO.getLongitud()));
				simpleModel.addOverlay(new Marker(coordenadas, parcelasVO.getNombre()));

			}
		}
	}
	
	
	
	 public void onMarkerSelect(OverlaySelectEvent event) {
	        marker = (Marker) event.getOverlay();
	    }
	
	
	public String borrarBeanSesionMenosUsuario(int redirigir) {
		
		
		FacesUtils.borrarBeanSessionMenosUsuario();
        String pagina = "";
        
        switch (redirigir) {
        	case 0: pagina = "/construyendo.xhtml";break;
        	case 2: pagina = "/mantenimientos/consultaUsuario.xhtml";break;
        	case 3: pagina = "/mantenimientos/consultaTiposSensores.xhtml";break;
        	case 4: pagina = "/mantenimientos/consultaSensores.xhtml"; break;
        	
        	case 11: pagina= "/documentacion/altaDocumento.xhtml"; break;
        	
        	case 20: pagina= "/gestioneconomica/altaMovimientoEconomico.xhtml"; break;
        	case 21: pagina= "/gestioneconomica/consultaEconomica.xhtml"; break;
        	
        	case 30: pagina= "/trabajos/gestionTrabajos.xhtml"; break;
        	case 31: pagina= "/trabajos/altaTrabajadores.xhtml"; break;
        	case 32: pagina= "/trabajos/altaTrabajo.xhtml"; break;
        	
        	case 40: pagina= "/trabajos/accesosParcelas.xhtml"; break;
        	
        	default: pagina= "/construyendo.xhtml";
        }
        return pagina;
    }
	
	public String goNuevaFinca(){
		
		FacesUtils.setSessionParameter("pagAnterior", FacesUtils.getURLActual());
		
		return "/Fincas/crearFinca.xhtml"; 
	}
	
	public String goInicio(){
		return "../Principal.xhtml?faces-redirect=true";
	}
	
	public String goDetalleFinca(String nombreFinca){
		
		//consultaFincaByNombre
		ParcelasVO parcela = getParcelasService().getParcelaByUsuarioByNombre(nombreFinca);
		FlashContext.put("selectedItem", parcela);
		FacesUtils.setSessionParameter("pagAnterior", FacesUtils.getURLActual());
		
		
		return "/Fincas/detalleFinca.xhtml";
	}
	

}
