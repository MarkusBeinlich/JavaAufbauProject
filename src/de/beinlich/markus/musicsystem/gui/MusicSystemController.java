/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.beinlich.markus.musicsystem.gui;

import de.beinlich.markus.musicsystem.model.*;
import java.util.logging.*;

/**
 *
 * @author Markus
 */
public class MusicSystemController implements MusicSystemControllerInterface {

    MusicSystemInterface musicSystem;
    MusicServerApp musicServerApp;

    public MusicSystemController(MusicSystem musicSystem) {
        this.musicSystem = musicSystem;
        musicServerApp = new MusicServerApp(this, musicSystem);
    }

    @Override
    public void play() {
        musicSystem.play();
    }

    @Override
    public void pause() {
        musicSystem.pause();
    }

    @Override
    public void stop() {
        musicSystem.stop();
    }

    @Override
    public void previous() {
        musicSystem.previous();
    }

    @Override
    public void next() {
        musicSystem.next();
    }

    @Override
    public void setVolume(double volume) {
        musicSystem.setVolume(volume);
    }

    @Override
    public void setCurrentTrack(PlayListComponent track) {
        musicSystem.setCurrentTrack(track);
    }

    @Override
    public void setActiveSource(String selectedSource) {
        try {
            musicSystem.setActiveSource(musicSystem.getSource(selectedSource));
        } catch (IllegaleSourceException ex) {
            Logger.getLogger(MusicServerApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void setRecord(Record record) {
        musicSystem.setRecord(record);
    }
}
