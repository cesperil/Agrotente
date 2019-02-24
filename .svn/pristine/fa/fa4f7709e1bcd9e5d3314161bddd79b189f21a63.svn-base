package es.grapecheck.plataforma.webaction.component;

import org.primefaces.component.datatable.DataTable;


/**
 * Extiende de la clase DataTable que define el componente, sobreescribo los métodos necesarios para customizar el componente.
 * @author jabecerra
 *
 */
public class DataTableViogen extends DataTable {
	
	@Override 
    public String getPaginatorPosition() { 
        return "bottom"; 
    } 
	
	@Override 
	public String getRowStyleClass() {
		Integer indice = this.getRowIndex();
		if ((indice % 2) == 0) {
			return "highlight1";
		}
		else {
			return "highlight2";
		}
	}
	
}
