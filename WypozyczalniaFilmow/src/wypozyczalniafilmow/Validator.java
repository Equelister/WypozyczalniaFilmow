package wypozyczalniafilmow;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author micha
 */
public class Validator {
    
    
    public static boolean checkStringLength(String text, int flag)
    {
        if(flag==0)
        {
            if(text.length()>60)
            {
                System.out.println("Zbyt dlugi tekst.");
                return false;
            } else
            {       
                return true;
            }
        } else if(flag==1)
        {
            if(text.length()>40)
            {
                System.out.println("Zbyt dlugi tekst.");
                return false;
            } else
            {       
                return true;
            }
        } 
        return true;
    }
    
    public static boolean checkIntYear(int number)
    {
        if(number>=1895 && number<=Calendar.getInstance().get(Calendar.YEAR))
        {
            return true;
        }
        else
        {
            System.out.println("Zly rok.");
            return false;
        }
    }
    
    public static boolean checkIfFileInDaneExist(String str)
    {
        File f = new File("WypozyczalniaFilmowProjekt\\dane\\"+str);     
        if (f.exists())
        {
            return true;
        }else
        {        
        return false;
        }
    }
    
    public static boolean checkIfUserFileIsEmpty(Account account) throws FileNotFoundException, IOException
    {
        BufferedReader br = new BufferedReader(new FileReader("WypozyczalniaFilmowProjekt\\users\\borrowed\\"+account.getLogin()+".dat"));     
        if (br.readLine() == null) {
            br.close();
            return true;
        }
        br.close();
        return false;
    }
    
    public static boolean checkIfUserFileExist(String str) throws FileNotFoundException, IOException
    {
        File f = new File("WypozyczalniaFilmowProjekt\\users\\borrowed\\"+str+".dat");     
        if (f.exists())
        {
            return true;
        }else
        {        
        return false;
        }
    }
    
    public static boolean checkIfUserFileIsEmpty(String login) throws FileNotFoundException, IOException
    {
        BufferedReader br = new BufferedReader(new FileReader("WypozyczalniaFilmowProjekt\\users\\borrowed\\"+login+".dat"));     
        if (br.readLine() == null) {
            br.close();
            return true;
        }
        br.close();
        return false;
    }    
    
    public static boolean checkStringCharacters(String tekst)
    {
        for(int i =0; i<tekst.length(); i++)
        {
            char znak = tekst.charAt(i);
            if(znak >='a' && znak<='z')
            {
                continue;
            }else if(znak>='A' && znak<='Z')
            {
                continue;
            }else if(znak>='0' && znak<='9')
            {
                continue;
            }else if(Character.isWhitespace(znak))
            {
                continue;
            }else if(znak =='.' || znak ==',' || znak == '?' || znak=='!' || znak==':' || znak=='&')
            {
                continue;
            }else
            {
                System.out.println("Niedozwolone znaki. Znaki dozwolone [A-Z], [a-z], [0-9], {,.!?&}");
                return false;
            }
        }
        return true;
    }
    
    
    public static String inStringNextLine()
    {
        Scanner in = new Scanner(System.in);
        String string = null;
        do{
            try{
            string = in.nextLine();
            }catch(Exception e)
            {
                System.out.println("Zle wprowadzone dane.");
                continue;
            }
        }while(string==null);
        return string;
    }
    public static String inStringNext()
    {
        Scanner in = new Scanner(System.in);
        String string = null;
        do{
            try{
            string = in.next();
            }catch(Exception e)
            {
                System.out.println("Zle wprowadzone dane.");
                in.nextLine();
                continue;
            }
            in.nextLine();
        }while(string==null);
        return string;
    }
    public static int inInt()
    {
        Scanner in = new Scanner(System.in);
        int i = -1;
        do{
            try{
            i = in.nextInt();
            }catch(Exception e)
            {
                System.out.println("Zle wprowadzone dane.");
                in.nextLine();
                i=-1;
                continue;
            }
            in.nextLine();
        }while(i==-1);
        return i;
    }
    
    public static void checkIfAccountsEmpty() throws FileNotFoundException, IOException
    {
        BufferedReader br = new BufferedReader(new FileReader("WypozyczalniaFilmowProjekt\\users\\Accounts.txt"));     
        if (br.readLine() == null) 
        {
            FileWriter fw = new FileWriter("WypozyczalniaFilmowProjekt\\users\\Accounts.txt", true);
            fw.write("Admin;Admin1;1\r\n");
            fw.close();    
            File f = new File("WypozyczalniaFilmowProjekt\\users\\borrowed\\Admin.dat");
            f.createNewFile();
        }
        br.close();
    }
    
    public static boolean checkIfAccountsTaken(String login)
    {
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try{
            fileReader = new FileReader("WypozyczalniaFilmowProjekt\\users\\Accounts.txt");
            bufferedReader = new BufferedReader(fileReader); 
            String textLine="";
            try{
                while(true)
                {
                    textLine= bufferedReader.readLine();
                    if(textLine.equals("")){
                        continue;
                    }
                    //textLine = bufferedReader.readLine();
                    String[] tab = new String[3];
                    tab=textLine.split(";");
                    if(tab[0].equals(login))
                    {
                        System.out.println("Login zajety.");
                        if(bufferedReader!=null)
                        bufferedReader.close();
                        if(fileReader!=null)
                        fileReader.close();
                        return true;
                    }
                }
            }catch(Exception ex){
                if(bufferedReader!=null)
                bufferedReader.close();
                if(fileReader!=null)
                fileReader.close();
                return false;

            }
        }catch(IOException ioex)
        {
            try {
                if(bufferedReader!=null)
                    bufferedReader.close();
                if(fileReader!=null)
                    fileReader.close();
                return false;
            } catch (IOException ex) {
                Logger.getLogger(Validator.class.getName()).log(Level.SEVERE, null, ex);
            } finally 
            {
                return false;
            }
        }finally 
        {
            try {
                if(bufferedReader!=null)
                    bufferedReader.close();
                if(fileReader!=null)
                    fileReader.close();
            } catch (IOException ex) {
                Logger.getLogger(Validator.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
    
    public static boolean czyTakiSamFilm(Film film) throws FileNotFoundException, IOException
    {
            ArrayList<Film> listaBinarny = new ArrayList();
            RandomAccessFile RAF = new RandomAccessFile("WypozyczalniaFilmowProjekt\\dane\\plikbinarny.bin", "rw");

            int number = 0;
            RAF.seek(0);
            
            Film temp = new Film(null, null, 0, 0);
            while(true)
            {  
                try{
                String odczyt1 = RAF.readUTF();
                String odczyt2 = RAF.readUTF();
                int odczyt3 = RAF.readInt();
                int odczyt4 = RAF.readInt();
                RAF.writeBytes("\r\n");
                
                odczyt1 = odczyt1.trim();
                odczyt2 = odczyt2.trim();
                number++;
                Film f0 = new Film(odczyt1, odczyt2, odczyt3, odczyt4);
                if(f0.nazwa.equals(film.nazwa))
                    if(f0.producent.equals(film.producent))
                        if(f0.rok == film.rok)
                        {
                            RAF.close();
                            return true;
                        }
                }catch (EOFException eofe)
            {
                RAF.close();
                return false;
            }
            }
    }
    
    
    
    
    
    
    
}
