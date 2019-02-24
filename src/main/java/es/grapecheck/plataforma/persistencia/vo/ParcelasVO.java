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
@Table(name = "com_parcelas")
public class ParcelasVO extends AuditoriaVO{

	private String latitud;
	
	private String longitud;
	
	private Integer cod_municipio;
	
	private Integer poligono;
	
	private Integer parcela;
	
	private UsuarioVO fkIdeUsuario;
	
	private String nombre;

	@Column(name = "latitud")
	public String getLatitud() {
		return latitud;
	}

	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}

	@Column(name = "longitud")
	public String getLongitud() {
		return longitud;
	}

	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}

	@Column(name = "cod_municipio")
	public Integer getCod_municipio() {
		return cod_municipio;
	}

	public void setCod_municipio(Integer cod_municipio) {
		this.cod_municipio = cod_municipio;
	}

	@Column(name = "poligono")
	public Integer getPoligono() {
		return poligono;
	}

	public void setPoligono(Integer poligono) {
		this.poligono = poligono;
	}

	@Column(name = "parcela")
	public Integer getParcela() {
		return parcela;
	}

	public void setParcela(Integer parcela) {
		this.parcela = parcela;
	}

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
	
	
}
