package wypozyczalniafilmow;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author micha
 */
public class MenuAdmin extends Menu {

    private final static String accountLogin = Account.login;
        
    public static void menu(Account account) throws IOException
    {
        
        File file = new File("WypozyczalniaFilmowProjekt\\users\\borrowed\\"+account.login+".dat");
        file.createNewFile();
        
        
        int menuWybor = -1;
        do {
            enter();
            System.out.println("Witaj "+account.getLogin());
            System.out.println("Witaj w menu administratora.\n\n");
            menuSystemOutPrintAdmin();
            
            menuWybor = Validator.inInt();

            switch (menuWybor) {
                case 1: {
                    zaladowanieTekstowy();
                    break;
                }

                case 2: {
                    sortBinarnyAlfabetNazwa();
                    break;
                }

                case 3: {
                    sortBinarnyAlfabetProducent();
                    break;
                }

                case 4: {
                    szukajNazwa();
                    break;
                }
                
                case 5:{
                    FilesInputOutput.zapisBinarny(dodajFilm(),sciezkaBinarny);
                    break;
                }
                
                case 6: ////////////////// Modyfikuj binarny
                {
                    modifyBinarny();
                    break;
                }
                
                case 7:
                {
                    wyswitlfFilmyBinarny();
                    break;
                }
                
                case 8:
                {
                    usunFilm();
                    break;
                }
                
                case 9:
                {
                    FilesInputOutput.kopiaZapasowa();
                    break;
                }
                
                case 10:
                {
                    wyborStworzKonto(account);
                    break;
                }
                
                case 11:
                {
                    usunKonto(account);
                    break;
                }
                

                
                case 0: {
                    menuWybor = 0;
                    break;
                }
            }
        } while (menuWybor != 0);    
    }
    
    
    
    
    
        public static Film dodajFilm()
    {
        String nazwaFilm = InputFilm.nazwaFilm();
        String producentFilm = InputFilm.producentFilm();
        int rokFilm = InputFilm.rokFilm();
        Film f0 = new Film(nazwaFilm, producentFilm, rokFilm, 0);
        System.out.println("");
        System.out.println("Dodany film: ");
        f0.pokazDane();
        System.out.println("");
        return f0;
    }
    
        
    
    public static void edytowanieFilm(ArrayList<Film> tempFilmLista) throws IOException {
        int i = 0;
        System.out.println("----- FILMY DO EDYTOWANIA -----");
        wyswietlListe(tempFilmLista);
        System.out.println("Wybierz film do modyfikacji.");
        int wybor = Validator.inInt();
        System.out.println("Wybrano:");
        int wyboredytuj;
        Film f0 = tempFilmLista.get(wybor);
        
        Film f0temp = new Film(f0.nazwa, f0.producent, f0.rok, f0.status);

        do {
            tempFilmLista.get(wybor).pokazDane3();
            System.out.println();
            System.out.println("Co chcesz zmienic? \n1.Nazwa.\n2.Producent.\n3.Date wydania.\n0.Powrot.\nWybor: ");
            wyboredytuj = Validator.inInt();
            switch (wyboredytuj) {

                case 1: {
                    System.out.print("Podaj nowa nazwe: ");
                    String newNazwa = InputFilm.nazwaFilm();
                    f0.edytujFilmNazwa(newNazwa);
                    break;
                }
                case 2: {
                    System.out.println("Podaj nowego producenta: ");
                    String newProducent = InputFilm.producentFilm();
                    f0.edytujFilmProducent(newProducent);
                    break;
                }
                case 3: {
                    System.out.println("Podaj nowa date: ");
                    int newRok = InputFilm.rokFilm();
                    f0.edytujFilmRok(newRok);
                    break;
                }
            }
        } while (wyboredytuj != 0);
        ////// ZMODYFIKUJ/USUN  JAKIS ELEMENT I ZWROC ZEDYTOWANA LISTE
        FilesInputOutput.odczytBinarnyDoEdycjiFilm(f0temp, f0);
    }
        
    
    
