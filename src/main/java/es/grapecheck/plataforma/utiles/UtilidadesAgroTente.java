package es.grapecheck.plataforma.utiles;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.context.RequestContext;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import es.gpex.asistente.persistencia.vo.DomicilioVO;
//import es.gpex.asistente.persistencia.vo.ExpedienteVO;

import es.grapecheck.plataforma.persistencia.vo.UsuarioVO;

/**
 * @author cesperilla
 *
 */

public class UtilidadesAgroTente {

	
	private static final Logger LOG = LoggerFactory
			.getLogger(UtilidadesAgroTente.class);

	/**
	 * Cojo el usuario de sesión
	 * @return
	 */
	public static String getUsuarioSesion(){
		UsuarioVO usuario = (UsuarioVO) FacesUtils.getSessionParameter("usuarioVO");
		return usuario.getUsername();
	}
	
	/**
	 * cesperilla 27-10-2015 obtiene de sesion el parametro para realizar las busquedas automaticamente, o por el contrario tener que pulsar el botón
	 * @return
	 */
	public static Boolean getBusquedaAutomatica(){
		
		if(!TratamientoDeDatos.esNullCeroVacio(FacesUtils.getSessionParameter("busquedaAutomatica"))){
			Boolean busquedaAuto = (Boolean)FacesUtils.getSessionParameter("busquedaAutomatica");
			if(busquedaAuto){
				return true;
			}
			
		}
		
		return false;
	}
	
//public static String mostrarNombreViaDomicilio(ExpedienteVO expediente){
//		
//		DomicilioVO domicilio = new DomicilioVO();
//		
//		if(!TratamientoDeDatos.esNullCeroVacio(expediente.getFkDomicilioNot())){
//			domicilio = expediente.getFkDomicilioNot();
//		}else{
//			domicilio = expediente.getFkDomicilio();
//		}
//		
//		String nombreVia = "";
//		String tipoVia = "";
//		String tipoNum = "";
//		if(!TratamientoDeDatos.esNullCeroVacio(domicilio)){
//			/*if(!TratamientoDeDatos.esNullCeroVacio(domicilio.getFkTipoVia())){
//				tipoVia =  domicilio.get getFkTipoVia().getNomLargo();
//				nombreVia+=tipoVia + " ";
//			}*/
////			if(!TratamientoDeDatos.esNullCeroVacio(domicilio.getFkTipoNum())){
////				//tipoNum = domicilio.getFkTipoNum().getNomCorto();
////			}
//			if(!TratamientoDeDatos.esNullCeroVacio(domicilio.getNombreVia())){
//					nombreVia+= domicilio.getNombreVia();
//			}
//			if(!TratamientoDeDatos.esNullCeroVacio(domicilio.getNumero())){
//				nombreVia+=" Nº " + domicilio.getNumero();
//			}
//			if(!TratamientoDeDatos.esNullCeroVacio(domicilio.getBloque())){
//				nombreVia+=" Blq " + domicilio.getBloque();
//			}
//			if(!TratamientoDeDatos.esNullCeroVacio(domicilio.getPortal())){
//				nombreVia+=" PORTAL " + domicilio.getPortal();
//			}
//			if(!TratamientoDeDatos.esNullCeroVacio(domicilio.getEscalera())){
//				nombreVia+=" ESC " + domicilio.getEscalera();
//			}
//			if(!TratamientoDeDatos.esNullCeroVacio(domicilio.getPlanta())){
//				nombreVia+=" PLTA " + domicilio.getPlanta();
//			}
//			if(!TratamientoDeDatos.esNullCeroVacio(domicilio.getPuerta())){
//				nombreVia+=" PTA " + domicilio.getPuerta();
//			}
//		}
//		
//		return nombreVia;
//	}
	
	/*
	 * cesperilla 27-10-2015 settea en sesion los valores para la busqueda automatica
	 */
	public static void setBusquedaAutomatica(Boolean condicion){
		if(condicion){
			FacesUtils.setSessionParameter("busquedaAutomatica", condicion);
		}else{
			FacesUtils.setSessionParameter("busquedaAutomatica", null);
		}
		
	}
	
