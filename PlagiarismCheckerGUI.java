import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class PlagiarismCheckerGUI extends JFrame {
    private PlagiarismChecker plagiarismChecker;
    private JTextArea resultTextArea;
    private JTextArea checkFileTextArea;

    public PlagiarismCheckerGUI() {
        plagiarismChecker = new PlagiarismChecker();

        setTitle("Detector de Plagio");
        setSize(800, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        resultTextArea = new JTextArea();
        resultTextArea.setEditable(false);
        JScrollPane resultScrollPane = new JScrollPane(resultTextArea);
        panel.add(resultScrollPane, BorderLayout.CENTER);

        JButton loadButton = new JButton("Cargar Bases de Datos");
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //CARGAR ARCHIVOS DE BASES DE DATOS
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setMultiSelectionEnabled(true); //Deja escoger muchos ala vez

                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File[] selectedFiles = fileChooser.getSelectedFiles();
                    String[] paths = new String[selectedFiles.length];
                    for (int i = 0; i < selectedFiles.length; i++) {
                        paths[i] = selectedFiles[i].getAbsolutePath();
                    }

                    if (plagiarismChecker.loadFiles(paths)) {
                        resultTextArea.setText("Las bases de datos se cargaron correctamente.\n");
                    } else {
                        resultTextArea.setText("Error .\n");
                    }
                }
            }
        });

        checkFileTextArea = new JTextArea();
        checkFileTextArea.setEditable(false);
        JScrollPane checkFileScrollPane = new JScrollPane(checkFileTextArea);
        panel.add(checkFileScrollPane, BorderLayout.EAST);

        JButton checkButton = new JButton("Verificar Plagio");
        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //CARGAR ARCHIVO PARA VERIFICAR
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    String pathToCheck = fileChooser.getSelectedFile().getAbsolutePath();
                    ResultChecker result = plagiarismChecker.verifyPlagiarism(pathToCheck);
                    //ESCRIBIR RESULTADOS
                    if (result != null) {
                        boolean[] results = result.getResults();
                        StringBuilder resultText = new StringBuilder();
                        for (int i = 1; i < results.length; i++) {
                            String fileName = "Database " + (i);
                            resultText.append(fileName).append(": ").append(results[i] ? "Con plagio" : "Sin Plagio").append("\n");
                        }
                        resultTextArea.setText(resultText.toString());
                    } else {
                        resultTextArea.setText("Error.\n");
                    }

                    // CONTENIDO DLE ARCHIVO
                    try (BufferedReader br = new BufferedReader(new FileReader(pathToCheck))) {
                        StringBuilder content = new StringBuilder();
                        String line;
                        while ((line = br.readLine()) != null) {
                            content.append(line).append('\n');
                        }
                        checkFileTextArea.setText(content.toString());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        checkFileTextArea.setText("Error.\n");
                    }
                }
            }
        });

        panel.add(loadButton, BorderLayout.NORTH);
        panel.add(checkButton, BorderLayout.SOUTH);

        add(panel);
    }

    
}
