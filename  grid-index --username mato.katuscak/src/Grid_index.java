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
        int pocet = 1;
        for (int i = 0; i < rozdelenie_indexu.size(); i++) {
            pocet *= rozdelenie_indexu.get(i);
        }
        pocetStranok = pocet;
        cache = new Cache(100, velkost_stranky, file);
        cache.open(true);
        cache.close();


    }

    public void open() {
        cache.open(false);
    }

    public void open(File file) {
        cache.setFile(file);
        cache.open(false);
    }

    public void close() {
        cache.close();
    }

    public boolean isOpen() {
        return cache.isOpen();
    }

    //metoda bude vykonavat pridavanie bodu do indexu,  najprv sa zavola metoda cislo_stranky ktora podla struktury
    //vyrata kde sa ma bod ulozit, nasledne sa zavola metoda objektu cache

    public void add(Point p) {
        cache.getVirtual_Page(this, cislo_stranky(p)).add(p);
        pocet_objektov++;

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


    public List<Point> hladaj_Rectangle(Point dolny_roh, List<Double> dlzdky) {
        Set<Integer> stranky = new HashSet<Integer>();
        ArrayList<Point> result = new ArrayList<Point>();
        int[] pomocne = new int[pocet_suradnic];
        pomocne[0] = 1;

        int[] d_h = new int[pocet_suradnic];
        int[] h_h = new int[pocet_suradnic];
        int[] postupnost = new int[pocet_suradnic];

        c:
        for (int i = 0; i < pocet_suradnic; i++) {
            for (int j = rozdelenie_indexu.get(i) - 1; j > 0; j--) {
                if (struktura.get(i).get(j - 1) < dolny_roh.getSuradnice().get(i)) {
                    d_h[i] = j;
                    break;
                }
            }

            for (int j = rozdelenie_indexu.get(i) - 1; j > 0; j--) {
                if (struktura.get(i).get(j - 1) < (dolny_roh.getSuradnice().get(i)) + dlzdky.get(i)) {
                    h_h[i] = j;
                    continue c;
                }
            }
        }


        for (int i = 1; i < pocet_suradnic; i++) {
            pomocne[i] = pomocne[i - 1] * rozdelenie_indexu.get(i - 1);
        }

        generuj_range(0, h_h, d_h, postupnost, result,pomocne);

        return result;
    }

    private void generuj_range(int n, int[] h_h, int[] d_h, int[] postupnost, List<Point> result,int[] pomocne) {

        for (int i = d_h[n]; i <= h_h[n]; i++) {
            postupnost[n] = i;
            if (n + 1 == pocet_suradnic) {
                int cislo_stranky = 0;
                for (int j = 0; j < pocet_suradnic; j++)
                    cislo_stranky+=pomocne[j]*postupnost[j];    
                System.out.println("Cisla na stiahnutie "+cislo_stranky);

            } else {
                generuj_range(++n, h_h, d_h, postupnost, result,pomocne);
                n = 0;
            }

        }

    }


}
