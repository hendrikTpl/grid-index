import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by IntelliJ IDEA.
 * User: re55095
 * Date: 09-Dec-2010
 * Time: 12:09:10
 * To change this template use File | Settings | File Templates.
 */
public class Point_Storage {

    private ArrayList<Point> points;
    private int pocet_objektov;

    public Point_Storage(ArrayList<Point> points, int pocet_objektov) {
        this.points = points;
        this.pocet_objektov = pocet_objektov;
    }

    public Point_Storage(int pocet_objektov, int pocet_suradnic){
        this.pocet_objektov = pocet_objektov;
        points = new ArrayList<Point>();
        for (int i = 0; i < pocet_objektov; i++){
            points.add(new Point(pocet_suradnic,i));
        }

    }

    public Point_Storage(File f) throws FileNotFoundException {
        points = new ArrayList<Point>();
        Scanner sc = new Scanner(f);
        Scanner lines;
        int iD = 0;
        while (sc.hasNextLine()){
            String line = sc.nextLine();
            lines = new Scanner(line);

            ArrayList<Double> suradnice = new ArrayList<Double>();
            while(lines.hasNext()){
                String a = lines.next();
                //double cislo = lines.nextDouble();
                double cislo = Double.valueOf(a);
                suradnice.add(cislo);
            }
            Point bod = new Point(iD,suradnice);
            points.add(bod);
            iD++;
        }
        this.pocet_objektov = this.points.size();
        sc.close();
    }

    @Override
    public String toString() {
        return points.toString();
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<Point> points) {
        this.points = points;
    }

    public int getPocet_objektov() {
        return pocet_objektov;
    }

    public void setPocet_objektov(int pocet_objektov) {
        this.pocet_objektov = pocet_objektov;
    }
}
