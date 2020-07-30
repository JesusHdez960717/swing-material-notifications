package com.jhw.swing.notification.toast.types.text;

import com.jhw.swing.notification.toast.ToastComponent;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import com.jhw.swing.material.effects.ElevationEffect;
import com.jhw.swing.material.standards.MaterialColors;
import com.jhw.swing.material.standards.MaterialFontRoboto;
import com.jhw.swing.material.standards.MaterialShadow;
import com.jhw.swing.util.MaterialDrawingUtils;
import com.jhw.swing.util.Utils;

/**
 * A toast that contains textDisplay.
 *
 * @see <a
 * href="https://www.google.com/design/spec/components/snackbars-toasts.html">Snackbars
 * and toasts</a>
 */
public class TextToast extends ToastComponent {

    private final static float TRANS = 0.8f;
    private final ElevationEffect elevation;

    private String textDisplay;

    private int borderRadius = 15;

    private Cursor cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

    /**
     * Creates a new toast.
     *
     * @param text the textDisplay of this toast
     */
    public TextToast(String text) {
        super.setCursor(cursor);
        elevation = ElevationEffect.applyTo(this, MaterialShadow.ELEVATION_DEFAULT);
        elevation.setBorderRadius(borderRadius);
        this.setBackground(MaterialColors.GREY_900);
        this.setFont(MaterialFontRoboto.REGULAR.deriveFont(18f));
        setTextDisplay(text);
    }

    public String getTextDisplay() {
        return textDisplay;
    }

    public int getBorderRadius() {
        return borderRadius;
    }

    public Cursor getCursor() {
        return cursor;
    }

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

    public void setElevationLevel(int level) {
        elevation.setLevel(level);
    }

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
        elevation.paint(g2);
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
        int alpha = (int) (255 * TRANS);
        return new Color(getBackground().getRed(), getBackground().getGreen(), getBackground().getBlue(), alpha);
    }
}
