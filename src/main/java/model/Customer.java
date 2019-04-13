/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;


/**
 *
 * @author hbroucke
 */
public class Customer {
    private String NAME;
    private String ADDRESSLINE1;
    private String CITY;
    private String STATE;
    private String PHONE;
    private String EMAIL;
    private int CREDIT_LIMIT;
    private String PASSWORD;
    
    public Customer(){
        
    }
    
    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getADDRESSLINE1() {
        return ADDRESSLINE1;
    }

    public void setADDRESSLINE1(String ADDRESSLINE1) {
        this.ADDRESSLINE1 = ADDRESSLINE1;
    }
    

    public String getCITY() {
        return CITY;
    }

    public void setCITY(String CITY) {
        this.CITY = CITY;
    }

    public String getSTATE() {
        return STATE;
    }

    public void setSTATE(String STATE) {
        this.STATE = STATE;
    }
    public String getPHONE() {
        return PHONE;
    }

    public void setPHONE(String PHONE) {
        this.PHONE = PHONE;
    }


    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public int getCREDIT_LIMIT() {
        return CREDIT_LIMIT;
    }

    public void setCREDIT_LIMIT(int CREDIT_LIMIT) {
        this.CREDIT_LIMIT = CREDIT_LIMIT;
    }
    public String getPassword() {
        return PASSWORD;
    }

    public void setPassword(String password) {
        this.PASSWORD = password;
    }   

}

    

