import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.PriorityQueue;


/**
 * This class represents worst-fit algorithms for allocating files to disks.
 * 
 * @author rcd
 */
public class WorstFitAlgorithm
{
    public static final String WORST_FIT_METHOD = "worst-fit";
    private String myDescription;

    /**
     * Default constructor
     */
    public WorstFitAlgorithm ()
    {
        this(WORST_FIT_METHOD);
    }

    /**
     * Create with given descriptor.
     */
    public WorstFitAlgorithm (String description)
    {
        myDescription = description;
    }
   
    /**
     * Allocates given files to the fewest number of disks.
     * 
     * @param data collection of files to be allocated to disks
     */
    public void fitDisksAndPrint (List<Integer> data)
    {
        List<Integer> copy = new ArrayList<>(data);
        organizeData(copy);
        Collection<Disk> disks = addFiles(copy);
        printResults(disks, myDescription);
    }

    /**
     * Arrange given data in preparation for fitting disks.
     * 
     * @param disks collection of disks to be printed
     */
    protected void organizeData (List<Integer> data)
    {
        // by default, do nothing
    }

    // add files to the collection of Disks
    private Collection<Disk> addFiles (List<Integer> data)
    {
        PriorityQueue<Disk> pq = new PriorityQueue<>();
        int diskId = 1;
        for (Integer size : data)
        {
            Disk d = pq.peek();
            if (d != null && d.freeSpace() >= size)
            {
                pq.poll();
                d.add(size);
                pq.add(d);
            }
            else
            {
                Disk d2 = new Disk(diskId);
                diskId++;
                d2.add(size);
                pq.add(d2);
            }
        }
        return pq;
    }

    // print contents of given collection and a header, description
    private void printResults (Collection<Disk> disks, String description)
    {
        System.out.println();
        System.out.println("\n" + description + " method");
        System.out.println("number of disks used: " + disks.size());
        for (Disk d : disks)
        {
            System.out.println(d);
        }
    }
}
