package ar.sviera.action;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.brickred.socialauth.AuthProvider;
import org.brickred.socialauth.Profile;
import org.brickred.socialauth.SocialAuthConfig;
import org.brickred.socialauth.SocialAuthManager;
import org.brickred.socialauth.util.OAuthConfig;

import ar.sviera.util.UserUtil;

import com.opensymphony.xwork2.ActionSupport;

public class Login extends ActionSupport {
	private static final Logger log = Logger.getLogger(Login.class.getName());
	private String socialURL = "";
	private String id;

	public String getSocialURL() {
		return socialURL;
	}

	public void setSocialURL(String socialURL) {
		this.socialURL = socialURL;
	}

	@Override
	public String execute(){
		return SUCCESS;
	}
	
	public String login(){
		this.socialURL = UserUtil.login(id);
		return SUCCESS;
	}
	
	public String loadSocialUser(){
		UserUtil.loadSocialUser();
		return SUCCESS;
		
	}

	public String logout(){
		UserUtil.logout();
		return SUCCESS;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

}