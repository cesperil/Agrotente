package es.grapecheck.plataforma.persistencia.vo;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class TrabajadorXUsuarioVO extends AuditoriaVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ParcelasVO fkIdeParcela;
	
	private UsuarioVO fkIdeUsuario;

	@ManyToOne(fetch = FetchType.LAZY, optional = false) 
	@JoinColumn(name = "fk_ide_parcela")
	public ParcelasVO getFkParcela() {
		return fkIdeParcela;
	}

	public void setFkParcela(ParcelasVO fkIdeParcela) {
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

	
}
