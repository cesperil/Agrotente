package es.grapecheck.plataforma.webaction.bean;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.jfree.util.Log;

import es.grapecheck.plataforma.negocio.service.MovimientoEconomicoService;
import es.grapecheck.plataforma.negocio.service.ParcelasService;
import es.grapecheck.plataforma.persistencia.exception.PersistenciaException;
import es.grapecheck.plataforma.persistencia.vo.MovimientoEconomicoVO;
import es.grapecheck.plataforma.persistencia.vo.ParcelasVO;
import es.grapecheck.plataforma.persistencia.vo.UsuarioVO;
import es.grapecheck.plataforma.utiles.Constantes;
import es.grapecheck.plataforma.utiles.FacesUtils;
import es.grapecheck.plataforma.utiles.FlashContext;
import es.grapecheck.plataforma.utiles.TipoBean;
import es.grapecheck.plataforma.utiles.TratamientoDeDatos;
import es.grapecheck.plataforma.utiles.TratamientoFechas;
import es.grapecheck.plataforma.webaction.form.BotoneraPrimefacesForm;

@ManagedBean
@ViewScoped
public class GestionEconomicaBean extends BotoneraPrimefacesForm<MovimientoEconomicoVO>{

	private static final long serialVersionUID = -1479162331630831185L;
	
	private List<ParcelasVO> listaParcelas;
	
	public List<ParcelasVO> getListaParcelas() {
		return listaParcelas;
	}

	public void setListaParcelas(List<ParcelasVO> listaParcelas) {
		this.listaParcelas = listaParcelas;
	}

	private MovimientoEconomicoVO movimientoEconomicoVO;
	
	public MovimientoEconomicoVO getMovimientoEconomicoVO() {
		return movimientoEconomicoVO;
	}

	public void setMovimientoEconomicoVO(MovimientoEconomicoVO movimientoEconomicoVO) {
		this.movimientoEconomicoVO = movimientoEconomicoVO;
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
	
	@EJB
	private ParcelasService parcelasService;
	
	public ParcelasService getParcelasService() {
		return parcelasService;
	}

	public void setParcelasService(ParcelasService parcelasService) {
		this.parcelasService = parcelasService;
	}

	@PostConstruct
	private void init() {

		try {
			
			this.getBotonAyuda().setOnclick("PF('modalAyuda').show()");
			this.setRutaAyuda("../ayudas/ayudaSinAyuda.xhtml");
			this.montarBotoneraMantenimientoComun(TipoBean.ALTA);
			this.getBotonImprimir().setDisabled(true);
			String accionSalirPorDefecto = "#{gestionEconomicaBean.goVolver}";
			this.meterAccionBoton(this.getBotonSalir(), accionSalirPorDefecto);
			setMovimientoEconomicoVO(new MovimientoEconomicoVO());
			setListaParcelas(this.getParcelasService().getParcelasByUsuario((UsuarioVO)FacesUtils.getSessionParameter(Constantes.USUARIO_LOGIN)));
		} catch (Exception e) {
			mostrarConfirmGenericoSinCancelar(FacesUtils.getValueMensajesProperties("etiqueta.mensaje.exception"),FacesUtils.getValueMensajesProperties("etiqueta.cabecera.exception"),Constantes.SEVERITY_ALERT,this.getActionListenerCancelarConfirmGenerico());
		}
	}
	
	
	public void add() {
		
		MovimientoEconomicoVO movimientoEconomicoVO = this.getMapper().map(this.getMovimientoEconomicoVO(),MovimientoEconomicoVO.class);
			try {
					//Guardar
					Log.info("Guardar Movimiento Economico");
					
					if(TratamientoDeDatos.esNullCeroVacio(movimientoEconomicoVO.getId())){
						String textoFecha = movimientoEconomicoVO.getTextoFecha();
						movimientoEconomicoVO.setFecha(TratamientoFechas.stringToDate(textoFecha, TratamientoFechas.formatoEspanol));
						movimientoEconomicoVO.setConcepto(movimientoEconomicoVO.getConcepto().toUpperCase());
						//el usuario es el de login de momento.
						movimientoEconomicoVO.setFkIdeUsuario((UsuarioVO)FacesUtils.getSessionParameter(Constantes.USUARIO_LOGIN));
						if(!TratamientoDeDatos.esNullCeroVacio(movimientoEconomicoVO.getTextoParcela())){
							ParcelasVO parcela = getParcelaByNombre(movimientoEconomicoVO.getTextoParcela());
							movimientoEconomicoVO.setFkIdeParcela(parcela);
						}
						this.getMovimientoEconomicoService().addMovimiento(movimientoEconomicoVO);
					}
					
					setMovimientoEconomicoVO(new MovimientoEconomicoVO());
					
					//UtilidadesContingencia.setBusquedaAutomatica(true);
					//MensajeGenericoNavegando(FacesUtils.getValueMensajesProperties("etiqueta.cabecera.informacion"), FacesUtils.getValueMensajesProperties("etiqueta.mensaje.usuarioguardado"), Constantes.SEVERITY_INFO);
					mostrarConfirmGenericoSinCancelar("Guardado correctamente", Constantes.SEVERITY_INFO, "");
					//cesperilla 27-10-2015 seteo para que busque automáticamente al volver a consulta
					

			} catch (PersistenciaException e) {
				e.printStackTrace();
				mostrarConfirmGenericoSinCancelar(FacesUtils.getValueMensajesProperties("etiqueta.mensaje.exception"),FacesUtils.getValueMensajesProperties("etiqueta.cabecera.exception"),Constantes.SEVERITY_ALERT,this.getActionListenerCancelarConfirmGenerico());
			}
	}
	
	
	/**
	 * 
	 * @param query
	 * @return
	 */
	public List<String> completeText(String query) {
	        List<String> results = new ArrayList<String>();
	        for(int i = 0; i < getListaParcelas().size(); i++) {
	        	ParcelasVO parcela = getListaParcelas().get(i);
	        	if(parcela.getNombre().contains(query)){
	        		results.add(parcela.getNombre());
	        	}
	        }
	         
	        return results;
	    }
	
	public ParcelasVO getParcelaByNombre(String textoParcela){
		for(int i=0; i<getListaParcelas().size(); i++){
			ParcelasVO parcela = getListaParcelas().get(i);
			if(parcela.getNombre().equals(textoParcela)){
				return parcela;
			}
		}
		return null;
	}
	
}
