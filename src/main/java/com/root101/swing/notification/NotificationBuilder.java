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
package com.root101.swing.notification;

import com.root101.swing.material.standards.MaterialColors;
import com.root101.swing.material.standards.MaterialFontRoboto;
import com.root101.swing.material.standards.MaterialIcons;
import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;

/**
 *
 * @author Root101 (jhernandezb96@gmail.com, +53-5-426-8660)
 * @author JesusHdezWaterloo@Github
 */
public class NotificationBuilder {

    public static builder builder() {
        return new builder();
    }

    public static class builder {

        public int delaySeconds = 3;
        public String header = "";
        public Font headerFont = MaterialFontRoboto.BOLD.deriveFont(18f);
        public String text = "";
        public Font textFont = MaterialFontRoboto.BOLD.deriveFont(16f);
        public ImageIcon icon = MaterialIcons.NOTIFICATIONS;
        public Color color = MaterialColors.WHITE;
        public int location = NotificationLocation.DOWN_RIGHT;

        public builder delaySeconds(int delaySeconds) {
            this.delaySeconds = delaySeconds;
            return this;
        }

        public builder header(String header) {
            this.header = header;
            return this;
        }

        public builder headerFont(Font headerFont) {
            this.headerFont = headerFont;
            return this;
        }

        public builder text(String text) {
            this.text = text;
            return this;
        }

        public builder textFont(Font textFont) {
            this.textFont = textFont;
            return this;
        }

        public builder icon(ImageIcon icon) {
            this.icon = icon;
            return this;
        }

        public builder color(Color color) {
            this.color = color;
            return this;
        }

        public builder location(int location) {
            this.location = location;
            return this;
        }
    }

}
