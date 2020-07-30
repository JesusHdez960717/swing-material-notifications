package com.jhw.swing.notification.fade;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import org.jdesktop.core.animation.timing.Animator;
import org.jdesktop.core.animation.timing.TimingTargetAdapter;
import org.jdesktop.core.animation.timing.interpolators.SplineInterpolator;
import com.jhw.swing.util.SafePropertySetter;
import com.jhw.swing.material.standards.MaterialColors;
import com.jhw.swing.material.standards.MaterialFontRoboto;
import com.jhw.swing.notification.NotificationLocation;
import com.jhw.personalization.core.domain.Personalization;
import com.jhw.personalization.services.PersonalizationHandler;
import com.jhw.swing.util.Utils;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public class NotificationDialogGeneral extends JDialog {

    public static final int DURATION_FADE = 150;
    public static final int DURATION_MOVE = 250;

    private int delay; //milliseconds
    private ImageIcon icon;
    private Color backgroundColor;

    private NotificationPanel basePanel;

    public static final Font DEFAULT_HEADER_FONT = MaterialFontRoboto.BOLD.deriveFont(18f);
    public static final Font DEFAULT_TEXT_FONT = MaterialFontRoboto.BOLD.deriveFont(16f);

    public static final int DISTANCE = 65;

    private Animator anim;

    private static final ArrayList<NotificationDialogGeneral> NOTIF = new ArrayList<>();

    private int nextY = 0;

    public NotificationDialogGeneral(int delaySeconds, String text, ImageIcon icon, Color color) {
        this(delaySeconds, "", text, icon, color);
    }

    public NotificationDialogGeneral(int delaySeconds, String header, String text, ImageIcon icon, Color color) {
        this(delaySeconds, header, DEFAULT_HEADER_FONT, text, DEFAULT_TEXT_FONT, icon, color, NotificationLocation.DOWN_RIGHT);
    }

    public NotificationDialogGeneral(int delaySeconds, String header, Font headerFont, String text, Font textFont, ImageIcon icon, Color color, int location) {
        super();
        this.delay = delaySeconds * 1000;
        this.icon = icon;
        this.backgroundColor = color;

        this.basePanel = new NotificationPanel(header, headerFont, text, textFont, this.icon, this.backgroundColor);
        basePanel.getMaterialIconButtonTranspRect1().addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeNotif();
            }
        });

        this.setLayout(new GridLayout(1, 1));
        this.add(basePanel);

        this.setSize(basePanel.getPreferredSize());
        this.setUndecorated(true);
        this.setBackground(MaterialColors.TRANSPARENT);
        this.setResizable(false);

        nextY = getYPosition() - (int) super.getSize().getHeight();
        this.setLocation(NotificationLocation.getXPosition(this, location), nextY);

        NotificationDialogGeneral act = this;

        act.setOpacity(0f);
        new Animator.Builder(Utils.getSwingTimerTimingSource())
                .setDuration(DURATION_FADE, TimeUnit.MILLISECONDS)
                .setInterpolator(new SplineInterpolator(0.1, 0.3, 0.45, 1))
                .addTarget(SafePropertySetter.getTarget(new SafePropertySetter.Setter<Float>() {
                    @Override
                    public void setValue(Float value) {
                        if (value != null && basePanel != null) {
                            act.setOpacity(value);
                            repaint();
                        }
                    }
                }, 0f, 1f))
                .addTarget(new TimingTargetAdapter() {
                    @Override
                    public void end(Animator source) {
                        act.setOpacity(1f);
                    }
                })
                .build().start();

        new Animator.Builder(Utils.getSwingTimerTimingSource())
                .setStartDelay(delay + DURATION_FADE, TimeUnit.MILLISECONDS)
                .setDuration(DURATION_FADE, TimeUnit.MILLISECONDS)
                .setInterpolator(new SplineInterpolator(0.1, 0.3, 0.45, 1))
                .addTarget(SafePropertySetter.getTarget(new SafePropertySetter.Setter<Float>() {
                    @Override
                    public void setValue(Float value) {
                        if (value != null && basePanel != null) {
                            act.setOpacity(value);
                            repaint();
                        }
                    }
                }, 1f, 0f))
                .addTarget(new TimingTargetAdapter() {
                    @Override
                    public void end(Animator source) {
                        act.setOpacity(0f);
                        closeNotif();
                    }
                })
                .build().start();

        NOTIF.add(this);

        this.setVisible(true);
    }

    private void closeNotif() {
        NOTIF.remove(this);
        moveAll(this);
        if (anim != null) {
            anim.cancel();
        }
        this.dispose();
    }

    private int getYPosition() {
        int pos = Toolkit.getDefaultToolkit().getScreenSize().height - DISTANCE;
        for (NotificationDialogGeneral act : NOTIF) {
            pos -= act.getHeight();
        }
        return pos;
    }

    private static void moveAll(NotificationDialogGeneral actual) {
        for (NotificationDialogGeneral act : NOTIF) {
            if (act.getLocation().getY() < actual.getLocation().getY()) {
                act.moveAmount((int) actual.getSize().getHeight());
            }
        }
    }

    private void moveAmount(int y) {
        nextY += y;
        if (anim != null) {
            anim.cancel();
        }
        if (PersonalizationHandler.getBoolean(Personalization.KEY_USE_ANIMATIONS_NOTIFICATIONS)) {
            doMoveAnimated(nextY);
        } else {
            setLocation(getLocation().x, nextY);//mantiene x y mueve y
        }
    }

    public int getDelay() {
        return delay;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public String getHeader() {
        return basePanel.getContentArea().getHeader();
    }

    public void setHeader(String header) {
        basePanel.getContentArea().setHeader(header);
    }

    public Font getHeaderFont() {
        return basePanel.getContentArea().getHeaderFont();
    }

    public void setHeaderFont(Font headerFont) {
        basePanel.getContentArea().setHeaderFont(headerFont);
    }

    public String getText() {
        return basePanel.getContentArea().getText();
    }

    public void setText(String text) {
        basePanel.getContentArea().setHeader(text);
    }

    public Font getTextFont() {
        return basePanel.getContentArea().getTextFont();
    }

    public void setTextFont(Font textFont) {
        basePanel.getContentArea().setTextFont(textFont);
    }

    private void doMoveAnimated(int nextY) {
        anim = new Animator.Builder(Utils.getSwingTimerTimingSource())
                .setDuration(DURATION_MOVE, TimeUnit.MILLISECONDS)
                .setInterpolator(new SplineInterpolator(0.1, 0.3, 0.45, 1))
                .addTarget(SafePropertySetter.getTarget(new SafePropertySetter.Setter<Integer>() {
                    @Override
                    public void setValue(Integer value) {
                        if (value != null) {
                            setLocation(getLocation().x, value);//mantiene x y mueve y
                        }
                    }
                }, getLocation().y, nextY)).build();
        anim.start();
    }

}
