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
package com.root101.swing.notification.toast;

import com.root101.swing.material.standards.MaterialFontRoboto;
import javax.swing.*;
import java.awt.*;
import com.root101.swing.util.MaterialDrawingUtils;

/**
 *
 * @author Root101 (jhernandezb96@gmail.com, +53-5-426-8660)
 * @author JesusHdezWaterloo@Github
 */
public abstract class ToastComponent extends JComponent {

    public static final Color BACKGROUND = Color.decode("#323232");
    public static final Font FONT = MaterialFontRoboto.REGULAR.deriveFont(14f);
    private double yOffset = Double.POSITIVE_INFINITY;

    public ToastComponent() {
        setOpaque(false);
    }

    void setYOffset(double yOffset) {
        this.yOffset = yOffset;
    }

    public double getyOffset() {
        return yOffset;
    }

    /**
     * Paints this toast.
     *
     * @param g graphics canvas
     */
    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = MaterialDrawingUtils.getAliasedGraphics(g);

        ((Graphics2D) g).translate(0, yOffset);
        super.paint(g2);
    }
}
