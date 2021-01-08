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

import com.root101.swing.util.Utils;
import java.awt.Component;

/**
 *
 * @author Root101 (jhernandezb96@gmail.com, +53-5-426-8660)
 * @author JesusHdezWaterloo@Github
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
            case DOWN_CENTER:
                return (int) (Utils.getScreenSize().getWidth() - component.getSize().getWidth()) / 2;
            default:
                throw new IndexOutOfBoundsException("The location isn't supported at this moment");
        }
    }

    public static int[] locations() {
        return new int[]{DOWN_LEFT, DOWN_CENTER, DOWN_RIGHT};
    }
}