	/**
	 * copia el contenido de un fichero en otro de forma rápida
	 * 
	 * @author mgaspar
	 * @param origen
	 * @param destino
	 * @throws IOException
	 */
	public static void copy(File origen, File destino) throws IOException {
		FileChannel in = null, out = null;
		try {
			in = new FileInputStream(origen).getChannel();
			out = new FileOutputStream(destino).getChannel();
			in.transferTo(0, in.size(), out);
		} finally {
			if (in != null)
				in.close();
			if (out != null)
				out.close();
		}
	}
	
	
	//JoseCortes 17-02-2016 
	public static boolean isNifNie(String nif) {

		// si es NIE, eliminar la x,y,z inicial para tratarlo como nif
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
				return true;
			} else {

				return false;
			}
		} else
			return false;
	}
	
	public static void paint(Object object,String nombreFichero) {
		 try {
		 if (!TratamientoDeDatos.esNullVacio(object)) {
		 if (!TratamientoDeDatos.esNullCeroVacio(nombreFichero)) {
			 
		 String ruta =nombreFichero;
		 File archivo = new File(ruta);
		 FileInputStream input = new FileInputStream(archivo);
		 byte[] datos = new byte[(int) archivo.length()];
		 input.read(datos);
		 FacesContext context = FacesContext.getCurrentInstance();
		 HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
		 response.reset();
		 response.setHeader("Content-disposition", "attachment; filename= " + nombreFichero);
		 response.setContentType("application/x-unknown");
		 //response.setContentType("application/vnd.ms-excel");
		 response.setContentLength((int) archivo.length());
		 OutputStream outputStream = response.getOutputStream();
		 outputStream.write(datos);
		 outputStream.close();
		 context.getApplication().getStateManager().saveView(context);
		 context.responseComplete();
		 }
		 }
		 } catch (Exception e) {
			 e.printStackTrace();
		 }
		 }
	
	/**
	 * cesperilla 16-11-2015 metodo para subir ficheros y guardarlos en el servidor
	 * @param file
	 */
	public static void guardarFicheroFisico(UploadedFile file, String ruta){
		 try {
			 if(!TratamientoDeDatos.esNullCeroVacio(file)){
				 File targetFolder = new File(ruta);
				 targetFolder.mkdirs();
		            InputStream inputStream = file.getInputstream();
		            OutputStream out = new FileOutputStream(new File(targetFolder,
		            		file.getFileName()));
		            int read = 0;
		            byte[] bytes = new byte[1024];

		            while ((read = inputStream.read(bytes)) != -1) {
		                out.write(bytes, 0, read);
		            }
		            inputStream.close();
		            out.flush();
		            out.close();
		       
			 }
			 
		 } catch (IOException e) {
	            e.printStackTrace();
	     }           
	}
	
	
	/** rsanchez borrado fisico de fichero dada su ruta (donde ruta contiene el nombre del fichero) */
	public static void borradoFicheroFisico(String ruta) {

		File ficheroBorrar = new File(ruta);
		ficheroBorrar.delete();
		LogAsistente.info("UtilidadesAsistenteAGILE - borradoFicheroFisico - Borrado fichero fisico con ruta: "+ruta);

	}
	
	
	public static void descargarFicheroADO(String ruta,String nombreFichero){

		
		RandomAccessFile fichero = null;
		FileOutputStream fos = null;
		long Tamano = 0;
		
		if (!ruta.equals("")) {
			try {
					fichero = new RandomAccessFile (ruta, "r");
					if(Tamano == 0 || Tamano > fichero.length())
						Tamano = fichero.length();
					
					if (fichero.length()-Tamano>0)
						fichero.seek(fichero.length()-Tamano);
					String line1 = null;
					StringBuilder SB = new StringBuilder();
					boolean primeraLinea = true;
					while ((line1 = fichero.readLine()) != null) {
						if(primeraLinea){
							SB.append(line1);
							primeraLinea=false;
						}else{
							SB.append("\r\n"+line1);
						}
					}
					fichero.close();
					File fi = new File(ruta);
					fos = new FileOutputStream(fi);
					//esta es la linea que lo jode todo
					fos.write(SB.toString().getBytes());
					fos.close();
					//alguna de las 2 siguientes lineas me jode la vida
					FileInputStream fis = new FileInputStream(fi);
					//OutputStream output = new FileOutputStream(fi);
					//abrirFicheroLog(fis, nombreFichero);
					paint(new Object(),nombreFichero);
					fis.close();
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				LogAsistente.error("Problema con la ruta del fichero",e);
				//e.printStackTrace();
			}finally {
				try {
					fichero.close();
				} catch (Exception e) {
					LogAsistente.error("Problema con la ruta del fichero",e);
					//e.printStackTrace();
				}
			}
		}
	}
	
	
	public static void descargarFichero(String ruta,String nombreFichero){
			RandomAccessFile fichero = null;
			FileOutputStream fos = null;
			long Tamano = 0;
			
			if (!ruta.equals("")) {
				try {
						fichero = new RandomAccessFile (ruta, "r");
						if(Tamano == 0 || Tamano > fichero.length())
							Tamano = fichero.length();
						
						if (fichero.length()-Tamano>0)
							fichero.seek(fichero.length()-Tamano);
						String line1 = null;
						StringBuilder SB = new StringBuilder();
						while ((line1 = fichero.readLine()) != null) {
							SB.append(line1 + "\n");
						}
						fichero.close();
						File fi = new File(ruta);
						fos = new FileOutputStream(fi);
						//esta es la linea que lo jode todo
						fos.write(SB.toString().getBytes());
						fos.close();
						//alguna de las 2 siguientes lineas me jode la vida
						FileInputStream fis = new FileInputStream(fi);
						//OutputStream output = new FileOutputStream(fi);
						//abrirFicheroLog(fis, nombreFichero);
						paint(new Object(),nombreFichero);
						fis.close();
				}
				catch (IOException e) {
					// TODO Auto-generated catch block
					LogAsistente.error("Problema con la ruta del fichero",e);
					//e.printStackTrace();
				}finally {
					try {
						fichero.close();
					} catch (Exception e) {
						LogAsistente.error("Problema con la ruta del fichero",e);
						//e.printStackTrace();
					}
				}
			}
		}
		
	
	public static void descargarFicheroExcel(String ruta){
		try {
		File ficheroXLS = new File(ruta);
		FacesContext ctx = FacesContext.getCurrentInstance();
		FileInputStream fis;
		
			fis = new FileInputStream(ficheroXLS);
	
		byte[] bytes = new byte[1000];
		int read = 0;

		if (!ctx.getResponseComplete()) {
		   String fileName = ficheroXLS.getName();
		   String contentType = "application/vnd.ms-excel";
		   //String contentType = "application/pdf";
		   HttpServletResponse response =(HttpServletResponse) ctx.getExternalContext().getResponse();
		   response.setContentType(contentType);
		   response.setHeader("Content-Disposition","attachment;filename=\"" + fileName + "\"");
		   ServletOutputStream out = response.getOutputStream();

		   while ((read = fis.read(bytes)) != -1) {
		        out.write(bytes, 0, read);
		   }

		   out.flush();
		   out.close();
		   System.out.println("\nDescargado\n");
		   ctx.responseComplete();
		}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void descargarFicheroPdf(String ruta){
		try {
		File ficheroPdf = new File(ruta);
		FacesContext ctx = FacesContext.getCurrentInstance();
		FileInputStream fis;
		
			fis = new FileInputStream(ficheroPdf);
	
		byte[] bytes = new byte[1000];
		int read = 0;

		if (!ctx.getResponseComplete()) {
		   String fileName = ficheroPdf.getName();
		   String contentType = "application/x-unknown";
		   //String contentType = "application/pdf";
		   HttpServletResponse response =(HttpServletResponse) ctx.getExternalContext().getResponse();
		   response.setContentType(contentType);
		   response.setHeader("Content-Disposition","attachment;filename=\"" + fileName + "\"");
		   ServletOutputStream out = response.getOutputStream();

		   while ((read = fis.read(bytes)) != -1) {
		        out.write(bytes, 0, read);
		   }

		   out.flush();
		   out.close();
		   System.out.println("\nDescargado\n");
		   ctx.responseComplete();
		}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * muestra un mensaje en la pantalla de informacion
	 * @param titulo
	 * @param mensaje
	 */
	public static void MensajeGenerico(String titulo,String mensaje){
		//this.setMensajeModal(mensaje);
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, titulo, mensaje);
		
		RequestContext.getCurrentInstance().showMessageInDialog(message);
		
		//RequestContext.getCurrentInstance().execute("PF('infoGenerico').show()");
	}
	
	
	
}
