import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;


/**
 * Runs a number of algorithms that try to fit files onto disks.
 */
public class Bins
{
    /**
     * Reads list of integer data from the given input.
     * 
     * @param input tied to an input source that contains space separated numbers
     * @return list of the numbers in the order they were read
     */
    public List<Integer> readData (Scanner input)
    {
        List<Integer> results = new ArrayList<Integer>();
        while (input.hasNext())
        {
            results.add(input.nextInt());
        }
        return results;
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

            PriorityQueue<Disk> pq = new PriorityQueue<Disk>();
            pq.add(new Disk(0));

            int diskId = 1;
            int total = 0;
            for (Integer size : data)
            {
                Disk d = pq.peek();
                if (d.freeSpace() > size)
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
                total += size;
            }

            System.out.println("total size = " + total / 1000000.0 + "GB");
            System.out.println();
            System.out.println("worst-fit method");
            System.out.println("number of pq used: " + pq.size());
            while (!pq.isEmpty())
            {
                System.out.println(pq.poll());
            }
            System.out.println();

            Collections.sort(data, Collections.reverseOrder());
            pq.add(new Disk(0));

            diskId = 1;
            for (Integer size : data)
            {
                Disk d = pq.peek();
                if (d.freeSpace() >= size)
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

            System.out.println();
            System.out.println("worst-fit decreasing method");
            System.out.println("number of pq used: " + pq.size());
            while (!pq.isEmpty())
            {
                System.out.println(pq.poll());
            }
            System.out.println();
        }
        catch (FileNotFoundException e)
        {
            System.err.println("Could not open file: " + args[0]);
        }
    }
}
