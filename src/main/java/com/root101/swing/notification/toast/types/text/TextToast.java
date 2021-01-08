package com.root101.swing.notification.toast.types.text;

import com.root101.swing.notification.toast.ToastComponent;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import com.root101.swing.material.effects.*;
import com.root101.swing.material.standards.MaterialColors;
import com.root101.swing.material.standards.MaterialFontRoboto;
import com.root101.swing.material.standards.MaterialShadow;
import com.root101.swing.util.MaterialDrawingUtils;
import com.root101.swing.util.Utils;

/**
 * A toast that contains textDisplay.
 *
 * @see <a
 * href="https://www.google.com/design/spec/components/snackbars-toasts.html">Snackbars
 * and toasts</a>
 */
public class TextToast extends ToastComponent implements ElevationEffect {

    private final static float OPACITY = 0.8f;

    private final ElevationEffect elevation;

    private String textDisplay;

    private int borderRadius = 15;

    private double elevationShadow = 1;

    private Cursor cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

    /**
     * Creates a new toast.
     *
     * @param text the textDisplay of this toast
     */
    public TextToast(String text, Font textFont, Color color) {
        super.setCursor(cursor);

        elevation = DefaultElevationEffect.applyTo(this, MaterialShadow.ELEVATION_DEFAULT);
        elevation.setBorderRadius(borderRadius);
        //elevation.setOpacity(OPACITY);

        this.setBackground(color);
        this.setFont(MaterialFontRoboto.REGULAR.deriveFont(18f));
        setTextDisplay(text);
    }

    @Override
    public double getLevel() {
        return elevation.getLevel();
    }

    @Override
    public double getElevation() {
        return elevationShadow;
    }

    @Override
    public void paintElevation(Graphics2D g2) {
        elevation.paintElevation(g2);
    }

    @Override
    public int getBorderRadius() {
        return borderRadius;
    }

    @Override
    public Cursor getCursor() {
        return cursor;
    }

    @Override
    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }

    @Override
    public void setBackground(Color bg) {
        super.setBackground(bg);
        this.setForeground(Utils.getForegroundAccording(backTranslucid()));
    }

    public void setTextDisplay(String text) {
        this.textDisplay = text;
        updateSize();
    }

    private void updateSize() {
        FontMetrics fm = Utils.fontMetrics(getFont());
        int width = fm.stringWidth(textDisplay);
        int heigth = fm.getAscent();

        this.setSize(width + 50, heigth + 30);
    }

    /**
     * Gets the textDisplay of this toast.
     *
     * @return the textDisplay of this toast
     */
    public String getContent() {
        return textDisplay;
    }

    @Override
    public void setBorderRadius(int border) {
        this.borderRadius = border;
        elevation.setBorderRadius(borderRadius);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = MaterialDrawingUtils.getAliasedGraphics(g);
//---------------------BACKGROUND-----------------------------------
        //Paint MaterialPanel background
        paintElevation(g2);
        g2.translate(MaterialShadow.OFFSET_LEFT, MaterialShadow.OFFSET_TOP);

        final int offset_lr = MaterialShadow.OFFSET_LEFT + MaterialShadow.OFFSET_RIGHT;
        final int offset_td = MaterialShadow.OFFSET_TOP + MaterialShadow.OFFSET_BOTTOM;
        g2.setColor(backTranslucid());
        g2.fill(new RoundRectangle2D.Float(0, 0, getWidth() - offset_lr, getHeight() - offset_td, borderRadius * 2, borderRadius * 2));
        g2.setClip(null);

        g2.translate(-MaterialShadow.OFFSET_LEFT, -MaterialShadow.OFFSET_TOP);
//---------------------TEXT-----------------------------------
        g2.setColor(getForeground());
        g2.setFont(getFont());

        FontMetrics fm = g2.getFontMetrics();
        int xText = (getWidth() - fm.stringWidth(textDisplay)) / 2;
        int yText = getHeight() / 2 + fm.getAscent() / 2 - 5;
        g2.drawString(textDisplay, xText, yText);
    }

    private Color backTranslucid() {
        int alpha = (int) (255 * OPACITY);
        return new Color(getBackground().getRed(), getBackground().getGreen(), getBackground().getBlue(), alpha);
    }
}
