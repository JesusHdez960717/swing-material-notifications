package com.jhw.swing.notification.toast;

import com.jhw.personalization.core.domain.Personalization;
import com.jhw.personalization.services.PersonalizationHandler;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.Timer;
import com.jhw.swing.material.standards.MaterialColors;
import com.jhw.swing.notification.NotificationLocation;
import static com.jhw.swing.notification.toast.ToastDisplayer.DURATION;
import static com.jhw.swing.notification.toast.types.text.DialogTextToastGeneral.DISTANCE;
import com.jhw.swing.util.SafePropertySetter;
import com.jhw.swing.util.Utils;
import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.jdesktop.core.animation.timing.Animator;
import org.jdesktop.core.animation.timing.interpolators.SplineInterpolator;

/**
 * Dialogo vacio que muestra un panel.<br/>
 * Si NO tiene titulo se undecora.
 *
 *
 * @author Jesús Hernandez Barrios (jhernandezb96@gmail.com)
 */
public class DialogToast extends JDialog {

    public static DialogToast from(int duration, ToastComponent toast, int location) {
        return new DialogToast(duration, toast, location);
    }
    //control de flujo e interaccion entre las notificaciones
    private Animator anim;//animator para mover cuando la de abajo se cierra
    private int nextY = 0;//y donde va a salir la proxima notificacion 
    private static final Map<Integer, ArrayList<DialogToast>> NOTIFICATIONS = new HashMap<>();//todas las notificaciones en cada posible posicion

    static {
        for (int locations : NotificationLocation.locations()) {
            NOTIFICATIONS.put(locations, new ArrayList<>());
        }
    }

    //distancia desde la barra de inicio hasta la primera notificacion
    public static final int DISTANCE = 65;

    //panel base de la notificacion, un panel por notificacion
    private final ToastPanelBack basePanel;

    //el toast como tal
    private final ToastComponent toast;

    //flag para saber si la notificacion esta arriba
    private boolean up = false;

    //timers para flujo
    private Timer close;
    private Timer upTrue;
    private Timer upFalse;
    private Timer click;

    //timpo de duracion de la notificacion
    private final int duration;

    //localizacion de la notificacion para saber donde ubicarla
    private final int location;

    public DialogToast(int duration, ToastComponent toast, int location) {
        this.toast = toast;
        this.duration = 1000 * duration;
        this.location = location;
        this.basePanel = new ToastPanelBack(this.duration);

        this.setUndecorated(true);
        this.setBackground(MaterialColors.TRANSPARENT);

        this.setLayout(new BorderLayout());
        this.add(basePanel);

        this.setSize(toast.getSize());
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        this.setVisible(true);
        this.basePanel.displayToast(this.toast);

        this.nextY = getYPosition() - (int) super.getSize().getHeight();
        this.setLocation(NotificationLocation.getXPosition(this, location), nextY);

        this.NOTIFICATIONS.get(location).add(this);
        this.startTimers();
    }

    @Override
    public void dispose() {
        if (close != null) {
            close.stop();
            close = null;
        }
        if (click != null) {
            click.stop();
            click = null;
        }
        if (upTrue != null) {
            upTrue.stop();
            upTrue = null;
        }
        if (upFalse != null) {
            upFalse.stop();
            upFalse = null;
        }
        super.dispose();
        closeNotif();
    }

    private void startTimers() {
        //---------------------------------Timers para controlar flujo--------------------------
        //espera que se acabe el tiempo y cierra el dialog
        close = new javax.swing.Timer(2 * ToastDisplayer.DURATION + this.duration + 10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DialogToast.this.dispose();
            }
        });
        close.start();

        //cuando llega arriba activa el flag
        upTrue = new javax.swing.Timer(ToastDisplayer.DURATION - 5, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                up = true;
                upTrue.stop();
            }
        });
        upTrue.start();

        //desactiva el flag cuando empieza a bajar
        upFalse = new javax.swing.Timer(ToastDisplayer.DURATION + this.duration + 5, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                up = false;
                upFalse.stop();
            }
        });
        upFalse.start();

        //al dar click baja si se cumplen las condiciones
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                //si se da un click, que sea el click izquierdo y esta arriba todavia, entonces lo baja
                if (evt.getButton() == 1 && evt.getClickCount() == 1 && up) {
                    basePanel.dispose();

                    click = new javax.swing.Timer(ToastDisplayer.DURATION + 10, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            DialogToast.this.dispose();
                        }
                    });
                    click.start();
                }
            }
        });
    }

// ---------------------------------------- static ----------------------------------------
    /**
     * Cierra la notificacion.
     */
    private void closeNotif() {
        NOTIFICATIONS.get(location).remove(this);//La elimina de la lista
        moveAll(this);//mueve todas las demas en dependencia de esta
        if (anim != null) {//cancela su animacion
            anim.cancel();
        }
    }

    private int getYPosition() {
        int pos = Toolkit.getDefaultToolkit().getScreenSize().height - DISTANCE;
        for (DialogToast act : NOTIFICATIONS.get(location)) {
            pos -= act.getHeight();
        }
        return pos;
    }

    private void moveAll(DialogToast actual) {
        for (DialogToast act : NOTIFICATIONS.get(location)) {
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
