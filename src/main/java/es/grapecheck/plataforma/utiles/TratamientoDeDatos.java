package es.grapecheck.plataforma.utiles;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/**
 * Clase estÃ¡tica de utilidades para el tratamiendo comÃºn de datos.
 * 
 * @author rquiros
 * @date 15/02/2010
 */
public class TratamientoDeDatos {

	private static final Logger LOG = LoggerFactory
			.getLogger(TratamientoDeDatos.class);

	private static final Pattern cifPattern = Pattern
			.compile("[[A-H][J-N][P-S]UVW][0-9]{7}[0-9A-J]");
	private static final String CONTROL_SOLO_NUMEROS = "ABEH";
	private static final String CONTROL_SOLO_LETRAS = "KPQS";
	private static final String CONTROL_NUMERO_A_LETRA = "JABCDEFGHI";

	public static Boolean esVONullVacio(Object objeto) {
		Boolean vacio = true;
		try {
			if (!esNullCeroVacio(objeto)) {
				if (objeto.getClass().getName().contains(Constantes.VO)) {
					Method getId = objeto.getClass().getMethod(
							getMetodoGet(Constantes.ID_ATRIBUTO, false));
					if (!esNullCeroVacio(getId.invoke(objeto))) {
						vacio = false;
					}
				} else {
					vacio = false;
				}
			}
		} catch (Exception e) {
		}
		return vacio;
	}

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List PasarArrayAList (String []array) {
	ArrayList listaA = new ArrayList();

	if (array==null || array.length==0) return listaA;
	for (int i=0;i<array.length;i++) {

	listaA.add(array[i]);

	}
	return listaA;
	}
	
	/**
	 * Función que elimina acentos y caracteres especiales de
	 * una cadena de texto.
	 * @param input
	 * @return cadena de texto limpia de acentos y caracteres especiales.
	 */
	public static String reemplazarTildes(String input) {
	    // Cadena de caracteres original a sustituir.
	    String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ";
	    // Cadena de caracteres ASCII que reemplazarán los originales.
	    String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
	    String output = input;
	    for (int i=0; i<original.length(); i++) {
	        // Reemplazamos los caracteres especiales.
	        output = output.replace(original.charAt(i), ascii.charAt(i));
	    }//for i
	    return output;
	}//reemplazarTildes
	
	
	
	/**
	 * Comparar cadenas. Controla si alguno de los parametros de entrada es
	 * null.
	 * 
	 * @param cadena1
	 *            - String
	 * @param cadena2
	 *            - String
	 * @return boolean - true: son iguales - false: hay diferencias
	 * @author rquiros
	 */
	public static boolean compararCadenas(String cadena1, String cadena2) {
		boolean iguales = false;
		try {
			if (TratamientoDeDatos.sNoNull(cadena1).equals(
					TratamientoDeDatos.sNoNull(cadena2))) {
				iguales = true;
			}
		} catch (Exception e) {
			LOG.error("ERROR compararCadenas: " + e.getMessage());
			iguales = false;
		}
		return iguales;
	}

	/**
	 * Comparar inicio de cadenas. Compara con trim() y equalsIgnoreCase.
	 * 
	 * @param cadena1
	 *            - String
	 * @param cadena2
	 *            - String
	 * @param cantidad
	 *            - int (cantidad de digitos a comparar)
	 * @return boolean - true: iguales - false: distintas
	 */
	public static boolean compararInicioCadenas(String cadena1, String cadena2,
			int cantidad) {
		boolean iguales = false;
		try {
			if (!TratamientoDeDatos.esNullVacio(cadena1)
					&& !TratamientoDeDatos.esNullVacio(cadena2)
					// comprobar que las cadenas son mas largas que el trozo a
					// coger
					&& cadena1.length() > cantidad
					&& cadena2.length() > cantidad) {
				// coger el trozo indicado y quitar los espacios
				String trozoCad1 = cadena1.substring(0, cantidad).trim();
				String trozoCad2 = cadena2.substring(0, cantidad).trim();
				// comparar trozos de cadena
				if (trozoCad1.equalsIgnoreCase(trozoCad2)) {
					iguales = true;
				}
			}
		} catch (Exception e) {
			LOG.error("ERROR compararInicioCadenas: " + e.getMessage());
			iguales = false;
		}
		return iguales;
	}

	/**
	 * Comparar cadenas. Controla si alguno de los parametros de entrada es
	 * null.
	 * 
	 * @param cadena1
	 *            - String
	 * @param cadena2
	 *            - String
	 * @return boolean - true: son iguales - false: hay diferencias
	 * @author rquiros
	 */
	public static boolean compararCadenasIgnoreCase(String cadena1,
			String cadena2) {
		boolean iguales = false;
		try {
			if (TratamientoDeDatos.sNoNull(cadena1).equalsIgnoreCase(
					TratamientoDeDatos.sNoNull(cadena2))) {
				iguales = true;
			}
		} catch (Exception e) {
			LOG.error("ERROR compararCadenasIgnoreCase: " + e.getMessage());
			iguales = false;
		}
		return iguales;
	}

	/**
	 * Comparar cadenas. Controla si alguno de los parametros de entrada es
	 * null. Hace trim y toLowerCase
	 * 
	 * @param cadena1
	 *            - String
	 * @param cadena2
	 *            - String
	 * @return boolean - true: son iguales - false: hay diferencias
	 * @author rquiros
	 */
	public static boolean compararCadenasIgnoreCaseTrim(String cadena1,
			String cadena2) {
		boolean iguales = false;
		try {
			if (TratamientoDeDatos
					.sNoNull(cadena1)
					.trim()
					.equalsIgnoreCase(
							TratamientoDeDatos.sNoNull(cadena2).trim())) {
				iguales = true;
			}
		} catch (Exception e) {
			LOG.error("ERROR compararCadenasIgnoreCase: " + e.getMessage());
			iguales = false;
		}
		return iguales;
	}

	/**
	 * Comparar Integer. Controla si alguno de los parametros de entrada es
	 * null.
	 * 
	 * @param int1
	 *            - Integer
	 * @param int2
	 *            - Integer
	 * @return boolean - true: son iguales - false: hay diferencias
	 * @author rquiros
	 */
	public static boolean compararInteger(Integer int1, Integer int2) {
		boolean iguales = false;
		try {
			if (TratamientoDeDatos.iNoNullEsCero(int1).equals(
					TratamientoDeDatos.iNoNullEsCero(int2))) {
				iguales = true;
			}
		} catch (Exception e) {
			LOG.error("ERROR compararInteger: " + e.getMessage());
			iguales = false;
		}
		return iguales;
	}

	/**
	 * @author rquiros
	 * @param Cadena
	 *            - Object
	 * @return String Devuelve un objeto convertido a string. si el objeto es
	 *         nulo, devuelve una cadena en blanco.
	 */
	public static String sNoNull(Object Cadena) {
		if (Cadena == null || Cadena.equals("null")
				|| Cadena.equals("undefined")) {
			return ("");
		} else {
			return (Cadena.toString());
		}
	}

	public static String sNoNullEsNull(Object Cadena) {
		if (Cadena == null || Cadena.equals("null")
				|| Cadena.equals("undefined")) {
			return null;
		} else {
			return (Cadena.toString());
		}
	}

	/**
	 * @author rquiros
	 * @param Cadena
	 * @return Integer si es numero sino null
	 */
	public static Integer iNoNullEsNull(Object Cadena) {
		Integer result = null;
		if (Cadena == null || Cadena.equals("null")
				|| Cadena.equals("undefined")) {
			return null;
		} else {
			try {
				if (Cadena instanceof BigDecimal) {
					result = ((BigDecimal) Cadena).intValue();
				} else {
					String valorCadena = Cadena.toString();
					if (valorCadena.length() > 0) {
						result = Integer.parseInt(Cadena.toString());
					} else {
						// si la cadena es vacia devolver null
						result = null;
					}
				}
			} catch (Exception e) {
				result = null;
			}
		}
		return result;
	}

	/**
	 * @author rquiros
	 * @param Cadena
	 * @return Integer si es numero sino Integer(0)
	 */
	public static Integer iNoNullEsCero(Object Cadena) {
		if (Cadena == null || Cadena.equals("null")
				|| Cadena.equals("undefined")) {
			return Integer.valueOf(0);
		} else {
			try {
				Integer result = 0;

				if (Cadena instanceof BigDecimal) {
					result = ((BigDecimal) Cadena).intValue();
				} else {
					String valorCadena = Cadena.toString();
					if (valorCadena.length() > 0) {
						result = Integer.parseInt(Cadena.toString());
					} else {
						// si la cadena es vacia devolver 0
						result = Integer.valueOf(0);
					}
				}
				return result;
			} catch (Exception e) {
				return Integer.valueOf(0);
			}
		}
	}

	/**
	 * @author rquiros
	 * @param cadena
	 * @return Double si es numero sino null
	 */
	public static Double doubleNoNull(Object cadena) {
		Double resul = null;
		if (cadena == null || cadena.equals("null")
				|| cadena.equals("undefined") || cadena.equals("")) {
			resul = null;
		} else {
			try {
				resul = Double.parseDouble(cadena.toString());
			} catch (Exception e) {
				resul = null;
			}
		}
		return resul;
	}

	/**
	 * @author rquiros
	 * @modificado lmpaz 2_5_2011
	 * @param cadena
	 * @return 0, si la cadena es null,undefined,'null' o ''.
	 */
	public static Double doubleNoNullEsCero(Object cadena) {
		Double resul = 0.0;
		if (cadena == null || cadena.equals("null")
				|| cadena.equals("undefined") || cadena.equals("")) {
			resul = 0.0;
		} else {
			try {
				if (cadena.toString().contains(",")) {
					resul = cadenaToDouble(cadena);
				} else {
					resul = Double.parseDouble(cadena.toString());
				}
			} catch (Exception e) {
				resul = 0.0;
			}
		}
		return resul;
	}

	/**
	 * @author jjpozo
	 * @modificado lmpaz 2_5_2011
	 * @param cadena
	 * @return 0, si la cadena es null,undefined,'null' o ''.
	 */
	public static Double doubleNoNullEsCeroD(double num) {
		Double resul = 0.0;
		if (esNullCeroVacio(num)) {
			resul = 0.0;
		} else {
			resul = num;
		}
		return resul;
	}

	/**
	 * Transformar una cadena a double. Ej. Entra: 12.455.456,54 Resultado:
	 * 12455456.54
	 * 
	 * @param cadena
	 *            - Object
	 * @return Double
	 */
	public static Double cadenaToDouble(Object cadena) {
		Double valor = null;
		if (cadena != null) {
			// cambiar de , a .
			String cadenaS = TratamientoDeDatos.sNoNull(cadena);
			// quitar los puntos
			cadenaS = cadenaS.replaceAll("\\.", "");
			// la coma de los decimales - serï¿½ un punto
			cadenaS = cadenaS.replaceAll(",", ".");
			valor = TratamientoDeDatos.doubleNoNull(cadenaS);
		}
		return valor;
	}

