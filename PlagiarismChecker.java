import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PlagiarismChecker {
    private TrieNode root;  //Nodo del trie raiz
    private String[] fileContents;  //String con la lista de archivos base de datos

    public PlagiarismChecker() {    //Constructor
        root = new TrieNode();  //el nodo inicial es la raiz
    }
    public boolean loadFiles(String[] paths) {  //Metodo para cargar las rutas de las bases de datos en forma de arreglo
        fileContents = new String[paths.length]; // Inicializa el arreglo de contenidos de archivos        
        for (int i = 0; i < paths.length; i++) {
            try (BufferedReader br = new BufferedReader(new FileReader(paths[i]))) {
                String line;    //Inicializacion de variable para guardar lineas
                StringBuilder content = new StringBuilder();    //Se guardan las lineas  en un Streingbuilder
                
                while ((line = br.readLine()) != null) {    //Se lee el archivo linea por linea
                    content.append(line).append('\n');  //Se incluye la linea en el StringBuilder
                }
                fileContents[i] = content.toString();   //Se almacena las lineas en un array fileContents
                addToTrie(fileContents[i]); //Se usa addToTrie para agregar las letras a el Trie.
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }      
        return true;
    }
    private void addToTrie(String text) {   //Metodo para agregar palabras al trie, necesita un string
    String[] words = text.split("\\s+");    //Divide el texto en palabras usando regex s+ como estpacios
    for (String word : words) {
        String processedWord = preprocessWord(word);    //Se preprocesa la palabra
        if (!processedWord.isEmpty()) {
            root.insert(processedWord); //Agraga la palabra al trie usando .insert
        }
    }
}
//Metodo de extraido de stackoverflow
private String preprocessWord(String word) {    //Metodo para preprocesamiento
    // Convertir a minúsculas, eliminar caracteres especiales y letras no pertenecientes al alfabeto en inglés
    String cleanedWord = word.toLowerCase().replaceAll("[^a-z]", ""); //Este regex convierte en minusculas y elimina letras que no sean del inlges
    return cleanedWord; //devuelve la palabra
}


public ResultChecker verifyPlagiarism(String path) {    //Metodo que realizara la verificacion, pide como parametro la direccion del archivo a verificar
    //Devuelve un result checker que es un objeto que tiene toda la informacion del proceso .
    ResultChecker result = new ResultChecker(fileContents.length);  //se crea un ResultChecker result con el mismo numero de archivos que bases de datos.

    try (BufferedReader br = new BufferedReader(new FileReader(path))) { //Se lee el archivo a comprobar (el de la direccion path)
        String line;    // Se lee el archivo en lineas
        StringBuilder content = new StringBuilder(); //Se guarda el archivo en lineas en un StringBuilder llamado content

        while ((line = br.readLine()) != null) {     //Se lee el archivo linea por linea
            content.append(line).append('\n');      //Se incluye la linea en el StringBuilder content
        }

        String[] wordsToCheck = content.toString().split("\\s+"); //Divide content en palabras usando el regex s+

        for (int i = 0; i < fileContents.length; i++) { //Iteracion sobre bases de datos
            String databaseText = fileContents[i]; // Obtener el contenido del archivo de la base de datos actual

            int wordCount = 0;
            for (String word : wordsToCheck) {  //Iterar sobre cada palabra del archivo a revisar
                if (databaseText.contains(word)) { // Comparar con el contenido del archivo de la base de datos actual
                    wordCount++;
                }
            }
            //Calcula la similitud entre las palabras del archivo a verificar y las palabras del archivo de la base de datos actual
            double similarity = (double) wordCount / wordsToCheck.length; //Se calcula dividiendo palabras totales de archivo a verificar / Palabras totales del archivo

            if (similarity >= 0.95) { // Si se sobrepasa este umbral, se considerara Plagio
                result.setPlagiarism(i, true);  //Si el texto es considerada plagio se le atribuye un boleano true, que es considerado copia
            }
        }
    } catch (IOException e) {   //Manejo de excepciones
        e.printStackTrace();
        return null;
    }

    return result; //Devuelve objeto ResultChecker
}

}
