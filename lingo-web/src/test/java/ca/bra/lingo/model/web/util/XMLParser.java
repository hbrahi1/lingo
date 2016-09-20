package ca.bra.lingo.model.web.util;

import java.io.InputStream;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.events.XMLEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * StAX Parser Implementation
 * 
 */
public class XMLParser {
	static final String ENTRY = "entry";
	static final String CUSTOMER_REVIEW_ID = "CustomerReviewId";
	static final String COMMENT = "Comment";
	static final String RATING = "Rating";
	static final String PRODUCT_ID = "ProductId";
	static final String DATE_OF_CREATION = "CreationDate";
	static final String FIRST_NAME = "FirstName";
	static final String LAST_NAME = "LastName";
	static Logger logger = LoggerFactory.getLogger(XMLParser.class);

	private InputStream in = null;
	private XMLEventReader eventReader = null;
	protected Boolean status;

	public String getEvent(XMLEvent event) {
		if (event.isCharacters()) {
			return event.asCharacters().getData();
		} else {
			return null;
		}
	}

}
