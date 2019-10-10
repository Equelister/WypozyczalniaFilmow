package wypozyczalniafilmow;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author micha
 */
public class Menu {
    
        static String sciezkaBinarny = "WypozyczalniaFilmowProjekt\\dane\\plikbinarny.bin";
    
        public static void enter()
    {
        System.out.println("\nWcisnij 'Enter'...\n");
        try {
            System.in.read();
        } catch (IOException ex) {
            Logger.getLogger(WypozyczalniaFilmow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    
    public static void sortBinarnyAlfabetNazwa() throws IOException
    {
        ArrayList<Film> lista = new ArrayList<Film>();
        lista = FilesInputOutput.odczytBinarnyDoListy();
        Collections.sort(lista, Film.FilmNazwaComparator);
        for(int i=0; i<lista.size(); i++)
        {
            FilesInputOutput.zapisBinarny(lista.get(i),"WypozyczalniaFilmowProjekt\\dane\\plikbinarny123.bin");
        }
        File inputFile = new File("WypozyczalniaFilmowProjekt\\dane\\plikbinarny.bin");
        File tempFile = new File("WypozyczalniaFilmowProjekt\\dane\\plikbinarny123.bin");
        inputFile.delete();
        tempFile.renameTo(inputFile); 
        lista.clear();
    }        
    
    
    public static void sortBinarnyAlfabetProducent() throws IOException
    {
        ArrayList<Film> lista = new ArrayList<Film>();
        lista = FilesInputOutput.odczytBinarnyDoListy();
        Collections.sort(lista, Film.FilmProducentComparator);
        for(int i=0; i<lista.size(); i++)
        {
            FilesInputOutput.zapisBinarny(lista.get(i),"WypozyczalniaFilmowProjekt\\dane\\plikbinarny123.bin");
        }
        File inputFile = new File("WypozyczalniaFilmowProjekt\\dane\\plikbinarny.bin");
        File tempFile = new File("WypozyczalniaFilmowProjekt\\dane\\plikbinarny123.bin");
        inputFile.delete();
        tempFile.renameTo(inputFile); 
        lista.clear();
    }


    
    

    }
    
    
    
    
    
    
    
    

