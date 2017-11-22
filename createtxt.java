public class createtxt 
{
	public static String generateNewText(final int[][] currentGraph,
            final String inputText) {
        String word = inputText.replaceAll("[^a-zA-Z]", " ");
        word = word.replaceAll(" +", " ");
        String[] words = new String[Interaction.MAX];
        words = word.split(" ");
        String outputText = "";
        int headindex = 0;
        int tailindex = 0;
        for (int i = 0; i < words.length - 1; i++) {
            final String bridgeTemp = find.queryBridgeWords(currentGraph,
                    words[i], words[i + 1]);
            final java.util.Random r = new java.util.Random();
            if (bridgeTemp.equalsIgnoreCase("")
                        || bridgeTemp.charAt(0) == '#') {
                tailindex = inputText.indexOf(words[i + 1], headindex)
                        + words[i + 1].length() - 1;
                outputText = outputText
                             + inputText.substring(headindex,
                                     headindex + words[i].length() + 1);
            } else {
                String[] bridgeWordArray = new String[Interaction.MAX];
                bridgeWordArray = bridgeTemp.split(",");
                if (bridgeWordArray.length == 1) {
                    tailindex = inputText.indexOf(words[i + 1], headindex)
                            + words[i + 1].length() - 1;
                    outputText = outputText + inputText.substring(headindex,
                            inputText.indexOf(words[i + 1],
                                    headindex))
                            + bridgeWordArray[0] + ' ';
                } else {
                    tailindex = inputText.indexOf(words[i + 1], headindex)
                            + words[i + 1].length() - 1;
                    final int number = r.nextInt(bridgeWordArray.length);
                    outputText += inputText.substring(headindex,
                             headindex + words[i].length() + 1)
                             + bridgeWordArray[number] + ' ';
                }
            }
            headindex = tailindex - words[i + 1].length() + 1;
        }
        outputText += inputText.substring(headindex, inputText.length());
        return outputText;
    }
}
