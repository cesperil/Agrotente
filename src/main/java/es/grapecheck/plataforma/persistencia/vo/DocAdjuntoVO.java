package es.grapecheck.plataforma.persistencia.vo;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Cacheable(true)
@Table(name = "doc_doc_adjunto")
public class DocAdjuntoVO extends AuditoriaVO {

	private UsuarioVO 	fkIdeUsuario;
	private String 		nombre;
	private String 		descripcion;
	private String 		ruta;
	private String 		idAlfresco;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false) 
	@JoinColumn(name = "fk_ide_usuario")
	public UsuarioVO getFkIdeUsuario() {
		return fkIdeUsuario;
	}
	public void setFkIdeUsuario(UsuarioVO fkIdeUsuario) {
		this.fkIdeUsuario = fkIdeUsuario;
	}
	
	@Column(name = "nombre")
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@Column(name = "descripcion")
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	@Column(name = "ruta")
	public String getRuta() {
		return ruta;
	}
	public void setRuta(String ruta) {
		this.ruta = ruta;
	}
	
	@Column(name = "id_gestor")
	public String getIdAlfresco() {
		return idAlfresco;
	}
	public void setIdAlfresco(String idAlfresco) {
		this.idAlfresco = idAlfresco;
	}
	
}
