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

    int iD;
    Grid_index index;
    private int pocet_objektov;
    private List<Real_Page> obsah;
    private boolean zmeneny;


    public Virtual_Page(Grid_index index, int iD) {
        this.iD = iD;
        this.index = index;
        pocet_objektov = 0;
        List<Real_Page> obsah = new ArrayList<Real_Page>();
        Real_Page page = new Real_Page(iD, index.kapacita);
        obsah.add(page);
        zmeneny = true;


    }

    public Virtual_Page(ArrayList<Real_Page> obsah, int iD, Grid_index index) {
        this.index = index;
        zmeneny = false;
        this.iD = iD;
        this.obsah = obsah;
    }

    public void prida_RealPage(Point p) {
        index.pocetStranok++;
        Real_Page nova = new Real_Page(index.pocetStranok, index.kapacita);
        nova.add(p);
        pocet_objektov++;
        zmeneny = true;
        obsah.add(nova);
    }

    //objekty triedy VP si pridavaju Pointy do seba same, Cache nad nimi len zavola metody add.

    public void add(Point p) {
        if (obsah.get(obsah.size() - 1).isVojde()) {
            obsah.get(obsah.size() - 1).add(p);
            pocet_objektov++;
            zmeneny = true;
        }
        prida_RealPage(p);
    }

    public List<Real_Page> getObsah() {
        return obsah;
    }

    public void setObsah(List<Real_Page> obsah) {
        this.obsah = obsah;
    }
}
