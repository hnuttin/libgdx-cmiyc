package com.jazzjack.rab.bit.game;

import com.jazzjack.rab.bit.level.Level;
import com.jazzjack.rab.bit.level.NewLevelListener;

import java.util.ArrayList;
import java.util.List;

public class GameEventBus {

    private final List<NewLevelListener> newLevelListeners = new ArrayList<>();

    public void registerNewLevelListener(NewLevelListener newLevelListener) {
        newLevelListeners.add(newLevelListener);
    }

    public void publishNewLevelEvent(Level newLevel) {
        newLevelListeners.forEach(listener -> listener.onNewLevel(newLevel));
    }
}
