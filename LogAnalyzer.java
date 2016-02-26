import java.util.*;
/**
 * Read web server data and analyse
 * hourly access patterns.
 * 
 * @author David J. Barnes and Michael KÃ¶lling.
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

    private int[] dayCounts;

    /**
     * Create an object to analyze hourly web accesses.
     */
    public LogAnalyzer()
    { 
        // Create the array object to hold the hourly
        // access counts.
        hourCounts = new int[24];

        dayCounts = new int[31] ;
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
        dayCounts = new int[31] ;

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

    public int busiestTwoHour()
    {
        int horaInicialPeriodoConMasPeticiones = -1;
        int mayorNumeroDePeticiones = 0;

        for(int hora = 0; hora < hourCounts.length; hora++) {
            int sumaDePeticionesDeDosHoras = hourCounts[hora] + hourCounts[(hora + 1) % 24];
            if (mayorNumeroDePeticiones <= sumaDePeticionesDeDosHoras) {
                mayorNumeroDePeticiones = sumaDePeticionesDeDosHoras;
                horaInicialPeriodoConMasPeticiones = hora;
            }
        }

        if (horaInicialPeriodoConMasPeticiones == -1) {
            System.out.println("No ha habido accesos");
        }

        return horaInicialPeriodoConMasPeticiones;      
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

    /**
     * Metodo que devuelve la hora que menos gente tiene el server
     * si no se analiza se devuelve -1
     */
    public int quietestHour()
    {
        //Guardamos la hoa con menor numero de peticiones
        int horaConMenosPeticiones = 0;
        //Guardamos aqui el menor numero de peticiones hasta el momento
        int menorNumeroDePeticiones = hourCounts[0];

        if (numberOfAccesses() == 0) {
            horaConMenosPeticiones = -1;
            System.out.println("No ha habido accesos");
        }
        else {
            //Recorro el array donde estan guardados los accesos por hora
            for(int hora = 1; hora < hourCounts.length; hora++) {
                //Por cada elemento, si el numero de peticiones de esa hora es menor
                //que el menor numero de peticiones que tenemos registrado...
                if (menorNumeroDePeticiones >= hourCounts[hora]) {
                    //Ponemos como menor numero de peticiones las de esta hora
                    menorNumeroDePeticiones = hourCounts[hora];
                    //Guardamos esta hora como la que menos peticiones ha tenido
                    horaConMenosPeticiones = hora;
                }
            }
        }
               return horaConMenosPeticiones; 
    }

    /** Analyze the hourly accesses only in the given date
     *
     * @param day   The given day
     * @param month The given month
     * @param year  The given year
     */
    public void analizarAccesosPorHoraDeUnDia(int day, int month, int year)
    {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            if ((entry.getYear() == year) && 
            (entry.getMonth() == month) && 
            (entry.getDay() == day))
            {
                int hour = entry.getHour();
                hourCounts[hour]++;
            }
        }      
    }

    /**
     * Analiza el archivo de log contando los accesos por dias
     */
    public void analyzeDailyData() 
    {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int dia = entry.getDay(); 
            dayCounts[dia-1]++;
        }

    }

    /**
     * Imprime los accesos que ha habido cada día
     */
    public void printDailyCounts ()
    {
        for(int index= 0; dayCounts.length > index; index++){
            System.out.println("Dia" + (index + 1) + " : "  + dayCounts[index]);
        }
    }

}
