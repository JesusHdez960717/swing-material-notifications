/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jhw.swing.notification;

import com.jhw.swing.notification.toast.DialogToast;
import com.jhw.swing.notification.toast.types.notification.ToastNotificationGeneral;
import com.jhw.swing.notification.toast.types.text.ToastTextGeneral;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public class NotificationFactory {

    public static DialogToast buildNotificationTOAST(NotificationBuilder.builder builder) {
        return ToastNotificationGeneral.from(builder);
    }

    public static DialogToast buildTextTOAST(NotificationBuilder.builder builder) {
        return ToastTextGeneral.from(builder);
    }
}
