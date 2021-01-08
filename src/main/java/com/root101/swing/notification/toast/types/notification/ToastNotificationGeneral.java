/*
 * Copyright 2021 Root101 (jhernandezb96@gmail.com, +53-5-426-8660).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Or read it directly from LICENCE.txt file at the root of this project.
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.root101.swing.notification.toast.types.notification;

import com.root101.swing.notification.toast.DialogToast;
import java.awt.Color;
import javax.swing.ImageIcon;
import com.root101.swing.notification.NotificationBuilder;
import java.awt.Font;

/**
 *
 * @author Root101 (jhernandezb96@gmail.com, +53-5-426-8660)
 * @author JesusHdezWaterloo@Github
 */
public class ToastNotificationGeneral extends DialogToast {

    public static ToastNotificationGeneral from(NotificationBuilder.builder builder) {
        return new ToastNotificationGeneral(builder.delaySeconds, builder.header, builder.headerFont, builder.text, builder.textFont, builder.icon, builder.color, builder.location);
    }

    public ToastNotificationGeneral(int delaySeconds, String header, Font headerFont, String text, Font textFont, ImageIcon icon, Color color, int location) {
        super(delaySeconds, new NotificationToast(header, headerFont, text, textFont, color, icon), location);
    }

}
