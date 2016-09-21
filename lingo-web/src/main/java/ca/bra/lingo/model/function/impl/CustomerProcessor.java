package ca.bra.lingo.model.function.impl;

import java.util.List;
import java.util.Locale;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.olingo.odata2.api.annotation.edm.EdmFunctionImport;
import org.apache.olingo.odata2.api.annotation.edm.EdmFunctionImport.ReturnType;
import org.apache.olingo.odata2.api.annotation.edm.EdmFunctionImport.ReturnType.Type;
import org.apache.olingo.odata2.api.annotation.edm.EdmFunctionImportParameter;
import org.apache.olingo.odata2.api.commons.HttpStatusCodes;
import org.apache.olingo.odata2.api.exception.ODataApplicationException;
import org.apache.olingo.odata2.api.exception.ODataException;

import ca.bra.lingo.model.Customer;
import ca.bra.lingo.model.util.Utility;

/**
 * 
 * Function Import processor class for Customer
 * 
 */
public class CustomerProcessor {

	/**
	 * Function Import implementation for getting customer by email address
	 * 
	 * @param emailAddress
	 *            email address of the customer
	 * @return customer entity.
	 * @throws ODataException
	 */
	@SuppressWarnings("unchecked")
	@EdmFunctionImport(name = "GetCustomerByEmailAddress", entitySet = "Customer", returnType = @ReturnType(type = Type.ENTITY, isCollection = true))
	public List<Customer> getCustomerByEmailAddress(
			@EdmFunctionImportParameter(name = "EmailAddress") String emailAddress)
			throws ODataException {
		EntityManagerFactory emf = Utility.getEntityManagerFactory();
		System.out.println("zlabia--> " + emailAddress);
		EntityManager em = emf.createEntityManager();
		List<Customer> custList = null;
		try {

			Query query = em
					.createQuery("SELECT c FROM Customer c WHERE c.emailAddress ='"
							+ emailAddress + "'");

			try {

				custList = query.getResultList();
				return custList;

			} catch (NoResultException e) {
				throw new ODataApplicationException(
						"No matching customer with Email Address:"
								+ emailAddress, Locale.ENGLISH,
						HttpStatusCodes.BAD_REQUEST);
			}
		} finally {
			em.close();
		}
	}
}
