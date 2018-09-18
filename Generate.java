import java.io.*; 
import java.util.*; 
import java.lang.Math;

public class Generate {
    public static void main(String[] args) {
        System.out.println("50000 1 1000000000");
        for (int i = 1; i <= 500000; i++) {
            double rand1 = Math.random();
            double rand2 = Math.random();
            if (rand1 < .5) {
                System.out.printf("S %d\n", (int) (rand2 * 1000000000.0));
            } else {
                System.out.printf("NS %d\n", (int) (rand2 * 1000000000.0));
            }
        }
    }

}

