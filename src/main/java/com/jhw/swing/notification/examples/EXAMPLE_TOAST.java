/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jhw.swing.notification.examples;

import com.jhw.swing.material.components.container.layout.VerticalLayoutContainer;
import com.jhw.swing.material.components.textfield.MaterialFormatedTextField;
import com.jhw.swing.material.components.textfield.MaterialTextAreaFactory;
import com.jhw.swing.material.components.textfield.MaterialTextField;
import com.jhw.swing.material.standards.MaterialColors;
import com.jhw.swing.material.standards.MaterialIcons;
import com.jhw.swing.notification.NotificationBuilder;
import com.jhw.swing.notification.fade.NotificationFadeDialogGeneral;
import com.jhw.swing.notification.toast.types.notification.DialogNotificationToastGeneral;
import com.jhw.swing.notification.toast.types.text.DialogTextToastGeneral;
import com.jhw.swing.ui.MaterialLookAndFeel;
import com.jhw.utils.formateer.MoneyFormateer;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.UIManager;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public class EXAMPLE_TOAST extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame
     */
    public EXAMPLE_TOAST() {
        initComponents();

        jPanel1.setLayout(new BorderLayout());
        jPanel1.setBackground(MaterialColors.REDA_200);

        VerticalLayoutContainer.builder vlc = VerticalLayoutContainer.builder();
        vlc.add(new JButton(new AbstractAction("toast natification") {
            @Override
            public void actionPerformed(ActionEvent e) {
                DialogNotificationToastGeneral.from(NotificationBuilder.builder().header("buajajaja").text("texting texting hua hua hua").color(MaterialColors.PURPLE_400).icon(MaterialIcons.BACKUP));
            }
        }));
        vlc.add(new JButton(new AbstractAction("toast text") {
            @Override
            public void actionPerformed(ActionEvent e) {
                DialogTextToastGeneral.from(NotificationBuilder.builder().text("texting texting hua hua hua").color(MaterialColors.GREY_900).icon(MaterialIcons.PALETTE));
            }
        }));
        jPanel1.add(vlc.build());

    }

    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        this.setContentPane(jPanel1);

        pack();

        this.setSize(new Dimension(500, 500));
        this.setLocationRelativeTo(null);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(new MaterialLookAndFeel());
        } catch (Exception e) {
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EXAMPLE_TOAST().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
