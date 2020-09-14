package com.jhw.swing.notification.toast.types.text;

import com.jhw.swing.notification.NotificationLocation;
import com.jhw.swing.notification.toast.DialogToast;
import static com.jhw.swing.notification.toast.ToastDisplayer.DURATION;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import org.jdesktop.core.animation.timing.Animator;
import org.jdesktop.core.animation.timing.interpolators.SplineInterpolator;
import com.jhw.personalization.core.domain.Personalization;
import com.jhw.personalization.services.PersonalizationHandler;
import com.jhw.swing.notification.NotificationBuilder;
import com.jhw.swing.notification.toast.types.notification.DialogNotificationToastGeneral;
import com.jhw.swing.util.SafePropertySetter;
import com.jhw.swing.util.Utils;
import java.awt.Color;
import java.awt.Font;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public class DialogTextToastGeneral extends DialogToast {

    public static DialogTextToastGeneral from(NotificationBuilder.builder builder) {
        return new DialogTextToastGeneral(builder.delaySeconds, builder.text, builder.textFont, builder.color, builder.location);
    }
    public static final int DISTANCE = 65;

    private Animator anim;

    private static final ArrayList<DialogTextToastGeneral> NOTIF = new ArrayList<>();

    private int nextY = 0;

    public DialogTextToastGeneral(int duration, String text, Font textFont, Color color, int location) {
        super(duration, new TextToast(text, textFont, color));

        nextY = getYPosition() - (int) super.getSize().getHeight();
        this.setLocation(NotificationLocation.getXPosition(this, location), nextY);

        this.setActionListenerClose(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeNotif();
            }
        });

        NOTIF.add(this);

        this.setVisible(true);
    }

    private void closeNotif() {
        NOTIF.remove(this);
        moveAll(this);
        if (anim != null) {
            anim.cancel();
        }
    }

    private int getYPosition() {
        int pos = Toolkit.getDefaultToolkit().getScreenSize().height - DISTANCE;
        for (DialogTextToastGeneral act : NOTIF) {
            pos -= act.getHeight();
        }
        return pos;
    }

    private static void moveAll(DialogTextToastGeneral actual) {
        for (DialogTextToastGeneral act : NOTIF) {
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
                .setDuration(DURATION, TimeUnit.MILLISECONDS)
                .setInterpolator(new SplineInterpolator(0.1, 0.3, 0.45, 1))
                .addTarget(SafePropertySetter.getTarget(new SafePropertySetter.Setter<Integer>() {
                    @Override
                    public void setValue(Integer value) {
                        if (value != null) {
                            setLocation(getLocation().x, value);
                        }
                    }
                }, getLocation().y, nextY)).build();
        anim.start();
    }

}
