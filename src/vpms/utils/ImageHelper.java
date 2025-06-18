/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.utils;

/**
 *
 * @author being
 */

import javax.swing.*;
import java.awt.*;

public final class ImageHelper {

    private ImageHelper() {}

    /** Scales the icon to the size of the label – if width/height == 0 it
     *  returns the original icon untouched to avoid IllegalArgumentException.
     * @param src
     * @param target
     * @return 
     */
    public static Icon scaleToLabel(ImageIcon src, JLabel target) {
        int w = target.getWidth();
        int h = target.getHeight();
        if (w <= 0 || h <= 0) return src;                 // avoid crash
        Image scaled = src.getImage().getScaledInstance(w, h,
                                                         Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }
}

