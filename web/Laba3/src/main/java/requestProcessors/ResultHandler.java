package requestProcessors;

import beans.ResultBean;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import tools.MessageHandler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Named
@ApplicationScoped
public class ResultHandler implements Serializable {

	private final ResultBean result = new ResultBean();

	@Getter
	private final List<ResultBean> resultList = new ArrayList<>();

	private final List<Double> availableR = List.of(1.0, 2.0, 3.0, 4.0, 5.0);

	@Getter
	@Setter
	private Double x;
	@Getter
	@Setter
	private Double y;

	@Getter
	@Setter
	private Double hiddenX;

	@Getter
	@Setter
	private Double hiddenY;	@Getter

	@Setter
	private Double hiddenR;

	@Inject
	private DatabaseHandler databaseHandler;

	public void setResultParams() {
		Map<String, String> requestParameters = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String error = requestParameters.get("error");

		if (error != null) {
			MessageHandler.error(error);
			return;
		}
		ResultBean result = new ResultBean();
		System.out.println("x: " + x);
		System.out.println("y: " + y);
		System.out.println("hiddenX: " + hiddenX);
		System.out.println("hiddenY: " + hiddenY);
		System.out.println("hiddenR: " + hiddenY);
		try {
			if (x != null) {
				result.setX(x);
			} else {
				MessageHandler.error("Значение x не должно быть null.");
				return;
			}

			if (y != null) {
				result.setY(y);
			} else {
				result.setX(hiddenX);
				result.setY(hiddenY);
			}
			result.setR(hiddenR);

		} catch (NumberFormatException | NullPointerException e) {
			System.err.println(e);
			MessageHandler.error("Некорректное значение.");
			return;
		}
		saveResult(result);
	}

	private void saveResult(ResultBean result) {
		ResultBean newResult = databaseHandler.create(result);
		resultList.add(newResult);
	}

	public void clean() {
		resultList.clear();
		databaseHandler.deleteAll();
		MessageHandler.message(FacesMessage.SEVERITY_INFO, "Успех", "Все элементы успешно удалены");
	}

}
