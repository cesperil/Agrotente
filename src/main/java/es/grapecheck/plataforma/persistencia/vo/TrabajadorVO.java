package es.grapecheck.plataforma.persistencia.vo;

import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@SuppressWarnings("serial")
@Entity
@Cacheable(true)
@Table(name = "com_trabajador")
public class TrabajadorVO extends AuditoriaVO {

	private String  dni;

	private String  nombre;
	
	private String  apellidos;
	
	private String  email;
	
	private String  telefono;
	
	private Date 	fechaNacimiento;
	
	private String 	textoFecha;
	
	
	@Transient
	public String getTextoFecha() {
		return textoFecha;
	}

	public void setTextoFecha(String textoFecha) {
		this.textoFecha = textoFecha;
	}
	
	@Column(name = "apellidos")
	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	@Column(name = "email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "fecha_nacimiento")
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
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

	@Column(name = "telefono")
	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	
	
}
