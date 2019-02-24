package es.grapecheck.plataforma.utiles;

import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.util.HSSFColor;


public class UtilidadesXLS {
	public UtilidadesXLS() {
		super();
	}
	
	public static HSSFFont  fuenteGenerica ;{
	fuenteGenerica.setFontHeightInPoints((short)18);
	fuenteGenerica.setFontName("Times New Roman");
	fuenteGenerica.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	fuenteGenerica.setUnderline((byte) 3);

	};
	
	public static HSSFCellStyle estiloTotal; {
	estiloTotal.setBorderLeft(HSSFCellStyle.BORDER_DOUBLE);
	estiloTotal.setBorderBottom(HSSFCellStyle.BORDER_DOUBLE);
	estiloTotal.setBorderRight(HSSFCellStyle.BORDER_DOUBLE);
	estiloTotal.setBorderTop(HSSFCellStyle.BORDER_DOUBLE);
	estiloTotal.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	estiloTotal.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	estiloTotal.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	estiloTotal.setFillForegroundColor(HSSFColor.CORAL.index);
	};
	
	
	public static HSSFSheet pintarHoja(int pagina, HSSFSheet hoja, String nombreProcedimiento, String codigo, String codigoNuevo, List<?> lista, HSSFCellStyle estiloCabecera, HSSFCellStyle estiloSubCabecera1, HSSFCellStyle estiloSubCabecera2, HSSFCellStyle estiloGeneral, HSSFCellStyle estiloGeneral2){
		
		switch(pagina){
		case 1:
			hoja = pintarHojaPagado(hoja,lista, estiloCabecera, estiloSubCabecera1, estiloSubCabecera2, estiloGeneral, estiloGeneral2);
			break;
		
	
		}
		return hoja;
	}
	
	
	public static HSSFSheet pintarHojaPagado(HSSFSheet hoja, List<?> lista, HSSFCellStyle estiloCabecera, HSSFCellStyle estiloSubCabecera1, HSSFCellStyle estiloSubCabecera2, HSSFCellStyle estiloGeneral, HSSFCellStyle estiloGeneral2){
		// CABECERA
		
		hoja = celdaCabecera(hoja, "EXPEDIENTES PAGADOS EN 2014 " , estiloCabecera, 1, 1, 1, 6);
		hoja = celdaCabecera(hoja, "Ayudas 2013 " , estiloCabecera, 3, 1, 3, 6);
		hoja= celdaCabecera(hoja, "EXPTE", estiloSubCabecera1, 5, 1, 5, 1);
		hoja= celdaCabecera(hoja, "NIF", estiloSubCabecera1, 5 ,2,5,2);
		hoja= celdaCabecera(hoja, "ENTIDAD", estiloSubCabecera1, 5,3,5,3);
		hoja= celdaCabecera(hoja, "IMPORTE", estiloSubCabecera1, 5,4,5,4);
		hoja= celdaCabecera(hoja, "F.CONTABLE", estiloSubCabecera1, 5,5,5,5);
		hoja= celdaCabecera(hoja, "F.PAGO", estiloSubCabecera1, 5,6,5,6);
		// FIN CABECERA
				//EXPTE	N.I.F.	ENTIDAD	IMPORTE	F. CONTABLE	F. PAGO
		// CUERPO
		int contador = 6;
		double total=0;
		int i=0;
		for(i = 0; i<lista.size();i++){
			Object[] linea = (Object[]) lista.get(i);
			
			hoja = celdaCabecera(hoja, (String)linea[0], estiloGeneral2, contador+i, 1, contador+i, 1);
			hoja = celdaCabecera(hoja, (String)linea[1], estiloGeneral, contador+i, 2, contador+i, 2);
			hoja = celdaCabecera(hoja, (String)linea[3], estiloSubCabecera2, contador+i, 3, contador+i, 3);
			hoja = celdaCabecera(hoja, 	TratamientoDeDatos.formatearNumero(((java.lang.Double) linea[4])), estiloSubCabecera2, contador+i, 4, contador+i, 4);
			hoja = celdaCabecera(hoja, ((Date)linea[5]).toString(), estiloSubCabecera2, contador+i, 5, contador+i, 5);
			hoja = celdaCabecera(hoja, ((Date)linea[6]).toString(), estiloSubCabecera2, contador+i, 6, contador+i, 6);
			total+=((java.lang.Double) linea[4]);
		}
		
		hoja = celdaCabecera(hoja, 	"Total ", estiloSubCabecera2, contador+i+2, 4, contador+i+2, 4);
		hoja = celdaCabecera(hoja, 	TratamientoDeDatos.formatearNumero(total), estiloSubCabecera2, contador+i+2, 4, contador+i+2, 4);
	
		// FIN CUERPO
		return hoja;
	}
	
	
	
