/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package orders;

import java.io.Serializable;

/**
 *
 * @author yingda
 */
 public class Inventory implements Serializable{
        public String productCode;
        public String description;
        public String quantity;
        public String price;
        
        public Inventory(){
        }
        
        public Inventory(String s1, String s2, String s3, String s4){
            productCode = s1;
            description = s2;
            quantity = s3;
            price = s4;
        }
    }
