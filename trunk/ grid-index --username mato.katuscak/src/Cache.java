import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * Created by IntelliJ IDEA.
 * User: re55095
 * Date: 15-Feb-2011
 * Time: 19:46:14
 * To change this template use File | Settings | File Templates.
 */
public class Cache {

    private HashMap<Long,Virtual_Page> offset_map;
    private LinkedList<Long> offset;
    private int kapacita;
    private int pageSize;
    private File file;
    private RandomAccessFile raf;
    private ByteBuffer buffer;
    private FileChannel channel;
    private HashSet<Integer> platne_stranky;


    


}
