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
public class ProductCode {
    public String PROD_CODE;
    public String DISCOUNT_CODE;
    public String DESCRITPION;  
    
    public ProductCode() {
    }

    public String getPROD_CODE() {
        return PROD_CODE;
    }

    public void setPROD_CODE(String PROD_CODE) {
        this.PROD_CODE = PROD_CODE;
    }

    public String getDISCOUNT_CODE() {
        return DISCOUNT_CODE;
    }

    public void setDISCOUNT_CODE(String DISCOUNT_CODE) {
        this.DISCOUNT_CODE = DISCOUNT_CODE;
    }

    public String getDESCRITPION() {
        return DESCRITPION;
    }

    public void setDESCRITPION(String DESCRITPION) {
        this.DESCRITPION = DESCRITPION;
    }
}
