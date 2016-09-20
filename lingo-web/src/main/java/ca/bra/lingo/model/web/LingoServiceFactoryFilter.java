package ca.bra.lingo.model.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.olingo.odata2.api.exception.ODataException;

public class LingoServiceFactoryFilter implements Filter{

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {

			if (request instanceof HttpServletRequest) {

				HttpServletRequest oCntxt = (HttpServletRequest) request;
				String url = oCntxt.getRequestURI().toString();
				if (!url.contains("/secure/")) {
					if (isPathRestricted(oCntxt)) {
						HttpServletResponse httpResp = (HttpServletResponse) response;
						httpResp.sendError(HttpServletResponse.SC_UNAUTHORIZED,
								"Access denied to the secure entity, use secure url /lingo.svc/secure");
					} else {
						chain.doFilter(request, response);
					}
				} else {
					chain.doFilter(request, response);
				}
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}

	/**
	 * Checks if triggered path is restricted for anonymous users
	 * 
	 * @param oCntxt
	 *            OData Context
	 * @return true if path is restricted else false
	 * @throws ODataException
	 */
	private boolean isPathRestricted(HttpServletRequest oCntxt)
			throws ODataException {

		boolean status;
		String path = oCntxt.getRequestURI().toString();
		if ((path.contains("/SalesOrderHeaders") || path.contains("/Customers") || path
				.contains("/SalesOrderItems"))
				&& (oCntxt.getMethod().equals("GET") || oCntxt.getMethod()
						.equals("DELETE"))) {
			status = true;
		} else if (path.contains("/PurchaseOrderHeaders")
				|| path.contains("/PurchaseOrderItems")
				|| path.contains("/Suppliers") || path.contains("/Stocks")) {
			status = true;
		} else if ((path.contains("/Products") || path
				.contains("/ProductCategories"))
				&& (oCntxt.getMethod().equals("POST")
						|| oCntxt.getMethod().equals("PUT") || oCntxt
						.getMethod().equals("DELETE"))) {
			status = true;
		} else if ((path.contains("/ConfirmSalesOrder") || path
				.contains("/CancelSalesOrder"))) {
			status = true;
		} else {
			status = false;
		}
		return status;
	}

	public void init(FilterConfig arg0) throws ServletException {
	}

}
