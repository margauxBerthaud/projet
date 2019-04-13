/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
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
    , @NamedQuery(name = "Product.findByPRODUCT_ID", query = "SELECT p FROM Product p WHERE p.PRODUCT_ID = :PRODUCT_ID")
    , @NamedQuery(name = "Product.findByPurchaseCost", query = "SELECT p FROM Product p WHERE p.PURCHASE_COST = :PURCHASE_COST")
    , @NamedQuery(name = "Product.findByQuantityOnHand", query = "SELECT p FROM Product p WHERE p.QUANTITY_ON_HAND = :QUANTITY_ON_HAND")
    , @NamedQuery(name = "Product.findByMarkup", query = "SELECT p FROM Product p WHERE p.MARKUP = :MARKUP")
    , @NamedQuery(name = "Product.findByAvailable", query = "SELECT p FROM Product p WHERE p.AVAILABLE = :AVAILABLE")
    , @NamedQuery(name = "Product.findByDescription", query = "SELECT p FROM Product p WHERE p.DESCRIPTION = :DESCRIPTION")})


public class Product implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "PRODUCT_ID")
    private Integer PRODUCT_ID;
    @Column(name = "PURCHASE_COST")
    private double PURCHASE_COST;
    @Column(name = "QUANTITY_ON_HAND")
    private Integer QUANTITY_ON_HAND;
    @Column(name = "MARKUP")
    private BigDecimal MARKUP;
    @Size(max = 5)
    @Column(name = "AVAILABLE")
    private String AVAILABLE;
    @Size(max = 50)
    @Column(name = "DESCRIPTION")
    private String DESCRIPTION;
    @JoinColumn(name = "MANUFACTURER_ID", referencedColumnName = "MANUFACTURER_ID")
    @ManyToOne(optional = false)
    private Manufacturer MANUFACTURER_ID;
    @JoinColumn(name = "PRODUCT_CODE", referencedColumnName = "PROD_CODE")
    @ManyToOne(optional = false)
    private ProductCode PRODUCT_CODE;
    
   
    
    public Product(){
    }
    
    public Product (Integer PRODUCT_ID){
        this.PRODUCT_ID = PRODUCT_ID;
    }

    public int getPRODUCT_ID() {
        return PRODUCT_ID;
    }

    public void setPRODUCT_ID(Integer PRODUCT_ID) {
        this.PRODUCT_ID = PRODUCT_ID;
    }

    public Manufacturer getMANUFACTURER_ID() {
        return MANUFACTURER_ID;
    }

    public void setMANUFACTURER_ID(Manufacturer MANUFACTURER_ID) {
        this.MANUFACTURER_ID = MANUFACTURER_ID;
    }

    public ProductCode getPRODUCT_CODE() {
        return PRODUCT_CODE;
    }

    public void setPRODUCT_CODE(ProductCode PRODUCT_CODE) {
        this.PRODUCT_CODE = PRODUCT_CODE;
    }

    public double getPURCHASE_COST() {
        return PURCHASE_COST;
    }

    public void setPURCHASE_COST(double PURCHASE_COST) {
        this.PURCHASE_COST = PURCHASE_COST;
    }

    public Integer getQUANTITY_ON_HAND() {
        return QUANTITY_ON_HAND;
    }

    public void setQUANTITY_ON_HAND(Integer QUANTITY_ON_HAND) {
        this.QUANTITY_ON_HAND = QUANTITY_ON_HAND;
    }

    public BigDecimal getMARKUP() {
        return MARKUP;
    }

    public void setMARKUP(BigDecimal MARKUP) {
        this.MARKUP = MARKUP;
    }

    public String getAVAILABLE() {
        return AVAILABLE;
    }

    public void setAVAILABLE(String AVAILABLE) {
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
