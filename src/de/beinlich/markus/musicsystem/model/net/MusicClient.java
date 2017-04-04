/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.beinlich.markus.musicsystem.model.net;

import de.beinlich.markus.musicsystem.gui.*;
import de.beinlich.markus.musicsystem.model.*;
import static de.beinlich.markus.musicsystem.model.net.ProtokollType.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.*;
import javax.swing.SwingWorker;

/**
 *
 * @author Markus Beinlich
 */
public class MusicClient extends SwingWorker<Void, Void> implements MusicSystemInterface, MusicSystemControllerInterface {

    private final MusicClientApp mca;

    // Verbindungsaufbau mit dem Server
    public Socket socket;
    private Socket newSocket;
    private ServerAddr currentServerAddr;
    private Thread readerThread;
    //
    // IO-Klassen zur Kommunikation
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    private static final int MAX_RECONNECTIONS = 10;
    private int reconnections = 0;

    private MusicSystem musicSystem;
    private Record record;
    private ServerPool serverPool;
    private MusicPlayer musicPlayer;
    private MusicCollection musicCollection;
    private PlayListComponent playListComponent;
    private MusicSystemState musicSystemState;
    private double volume;
    private double oldVolume;
    private int trackTime;
    private ClientInit clientInit;

    public MusicClient(MusicClientApp mca) {
        this.mca = mca;
        serverPool = ServerPool.getInstance(mca.getClientName());
        currentServerAddr = serverPool.getFirstServer();
        System.out.println("Alle:" + serverPool.getServers().toString());
//        currentServerAddr = (ServerAddr)((TreeMap)serverPool.getServers()).floorEntry("xyz").getValue();
        System.out.println("currentServerAddr: " + currentServerAddr);
        netzwerkEinrichten(currentServerAddr);
        musicSystemObjectRead();
        startReaderThread();
        System.out.println(System.currentTimeMillis() + "netzwerk eingerichtet: ");
    }

