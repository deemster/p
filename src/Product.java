import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "product")
public class Product {
    @Id
    private int id;
    @Column
    private String naam;
    private String beschrijving;
    private double prijs;



    public Product(int id, String naam, String beschrijving, double prijs) {
        this.id = id;
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.prijs = prijs;
    }

    public Product() {

    }
    @ManyToMany(mappedBy = "producten", targetEntity = OVChipkaart.class)
    private List<OVChipkaart> ovChipkaarten;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public double getPrijs() {
        return prijs;
    }

    public void setPrijs(double prijs) {
        this.prijs = prijs;
    }

    public List<OVChipkaart> getOvChipkaarten() {
        return ovChipkaarten;
    }

    public void setOvChipkaarten(List<OVChipkaart> ovChipkaarten) {
        this.ovChipkaarten = ovChipkaarten;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", naam='" + naam + '\'' +
                ", beschrijving='" + beschrijving + '\'' +
                ", prijs=" + prijs +
                '}';
    }
}