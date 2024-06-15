package beans;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import static java.util.Objects.isNull;

@ApplicationScoped
public class PercentCounter {
    @PersistenceContext
    private EntityManager em;

    public Double countPercent(Integer hits, Integer misses) {
        if (hits != null && misses != null) {
            return (double) hits / (hits + misses) * 100;
        }
        return 0.0;
    }


}
