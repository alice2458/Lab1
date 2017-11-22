import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class create 
{
	public static int findAim(final String aim, final int k) {
        for (int i = 0; i < k; i++) {
            if (aim.equals(Directed_Graph.dots[i])) {
                return i;
            }
        }
        return -1;
    }
	
	public static int dotsNum() 
    {
    	int dotsNumData = -1;
    	if (dotsNumData != -1) {
    		return dotsNumData;
    	}
        for (int i = 0; i < Interaction.MAX; i++) {
            if (Directed_Graph.dots[i] == null) {
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
        String[] words = new String[Interaction.MAX];
        words = filename.split(" ");

        final int len = words.length;
        int j = 0;
        for (int i = 0; i < len; i++) {
            final List<String> list = Arrays.asList(Directed_Graph.dots);
            if (!list.contains(words[i])) {
            	Directed_Graph.dots[j] = words[i];
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
	
	public static void showDirectedGraph(final int[][] currentGraph) {
        int i;
        int j;
        final Graph gv = new Graph();
        gv.addln(gv.startGraph());
        for (i = 0; i < dotsNum(); i++) {
            for (j = 0; j < dotsNum(); j++) {
                if (currentGraph[i][j] != 0) {
                    gv.addln(Directed_Graph.dots[i] + " -> " + Directed_Graph.dots[j]
                            + "[label = " + currentGraph[i][j] + "]" + ";");
                }
            }
        }
        gv.addln(gv.endGraph());
        final String graphType = "png";
        final File outFile = new File("Tu." + graphType);
        gv.writeGraphToFile(gv.getGraph(gv.getDotSource(), graphType), outFile);
    }
	
	public static void create_get()throws IOException
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
    	Directed_Graph.currentGraph = createDirectedGraph(str);
    }
	
	public static int exe()
    {
    	for (int i=0;i<2;i++)
        {
        	for (int j=0;j<2;j++)
        	{
        		if (Directed_Graph.currentGraph[i][j] != 0)
        			Directed_Graph.temp = 1;
        	}
        }
        return Directed_Graph.temp;
    }
}
