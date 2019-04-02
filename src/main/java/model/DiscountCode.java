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
public class DiscountCode {
    public String DISCOUNT_CODE;
    public float RATE;
    
    public DiscountCode(String DISCOUNT_CODE, float Rate){
        this.DISCOUNT_CODE = DISCOUNT_CODE;
        this.RATE=Rate;
    }
    
    public String getDISCOUNT_CODE() {
	return DISCOUNT_CODE;
    }

    public float getRATE() {
	return RATE;
    }
}
