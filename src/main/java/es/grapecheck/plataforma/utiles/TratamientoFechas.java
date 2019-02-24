package es.grapecheck.plataforma.utiles;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Clase com�n para tratar las fechas y ponerlas en un formato correcto.
 * 
 * @author Pedro Garc�a. Fecha: 19/05/2010
 */
public class TratamientoFechas {

	private static final Logger LOG = LoggerFactory
			.getLogger(TratamientoFechas.class);
	public static final String formatoEspanol = "dd/MM/yyyy";
	public static final String formatoBBDD = "yyyy-MM-dd";

	/**
	 * @author pgarcia
	 * @return date con la fecha actual
	 */
	public static Date fechaActual() {
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(new Date());
		calendario.set(Calendar.MILLISECOND, 0);
		calendario.set(Calendar.SECOND, 0);
		calendario.set(Calendar.MINUTE, 0);
		calendario.set(Calendar.HOUR, 0);
		return calendario.getTime();
	}

	/**
	 * @author pgarcia
	 * @return cadena con formato aaaa-MM-dd con la fecha actual
	 */
	public static String fechaActualConFormato() {
		SimpleDateFormat formato = new SimpleDateFormat(formatoBBDD);
		String cadenaFecha = formato.format(new Date());
		return cadenaFecha;
	}

	public static String fechaHoyBarras() {
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		return formato.format(new Date());
	}

	public static String fechaConFormato(Date date, String stringFormato) {
		SimpleDateFormat formato = new SimpleDateFormat(stringFormato);
		return formato.format(date);
	}

	/**
	 * @author pgarcia
	 * @param fecha
	 * @return convierte a date la fecha tipo cadena que entra por parametro
	 * @throws ParseException
	 */
	public static String convertirFechaActualConFormato(String fecha) {
		fecha = FormateaFecha(fecha);
		SimpleDateFormat formatoDelTexto = new SimpleDateFormat(formatoBBDD);
		Date date = null;
		try {
			date = formatoDelTexto.parse(fecha);
		} catch (ParseException ex) {
			LOG.error("ERROR AL CONVERTIR UNA FECHA: TratamientoFechas.convertirFechaActualConFormato(String fecha)");
		}
		return formatoDelTexto.format(date).toString();
	}

	public static String FormateaFecha(String vFecha) {
		String vDia, vMes, vAno;
		String fechaFinal;
		if (vFecha.contains("/")) {
			StringTokenizer tokens = new StringTokenizer(vFecha, "/");
			vDia = tokens.nextToken();
			vMes = tokens.nextToken();
			vAno = tokens.nextToken();
			fechaFinal = vAno + "-" + vMes + "-" + vDia;
		} else {
			fechaFinal = vFecha;
		}
		return fechaFinal;
	}

	/**
	 * (Funci�n pasada de PATTEX) Formatea una fecha sin la hora.
	 */
	public static Date formateaFechaSinHora(Date pfechaInicio) {
		Integer ejercicio = new Integer(TratamientoFechas.getAnio(pfechaInicio));
		Integer mes = new Integer(TratamientoFechas.getMes(pfechaInicio));
		Integer dia = new Integer(TratamientoFechas.getDia(pfechaInicio));
		Calendar cal = new GregorianCalendar(ejercicio, mes - 1, dia);
		return cal.getTime();
	}

	/**
	 * (Funci�n pasada de PATTEX) Comprueba que una fecha sea mayor que
	 * fechaInicio y menor que fechaFin
	 */
	public static Boolean fechaDentroPeriodo(Date fechaComparada,
			Date fechaInicio, Date fechaFin) {
		Boolean retorno = false;
		// si fecha inicio es menor o igual que fecchaComparada(presentacion)
		if ((fechaInicio.compareTo(fechaComparada) <= 0)
		// y fechaFin es mayor o iggual que fechaComparada(presentacion)
				&& (fechaFin.compareTo(fechaComparada) >= 0)) {
			retorno = true;
		}
		return retorno;
	}

