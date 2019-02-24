package es.grapecheck.plataforma.persistencia.exception;

import javax.ejb.ApplicationException;
import javax.persistence.PersistenceException;

import org.hibernate.exception.SQLGrammarException;

/**
 * @author rsanchez
 *
 */

@SuppressWarnings("serial")
@ApplicationException(rollback = true)
public class PersistenciaException extends Exception {

	private int codeError;

	private String description;

	public PersistenciaException(String message) {
		this(message, null);
	}

	public PersistenciaException(String message, String description) {
		super(message);
		this.setDescription(description);
	}

	public PersistenciaException(Exception e, String mensaje) {
		this.procesarExcepcion(e, mensaje);

	}

	public PersistenciaException(Exception e) {
		this.procesarExcepcion(e, e.getMessage());
	}

	private void procesarExcepcion(Exception e, String mensaje) {
		if (e instanceof PersistenceException) {
			if (e.getCause() instanceof SQLGrammarException) {
				SQLGrammarException sqlGrammarException = (SQLGrammarException) e
						.getCause();
				this.setCodeError(sqlGrammarException.getErrorCode());
				if (sqlGrammarException
						.getCause()
						.getClass()
						.getName()
						.equals("com.microsoft.sqlserver.jdbc.SQLServerException")) {
					this.setDescription(this.tratarExcepcionSQLServer(
							this.getCodeError(), mensaje));
				} else {
					this.setDescription(mensaje);
				}
			}
		}
	}

	private String tratarExcepcionSQLServer(int code, String errorGenerico) {
		String mensaje = "Error desconocido";

		switch (code) {
		case 0:
			mensaje = "";
			break;
		case 1:
			mensaje = "";
			break;
		case 2601:
			mensaje = "Registro repetido";
			break;
		case 3:
			mensaje = "";
			break;
		case 4:
			mensaje = "";
			break;
		case 5:
			mensaje = "";
			break;
		case 6:
			mensaje = "";
			break;
		case 7:
			mensaje = "";
			break;
		case 8:
			mensaje = "";
			break;

		}

		return mensaje;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	public int getCodeError() {
		return codeError;
	}

	public void setCodeError(int codeError) {
		this.codeError = codeError;
	}

}
