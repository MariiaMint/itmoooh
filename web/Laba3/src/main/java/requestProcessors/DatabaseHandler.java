package requestProcessors;

import beans.ResultBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.io.Serializable;
import java.util.List;

@Stateless
public class DatabaseHandler implements Serializable {

	@PersistenceContext
	private EntityManager db;

	public ResultBean create(ResultBean result) {
		ResultBean newResult = new ResultBean(result);
		db.persist(newResult);
		return newResult;
	}

	public List<ResultBean> findAll() {
		return db.createQuery("from ResultBean", ResultBean.class).getResultList();
	}


	public void deleteAll() {
		Query query = db.createQuery("DELETE FROM ResultBean");
		query.executeUpdate();
	}
}
