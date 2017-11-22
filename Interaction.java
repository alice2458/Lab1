import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

//C:\Users\Alice\Desktop\Test.txt

public class Interaction
{
	public static final int MAX = 1000;
	
	public static Scanner input = new Scanner(System.in);
	
	public static String getPath()
	{
        final String address = input.nextLine();
        return address;
	}
	
	public static void main(final String[] args) throws IOException 
	{
        final PrintWriter out = new PrintWriter("myfile.txt");
        System.out.println("Please input the path of input file:");
        create.create_get();
		create.exe();
        if (Directed_Graph.temp == 0)
        {
        	System.out.println("Please re-run the program!");
        	System.exit(0);
        }
      
        System.out.println("Visualization of this directed graph:");
        create.showDirectedGraph(Directed_Graph.currentGraph);
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
            case 1:
                System.out.println("Please two words:");
                final String word1 = input.next();
                final String word2 = input.next();
                final String ansBridgeWords = find.queryBridgeWords(Directed_Graph.currentGraph,
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
            case 2:
                System.out.println("Please input the source text:");
                input.nextLine();
                final String newWord = input.nextLine();
                final String bridgeWord = createtxt.generateNewText(Directed_Graph.currentGraph, newWord);
                System.out.println(bridgeWord);
                break;
            case 3:
                System.out.println("Please input two words:");
                final String word3 = input.next();
                final String word4 = input.next();
                shortpath.calcShortestPath(Directed_Graph.currentGraph, word3, word4);
                break;
            case 4:
                System.out.println("Random Walk Start:(Y to continue, N to end)");
                final String result = random.randomWalk(Directed_Graph.currentGraph);
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

            if (function == 5) {
                System.out.println("Exit");
                break;
            }
        }
        input.close();
        out.close();
    }
}
//C:\Users\Alice\Desktop\Test.txt