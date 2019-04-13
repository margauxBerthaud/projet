/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author hbroucke
 */
public class ProductCode implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private String PROD_CODE;
    private Character DISCOUNT_CODE;
    private String DESCRITPION;  
    
    public Collection<Product> productCollection;
    
    public ProductCode(){
    }
    
    
    public ProductCode(String PROD_CODE) {
        this.PROD_CODE = PROD_CODE;
    }

    public String getPROD_CODE() {
        return PROD_CODE;
    }

    public void setPROD_CODE(String PROD_CODE) {
        this.PROD_CODE = PROD_CODE;
    }

    public Character getDISCOUNT_CODE() {
        return DISCOUNT_CODE;
    }

    public void setDISCOUNT_CODE(Character DISCOUNT_CODE) {
        this.DISCOUNT_CODE = DISCOUNT_CODE;
    }

    public String getDESCRITPION() {
        return DESCRITPION;
    }

    public void setDESCRITPION(String DESCRITPION) {
        this.DESCRITPION = DESCRITPION;
    }
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (PROD_CODE != null ? PROD_CODE.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductCode)) {
            return false;
        }
        ProductCode other = (ProductCode) object;
        if ((this.PROD_CODE == null && other.PROD_CODE != null) || (this.PROD_CODE != null && !this.PROD_CODE.equals(other.PROD_CODE))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.ProductCode[ prodCode=" + PROD_CODE+ " ]";
    }
    
}
