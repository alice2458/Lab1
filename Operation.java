import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class Operation 
{
	public static final int MAX = 1000;
    
    static final int st1 = 1;
    
    static final int st2 = 2;
    
    static final int st3 = 3;
   
    static final int st4 = 4;
    
    static final int st5 = 5;
   
    public static int findAim(final String aim, final int k) {
        for (int i = 0; i < k; i++) {
            if (aim.equals(Data.dots[i])) {
                return i;
            }
        }
        return -1;
    }
    
    public static int dotsNum() {
    	int dotsNumData = -1;
    	if (dotsNumData != -1) {
    		return dotsNumData;
    	}
        for (int i = 0; i < MAX; i++) {
            if (Data.dots[i] == null) {
            	dotsNumData = i;
                return i;
            }
        }
        return 0;
    }
	
	public static int[][] createDirectedGraph(final String paraFilename) {
        String filename = paraFilename;
        filename = filename.replaceAll("[^a-zA-Z]", " ");
        filename = filename.toLowerCase(Locale.US);
        filename = filename.replaceAll(" +", " ");
        int[][] flag = new int[2][2];
        if (filename.length() <= 0)
        	return flag;
        String[] words = new String[Operation.MAX];
        words = filename.split(" ");

        final int len = words.length;
        int j = 0;
        for (int i = 0; i < len; i++) {
            final List<String> list = Arrays.asList(Data.dots);
            if (!list.contains(words[i])) {
            	Data.dots[j] = words[i];
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
                    bridgeWord = bridgeWord + Data.dots[i] + ',';
                }
            }
            return bridgeWord;
        }
    }
	
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
	
	public static void calcShortestPath(
        final int[][] currentGraph,
        final String word1, final String word2) {
        final int v = dotsNum();
        final int m = findAim(word1, v);
        final int n = findAim(word2, v);
        if (m == -1 && n != -1) {
            System.out.println("No " + word1 + " in the graph!");
        } else if (n == -1 && m != -1) {
        	System.out.println("No " + word2 + " in the graph!");
        } else if (m == -1 && n == -1) {
        	System.out.println("No " + word1 + " and " + word2
                    + " in the graph!");
        } else {
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
            if (dist[findAim(word1, v)][findAim(word2, v)] >= MAX)
            {
            	System.out.println("No path between " + word1 + " and " + word2 + "!");
            	System.exit(0);
            }
            String[] temps = new String[v];
            int flag1 = 0;
            while (temp != -1) {
                    temps[flag1] = Data.dots[temp];
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
                        gv.addln(Data.dots[i] + " -> " + Data.dots[j]
                                + "[label = " + currentGraph[i][j] + "]" + ";");
                    }
                }
            }
            for (i = 0; i < flag1 + 1; i++) {
                gv.addln(Data.dots[findAim(temps2[i], v)] + " -> "
                        + Data.dots[findAim(temps2[i + 1], v)]
                        + "[color = " + "\"red\"" + "]" + ";");
            }
            //C:\Users\Alice\Desktop\EXE.txt
            gv.addln(gv.endGraph());
            final String type = "png";
            final File out = new File("Ctu." + type);
            gv.writeGraphToFile(gv.getGraph(gv.getDotSource(), type), out);       
        }
    }
	
	public static void showDirectedGraph(final int[][] currentGraph) {
        int i;
        int j;
        final Graph gv = new Graph();
        gv.addln(gv.startGraph());
        for (i = 0; i < dotsNum(); i++) {
            for (j = 0; j < dotsNum(); j++) {
                if (currentGraph[i][j] != 0) {
                    gv.addln(Data.dots[i] + " -> " + Data.dots[j]
                            + "[label = " + currentGraph[i][j] + "]" + ";");
                }
            }
        }
        gv.addln(gv.endGraph());
        final String graphType = "png";
        final File outFile = new File("Tu." + graphType);
        gv.writeGraphToFile(gv.getGraph(gv.getDotSource(), graphType), outFile);
    }
    
    public static String randomWalk(final int[][] currentGraph) {
        final java.util.Random r = new java.util.Random();
        int head = r.nextInt(dotsNum());
        String output = Data.dots[head] + " ";
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
            if (flag == 0)
            	break;
            final int k = r.nextInt(flag);
            queue = queue + eff[k];
            output = output + Data.dots[eff[k]] + " ";
            head = eff[k];
            if (queue.substring(0, queue.length() - 2).indexOf(
                    queue.substring(queue.length() - 2,
                    queue.length()), 0) != -1) {
                break;
            }
        } while (true);
        return output;
    }
    
    public static void create()throws IOException
    {
        String str = "";
        String address = Interaction.getPath();
        final File file = new File(address);
        try {
            final FileInputStream in =  new FileInputStream(file);
            final int size = in.available();
            byte[] buffer = new byte[size];
            in.read(buffer);
            in.close();
            str = new String(buffer, "UTF-8");
        } catch (IOException e) 
        {
            e.printStackTrace();
        }
    	Data.currentGraph = createDirectedGraph(str);
    }
    
    public static int exe()
    {
    	for (int i=0;i<2;i++)
        {
        	for (int j=0;j<2;j++)
        	{
        		if (Data.currentGraph[i][j] != 0)
        			Data.temp = 1;
        	}
        }
        return Data.temp;
    }
    
    public static String ost1()
    {
    	String ansBridgeWords = queryBridgeWords(Data.currentGraph,
                Interaction.word1, Interaction.word2);
    	return ansBridgeWords;
    }
    
    public static String ost2()
    {
    	String bridgeWord = generateNewText(Data.currentGraph, 
    			Interaction.newWord);
    	return bridgeWord;
    }
    
    public static void ost3()
    {
    	calcShortestPath(Data.currentGraph, 
    			Interaction.word3, Interaction.word4);
    }
    
    public static String ost4()
    {
    	String result = randomWalk(Data.currentGraph);
    	return result;
    }
}
