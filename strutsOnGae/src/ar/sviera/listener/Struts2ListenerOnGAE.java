package ar.sviera.listener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.opensymphony.xwork2.util.LocalizedTextUtil;

import ognl.OgnlRuntime;

public class Struts2ListenerOnGAE  implements ServletContextListener,
		HttpSessionListener, HttpSessionAttributeListener {
 
	private static final String DEFAULT_RESOURCE = "messages";
	
	public void contextInitialized(ServletContextEvent sce) {
		LocalizedTextUtil.addDefaultResourceBundle(DEFAULT_RESOURCE);
		OgnlRuntime.setSecurityManager(null);
	}
 
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
 
	}
 
	public void sessionCreated(HttpSessionEvent arg0) {
		// TODO Auto-generated method stub
 
	}
 
	public void sessionDestroyed(HttpSessionEvent arg0) {
		// TODO Auto-generated method stub
 
	}
 
	public void attributeAdded(HttpSessionBindingEvent arg0) {
		// TODO Auto-generated method stub
 
	}
 
	public void attributeRemoved(HttpSessionBindingEvent arg0) {
		// TODO Auto-generated method stub
 
	}
 
	public void attributeReplaced(HttpSessionBindingEvent arg0) {
		// TODO Auto-generated method stub
 
	}
}
