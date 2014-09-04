import java.util.Collections;
import java.util.List;


/**
 * Uses worst-fit decreasing algorithm to fit disks.
 * 
 * @author rcd
 */
public class WorstFitDecreasingAlgorithm extends WorstFitAlgorithm
{
    public static final String WORST_FIT_DECREASING_METHOD = "worst-fit decreasing method";


    /**
     * Default constructor
     */
    public WorstFitDecreasingAlgorithm ()
    {
        super(WORST_FIT_DECREASING_METHOD);
    }

    @Override
    protected void organizeData (List<Integer> data)
    {
        Collections.sort(data, Collections.reverseOrder());
    }
}
