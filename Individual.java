import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Random;
import java.util.Arrays;


public abstract class Individual{

    static int maxFitness = 0;
    int fitness = 0;
    double[] genes = new double[8];

    protected Random rn;
    protected static int numeroMosseIniziali = 10000;
    protected int numeroMosseAttuali = 10000;
    protected static boolean firstComputation = true;
    protected final String historyFilePath = "/tmp/NeuroGeneticHistory.txt";
    protected final String gameOutputFilePath = "/tmp/NeuroClientOutput.txt";
    protected String outputFilePath;
    protected int id;

    public Individual(String outputFilePath, int id){
        this.outputFilePath = outputFilePath;
        this.id = id;
    }
    public boolean isUnique(double[] genes){
        //System.out.println("ENTRO isUnique");
        int weightIndex = 0;
        double currentReadWeight;
        double[] currentRowWeights = new double[8];
        String data = "";
        try (var br = new BufferedReader(new FileReader(historyFilePath + id))) {
            while ((data = br.readLine()) != null) {
                weightIndex = 0;
                var st = new StringTokenizer(data, ";");
                //insert the weights found in the line into the double[] array
                while (st.hasMoreTokens()){
                    currentReadWeight = Double.parseDouble(st.nextToken().trim());
                    currentRowWeights[weightIndex] = currentReadWeight;
                    weightIndex++;
                }
                //if the double array built from the file row is equal to the current weights double array
                //return false
                if(Arrays.equals(genes, currentRowWeights)){
                    return false;
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            System.exit(1);
        }
        //if genes array is never equal to one of the array wrote in the history file, return true (is unique)
        return true;
    }
    //Calculate fitness
    public void calcFitness() {
        String matchResult = "";
        String data = "";
        fitness = 0;

        //try (var br = new BufferedReader(new FileReader(gameOutputFilePath + id))) {
        try (var br = new BufferedReader(new FileReader(gameOutputFilePath))) {
            while ((data = br.readLine()) != null) {
                var st = new StringTokenizer(data, ";");
                System.out.println("LETTA RIGA: " + data);
                //LEGGO SE HO VINTO O PERSO
                matchResult = st.nextToken().trim();
                //LEGGO IL NUMERO DI MOSSE DELLA PARTITA
                numeroMosseAttuali = Integer.parseInt(st.nextToken().trim());
                break;
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            System.exit(1);
        }

        System.out.println("RISULTATO PARTITA: " + matchResult);

        if(matchResult.equals("DRAW")){
            fitness = 2;
        }

        if(matchResult.equals("WIN")){
            fitness = 4;
        }

        if(firstComputation){
            numeroMosseIniziali = numeroMosseAttuali;
        }

        if(matchResult.equals("WIN") && !firstComputation && numeroMosseAttuali < numeroMosseIniziali){
            fitness = 5;
        }

        if(matchResult.equals("DRAW") && !firstComputation && numeroMosseAttuali > numeroMosseIniziali){
            fitness = 3;
        }

        if(matchResult.equals("LOSE") && !firstComputation && numeroMosseAttuali > numeroMosseIniziali){
            fitness = 1;
        }

        if (fitness > maxFitness){
            maxFitness = fitness;
        }

        firstComputation = false;
    }

    public abstract void retryFirtStrategy();

    public abstract void markImprovement(String improvementOutputFilePath, double[] genes);

    public abstract void setInitialGenes(double[] initialGenes);

    public static int getMaxFitness() {
        return maxFitness;
    }

    public static void decMaxFitness(){
        maxFitness--;
    }
}
