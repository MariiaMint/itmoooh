package beans;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;



@ApplicationScoped
public class DotCounter {

    @PersistenceContext
    private EntityManager em;

    public long countHits() {
        return em.createQuery("SELECT COUNT(r) FROM ResultBean r WHERE r.isHit = true", Long.class)
                .getSingleResult();
    }

    public long countMisses() {
        return em.createQuery("SELECT COUNT(r) FROM ResultBean r WHERE r.isHit = false", Long.class)
                .getSingleResult();
    }
}
