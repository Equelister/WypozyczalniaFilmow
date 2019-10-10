package wypozyczalniafilmow;

public class CustomSetter {
    
    
    
    public static String nazwaMaxBytes(String tekst)
    {
        StringBuffer tekstBuffer = new StringBuffer(tekst);
        for(int i=tekst.length(); i<60; i++)
        {
            tekstBuffer.append(" ");
        }     
        tekst = tekstBuffer.toString();
        return tekst;
    }
    
    public static String producentMaxBytes(String tekst) 
    {
        StringBuffer tekstBuffer = new StringBuffer(tekst);        
        for(int i=tekst.length(); i<40; i++)
        {
            tekstBuffer.append(" ");
        }     
        tekst = tekstBuffer.toString();        
        return tekst;
    }
        
    
    
    
    
    
    
    
}
