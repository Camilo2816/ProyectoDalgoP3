import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

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

            HashMap<Integer, Set<Integer>> mensajes = new HashMap<Integer, Set<Integer>>();
            

            for (int i = 0; i < n; i++) {
                Celula c1 = celulas.get(i);
                for (int j = i + 1; j < n; j++) {
                    Celula c2 = celulas.get(j);
                    
                    double distancia = c1.distancia(c2);
                    if (distancia <= d) {
                        HashSet<String> peptidosC1 = c1.peptidos;
                        HashSet<String> peptidosC2 = c2.peptidos;
        
                        boolean peptidosComunes = Celula.contarPeptidosComunes(peptidosC1, peptidosC2);
                        if(peptidosComunes)
                        {
                        
                        if (mensajes.containsKey(c2.id))
                        {
                            mensajes.get(c2.id).add(c1.id);
                        }
                        else
                        {
                            mensajes.put(c2.id, new HashSet<Integer>());
                            mensajes.get(c2.id).add(c1.id);
                        }

                        if (mensajes.containsKey(c1.id))
                        {
                            mensajes.get(c1.id).add(c2.id);
                        }
                        else
                        {
                            mensajes.put(c1.id, new HashSet<Integer>());
                            mensajes.get(c1.id).add(c2.id);
                        }
                      }           
                                            
                    }
                    
                }
            }
            imprimirMensajes(mensajes);
            System.out.println("Fin de la ejecución");
               
        }

    
    }


    public static void imprimirMensajes(HashMap<Integer, Set<Integer>> mensajes) {
        for (Map.Entry<Integer, Set<Integer>> entry : mensajes.entrySet()) {
            System.out.print(entry.getKey() + ": ");
            for (Integer id : entry.getValue()) {
                System.out.print(id + " ");
            }
            System.out.println();
        }
    }
}
    



   
    

    

    
    
    

   

class Celula {
    int id;
    int x;
    int y;
    int tipo;
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

