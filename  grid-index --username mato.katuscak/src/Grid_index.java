import java.io.*;
import java.util.*;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: re55095
 * Date: 09-Dec-2010
 * Time: 12:17:11
 * To change this template use File | Settings | File Templates.
 */
public class Grid_index implements Serializable, IGrid_Index {

    int velkost_stranky;
    int pocet_objektov;
    ArrayList<ArrayList<Double>> struktura;
    ArrayList<Integer> rozdelenie_indexu;
    int pocet_suradnic;
    int pocetStranok;
    int kapacita;
    int pocet_pristupov = 0;
    Cache cache;


    public Grid_index(Index_Maker maker, File file) {
        velkost_stranky = maker.velkost_stranky;
        pocet_objektov = maker.pocet_objektov;
        struktura = maker.get_index_grid();
        rozdelenie_indexu = maker.rozdelenie;
        pocet_suradnic = maker.pocet_suradnic;
        kapacita = maker.kapacita();
        int pocet = 1;
        for (int i = 0; i < rozdelenie_indexu.size(); i++) {
            pocet *= rozdelenie_indexu.get(i);
        }
        pocetStranok = pocet;
        cache = new Cache(1000, velkost_stranky, file);
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
        List<Point> result = new ArrayList<Point>();
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

        generuj_range(0, h_h, d_h, postupnost, result, pomocne);
        //System.out.println("Pred objektov pred kontrolou " + result.size());
        //System.out.println("Pred kontrolou " + result);
        result = kontrola_hranic_range(result, dlzdky, dolny_roh);

        return result;
    }

    private List<Point> kontrola_hranic_range(List<Point> zdroj, List<Double> dlzky, Point dolny_roh) {
        List<Point> result = new ArrayList<Point>();
        for (Point p : zdroj) {
            boolean plati = false;
            for (int i = 0; i < pocet_suradnic; i++) {
                if ((p.getSuradnice().get(i) >= dolny_roh.getSuradnice().get(i)) && (p.getSuradnice().get(i) <= (dolny_roh.getSuradnice().get(i) + dlzky.get(i)))) {
                    plati = true;
                } else {
                    plati = false;
                    break;
                }
            }
            if (plati)
                result.add(p);

        }
        return result;
    }


    private void generuj_range(int n, int[] h_h, int[] d_h, int[] postupnost, List<Point> result, int[] pomocne) {

        for (int i = d_h[n]; i <= h_h[n]; i++) {
            postupnost[n] = i;
            if (n + 1 == pocet_suradnic) {
                int cislo_stranky = 0;
                for (int j = 0; j < pocet_suradnic; j++)
                    cislo_stranky += pomocne[j] * postupnost[j];
                if (cache.platne_stranky.contains(cislo_stranky)) {
                    pocet_pristupov++;
                    Virtual_Page page = cache.loadPage(this, cislo_stranky);
                    for (Real_Page p : page.getObsah())
                        result.addAll(p.getZoznam());
                }


            } else {
                generuj_range(++n, h_h, d_h, postupnost, result, pomocne);
                n--;
            }

        }

    }

    public void serializeTo(File file) {

        boolean wasOpen = isOpen();

        if (wasOpen) {
            close();
        }
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(this);

            oos.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (wasOpen) {
            open();
        }
    }

    @SuppressWarnings("unchecked")
    public static Grid_index serializeFrom(File file) {

        Grid_index index = null;

        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);

            index = (Grid_index) ois.readObject();

            ois.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return index;
    }


}
