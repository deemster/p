import javax.persistence.*;


@Entity
@Table(name = "adres")
public class Adres{
    @Id
    private int id;
    @Column
    private String postcode;
    private String huisnummer;
    private String straat;
    private String woonplaats;
    private int reizigerId;

    public Adres(int id, String pc, String hn, String st, String wp, int rid){
        this.id = id;
        this.postcode = pc;
        this.huisnummer = hn;
        this.straat = st;
        this.woonplaats = wp;
        this.reizigerId = rid;
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
        return reizigerId;
    }

    public void setReizigerId(int reizigerId) {
        this.reizigerId = reizigerId;
    }

    @Override
    public String toString() {
        return "Adres{" +
                "id=" + id +
                ", postcode='" + postcode + '\'' +
                ", huisnummer='" + huisnummer + '\'' +
                ", straat='" + straat + '\'' +
                ", woonplaats='" + woonplaats + '\'' +
                "reiziger id='" + reizigerId + '\'' +
                '}';
    }
}