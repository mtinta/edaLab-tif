import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
       
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                PlagiarismCheckerGUI gui = new PlagiarismCheckerGUI();  //Instanciando interfaz
                gui.setVisible(true);                                 
            }
        });
    }
}
