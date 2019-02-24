package es.grapecheck.plataforma.negocio.service;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.grapecheck.plataforma.persistencia.dao.ParcelasDAO;
import es.grapecheck.plataforma.persistencia.exception.PersistenciaException;
import es.grapecheck.plataforma.persistencia.vo.ParcelasVO;
import es.grapecheck.plataforma.persistencia.vo.UsuarioVO;

@Stateless
@LocalBean
public class ParcelasService {

	private static final Logger LOG = LoggerFactory.getLogger(ParcelasService.class);
	
	@Inject
	private ParcelasDAO parcelasDAO;

	public ParcelasDAO getParcelasDAO() {
		return parcelasDAO;
	}

	public void setParcelasDAO(ParcelasDAO parcelasDAO) {
		this.parcelasDAO = parcelasDAO;
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void addParcelas(ParcelasVO parcelasVO) throws PersistenciaException {
		getParcelasDAO().create(parcelasVO);
	}
	
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateParcelas(ParcelasVO parcelasVO) throws PersistenciaException {
		getParcelasDAO().update(parcelasVO);
	}
	
	
	public List<ParcelasVO> getParcelasByUsuario(UsuarioVO usuarioVO){
		return getParcelasDAO().getParcelasByUsuario(usuarioVO);
	}
	
	/**
	 * cesperilla 29/01/2017 consulta parcelas by ide usuario y coordenadas
	 * @param usuarioVO
	 * @return
	 */
	public List<ParcelasVO> getParcelasByUsuarioByCoordenadas(Double latitud, Double longitud){
		return getParcelasDAO().getParcelasByUsuarioByCoordenadas(latitud, longitud);
	}
	
	/**
	 * cesperilla 27/01/2017 consulta parcelas by ide usuario, solo es valido si mantenemos que no se repita el nombre de la parcela para el usuario
	 * @param usuarioVO
	 * @return
	 */
	public ParcelasVO getParcelaByUsuarioByNombre(String nombreParcela){
		return getParcelasDAO().getParcelaByUsuarioByNombre(nombreParcela);
	}
	
	/**
	 * Obtiene el objeto parcela a partir del nombre
	 * Utilizada en combos autocomplete
	 * @param listaParcelas
	 * @param textoParcela
	 * @return
	 */
	public ParcelasVO getParcelaByNombre(List<ParcelasVO> listaParcelas, String textoParcela){
		for(int i=0; i<listaParcelas.size(); i++){
			ParcelasVO parcela = listaParcelas.get(i);
			if(parcela.getNombre().equals(textoParcela)){
				return parcela;
			}
		}
		return null;
	}
	
}
