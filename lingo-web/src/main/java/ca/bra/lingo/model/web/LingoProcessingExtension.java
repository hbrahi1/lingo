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

	@Override
	public void extendWithOperation(JPAEdmSchemaView view) {
		view.registerOperations(CustomerProcessor.class, null);
	}

	@Override
	public void extendJPAEdmSchema(JPAEdmSchemaView arg0) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public InputStream getJPAEdmMappingModelStream(){
		// TODO Auto-generated method stub
		return null;
	}

}