	/**
	 * Comprueba que una fecha sea mayor que fechaInicio y menor que fechaFin.
	 * Comprueba que la fecha fin puede ser nula. En ese casi si la fecha inicio
	 * es menor, esta dentro del rango.
	 * 
	 * @param fechaComparada
	 *            Date
	 * @param fechaInicio
	 *            Date
	 * @param fechaFin
	 *            Date
	 * @return Boolean
	 */
	public static Boolean fechaDentroPeriodoVencimiento(Date fechaComparada,
			Date fechaInicio, Date fechaFin) {
		Boolean retorno = false;
		// si fecha inicio es menor o igual que fecchaComparada(presentacion)
		if ((fechaInicio.compareTo(fechaComparada) <= 0)) {
			// y fechaFin es mayor o iggual que fechaComparada(presentacion)
			if (TratamientoDeDatos.esNullCeroVacio(fechaFin)
					|| (fechaFin.compareTo(fechaComparada) >= 0)) {
				retorno = true;
			}
		}
		return retorno;
	}

	public static Date objectToFecha(Object cadena) throws ParseException {
		Date fecha = null;
		if (cadena != null) {
			String dateFormat = cadena.toString();
			SimpleDateFormat sdf = new SimpleDateFormat(formatoBBDD);
			fecha = sdf.parse(dateFormat);
		}
		return fecha;
	}

	public static Date objectToFechaFormEsp(Object cadena)
			throws ParseException {
		Date fecha = null;
		if (!TratamientoDeDatos.esNullCeroVacio(cadena)) {
			String dateFormat = null;
			if (cadena.getClass().equals(Date.class)) {
				dateFormat = ((Date) cadena).toString();
			} else if (cadena.getClass().equals(Timestamp.class)) {
				dateFormat = ((Timestamp) cadena).toString();
			} else {
				dateFormat = cadena.toString();
			}
			String formato = getFormatoFecha(dateFormat);
			SimpleDateFormat sdf = new SimpleDateFormat(formato);
			fecha = sdf.parse(dateFormat);
		}
		return fecha;
	}

	public static String getFormatoFecha(String dateFormat) {
		String formato = null;
		if (dateFormat.contains(":") && dateFormat.contains("-")
				&& dateFormat.indexOf("-") == 4) {
			// yyyy-MM-dd HH:mm:ss
			formato = Constantes.FORMATO_FECHA_YYYY_MM_DD_HH_MM_SS_GUIONES;
		} else if (dateFormat.contains(":") && dateFormat.contains("-")
				&& dateFormat.indexOf("-") == 2) {
			// dd-MM-yyyy HH:mm:ss
			formato = Constantes.FORMATO_FECHA_DD_MM_YYYY_HH_MM_SS_GUIONES;
		} else if (dateFormat.contains("-") && dateFormat.indexOf("-") == 4) {
			formato = Constantes.FORMATO_FECHA_YYYY_MM_DD_GUIONES;
		} else if (dateFormat.contains("-") && dateFormat.indexOf("-") == 2) {
			formato = Constantes.FORMATO_FECHA_DD_MM_YYYY_GUIONES;
		} else if (dateFormat.contains("/") && dateFormat.contains(":")) {
			formato = Constantes.FORMATO_FECHA_DD_MM_YYYY_HH_MM_SS;
		} else if (dateFormat.contains("/")) {
			formato = Constantes.FORMATO_FECHA_DD_MM_YYYY;
		}
		return formato;
	}

	public static Date obtenerFechaCampoPantalla(Object objeto)
			throws ParseException {
		Date fecha = null;
		if (objeto != null && objeto.getClass().equals(Date.class)) {
			fecha = objectToFechaFormEsp(((Date) objeto).toString());
		}
		return fecha;
	}

