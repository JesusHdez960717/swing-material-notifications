package com.root101.swing.notification.fade;

import com.root101.swing.material.components.button.MaterialButtonsFactory;
import com.root101.swing.material.components.container.panel._MaterialPanelComponent;
import com.root101.swing.material.components.labels.MaterialLabel;
import com.root101.swing.material.components.labels.MaterialLabelsFactory;
import com.root101.swing.material.components.textarea.ContentArea;
import com.root101.swing.material.components.textarea.MaterialTextAreaFactory;
import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;
import com.root101.swing.material.standards.MaterialIcons;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
@Deprecated
public class NotificationFadePanel extends _MaterialPanelComponent {

    public NotificationFadePanel(String header, Font headerFont, String text, Font textFont, ImageIcon icon, Color color) {
        initComponents();

        this.setBackground(color);
        labelIcon.setBackground(color);
        buttonClose.setBackground(color);
        contentArea.backgroundColor(color);
        contentArea.header(header).headerFont(headerFont);
        contentArea.text(text).textFont(textFont);
        setIcon(icon);
    }

    private void setIcon(ImageIcon icon) {
        labelIcon.setBackground(contentArea.getBackground());
        labelIcon.setIcon(icon);
    }

    private void initComponents() {
        contentArea = MaterialTextAreaFactory.buildContentArea();
        labelIcon = MaterialLabelsFactory.build();

        buttonClose = MaterialButtonsFactory.buildIconTransparent();
        buttonClose.setIcon(MaterialIcons.CLOSE.deriveIcon(18f));

        this.setLayout(new BorderLayout());
        this.add(labelIcon, BorderLayout.WEST);
        this.add(contentArea, BorderLayout.CENTER);//especificar center xq el add nada mas es un set
        this.add(buttonClose, BorderLayout.EAST);

        //this.setPreferredSize(new Dimension(50, 50));
    }

    private JButton buttonClose;
    private ContentArea contentArea;
    private MaterialLabel labelIcon;

    public void addCloseActionListener(ActionListener l) {
        buttonClose.addActionListener(l);
    }
}
