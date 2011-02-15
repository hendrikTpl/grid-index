import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by IntelliJ IDEA.
 * User: re55095
 * Date: 15-Feb-2011
 * Time: 19:46:14
 * To change this template use File | Settings | File Templates.
 */
public class Cache {

    HashMap<Long,Virtual_Page> offset_map;
    LinkedList<Long> offset;
    int kapacita;
    int pageSize;
    private File file;
    private RandomAccessFile raf;
    private ByteBuffer buffer;
    private FileChannel channel;
}
