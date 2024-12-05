
import java.util.*;

public class Proyectov3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int numCasos = Integer.parseInt(scanner.nextLine());

        for (int caso = 0; caso < numCasos; caso++) {
            String[] primeraLinea = scanner.nextLine().split(" ");
            int n = Integer.parseInt(primeraLinea[0]);
            int d = Integer.parseInt(primeraLinea[1]);

            List<Celula> celulas = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                String[] datosCelula = scanner.nextLine().split(" ");
                int id = Integer.parseInt(datosCelula[0]);
                int x = Integer.parseInt(datosCelula[1]);
                int y = Integer.parseInt(datosCelula[2]);
                HashSet<String> peptidos = new HashSet<>();
                for (int j = 3; j < datosCelula.length; j++) {
                    peptidos.add(datosCelula[j]);
                }
                celulas.add(new Celula(id, x, y, peptidos));
            }

            // Crear el grafo de mensajes
            HashMap<Integer, Set<Integer>> mensajes = new HashMap<>();
            for (int i = 0; i < n; i++) {
                Celula c1 = celulas.get(i);
                for (int j = i + 1; j < n; j++) {
                    Celula c2 = celulas.get(j);

                    double distancia = c1.distancia(c2);
                    if (distancia <= d) {
                        HashSet<String> peptidosC1 = c1.peptidos;
                        HashSet<String> peptidosC2 = c2.peptidos;

                        boolean peptidosComunes = Celula.contarPeptidosComunes(peptidosC1, peptidosC2);
                        if (peptidosComunes) {
                            mensajes.computeIfAbsent(c1.id, k -> new HashSet<>()).add(c2.id);
                            mensajes.computeIfAbsent(c2.id, k -> new HashSet<>()).add(c1.id);
                        }
                    }
                }
            }

            // Aplicar el algoritmo greedy para encontrar los grupos
            List<Integer> cliques = greedy(mensajes, n);

            System.out.println("Caso #" + (caso + 1) + ":");
            for (int i = 0; i < celulas.size(); i++) {
                int id = celulas.get(i).id; // Identificador de la célula
                int grupo = cliques.get(i); // Grupo al que pertenece
                System.out.println(id + " " + grupo);
            }
        }
    }

    // Método greedy para agrupar las células
    public static List<Integer> greedy(HashMap<Integer, Set<Integer>> grafo, int n) {
        List<Integer> vertices = new ArrayList<>(grafo.keySet());
        Collections.shuffle(vertices); // Permutar aleatoriamente
        List<Integer> cliques = new ArrayList<>(Collections.nCopies(n, 0));
        Map<Integer, Integer> tamanios = new HashMap<>();
        tamanios.put(0, n);
        int c = 1;

        for (int v : vertices) {    
            boolean etiquetado = false;
            Map<Integer, Integer> cliquesVecinos = new HashMap<>();

            for (int vecino : grafo.getOrDefault(v, Collections.emptySet())) {
                cliquesVecinos.put(cliques.get(vecino - 1), cliquesVecinos.getOrDefault(cliques.get(vecino - 1), 0) + 1);
            }

            for (Map.Entry<Integer, Integer> entry : cliquesVecinos.entrySet()) {
                int clique = entry.getKey();
                int tam = entry.getValue();
                if (tam == tamanios.getOrDefault(clique, 0)) {
                    cliques.set(v - 1, clique);
                    tamanios.put(clique, tamanios.getOrDefault(clique, 0) + 1);
                    etiquetado = true;
                    break;
                }
            }

            if (!etiquetado) {
                cliques.set(v - 1, c);
                tamanios.put(c, tamanios.getOrDefault(c, 0) + 1);
                c++;
            }
        }

        return cliques;
    }
}

// Clase Celula
class Celula {
    int id;
    int x;
    int y;
    HashSet<String> peptidos;

    public Celula(int id, int x, int y, HashSet<String> peptidos) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.peptidos = peptidos;
    }

    public double distancia(Celula otra) {
        return Math.sqrt(Math.pow(this.x - otra.x, 2) + Math.pow(this.y - otra.y, 2));
    }

    public static boolean contarPeptidosComunes(HashSet<String> peptidosC1, HashSet<String> peptidosC2) {
        if (peptidosC1.size() > peptidosC2.size()) {
            return contarInterseccion(peptidosC2, peptidosC1);
        } else {
            return contarInterseccion(peptidosC1, peptidosC2);
        }
    }

    private static boolean contarInterseccion(HashSet<String> pequeno, HashSet<String> grande) {
        for (String peptido : pequeno) {
            if (grande.contains(peptido)) {
                return true;
            }
        }
        return false;
    }
}
