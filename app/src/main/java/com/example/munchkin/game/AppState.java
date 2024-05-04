package com.example.munchkin.game;

public class AppState {
    private static AppState instance;
    private String currentUser;

    private AppState() {}

    public static synchronized AppState getInstance() {
        if (instance == null) {
            instance = new AppState();
        }
        return instance;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }


}




