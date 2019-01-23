package main.java.zpi;

import static main.java.zpi.MatrixUtil.apply;
import static main.java.zpi.NNMath.*;

public class NeuralNetSimple {

    private final NeuronLayer layer1;
    private double[][] outputLayer1;

    public NeuralNetSimple(NeuronLayer layer1) {
        this.layer1 = layer1;
    }

    /**
     * Forward propagation
     * <p>
     * Output of neuron = 1 / (1 + e^(-(sum(weight, input)))
     *
     * @param inputs
     */
    public void goThroughNet(double[][] inputs) {
        outputLayer1 = apply(matrixMultiply(inputs, layer1.weights), layer1.activationFunction);
    }

    public void train(double[][] inputs, double[][] outputs, int numberOfTrainingIterations) {
        for (int i = 0; i < numberOfTrainingIterations; ++i) {
            // prześlij dane przez siećŚ
            goThroughNet(inputs);

            // dopasuj wagi według error * input * output * (1 - output)

            double[][] errorLayer1 = matrixSubtract(outputs, outputLayer1);
            double[][] deltaLayer1 = scalarMultiply(errorLayer1, apply(outputLayer1, layer1.activationFunctionDerivative));

            // Calculate how much to adjust the weights by
            // Since we’re dealing with matrices, we handle the division by multiplying the delta output sum with the inputs' transpose!

            double[][] adjustmentLayer1 = matrixMultiply(matrixTranspose(inputs), deltaLayer1);

            this.layer1.adjustWeights(adjustmentLayer1);

            if (i % 1000 == 0) {
                System.out.println("Iteracja " + i + " z " + numberOfTrainingIterations);
            }
        }
    }

    public double[][] getOutput() {
        return outputLayer1;
    }

    @Override
    public String toString() {
        String result = "Layer 1\n";
        result += layer1.toString();

        if (outputLayer1 != null) {
            result += "Layer 1 output\n";
            result += MatrixUtil.matrixToString(outputLayer1);
        }
        return result;
    }
}
