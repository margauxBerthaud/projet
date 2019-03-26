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
public class Product {
    public int PRODUCT_ID;
    public int MANUFACTURER_ID;
    public String PRODUCT_CODE;
    public float PURCHASE_COST;
    public String QUANTITY_ON_HAND;
    public float MARKUP;
    public Boolean AVAILABLE;
    public String DESCRIPTION;
    
    public Product(int PRODUCT_ID, int MANUFACTURER_ID, String PRODUCT_CODE) {
        this.PRODUCT_ID = PRODUCT_ID;
        this.MANUFACTURER_ID = MANUFACTURER_ID;
        this.PRODUCT_CODE = PRODUCT_CODE;
    }

    public int getPRODUCT_ID() {
        return PRODUCT_ID;
    }

    public void setPRODUCT_ID(int PRODUCT_ID) {
        this.PRODUCT_ID = PRODUCT_ID;
    }

    public double getMANUFACTURER_ID() {
        return MANUFACTURER_ID;
    }

    public void setMANUFACTURER_ID(int MANUFACTURER_ID) {
        this.MANUFACTURER_ID = MANUFACTURER_ID;
    }

    public String getPRODUCT_CODE() {
        return PRODUCT_CODE;
    }

    public void setPRODUCT_CODE(String PRODUCT_CODE) {
        this.PRODUCT_CODE = PRODUCT_CODE;
    }

    public float getPURCHASE_COST() {
        return PURCHASE_COST;
    }

    public void setPURCHASE_COST(float PURCHASE_COST) {
        this.PURCHASE_COST = PURCHASE_COST;
    }

    public String getQUANTITY_ON_HAND() {
        return QUANTITY_ON_HAND;
    }

    public void setQUANTITY_ON_HAND(String QUANTITY_ON_HAND) {
        this.QUANTITY_ON_HAND = QUANTITY_ON_HAND;
    }

    public float getMARKUP() {
        return MARKUP;
    }

    public void setMARKUP(float MARKUP) {
        this.MARKUP = MARKUP;
    }

    public Boolean getAVAILABLE() {
        return AVAILABLE;
    }

    public void setAVAILABLE(Boolean AVAILABLE) {
        this.AVAILABLE = AVAILABLE;
    }

    public String getDESCRIPTION(){
        return DESCRIPTION;
    }
    
    public void setDESCRIPTION(String DESCRIPTION){
        this.DESCRIPTION = DESCRIPTION;
    }
}