	/**
	 * Transformar una cadena a BigDecimal. Ej. Entra: 12.455.456,54 Resultado:
	 * 12455456.54
	 * 
	 * @param cadena
	 *            - Object
	 * @return BigDecimal
	 */
	public static BigDecimal cadenaToBigDecimal(Object cadena) {
		BigDecimal valor = null;
		if (cadena != null && !cadena.equals("")) {
			// cambiar de , a .
			String cadenaS = TratamientoDeDatos.sNoNull(cadena);
			// quitar los puntos
			cadenaS = cadenaS.replaceAll("\\.", "");
			// la coma de los decimales - serï¿½ un punto
			cadenaS = cadenaS.replaceAll(",", ".");
			valor = TratamientoDeDatos.bigDecimalNoNull(cadenaS);
		}
		return valor;
	}

	/**
	 * Transformar una cadena a Map. Ej. Entra: "600:101T;610:102T" Resultado:
	 * map<(key, value) (600, 101T)...>
	 * 
	 * @param String
	 *            cadena
	 * @param String
	 *            separador interno
	 * @param String
	 *            separador externo
	 * 
	 * @return Map
	 */
	public static Map<String, String> cadenaToMap(String cadena,
			String separador_int, String separador_ext) {
		Map<String, String> map = new HashMap<String, String>();
		String[] pairs = cadena.split(separador_ext);
		for (int i = 0; i < pairs.length; i++) {
			String pair = pairs[i];
			String[] keyValue = pair.split(separador_int);
			map.put(keyValue[0], keyValue[1]);
		}
		return map;
	}

	/**
	 * Obtener la cadena del double.
	 * 
	 * @author rquiros
	 * @param cadena
	 * @return String si es un double
	 */
	public static String doubleAString(Double cadena) {
		String resul = null;
		if (cadena != null) {
			try {
				resul = Double.toString(cadena);
			} catch (Exception e) {
				resul = null;
			}
		}
		return resul;
	}

	/**
	 * Obtener long de una cadena.
	 * 
	 * @param cadena
	 * @return Long si es numero sino null
	 */
	public static Long longNoNull(Object cadena) {
		Long resul = null;
		if (cadena == null || cadena.equals("null")
				|| cadena.equals("undefined")) {
			resul = null;
		} else {
			try {
				resul = Long.parseLong(cadena.toString());
			} catch (Exception e) {
				resul = null;
			}
		}
		return resul;
	}

	/**
	 * Obtener la cadena del Long.
	 * 
	 * @author rquiros
	 * @param cadena
	 * @return String si es un Long
	 */
	public static String longAString(Long cadena) {
		String resul = null;
		if (cadena != null) {
			try {
				resul = Long.toString(cadena);
			} catch (Exception e) {
				resul = null;
			}
		}
		return resul;
	}

	public static int parseLongToInt(long l) {
		if (l < Integer.MIN_VALUE || l > Integer.MAX_VALUE) {
			throw new IllegalArgumentException(l
					+ " cannot be cast to int without changing its value.");
		}
		return (int) l;
	}

	/**
	 * @author rquiros
	 * @param cadena
	 * @return Boolean En caso de nulo o excepciï¿½n retorna FALSE
	 */
	public static Boolean booleanNoNull(Object cadena) {
		Boolean resul = false;
		if (cadena == null || cadena.equals("null")
				|| cadena.equals("undefined")) {
			resul = false;
		} else {
			try {
				resul = Boolean.parseBoolean(cadena.toString());
			} catch (Exception e) {
				resul = false;
			}
		}
		return resul;
	}

	/**
	 * Comprueba si una cadena es boolean.
	 * 
	 * @author rquiros
	 * @param cadena
	 *            - Object
	 * @return boolean true - es boolean false - no es boolean
	 */
	public static boolean isBoolean(Object cadena) {
		boolean esBoolean = false;
		try {
			if (cadena != null) {
				if (sNoNull(cadena).toLowerCase().equals("true")
						|| sNoNull(cadena).toLowerCase().equals("false"))
					esBoolean = true;
			}
		} catch (Exception e) {
			esBoolean = false;
		}
		return esBoolean;
	}

	/**
	 * Indica si el valor es null, cero o cadena vacÃ­a
	 * 
	 * @author rquiros
	 * @param objeto
	 *            - Object
	 * @return true o false
	 */
	public static boolean esNullCeroVacio(Object objeto) {
		if (objeto == null)
			return true;
		if (objeto.toString().equals("0"))
			return true;
		if (objeto.toString().equals("0.0"))
			return true;
		if (objeto.toString().equals("0,0"))
			return true;
		if (objeto.toString().equals("0,00"))
			return true;
		if (objeto.toString().equals("0.00"))
			return true;
		if (objeto.toString().equals(""))
			return true;
		if (objeto.toString().equals("null"))
			return true;
		return false;
	}

	public static boolean esCero(Object objeto) {
		boolean esCero = false;

		if (objeto.toString().equals("0")) {
			esCero = true;
		} else if (objeto.toString().equals("0.0")) {
			esCero = true;
		} else if (objeto.toString().equals("0,0")) {
			esCero = true;
		} else if (objeto.toString().equals("0,00")) {
			esCero = true;
		} else if (objeto.toString().equals("0.00")) {
			esCero = true;
		}

		return esCero;
	}

	/**
	 * Indica si el valor es null o cadena vacÃ­a
	 * 
	 * @author rquiros
	 * @param objeto
	 *            - Object
	 * @return true o false
	 */
	public static boolean esNullVacio(Object objeto) {
		if (objeto == null)
			return true;
		if (objeto.toString().equals(""))
			return true;
		if (objeto.toString().equals("null"))
			return true;

		return false;
	}

	/**
	 * Indica si la lista es null o no tiene elementos
	 * 
	 * @author fjalonso
	 * @param lista
	 *            - List
	 * @return true o false
	 */
	public static boolean esListaNullVacia(List<?> lista) {
		return ((lista == null) || (lista.size() < 1));
	}

	/**
	 * Indica si la lista tiene elementos.
	 * 
	 * @param lista
	 *            - List
	 * @return true o false
	 */
	public static boolean esListaConElementos(List<?> lista) {
		return !esListaNullVacia(lista);
	}

	/**
	 * Indica si el set es null o no tiene elementos
	 * 
	 * @author brosco
	 * @param set
	 *            - Set
	 * @return true o false
	 */
	public static boolean esSetNullVacio(Set<?> set) {
		return ((set == null) || (set.size() < 1));
	}

	/**
	 * Indica si el hashMap es null o no tiene elementos
	 * 
	 * @author brosco
	 * @param set
	 *            - Set
	 * @return true o false
	 */
	public static boolean esMapNullVacio(Map<?, ?> map) {
		return ((map == null) || (map.size() < 1));
	}