    public static void menuSystemOutPrintAdmin()
    {
            System.out.println();
            System.out.println("1.Zaladowanie z pliku TEKSTOWEGO DO BINARNEGO INSTANT.");
            System.out.println("2.Sortuj liste wedlug nazwy filmu alfabetycznie.");
            System.out.println("3.Sortuj liste wedlug producenta filmu alfabetycznie.");
            System.out.println("4.Szukaj wedlug nazwy.");
            System.out.println("5.Zapis 1 filmu do binarnego");
            System.out.println("6.Modyfikuj film w pliku binarnym.");
            System.out.println("7.Wyswietl wszystkie filmy z pliku binarnego.");
            System.out.println("8.Usuwanie wybranego filmu z pliku binanrnego.");
            System.out.println("9.Stworz kopie zapasowa plikow.");
            System.out.println("10.Stworz konto.");
            System.out.println("11.Usun konto.");
            System.out.println("0.Wyjscie z programu.");
    }
    

    
        
    public static Film edytowanieFilmBinarny(Film f0) {
        int i = 0;
        int wyboredytuj;

        do {
            System.out.println("Co chcesz zmienic? \n1.Nazwa.\n2.Producent.\n3.Date wydania.\n0.Powrot.\nWybor: ");
            wyboredytuj = Validator.inInt();
            switch (wyboredytuj) {

                case 1: {
                    f0.edytujFilmNazwa(InputFilm.nazwaFilm());
                    break;
                }
                case 2: {
                    f0.edytujFilmProducent(InputFilm.producentFilm());
                    break;
                }
                case 3: {
                    f0.edytujFilmRok(InputFilm.rokFilm());
                    break;
                }
            }
        } while (wyboredytuj != 0);
        return f0;
    }    
    

    public static void wyswietlListe(ArrayList<Film> filmLista) {
        int i = 0;
        for (Film f : filmLista) {
            System.out.print(i + ".");
            f.pokazDane2();
            i++;
        }
    }



    

    

    
    public static void szukajNazwa() throws IOException
    {
        ArrayList<Film> filmLista = new ArrayList<Film>();
        System.out.println("Wpisz szukane: ");
        String szukane = Validator.inStringNext();
        System.out.println("SZUKANA: " + szukane);
        filtrowanie0(FilesInputOutput.odczytBinarnyDoListy(), szukane);
    }


