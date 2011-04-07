import java.io.File;
import java.io.PrintWriter;
import java.util.Random;


public class Generator {

    private static double getUniformDouble(double od, double po) {

        return Math.random() * (po - od) + od;
    }

    /**
     * cele cisla z intervalu [od; po)
     */
    public static void generateUniformLong(int pocet, int od, int po, File file) throws Exception {

        if (!file.exists()) {
            file.createNewFile();
        }
        PrintWriter pw = new PrintWriter(file);

        for (int i = 0; i < pocet; i++) {
            pw.println(i + " : " + (long) (getUniformDouble(od, po)));
        }
        pw.close();
    }

    /**
     * realne cisla z intervalu [od; po)
     */
    public static void generateUniformDouble(int pocet, int pocet_suradnic, int od, int po, File file) throws Exception {

        if (!file.exists()) {
            file.createNewFile();
        }
        PrintWriter pw = new PrintWriter(file);

        for (int i = 0; i < pocet; i++) {
            for (int j = 0; j < pocet_suradnic; j++) {
                double a = getUniformDouble(od, po) * 100000;
                int b = (int) a;
                pw.print((double) b / 100000 + " ");
            }
            pw.println();
        }
        pw.close();
    }

    private static double getGaussianDouble(int[] body, double sigma, Random random) {

        int k = (int) (Math.random() * body.length);
        return random.nextGaussian() * sigma + body[k];
    }

    /**
     * cele cisla gaussovo okolo zvolenych bodov so zvolenou smerodajnou odchylkou (sigma)
     * hodnoty nezvyknu prekonat 3*sigma a takmer nikdy neprekonaju 5*sigma
     * pozri obrazok http://en.wikipedia.org/wiki/File:Standard_deviation_diagram.svg
     */
    public static void generateGaussianLong(int pocet, int[] body, double sigma, File file) throws Exception {

        if (!file.exists()) {
            file.createNewFile();
        }
        PrintWriter pw = new PrintWriter(file);

        Random random = new Random();
        for (int i = 0; i < pocet; i++) {
            pw.println(Math.round(getGaussianDouble(body, sigma, random)));
        }
        pw.close();
    }

    /**
     * realne cisla gaussovo okolo zvolenych bodov so zvolenou smerodajnou odchylkou
     */
    public static void generateGaussianDouble(int pocet, int pocet_suradnic, int[] body, double sigma, File file) throws Exception {

        if (!file.exists()) {
            file.createNewFile();
        }
        PrintWriter pw = new PrintWriter(file);

        Random random = new Random();

        for (int i = 0; i < pocet; i++) {
            for (int j = 0; j < pocet_suradnic; j++) {
                double a = getGaussianDouble(body, sigma, random) * 100000;
                int b = (int) a;
                pw.print((double) b / 100000 + " ");
            }
            pw.println();
        }
        pw.close();
    }

    private static double getExponentialDouble(double[] hranice, double lambda, double natiahnutie) {

        int k = (int) (Math.random() * hranice.length);
        return (Math.log(1.0 - Math.random()) / (-lambda)) * natiahnutie + hranice[k];
    }

    /**
     * realne cisla exponencialne sprava ku zvolenej hranici so zvolenou lambdou
     * pozri obrazok http://en.wikipedia.org/wiki/File:Exponential_pdf.svg
     */
    public static void generateExponentialDouble(int pocet, int pocet_suradnic, double[] hranice, double lambda, double natiahnutie, File file) throws Exception {

        if (!file.exists()) {
            file.createNewFile();
        }
        PrintWriter pw = new PrintWriter(file);

        for (int i = 0; i < pocet; i++) {
            for (int j = 0; j < pocet_suradnic; j++) {
                double a = getExponentialDouble(hranice, lambda, natiahnutie) * 100000;
                int b = (int) a;
                pw.print((double) b / 100000 + " ");

            }
            pw.println();
        }
        pw.close();
    }

