import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


public class Reiziger extends Main{
    private int id;
    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private java.sql.Date geboortedatum;
    private Adres adres;
    private List<OVChipkaart> ovChipkaarten = new ArrayList<>();

    public Reiziger(int id, String vl, String tv, String an, Date gb){
        this.id = id;
        this.voorletters = vl;
        this.tussenvoegsel = tv;
        this.achternaam = an;
        this.geboortedatum = gb;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVoorletters() {
        return voorletters;
    }

    public void setVoorletters(String voorletters) {
        this.voorletters = voorletters;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel = tussenvoegsel;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public Date getGeboortedatum() {
        return geboortedatum;
    }

    public void setGeboortedatum(Date geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    public Adres getAdres() {
        return adres;
    }

    public void setAdres(Adres adres) {
        this.adres = adres;
    }

    public List<OVChipkaart> getOvChipkaart() {
        return ovChipkaarten;
    }

    public void setOvChipkaart(List<OVChipkaart> ovChipkaart) {
        this.ovChipkaarten = ovChipkaart;
    }


    @Override
    public String toString() {
        return "Reiziger_id = " + id + '\'' +
                ", voorletters = '" + voorletters + '\'' +
                ", tussenvoegsel = '" + tussenvoegsel + '\'' +
                ", achternaam = '" + achternaam + '\'' +
                ", geboortedatum = " + geboortedatum + '\n' +
                ", adres = " + adres + '\n';
    }
}