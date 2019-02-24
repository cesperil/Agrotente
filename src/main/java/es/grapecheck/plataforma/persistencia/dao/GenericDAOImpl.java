package es.grapecheck.plataforma.persistencia.dao;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.PersistenceException;
import javax.persistence.PersistenceUnitUtil;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;

import org.hibernate.SQLQuery;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.hibernate.persister.entity.SingleTableEntityPersister;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.grapecheck.plataforma.persistencia.exception.PersistenciaException;
import es.grapecheck.plataforma.persistencia.vo.AuditoriaVO;
import es.grapecheck.plataforma.persistencia.vo.Estado;
import es.grapecheck.plataforma.persistencia.vo.MetadataHibernate;
import es.grapecheck.plataforma.persistencia.vo.UsuarioVO;
import es.grapecheck.plataforma.utiles.Constantes;
import es.grapecheck.plataforma.utiles.FacesUtils;
import es.grapecheck.plataforma.utiles.LogAsistente;
import es.grapecheck.plataforma.utiles.TratamientoDeDatos;
import es.grapecheck.plataforma.utiles.TratamientoFechas;

/**
 * Implementación de la interface {@link GenericDAO}
 * 
 * @author rsanchez
 * 
 */
public class GenericDAOImpl<T extends AuditoriaVO> implements GenericDAO<T> {
	/*
	 * Este dato lo tensmo en persistence.xml: <persistence-unit name="agile"
	 * transaction-type="JTA">
	 */
	@PersistenceContext(unitName = "agile", type = PersistenceContextType.TRANSACTION)
	private EntityManager em;

	public static final String ORDER_ASC = "asc";
	public static final String ORDER_DES = "des";

	private static final Logger LOG = LoggerFactory
			.getLogger(GenericDAOImpl.class);

	public void inicializeCollection(Object entity,
			SetAttribute<?, ?> attributeName) {
		PersistenceUnitUtil unitUtil = em.getEntityManagerFactory()
				.getPersistenceUnitUtil();
		if (!unitUtil.isLoaded(entity, attributeName.getName())) {
			this.initializeCollection(entity, attributeName.getName());
		}

	}

