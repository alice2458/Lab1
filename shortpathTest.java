import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Logger;

import org.junit.Test;

public class shortpathTest 
{

	@Test
	public void testCalcShortestPath() 
	{
		final Scanner input = new Scanner(System.in);
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
        	e.printStackTrace();
        }
        
        int[][] Graph1 = create.createDirectedGraph(str);
        
        shortpath.calcShortestPath(Graph1 ,"you", "wen");
        shortpath.calcShortestPath(Graph1 ,"youyou", "wen");
        shortpath.calcShortestPath(Graph1 ,"you", "wenwen");
        shortpath.calcShortestPath(Graph1 ,"youyou", "wenwen");
        shortpath.calcShortestPath(Graph1 ,"zheng", "bu");
    }
}

