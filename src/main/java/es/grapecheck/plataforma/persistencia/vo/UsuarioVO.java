package es.grapecheck.plataforma.persistencia.vo;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import es.grapecheck.plataforma.utiles.Constantes;


/**
 * @author rsanchez
 * 
 */

@SuppressWarnings("serial")
@Entity
@Cacheable(true)
@Table(name = "com_usuarios")
public class UsuarioVO extends AuditoriaVO 
{
	
	/*
	  `fk_perfiles` int(11) NOT NULL,
	  `username` varchar(50) NOT NULL,
	  `password` varchar(50) NOT NULL,
	  `nombre` varchar(50) NOT NULL,
	  `apellido1` varchar(50) NOT NULL,
	  `apellido2` varchar(50) NOT NULL,
	  `dni` varchar(10) NOT NULL,
	  `telefono` varchar(11) DEFAULT NULL,
	  `correo_electronico` varchar(50) DEFAULT NULL,
	  */
	
	
	
	
	private String username;

	private String password;
	
	private PerfilesVO fkIdePerfiles;

	private String  dni;

	private String  nombre;
	
	private String  apellido1;
	
	private String  apellido2;
	
	private String  telefono;
	
	private String correo;
	
	@Column(name = "username")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	@Column(name = "password")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@ManyToOne(fetch = FetchType.LAZY, optional = false) 
	@JoinColumn(name = "fk_perfiles")
	public PerfilesVO getFkIdePerfiles() {
		return fkIdePerfiles;
	}

	public void setFkIdePerfiles(PerfilesVO fkIdePerfiles) {
		this.fkIdePerfiles = fkIdePerfiles;
	}

	@Column(name = "dni")
	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	@Column(name = "nombre")
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "apellido1")
	public String getApellido1() {
		return apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	@Column(name = "apellido2")
	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	@Column(name = "telefono")
	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	@Column(name = "correo_electronico")
	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

}