import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Locale;
import java.util.logging.Logger;
/**
 */
public class ShiYan1 {
    /**
     */
    public static final int MAX = 1005;
    /**
     */
    static Logger log=Logger.getLogger(ShiYan1.class.getName());
    /**
     */
    static final int st1 = 1;
    /**
     */
    static final int st2 = 2;
    /**
     */
    static final int st3 = 3;
    /**
     */
    static final int st4 = 4;
    /**
     */
    static final int st5 = 5;
    /**
     */
    private static String[] dots = new String[MAX];
    /**
     * @return .
     */
    public static String[] getDots() {
        return dots;
    }
    /**
     * @return .
     * @param aim .
     * @param k .
     */
    public static int findAim(final String aim, final int k) {
        for (int i = 0; i < k; i++) {
            if (aim.equals(dots[i])) {
                return i;
            }
        }
        return -1;
    }
    /**
     * @return .
     * @param
     */
    private static int dotsNumData = -1;
    public static int dotsNum() {
    	if (dotsNumData != -1) {
    		return dotsNumData;
    	}
        for (int i = 0; i < MAX; i++) {
            if (dots[i] == null) {
            	dotsNumData = i;
                return i;
            }
        }
        return 0;
    }
    /**
     * @return .
     * @param paraFilename .
     */
    public static int[][] createDirectedGraph(final String paraFilename) {
        String filename = paraFilename;
        filename = filename.replaceAll("[^a-zA-Z]", " ");
        filename = filename.toLowerCase(Locale.US);
        filename = filename.replaceAll(" +", " ");
        filename = filename.substring(1, filename.length());
        String[] words = new String[MAX];
        words = filename.split(" ");

        final int len = words.length;
        int j = 0;
        for (int i = 0; i < len; i++) {
            final List<String> list = Arrays.asList(dots);
            if (!list.contains(words[i])) {
                dots[j] = words[i];
                j = j + 1;
            }
        }

        int[][] cost = new int[j][j];
        for (int i = 0; i < len - 1 && words[i] != null; i++) {
            final int a = findAim(words[i], j);
            final int b = findAim(words[i + 1], j);
            cost[a][b] = cost[a][b] + 1;
        }
        return cost;
    }
    /**
     * @return .
     * @param currentGraph .
     * @param word1 .
     * @param word2 .
     */
    public static String queryBridgeWords(final int[][] currentGraph,
            final String word1, final String word2) {
        final int len = dotsNum();
        final int m = findAim(word1, len);
        final int n = findAim(word2, len);
        int num = -1;
        String bridgeWord = "";
        if (m == -1 && n != -1) {
            return bridgeWord + '#' + word1;
        } else if (n == -1 && m != -1) {
            return bridgeWord + '#' + word2;
        } else if (m == -1 && n == -1) {
            return bridgeWord + "#" + word1 + word2;
        } else {
            for (int i = 0; i < len - 1; i++) {
                if (currentGraph[m][i] != 0 && currentGraph[i][n] != 0) {
                    num = num + 1;
                    bridgeWord = bridgeWord + dots[i] + ',';
                }
            }
            return bridgeWord;
        }
    }
    /**
     * @return .
     * @param currentGraph .
     * @param inputText .
     */
    public static String generateNewText(final int[][] currentGraph,
            final String inputText) {
        String word = inputText.replaceAll("[^a-zA-Z]", " ");
        word = word.replaceAll(" +", " ");
        String[] words = new String[MAX];
        words = word.split(" ");
        String outputText = "";
        int headindex = 0;
        int tailindex = 0;
        for (int i = 0; i < words.length - 1; i++) {
            final String bridgeTemp = queryBridgeWords(currentGraph,
                    words[i], words[i + 1]);
            final java.util.Random r = new java.util.Random();
            if (bridgeTemp.equalsIgnoreCase("")
                        || bridgeTemp.charAt(0) == '#') {
                tailindex = inputText.indexOf(words[i + 1], headindex)
                        + words[i + 1].length() - 1;
                outputText = outputText
                             + inputText.substring(headindex,
                                     headindex + words[i].length() + 1);
            } else {
                String[] bridgeWordArray = new String[MAX];
                bridgeWordArray = bridgeTemp.split(",");
                if (bridgeWordArray.length == 1) {
                    tailindex = inputText.indexOf(words[i + 1], headindex)
                            + words[i + 1].length() - 1;
                    outputText = outputText + inputText.substring(headindex,
                            inputText.indexOf(words[i + 1],
                                    headindex))
                            + bridgeWordArray[0] + ' ';
                } else {
                    tailindex = inputText.indexOf(words[i + 1], headindex)
                            + words[i + 1].length() - 1;
                    final int number = r.nextInt(bridgeWordArray.length);
                    outputText += inputText.substring(headindex,
                             headindex + words[i].length() + 1)
                             + bridgeWordArray[number] + ' ';
                }
            }
            headindex = tailindex - words[i + 1].length() + 1;
        }
        outputText += inputText.substring(headindex, inputText.length());
        return outputText;
    }
    /**
     * @param currentGraph .
     * @param word1 .
     * @param word2 .
     */
    public void calcShortestPath(
            final int[][] currentGraph,
            final String word1, final String word2) {
        final int v = dotsNum();
        int[][] dist = new int[v][v];
        int[][] prev = new int[v][v];
        int i;
        int j;
        int k;
        for (i = 0; i < v; i++) {
            for (j = 0; j < v; j++) {
                    if (currentGraph[i][j] == 0) {
                        dist[i][j] = MAX;
                    } else {
                        dist[i][j] = currentGraph[i][j];
                        prev[i][j] = -1;
                    }
            }
        }
        for (k = 0; k < v; k++) {
            for (i = 0; i < v; i++) {
                for (j = 0; j < v; j++) {
                    if (dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                        prev[i][j] = k;
                    }
                }
            }
        }
        int temp = prev[findAim(word1, v)][findAim(word2, v)];
        String[] temps = new String[v];
        int flag1 = 0;
        while (temp != -1) {
                temps[flag1] = dots[temp];
                flag1++;
                temp = prev[findAim(word1, v)][temp];
        }
        String[] temps2 = new String[v];
        int flag2 = 0;
        for (i = flag1; i > 0; i--) {
            temps2[i] = temps[flag2];
            flag2++;
        }
        temps2[0] = word1;
        temps2[flag1 + 1] = word2;

        for (i = 0; i < flag1 + 1; i++) {
            System.out.print(temps2[i] + " -> ");
        }
        System.out.println(temps2[flag1 + 1]);

        final Graph gv = new Graph();
        gv.addln(gv.startGraph());
        for (i = 0; i < dotsNum(); i++) {
            for (j = 0; j < dotsNum(); j++) {
                if (currentGraph[i][j] != 0) {
                    gv.addln(dots[i] + " -> " + dots[j]
                            + "[label = " + currentGraph[i][j] + "]" + ";");
                }
            }
        }
        for (i = 0; i < flag1 + 1; i++) {
            gv.addln(dots[findAim(temps2[i], v)] + " -> "
                    + dots[findAim(temps2[i + 1], v)]
                    + "[color = " + "\"red\"" + "]" + ";");
        }
        //C:\Users\Alice\Desktop\EXE.txt
        gv.addln(gv.endGraph());
        final String type = "png";
        final File out = new File("Ctu." + type);
        gv.writeGraphToFile(gv.getGraph(gv.getDotSource(), type), out);
    }
    /**
     * @param currentGraph .
     */
    public void showDirectedGraph(final int[][] currentGraph) {
        int i;
        int j;
        final Graph gv = new Graph();
        gv.addln(gv.startGraph());
        for (i = 0; i < dotsNum(); i++) {
            for (j = 0; j < dotsNum(); j++) {
                if (currentGraph[i][j] != 0) {
                    gv.addln(dots[i] + " -> " + dots[j]
                            + "[label = " + currentGraph[i][j] + "]" + ";");
                }
            }
        }
        gv.addln(gv.endGraph());
        final String graphType = "png";
        final File outFile = new File("Tu." + graphType);
        gv.writeGraphToFile(gv.getGraph(gv.getDotSource(), graphType), outFile);
    }
    /**
     * @return .
     * @param currentGraph .
     */
    public static String randomWalk(final int[][] currentGraph) {
        final java.util.Random r = new java.util.Random();
        int head = r.nextInt(dotsNum());
        String output = dots[head] + " ";
        String queue = String.valueOf(head);
        do {
            int[] eff = new int[dotsNum()];
            int flag = 0;
            for (int i = 0; i < dotsNum(); i++) {
                if (currentGraph[head][i] != 0) {
                    eff[flag] = i;
                    flag = flag + 1;
                }
            }
            for (int i = flag; i < dotsNum(); i++) {
                eff[i] = -1;
            }
            int m = 0;
            for (int i = 0; i < dotsNum(); i++) {
                if (eff[i] == -1) {
                	break;
                } else {
                	m = m + 1;
                }
            }
            if (m == 0) {
                break;
            }
            final int k = r.nextInt(m);
            queue = queue + eff[k];
            output = output + dots[eff[k]] + " ";
            head = eff[k];
            if (queue.substring(0, queue.length() - 2).indexOf(
                    queue.substring(queue.length() - 2,
                    queue.length()), 0) != -1) {
                break;
            }
        } while (true);
        return output;
    }
    /**
     * @param args .
     * @throws java.io.IOException .
     */
    public static void main(final String[] args) throws IOException {
        final Scanner input = new Scanner(System.in);
        final PrintWriter out = new PrintWriter("myfile.txt");
        System.out.println("Please input the path of input file:");
        final String address = input.nextLine();
        String str = "";
        
        final File file = new File(address);
        try {
            final FileInputStream in =  new FileInputStream(file);
            final int size = in.available();
            byte[] buffer = new byte[size];
            in.read(buffer);
            in.close();
            str = new String(buffer, "UTF-8");
        } catch (IOException e) {
        	log.warning(e.toString());
        }
        final int[][] currentGraph = createDirectedGraph(str);

        System.out.println("Visualization of this directed graph:");
        final ShiYan1 p = new ShiYan1();
        p.showDirectedGraph(currentGraph);
        while (true) {
            System.out.println("Please select the function:");
            System.out.println("****************************");
            System.out.println("1.Search the Bridge Word");
            System.out.println("2.Add Bridge Word in Sentence");
            System.out.println("3.Search the Shortest Path");
            System.out.println("4.Randomm Walk");
            System.out.println("5.Exit");
            System.out.println("****************************");
            final int function = input.nextInt();
            switch (function) {
            case st1:
                System.out.println("Please two words:");
                final String word1 = input.next();
                final String word2 = input.next();
                final String ansBridgeWords = queryBridgeWords(currentGraph,
                        word1, word2);
                int flag = 0;
                if (ansBridgeWords.charAt(0) == '#') {
                    if (ansBridgeWords.length() == 1 + word1.length()
                            + word2.length()) {
                        System.out.println("No " + word1 + " or " + word2
                                + " in the graph!");
                    } else {
                        System.out.println("No " + ansBridgeWords.substring(
                                1, ansBridgeWords.length())
                                + " in the graph!");
                    }
                } else if (ansBridgeWords.equals("")) {
                    System.out.println("No bridge words from \"" + word1
                            + "\" to \"" + word2 + "\"!");
                } else {
                    for (int i = ansBridgeWords.length() - 2; i > 0; i--) {
                        if (ansBridgeWords.charAt(i) == ',') {
                            System.out.print("The bridge words from " + word1
                                    + " to " + word2 + " are:"
                                    + ansBridgeWords.substring(0, i + 1)
                                    + "and "
                                    + ansBridgeWords.substring(i + 1,
                                           ansBridgeWords.length() - 1)
                                    + ".");
                            flag = 1;
                            break;
                        }
                    }
                    if (flag == 0) {
                        System.out.print("The bridge word from " + word1
                                + " to " + word2 + " is:"
                                + ansBridgeWords.substring(0,
                                  ansBridgeWords.length() - 1));
                    }
                    System.out.println();
                }
                break;
            case st2:
                System.out.println("Please input the source text:");
                input.nextLine();
                final String newWord = input.nextLine();
                final String bridgeWord = generateNewText(currentGraph, newWord);
                System.out.println(bridgeWord);
                break;
            case st3:
                System.out.println("Please input two words:");
                final String word3 = input.next();
                final String word4 = input.next();
                final ShiYan1 c = new ShiYan1();
                c.calcShortestPath(currentGraph, word3, word4);
                break;
            case st4:
                System.out.println("Random Walk Start:(Y to continue, N to end)");
                final String result = randomWalk(currentGraph);
                String[] resultwords = new String[MAX];
                resultwords = result.split(" ");
                int n = 0;
                do {
                    final String judge = input.nextLine();
                    if (judge.equals("N")) {
                        break;
                    }
                    System.out.println(resultwords[n]);
                    n = n + 1;
                } while (n < resultwords.length);
                System.out.println("Random Walk Complete!");
                System.out.println("The Resutlt of Random Walk is:");
                for (int i = 0; i < n; i++) {
                    System.out.print(resultwords[i] + " ");
                    out.print(resultwords[i] + " ");
                }
                System.out.println();
                break;
            default:
                break;
            }

            if (function == st5) {
                final String over = "Exit";
                System.out.println(over);
                break;
            }
        }
        input.close();
        out.close();
    }
}
