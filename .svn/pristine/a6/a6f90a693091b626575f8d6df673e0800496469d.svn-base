package es.grapecheck.plataforma.utiles;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class UtilFile {

	public static Boolean renombrarArchivo(String rutaOriginal, String rutaNueva) {
		Boolean exito = false;
		File orig = new File(rutaOriginal), nuevo = new File(rutaNueva);
		exito = orig.renameTo(nuevo);
		return exito;
	}

	public static Boolean comprobarSiExisteFichero(String rutaNueva) {
		Boolean existe = true;

		File archivo = new File(rutaNueva);

		try {
			FileInputStream fjspx = new FileInputStream(archivo);
			try {
				fjspx.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			existe = false;
		}

		return existe;
	}

}
