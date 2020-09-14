package com.jhw.swing.notification.toast.types.notification;

import com.jhw.swing.notification.toast.DialogToast;
import java.awt.Color;
import javax.swing.ImageIcon;
import com.jhw.swing.notification.NotificationBuilder;
import java.awt.Font;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public class DialogNotificationToastGeneral extends DialogToast {

    public static DialogNotificationToastGeneral from(NotificationBuilder.builder builder) {
        return new DialogNotificationToastGeneral(builder.delaySeconds, builder.header, builder.headerFont, builder.text, builder.textFont, builder.icon, builder.color, builder.location);
    }

    public DialogNotificationToastGeneral(int delaySeconds, String header, Font headerFont, String text, Font textFont, ImageIcon icon, Color color, int location) {
        super(delaySeconds, new NotificationToast(header, headerFont, text, textFont, color, icon), location);
    }

}
