import java.util.ArrayList;
import java.util.List;


/**
 * Represents a collection of files; how many it can hold is limited by its capacity.
 */
public class Disk implements Comparable<Disk>
{
    private int myId;
    private int mySize;
    private int myCapacity;
    private List<Integer> myFiles;

    /**
     * Create an empty Disk.
     */
    public Disk ()
    {
        mySize = 0;
        myCapacity = 1000000;
        myFiles = new ArrayList<Integer>();
    }

    /**
     * Create an empty Disk with the given ID.
     */
    public Disk (int id)
    {
        myId = id;
        mySize = 0;
        myCapacity = 1000000;
        myFiles = new ArrayList<Integer>();
    }

    /**
     * @return amount of free space available on this disk
     */
    public int freeSpace ()
    {
        return myCapacity - mySize;
    }

    /**
     * Adds the given file to this disk.
     * 
     * No error checking is done to ensure the file can fit on this disk.
     * 
     * @param filesize size of file to add to this disk
     */
    public void add (int filesize)
    {
        myFiles.add(filesize);
        mySize += filesize;
    }

    /**
     * Converts this disk's information to a string.
     */
    @Override
    public String toString ()
    {
        String result = myId + "\t" + freeSpace() + ":\t";
        for (int k = 0; k < myFiles.size(); k++)
        {
            result += " " + myFiles.get(k);
        }
        return result;
    }

    /**
     * Check if this disk's information is the same as the given disk's.
     * 
     * Note, currently only equality is based only on their ID.
     * 
     * @param other disk to compare to this disk
     * @return true iff their values match
     */
    @Override
    public boolean equals (Object other)
    {
        if (other != null && other instanceof Disk)
        {
            if (myId == ((Disk) other).myId)
                return true;
            else 
                return false;
        }
        else 
            return false;
    }

    /**
     * Compare this disk's information to the given disk's for ordering.
     * 
     * Note, currently only equality is based only on their free space.
     * 
     * @param other disk to compare to this disk
     * @return positive if this disk is greater than the given disk,
     *         zero if they are equal, and
     *         negative if this disk is less than the given disk
     */
    @Override
    public int compareTo (Disk other)
    {
        if (other != null)
        {
            int result = other.freeSpace() - freeSpace();
            if (result == 0)
                return myId - other.myId;
            else 
                return result;
        }
        else 
            return -1;
    }
}
