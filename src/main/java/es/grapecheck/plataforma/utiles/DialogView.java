package es.grapecheck.plataforma.utiles;


import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

/**
 * @author rsanchez
 *
 */
 
@ManagedBean
public class DialogView {
 
	  private static String message;
	
     
   public static void addMessageInfo(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
   
   public static void addMessageError(String summary, String detail){
	   FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail);
       FacesContext.getCurrentInstance().addMessage(null, message);
   }
   
   public static void addMessageWarn(String summary, String detail){
	   FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, summary, detail);
       FacesContext.getCurrentInstance().addMessage(null, message);
       
   }
   
   
   public static String getMessage() {
       return message;
   }

   public static void setMessage(String messageP) {
       message = messageP;
   }
   
}