	public static HSSFSheet celdaCabecera(HSSFSheet hoja, String contenido, HSSFCellStyle estiloCabecera, int filaInicio, int celdaInicio){
		HSSFRow fila = hoja.getRow(filaInicio);
		if(TratamientoDeDatos.esNullVacio(fila)){
		       fila = hoja.createRow(filaInicio);
		}
		fila = celdaCabecera(fila, celdaInicio, contenido, estiloCabecera);
		return hoja;
	}
	public static HSSFSheet celdaCabecera(HSSFSheet hoja, String contenido, HSSFCellStyle estiloCabecera, int filaInicio, int celdaInicio, int filaFin, int celdaFin){
		if(filaInicio == filaFin && celdaInicio == celdaFin){
			return celdaCabecera(hoja, contenido, estiloCabecera, filaInicio, celdaInicio);
		}else{
			
		/*	Region region = new Region(filaInicio,(short)celdaInicio,filaFin,(short)celdaFin);
			hoja.addMergedRegion(region);*/
			
			//CellRangeAddress[] regions = {CellRangeAddress.valueOf("A1:A6")};
			/*hoja.addMergedRegion(CellRangeAddress.valueOf("A1:A6"));
			HSSFRow fila = hoja.createRow(filaInicio);
			fila = celdaCabecera(fila, celdaInicio, contenido, estiloCabecera);
			
			/*msalguero . añadido para que se copie el estilo en toda la region creada (horizontal) */
			/*for(int i = celdaInicio; i<=celdaFin; i++)
				hoja=editarEstiloCelda(hoja, filaInicio, i, estiloCabecera);
				*/
			return hoja;
		}
	}
	
	
	public static HSSFRow celdaCabecera(HSSFRow fila, int celdaInicio, String contenido, HSSFCellStyle estiloCabecera){
		HSSFCell celda = fila.createCell(celdaInicio);
		celda.setCellValue(contenido);
		//celda.setCellType(); /*  tipo de celda numerica =0..          */
		celda.setCellStyle(estiloCabecera);
		return fila;
	}
	
	public static HSSFSheet celdaSinEstilo(HSSFSheet hoja, String contenido, int filaInicio, int celdaInicio){
		HSSFRow fila = hoja.createRow(filaInicio);
		fila = celdaSinEstilo(fila, celdaInicio, contenido);
		return hoja;
	}
	
	public static HSSFRow celdaSinEstilo(HSSFRow fila, int celdaInicio, String contenido){
		HSSFCell celda = fila.createCell(celdaInicio);
		celda.setCellValue(contenido);
		
		return fila;
	}
	
