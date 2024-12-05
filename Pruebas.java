import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Pruebas {

    public static void main(String[] args) {
        int numTestCases = 3; // Número de casos de prueba
        String fileName = "test_cases.txt"; // Nombre del archivo de salida

        try (FileWriter writer = new FileWriter(fileName)) {
            // Escribir el número de casos de prueba
            writer.write(numTestCases + "\n");

            Random random = new Random();

            for (int t = 0; t < numTestCases; t++) {
                // Generar n (número de células) y d (distancia máxima)
                int n = random.nextInt(100) + 1; // 1 ≤ n ≤ 100 para un ejemplo pequeño
                int d = random.nextInt(10) + 1; // 1 ≤ d ≤ 10
                writer.write(n + " " + d + "\n");

                // Generar un conjunto base de péptidos compartidos
                int sharedPeptideCount = random.nextInt(10) + 5; // 5 a 15 péptidos compartidos
                List<String> sharedPeptides = new ArrayList<>();
                for (int i = 0; i < sharedPeptideCount; i++) {
                    sharedPeptides.add(generatePeptide());
                }

                // Generar un punto base
                int baseX = random.nextInt(101);
                int baseY = random.nextInt(101);

                for (int i = 1; i <= n; i++) {
                    // Generar coordenadas dentro de la distancia máxima d
                    int x = baseX + random.nextInt(2 * d + 1) - d; // Generar en [-d, d] alrededor de baseX
                    int y = baseY + random.nextInt(2 * d + 1) - d; // Generar en [-d, d] alrededor de baseY

                    // Generar péptidos (m hasta 5 cadenas por célula)
                    int numPeptides = random.nextInt(5) + 1; // Entre 1 y 5 péptidos por célula
                    StringBuilder peptides = new StringBuilder();

                    // Agregar algunos péptidos compartidos
                    int numSharedPeptides = random.nextInt(numPeptides) + 1; // Al menos 1 compartido
                    Collections.shuffle(sharedPeptides); // Mezclar para variedad
                    for (int j = 0; j < numSharedPeptides; j++) {
                        peptides.append(sharedPeptides.get(j)).append(" ");
                    }

                    // Agregar péptidos únicos si es necesario
                    for (int j = numSharedPeptides; j < numPeptides; j++) {
                        peptides.append(generatePeptide()).append(" ");
                    }

                    // Escribir la línea de la célula
                    writer.write(i + " " + x + " " + y + " " + peptides.toString().trim() + "\n");
                }
            }

            System.out.println("Casos de prueba generados en " + fileName);

        } catch (IOException e) {
            System.err.println("Error al escribir el archivo: " + e.getMessage());
        }
    }

    // Método para generar una cadena aleatoria de 5 caracteres
    private static String generatePeptide() {
        String aminoAcids = "ACDEFGHIKLMNPQRSTVWY"; // Aminoácidos comunes
        Random random = new Random();
        StringBuilder peptide = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            peptide.append(aminoAcids.charAt(random.nextInt(aminoAcids.length())));
        }
        return peptide.toString();
    }
}


