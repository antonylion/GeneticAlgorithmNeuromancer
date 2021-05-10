import java.util.Random;
import java.util.StringTokenizer;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.lang.Thread;
import java.lang.InterruptedException;
public class MainGeneticAlgorithm{

    //TODO: understand what is our fitness goal; could be win at least 8 games on 10 versus FrittoMisto_Agent
    static final int fitnessGoal = 1;

    Population population = new Population();
    Individual fittest;
    Individual secondFittest;
    int generationCount = 0;

    public static void main(String[] args) {

        Random rn = new Random();

        MainGeneticAlgorithm demo = new MainGeneticAlgorithm();

        //Initialize population
        demo.population.initializePopulation(1);

        //Calculate fitness of each individual
        demo.population.calculateFitness();

        System.out.println("Generation: " + demo.generationCount + " Fittest: " + demo.population.fittest);

        //While population gets an individual with maximum fitness
        while (demo.population.fittest < fitnessGoal) {
            ++demo.generationCount;

            /* CORRECT AND COMPLETE WAY TO GENERATE AN EVOLVED INDIVIDUAL
            //Do selection
            demo.selection();

            //Do crossover
            demo.crossover();

            //********************************************
            //TODO: define a mutuation method to call here
            //********************************************

            //Add fittest offspring to population
            demo.addFittestOffspring();
            */

            //DUMMY WAY TO TRY ONE INDIVIDUAL AT TIME, RANDOMLY (WITH NO EVOLUTION)
            demo.population.inidividualRetry();

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
        for (int i = 0; i < 5; i++) {
            System.out.print(demo.population.getFittest().genes[i]);
        }

        System.out.println("");

    }

    //Selection
    void selection() {

        //Select the most fittest individual
        fittest = population.getFittest();

        //Select the second most fittest individual
        secondFittest = population.getSecondFittest();
    }

    //Crossover
    void crossover() {
        Random rn = new Random();

        //Select a random crossover point
        int crossOverPoint = rn.nextInt(population.individuals[0].geneLength);

        //Swap values among parents
        for (int i = 0; i < crossOverPoint; i++) {
            int temp = fittest.genes[i];
            fittest.genes[i] = secondFittest.genes[i];
            secondFittest.genes[i] = temp;

        }

    }

    Individual getFittestOffspring() {
        if (fittest.fitness > secondFittest.fitness) {
            return fittest;
        }
        return secondFittest;
    }

    //Replace least fittest individual from most fittest offspring
    void addFittestOffspring() {

        //Update fitness values of offspring
        fittest.calcFitness();
        secondFittest.calcFitness();

        //Get index of least fit individual
        int leastFittestIndex = population.getLeastFittestIndex();

        //Replace least fittest individual from most fittest offspring
        population.individuals[leastFittestIndex] = getFittestOffspring();
    }



}