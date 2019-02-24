package es.grapecheck.plataforma.utiles;

import javax.faces.context.FacesContext;

/**
 * @author rsanchez
 *
 */

public class FlashContext {

	public static void put(String label, Object value) {
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put(label, value);
	}

	public static void putNow(String label, Object value) {
		FacesContext.getCurrentInstance().getExternalContext().getFlash().putNow(label, value);
	}

	public static Object get(String label) {

		return FacesContext.getCurrentInstance().getExternalContext().getFlash().get(label);
	}

	public static void setRedirect(Boolean redirect) {
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setRedirect(redirect);
	}

	public static void remove(String label) {
		FacesContext.getCurrentInstance().getExternalContext().getFlash().remove(label);
	}

	public static void keep(String label) {
		FacesContext.getCurrentInstance().getExternalContext().getFlash().keep(label);
	}

	public static void keepOld(String label) {
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put(label,FacesContext.getCurrentInstance().getExternalContext().getFlash().get(label));
	}

}
