import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: re55095
 * Date: 15-Feb-2011
 * Time: 19:46:14
 * To change this template use File | Settings | File Templates.
 */
public class Cache {

    private Map<Integer, Virtual_Page> offset_map;
    private LinkedList<Integer> offset;
    private int kapacita;
    private int pageSize;
    private File file;
    private RandomAccessFile raf;
    private ByteBuffer buffer;
    private FileChannel channel;
    private HashSet<Integer> platne_stranky;


    public Cache(int kapacita, int pageSize, File file) {
        this.kapacita = kapacita;
        this.pageSize = pageSize;
        this.file = file;
        offset_map = new HashMap<Integer, Virtual_Page>();
        offset = new LinkedList<Integer>();
    }

    public Virtual_Page getVirtual_Page(Grid_index index, int iD) {
        Virtual_Page page;
        if ((page = offset_map.get(iD)) != null) {
            return page;
        } else if (platne_stranky.contains(iD)) {
            loadPage(index, iD);
        } else {
            page = new Virtual_Page(index, iD);
            return page;

        }


        return page;
    }


    public void putPage(Virtual_Page page) {
        offset_map.put(page.iD, page);
        offset.addFirst(page.iD);

    }
    //TREBA DOKONCIT SAVE NA ZAKLADE UKLADANIA STRANOK REAL A VIRTUAL PAGU
    public void savepage(Virtual_Page page) {
        buffer.clear();
        

    }

    public Virtual_Page loadPage(Grid_index index, int iD) {
        buffer.clear();
        try {
            channel.read(buffer, iD * pageSize);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        buffer.rewind();
        return new Virtual_Page(buffer, iD, index);


    }

}
