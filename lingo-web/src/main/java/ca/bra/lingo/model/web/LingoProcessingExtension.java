package ca.bra.lingo.model.web;

import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmExtension;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmSchemaView;
import java.io.InputStream;
import ca.bra.lingo.model.function.impl.CustomerProcessor;


/**
 * 
 * Class for registering function imports.
 * 
 */
public class LingoProcessingExtension implements JPAEdmExtension {

	/**
	 * Register function imports.
	 */

	public void extendWithOperation(JPAEdmSchemaView view) {
		view.registerOperations(CustomerProcessor.class, null);
	}

	public void extendJPAEdmSchema(JPAEdmSchemaView arg0) {
	}
	
	public InputStream getJPAEdmMappingModelStream(){
		return null;
	}

}
