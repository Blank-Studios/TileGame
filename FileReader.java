import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * Break the lines up into all the stats.
 * 
 * @author ???
 * @version Will Robertson
 * 
 */
public class FileReading
{

    private Scanner fileIn = null;

    /**
     * Constructor reads from filename.
     * 
     * @param filename
     *            The name of the file.
     */
    public FileReading(String filename)
    {
        // open the input file
        try
        {
            setFileIn(new Scanner(new FileReader(filename)));
        }
        catch (IOException ioe)
        {
            System.out.println("Could not open the input file." + filename);
            System.exit(0);
        }

        // read the data
        readLines();

        // close the input file
        fileIn.close();
    }

    /**
     * Sets the input scanner.
     * 
     * @param input
     *            The input scanner.
     */
    public void setFileIn(Scanner input)
    {
        fileIn = input;
    }

    /**
     * Read lines of the file.
     */
    public void readLines()
    {
        String[] stats;
        String Name;
        String Class;
        int Level;
        int Life;
        int Attack;
        int Defense;
        int Spirit;
        int Resistance;
        int Speed;
        int Critical;
        int Move;

        // as long as there are lines to read
        while (fileIn.hasNextLine())
        {
            // read a line from the file and split it
            // into an array of Strings around the commas
            stats = fileIn.nextLine().split(",");
            // put the data before the first comma in petName
            Name = stats[0];
            Class = stats[1];
            Level = Integer.parseInt(stats[2]);
            // put the data before the second comma in petAge
            // after converting the String to an integer
            Attack = Integer.parseInt(stats[3]);
            // put the data before the third comma in petWeight
            // after converting the String to a Double
            Defense = Integer.parseInt(stats[4]);
            Spirit = Integer.parseInt(stats[5]);
            Resistance = Integer.parseInt(stats[6]);
            Speed = Integer.parseInt(stats[7]);
            Critical = Integer.parseInt(stats[8]);
            Move = Integer.parseInt(stats[9]);
        }
    }

    /**
     * The main program to read from pets.txt.
     * 
     * @param args
     *            unused.
     */
    public static void main(String[] args)
    {
        new FileReading("pets.txt");
    }

}
