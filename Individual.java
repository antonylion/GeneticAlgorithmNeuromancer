import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Random;


public abstract class Individual{
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
        String data = "";
        try (var br = new BufferedReader(new FileReader(historyFilePath + id))) {
            while ((data = br.readLine()) != null) {
                weightIndex = 0;
                var st = new StringTokenizer(data, ";");
                while (st.hasMoreTokens()){
                    currentReadWeight = Double.parseDouble(st.nextToken().trim());
                    if(currentReadWeight != genes[weightIndex]){
                        return true;
                    }
                    weightIndex++;
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            System.exit(1);
        }
        return false;
    }
    //Calculate fitness
    public void calcFitness() {
        String matchResult = "";
        String data = "";
        fitness = 0;

        try (var br = new BufferedReader(new FileReader(gameOutputFilePath + id))) {
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

    public abstract void retryFirtStrategy();

}
