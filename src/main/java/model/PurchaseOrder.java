/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;

/**
 *
 * @author hbroucke
 */
public class PurchaseOrder {
    public int ORDER_NUM;
    public int CUSTOMER_ID;
    public int PRODUCT_ID;
    public int QUANTITY;
    public float SHIPPING_COST;
    public Date SALES_DATE;
    public Date SHIPPING_DATE;
    public String FREIGHT_COMPANY;
    public double COST;

    public PurchaseOrder(){
        
    }
    
    public int getORDER_NUM() {
        return ORDER_NUM;
    }

    public void setORDER_NUM(int ORDER_NUM) {
        this.ORDER_NUM = ORDER_NUM;
    }
    
    public int getCUSTOMER_ID() {
        return CUSTOMER_ID;
    }

    public void setCUSTOMER_ID(int CUSTOMER_ID) {
        this.CUSTOMER_ID = CUSTOMER_ID;
    }

    public int getPRODUCT_ID() {
        return PRODUCT_ID;
    }

    public void setPRODUCT_ID(int PRODUCT_ID) {
        this.PRODUCT_ID = PRODUCT_ID;
    }

    public int getQUANTITY() {
        return QUANTITY;
    }

    public void setQUANTITY(int QUANTITY) {
        this.QUANTITY = QUANTITY;
    }

    public float getSHIPPING_COST() {
        return SHIPPING_COST;
    }

    public void setSHIPPING_COST(float SHIPPING_COST) {
        this.SHIPPING_COST = SHIPPING_COST;
    }
    
    
    public Date getSALES_DATE() {
        return SALES_DATE;
    }

    public void setSALES_DATE(Date SALES_DATE) {
        this.SALES_DATE = SALES_DATE;
    }

    public Date getSHIPPING_DATE() {
        return SHIPPING_DATE;
    }

    public void setSHIPPING_DATE(Date SHIPPING_DATE) {
        this.SHIPPING_DATE = SHIPPING_DATE;
    }

    public String getFREIGHT_COMPANY() {
        return FREIGHT_COMPANY;
    }

    public void setFREIGHT_COMPANY(String FREIGHT_COMPANY) {
        this.FREIGHT_COMPANY = FREIGHT_COMPANY;
    }

    public double getCOST() {
        return COST;
    }

    public void setCOST(double COST) {
        this.COST = COST;
    }    
}
