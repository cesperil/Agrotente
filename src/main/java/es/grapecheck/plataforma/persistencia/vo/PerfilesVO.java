package es.grapecheck.plataforma.persistencia.vo;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import es.grapecheck.plataforma.utiles.Constantes;

/**
 * @author rsanchez
 *
 */

@SuppressWarnings("serial")//Esta anotación se utiliza para evitar un error en tiempo de compilación al implementar la interfaz java.io.Serializable y no tiene el campo llamado serialVersionUI
@Entity
@Cacheable(true)
@Table(name = "com_perfiles")
public class PerfilesVO extends AuditoriaVO {
	
	private String descripcion;	
	private String nombre;
	

	@Column(name = "descripcion")
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Column(name = "nombre")
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	

}