	public static String dateToString(Date dateFecha) {
		String fechaString = "";
		if (!TratamientoDeDatos.esNullVacio(dateFecha)) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			fechaString = sdf.format(dateFecha);
		}
		return fechaString;
	}

	/**
	 * Validar un campo fecha.
	 * 
	 * @author rquiros
	 * @param fechax
	 *            - String fecha
	 * @return boolean
	 */
	public static boolean esFechaValida(String fechax) {
		Boolean esValido = Boolean.FALSE;
		if (esFechaValida(fechax, Constantes.FORMATO_FECHA_DD_MM_YYYY)
				|| esFechaValida(fechax,
						Constantes.FORMATO_FECHA_DD_MM_YYYY_GUIONES)) {
			esValido = Boolean.TRUE;
		}
		return esValido;
	}

	public static boolean esFechaValida(Date fechax) {
		boolean correcta = true;
		if (!TratamientoDeDatos.esNullCeroVacio(fechax))
			correcta = esFechaValida(dateToString(fechax),
					Constantes.FORMATO_FECHA_DD_MM_YYYY);
		return correcta;
	}

	/**
	 * Validar un campo fecha.
	 * 
	 * @author rquiros
	 * @param fechax
	 *            - String fecha
	 * @param formato
	 * @return boolean
	 */
	public static boolean esFechaValida(String fechax, String formato) {
		boolean correcta = true;
		try {
			if (!TratamientoDeDatos.esNullCeroVacio(fechax)) {
				SimpleDateFormat formatoFecha = new SimpleDateFormat(formato,
						Locale.getDefault());
				formatoFecha.setLenient(false);
				formatoFecha.parse(fechax);
			}
		} catch (ParseException e) {
			correcta = false;
		}
		return correcta;
	}

	/**
	 * Recibe dos fechas y calcula la diferencia entre f1 y f2, devolviendo la
	 * el resultado. en dias.
	 * 
	 * @param f1
	 *            fecha inicial.
	 * @param f2
	 *            fecha final.
	 * @return long numero de dias de difenrencia.
	 */
	public static long restarFechas(Date f1, Date f2) {
		long resultado = Constantes.NUMERO_CERO;
		long fecha1ms = f1.getTime();
		long fecha2ms = f2.getTime();
		long diferencia = 0;
		if (fecha2ms >= fecha1ms) {
			diferencia = fecha2ms - fecha1ms;
		} else if (fecha2ms < fecha1ms) {
			diferencia = fecha1ms - fecha2ms;
		}
		Long dias = Math.round((double) diferencia / (1000 * 60 * 60 * 24));
		resultado = dias.longValue();
		return resultado;
	}

	/**
	 * ESTE METODO SE UTILIZA EN VARIAS FUNCIONES DE VALIDACI�N (NO BORRAR)
	 * Recibe dos fechas y calcula la diferencia entre f1 y f2, devolviendo la
	 * el resultado. en dias.
	 * 
	 * @param f1
	 *            fecha inicial.
	 * @param f2
	 *            fecha final.
	 * @return long numero de dias de difenrencia.
	 */
	public static long restarFechasValidacion(Date f1, Date f2) {
		long resultado = Constantes.NUMERO_CERO;
		long fecha1ms = f1.getTime();
		long fecha2ms = f2.getTime();
		long diferencia = fecha2ms - fecha1ms;
		Long dias = Math.round((double) diferencia / (1000 * 60 * 60 * 24));
		resultado = dias.longValue();
		return resultado;
	}

	/**
	 * ESTE METODO SE UTILIZA EN UNA FUNCI�N DE VALIDACI�N (NO BORRAR) Recibe
	 * dos fechas y comprueba cual de la dos es mayor. -1 si f1 es menor, 1 si
	 * f1 es mayor , 0 si son iguales. en dias.
	 * 
	 * @param f1
	 *            fecha inicial.
	 * @param f2
	 *            fecha final.
	 * @return long numero de dias de difenrencia.
	 */
	public static int restarDates(Date f1, Date f2) {
		int result = 0;
		if (f1 != null && f2 != null && esFechaValida(f1) && esFechaValida(f2)) {
			result = f1.compareTo(f2);
		}
		return result;
	}

	/**
	 * Resta dos fechas y devuelve un entero con los dias de diferencia de f2-f1
	 * 
	 * @param f1
	 *            fecha menor. Mas antigua
	 * @param f2
	 *            fecha mayor o mas actual
	 * @return Integer
	 */
	public static Integer diferenciaDiasFechas(Date f1, Date f2) {
		long resultado = Constantes.NUMERO_CERO;
		resultado = restarFechas(f1, f2); // resultado en dias.
		return TratamientoDeDatos.iNoNullEsCero(resultado);
	}

	/**
	 * Resta las fechas y devuelve el resultado en meses.
	 * 
	 * @param f1
	 *            Fecha m�s alta.
	 * @param f2
	 *            Fecha m�s baja.
	 * @return La diferencia de las fechas en meses
	 */
	public static Double diferenciaMesesFechas(Date f1, Date f2) {
		Double resultado = new Double(0);
		Integer dias = diferenciaDiasFechas(f1, f2);
		if (!TratamientoDeDatos.esNullCeroVacio(dias)) {
			resultado = new Double(new Double(dias) / new Double(30));
		}
		return resultado;
	}

	/**
	 * Suma a la fecha de entrada los anios que queramos.
	 * 
	 * @author mgraham
	 */
	public static Date sumarAniosFecha(Date f1, int anio) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(f1);
		calendar.add(Calendar.YEAR, anio);
		Date fechaResult = calendar.getTime();
		return fechaResult;
	}

	/**
	 * Suma a la fecha de entrada los meses que queramos.
	 * 
	 * @author lmpaz
	 */
	public static Date sumarMesesFecha(Date f1, int meses) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(f1);
		calendar.add(Calendar.MONTH, meses);
		Date fechaResult = calendar.getTime();
		return fechaResult;
	}

	/**
	 * Suma a la fecha de entrada los meses que queramos.
	 * 
	 * @author lmpaz
	 */
	public static Date sumarDiasFecha(Date f1, int dias) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(f1);
		calendar.add(Calendar.DAY_OF_YEAR, dias);
		Date fechaResult = calendar.getTime();
		return fechaResult;
	}

	public static Date restasDiasFecha(Date f1, int dias) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(f1);
		long tiempoActual = calendar.getTimeInMillis();
		long totalDias = dias * 24 * 60 * 60 * 1000L;
		Date fechaAnterior = new Date(tiempoActual - totalDias);
		return fechaAnterior;
	}

	/**
	 * Devuelve 1 si la fecha entra dentro del rango. Devuelve 0 en caso
	 * contrario. Metodo necesario para ejecutar las validaciones de los
	 * documentos.
	 * 
	 * @param fecIni
	 *            String
	 * @param fecFin
	 *            String
	 * @param fecha
	 *            String
	 * @return Integer
	 */
	public static Integer comprobarFechaEnRango(String fecIni, String fecFin,
			String fecha) throws ParseException {
		Integer salida = 0;
		Date fechaIni = null;
		Date fechaFin = null;
		Date fechaRango = null;
		if (!TratamientoDeDatos.esNullCeroVacio(fecFin)) {
			fechaIni = objectToFechaFormEsp(fecIni);
			fechaFin = objectToFechaFormEsp(fecFin);
			fechaRango = objectToFechaFormEsp(fecha);
			if (fechaRango.after(fechaIni) && fechaRango.before(fechaFin)) {
				salida = 1;
			}
		} else {
			fechaIni = objectToFechaFormEsp(fecIni);
			fechaRango = objectToFechaFormEsp(fecha);
			if (fechaRango.after(fechaIni)) {
				salida = 1;
			}
		}
		return salida;
	}

	/**
	 * mgraham. NO BORARAR. NO CAMBIAR. Resta las fechas y devuelve el resultado
	 * en anios.
	 * 
	 * @param fecha
	 *            mas antigua (menor) f1.
	 * @param fecha
	 *            mas actual (mayor) f2.
	 * @return numero de a�os de diferencia.
	 */
	public static int obtenerAniosEntreFechas(Date f1, Date f2) {
		int resultado = Constantes.NUMERO_CERO;
		// Fecha Devengo
		Calendar fec1 = Calendar.getInstance();
		fec1.setTime(f1);
		// Fecha matriculacion u otra
		Calendar fec2 = Calendar.getInstance();
		fec2.setTime(f2);
		Integer years = fec1.get(Calendar.YEAR) - fec2.get(Calendar.YEAR);
		String strYears = years.toString();
		strYears = strYears.replace("-", "");
		years = TratamientoDeDatos.iNoNullEsCero(strYears);
		if (fec1.get(Calendar.MONTH) == fec2.get(Calendar.MONTH)) { // same
																	// month
			if (fec1.get(Calendar.DAY_OF_MONTH) >= fec2
					.get(Calendar.DAY_OF_MONTH)) {
				resultado = years;
			} else {
				resultado = years - 1;
			}
		} else {
			if (fec1.get(Calendar.MONTH) < fec2.get(Calendar.MONTH)) {
				resultado = years - 1;
			} else {
				if (fec1.get(Calendar.MONTH) > fec2.get(Calendar.MONTH)) {
					resultado = years;
				}
			}
		}
		return resultado;
	}

	/**
	 * Lmpaz 25/05/2011 Resta las fechas y devuelve el resultado en anios.
	 * 
	 * @param fecha
	 *            mas antigua (menor) f1.
	 * @param fecha
	 *            mas actual (mayor) f2.
	 * @return numero de a�os de diferencia.
	 */
	public static int diferenciaEnAniosFechas(Date f1, Date f2) {
		int resultado = Constantes.NUMERO_CERO;
		Integer dias = diferenciaDiasFechas(f1, f2);
		if (!TratamientoDeDatos.esNullCeroVacio(dias)) {
			resultado = (int) Math.floor(dias / 365);
		}
		return resultado;
	}

	public static int diferenciaAniosFechas(Date f1, Date f2) {
		int result = 0;
		double dif = 0.0;
		String anios = null;
		if (f1 != null && f2 != null && esFechaValida(f1) && esFechaValida(f2)) {
			long fecha1ms = f1.getTime();
			long fecha2ms = f2.getTime();
			long diferencia = 0;
			if (fecha2ms >= fecha1ms) {
				diferencia = fecha2ms - fecha1ms;
			} else if (fecha2ms < fecha1ms) {
				diferencia = fecha1ms - fecha2ms;
			}
			result = f1.compareTo(f2);
			if (result != 0) {
				Double dias = Math.floor(diferencia / (1000 * 60 * 60 * 24));
				dif = dias / 365;
				anios = dif + Constantes.CADENA_VACIA;
				anios = anios.replace(Constantes.SIMBOLO_NEGATIVO,
						Constantes.CADENA_VACIA);
				if (anios.contains(Constantes.PUNTO)) {
					anios = anios.substring(0, anios.indexOf(Constantes.PUNTO));
				}
				result = Integer.valueOf(anios);
			}
		}
		return result;
	}

	/**
	 * Diferencia anios cumplidos entre dos fechas. Para calcular los a�os de
	 * uso de un veh�culo. Copiado de PATTEX
	 */
	public static Integer diferenciaEnAnios(Date fechaMayor, Date fechaMenor) {
		Calendar cal = Calendar.getInstance(); // current date
		cal.setTime(fechaMayor);
		Integer anioMayor = cal.get(Calendar.YEAR);
		Integer mesMayor = cal.get(Calendar.MONTH) + 1;
		Integer diaMayor = cal.get(Calendar.DAY_OF_MONTH);
		cal.setTime(fechaMenor); // now born date
		Integer years = anioMayor - cal.get(Calendar.YEAR);
		Integer mesMenor = cal.get(Calendar.MONTH) + 1;
		if (mesMenor.equals(mesMayor)) { // same month
			if (cal.get(Calendar.DAY_OF_MONTH) <= diaMayor)
				return years;
			else
				return (years - 1);
		} else {
			if (mesMenor < mesMayor)
				return (years);
			else
				return (years - 1);
		}
	}

	public static Date getEndOfDay(Date fecha) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);
		// hora 23
		cal.set(Calendar.HOUR_OF_DAY, 23);
		// minuto 59
		cal.set(Calendar.MINUTE, 59);
		// segundo 59
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		return cal.getTime();
	}

	/**
	 * Entrada: fecha inicio y fecha fin. Salida: true si entre fecha de inicio
	 * y fecha fin hay mas de un mes de diferencia.
	 */
	public static Boolean mesDiferencia(Date fechaInicio, Date fechaFin) {
		Boolean retorno = Boolean.FALSE;
		if ((restarFechas(fechaFin, fechaInicio)) > 30) {
			// La diferencia es mayor de un mes.
			retorno = true;
		}
		return retorno;
	}

	public static final Date AddMes(Date Fecha, Integer cantidad) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(Fecha);
		int campoMes = Calendar.MONTH;
		cal.add(campoMes, cantidad);
		return cal.getTime();
	}

	public static Long FechaMsADias(Long vFecha) {
		return vFecha / (1000 * 60 * 60 * 24);
	}

	public static final Date AddAnio(Date Fecha, Integer cantidad) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(Fecha);
		int campoAnio = Calendar.YEAR;
		cal.add(campoAnio, cantidad);
		return cal.getTime();
	}

	public static final Date AddDia(Date Fecha, Integer cantidad) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(Fecha);
		int campoDia = Calendar.DAY_OF_MONTH;
		cal.add(campoDia, cantidad);
		return cal.getTime();
	}

	public static String getAnio(Date fecha) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(fecha);
		return Integer.toString(calendar.get(Calendar.YEAR));
	}

	/**
	 * Obtener el anio de una fecha.
	 * 
	 * @param fecha
	 *            - Date
	 * @return Integer
	 */
	public static Integer getAnioInteger(Date fecha) {
		Integer anioInteger = Integer.valueOf(0);
		try {
			if (fecha != null) {
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(fecha);
				anioInteger = calendar.get(Calendar.YEAR);
			}
		} catch (Exception e) {
			anioInteger = Integer.valueOf(0);
		}
		return anioInteger;
	}

	public static String getMes(Date fecha) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(fecha);
		return Integer.toString(calendar.get(Calendar.MONTH) + 1);
	}

	public static String getDia(Date fecha) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(fecha);
		return Integer.toString(calendar.get(Calendar.DATE));
	}

	public static String dateToString(Date dateFecha, String formato) {
		SimpleDateFormat sdf = new SimpleDateFormat(formato);
		return sdf.format(dateFecha);
	}

	public static Boolean compararFechas(Date fecha1, Date fecha2) {
		return fecha1.before(fecha2);
	}

	/**
	 * Comprueba si fecha2 >= fecha1. Antes de hacer la comparaci�n formateamos
	 * ambas fechas para quitarles la hora
	 * 
	 * @param fecha1
	 *            - Date
	 * @param fecha2
	 *            - Date
	 * @return Boolean
	 */
	public static Boolean fechaMayorOIgual(Date estaFechaMayor, Date queEsta) {
		boolean mayorOIgual = false;
		// solo comprobar si son fechas validas
		if (esFechaValida(estaFechaMayor) && esFechaValida(queEsta)) {
			estaFechaMayor = formateaFechaSinHora(estaFechaMayor);
			queEsta = formateaFechaSinHora(queEsta);
			if (estaFechaMayor.after(queEsta) || estaFechaMayor.equals(queEsta)) {
				mayorOIgual = true;
			}
		}
		return mayorOIgual;
	}

	/**
	 * Comprueba si fecha2 >= fecha1. Antes de hacer la comparaci�n formateamos
	 * ambas fechas para quitarles la hora
	 * 
	 * @param fecha1
	 *            - Date
	 * @param fecha2
	 *            - Date
	 * @return Boolean
	 */
	public static Boolean fechaMayor(Date estaFechaMayor, Date queEsta) {
		boolean mayor = false;
		// solo comprobar si son fechas validas
		if (esFechaValida(estaFechaMayor) && esFechaValida(queEsta)) {
			estaFechaMayor = formateaFechaSinHora(estaFechaMayor);
			queEsta = formateaFechaSinHora(queEsta);
			if (estaFechaMayor.after(queEsta)) {
				mayor = true;
			}
		}
		return mayor;
	}
	
	 
    public static final String convertirDateToText(Date aDate) {
        if (aDate==null) return "";
        String cadena = "";
        cadena += obtenerDiaMes( aDate );
        cadena += " de "+ obtenerNombreMes ( obtenerMes( aDate ));
        cadena += " de "+ obtenerAnio( aDate );        
        return cadena; 
    }

    public static final String obtenerDiaMes(Date aDate) {
        if (aDate==null) return "";
        Calendar cal = new GregorianCalendar();
        cal.setTime(aDate);
        int campoDia = Calendar.DAY_OF_MONTH;
        return ""+cal.get(campoDia); 
    }
    
    
    public static final String obtenerNombreMes(String mes) {
        if (mes==null) return "";
        String cadena = "";
        int iMes = new Integer(mes).intValue();
        cadena=Constantes.MES[iMes+1];
        return cadena; 
    }
    
    
    public static final String obtenerMes(Date aDate) {
        if (aDate==null) return "";
        Calendar cal = new GregorianCalendar();
        cal.setTime(aDate);
        int campoMes = Calendar.MONTH;
        return ""+cal.get(campoMes); 
    }
    
	public static Date stringToDate(String dateFecha, String formato) {
		SimpleDateFormat sdf = new SimpleDateFormat(formato);
		Date fecha = null;
		if (!TratamientoDeDatos.esNullCeroVacio(dateFecha)
				&& !TratamientoDeDatos.esNullCeroVacio(formato)) {
			try {
				fecha = sdf.parse(dateFecha);
			} catch (ParseException ex) {
				LOG.error("ERROR AL PARSEAR UNA FECHA: TratamientoFechas.stringToDate(String dateFecha, String formato)");
			}
		}
		return fecha;
	}
	
	
    public static final String obtenerAnio(Date aDate) {
        if (aDate==null) return "";
        Calendar cal = new GregorianCalendar();
        cal.setTime(aDate);
        int campoAnio = Calendar.YEAR;
        return ""+cal.get(campoAnio); 
    }

	/**
	 * Funcion que calcula la fecha de prescripcion para las Transmisiones
	 * Patrimoniales
	 * 
	 * @return
	 */
	public static Boolean esPrescrito(Date fechaLimite) {
		Date fechaPrescrito;
		Boolean esPrescrito = Boolean.FALSE;
		fechaPrescrito = AddMes(fechaLimite, 48);
		if (restarFechas(fechaPrescrito, fechaActual()) > 0) {
			esPrescrito = true;
		}
		return esPrescrito;
	}

	public static String fechaFormatoREDES(Date fechaDevengo) {
		Calendar c = new GregorianCalendar();
		c.setTime(fechaDevengo);
		String dia = TratamientoDeDatos.padZeros(2,
				TratamientoDeDatos.sNoNull((c.get(Calendar.DATE))));
		String mes = TratamientoDeDatos.padZeros(2,
				TratamientoDeDatos.sNoNull((c.get(Calendar.MONTH) + 1)));
		String annio = TratamientoDeDatos.sNoNull((c.get(Calendar.YEAR)));
		return annio + mes + dia;
	}

	public static Date objectToFecha(Object cadena, String formato)
			throws ParseException {
		if (cadena == null || cadena.equals(""))
			return null;
		String dateFormat = cadena.toString();
		SimpleDateFormat sdf = new SimpleDateFormat(formato);
		Date date = sdf.parse(dateFormat);
		return date;
	}

	public static Date stringToDate(String dateFecha) {
		Date fecha = null;
		if (!TratamientoDeDatos.esNullCeroVacio(dateFecha)) {
			SimpleDateFormat sdf = new SimpleDateFormat(formatoBBDD);
			try {
				fecha = sdf.parse(dateFecha);
			} catch (ParseException ex) {
				LOG.error("ERROR AL PARSEAR UNA FECHA: TratamientoFechas.stringToDate(String dateFecha)");
				try {
					SimpleDateFormat sdfEspanol = new SimpleDateFormat(
							formatoEspanol);
					fecha = sdfEspanol.parse(dateFecha);
				} catch (ParseException e) {
					LOG.error("ERROR AL PARSEAR UNA FECHA: TratamientoFechas.stringToDate(String dateFecha)");
				}
			}
		}
		return fecha;
	}

	/**
	 * Obtiene los dias de un mes determinado por el parametro de entrada
	 * 
	 * @param iMes
	 *            (0-11)
	 * @return int dias del mes
	 */
	public static Integer diasXMes(Integer iMes, Integer iAnio) {
		Integer iDiasMes = null;
		Calendar cal = new GregorianCalendar(iAnio, iMes, 1);
		iDiasMes = new Integer(cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		return iDiasMes;
	}

	public static String getFechaFormatoLargo(String fecha)
			throws ParseException {
		String cadena = "";
		if (!TratamientoDeDatos.esNullCeroVacio(fecha)) {
			cadena = getFechaFormatoLargo(objectToFechaFormEsp(fecha));
		}
		return cadena;
	}

	public static String getFechaFormatoLargo(Date fecha) {
		String cadena = "";
		if (!TratamientoDeDatos.esNullCeroVacio(fecha)) {
			Calendar calendario = Calendar.getInstance();
			calendario.setTime(fecha);
			cadena = getFechaFormatoLargo(calendario);
		}
		return cadena;
	}

	public static String getFechaFormatoLargo(Calendar fecha) {
		Locale userLocale = new Locale("ES");
		String ano = TratamientoDeDatos.sNoNull(fecha.get(Calendar.YEAR));
		String mesLetra = fecha.getDisplayName(Calendar.MONTH, Calendar.LONG,
				userLocale);
		String dia = TratamientoDeDatos.sNoNull(fecha
				.get(Calendar.DAY_OF_MONTH));
		return dia + " de " + mesLetra + " de " + ano;
	}

	/**
	 * Comparar 2 fechas sin tener en cuenta la hora.
	 * 
	 * fecha1.compareTo(fecha2)
	 * 
	 * @param fecha1
	 *            - Date
	 * @param fecha2
	 *            - Date
	 * @return Integer - null si error int - resultado del
	 *         fecha1.compareTo(fecha2) si ok
	 */
	public static Integer compararFechasSinHora(Date fecha1, Date fecha2) {
		Integer result = null;

		if (!TratamientoDeDatos.esNullVacio(fecha1)
				&& !TratamientoDeDatos.esNullVacio(fecha2)) {
			try {
				fecha1 = formateaFechaSinHora(fecha1);
				fecha2 = formateaFechaSinHora(fecha2);
				// comparar sin tener en cuenta la hora
				result = fecha1.compareTo(fecha2);
			} catch (Exception e) {
				LOG.error("ERROR en compararFechasSinHora" + e.getMessage());
			}
		}
		return result;
	}

	/**
	 * Obtiene un timestamp a partir de una fecha
	 * 
	 * @param fecha
	 * @return
	 */
	public static String getTimestamp(Date fecha) {
		String stTimestamp;
		stTimestamp = TratamientoDeDatos.cerosAIzquierda(
				TratamientoDeDatos.sNoNull(getAnio(fecha)), 4);
		stTimestamp += TratamientoDeDatos.cerosAIzquierda(
				TratamientoDeDatos.sNoNull(getMes(fecha)), 2);
		stTimestamp += TratamientoDeDatos.cerosAIzquierda(
				TratamientoDeDatos.sNoNull(getDia(fecha)), 2);
		stTimestamp += TratamientoDeDatos.cerosAIzquierda(
				TratamientoDeDatos.sNoNull(getHora(fecha)), 2);
		stTimestamp += TratamientoDeDatos.cerosAIzquierda(
				TratamientoDeDatos.sNoNull(getMinutos(fecha)), 2);
		stTimestamp += TratamientoDeDatos.cerosAIzquierda(
				TratamientoDeDatos.sNoNull(getSegundos(fecha)), 2);

		return stTimestamp;

	}

	/**
	 * Devuelve las horas de un Date
	 * 
	 * @param fecha
	 * @return
	 */
	public static String getHora(Date fecha) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(fecha);
		String stHora = Integer.toString(calendar.get(Calendar.HOUR));
		return stHora;
	}

	/**
	 * Devuelve los minutos de un Date
	 * 
	 * @param fecha
	 * @return
	 */
	public static String getMinutos(Date fecha) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(fecha);
		String stMin = Integer.toString(calendar.get(Calendar.MINUTE));
		return stMin;
	}

	/**
	 * Devuelve los segundos de un Date
	 * 
	 * @param fecha
	 * @return
	 */
	public static String getSegundos(Date fecha) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(fecha);
		String stSeg = Integer.toString(calendar.get(Calendar.SECOND));
		return stSeg;
	}

}