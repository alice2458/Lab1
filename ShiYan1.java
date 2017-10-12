import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ShiYan1
{
	public static final int MAX = 100;
	public static String[] dots = new String[MAX];
	public static int FindAim(String aim, int k) 
	{
		for (int i=0; i<k; i++)
		{
			if(aim.equals(dots[i]))
				return i;
		}
		return -1;
	}
	public static int DotsNum()
	{
		for (int i=0;i<MAX;i++)
		{
			if (dots[i] == null)
				return i;
		}
		return 0;
	}
	public static int[][]createDirectedGraph(String filename)
	{	
		filename = filename.replaceAll("[^a-zA-Z]", " ");
		filename = filename.toLowerCase();
		filename = filename.replaceAll(" +", " ");
		filename = filename.substring(1, filename.length());
		String[] words = new String[MAX];
		words = filename.split(" ");
		
		int len = words.length;
		int j = 0;
		for(int i=0;i<len;i++)
		{
			List<String> list = Arrays.asList(dots);
			if (list.contains(words[i]) == false)
			{
				dots[j] = words[i];
				j = j+1;
			}
		}
		
		int[][] cost = new int[j][j];
		for(int i=0;i<len-1 && words[i] != null;i++)
		{
			int a = FindAim(words[i], j);
			int b = FindAim(words[i+1], j);
			cost[a][b] = cost[a][b]+1;
		}
		return cost;
	}
	public static String queryBridgeWords(int[][] G, String word1, String word2)
	{
		int len = DotsNum();
		int m = FindAim(word1, len);
		int n = FindAim(word2, len);
		int num = -1;
		String Bridge = "";
		if (m==-1 && n!=-1)
		{
			return Bridge+'#'+word1;
		}
		else if (n==-1 && m!=-1)
		{
			return Bridge+'#'+word2;
		}
		else if (m==-1 && n==-1)
		{
			return Bridge+"#"+word1+word2;
		}
		else 
		{
			for (int i=0;i<len-1;i++)
			{
				if (G[m][i]!=0 && G[i][n]!=0)
				{
					num = num+1;
					Bridge = Bridge+dots[i]+',';
				}	
			}
			return Bridge;
		}
	}
	public static String generateNewText(int[][] G, String inputText)
	{
		String word = inputText.replaceAll("[^a-zA-Z]", " ");
		word = word.replaceAll(" +", " ");
		String[] words = new String[MAX];
		words = word.split(" ");
		String outputText = "";
		int headindex = 0;
		int tailindex = 0;
		for (int i=0;i<words.length-1;i++)
		{
			String Bri = queryBridgeWords(G, words[i], words[i+1]);
			java.util.Random r = new java.util.Random();
			if (Bri.equalsIgnoreCase("") || Bri.charAt(0)=='#')
			{
				tailindex = inputText.indexOf(words[i+1], headindex)+words[i+1].length()-1;
				outputText = outputText + inputText.substring(headindex,headindex+words[i].length()+1);
			}
			else
			{
				String[] B = new String[MAX];
				B = Bri.split(",");
				if (B.length == 1)
				{
					tailindex = inputText.indexOf(words[i+1], headindex)+words[i+1].length()-1;
					outputText = outputText + inputText.substring(headindex,inputText.indexOf(words[i+1], headindex)) + B[0] + ' ';
				}
				else
				{
					tailindex = inputText.indexOf(words[i+1], headindex)+words[i+1].length()-1;
					int number = r.nextInt(B.length);
					outputText = outputText + inputText.substring(headindex,headindex+words[i].length()+1) + B[number] + ' ';
				}
			}
			headindex = tailindex - words[i+1].length() + 1;
		}
		outputText = outputText + inputText.substring(headindex,inputText.length());
		return outputText;
	}
	public void calcShortestPath(int[][] G, String word1, String word2)
	{
		int v = DotsNum();
		int[][] A = new int[v][v] ;
		int[][] P = new int[v][v] ;
		int i,j,k;
		for (i = 0 ; i < v ; i ++)
	    {
	        for (j = 0 ; j < v ; j ++)
	        {
	        		if(G[i][j] == 0)
	        			A[i][j] = MAX;
	        		else
	        		{
	        			A[i][j] = G[i][j];
	        			P[i][j] = -1;
	        		}
	        }
	    }
	    for (k = 0 ; k < v ; k ++)
	    {
	        for (i = 0 ; i < v ; i ++)
	        {
	            for (j = 0 ; j < v ; j ++)
	            {
	                if (A[i][k] + A[k][j] < A[i][j])
	                {
	                    A[i][j] = A[i][k] + A[k][j];
	                    P[i][j] = k;
	                }
	            }
	        }
	    }
	    int temp = P[FindAim(word1,v)][FindAim(word2,v)];
	    String[] temps = new String[v];
	    int flag1 = 0;	   
	    while(temp!=-1)
	    {
	    		temps[flag1] = dots[temp];
	    		flag1++;
	    		temp = P[FindAim(word1,v)][temp];
	    }
	    String[] temps2 = new String[v];
	    int flag2 = 0;
	    for(i=flag1;i>0;i--)
	    {
	    	temps2[i] = temps[flag2];
	    	flag2++;
	    }
	    temps2[0]=word1;
	    temps2[flag1+1]=word2;
	    
	    for(i=0;i<flag1+1;i++)
	    {
	    	System.out.print(temps2[i]+" -> ");
	    }
	    System.out.println(temps2[flag1+1]);
	    
		Graph gv = new Graph();
	    gv.addln(gv.start_graph());
		for(i=0;i<DotsNum();i++)
		{
			for(j=0;j<DotsNum();j++)
			{
				if(G[i][j]!=0)
				{
					gv.addln(dots[i] + " -> " + dots[j] + "[label = " + G[i][j] + "]" + ";");
				}
			}
		}
		for(i=0;i<flag1+1;i++)
	    {
			gv.addln(dots[FindAim(temps2[i] ,v)] + " -> " + dots[FindAim(temps2[i+1],v)] + "[color = " + "\"red\"" +"]" + ";");;
	    }
		//C:\Users\Alice\Desktop\EXE.txt
		gv.addln(gv.end_graph());
		String type = "png";
		File out = new File("Ctu." + type); 
	    gv.writeGraphToFile( gv.getGraph( gv.getDotSource(), type ), out );  
	}
	public void showDirectedGraph(int[][] G)
	{
		int i, j;
		Graph gv = new Graph();
	    gv.addln(gv.start_graph());
		for(i=0;i<DotsNum();i++)
		{
			for(j=0;j<DotsNum();j++)
			{
				if(G[i][j]!=0)
				{
					gv.addln(dots[i] + " -> " + dots[j] + "[label = " + G[i][j] + "]" + ";");
				}
			}
		}
		gv.addln(gv.end_graph());
		String type = "png";
		File out = new File("Tu." + type); 
	    gv.writeGraphToFile( gv.getGraph( gv.getDotSource(), type ), out );
	}
	public static String randomWalk(int[][] G)
	{
		java.util.Random r = new java.util.Random(); 
		int head = r.nextInt(DotsNum());
		String output = dots[head] + " ";
		String queue = head + "";
		do 
		{
			int[] eff = new int[DotsNum()];
			int flag = 0;
			for (int i=0; i<DotsNum(); i++)
			{
				if (G[head][i] != 0)
				{
					eff[flag] = i;
					flag = flag+1;
				}
			}
			for (int i=flag; i<DotsNum(); i++)
			{
				eff[i] = -1;
			}
			int m = 0;
			for (int i=0;i<DotsNum();i++)
			{
				if (eff[i] != -1)
					m = m+1;
				else
					break;
			}
			if (m == 0)
				break;
			int k = r.nextInt(m);
			queue = queue + eff[k];
			output = output + dots[eff[k]] + " ";
			head = eff[k];
			if ( queue.substring( 0,queue.length()-2 ).indexOf( queue.substring(queue.length()-2,queue.length()), 0) != -1)
			{
				break;
			}
		}while(true);
		return output;
	}
	
	public static void main(String[] args) throws IOException
	{
		
		Scanner input = new Scanner(System.in);
		PrintWriter out = new PrintWriter("myfile.txt");
		System.out.println("请输入你要输入的文件路径：");
		String address = input.nextLine();
		String str="";
		File file = new File(address);
		try 
		{
			FileInputStream in =  new FileInputStream(file);
			int size = in.available();
			byte[] buffer = new byte[size];
			in.read(buffer);
			in.close();
			str = new String(buffer,"UTF-8");
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		int[][] G = createDirectedGraph(str);
		
		System.out.println("有向图的可视化输出：");
		ShiYan1 p = new ShiYan1();
	    p.showDirectedGraph(G);
		
		while(true)
		{
			System.out.println("请选择功能：");
			System.out.println("****************************");
			System.out.println("1.查询桥接词；");
			System.out.println("2.加入桥接词；");
			System.out.println("3.查询最短路径；");
			System.out.println("4.随机游走；");
			System.out.println("5.退出；");
			System.out.println("****************************");
			int function = input.nextInt();
			switch (function)
			{
			case 1:
				System.out.println("请输入两个单词来查询它们的桥接词：");
				String word1 = input.next();
				String word2 = input.next();
				String B = queryBridgeWords(G, word1, word2);
				int flag = 0;
				if (B.charAt(0) == '#')
				{
					if (B.length() == 1+word1.length()+word2.length())
					{
						System.out.println("No "+word1+" or "+word2+" in the graph!");
					}
					else
					{
						System.out.println("No "+ B.substring(1,B.length()) +" in the graph!");
					}
				}
				else if (B.equals(""))
				{
					System.out.println("No bridge words from \""+word1+"\" to \""+word2+"\"!");
				}
				else
				{
					for (int i=B.length()-2;i>0;i--)
					{
						if (B.charAt(i) == ',')
						{
							System.out.print("The bridge words from "+word1+" to "+word2+" are:"+B.substring(0,i+1)+"and "+B.substring(i+1,B.length()-1)+".");
							flag = 1;
							break;
						}
					}
					if (flag == 0)
					{
						System.out.print("The bridge word from "+word1+" to "+word2+" is:"+B.substring(0, B.length()-1));
					}
					System.out.println();
				}
				break;
			case 2:
				System.out.println("请输入要插入桥接词的新文本:");
				input.nextLine();
				String NewWord = input.nextLine();
				String Bridge = generateNewText(G, NewWord);
				System.out.println(Bridge);
				break;
			case 3:
				System.out.println("请输入要查询最短路径的单词：");
				String word3 = input.next();
				String word4 = input.next();
				ShiYan1 c = new ShiYan1();
				c.calcShortestPath(G, word3, word4);
				break;
			case 4:
				System.out.println("游走开始：（Y继续，N结束）");
				String result = randomWalk(G);
				String[] resultwords = new String[MAX];
				resultwords = result.split(" ");
				int n = 0;
				do
				{
					String judge = input.nextLine();
					if (judge.equals("N"))
					{
						break;
					}
					System.out.println(resultwords[n]);
					n = n+1;	
				}while(n<resultwords.length);
				System.out.println("游走结束!");
				System.out.println("随机游走的结果为：");		
				for (int i=0;i<n;i++)
				{
					System.out.print(resultwords[i]+" ");	
					out.print(resultwords[i]+" ");
				}
				System.out.println();
				break;
			case 5:
				break;
			}
			if (function == 5)
			{
				String ov = "结";
				String er = "束";
				System.out.println(ov + er);
				break;
			}
		}
		input.close();
		out.close();
	}
}
