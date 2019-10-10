package wypozyczalniafilmow;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author micha
 */
public class MenuUser extends Menu {
    
    public static void menu(Account account) throws IOException, FileNotFoundException, ClassNotFoundException
    {
        
        File file = new File("WypozyczalniaFilmowProjekt\\users\\borrowed\\"+account.login+".dat");
        file.createNewFile();
        
        
        int menuWybor = -1;
        do {
            
            enter();
            System.out.println("Witaj "+account.getLogin());
            System.out.println("Witaj w menu uzytkownika.\n\n");
            menuSystemOutPrintUser();
            
            try{
                menuWybor = Validator.inInt();
                }catch(Exception ex)
                {
                    System.out.println("Błąd! wpisz liczbe");
                    continue;
                }
            
            switch (menuWybor) {
                case 1:
                {
                    przegladajFilmy();
                    break;
                }
                
                case 2:
                {
                    szukajFilmu(account);
                    break;
            
                }
                
                case 3:
                {
                    szukajProducent(account);
                    break;
                }
                
                case 4:
                {
                    sortBinarnyAlfabetNazwa();
                    break;
                }
                
                case 5:
                {
                    sortBinarnyAlfabetProducent();
                    break;
                }
                case 6:
                {
                    wypozyczFilm(account);
                    break;
                }
                
                case 7:
                {
                    zwrotFilm(account);
                    break;
                }
                
                case 8:
                {
                    wypozyczonyFilm(account);
                    break;
                }
                
                case 0:
                {
                menuWybor = 0;
                break;
                }
            }
        } while (menuWybor != 0);    
    }
    
    public static void wypozyczenie(ArrayList<Film> lista, int wybor, Account account) throws IOException
    {
                    boolean test = Validator.checkIfUserFileIsEmpty(account);
                    if(test==true)
                    {
                    FilesInputOutput.odczytBinarnyDoZmianystatusu(lista.get(wybor));    
                    FilesInputOutput.wypozyczenieBinarnyZapisz(lista.get(wybor), account);
                    }
                    else
                    {
                        System.out.println("Juz wypozyczyles inny film.");
                    }        
    }
    
    public static int czyChceszWypozyczyc()
    {
                            System.out.println("Czy chcesz wypozyczyc ktorys film?\n1.Tak\n2.Nie");
                    int wybor = 0;
                    do{
                        wybor = Validator.inInt();
                    }while(wybor!=1 && wybor!=2);
                    return wybor;
    }
    
    public static void menuSystemOutPrintUser()
    {
            System.out.println();
            System.out.println("1.Przegladaj filmy.");
            System.out.println("2.Szukaj filmu.");
            System.out.println("3.Szukaj producenta.");
            System.out.println("4.Sortuje nazwy alfabetycznie.");
            System.out.println("5.Sortuj producentow alfabetycznie.");
            System.out.println("6.Wypozycz film.");
            System.out.println("7.Oddaj obecnie wypozyczony film.");
            System.out.println("8.Nazwa obecnie wypozyczonego filmu.");
            System.out.println("0.Wyjscie z programu.");
    }
    
    
    public static ArrayList<Film> szukanieNazwa(ArrayList<Film> filmLista, String szukane, Account account) throws IOException 
    {
        ArrayList<Film> tempFilmLista = new ArrayList<Film>();
        ArrayList<Film> tempFilmLista1 = new ArrayList<Film>();

    //////////////    Collections.sort(filmLista, Film.FilmNazwaComparator);
        Collections.sort(tempFilmLista, Film.FilmNazwaComparator);
        for (Film f : filmLista)
        {
            if (f.nazwa.toLowerCase().contains(szukane.toLowerCase()))
            {
                tempFilmLista.add(f);
            }
            else
            {
                tempFilmLista1.add(f);
            }
        }
        if(tempFilmLista.size()>0)
        {
            pokazListeFilm(tempFilmLista);
            return tempFilmLista;
        }
        else
        {
            System.out.println("Brak filmow zawierajacych podana fraze.");
        }
        return null;
    }
    
        public static ArrayList<Film> szukanieProducent(ArrayList<Film> filmLista, String szukane, Account account) throws IOException 
    {
        ArrayList<Film> tempFilmLista = new ArrayList<Film>();
        ArrayList<Film> tempFilmLista1 = new ArrayList<Film>();

    //////////////    Collections.sort(filmLista, Film.FilmNazwaComparator);
        Collections.sort(tempFilmLista, Film.FilmProducentComparator);
        for (Film f : filmLista)
        {
            if (f.producent.toLowerCase().contains(szukane.toLowerCase()))
            {
                tempFilmLista.add(f);
            }
            else
            {
                tempFilmLista1.add(f);
            }
        }
        if(tempFilmLista.size()>0)
        {
            pokazListeFilm(tempFilmLista);
            return tempFilmLista;
        }
        else
        {
            System.out.println("Brak filmow zawierajacych podana fraze.");
        }
        return null;
    }
    
    
    public static void wypozyczcenieZListy(ArrayList<Film> tempFilmLista, Account account)
    {

            System.out.println("Ktory film chcesz wypozyczyc?");
            int wybor = Validator.inInt();
            if(wybor<0 || wybor>=tempFilmLista.size())
            {
                System.out.println("Zly numer.");
            }else if(tempFilmLista.get(wybor).status==1)
            {
                System.out.println("Film zostal juz wypozyczony.");
            }else
            {
                try {
                    wypozyczenie(tempFilmLista, wybor, account);
                } catch (IOException ex) {
                    Logger.getLogger(MenuUser.class.getName()).log(Level.SEVERE, null, ex);
                }
            }   
            
            
    }        
    
