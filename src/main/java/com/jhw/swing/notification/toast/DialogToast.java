package com.jhw.swing.notification.toast;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.jhw.swing.material.standards.MaterialColors;

/**
 * Dialogo vacio que muestra un panel.<br/>
 * Si NO tiene titulo se undecora.
 *
 * Ejemplo: new DialogToast("123", new EmptyPanel());
 *
 * @author Jesús Hernandez Barrios (jhernandezb96@gmail.com) 26/02/2020 00:08
 */
public class DialogToast extends JDialog {

    private final ToastPanelBack basePanel;

    private final ToastComponent toast;

    private boolean up = false;

    private Timer close;
    private Timer upTrue;
    private Timer upFalse;
    private Timer click;

    private final int duration;

    private ActionListener action;

    public DialogToast(int duration, ToastComponent toast) {
        super();
        this.toast = toast;
        this.duration = 1000 * duration;
        basePanel = new ToastPanelBack(this.duration);

        this.setUndecorated(true);
        this.setBackground(MaterialColors.TRANSPARENT);

        this.setLayout(new GridLayout(1, 1));
        this.add(basePanel);

        this.setSize(toast.getSize());
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        this.setVisible(true);
        basePanel.displayToast(this.toast);

        DialogToast actual = this;

        //espera que se acabe el tiempo y cierra el dialog
        close = new javax.swing.Timer(2 * ToastDisplayer.DURATION + this.duration + 10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actual.dispose();
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
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                //si se da un click, que sea el click izquierdo y esta arriba todavia, entonces lo baja
                if (evt.getButton() == 1 && evt.getClickCount() == 1 && up) {
                    basePanel.dispose();

                    click = new javax.swing.Timer(ToastDisplayer.DURATION + 10, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            actual.dispose();
                        }
                    });
                    click.start();
                }
            }
        });

    }

    protected void setActionListenerClose(ActionListener action) {
        this.action = action;
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

        if (action != null) {
            action.actionPerformed(null);
        }
    }

    public JPanel getBasePanel() {
        return basePanel;
    }

}
