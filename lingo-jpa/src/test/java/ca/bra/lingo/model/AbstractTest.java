package ca.bra.lingo.model;

import javax.persistence.EntityManagerFactory;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import ca.bra.lingo.model.util.TestFactory;

public abstract class AbstractTest {

	protected static EntityManagerFactory emf;

	@BeforeClass
	public static void setup() {
		emf = TestFactory
				.createEntityManagerFactory(TestFactory.PERSISTENCE_UNIT);
	}

	@AfterClass
	public static void shutdown() {
		emf.close();
	}

}
