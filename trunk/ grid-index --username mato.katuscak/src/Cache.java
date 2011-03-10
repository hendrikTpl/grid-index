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
        offset_map = new HashMap<Integer, Virtual_Page>(kapacita + 1);
        offset = new LinkedList<Integer>();
        platne_stranky = new HashSet<Integer>();
    }

    public Virtual_Page getVirtual_Page(Grid_index index, int iD) {
        Virtual_Page page;
        if ((page = offset_map.get(iD)) != null) {
            return page;
        } else if (platne_stranky.contains(iD)) {
            page = loadPage(index, iD);
            putPage(page);
        } else {
            page = new Virtual_Page(index, iD);
            putPage(page);
            return page;

        }


        return page;
    }

    public void open(boolean newIndex) {

        try {
            if (!file.exists()) {
                if (newIndex) {
                    file.createNewFile();
                    raf = new RandomAccessFile(file, "rw");
                    raf.setLength(0);
                } else {
                    raf = new RandomAccessFile(file, "rw");
                }
            } else {
                if (newIndex) {
                    raf = new RandomAccessFile(file, "rw");
                    raf.setLength(0);
                } else {
                    raf = new RandomAccessFile(file, "rw");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        channel = raf.getChannel();
        buffer = ByteBuffer.allocateDirect(pageSize);


    }

    public boolean isOpen() {

		return raf != null;
	}

    public void close() {

        for (Virtual_Page page : offset_map.values()) {
            if (page.isZmeneny()) {
                savepage(page);
            }
        }
        offset_map.clear();
        offset.clear();
        buffer = null;
        try {
            channel.close();
            channel = null;
            raf.close();
            raf = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void putPage(Virtual_Page page) {
        if (offset_map.size() == kapacita) {
            Virtual_Page removedPage = offset_map.remove(offset.removeLast());
            if (removedPage.isZmeneny()) {
                savepage(removedPage);
            }
        }
        offset_map.put(page.iD, page);
        offset.addFirst(page.iD);

    }
    //TREBA DOKONCIT SAVE NA ZAKLADE UKLADANIA STRANOK REAL A VIRTUAL PAGU

    public void savepage(Virtual_Page page) {
        buffer.clear();
        buffer.putInt(page.getObsah().size());
        for (int i = 1; i < page.getObsah().size(); i++) {
            buffer.putInt(page.getObsah().get(i).iD);
        }
        for (int i = 0; i < page.getObsah().size(); i++) {
            page.getObsah().get(i).save(buffer);
            buffer.rewind();
            try {
                channel.write(buffer, page.getObsah().get(i).iD * pageSize);
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            buffer.clear();
        }
        platne_stranky.add(page.iD);

    }

    public Virtual_Page loadPage(Grid_index index, int iD) {
        buffer.clear();
        try {
            channel.read(buffer, iD * pageSize);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        buffer.rewind();
        int pocet_Real_Page = buffer.getInt();
        ArrayList<Integer> zoznam_iD = new ArrayList<Integer>(pocet_Real_Page);
        zoznam_iD.add(iD);
        for (int i = 1; i < pocet_Real_Page; i++) {
            zoznam_iD.add(buffer.getInt());
        }
        ArrayList<Real_Page> zoznam = new ArrayList<Real_Page>(pocet_Real_Page);
        zoznam.add(new Real_Page(buffer, iD, index.pocet_suradnic, index.kapacita));
        for (int i = 1; i < pocet_Real_Page; i++) {
            buffer.clear();
            try {
                channel.read(buffer, zoznam_iD.get(i) * pageSize);
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            buffer.rewind();
            zoznam.add(new Real_Page(buffer, zoznam_iD.get(i), index.pocet_suradnic, index.kapacita));


        }

        return new Virtual_Page(zoznam, iD, index);


    }

}