	/**
	 * Comprueba si una cadena es numÃ©rica.
	 * 
	 * @author rquiros
	 * @param cadena
	 *            - Object
	 * @return boolean true - es numÃ©rica false - no es numÃ©rica
	 */
	public static boolean isNumber(Object cadena) {
		try {
			if (cadena != null) {
				Integer.parseInt(cadena.toString());
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	/**
	 * Comprueba si una cadena es Long.
	 * 
	 * @author rquiros
	 * @param cadena
	 *            - Object
	 * @return boolean true - es Long false - no es cadena.toString()
	 */
	public static boolean isLong(Object cadena) {
		try {
			if (cadena != null) {
				Long.parseLong(cadena.toString());
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	/**
	 * Comprueba si una cadena es Double.
	 * 
	 * @author jmlezcano
	 * @param cadena
	 *            - Object
	 * @return boolean true - es Long false - no es cadena.toString()
	 */
	public static boolean isDouble(Object cadena) {
		try {
			if (cadena != null) {
				Double.parseDouble(cadena.toString());
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	/**
	 * @param cadena
	 * @return
	 */
	public static boolean isBigDecimal(Object cadena) {
		try {
			if (cadena != null) {
				Double d = new Double(cadena.toString());
				BigDecimal.valueOf(d);
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	// Si es nul o vacio devuelve 0.
	public static BigDecimal bigDecimalNoNull(Object cadena) {
		BigDecimal result = new BigDecimal(0);
		try {
			if (!esNullVacio(cadena)) {
				// quitar las comas
				cadena = getNumeroEnteroDecimal(cadena.toString());
				Double d = doubleNoNull(cadena);
				result = BigDecimal.valueOf(d);
			}
		} catch (Exception e) {
			result = new BigDecimal(0);
		}
		return result;
	}

	/**
	 * Comprueba si una cadena tiene dos decimales.Consideramos q estará
	 * separados por "." ej: 22.05
	 * 
	 * @author sgarciao
	 * @param cadena
	 *            - Object
	 * @return Boolean
	 */
	public static Boolean dosDecimales(Object cadena) {

		try {

			if (cadena != null) {
				String[] numero = (cadena.toString()).split("\\.");
				if (numero.length > 0) {
					if (numero[1].length() == 2) {
						return true;
					}
				}
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	public static String getNombreMes(int id) {
		if (esNullVacio(id))
			return "";
		String cadena = ResourceBundle.getBundle("constantes").getString(
				"constantes.meses");
		String meses[] = cadena.split(";");
		if (meses.length == 12 && id >= 0 && id < 12)
			return meses[id];
		else
			return "";
	}

	public static String getNombreMes(String id) {
		if (!esNullVacio(id))
			return getNombreMes(Integer.valueOf(id));
		else
			return "";
	}

	public static String getNumeroMes(String nombre) {
		if (esNullVacio(nombre))
			return "";
		String cadena = ResourceBundle.getBundle("constantes").getString(
				"constantes.meses");
		String meses[] = cadena.split(";");
		for (Integer i = 0; i < meses.length; i++) {
			if (meses[i].equals(nombre))
				return i.toString();
		}
		return "";
	}

	/**
	 * @author jmlezcano
	 * @param nombre
	 * @return Devuelve un nÃºmero con este formato '123.455,00' a '123455.00'
	 *         (patrÃ³n que permite la BB.DD)
	 */
	public static String getNumeroEnteroDecimal(String nombre) {
		String cadena = nombre;
		if (nombre.contains(",")) {
			cadena = nombre.replace(".", "");
			cadena = cadena.replace(",", ".");
		}
		return cadena;
	}

	/**
	 * Devuelve un nï¿½mero con este formato '123455.00'(en la BB.DD) a
	 * '123.455,00' (nuestro formato de presentaciï¿½n en las pantallas).
	 * 
	 * @author jmlezcano
	 * @author fjalonso
	 * @param numeroOrigen
	 * @return Devuelve un nï¿½mero con este formato '123455.00'(en la BB.DD) a
	 *         '123.455,00' (nuestro formato de presentaciÃ³n en las pantallas)
	 */
	public static String getNumeroDecimalFormateado(String numeroOrigen) {
		String decimal = Constantes.CADENA_DOS_CEROS;
		String signo = "", entera = new String(), nuevaCadena = new String();
		int i = 0;
		if (!esNullVacio(numeroOrigen)) {
			if ("-".equals(numeroOrigen.substring(0, 1))) {
				numeroOrigen = numeroOrigen.substring(1);
				signo = "-";
			}
			String cadena = numeroOrigen.replace(Constantes.PUNTO,
					Constantes.COMA);
			StringTokenizer st = new StringTokenizer(cadena, Constantes.COMA);
			if (st.hasMoreElements()) {
				entera = st.nextToken();
			}
			int longitud = entera.length();
			while (i < entera.length()) {
				if (((longitud - i) % 3) != 0) {
					nuevaCadena = nuevaCadena + entera.charAt(i);
				} else {
					nuevaCadena = nuevaCadena + Constantes.PUNTO
							+ entera.charAt(i);
				}
				i++;
			}
			// tratamiento de la parte decimal
			if (st.hasMoreElements()) {
				decimal = st.nextToken();
				if (decimal.length() > 1) {
					if (decimal.equals(Constantes.VALOR_INICIAL_CERO) == false) {
						decimal = decimal.substring(0, decimal.length());
					}
				} else {
					// Si solo tiene un decimal aï¿½adimos un 0.
					decimal = decimal + "0";
				}
			}
			if (nuevaCadena.charAt(0) == '.') {
				nuevaCadena = nuevaCadena.substring(1);
			}
			nuevaCadena = signo.concat(nuevaCadena.concat(Constantes.COMA
					+ decimal));
		}
		return nuevaCadena;
	}

	public static String getNumeroDecimalFormateado(Object valor) {
		String resultado = "0,00";
		if (valor instanceof Double) {
			resultado = getNumeroDecimalFormateado(new DecimalFormat("0.00")
					.format((Double) valor));
		} else if (valor instanceof BigDecimal) {
			resultado = getNumeroDecimalFormateado(new DecimalFormat("0.00")
					.format(((BigDecimal) valor).doubleValue()));
		} else if (valor instanceof Integer) {
			resultado = getNumeroDecimalFormateado(new DecimalFormat("0.00")
					.format((Integer) valor));
		} else if (valor instanceof String) {
			Double valorD = new Double((String) valor);
			resultado = getNumeroDecimalFormateado(new DecimalFormat("0.00")
					.format(valorD));
		}
		return resultado;
	}

	/**
	 * Devuelve un nï¿½mero con este formato '123455.0000'(en la BB.DD) a
	 * '123.455,0000' (nuestro formato de presentaciï¿½n en las pantallas).
	 * 
	 * @author mnieto
	 * @param numeroOrigen
	 * @return Devuelve un nï¿½mero con este formato '123455.0000'(en la BB.DD)
	 *         a '123.455,0000' (nuestro formato de presentaciÃ³n en las
	 *         pantallas)
	 */
	public static String getNumeroDecimalFormateadoCuatroDec(String numeroOrigen) {
		String decimal = Constantes.CADENA_CUATRO_CEROS;
		String signo = "", entera = new String(), nuevaCadena = new String();
		int i = 0;
		if (!esNullVacio(numeroOrigen)) {
			if ("-".equals(numeroOrigen.substring(0, 1))) {
				numeroOrigen = numeroOrigen.substring(1);
				signo = "-";
			}
			String cadena = numeroOrigen.replace(Constantes.PUNTO,
					Constantes.COMA);
			StringTokenizer st = new StringTokenizer(cadena, Constantes.COMA);
			if (st.hasMoreElements()) {
				entera = st.nextToken();
			}
			int longitud = entera.length();
			while (i < entera.length()) {
				if (((longitud - i) % 3) != 0) {
					nuevaCadena = nuevaCadena + entera.charAt(i);
				} else {
					nuevaCadena = nuevaCadena + Constantes.PUNTO
							+ entera.charAt(i);
				}
				i++;
			}
			// tratamiento de la parte decimal
			if (st.hasMoreElements()) {
				decimal = st.nextToken();
				if (decimal.length() > 1) {
					if (decimal.equals(Constantes.VALOR_INICIAL_CERO) == false) {
						decimal = decimal.substring(0, decimal.length());
					}
				} else {
					// Si solo tiene un decimal aï¿½adimos un 0.
					decimal = decimal + "0";
				}
			}
			if (nuevaCadena.charAt(0) == '.') {
				nuevaCadena = nuevaCadena.substring(1);
			}
			nuevaCadena = signo.concat(nuevaCadena.concat(Constantes.COMA
					+ decimal));
		}
		return nuevaCadena;
	}

	public static String getNumeroDecimalFormateadoCuatroDec(Object valor) {
		String resultado = "0,0000";
		if (valor instanceof Double) {
			resultado = getNumeroDecimalFormateadoCuatroDec(new DecimalFormat(
					"0.0000").format((Double) valor));
		} else if (valor instanceof BigDecimal) {
			resultado = getNumeroDecimalFormateadoCuatroDec(new DecimalFormat(
					"0.0000").format(((BigDecimal) valor).doubleValue()));
		} else if (valor instanceof Integer) {
			resultado = getNumeroDecimalFormateadoCuatroDec(new DecimalFormat(
					"0.0000").format((Integer) valor));
		} else if (valor instanceof String) {
			Double valorD = new Double((String) valor);
			resultado = getNumeroDecimalFormateadoCuatroDec(new DecimalFormat(
					"0.0000").format(valorD));
		}
		return resultado;
	}

	/**
	 * Metodo que formatea un decimal, lo redondea y lo devuelve con punto en
	 * vez de coma. Se utiliza en las funciones python.
	 * 
	 * @param valor
	 *            Object
	 * @return String
	 */
	public static String devolverDecimalConPunto(Object valor) {
		String resultado = "0,00";
		resultado = getNumeroDecimalFormateado(valor);
		if (resultado.contains(Constantes.PUNTO)) {
			resultado = resultado.replace(Constantes.PUNTO,
					Constantes.VALOR_INICIAL_VACIO);
		}
		if (resultado.contains(Constantes.COMA)) {
			resultado = resultado.replace(Constantes.COMA, Constantes.PUNTO);
		}
		return resultado;
	}

	/**
	 * @author jmlezcano MÃ©todo que obtiene de un elemento seleccionado el
	 *         numero
	 * @param number
	 * @return
	 */
	public static Integer devuelveNumerodeString(String number) {
		String cadena = null;
		Integer num = null;
		StringTokenizer st = new StringTokenizer(number, " ");
		if (st.hasMoreElements()) {
			cadena = st.nextToken();
			num = Integer.valueOf(cadena);
		}
		return num;
	}

	/**
	 * Creates a string from an integer value using the specified base and pads
	 * it with zeros at the start to fill the supplied length. e/g padZeros (5,
	 * 1, 10) returns "00001". If the value is negative then the minus sign is
	 * included in the length. eg padZeros (5, -1, 10) returns "-0001".
	 * 
	 * @param length
	 *            the length of the returned string after padding with zeros
	 * @param value
	 *            the value to be padded with zeros.
	 * @param base
	 *            the base to use for the number when represented as a string.
	 */

	public static String padZeros(int length, int value, int base) {
		if (value < 0)
			return "-" + padZeros(length - 1, Math.abs(value), base);
		String s = Integer.toString(value, base);
		int padCount = length - s.length();
		StringBuffer buffer = new StringBuffer(length);
		for (int i = 0; i < padCount; i++)
			buffer.append('0');
		buffer.append(s);
		return buffer.toString();
	}

	public static String padZeros(int length, String value, int base) {
		StringBuffer buffer = new StringBuffer(length);
		if (!esNullVacio(value)) {
			int padCount = length - value.trim().length();
			for (int i = 0; i < padCount; i++)
				buffer.append('0');
			buffer.append(value);
		}
		return buffer.toString();
	}

	public static String padZerosFinal(int length, String value) {
		int padCount = length - value.length();
		StringBuffer buffer = new StringBuffer(length);
		buffer.append(value);
		for (int i = 0; i < padCount; i++)
			buffer.append('0');
		return buffer.toString();
	}

	public static String padSpaces(int length, String value, int base) {
		int padCount = length - value.length();
		StringBuffer buffer = new StringBuffer(length);
		buffer.append(value);
		for (int i = 0; i < padCount; i++)
			buffer.append(" ");
		return buffer.toString();
	}

	public static String padZeros(int length, long value, int base) {
		if (value < 0)
			return "-" + padZeros(length - 1, Math.abs(value), base);
		String s = Long.toString(value, base);
		int padCount = length - s.length();
		StringBuffer buffer = new StringBuffer(length);
		for (int i = 0; i < padCount; i++)
			buffer.append('0');
		buffer.append(s);
		return buffer.toString();
	}

	/**
	 * Creates a string from an integer value and pads it with zeros at the
	 * start to fill the supplied length. eg padZeros (5, 1) returns "00001". If
	 * the value is negative then the minus sign is included in the length. eg
	 * padZeros (5, -1) returns "-0001". The base used for the returned string
	 * is base 10.
	 * 
	 * @param length
	 *            the length of the returned string after padding with zeros
	 * @param value
	 *            the value to be padded with zeros.
	 */

	public static String padZeros(int length, int value) {
		return padZeros(length, value, 10);
	}

	public static String padZeros(int length, long value) {
		return padZeros(length, value, 10);
	}

	public static String padZeros(int length, String value) {
		return padZeros(length, value, 10);
	}

	public static String padSpaces(int length, String value) {
		return padSpaces(length, value, 10);
	}

	/**
	 * Obtener el valor de una etiqueda de mensajes.properties.
	 * 
	 * @author rquiros
	 * @param etiqueta
	 * @return String
	 */
	public static String getEtiquetaMensajes(String etiqueta) {
		return sNoNull(ResourceBundle.getBundle("mensajes").getString(etiqueta));
	}

	/**
	 * Mï¿½todo que obtiene el nï¿½mero de RUE, dicho nï¿½mero consta de 22
	 * dï¿½gitos, construido de la siguiente forma: Cï¿½digo del circuito (4)
	 * Versiï¿½n del circuito (1) Cï¿½digo territorial (6) Aï¿½o de apertura (4)
	 * Nï¿½mero de expediente consecutivo por circuito, versiï¿½n, cï¿½digo
	 * territorial y aï¿½o (6) Dï¿½gito de control por RUE El dï¿½gito de
	 * control se calcula de la siguiente forma: Dividir las 21 cifras de la
	 * referencia ï¿½nica por 7, obteniendo el resto, si el resto se 0, el
	 * dï¿½gito de control serï¿½ 0, en caso contrario se obtiene el complemento
	 * a 7 del resto (7 - resto), siendo el resultado obtenido el dï¿½gito de
	 * control.
	 * 
	 * @param codigoCircuito
	 *            Cï¿½digo del circuito.
	 * @param version
	 *            versiï¿½n del circuito.
	 * @param codigoTerritorial
	 *            cï¿½digo territorial.
	 * @param anoApertura
	 *            Aï¿½o de apertura.
	 * @param secuencial
	 *            expediente consecutivo.
	 * @return String nï¿½mero RUE.
	 */
	public static String obtenerNumeroRue(String codigoCircuito,
			Integer version, String codigoTerritorial, Integer anoApertura,
			Integer secuencial) {
		String numeroRUE = new String();
		numeroRUE = numeroRUE.concat(codigoCircuito);
		numeroRUE = numeroRUE.concat(version.toString());
		numeroRUE = numeroRUE.concat(codigoTerritorial);
		numeroRUE = numeroRUE.concat(anoApertura.toString());
		numeroRUE = numeroRUE
				.concat(TratamientoDeDatos.padZeros(6, secuencial));
		BigInteger nRUE = new BigInteger(numeroRUE);
		BigInteger resto = nRUE.remainder(new BigInteger(
				Constantes.NUM_DIV_DC_RUE.toString()));
		Integer digitoControl = resto.intValue();
		if (digitoControl.intValue() != Constantes.RESTO_EXACTO_EN_DC_RUE) {
			digitoControl = Constantes.NUM_DIV_DC_RUE - resto.intValue();
		}
		numeroRUE = numeroRUE.concat(digitoControl.toString());
		return numeroRUE;
	}

	/**
	 * @param number
	 * @return
	 */
	public static String recortaCeros(String number) {
		if (number.endsWith(Constantes.PUNTO
				.concat(Constantes.CADENA_DOS_CEROS))) {
			number = number.substring(0, number.length() - 3);
		}
		return number;
	}

	/**
	 * Mï¿½todo que transforma el nombre de las columnas en su respectivo en
	 * forma de atributo. Quita los _ y cambia la primera letra a mayusculas.
	 * Ejemplo: nombre_corto -> nombreCorto
	 * 
	 * @param nombreColumna
	 *            . String
	 * @return Cadena transformada.
	 */
	public static String transformarNombreColumna(String nombreColumna) {
		String nombreAtributo = "", letraUp = "", resto = "", aux[];
		nombreColumna = nombreColumna.trim();
		if (!esNullCeroVacio(nombreColumna)) {
			if (nombreColumna.contains("_")) {
				aux = nombreColumna.split("_");
				for (int j = 0; j < aux.length; j++) {
					if (!esNullCeroVacio(aux[j])) {
						letraUp = aux[j].substring(0, 1);
						resto = aux[j].substring(1);
						letraUp = letraUp.toUpperCase();
						nombreAtributo += letraUp + resto;
					}
				}
			}
			if (!esNullVacio(nombreAtributo)) {
				letraUp = nombreAtributo.substring(0, 1);
				resto = nombreAtributo.substring(1);
				letraUp = letraUp.toLowerCase();
				nombreAtributo = letraUp + resto;
			} else {
				nombreAtributo = nombreColumna;
			}
		}
		return nombreAtributo;
	}

	/**
	 * Mï¿½todo que devuelve el nombre del mï¿½todo set de la columna pasada por
	 * parï¿½metros
	 * 
	 * @param nombreColumna
	 *            , nombre del atributo o columna.
	 * @param transformar
	 *            , true-> se transforma de columna a atributo
	 * @return
	 */
	public static String getMetodoSet(String nombreColumna, Boolean transformar) {
		String nombreMetodo = "", letraUp = "", resto = "";
		if (!esNullCeroVacio(nombreColumna)) {
			if (transformar) {
				nombreColumna = transformarNombreColumna(nombreColumna);
			}
			letraUp = nombreColumna.substring(0, 1).toUpperCase();
			resto = nombreColumna.substring(1);
			nombreMetodo = Constantes.METODO_SET + letraUp + resto;
		}
		return nombreMetodo;
	}

	/**
	 * Mï¿½todo que devuelve el nombre del mï¿½todo set de la columna pasada por
	 * parï¿½metros
	 * 
	 * @param nombreColumna
	 *            , nombre del atributo o columna.
	 * @param transformar
	 *            , true-> se transforma de columna a atributo
	 * @return
	 */
	public static Method getMetodoSet(Object objeto, String nombreColumna,
			Boolean transformar, Class<?>... parameterTypes) {
		Method metodoSet = null;

		if (!esNullCeroVacio(nombreColumna)) {
			String nombreMetodo = TratamientoDeDatos.getMetodoSet(
					nombreColumna, transformar);

			try {
				metodoSet = objeto.getClass().getMethod(nombreMetodo,
						parameterTypes);
			} catch (SecurityException e) {
				LOG.error(
						"Error de seguridad al intentar obtener el valor del atributo "
								+ nombreColumna + " en la clase "
								+ objeto.getClass().getName(), e);
			} catch (NoSuchMethodException e) {
				LOG.error("No existe el atributo " + nombreColumna
						+ " en la clase " + objeto.getClass().getName(), e);
			}
		}

		return metodoSet;
	}

	public static Method getMetodoSet(Object objeto, String nombreColumna,
			Boolean transformar) {
		Method metodoSet = null;

		if (!esNullCeroVacio(nombreColumna)) {
			String nombreMetodo = TratamientoDeDatos.getMetodoSet(
					nombreColumna, transformar);

			try {
				Class<?> claseType = TratamientoDeDatos.obtenerReturnType(
						nombreColumna, objeto, transformar);

				metodoSet = objeto.getClass()
						.getMethod(nombreMetodo, claseType);
			} catch (SecurityException e) {
				LOG.error(
						"Error de seguridad al intentar obtener el valor del atributo "
								+ nombreColumna + " en la clase "
								+ objeto.getClass().getName(), e);
			} catch (NoSuchMethodException e) {
				LOG.error("No existe el atributo " + nombreColumna
						+ " en la clase " + objeto.getClass().getName(), e);
			}
		}

		return metodoSet;
	}

	/**
	 * Mï¿½todo que devuelve el nombre del mï¿½todo get de la columna pasada por
	 * parï¿½metros
	 * 
	 * @param nombreColumna
	 *            , nombre del atributo o columna.
	 * @param transformar
	 *            , true-> se transforma de columna a atributo
	 * @return
	 */
	public static String getMetodoGet(String nombreColumna, Boolean transformar) {
		String nombreMetodo = "", letraUp = "", resto = "";
		if (!esNullCeroVacio(nombreColumna)) {
			if (transformar) {
				nombreColumna = transformarNombreColumna(nombreColumna);
			}
			letraUp = nombreColumna.substring(0, 1).toUpperCase();
			resto = nombreColumna.substring(1);
			nombreMetodo = Constantes.METODO_GET + letraUp + resto;
		}
		return nombreMetodo;
	}

	/**
	 * Mï¿½todo para construir el anagrama de bï¿½squeda de los contribuyentes.
	 * 
	 * @return String - Devuelve el anagrama de bï¿½squeda
	 */
	public static String construirAnagramaBusqueda(String nomApeRazSoc) {
		String retorno = new String();
		if (!esNullVacio(nomApeRazSoc)) {
			// SIEMPRE SE TRATARAN LETRAS MAYUSCULAS
			nomApeRazSoc = nomApeRazSoc.toUpperCase();
			// QUITAR LAS TILDES
			nomApeRazSoc = nomApeRazSoc.replace("ï¿½", "A");
			nomApeRazSoc = nomApeRazSoc.replace("ï¿½", "E");
			nomApeRazSoc = nomApeRazSoc.replace("ï¿½", "I");
			nomApeRazSoc = nomApeRazSoc.replace("ï¿½", "O");
			nomApeRazSoc = nomApeRazSoc.replace("ï¿½", "U");

			// reemplazar . (punto), ,(coma) y '(comilla) por " " (espacio) para
			// que
			// separe las palabras
			// nomApeRazSoc = nomApeRazSoc.replaceAll("\\.", " ")
			// .replaceAll("\\,", " ")
			// .replaceAll("'", " ");

			// TODO: falta por cambiar el '
			// y algunos caracteres hexadecimales
			StringBuilder aux = new StringBuilder();
			aux.append((char) 209);
			String caracterEnie = aux.toString();
			nomApeRazSoc = nomApeRazSoc.replaceAll("\"", caracterEnie)
					.replaceAll("\\'", caracterEnie)
					.replaceAll("\\,", caracterEnie)
					.replaceAll("\\.", caracterEnie)
					.replaceAll("\\,", caracterEnie)
					.replaceAll("\\ï¿½", caracterEnie)
					.replaceAll("\\?", caracterEnie)
					.replaceAll("\\\\", caracterEnie)
					.replaceAll("\\/", caracterEnie)
					.replaceAll("=", caracterEnie);
			String[] nomApe = nomApeRazSoc.split(" ");
			if (!esNullVacio(nomApe)) {
				// limpiar las excepciones
				nomApe = limpiarExcepcionesCadena(nomApe);
				nomApe = expandirAbreviaturas(nomApe);
				// CAMBIO EN LA FORMA DE Cï¿½LCULO DEL ANAGRAMA, INDICADO POR LA
				// AEAT
				String strUno = "", strDos = "", strTres = "";
				if (nomApe.length > 0) {
					// Procesamos la primera palabra
					if (nomApe[0].length() < 4) {
						strUno = TratamientoDeDatos.padSpaces(4, nomApe[0]);
					} else {
						strUno = nomApe[0].substring(0, 4);
					}
					if (nomApe.length >= 2) { // El nombre contiene dos o mï¿½s
						// palabras
						if (nomApe[1].length() < 3) {
							strDos = TratamientoDeDatos.padSpaces(3, nomApe[1]);
						} else {
							strDos = nomApe[1].substring(0, 3);
						}
						if (nomApe.length > 2) { // El nombre contiene mï¿½s de
							// dos palabras
							if (nomApe[2].length() > 0) {
								strTres = nomApe[2].substring(0, 1);
							}
						}
					}
					retorno = TratamientoDeDatos.padSpaces(8, strUno + strDos
							+ strTres);
				}
			}
		}
		return retorno.toUpperCase();
	}

	public static String pasarAMayusculas(String men) {
		String result = "";
		if (!esNullCeroVacio(men)) {
			result = men.toUpperCase();
		}
		return result;
	}

	public static String pasarAMinusculas(String men) {
		String result = "";
		if (!esNullCeroVacio(men)) {
			result = men.toLowerCase();
		}
		return result;
	}

	/**
	 * Pasar la 1 letra de una cadena a minuscula.
	 * 
	 * @param cadena
	 *            - String
	 * @return String
	 */
	public static String pasarPrimeraLetraAMinuscula(String cadena) {
		String result = "";
		if (!esNullVacio(cadena)) {
			result = sNoNull(cadena.charAt(0)).toLowerCase()
					+ cadena.substring(1);
		}
		return result;
	}

	/**
	 * Pasar la 1 letra de una cadena a mayuscula.
	 * 
	 * @param cadena
	 *            - String
	 * @return String
	 */
	public static String pasarPrimeraLetraAMayuscula(String cadena) {
		String result = "";
		if (!esNullVacio(cadena)) {
			result = sNoNull(cadena.charAt(0)).toUpperCase()
					+ cadena.substring(1);
		}
		return result;
	}

	/**
	 * Mï¿½todo para excluir de un nombre, apellido o razï¿½n social los
	 * artï¿½culos
	 * 
	 * @param nomApe
	 *            - String[]
	 * @return String[] - cadena sin cadenas excluidas
	 */
	private static String[] limpiarExcepcionesCadena(String[] nomApe) {
		// array auxiliar
		String[] retornoAux = new String[nomApe.length];
		int i = 0;
		for (String object : nomApe) {
			if (!esNullVacio(object)
					// no insertar los espaciones en blanco
					&& !esNullVacio(object.trim())
					&& !object.equalsIgnoreCase(Constantes.EXCEPCION_Y)
					&& !object.equalsIgnoreCase(Constantes.EXCEPCION_DE)
					&& !object.equalsIgnoreCase(Constantes.EXCEPCION_LA)
					&& !object.equalsIgnoreCase(Constantes.EXCEPCION_DEL)
					&& !object.equalsIgnoreCase(Constantes.EXCEPCION_EL)
					&& !object.equalsIgnoreCase(Constantes.EXCEPCION_LOS)
					&& !object.equalsIgnoreCase(Constantes.EXCEPCION_LAS)
					&& !object.equalsIgnoreCase(Constantes.EXCEPCION_AL)
					&& !object.equalsIgnoreCase(Constantes.EXCEPCION_UN)
					&& !object.equalsIgnoreCase(Constantes.EXCEPCION_I)
					&& !object.equalsIgnoreCase(Constantes.EXCEPCION_SA)
					&& !object.equalsIgnoreCase(Constantes.EXCEPCION_SL)
					&& !object.equalsIgnoreCase(Constantes.EXCEPCION_SOC)
					&& !object.equalsIgnoreCase(Constantes.EXCEPCION_LTDA)
					&& !object.equalsIgnoreCase(Constantes.EXCEPCION_SAT)
					&& !object.equalsIgnoreCase(Constantes.EXCEPCION_CIA)
					&& !object.equalsIgnoreCase(Constantes.EXCEPCION_ASOC)
					&& !object.equalsIgnoreCase(Constantes.EXCEPCION_COOP)
					&& !object.equalsIgnoreCase(Constantes.EXCEPCION_SAE)
					&& !object.equalsIgnoreCase(Constantes.EXCEPCION_SC)
					&& !object.equalsIgnoreCase(Constantes.EXCEPCION_SCL)
					&& !object.equalsIgnoreCase(Constantes.EXCEPCION_SDAD)
					&& !object.equalsIgnoreCase(Constantes.EXCEPCION_SRC)
					&& !object.equalsIgnoreCase(Constantes.EXCEPCION_CB)
					&& !object.equalsIgnoreCase(Constantes.EXCEPCION_CDAD)
					&& !object.equalsIgnoreCase(Constantes.EXCEPCION_COMPANIA)
					&& !object.equalsIgnoreCase(Constantes.EXCEPCION_LIMITADA)
					&& !object
							.equalsIgnoreCase(Constantes.EXCEPCION_ASOCIACION)
					&& !object
							.equalsIgnoreCase(Constantes.EXCEPCION_COOPERATIVA)
					&& !object.equalsIgnoreCase(Constantes.EXCEPCION_BIENES)
					&& !object.equalsIgnoreCase(Constantes.EXCEPCION_COMUNIDAD)
					&& !object.equalsIgnoreCase(Constantes.EXCEPCION_HNOS)
					&& !object
							.equalsIgnoreCase(Constantes.EXCEPCION_PROPIETARIOS)
					&& !TratamientoDeDatos.isNumber(object)) {
				retornoAux[i] = object;
				i++;
			}
		}
		String[] retorno = new String[i];
		for (int j = 0; j < i; j++) {
			retorno[j] = retornoAux[j];
		}
		return retorno;
	}

	/**
	 * Expandir las posibles abreviaturas de la cadena.
	 * 
	 * @param nomApe
	 *            - String[]
	 * @return String[]
	 */
	private static String[] expandirAbreviaturas(String[] nomApe) {
		for (int i = 0; i < nomApe.length; i++) {
			String auxCad = nomApe[i];
			if (!esNullVacio(auxCad)) {
				if (auxCad.equals(Constantes.ABREV_ANAGRAMA_BUSQ_ALREZ)) {
					nomApe[i] = Constantes.ABREV_ANAGRAMA_BUSQ_ALREZ_L;
				} else if (auxCad.equals(Constantes.ABREV_ANAGRAMA_BUSQ_DGUEZ)) {
					nomApe[i] = Constantes.ABREV_ANAGRAMA_BUSQ_DGUEZ_L;
				} else if (auxCad.equals(Constantes.ABREV_ANAGRAMA_BUSQ_FDEZ)
						|| auxCad.equals(Constantes.ABREV_ANAGRAMA_BUSQ_FEDEZ)) {
					nomApe[i] = Constantes.ABREV_ANAGRAMA_BUSQ_FDEZ_L;
				} else if (auxCad.equals(Constantes.ABREV_ANAGRAMA_BUSQ_GLEZ)) {
					nomApe[i] = Constantes.ABREV_ANAGRAMA_BUSQ_GLEZ_L;
				} else if (auxCad.equals(Constantes.ABREV_ANAGRAMA_BUSQ_GRREZ)) {
					nomApe[i] = Constantes.ABREV_ANAGRAMA_BUSQ_GRREZ_L;
				} else if (auxCad.equals(Constantes.ABREV_ANAGRAMA_BUSQ_HDEZ)) {
					nomApe[i] = Constantes.ABREV_ANAGRAMA_BUSQ_HDEZ_L;
				} else if (auxCad.equals(Constantes.ABREV_ANAGRAMA_BUSQ_JNEZ)) {
					nomApe[i] = Constantes.ABREV_ANAGRAMA_BUSQ_JNEZ_L;
				} else if (auxCad.equals(Constantes.ABREV_ANAGRAMA_BUSQ_MNEZ)) {
					nomApe[i] = Constantes.ABREV_ANAGRAMA_BUSQ_MNEZ_L;
				} else if (auxCad.equals(Constantes.ABREV_ANAGRAMA_BUSQ_RGUEZ)) {
					nomApe[i] = Constantes.ABREV_ANAGRAMA_BUSQ_RGUEZ_L;
				} else if (auxCad.equals(Constantes.ABREV_ANAGRAMA_BUSQ_SCHAZ)) {
					nomApe[i] = Constantes.ABREV_ANAGRAMA_BUSQ_SCHAZ_L;
				} else if (auxCad.equals(Constantes.ABREV_ANAGRAMA_BUSQ_VQUEZ)) {
					nomApe[i] = Constantes.ABREV_ANAGRAMA_BUSQ_VQUEZ_L;
				} else if (auxCad.equals(Constantes.ABREV_ANAGRAMA_BUSQ_FCO)) {
					nomApe[i] = Constantes.ABREV_ANAGRAMA_BUSQ_FCO_L;
				}
			}
		}
		return nomApe;
	}

	/**
	 * Calcular la letra para un numero de DNI.
	 * 
	 * @param dni
	 *            - int
	 * @return String - letra que le corresponde
	 */
	public static String calcularLetraNif(int dni) {
		String letra = "";
		try {
			if (!esNullVacio(dni)) {
				letra = Constantes.NIF_STRING_ASOCIATION.substring(dni % 23,
						(dni % 23) + 1);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
			letra = "";
		}
		return letra;
	}

	/**
	 * Comprueba si el nif pasado por parï¿½metro es vï¿½lido.
	 * 
	 * @param nif
	 *            - String
	 * @return Boolean resultado de la comprobaciï¿½n.
	 */
	public static Boolean comprobarNif(String nif) {
		boolean correcto = false;
		if (!TratamientoDeDatos.esNullVacio(nif)) {
			if (nif.toUpperCase().startsWith("X")
					|| nif.toUpperCase().startsWith("Y")
					|| nif.toUpperCase().startsWith("Z"))
				nif = nif.substring(1);
			Pattern nifPattern = Pattern
					.compile("(\\d{1,8})([TRWAGMYFPDXBNJZSQVHLCKEtrwagmyfpdxbnjzsqvhlcke])");
			Matcher m = nifPattern.matcher(nif);
			if (m.matches()) {
				String letra = m.group(2);
				// Extraer letra del NIF
				String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
				int dni = Integer.parseInt(m.group(1));
				dni = dni % 23;
				String reference = letras.substring(dni, dni + 1);
				if (reference.equalsIgnoreCase(letra)) {
					correcto = true;
				} else {
					correcto = false;
				}
			} else {
				correcto = false;
			}
		}
		return correcto;
	}

	/**
	 * Convertir un valor a una cantidad para mostrar en pantalla.
	 * 
	 * @param valor
	 *            - Object
	 * @return String formateado a cantidad
	 */
	public static String valorACantidad(Object valor) {
		String cantidad = "";
		try {
			// comprobar que tipo entra
			if (!esNullVacio(valor)) {
				if (valor instanceof String) {
					valor = ((String) valor).replaceFirst("^0*", "");
					valor = new Integer((String) valor);
				}
				// controlar los valores que ya entran con formato, por ejemplo
				// 5.000,00
				valor = doubleNoNullEsCero(valor);
				// mantener siempre 2 decimales
				DecimalFormat f = new DecimalFormat(Constantes.FORMATO_IMPORTE);
				cantidad = f.format(valor);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
			cantidad = "";
		}
		return cantidad;
	}

	/**
	 * Obtener el digito de control del numero de documento.
	 * modelo+version+secuencial+digito control 13: 3+1+8+1
	 * 
	 * @param numDocumento
	 *            - 13 digitos
	 * @return digito control - Integer.
	 */
	public static Integer getDigitoControlDocumento(String numDocumento) {
		Integer digitoControl = null;

		if (!esNullVacio(numDocumento)) {
			digitoControl = iNoNullEsNull(numDocumento.substring(numDocumento
					.length() - 1));
		}
		return digitoControl;
	}

	/**
	 * Obtener el modelo del numero de documento.
	 * modelo+version+secuencial+digito control 13: 3+1+8+1
	 * 
	 * @param numDocumento
	 *            - 13 digitos
	 * @return modelo documento - Integer.
	 */
	public static Integer getModeloDocumento(String numDocumento) {
		Integer modelo = null;

		if (!esNullVacio(numDocumento)) {
			modelo = iNoNullEsNull(numDocumento.substring(0, 3));
		}
		return modelo;
	}

	/**
	 * Obtener el modelo del numero de documento.
	 * modelo+version+secuencial+digito control 13: 3+1+8+1
	 * 
	 * @param numDocumento
	 *            - 13 digitos
	 * @return modelo documento - Integer.
	 */
	public static Integer getSecuencialModeloDocumento(String numDocumento) {
		Integer secuencialModelo = null;

		if (!esNullVacio(numDocumento)) {
			secuencialModelo = iNoNullEsNull(numDocumento.substring(4, 12));
		}
		return secuencialModelo;
	}

	/**
	 * Convertir BigDecimal a Double.
	 * 
	 * @param cadena
	 *            - Object
	 * @return Double
	 */
	public static Double bigDecimalToDouble(Object cadena) {
		Double result = new Double(0);
		try {
			if (cadena != null && isBigDecimal(cadena)) {

				result = Double.valueOf(cadena.toString());
			}
		} catch (Exception e) {
			result = new Double(0);
		}
		return result;
	}

	/**
	 * Convertir float a Integer.
	 * 
	 * @param number
	 *            - Object
	 * @return Integer Devuelve -1 en caso de error
	 */
	public static Integer floatToInteger(Float number) {
		Integer result = Integer.valueOf(0);
		try {
			if (!esNullVacio(number)) {
				result = number.intValue();
			}
		} catch (Exception e) {
			result = new Integer(-1);
		}
		return result;
	}

	/**
	 * Convertir float a Double.
	 * 
	 * @param number
	 *            - Object
	 * @return Integer Devuelve -1 en caso de error
	 */
	public static Double floatToDouble(Float number) {
		Double result = new Double(0);
		try {
			if (!esNullVacio(number)) {
				result = Double.parseDouble(number.toString());
			}
		} catch (Exception e) {
			result = new Double(0);
		}
		return result;
	}

	/**
	 * @author rquiros
	 * @param cadena
	 *            - Object
	 * @return Float si es numero sino null
	 */
	public static Float floatNoNull(Object cadena) {
		Float resul = null;
		if (cadena == null || cadena.equals("null")
				|| cadena.equals("undefined")) {
			resul = null;
		} else {
			try {
				resul = Float.parseFloat(cadena.toString());
			} catch (Exception e) {
				resul = null;
			}
		}
		return resul;
	}

	public static boolean validateCif(String cif) {
		Boolean valido = true;
		try {
			cif = cif.toUpperCase();
			int valor = -1;
			int longitud = cif.length();
			if (!TratamientoDeDatos.esNullCeroVacio(longitud) && longitud > 0) {
				if (cif.startsWith("X")) {
					valor = TratamientoDeDatos.iNoNullEsNull(cif.substring(1,
							cif.length() - 1));
				} else if (cif.startsWith("Y")) {
					valor = 10000000 + TratamientoDeDatos.iNoNullEsNull(cif
							.substring(1, cif.length() - 1));
				} else if (cif.startsWith("Z")) {
					valor = 20000000 + TratamientoDeDatos.iNoNullEsNull(cif
							.substring(1, cif.length() - 1));
				} else if (TratamientoDeDatos.isNumber(cif.charAt(0))
						&& longitud > 1) {
					valor = TratamientoDeDatos.iNoNullEsNull(cif.substring(0,
							cif.length() - 1));
				}
				if (valor != -1) {

					if (cif.endsWith(""
							+ Constantes.NIF_STRING_ASOCIATION
									.charAt(valor % 23)) == true) {
						valido = true;
					} else
						valido = false;
				} else {
					if (!cifPattern.matcher(cif).matches()) {
						// No cumple el patrï¿½n
						return false;
					}
					int parA = 0;
					for (int i = 2; i < 8; i += 2) {
						final int digito = Character.digit(cif.charAt(i), 10);
						if (digito < 0) {
							return false;
						}
						parA += digito;
					}
					int nonB = 0;
					for (int i = 1; i < 9; i += 2) {
						final int digito = Character.digit(cif.charAt(i), 10);
						if (digito < 0) {
							return false;
						}
						int nn = 2 * digito;
						if (nn > 9) {
							nn = 1 + (nn - 10);
						}
						nonB += nn;
					}
					final int parcialC = parA + nonB;
					final int digitoE = parcialC % 10;
					final int digitoD = (digitoE > 0) ? (10 - digitoE) : 0;
					final char letraIni = cif.charAt(0);
					final char caracterFin = cif.charAt(8);
					final boolean esControlValido =
					// ï¿½el caracter de control es vï¿½lido como letra?
					(CONTROL_SOLO_NUMEROS.indexOf(letraIni) < 0 && CONTROL_NUMERO_A_LETRA
							.charAt(digitoD) == caracterFin) ||
					// ï¿½el caracter de control es vï¿½lido como
					// dï¿½gito?
							(CONTROL_SOLO_LETRAS.indexOf(letraIni) < 0 && digitoD == Character
									.digit(caracterFin, 10));
					return esControlValido;
				}
			}
		} catch (Exception e) {
			return false;
		}
		return valido;
	}

	/**
	 * Redondeamos el importe segun el modo que queramos, al alza, a la baja,
	 * ect ect.
	 * 
	 * @param valor
	 *            Double
	 * @param modo
	 *            RoundingMode
	 * @return Double
	 * @author mgraham
	 */
	public static Double redondearImporte(Double valor, RoundingMode modo) {
		String val = valor + "";
		BigDecimal big = new BigDecimal(val);
		// En el setScale se declara el numero de decimales y el tipo de
		// redondeo.
		big = big.setScale(2, modo);
		return big.doubleValue();
	}

	/**
	 * Redondea la cantidad pasada por parametro a las cifras decimales
	 * indicadas
	 * 
	 * @param bd
	 *            Cantidad a redondear
	 * @param decimalPlace
	 * @return
	 */
	public static BigDecimal redondea(BigDecimal bd, int numDecimales) {
		if (bd == null)
			return null;
		bd = bd.setScale(numDecimales, BigDecimal.ROUND_HALF_UP);
		return bd;
	}

	// Entra un numerico en forma de object lo redondea y lo transforma en
	// object de nuevo.
	public static Object redondea(Object number, int numDecimales) {
		if (number == null)
			return null;
		BigDecimal bd = new BigDecimal(number.toString());
		bd = redondea(bd, numDecimales);
		return (Object) bd;
	}

	/**
	 * Funciï¿½n que elimina acentos y caracteres especiales de una cadena de
	 * texto. NO CAMBIAR, SE UTILIZA EN VALIDACIONES.
	 * 
	 * @param String
	 *            texto
	 * @return String
	 * @author mgraham
	 */
	public static String eliminarCaracteresEspeciales(String texto) {
		Pattern regex = Pattern
				.compile("^[a-zA-Z ï¿½ï¿½ï¿½ï¿½ï¿½Aï¿½ï¿½ï¿½ï¿½ï¿½ï¿½]+$");
		Matcher regexMatcher = regex.matcher(texto);
		for (int i = 0; i < 1; i++) {
			while (regexMatcher.find()) {
				String find = regexMatcher.group();
				find = find.replace("ï¿½", "e");
				find = find.replace("ï¿½", "i");
				find = find.replace("ï¿½", "a");
				find = find.replace("ï¿½", "o");
				find = find.replace("ï¿½", "u");
				texto = find;
			}
		}
		String nrml = Normalizer.normalize(texto, Normalizer.Form.NFD);
		StringBuilder stripped = new StringBuilder();
		for (int i = 0; i < nrml.length(); ++i) {
			if (Character.getType(nrml.charAt(i)) != Character.NON_SPACING_MARK) {
				stripped.append(nrml.charAt(i));
			}
		}
		return stripped.toString();
	}

	/**
	 * Funcion que comprueba que la cadena introducida no contenga caracteres
	 * especiales. Solo valores alfanumericos. NO CAMBIAR, SE UTILIZA EN
	 * VALIDACIONES.
	 * 
	 * @param String
	 *            texto
	 * @return Boolean
	 * @author mgraham
	 */
	public static Boolean contieneCaracteresNoAlfanumerico(String texto) {
		Boolean noContiene = true;
		Pattern regex = Pattern.compile("[^0-9a-zA-Z]");
		Matcher regexMatcher = regex.matcher(texto);
		for (int i = 0; i < 1; i++) {
			while (regexMatcher.find()) {
				noContiene = false;
			}
		}
		return noContiene;
	}

	/**
	 * Tratar el numero de bastidor. Rellenar hasta
	 * 
	 * @param bastidor
	 *            - String
	 * @return String
	 */
	public static String formateoBastidor(String bastidor) {
		String bastidorFormateado = "";
		if (!TratamientoDeDatos.esNullVacio(bastidor)) {
			// Concatenamos al bastidor 17 espacios en blanco //
			bastidorFormateado = String.format("%1$-17s", bastidor)
					.toUpperCase();
		}
		return bastidorFormateado;
	}

	/**
	 * Comprobar si un email estÃ¡ bien formado.
	 * 
	 * @param email
	 *            - String
	 * @return Integer
	 */
	public static Integer comprobarEmail(String email) {
		Integer correcto = 0;
		try {
			if (email
					.matches("(?i)\\b[A-Z0-9._%+-]+@(?:[A-Z0-9-]+\\.)+[A-Z]{2,4}\\b")) {
				correcto = 1;
			}
		} catch (PatternSyntaxException ex) {
			// Syntax error in the regular expression
		}
		return correcto;
	}

	public static Boolean comprobarEmailBoolean(String email) {
		Boolean correcto = false;
		try {
			if (email
					.matches("(?i)\\b[A-Z0-9._%+-]+@(?:[A-Z0-9-]+\\.)+[A-Z]{2,4}\\b")) {
				correcto = true;
			}
		} catch (PatternSyntaxException ex) {
			// Syntax error in the regular expression
		}
		return correcto;
	}

	/**
	 * Método usado en validaciones para obtener el recargo.
	 * 
	 * @param fecha
	 *            pfechaPresentacion
	 * @param Date
	 *            pfechaInicio
	 * @author mgraham
	 * @return Integer
	 */
	public static Integer obtenerProcentajeRecargo610(
			String sfechaPresentacion, String sfechaInicio) {
		Integer porcentajeRecargo = 0;
		Date pfechaPresentacion = TratamientoFechas.stringToDate(
				sfechaPresentacion, "dd/MM/yyyy");
		Date pfechaInicio = TratamientoFechas.stringToDate(sfechaInicio);
		// Tenemos que sumarle 1 dia para que tengamos el primer dia con
		// recargo.
		pfechaInicio = TratamientoFechas.AddDia(pfechaInicio, 1);
		// Formateo de fechas para quitar horas
		pfechaInicio = TratamientoFechas.formateaFechaSinHora(pfechaInicio);
		Date fechaDesdeT5 = pfechaInicio, fechaHastaT5 = null;
		Date fechaDesdeT10 = null, fechaHastaT10 = null;
		Date fechaDesdeT15 = null, fechaHastaT15 = null;
		Date fechaDesdeT20 = null;
		// Fechas tramo donde el recargo se efectua al 5%.
		// El primer dia de recargo con 5% es la fechaInicio obtenida arriba
		// El ultimo dia de 5% le suma a la fDevengo 4 meses.
		fechaHastaT5 = TratamientoFechas.AddMes(pfechaInicio, 3);
		// Fechas tramo donde el recargo se efectua al 10%
		// El primer dia con recargo 10% es fechaFin de 5% + 1 dia.
		// El ultimo dia de recargo 10% serï¿½ la fechaDevengo + 7 meses.
		fechaDesdeT10 = TratamientoFechas.AddDia(fechaHastaT5, 1);
		fechaHastaT10 = TratamientoFechas.AddMes(pfechaInicio, 6);
		// Fechas tramo donde el recargo se efectua al 15%
		// El primer dia de recargo 15% es la fechaFin del 10 mas 1 dia.
		// El ultimo dia de recargo 15% serï¿½ la fechaDevengo + 13 meses.
		fechaDesdeT15 = TratamientoFechas.AddDia(fechaHastaT10, 1);
		fechaHastaT15 = TratamientoFechas.AddMes(pfechaInicio, 12);
		// Fechas tramo donde el recargo se efectua al 20%
		// El primer dia de recargo 20% es la fechaFin del 15 mas 1 dia.
		fechaDesdeT20 = TratamientoFechas.AddDia(fechaHastaT15, 1);
		// Tramo del 5%
		if (TratamientoFechas.fechaDentroPeriodo(pfechaPresentacion,
				fechaDesdeT5, fechaHastaT5)) {
			porcentajeRecargo = Integer.valueOf(5);
			// Tramo del 10%
		} else if (TratamientoFechas.fechaDentroPeriodo(pfechaPresentacion,
				fechaDesdeT10, fechaHastaT10)) {
			porcentajeRecargo = Integer.valueOf(10);
			// Tramo del 15%
		} else if (TratamientoFechas.fechaDentroPeriodo(pfechaPresentacion,
				fechaDesdeT15, fechaHastaT15)) {
			porcentajeRecargo = Integer.valueOf(15);
			// Tramos del 20% implica intereses
		} else if (TratamientoFechas.fechaMayorOIgual(fechaDesdeT20,
				pfechaPresentacion)) {
			porcentajeRecargo = Integer.valueOf(20);
		}
		return porcentajeRecargo;
	}

	public static String valorExpedienteCatastro(Object ejercicio,
			Object referencia, Object entidad) {
		String ejer = sNoNull(ejercicio), refer = sNoNull(referencia);
		String ent = sNoNull(entidad), expediente = null;
		if (!TratamientoDeDatos.esNullCeroVacio(ejer)
				&& !TratamientoDeDatos.esNullCeroVacio(refer)
				&& !TratamientoDeDatos.esNullCeroVacio(ent)) {
			ejer = ejer.substring(ejer.length() - 1, ejer.length());
			ent = ent.substring(ent.length() - 1, ent.length());
			expediente = refer + "." + ent + "/" + ejer;
		}
		return expediente;
	}

	public static Date valorFechaAlteracion(Object fecha) {
		String sFecha = sNoNull(fecha);
		String anio = null, mes = null, dia = null;
		Date fechaA = null;
		if (!esNullCeroVacio(sFecha)) {
			if (sFecha.length() == 8) {
				anio = sFecha.substring(0, 4);
				mes = sFecha.substring(4, 6);
				dia = sFecha.substring(6, 8);
				sFecha = dia + "/" + mes + "/" + anio;
				fechaA = TratamientoFechas.stringToDate(sFecha);
			}
		}
		return fechaA;
	}

	public static String valorFincaRegistral(Object numeroFinca) {
		String numFinca = sNoNull(numeroFinca);
		String finca = null;
		Integer fincaA = null, fincaB = null, fincaC = null;
		if (!TratamientoDeDatos.esNullCeroVacio(numFinca)) {
			if (numFinca.contains("null")) {
				numFinca = numFinca.replace("null", "");
			}
			finca = numFinca.substring(0, 2);
			fincaA = iNoNullEsCero(finca);
			finca = numFinca.substring(2, 5);
			fincaB = iNoNullEsCero(finca);
			finca = numFinca.substring(5, numFinca.length());
			fincaC = iNoNullEsCero(finca);
			finca = fincaA + " " + fincaB + " " + fincaC;
		}
		return finca;
	}

	/**
	 * Obtener la cadena del double.
	 * 
	 * @author rquiros
	 * @param cadena
	 * @return String si es un double
	 */
	public static String valorDoubleToString(Object cadena) {
		String resul = null;
		if (cadena == null || cadena.equals("null")
				|| cadena.equals("undefined")) {
			resul = null;
		} else {
			try {
				Double valor = doubleNoNull(cadena);
				resul = Double.toString(valor);
				resul = resul.replace(",", "");
				resul = resul.replace(".", ",");
			} catch (Exception e) {
				resul = null;
			}
		}
		return resul;
	}

	/**
	 * Formatea la cadena del double sin las apostrofe y sin reemplazando el .
	 * por ,.
	 * 
	 * @author rquiros
	 * @param cadena
	 * @return String si es un double
	 */
	public static String valorDoubleFormateadoODS(Object cadena) {
		String resul = null;
		if (cadena == null || cadena.equals("null")
				|| cadena.equals("undefined")) {
			resul = null;
		} else {
			try {
				Double valor = doubleNoNull(cadena);
				resul = Double.toString(valor);
				resul = resul.replace(",", "");
				resul = resul.replace(".", ",");
			} catch (Exception e) {
				resul = null;
			}
		}
		return resul;
	}

	/**
	 * Formatea la cadena numérica para que CVS no la convierta a Integer.
	 * 
	 * @author rquiros
	 * @param cadena
	 * @return String si es un double
	 */
	public static String valorCadenaToCadenaCVS(Object cadena) {
		String resul = null;
		if (cadena == null || cadena.equals("null")
				|| cadena.equals("undefined")) {
			resul = null;
		} else {
			try {
				resul = cadena.toString();
				resul = "" + resul;
			} catch (Exception e) {
				resul = null;
			}
		}
		return resul;
	}

	/**
	 * Metodo que devuelve el objeto double como un String para eliminar los E
	 * 
	 * @param valor
	 *            Double
	 * @return String
	 */
	public static String formatearDouble(Double valor) {
		NumberFormat formatter = new DecimalFormat(Constantes.FORMATO_IMPORTE);
		Double result = doubleNoNullEsCeroD(valor);
		String resultado = formatter.format(result);
		return resultado;
	}

	/**
	 * M�todo que recibe una cadena de texto, le quita las "enies" y las tildes,
	 * y la recorta a la longitud indicada por par�metro. El par�metro longitud
	 * es opcional. La cadena resultante se usa en la generaci�n del fichero de
	 * Apremios.
	 * 
	 * @param cadena
	 * @param longitud
	 * @return
	 */
	public static String limpiarCaracteresApremio(String cadena,
			Integer longitud) {
		int a1 = 0;
		cadena = sNoNull(cadena);
		for (int i = 0; i < cadena.length(); i++) {
			a1 = cadena.codePointAt(i);
			// Rango de enies raras
			if (a1 == 241) {
				cadena = cadena.replace(cadena.charAt(i) + "", "n");
			}
			if (a1 == 209) {
				cadena = cadena.replace(cadena.charAt(i) + "", "N");
			}
			// Rango de a raras
			if ((a1 > 223 && a1 < 230) || (a1 > 191 && a1 < 198)) {
				cadena = cadena.replace(cadena.charAt(i) + "", "A");
			}
			// Rango de e raras
			if ((a1 > 199 && a1 < 204) || (a1 > 231 && a1 < 236)) {
				cadena = cadena.replace(cadena.charAt(i) + "", "E");
			}
			// Rango de i raras
			if ((a1 > 203 && a1 < 208) || (a1 > 235 && a1 < 240)) {
				cadena = cadena.replace(cadena.charAt(i) + "", "I");
			}
			// Rango de o raras
			if ((a1 > 209 && a1 < 215) || (a1 > 241 && a1 < 247)) {
				cadena = cadena.replace(cadena.charAt(i) + "", "O");
			}
			// Rango de u raras
			if ((a1 > 216 && a1 < 221) || (a1 > 248 && a1 < 253)) {
				cadena = cadena.replace(cadena.charAt(i) + "", "U");
			}
		}
		if (!TratamientoDeDatos.esNullCeroVacio(longitud)) {
			if (cadena.length() > longitud) {
				cadena = cadena.substring(0, longitud);
			} else {
				cadena = TratamientoDeDatos.padSpaces(longitud, cadena);
			}
		}
		return cadena;
	}

	/**
	 * Lee un fichero y devuelve el resultado en un array de bytes
	 * 
	 * @param stRutaTotal
	 * @return
	 * @throws Exception
	 */
	public static byte[] leerBytesFichero(String stRutaTotal) throws Exception {
		byte[] contenidoDelFichero = null;

		File fi = new File(stRutaTotal);
		InputStream is = new FileInputStream(fi);

		byte[] buffer = new byte[8 * 1024];
		int leido = 0;

		BufferedInputStream bufferedInputStream = null;
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

		try {
			bufferedInputStream = new BufferedInputStream(is);
			while ((leido = bufferedInputStream.read(buffer)) >= 0)
				byteArrayOutputStream.write(buffer, 0, leido);

			// Fichero leido del todo, pasamos el contenido del BOS al byte[] //
			contenidoDelFichero = byteArrayOutputStream.toByteArray();
			// Liberamos y cerramos para ser eficientes //
			byteArrayOutputStream.reset();
			// Este close no va dentro de un try/catch por que BOS es un Stream
			// especial y close no hace nada
			byteArrayOutputStream.close();
		} catch (IOException e1) {
			// Error leyendo el fichero así que no tenemos
			// en memoria el fichero.
			e1.printStackTrace();
		} finally {
			if (bufferedInputStream != null) {
				try {
					bufferedInputStream.close();
				} catch (IOException e) {
					// Error cerrando stream del fichero
					e.printStackTrace();
				}
			}
		}

		return contenidoDelFichero;
	}

	public static String getRealPath(String rutaFicheros, String ruta) {
		String path = rutaFicheros;

		path += System.getProperty(Constantes.SYSTEM_FILE_SEPARATOR) + ruta;

		return path;
	}

	public static Boolean comprobarSiPDFNuevo(String rutaFicheros,
			String modelo, String version) {
		Boolean nuevo = false;

		try {
			File xml = new File(getRealPath(rutaFicheros, "modelos")
					+ System.getProperty(Constantes.SYSTEM_FILE_SEPARATOR)
					+ Constantes.NOMBRE_MODELO + modelo + Constantes.LETRA_V
					+ version + Constantes.EXTENSION_XML);

			File jrxml = new File(getRealPath(rutaFicheros, "documentos")
					+ System.getProperty(Constantes.SYSTEM_FILE_SEPARATOR)
					+ Constantes.PREFIJO_XML_JSPX + modelo + Constantes.LETRA_V
					+ version + Constantes.BARRA_BAJA + (1) + "_v2"
					+ Constantes.EXTENSION_JRXML);

			if (jrxml.lastModified() != 0 && xml.lastModified() != 0) {
				if (jrxml.lastModified() < xml.lastModified()) {
					nuevo = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nuevo;
	}

	public static Boolean comprobarSiWebNuevo(String rutaFicheros,
			String modelo, String version, Boolean esTasa, String deployPath,
			String carpeta, Boolean esAsistente) {
		Boolean nuevo = false;

		try {

			File xml = new File(rutaFicheros
					+ System.getProperty(Constantes.SYSTEM_FILE_SEPARATOR)
					+ "modelos"
					+ System.getProperty(Constantes.SYSTEM_FILE_SEPARATOR)
					+ Constantes.NOMBRE_MODELO + modelo + Constantes.LETRA_V
					+ version + Constantes.XML_PA + Constantes.EXTENSION_XML);

			File jspx = null;
			if (esAsistente) {
				jspx = new File(deployPath
						+ System.getProperty(Constantes.SYSTEM_FILE_SEPARATOR)
						+ carpeta
						+ System.getProperty(Constantes.SYSTEM_FILE_SEPARATOR)
						+ Constantes.PREFIJO_XML_JSPX + modelo
						+ Constantes.LETRA_V + version + 1
						+ (esTasa ? "_tasa" : "") + Constantes.EXTENSION_XHTML);
			} else {
				jspx = new File(deployPath
						+ System.getProperty(Constantes.SYSTEM_FILE_SEPARATOR)
						+ carpeta
						+ System.getProperty(Constantes.SYSTEM_FILE_SEPARATOR)
						+ Constantes.PREFIJO_XML_JSPX + modelo
						+ Constantes.LETRA_V + version
						+ (esTasa ? "_tasa" : "") + Constantes.EXTENSION_XHTML);
			}
			if (jspx.lastModified() != 0 && xml.lastModified() != 0) {
				if (jspx.lastModified() < xml.lastModified()) {
					nuevo = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nuevo;
	}

	/**
	 * @author fcastro
	 * @param Cadena
	 *            - String
	 * @return String Añade tantos ceros a la izquierda como sean necesarios
	 *         hasta alcanzar el tamaño (Tamano)
	 */
	public static String cerosAIzquierda(String stCadena, Integer iTamano) {
		while (stCadena.length() < iTamano)
			stCadena = "0" + stCadena;
		return stCadena;
	}

	public static Class<?> obtenerReturnType(String nombreAtributo,
			Object objetoVO, Boolean transformar) {
		Class<?> clase = null;
		try {
			Method metodo = objetoVO.getClass().getMethod(
					TratamientoDeDatos
							.getMetodoGet(nombreAtributo, transformar));

			clase = metodo.getReturnType();
		} catch (SecurityException e) {
			LOG.error(
					"Error de seguridad al intentar obtener el valor del atributo "
							+ nombreAtributo + " en la clase "
							+ objetoVO.getClass().getName(), e);
		} catch (NoSuchMethodException e) {
			LOG.error("No existe el atributo " + nombreAtributo
					+ " en la clase " + objetoVO.getClass().getName(), e);
		}

		return clase;
	}

	public static Object getValorMetodo(String nombreAtributo, Object objetoVO,
			Boolean intentarTransformacion) {
		Object resultado = null;
		String[] nombres = null;
		if (!TratamientoDeDatos.esNullVacio(nombreAtributo)) {
			if (nombreAtributo.contains(Constantes.PUNTO)) {
				nombres = nombreAtributo.split(Constantes.SEPARACION_METODOS);
			} else {
				nombres = new String[1];
				nombres[0] = nombreAtributo;
			}
			resultado = objetoVO;

			Method metodoGet;
			for (String parte : nombres) {
				if (resultado != null) {
					if (resultado.getClass().isArray()) {
						if (((Object[]) resultado).length > 0) {
							resultado = ((Object[]) resultado)[0];
						} else {
							resultado = null;
						}
					} else if (resultado instanceof List<?>) {
						if (TratamientoDeDatos
								.esListaConElementos((List<?>) resultado)) {
							resultado = ((List<?>) resultado).get(0);
						} else {
							resultado = null;
						}
					}
					try {
						if (resultado != null) {
							metodoGet = resultado.getClass().getMethod(
									TratamientoDeDatos.getMetodoGet(parte,
											false));
							resultado = metodoGet.invoke(resultado);
						}
					} catch (SecurityException e) {
						LOG.error(
								"Error de seguridad al intentar obtener el valor del atributo "
										+ nombreAtributo + " en la clase "
										+ objetoVO.getClass().getName(), e);
					} catch (IllegalArgumentException e) {
						LOG.error(
								"Error al intentar obtener el valor del atributo "
										+ nombreAtributo + " en la clase "
										+ objetoVO.getClass().getName(), e);
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						if (intentarTransformacion) {
							parte = TratamientoDeDatos
									.transformarNombreColumna(parte);
							resultado = TratamientoDeDatos.getValorMetodo(
									parte, resultado);

						} else {
							LOG.error("No existe el atributo " + nombreAtributo
									+ " en la clase "
									+ objetoVO.getClass().getName(), e);
						}
					} catch (IllegalAccessException e) {
						LOG.error(
								"Error de seguridad al intentar obtener el valor del atributo "
										+ nombreAtributo + " en la clase "
										+ objetoVO.getClass().getName(), e);
					} catch (InvocationTargetException e) {
						LOG.error(
								"Error al intentar obtener el valor del atributo "
										+ nombreAtributo + " en la clase "
										+ objetoVO.getClass().getName(), e);
					}
				} else {
					break;
				}
			}

		}
		return resultado;

	}

	public static Object getValorMetodo(String nombreAtributo, Object objetoVO) {
		return TratamientoDeDatos.getValorMetodo(nombreAtributo, objetoVO,
				false);
	}

	/**
	 * Obitene el valor de un parámetro de configuración, para ello lee la ruta
	 * del fichero de configuración en el properties ruta_parametros.
	 * 
	 * @param name
	 *            parámetro que se quiere leer.
	 * @return valor del parámetro.
	 */
	public static String getParameterConfg(String name) {
		LOG.debug("Inicio - getParameterConfg");
		LOG.debug("name: " + name);
		InputStreamReader reader;
		FileInputStream fis;
		String valorParametro = new String();
		String rutaParametros = ResourceBundle.getBundle(
				Constantes.FILE_RUTA_CONF).getString(Constantes.PARAM_RUTA);
		LOG.debug("rutaParametros: " + rutaParametros);
		try {
			File filePa = new File(rutaParametros);
			if (filePa.isFile()) {
				fis = new FileInputStream(filePa);
				reader = new InputStreamReader(fis,
						Charset.forName(Constantes.COD_UTF8));
				PropertyResourceBundle bundle;
				bundle = new PropertyResourceBundle(reader);
				valorParametro = bundle.getString(name);
				LOG.info("valorParametro: " + valorParametro);
			}
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		}
		LOG.debug("Fin - getParameterConfg");
		return valorParametro;
	}

	/**
	 * Metodo que obtiene el valor de un campo del xml contenido en el fichero
	 * pasado por parametro
	 * 
	 * @param fichero
	 * @param campo
	 * @return
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public static String obtenerValorFicheroXML(File fichero,
			String campo) throws ParserConfigurationException, SAXException,
			IOException {
		String valor = new String();
		LOG.debug("Inicio - obtenerValorFicheroXML");

		if (fichero == null || campo == null || "".equals(campo)) {
			LOG.debug("obtenerValorFicheroXML - campos vacios");
			return valor;
		}

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

		DocumentBuilder dBuilder;
		dBuilder = dbFactory.newDocumentBuilder();

		Document doc = dBuilder.parse(fichero);
		doc.getDocumentElement().normalize();

		NodeList nodes = doc.getElementsByTagName(campo);

		Node node = nodes.item(0);
		valor = node.getFirstChild().getNodeValue();

		LOG.debug("Fin - obtenerValorFicheroXML");

		return valor;
	}
	
	// ///////////////////////////////////////////////////////////
		// /// Funciones para poner un número a un formato correcto //
		// ///////////////////////////////////////////////////////////
		public static String formatearNumero(Double valor) {

			if (valor == null || valor.doubleValue() == 0.0) {
				return "";
			}
			// Limitamos el valor a dos decimales
			valor = new Double(Math.rint(valor.doubleValue() * 100) / 100);
			// Ponemos como formato estándar el alemán, que es igual al español
			// Para los miles el "." y para los decimales la ","
			NumberFormat nf2 = NumberFormat.getInstance(Locale.GERMAN);
			// Creamos el patrón de miles y decimales
			DecimalFormat df = new DecimalFormat("###,##0.00");
			// Asignamos el formato al valor
			String prueba = df.format(valor);
			return prueba;
		}

	
}