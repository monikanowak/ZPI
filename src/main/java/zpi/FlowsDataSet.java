package main.java.zpi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FlowsDataSet {

    private final String czas;
    private final String czas_trwania;
    private final String komputer_zrodlowy;
    private final String port_zrodlowy;
    private final String komputer_docelowy;
    private final String port_docelowy;
    private final String protokol;
    private final String liczba_pakietow;
    private final String liczba_bajtow;

    private final List<String[]> x = new ArrayList<>();
    private final List<String[]> y = new ArrayList<>();

    public FlowsDataSet() throws IOException {
        try(InputStream is = FlowsDataSet.class.getResourceAsStream("flows_prep_tagged.csv")) {
            try(BufferedReader br = new BufferedReader(new InputStreamReader(is, "ASCII"))) {
                String[]  meta_columns = br.readLine().split(",");
                czas = meta_columns[0];
                czas_trwania=meta_columns[1];
                komputer_zrodlowy=meta_columns[2];
                port_zrodlowy=meta_columns[3];
                komputer_docelowy=meta_columns[4];
                port_docelowy=meta_columns[5];
                protokol=meta_columns[6];
                liczba_pakietow=meta_columns[7];
                liczba_bajtow=meta_columns[8];
                br.readLine();

                for(String line; (line = br.readLine()) != null; ) {
                    String[] columns = line.split(",");
                    x.add(new String[] { columns[0], columns[1], columns[2], columns[3], columns[4], columns[5], columns[6], columns[7], columns[8] });
                    y.add(new String[] { columns[9] });
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