	public static String anyadirNombreANombreFichero (String nombreFichero, String anyadido){
		String nomPlantilla = nombreFichero.substring(0, nombreFichero.indexOf("."));
		String extensionPlatilla = nombreFichero.substring(nombreFichero.indexOf("."));
		
		return nomPlantilla + anyadido + extensionPlatilla;
	}
	
	
	public static String nombreFichero(String nombrePlantilla, String RutaImagen,Long idExpediente, String texto){
		String nombreFichero = "";
		nombrePlantilla = RutaImagen + UtilidadesXLS.anyadirNombreANombreFichero(nombrePlantilla,texto);
		if(!idExpediente.equals(new Long(0))){
	//		CxAiExpedientes expediente = UtilidadesAISES.getExpedienteById(idExpediente);
		//	nombreFichero =UtilidadesXLS.anyadirNombreANombreFichero (nombrePlantilla, "_" + expediente.getNumeroSolicitud());
		}else{
			nombreFichero = nombrePlantilla;
		}
		return nombreFichero;
	}
	
	//msalguero copia el estilo en un rango de celdas en la misma fila
	public static HSSFSheet editarEstiloCelda(HSSFSheet hoja, int filaI, int columnaInicio,int columnaFin, HSSFCellStyle estilo){
		
		while (columnaInicio<=columnaFin)
		{
			hoja=editarEstiloCelda(hoja,filaI,columnaInicio,estilo);
			columnaInicio++;
			
		}
		
		
		return hoja;
		
	}
	
	//--msalguero copia el estilo en la celda
	public static HSSFSheet editarEstiloCelda(HSSFSheet hoja, int filaI, int columnaI, HSSFCellStyle estilo){
		HSSFRow fila = hoja.getRow(filaI);
		if(TratamientoDeDatos.esNullVacio(fila))
			fila = hoja.createRow(filaI);
		HSSFCell celda = fila.getCell(columnaI);
		if(TratamientoDeDatos.esNullVacio(celda))
			celda = fila.createCell(columnaI);
		celda.setCellStyle(estilo);
		
		
		return hoja;
		
	}
	
	
		//--msalguero modifica el tipo de celda = numerico, texto, formula, etc
		public static HSSFSheet editarTipoCelda(HSSFSheet hoja, int filaI, int columnaI, int tipoCelda){
			HSSFRow fila = hoja.getRow(filaI);
			if(TratamientoDeDatos.esNullVacio(fila))
				fila = hoja.createRow(filaI);
			HSSFCell celda = fila.getCell(columnaI);
			if(TratamientoDeDatos.esNullVacio(celda))
				celda = fila.createCell(columnaI);
			celda.setCellType(tipoCelda);
			
			
			return hoja;
		
	}
		
		//--msalguero  , añadir tipo numero en celda
		public static HSSFSheet celdaDouble(HSSFSheet hoja,int filaI, int columnaI, double contenido, HSSFCellStyle estiloCabecera){
			HSSFRow fila = hoja.getRow(filaI);
			if(TratamientoDeDatos.esNullVacio(fila))
				fila = hoja.createRow(filaI);
			HSSFCell celda = fila.getCell(columnaI);
			if(TratamientoDeDatos.esNullVacio(celda))
				celda = fila.createCell(columnaI); 
			
			
			celda.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			celda.setCellStyle(estiloCabecera);
			celda.setCellValue(contenido);
			celda.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			
			
			//celda.setCellType(); /*  tipo de celda numerica =0..          */
			
			return hoja;
		}
		
		
		//--msalguero  , añadir tipo formula
				public static HSSFSheet celdaFormula(HSSFSheet hoja,int filaI, int columnaI, String formula, HSSFCellStyle estiloCabecera){
					HSSFRow fila = hoja.getRow(filaI);
					if(TratamientoDeDatos.esNullVacio(fila))
						fila = hoja.createRow(filaI);
					HSSFCell celda = fila.getCell(columnaI);
					if(TratamientoDeDatos.esNullVacio(celda))
						celda = fila.createCell(columnaI); 
					
					
					celda.setCellType(HSSFCell.CELL_TYPE_FORMULA);
					
					celda.setCellStyle(estiloCabecera);
					//celda.setCellValue(formula);
					celda.setCellFormula(formula);
					
					
					//celda.setCellType(); /*  tipo de celda numerica =0..          */
					
					return hoja;
				}
}
