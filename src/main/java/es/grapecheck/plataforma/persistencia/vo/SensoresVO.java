package es.grapecheck.plataforma.persistencia.vo;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * @author Alejandro
 *
 */
@SuppressWarnings("serial")
@Entity
@Cacheable(true)
@Table(name = "com_sensor")
public class SensoresVO extends AuditoriaVO {
	
	private ParcelasVO fkIdeParcela;
	private TipoSensoresVO fkIdeTipoSensor;
	private boolean activo;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false) 
	@JoinColumn(name = "fk_ide_parcela")
	public ParcelasVO getFkIdeParcela() {
		return fkIdeParcela;
	}
	
	public void setFkIdeParcela(ParcelasVO fkIdeParcela) {
		this.fkIdeParcela = fkIdeParcela;
	}
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false) 
	@JoinColumn(name = "fk_ide_tipo_sensor")
	public TipoSensoresVO getFkIdeTipoSensor() {
		return fkIdeTipoSensor;
	}
	
	public void setFkIdeTipoSensor(TipoSensoresVO fkIdeTipoSensor) {
		this.fkIdeTipoSensor = fkIdeTipoSensor;
	}
	
	@Column(name = "activo")
	public boolean getActivo() {
		return activo;
	}
	
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
	

}
