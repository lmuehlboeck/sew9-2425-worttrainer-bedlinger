package at.ac.tgm.bedlinger.controller;

import at.ac.tgm.bedlinger.model.WortTrainer;
import at.ac.tgm.bedlinger.persistenz.Persistenz;
import at.ac.tgm.bedlinger.persistenz.PersistenzXML;

import javax.swing.*;

/**
 * Repräsentiert den Controller des Programms
 *
 * @author Benjamin Edlinger
 * @version 2024-09-28
 */
public class Controller {
    private WortTrainer wortTrainer;
    private Persistenz persistenz;
    private View view;

    /**
     * Standardkonstruktor, der eine neue PersistenzXML als Standard-Persistenz verwendet.
     */
    public Controller() {
        this(new PersistenzXML());
    }

    /**
     * Konstruktor, der eine Persistenz als Parameter erwartet.
     *
     * @param persistenz die zu verwendende Persistenz
     */
    public Controller(Persistenz persistenz) {
        if (persistenz == null)
            throw new IllegalArgumentException("Persistenz darf nicht null sein");

        this.persistenz = persistenz;
        try {
            wortTrainer = this.persistenz.load();
        } catch (Exception e) {
            wortTrainer = new WortTrainer();
        }
    }

    /**
     * Überprüft, ob das Wort korrekt ist und aktualisiert die Anzeige.
     * Falls das Wort korrekt ist, wird das Bild des neuen WortEintrags angezeigt.
     *
     * @param wort das zu überprüfende Wort
     */
    public void check(String wort) {
        boolean result = wortTrainer.check(wort);
        view.setCounter(wortTrainer.getCounterAbgefragt(), wortTrainer.getCounterKorrekt(), wortTrainer.getCounterFalsch());
        view.setVorherigerVersuch(wortTrainer.getVorherigerVersuchKorrekt());
        if (result)
            view.setImg(wortTrainer.getAktuellerWortEintrag().getUrl());
    }

    /**
     * Speichert den aktuellen Zustand des WortTrainers.
     * Wenn ein Fehler auftritt, wird eine Fehlermeldung angezeigt.
     */
    public void save() {
        try {
            persistenz.save(wortTrainer);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Fehler beim Speichern: " + e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }
}
