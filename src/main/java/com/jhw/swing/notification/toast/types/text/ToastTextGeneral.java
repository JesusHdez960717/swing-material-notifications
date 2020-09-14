package com.jhw.swing.notification.toast.types.text;

import com.jhw.swing.notification.toast.DialogToast;
import com.jhw.swing.notification.NotificationBuilder;
import java.awt.Color;
import java.awt.Font;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public class ToastTextGeneral extends DialogToast {

    public static ToastTextGeneral from(NotificationBuilder.builder builder) {
        return new ToastTextGeneral(builder.delaySeconds, builder.text, builder.textFont, builder.color, builder.location);
    }

    public ToastTextGeneral(int duration, String text, Font textFont, Color color, int location) {
        super(duration, new TextToast(text, textFont, color), location);
    }

}
