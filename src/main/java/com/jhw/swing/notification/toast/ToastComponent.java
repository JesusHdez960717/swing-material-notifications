package com.jhw.swing.notification.toast;

import com.root101.swing.material.standards.MaterialFontRoboto;
import javax.swing.*;
import java.awt.*;
import com.root101.swing.util.MaterialDrawingUtils;

/**
 * A toast.
 */
public abstract class ToastComponent extends JComponent {

    public static final Color BACKGROUND = Color.decode("#323232");
    public static final Font FONT = MaterialFontRoboto.REGULAR.deriveFont(14f);
    private double yOffset = Double.POSITIVE_INFINITY;

    public ToastComponent() {
        setOpaque(false);
    }

    void setYOffset(double yOffset) {
        this.yOffset = yOffset;
    }

    public double getyOffset() {
        return yOffset;
    }

    /**
     * Paints this toast.
     *
     * @param g graphics canvas
     */
    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = MaterialDrawingUtils.getAliasedGraphics(g);

        ((Graphics2D) g).translate(0, yOffset);
        super.paint(g2);
    }
}
