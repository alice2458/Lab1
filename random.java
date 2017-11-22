public class random 
{
	public static String randomWalk(final int[][] currentGraph) {
        final java.util.Random r = new java.util.Random();
        int head = r.nextInt(create.dotsNum());
        String output = Directed_Graph.dots[head] + " ";
        String queue = String.valueOf(head);
        do {
            int[] eff = new int[create.dotsNum()];
            int flag = 0;
            for (int i = 0; i < create.dotsNum(); i++) {
                if (currentGraph[head][i] != 0) {
                    eff[flag] = i;
                    flag = flag + 1;
                }
            }
            if (flag == 0)
            	break;
            final int k = r.nextInt(flag);
            queue = queue + eff[k];
            output = output + Directed_Graph.dots[eff[k]] + " ";
            head = eff[k];
            if (queue.substring(0, queue.length() - 2).indexOf(
                    queue.substring(queue.length() - 2,
                    queue.length()), 0) != -1) {
                break;
            }
        } while (true);
        return output;
    }
}
