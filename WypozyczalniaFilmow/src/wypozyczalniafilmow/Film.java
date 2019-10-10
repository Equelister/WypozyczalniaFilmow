package wypozyczalniafilmow;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Comparator;


public class Film implements Serializable{
    
    
    public String nazwa;
    public String producent;
    public int rok;
    public int status = 0;

    
    // Konstruktor przyjmujÃ„â€¦cy 3 parametry
    public Film(String nazwa, String producent, int rok, int status)
    {
    this.nazwa = nazwa;
    this.producent = producent;
    this.rok = rok;
    this.status = status;
    }
    
    public String getNazwa() 
    {
        return nazwa;
    }
    public String getProducent() 
    {
        return producent;
    }
    public int getRok()
    {
        return rok;
    }
    public int getStatus()
    {
        return status;
    }
    
    public void edytujFilmNazwa(String newNazwa)
    {
        nazwa = newNazwa;
    }
    public void edytujFilmProducent(String newProducent)
    {
        producent = newProducent;
    }
    public void edytujFilmRok(int newRok)
    {
        rok = newRok;
    }   
    public void edytujStatusWypozyczcone()
    {
        status = 1;
    }
    public void edytujStatusDostepne()
    {
        status = 0;
    }
    
    
    public void pokazDane()
    {
    System.out.println(nazwa + " - " + producent + " - " + rok);
    }
    
    public void pokazDane2Naglowek()
    {
        String nazwaOut="Nazwa:";
        int k = nazwaOut.length();
        for(int i=k; i<62; i++)
        {
            nazwaOut = nazwaOut+" ";
        }
        String producentOut="Producent:";
        k = producentOut.length();
        for(int i=k; i<40; i++)
        {
            producentOut = producentOut+" ";
        }
        System.out.println(nazwaOut+" "+ producentOut + " "+ "Rok:");
    }
    
    public void pokazDane2()
    {
        
        String statusStr = null;
        String nazwaOut=nazwa;
        int k = nazwa.length();
        for(int i=k; i<60; i++)
        {
            nazwaOut = nazwaOut+" ";
        }
        String producentOut=producent;
        k = producent.length();
        for(int i=k; i<40; i++)
        {
            producentOut = producentOut+" ";
        }
        if(status==1)
        {
            statusStr = "Wypozyczone";
        }else if(status==0)
        {
            statusStr = "Dostepne";
        }
        
        System.out.println(nazwaOut+" "+ producentOut + " "+ rok+" "+statusStr);
    }
    
    public void pokazDane3()
    {
        System.out.println("Nazwa: "+nazwa);
        System.out.println("Producent: "+producent);
        System.out.println("Rok Wydania: "+rok);
    }

    @Override
    public String toString()
    {
        return this.nazwa+";"+this.producent+";"+this.rok;    
    }
    
    
    public static Comparator<Film> FilmNazwaComparator = new Comparator<Film>() {

        @Override
	public int compare(Film f1, Film f2) {
            
	   String nazwa1 = f1.getNazwa().toUpperCase();
	   String nazwa2 = f2.getNazwa().toUpperCase();
	   return nazwa1.compareToIgnoreCase(nazwa2);
    }};

    public static Comparator<Film> FilmProducentComparator = new Comparator<Film>() {

        @Override
	public int compare(Film f1, Film f2) {

	   String pr1 = f1.getProducent();
	   String pr2 = f2.getProducent();
	   return pr1.compareToIgnoreCase(pr2);
   }};
    
    
    
    
}


    

