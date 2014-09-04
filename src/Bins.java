import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;


/**
 * Runs a number of algorithms that try to fit files onto disks.
 */
public class Bins
{
    public static final String WORST_FIT = "worst-fit";
    public static final String WORST_FIT_DECREASING = "worst-fit decreasing";


    /**
     * Reads list of integer data from the given input.
     * 
     * @param input tied to an input source that contains space separated numbers
     * @return list of the numbers in the order they were read
     */
    public List<Integer> readData (Scanner input)
    {
        List<Integer> results = new ArrayList<>();
        while (input.hasNext())
        {
            results.add(input.nextInt());
        }
        return results;
    }

    /**
     * Returns sum of all values in given list.
     */
    public int sum (Collection<Integer> data)
    {
        int total = 0;
        for (int d : data)
        {
            total += d;
        }
        return total;
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

    // add contents of given pq and a header, description
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

    // try the worst fit algorithm and also show the results
    private void addFilesAndPrintResults (List<Integer> data, String description)
    {
        List<Integer> copy = new ArrayList<>(data);
        if (description.equals(WORST_FIT_DECREASING))
        {
            Collections.sort(copy, Collections.reverseOrder());
        }
        Collection<Disk> disks = addFiles(data);
        printResults(disks, description);
    }


    /**
     * The main program.
     */
    public static void main (String args[])
    {
        Bins b = new Bins();
        try
        {
            Scanner input = new Scanner(new File(args[0]));
            List<Integer> data = b.readData(input);
            int total = b.sum(data);
            System.out.println("total size = " + total / 1000000.0 + "GB");

            b.addFilesAndPrintResults(data, WORST_FIT);
            b.addFilesAndPrintResults(data, WORST_FIT_DECREASING);
        }
        catch (FileNotFoundException e)
        {
            System.err.println("Could not open file: " + args[0]);
        }
    }
}
