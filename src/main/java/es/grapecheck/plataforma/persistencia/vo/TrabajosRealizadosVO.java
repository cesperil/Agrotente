package es.grapecheck.plataforma.persistencia.vo;

import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;


@SuppressWarnings("serial")
@Entity
@Cacheable(true)
@Table(name = "com_trabajos_realizados")
public class TrabajosRealizadosVO extends AuditoriaVO {

	private ParcelasVO fkParcela;
	
	private TrabajadorVO fkTrabajador;
	
	private Date fechaInicio;
	
	private Date fechaFin;
	
	private Double tiempo;
	
	private String descripcion;
	
	private Double importe;
	
	private String textoFechaInicio;
	
	private String textoFechaFin;

	private String textoTrabajador;
	
	private String textoParcela;
	
	@Transient
	public String getTextoTrabajador() {
		return textoTrabajador;
	}

	public void setTextoTrabajador(String textoTrabajador) {
		this.textoTrabajador = textoTrabajador;
	}

	@Transient
	public String getTextoParcela() {
		return textoParcela;
	}

	public void setTextoParcela(String textoParcela) {
		this.textoParcela = textoParcela;
	}

	@Transient
	public String getTextoFechaInicio() {
		return textoFechaInicio;
	}

	public void setTextoFechaInicio(String textoFechaInicio) {
		this.textoFechaInicio = textoFechaInicio;
	}

	@Transient
	public String getTextoFechaFin() {
		return textoFechaFin;
	}

	public void setTextoFechaFin(String textoFechaFin) {
		this.textoFechaFin = textoFechaFin;
	}

	@ManyToOne(fetch = FetchType.LAZY, optional = false) 
	@JoinColumn(name = "fk_parcela")
	public ParcelasVO getFkParcela() {
		return fkParcela;
	}

	public void setFkParcela(ParcelasVO fkIdeParcela) {
		this.fkParcela = fkIdeParcela;
	}

	@ManyToOne(fetch = FetchType.LAZY, optional = false) 
	@JoinColumn(name = "fk_trabajador")
	public TrabajadorVO getFkTrabajador() {
		return fkTrabajador;
	}

	
	public void setFkTrabajador(TrabajadorVO fkIdeTrabajador) {
		this.fkTrabajador = fkIdeTrabajador;
	}

	@Column(name = "fecha_inicio")
	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	@Column(name = "fecha_fin")
	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	@Column(name = "tiempo")
	public Double getTiempo() {
		return tiempo;
	}

	public void setTiempo(Double tiempo) {
		this.tiempo = tiempo;
	}

	@Column(name = "descripcion")
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Column(name = "importe")
	public Double getImporte() {
		return importe;
	}

	public void setImporte(Double importe) {
		this.importe = importe;
	}
	
	
}
