class TrieNode {
    private TrieNode[] children;
    private boolean isEndOfWord;    //boleano que determina si la letra es final de una palabra.

    public TrieNode() {
        children = new TrieNode[26]; // array para las 26 letras
        isEndOfWord = false;
    }
//Metodo para insertar palabra en el trie
    public void insert(String word) {
        TrieNode node = this;
        for (char ch : word.toCharArray()) {
            int index = ch - 'a';   //Convirtiendo a indices numericos mediante la resta de 'a'
            if (node.children[index] == null) {
                node.children[index] = new TrieNode();
            }
            node = node.children[index];
        }
        node.isEndOfWord = true;
    }
//Metodo para buscar palabra en el trie
    public boolean search(String word) {
        TrieNode node = this;
        for (char ch : word.toCharArray()) {
            int index = ch - 'a';    //Convirtiendo a indices numericos mediante la resta de 'a'
            if (node.children[index] == null) {
                return false;
            }
            node = node.children[index];
        }
        return node.isEndOfWord;
    }
}
