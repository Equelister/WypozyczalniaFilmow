package wypozyczalniafilmow;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author micha
 */
public class FilesInputOutput {

    private final static int filmBinaryInBytes = 114;

    public static Film odczytWypozyczonegoFilmuUzytkownika(Account account) throws FileNotFoundException, IOException, ClassNotFoundException {
        try {
            FileInputStream fis = new FileInputStream("WypozyczalniaFilmowProjekt\\users\\borrowed\\" + account.login + ".dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            Film f0 = (Film) ois.readObject();
            ois.close();
            return f0;
        } catch (Exception e) {
        }
        return null;
    }

    public static void odczytPlikTekstowy(String inFile) throws FileNotFoundException, IOException, ClassNotFoundException {
        String sciezkaBinarny = "WypozyczalniaFilmowProjekt\\dane\\plikbinarny.bin";
        int liczlinie = -1;
        ArrayList<Film> filmLista = new ArrayList<Film>();
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try {
            fileReader = new FileReader("WypozyczalniaFilmowProjekt\\dane\\" + inFile);
            bufferedReader = new BufferedReader(fileReader);
            String textLine = "";
            try {
                while (true) {
                    liczlinie++;
                    textLine = bufferedReader.readLine();
                    if (textLine.equals("")) {
                        continue;
                    }
                    //textLine = bufferedReader.readLine();
                    String[] tab = new String[4];
                    tab = textLine.split(";");
                    int tab2Int = Integer.parseInt(tab[2]);
                    Film f0 = new Film(tab[0], tab[1], tab2Int, 0);
                    if (tab[0] == null || tab[1] == null || tab[2] == null || Validator.checkStringLength(tab[0], 0) == false || Validator.checkStringLength(tab[1], 1) == false || Validator.checkIntYear(tab2Int) == false) {
                        System.out.println("Zly format danych. Linia: " + liczlinie);
                        continue;
                    } else if (Validator.checkStringCharacters(tab[0]) == false || Validator.checkStringCharacters(tab[1]) == false) {
                        System.out.println("Zly format danych. Linia: " + liczlinie);
                        continue;
                    } else if(Validator.czyTakiSamFilm(f0) == true)
                    {
                        
                        System.out.println("Istniej juz taki film. Linia: " + liczlinie);
                        continue;
                    }else{
                        zapisBinarny(f0, sciezkaBinarny);
                    }

                }
            } catch (Exception ex) {
                System.out.println("Koniec pliku.");
            }
        } catch (IOException ioex) {
            System.out.println("Brak takiego pliku.");
        } finally {
            fileReader.close();
            bufferedReader.close();
        }
    }

    public static void zapisBinarny(Film film, String sciezkaBinarny) {
        try {
            RandomAccessFile RAF = new RandomAccessFile(sciezkaBinarny, "rw");

            long dl = RAF.length();
            RAF.seek(dl);

            String nazwaFilm = film.nazwa;
            nazwaFilm = CustomSetter.nazwaMaxBytes(nazwaFilm);
            String producentFilm = film.producent;
            producentFilm = CustomSetter.producentMaxBytes(producentFilm);

            byte[] nazwaBytes = nazwaFilm.getBytes();
            byte[] producentBytes = producentFilm.getBytes();

            RAF.writeUTF(nazwaFilm);
            RAF.writeUTF(producentFilm);
            RAF.writeInt(film.rok);
            RAF.writeInt(film.status);
            RAF.writeBytes("\r\n");

            RAF.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(FilesInputOutput.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Blad pliku Binarnego Zapis");
        } catch (IOException ex) {
            Logger.getLogger(FilesInputOutput.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Blad Zapis Binarny");
        }
    }

    public static void edycjaBinarny(Film film, int number) {
        try {
            RandomAccessFile RAF = new RandomAccessFile("WypozyczalniaFilmowProjekt\\dane\\plikbinarny.bin", "rw");

            RAF.seek(number * filmBinaryInBytes);

            String nazwaFilm = film.nazwa;
            nazwaFilm = CustomSetter.nazwaMaxBytes(nazwaFilm);
            String producentFilm = film.producent;
            producentFilm = CustomSetter.producentMaxBytes(producentFilm);

            byte[] nazwaBytes = nazwaFilm.getBytes();
            byte[] producentBytes = producentFilm.getBytes();

            RAF.writeUTF(nazwaFilm);
            RAF.writeUTF(producentFilm);
            RAF.writeInt(film.rok);
            RAF.writeInt(film.status);

            RAF.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(FilesInputOutput.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Blad pliku Binarnego Zapis - brak pliku.");
        } catch (IOException ex) {
            Logger.getLogger(FilesInputOutput.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Blad pliku zapis Binarny.");
        }
    }

    public static void usunBinarny(int numberDelete) throws FileNotFoundException, IOException {
        int number = 0;
        File inputFile = new File("WypozyczalniaFilmowProjekt\\dane\\plikbinarny.bin");
        File tempFile = new File("WypozyczalniaFilmowProjekt\\dane\\Tempplikbinarny.bin");
        tempFile.createNewFile();
        String sciezkaTempBin = "WypozyczalniaFilmowProjekt\\dane\\Tempplikbinarny.bin";
        try {
            while (true) {
                if (numberDelete == number) {
                    Film f0 = new Film(null, null, 0, 0);
                    f0 = odczytPojedynczegoFilmu(number);

                    if (f0.equals(null)) {
                        break;
                    }

                    if (f0.status == 1) {
                        System.out.println("Nie mozna usunac wypozyczonego filmu.");
                        zapisBinarny(f0, sciezkaTempBin);
                        number++;
                        continue;
                    } else {
                        number++;
                        continue;
                    }
                } else {
                    Film f0 = new Film(null, null, 0, 0);
                    f0 = odczytPojedynczegoFilmu(number);

                    if (f0.equals(null)) {
                        break;
                    }
                    zapisBinarny(f0, sciezkaTempBin);
                    number++;
                }
            }
        } catch (NullPointerException npe) {
            // f0 == null
        } finally {
            inputFile.delete();
            tempFile.renameTo(inputFile);
        }
    }

    public static Film odczytPojedynczegoFilmu(int number) throws IOException {

        RandomAccessFile RAF = new RandomAccessFile("WypozyczalniaFilmowProjekt\\dane\\plikbinarny.bin", "rw");

        long dl = RAF.length();

        if ((number * filmBinaryInBytes) >= dl || (number * filmBinaryInBytes) < 0) {

        } else {
            RAF.seek(number * filmBinaryInBytes);
            String odczyt1 = RAF.readUTF();
            String odczyt2 = RAF.readUTF();
            int odczyt3 = RAF.readInt();
            int odczyt4 = RAF.readInt();

            odczyt1 = odczyt1.trim();
            odczyt2 = odczyt2.trim();

            Film f0 = new Film(odczyt1, odczyt2, odczyt3, odczyt4);
            RAF.close();

            return f0;
        }
        RAF.close();
        return null;
    }

    public static void odczytBinarnyFull() throws FileNotFoundException, IOException {

        RandomAccessFile RAF = new RandomAccessFile("WypozyczalniaFilmowProjekt\\dane\\plikbinarny.bin", "rw");

        int number = 0;
        RAF.seek(0);
        try {
            Film temp = new Film(null, null, 0, 0);
            while (true) {
                String odczyt1 = RAF.readUTF();
                String odczyt2 = RAF.readUTF();
                int odczyt3 = RAF.readInt();
                int odczyt4 = RAF.readInt();
                RAF.writeBytes("\r\n");

                odczyt1 = odczyt1.trim();
                odczyt2 = odczyt2.trim();
                System.out.print(number + ".");
                number++;
                Film f0 = new Film(odczyt1, odczyt2, odczyt3, odczyt4);
                f0.pokazDane2();
                //              System.out.println(RAF.getFilePointer());
            }
        } catch (EOFException eofe) {
            RAF.close();
        } finally {
            RAF.close();
        }
    }

    public static void zapisRejestracja(String log, String has, int upr) throws IOException {

        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            fw = new FileWriter("WypozyczalniaFilmowProjekt\\users\\Accounts.txt", true);
            bw = new BufferedWriter(fw);
            bw.write(log + ";" + has + ";" + Integer.toString(upr) + "\r\n");

        } catch (IOException ex) {
            Logger.getLogger(FilesInputOutput.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (Exception e) {
                    Logger.getLogger(FilesInputOutput.class.getName()).log(Level.SEVERE, null, e);

                    System.out.println("Blad w zamykaniu pliku.");

                }
            }
            if (fw != null) {
                try {
                    fw.close();
                } catch (Exception e) {
                    Logger.getLogger(FilesInputOutput.class.getName()).log(Level.SEVERE, null, e);

                    System.out.println("Blad w zamykaniu pliku");

                }
            }
        }
    }

    public static Account odczytLogin(String login, String password) throws IOException {
        Account account;
        int[] tabRet = new int[3];
        FileReader fr = null;
        BufferedReader bufferedReader = null;
        try {
            fr = new FileReader("WypozyczalniaFilmowProjekt\\users\\Accounts.txt");
            bufferedReader = new BufferedReader(fr);
            String textLine = "";
            while (true) {
                try {
                    textLine = bufferedReader.readLine();
                } catch (IOException ex) {
                    Logger.getLogger(FilesInputOutput.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception e) {
                    System.out.println("Zle dane");
                }
                if (textLine.equals("")) {
                    continue;
                }
                String[] tab = new String[3];
                tab = textLine.split(";");
                if (tab[0].equals(login)) {
                    if (tab[1].equals(password)) {
                        fr.close();
                        bufferedReader.close();
                        int uprawnienia = Integer.parseInt(tab[2]);
                        account = new Account(tab[0], tab[1], uprawnienia);

                        return account;
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FilesInputOutput.class.getName()).log(Level.SEVERE, null, ex);
        } catch (EOFException eof) {
        } catch (Exception e) {
            System.out.println("Zle Dane.");
        } finally {
            bufferedReader.close();
            fr.close();
        }
        return null;
    }

//    public static void usunKonto2(String login) throws IOException
//    {
//        ArrayList<String> lista = new ArrayList<String>();
//        
//        FileReader fr = null;
//        BufferedReader bufferedReader = null;
//        try {
//            fr = new FileReader("WypozyczalniaFilmowProjekt\\users\\Accounts.txt");
//            bufferedReader = new BufferedReader(fr);
//            String textLine = "";
//            while (true) {
//                try {
//                    textLine = bufferedReader.readLine();
//                } catch (IOException ex) {
//                    Logger.getLogger(FilesInputOutput.class.getName()).log(Level.SEVERE, null, ex);
//                } catch (Exception e) {
//                    System.out.println("errrrrrrrrrrrrrrrrrrrrrrrrror");
//                }
//                if (textLine.equals("")) {
//                    continue;
//                }
//                String[] tab = new String[3];
//                tab = textLine.split(";");
//                if (tab[0].equals(login)){
//                    File file = new File("WypozyczalniaFilmowProjekt\\users\\borrowed\\" + login + ".dat"); //////////////////////////////////////
//                    file.delete();  ////////// deleteOnExit() ?        
//                    file = null;  
//                    continue;
//                } else{
//                lista.add(textLine);
//                }
//            }
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(FilesInputOutput.class.getName()).log(Level.SEVERE, null, ex);
//        }catch (Exception e) {
//            System.out.println("Error/koniec");
//        } finally {
//            bufferedReader.close();
//            fr.close();
//        }
//        
//        File pl = new File ("WypozyczalniaFilmowProjekt\\users\\Accounts.txt");
//        pl.delete();
//        pl.createNewFile();
//        
//        FileWriter fw = null;
//        BufferedWriter bw = null;
//        int i=0;
//        try {
//            fw = new FileWriter("WypozyczalniaFilmowProjekt\\users\\Accounts.txt");
//            bw = new BufferedWriter(fw);
//            while(i<lista.size())
//            {
//                bw.write(lista.get(i));
//                bw.write("\r\n");
//                i++;
//            }
//        }catch (Exception e)
//        {
//            System.out.println("Blad usun konto 2222");
//        } finally
//        {
//            bw.close();
//            fw.close();
//        }
//        
//
//    }
//    
    
    public static void usunKonto(String login) throws IOException {
        File inputFile = new File("WypozyczalniaFilmowProjekt\\users\\Accounts.txt");
        File tempFile = new File("WypozyczalniaFilmowProjekt\\users\\TempAccounts.txt");
        tempFile.createNewFile();
        int[] tabRet = new int[3];
        FileReader fr = null;
        BufferedReader bufferedReader = null;
        try {
            fr = new FileReader(inputFile);
            bufferedReader = new BufferedReader(fr);
            String textLine = "";
            while (true) {
                textLine = bufferedReader.readLine();
                if (textLine.equals("")) {
                    continue;
                }
                //textLine = bufferedReader.readLine();
                String[] tab = new String[3];
                tab = textLine.split(";");
                if (tab[0].equals(login)) {
                    File file = new File("WypozyczalniaFilmowProjekt\\users\\borrowed\\" + login + ".dat"); //////////////////////////////////////
                    file.delete();
                    file = null;
                    continue;
                } else {
                    FileWriter fw = new FileWriter(tempFile, true);
                    fw.write(tab[0] + ";" + tab[1] + ";" + tab[2] + "\r\n");
                    fw.close();
                }
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(FilesInputOutput.class.getName()).log(Level.SEVERE, null, ex);
        } catch (EOFException eof) {
        } catch (Exception e) {
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (Exception e) {
                    Logger.getLogger(FilesInputOutput.class.getName()).log(Level.SEVERE, null, e);

                    System.out.println("Blad w zamykaniu pliku.");

                }
            }
            if (fr != null) {
                try {
                    fr.close();
                } catch (Exception e) {
                    Logger.getLogger(FilesInputOutput.class.getName()).log(Level.SEVERE, null, e);

                    System.out.println("Blad w zamykaniu pliku");

                }
            }
            inputFile.delete();
            tempFile.renameTo(inputFile);
        }

    }

    public static void odczytBinarnyDoEdycjiFilm(Film film, Film film2) throws FileNotFoundException, IOException {
        RandomAccessFile RAF = new RandomAccessFile("WypozyczalniaFilmowProjekt\\dane\\plikbinarny.bin", "rw");

        RAF.seek(0);
        try {
            Film temp = new Film(null, null, 0, 0);
            while (true) {
                String odczyt1 = RAF.readUTF();
                String odczyt2 = RAF.readUTF();
                int odczyt3 = RAF.readInt();
                int odczyt4 = RAF.readInt();
                RAF.writeBytes("\r\n");

                odczyt1 = odczyt1.trim();
                odczyt2 = odczyt2.trim();
                Film f0 = new Film(odczyt1, odczyt2, odczyt3, odczyt4);
                if (f0.nazwa.equals(film.nazwa)) {
                    if (f0.producent.equals(film.producent)) {
                        if (f0.rok == film.rok) {
                            RAF.seek(RAF.getFilePointer() - filmBinaryInBytes);
                            RAF.writeUTF(CustomSetter.nazwaMaxBytes(film2.nazwa));
                            RAF.writeUTF(CustomSetter.producentMaxBytes(film2.producent));
                            RAF.writeInt(film2.rok);
                            RAF.writeInt(film2.status);
                            break;
                        }
                    }
                }

            }
        } catch (EOFException eofe) {
            RAF.close();
        } finally {
            RAF.close();
        }
    }

    public static void odczytBinarnyDoZmianystatusu(Film film) throws FileNotFoundException, IOException {
        RandomAccessFile RAF = new RandomAccessFile("WypozyczalniaFilmowProjekt\\dane\\plikbinarny.bin", "rw");

        RAF.seek(0);
        try {
            Film temp = new Film(null, null, 0, 0);
            while (true) {
                String odczyt1 = RAF.readUTF();
                String odczyt2 = RAF.readUTF();
                int odczyt3 = RAF.readInt();
                int odczyt4 = RAF.readInt();
                RAF.writeBytes("\r\n");

                odczyt1 = odczyt1.trim();
                odczyt2 = odczyt2.trim();
                Film f0 = new Film(odczyt1, odczyt2, odczyt3, odczyt4);
                if (f0.nazwa.equals(film.nazwa)) {
                    if (f0.producent.equals(film.producent)) {
                        if (f0.rok == film.rok) {
                            if (odczyt4 == 0) {
                                f0.edytujStatusWypozyczcone();
                                long a = RAF.getFilePointer();
                                RAF.seek(a - 6);
                                RAF.writeInt(f0.status);
                                RAF.close();
                                break;
                            }
                            if (odczyt4 == 1) {
                                f0.edytujStatusDostepne();
                                long a = RAF.getFilePointer();
                                RAF.seek(a - 6);
                                RAF.writeInt(f0.status);
                                RAF.close();
                                break;
                            }
                        }
                    }
                }

                //              System.out.println(RAF.getFilePointer());
            }
        } catch (EOFException eofe) {
            RAF.close();
        } finally {
            RAF.close();
        }
    }

    public static ArrayList<Film> odczytBinarnyDoListy() throws FileNotFoundException, IOException {
        ArrayList<Film> listaBinarny = new ArrayList();
        RandomAccessFile RAF = new RandomAccessFile("WypozyczalniaFilmowProjekt\\dane\\plikbinarny.bin", "rw");

        int number = 0;
        RAF.seek(0);
        try {
            Film temp = new Film(null, null, 0, 0);
            while (true) {
                String odczyt1 = RAF.readUTF();
                String odczyt2 = RAF.readUTF();
                int odczyt3 = RAF.readInt();
                int odczyt4 = RAF.readInt();
                RAF.writeBytes("\r\n");

                odczyt1 = odczyt1.trim();
                odczyt2 = odczyt2.trim();
                number++;
                Film f0 = new Film(odczyt1, odczyt2, odczyt3, odczyt4);
                listaBinarny.add(f0);
                //              System.out.println(RAF.getFilePointer());
            }
        } catch (EOFException eofe) {
            RAF.close();
        } finally {
            RAF.close();
            return listaBinarny;
        }
    }

    public static void wypozyczenieBinarnyZapisz(Film f, Account account) {

        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("WypozyczalniaFilmowProjekt\\users\\borrowed\\" + account.getLogin() + ".dat", true));
            out.writeObject(f);
            out.close();
        } catch (IOException ioe) {
            System.out.println("Błąd");
        }
    }

    public static Film oddanieBinarnyOdczyt(Account account) throws FileNotFoundException, IOException, ClassNotFoundException {
        Film f0 = null;
        try {
            FileInputStream fis = new FileInputStream("WypozyczalniaFilmowProjekt\\users\\borrowed\\" + account.getLogin() + ".dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            f0 = (Film) ois.readObject();
        } catch (IOException ioe) {
        } catch (Exception e) {
            System.out.println("Oddanie binarny odczzyt error exception e.");
        }
        return f0;
    }

    public static void czyszczeniePlikuUser(Account account) throws IOException {
        File file = new File("WypozyczalniaFilmowProjekt\\users\\borrowed\\" + account.getLogin() + ".dat");
        //   System.out.println(file.getAbsolutePath());
        try (PrintWriter writer = new PrintWriter(file.getAbsolutePath())) {
            writer.print("");

        } catch (FileNotFoundException fnfe) {
            file.delete();
        } finally {
            file.delete();
        }

    }

    public static void createFiles() throws IOException {
        new File("WypozyczalniaFilmowProjekt").mkdirs();
        new File("WypozyczalniaFilmowProjekt\\dane").mkdirs();
        new File("WypozyczalniaFilmowProjekt\\users").mkdirs();
        new File("WypozyczalniaFilmowProjekt\\users\\borrowed").mkdirs();
        new File("WypozyczalniaFilmowProjekt\\backupFiles").mkdirs();
        new File("WypozyczalniaFilmowProjekt\\backupFiles\\users").mkdirs();
        new File("WypozyczalniaFilmowProjekt\\backupFiles\\users\\borrowed").mkdirs();
        new File("WypozyczalniaFilmowProjekt\\backupFiles\\dane").mkdirs();

        File file = new File("WypozyczalniaFilmowProjekt\\dane\\plikbinarny.bin");
        file.createNewFile();
        file = new File("WypozyczalniaFilmowProjekt\\users\\Accounts.txt");
        file.createNewFile();

        Validator.checkIfAccountsEmpty();
    }

    public static void kopiaZapasowa() throws IOException {
        String time = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
        Path source = Paths.get("WypozyczalniaFilmowProjekt\\users\\Accounts.txt");
        Path out = Paths.get("WypozyczalniaFilmowProjekt\\backupFiles\\users\\Accounts " + time + ".txt");
        Files.copy(source, out);
        time = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
        source = Paths.get("WypozyczalniaFilmowProjekt\\dane\\plikbinarny.bin");
        out = Paths.get("WypozyczalniaFilmowProjekt\\backupFiles\\dane\\plikbinarny " + time + ".bin");
        Files.copy(source, out);

        ArrayList<File> nazwyFiles = new ArrayList<File>();

        final File folder = new File("WypozyczalniaFilmowProjekt\\users\\borrowed");
        for (final File fileEntry : folder.listFiles()) {
            {
                time = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
                source = Paths.get("WypozyczalniaFilmowProjekt\\users\\borrowed\\" + fileEntry.getName());
                out = Paths.get("WypozyczalniaFilmowProjekt\\backupFiles\\users\\borrowed\\" + fileEntry.getName() + " " + time + ".dat");
                Files.copy(source, out);
            }
        }
        System.out.println("Utoworzono kopie zapasowa.");
    }

}
