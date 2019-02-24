package es.grapecheck.plataforma.webaction.bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.jfree.util.Log;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

import es.grapecheck.plataforma.negocio.service.ActivadoresService;
import es.grapecheck.plataforma.negocio.service.MedicionesMinMaxService;
import es.grapecheck.plataforma.negocio.service.ParcelasService;
import es.grapecheck.plataforma.negocio.service.SensoresService;
import es.grapecheck.plataforma.persistencia.exception.PersistenciaException;
import es.grapecheck.plataforma.persistencia.vo.ActivadoresVO;
import es.grapecheck.plataforma.persistencia.vo.MedicionesMinMaxTempVO;
import es.grapecheck.plataforma.persistencia.vo.MedicionesMinMaxVO;
import es.grapecheck.plataforma.persistencia.vo.ParcelasVO;
import es.grapecheck.plataforma.persistencia.vo.SensoresVO;
import es.grapecheck.plataforma.persistencia.vo.TipoSensoresVO;
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
public class DetalleFincasBean extends BotoneraPrimefacesForm<ParcelasVO> {

private static final long serialVersionUID = -1479162331630831185L;

	private LineChartModel lineas;
	private BarChartModel barras;
	
	private ParcelasVO parcelasVO;
	
	public ParcelasVO getParcelasVO() {
		return parcelasVO;
	}

	public void setParcelasVO(ParcelasVO parcelasVO) {
		this.parcelasVO = parcelasVO;
	}
	
	private SensoresVO sensoresVO;
	
	@EJB 
	private SensoresService sensoresService;

	private List<SensoresVO> listaSensores;
	
	private List<MedicionesMinMaxTempVO> listaMedicionesTemperaturaMinMax;
	
	
	public List<MedicionesMinMaxTempVO> getListaMedicionesTemperaturaMinMax() {
		return listaMedicionesTemperaturaMinMax;
	}

	public void setListaMedicionesTemperaturaMinMax(
			List<MedicionesMinMaxTempVO> listaMedicionesTemperaturaMinMax) {
		this.listaMedicionesTemperaturaMinMax = listaMedicionesTemperaturaMinMax;
	}

	public SensoresVO getSensoresVO() {
		return sensoresVO;
	}

	public void setSensoresVO(SensoresVO sensoresVO) {
		this.sensoresVO = sensoresVO;
	}

	public SensoresService getSensoresService() {
		return sensoresService;
	}

	public void setSensoresService(SensoresService sensoresService) {
		this.sensoresService = sensoresService;
	}

	public List<SensoresVO> getListaSensores() {
		return listaSensores;
	}

