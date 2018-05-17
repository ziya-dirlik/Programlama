/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.List;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import model.Musteri;
import org.hibernate.Session;

/**
 *
 * @author Ziya
 */
public class MusteriYonetici {
      private JTable musteriTablo;
    private final static String SORGU_KALIP = "from Musteri m";
    private Session session;
    private Vector<String> sutunlar = new Vector<>();
    private Vector<Object> satir;
    private DefaultTableModel model;
    public MusteriYonetici(JTable musteriTablo) {
        this.musteriTablo = musteriTablo;
        sutunlar.add("Müşteri ID");
        sutunlar.add("Ad");
        sutunlar.add("Soyad");
        sutunlar.add("Şehir");
        sutunlar.add("Tel No");
        model=(DefaultTableModel)musteriTablo.getModel();
        model.setColumnIdentifiers(sutunlar);
    }

    public void musteriGetir(String aranan, String filtre) {
        String sorguMetin = "";
        if (filtre.equalsIgnoreCase("ad")) {
            sorguMetin = SORGU_KALIP + " where m.musteriad like '%" + aranan + "%'";
        } else if (filtre.equalsIgnoreCase("adres")) {
            sorguMetin = SORGU_KALIP + " where m.adres like '%" + aranan + "%'";
        }
        session.beginTransaction();
        List musterilerList = session.createQuery(sorguMetin).list();
        session.getTransaction().commit();
        musteriGoster(musterilerList);

    }

    public void ac() {
        session = HibernateUtil.getSessionFactory().openSession();
    }

    public void kapat() {
        session.close();
    }

    private void musteriGoster(List<Musteri> musterilerList) {
        model.getDataVector().removeAllElements();
        for (Musteri gelenMusteri : musterilerList) {
            satir=new Vector();
            satir.add(gelenMusteri.getMusteriid());
            satir.add(gelenMusteri.getMusteriad());
            satir.add(gelenMusteri.getMusterisoyad());
            satir.add(gelenMusteri.getAdres());
            satir.add(gelenMusteri.getTelno());
            model.addRow(satir);
        }
    }
}
