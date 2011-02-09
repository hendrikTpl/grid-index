import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.util.*;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: re55095
 * Date: 09-Dec-2010
 * Time: 09:31:49
 * To change this template use File | Settings | File Templates.
 */
public class main {
   static Index_Maker grid;

    public static void main (String[] args) throws IOException {

        System.out.println("HelloWorld");
        grid = new Index_Maker(50,4,60);
        System.out.println(grid.median(1));
        System.out.println(grid.pocetStranok());
        grid.setPocet_deleni();
        System.out.println(grid.getPocet_deleni());
        System.out.println(grid.getRozdelenieIndexu());
        System.out.println(grid.get_index_grid());
        
        //test na skusanie zapisu :)

        //File subor = new File("C:/1.moja");


       //write_toFile(subor);
      // read_file(subor);



     





    }

    public static void read_file(File f) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(f, "rw");
        FileChannel channel = raf.getChannel();
        ByteBuffer baf = ByteBuffer.allocateDirect(grid.getVelkost_stranky());
        channel.read(baf);
        baf.rewind();
        /*
        for(int i = 0; i <5;i++){
             int p = baf.getInt();
            System.out.println(p);
        }*/

        for(int i = 0; i<grid.getPocet_objektov(); i++){
            ArrayList<Double> temp = new ArrayList<Double>();
            int iD = baf.getInt();
            for(int j=0; j<grid.pocet_suradnic;j++){
                temp.add(baf.getDouble());

            }

            Point p= new Point(iD,temp);
            System.out.println(p);
        }
        baf.clear();
        channel.close();
    }

    public static void write_toFile(File f) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(f,"rw");
        raf.setLength(grid.getVelkost_stranky()*grid.pocetStranok());
        FileChannel channel = raf.getChannel();
        System.out.println(channel.size());
        ByteBuffer bb = ByteBuffer.allocateDirect(grid.getVelkost_stranky());
        /*for (int i = 0; i < 5; i++){
            bb.putInt(i);
        }*/
        for(int i = 0; i<grid.pocet_objektov;i++){
            grid.getStorage().getPoints().get(i).save(bb);
        }
        bb.rewind();
        channel.write(bb);
        channel.close();
        raf.close();

    }
}
