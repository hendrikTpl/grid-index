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

    private List<Real_Page> obsah;
    private boolean zmeneny;


    public Virtual_Page(Grid_index index, int iD) {
        this.iD = iD;
        this.index = index;
        obsah = new ArrayList<Real_Page>();
        Real_Page page = new Real_Page(iD, index.kapacita );
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
        obsah.add(nova);
        nova.add(p);

        zmeneny = true;

        kontrola_pretecenia(p);
    }

    private void kontrola_pretecenia(Point p) {

        int first_page_size = 4+4*(obsah.size()-1)+4+obsah.get(0).getZoznam().size()*p.size();
        System.out.println("Velkost 1 strany"+first_page_size);
        if(index.velkost_stranky <= first_page_size){
            Point remove = obsah.get(0).getZoznam().get(0);
            obsah.get(0).getZoznam().remove(remove);
            obsah.get(obsah.size()-1).add(remove);
        }
    }

    //objekty triedy VP si pridavaju Pointy do seba same, Cache nad nimi len zavola metody add.

    public void add(Point p) {
        Real_Page page = obsah.get(obsah.size() - 1);
        if (page.isVojde()) {
            page.add(p);

            zmeneny = true;
        } else {
            prida_RealPage(p);
        }
    }

    public boolean isZmeneny() {
        return zmeneny;
    }

    public void setZmeneny(boolean zmeneny) {
        this.zmeneny = zmeneny;
    }

    public List<Real_Page> getObsah() {
        return obsah;
    }

    public void setObsah(List<Real_Page> obsah) {
        this.obsah = obsah;
    }


}
