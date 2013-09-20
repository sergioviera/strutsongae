package ar.sviera.util;

import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.brickred.socialauth.AuthProvider;
import org.brickred.socialauth.Profile;
import org.brickred.socialauth.SocialAuthConfig;
import org.brickred.socialauth.SocialAuthManager;
import org.brickred.socialauth.util.OAuthConfig;

import ar.sviera.data.PMF;
import ar.sviera.domain.Statistics;
import ar.sviera.domain.User;


public class UserUtil {
	private static final Logger log = Logger.getLogger(UserUtil.class.getName());

	public static String login(String id) {
		String result = null;
		if(!isAuthenticated()){
			try{
				//load configuration. By default load the configuration from oauth_consumer.properties. 
				SocialAuthConfig config = SocialAuthConfig.getDefault();
				config.load();
	
				//Create an instance of SocialAuthManager and set config
				SocialAuthManager manager = new SocialAuthManager();
				manager.setSocialAuthConfig(config);
				
				OAuthConfig fbConfig = manager.getSocialAuthConfig().getProviderConfig(id);
				fbConfig.setCustomPermissions( "email,user_location" );
	
				// id can have values "facebook", "twitter", "yahoo" etc. or the OpenID URL
				//URL of YOUR application which will be called after authentication
				result  = manager.getAuthenticationUrl(id, "http://angryindec.appspot.com/doSetSocialUser");
//				result  = manager.getAuthenticationUrl(id, "http://dev.angryindec.appspot.com:8888/doSetSocialUser");
				
				// Store in session
				ServletActionContext.getRequest().getSession(true).setAttribute("authManager", manager);
							
			}catch (Exception e){
				log.severe("Error al Autenticar: "+e.getMessage());
			}
		}else{
			findCurrentAuthProvider();
		}
		return result;
	}

    public static boolean isAuthenticated(){
    	HttpServletRequest request = ServletActionContext.getRequest();
		SocialAuthManager manager = (SocialAuthManager)request.getSession().getAttribute("authManager");

		return (manager != null) && (manager.getConnectedProvidersIds().size() > 0);
    }
    
    public static SocialAuthManager getSocialAuthManager(){
    	// get the auth provider manager from session
		return (SocialAuthManager)ServletActionContext.getRequest().getSession().getAttribute("authManager");
    }

	public static AuthProvider findCurrentAuthProvider(){
		AuthProvider provider = null;

		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			  // get the auth provider manager from session
			  SocialAuthManager manager = (SocialAuthManager)request.getSession().getAttribute("authManager");
			  
			  if( manager != null ){
				  provider = manager.getCurrentAuthProvider();
				  
				  if( provider == null ){
					  // call connect method of manager which returns the provider object. 
					  // Pass request parameter map while calling connect method.
					  Map<String,String> params = new HashMap<String,String>();
					  Enumeration<String> enumer = request.getParameterNames();
					  while( enumer.hasMoreElements() ){
						  String paramName = enumer.nextElement();
						  params.put( paramName, request.getParameter(paramName) );
					  }  
					  
					  provider = manager.connect(params);

						Profile profile = provider.getUserProfile();
						String email = profile.getCountry();
				  }else{
						Profile profile = provider.getUserProfile();
						String email = profile.getCountry();
				  }
			  }
		} catch (Exception e) {
			String msg = "Exception occurred while getting social AuthProvider";
			log.severe( msg );
		}
		
		return provider;
	}

    
	public static void saveUserData(String product, Float productPrice, double lat, double lon) {
    	HttpServletRequest request = ServletActionContext.getRequest();
		try {
			AuthProvider provider = (AuthProvider) request.getSession().getAttribute("authProvider");	
			
			if( provider != null ){
				String providerId = provider.getProviderId();
				Profile profile = provider.getUserProfile();
				if( (profile != null) && providerId!=null && !providerId.isEmpty() ){
					saveStatistics(product, productPrice, profile);
				}
			}

		}catch(Exception e){
			log.severe("Error al guardar el precio ingresado por el usuario: "+e.getLocalizedMessage());
		}		
	}
	
	public static boolean userExists(String id){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		Query query = pm.newQuery(User.class);
		query.setFilter("validatedId==:i");
	    Object res = query.execute(id);
	    pm.close();
	    return (res != null);
	}
	
	public static void loadSocialUser() {
		try {
			HttpSession session = ServletActionContext.getRequest().getSession(false);
			AuthProvider provider = findCurrentAuthProvider();	
			
			if( provider != null ){
				
				String providerId = provider.getProviderId();
				Profile profile = provider.getUserProfile();
				
				if( (profile != null) && providerId!=null && !providerId.isEmpty() && userExists(profile.getProviderId())){
					saveNewUser(profile);
				}
		
				session.setAttribute("authProvider", provider);
			}
		} catch (Exception e) {
			log.severe( "Exception occurred while login: "+e.getLocalizedMessage() );
		}	
	}
	
	private static void saveNewUser(Profile profile) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		User usr = new User();
		usr.setProviderId( profile.getProviderId() );
		usr.setValidatedId( profile.getValidatedId() );
		usr.setFullName( profile.getFullName() );
		usr.setFirstName( profile.getFirstName() );
		usr.setDisplayName( profile.getDisplayName() );
		usr.setLastName( profile.getLastName() );
		usr.setProfileImageURL( profile.getProfileImageURL() );
		usr.setCountry( profile.getCountry() );
		usr.setDateOfBirth( profile.getDob()==null?"":profile.getDob().toString() );
		usr.setEmail( profile.getEmail() );
		usr.setGender( profile.getGender() );
		usr.setLanguage( profile.getLanguage() );
		usr.setLocation( profile.getLocation() );
		
		pm.makePersistent(usr);
	}


	private static void saveStatistics(String product, Float productPrice, Profile profile) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		Statistics sts = new Statistics();
		sts.setUserEmail( profile.getValidatedId() );
		sts.setLocation( profile.getLocation() );
		sts.setProduct( product );
		sts.setDate(new Date());
		sts.setPrice( productPrice );
		
		pm.makePersistent(sts);
	}

	public static void logout() {
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpSession session = request.getSession();
			SocialAuthManager manager = (SocialAuthManager)session.getAttribute("authManager");

			if( manager != null ){
				List<String> connectedProviders = manager.getConnectedProvidersIds();

				for(String provId: connectedProviders){
					manager.disconnectProvider( provId );
				}	
			}

			session.removeAttribute("authProvider");
		} catch (Exception e) {
			log.severe( "Exception occurred while logout Facebook "+e.getMessage() );
		}	
	}

}
