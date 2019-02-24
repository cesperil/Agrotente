package es.grapecheck.plataforma.utiles;

import java.util.Iterator;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

/**
 * @author rsanchez
 * Controlar las fases del ciclo de vida de JSF2. 
 */

@SuppressWarnings ("serial")
public class AyudasPhaseListener implements PhaseListener {

	 public void beforePhase(PhaseEvent pe) {
		    FacesContext context = FacesContext.getCurrentInstance();		    
//		    if (pe.getPhaseId() == PhaseId.RESTORE_VIEW) 
//		      context.addMessage(
//		        null,  
//		        new FacesMessage("Procesando una nueva peticion!")
//		      );
//		      context.addMessage(
//		        null, 
//		        new FacesMessage("antes de - " + pe.getPhaseId().toString())
//		      );
		 if (FacesContext.getCurrentInstance().getCurrentPhaseId() == PhaseId.PROCESS_VALIDATIONS) {
//			 FacesContext.getCurrentInstance().setCurrentPhaseId(PhaseId.UPDATE_MODEL_VALUES);
//			 pe.getFacesContext().responseComplete();
		 }
	 }
		     
	  /* Modificado el metodo por rsanchez 20160112 para recuperar la severidad y el titulo del mensaje y mostrarlo con ellos, caso contrario, mensaje de validacion*/
	 public void afterPhase(PhaseEvent pe) {      
		 FacesContext context = FacesContext.getCurrentInstance();
		 
		 //rsanchez
//		 if (FacesContext.getCurrentInstance().getCurrentPhaseId() == PhaseId.PROCESS_VALIDATIONS) {
//		 UIViewRoot view = context.getViewRoot();
//		 UIComponent comp = view.findComponent("formulario");
//		 
//		 List<UIComponent> components = comp.getChildren();
//		 for (UIComponent component : components) {
//             if (component instanceof UIInput) {
//                 UIInput input = (UIInput) component;
//                 // JSF 1.1+ 
////             input.setSubmittedValue(null);
////             input.setValue(null);
////             input.setLocalValueSet(false);
////             input.setValid(true);
//                 // JSF 1.2+
//                 input.resetValue();
//             }
//         }
//		  
//		 }
		 
//		 if (FacesContext.getCurrentInstance().getCurrentPhaseId() == PhaseId.APPLY_REQUEST_VALUES) {
//			 FacesContext.getCurrentInstance().setCurrentPhaseId(PhaseId.UPDATE_MODEL_VALUES);
//			 //pe.getFacesContext().responseComplete(); //finaliza el ciclo
//			 //pe.getFacesContext().
////			 FacesContext.getCurrentInstance().isValidationFailed()
//			 pe.getFacesContext().renderResponse();
//		 }
		 
//		 String s = comp.getNamingContainer().getParent().getNamingContainer().getClientId();
//		 String s1 =  view.getNamingContainer().getParent().getNamingContainer().getClientId();
		 
//		 List<UIComponent> lista = comp.getChildren();
		 
//		 for (UIComponent componente : )
		 
		 //*/*****************************
	    
		 // unir los mensajes de validacion
		 Iterator<FacesMessage> msgIterator = context.getMessages();
		 String mensajeGlobal = "";
		 Severity severity=null;
		 String summary="";
		 while(msgIterator.hasNext()) {
	        FacesMessage mensaje = msgIterator.next();
	       
	        if (mensaje != null && !TratamientoDeDatos.esNullVacio(mensaje.getDetail())) {
	        	// para poner el salto de linea es necesario poner la propiedad escape="false" en el componente <p:growl 
	        	severity = mensaje.getSeverity();
	        	summary = mensaje.getSummary();
	        	mensajeGlobal = mensajeGlobal + mensaje.getDetail() + " <br/>";
	        }
	        msgIterator.remove();
	    }
	    
	    // si hay algun mensaje -- mostrarlo
	    if (!TratamientoDeDatos.esNullVacio(mensajeGlobal)) {
	    	if (!TratamientoDeDatos.esNullCeroVacio(severity)&&!TratamientoDeDatos.esNullCeroVacio(summary)) {
	    		context.addMessage(null, new FacesMessage(severity, summary, mensajeGlobal));
	    	} else {
	    		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, TratamientoDeDatos.getEtiquetaMensajes("popup.mensaje.error.validacion"), mensajeGlobal));
	    	}
	    }
	 }

	@Override
	public PhaseId getPhaseId() {
		 return PhaseId.ANY_PHASE;
	}

}
