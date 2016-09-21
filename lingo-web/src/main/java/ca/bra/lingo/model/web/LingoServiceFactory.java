package ca.bra.lingo.model.web;

import javax.persistence.EntityManagerFactory;

import org.apache.olingo.odata2.api.ODataCallback;
import org.apache.olingo.odata2.api.ODataDebugCallback;
import org.apache.olingo.odata2.api.ep.EntityProvider;
import org.apache.olingo.odata2.api.exception.ODataApplicationException;
import org.apache.olingo.odata2.api.processor.ODataErrorCallback;
import org.apache.olingo.odata2.api.processor.ODataErrorContext;
import org.apache.olingo.odata2.api.processor.ODataResponse;
import org.apache.olingo.odata2.core.exception.ODataRuntimeException;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAContext;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAServiceFactory;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LingoServiceFactory extends ODataJPAServiceFactory{
	
	private static final Logger logger =
			LoggerFactory.getLogger(LingoServiceFactory.class);

	private static final String PERSISTENCE_UNIT_NAME = "ca.bra.lingo.model";
	
	@Override
	public ODataJPAContext initializeODataJPAContext() throws ODataJPARuntimeException {
		ODataJPAContext oDataJPAContext = this.getODataJPAContext();
		EntityManagerFactory emf;
		try {
			emf = JpaEntityManagerFactory.getEntityManagerFactory();
			oDataJPAContext.setEntityManagerFactory(emf);
			oDataJPAContext.setPersistenceUnitName(PERSISTENCE_UNIT_NAME);
			oDataJPAContext.setJPAEdmExtension(new LingoProcessingExtension());
			oDataJPAContext.setJPAEdmMappingModel("LingoEdmMapping.xml");
			return oDataJPAContext;
		} catch (Exception e) {
			throw new ODataRuntimeException(e);
		}
	}
	
	@Override
	public <T extends ODataCallback> T getCallback(Class<T> callbackInterface)
	{
		  return (T) (callbackInterface.isAssignableFrom(ScenarioErrorCallback.class) ? 
	    new ScenarioErrorCallback() : 
	      callbackInterface.isAssignableFrom(ODataDebugCallback.class) ? 
	        new ScenarioDebugCallback() : 
	        super.getCallback(callbackInterface));
	}
	
	
	private final class ScenarioErrorCallback implements ODataErrorCallback {
//		public boolean isDebugEnabled()
//		  { 
//		    return true; 
//		  }

		public ODataResponse handleError(ODataErrorContext context) throws ODataApplicationException
		{
			logger.error(context.getException().getClass().getName() + ":" + context.getMessage());
	        return EntityProvider.writeErrorDocument(context);
		}
	}
	
	private final class ScenarioDebugCallback implements ODataDebugCallback {
		  public boolean isDebugEnabled()
		  { 
		    return true; 
		  }
		}

}
