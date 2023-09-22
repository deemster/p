import java.sql.*;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SQLException{
        String dbUrl = "jdbc:postgresql://localhost:5432/ovchip";
        String user = "postgres";
        String pass = "halloo";

        Connection conn = DriverManager.getConnection(dbUrl, user, pass);

        ReizigerDAOsql daOsql = new ReizigerDAOsql(conn);
        testReizigerDAO(daOsql);
        AdresDAOsql adaOsql = new AdresDAOsql(conn);
        testadresDAO(adaOsql, daOsql);
        OVChipkaartDAOsql odaOsql = new OVChipkaartDAOsql(conn);
        testOVChipDAO(odaOsql, daOsql);

//        tes
        try {
            conn.close();
            if (conn.isClosed()) {
                System.out.println("" +
                        "de connectie is gestopt");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private static void testReizigerDAO(ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test ReizigerDAO -------------");

        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        List<Reiziger> datums = rdao.findByGbDatum("2002-10-22");
        System.out.println("[Test] ReizigerDAO.findByGbDatum() geeft de volgende reizigers:");
        for (Reiziger d : datums){
            System.out.println(d);
        }
        System.out.println();


        Reiziger reiziger = rdao.findById(1);
        System.out.println("[Test] ReizigerDAO.findByGbDatum() geeft de volgende persoon:");
        System.out.println(reiziger);
        System.out.println();

        String gbdatum = "1981-03-14";

        Reiziger sietske =  new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum), 94568);
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");


        // hieronder maak ik een nieuw persoon aan zodat ik mijn delete kan testen
        String gebdatum = "2000-04-14";
        Reiziger jan = new Reiziger(7, "j", "van de", "berg", java.sql.Date.valueOf(gebdatum), 88573);
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(jan);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        System.out.println("[Test] " + jan.getVoorletters() + " " + jan.getTussenvoegsel() + " " + jan.getAchternaam() + " is verwijderd  ");

        rdao.delete(jan);

        // hieronder maak ik een nieuw persoon aan. vervolgens update ik dees via de update methode in de ReizigerDAOsql classen

        String geboortedatum = "2000-04-12";
        Reiziger kees = new Reiziger(8, "k", "", "mees", java.sql.Date.valueOf(geboortedatum),46392 );
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(kees);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        int id = 8;
        Reiziger keesie = new Reiziger(id, "k", "van", "huizen", java.sql.Date.valueOf(geboortedatum), 18326);
        rdao.update(keesie);
        System.out.println("[Test] " + kees.getVoorletters() + " " + kees.getTussenvoegsel() + " " + kees.getAchternaam() + " is geupdate naar: " +
                "" + keesie.getVoorletters() + " " + keesie.getTussenvoegsel() + " " + keesie.getAchternaam());

    }
    private static void testadresDAO(AdresDAO adao, ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test AdresDAO -------------");

        List<Adres> adres = adao.findAll();
        System.out.println("[Test] AdresDAO.findAll() geeft de volgende adressen:");
        for (Adres a : adres) {
            System.out.println(a);
        }
        System.out.println();
        System.out.println("[Test] AdresDAO.findByReiziger() geeft de volgende persoon:");
        String geboorte = "2000-10-18";
        int reizigerID = 123;
        Reiziger frank = new Reiziger(reizigerID, "f", "op", "steeg", java.sql.Date.valueOf(geboorte), 67568);
        rdao.save(frank);
        int adresID = 678;
        Adres zoutstraat = new Adres(adresID, "2435HJ", "10", "zoutstraat", "bergen op zoom", frank.getId());
        adao.save(zoutstraat);
        System.out.println(adao.findByReiziger(frank));

        Adres bergstraat = new Adres(6, "1563GG", "67", "bergstraat", "Zaandam", 8);
        System.out.print("[Test] Eerst " + adres.size() + " adressen, na AdresDAO.save() ");
        adao.save(bergstraat);
        adres = adao.findAll();
        System.out.println(adres.size() + " adressen\n");

        // hieronder maak ik een nieuw adres aan zodat ik mijn delete kan testen

        Adres volkweg = new Adres(7, "1367KL", "13", "volkweg", "alkmaar", 77);
        System.out.print("[Test] Eerst " + adres.size() + " adressen, na AdresDAO.save() ");
        adao.save(volkweg);
        adres = adao.findAll();
        System.out.println(adres.size() + " adressen\n");

        System.out.println("[Test] " + volkweg.getPostcode() + " " + volkweg.getHuisnummer() + " "  + volkweg.getStraat() + " is verwijderd  ");

        adao.delete(volkweg);


        Adres krantsteeg = new Adres(8, "3214QG", "23", "krantsteeg", "haarlem", 5);
        System.out.print("[Test] Eerst " + adres.size() + " adressen, na AdresDAO.save() ");
        adao.save(krantsteeg);
        adres = adao.findAll();
        System.out.println(adres.size() + " adressen\n");

        int id = 8;
        Adres krants = new Adres(id, "3215QG", "24", "krantsteeg", "haarlem", 4);
        adao.update(krants);
        System.out.println("[Test] " + krantsteeg.getPostcode() + " " + krantsteeg.getHuisnummer() + " " + krantsteeg.getStraat()  + " " + krantsteeg.getReizigerId() + " is geupdate naar " +
                " " + krants.getPostcode() + " " + krants.getHuisnummer() + " " + krants.getStraat() +  " " + krants.getReizigerId());
    }

    private static void testOVChipDAO(OVChipkaartDAO odao, ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test testOVChipDAO -------------");

        List<OVChipkaart> ovchipkaart = odao.findAll();
        System.out.println("[Test] OVChipkaartDAO.findAll() geeft de volgende chipkaart:");
        for (OVChipkaart o : ovchipkaart) {
            System.out.println(o);
        }
        System.out.println();
        System.out.println("[Test] OVChipkaartDAO.findByReiziger() geeft de volgende chipkaart:");
        String geboorte = "2000-10-18";
        int reizigerID = 101;
        Reiziger kees = new Reiziger(reizigerID, "k", "van", "de steen", java.sql.Date.valueOf(geboorte), 57401);
        rdao.save(kees);
        int ovkaartnum = 34567;
        OVChipkaart ovkaart = new OVChipkaart(ovkaartnum, java.sql.Date.valueOf("2024-10-10"), 2, 20, kees.getId());
        odao.save(ovkaart);
        System.out.println(odao.findByReiziger(kees));


        OVChipkaart ovChipkaart = new OVChipkaart(67568, java.sql.Date.valueOf("2024-11-20"), 1, 0, 5);
        System.out.print("[Test] Eerst " + ovchipkaart.size() + " kaarten, na OVChipkaartDAO.save() ");
        odao.save(ovChipkaart);
        ovchipkaart = odao.findAll();
        System.out.println(ovchipkaart.size() + " kaarten\n");

        // hieronder maak ik een nieuw adres aan zodat ik mijn delete kan testen

        OVChipkaart kaart = new OVChipkaart(38290, java.sql.Date.valueOf("2025-07-14"), 1, 50, 77);
        System.out.print("[Test] Eerst " + ovchipkaart.size() + " kaarten, na OVChipkaartDAO.delete() ");
        odao.save(kaart);
        ovchipkaart = odao.findAll();
        System.out.println(ovchipkaart.size() + " adressen\n");

        System.out.println("[Test] " + kaart.getKaartNummer() + " " + kaart.getKlasse() + " "  + kaart.getSaldo() + " is verwijderd  ");

        odao.delete(kaart);


        OVChipkaart kaartje = new OVChipkaart(88573, java.sql.Date.valueOf("2030-12-12"), 2, 10, 8);
        System.out.print("[Test] Eerst " + ovchipkaart.size() + " kaarten, na OVChipkaartDAO.save() ");
        odao.save(kaartje);
        ovchipkaart = odao.findAll();
        System.out.println(ovchipkaart.size() + " kaarten\n");

        OVChipkaart kaarte = new OVChipkaart(88573, java.sql.Date.valueOf("2033-12-31"), 2, 50, 8);
        odao.update(kaarte);
        System.out.println("[Test] " + kaartje.getKaartNummer() + " " + kaartje.getGeldigTot() + " " + kaartje.getKlasse()  + " " + kaartje.getSaldo() + " is geupdate naar " +
                " " + kaarte.getKaartNummer() + " " + kaarte.getGeldigTot() + " " + kaarte.getKlasse()  + " " + kaarte.getSaldo());
    }

}
