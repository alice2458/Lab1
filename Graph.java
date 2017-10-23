
// GraphViz.java - a simple API to call dot from Java programs
/*$Id$*/
/*
 ******************************************************************************
 *                                                                            *
 *              (c) Copyright 2003 Laszlo Szathmary                           *
 *                                                                            *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms of the GNU Lesser General Public License as published by   *
 * the Free Software Foundation; either version 2.1 of the License, or        *
 * (at your option) any later version.                                        *
 *                                                                            *
 * This program is distributed in the hope that it will be useful, but        *
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY *
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public    *
 * License for more details.                                                  *
 *                                                                            *
 * You should have received a copy of the GNU Lesser General Public License   *
 * along with this program; if not, write to the Free Software Foundation,    *
 * Inc., 675 Mass Ave, Cambridge, MA 02139, USA.                              *
 *                                                                            *
 ******************************************************************************
 */
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
/**
 * <dl>
 * <dt>Purpose: GraphViz Java API.
 * <dd>
 *
 * <dt>Description:
 * <dd> With this Java class you can simply call dot
 *      from your Java programs.
 * <dt>Example usage:
 * <dd>
 * <pre>
 *    GraphViz gv = new GraphViz();
 *    gv.addln(gv.startGraph());
 *    gv.addln("A -> B;");
 *    gv.addln("A -> C;");
 *    gv.addln(gv.endGraph());
 *    System.out.println(gv.getDotSource());
 *
 *    String type = "gif";
 *    File out = new File("out." + type);   // out.gif in this example
 *    gv.writeGraphToFile( gv.getGraph( gv.getDotSource(), type ), out );
 * </pre>
 * </dd>
 *
 * </dl>
 *
 * @version v0.4, 2011/02/05 (February) -- Patch of Keheliya Gallaba
 *         is added. Now you can specify the type of the output file:
 *                 gif, dot, fig, pdf, ps, svg, png, etc.
 * @version v0.3, 2010/11/29 (November) -- Windows support + ability
 * to read the graph from a text file
 * @version v0.2, 2010/07/22 (July) -- bug fix
 * @version v0.1, 2003/12/04 (December) -- first release
 * @author  Laszlo Szathmary (<a href="jabba.laci@gmail.com">
 *                                     jabba.laci@gmail.com</a>)
 */
