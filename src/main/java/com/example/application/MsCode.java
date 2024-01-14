package com.example.application;

import java.util.ArrayList;
import java.util.UUID;

public class MsCode {
     String id;
     String Year;
     String EstCode;
     String Flag;

     public MsCode(String id, String year, String estCode, String flag) {
          this.id = id;
          Year = year;
          EstCode = estCode;
          Flag = flag;
     }

     public String getId() {
          return id;
     }

     public void setId(String id) {
          this.id = id;
     }

     public String getYear() {
          return Year;
     }

     public void setYear(String year) {
          Year = year;
     }

     public String getEstCode() {
          return EstCode;
     }

     public void setEstCode(String estCode) {
          EstCode = estCode;
     }

     public String getFlag() {
          return Flag;
     }

     public void setFlag(String flag) {
          Flag = flag;
     }

     public static ArrayList<MsCode> getData() {
          ArrayList<MsCode> data = new ArrayList<MsCode>();
          data.add(new MsCode(UUID.randomUUID().toString(), "2007", "P", "EU"));
          data.add(new MsCode(UUID.randomUUID().toString(), "2007", "null", "ES"));
          data.add(new MsCode(UUID.randomUUID().toString(), "2007", "D", "DE"));
          return data;
     }

}