	protected void initializeCollection(Object entity, String attributeName) {
		// works with Hibernate EM 3.6.1-SNAPSHOT
		try {
			Method metodo = entity.getClass().getMethod(
					"get" + attributeName.substring(0, 1).toUpperCase()
							+ attributeName.substring(1));
			if (Collection.class.isAssignableFrom(metodo.getReturnType())) {
				Collection<?> collection = (Collection<?>) metodo
						.invoke(entity);
				if (collection == null) {
					return;
				}
				collection.iterator().hasNext();
			}
		} catch (Exception e) {
			LOG.error("No se ha podiddo inicializar la colección "
					+ attributeName + " de la clase "
					+ entity.getClass().getCanonicalName(), e);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws PersistenciaException
	 */
	@Override
	public T save(T entity) throws PersistenciaException {
		T object = null;
		try {
			if (TratamientoDeDatos.esNullCeroVacio(entity.getId())) {
				entity.setId(null);
			}
			entity.setFechaAlta(new Date());
			entity.setFechaUltimaModificacion(new Date());
			entity.setUsuarioUltimaModificacion(((UsuarioVO) FacesUtils
					.getSessionParameter(Constantes.USUARIO_LOGIN))
					.getUsername());
			object = em.merge(entity);
		} catch (PersistenceException e) {
			LOG.error(e.getMessage());

			throw new PersistenciaException(e,
					"Error al realizar la inserción en base de datos");
		}
		return object;
	}

	public void create(final T entity) throws PersistenciaException {
		try {
			entity.setId(null);
			entity.setFechaAlta(new Date());
			entity.setFechaUltimaModificacion(new Date());
			// entity.setUsuarioUltimaModificacion(((UsuarioVO)FacesUtils.getSessionParameter(Constantes.USUARIO_LOGIN)).getUsername());
			entity.setUsuarioUltimaModificacion("carga_inicial");

			em.persist(entity);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new PersistenciaException(e,
					"Error al realizar la inserción en base de datos");
		}
	}

	public void update(final T entity) throws PersistenciaException {
		try {
			entity.setFechaUltimaModificacion(new Date());
			entity.setUsuarioUltimaModificacion(((UsuarioVO) FacesUtils
					.getSessionParameter(Constantes.USUARIO_LOGIN))
					.getUsername());
			em.merge(entity);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new PersistenciaException(e,
					"Error al realizar la actualización en base de datos");
		}
	}

	@SuppressWarnings("unchecked")
	public T find(final Class<T> type, final Integer id)
			throws PersistenciaException {
		T result;
		Class<T> claseBusqueda = type;
		try {
			if (type.isInterface()) {
				for (EntityType<?> entity : em.getMetamodel().getEntities()) {
					final String className = entity.getName();
					if (type.getSimpleName().equals(className)) {
						claseBusqueda = (Class<T>) entity.getJavaType();
						break;
					}
				}
			}
			result = em.find(claseBusqueda, id);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new PersistenciaException(e,
					"Error al realizar la consulta en base de datos");
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public Class<T> getImplementClass(final Class<T> type) {
		Class<T> claseBusqueda = type;

		if (type.isInterface()) {
			for (EntityType<?> entity : em.getMetamodel().getEntities()) {
				final String className = entity.getName();
				if (type.getSimpleName().equals(className)) {
					claseBusqueda = (Class<T>) entity.getJavaType();
					break;
				}
			}
		}

		return claseBusqueda;
	}

	@SuppressWarnings("unchecked")
	public void delete(final T entity) throws PersistenciaException {
		try {
			this.delete((Class<T>) entity.getClass(), entity.getId());
		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new PersistenciaException(e,
					"Error al realizar el borrado en base de datos");
		}
	}

	public void delete(final Class<T> type, Integer id)
			throws PersistenciaException {
		try {
			T object = em.find(type, id);
			object.setFechaBaja(new Date());
			object.setFechaUltimaModificacion(new Date());
			object.setUsuarioUltimaModificacion(((UsuarioVO) FacesUtils
					.getSessionParameter(Constantes.USUARIO_LOGIN))
					.getUsername());
			em.merge(object);

		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new PersistenciaException(e,
					"Error al realizar el borrado en base de datos");
		}
	}

	public void deleteReal(final T entity) throws PersistenciaException {
		try {
			em.remove(entity);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new PersistenciaException(e,
					"Error al realizar el borrado en base de datos");
		}
	}

	public void deleteReal(final Class<T> type, Integer id)
			throws PersistenciaException {
		try {

			T object = type.newInstance();
			object.setId(id);
			deleteReal(object);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new PersistenciaException(e,
					"Error al realizar el borrado en base de datos");
		}
	}

	public List<T> getAllList(Class<T> type) throws PersistenciaException {

		return this.getAllList(type, null, null);
	}

	@SuppressWarnings("unchecked")
	public List<T> getAllList(Class<T> type, SingularAttribute<T, ?> orderBy,
			String tipoOrder) throws PersistenciaException {
		List<T> result;
		try {
			Query q;
			if (!TratamientoDeDatos.esNullCeroVacio(orderBy)) {
				q = em.createQuery("from " + type.getName() + " order by "
						+ orderBy.getName() + " " + tipoOrder);
			} else {
				q = em.createQuery("from " + type.getName());
			}
			result = q.getResultList();
		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new PersistenciaException(e,
					"Error al realizar la consulta en base de datos");
		}
		return result;
	}

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<T> getAllActivo(Class<T> type) throws PersistenciaException {
		List<T> resultado;
		Class<T> claseBusqueda = type;
		try {
			if (type.isInterface()) {
				for (EntityType<?> entity : em.getMetamodel().getEntities()) {
					final String className = entity.getName();
					if (type.getSimpleName().equals(className)) {
						claseBusqueda = (Class<T>) entity.getJavaType();
						break;
					}
				}
			}
			CriteriaBuilder cb = this.getEm().getCriteriaBuilder();

			CriteriaQuery<T> cq = cb.createQuery(claseBusqueda);
			Root<T> foo = cq.from(claseBusqueda);
			Predicate predicate = cb.isNull((Expression) foo.get("fechaBaja"));
			cq.where(predicate);
			cq.orderBy(cb.asc(foo.get("id")));
			resultado = this.getEm().createQuery(cq).getResultList();
		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new PersistenciaException(e,
					"Error al realizar la consulta en base de datos");
		}
		return resultado;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List<?> getAllActivoGeneric(Class<?> type)
			throws PersistenciaException {
		List<?> resultado;
		Class<?> claseBusqueda = type;
		try {

			CriteriaBuilder cb = this.getEm().getCriteriaBuilder();
			CriteriaQuery<?> cq = cb.createQuery(claseBusqueda);
			Root<?> foo = cq.from(claseBusqueda);
			Predicate predicate = cb.isNull((Expression) foo.get("fechaBaja"));
			cq.where(predicate);
			resultado = this.getEm().createQuery(cq).getResultList();
		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new PersistenciaException(e,
					"Error al realizar la consulta en base de datos");
		}
		return resultado;
	}

	public Integer updateHQL(String hql) {
		Integer listaObjetos = 0;
		if (!TratamientoDeDatos.esNullVacio(hql)) {
			listaObjetos = this.getEm().createQuery(hql).executeUpdate();
		}
		return listaObjetos;

	}

	public Map<String, SingleTableEntityPersister> obtenerMetadataClass() {
		@SuppressWarnings("unchecked")
		Map<String, SingleTableEntityPersister> mapas = em
				.unwrap(org.hibernate.Session.class).getSessionFactory()
				.getAllCollectionMetadata();
		return mapas;
	}

	public List<MetadataHibernate> obtenerTablasHibernate() {
		List<MetadataHibernate> lista = new ArrayList<MetadataHibernate>();
		MetadataHibernate metadato = new MetadataHibernate();
		AbstractEntityPersister entidad = null;
		Map<String, SingleTableEntityPersister> mapa = this
				.obtenerMetadataClass();
		Collection<SingleTableEntityPersister> collection = mapa.values();
		Iterator<SingleTableEntityPersister> iterator = collection.iterator();
		while (iterator.hasNext()) {
			entidad = iterator.next();
			metadato = new MetadataHibernate();
			metadato.setNombreTabla(entidad.getTableName());
			metadato.setNombreVO(entidad.getEntityName());
			metadato.setEsquema("");
			lista.add(metadato);
		}
		Collections.sort(lista);
		return lista;
	}

	public List<?> listarHQL(String consulta) {

		return this.getEm().createQuery(consulta).getResultList();

	}

	public List<?> listarNaturalClase(String consulta, Class<?> resultado) {

		return this.getEm().createNativeQuery(consulta, resultado)
				.getResultList();

	}

	public List<?> listarNatural(String consulta) {

		return this.getEm().createNativeQuery(consulta).getResultList();

	}

	/** {@inheritDoc} */
	public List<?> listarNaturalScalar(String consulta, List<Object[]> resultado) {

		Query query = this.getEm().createNativeQuery(consulta);

		for (Object[] item : resultado) {
			query.unwrap(SQLQuery.class).addScalar((String) item[0],
					(Type) item[1]);
		}
		return query.getResultList();
	}

	/** {@inheritDoc} */
	public List<T> findByCriteria(T objeto) throws PersistenciaException {
		return findByCriteria(objeto, Estado.ACTIVO);
	}

	/** {@inheritDoc} */
	public List<T> findByCriteria(T objeto, Estado estado)
			throws PersistenciaException {
		return findByCriteria(objeto, estado, false);
	}

	/** {@inheritDoc} */
	public List<T> findByCriteria(T objeto, Estado estado, Boolean noMaxResult)
			throws PersistenciaException {
		return findByCriteria(objeto, null, null, estado, noMaxResult);
	}

	/** {@inheritDoc} */
	public List<T> findByCriteria(T objeto,
			List<SingularAttribute<T, ?>> fletchedAttribute, JoinType joinType,
			Estado estado, Boolean noMaxResult) throws PersistenciaException {
		return this.findByCriteria(objeto, fletchedAttribute, joinType, estado,
				noMaxResult, null, null);
	}

	/** {@inheritDoc} */
	public List<T> findByCriteria(T objeto, Estado estado, Boolean noMaxResult,
			SingularAttribute<T, ?> orderBy) throws PersistenciaException {
		return this.findByCriteria(objeto, null, null, estado, noMaxResult,
				orderBy, ORDER_ASC);
	}

	/** {@inheritDoc} */
	public List<T> findByCriteria(T objeto, Estado estado, Boolean noMaxResult,
			SingularAttribute<T, ?> orderBy, String orderMode)
			throws PersistenciaException {
		return this.findByCriteria(objeto, null, null, estado, noMaxResult,
				orderBy, orderMode);
	}

	public List<T> findByCriteria(T objeto,
			List<SingularAttribute<T, ?>> fletchedAttribute, JoinType joinType,
			Estado estado, Boolean noMaxResult, SingularAttribute<T, ?> orderBy)
			throws PersistenciaException {
		return this.findByCriteria(objeto, fletchedAttribute, joinType, estado,
				noMaxResult, orderBy, ORDER_ASC);
	}

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	public List<T> findByCriteria(T objeto,
			List<SingularAttribute<T, ?>> fletchedAttribute, JoinType joinType,
			Estado estado, Boolean noMaxResult,
			SingularAttribute<T, ?> orderBy, String orderMode)
			throws PersistenciaException {
		List<T> listaObjetos = null;
		try {
			Class<T> type = (Class<T>) objeto.getClass();
			CriteriaBuilder cb = this.getEm().getCriteriaBuilder();
			CriteriaQuery<T> cq = cb.createQuery(type);

			// asociar a la tabla de búsqueda

			Root<T> foo = cq.from(type);

			if (TratamientoDeDatos.esListaConElementos(fletchedAttribute)
					&& !TratamientoDeDatos.esNullVacio(joinType)) {
				for (SingularAttribute<T, ?> fetchedAtribute : fletchedAttribute) {
					foo.fetch(fetchedAtribute, joinType);
				}

			}

			// filtrar segun los campos setteado en el VO
			List<Predicate> restricciones = this.incluirRestricciones(cb, foo,
					objeto);
			Predicate filtroEstado = filtrarPorEstado(cb, foo, estado);
			if (filtroEstado != null) {
				restricciones.add(filtroEstado);
			}

			List<Predicate> filtroEspecial = filtrarEspecial(cb, foo, objeto);
			if (TratamientoDeDatos.esListaConElementos(filtroEspecial)) {
				restricciones.addAll(filtroEspecial);
			}
			if (TratamientoDeDatos.esListaConElementos(restricciones)) {
				cq.where(restricciones.toArray(new Predicate[0]));
			}

			// settear la lista de objetos encontrados

			if (orderBy != null) {
				if (orderMode.equalsIgnoreCase(ORDER_ASC)) {
					cq.orderBy(cb.asc(foo.get(orderBy)));
				} else {
					cq.orderBy(cb.desc(foo.get(orderBy)));
				}
			}
			cq.orderBy(cb.asc(foo.get("id")));
			TypedQuery<T> q = this.getEm().createQuery(cq);

			if (!TratamientoDeDatos.booleanNoNull(noMaxResult)) {
				q.setMaxResults(500);

			}
			listaObjetos = q.getResultList();

		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new PersistenciaException(e,
					"Error al realizar la consulta en base de datos");
		}
		return listaObjetos;

	}

	/**
	 * cesperilla 11-01-2015 metodo genérico para obtener la el objeto a partir
	 * del texto introducido en un combo, utilizado para validar que el valor
	 * introducido sea correcto
	 * 
	 * @param objeto
	 * @param nombreAtributo
	 * @param valorCombo
	 * @return
	 * @throws PersistenciaException
	 */
	public List<T> findCriteriaEqualsByTextoCombo(T objeto,
			String nombreAtributo, String valorCombo)
			throws PersistenciaException {

		List<T> listaResults;
		List<Predicate> restricciones = new ArrayList<Predicate>();
		try {

			CriteriaBuilder cb = this.getEm().getCriteriaBuilder();
			Class<T> type = (Class<T>) objeto.getClass();
			CriteriaQuery<T> cq = cb.createQuery(type);
			;
			Root<T> foo = cq.from(type);
			restricciones.add(cb.isNull((Expression) foo.get("fechaBaja")));

			String cadena = TratamientoDeDatos.sNoNull(valorCombo);
			// quitar las comillas ''
			cadena = cadena.replaceAll("'", "");
			restricciones.add(cb.equal(foo.get(nombreAtributo), cadena));
			cq.where(restricciones.toArray(new Predicate[0]));
			listaResults = (List<T>) this.getEm().createQuery(cq)
					.getResultList();
		} catch (Exception e) {
			LogAsistente.error("Error al realizar la consulta", e);
			throw new PersistenciaException(e,
					"Error al realizar la consulta en base de datos");
		}

		return listaResults;
	}

	protected List<Predicate> filtrarEspecial(CriteriaBuilder cb, Root<T> foo,
			T objeto) {
		return null;
	}

	protected Predicate filtrarPorEstado(CriteriaBuilder cb, Root<T> foo,
			Estado estado) {
		Predicate predicate = null;
		// if (estado.equals(Estado.ACTIVO)) {
		// predicate = cb.isNull(foo.get("fechaBaja"));
		// } else if (estado.equals(Estado.INACTIVO)) {
		// predicate = cb.isNotNull(foo.get("fechaBaja"));
		// }

		return predicate;
	}

	protected List<Predicate> incluirRestricciones(CriteriaBuilder cb,
			Root<T> foo, T objeto) throws Exception {

		Field[] fieldsH = objeto.getClass().getDeclaredFields();
		Field[] fieldsP = objeto.getClass().getSuperclass().getDeclaredFields();
		List<Field> fields = new ArrayList<Field>();
		fields.addAll(Arrays.asList(fieldsH));
		fields.addAll(Arrays.asList(fieldsP));
		List<Predicate> restricciones = new ArrayList<Predicate>();

		for (Field field : fields) {
			Method metodo = this.obtenerMetodoGet(field, objeto);
			if (metodo != null) {
				Object result = this.obtenerResultadoMetodoGet(metodo, objeto);
				if ((field.getAnnotations().length == 0 || !TratamientoDeDatos
						.sNoNull(
								field.getAnnotations()[0].annotationType()
										.getSimpleName()).equalsIgnoreCase(
								"transient"))) {
					if ((!TratamientoDeDatos.esNullCeroVacio(result) || (field
							.getType().equals(String.class) && !TratamientoDeDatos
							.esNullVacio(result)))) {
						if (field.getType().equals(Integer.class)
								|| field.getType().equals(Long.class)) {
							restricciones.add(this.restringirPorInteger(cb,
									foo, field.getName(), result, metodo));
						} else if (field.getType().equals(String.class)) {
							// STRING
							restricciones.add(this.restringirPorString(cb, foo,
									field.getName(), result, metodo, objeto));
						} else if (field.getType().equals(Boolean.class)) {
							// BOOLEAN
							restricciones.add(this.restringirPorBoolean(cb,
									foo, field.getName(), result, metodo));
						} else if (field.getType().equals(Double.class)) {
							// DOUBLE
							restricciones.add(this.restringirPorDouble(cb, foo,
									field.getName(), result, metodo));
						} else if (field.getType().getName().contains("VO")) {
							// VO
							Predicate pr = this.restringirPorVO(cb, foo,
									field.getName(), result, metodo);
							if (pr != null) {
								restricciones.add(pr);
							}
						} else if (field.getType().equals(BigDecimal.class)) {
							// BIGDECIMAL
							restricciones.add(this.restringirPorDouble(cb, foo,
									field.getName(), result, metodo));
						} else if (field.getType().equals(Date.class)) {
							// DATE

							/*
							 * rsanchez 20151112 añadir restriccion para
							 * fechaRegistroDesde y Hasta en Expediente hacer en
							 * otro metodo??
							 */
							// if (field.getName().equals("fechaRegistroDesde"))
							// {
							// restricciones.add(this.restringirPorDateDesde(cb,
							// foo,field.getName(),"fechaRegistro",result,
							// metodo));
							// } else if
							// (field.getName().equals("fechaRegistroHasta")) {
							// restricciones.add(this.restringirPorDateHasta(cb,
							// foo,field.getName(),"fechaRegistro",result,
							// metodo));
							// }

						}
					}
				}
			}
		}
		return restricciones;
	}

	/**
	 * Filtrar por integer.
	 * 
	 * @author rquiros
	 * @param criteria
	 *            - DetachedCriteria
	 * @param atrib
	 *            - String
	 * @param result
	 *            - Objetct
	 * @param meth
	 *            - Method
	 */
	protected Predicate restringirPorInteger(CriteriaBuilder cb, Root<T> foo,
			String atrib, Object result, Method meth) {
		return cb.equal(foo.get(atrib), result);

	}

	/**
	 * Filtrar por double.
	 * 
	 * @author rquiros
	 * @param criteria
	 *            - DetachedCriteria
	 * @param atrib
	 *            - String
	 * @param result
	 *            - Objetct
	 * @param meth
	 *            - Method
	 */
	protected Predicate restringirPorDouble(CriteriaBuilder cb, Root<T> foo,
			String atrib, Object result, Method meth) {
		return cb.equal(foo.get(atrib), result);

	}

	/**
	 * Filtrar por double.
	 * 
	 * @author rquiros
	 * @param criteria
	 *            - DetachedCriteria
	 * @param atrib
	 *            - String
	 * @param result
	 *            - Objetct
	 * @param meth
	 *            - Method
	 */
	protected Predicate restringirPorLong(CriteriaBuilder cb, Root<T> foo,
			String atrib, Object result, Method meth) {
		return cb.equal(foo.get(atrib), result);

	}

	/**
	 * Filtrar por String.
	 * 
	 * @author rquiros
	 * @param criteria
	 *            - DetachedCriteria
	 * @param atrib
	 *            - String
	 * @param meth
	 *            - Method
	 * @param result
	 *            - Objetct
	 */
	protected Predicate restringirPorString(CriteriaBuilder cb, Root<T> foo,
			String atrib, Object result, Method meth) {

		String cadena = TratamientoDeDatos.sNoNull(result);
		// quitar las comillas ''
		cadena = cadena.replaceAll("'", "");
		return cb.equal(foo.get(atrib), cadena);

	}

	/**
	 * Filtrar por String mediante like, para no tener que introducir el valor
	 * exacto.
	 * 
	 * @author rsanchez
	 * @param criteria
	 *            - DetachedCriteria
	 * @param atrib
	 *            - String
	 * @param meth
	 *            - Method
	 * @param result
	 *            - Objetct
	 */
	protected Predicate restringirPorString(CriteriaBuilder cb, Root<T> foo,
			String atrib, Object result, Method meth, T object) {

		EntityType<T> type = (EntityType<T>) em.getMetamodel().entity(
				object.getClass());

		String cadena = TratamientoDeDatos.sNoNull(result);
		// quitar las comillas ''
		cadena = cadena.replaceAll("'", "");
		// rsanchez Permitir que una cadena contenga a otra, no que sea igual
		// return cb.equal(foo.get(atrib), cadena);
		return cb.like(cb.lower(foo.get(type.getDeclaredSingularAttribute(
				atrib, String.class))), "%" + cadena.toLowerCase() + "%");

	}

	/**
	 * Filtrar por Boolean.
	 * 
	 * @param criteria
	 *            - DetachedCriteria
	 * @param atrib
	 *            - String
	 * @param meth
	 *            - Method
	 * @param result
	 *            - Objetct
	 */
	protected Predicate restringirPorBoolean(CriteriaBuilder cb, Root<T> foo,
			String atrib, Object result, Method meth) {
		return cb.equal(foo.get(atrib), result);

	}

	/**
	 * Filtrar por fk de un VO.
	 * 
	 * @author rquiros
	 * @param criteria
	 *            - DetachedCriteria
	 * @param atrib
	 *            - String
	 * @param result
	 *            - Object
	 * @param meth
	 *            - Method
	 */
	protected Predicate restringirPorVO(CriteriaBuilder cb, Root<T> foo,
			String atrib, Object result, Method meth) {
		// filtrar por el id de la foreign key
		if (!TratamientoDeDatos.esNullCeroVacio((AuditoriaVO) result)
				&& !TratamientoDeDatos.esNullCeroVacio(((AuditoriaVO) result)
						.getId())) {
			return cb.equal(foo.get(atrib).get("id"),
					((AuditoriaVO) result).getId());

		} else {
			return null;
		}
	}

	/**
	 * rsanchez 20151112 Restringir por fecha desde
	 * 
	 * @param criteria
	 *            - DetachedCriteria
	 * @param atrib
	 *            - String
	 * @param meth
	 *            - Method
	 * @param result
	 *            - Objetct
	 */
	// protected Predicate restringirPorDateDesde(CriteriaBuilder cb, Root<T>
	// foo,
	// String atribDesde, String atrib, Object result, Method meth) {
	// return cb.greaterThanOrEqualTo(foo.get(atrib).as(Date.class),
	// (Date)result);
	// }

	/**
	 * rsanchez 20151112 Restringir por fecha hasta
	 * 
	 * @param criteria
	 *            - DetachedCriteria
	 * @param atrib
	 *            - String
	 * @param meth
	 *            - Method
	 * @param result
	 *            - Objetct
	 */
	// protected Predicate restringirPorDateHasta(CriteriaBuilder cb, Root<T>
	// foo,
	// String atribDesde, String atrib, Object result, Method meth) {
	// return cb.lessThanOrEqualTo(foo.get(atrib).as(Date.class), (Date)result);
	// }

	protected Object obtenerResultadoMetodoGet(Method metodoGet, T objeto)
			throws Exception {
		Object result = null;

		result = metodoGet.invoke(objeto);

		return result;
	}

	protected Method obtenerMetodoGet(Field field, T objeto) throws Exception {
		Method metodoGet = null;
		if (field != null && !field.getName().contains("IdeUuid")
				&& !field.getName().contains("EstadoRegistro")
				&& !field.getName().contains("serialVersionUID")) {
			String nombreMetodo = "get"
					+ field.getName().substring(0, 1).toUpperCase()
					+ field.getName().substring(1);
			try {
				metodoGet = objeto.getClass().getMethod(nombreMetodo);

			} catch (Exception e) {
				
			}
		}

		return metodoGet;
	}

	// cesperilla_pendiente: buscar solucion al problema de la universalizacion
	// al escribir los metodos
	//
	// public boolean sePuedeBorrarRelacion(T objeto, Integer id) throws
	// PersistenciaException {
	// Boolean borrarRelacion = Boolean.TRUE;
	// Class<T> type = (Class<T>) objeto.getClass();
	// //nombre del metodo que llama al objeto desde el que estamos borrando;
	// String nombreMetodo =
	// UtilidadesContingencia.formatoNombreClaseFk(type.getName());
	// Class<? extends BaseVOImpl> func;
	//
	// Reflections reflections = new
	// Reflections("es.gpex.ayudacontingencias.vo");
	// //obtengo todas las tablas de mi base de datos
	// Object[] tablas =
	// ((HashSet)reflections.getSubTypesOf(BaseVOImpl.class)).toArray();
	// for (int i = 0; i<tablas.length;i++){
	// //
	// Class miClase = (Class) tablas[i];
	// Method[] metodos = miClase.getDeclaredMethods();
	// for(int j =0;j<metodos.length;i++){
	// if(metodos[j].getName().equals(nombreMetodo)){
	// //hago la consulta en BD
	// }
	// }
	// }
	//
	// //List<Object> listaU = findByCriteria( objeto, Estado.ACTIVO, null,
	// null, "fkCodTerr.id", id );
	// //if(!TratamientoDeDatos.esListaNullVacia(listaU)) {
	// // borrarRelacion = Boolean.FALSE;
	// //}
	// return borrarRelacion;
	// }

	/** {@inheritDoc} */

	public void deleteList(Class clase, List listaIds)
			throws PersistenciaException {
		// comprobar la clase y la lista de entrada
		if (!TratamientoDeDatos.esNullVacio(clase)
				&& TratamientoDeDatos.esListaConElementos(listaIds)) {
			// pasar la lista a lista de integer para que no de fallo de
			// conversion
			List<Integer> listaIdsInteger = new ArrayList<Integer>();
			String IdsQuery = "";

			for (int i = 0; i < listaIds.size(); i++) {
				// listaIdsInteger.add(TratamientoDeDatos.iNoNullEsNull(listaIds.get(i)));
				if (i != 0) {
					IdsQuery += ",";
				}
				IdsQuery += listaIds.get(i).toString(); // msalguero
														// /22/03/2016, antes
														// estaba sin el += y
														// solo borraba un
														// elemento de la lista
			}

			String nombreUsuario = ((UsuarioVO) FacesUtils
					.getSessionParameter(Constantes.USUARIO_LOGIN))
					.getUsername();
			// update de una lista
			updateHQL("update " + clase.getName() + "  s set s.fechaBaja='"
					+ TratamientoFechas.dateToString(new Date())
					+ "', s.usuario='" + nombreUsuario + "' where s.id in ("
					+ IdsQuery + ")");

		}
	}

}
