package com.jauntymarble.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jauntymarble.game.AdHandler;
import com.jauntymarble.game.GameController;
import com.jauntymarble.game.GameData;

import ownLib.Own;


public class GameEntry extends Game {
    public static SpriteBatch spriteBatch;
    public AdHandler handler;

    private String TAG = "GameEntry";
    private Splash splashScreen;
    private boolean debug;
    private GameController gameController;

    public GameEntry(AdHandler handler) {
        this.handler = handler;
    }

    public void debugMode(boolean mode) {
        debug = mode;
    }

    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
//        if (handler != null) handler.showAds(GameData.SHOW_ADS); // enable ads
        splashScreen = new Splash(this, spriteBatch);
        this.setScreen(splashScreen);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        Own.dispose();
        spriteBatch.dispose();
    }

    public void finishLoading() {
        this.gameController = new GameController(this, spriteBatch);
        this.gameController.debugMode(debug);
        this.setScreen(this.gameController);
    }
}
