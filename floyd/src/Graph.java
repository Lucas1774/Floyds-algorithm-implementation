import java.util.ArrayList;

public class Graph{
    static final int INFINITE = Integer.MAX_VALUE/2;
    private int matrix[][];
    public Graph (){
    }
    
    public Graph(int N){
        matrix = new int[N][N];
    }
    
    public int[][] get(){
        return this.matrix;
    }
    
    public int size(){
        return this.matrix.length;
    }
    
    public void edit (int row, int column, int value){
        this.matrix[row][column] = value;
    }
    
    public void fill (int data[][]){
        this.matrix = data;
    }
        
    public void print(){
        for (int i = 0; i < this.get().length; i++) {
            for (int j = 0; j < this.get()[i].length; j++) {
                if (this.get()[i][j] == INFINITE){
                    System.out.print("- ");
                } else {
                    System.out.print(this.get()[i][j] + " ");
                }
            }
        System.out.println();
        }
    }
    
    public Graph getMatrixofPath(int enablelog, int depth){
        Graph s = new Graph(this.size());
        for (int k = 0; k < this.size(); k++){
            for (int i = 0; i < this.size(); i++){
                for (int j = 0; j < this.size(); j++){
                    if (this.get()[i][k] + this.get()[k][j] < this.get()[i][j]){
                        s.edit(i, j, k+1);
                        this.edit (i, j, this.get()[i][k] + this.get()[k][j]);
                        if (enablelog == 1){
                            System.out.println("Se actualizó la distancia del nodo " + (i+1)+ " al nodo " + (j+1) + " pasando por el nodo " + (k+1));
                            this.print();
                        }
                    }
                }
            }
        }
        if (enablelog == 1){
            System.out.println("Matriz asociada con el último punto del camino desde el que se llega desde un i a un j:");
            s.print();
        }
        return s;
    }
    
    public Solution getInfo(Graph matrixofpath, int enablelog, int depth){
        Solution s = new Solution();
        for (int i = 0; i < this.size(); i++){
            for (int j = 0; j < this.size(); j++){
                ArrayList <Integer> aux = new ArrayList <Integer>();
                s.add(i, j, matrixofpath.getNodesofPath(i, j, aux, enablelog, depth),this.get()[i][j]);
            }            
        }
        return s;
    }
    
    public ArrayList <Integer> getNodesofPath(int i, int j, ArrayList <Integer> aux, int enablelog, int depth){
        depth++;
        int k = this.get()[i][j];
        if (k != 0){
            if (enablelog == 1){
                String string = "";
                for(int l = 0; l < depth; l++){
                    string = "  " + string;
                }
                System.out.println (string + "el camino más corto entre el nodo " + (i+1) + " y el nodo " + (j+1) + " pasa por el nodo " + k);
            }
            this.getNodesofPath(i, k-1, aux, enablelog, depth);
            aux.add(k);
            this.getNodesofPath(k-1, j, aux, enablelog, depth);
        }
        return aux;
    }
}
