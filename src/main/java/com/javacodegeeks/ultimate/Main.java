package com.javacodegeeks.ultimate;

import com.javacodegeeks.ultimate.entities.Geek;
import com.javacodegeeks.ultimate.entities.IdCard;
import com.javacodegeeks.ultimate.entities.Person;
import com.javacodegeeks.ultimate.entities.Phone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

/**
 * Hello world!
 */
public class Main {
	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {
		new Main().run();
	}

	public void run() {
		EntityManagerFactory factory = null;
		EntityManager entityManager = null;
		TransactionExecutor transactionExecutor;
		try {
			// h2e means h2 existing (won't recreate the db)
			factory = Persistence.createEntityManagerFactory("h2");
			entityManager = factory.createEntityManager();
			transactionExecutor = new TransactionExecutor(entityManager);

			// persist/insert section
			persistPerson(transactionExecutor);
			persistGeek(transactionExecutor);
			persistPersonExt(transactionExecutor);
			// query/find section
			queryPerson(transactionExecutor);
			findPerson(transactionExecutor);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
			if (factory != null) {
				factory.close();
			}
		}
		LOGGER.debug("END run");
	}

	private void persistPerson(TransactionExecutor transactionExecutor) {
		Person person = new Person();
		person.setFirstName("Homer");
		person.setLastName("Simpson");
		transactionExecutor.execute(em -> em.persist(person));
	}

	private void persistGeek(TransactionExecutor transactionExecutor) {
		transactionExecutor.execute(em -> {
			Geek geek = new Geek();
			geek.setFirstName("Gavin");
			geek.setLastName("Coffee");
			geek.setFavouriteProgrammingLanguage("Java");
			em.persist(geek);
			geek = new Geek();
			geek.setFirstName("Thomas");
			geek.setLastName("Micro");
			geek.setFavouriteProgrammingLanguage("C#");
			em.persist(geek);
			geek = new Geek();
			geek.setFirstName("Christian");
			geek.setLastName("Cup");
			geek.setFavouriteProgrammingLanguage("Java");
			em.persist(geek);
		});
	}

	private void persistPersonExt(TransactionExecutor transactionExecutor) {
		transactionExecutor.execute(em -> {
			IdCard idCard = new IdCard();
			idCard.setIdNumber("cardId-123");
			idCard.setIssueDate(new Date());
			em.persist(idCard);

			Geek geek = new Geek();
			geek.setFirstName("Adr");
			geek.setLastName("Adr");
			geek.setFavouriteProgrammingLanguage("C++");
			geek.setIdCard(idCard);
			em.persist(geek);

			Phone phone = new Phone();
			phone.setNumber("0724234096");
			phone.setPerson(geek);
			em.persist(phone);
		});
	}

	private void queryPerson(TransactionExecutor transactionExecutor) {
		LOGGER.debug("BEGIN queryPerson");
		transactionExecutor.execute(em -> {
			TypedQuery<Person> query = em.createQuery("from Person", Person.class);
			List<Person> resultList = query.getResultList();
			for (Person person : resultList) {
				printPerson(person);
			}
		});
	}

	private void findPerson(TransactionExecutor transactionExecutor) {
		LOGGER.debug("BEGIN findPerson");
		EntityManager entityManager = transactionExecutor.getEntityManager();
		Person person1 = entityManager.find(Person.class, 1L);
		printPerson(person1);
		Person person5 = entityManager.find(Person.class, 5L);
		printPerson(person5);
		Person person6 = entityManager.find(Person.class, 6L);
		printPerson(person6);
		transactionExecutor.execute(em -> {
			Person person66 = em.find(Person.class, 6L);
			printPerson(person66);
		});
	}

	private void printPerson(Person person) {
		if (person == null) {
			LOGGER.debug("Null person!");
			return;
		}
		StringBuilder sb = new StringBuilder();
		sb.append(person.getId()).append(", ");
		sb.append(person.getFirstName()).append(" ").append(person.getLastName());
		if (person.getIdCard() != null) {
			sb.append(", ").append(person.getIdCard().getIdNumber());
		} else {
			sb.append(", ").append("no id-card");
		}
		if (person.getPhones().isEmpty()) {
			sb.append(", ").append("no phone");
		} else {
			sb.append(", ").append(person.getPhones().get(0).getNumber());
		}
		if (person instanceof Geek) {
			Geek geek = (Geek) person;
			sb.append(", ").append(geek.getFavouriteProgrammingLanguage());
		}
		LOGGER.debug(sb.toString());
	}
}