    public static void generateExponentialLong(int pocet, double[] hranice, double lambda, double natiahnutie, File file) throws Exception {

        if (!file.exists()) {
            file.createNewFile();
        }
        PrintWriter pw = new PrintWriter(file);

        for (int i = 0; i < pocet; i++) {
            pw.println(i + " : " + Math.round(getExponentialDouble(hranice, lambda, natiahnutie)));
        }
        pw.close();
    }


    /**
     * rovnomerne sa vygeneruje zopar (pocetGaussov) pomocnych bodov z daneho rozsahu (latOd, latPo, lonOd, lonPo)
     * okolo tychto bodov sa vygeneruju pozadovane body gaussovo so zadanou smerodajnou odchylkou v oboch smeroch (latSigma, lonSigma)
     */


//	public static void main(String[] args) throws Exception {
//
//		String path = System.getProperty("user.dir") + System.getProperty("file.separator") + "data";
//
////		generateUniformDouble(1000000, 0, 1, new File(path, "uniformDouble1000000_0_1.txt"));
////		generateUniformLong(1000000, 1000, 2000, new File(path, "uniformLong1000000_1000_2000.txt"));
////		generateUniformLong(1000000, -3, 7, new File(path, "uniformLong1000000_-3_7.txt"));
////		generateGaussianDouble(1000000, new int[] {1, 10, 15, 20, 30, 50, 100, 150, 180}, 1, new File(path, "gaussianDouble1000000_-5_186.txt"));
////		generateGaussianLong(1000000, new int[] {1, 5, 10}, 1, new File(path, "gaussianLong1000000_-5_16.txt"));
////		generateGaussianLong(1000000, new int[] {1000000, 10000000, 15000000, 20000000, 30000000, 50000000, 100000000, 150000000, 180000000}, 2000000, new File(path, "gaussianLong1000000_-10000000_189999997.txt"));
////		generateExponentialDouble(1000000, 0, 1, 10, new File(path, "exponentialDouble1000000_0_50.txt"));
////		generateExponentialLong(1000000, 10000, 0.5, 10, new File(path, "exponentialLong1000000_10000_10100.txt"));
////		generateExponentialDouble(1000000, new double[] {0.001, 0.01, 0.015, 0.02, 0.03, 0.05, 0.1, 0.15, 0.18}, 1, 0.008, new File(path, "exponentialDouble1000000_0.001_0.22.txt"));
////		generateExponentialLong(1000000, new double[] {10, 100, 105, 250, 305, 508, 1000, 1501, 1830}, 0.5, 1, new File(path, "exponentialLong1000000_10_1840.txt"));
//
////		generateUniformDouble(1000000, 1, 2, new File(path, "uniformDouble1000000_1_2.txt"));
////		generateUniformLong(1000000, 100, 200, new File(path, "uniformLong1000000_100_200.txt"));
////		generateUniformLong(1000000, -7, 3, new File(path, "uniformLong1000000_-7_3.txt"));
////		generateGaussianDouble(1000000, new int[] {1, 10, 15, 20, 50, 100, 150, 180}, 2, new File(path, "gaussianDouble1000000_-10_192.txt"));
////		generateGaussianLong(1000000, new int[] {1, 5, 15}, 2, new File(path, "gaussianLong1000000_-10_25.txt"));
////		generateGaussianLong(1000000, new int[] {1000000, 10000000, 15000000, 100000000, 150000000, 180000000}, 2000000, new File(path, "gaussianLong1000000_-10000000_190000000.txt"));
////		generateExponentialDouble(1000000, new double[] {-1}, 1, 10, new File(path, "exponentialDouble1000000_-1_48.txt"));
////		generateExponentialLong(1000000, new double[] {7000}, 0.2, 10, new File(path, "exponentialLong1000000_10000_10400.txt"));
////		generateExponentialDouble(1000000, new double[] {0.001, 0.01, 0.015, 0.02, 0.03, 0.05, 0.1, 0.15, 0.18}, 2, 0.008, new File(path, "exponentialDouble1000000_0.001_0.2.txt"));
////		generateExponentialLong(1000000, new double[] {10, 100, 105, 250, 305, 1000, 1501, 1830}, 1.5, 1, new File(path, "exponentialLong1000000_10_1835.txt"));
//	}
}