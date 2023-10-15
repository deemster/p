import javax.persistence.*;


@Entity
@Table(name = "adres")
public class Adres{
    @Id
    @Column(name = "adres_id")
    private int id;
    private String postcode;
    private String huisnummer;
    private String straat;
    private String woonplaats;
    private int reiziger_Id;
    @OneToOne
    @JoinColumn(name = "adres_id")
    private Reiziger reiziger;

    public Adres(int id, String pc, String hn, String st, String wp, int reizigerid){
        this.id = id;
        this.postcode = pc;
        this.huisnummer = hn;
        this.straat = st;
        this.woonplaats = wp;
        this.reiziger_Id = reizigerid;

    }

    public Adres() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getHuisnummer() {
        return huisnummer;
    }

    public void setHuisnummer(String huisnummer) {
        this.huisnummer = huisnummer;
    }

    public String getStraat() {
        return straat;
    }

    public void setStraat(String straat) {
        this.straat = straat;
    }

    public String getWoonplaats() {
        return woonplaats;
    }

    public void setWoonplaats(String woonplaats) {
        this.woonplaats = woonplaats;
    }

    public int getReizigerId() {
        return reiziger_Id;
    }

    public void setReizigerId(int reizigerId) {
        this.reiziger_Id = reizigerId;
    }

    public Reiziger getReiziger() {
        return reiziger;
    }

    public void setReiziger(Reiziger reiziger) {
        this.reiziger = reiziger;
    }

    @Override
    public String toString() {
        return "main.java.Adres{" +
                "id=" + id +
                ", postcode='" + postcode + '\'' +
                ", huisnummer='" + huisnummer + '\'' +
                ", straat='" + straat + '\'' +
                ", woonplaats='" + woonplaats + '\'' +
                '}' +'\n' ;
    }
}