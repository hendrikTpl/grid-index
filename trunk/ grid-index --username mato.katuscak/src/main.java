import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
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

    public static void main(String[] args) throws IOException {

        System.out.println("HelloWorld");
        File f = new File("C:\\vstup.txt");
        File file = new File("C:\\test.index");
        File index_file = new File("C:\\index_file.idf");
        Point_Storage point = new Point_Storage(f);
        //Point_Storage point = new Point_Storage(300, 2);
        grid = new Index_Maker(2, 64, point);
        //System.out.println(grid.median(1));
        System.out.println(grid.pocetStranok());
        grid.getRozdelenieIndexu_median();
        System.out.println(grid.getRozdelenieIndexu_median());
        System.out.println(grid.getRozdelenieIndexu_pocetnost());
        System.out.println(grid.pocet_roznych_hodnot(0));
        System.out.println(grid.pocet_roznych_hodnot(1));
        System.out.println(grid.get_index_grid());
        for (int i = 0; i < grid.pocet_objektov; i++) {
            System.out.println(new Grid_index(grid, file).cislo_stranky(grid.storage.getPoints().get(i)));

        }
        Grid_index index = new Grid_index(grid, file);
        index.open();
        for (int i = 0; i < grid.pocet_objektov; i++) {
            index.add(grid.storage.getPoints().get(i));

        }

        //index.add(grid.storage.getPoints().get(0));
        //index.add(grid.storage.getPoints().get(2));
        index.close();
        index.serializeTo(index_file);

        Grid_index index2 = Grid_index.serializeFrom(index_file);
        index2.open();

        for (Integer i : index2.cache.platne_stranky) {
            Virtual_Page page = index2.cache.loadPage(index, i);
            System.out.println(page.getObsah());

        }
        index2.close();
        //index.open();


        //for (Integer i : index.cache.platne_stranky) {
        //  Virtual_Page page = index.cache.loadPage(index, i);
        // System.out.println(page.getObsah());

        //}
        //ArrayList<Double> dlzky = new ArrayList<Double>();
        //dlzky.add(0.);
        //dlzky.add(0.);
        //System.out.println("Vysledok hladania"+ index.hladaj_Rectangle(grid.getStorage().getPoints().get(0),dlzky));
        //index.close();

        //test na skusanie zapisu :)

        //File subor = new File("C:/1.moja");


        //write_toFile(subor);
        // read_file(subor);


    }

   
}
