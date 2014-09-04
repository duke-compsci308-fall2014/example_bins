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
    private void addFiles (List<Integer> data, PriorityQueue<Disk> pq)
    {
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
    }

    // add contents of given pq and a header, description
    private void printResults (PriorityQueue<Disk> pq, String description)
    {
        System.out.println();
        System.out.println("\n" + description + " method");
        System.out.println("number of disks used: " + pq.size());
        PriorityQueue<Disk> copy = new PriorityQueue<>(pq);
        while (!copy.isEmpty())
        {
            System.out.println(copy.poll());
        }
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

            PriorityQueue<Disk> pq = new PriorityQueue<>();
            b.addFiles(data, pq);
            b.printResults(pq, WORST_FIT);
            List<Integer> copy = new ArrayList<>(data);
            Collections.sort(copy, Collections.reverseOrder());
            b.addFiles(copy, pq);
            b.printResults(pq, WORST_FIT_DECREASING);
        }
        catch (FileNotFoundException e)
        {
            System.err.println("Could not open file: " + args[0]);
        }
    }
}
