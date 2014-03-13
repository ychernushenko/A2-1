package orders;



import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author yingda
 */
public class ResultWithErr implements Serializable{
    private ArrayList<Inventory> res;
    private String errMsg;

    public ResultWithErr(){
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
} 
