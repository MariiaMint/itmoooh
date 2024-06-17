package beans;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;



@ApplicationScoped
public class DotCounter implements DotCounterMBean{
    private long hits;
    private long misses;

    @PersistenceContext
    private EntityManager em;



    public long countHits() {
        this.hits = em.createQuery("SELECT COUNT(r) FROM ResultBean r WHERE r.isHit = true", Long.class)
                .getSingleResult();
        return hits;
    }

    public long countMisses() {
        this.misses = em.createQuery("SELECT COUNT(r) FROM ResultBean r WHERE r.isHit = false", Long.class)
                .getSingleResult();
        return misses;
    }
}
