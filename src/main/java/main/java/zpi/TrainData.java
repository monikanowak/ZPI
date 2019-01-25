package main.java.zpi;

import java.io.*;
import java.nio.FloatBuffer;
import java.util.List;

import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.tensorflow.Add;
import org.bytedeco.javacpp.tensorflow.AddN;
import org.bytedeco.javacpp.tensorflow.ApplyGradientDescent;
import org.bytedeco.javacpp.tensorflow.Assign;
import org.bytedeco.javacpp.tensorflow.GraphDef;
import org.bytedeco.javacpp.tensorflow.Input;
import org.bytedeco.javacpp.tensorflow.InputList;
import org.bytedeco.javacpp.tensorflow.L2Loss;
import org.bytedeco.javacpp.tensorflow.MatMul;
import org.bytedeco.javacpp.tensorflow.Mean;
import org.bytedeco.javacpp.tensorflow.Multiply;
import org.bytedeco.javacpp.tensorflow.OutputVector;
import org.bytedeco.javacpp.tensorflow.Placeholder;
import org.bytedeco.javacpp.tensorflow.RandomNormal;
import org.bytedeco.javacpp.tensorflow.Scope;
import org.bytedeco.javacpp.tensorflow.Session;
import org.bytedeco.javacpp.tensorflow.SessionOptions;
import org.bytedeco.javacpp.tensorflow.Square;
import org.bytedeco.javacpp.tensorflow.Status;
import org.bytedeco.javacpp.tensorflow.StringTensorPairVector;
import org.bytedeco.javacpp.tensorflow.StringVector;
import org.bytedeco.javacpp.tensorflow.Subtract;
import org.bytedeco.javacpp.tensorflow.Tanh;
import org.bytedeco.javacpp.tensorflow.Tensor;
import org.bytedeco.javacpp.tensorflow.TensorShape;
import org.bytedeco.javacpp.tensorflow.TensorVector;
import org.bytedeco.javacpp.tensorflow.Variable;

import static org.bytedeco.javacpp.tensorflow.*;

public class TrainData {

