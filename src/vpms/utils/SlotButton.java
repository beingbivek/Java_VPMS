/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.utils;

import java.awt.Color;
import java.awt.Insets;
import javax.swing.JToggleButton;
import vpms.model.SlotInstanceData;

/**
 *
 * @author being
 */
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
    public void setStatus(String s){
        bay.setStatus(s);                            // ✔ setStatus()
        applyColour();
    }

    private void applyColour(){
        switch (bay.getStatus()) {
            case "occupied" -> setBackground(new Color(255,87,51));
            case "reserved" -> setBackground(new Color(255,195,0));
            case "disabled" -> setBackground(Color.DARK_GRAY);
            default         -> setBackground(new Color(52,168,83));
        }
        setForeground(Color.WHITE);
    }
}