    public static void pokazListeFilm(ArrayList<Film> lista)
    {
            lista.get(0).pokazDane2Naglowek();

            int i=0;
            for(Film f : lista)
            {
                f = lista.get(i);
                System.out.print(i+".");
                f.pokazDane2(); 
                i++;
            }              
    }
    
    
    
    public static void przegladajFilmy()
    {
                    Film naglowek = new Film(null,null,0,0);
                    naglowek.pokazDane2Naglowek();
                    try {
                        FilesInputOutput.odczytBinarnyFull();
                    } catch (IOException ex) {
                        Logger.getLogger(MenuAdmin.class.getName()).log(Level.SEVERE, null, ex);
                    }        
    }
    
    public static void szukajFilmu(Account account) throws IOException
    {
                            System.out.println("Wpisz szukane: ");
                    String szukane = Validator.inStringNextLine();
                    //////////////////////////////////////////////////////////////////////////////// JESZCZE NIE DZIALA
                    System.out.println("SZUKANA: " + szukane);
                    ArrayList<Film> lista = szukanieNazwa(FilesInputOutput.odczytBinarnyDoListy(), szukane, account);
                    try{
                    if(lista.size()>0)
                    {
                    if(czyChceszWypozyczyc()==1)
                    {
                        wypozyczcenieZListy(lista, account);
                    }
                    }else 
                    {
                        System.out.println("Brak filmow.");
                    }
                    }catch(NullPointerException nue)
                    {
                        System.out.println("Brak filmow.");
                    }
    }
    
    public static void szukajProducent(Account account) throws IOException
    {
                            System.out.println("Wpisz szukane: ");
                    String szukane = Validator.inStringNextLine();
                    //////////////////////////////////////////////////////////////////////////////// JESZCZE NIE DZIALA
                    System.out.println("SZUKANA: " + szukane);
                    ArrayList<Film> lista = szukanieProducent(FilesInputOutput.odczytBinarnyDoListy(), szukane, account);
                    try{
                    if(lista.size()>0)
                    {
                    if(czyChceszWypozyczyc()==1)
                    {
                        wypozyczcenieZListy(lista, account);
                    }
                    }else
                    {
                        System.out.println("Brak filmow.");
                    }
                    }catch(NullPointerException nue)
                    {
                        System.out.println("Brak filmow.");
                    }
    }
    
    public static void wypozyczFilm(Account account) throws IOException
    {
                    ArrayList<Film> filmyBinarka = new ArrayList(FilesInputOutput.odczytBinarnyDoListy());
                    if(filmyBinarka.size()>0)
                    {
                    int wyborek;
                    filmyBinarka.get(0).pokazDane2Naglowek();
                    FilesInputOutput.odczytBinarnyFull();
                    do{
                    System.out.println("Ktory chcesz wypozyczyc?");
                    wyborek = Validator.inInt();
                    }while(wyborek>=filmyBinarka.size() && wyborek>=0);
                    
                    if(filmyBinarka.get(wyborek).status==1)
                    {
                        System.out.println("Film zostal juz wypozyczony.");
                    }else
                    {
                        wypozyczenie(filmyBinarka, wyborek, account);
                    }
                    filmyBinarka.clear();
                    }else
                    {
                        System.out.println("Brak filmow.");
                    }
    }
    
    public static void zwrotFilm(Account account) throws IOException
    {
        try {
            Film f0 = FilesInputOutput.oddanieBinarnyOdczyt(account);
            FilesInputOutput.odczytBinarnyDoZmianystatusu(f0);
            FilesInputOutput.czyszczeniePlikuUser(account);
            System.out.println("Pomyslnie oddanoo film.");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MenuUser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MenuUser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void wypozyczonyFilm(Account account) throws IOException
    {
                    try{
                    if(Validator.checkIfUserFileIsEmpty(account))
                    {
                        System.out.println("Brak wypozyczonego filmu.");
                    }else
                    {
                        FilesInputOutput.odczytWypozyczonegoFilmuUzytkownika(account).pokazDane3();
                    }
                    } catch(NullPointerException npe)
                    {
                        System.out.println("Brak wypozyczonego filmu.");
                    } catch(Exception e)
                    {
                        System.out.println("Nieznany blad");
                    }
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
    
    
    

