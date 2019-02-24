package es.grapecheck.plataforma.webaction.bean.documentacion;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.chemistry.opencmis.client.api.Document;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import es.grapecheck.plataforma.negocio.service.DocumentoService;
import es.grapecheck.plataforma.persistencia.exception.PersistenciaException;
import es.grapecheck.plataforma.persistencia.vo.DocAdjuntoVO;
import es.grapecheck.plataforma.persistencia.vo.ParcelasVO;
import es.grapecheck.plataforma.persistencia.vo.PerfilesVO;
import es.grapecheck.plataforma.persistencia.vo.UsuarioVO;
import es.grapecheck.plataforma.utiles.Constantes;
import es.grapecheck.plataforma.utiles.FacesUtils;
import es.grapecheck.plataforma.utiles.FlashContext;
import es.grapecheck.plataforma.utiles.TipoBean;
import es.grapecheck.plataforma.utiles.TratamientoDeDatos;
import es.grapecheck.plataforma.utiles.UtilidadesAgroTente;
import es.grapecheck.plataforma.webaction.form.BotoneraPrimefacesForm;

@ManagedBean
@ViewScoped
public class AltaDocumentoBean extends BotoneraPrimefacesForm<DocAdjuntoVO> {

	private UsuarioVO usuarioVO;

	public UsuarioVO getUsuarioVO() {
		return usuarioVO;
	}

	public void setUsuarioVO(UsuarioVO usuarioVO) {
		this.usuarioVO = usuarioVO;
	}

	@EJB
	private DocumentoService documentoService;

	public DocumentoService getDocumentoService() {
		return documentoService;
	}

	public void setDocumentoService(DocumentoService documentoService) {
		this.documentoService = documentoService;
	}
	
	public List<DocAdjuntoVO> listaDocAdjunto; 

	public List<DocAdjuntoVO> getListaDocAdjunto() {
		return listaDocAdjunto;
	}

	public void setListaDocAdjunto(List<DocAdjuntoVO> listaDocAdjunto) {
		this.listaDocAdjunto = listaDocAdjunto;
	}
	
	private StreamedContent file;

	public StreamedContent getFile() {
		return file;
	}

	public void setFile(StreamedContent file) {
		this.file = file;
	}

	@PostConstruct
	private void init() {

		try {

			UsuarioVO usuarioVO = (UsuarioVO) FacesUtils
					.getSessionParameter(Constantes.USUARIO_LOGIN);
			setUsuarioVO(usuarioVO);

			DocAdjuntoVO docAdjuntoVO = (DocAdjuntoVO) FlashContext
					.get("selectedItem");
			
			List<DocAdjuntoVO> listaDocAdjunto = getDocumentoService().getDocAdjuntoByUsuario(usuarioVO);
			setListaDocAdjunto(listaDocAdjunto);
			this.getBotonAyuda().setOnclick("PF('modalAyuda').show()"); 	 	
			this.setRutaAyuda("../ayudas/ayudaSinAyuda.xhtml");
			if (!TratamientoDeDatos.esVONullVacio(docAdjuntoVO)) {
				// this.getMapper().map(docAdjuntoVO, this.getUsuarioVO());

				// PerfilesVO perfilesVO = usuarioVO.getFkIdePerfiles();

				/*
				 * if (!TratamientoDeDatos.esVONullVacio(perfilesVO)) {
				 * setTextoComboPerfil(perfilesVO.getNombre()); }
				 */

				this.montarBotoneraMantenimientoComun(TipoBean.DETALLE);
			} else {
				this.montarBotoneraMantenimientoComun(TipoBean.ALTA);
			}

			/* rsanchez deshabilitar el boton de impresión */
			this.getBotonImprimir().setDisabled(true);
			
		} catch (Exception e) {
			// RequestContext.getCurrentInstance().execute("PF('Excepcion').show()");
			// juanlu 10/02/2016
			mostrarConfirmGenericoSinCancelar(
					FacesUtils
							.getValueMensajesProperties("etiqueta.mensaje.exception"),
					FacesUtils
							.getValueMensajesProperties("etiqueta.cabecera.exception"),
					Constantes.SEVERITY_ALERT, this
							.getActionListenerCancelarConfirmGenerico());

		}
	}

	public void handleFileUpload(FileUploadEvent event) {
		FacesMessage message = new FacesMessage("Archivo", event.getFile()
				.getFileName() + " subido correctamente.");
		FacesContext.getCurrentInstance().addMessage(null, message);
		String username = getUsuarioVO().getUsername();
		String nombreFichero = event.getFile().getFileName();
		UtilidadesAgroTente.guardarFicheroFisico(event.getFile(),
				Constantes.RUTA_DOCUMENTOS_DESARROLLO + "\\" + username);
		File archivo = new File(Constantes.RUTA_DOCUMENTOS_DESARROLLO + "\\"
				+ username + "\\" + nombreFichero);
		// enviar documento alfresco
		// WebServiceFactory.setEndpointAddress("http://localhost:8080/alfresco/api");
		String idAlfresco = "";
		try {
			idAlfresco = AlfrescoClienteCmis.enviarDocumentoAlfresco(username,
					archivo);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();	
		}
		// guardar documento alfresco con su id
		DocAdjuntoVO docAdjuntoVO = new DocAdjuntoVO();
		docAdjuntoVO.setFkIdeUsuario(getUsuarioVO());
		docAdjuntoVO.setNombre(nombreFichero);
		docAdjuntoVO.setRuta(archivo.getPath());
		docAdjuntoVO.setIdAlfresco(idAlfresco);
		try {
			getDocumentoService().addDocumento(docAdjuntoVO);
			getListaDocAdjunto().add(docAdjuntoVO);
		} catch (PersistenciaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 
	 * @param documento
	 * @throws IOException 
	 */
	public void descargarDocumento(DocAdjuntoVO documento) throws IOException{
		
		String idAlfresco = documento.getIdAlfresco();
		String info[] = idAlfresco.split(";");
		Document document = AlfrescoClienteCmis.getDocumentoAlfresco(info[0]);
		
		InputStream in = document.getContentStream().getStream();
        
		file = new DefaultStreamedContent(in, "pdf", document.getName());
	
	}
}
