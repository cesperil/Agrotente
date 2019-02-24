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
@Table(name = "ge_movimiento_economico")
public class MovimientoEconomicoVO extends AuditoriaVO{

	private ParcelasVO fkIdeParcela;
	
	private UsuarioVO fkIdeUsuario;
	
	private Date fecha;
	
	private String textoFecha;
	
	private String textoParcela;

	private String concepto;
	
	private Double importe;

	@ManyToOne(fetch = FetchType.LAZY, optional = false) 
	@JoinColumn(name = "fk_ide_parcela")
	public ParcelasVO getFkIdeParcela() {
		return fkIdeParcela;
	}

	public void setFkIdeParcela(ParcelasVO fkIdeParcela) {
		this.fkIdeParcela = fkIdeParcela;
	}

	@ManyToOne(fetch = FetchType.LAZY, optional = false) 
	@JoinColumn(name = "fk_ide_usuario")
	public UsuarioVO getFkIdeUsuario() {
		return fkIdeUsuario;
	}

	public void setFkIdeUsuario(UsuarioVO fkIdeUsuario) {
		this.fkIdeUsuario = fkIdeUsuario;
	}

	@Column(name = "fecha")
	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	@Column(name = "concepto")
	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	@Column(name = "importe")
	public Double getImporte() {
		return importe;
	}

	public void setImporte(Double importe) {
		this.importe = importe;
	}
	
	@Transient
	public String getTextoFecha() {
		return textoFecha;
	}

	public void setTextoFecha(String textoFecha) {
		this.textoFecha = textoFecha;
	}
	
	@Transient
	public String getTextoParcela() {
		return textoParcela;
	}

	public void setTextoParcela(String textoParcela) {
		this.textoParcela = textoParcela;
	}
	
	
	
}
