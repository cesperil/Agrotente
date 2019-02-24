package es.grapecheck.plataforma.utiles;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * Converter para pasar a mayúsculas el contenido de los inputs
 * @author rsanchez
 *
 */

@FacesConverter("uppercaseConverter")
public class UppercaseConverter implements Converter {

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return (String) value; // Or (value != null) ? value.toString().toUpperCase() : null;
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        //return (value != null) ? value.toUpperCase() : null;
    	return (value != null) ? value : null;
    }
}