	public void setListaSensores(List<SensoresVO> listaSensores) {
		this.listaSensores = listaSensores;
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
	private MedicionesMinMaxService medicionesMinMaxService;
	
	
	public MedicionesMinMaxService getMedicionesMinMaxService() {
		return medicionesMinMaxService;
	}

	public void setMedicionesMinMaxService(
			MedicionesMinMaxService medicionesMinMaxService) {
		this.medicionesMinMaxService = medicionesMinMaxService;
	}

	public DetalleFincasBean(){
		super();
	}
	
	@EJB
	private ActivadoresService activadoresService;
	
	public ActivadoresService getActivadoresService() {
		return activadoresService;
	}

	public void setActivadoresService(ActivadoresService activadoresService) {
		this.activadoresService = activadoresService;
	}
	
	private List<ActivadoresVO> listaActivadores;

	public List<ActivadoresVO> getListaActivadores() {
		return listaActivadores;
	}

	public void setListaActivadores(List<ActivadoresVO> listaActivadores) {
		this.listaActivadores = listaActivadores;
	}

	private LineChartModel dateModel;
	 
 
    public LineChartModel getDateModel() {
        return dateModel;
    }
	
	@PostConstruct
	private void init() {

		try {
			setParcelasVO(new ParcelasVO());
			ParcelasVO parcelasVO = (ParcelasVO) FlashContext.get("selectedItem");
			if(!TratamientoDeDatos.esNullVacio(parcelasVO)){
				setParcelasVO(parcelasVO);
				setListaSensores(findSensores());
				setListaMedicionesTemperaturaMinMax(findMedicionesTemperaturaMinMax(parcelasVO));
				setListaActivadores(findActivadores(parcelasVO));
			}
			this.getBotonAyuda().setOnclick("PF('modalAyuda').show()");
			this.setRutaAyuda("../ayudas/ayudaSinAyuda.xhtml");
			this.montarBotoneraMantenimientoComun(TipoBean.ALTA);
			this.getBotonImprimir().setDisabled(true);
			String accionSalirPorDefecto = "#{detalleFincasBean.goVolver}";
			this.meterAccionBoton(this.getBotonSalir(), accionSalirPorDefecto);
			
			crearDiagramas(getListaMedicionesTemperaturaMinMax());
			createDateModel();
			
		} catch (Exception e) {
			mostrarConfirmGenericoSinCancelar(FacesUtils.getValueMensajesProperties("etiqueta.mensaje.exception"),FacesUtils.getValueMensajesProperties("etiqueta.cabecera.exception"),Constantes.SEVERITY_ALERT,this.getActionListenerCancelarConfirmGenerico());
		}
	}
	
	public List<SensoresVO> findSensores(){
		List<SensoresVO> listaSensores= new ArrayList<SensoresVO>();
		try {
			listaSensores = getSensoresService().findSensoresByParcela(getParcelasVO());
		} catch (PersistenciaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		return listaSensores;
	}
	
	
	public List<MedicionesMinMaxTempVO> findMedicionesTemperaturaMinMax(ParcelasVO parcelaVO) throws PersistenciaException{
		List<MedicionesMinMaxTempVO> listaMediciones = getMedicionesMinMaxService().getMedicionesTemperaturaByIdParcela(parcelaVO);
		return listaMediciones;
	}
	
	public List<ActivadoresVO> findActivadores(ParcelasVO parcelaVO) throws PersistenciaException{
		List<ActivadoresVO> listaActivadores = getActivadoresService().getActivadoresByIdParcela(parcelaVO);//getMedicionesMinMaxService().getMedicionesTemperaturaByIdParcela(parcelaVO);
		return listaActivadores;
	}
	
	
	public String goDetalleFinca(){
		
		FlashContext.put("selectedItem", getParcelasVO());
		FacesUtils.setSessionParameter("pagAnterior", FacesUtils.getURLActual());
		
		return "/Fincas/crearFinca.xhtml";
	}
	

	public String goVolver(){
		
		String pagAnterior = FacesUtils.getSessionParameter("pagAnterior")+"?faces-redirect=true";
		
		if("/Fincas/crearFinca.xhtml".equals(FacesUtils.getSessionParameter("pagAnterior"))){
			pagAnterior = "/Principal.xhtml"+"?faces-redirect=true";
		}
		
		return pagAnterior;
	}
	
	public void add() {
		
		ParcelasVO parcelasVO = this.getMapper().map(this.getParcelasVO(),ParcelasVO.class);
			
			try {

					
					
					//Guardar
					Log.info("Guardar Parcela");
					
					if(TratamientoDeDatos.esNullCeroVacio(parcelasVO.getId())){
						//el usuario es el de login de momento.
						parcelasVO.setFkIdeUsuario((UsuarioVO)FacesUtils.getSessionParameter(Constantes.USUARIO_LOGIN));
						this.getParcelasService().addParcelas(parcelasVO);
					}else{
						
						this.getParcelasService().updateParcelas(parcelasVO);
					}
					
					//UtilidadesContingencia.setBusquedaAutomatica(true);
					//MensajeGenericoNavegando(FacesUtils.getValueMensajesProperties("etiqueta.cabecera.informacion"), FacesUtils.getValueMensajesProperties("etiqueta.mensaje.usuarioguardado"), Constantes.SEVERITY_INFO);
					mostrarConfirmGenericoSinCancelar("Guardado correctamente", Constantes.SEVERITY_INFO, "");
					//cesperilla 27-10-2015 seteo para que busque automáticamente al volver a consulta
					

			} catch (PersistenciaException e) {
				//RequestContext.getCurrentInstance().execute("PF('Excepcion').show()");
				//juanlu 10/02/2016
				e.printStackTrace();
				mostrarConfirmGenericoSinCancelar(FacesUtils.getValueMensajesProperties("etiqueta.mensaje.exception"),FacesUtils.getValueMensajesProperties("etiqueta.cabecera.exception"),Constantes.SEVERITY_ALERT,this.getActionListenerCancelarConfirmGenerico());
				
			}
				
		}
	
	public LineChartModel getAnimatedModel1() {
        return lineas;
    }
 
    public BarChartModel getAnimatedModel2() {
        return barras;
    }
 
    private void crearDiagramas(List<MedicionesMinMaxTempVO> medicionesMixMaxTempVO) {
    	lineas = initLinearModel(medicionesMixMaxTempVO);
    	lineas.setTitle("Desde el inicio de los tiempos");
    	lineas.setAnimate(true);
    	lineas.setLegendPosition("se");
        Axis yAxis = lineas.getAxis(AxisType.Y);
        yAxis.setMin(-10);
        yAxis.setMax(50);
        
         
        barras = initBarModel(medicionesMixMaxTempVO);
        barras.setTitle("Última semana");
        barras.setAnimate(true);
        barras.setLegendPosition("ne");
        yAxis = barras.getAxis(AxisType.Y);
        yAxis.setMin(-10);
        yAxis.setMax(50);
        DateAxis axis = new DateAxis("Dates");
        axis.setTickAngle(-50);
        axis.setMax("2018-06-01");
        axis.setTickFormat("%b %#d, %y");
         
        barras.getAxes().put(AxisType.X, axis);
    }
     
    private BarChartModel initBarModel(List<MedicionesMinMaxTempVO> medicionesMixMaxTempVO) {
        BarChartModel model = new BarChartModel();
 
        ChartSeries maxima = new ChartSeries();
        maxima.setLabel("Maxima");
        
        ChartSeries minima = new ChartSeries();
        minima.setLabel("Minima");
        
        for(int i=0; i<medicionesMixMaxTempVO.size(); i++){
        	MedicionesMinMaxTempVO medicion = medicionesMixMaxTempVO.get(i);
        	
        	maxima.set(TratamientoFechas.getAnio(medicion.getFechaDia())+"-"+TratamientoFechas.getMes(medicion.getFechaDia())+"-"+TratamientoFechas.getDia(medicion.getFechaDia()), TratamientoDeDatos.floatToDouble(medicion.getMax()));
        	minima.set(TratamientoFechas.getAnio(medicion.getFechaDia())+"-"+TratamientoFechas.getMes(medicion.getFechaDia())+"-"+TratamientoFechas.getDia(medicion.getFechaDia()), TratamientoDeDatos.floatToDouble(medicion.getMin()));
        	
        }
        
 
        model.addSeries(maxima);
        model.addSeries(minima);
        
        model.setTitle("Desde el inicio de los tiempos");
         
        return model;
    }
     
    
    
    private void createDateModel() {
        dateModel = new LineChartModel();
        LineChartSeries series1 = new LineChartSeries();
        series1.setLabel("Series 1");
 
        series1.set("2014-01-01", 51);
        series1.set("2014-01-06", 22);
        series1.set("2014-01-12", 65);
        series1.set("2014-01-18", 74);
        series1.set("2014-01-24", 24);
        series1.set("2014-01-30", 51);
 
        LineChartSeries series2 = new LineChartSeries();
        series2.setLabel("Series 2");
 
        series2.set("2014-01-01", 32);
        series2.set("2014-01-06", 73);
        series2.set("2014-01-12", 24);
        series2.set("2014-01-18", 12);
        series2.set("2014-01-24", 74);
        series2.set("2014-01-30", 62);
 
        dateModel.addSeries(series1);
        dateModel.addSeries(series2);
         
        dateModel.setTitle("Zoom for Details");
        dateModel.setZoom(true);
        dateModel.getAxis(AxisType.Y).setLabel("Values");
        DateAxis axis = new DateAxis("Dates");
        axis.setTickAngle(-50);
        axis.setMax("2014-02-01");
        axis.setTickFormat("%b %#d, %y");
         
        dateModel.getAxes().put(AxisType.X, axis);
    }
    
    private LineChartModel initLinearModel(List<MedicionesMinMaxTempVO> medicionesMixMaxTempVO) {
        LineChartModel model = new LineChartModel();
 
        /*LineChartSeries maxima = new LineChartSeries();
        maxima.setLabel("Maxima");
        
        LineChartSeries minima = new LineChartSeries();
        minima.setLabel("Minima");*/
 
        ChartSeries maxima = new ChartSeries();
        maxima.setLabel("Maxima");
        
        ChartSeries minima = new ChartSeries();
        minima.setLabel("Minima");
        
        for(int i=0; i<medicionesMixMaxTempVO.size(); i++){
        	MedicionesMinMaxTempVO medicion = medicionesMixMaxTempVO.get(i);
        	
        	maxima.set(TratamientoFechas.getAnio(medicion.getFechaDia())+"/"+TratamientoFechas.getMes(medicion.getFechaDia())+"/"+TratamientoFechas.getDia(medicion.getFechaDia()), TratamientoDeDatos.floatToDouble(medicion.getMax()));
        	minima.set(TratamientoFechas.getAnio(medicion.getFechaDia())+"/"+TratamientoFechas.getMes(medicion.getFechaDia())+"/"+TratamientoFechas.getDia(medicion.getFechaDia()), TratamientoDeDatos.floatToDouble(medicion.getMin()));
        	
        }
        
        /*maxima.set(1, 2);
        maxima.set(2, 1);
        maxima.set(3, 3);
        maxima.set(4, 6);
        maxima.set(5, 8);
       
 
        
 
        minima.set(1, 6);
        minima.set(2, 3);
        minima.set(3, 2);
        minima.set(4, 7);
        minima.set(5, 9);*/
        
 
        model.addSeries(maxima);
        model.addSeries(minima);
         
        return model;
    }
	
	
}
