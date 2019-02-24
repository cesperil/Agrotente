package es.grapecheck.plataforma.persistencia.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @author rsanchez
 *
 */


@MappedSuperclass
public class AuditoriaVO implements Serializable {
	
	private static final long serialVersionUID = 1L;

    private Integer id;
	
	private Date fechaAlta;
	
	private Date fechaBaja;
	
	private Date fechaUltimaModificacion;
	
	private String usuarioUltimaModificacion;
	
//	private String ideUuid = UUID.randomUUID().toString();
	
	@Id
	@Column(name="id", updatable=false)
	@GeneratedValue(strategy =GenerationType.IDENTITY)		
	public Integer getId() {
		return this.id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="fecha_alta", updatable=false)
	@org.hibernate.annotations.AccessType("property")	
	public Date getFechaAlta() {
		return this.fechaAlta;
	}
	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	
	@Column(name="fecha_baja")
	@org.hibernate.annotations.AccessType("property")	
	public Date getFechaBaja() {
		return this.fechaBaja;
	}
	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	@Column(name="fecha_ult_modificacion")
	@org.hibernate.annotations.AccessType("property")
	public Date getFechaUltimaModificacion() {
		return this.fechaUltimaModificacion;
	}
	public void setFechaUltimaModificacion(Date fechaUltimaModificacion) {
		this.fechaUltimaModificacion = fechaUltimaModificacion;
	}

	@Column(name="usuario_modificacion")
	@org.hibernate.annotations.AccessType("property")	
	public String getUsuarioUltimaModificacion() {
		return this.usuarioUltimaModificacion;
	}

	public void setUsuarioUltimaModificacion(String usuarioUltimaModificacion) {
		this.usuarioUltimaModificacion = usuarioUltimaModificacion;
	}
}