/**
 * 
 */
package es.grapecheck.plataforma.persistencia.vo;

import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Alejandro
 *
 */
@SuppressWarnings("serial")
@Entity
@Cacheable(true)
@Table(name = "com_mediciones")
public class MedicionesVO extends AuditoriaVO {

	private SensoresVO fkIdeSensor;
	private float medicion;
	private Date hora;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false) 
	@JoinColumn(name = "fk_ide_sensor")
	public SensoresVO getFkIdeSensor() {
		return fkIdeSensor;
	}
	
	public void setFkIdeSensor(SensoresVO fkIdeSensor) {
		this.fkIdeSensor = fkIdeSensor;
	}
	
	@Column(name = "medicion")
	public float getMedicion() {
		return medicion;
	}
	
	public void setMedicion(float medicion) {
		this.medicion = medicion;
	}
	
	@Column(name = "hora")
	public Date getHora() {
		return hora;
	}
	
	public void setHora(Date hora) {
		this.hora = hora;
	}
	
	
}