    public void Train() throws Exception {

        // Load all javacpp-preset classes and native libraries
        Loader.load(org.bytedeco.javacpp.tensorflow.class);

        // Platform-specific initialization routine
        InitMain("trainer", (int[])null, null);

        // Read the data set
        FlowsDataSet flowsDataSet = new FlowsDataSet();

        // Copy the data into some tensors
        Tensor tensorX = Tensor.create(flowsDataSet.flatX(), new TensorShape(flowsDataSet.size(), 3));
        Tensor tensorY = Tensor.create(flowsDataSet.flatY(), new TensorShape(flowsDataSet.size(), 1));

        // Create a new empty graph
        Scope scope = Scope.NewRootScope();

        // Placeholder in the graph where tensors can be loaded into
        Placeholder x = new Placeholder(scope.WithOpName("x"), DT_FLOAT);
        Placeholder y = new Placeholder(scope.WithOpName("y"), DT_FLOAT);

        // Weights initialization
        Variable w1 = new Variable(scope.WithOpName("w1"), new TensorShape(3, 3).asPartialTensorShape(), DT_FLOAT);
        Input rws1 = new Input(Tensor.create(new int[] { 3, 3 }, new TensorShape(new long[] { 2 })));
        RandomNormal rw1 = new RandomNormal(scope, rws1, DT_FLOAT);
        Assign assign_w1 = new Assign(scope.WithOpName("assign_w1"), w1.asInput(), rw1.asInput());

        Variable w2 = new Variable(scope.WithOpName("w2"), new TensorShape(3, 2).asPartialTensorShape(), DT_FLOAT);
        Input rws2 = new Input(Tensor.create(new int[] { 3, 2 }, new TensorShape(new long[] { 2 })));
        RandomNormal rw2 = new RandomNormal(scope, rws2, DT_FLOAT);
        Assign assign_w2 = new Assign(scope.WithOpName("assign_w2"), w2.asInput(), rw2.asInput());

        Variable w3 = new Variable(scope.WithOpName("w3"), new TensorShape(2, 1).asPartialTensorShape(), DT_FLOAT);
        Input rws3 = new Input(Tensor.create(new int[] { 2, 1 }, new TensorShape(new long[] { 2 })));
        RandomNormal rw3 = new RandomNormal(scope, rws3, DT_FLOAT);
        Assign assign_w3 = new Assign(scope.WithOpName("assign_w3"), w3.asInput(), rw3.asInput());

        // Bias initialization
        Variable b1 = new Variable(scope.WithOpName("b1"), new TensorShape(1, 3).asPartialTensorShape(), DT_FLOAT);
        Input rbs1 = new Input(Tensor.create(new int[] { 1, 3 }, new TensorShape(new long[] { 2 })));
        RandomNormal rb1 = new RandomNormal(scope, rbs1, DT_FLOAT);
        Assign assign_b1 = new Assign(scope.WithOpName("assign_b1"), b1.asInput(), rb1.asInput());

        Variable b2 = new Variable(scope.WithOpName("b2"), new TensorShape(1, 2).asPartialTensorShape(), DT_FLOAT);
        Input rbs2 = new Input(Tensor.create(new int[] { 1, 2 }, new TensorShape(new long[] { 2 })));
        RandomNormal rb2 = new RandomNormal(scope, rbs2, DT_FLOAT);
        Assign assign_b2 = new Assign(scope.WithOpName("assign_b2"), b2.asInput(), rb2.asInput());

        Variable b3 = new Variable(scope.WithOpName("b3"), new TensorShape(1, 1).asPartialTensorShape(), DT_FLOAT);
        Input rbs3 = new Input(Tensor.create(new int[] { 1, 1 }, new TensorShape(new long[] { 2 })));
        RandomNormal rb3 = new RandomNormal(scope, rbs3, DT_FLOAT);
        Assign assign_b3 = new Assign(scope.WithOpName("assign_b3"), b3.asInput(), rb3.asInput());

        // Fully connecter layers with an Tanh activation function
        Tanh layer_1 = new Tanh(scope, new Tanh(scope, new Add(scope, new MatMul(scope, x.asInput(), w1.asInput()).asInput(), b1.asInput()).asInput()).asInput());
        Tanh layer_2 = new Tanh(scope, new Add(scope, new MatMul(scope, layer_1.asInput(), w2.asInput()).asInput(), b2.asInput()).asInput());
        Tanh layer_3 = new Tanh(scope, new Add(scope, new MatMul(scope, layer_2.asInput(), w3.asInput()).asInput(), b3.asInput()).asInput());

        // Weight regularization
        L2Loss l1 = new L2Loss(scope, w1.asInput());
        L2Loss l2 = new L2Loss(scope, w2.asInput());
        L2Loss l3 = new L2Loss(scope, w3.asInput());
        AddN regularization = new AddN(scope, new InputList(new OutputVector(l1.asOutput(), l2.asOutput(), l3.asOutput())));

        // Combined loss calculation (prediction loss and weight loss)
        Input axis = new Input(Tensor.create(new int[] { 0, 1 }, new TensorShape(new long[] { 2 })));
        Input scale = new Input(Const(scope, 0.01f));
        Add loss = new Add(scope.WithOpName("loss"),
                new Mean(scope, new Square(scope, new Subtract(scope, layer_3.asInput(), y.asInput()).asInput()).asInput(), axis).asInput(),
                new Multiply(scope, scale, regularization.asInput()).asInput());

        // Add the gradient operations to the graph
        OutputVector node_outputs = new OutputVector(loss.asOutput());
        OutputVector node_inputs = new OutputVector(w1.asOutput(), w2.asOutput(), w3.asOutput(), b1.asOutput(), b2.asOutput(), b3.asOutput());
        OutputVector node_grad_outputs = new OutputVector();
        checkStatus(AddSymbolicGradients(scope, node_outputs, node_inputs, node_grad_outputs));

        // Update the weight and bias values using gradient descent
        Input alpha = new Input(Const(scope.WithOpName("alpha"), 0.01f));
        ApplyGradientDescent apply_w1 = new ApplyGradientDescent(scope.WithOpName("apply_w1"), w1.asInput(), alpha, new Input(node_grad_outputs.get(0)));
        ApplyGradientDescent apply_w2 = new ApplyGradientDescent(scope.WithOpName("apply_w2"), w2.asInput(), alpha, new Input(node_grad_outputs.get(1)));
        ApplyGradientDescent apply_w3 = new ApplyGradientDescent(scope.WithOpName("apply_w3"), w3.asInput(), alpha, new Input(node_grad_outputs.get(2)));
        ApplyGradientDescent apply_b1 = new ApplyGradientDescent(scope.WithOpName("apply_b1"), b1.asInput(), alpha, new Input(node_grad_outputs.get(3)));
        ApplyGradientDescent apply_b2 = new ApplyGradientDescent(scope.WithOpName("apply_b2"), b2.asInput(), alpha, new Input(node_grad_outputs.get(4)));
        ApplyGradientDescent apply_b3 = new ApplyGradientDescent(scope.WithOpName("apply_b3"), b3.asInput(), alpha, new Input(node_grad_outputs.get(5)));

        // Build a graph definition object
        GraphDef def = new GraphDef();
        checkStatus(scope.ToGraphDef(def));

        // Creates a session.
        SessionOptions options = new SessionOptions();
        try(final Session session = new Session(options)) {

            // Create the graph to be used for the session.
            checkStatus(session.Create(def));

            // empty vectors
            StringTensorPairVector input_feed = new StringTensorPairVector();
            StringVector output_tensor_name = new StringVector();
            StringVector target_tensor_name = new StringVector();
            TensorVector outputs = new TensorVector();

            // different
            StringVector target_assign_tensor_name = new StringVector("assign_w1:0", "assign_w2:0", "assign_w3:0", "assign_b1:0", "assign_b2:0", "assign_b3:0");
            StringVector target_apply_tensor_name = new StringVector("apply_w1:0", "apply_w2:0", "apply_w3:0", "apply_b1:0", "apply_b2:0", "apply_b3:0");
            StringTensorPairVector input_xy_feed = new StringTensorPairVector(new String[] {"x", "y"}, new Tensor[] { tensorX, tensorY });
            StringVector output_loss_tensor_name = new StringVector("loss:0");

            // Generate random weights and bias values and assign them
            System.out.println("Setup");
            checkStatus(session.Run(input_feed, output_tensor_name, target_assign_tensor_name, outputs));

            // Input some training data into the graph
            for (int epoch = 0; epoch < 5000; epoch++) {
                CheckClassificator();
                if(flowsDataSet.czas!=flowsDataSet.klasyfikator)
                {
                    break;
                }
                // print loss every 100 epoch
                checkStatus(session.Run(input_xy_feed, output_loss_tensor_name, target_tensor_name, outputs));
                if(epoch % 100 == 0) {
                    FloatBuffer loss_flat = outputs.get(0).createBuffer();
                    System.out.printf("Iteration %5d with error: %.5f \n", epoch, loss_flat.get(0));
                }

                // train
                checkStatus(session.Run(input_xy_feed, output_tensor_name, target_apply_tensor_name, outputs));
            }
        }
        System.out.println("Finished");
    }


    public void CheckClassificator(){
        int lineNumber=0;
        String line;
        try(InputStream is = new FileInputStream("C:\\Users\\Agata\\Downloads\\Projekt\\src\\main\\java\\zpi\\flows_prep_tagged_cut.csv")) {
            try(BufferedReader br = new BufferedReader(new InputStreamReader(is, "ASCII"))) {
                while ((line = br.readLine()) != null) {
                    String[] meta_columns = line.split(",");
                    float klasyfikator = Float.parseFloat(meta_columns[3]);
                    if (CheckClassificator((klasyfikator))) {
                        System.out.println("linia - " + lineNumber + " - Przewidywany atak");
                    }
                    lineNumber++;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**

     * @param s
     * @throws Exception
     */
    static void checkStatus(Status s) throws Exception {
        if (!s.ok())
            throw new Exception(s.error_message().getString());
        s.close();
    }

    public Boolean CheckClassificator(Float classificatorValue)
    {
        if(classificatorValue==1)
            return true;
        return false;
    }
}
