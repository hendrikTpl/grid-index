import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: opteron1441
 * Date: 21.12.2010
 * Time: 19:40
 * To change this template use File | Settings | File Templates.
 */
public class Index_Maker {
    int velkost_stranky;
    int pocet_objektov;
    int pocet_suradnic;
    Point_Storage storage;
    ArrayList<Integer> rozdelenie;



    public Index_Maker(int pocet_objektov, int pocet_suradnic, int velkost_stranky) {
        this.pocet_objektov = pocet_objektov;
        this.pocet_suradnic = pocet_suradnic;
        this.velkost_stranky = velkost_stranky;

        create_Index();
    }

    public Index_Maker(int pocet_suradnic, int velkost_stranky, Point_Storage storage) {

        this.pocet_suradnic = pocet_suradnic;
        this.velkost_stranky = velkost_stranky;
        this.storage = storage;
        this.pocet_objektov = storage.getPocet_objektov();

    }


    public int getVelkost_stranky() {
        return velkost_stranky;
    }

    public Point_Storage getStorage() {
        return storage;
    }


    private boolean kontrola(ArrayList<Integer> pom) {
        boolean resutl = false;
        int temp = 1;
        for (int i = 0; i < pom.size(); i++) {
            temp *= pom.get(i);
        }
        if (temp >= pocetStranok())
            resutl = true;

        return resutl;
    }


    public void create_Index() {
        storage = new Point_Storage(pocet_objektov, pocet_suradnic);

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

    public int kapacita(){
        int kapacita = velkost_stranky / (storage.getPoints().get(0).size()+4);

        return  kapacita ;
    }

    public void setPocet_suradnic(int pocet_suradnic) {
        this.pocet_suradnic = pocet_suradnic;
    }


    public int pocetStranok() {
        int result = 0;
        int kapacita = velkost_stranky / storage.getPoints().get(0).size();
        result = 1 + (int) Math.round((pocet_objektov / kapacita) * 1.3);
        return result;
    }


    public double median(int suradnica) {
        ArrayList<Double> temp = new ArrayList<Double>(pocet_objektov);
        for (int i = 0; i < pocet_objektov; i++) {
            temp.add(storage.getPoints().get(i).getSuradnice().get(suradnica));
        }
        Collections.sort(temp);
        double result = 0;
        if ((pocet_objektov % 2) == 1)
            result = temp.get(pocet_objektov / 2);
        else {
            double a = temp.get(pocet_objektov / 2);
            double b = temp.get(pocet_objektov / 2 - 1);

            result = ((a + b) / 2);
        }
        return result;
    }

    public int pocet_roznych_hodnot(int suradnica) {
        int result = 0;
        Set<Double> temp2 = new HashSet<Double>();

        for (int i = 0; i < pocet_objektov; i++) {
            temp2.add(storage.getPoints().get(i).getSuradnice().get(suradnica));
        }

        result = temp2.size();
        return result;
    }

    public ArrayList<Integer> getRozdelenieIndexu_median() {
        ArrayList<Integer> pocet_deleni = new ArrayList<Integer>(pocet_objektov);
        int priemer = (int) Math.pow(pocetStranok(), 1. / pocet_suradnic);
        pocet_deleni = new ArrayList<Integer>();
        ArrayList<Double> mediany = new ArrayList<Double>(pocet_suradnic);
        for (int i = 0; i < pocet_suradnic; i++) {
            pocet_deleni.add(priemer);
            mediany.add(median(i));
        }
        while (!kontrola(pocet_deleni)) {
            int index_m = mediany.indexOf(Collections.max(mediany));
            mediany.set(index_m, 0.0);
            pocet_deleni.set(index_m, pocet_deleni.get(index_m) + 1);
        }

        return pocet_deleni;
    }

    public ArrayList<Integer> getRozdelenieIndexu_pocetnost() {
        System.out.println("Pocitam pocetnost indexu");
        ArrayList<Integer> result = new ArrayList<Integer>(pocet_suradnic);
        int priemer = (int) Math.pow(pocetStranok(), 1. / pocet_suradnic);
        ArrayList<Integer> pocer_hodnot = new ArrayList<Integer>(pocet_suradnic);
        System.out.println("Mam priemer");
        for (int i = 0; i < pocet_suradnic; i++) {
            pocer_hodnot.add(pocet_roznych_hodnot(i));
            result.add(priemer);
        }
        System.out.println("Idem kontrolovat");
        while (!kontrola(result)) {
            int temo = pocer_hodnot.indexOf(Collections.max(pocer_hodnot));
            pocer_hodnot.set(temo, 0);
            result.set(temo, result.get(temo) + 1);
        }
        System.out.println(result);
        rozdelenie = result;
        return result;
    }


    public ArrayList<ArrayList<Double>> get_index_grid() {
        System.out.println("Idem rozdelit index");
        ArrayList<ArrayList<Double>> result = new ArrayList<ArrayList<Double>>();
        ArrayList<Integer> delenia = getRozdelenieIndexu_pocetnost();
        for (int i = 0; i < pocet_suradnic; i++) {
            ArrayList<Double> pas = new ArrayList<Double>();
            int objektov_v_pase = pocet_objektov / delenia.get(i);
            ArrayList<Double> pom = new ArrayList<Double>(pocet_objektov);
            for (int j = 0; j < pocet_objektov; j++) {
                pom.add(storage.getPoints().get(j).getSuradnice().get(i));
            }
            Collections.sort(pom);
            for (int h = 1; h < delenia.get(i); h++) {
                double jeden = pom.get(h * objektov_v_pase);
                double druhy = pom.get(h * objektov_v_pase + 1);
                pas.add((jeden + druhy) / 2);
            }
            result.add(pas);
        }

        return result;
    }
}
