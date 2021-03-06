package de.beinlich.markus.musicsystem.model;

import java.io.InputStream;
import java.io.Serializable;
import java.util.*;

/**
 *
 * @author Markus Beinlich
 */
public class Record implements Serializable {

    private String title;
    private String artist;
    private String medium;
    private byte[] cover;
    private ArrayList<PlayListComponent> tracks;

    /**
     *
     */
    public Record() {
        this("Blank");
    }

    /**
     *
     * @param title
     */
    public Record(String title) {
        this(title, "Nobody");
    }

    public Record(String title, String artist) {
        this(title, artist, null);
    }

    /**
     *
     * @param title
     * @param artist
     */
    public Record(String title, String artist, byte[] cover) {
        this.title = title;
        this.artist = artist;
        this.cover = cover;
        tracks = new ArrayList<>();
    }

    /**
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title das zu setzende Objekt title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int hashCode() {
        return title.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Record other = (Record) obj;
        return this.title.equals(other.title);
    }

    @Override
    public String toString() {
        return this.artist + " - " +this.getTitle();
    }

    /**
     * @return the tracks
     */
    public List<PlayListComponent> getTracks() {
        return Collections.unmodifiableList(tracks);
    }

    /**
     * @param tracks the tracks to set
     */
    public void setTracks(ArrayList<PlayListComponent> tracks) {
        this.tracks = tracks;
    }

    /**
     *
     * @param track
     */
    public void addTrack(PlayListComponent track) {
        tracks.add(track);
    }

    /**
     * @return the artist
     */
    public String getArtist() {
        return artist;
    }

    /**
     * @param artist the artist to set
     */
    public void setArtist(String artist) {
        this.artist = artist;
    }

    /**
     * @return the medium
     */
    public String getMedium() {
        return medium;
    }

    /**
     * @param medium the medium to set
     */
    public void setMedium(String medium) {
        this.medium = medium;
    }

    /**
     * @return the cover
     */
    public byte[] getCover() {
        return cover;
    }

    /**
     * @param cover the cover to set
     */
    public void setCover(byte[] cover) {
        this.cover = cover;
    }

}
