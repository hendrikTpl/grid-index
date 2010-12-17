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

    int pocet_objektov;
    int pocet_suradnic;
    Point_Storage storage;
    List<Integer> pocet_deleni;




    public Grid_index(int pocet_objektov, int pocet_suradnic) {
        this.pocet_objektov = pocet_objektov;
        this.pocet_suradnic = pocet_suradnic;
        create_Index();
    }

    public void create_Index(){
        storage = new Point_Storage(pocet_objektov,pocet_suradnic);
        System.out.println(storage.toString());
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
        result = (int) Math.round((pocet_objektov / 100)*1.3);
        return result;
    }


    public double median (int suradnica){
        double result = 0;
        if( (pocet_objektov % 2) == 1 )
            result =  storage.getPoints().get(pocet_objektov / 2 ).getSuradnice().get(suradnica);
        else {
            double a = storage.getPoints().get(pocet_objektov / 2).getSuradnice().get(suradnica);
            double b = storage.getPoints().get(pocet_objektov / 2 - 1).getSuradnice().get(suradnica);

            result = ((a + b) / 2);
        }
    return result;
    }
}
