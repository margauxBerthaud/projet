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
public class MicroMarket {
    public String ZIP_CODE;
    public Double RADIUS;
    public Double AREA_LENGTH;
    public Double AREA_WIDTH;
    
    public MicroMarket(String ZIP_CODE) {
        this.ZIP_CODE = ZIP_CODE;
    }

    public String getZIP_CODE() {
        return ZIP_CODE;
    }

    public void setZIP_CODE(String ZIP_CODE) {
        this.ZIP_CODE = ZIP_CODE;
    }

    public Double getRADIUS() {
        return RADIUS;
    }

    public void setRadius(Double RADIUS) {
        this.RADIUS = RADIUS;
    }

    public Double getAREA_LENGTH() {
        return AREA_LENGTH;
    }

    public void setAREA_LENGTH(Double AREA_LENGTH) {
        this.AREA_LENGTH = AREA_LENGTH;
    }

    public Double getAREA_WIDTH() {
        return AREA_WIDTH;
    }

    public void setAREA_WIDTH(Double AREA_WIDTH) {
        this.AREA_WIDTH = AREA_WIDTH;
    }
    
     @Override
    public int hashCode() {
        int hash = 0;
        hash += (ZIP_CODE != null ? ZIP_CODE.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MicroMarket)) {
            return false;
        }
        MicroMarket other = (MicroMarket) object;
        if ((this.ZIP_CODE == null && other.ZIP_CODE != null) || (this.ZIP_CODE != null && !this.ZIP_CODE.equals(other.ZIP_CODE))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.MicroMarket[ zipCode=" + ZIP_CODE + " ]";
    }
    
}
