public class find 
{
	public static String queryBridgeWords(final int[][] currentGraph,
            final String word1, final String word2) {
        final int len = create.dotsNum();
        final int m = create.findAim(word1, len);
        final int n = create.findAim(word2, len);
        int num = -1;
        String bridgeWord = "";
        if (m == -1 && n != -1) {
            return bridgeWord + '#' + word1;
        } else if (n == -1 && m != -1) {
            return bridgeWord + '#' + word2;
        } else if (m == -1 && n == -1) {
            return bridgeWord + "#" + word1 + word2;
        } else {
            for (int i = 0; i < len - 1; i++) {
                if (currentGraph[m][i] != 0 && currentGraph[i][n] != 0) {
                    num = num + 1;
                    bridgeWord = bridgeWord + Directed_Graph.dots[i] + ',';
                }
            }
            return bridgeWord;
        }
    }
}
