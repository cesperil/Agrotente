package es.grapecheck.plataforma.negocio.service;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.grapecheck.plataforma.persistencia.dao.MovimientoEconomicoDAO;
import es.grapecheck.plataforma.persistencia.exception.PersistenciaException;
import es.grapecheck.plataforma.persistencia.vo.MedicionesVO;
import es.grapecheck.plataforma.persistencia.vo.MovimientoEconomicoVO;
import es.grapecheck.plataforma.persistencia.vo.ParcelasVO;
import es.grapecheck.plataforma.utiles.TratamientoDeDatos;

@Stateless
@LocalBean
public class MovimientoEconomicoService {

	private static final Logger LOG = LoggerFactory.getLogger(MovimientoEconomicoService.class);
	
	@Inject
	private MovimientoEconomicoDAO movimientoEconomicoDAO;
	
	public MovimientoEconomicoDAO getMovimientoEconomicoDAO() {
		return movimientoEconomicoDAO;
	}

	public void setMovimientoEconomicoDAO(
			MovimientoEconomicoDAO movimientoEconomicoDAO) {
		this.movimientoEconomicoDAO = movimientoEconomicoDAO;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void addMovimiento(MovimientoEconomicoVO movimientoEconomicoVO) throws PersistenciaException {
		getMovimientoEconomicoDAO().create(movimientoEconomicoVO);
	}
	
	/**
	 * 
	 * @param importeDesde
	 * @param importeHasta
	 * @param fechaDesde
	 * @param fechaHasta
	 * @param textoParcela
	 */
	public List<MovimientoEconomicoVO> findMovimientosEconomicos(String importeDesde, String importeHasta, String fechaDesde, String fechaHasta, ParcelasVO parcelasVO){
		
	
		return getMovimientoEconomicoDAO().findListaMovimientosEconomicos(parcelasVO);
	}
	
	/**
	 * 
	 * @param listaMovimientos
	 * @return
	 */
	public String getTotalImportes( List<MovimientoEconomicoVO> listaMovimientos){
		
		Double total = 0.0;
		for(int i=0; i<listaMovimientos.size(); i++){
			MovimientoEconomicoVO movimientoEconomico = listaMovimientos.get(i);
			total = total + movimientoEconomico.getImporte();
		}
		return TratamientoDeDatos.doubleAString(total);
	}
}
