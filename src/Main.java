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
        testadresDAO(adaOsql);
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
        Reiziger sietske =  new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");


        // hieronder maak ik een nieuw persoon aan zodat ik mijn delete kan testen
        String gebdatum = "2000-04-14";
        Reiziger jan = new Reiziger(7, "j", "van de", "berg", java.sql.Date.valueOf(gebdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(jan);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        System.out.println("[Test] " + jan.getVoorletters() + " " + jan.getTussenvoegsel() + " " + jan.getAchternaam() + " is verwijderd  ");

        rdao.delete(jan);

        // hieronder maak ik een nieuw persoon aan. vervolgens update ik dees via de update methode in de ReizigerDAOsql classen

        String geboortedatum = "2000-04-12";
        Reiziger kees = new Reiziger(8, "k", "", "mees", java.sql.Date.valueOf(geboortedatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(kees);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        int id = 8;
        Reiziger keesie = new Reiziger(id, "k", "van", "huizen", java.sql.Date.valueOf(geboortedatum));
        rdao.update(keesie);
        System.out.println("[Test] " + kees.getVoorletters() + " " + kees.getTussenvoegsel() + " " + kees.getAchternaam() + " is geupdate naar: " +
                "" + keesie.getVoorletters() + " " + keesie.getTussenvoegsel() + " " + keesie.getAchternaam());

    }
    private static void testadresDAO(AdresDAO adao) throws SQLException {
        System.out.println("\n---------- Test AdresDAO -------------");

        List<Adres> adres = adao.findAll();
        System.out.println("[Test] AdresDAO.findAll() geeft de volgende adressen:");
        for (Adres a : adres) {
            System.out.println(a);
        }
        System.out.println();

//        Adres ad = adao.findByReiziger();
//        System.out.println("[Test] ReizigerDAO.findByGbDatum() geeft de volgende persoon:");
//        System.out.println(reiziger);
//        System.out.println();

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



}
