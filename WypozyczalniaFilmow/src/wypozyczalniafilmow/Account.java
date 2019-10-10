/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wypozyczalniafilmow;

/**
 *
 * @author micha
 */
public class Account {
    
    static String login;
    static String password;
    static int uprawnienia;

    public Account(String login, String password, int uprawnienia)
    {
        this.login = login;
        this.password = password;
        this.uprawnienia = uprawnienia;
    }
    
    public String getLogin() 
    {
        return login;
    }
    
    public String getPass()
    {
        return password;
    }
    
    public int getUpr()
    {
        return uprawnienia;
    }
    
    //Admin 1 --- User 2
    
    
    
    
    
    
}
