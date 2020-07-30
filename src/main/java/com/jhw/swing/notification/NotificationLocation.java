package com.jhw.swing.notification;

import com.jhw.swing.util.Utils;
import java.awt.Component;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public class NotificationLocation {

    public static final int DOWN_LEFT = 0;
    public static final int DOWN_CENTER = 1;
    public static final int DOWN_RIGHT = 2;

    public static int getXPosition(Component component, int location) {
        switch (location) {
            case DOWN_LEFT:
                return 0;
            case DOWN_RIGHT:
                return (int) (Utils.getScreenSize().getWidth() - component.getSize().getWidth());
            default:
                return (int) (Utils.getScreenSize().getWidth() - component.getSize().getWidth()) / 2;
        }
    }

}
