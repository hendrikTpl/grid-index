import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: re55095
 * Date: 09-Dec-2010
 * Time: 09:31:49
 * To change this template use File | Settings | File Templates.
 */
public class main {
    static Index_Maker grid;

    public static void main(String[] args) throws Exception {

//        System.out.println("HelloWorld");
//        File f = new File("C:\\1000000_10_exp.txt");
//        File file = new File("C:\\test.index");
        File index_file = new File("C:\\Testy\\1000000_5_gauss.idf");
        //Point_Storage point = new Point_Storage(f);
//        Point_Storage point = new Point_Storage(2000000, 10);
//        grid = new Index_Maker(10, 1500, point);
//
//        System.out.println(grid.pocetStranok());
//
//        //System.out.println(grid.getRozdelenieIndexu_pocetnost());
//        //System.out.println(grid.pocet_roznych_hodnot(0));
//        //System.out.println(grid.pocet_roznych_hodnot(1));
//        //System.out.println(grid.pocet_roznych_hodnot(2));
//        //System.out.println(grid.get_index_grid());
//
//        Grid_index index = new Grid_index(grid, file);
//        index.open();
////        for (int i = 0; i < grid.pocet_objektov; i++) {
////            //System.out.println(index.cislo_stranky(grid.storage.getPoints().get(i)));
////            //System.out.println(grid.storage.getPoints().get(i));
////        }
//        System.out.println("Pocet Objektov index1 " + grid.pocet_objektov);
//        for (int i = 0; i < grid.pocet_objektov; i++) {
//            index.add(grid.storage.getPoints().get(i));
//
//        }
//
//        //index.add(grid.storage.getPoints().get(0));
//        //index.add(grid.storage.getPoints().get(2));
//        index.close();
//        index.open();
//        int pocet = 0;
//
//        for (Integer i : index.cache.platne_stranky) {
//            Virtual_Page page = index.cache.loadPage(index, i);
//            //System.out.println(page.getObsah());
//            for (Real_Page pp : page.getObsah()) {
//                for (Point p : pp.getZoznam()) {
//                    pocet++;
//                }
//            }
//
//        }
//        System.out.println("Pocet realne v indexe " + pocet);
//        index.close();
//        index.serializeTo(index_file);
//        PrintWriter pw2 = new PrintWriter(new File("C:\\1000000n_10.txt"));
//        for(int i = 0; i<1000000; i++){
//            for(int j = 0; j<10; j++){
//                  int temp = (int)(Math.random()*1000);
//                  if(j<4)
//                    pw2.print("0,"+temp+" ");
//                  if(j==4)
//                    pw2.print("0,"+temp);
//            }
//            pw2.println();
//        }
//        pw2.close();


        Grid_index index2 = Grid_index.serializeFrom(index_file);
        index2.open();

//        Generator.generateUniformDouble(100000,5,-3,5,new File("C:\\100000_5_un.txt"));
//        Generator.generateUniformDouble(100000,10,-3,5,new File("C:\\100000_10_un.txt"));
//        int[] body = new int[]{0};
//        double [] hranice = new double[]{0};
//        Generator.generateGaussianDouble(100000,5,body,1,new File("C:\\100000_5_gauss.txt"));
//        Generator.generateGaussianDouble(100000,10,body,1,new File("C:\\100000_10_gauss.txt"));
//        Generator.generateExponentialDouble(1000000,5,hranice,1,1,new File("C:\\1000000_5_exp.txt"));
//        Generator.generateExponentialDouble(1000000,10,hranice,1,1,new File("C:\\1000000_10_exp.txt"));
//        Generator.generateExponentialDouble(100000,5,hranice,1,1,new File("C:\\100000_5_exp.txt"));
//        Generator.generateExponentialDouble(100000,10,hranice,1,1,new File("C:\\100000_10_exp.txt"));
        //System.out.println("Pocet realne v indexe " + pocet);
        System.out.println("Pocet Objektov index2 " + index2.pocet_objektov);

        index2.close();
        index2.open();


        System.out.println("Pocet vs. stranok " + index2.pocetStranok);

        ArrayList<Double> dlzky = new ArrayList<Double>();
        dlzky.add(0.9);
        dlzky.add(0.9);
        dlzky.add(0.9);
        dlzky.add(1.3);
        dlzky.add(1.4);

        ArrayList<Double> suradnice = new ArrayList<Double>();
        suradnice.add(-1.);
        suradnice.add(-1.);
        suradnice.add(0.);
        suradnice.add(0.);
        suradnice.add(0.);
        Point p = new Point(1, suradnice);

        long start = System.currentTimeMillis();


        //System.out.println("Vysledok hladania" + index2.hladaj_Rectangle(p, dlzky));
        System.out.println("Pocet objektov po kontrole hladania " + index2.hladaj_Rectangle(p, dlzky).size());
        long end = System.currentTimeMillis();

        System.out.println("Execution time was " + (end - start) + " ms.");

        index2.close();


    }


}
