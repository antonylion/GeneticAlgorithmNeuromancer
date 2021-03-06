import java.io.*;
import java.util.Random;
import java.util.StringTokenizer;

public class BlackIndividual extends Individual{

    double[] initialGenes = {-5000, 600, 450, -200, 100, 170, -100, 50};

    //Weights indexes
    final static int WEIGHT_VICTORY = 0;
    final static int WEIGHT_RHOMBUS = 1;
    final static int WEIGHTROWCOLCOVER = 2;
    final static int NUMBER_WHITES = 3;
    final static int SURROUNDING_BLACKS = 4;
    final static int NUMBER_BLACKS = 5;
    final static int THREAT = 6;
    final static int BLACK_NEAR_KING = 7;

    //variables for first strategy
    //static int countWeightIndex = 0;
    final static int NUMBER_GENERATION_PER_INDEX = 15;
    static int numberOfGeneration = NUMBER_GENERATION_PER_INDEX;

    public BlackIndividual(String outputFilePath, int id){
        super(outputFilePath, id);
        rn = new Random();
        genes[WEIGHT_VICTORY] = initialGenes[WEIGHT_VICTORY];
        genes[WEIGHT_RHOMBUS] = initialGenes[WEIGHT_RHOMBUS];
        genes[WEIGHTROWCOLCOVER] = initialGenes[WEIGHTROWCOLCOVER];
        genes[NUMBER_WHITES] = initialGenes[NUMBER_WHITES];
        genes[SURROUNDING_BLACKS] = initialGenes[SURROUNDING_BLACKS];
        genes[NUMBER_BLACKS] = initialGenes[NUMBER_BLACKS];
        genes[THREAT] = initialGenes[THREAT];
        genes[BLACK_NEAR_KING] = initialGenes[BLACK_NEAR_KING];

        fitness = 0;
        try {
            var myWriter = new FileWriter(outputFilePath + id);
            for(double peso : genes){
                myWriter.write(peso + ";");
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            System.exit(1);
        }

        try {
            var myWriter = new FileWriter(historyFilePath + id);
            for(double peso : genes){
                myWriter.write(peso + ";");
            }
            myWriter.write("\n");
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            System.exit(1);
        }

    }

    public void changeOneWeight(){
        if (numberOfGeneration / NUMBER_GENERATION_PER_INDEX == WEIGHT_VICTORY) genes[WEIGHT_VICTORY] = -5000;
        if (numberOfGeneration / NUMBER_GENERATION_PER_INDEX == WEIGHT_RHOMBUS) genes[WEIGHT_RHOMBUS] = rn.nextInt(600) + 300;
        if (numberOfGeneration / NUMBER_GENERATION_PER_INDEX == WEIGHTROWCOLCOVER) genes[WEIGHTROWCOLCOVER] = rn.nextInt(450) + 150;
        if (numberOfGeneration / NUMBER_GENERATION_PER_INDEX == NUMBER_WHITES) genes[NUMBER_WHITES] = (rn.nextInt(200) + 100) * (-1);
        if (numberOfGeneration / NUMBER_GENERATION_PER_INDEX == SURROUNDING_BLACKS) genes[SURROUNDING_BLACKS] = (rn.nextInt(150) + 50);
        if (numberOfGeneration / NUMBER_GENERATION_PER_INDEX == NUMBER_BLACKS) genes[NUMBER_BLACKS] = (rn.nextInt(200) + 100);
        if (numberOfGeneration / NUMBER_GENERATION_PER_INDEX == THREAT) genes[THREAT] = (rn.nextInt(100) + 50) * (-1);
        if (numberOfGeneration / NUMBER_GENERATION_PER_INDEX == BLACK_NEAR_KING) genes[BLACK_NEAR_KING] = rn.nextInt(50) + 25;
    }

    public void fixOtherWeights(){
        if (numberOfGeneration / NUMBER_GENERATION_PER_INDEX != WEIGHT_VICTORY) genes[WEIGHT_VICTORY] = initialGenes[WEIGHT_VICTORY];
        if (numberOfGeneration / NUMBER_GENERATION_PER_INDEX != WEIGHT_RHOMBUS) genes[WEIGHT_RHOMBUS] = initialGenes[WEIGHT_RHOMBUS];
        if (numberOfGeneration / NUMBER_GENERATION_PER_INDEX != WEIGHTROWCOLCOVER) genes[WEIGHTROWCOLCOVER] = initialGenes[WEIGHTROWCOLCOVER];
        if (numberOfGeneration / NUMBER_GENERATION_PER_INDEX != NUMBER_WHITES) genes[NUMBER_WHITES] = initialGenes[NUMBER_WHITES];
        if (numberOfGeneration / NUMBER_GENERATION_PER_INDEX != SURROUNDING_BLACKS) genes[SURROUNDING_BLACKS] = initialGenes[SURROUNDING_BLACKS];
        if (numberOfGeneration / NUMBER_GENERATION_PER_INDEX != NUMBER_BLACKS) genes[NUMBER_BLACKS] = initialGenes[NUMBER_BLACKS];
        if (numberOfGeneration / NUMBER_GENERATION_PER_INDEX != THREAT) genes[THREAT] = initialGenes[THREAT];
        if (numberOfGeneration / NUMBER_GENERATION_PER_INDEX != BLACK_NEAR_KING) genes[BLACK_NEAR_KING] = initialGenes[BLACK_NEAR_KING];
    }

    public void retryFirtStrategy(){
        System.out.println("ENTRO retryFirtStrategy");
        System.out.println("Number of generation: " + numberOfGeneration + ". numberOfGeneration / NUMBER_GENERATION_PER_INDEX = " + (numberOfGeneration / NUMBER_GENERATION_PER_INDEX));

        //Search for a different configuration
        changeOneWeight();

        //Restore previous weight initial value if previous computation did't found better fitness
        fixOtherWeights();

        while(!isUnique(genes)){ //While individual, composed by genes, is similar to one already tried
            //Search for a different configuration
            changeOneWeight();

            //Restore previous weight initial value if previous computation did't found better fitness
            fixOtherWeights();
        }

        numberOfGeneration++;

        try { //write to evolution.txt for NeuroApp input
            var myWriter = new FileWriter(outputFilePath + id);
            for(double peso : genes){
                myWriter.write(peso + ";");
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        try { //write (IN APPEND) to History.txt for unique generations
            var myWriter = new FileWriter(historyFilePath + id, true);
            for(double peso : genes){
                myWriter.write(peso + ";");
            }
            myWriter.write("\n");
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void markImprovement(String improvementOutputFilePath, double[] genes){
        String lastMatchResultAndMoves = "";
        try (var br = new BufferedReader(new FileReader(gameOutputFilePath))){
            lastMatchResultAndMoves = br.readLine();
            var myWriter = new FileWriter(improvementOutputFilePath, true);
            myWriter.write("BLACK IMPROVEMENT. RESULT: " + lastMatchResultAndMoves + ". WEIGHTS USED: ");
            for(double peso : genes){
                myWriter.write(peso + ";");
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            System.exit(1);
        }

        //After having saved the improvement, start computing the next weight in the set
        //For example, while computing the 3rd weight the algorithm found and improvement in the fitness function
        //Computing the 3rd weight means that  30 <= numberOfGeneration <= 44;
        //We now want numberOfGeneration to skip from its actual value to 45 in order to compute the 4th weight:
        numberOfGeneration = ((numberOfGeneration / NUMBER_GENERATION_PER_INDEX) + 1) * NUMBER_GENERATION_PER_INDEX;

        //so restart computation with evolved weights
        setInitialGenes(genes);
    }

    public void retrySecondStrategy(){

    }

    public void setInitialGenes(double[] initialGenes) {
        this.initialGenes = initialGenes;
    }

}
