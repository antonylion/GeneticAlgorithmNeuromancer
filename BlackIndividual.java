import java.util.Random;
import java.util.StringTokenizer;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors

public class BlackIndividual extends Individual{
    //int fitness = 0;
    static int numeroMosseIniziali = 10000;
    int numeroMosseAttuali = 10000;
    static boolean firstComputation = true;
    //double[] genes = new double[8];
    double[] initialGenes = {-5000, 600, 450, -200, 100, 170, -100, 50};
    StringTokenizer st;
    FileWriter myWriter;
    Random rn;

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

    public BlackIndividual(){
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
            myWriter = new FileWriter("/Users/antonyzappacosta/Desktop/filesForGenetic/evolution.txt");
            for(double peso : genes){
                myWriter.write(peso + ";");
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        try {
            myWriter = new FileWriter("/Users/antonyzappacosta/Desktop/filesForGenetic/History.txt");
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

    //Calculate fitness
    public void calcFitness() {
        String matchResult = "";
        String data = "";
        fitness = 0;

        try {
            File myObj = new File("/Users/antonyzappacosta/Desktop/filesForGenetic/NeuroAppOutput.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                data = myReader.nextLine();
                st = new StringTokenizer(data, ";");
                System.out.println("LETTA RIGA: " + data);
                //LEGGO SE HO VINTO O PERSO
                matchResult = st.nextToken().trim();
                //LEGGO IL NUMERO DI MOSSE DELLA PARTITA
                numeroMosseAttuali = Integer.parseInt(st.nextToken().trim());
                break;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        System.out.println("RISULTATO PARTITA: " + matchResult);


        if(matchResult.equals("WIN")){
            fitness = 2;
        }

        if(firstComputation){
            numeroMosseIniziali = numeroMosseAttuali;
        }

        if(matchResult.equals("WIN") && !firstComputation && numeroMosseAttuali < numeroMosseIniziali){
            fitness = 3;
        }

        if(matchResult.equals("LOSE") && !firstComputation && numeroMosseAttuali > numeroMosseIniziali){
            fitness = 1;
        }

        firstComputation = false;
    }

    /*
    public void retry(){

        //dummy trial of another random individual
        for (int i = 0; i < genes.length; i++) {
            genes[WEIGHT_VICTORY] = -5000;
            genes[WEIGHT_RHOMBUS] = rn.nextInt(600) + 300;
            genes[WEIGHTROWCOLCOVER] = rn.nextInt(450) + 150;
            genes[NUMBER_WHITES] = (rn.nextInt(200) + 100) * (-1);
            genes[SURROUNDING_BLACKS] = (rn.nextInt(150) + 50);
            genes[NUMBER_BLACKS] = (rn.nextInt(200) + 100);
            genes[THREAT] = (rn.nextInt(150) + 50) * (-1);
            genes[BLACK_NEAR_KING] = rn.nextInt(50) + 25;
        }
        try {
            myWriter = new FileWriter("/Users/antonyzappacosta/Desktop/filesForGenetic/evolution.txt");
            for(double peso : genes){
                myWriter.write(peso + ";");
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }*/

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
            myWriter = new FileWriter("/Users/antonyzappacosta/Desktop/filesForGenetic/evolution.txt");
            for(double peso : genes){
                myWriter.write(peso + ";");
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        try { //write (IN APPEND) to History.txt for unique generations
            myWriter = new FileWriter("/Users/antonyzappacosta/Desktop/filesForGenetic/History.txt", true);
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

    public void retrySecondStrategy(){

    }

    public boolean isUnique(double[] genes){
        //System.out.println("ENTRO isUnique");
        int weightIndex = 0;
        double currentReadWeight;
        String data = "";
        try {
            File myObj = new File("/Users/antonyzappacosta/Desktop/filesForGenetic/History.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                weightIndex = 0;
                data = myReader.nextLine();
                st = new StringTokenizer(data, ";");
                while (st.hasMoreTokens()){
                    currentReadWeight = Double.parseDouble(st.nextToken().trim());
                    if(currentReadWeight != genes[weightIndex]){
                        return true;
                    }
                    weightIndex++;
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return false;
    }

}