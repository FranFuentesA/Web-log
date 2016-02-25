import java.util.*;
/**
 * Read web server data and analyse
 * hourly access patterns.
 * 
 * @author David J. Barnes and Michael Kölling.
 * @version 2011.07.31
 */
public class LogAnalyzer
{
    // Where to calculate the hourly access counts.
    private int[] hourCounts;
    // Use a LogfileReader to access the data.
    private LogfileReader reader;

    private LogfileCreator nuevoArchivo;

    private boolean conAccesos;

    /**
     * Create an object to analyze hourly web accesses.
     */
    public LogAnalyzer()
    { 
        // Create the array object to hold the hourly
        // access counts.
        hourCounts = new int[24];
        // Create the reader to obtain the data.
        reader = new LogfileReader();
    }

    /**
     * Create an object to analyze hourly web accesses.
     */
    public LogAnalyzer(String nombreNuevoLog)
    { 
        hourCounts = new int[24];
        reader = new LogfileReader(nombreNuevoLog);

    }

    /**
     * Analyze the hourly access data from the log file.
     */
    public void analyzeHourlyData()
    {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int hour = entry.getHour();
            hourCounts[hour]++;
        }

    }

    public int numberOfAccesses()
    {
        int numeroDeAccesos = 0;
        int index = 0;
        while(index < hourCounts.length)
        {
            numeroDeAccesos =numeroDeAccesos + hourCounts[index];
            index++;
        }
        return numeroDeAccesos;
    }

    /**
     *
     */
    public int busiestHour()
    {
        int mayorNumero = -1;
        int  mayorNumeroDePeticiones = 0;
        for (int index = 0; index < hourCounts.length; index++) {

            if (hourCounts[index] >= mayorNumeroDePeticiones  ){
                mayorNumero = index;
                mayorNumeroDePeticiones = hourCounts[index];
            } 

        }
        if (mayorNumeroDePeticiones == 0) {
            mayorNumero = -1;
            System.out.println("No hay accesos");
        }
     
        return mayorNumero;
    }

    /**
     * Print the hourly counts.
     * These should have been set with a prior
     * call to analyzeHourlyData.
     */
    public void printHourlyCounts()
    {
        System.out.println("Hr: Count");
        int index = 0;
        while (hourCounts.length > index) {
            System.out.println(index + ": " + hourCounts[index]);
            index++;            
        }

    }

    /**
     * Print the lines of data read by the LogfileReader
     */
    public void printData()
    {
        reader.printData();
    }
}
