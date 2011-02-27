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
public class Real_Page{

    private int iD;
    private List<Point> zoznam;
    private boolean zmeneny;
    private int horna_hranica;
    private boolean vojde;

    public Real_Page(int iD, int pocetObjektov) {
        this.iD = iD;
        horna_hranica = pocetObjektov;
        zoznam = new ArrayList<Point>();
        zmeneny = false;
        vojde = true;
    }

    public void save(ByteBuffer bb){
        bb.putInt(zoznam.size());
        for(Point p:zoznam){
            p.save(bb);
        }

    }

    public void add(Point p){
        zoznam.add(p);
        zmeneny = true;
        if(horna_hranica<zoznam.size())
            vojde = false;


    }

    public boolean isVojde() {
        return vojde;
    }

    public void setVojde(boolean vojde) {
        this.vojde = vojde;
    }
}
