package main.java.zpi;

import java.util.Objects;

public class DataFile {

    private String czas;
    private String czas_trwania;
    private String komputer_zrodlowy;
    private String port_zrodlowy;
    private String komputer_docelowy;
    private String port_docelowy;
    private String protokol;
    private String liczba_pakietow;
    private String liczba_bajtow;

    public String getCzas() {
        return czas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataFile dataFile = (DataFile) o;
        return Objects.equals(czas, dataFile.czas) &&
                Objects.equals(czas_trwania, dataFile.czas_trwania) &&
                Objects.equals(komputer_zrodlowy, dataFile.komputer_zrodlowy) &&
                Objects.equals(port_zrodlowy, dataFile.port_zrodlowy) &&
                Objects.equals(komputer_docelowy, dataFile.komputer_docelowy) &&
                Objects.equals(port_docelowy, dataFile.port_docelowy) &&
                Objects.equals(protokol, dataFile.protokol) &&
                Objects.equals(liczba_pakietow, dataFile.liczba_pakietow) &&
                Objects.equals(liczba_bajtow, dataFile.liczba_bajtow);
    }

    @Override
    public int hashCode() {
        return Objects.hash(czas, czas_trwania, komputer_zrodlowy, port_zrodlowy, komputer_docelowy, port_docelowy, protokol, liczba_pakietow, liczba_bajtow);
    }

    @Override
    public String toString() {
        return "DataFile{" +
                "czas='" + czas + '\'' +
                ", czas_trwania='" + czas_trwania + '\'' +
                ", komputer_zrodlowy='" + komputer_zrodlowy + '\'' +
                ", port_zrodlowy='" + port_zrodlowy + '\'' +
                ", komputer_docelowy='" + komputer_docelowy + '\'' +
                ", port_docelowy='" + port_docelowy + '\'' +
                ", protokol='" + protokol + '\'' +
                ", liczba_pakietow='" + liczba_pakietow + '\'' +
                ", liczba_bajtow='" + liczba_bajtow + '\'' +
                '}';
    }

    public void setCzas(String czas) {
        this.czas = czas;
    }

    public String getCzas_trwania() {
        return czas_trwania;
    }

    public void setCzas_trwania(String czas_trwania) {
        this.czas_trwania = czas_trwania;
    }

    public String getKomputer_zrodlowy() {
        return komputer_zrodlowy;
    }

    public void setKomputer_zrodlowy(String komputer_zrodlowy) {
        this.komputer_zrodlowy = komputer_zrodlowy;
    }

    public String getPort_zrodlowy() {
        return port_zrodlowy;
    }

    public void setPort_zrodlowy(String port_zrodlowy) {
        this.port_zrodlowy = port_zrodlowy;
    }

    public String getKomputer_docelowy() {
        return komputer_docelowy;
    }

    public void setKomputer_docelowy(String komputer_docelowy) {
        this.komputer_docelowy = komputer_docelowy;
    }

    public String getPort_docelowy() {
        return port_docelowy;
    }

    public void setPort_docelowy(String port_docelowy) {
        this.port_docelowy = port_docelowy;
    }

    public String getProtokol() {
        return protokol;
    }

    public void setProtokol(String protokol) {
        this.protokol = protokol;
    }

    public String getLiczba_pakietow() {
        return liczba_pakietow;
    }

    public void setLiczba_pakietow(String liczba_pakietow) {
        this.liczba_pakietow = liczba_pakietow;
    }

    public String getLiczba_bajtow() {
        return liczba_bajtow;
    }

    public void setLiczba_bajtow(String liczba_bajtow) {
        this.liczba_bajtow = liczba_bajtow;
    }
}