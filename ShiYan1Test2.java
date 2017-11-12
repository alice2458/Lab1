import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Logger;

import org.junit.Test;

public class ShiYan1Test2 {

	@Test
	public void testCalcShortestPath() 
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
        int[][] Graph1 = ShiYan1.createDirectedGraph(str);
        
        ShiYan1 hei1 = new ShiYan1();
        hei1.calcShortestPath(Graph1 ,"wu", "yun");
        hei1.calcShortestPath(Graph1 ,"wuwu", "yun");
        hei1.calcShortestPath(Graph1 ,"wu", "yunyun");
        hei1.calcShortestPath(Graph1 ,"wuwu", "yunyun");
        hei1.calcShortestPath(Graph1 ,"ku", "xin");
        }
}
