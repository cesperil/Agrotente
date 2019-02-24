package es.grapecheck.plataforma.utiles;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.aeat.valida.Validador;

//import es.gpex.asistente.negocio.service.ExpedienteService;

/**
 * 
 * Simple email validator to demonstrate custom validation.
 * @author rsanchez
 * 
 */
@FacesValidator(value = "nifValidator")
public class NifValidator implements Validator {

//	@EJB 
//	private ExpedienteService expedienteService;
//	
//	
//	public ExpedienteService getExpedienteService() {
//		return expedienteService;
//	}
//
//	public void setExpedienteService(ExpedienteService expedienteService) {
//		this.expedienteService = expedienteService;
//	}

	@Override
	public void validate(FacesContext context, UIComponent component,Object value) throws ValidatorException {
		if (value == null) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, TratamientoDeDatos.getEtiquetaMensajes("popup.mensaje.error.validacion"),"·El Nif introducido no es correcto.");
            throw new ValidatorException(message);
		}
		
		if (!String.valueOf(value).equals("")) {
				validarNIF(String.valueOf(value).toUpperCase(), component.getId());
		}
	}
	
	/**
	 * @param evento
	 * rsanchez_doc mediante FacesMessage message al lanzar las excepciones metemos mensaje en el context para mediante el growlPrincipal mostrar el mensaje
	 * Para mostrar más mensajes, añadir al context esos mensajes context.addMessage(null, new FacesMessage("Second Message", "Additional Message Detail"));
	 * En este caso, al ser una clase validator, los mensajes se envian al context desde las excepciones
	 */
	public void validarNIF(String nif, String idComponente) throws ValidatorException {
		
		if (nif.length() < 8) {
			FacesMessage message =  new FacesMessage(FacesMessage.SEVERITY_ERROR, TratamientoDeDatos.getEtiquetaMensajes("popup.mensaje.error.validacion"),"·El Nif debe tener 9 carácteres.");
            throw new ValidatorException(message);
		}	
			Validador val = new Validador();
			try {
				int numeroDni = Integer.parseInt(nif.substring(0, 8));
				
				if (nif.length() == 9 && !TratamientoDeDatos.calcularLetraNif(numeroDni).equals(nif.subSequence(8, 9))){			
					FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, TratamientoDeDatos.getEtiquetaMensajes("popup.mensaje.error.validacion"),"·La letra del Nif no es correcta, debería ser " + TratamientoDeDatos.calcularLetraNif(numeroDni));
		            throw new ValidatorException(message);
				}
					
				
			} catch (NumberFormatException e){
				//compruebo si es nif o nie
				if(val.checkNif(nif)==4){
					//es nie correcto no se valida nada mas
					
				}else if(val.checkNif(nif)==-11){
					FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, TratamientoDeDatos.getEtiquetaMensajes("popup.mensaje.error.validacion"),"·Verifique el Nie correctamente");
		            throw new ValidatorException(message);
				}else if(val.checkNif(nif)==-1){
					FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, TratamientoDeDatos.getEtiquetaMensajes("popup.mensaje.error.validacion"),"·El formato introducido no es correcto");
		            throw new ValidatorException(message);
				}else{
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, TratamientoDeDatos.getEtiquetaMensajes("popup.mensaje.error.validacion"),"·Los 8 primeros carácteres deben ser numéricos");
	            throw new ValidatorException(message);
			
			//Si el nif no tiene la letra, nif length == 8 se calcula automaticamente con el evento ajax blur en el campo
				
			}
		} 
	}
}