import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

//C:\Users\Alice\Desktop\Test.txt

public class Interaction
{
	public static Scanner input = new Scanner(System.in);
	
	public static String getPath()
	{
        final String address = input.nextLine();
        return address;
	}
	
	public static String word1 = "";
	
	public static String word2 = "";
	
	public static String word3 = "";
		
	public static String word4 = "";
	
	public static String newWord = "";
	
	public static void main(final String[] args) throws IOException 
	{
		final PrintWriter out = new PrintWriter("myfile.txt");
		
		System.out.println("Please input the path of input file:");
		Operation.create();
		Operation.exe();
        if (Data.temp == 0)
        {
        	System.out.println("Please re-run the program!");
        	System.exit(0);
        }
      
        System.out.println("Visualization of this directed graph:");
        Operation.showDirectedGraph(Data.currentGraph);
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
            case Operation.st1:
                System.out.println("Please two words:");
                input.nextLine();
                word1 = input.nextLine();
                word2 = input.nextLine();
                int flag = 0;
                String half = Operation.ost1();
                if (half.charAt(0) == '#') {
                    if (half.length() == 1 + word1.length()
                            + word2.length()) {
                        System.out.println("No " + word1 + " or " + word2
                                + " in the graph!");
                    } else {
                        System.out.println("No " + half.substring(
                                1, half.length())
                                + " in the graph!");
                    }
                } else if (half.equals("")) {
                    System.out.println("No bridge words from \"" + word1
                            + "\" to \"" + word2 + "\"!");
                } else {
                    for (int i = half.length() - 2; i > 0; i--) {
                        if (half.charAt(i) == ',') {
                            System.out.print("The bridge words from " + word1
                                    + " to " + word2 + " are:"
                                    + half.substring(0, i + 1)
                                    + "and "
                                    + half.substring(i + 1,
                                    		half.length() - 1)
                                    + ".");
                            flag = 1;
                            break;
                        }
                    }
                    if (flag == 0) {
                        System.out.print("The bridge word from " + word1
                                + " to " + word2 + " is:"
                                + half.substring(0,
                                     half.length() - 1));
                    }
                    System.out.println();
                }
                break;
            case Operation.st2:
                System.out.println("Please input the source text:");
                input.nextLine();
                newWord = input.nextLine();
                System.out.println(Operation.ost2());
                break;
            case Operation.st3:
                System.out.println("Please input two words:");
                input.nextLine();
                word3 = input.nextLine();
                word4 = input.nextLine();
                Operation.ost3();
                break;
            case Operation.st4:
                System.out.println("Random Walk Start:(Y to continue, N to end)");
                String[] resultwords = new String[Operation.MAX];
                resultwords = Operation.ost4().split(" ");
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
            if (function == Operation.st5) {
                System.out.println("Exit");
                break;
            }
        }
        input.close();
        out.close();
    }
}
