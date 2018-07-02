package com.jazzjack.rab.bit.cmiyc.render.hud.action;

import com.jazzjack.rab.bit.cmiyc.shared.position.HasPosition;

abstract class HUDActionBase implements HUDAction {

    private final HasPosition position;

    private boolean selected;

    protected HUDActionBase(HasPosition position) {
        this.position = position;
        this.selected = false;
    }

    @Override
    public boolean isSelected() {
        return selected;
    }

    @Override
    public void select() {
        selected = true;
    }

    @Override
    public HasPosition getPosition() {
        return position;
    }
}
