package main.java.zpi;

public class Main {

    public static void main(String[] args) {
        NeuronLayer layer1 = new NeuronLayer(1, 3);

        NeuralNetSimple net = new NeuralNetSimple(layer1);

       // przykładowy zbiór danych trenujących
        double[][] inputs = new double[][]{
                {0, 0, 1},
                {1, 1, 1},
                {1, 0, 1},
                {0, 1, 1},
                {1, 0, 0}
        };

        double[][] outputs = new double[][]{
                {0},
                {1},
                {1},
                {0},
                {1}
        };

        System.out.println("Trenowanie danych");
        net.train(inputs, outputs, 10000);

        System.out.println("Wagi dla warstwy 1");
        System.out.println(layer1);

        // oszacuj wynik dla nowych danych
        predict(new double[][]{{1, 0, 0}}, net);
        predict(new double[][]{{0, 1, 0}}, net);
        predict(new double[][]{{1, 1, 0}}, net);
        predict(new double[][]{{1, 1, 1}}, net);
        predict(new double[][]{{1, 0, 0}}, net);
    }

    public static void predict(double[][] testInput, NeuralNetSimple net) {
        net.goThroughNet(testInput);

        System.out.println("Dla danych "
                + testInput[0][0] + " "
                + testInput[0][1] + " "
                + testInput[0][2] + " -> "
                + net.getOutput()[0][0] + ", przewidziano -> " + testInput[0][0]);
    }
}

