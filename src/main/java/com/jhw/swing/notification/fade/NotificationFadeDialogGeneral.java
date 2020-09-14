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
import com.jhw.swing.material.standards.MaterialIcons;
import com.jhw.swing.util.Utils;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public class NotificationFadeDialogGeneral extends JDialog {

    public static final int DURATION_FADE = 150;
    public static final int DURATION_MOVE = 250;

    private NotificationFadePanel basePanel;

    public static final int DISTANCE = 65;

    private Animator anim;

    private static final ArrayList<NotificationFadeDialogGeneral> NOTIF = new ArrayList<>();

    private int nextY = 0;

    public NotificationFadeDialogGeneral(int delaySeconds, String header, Font headerFont, String text, Font textFont, ImageIcon icon, Color color, int location) {
        super();
        int delay = delaySeconds * 1000;

        this.basePanel = new NotificationFadePanel(header, headerFont, text, textFont, icon, color);
        basePanel.addCloseActionListener((java.awt.event.ActionEvent evt) -> {
            closeNotif();
        });

        this.setLayout(new GridLayout(1, 1));
        this.add(basePanel);

        this.setSize(basePanel.getPreferredSize());
        this.setUndecorated(true);
        this.setBackground(MaterialColors.TRANSPARENT);
        this.setResizable(false);

        nextY = getYPosition() - (int) super.getSize().getHeight();
        this.setLocation(NotificationLocation.getXPosition(this, location), nextY);

        NotificationFadeDialogGeneral act = this;

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
        for (NotificationFadeDialogGeneral act : NOTIF) {
            pos -= act.getHeight();
        }
        return pos;
    }

    private static void moveAll(NotificationFadeDialogGeneral actual) {
        for (NotificationFadeDialogGeneral act : NOTIF) {
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

    public static builder builder() {
        return new builder();
    }

    public static class builder {

        private int delaySeconds = 3;
        private String header = "header";
        private Font headerFont = MaterialFontRoboto.BOLD.deriveFont(18f);
        private String text = "text";
        private Font textFont = MaterialFontRoboto.BOLD.deriveFont(16f);
        private ImageIcon icon = MaterialIcons.NOTIFICATIONS;
        private Color color = MaterialColors.WHITE;
        private int location = NotificationLocation.DOWN_RIGHT;

        public builder delaySeconds(int delaySeconds) {
            this.delaySeconds = delaySeconds;
            return this;
        }

        public builder header(String header) {
            this.header = header;
            return this;
        }

        public builder headerFont(Font headerFont) {
            this.headerFont = headerFont;
            return this;
        }

        public builder text(String text) {
            this.text = text;
            return this;
        }

        public builder textFont(Font textFont) {
            this.textFont = textFont;
            return this;
        }

        public builder icon(ImageIcon icon) {
            this.icon = icon;
            return this;
        }

        public builder color(Color color) {
            this.color = color;
            return this;
        }

        public builder location(int location) {
            this.location = location;
            return this;
        }

        public NotificationFadeDialogGeneral build() {
            return new NotificationFadeDialogGeneral(delaySeconds, header, headerFont, text, textFont, icon, color, location);
        }
    }

}