public class Graph {
    /**
    * The dir. where temporary files will be created.
    */
    //private static String tempDir = "/tmp"; // Linux
    private static String tempDir = "D:\\Temp"; // Windows
    /**
    * Where is your dot program located? It will be called externally.
    */
    // private static String dotGraph = "/usr/bin/dot"; // Linux
    private static String dotGraph = "C:\\Program Files (x86)\\Graphviz2.38"
            + "\\bin\\dot.exe"; // Windows
/**
    * The source of the graph written in dot language.
    */
 private StringBuilder graph = new StringBuilder();
/**
    * Constructor: creates a new GraphViz object that will contain
    * a graph.
    */
   public Graph() {
   }
/**
    * Returns the graph's source description in dot language.
    * @return Source of the graph in dot language.
    */
   public String getDotSource() {
      return graph.toString();
   }
/**
    * Adds a string to the graph's source (without newline).
    * @param line The string to be added.
    */
   public void add(final String line) {
      graph.append(line);
   }
/**
    * Adds a string to the graph's source (with newline).
    * @param line The string to be added.
    */
   public void addln(final String line) {
      graph.append(line + "\n");
   }
/**
    * Adds a newline to the graph's source.
    */
   public void addln() {
      graph.append('\n');
   }
/**
    * Returns the graph as an image in binary format.
    * @param dotSource Source of the graph to be drawn.
    * @param type Type of the output image to be produced,
    *        e.g.: gif, dot, fig, pdf, ps, svg, png.
    * @return A byte array containing the image of the graph.
    */
   public byte[] getGraph(final String dotSource, final String type) {
      File dot;
      byte[] imgStream = null;
      final int temp = 10;
      int a = temp;
      a = a + 1;

      try {
         dot = writeDotSourceToFile(dotSource);
         if (dot != null) {
            imgStream = getImgStream(dot, type);
            if (!dot.delete()) {
               System.err.println(
                       "Warning: " + dot.getAbsolutePath()
                       + " could not be deleted!"
                       );
            }
            return imgStream;
         }
         return null;
      } catch (java.io.IOException ioe) {
          return null;
      }
   }
/**
    * Writes the graph's image in a file.
    * @param img   A byte array containing the image of the graph.
    * @param file  Name of the file to where we want to write.
    * @return Success: 1, Failure: -1
    */
    public int writeGraphToFile(final byte[] img, final String file) {
        File to = new File(file);
        return writeGraphToFile(img, to);
    }
/**
    * Writes the graph's image in a file.
    * @param img   A byte array containing the image of the graph.
    * @param to    A File object to where we want to write.
    * @return Success: 1, Failure: -1
    */
   public int writeGraphToFile(final byte[] img, final File to) {
	   try {
         FileOutputStream fos = new FileOutputStream(to);
         fos.write(img);
         fos.close();
      } catch (java.io.IOException ioe) {
          ioe.printStackTrace();
          return -1;
      }
      return 1;
   }
/**
    * It will call the external dot program, and return the image in
    * binary format.
    * @param dot Source of the graph (in dot language).
    * @param type Type of the output image to be produced,
    *        e.g.: gif, dot, fig, pdf, ps, svg, png.
    * @return The image of the graph in .gif format.
    */
   private byte[] getImgStream(final File dot, final String type) {
      File img;
      byte[] imgStream = null;
      try {
         img = File.createTempFile("graph_", "." + type,
                 new File(Graph.tempDir));
         Runtime rt = Runtime.getRuntime();

         // patch by Mike Chenault
         String[] args = {
                 dotGraph,
                 "-T" + type,
                 dot.getAbsolutePath(),
                 "-o",
                 img.getAbsolutePath()
                 };
         Process p = rt.exec(args);

         p.waitFor();
FileInputStream in = new FileInputStream(img.getAbsolutePath());
         imgStream = new byte[in.available()];
         in.read(imgStream);
         // Close it if we need to
         if (in != null) {
             in.close();
         }
         if (!img.delete()) {
            System.err.println(
                    "Warning: " + img.getAbsolutePath()
                    + " could not be deleted!"
                    );
         }
     } catch (java.io.IOException ioe) {
         System.err.println("Error:    in I/O processing of tempfile in dir "
                 + Graph.tempDir + "\n");
         System.err.println("       or in calling external command");
         ioe.printStackTrace();
      } catch (java.lang.InterruptedException ie) {
         System.err.println("Error: the execution of the external program "
                 + "was interrupted");
         ie.printStackTrace();
      }
return imgStream;   }
   /**
    * Writes the source of the graph in a file, and returns the written file
    * as a File object.
    * @param str Source of the graph (in dot language).
    * @return The file (as a File object) that contains the source of the graph.
    * @throws java.io.IOException the error.
    */
   public File writeDotSourceToFile(final String str)
           throws java.io.IOException {
      File temp;
      try {
         temp = File.createTempFile("graph_", ".dot.tmp",
                 new File(Graph.tempDir));
         FileWriter fout = new FileWriter(temp);
         fout.write(str);
         fout.close();
      } catch (Exception e) {
         System.err.println("Error: I/O error while writing the dot source "
                 + "to temp file!");
         return null;
      }
      return temp;
   }
/**
    * Returns a string that is used to start a graph.
    * @return A string to open a graph.
    */
   public String startGraph() {
      return "digraph G {";
   }
/**
    * Returns a string that is used to end a graph.
    * @return A string to close a graph.
    */
   public String endGraph() {
      return "}";
   }
/**
    * Read a dotGraph graph from a text file.
    *
    * @param input Input text file containing the dotGraph graph
    * source.
    */
   public void readSource(final String input) {
    StringBuilder sb = new StringBuilder();

    try {
     FileInputStream fis = new FileInputStream(input);
     DataInputStream dis = new DataInputStream(fis);
     BufferedReader br = new BufferedReader(new InputStreamReader(dis));
     String line;
     while ((line = br.readLine()) != null) {
      sb.append(line);
     }
     dis.close();
    } catch (Exception e) {
     System.err.println("Error: " + e.getMessage());
    }

    this.graph = sb;
   }
}
