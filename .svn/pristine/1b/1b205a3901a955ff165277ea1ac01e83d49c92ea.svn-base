package es.grapecheck.plataforma.utiles;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * La configuracion esta en el fichero log4j.properties en tomcat\lib
 * @author rsanchez
 *
 */
public class LogAsistente {
	
private static Log log = LogFactory.getLog("AsistenteAGILE");
	
	/**
	 * Obtener clase:metodo y linea desde donde se hace la llamada.
	 * @return String
	 */
	public static String lugarProcedencia(){
		// clase:metodo y linea 
		String procedencia = Thread.currentThread().getStackTrace()[3].getClassName() + ":" + Thread.currentThread().getStackTrace()[3].getMethodName() + " linea:" + Thread.currentThread().getStackTrace()[3].getLineNumber() + " ";
		// devolver controlando null
		return TratamientoDeDatos.sNoNull(procedencia);
	}
	
	/**
	 * Mostrar mensaje INFO
	 * @param men - String
	 */
	public static void info (String men) {
		String cabecera = TratamientoFechas.dateToString(new Date()) +  " ASISTENTEAGILE-INFO: ";
		String mensaje = cabecera + lugarProcedencia() + men; 
		log.info(mensaje);
		System.out.println(mensaje);
		
	}
	
	public static void info (Object men) {
		String cabecera = TratamientoFechas.dateToString(new Date()) +  " ASISTENTEAGILE-INFO: ";
		String mensaje = cabecera + lugarProcedencia() + men; 
		log.info(mensaje);
		System.out.println(mensaje);
	}
	
	public static void error(String men, Exception e) {
		String cabecera = TratamientoFechas.dateToString(new Date()) +  " ASISTENTEAGILE-INFO: ";
		String mensaje = cabecera + lugarProcedencia() + men; 
		log.error(mensaje, e);
		System.out.println(mensaje);
	}
	
	/**
	 * Mostrar mensaje DEBUG
	 * @param men - String
	 */
	public static void debug(String men) {
		String cabecera =TratamientoFechas.dateToString(new Date()) +  " ASISTENTEAGILE-DEBUG: ";
		String mensaje = cabecera + lugarProcedencia() + men; 
		log.debug(mensaje);
		System.out.println(mensaje);
	}
	
	public static boolean isDebugEnabled() {
		return log.isDebugEnabled();
	}
}
