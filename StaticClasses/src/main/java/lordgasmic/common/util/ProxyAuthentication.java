package lordgasmic.common.util;

import java.net.Authenticator;  
import java.net.PasswordAuthentication;

public class ProxyAuthentication extends Authenticator {  
	
	private String user, password;  

	/**
	 * ProxyAuthentication <br /> <br />
	 * 
	 * Allow the user to run internet based
	 * applications from behind a proxy
	 * 
	 * @param user user
	 * @param password password
	 */
	public ProxyAuthentication(String user, String password) {  
		this.user = user;  
		this.password = password;  
		
		System.setProperty("http.proxyHost", "proxy.gfs.com");  
		System.setProperty("http.proxyPort", "8080");
	}  

	protected PasswordAuthentication getPasswordAuthentication() {  
        return new PasswordAuthentication(user, password.toCharArray());  
    }  
}