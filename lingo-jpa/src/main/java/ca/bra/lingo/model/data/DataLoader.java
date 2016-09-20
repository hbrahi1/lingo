package ca.bra.lingo.model.data;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.bra.lingo.model.Customer;

/**
 * Data Loader tool for loading business partners and products into the db.
 * 
 */
public class DataLoader {

	private static Logger logger = LoggerFactory.getLogger(DataLoader.class);

	private EntityManagerFactory emf;

	public DataLoader(EntityManagerFactory emf) {
		this.emf = emf;
	}

	/**
	 * Load Customers to db from Business_Partners.xml.
	 */
	public void loadCustomers() {
		EntityManager em = emf.createEntityManager();
		TypedQuery<Customer> queryBP;
		List<Customer> resBP;
		try {
			em.getTransaction().begin();
			queryBP = em
					.createQuery("SELECT c FROM Customer c", Customer.class);
			resBP = queryBP.getResultList();
			if (resBP.size() > 5) {
				logger.info(resBP.size()
						+ " Customers already available in the db");
			} else {
				new XMLParser().readCustomers(em,
						"ca/bra/lingo/model/data/Business_Partners.xml");
				em.getTransaction().commit();
				queryBP = em.createQuery("SELECT c FROM Customer c",
						Customer.class);
				resBP = queryBP.getResultList();
				logger.info(resBP.size() + " customers loaded into the db");
			}
		} catch (Exception e) {
			logger.error("Exception occured", e);
		} finally {
			em.close();
		}
	}

	/**
	 * Load Products, Customers, Suppliers, Product Categories, customer reviews to db from
	 * respective xml's and Generate Stock.
	 */
	public void loadData() {
		loadCustomers();
	}

}
