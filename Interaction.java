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
	
	public static String getWord1()
	{
		final String word1 = input.next();
		return word1;
	}
	
	public static String getWord2()
	{
		final String word2 = input.next();
		return word2;
	}
	
	public static String getWord3()
	{
		final String word3 = input.next();
		return word3;
	}
	
	public static String getWord4()
	{
		final String word4 = input.next();
		return word4;
	}
	
	public static String getNewWord()
	{
		final String newWord = input.nextLine();
		return newWord;
	}
	
	public static void main(final String[] args) throws IOException 
	{
		final PrintWriter out = new PrintWriter("myfile.txt");
		Operation op = new Operation();
		
		System.out.println("Please input the path of input file:");
        getPath();
        op.create();
        op.exe();
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
                String word1 = getWord1();
                String word2 = getWord2();
                int flag = 0;
                if (Operation.ost1().charAt(0) == '#') {
                    if (Operation.ost1().length() == 1 + word1.length()
                            + word2.length()) {
                        System.out.println("No " + word1 + " or " + word2
                                + " in the graph!");
                    } else {
                        System.out.println("No " + Operation.ost1().substring(
                                1, Operation.ost1().length())
                                + " in the graph!");
                    }
                } else if (Operation.ost1().equals("")) {
                    System.out.println("No bridge words from \"" + word1
                            + "\" to \"" + word2 + "\"!");
                } else {
                    for (int i = Operation.ost1().length() - 2; i > 0; i--) {
                        if (Operation.ost1().charAt(i) == ',') {
                            System.out.print("The bridge words from " + word1
                                    + " to " + word2 + " are:"
                                    + Operation.ost1().substring(0, i + 1)
                                    + "and "
                                    + Operation.ost1().substring(i + 1,
                                    		Operation.ost1().length() - 1)
                                    + ".");
                            flag = 1;
                            break;
                        }
                    }
                    if (flag == 0) {
                        System.out.print("The bridge word from " + word1
                                + " to " + word2 + " is:"
                                + Operation.ost1().substring(0,
                                		Operation.ost1().length() - 1));
                    }
                    System.out.println();
                }
                break;
            case Operation.st2:
                System.out.println("Please input the source text:");
                input.nextLine();
                getNewWord();
                System.out.println(Operation.ost2());
                break;
            case Operation.st3:
                System.out.println("Please input two words:");
                getWord3();
                getWord4();
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
