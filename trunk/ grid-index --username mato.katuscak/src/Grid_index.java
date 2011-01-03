import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: re55095
 * Date: 09-Dec-2010
 * Time: 12:17:11
 * To change this template use File | Settings | File Templates.
 */
public class Grid_index {

    int velkost_stranky;
    int pocet_objektov;
    int pocet_suradnic;
    Point_Storage storage;
    List<Integer> pocet_deleni;

    public int getVelkost_stranky() {
        return velkost_stranky;
    }

    public Point_Storage getStorage() {
        return storage;
    }

    public List<Integer> getPocet_deleni() {
        return pocet_deleni;
    }

    private boolean kontrola(){
        boolean resutl = false;
        int temp = 1;
        for(int i = 0; i < pocet_deleni.size(); i++ ){
            temp *= pocet_deleni.get(i);
        }
        if(temp >= pocetStranok())
            resutl = true;

        return resutl;
    }

    public void setPocet_deleni() {

        int priemer =(int) Math.pow(pocetStranok(),1./pocet_suradnic);
        pocet_deleni = new ArrayList<Integer>();
        ArrayList<Double> mediany = new ArrayList<Double>(pocet_suradnic);
        for(int i=0; i<pocet_suradnic; i++){
            pocet_deleni.add(priemer);
            mediany.add(median(i));
        }
        while(!kontrola()){
            int index_m = mediany.indexOf(Collections.max(mediany));
            mediany.set(index_m,0.0);
            pocet_deleni.set(index_m,pocet_deleni.get(index_m)+1);
        }


    }

    public Grid_index(int pocet_objektov, int pocet_suradnic, int velkost_stranky){
        this.pocet_objektov = pocet_objektov;
        this.pocet_suradnic = pocet_suradnic;
        this.velkost_stranky = velkost_stranky;
        create_Index();
    }

    public void create_Index(){
        storage = new Point_Storage(pocet_objektov,pocet_suradnic);
       
    }

    public int getPocet_objektov() {
        return pocet_objektov;

    }

    public void setPocet_objektov(int pocet_objektov) {
        this.pocet_objektov = pocet_objektov;
    }

    public int getPocet_suradnic() {
        return pocet_suradnic;
    }

    public void setPocet_suradnic(int pocet_suradnic) {
        this.pocet_suradnic = pocet_suradnic;
    }


    public int pocetStranok(){
        int result = 0;
        int kapacita = velkost_stranky/storage.getPoints().get(0).size();
        result = 1+(int) Math.round((pocet_objektov / kapacita)*1.3);
        return result;
    }




    public double median (int suradnica){
        ArrayList<Double> temp = new ArrayList<Double>(pocet_objektov);
        for (int i = 0; i < pocet_objektov; i++){
            temp.add(storage.getPoints().get(i).getSuradnice().get(suradnica));
        }
        Collections.sort(temp);
        double result = 0;
        if( (pocet_objektov % 2) == 1 )
            result =  temp.get(pocet_objektov / 2);
        else {
            double a = temp.get(pocet_objektov / 2);
            double b = temp.get(pocet_objektov / 2 - 1);

            result = ((a + b) / 2);
        }
    return result;
    }
}
