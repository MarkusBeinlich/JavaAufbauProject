/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.beinlich.markus.musicsystem.model.net;

import de.beinlich.markus.musicsystem.model.MusicCollection;
import de.beinlich.markus.musicsystem.model.MusicSystem;
import java.io.Serializable;

/**
 *
 * @author Markus
 */
public class ClientInit implements Serializable{
    private final MusicSystem musicSystem;
    private final MusicCollection musicCollection;
    private final ServerPool serverPool;

    
    public ClientInit(MusicSystem musicSystem, MusicCollection musicCollection, ServerPool serverPool){
        this.musicSystem = musicSystem;
        this.musicCollection = musicCollection;
        this.serverPool = serverPool;
    }
    /**
     * @return the musicSystem
     */
    public MusicSystem getMusicSystem() {
        return musicSystem;
    }

    /**
     * @return the musicCollection
     */
    public MusicCollection getMusicCollection() {
        return musicCollection;
    }

    /**
     * @return the serverPool
     */
    public ServerPool getServerPool() {
        return serverPool;
    }
    
}
