package requestProcessors;

import beans.ResultBean;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import tools.MessageHandler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Named
@ApplicationScoped
public class ResultHandler implements Serializable {

	private final ResultBean result = new ResultBean();

	
	private final List<ResultBean> resultList = new ArrayList<>();

	private final List<Double> availableR = List.of(1.0, 2.0, 3.0, 4.0, 5.0);

	
	
	private Double x;
	
	
	private Double y;

	
	
	private Double hiddenX;

	
	
	private Double hiddenY;	

	
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

	public ResultBean getResult() {
		return result;
	}

	public List<ResultBean> getResultList() {
		return resultList;
	}

	public List<Double> getAvailableR() {
		return availableR;
	}

	public Double getX() {
		return x;
	}

	public void setX(Double x) {
		this.x = x;
	}

	public Double getY() {
		return y;
	}

	public void setY(Double y) {
		this.y = y;
	}

	public Double getHiddenX() {
		return hiddenX;
	}

	public void setHiddenX(Double hiddenX) {
		this.hiddenX = hiddenX;
	}

	public Double getHiddenY() {
		return hiddenY;
	}

	public void setHiddenY(Double hiddenY) {
		this.hiddenY = hiddenY;
	}

	public Double getHiddenR() {
		return hiddenR;
	}

	public void setHiddenR(Double hiddenR) {
		this.hiddenR = hiddenR;
	}

	public DatabaseHandler getDatabaseHandler() {
		return databaseHandler;
	}

	public void setDatabaseHandler(DatabaseHandler databaseHandler) {
		this.databaseHandler = databaseHandler;
	}
}
