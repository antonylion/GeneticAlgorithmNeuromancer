import java.util.Random;
import java.util.StringTokenizer;
import java.io.File;  // Import the File class
import java.io.IOException;
import java.util.Scanner; // Import the Scanner class to read text files
import java.lang.Thread;
import java.lang.InterruptedException;
public class MainGeneticAlgorithm{

    //TODO: understand what is our fitness goal; could be win at least 8 games on 10 versus FrittoMisto_Agent
    static int fitnessGoal = 1;
    static String outputFilePath = "/tmp/NeuroWeights.txt";
    static String improvementOutputFilePath = "/tmp/ImprovementWeights.txt";

    Population population = new Population();
    static Individual fittest;
    static Individual secondFittest;
    int generationCount = 0;

    public static void main(String[] args) {

        if (args.length < 2) {
            System.err.println("Usage: java MainGeneticAlgorithm ID player");
            System.exit(1);
        }

        String geneticStartFileName = "/tmp/genetic_start";
        String gameStartFileName = "/tmp/game_start";

        String id = args[0];
        geneticStartFileName += id;
        gameStartFileName += id;
        String player = args[1];

        Random rn = new Random();

        MainGeneticAlgorithm demo = new MainGeneticAlgorithm();

        //Initialize population
        demo.population.initializePopulation(1, player, outputFilePath, Integer.parseInt(id));

        //Calculate fitness of each individual
        demo.population.calculateFitness();

        System.out.println("Generation: " + demo.generationCount + " Fittest: " + demo.population.fittest);
        fittest = demo.population.getFittest();
        fitnessGoal = demo.population.fittest + 1;
        File gameStartFile = new File(gameStartFileName);
        File geneticStartFile = new File(geneticStartFileName);

        while(true){
            //While population gets an individual with maximum fitness
            while (demo.population.fittest < fitnessGoal) {
                System.out.println("Waiting for " + geneticStartFileName);
                // Wait for geneticStartFile to be created by GameTablut
                while (!geneticStartFile.exists()) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        System.exit(1);
                    }
                }
                // Delete geneticStartFile; it will be recreated by GameTablut
                geneticStartFile.delete();
                System.out.println("Deleted geneticStartFile " + geneticStartFileName);
                ++demo.generationCount;

                //DUMMY WAY TO TRY ONE INDIVIDUAL AT TIME, RANDOMLY (WITH NO EVOLUTION)
                //demo.population.inidividualRetry();

                //Evolving with first strategy
                demo.population.retryWithFirstStrategy();

                //Calculate new fitness value
                demo.population.calculateFitness();

                //System.out.println("Generation: " + demo.generationCount + " Fittest: " + demo.population.fittest);
                System.out.println("Generation: " + demo.generationCount + " Fittest: " + demo.population.individuals[0].getMaxFitness());
                // Let GameTablut run
                try {
                    if (!gameStartFile.createNewFile()) {
                        System.err.println(gameStartFileName + " already exists. Timing violation. Exiting...");
                        throw new IOException();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    System.exit(1);
                }
            }

            System.out.println("\nSolution found in generation " + demo.generationCount);
            System.out.println("Fitness: "+demo.population.getFittest().fitness);
            System.out.print("Genes: ");
            for (int i = 0; i < demo.population.individuals[0].genes.length; i++) {
                System.out.print(demo.population.getFittest().genes[i] + ";");
            }
            System.out.println("");

            //write this improvement to improvementOutputFilePath and pass to compute next weights
            demo.population.markImprovement(improvementOutputFilePath, demo.population.getFittest().genes);

            //restart evolution
            fittest = demo.population.getFittest();
            demo.population.fittest = demo.population.individuals[0].getMaxFitness();
            demo.population.fittest--;
            fitnessGoal = demo.population.fittest + 1;
            demo.population.individuals[0].decMaxFitness();
        }

    }




}