    public static void filtrowanie0(ArrayList<Film> filmLista, String szukane) throws IOException 
    {
        ArrayList<Film> tempFilmLista = new ArrayList<Film>();
        ArrayList<Film> tempFilmLista1 = new ArrayList<Film>();

        Collections.sort(filmLista, Film.FilmNazwaComparator);
        for (Film f : filmLista)
        {
            if (f.getNazwa().toLowerCase().contains(szukane.toLowerCase()))
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
            edytowanieFilm(tempFilmLista);     
        }
        else
        {
            System.out.println("Brak filmow zawierajacych podana fraze.");
        }
    }    
 
    
    public static void modifyBinarny()
    {
        try{
        try {
            FilesInputOutput.odczytBinarnyFull();
        } catch (IOException ex) {
            Logger.getLogger(MenuAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
            System.out.println("Wybierz ktory film edytowac: ");
            int numer = Validator.inInt();
            Film f0 = null;
        try {
            f0 = (FilesInputOutput.odczytPojedynczegoFilmu(numer));
        } catch (IOException ex) {
            Logger.getLogger(MenuAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
            f0.pokazDane2Naglowek();
            f0.pokazDane2();
            edytowanieFilmBinarny(f0);
            FilesInputOutput.edycjaBinarny(f0, numer);
        try {
            FilesInputOutput.odczytBinarnyFull();
        } catch (IOException ex) {
            Logger.getLogger(MenuAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
        } catch(NullPointerException npe)
        {
            System.out.println("Brak filmow.");
        }
    }
    
    public static void wyswitlfFilmyBinarny()
    {
                        try {
                    FilesInputOutput.odczytBinarnyFull();
                } catch (IOException ex) {
                    Logger.getLogger(MenuAdmin.class.getName()).log(Level.SEVERE, null, ex);
                }
    }
    
    public static void usunFilm()
    {
                            System.out.println("Ktory film chcesz usunac?");
                try {
                    FilesInputOutput.odczytBinarnyFull();
                } catch (IOException ex) {
                    Logger.getLogger(MenuAdmin.class.getName()).log(Level.SEVERE, null, ex);
                }
                    int numerUsun = Validator.inInt();
                try {
                    FilesInputOutput.usunBinarny(numerUsun);
                } catch (IOException ex) {
                    Logger.getLogger(MenuAdmin.class.getName()).log(Level.SEVERE, null, ex);
                }
    }
    

    
    public static void stworzKonto(int upraw, Account account) throws IOException 
    {
        String log;
        String has;

        do{
        System.out.println("Podaj login dla nowego konta:");
        log = Validator.inStringNext();
        System.out.println("Podaj haslo dla nowego konta:");
        has = Validator.inStringNext();
        }while(Validator.checkIfAccountsTaken(log));
  //      Account accountCreate = new Account(log, has, upraw);
        FilesInputOutput.zapisRejestracja(log, has, upraw);
        File file = new File("WypozyczalniaFilmowProjekt\\users\\borrowed\\"+log+".dat");
        try {
            file.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(MenuAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
        file = null;
        System.out.println("Nowe konto utworzone.");
    }
    
    public static void wyborStworzKonto(Account account) throws IOException
    {
        int a;
        System.out.println("Stworz:\n1.Admin\n2.User");
        do{
        a = Validator.inInt();
        }while(a!=1 && a!=2);
        stworzKonto(a, account);
        
    }
    
    public static void usunKonto(Account account) throws IOException
    {
        String nazwa="";
        System.out.println("Podaj nazwe konta.");
        nazwa = Validator.inStringNext();
        int a=0;
        System.out.println("Czy jestes tego pewien?\n1.Tak\n2.Nie");
        do{
        a = Validator.inInt();
        }while(a!=1 && a!=2);
        if(a==1)
        {
            if(account.getLogin().equals(nazwa))
            {
                System.out.println("Nie mozna usunąć, ponieważ to konto jest obecnie używane");
            }else
            {
                try {
                    if(Validator.checkIfUserFileExist(nazwa))
                    {
                        if(Validator.checkIfUserFileIsEmpty(nazwa))
                        {
                            try {
                                FilesInputOutput.usunKonto(nazwa);
          /////////                      FilesInputOutput.usunKonto2(nazwa);
                            } catch (IOException ex) {
                                Logger.getLogger(MenuAdmin.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }else
                        {
                            System.out.println("Nie mozna usunac, poniewaz na to konto jest wypozyczony film.");
                        }
                    }else
                    {
                        System.out.println("Brak takiego konta.");
                    }
                } catch (IOException ex) {  
                    Logger.getLogger(MenuAdmin.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

        public static void zaladowanieTekstowy()
        {
                    System.out.println("Przed dodaniem, umiesc plik w folderze 'WypozyczalniaFilmowProjekt/dane'.");
                    enter();
                    System.out.println("Podaj nazwe pliku z rozserzeniem [np. BazaFilm.txt]");
                    String nazwaPliku = Validator.inStringNext();
                    if(Validator.checkIfFileInDaneExist(nazwaPliku))
                    {
                try {
                    FilesInputOutput.odczytPlikTekstowy(nazwaPliku);
                } catch (IOException ex) {
                    Logger.getLogger(MenuAdmin.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(MenuAdmin.class.getName()).log(Level.SEVERE, null, ex);
                }
                    }else
                    {
                        System.out.println("Brak takiego pliku.");
                    }
                    
        }
    
    
    
    
}
