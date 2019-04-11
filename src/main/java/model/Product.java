/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author hbroucke
 */
@Entity
@Table(name = "PRODUCT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p")
    , @NamedQuery(name = "Product.findByProductId", query = "SELECT p FROM Product p WHERE p.productId = :productId")
    , @NamedQuery(name = "Product.findByPurchaseCost", query = "SELECT p FROM Product p WHERE p.purchaseCost = :purchaseCost")
    , @NamedQuery(name = "Product.findByQuantityOnHand", query = "SELECT p FROM Product p WHERE p.quantityOnHand = :quantityOnHand")
    , @NamedQuery(name = "Product.findByMarkup", query = "SELECT p FROM Product p WHERE p.markup = :markup")
    , @NamedQuery(name = "Product.findByAvailable", query = "SELECT p FROM Product p WHERE p.available = :available")
    , @NamedQuery(name = "Product.findByDescription", query = "SELECT p FROM Product p WHERE p.description = :description")})


public class Product implements Serializable {
    @Id
    @Column(name = "PRODUCT_ID")
    public Integer PRODUCT_ID;
    @Column(name = "PURCHASE_COST")
    public int MANUFACTURER_ID;
    @Column(name = "QUANTITY_ON_HAND")
    public String PRODUCT_CODE;
    @Column(name = "MARKUP")
    public float PURCHASE_COST;
    @Size(max = 5)
    @Column(name = "AVAILABLE")
    public String QUANTITY_ON_HAND;
    @JoinColumn(name = "MANUFACTURER_ID", referencedColumnName = "MANUFACTURER_ID")
    @ManyToOne(optional = false)
    public float MARKUP;
    @JoinColumn(name = "MANUFACTURER_ID", referencedColumnName = "MANUFACTURER_ID")
    @ManyToOne(optional = false)
    public Boolean AVAILABLE;
    @JoinColumn(name = "PRODUCT_CODE", referencedColumnName = "PROD_CODE")
    @ManyToOne(optional = false)
    public String DESCRIPTION;
    
    public Product(Integer PRODUCT_ID, int MANUFACTURER_ID, String PRODUCT_CODE) {
        this.PRODUCT_ID = PRODUCT_ID;
        this.MANUFACTURER_ID = MANUFACTURER_ID;
        this.PRODUCT_CODE = PRODUCT_CODE;
    }

    public int getPRODUCT_ID() {
        return PRODUCT_ID;
    }

    public void setPRODUCT_ID(Integer PRODUCT_ID) {
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
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (PRODUCT_ID != null ? PRODUCT_ID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Product)) {
            return false;
        }
        Product other = (Product) object;
        if ((this.PRODUCT_ID == null && other.PRODUCT_ID != null) || (this.PRODUCT_ID != null && !this.PRODUCT_ID.equals(other.PRODUCT_ID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Product[ productId=" + PRODUCT_ID + " ]";
    }

}
