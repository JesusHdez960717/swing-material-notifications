package com.jhw.swing.notification.toast;

import com.jhw.swing.material.components.container.panel._PanelTransparent;
import java.awt.BorderLayout;
import java.awt.Dimension;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public class ToastPanelBack extends _PanelTransparent {

    public ToastPanelBack() {
        initComponents();
    }

    ToastPanelBack(int duration) {
        initComponents();
        toastDisplayer.setDelayUp(duration);
    }

    private void initComponents() {

        toastDisplayer = new com.jhw.swing.notification.toast.ToastDisplayer();

        this.setLayout(new BorderLayout());
        this.add(toastDisplayer);
        this.setPreferredSize(new Dimension(150, 150));
    }

    private com.jhw.swing.notification.toast.ToastDisplayer toastDisplayer;

    public void displayToast(ToastComponent toast) {
        toastDisplayer.display(toast);
    }

    public int getTotalTime() {
        return toastDisplayer.getTotalTime();
    }

    public void dispose() {
        toastDisplayer.dispose();
    }

}
