package customtags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class HelloTag extends SimpleTagSupport {
	
	boolean afficheMessage;
	
	String message;
	
	
	 public void doTag() throws JspException, IOException {
	      JspWriter out = getJspContext().getOut();
	      if (afficheMessage) {
	      	out.println(message);
	      }
	   }


	public boolean isAfficheMessage() {
		return afficheMessage;
	}


	public void setAfficheMessage(boolean afficheMessage) {
		this.afficheMessage = afficheMessage;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}
	 
	
	 
}
