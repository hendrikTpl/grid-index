import java.nio.ByteBuffer;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: re55095
 * Date: 15-Feb-2011
 * Time: 19:47:55
 * To change this template use File | Settings | File Templates.
 */
public class Virtual_Page implements Comparable {

    long iD;
    int fyzicka_kapacita;
    int pocet_objektov;
    ArrayList<Point> obsah;
    boolean zmeneny;
    boolean preteceny;
    long adresa_pretecenej_stranky;


    public void save(ByteBuffer bb){
        
    }

    public int compareTo(Object o) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
