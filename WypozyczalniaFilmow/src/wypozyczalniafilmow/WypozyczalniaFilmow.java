package wypozyczalniafilmow;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;



public class WypozyczalniaFilmow {

    public static void main(String[] args) throws IOException, FileNotFoundException, ClassNotFoundException {
        FilesInputOutput.createFiles();
        credits();
        dostep();
        credits();
    }
    
    
    public static void dostep() throws IOException, FileNotFoundException, ClassNotFoundException
    {
        int wybor =-1;
        do{
            System.out.println("1.Rejestracja\n2.Logowanie\n0.Wyjscie");
            wybor = Validator.inInt();      
            if(wybor==1)
            {
                rejestracja();
            }else if(wybor==2)
            {
                Account account = login();
                if(Account.uprawnienia==1)
                {        
                MenuAdmin.menu(account);
                break;
                }
                if(Account.uprawnienia==2)
                {
                MenuUser.menu(account);
                break;
                }
            }
        }while(wybor!=0);
    }
    
    public static void credits()
    {
        System.out.println("\n*****************************************");
        System.out.println("Autor: Michal Mazurek\nWIiNoM I Semestr Inz. Inf.");
        System.out.println("*****************************************\n");
    }
    
    public static void rejestracja() throws IOException
    {
        String log;
        String has;
        do{
        System.out.println("Podaj nowy login:");
        log = Validator.inStringNext();
        System.out.println("Podaj nowe haslo:");
        has = Validator.inStringNext();
        }while(Validator.checkIfAccountsTaken(log));
        Account account = new Account(log, has, 2);
        FilesInputOutput.zapisRejestracja(log, has, 2);
        File file = new File("WypozyczalniaFilmowProjekt\\users\\borrowed\\"+log+".dat");
        try {
            file.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(MenuAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }        
        System.out.println("Nowe konto utworzone.");
    }

    
    public static Account login() throws IOException
    {
        String loginIn;
        Account account;
        String passwordIn;
        int tab[] = new int[2];
        do{
        System.out.println("Podaj login:");
        loginIn = Validator.inStringNext();
        System.out.println("Podaj haslo:");
        passwordIn = Validator.inStringNext();
        account = FilesInputOutput.odczytLogin(loginIn, passwordIn);
        }while(account.uprawnienia==1 && account.uprawnienia==0);
        return account;
    }
    

}
