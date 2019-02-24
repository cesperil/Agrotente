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
@Table(name = "com_activadores")
public class ActivadoresVO extends AuditoriaVO {
	
	private UsuarioVO fkIdeUsuario;
	private ParcelasVO fkIdeParcela;
	private Integer estado;
	private String ipActivador;
	private String alias;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false) 
	@JoinColumn(name = "fk_ide_usuario")
	public UsuarioVO getFkIdeUsuario() {
		return fkIdeUsuario;
	}
	public void setFkIdeUsuario(UsuarioVO fkIdeUsuario) {
		this.fkIdeUsuario = fkIdeUsuario;
	}
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false) 
	@JoinColumn(name = "fk_ide_parcela")
	public ParcelasVO getFkIdeParcela() {
		return fkIdeParcela;
	}
	public void setFkIdeParcela(ParcelasVO fkIdeParcela) {
		this.fkIdeParcela = fkIdeParcela;
	}
	
	@Column(name = "estado")
	public Integer getEstado() {
		return estado;
	}
	public void setEstado(Integer estado) {
		this.estado = estado;
	}
	
	@Column(name = "ip_activador")
	public String getIpActivador() {
		return ipActivador;
	}
	public void setIpActivador(String ipActivador) {
		this.ipActivador = ipActivador;
	}

	@Column(name = "alias")
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}

}
