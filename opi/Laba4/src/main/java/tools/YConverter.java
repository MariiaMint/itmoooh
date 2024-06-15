package tools;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.ConverterException;
import jakarta.faces.convert.FacesConverter;

import java.util.regex.Pattern;


@FacesConverter(value = "tools.YCoordinateConverter", managed = true)
public class YConverter implements Converter<Double> {
	@Override
	public Double getAsObject(FacesContext facesContext, UIComponent uiComponent, String x) {
		if (isAutoFill(uiComponent)) {
			return null; // Если значение пришло из автозаполненного поля, возвращаем null
		}

		if (x == null || x.isEmpty()) {
			return null;
		} else {
			if (!Pattern.matches("(?:-3|\\+?3)(?:[.,]0{1,30})?|(?:-[43210]|\\+?[012])(?:[.,]\\d{1,30})?", x)) {
				throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "X: Значение не соответствует шаблону.", ""));
			}
			return Double.parseDouble(x.replace(",", "."));
		}
	}

	private boolean isAutoFill(UIComponent uiComponent) {
		return uiComponent.getId() != null && uiComponent.getId().startsWith("hidden");
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Double x) {
		if (x == null) {
			return "";
		} else {
			return x.toString();
		}
	}
}
