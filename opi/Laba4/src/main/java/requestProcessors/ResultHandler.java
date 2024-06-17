package requestProcessors;

import beans.DotCounter;
import beans.PercentCounter;
import beans.ResultBean;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import tools.MessageHandler;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.io.Serializable;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Named
@ApplicationScoped
public class ResultHandler implements Serializable {

	private final ResultBean result = new ResultBean();
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
	private Double hiddenY;
	@Getter
	@Setter
	private Double hiddenR;

	@Inject
	private DatabaseHandler databaseHandler;

	@Inject
	private DotCounter counter;

	@Getter
	private Integer hits;
	@Getter
	private Integer misses;

	@PostConstruct
	public void init() {
		try {
			MBeanServer mbeanServer = ManagementFactory.getPlatformMBeanServer();

			// Регистрация PercentCounter MBean
			ObjectName percentCounterName = new ObjectName("beans:type=PercentCounter");
			PercentCounter percentCounter = new PercentCounter();
			mbeanServer.registerMBean(percentCounter, percentCounterName);

			// Регистрация DotCounter MBean
			ObjectName dotCounterName = new ObjectName("beans:type=DotCounter");
			mbeanServer.registerMBean(counter, dotCounterName);

			System.out.println("MBeans registered successfully.");
		} catch (Exception e) {
			System.out.println("MBeans registered not successfully.");
			e.printStackTrace();
		}
	}


	public void setResultParams() {
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

		result.setIsHit(result.checkHit());
		saveResult(result);
		hits = (int) counter.countHits();
		misses = (int) counter.countMisses();

		if (!result.checkHit()) {
			MessageHandler.message(FacesMessage.SEVERITY_INFO, "Количество промахов увеличилось", String.valueOf(misses));
		}

		Map<String, String> requestParameters = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String error = requestParameters.get("error");

		if (error != null) {
			MessageHandler.error(error);
			return;
		}
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

	public List<Summary> getSummaryList() {
		List<Summary> summaryList = new ArrayList<>();
		PercentCounter percent = new PercentCounter();
		summaryList.add(new Summary(hits, misses, percent.countPercent(hits,misses)));
		return summaryList;
	}

	@Getter
	@Setter
	public static class Summary {
		private Integer hits;
		private Integer misses;
		private Double percent;


		public Summary(Integer hits, Integer misses, Double percent) {
			this.hits = hits;
			this.misses = misses;
			this.percent = percent;
		}
	}
}
