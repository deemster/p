import java.sql.Date;

public class OVChipkaart {
    private int kaartNummer;
    private java.sql.Date geldigTot;
    private int klasse;
    private int saldo;
    private int reizigerId;

    public OVChipkaart(int kn, java.sql.Date gt, int ks, int sa, int rid){
        this.kaartNummer = kn;
        this.geldigTot = gt;
        this.klasse = ks;
        this.saldo = sa;
        this.reizigerId = rid;
    }

    public int getKaartNummer() {
        return kaartNummer;
    }

    public void setKaartNummer(int kaartNummer) {
        this.kaartNummer = kaartNummer;
    }

    public Date getGeldigTot() {
        return geldigTot;
    }

    public void setGeldigTot(Date geldigTot) {
        this.geldigTot = geldigTot;
    }

    public int getKlasse() {
        return klasse;
    }

    public void setKlasse(int klasse) {
        this.klasse = klasse;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public int getReizigerId() {
        return reizigerId;
    }

    public void setReizigerId(int reizigerId) {
        this.reizigerId = reizigerId;
    }

    @Override
    public String toString() {
        return "OVChipkaart{" +
                "kaartNummer=" + kaartNummer +
                ", geldigTot=" + geldigTot +
                ", klasse=" + klasse +
                ", saldo=" + saldo +
                ", reizigerId=" + reizigerId +
                '}';
    }
}
