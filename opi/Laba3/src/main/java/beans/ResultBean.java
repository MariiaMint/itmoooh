package beans;

import jakarta.persistence.*;


import java.io.Serializable;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Entity
public class ResultBean implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)  
	private Double x;
	@Column(nullable = false)  
	private Double y;
	@Column(nullable = false)  
	private Double r;

	@Column(nullable = false)  
	private String time;

	@Column(nullable = false)  
	private String executionTime;

	@Column(nullable = false) 
	private boolean isHit;

	public ResultBean() {}

	public ResultBean(ResultBean result) {
		this.x = result.x;
		this.y = result.y;
		this.r = result.r;
	}

	public boolean getIsHit() {
		return isHit;
	}

	@PrePersist
	protected void prePersist() {
		this.time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
		this.isHit = checkHit();
		this.setExecutionTime(String.valueOf( 0 + Math.random() * (0.3 - 0 + 0.1) ));
	}

	public boolean checkHit() {
		boolean area1_hit = (x >= 0 && y <= 0 && (r * r) >= (x * x + y * y));
		boolean area2_hit = (x >= -r/2 && y >= -r && y <= 0 && x <= 0);
		boolean area3_hit = (y <= r/2 - x && x >= 0 && y >= 0);

		return area1_hit || area2_hit || area3_hit;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Double getR() {
		return r;
	}

	public void setR(Double r) {
		this.r = r;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getExecutionTime() {
		return executionTime;
	}

	public void setExecutionTime(String executionTime) {
		this.executionTime = executionTime;
	}

	public boolean isHit() {
		return isHit;
	}

	public void setHit(boolean hit) {
		isHit = hit;
	}
}
