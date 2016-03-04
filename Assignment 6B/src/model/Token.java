package model;

/**
 * This interface represents a Token. It allows for a generic instantiation of a Token.
 * 
 * @author Lisa Taylor
 * @version 3 March 2016
 */
public abstract class Token {

    private String token = null;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    
    @Override
    public String toString() {
        return token;
    }
}

