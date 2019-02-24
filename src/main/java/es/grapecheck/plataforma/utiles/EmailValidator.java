package es.grapecheck.plataforma.utiles;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * validacion de campos email
 * @author rsanchez
 *
 */
@FacesValidator(value = "emailValidator")
public class EmailValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component,Object value) throws ValidatorException {
		
		/*if (TratamientoDeDatos.esNullCeroVacio(value)) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email no correcto","El texto introducido no tiene formato de correo electrónico");
            throw new ValidatorException(message);
		}*/
		
		if (!String.valueOf(value).equals("")) {
			validarEmail(String.valueOf(value).toUpperCase());
		}
	}
	
	
	public void validarEmail(String email) throws ValidatorException {
		String summary = FacesUtils.getValueMensajesProperties("popup.mensaje.error.validacion");
		String detail = FacesUtils.getValueMensajesProperties("etiqueta.mensaje.validacion.email");
		
		if (!email.contains("@") || email.indexOf('@')==0) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail));
		} else if(!email.contains(".") || (email.lastIndexOf('.')<email.lastIndexOf('@'))) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail));
		}
	} 
		
		
}
