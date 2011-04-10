import com.sun.corba.se.spi.ior.Identifiable;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: re55095
 * Date: 09-Dec-2010
 * Time: 12:03:32
 * To change this template use File | Settings | File Templates.
 */
public class Point {

    private int iD;
    private List<Double> suradnice;

    @Override
    public String toString() {
        return suradnice.toString()+" iD " + iD;
    }

    public Point(int iD, List<Double> suradnice) {
        this.iD = iD;
        this.suradnice = suradnice;
    }


    public Point(int pocet_suradnic, int iD){
        this.iD = iD;
        suradnice = new ArrayList<Double>();
        double suradnica = 0;
        for (int i=0; i< pocet_suradnic; i++){
            int temp = (int)(Math.random()*100000);
            suradnica = (double)temp/100000;
            //System.out.print(suradnica+" ");
            suradnice.add(suradnica);
        }
        //System.out.println();
    }




    public int getiD() {
        return iD;
    }

    public void setiD(int iD) {
        this.iD = iD;
    }

    public List<Double> getSuradnice() {
        return suradnice;
    }

    public void setSuradnice(ArrayList<Double> suradnice) {
        this.suradnice = suradnice;
    }

    public int size(){
        return 4 + 8*suradnice.size();
    }

    public void save(ByteBuffer bb){
        bb.putInt(iD);
        for(int i = 0; i< suradnice.size(); i++){
            bb.putDouble(suradnice.get(i));
        }
    }
}
