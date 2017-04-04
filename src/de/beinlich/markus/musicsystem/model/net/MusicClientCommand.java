///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package de.beinlich.markus.musicsystem.model.net;
//
//import java.io.IOException;
//import java.io.ObjectOutputStream;
//import java.io.Serializable;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
///**
// *
// * @author Markus
// */
//public class MusicClientCommand implements Serializable{
//
//    private final String type;
//    private final Object newState;
//    private final Object oldState;
//
//    public MusicClientCommand(String type, Object newState, Object oldState) {
//        this.type = type;
//        this.newState = newState;
//        this.oldState = oldState;
//    }
//
//
//    /**
//     * @return the type
//     */
//    public String getType() {
//        return type;
//    }
//
//    /**
//     * @return the oldState
//     */
//    public Object getOldState() {
//        return oldState;
//    }
//
//    /**
//     * @return the newState
//     */
//    public Object getNewState() {
//        return newState;
//    }
//        @Override
//    public String toString() {
//        String newSt = (this.newState == null) ? " new: null" : (" new: " + this.newState);
//        String oldSt = (this.oldState == null) ? " old: null" : (" old: " + this.oldState);
//        return "\nCommand: " + type + newSt + oldSt;
//    }
//
//}
