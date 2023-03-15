import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;

public class floyd{
    static final int INFINITE = Integer.MAX_VALUE/2;
    private static Scanner scanner = new Scanner(System.in);
    public static void main (String[] args) throws IOException {
        Graph mygraph = new Graph();
        int enablelog = 0;
        switch (args.length){
            case 0:
                //pedir grafo
                mygraph = createnewGraph();
                //resultados por pantalla
                print(mygraph.getInfo(mygraph.getMatrixofPath(enablelog, 0), enablelog, 0)); 
                break;
            case 1:
                if (args[0].equals("-h")){
                    //imprimir ayuda
                    displayHelp();
                } else if (args[0].equals("-t")){
                    //habilitar traza
                    enablelog = 1;
                    //pedir grafo
                    mygraph = createnewGraph();
                    //resultados por pantalla
                    print(mygraph.getInfo(mygraph.getMatrixofPath(enablelog, 0), enablelog, 0)); 
                } else {
                    //volcar grafo
                    mygraph = converttoGraph (args[0]);
                    //resultados por pantalla
                    print(mygraph.getInfo(mygraph.getMatrixofPath(enablelog, 0), enablelog, 0));
                    }
                break;
            case 2:
                if (args[0].equals("-t")){
                    //habilitar traza
                    enablelog = 1;
                        //volcar grafo
                        mygraph = converttoGraph (args[1]);
                        //resultados por pantalla
                        print(mygraph.getInfo(mygraph.getMatrixofPath(enablelog, 0), enablelog, 0));
                } else {                    
                    //volcar grafo
                    mygraph = converttoGraph(args[0]);
                    //resultados por archivo
                    createOutputFile (mygraph.getInfo(mygraph.getMatrixofPath(enablelog, 0), enablelog, 0), args [1]);
                }
                break;
            case 3:
                //habilitar traza
                enablelog = 1;
                //volcar grafo
                mygraph = converttoGraph (args[1]);
                //resultados por archivo
                createOutputFile (mygraph.getInfo(mygraph.getMatrixofPath(enablelog, 0), enablelog, 0), args[2]);
                break;
            default:
                break;
        }
    }
    
    static void displayHelp(){
        System.out.println ("SINTAXIS: floyd [-h][-t] [fichero entrada] [fichero salida]");
        System.out.println ("el parámetro [-h] debe ser único");
        System.out.println ("el programa puede ejecutarse sin argumentos");
        System.out.println ("los campos deben respetar el orden establecido más arriba");
        System.out.println ("Si solo existe un argumento ruta se considerará la del archivo de entrada");
        System.out.println ("El formato de ruta de fichero es Unidad:\\\\Directorio\\\\Subdirectorio\\\\archivo.txt");
        System.out.println ("-t Traza cada llamada recursiva y sus parámetros");
        System.out.println ("-h Muestra esta ayuda");
        System.out.println ("[fichero entrada] Ruta del archivo .txt entrada Matriz de adyacencia del grafo");
        System.out.println ("[fichero salida] Ruta del archivo .txt salida Para cada par de nodos la lista de nodos del camino más corto y la longitud del camino");
    }
    
    static Graph createnewGraph(){
        System.out.println ("Introduzca número de nodos");
        Graph graph = new Graph(scanner.nextInt());
        int aux[][] = new int [graph.size()][graph.size()];
        scanner.nextLine();
        for (int i = 0 ; i < graph.size(); i++){
            for (int j = 0; j < graph.size(); j++){
                if (i == j){
                    aux [i][j] = 0;
                } else {
                    String auxstr;
                    System.out.println ("introduzca la longitud de la arista que une el nodo " + (i + 1) + " con el nodo " + (j + 1));  
                    System.out.println ("si no existe dicha arista introduzca un guión"); 
                    auxstr = scanner.nextLine();
                    if (auxstr.equals("-")){
                        aux [i][j] = INFINITE;
                    } else {
                        aux [i][j] = Integer.parseInt (auxstr);
                    }
                }
            }            
        }
        graph.fill(aux);
        System.out.println("Matriz guardada:");
        graph.print();
        return graph;  
    }
    
    static Graph converttoGraph (String path) throws IOException{
        BufferedReader reader = new BufferedReader (new FileReader(path));
        String line;
        int size = 0;
        while (reader.readLine() != null){
            size++;
        }
        reader.close();
        Graph graph = new Graph(size);
        int aux [][] = new int[size][size];
        reader = new BufferedReader (new FileReader(path)); 
        for (int i = 0; (line = reader.readLine()) != null ; i++){
            String values[] = line.split (" ");
            for (int j = 0; j < size; j++){
                if (values[j].equals("-")){
                    aux[i][j] = INFINITE;
                } else{
                    aux[i][j] = Integer.parseInt (values[j]);
                }
            }
        }
        reader.close();
        graph.fill(aux);
        System.out.println("Matriz leída:");
        graph.print();      
        return graph;
    }
    
    static void print(Solution s){
        for (int i = 0; i < s.size; i++){
            int o = s.get(i).getOrigin()+1;
            int d = s.get(i).getDestination()+1;
            ArrayList <Integer> n = s.get (i).getNodesofPath();
            int dis = s.get(i).getDistance();
            String str = "[" + o + ", " + d + "]: " + n + ": ";
            if (dis == INFINITE){
                str = str + "inalcanzable";
            } else{
                str = str + dis;
            }
            System.out.println(str);
        }
    }
    
    static void createOutputFile (Solution s, String arg) throws IOException{
        File test = new File (arg);
        if (test.exists()){
            System.out.println ("El archivo de salida ya existe y no se puede sobreescribir");
        }else{
            FileWriter file = new FileWriter(arg);
            PrintWriter writer = new PrintWriter (file);
            for (int i = 0; i < s.size; i++){
                int o = s.get(i).getOrigin()+1;
                int d = s.get(i).getDestination()+1;
                ArrayList <Integer> n = s.get (i).getNodesofPath();
                int dis = s.get(i).getDistance();
                String str = "[" + o + ", " + d + "]: " + n + ": ";
                if (dis == INFINITE){
                    str = str + "inalcanzable";
                } else{
                    str = str + dis;
                }
                writer.println(str);
            }
            file.close();
            writer.println ("Archivo de salida creado con éxito");
        }
    }
    
}
