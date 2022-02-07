package org.openmrs.eip.component.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.openmrs.eip.component.SyncContext;
import org.openmrs.eip.component.entity.BaseEntity;
import org.openmrs.eip.component.entity.light.UserLight;
import org.openmrs.eip.component.exception.EIPException;
import org.openmrs.eip.component.mapper.EntityToModelMapper;
import org.openmrs.eip.component.mapper.ModelToEntityMapper;
import org.openmrs.eip.component.model.BaseModel;
import org.openmrs.eip.component.model.PatientModel;
import org.openmrs.eip.component.repository.SyncEntityRepository;
import org.openmrs.eip.component.repository.light.UserLightRepository;
import org.openmrs.eip.component.service.impl.AbstractSubclassEntityService;
import org.openmrs.eip.component.service.light.AbstractLightService;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractEntityService<E extends BaseEntity, M extends BaseModel> implements EntityService<M> {
	
	protected SyncEntityRepository<E> repository;
	
	protected EntityToModelMapper<E, M> entityToModelMapper;
	
	protected ModelToEntityMapper<M, E> modelToEntityMapper;
	
	private static final String INSERT_PATIENT = "insert into patient (patient_id,creator,date_created,voided) "
	        + "values (?, ?, ?, ?)";
	
	private static final String INSERT_TEST_ORDER = "insert into test_order (order_id) values (?)";
	
	private static final String INSERT_DRUG_ORDER = "insert into drug_order (order_id,dispense_as_written) values (?, ?)";
	
	private static final String GET_ORDER_ID = "select order_id from orders where uuid = (?)";
	
	@Autowired
	private EntityManagerFactory entityManagerFactory;
	
	@Autowired
	private UserLightRepository userLightRepository;
	
	public AbstractEntityService(final SyncEntityRepository<E> repository,
	    final EntityToModelMapper<E, M> entityToModelMapper, final ModelToEntityMapper<M, E> modelToEntityMapper) {
		this.repository = repository;
		this.entityToModelMapper = entityToModelMapper;
		this.modelToEntityMapper = modelToEntityMapper;
	}
	
	/**
	 * get the service entity name
	 *
	 * @return enum
	 */
	public abstract TableToSyncEnum getTableToSync();
	
	@Override
	public M save(final M model) {
		E etyInDb = repository.findByUuid(model.getUuid());
		
		E ety = modelToEntityMapper.apply(model);
		
		if (etyInDb == null && model instanceof PatientModel && this instanceof AbstractSubclassEntityService) {
			//There is no row yet in the subclass table and we don't yet know the FK, get the parent row by uuid so
			//we can get the id and set it on this subclass
			Long id;
			BaseEntity parent = ((AbstractSubclassEntityService) this).getParentRepository().findByUuid(model.getUuid());
			if (parent != null) {
				log.info(model.getClass() + " has no matching row in the subclass table, inserting one");
				id = parent.getId();
				PatientModel pModel = (PatientModel) model;
				UserLight user = SyncContext.getBean(UserLightRepository.class).findByUuid(pModel.getPatientCreatorUuid());
				Long creatorId = user != null ? user.getId() : AbstractLightService.DEFAULT_USER_ID;
				
				PatientServiceUtils.createPatient(id, pModel.getUuid(), pModel.isPatientVoided(), creatorId,
				    pModel.getPatientDateCreated());
				
				ety.setId(id);
			}
		}
		
		M modelToReturn;
		if (etyInDb == null) {
			modelToReturn = saveEntity(ety);
			log.info(getMsg(ety, model.getUuid(), " inserted"));
		} else {
			ety.setId(etyInDb.getId());
			modelToReturn = saveEntity(ety);
			log.info(getMsg(ety, model.getUuid(), " updated"));
		}
		
		return modelToReturn;
	}
	
	private M saveEntity(final E ety) {
		return entityToModelMapper.apply(repository.save(ety));
	}
	
	@Override
	public List<M> getAllModels() {
		return mapEntities(repository.findAll());
	}
	
	@Override
	public List<M> getModels(final LocalDateTime lastSyncDate) {
		List<E> entities = repository.findModelsChangedAfterDate(lastSyncDate);
		
		return mapEntities(entities);
	}
	
	@Override
	public M getModel(final String uuid) {
		E entity = repository.findByUuid(uuid);
		return entity != null ? entityToModelMapper.apply(entity) : null;
	}
	
	@Override
	public M getModel(final Long id) {
		Optional<E> entity = repository.findById(id);
		return entity.map(entityToModelMapper).orElse(null);
	}
	
	@Override
	public void delete(String uuid) {
		E entity = repository.findByUuid(uuid);
		if (entity != null) {
			repository.delete(entity);
			log.info(getMsg(entity, uuid, " deleted"));
		} else {
			log.warn("No " + getTableToSync().getEntityClass().getName() + " found matching uuid: " + uuid);
		}
	}
	
	protected List<M> mapEntities(List<E> entities) {
		return entities.stream().map(entityToModelMapper).collect(Collectors.toList());
	}
	
	private String getMsg(final E ety, final String uuid, final String s) {
		return "Entity of type " + ety.getClass().getName() + " with uuid " + uuid + s;
	}
	
	private Long getOrderId(BaseModel model) {
		EntityManager em = entityManagerFactory.createEntityManager();
		try {
			Query query = em.createNativeQuery(INSERT_TEST_ORDER);
			query.setParameter(1, model.getUuid());
			List<Object> matches = query.getResultList();
			if (matches != null && matches.size() == 1) {
				return (Long) matches.get(0);
			} else {
				throw new EIPException("Found multiple orders with uuid: " + model.getUuid());
			}
		}
		catch (Exception e) {
			throw new EIPException("Failed to get order id for order: " + model);
		}
		finally {
			if (em != null) {
				em.close();
			}
		}
	}
	
}
