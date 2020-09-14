/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jhw.swing.notification;

import com.jhw.swing.material.standards.MaterialColors;
import com.jhw.swing.material.standards.MaterialFontRoboto;
import com.jhw.swing.material.standards.MaterialIcons;
import com.jhw.swing.notification.fade.DialogFade;
import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public class NotificationBuilder {

    public static builder builder() {
        return new builder();
    }

    public static class builder {

        public int delaySeconds = 3;
        public String header = "header";
        public Font headerFont = MaterialFontRoboto.BOLD.deriveFont(18f);
        public String text = "text";
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
