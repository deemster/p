import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "ov_chipkaart")
public class OVChipkaart {
    @Id
    @Column(name = "kaart_nummer")
    private int kaartNummer;
    @Column(name = "geldig_tot")
    private java.sql.Date geldigTot;
    private int klasse;
    private int saldo;
    private int reizigerId;
    @ManyToOne
    @JoinColumn(name = "reiziger_id", insertable = false, updatable = false)
    private Reiziger reiziger;

    @ManyToMany
    @JoinTable(name = "ov_chipkaart_product",
    joinColumns = {@JoinColumn(name = "kaart_nummer")},
    inverseJoinColumns = {@JoinColumn(name = "product_nummer")})
    private List<Product> producten = new ArrayList<>();


    public OVChipkaart(int kn, java.sql.Date gt, int ks, int sa, int reizigerId){
        this.kaartNummer = kn;
        this.geldigTot = gt;
        this.klasse = ks;
        this.saldo = sa;
        this.reizigerId = reizigerId;
    }

    public OVChipkaart() {

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


    public Reiziger getReiziger() {
        return reiziger;
    }

    public void setReiziger(Reiziger reiziger) {
        this.reiziger = reiziger;
    }

    public List<Product> getProducten() {
        return producten;
    }

    public void setProducten(List<Product> producten) {
        this.producten = producten;
    }

    @Override
    public String toString() {
        return "main.java.OVChipkaart{" +
                "kaartNummer=" + kaartNummer +
                ", geldigTot=" + geldigTot +
                ", klasse=" + klasse +
                ", saldo=" + saldo +
                "main.java.Product(en)=" + producten +
                '}';
    }
}