package es.grapecheck.plataforma.webaction.bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.jfree.util.Log;

import es.grapecheck.plataforma.negocio.service.ParcelasService;
import es.grapecheck.plataforma.negocio.service.TrabajadoresService;
import es.grapecheck.plataforma.negocio.service.TrabajosService;
import es.grapecheck.plataforma.persistencia.exception.PersistenciaException;
import es.grapecheck.plataforma.persistencia.vo.MovimientoEconomicoVO;
import es.grapecheck.plataforma.persistencia.vo.ParcelasVO;
import es.grapecheck.plataforma.persistencia.vo.TrabajadorVO;
import es.grapecheck.plataforma.persistencia.vo.TrabajosRealizadosVO;
import es.grapecheck.plataforma.persistencia.vo.UsuarioVO;
import es.grapecheck.plataforma.utiles.Constantes;
import es.grapecheck.plataforma.utiles.FacesUtils;
import es.grapecheck.plataforma.utiles.TipoBean;
import es.grapecheck.plataforma.utiles.TratamientoDeDatos;
import es.grapecheck.plataforma.utiles.TratamientoFechas;
import es.grapecheck.plataforma.webaction.form.BotoneraPrimefacesForm;

@ManagedBean
@ViewScoped
public class AltaTrabajoBean extends BotoneraPrimefacesForm<TrabajadorVO> {

	private static final long serialVersionUID = -1479162331630831185L;
	
	private List<ParcelasVO> listaParcelas;
	
	public List<ParcelasVO> getListaParcelas() {
		return listaParcelas;
	}

	public void setListaParcelas(List<ParcelasVO> listaParcelas) {
		this.listaParcelas = listaParcelas;
	}

	private List<TrabajadorVO> listaTrabajadores;
	
	
	public List<TrabajadorVO> getListaTrabajadores() {
		return listaTrabajadores;
	}

	public void setListaTrabajadores(List<TrabajadorVO> listaTrabajadores) {
		this.listaTrabajadores = listaTrabajadores;
	}
	
	@EJB
	private TrabajadoresService trabajadoresService;
	
	public TrabajadoresService getTrabajadoresService() {
		return trabajadoresService;
	}

	public void setTrabajadoresService(TrabajadoresService trabajadoresService) {
		this.trabajadoresService = trabajadoresService;
	}

	@EJB
	private TrabajosService trabajosService;
	
	public TrabajosService getTrabajosService() {
		return trabajosService;
	}

	public void setTrabajosService(TrabajosService trabajosService) {
		this.trabajosService = trabajosService;
	}


	private TrabajosRealizadosVO trabajoRealizadoVO;
	
	public TrabajosRealizadosVO getTrabajoRealizadoVO() {
		return trabajoRealizadoVO;
	}

	public void setTrabajoRealizadoVO(TrabajosRealizadosVO trabajoRealizadoVO) {
		this.trabajoRealizadoVO = trabajoRealizadoVO;
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
			setTrabajoRealizadoVO(new TrabajosRealizadosVO());
			setListaParcelas(this.getParcelasService().getParcelasByUsuario((UsuarioVO)FacesUtils.getSessionParameter(Constantes.USUARIO_LOGIN)));
			setListaTrabajadores(this.getTrabajadoresService().getTrabajadores());
		} catch (Exception e) {
			mostrarConfirmGenericoSinCancelar(FacesUtils.getValueMensajesProperties("etiqueta.mensaje.exception"),FacesUtils.getValueMensajesProperties("etiqueta.cabecera.exception"),Constantes.SEVERITY_ALERT,this.getActionListenerCancelarConfirmGenerico());
		}
	}
	
	
	public void add() {
		
		TrabajosRealizadosVO trabajosRealizadosVO = this.getMapper().map(this.getTrabajoRealizadoVO(),TrabajosRealizadosVO.class);
			try {
					//Guardar
					Log.info("Guardar Movimiento Economico");
					
					if(TratamientoDeDatos.esNullCeroVacio(trabajosRealizadosVO.getId())){
						String textoFechaInicio = trabajosRealizadosVO.getTextoFechaInicio();
						trabajosRealizadosVO.setFechaInicio(TratamientoFechas.stringToDate(textoFechaInicio, TratamientoFechas.formatoEspanol));
						String textoFechaFin = trabajosRealizadosVO.getTextoFechaFin();
						trabajosRealizadosVO.setFechaFin(TratamientoFechas.stringToDate(textoFechaFin, TratamientoFechas.formatoEspanol));
						
						String textoTrabajador =  trabajosRealizadosVO.getTextoTrabajador();
						String textoParcela    =  trabajosRealizadosVO.getTextoParcela();
						if(!TratamientoDeDatos.esNullCeroVacio(textoParcela)){
							ParcelasVO parcela = getParcelaByNombre(textoParcela);
							trabajosRealizadosVO.setFkParcela(parcela);
						}
						if(!TratamientoDeDatos.esNullCeroVacio(textoTrabajador)){
							TrabajadorVO trabajador = getTrabajadorByNombre(textoTrabajador);
							trabajosRealizadosVO.setFkTrabajador(trabajador);
						}
						this.getTrabajosService().addTrabajo(trabajosRealizadosVO); 
					}
					
					setTrabajoRealizadoVO(new TrabajosRealizadosVO());
					
					
					mostrarConfirmGenericoSinCancelar("Guardado correctamente", Constantes.SEVERITY_INFO, "");
					
			} catch (PersistenciaException e) {
				e.printStackTrace();
				mostrarConfirmGenericoSinCancelar(FacesUtils.getValueMensajesProperties("etiqueta.mensaje.exception"),FacesUtils.getValueMensajesProperties("etiqueta.cabecera.exception"),Constantes.SEVERITY_ALERT,this.getActionListenerCancelarConfirmGenerico());
			}
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
	
	
	public TrabajadorVO getTrabajadorByNombre(String textoTrabajador){
		for(int i=0; i<getListaTrabajadores().size(); i++){
			TrabajadorVO trabajador = getListaTrabajadores().get(i);
			if(trabajador.getNombre().equals(textoTrabajador)){
				return trabajador;
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @param query
	 * @return
	 */
	public List<String> completeTextParcelas(String query) {
	        List<String> results = new ArrayList<String>();
	        for(int i = 0; i < getListaParcelas().size(); i++) {
	        	ParcelasVO parcela = getListaParcelas().get(i);
	        	if(parcela.getNombre().contains(query)){
	        		results.add(parcela.getNombre());
	        	}
	        }
	         
	        return results;
	    }
	
	/**
	 * 
	 * @param query
	 * @return
	 */
	public List<String> completeTextTrabajador(String query) {
	        List<String> results = new ArrayList<String>();
	        for(int i = 0; i < getListaTrabajadores().size(); i++) {
	        	TrabajadorVO trabajador= getListaTrabajadores().get(i);
	        	if(trabajador.getNombre().contains(query)){
	        		results.add(trabajador.getNombre());
	        	}
	        }
	         
	        return results;
	    }
	
	

	public String goNuevoAcceso(){
		
		
		
		FacesUtils.setSessionParameter("pagAnterior", FacesUtils.getURLActual());
		
		
		return "/accesos/solicitudAcceso.xhtml";
	}
}
