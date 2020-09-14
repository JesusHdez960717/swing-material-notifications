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
public class ToastNotificationGeneral extends DialogToast {

    public static ToastNotificationGeneral from(NotificationBuilder.builder builder) {
        return new ToastNotificationGeneral(builder.delaySeconds, builder.header, builder.headerFont, builder.text, builder.textFont, builder.icon, builder.color, builder.location);
    }

    public ToastNotificationGeneral(int delaySeconds, String header, Font headerFont, String text, Font textFont, ImageIcon icon, Color color, int location) {
        super(delaySeconds, new NotificationToast(header, headerFont, text, textFont, color, icon), location);
    }

}
