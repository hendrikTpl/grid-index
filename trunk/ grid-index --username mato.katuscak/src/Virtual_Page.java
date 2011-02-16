import java.nio.ByteBuffer;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: re55095
 * Date: 15-Feb-2011
 * Time: 19:47:55
 * To change this template use File | Settings | File Templates.
 */
public class Virtual_Page {

    private long iD;
    private int fyzicka_kapacita;
    private int pocet_objektov=0;
    private ArrayList<Point> obsah = new ArrayList<Point>();
    private boolean zmeneny = false;
    private boolean preteceny = false;
    private long adresa_pretecenej_stranky;

    public Virtual_Page(int velkost_stranky, int iD){
        pocet_objektov=0;
        ArrayList<Point> obsah = new ArrayList<Point>();
        zmeneny = false;
        preteceny = false;
        adresa_pretecenej_stranky = -1;
        fyzicka_kapacita = velkost_stranky;
        this.iD = iD;
    }


    public void save(ByteBuffer bb){
        bb.putInt(pocet_objektov);
        for (Point p:obsah){
            p.save(bb);
        }
        
    }
    //objekty triedy VP si pridavaju Pointy do seba same, Cache nad nimi len zavola metody add.
    public void add(Point p){
        obsah.add(p);
        pocet_objektov++;
        zmeneny = true;

    }

}
