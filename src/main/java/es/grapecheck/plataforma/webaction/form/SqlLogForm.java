package es.grapecheck.plataforma.webaction.form;


import java.util.List;

import org.primefaces.model.TreeNode;
import org.primefaces.model.UploadedFile;

import es.grapecheck.plataforma.utiles.ColumnModel;

/**
 * @author rsanchez
 *
 */

public class SqlLogForm extends BotoneraPrimefacesForm<Object>{

	private String ruta;
	
	//rsanchez
	private String consulta;
	private String consola;
	List<String[]> resultadosList;
	
	
	private UploadedFile fileProperties;
	private Boolean mostrarProperties;
	private TreeNode root;
	private TreeNode selectedNode;
	private String rutaDescargaDocumento;
	private String rutaAdjuntarDocumento;
	
	private String rutaProperties;
	

	//Numero de registros obtenidos al lanzar la consultaHql
	Integer numeroRegistros;
	
	//Lista de columnas del datatable de resultados de ejecutar una consultaHql
	List<ColumnModel> columns;
	


	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public String getConsulta() {
		return consulta;
	}

	public void setConsulta(String consulta) {
		this.consulta = consulta;
	}

	public String getConsola() {
		return consola;
	}

	public void setConsola(String consola) {
		this.consola = consola;
	}

	public List<String[]> getResultadosList() {
		return resultadosList;
	}

	public void setResultadosList(List<String[]> resultadosList) {
		this.resultadosList = resultadosList;
	}

	public List<ColumnModel> getColumns() {
		return columns;
	}

	public void setColumns(List<ColumnModel> columns) {
		this.columns = columns;
	}

	public Integer getNumeroRegistros() {
		return numeroRegistros;
	}

	public void setNumeroRegistros(Integer numeroRegistros) {
		this.numeroRegistros = numeroRegistros;
	}

	public UploadedFile getFileProperties() {
		return fileProperties;
	}

	public void setFileProperties(UploadedFile fileProperties) {
		this.fileProperties = fileProperties;
	}

	public Boolean getMostrarProperties() {
		return mostrarProperties;
	}

	public void setMostrarProperties(Boolean mostrarProperties) {
		this.mostrarProperties = mostrarProperties;
	}

	public TreeNode getRoot() {
		return root;
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}

	public TreeNode getSelectedNode() {
		return selectedNode;
	}

	public void setSelectedNode(TreeNode selectedNode) {
		this.selectedNode = selectedNode;
	}

	public String getRutaDescargaDocumento() {
		return rutaDescargaDocumento;
	}

	public void setRutaDescargaDocumento(String rutaDescargaDocumento) {
		this.rutaDescargaDocumento = rutaDescargaDocumento;
	}

	public String getRutaAdjuntarDocumento() {
		return rutaAdjuntarDocumento;
	}

	public void setRutaAdjuntarDocumento(String rutaAdjuntarDocumento) {
		this.rutaAdjuntarDocumento = rutaAdjuntarDocumento;
	}

	public String getRutaProperties() {
		return rutaProperties;
	}

	public void setRutaProperties(String rutaProperties) {
		this.rutaProperties = rutaProperties;
	}
	
}
