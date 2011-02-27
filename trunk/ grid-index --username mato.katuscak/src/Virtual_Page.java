import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: re55095
 * Date: 15-Feb-2011
 * Time: 19:47:55
 * To change this template use File | Settings | File Templates.
 */
public class Virtual_Page {

    private int iD;
    private int fyzicka_kapacita;
    private int pocet_objektov;
    private List<Real_Page> obsah;
    private boolean zmeneny = false;
    private int kapacita;

    public Virtual_Page(int velkost_stranky, int iD, int kapacita){
        pocet_objektov=0;
        List<Real_Page> obsah = new ArrayList<Real_Page>();
        Real_Page page = new Real_Page(iD,kapacita);
        obsah.add(page);
        zmeneny = true;
        fyzicka_kapacita = velkost_stranky;
        this.iD = iD;
        this.kapacita = kapacita;
    }




    public void save(ByteBuffer bb){
        for (Real_Page p:obsah){
            p.save(bb);
        }
        
    }
    //objekty triedy VP si pridavaju Pointy do seba same, Cache nad nimi len zavola metody add.
    public void add(Point p){
        if(obsah.get(obsah.size()-1).isVojde()){
            obsah.get(obsah.size()-1).add(p);
            pocet_objektov++;
            zmeneny = true;
        }
        Real_Page nova = new Real_Page(iD,kapacita);
        nova.add(p);
        pocet_objektov++;
        zmeneny = true;
        obsah.add(nova);


    }

}
