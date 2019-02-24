package es.grapecheck.plataforma.utiles;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

//import es.gpex.asistente.negocio.service.ProvinciaService;
//import es.gpex.asistente.persistencia.vo.ProvinciaVO;

/**
 * Converter de Provincia, necesario para transformar el combo de Provincias VO y poder mostrar su nomLargo
 * @author rsanchez
 *
 */

@FacesConverter("provinciaConverter")
public class ProvinciaConverter implements Converter {
	 
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if(value != null && value.trim().length() > 0) {
            try {
//                ProvinciaService service = (ProvinciaService) fc.getExternalContext().getApplicationMap().get("provinciaService");
                return new Object();// service.getProvinciaById(Integer.parseInt(value));
            } catch(Exception e) {
            	//rsanchez quitado provisionalemente, ya que cuando se escribe una provincia que no existe salta este mensaje
                //throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error de Conversión de la Provincia", "Error de Conversión de la Provincia"));
            	return null;
            }
        }
        else {
            return null;
        }
    }
 
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if(object != null) {
//            return String.valueOf(((ProvinciaVO) object).getId());
        	return "";
        }
        else {
            return null;
        }
    }   
} 