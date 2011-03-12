import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: opteron1441
 * Date: 27.2.2011
 * Time: 15:52
 * To change this template use File | Settings | File Templates.
 */
public class Real_Page {

    int iD;
    private List<Point> zoznam;
    private boolean zmeneny;
    private int horna_hranica;
    private boolean vojde;


    public Real_Page(ByteBuffer bb, int iD, int pocet_rozmerov, int kapacita) {

        horna_hranica = kapacita;
        int pocet = bb.getInt();
        zoznam = new ArrayList<Point>();
        for (int i = 0; i < pocet; i++) {
            int iD_p = bb.getInt();
            ArrayList<Double> zoznam_suradnic = new ArrayList<Double>(pocet_rozmerov);
            for (int j = 0; j < pocet_rozmerov; j++) {

                zoznam_suradnic.add(bb.getDouble());
            }
            zoznam.add(new Point(iD_p, zoznam_suradnic));
        }
        zmeneny = false;
        vojde = true;
        if (zoznam.size() >= kapacita)
            vojde = false;

    }

    public List<Point> getZoznam() {
        return zoznam;
    }

    @Override
    public String toString() {
        return zoznam.toString();
    }

    public Real_Page(int iD, int pocetObjektov) {
        this.iD = iD;
        horna_hranica = pocetObjektov;
        zoznam = new ArrayList<Point>();
        zmeneny = true;
        vojde = true;
    }

    public void save(ByteBuffer bb) {
        bb.putInt(zoznam.size());
        for (Point p : zoznam) {
            p.save(bb);
        }

    }

    public void add(Point p) {
        zoznam.add(p);
        zmeneny = true;
        if (horna_hranica <= zoznam.size())
            vojde = false;


    }

    public boolean isVojde() {
        return vojde;
    }

    public void setVojde(boolean vojde) {
        this.vojde = vojde;
    }
}
