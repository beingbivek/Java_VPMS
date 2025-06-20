package vpms.utils;

import vpms.model.SlotInstanceData;
import javax.swing.*;
import java.awt.*;

public class SlotButton extends JToggleButton {
    private final SlotInstanceData bay;

    public SlotButton(SlotInstanceData bay) {
        super(bay.getCode());
        this.bay = bay;
        setMargin(new Insets(2,2,2,2));
        setFont(getFont().deriveFont(11f));
        setFocusPainted(false);
        setOpaque(true);
        applyColour();
    }
    public SlotInstanceData getBay() { return bay; }
    public void setStatus(String s){ bay.setStatus(s); applyColour(); }

    private void applyColour(){
        switch (bay.getStatus()) {
            case "occupied" -> setBackground(new Color(255,87,51));     // red
            case "reserved" -> setBackground(new Color(255,195,0));     // yellow
            case "disabled" -> setBackground(Color.DARK_GRAY);          // gray
            default         -> setBackground(new Color(52,168,83));     // green (free)
        }
        setForeground(Color.WHITE);
    }
}
