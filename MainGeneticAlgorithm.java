import java.util.Random;
import java.util.StringTokenizer;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.lang.Thread;
import java.lang.InterruptedException;
public class MainGeneticAlgorithm{

    static int fitnessGoal = 1;

    Population population = new Population();
    static Individual fittest;
    int generationCount = 0;

    public static void main(String[] args) {

        String player = args[0];

        Random rn = new Random();

        MainGeneticAlgorithm demo = new MainGeneticAlgorithm();

        //Initialize population
        demo.population.initializePopulation(1, player);

        //Calculate fitness of each individual
        demo.population.calculateFitness();

        System.out.println("Generation: " + demo.generationCount + " Fittest: " + demo.population.fittest);
        fittest = demo.population.getFittest();
        fitnessGoal = demo.population.fittest + 1;

        //While population gets an individual with maximum fitness
        while (demo.population.fittest < fitnessGoal) {
            ++demo.generationCount;

            //DUMMY WAY TO TRY ONE INDIVIDUAL AT TIME, RANDOMLY (WITH NO EVOLUTION)
            //demo.population.inidividualRetry();

            //Evolving with first strategy
            demo.population.retryWithFirstStrategy();

            //Calculate new fitness value
            demo.population.calculateFitness();

            System.out.println("Generation: " + demo.generationCount + " Fittest: " + demo.population.fittest);
            try{
                Thread.sleep(2000);
            }
            catch(InterruptedException e){
                System.out.println("Interrupted Exception");
                System.exit(0);
            }
        }

        System.out.println("\nSolution found in generation " + demo.generationCount);
        System.out.println("Fitness: "+demo.population.getFittest().fitness);
        System.out.print("Genes: ");
        for (int i = 0; i < demo.population.individuals[0].genes.length; i++) {
            System.out.print(demo.population.getFittest().genes[i] + ";");
        }

        System.out.println("");

    }
}