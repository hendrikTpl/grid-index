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




    public Grid_index(int pocet_objektov, int pocet_suradnic) {
        this.pocet_objektov = pocet_objektov;
        this.pocet_suradnic = pocet_suradnic;
    }

    public void create_Index(){
        Point_Storage storage = new Point_Storage(pocet_objektov,pocet_suradnic);
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

    private double median (){
        double result = 0;

        return result;
    }
}
