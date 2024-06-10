package tools;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

public class MessageHandler {
	public static void message(FacesMessage.Severity severity, String summary, String detail) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
	}

	public static void error(String error) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", error));
	}
}
