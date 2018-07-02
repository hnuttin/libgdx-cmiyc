package com.jazzjack.rab.bit.cmiyc.render.hud.action;

import com.jazzjack.rab.bit.cmiyc.shared.position.HasPosition;

public interface HUDAction {

    boolean isSelected();

    void select();

    void execute();

    HasPosition getPosition();
}
