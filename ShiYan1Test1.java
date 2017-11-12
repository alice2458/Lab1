import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Logger;

import org.junit.Test;

public class ShiYan1Test1 {

	@Test
	public void testRandomWalk() 
	{
		Scanner input = new Scanner(System.in);
        System.out.println("Please input the path of input file:");
        String address = input.nextLine();
        String str = "";
        Logger log = Logger.getLogger(ShiYan1.class.getName());
        
        File file = new File(address);
        try {
            FileInputStream in =  new FileInputStream(file);
            int size = in.available();
            byte[] buffer = new byte[size];
            in.read(buffer);
            in.close();
            str = new String(buffer, "UTF-8");
        } catch (IOException e) {
        	log.warning(e.toString());
        }
        int[][] Graph = ShiYan1.createDirectedGraph(str);
        
        String result1 = ShiYan1.randomWalk(Graph);
        System.out.println(result1);
        
        System.out.println("Please input the path of input file:");
        String address0 = input.nextLine();
        String str0 = "";
        Logger log0 = Logger.getLogger(ShiYan1.class.getName());
        
        File file0 = new File(address0);
        try {
            FileInputStream in1 =  new FileInputStream(file0);
            int size1 = in1.available();
            byte[] buffer1 = new byte[size1];
            in1.read(buffer1);
            in1.close();
            str0 = new String(buffer1, "UTF-8");
        } catch (IOException e) {
        	log0.warning(e.toString());
        }
        int[][] Graph0 = ShiYan1.createDirectedGraph(str0);
        
        String result0 = ShiYan1.randomWalk(Graph0);
        System.out.println(result0);
	}
}
