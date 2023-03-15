import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class inputGenerator {
    private static Scanner scanner = new Scanner(System.in); 
    public static void main (String []args) throws java.lang.InterruptedException, IOException {
        System.out.println("Enter path of output file (C:\\\\Directory\\\\Subdirectory\\\\file.txt)");
        String string = scanner.nextLine(); 
        FileWriter file = new FileWriter(string);
        PrintWriter writer = new PrintWriter(file);
        System.out.println("Enter number of nodes");
        int N = scanner.nextInt();
        System.out.println("Enter max distance");
        double M = scanner.nextDouble();
        System.out.println("Enter link precentage");
        double O = scanner.nextDouble();
        double P = (M/100)*O;
        int[][] matrix= new int[N][N];
        Random random = new Random();
                for (int i=0; i < N; i++) {
            String str =  "";
            for (int j=0; j < N; j++){
                if (i == j){
                    matrix[i][j] = 0;
                } else{
                    matrix[i][j] = random.ints(0, (int)M).findFirst().getAsInt();
                }
                if ( P < matrix[i][j]){
                    if (j == 0){
                        str = str + "-";    
                    } else{
                        str = str + " -";
                    }
                } else{
                    if (j == 0){
                        str = str + matrix[i][j];    
                    } else{
                        str = str + " " + matrix[i][j];
                    }
                }
            }
            writer.println(str);
        }
        System.out.println("Succesfully updated file");
        file.close();
    }
}