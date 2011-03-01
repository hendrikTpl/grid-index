import java.awt.*;
import java.io.File;
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
    ArrayList<ArrayList<Double>> struktura;
    ArrayList<Integer> rozdelenie_indexu;
    int pocet_suradnic;
    int pocetStranok;
    int kapacita;
    Cache cache;


    public Grid_index(Index_Maker maker, File file) {
        velkost_stranky = maker.velkost_stranky;
        pocet_objektov = maker.pocet_objektov;
        struktura = maker.get_index_grid();
        rozdelenie_indexu = maker.getRozdelenieIndexu_pocetnost();
        pocet_suradnic = maker.pocet_suradnic;
        kapacita = maker.kapacita();
        pocetStranok = maker.pocetStranok();
        cache = new Cache(1000,velkost_stranky,file);

    }


    //metoda bude vykonavat pridavanie bodu do indexu,  najprv sa zavola metoda cislo_stranky ktora podla struktury
    //vyrata kde sa ma bod ulozit, nasledne sa zavola metoda objektu cache

    public void add(Point p) {

    }


    int cislo_stranky(Point p) {
        int result = 0;
        int[] koeficienty = new int[pocet_suradnic];
        int[] pomocne = new int[pocet_suradnic];
        pomocne[0] = 1;

        for (int i = 1; i < pocet_suradnic; i++) {
            pomocne[i] = pomocne[i - 1] * rozdelenie_indexu.get(i - 1);
        }

        c:
        for (int i = 0; i < pocet_suradnic; i++) {
            for (int j = rozdelenie_indexu.get(i) - 1; j > 0; j--) {
                if (struktura.get(i).get(j - 1) < p.getSuradnice().get(i)) {
                    koeficienty[i] = j;
                    continue c;
                }
            }
        }
        for (int i = 0; i < pocet_suradnic; i++) {
            result += pomocne[i] * koeficienty[i];
        }
        return result;
    }


}