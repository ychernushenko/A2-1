/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package inventoryMgmt;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Yury
 */
public class Result implements Serializable{
    private ArrayList<Inventory> res;
    private String errMsg;
    private Integer NumberOfChangedEntries;

    public Result(){
        res = new ArrayList<Inventory>();
        errMsg = "";
    }
    
    public ArrayList<Inventory> getRes(){
         return res;
    }
    public void setRes( ArrayList<Inventory> r){
         this.res = r;
    }
    
    public void addtoRes(String s1, String s2, String s3, String s4){
         this.res.add(new Inventory(s1,s2,s3,s4));
    }
    
    public String getErrMsg(){
         return errMsg;
    }
    public void setErrMsg(String s){
         this.errMsg = s;
    }
    public void setChanged(Integer i){
        NumberOfChangedEntries = i;
    }
    public Integer getChanged(){
        return NumberOfChangedEntries;
    }
}
