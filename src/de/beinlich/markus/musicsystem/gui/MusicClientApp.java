/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.beinlich.markus.musicsystem.gui;

import de.beinlich.markus.musicsystem.model.*;
import de.beinlich.markus.musicsystem.model.net.*;
import static de.beinlich.markus.musicsystem.model.net.ProtokollType.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.*;
import javax.imageio.*;
import javax.swing.*;

/**
 *
 * @author Markus Beinlich
 */
public class MusicClientApp extends javax.swing.JFrame {

    private MusicSystemInterface musicSystem;
    private MusicSystemControllerInterface musicSystemController;
    private final MusicClient mc;
//    private RecordCollection rc;
//    private RecordCollection cdc;
    private final TrackListModel tlm;
    private final RecordComboBoxModel rcm;
    private final SourceComboBoxModel scm;
    private final ServerComboBoxModel sercm;
    private static String clientName;
//    private Record record;
//    private ServerPool serverPool;
//    private MusicPlayer musicPlayer;
//    private MusicCollection musicCollection;
//    private PlayListComponent playListComponent;
//    private MusicSystemState musicState;
//    private double volume;
//    private double oldVolume;
//    private ClientInit clientInit;

    /**
     * Creates new form Player
     */
    public MusicClientApp() {

        //MusicClient erzeugen
        mc = new MusicClient(this);

        System.out.println(System.currentTimeMillis() + "**************MusicClient ist aktiv");
//        musicSystem = clientInit.getMusicSystem();
        musicSystem = mc;
        musicSystemController = mc;

        System.out.println(System.currentTimeMillis() + "musicSystem ist übergeben:" + musicSystem);

//        record = musicSystem.getRecord();
        tlm = new TrackListModel(musicSystem.getRecord());
        rcm = new RecordComboBoxModel(mc.getMusicCollection());
        scm = new SourceComboBoxModel((musicSystem.getSources()));
        sercm = new ServerComboBoxModel(mc.getServerPool().getActiveServers());

        initComponents();
//        this.setServerPool(serverPool);
        this.updateMusicCollection(mc.getMusicCollection());
        this.updateMusicPlayer(musicSystem.getActivePlayer());
        this.updateRecord(musicSystem.getRecord());
        this.updatePlayListComponent(musicSystem.getCurrentTrack());
        doClientInit();
//        musicSystem.setRecord(rc.getRecord(0)); //die cd darf erst eingelegt werden, wenn das GUI aufgebaut ist. Ansonsten wird durch den RecordObserver das nicht vorhandene GUI angesprochen.,
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroupSource = new javax.swing.ButtonGroup();
        buttonPlay = new javax.swing.JButton();
        buttonNext = new javax.swing.JButton();
        buttonPrevious = new javax.swing.JButton();
        buttonPause = new javax.swing.JButton();
        buttonStop = new javax.swing.JButton();
        labelElapsedTime = new javax.swing.JLabel();
        labelRemainingTime = new javax.swing.JLabel();
        sliderProgress = new javax.swing.JSlider();
        jLabel2 = new javax.swing.JLabel();
        labelCurrentTrack = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listCurrentRecord = new javax.swing.JList<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanelPicture = new javax.swing.JPanel();
        jCover = new javax.swing.JLabel();
        comboBoxRecords = new javax.swing.JComboBox<>();
        comboBoxSource = new javax.swing.JComboBox<>();
        sliderVolume = new javax.swing.JSlider();
        jLabel6 = new javax.swing.JLabel();
        comboBoxServer = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle(musicSystem.getName() + " - " + musicSystem.getLocation() + " - " + clientName);

        buttonPlay.setText("Play");
        buttonPlay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonPlayActionPerformed(evt);
            }
        });

        buttonNext.setText("Next");
        buttonNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonNextActionPerformed(evt);
            }
        });

        buttonPrevious.setText("Previous");
        buttonPrevious.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonPreviousActionPerformed(evt);
            }
        });

        buttonPause.setText("Pause");
        buttonPause.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonPauseActionPerformed(evt);
            }
        });

        buttonStop.setText("Stop");
        buttonStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonStopActionPerformed(evt);
            }
        });

        labelElapsedTime.setText("-");

        labelRemainingTime.setText("-");

        sliderProgress.setToolTipText("");
        sliderProgress.setValue(0);

        jLabel2.setText("Current Track: ");

        labelCurrentTrack.setText("-");

        listCurrentRecord.setModel(tlm);
        listCurrentRecord.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listCurrentRecord.setVisibleRowCount(4);
        listCurrentRecord.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listCurrentRecordMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(listCurrentRecord);

        jLabel3.setText("Current Record:");

        jLabel4.setText("Volume:");

        jLabel5.setText("Record:");

        jLabel1.setText("Source:");

        jPanelPicture.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jCover.setIcon(showCover()
        );
        jCover.setLabelFor(jPanelPicture);

        javax.swing.GroupLayout jPanelPictureLayout = new javax.swing.GroupLayout(jPanelPicture);
        jPanelPicture.setLayout(jPanelPictureLayout);
        jPanelPictureLayout.setHorizontalGroup(
            jPanelPictureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPictureLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jCover)
                .addContainerGap(116, Short.MAX_VALUE))
        );
        jPanelPictureLayout.setVerticalGroup(
            jPanelPictureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPictureLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jCover)
                .addContainerGap(112, Short.MAX_VALUE))
        );

        comboBoxRecords.setModel(rcm);
        comboBoxRecords.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboBoxRecordsItemStateChanged(evt);
            }
        });

        comboBoxSource.setModel(scm);
        comboBoxSource.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboBoxSourceItemStateChanged(evt);
            }
        });

        sliderVolume.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderVolumeStateChanged(evt);
            }
        });

        jLabel6.setText("Server:");

        comboBoxServer.setModel(sercm);
        comboBoxServer.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboBoxServerItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(labelElapsedTime)
                        .addGap(18, 18, 18)
                        .addComponent(sliderProgress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(labelRemainingTime))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(comboBoxRecords, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(1, 1, 1)
                                        .addComponent(jLabel1)
                                        .addGap(10, 10, 10)
                                        .addComponent(comboBoxSource, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jPanelPicture, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel4)
                                            .addComponent(jLabel6))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(comboBoxServer, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(sliderVolume, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(buttonPlay)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buttonNext)
                                .addGap(2, 2, 2)
                                .addComponent(buttonPrevious)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(buttonPause)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buttonStop))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(labelCurrentTrack, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanelPicture, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(sliderVolume, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(comboBoxServer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(comboBoxSource, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(comboBoxRecords, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(8, 8, 8))
                    .addComponent(jScrollPane1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(labelCurrentTrack))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sliderProgress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(labelElapsedTime)
                        .addComponent(labelRemainingTime)))
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonNext)
                    .addComponent(buttonPlay)
                    .addComponent(buttonPrevious)
                    .addComponent(buttonPause)
                    .addComponent(buttonStop)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonPlayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonPlayActionPerformed
        musicSystemController.play();
    }//GEN-LAST:event_buttonPlayActionPerformed

    private void buttonNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonNextActionPerformed
        musicSystemController.next();
    }//GEN-LAST:event_buttonNextActionPerformed

    private void buttonPreviousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonPreviousActionPerformed
        musicSystemController.previous();
    }//GEN-LAST:event_buttonPreviousActionPerformed

    private void buttonPauseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonPauseActionPerformed
        musicSystemController.pause();
    }//GEN-LAST:event_buttonPauseActionPerformed

    private void buttonStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonStopActionPerformed
        musicSystemController.stop();
    }//GEN-LAST:event_buttonStopActionPerformed

    private void sliderVolumeStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sliderVolumeStateChanged
        musicSystemController.setVolume(sliderVolume.getValue());
    }//GEN-LAST:event_sliderVolumeStateChanged

    private void listCurrentRecordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listCurrentRecordMouseClicked
        System.out.println(System.currentTimeMillis() + "listCurrentRecordMouseClicked" + listCurrentRecord.getSelectedValue());
        musicSystemController.setCurrentTrack(listCurrentRecord.getSelectedValue());
    }//GEN-LAST:event_listCurrentRecordMouseClicked

    private void comboBoxRecordsItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboBoxRecordsItemStateChanged
        //das Verändern des musicSystem/MusicSystem-Objektes muss vom Model/Server aus erfolgen. Sonst gibt es Rückkoppelungen
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            System.out.println(System.currentTimeMillis() + "comboBoxRecordsItemStateChanged: " + listCurrentRecord.getSelectedValue());
            musicSystemController.setRecord((Record) comboBoxRecords.getSelectedItem());
        }

    }//GEN-LAST:event_comboBoxRecordsItemStateChanged

    private void comboBoxSourceItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboBoxSourceItemStateChanged
        //das Verändern des musicSystem/MusicSystem-Objektes muss vom Model/Server aus erfolgen. Sonst gibt es Rückkoppelungen
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            System.out.println("musicPlayerSelected: " + comboBoxSource.getSelectedItem() );
            musicSystemController.setActiveSource(((MusicPlayer) comboBoxSource.getSelectedItem()).getTitle());
        }

    }//GEN-LAST:event_comboBoxSourceItemStateChanged

    private void comboBoxServerItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboBoxServerItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            if (!comboBoxServer.getSelectedItem().equals(mc.getCurrentServerAddr().getName())) {
                if (false == mc.switchToServer((String) comboBoxServer.getSelectedItem())) {
                    JOptionPane.showMessageDialog(null, "Server " + comboBoxServer.getSelectedItem() + " ist im Moment nicht erreichbar. Eventuell ist er nicht gestartet.");
                    sercm.setSelectedItem(mc.getCurrentServerAddr().getName());
                } else {
                    System.out.println(System.currentTimeMillis() + "**************MusicClient ist aktiv");
                    doClientInit();

                }
            }
        }
    }//GEN-LAST:event_comboBoxServerItemStateChanged

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        //Namen des Client setzen (wird für Eindeutigkeit benötigt
        if (args.length >= 1) {
            clientName = args[0];
        } else {
            clientName = "Client";
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MusicClientApp().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroupSource;
    private javax.swing.JButton buttonNext;
    private javax.swing.JButton buttonPause;
    private javax.swing.JButton buttonPlay;
    private javax.swing.JButton buttonPrevious;
    private javax.swing.JButton buttonStop;
    private javax.swing.JComboBox<Record> comboBoxRecords;
    private javax.swing.JComboBox<String> comboBoxServer;
    private javax.swing.JComboBox<MusicPlayer> comboBoxSource;
    private javax.swing.JLabel jCover;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanelPicture;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelCurrentTrack;
    private javax.swing.JLabel labelElapsedTime;
    private javax.swing.JLabel labelRemainingTime;
    private javax.swing.JList<PlayListComponent> listCurrentRecord;
    private javax.swing.JSlider sliderProgress;
    private javax.swing.JSlider sliderVolume;
    // End of variables declaration//GEN-END:variables

    class RecordComboBoxModel extends DefaultComboBoxModel<Record> {

        private MusicCollection rc;

        public RecordComboBoxModel(MusicCollection rc) {
            this.rc = rc;
            this.setSelectedItem(rc.getRecord(0));
        }

        public void replaceRecordCollection(MusicCollection rc) {
            this.rc = rc;
        }

        @Override
        public int getSize() {
            return rc.getRecords().size();
        }

        @Override
        public Record getElementAt(int index) {
            return rc.getRecord(index);
        }

    }

    class SourceComboBoxModel extends DefaultComboBoxModel<MusicPlayer> {

        private LinkedList<MusicPlayer> sources;

        public SourceComboBoxModel(LinkedList<MusicPlayer> sources) {
            this.sources = sources;
            this.setSelectedItem(sources.getFirst());
        }

        public void replaceSources(LinkedList<MusicPlayer> sources) {
            this.sources = sources;
        }

        @Override
        public int getSize() {
            return sources.size();
        }

        @Override
        public MusicPlayer getElementAt(int index) {
            return sources.get(index);
        }

    }

    class ServerComboBoxModel extends DefaultComboBoxModel<String> {

        private List<String> servers;

        public ServerComboBoxModel(List<String> servers) {
            this.servers = servers;
            this.setSelectedItem(servers.get(0));
        }

        public void replaceServers(List<String> servers) {
            this.servers = servers;
        }

        @Override
        public int getSize() {
            return servers.size();
        }

        @Override
        public String getElementAt(int index) {
            return servers.get(index);
        }

    }

    class TrackListModel extends DefaultListModel<PlayListComponent> {

        private Record record;

        public TrackListModel(Record record) {
//            addListDataListener(this);
            this.record = record;
        }

        public void replaceRecord(Record record) {

            this.record = record;
            System.out.println("replaceRecord: " + getSize());
            this.fireContentsChanged(this, 0, getSize() - 1);
        }

        @Override
        public int getSize() {
            return record.getTracks().size();
        }

        @Override
        public PlayListComponent getElementAt(int index) {
//            System.out.println(System.currentTimeMillis() + "getElementAt" + index + record.getTracks().get(index));
            return record.getTracks().get(index);
        }

    }

    public void updateServerPool(ServerPool serverPool) {
        System.out.println(System.currentTimeMillis() + "updateServerPool ");
        sercm.replaceServers(serverPool.getActiveServers());
//        comboBoxServer.setSelectedItem(musicSystem.getServerAddr().getName());
    }

    public void updateMusicCollection(MusicCollection musicCollection) {
        rcm.replaceRecordCollection(musicCollection);
    }

    public void updateMusicPlayer(MusicPlayer musicPlayer) {
        System.out.println(System.currentTimeMillis() + "UpdateMusicPlayer");

        //Werte der aktiven MusicPlayer anzeigen
        buttonNext.setEnabled(musicSystem.hasNext());
        buttonPrevious.setEnabled(musicSystem.hasPrevious());
        buttonPause.setEnabled(musicSystem.hasPause());
        sliderProgress.setEnabled(musicSystem.hasCurrentTime());
        jScrollPane1.setEnabled(musicSystem.hasTracks());
        listCurrentRecord.setEnabled(musicSystem.hasTracks());
        //richtige Source selektieren
        comboBoxSource.setSelectedItem(musicPlayer);
    }

    public void updateRecord(Record record) {

        if (listCurrentRecord != null) {
            listCurrentRecord.clearSelection(); //erzeugt eine Nullpointerexception
        }
        comboBoxRecords.setSelectedItem(record);
        System.out.println(System.currentTimeMillis() + "setRecord: " + record);

        tlm.replaceRecord(record);
        listCurrentRecord.setSelectedIndex(0);
        jCover.setIcon(showCover());

    }

    /**
     *
     */
    public void updatePlayListComponent(PlayListComponent playListComponent) {
        System.out.println("updatePlayListComponent:" + playListComponent);
        if (musicSystem.getCurrentTrack() != null) {
            listCurrentRecord.setSelectedValue(playListComponent, true);
            labelCurrentTrack.setText(playListComponent.getTitle() + " : " + playListComponent.getPlayingTime());
            sliderProgress.setMinimum(0);
            sliderProgress.setMaximum(playListComponent.getPlayingTime());
        }
    }

    private void doClientInit() {

//        musicSystem = clientInit.getMusicSystem();
        System.out.println(System.currentTimeMillis() + "musicSystem ist übergeben:" + musicSystem);
        updateRecord(musicSystem.getRecord());
        updateServerPool(mc.getServerPool());
        updateMusicCollection(mc.getMusicCollection());
        updateMusicPlayer(musicSystem.getActivePlayer());
        updatePlayListComponent(musicSystem.getCurrentTrack());
        updateTrackTime(musicSystem.getCurrentTimeTrack());
        updateVolume(musicSystem.getVolume());
        scm.replaceSources(musicSystem.getSources());
        setTitle(musicSystem.getName() + " - " + musicSystem.getLocation() + " - " + clientName);
    }

    /**
     *
     */
    public void updateTrackTime(int trackTime) {
        labelElapsedTime.setText("- " + trackTime);
        labelRemainingTime.setText(" " + (musicSystem.getCurrentTrack().getPlayingTime() - trackTime));
        sliderProgress.setValue((int) trackTime);
    }

    public void updateVolume(double volume) {
        sliderVolume.setValue((int) volume);
    }

    public boolean hasCover() {
        return (musicSystem.getRecord().getCover() != null);
    }

    public Icon showCover() {
        Icon icon;
        BufferedImage img;
        icon = null;
        if (hasCover()) {
            try {
                img = ImageIO.read(new ByteArrayInputStream(musicSystem.getRecord().getCover()));
                if (img != null) {
                    icon = new ImageIcon(img.getScaledInstance(150, 150, WIDTH));
                }
            } catch (IOException ex) {
                Logger.getLogger(MusicClientApp.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return icon;
    }

    /**
     * @return the title
     */
    public String getClientName() {
        return clientName;
    }


}
