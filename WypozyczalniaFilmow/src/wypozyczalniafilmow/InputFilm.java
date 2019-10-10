package wypozyczalniafilmow;

public class InputFilm {
    


    
    public static String nazwaFilm() {
        String nazwaFilm;
        boolean testStr0;
        boolean testStr1;
        do{
        System.out.print("Podaj nazwe filmu: ");
        nazwaFilm = Validator.inStringNextLine();
        testStr0 = Validator.checkStringLength(nazwaFilm, 0);
        testStr1 = Validator.checkStringCharacters(nazwaFilm);
        }while(testStr0==false || testStr1==false);
        return nazwaFilm;
    }

    public static String producentFilm() {
        String producentFilm;
        boolean testStr0;
        boolean testStr1;
        do{
        System.out.print("Producent Filmu: ");
        producentFilm = Validator.inStringNextLine();
        testStr0 = Validator.checkStringLength(producentFilm, 1);
        testStr1 = Validator.checkStringCharacters(producentFilm);
        }while(testStr0==false || testStr1==false);
        return producentFilm;
    }

    public static int rokFilm() {
        int rokFilm=0;
        boolean testInt;
        do{
        System.out.print("Rok Wydania Filmu: ");
        rokFilm = Validator.inInt();
        testInt = Validator.checkIntYear(rokFilm);
        }while(testInt==false);
        return rokFilm;
    }
    
    
    
    
}
