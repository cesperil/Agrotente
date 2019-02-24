package es.grapecheck.plataforma.persistencia.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@SuppressWarnings("serial")
@Cacheable(true)
@Entity
@Table(name = "com_med_max_min_temp")
public class MedicionesMinMaxTempVO extends AuditoriaVO {

		
	private float max;
	
	private float min;
	
	private SensoresVO fkIdeSensor;
	
	private ParcelasVO fkIdeParcela;
	
	private Date fechaDia;

	
	
	@Column(name = "max")
	public float getMax() {
		return max;
	}

	public void setMax(float max) {
		this.max = max;
	}

	@Column(name = "min")
	public float getMin() {
		return min;
	}

	public void setMin(float min) {
		this.min = min;
	}

	@ManyToOne(fetch = FetchType.LAZY, optional = false) 
	@JoinColumn(name = "fk_ide_sensor")
	public SensoresVO getFkIdeSensor() {
		return fkIdeSensor;
	}
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false) 
	@JoinColumn(name = "fk_ide_parcela")
	public ParcelasVO getFkIdeParcela() {
		return fkIdeParcela;
	}

	public void setFkIdeParcela(ParcelasVO fkIdeParcela) {
		this.fkIdeParcela = fkIdeParcela;
	}
	
	public void setFkIdeSensor(SensoresVO fkIdeSensor) {
		this.fkIdeSensor = fkIdeSensor;
	}

	@Column(name = "fecha_dia")
	public Date getFechaDia() {
		return fechaDia;
	}

	public void setFechaDia(Date fechaDia) {
		this.fechaDia = fechaDia;
	}
}
