/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Collection;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlTransient;


/**
 *
 * @author hbroucke
 */
public class Manufacturer implements Serializable {
    private static final long serialVersionUID = 1L;
    public int MANUFACTURER_ID;
    public String NAME;
    public String ADDRESSLINE1;
    public String ADDRESSLINE2;
    public String CITY;
    public String STATE;
    public int ZIP;
    public String PHONE;
    public String FAX;
    public String EMAIL;
    public String REP;
    private Collection<Product> productCollection;
    
    public Manufacturer(int MANUFACTURER_ID){
        this.MANUFACTURER_ID = MANUFACTURER_ID;
    }

    public int getMANUFACTURER_ID() {
        return MANUFACTURER_ID;
    }

    public void setMANUFACTURER_ID(int MANUFACTURER_ID) {
        this.MANUFACTURER_ID = MANUFACTURER_ID;
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
    

    public String getADDRESSLINE2() {
        return ADDRESSLINE2;
    }

    public void setADDRESSLINE2(String ADDRESSLINE2) {
        this.ADDRESSLINE2 = ADDRESSLINE2;
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
    
    public int getZIP() {
    return ZIP;
    }

    public void setZIP(int ZIP) {
        this.ZIP = ZIP;
    }
    
    public String getPHONE() {
        return PHONE;
    }

    public void setPHONE(String PHONE) {
        this.PHONE = PHONE;
    }

    public String getFAX() {
        return FAX;
    }

    public void setFAX(String FAX) {
        this.FAX = FAX;
    }
    

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getREP() {
        return REP;
    }

    public void setREP(String REP) {
        this.REP = REP;
    }
    
    public Collection<Product> getProductCollection() {
        return productCollection;
    }

    public void setProductCollection(Collection<Product> productCollection) {
        this.productCollection = productCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (MANUFACTURER_ID != null ? MANUFACTURER_ID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Manufacturer)) {
            return false;
        }
        Manufacturer other = (Manufacturer) object;
        if ((this.MANUFACTURER_ID == null && other.MANUFACTURER_ID != null) || (this.MANUFACTURER_ID != null && !this.MANUFACTURER_ID.equals(other.MANUFACTURER_ID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Manufacturer[ manufacturerId=" + MANUFACTURER_ID + " ]";
    }    
}
