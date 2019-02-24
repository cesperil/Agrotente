package es.grapecheck.plataforma.persistencia.vo;

public class MetadataHibernate implements Comparable<MetadataHibernate> {

	/**
	 * Atributo nombreVO, del tipo String.
	 */
	private String nombreVO;

	/**
	 * Atributo nombreTabla, del tipo String.
	 */
	private String nombreTabla;

	/**
	 * Atributo esquema, del tipo String.
	 */
	private String esquema;

	public int compareTo(MetadataHibernate o) {
		return this.getNombreTabla().compareToIgnoreCase(o.getNombreTabla());
	}

	/**
	 * Retorna el atributo nombreVO.
	 * 
	 * @return the nombreVO
	 */
	public String getNombreVO() {
		return nombreVO;
	}

	/**
	 * Setter del atributo nombreVO.
	 * 
	 * @param nombreVO
	 *            el nombreVO to set
	 */
	public void setNombreVO(String nombreVO) {
		this.nombreVO = nombreVO;
	}

	/**
	 * Retorna el atributo nombreTabla.
	 * 
	 * @return the nombreTabla
	 */
	public String getNombreTabla() {
		return nombreTabla;
	}

	/**
	 * Setter del atributo nombreTabla.
	 * 
	 * @param nombreTabla
	 *            el nombreTabla to set
	 */
	public void setNombreTabla(String nombreTabla) {
		this.nombreTabla = nombreTabla;
	}

	/**
	 * Retorna el atributo esquema.
	 * 
	 * @return the esquema
	 */
	public String getEsquema() {
		return esquema;
	}

	/**
	 * Setter del atributo esquema.
	 * 
	 * @param esquema
	 *            el esquema to set
	 */
	public void setEsquema(String esquema) {
		this.esquema = esquema;
	}

}
