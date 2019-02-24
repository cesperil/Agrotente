package es.grapecheck.plataforma.webaction.bean;

import javax.faces.bean.ManagedBean;

import es.grapecheck.plataforma.webaction.form.ModalGenericaForm;

/**
 * @author rsanchez
 *
 */

@ManagedBean(name="modalBean")
public class ModalGenericaBean extends ModalGenericaForm {
	
	public ModalGenericaBean() {
		super();
	}
	
	public ModalGenericaBean(Boolean mostrarModal, String tituloPrincipal, String texto, Boolean boton1, Boolean Boton2) {
		super();
		setMostrarModal(mostrarModal);
		setTituloPrincipal(tituloPrincipal);
		setTexto(texto);
		setBoton1(boton1);
		setBoton2(Boton2);
	}
	
}
