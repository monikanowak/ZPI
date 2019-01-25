package main.java.zpi;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FlowsDataSet {

    public final float czas;
    private final float liczba_pakietow;
    private final float liczba_bajtow;
    public final float klasyfikator;

    private final List<String[]> x = new ArrayList<>();
    private final List<String[]> y = new ArrayList<>();

    public FlowsDataSet() throws IOException {
        try(InputStream is = new FileInputStream("C:\\Users\\Agata\\Downloads\\Projekt\\src\\main\\java\\zpi\\flows_prep_tagged_cud.csv")) {
            try(BufferedReader br = new BufferedReader(new InputStreamReader(is, "ASCII"))) {
                String[]  meta_columns = br.readLine().split(",");
                czas = Float.parseFloat(meta_columns[0]);
                liczba_pakietow = Float.parseFloat(meta_columns[1]);
                liczba_bajtow = Float.parseFloat(meta_columns[2]);
                klasyfikator = Float.parseFloat(meta_columns[3]);
                br.readLine();

                for(String line; (line = br.readLine()) != null; ) {
                    String[] columns = line.split(",");
                    x.add(new String[] { columns[0], columns[1], columns[2] });
                    y.add(new String[] { columns[3] });
                }
            }
        }
    }
    public int size() {
        return y.size();
    }

    public String[] flatX() {
        return flatten(x);
    }

    public String[] flatY() {
        return flatten(y);
    }

    private static String[] flatten(List<String[]> input) {

        // sum of all input array sizes
        int size = 0;
        for (String[] datapoint : input)
            size += datapoint.length;

        // copy data into a new single 1d array
        int i = 0;
        String[] result = new String[size];
        for (String[] datapoint : input)
            for (String value : datapoint)
                result[i++] = value;

        return result;
    }

}
