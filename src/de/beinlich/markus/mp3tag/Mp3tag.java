/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.beinlich.markus.mp3tag;

import com.Ostermiller.util.CSVParser;
import com.Ostermiller.util.ExcelCSVParser;
import com.Ostermiller.util.LabeledCSVParser;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import de.beinlich.markus.musicsystem.model.db.DatabaseConnection;
import de.beinlich.markus.musicsystem.model.db.RecordDAO;
import de.beinlich.markus.musicsystem.model.db.TrackDAO;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Markus
 */
public class Mp3tag {

    Multimap<RecordDAO, TrackDAO> rc;

    // Verbindungs-Objekt
    private DatabaseConnection dbCon;

    // Statement-Objekt / Transport von Informationen ZUR datenbank
    private PreparedStatement stm;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        new Mp3tag().go();
    }

    public void go() {
        rc = HashMultimap.create();

        try {
            FileReader fr = new FileReader("c:\\temp\\mp3tag.csv");
            System.out.println("Encoding" + fr.getEncoding());
            LabeledCSVParser lcsvp = new LabeledCSVParser(
                    new ExcelCSVParser(fr)
            );
            lcsvp.changeDelimiter(';');

            while (lcsvp.getLine() != null) {
                rc.put(new RecordDAO(lcsvp.getValueByLabel("Album"), lcsvp.getValueByLabel("Interpret"),lcsvp.getValueByLabel("Pfad"), "CD"),
                        new TrackDAO(0, 0, lcsvp.getValueByLabel("Titel"), 
                                Integer.parseInt(lcsvp.getValueByLabel("Länge")), 0,
                                lcsvp.getValueByLabel("Dateiname")));
            }
        } catch (IOException ex) {
            Logger.getLogger(Mp3tag.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("RecordCollection:" + rc.size() + "-" + rc.toString());
        System.out.println("keyset: " + rc.keySet().size() + "-" + rc.keySet());

        dbCon = new DatabaseConnection();
        int rid = 0;
        int tn = 0;
        int uid = 0;

        for (RecordDAO record : rc.keySet()) {
            System.out.println("Record: " + record);
            rid = insertRecord(record);
            tn = 1;
            for (TrackDAO track : rc.get(record)) {
                track.setRid(rid);
                track.setTrackNumber(tn++);
                System.out.println("Track: " + track);
                uid = insertTrack(track);
            }
        }
    }

    public int insertRecord(RecordDAO record) {
        int rid = 0;
        Connection con = null;
        ResultSet rs = null;

        try {

            con = dbCon.getConnection();
            if (con == null) {
                return 0;
            }
            stm = con.prepareStatement("INSERT INTO record ("
                    + "ARTIST, "
                    + "MEDIUM, "
                    + "TITLE) VALUES(?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, record.getArtist());
            stm.setString(2, record.getMedium());
            stm.setString(3, record.getTitle());

            int rows = stm.executeUpdate();

//            con.commit();
            //Vergebenen Schlüssel holen
            rs = stm.getGeneratedKeys();
            if (rs.next()) {
                rid = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Mp3tag.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Mp3tag.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return rid;
    }

    public int insertTrack(TrackDAO track) {
        int uid = 0;
        Connection con = null;
        ResultSet rs = null;

        try {

            con = dbCon.getConnection();
            if (con == null) {
                return 0;
            }
            stm = con.prepareStatement("INSERT INTO track ("
                    + "PLAYINGTIME, "
                    + "RID, "
                    + "TRACKNUMBER, "
                    + "FILENAME, "
                    + "TITLE) VALUES(?,?,?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            stm.setInt(1, track.getPlayingTime());
            stm.setInt(2, track.getRid());
            stm.setInt(3, track.getTrackNumber());
            stm.setString(4, track.getFilename());
            stm.setString(5, track.getTitle());

            int rows = stm.executeUpdate();

//            con.commit();
            //Vergebenen Schlüssel holen
            rs = stm.getGeneratedKeys();
            if (rs.next()) {
                uid = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Mp3tag.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Mp3tag.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return uid;
    }
}