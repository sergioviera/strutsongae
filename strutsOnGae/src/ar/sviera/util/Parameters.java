package ar.sviera.util;

import java.text.SimpleDateFormat;

public class Parameters {
	
	public static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@"+
         										"[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	public static final String URL_PATTERN = "^[0-9A-Za-z._-]{0,100}$";
	
	public static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
}
