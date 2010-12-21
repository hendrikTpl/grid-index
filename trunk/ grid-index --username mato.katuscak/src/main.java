import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by IntelliJ IDEA.
 * User: re55095
 * Date: 09-Dec-2010
 * Time: 09:31:49
 * To change this template use File | Settings | File Templates.
 */
public class main {

    public static void main (String[] args) throws IOException {

        System.out.println("HelloWorld");
        Grid_index grid = new Grid_index(100,2);
        System.out.println(grid.median(1));
        System.out.println(grid.pocetStranok());


        //test na skusanie zapisu :)

        File subor = new File("C:/1.moja");


        write_toFile(subor);
        read_file(subor);



     





    }

    public static void read_file(File f) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(f, "rw");
        FileChannel channel = raf.getChannel();
        ByteBuffer baf = ByteBuffer.allocateDirect(20);
        channel.read(baf,10);
        baf.rewind();
        for(int i = 0; i <5;i++){
             int p = baf.getInt();
            System.out.println(p);
        }
        baf.clear();
        channel.close();
    }

    public static void write_toFile(File f) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(f,"rw");
        raf.setLength(40);
        FileChannel channel = raf.getChannel();
        System.out.println(channel.size());
        ByteBuffer bb = ByteBuffer.allocateDirect(20);
        for (int i = 0; i < 5; i++){
            bb.putInt(i);
        }
        bb.rewind();
        channel.write(bb,10);
        channel.close();
        raf.close();

    }
}
