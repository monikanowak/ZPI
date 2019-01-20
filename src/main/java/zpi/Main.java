package main.java.zpi;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

    File file = new File("C:\\Users\\Artur\\Desktop\\projektzpi\\ZPI\\src\\main\\java\\zpi\\emps.csv");
        System.out.println(file.exists());
        CSVReader reader =  new CSVReader(new FileReader(file), ',');//(new FileReader("../src/main/java/zpi/emps.csv"), ',');

        ColumnPositionMappingStrategy<DataFile> beanStrategy = new ColumnPositionMappingStrategy<DataFile>();
        beanStrategy.setType(DataFile.class);
        beanStrategy.setColumnMapping(new String[] {"czas","czas_trwania","komputer_zrodlowy","port_zrodlowy","komputer_docelowy","port_docelowy","protokol","liczba_pakietow","liczba_bajtow"});

        CsvToBean<DataFile> csvToBean = new CsvToBean<DataFile>();

        List<DataFile> dataFiles = csvToBean.parse(beanStrategy, reader);

        System.out.println("tu");
        System.out.println(dataFiles);





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

