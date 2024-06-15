import beans.ResultBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;


@RunWith(Parameterized.class)
public class ResultBeanTest {

    private final double x;
    private final double y;
    private final double r;
    private final boolean ref;
    public ResultBeanTest(double x, double y, double r, boolean ref) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.ref = ref;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {1, 0, 1, true},
                {0, 1, 1, false},
                {-1, 0, 1, false},

                {-1, -1, 2, true},
                {-2, -3, 3, false},
                {0, -1, 1, true},

                {1, 0.5, 1, false},
                {0.5, 1, 1, false},
                {0, 1.5, 1, false}
        });
    }

    @Test
    public void test() {
        ResultBean resultBean = new ResultBean();
        resultBean.setX(x);
        resultBean.setY(y);
        resultBean.setR(r);
        assertEquals(resultBean.checkHit(), ref);
    }
}