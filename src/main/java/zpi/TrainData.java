package main.java.zpi;

import java.io.IOException;
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

         FlowsDataSet flowsDataSet = new FlowsDataSet();

        // Copy the data into some tensors
        Tensor tensorX = Tensor.create(flowsDataSet.flatX(), new TensorShape(flowsDataSet.size(), 9));
        Tensor tensorY = Tensor.create(flowsDataSet.flatY(), new TensorShape(flowsDataSet.size(), 1));

        // Create a new empty graph
        Scope scope = Scope.NewRootScope();

        // Placeholder in the graph where tensors can be loaded into
        Placeholder x = new Placeholder(scope.WithOpName("x"), DT_STRING);
        Placeholder y = new Placeholder(scope.WithOpName("y"), DT_STRING);

        Variable w1 = new Variable(scope.WithOpName("w1"), new TensorShape(9, 9).asPartialTensorShape(), DT_STRING);
        Input rws1 = new Input(Tensor.create(new int[] { 9, 9 }, new TensorShape(new long[] { 2 })));
        RandomNormal rw1 = new RandomNormal(scope, rws1, DT_STRING);
        Assign assign_w1 = new Assign(scope.WithOpName("assign_w1"), w1.asInput(), rw1.asInput());

        Variable w2 = new Variable(scope.WithOpName("w2"), new TensorShape(9, 8).asPartialTensorShape(), DT_STRING);
        Input rws2 = new Input(Tensor.create(new int[] { 9, 8 }, new TensorShape(new long[] { 2 })));
        RandomNormal rw2 = new RandomNormal(scope, rws2, DT_STRING);
        Assign assign_w2 = new Assign(scope.WithOpName("assign_w2"), w2.asInput(), rw2.asInput());

        Variable w3 = new Variable(scope.WithOpName("w3"), new TensorShape(8, 7).asPartialTensorShape(), DT_STRING);
        Input rws3 = new Input(Tensor.create(new int[] { 8, 7 }, new TensorShape(new long[] { 2 })));
        RandomNormal rw3 = new RandomNormal(scope, rws3, DT_STRING);
        Assign assign_w3 = new Assign(scope.WithOpName("assign_w1"), w3.asInput(), rw3.asInput());

        Variable w4 = new Variable(scope.WithOpName("w4"), new TensorShape(7, 6).asPartialTensorShape(), DT_STRING);
        Input rws4 = new Input(Tensor.create(new int[] { 7, 6 }, new TensorShape(new long[] { 2 })));
        RandomNormal rw4 = new RandomNormal(scope, rws4, DT_STRING);
        Assign assign_w4 = new Assign(scope.WithOpName("assign_w4"), w4.asInput(), rw4.asInput());

        Variable w5 = new Variable(scope.WithOpName("w5"), new TensorShape(6, 5).asPartialTensorShape(), DT_STRING);
        Input rws5 = new Input(Tensor.create(new int[] { 6, 5 }, new TensorShape(new long[] { 2 })));
        RandomNormal rw5 = new RandomNormal(scope, rws5, DT_STRING);
        Assign assign_w5 = new Assign(scope.WithOpName("assign_w5"), w5.asInput(), rw5.asInput());

        Variable w6 = new Variable(scope.WithOpName("w6"), new TensorShape(5, 4).asPartialTensorShape(), DT_STRING);
        Input rws6 = new Input(Tensor.create(new int[] { 5, 4}, new TensorShape(new long[] { 2 })));
        RandomNormal rw6 = new RandomNormal(scope, rws6, DT_STRING);
        Assign assign_w6 = new Assign(scope.WithOpName("assign_w6"), w6.asInput(), rw6.asInput());

        Variable w7 = new Variable(scope.WithOpName("w7"), new TensorShape(4, 3).asPartialTensorShape(), DT_STRING);
        Input rws7 = new Input(Tensor.create(new int[] { 4, 3 }, new TensorShape(new long[] { 2 })));
        RandomNormal rw7 = new RandomNormal(scope, rws7, DT_STRING);
        Assign assign_w7 = new Assign(scope.WithOpName("assign_w7"), w7.asInput(), rw7.asInput());

        Variable w8 = new Variable(scope.WithOpName("w8"), new TensorShape(3, 2).asPartialTensorShape(), DT_STRING);
        Input rws8 = new Input(Tensor.create(new int[] { 3, 2 }, new TensorShape(new long[] { 2 })));
        RandomNormal rw8 = new RandomNormal(scope, rws8, DT_STRING);
        Assign assign_w8 = new Assign(scope.WithOpName("assign_w8"), w8.asInput(), rw8.asInput());

        Variable w9 = new Variable(scope.WithOpName("w9"), new TensorShape(2, 1).asPartialTensorShape(), DT_STRING);
        Input rws9 = new Input(Tensor.create(new int[] { 2, 1 }, new TensorShape(new long[] { 2 })));
        RandomNormal rw9 = new RandomNormal(scope, rws9, DT_STRING);
        Assign assign_w9 = new Assign(scope.WithOpName("assign_w9"), w9.asInput(), rw9.asInput());

        Variable b1 = new Variable(scope.WithOpName("b1"), new TensorShape(1, 9).asPartialTensorShape(), DT_STRING);
        Input rbs1 = new Input(Tensor.create(new int[] { 1, 9 }, new TensorShape(new long[] { 2 })));
        RandomNormal rb1 = new RandomNormal(scope, rbs1, DT_STRING);
        Assign assign_b1 = new Assign(scope.WithOpName("assign_b1"), b1.asInput(), rb1.asInput());

        Variable b2 = new Variable(scope.WithOpName("b2"), new TensorShape(1, 8).asPartialTensorShape(), DT_STRING);
        Input rbs2 = new Input(Tensor.create(new int[] { 1, 8 }, new TensorShape(new long[] { 2 })));
        RandomNormal rb2 = new RandomNormal(scope, rbs2, DT_STRING);
        Assign assign_b2 = new Assign(scope.WithOpName("assign_b2"), b2.asInput(), rb2.asInput());

        Variable b3 = new Variable(scope.WithOpName("b3"), new TensorShape(1, 7).asPartialTensorShape(), DT_STRING);
        Input rbs3 = new Input(Tensor.create(new int[] { 1, 7 }, new TensorShape(new long[] { 2 })));
        RandomNormal rb3 = new RandomNormal(scope, rbs3, DT_STRING);
        Assign assign_b3 = new Assign(scope.WithOpName("assign_b3"), b3.asInput(), rb3.asInput());

        Variable b4 = new Variable(scope.WithOpName("b4"), new TensorShape(1, 6).asPartialTensorShape(), DT_STRING);
        Input rbs4 = new Input(Tensor.create(new int[] { 1, 6 }, new TensorShape(new long[] { 2 })));
        RandomNormal rb4 = new RandomNormal(scope, rbs4, DT_STRING);
        Assign assign_b4 = new Assign(scope.WithOpName("assign_b4"), b4.asInput(), rb4.asInput());

        Variable b5 = new Variable(scope.WithOpName("b5"), new TensorShape(1, 5).asPartialTensorShape(), DT_STRING);
        Input rbs5 = new Input(Tensor.create(new int[] { 1, 5 }, new TensorShape(new long[] { 2 })));
        RandomNormal rb5 = new RandomNormal(scope, rbs5, DT_STRING);
        Assign assign_b5 = new Assign(scope.WithOpName("assign_b5"), b5.asInput(), rb5.asInput());

        Variable b6 = new Variable(scope.WithOpName("b6"), new TensorShape(1, 4).asPartialTensorShape(), DT_STRING);
        Input rbs6 = new Input(Tensor.create(new int[] { 1, 4 }, new TensorShape(new long[] { 2 })));
        RandomNormal rb6 = new RandomNormal(scope, rbs6, DT_STRING);
        Assign assign_b6 = new Assign(scope.WithOpName("assign_b6"), b6.asInput(), rb6.asInput());

        Variable b7 = new Variable(scope.WithOpName("b7"), new TensorShape(1, 3).asPartialTensorShape(), DT_STRING);
        Input rbs7 = new Input(Tensor.create(new int[] { 1, 3 }, new TensorShape(new long[] { 2 })));
        RandomNormal rb7 = new RandomNormal(scope, rbs7, DT_STRING);
        Assign assign_b7 = new Assign(scope.WithOpName("assign_b7"), b7.asInput(), rb7.asInput());

        Variable b8 = new Variable(scope.WithOpName("b8"), new TensorShape(1, 2).asPartialTensorShape(), DT_STRING);
        Input rbs8 = new Input(Tensor.create(new int[] { 1, 2 }, new TensorShape(new long[] { 2 })));
        RandomNormal rb8 = new RandomNormal(scope, rbs8, DT_STRING);
        Assign assign_b8 = new Assign(scope.WithOpName("assign_b8"), b8.asInput(), rb8.asInput());

        Variable b9 = new Variable(scope.WithOpName("b9"), new TensorShape(1, 1).asPartialTensorShape(), DT_STRING);
        Input rbs9 = new Input(Tensor.create(new int[] { 1, 1 }, new TensorShape(new long[] { 2 })));
        RandomNormal rb9 = new RandomNormal(scope, rbs9, DT_STRING);
        Assign assign_b9 = new Assign(scope.WithOpName("assign_b9"), b9.asInput(), rb9.asInput());


        Tanh layer_1 = new Tanh(scope, new Tanh(scope, new Add(scope, new MatMul(scope, x.asInput(), w1.asInput()).asInput(), b1.asInput()).asInput()).asInput());
        Tanh layer_2 = new Tanh(scope, new Add(scope, new MatMul(scope, layer_1.asInput(), w2.asInput()).asInput(), b2.asInput()).asInput());
        Tanh layer_3 = new Tanh(scope, new Add(scope, new MatMul(scope, layer_2.asInput(), w3.asInput()).asInput(), b3.asInput()).asInput());
        Tanh layer_4 = new Tanh(scope, new Add(scope, new MatMul(scope, layer_3.asInput(), w4.asInput()).asInput(), b4.asInput()).asInput());
        Tanh layer_5 = new Tanh(scope, new Add(scope, new MatMul(scope, layer_4.asInput(), w5.asInput()).asInput(), b5.asInput()).asInput());
        Tanh layer_6 = new Tanh(scope, new Add(scope, new MatMul(scope, layer_5.asInput(), w6.asInput()).asInput(), b6.asInput()).asInput());
        Tanh layer_7 = new Tanh(scope, new Add(scope, new MatMul(scope, layer_6.asInput(), w7.asInput()).asInput(), b7.asInput()).asInput());
        Tanh layer_8 = new Tanh(scope, new Add(scope, new MatMul(scope, layer_7.asInput(), w8.asInput()).asInput(), b8.asInput()).asInput());
        Tanh layer_9 = new Tanh(scope, new Add(scope, new MatMul(scope, layer_8.asInput(), w9.asInput()).asInput(), b9.asInput()).asInput());

        // Weight regularization
        L2Loss l1 = new L2Loss(scope, w1.asInput());
        L2Loss l2 = new L2Loss(scope, w2.asInput());
        L2Loss l3 = new L2Loss(scope, w3.asInput());
        L2Loss l4 = new L2Loss(scope, w4.asInput());
        L2Loss l5 = new L2Loss(scope, w5.asInput());
        L2Loss l6 = new L2Loss(scope, w6.asInput());
        L2Loss l7 = new L2Loss(scope, w7.asInput());
        L2Loss l8 = new L2Loss(scope, w8.asInput());
        L2Loss l9 = new L2Loss(scope, w9.asInput());
        AddN regularization = new AddN(scope, new InputList(new OutputVector(l1.asOutput(), l2.asOutput(), l3.asOutput(), l4.asOutput(), l5.asOutput(), l6.asOutput(), l7.asOutput(), l8.asOutput(), l9.asOutput())));

        // Combined loss calculation (prediction loss and weight loss)
        Input axis = new Input(Tensor.create(new int[] { 0, 1 }, new TensorShape(new long[] { 2 })));
        Input scale = new Input(Const(scope, 0.01f));
        Add loss = new Add(scope.WithOpName("loss"),
                new Mean(scope, new Square(scope, new Subtract(scope, layer_8.asInput(), y.asInput()).asInput()).asInput(), axis).asInput(),
                new Multiply(scope, scale, regularization.asInput()).asInput());

        OutputVector node_outputs = new OutputVector(loss.asOutput());
        OutputVector node_inputs = new OutputVector(w1.asOutput(), w2.asOutput(), w3.asOutput(),w4.asOutput(), w5.asOutput(), w6.asOutput(),w7.asOutput(), w8.asOutput(), w9.asOutput(), b1.asOutput(), b2.asOutput(), b3.asOutput(),b4.asOutput(), b5.asOutput(), b6.asOutput(),b7.asOutput(),b8.asOutput(),b9.asOutput());
        OutputVector node_grad_outputs = new OutputVector();
        checkStatus(AddSymbolicGradients(scope, node_outputs, node_inputs, node_grad_outputs));

        Input alpha = new Input(Const(scope.WithOpName("alpha"), 0.01f));
        ApplyGradientDescent apply_w1 = new ApplyGradientDescent(scope.WithOpName("apply_w1"), w1.asInput(), alpha, new Input(node_grad_outputs.get(0)));
        ApplyGradientDescent apply_w2 = new ApplyGradientDescent(scope.WithOpName("apply_w2"), w2.asInput(), alpha, new Input(node_grad_outputs.get(1)));
        ApplyGradientDescent apply_w3 = new ApplyGradientDescent(scope.WithOpName("apply_w3"), w3.asInput(), alpha, new Input(node_grad_outputs.get(2)));
        ApplyGradientDescent apply_w4 = new ApplyGradientDescent(scope.WithOpName("apply_w4"), w4.asInput(), alpha, new Input(node_grad_outputs.get(3)));
        ApplyGradientDescent apply_w5 = new ApplyGradientDescent(scope.WithOpName("apply_w5"), w5.asInput(), alpha, new Input(node_grad_outputs.get(4)));
        ApplyGradientDescent apply_w6 = new ApplyGradientDescent(scope.WithOpName("apply_w6"), w6.asInput(), alpha, new Input(node_grad_outputs.get(5)));
        ApplyGradientDescent apply_w7 = new ApplyGradientDescent(scope.WithOpName("apply_w7"), w7.asInput(), alpha, new Input(node_grad_outputs.get(6)));
        ApplyGradientDescent apply_w8 = new ApplyGradientDescent(scope.WithOpName("apply_w8"), w8.asInput(), alpha, new Input(node_grad_outputs.get(7)));
        ApplyGradientDescent apply_w9 = new ApplyGradientDescent(scope.WithOpName("apply_w9"), w9.asInput(), alpha, new Input(node_grad_outputs.get(8)));
        ApplyGradientDescent apply_b1 = new ApplyGradientDescent(scope.WithOpName("apply_b1"), b1.asInput(), alpha, new Input(node_grad_outputs.get(9)));
        ApplyGradientDescent apply_b2 = new ApplyGradientDescent(scope.WithOpName("apply_b2"), b2.asInput(), alpha, new Input(node_grad_outputs.get(10)));
        ApplyGradientDescent apply_b3 = new ApplyGradientDescent(scope.WithOpName("apply_b3"), b3.asInput(), alpha, new Input(node_grad_outputs.get(11)));
        ApplyGradientDescent apply_b4 = new ApplyGradientDescent(scope.WithOpName("apply_b4"), b4.asInput(), alpha, new Input(node_grad_outputs.get(12)));
        ApplyGradientDescent apply_b5 = new ApplyGradientDescent(scope.WithOpName("apply_b5"), b5.asInput(), alpha, new Input(node_grad_outputs.get(13)));
        ApplyGradientDescent apply_b6 = new ApplyGradientDescent(scope.WithOpName("apply_b6"), b6.asInput(), alpha, new Input(node_grad_outputs.get(14)));
        ApplyGradientDescent apply_b7 = new ApplyGradientDescent(scope.WithOpName("apply_b7"), b7.asInput(), alpha, new Input(node_grad_outputs.get(15)));
        ApplyGradientDescent apply_b8 = new ApplyGradientDescent(scope.WithOpName("apply_b8"), b8.asInput(), alpha, new Input(node_grad_outputs.get(16)));
        ApplyGradientDescent apply_b9 = new ApplyGradientDescent(scope.WithOpName("apply_b9"), b9.asInput(), alpha, new Input(node_grad_outputs.get(17)));


        GraphDef def = new GraphDef();
        checkStatus(scope.ToGraphDef(def));

        SessionOptions options = new SessionOptions();
        try(final Session session = new Session(options)) {

            checkStatus(session.Create(def));

            // empty vectors
            StringTensorPairVector input_feed = new StringTensorPairVector();
            StringVector output_tensor_name = new StringVector();
            StringVector target_tensor_name = new StringVector();
            TensorVector outputs = new TensorVector();

            StringVector target_assign_tensor_name = new StringVector("assign_w1:0", "assign_w2:0", "assign_w3:0", "assign_w4:0", "assign_w5:0", "assign_w6:0","assign_w7:0", "assign_w8:0", "assign_w9:0",  "assign_b1:0", "assign_b2:0", "assign_b3:0","assign_b4:0", "assign_b5:0", "assign_b6:0","assign_b7:0", "assign_b8:0", "assign_b9:0");
            StringVector target_apply_tensor_name = new StringVector("apply_w1:0", "apply_w2:0", "apply_w3:0", "apply_w4:0", "apply_w5:0", "apply_w6:0", "apply_w7:0", "apply_w8:0", "apply_w9:0", "apply_b1:0", "apply_b2:0", "apply_b3:0","apply_b4:0", "apply_b5:0", "apply_b6:0","apply_b7:0", "apply_b8:0", "apply_b9:0");
            StringTensorPairVector input_xy_feed = new StringTensorPairVector(new String[] {"x", "y"}, new Tensor[] { tensorX, tensorY });
            StringVector output_loss_tensor_name = new StringVector("loss:0");

            // Generate random weights and bias values and assign them
            System.out.println("Setup");
            checkStatus(session.Run(input_feed, output_tensor_name, target_assign_tensor_name, outputs));

            // Input some training data into the graph
            for (int epoch = 0; epoch < 5000; epoch++) {

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

    /**

     * @param s
     * @throws Exception
     */
    static void checkStatus(Status s) throws Exception {
        if (!s.ok())
            throw new Exception(s.error_message().getString());
        s.close();
    }


}
