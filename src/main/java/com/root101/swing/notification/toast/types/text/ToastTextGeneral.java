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
package com.root101.swing.notification.toast.types.text;

import com.root101.swing.notification.toast.DialogToast;
import com.root101.swing.notification.NotificationBuilder;
import java.awt.Color;
import java.awt.Font;

/**
 *
 * @author Root101 (jhernandezb96@gmail.com, +53-5-426-8660)
 * @author JesusHdezWaterloo@Github
 */
public class ToastTextGeneral extends DialogToast {

    public static ToastTextGeneral from(NotificationBuilder.builder builder) {
        return new ToastTextGeneral(builder.delaySeconds, builder.text, builder.textFont, builder.color, builder.location);
    }

    public ToastTextGeneral(int duration, String text, Font textFont, Color color, int location) {
        super(duration, new TextToast(text, textFont, color), location);
    }

}
