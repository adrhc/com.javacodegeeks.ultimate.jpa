package com.javacodegeeks.ultimate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.function.Consumer;

public class TransactionExecutor {
	private static final Logger LOGGER = LoggerFactory.getLogger(TransactionExecutor.class);
	private final EntityManager entityManager;

	public TransactionExecutor(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void execute(Consumer<EntityManager> command) {
		EntityTransaction transaction = entityManager.getTransaction();
		try {
			transaction.begin();
			command.accept(entityManager);
			transaction.commit();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			if (transaction.isActive()) {
				transaction.rollback();
			}
		}
	}
}
