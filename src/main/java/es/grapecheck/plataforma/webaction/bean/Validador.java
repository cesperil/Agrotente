package es.grapecheck.plataforma.webaction.bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import es.grapecheck.plataforma.utiles.TratamientoDeDatos;


/**
 * @author rsanchez
 *
 */

@ManagedBean
public class Validador {
	
   public void validaDNI(FacesContext arg0, UIComponent arg1, Object arg2)
	         throws ValidatorException {
	   
	   if(((String)arg2).length()==9 ){
		   String cadena= (String)arg2;
		   String subcadena=cadena.substring(0, 8);	
		   if(TratamientoDeDatos.calcularLetraNif(Integer.parseInt(subcadena)).equalsIgnoreCase(cadena.substring(8,9))){
			  //System.out.println("Correcto");
		   }
		   else{ throw new ValidatorException(new FacesMessage("LETRA DNI INCORRECTA, DEBE SER "+TratamientoDeDatos.calcularLetraNif(Integer.parseInt(subcadena)))); }
		   
	   }   
		   
		
	      if (((String)arg2).length()!=9) {
	         throw new ValidatorException(new FacesMessage("DNI 9 CARACTERES"));
	      }
	   }
   
   
   public void validar(String entrada){
	  
	   System.out.println(entrada);
	   if(entrada.length()==9 ){
		   
		   String subcadena=entrada.substring(0, 8);	
		   if(TratamientoDeDatos.calcularLetraNif(Integer.parseInt(subcadena)).equalsIgnoreCase(entrada.substring(8,9))){
			  //System.out.println("Correcto");
		   }
		   else{ new FacesMessage("LETRA DNI INCORRECTA, DEBERÍA SER "+TratamientoDeDatos.calcularLetraNif(Integer.parseInt(subcadena))); }
		   
	   }   
		   
		
	      if (entrada.length()!=9) {
	        new FacesMessage("DNI 9 CARACTERES");
	      }
	   }
}