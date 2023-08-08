public class ResultChecker {    //Almacenar resultados de la deteccion de plagio para cada archivo base de datos
    private boolean[] results; //Arreglo de booleanos con los resultados

    public ResultChecker(int numFiles) {
        results = new boolean[numFiles]; //Crea un arreglo de booleanos para almacenar resultados de deteccion de plagio
    }

    public void setPlagiarism(int fileIndex, boolean isPlagiarized) {
        results[fileIndex] = isPlagiarized;         //Establece el resultado de plagio en el indice especificado
    }

    public boolean[] getResults() {
        return results; //Obterner el arreglo de resultados
    }
}
