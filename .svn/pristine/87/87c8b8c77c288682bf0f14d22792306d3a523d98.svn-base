package es.grapecheck.plataforma.persistencia.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.criteria.JoinType;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;

import org.hibernate.persister.entity.SingleTableEntityPersister;

import es.grapecheck.plataforma.persistencia.exception.PersistenciaException;
import es.grapecheck.plataforma.persistencia.vo.Estado;
import es.grapecheck.plataforma.persistencia.vo.MetadataHibernate;

/**
 * Clase genérica para poder realizar las operaciones de escritura y lectura en
 * base de datos.
 * @author rsanchez
 */
public interface GenericDAO<T> {

	/**
	 * Método para insertar o actualizar un registro en base de datos.
	 * 
	 * @param obj
	 *            Registro que se quiere insertar o actualizar.
	 * @throws PersistenciaException
	 */
	public T save(T entity) throws PersistenciaException;

	public void create(T entity) throws PersistenciaException;

	public void update(T entity) throws PersistenciaException;

	public void inicializeCollection(Object entity, SetAttribute<?, ?> attributeName);

	/**
	 * Método para borrar un registro de base de datos.
	 * 
	 * @param obj
	 *            Registro que se quiere borrar.
	 * @throws PersistenciaException
	 */
	// public void deleteReal(final T entity) throws PersistenciaException;

	public void delete(T objeto) throws PersistenciaException;

	public void delete(final Class<T> type, Integer id)throws PersistenciaException;

	public void deleteReal(T objeto) throws PersistenciaException;

	public void deleteReal(final Class<T> type, Integer id) throws PersistenciaException;

	public T find(final Class<T> type, final Integer id) throws PersistenciaException;

	public List<T> getAllList(final Class<T> type) throws PersistenciaException;

	public List<T> findByCriteria(T objeto) throws PersistenciaException;

	public List<T> findByCriteria(T objeto, Estado estado)throws PersistenciaException;

	public List<T> findByCriteria(T objeto, Estado estado, Boolean noMaxResult) throws PersistenciaException;

	public List<T> findByCriteria(T objeto,	List<SingularAttribute<T, ?>> fletchedAttribute, JoinType joinType,	Estado estado, Boolean noMaxResult) throws PersistenciaException;

	public List<T> findByCriteria(T objeto, Estado estado, Boolean noMaxResult,	SingularAttribute<T, ?> orderBy) throws PersistenciaException;

	public List<T> findByCriteria(T objeto, Estado estado, Boolean noMaxResult,	SingularAttribute<T, ?> orderBy, String orderMode)
			throws PersistenciaException;

	public List<T> findByCriteria(T objeto,
			List<SingularAttribute<T, ?>> fletchedAttribute, JoinType joinType,
			Estado estado, Boolean noMaxResult, SingularAttribute<T, ?> orderBy)
			throws PersistenciaException;

	public List<T> findByCriteria(T objeto,
			List<SingularAttribute<T, ?>> fletchedAttribute, JoinType joinType,
			Estado estado, Boolean noMaxResult,
			SingularAttribute<T, ?> orderBy, String orderMode)
			throws PersistenciaException;

	/**
	 * Método que devuelve todos los registros activos de T.
	 * @param type Tipo de registro que se quiere obtener.
	 * @return Lista de registros que se encuentran activos.
	 * @throws PersistenciaException
	 */
	public List<T> getAllActivo(Class<T> type) throws PersistenciaException;

	public Integer updateHQL(String hql);

	public Map<String, SingleTableEntityPersister> obtenerMetadataClass();

	public List<MetadataHibernate> obtenerTablasHibernate();

	public List<?> listarHQL(String consulta);

	public List<?> listarNaturalClase(String consulta, Class<?> resultado);

	public List<?> listarNaturalScalar(String consulta, List<Object[]> resultado);

	public List<?> listarNatural(String consulta);

	public Class<T> getImplementClass(final Class<T> type);

	List<?> getAllActivoGeneric(Class<?> type) throws PersistenciaException;

}
