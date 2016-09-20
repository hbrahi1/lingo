package ca.bra.lingo.model.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.bra.lingo.model.Customer;


public class TestFactory {
	public static final String TEST_JDBC_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
	private static final String TEST_JDBC_URL_IN_MEMORY = "jdbc:derby:memory:DemoDB;create=true";
	public static final String TEST_JDBC_URL = "jdbc:derby:DemoDB;create=true";
	public static final String TEST_JDBC_USER = "demo";
	public static final String TEST_JDBC_PASSWORD = "demo";
	private static final String TEST_TARGET_DATABASE = "Derby";
	private static final String TEST_JPA_LOG_LEVEL = "INFO";
	public static final String PERSISTENCE_UNIT = "ca.bra.lingo.model";

	/*
	 * Indicates whether the test database instance is in-memory or file system
	 * based
	 */
	private static boolean inMemory = true;
	private static Logger logger = LoggerFactory.getLogger(TestFactory.class);
	private static Map<String, String> defaultProperties = null;
	protected static EntityManagerFactory emf;

	/**
	 * Create an EntityManagerFactory instance for the named persistence unit
	 * for accessing a local in-memory Derby test database instance.
	 * <p>
	 * 
	 * @param persistenceUnitName
	 *            the persistence unit name
	 * @return an EntityManagerFactory instance for accessing a local in-memory
	 *         Derby test database instance
	 */
	public static EntityManagerFactory createEntityManagerFactory(
			String persistenceUnitName) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(
				persistenceUnitName, getDefaultTestProperties());
		Utility.setEntityManagerFactory(emf);
		return emf;
	}

	/**
	 * Helper method to create Business Partner
	 * 
	 * @param em
	 *            the entity manager instance
	 * @param id
	 *            the primary key product db id.
	 * @return the true/false status of the success/failure of operation.
	 */
	public Boolean createCustomer(EntityManager em, String customerId) {
		Boolean status = true;
		Customer bupa = null;
		Date date = null;
		DateFormat formatter = new SimpleDateFormat("yyyymmdd");
		try {
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
			}

			Calendar cal = Calendar.getInstance();
			date = formatter.parse("19770707");
			cal.setTime(date);
			bupa = new Customer();
			bupa.setCustomerId(customerId);
			bupa.setPhoneNumber("009180437980098");
			bupa.setDateOfBirth(cal);
			em.persist(bupa);
			em.getTransaction().commit();
		} catch (Exception e) {
			status = false;
			logger.error("Error occured during creation of Business Partner. Detailed info: "
					+ e);
		}

		return status;
	}

	/**
	 * Helper method to delete Business Partner
	 * 
	 * @param em
	 *            the entity manager instance
	 * @param id
	 *            the primary key product db id.
	 * @return the true/false status of the success/failure of operation.
	 */
	public Boolean deleteCustomer(EntityManager em, String id) {
		Boolean status = true;
		Customer bupa = null;
		try {
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
			}
			bupa = em.find(Customer.class, id);
			em.remove(bupa);
			em.getTransaction().commit();
		} catch (Exception e) {
			status = false;
			logger.error("Error occured during delete of Business Partner. Detailed info: "
					+ e);
		}
		return status;
	}

	/**
	 * Get persistence.xml properties
	 * 
	 * @return properties as Map
	 */
	private static Map<String, String> getDefaultTestProperties() {
		if (defaultProperties == null) {
			defaultProperties = new HashMap<String, String>();
			defaultProperties.put(PersistenceUnitProperties.JDBC_DRIVER,
					TEST_JDBC_DRIVER);
			if (inMemory) {
				defaultProperties.put(PersistenceUnitProperties.JDBC_URL,
						TEST_JDBC_URL_IN_MEMORY);
			} else {
				defaultProperties.put(PersistenceUnitProperties.JDBC_URL,
						TEST_JDBC_URL);
			}
			defaultProperties.put(PersistenceUnitProperties.JDBC_USER,
					TEST_JDBC_USER);
			defaultProperties.put(PersistenceUnitProperties.JDBC_PASSWORD,
					TEST_JDBC_PASSWORD);
			defaultProperties.put(PersistenceUnitProperties.TARGET_DATABASE,
					TEST_TARGET_DATABASE);
			defaultProperties.put(PersistenceUnitProperties.LOGGING_LEVEL,
					TEST_JPA_LOG_LEVEL);
			defaultProperties.put(PersistenceUnitProperties.DDL_GENERATION,
					PersistenceUnitProperties.DROP_AND_CREATE);
		}
		return defaultProperties;
	}

}
