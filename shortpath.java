import java.io.File;

public class shortpath 
{
	public static void calcShortestPath(
	        final int[][] currentGraph,
	        final String word1, final String word2) {
	        final int v = create.dotsNum();
	        final int m = create.findAim(word1, v);
	        final int n = create.findAim(word2, v);
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
	                            dist[i][j] = Interaction.MAX;
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
	            int temp = prev[create.findAim(word1, v)][create.findAim(word2, v)];
	            if (dist[create.findAim(word1, v)][create.findAim(word2, v)] >= Interaction.MAX)
	            {
	            	System.out.println("No path between " + word1 + " and " + word2 + "!");
	            	System.exit(0);
	            }
	            String[] temps = new String[v];
	            int flag1 = 0;
	            while (temp != -1) {
	                    temps[flag1] = Directed_Graph.dots[temp];
	                    flag1++;
	                    temp = prev[create.findAim(word1, v)][temp];
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
	            for (i = 0; i < create.dotsNum(); i++) {
	                for (j = 0; j < create.dotsNum(); j++) {
	                    if (currentGraph[i][j] != 0) {
	                        gv.addln(Directed_Graph.dots[i] + " -> " + Directed_Graph.dots[j]
	                                + "[label = " + currentGraph[i][j] + "]" + ";");
	                    }
	                }
	            }
	            for (i = 0; i < flag1 + 1; i++) {
	                gv.addln(Directed_Graph.dots[create.findAim(temps2[i], v)] + " -> "
	                        + Directed_Graph.dots[create.findAim(temps2[i + 1], v)]
	                        + "[color = " + "\"red\"" + "]" + ";");
	            }
	            gv.addln(gv.endGraph());
	            final String type = "png";
	            final File out = new File("Ctu." + type);
	            gv.writeGraphToFile(gv.getGraph(gv.getDotSource(), type), out);       
	        }
	    }
}
