package com.example.application;

import java.util.Objects;
import java.util.UUID;


public class MsCode {
     String id;
     String msCode;
     String year;
     float se;
     public float getSe() {
          return se;
     }

     public void setSe(float se) {
          this.se = se;
     }

     String estCode;
     float estimate;
     float lowerCIB;
     float upperCIB;
     String flag;

     public MsCode(String msCode, String year,float se, String estCode, float estimate, float lowerCIB,
               float upperCIB, String flag) {
          this.id = UUID.randomUUID().toString();
          this.se = se;
          this.msCode = msCode;
          this.year = year;
          this.estCode = estCode;
          this.estimate = estimate;
          this.lowerCIB = lowerCIB;
          this.upperCIB = upperCIB;
          this.flag = flag;
     }
     
     @Override
public boolean equals(Object obj) {
    if (obj == null) {
        return false;
    }
    if (obj == this) {
        return true;
    }
    if (obj instanceof MsCode) {
        MsCode other = (MsCode) obj;
        return Objects.equals(this.id, other.id)
            && Objects.equals(this.msCode, other.msCode)
            && Objects.equals(this.year, other.year)
            && Objects.equals(this.se, other.se)
            && Objects.equals(this.estCode, other.estCode)
            && Float.compare(this.estimate, other.estimate) == 0
            && Float.compare(this.lowerCIB, other.lowerCIB) == 0
            && Float.compare(this.upperCIB, other.upperCIB) == 0
            && Objects.equals(this.flag, other.flag);
    }
    return false;
}
     @Override
     public String toString() {
         return "MsCode{" +
                 "id='" + this.id + '\'' +
                 ", MsCode='" + this.msCode + '\'' +
                 ", year=" + this.year +
                 ", EstCode='" + this.estCode + '\'' +
                 ", SE = ' " + this.se + "'\'"+
                 ", Estimate=" + this.estimate +
                 ", LowerCIB=" + this.lowerCIB +
                 ", UpperCIB=" + this.upperCIB +
                 ", Flag='" + this.flag + '\'' +
                 '}';
     }

     public String getId() {
          return id;
     }

     public void setId(String id) {
          this.id = id;
     }

     public String getMsCode() {
          return msCode;
     }

     public void setMsCode(String msCode) {
          this.msCode = msCode;
     }

     public String getYear() {
          return year;
     }

     public void setYear(String year) {
          this.year = year;
     }

     public String getEstCode() {
          return estCode;
     }

     public void setEstCode(String estCode) {
          this.estCode = estCode;
     }

     public float getEstimate() {
          return estimate;
     }

     public void setEstimate(float estimate) {
          this.estimate = estimate;
     }

     public float getLowerCIB() {
          return lowerCIB;
     }

     public void setLowerCIB(float lowerCIB) {
          this.lowerCIB = lowerCIB;
     }

     public float getUpperCIB() {
          return upperCIB;
     }

     public void setUpperCIB(float upperCIB) {
          this.upperCIB = upperCIB;
     }

     public String getFlag() {
          return flag;
     }

     public void setFlag(String flag) {
          this.flag = flag;
     }

}