    private void netzwerkEinrichten(ServerAddr serverAddr) {

        try {
            // Erzeugung eines Socket-Objekts
            //                  Rechner (Adresse / Name)
            //                  |            Port

            //Verbindungs-Parameter in property-file auslagern
            NetProperties netProperties = new NetProperties();
            System.out.println(System.currentTimeMillis() + "new Socket with " + serverAddr.getServer_ip() + serverAddr.getPort());
            socket = new Socket(serverAddr.getServer_ip(), serverAddr.getPort());
            System.out.println(System.currentTimeMillis() + "socket.connect");
            //socket.connect(socket.getRemoteSocketAddress() , 0);
            // Erzeugung der Kommunikations-Objekte
            ois = new ObjectInputStream(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());
        } catch (ConnectException e) {
            System.out.println(System.currentTimeMillis() + "Error while connecting. " + e.getMessage());
            this.tryToReconnect();
        } catch (SocketTimeoutException e) {
            System.out.println(System.currentTimeMillis() + "Connection: " + e.getMessage() + ".");
            this.tryToReconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startReaderThread() {
        // Thread der sich um die eingehende Kommunikation kümmert
        readerThread = new Thread(new MusicClient.EingehendReader(this));
        // Thread als (Hintergrund-) Service
        readerThread.setDaemon(true);
        readerThread.start();
        System.out.println(System.currentTimeMillis() + "CLIENT: Netzwerkverbindung steht jetzt");
    }

    private void musicSystemObjectRead() {
        Protokoll nachricht;
        ClientInit clientInit;

        try {
            // Als erstes write den Namen des eigenen Client übergeben!
            oos.writeObject(new Protokoll(ProtokollType.CLIENT_NAME, mca.getClientName()));
            oos.flush();
            try {
                // reinkommende Nachrichten vom Server. Auf diese muss gewartet werden, 
                // da ansonsten die initialisierung der GUI nicht funktioniert.
                nachricht = (Protokoll) ois.readObject(); // blockiert!
                clientInit = (ClientInit) nachricht.getValue();
//                mca.setClientInit(clientInit);
                musicSystem = clientInit.getMusicSystem();
                musicCollection = clientInit.getMusicCollection();
                ServerPool.getInstance(mca.getClientName()).addServers(clientInit.getServerPool());
                musicSystemState = musicSystem.getMusicSystemState();
                record = musicSystem.getRecord();
                musicPlayer = musicSystem.getActivePlayer();
                playListComponent = musicSystem.getCurrentTrack();
                trackTime = musicSystem.getCurrentTimeTrack();

            } catch (ClassNotFoundException ex) {
                System.out.println(ex);
            }

        } catch (IOException ex) {
            System.out.println(System.currentTimeMillis() + "no connection - " + ex);
        }
    }

    public void writeObject(Protokoll protokoll) {
        try {
            System.out.println(System.currentTimeMillis() + "writeObject:" + protokoll.getProtokollType() + ": " + protokoll.getValue());
            // einen Befehl an der Server übertragen
            oos.writeObject(protokoll);
            oos.flush();
        } catch (IOException ex) {
            Logger.getLogger(Protokoll.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    public void writeCommand(MusicClientCommand mCC) {
//        System.out.println(System.currentTimeMillis() + "writeCommand:" + mCC.getType() + " new:" + mCC.getNewState() + " old: " + mCC.getOldState());
//        try {
//            // einen Befehl an der Server übertragen
//            oos.writeObject(new Protokoll(ProtokollType.CLIENT_COMMAND, mCC));
//            oos.flush();
//        } catch (IOException ex) {
//            Logger.getLogger(MusicClientCommand.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//    }
    @Override
    protected Void doInBackground() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void tryToReconnect() {
//        this.disconnect();

        System.out.println(System.currentTimeMillis() + "I will try to reconnect in 10 seconds... (" + this.reconnections + "/10)");
        try {
            Thread.sleep(10000); //milliseconds
        } catch (InterruptedException e) {
        }

        if (this.reconnections < MAX_RECONNECTIONS) {
            this.reconnections++;
            this.netzwerkEinrichten(getCurrentServerAddr());

        } else {
            System.out.println(System.currentTimeMillis() + "Reconnection failed, exceeded max reconnection tries. Shutting down.");
//            this.disconnect();
            System.exit(0);
            return;
        }

    }

    public boolean switchToServer(String newServer) {
        ServerAddr serverAddr;
        System.out.println("switchToServer:" + newServer);
        serverAddr = serverPool.getServers().get(newServer);
        System.out.println("switchToServer:" + serverAddr + ServerPool.getInstance(mca.getClientName()));

        try {
            //wenn es geklappt hat, kann die Verbindung zum alten Server getrennt werden
            System.out.println("Old Socket:" + socket.hashCode());
            newSocket = new Socket(serverAddr.getServer_ip(), serverAddr.getPort());
            socket.close();
            System.out.println("Old Socket:" + socket.hashCode());
            System.out.println("LocalSocketAddress: " + newSocket.getLocalSocketAddress());
            System.out.println("RemoteSocketAddress: " + newSocket.getRemoteSocketAddress());

            // Erzeugung der Kommunikations-Objekte
            ois = new ObjectInputStream(newSocket.getInputStream());
            oos = new ObjectOutputStream(newSocket.getOutputStream());
            socket = newSocket;
            this.currentServerAddr = serverAddr;
            musicSystemObjectRead();
            startReaderThread();
            System.out.println(System.currentTimeMillis() + "netzwerk eingerichtet: ");

        } catch (IOException ex) {
            Logger.getLogger(MusicClient.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    @Override
    public MusicPlayer getActivePlayer() {
        return musicPlayer;
    }

    @Override
    public Record getRecord() {
        return record;
    }

    @Override
    public void setRecord(Record record) {
        if (!this.record.equals(record)) {
            try {
                writeObject(new Protokoll(RECORD_SELECTED, record));
            } catch (InvalidObjectException ex) {
                Logger.getLogger(MusicClientApp.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public MusicSystemState getMusicSystemState() {
        return musicSystemState;
    }

    @Override
    public LinkedList<MusicPlayer> getSources() {
       return musicSystem.getSources();
    }

    @Override
    public PlayListComponent getCurrentTrack() {
        return playListComponent;
    }

    @Override
    public String getName() {
        return musicSystem.getName();
    }

    @Override
    public String getLocation() {
        return musicSystem.getLocation();
    }

    @Override
    public int getCurrentTimeTrack() {
        return trackTime;
    }

    @Override
    public double getVolume() {
        return volume;
    }

    @Override
    public void play() {
        try {
            writeObject(new Protokoll(CLIENT_COMMAND_PLAY, musicSystemState));

        } catch (InvalidObjectException ex) {
            Logger.getLogger(MusicClientApp.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void pause() {
        try {
            writeObject(new Protokoll(CLIENT_COMMAND_PAUSE, musicSystemState));

        } catch (InvalidObjectException ex) {
            Logger.getLogger(MusicClientApp.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void next() {
        try {
            writeObject(new Protokoll(CLIENT_COMMAND_NEXT, playListComponent));

        } catch (InvalidObjectException ex) {
            Logger.getLogger(MusicClientApp.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void previous() {
        try {
            writeObject(new Protokoll(CLIENT_COMMAND_PREVIOUS, playListComponent));

        } catch (InvalidObjectException ex) {
            Logger.getLogger(MusicClientApp.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void stop() {
        try {
            writeObject(new Protokoll(CLIENT_COMMAND_STOP, musicSystemState));

        } catch (InvalidObjectException ex) {
            Logger.getLogger(MusicClientApp.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void setVolume(double volume) {
        try {
            writeObject(new Protokoll(VOLUME, new Double(volume)));

        } catch (InvalidObjectException ex) {
            Logger.getLogger(MusicClientApp.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void setCurrentTrack(PlayListComponent track) {
        try {
            //das Verändern des musicSystem/MusicSystem-Objektes muss vom Model/Server aus erfolgen. Sonst gibt es Rückkoppelungen
            //musicSystem.setCurrentTrack(listCurrentRecord.getSelectedValue());
            writeObject(new Protokoll(TRACK_SELECTED, track));

        } catch (InvalidObjectException ex) {
            Logger.getLogger(MusicClientApp.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void setActiveSource(MusicPlayer activeSource) throws IllegaleSourceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ServerAddr getServerAddr() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean hasPause() {
        return musicSystem.hasPause();
    }

    @Override
    public boolean hasPlay() {
        return musicSystem.hasPlay();
    }

    @Override
    public boolean hasNext() {
        return musicSystem.hasNext();
    }

    @Override
    public boolean hasPrevious() {
        return musicSystem.hasPrevious();
    }

    @Override
    public boolean hasStop() {
        return musicSystem.hasStop();
    }

    @Override
    public boolean hasTracks() {
        return musicSystem.hasTracks();
    }

    @Override
    public boolean hasCurrentTime() {
        return musicSystem.hasCurrentTime();
    }

    @Override
    public void registerObserver(TrackObserver o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void registerObserver(TrackTimeObserver o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void registerObserver(VolumeObserver o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void registerObserver(StateObserver o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void registerObserver(RecordObserver o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void registerObserver(MusicPlayerObserver o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setActiveSource(String selectedSource) {

        if (!(musicPlayer.getTitle().equals(selectedSource))) {
            try {
                writeObject(new Protokoll(MUSIC_SOURCE_SELECTED, selectedSource));
            } catch (InvalidObjectException ex) {
                Logger.getLogger(MusicClientApp.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @Override
    public MusicPlayer getSource(String title) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    class EingehendReader implements Runnable {

        private final MusicClient musicClient;

        private EingehendReader(MusicClient musicClient) {
            this.musicClient = musicClient;
        }

        @Override
        public void run() {
            Protokoll nachricht;
            MusicPlayer musicPlayer;
            Record record;
            MusicSystemState state;
            PlayListComponent playListComponent;
            double volume;

            try {
                while (true) {

                    // reinkommende Nachrichten vom Server
                    Object o = ois.readObject();
                    nachricht = (Protokoll) o; // blockiert!
                    System.out.println(System.currentTimeMillis() + "CLIENT: gelesen: " + nachricht + " - " + o.getClass());
                    switch (nachricht.getProtokollType()) {
                        case MUSIC_COLLECTION:
                            musicClient.musicCollection = (MusicCollection) nachricht.getValue();
                            mca.updateMusicCollection(musicClient.getMusicCollection());
                            break;
                        case MUSIC_SOURCE:
                            musicPlayer = (MusicPlayer) nachricht.getValue();
                            if (!musicPlayer.equals(musicClient.musicPlayer)) {
                                musicClient.musicSystem.setActiveSource(musicPlayer);
                                musicClient.musicPlayer = musicPlayer;
                                mca.updateMusicPlayer(musicPlayer);
                            }
                            break;
                        case RECORD:
                            //Achtung: Rückkopplung vermeiden
                            record = (Record) nachricht.getValue();
                            if (!(record.equals(musicClient.record))) {
                                System.out.println(System.currentTimeMillis() + "RECORD");
                                musicClient.record = record;
                                mca.updateRecord(record);
                            }
                            break;
                        case STATE:
                            //Achtung: Rückkopplung vermeiden
                            state = (MusicSystemState) nachricht.getValue();
                            System.out.println(System.currentTimeMillis() + "State");
                            musicSystemState = state;
                            break;
                        case PLAY_LIST_COMPONENT:
                            //Achtung: Rückkopplung vermeiden
                            playListComponent = (PlayListComponent) nachricht.getValue();
                            if (!(playListComponent.equals(musicClient.playListComponent))) {
                                System.out.println(System.currentTimeMillis() + "TRACK");
                                musicClient.playListComponent = playListComponent;
                                mca.updatePlayListComponent(playListComponent);
                            }
                            break;
                        case TRACK_TIME:
                            trackTime = (int) nachricht.getValue();
                            mca.updateTrackTime(trackTime);
                            break;
                        case VOLUME:
                            //Achtung: Rückkopplung vermeiden
                            volume = (double) nachricht.getValue();
                            if (volume != musicClient.getVolume() && volume != musicClient.getOldVolume()) {
                                musicClient.setOldVolume(musicClient.getVolume());
                                mca.updateVolume(volume);
                            }
                            break;
                        case SERVER_POOL:
                            musicClient.serverPool = (ServerPool) nachricht.getValue();
                            mca.updateServerPool(musicClient.serverPool);
                            break;
//                        case HAS_CURRENT_TIME:
//                            mca.updateHasCurrentTime((Boolean) nachricht.getValue());
//                            break;
//                        case HAS_TRACKS:
//                            mca.updateHasTracks((Boolean) nachricht.getValue());
//                            break;
//                        case HAS_PAUSE:
//                            mca.updateHasPause((Boolean) nachricht.getValue());
//                            break;
//                        case HAS_NEXT:
//                            mca.updateHasNext((Boolean) nachricht.getValue());
//                            break;
//                        case HAS_PREVIOUS:
//                            mca.updateHasPrevious((Boolean) nachricht.getValue());
//                            break;
                        default:
                            System.out.println(System.currentTimeMillis() + "Unbekannte Nachricht:" + nachricht.getProtokollType());
                    }
                }

            } catch (Exception ex) {
                System.out.println(System.currentTimeMillis() + "CLIENT: Verbindung zum Server beendet - " + ex);
                ex.printStackTrace();
            }
        }
    }

    /**
     * @return the currentServerAddr
     */
    public ServerAddr getCurrentServerAddr() {
        return currentServerAddr;
    }

    /**
     * @param currentServerAddr the currentServerAddr to set
     */
    public void setCurrentServerAddr(ServerAddr currentServerAddr) {
        this.currentServerAddr = currentServerAddr;
    }

    /**
     * @return the oldVolume
     */
    public double getOldVolume() {
        return oldVolume;
    }

    /**
     * @param oldVolume the oldVolume to set
     */
    public void setOldVolume(double oldVolume) {
        this.oldVolume = oldVolume;
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
