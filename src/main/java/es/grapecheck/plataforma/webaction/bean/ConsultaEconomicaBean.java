package es.grapecheck.plataforma.webaction.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import es.grapecheck.plataforma.negocio.service.MovimientoEconomicoService;
import es.grapecheck.plataforma.negocio.service.ParcelasService;
import es.grapecheck.plataforma.persistencia.vo.MovimientoEconomicoVO;
import es.grapecheck.plataforma.persistencia.vo.ParcelasVO;
import es.grapecheck.plataforma.persistencia.vo.UsuarioVO;
import es.grapecheck.plataforma.utiles.Constantes;
import es.grapecheck.plataforma.utiles.FacesUtils;
import es.grapecheck.plataforma.utiles.TipoBean;
import es.grapecheck.plataforma.utiles.TratamientoDeDatos;
import es.grapecheck.plataforma.webaction.form.BotoneraPrimefacesForm;

@ManagedBean
@ViewScoped
public class ConsultaEconomicaBean extends BotoneraPrimefacesForm<MovimientoEconomicoVO>{

private String textoParcela;
	
	private String importeDesde;
	
	private String importeHasta;
	
	private String fechaDesde;
	
	private String fechaHasta;
	
	private String importeTotal;

	public String getImporteTotal() {
		return importeTotal;
	}

	public void setImporteTotal(String importeTotal) {
		this.importeTotal = importeTotal;
	}

	public String getTextoParcela() {
		return textoParcela;
	}

	public void setTextoParcela(String textoParcela) {
		this.textoParcela = textoParcela;
	}

	public String getImporteDesde() {
		return importeDesde;
	}

	public void setImporteDesde(String importeDesde) {
		this.importeDesde = importeDesde;
	}

	public String getImporteHasta() {
		return importeHasta;
	}

	public void setImporteHasta(String importeHasta) {
		this.importeHasta = importeHasta;
	}

	public String getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(String fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public String getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(String fechaHasta) {
		this.fechaHasta = fechaHasta;
	}
	
	private List<ParcelasVO> listaParcelas;
	
	public List<ParcelasVO> getListaParcelas() {
		return listaParcelas;
	}

	public void setListaParcelas(List<ParcelasVO> listaParcelas) {
		this.listaParcelas = listaParcelas;
	}
	
	private List<MovimientoEconomicoVO> listaMovimientos;
	
	public List<MovimientoEconomicoVO> getListaMovimientos() {
		return listaMovimientos;
	}

	public void setListaMovimientos(List<MovimientoEconomicoVO> listaMovimientos) {
		this.listaMovimientos = listaMovimientos;
	}

	@EJB
	private ParcelasService parcelasService;
		
	public ParcelasService getParcelasService() {
		return parcelasService;
	}

	public void setParcelasService(ParcelasService parcelasService) {
		this.parcelasService = parcelasService;
	}
	
	@EJB
	private MovimientoEconomicoService movimientoEconomicoService;
	
	
	public MovimientoEconomicoService getMovimientoEconomicoService() {
		return movimientoEconomicoService;
	}

	public void setMovimientoEconomicoService(
			MovimientoEconomicoService movimientoEconomicoService) {
		this.movimientoEconomicoService = movimientoEconomicoService;
	}

	@PostConstruct
	private void init() {

		try {
			
			this.getBotonAyuda().setOnclick("PF('modalAyuda').show()");
			this.setRutaAyuda("../ayudas/ayudaSinAyuda.xhtml");
			this.montarBotoneraMantenimientoComun(TipoBean.CONSULTA);
			this.getBotonImprimir().setDisabled(true);
			this.getBotonGuardar().setDisabled(true);
			this.getBotonBuscar().setDisabled(false);
			String accionSalirPorDefecto = "#{gestionEconomicaBean.goVolver}";
			this.meterAccionBoton(this.getBotonSalir(), accionSalirPorDefecto);
			setListaParcelas(this.getParcelasService().getParcelasByUsuario((UsuarioVO)FacesUtils.getSessionParameter(Constantes.USUARIO_LOGIN)));
		} catch (Exception e) {
			mostrarConfirmGenericoSinCancelar(FacesUtils.getValueMensajesProperties("etiqueta.mensaje.exception"),FacesUtils.getValueMensajesProperties("etiqueta.cabecera.exception"),Constantes.SEVERITY_ALERT,this.getActionListenerCancelarConfirmGenerico());
		}
	}
	
	

	public void findElements() {
		
		String parcela 		= this.getTextoParcela();
		String importeDesde = this.getImporteDesde();
		String importeHasta = this.getImporteHasta();
		String fechaDesde 	= this.getFechaDesde();
		String fechaHasta 	= this.getFechaHasta();
		
		ParcelasVO parcelaVO = null;
		
		if(!TratamientoDeDatos.esNullCeroVacio(parcela)){
			parcelaVO = this.getParcelasService().getParcelaByNombre(getListaParcelas(), parcela);
		}
		
		List<MovimientoEconomicoVO> listaMovimientos = this.getMovimientoEconomicoService().findMovimientosEconomicos(importeDesde, importeHasta, fechaDesde, fechaHasta, parcelaVO);
		
		setListaMovimientos(listaMovimientos);
		
		setImporteTotal(this.getMovimientoEconomicoService().getTotalImportes(getListaMovimientos()));
		
	}
	
	
	
	
}
