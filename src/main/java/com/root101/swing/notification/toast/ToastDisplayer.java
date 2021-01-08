package com.root101.swing.notification.toast;

import java.awt.event.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import javax.swing.*;
import org.jdesktop.core.animation.timing.Animator;
import org.jdesktop.core.animation.timing.TimingTargetAdapter;
import org.jdesktop.core.animation.timing.interpolators.SplineInterpolator;
import com.root101.swing.util.SafePropertySetter;
import com.root101.swing.util.Utils;

/**
 * A bar that displays toasts.
 *
 * @see <a
 * href="https://www.google.com/design/spec/components/snackbars-toasts.html">Snackbars
 * and toasts</a>
 */
public class ToastDisplayer extends JComponent {

    public static final int DURATION = 400;

    private final Queue<ToastComponent> toasts = new LinkedList<>();
    private boolean animationRunning = false;
    private int delayUp;

    private Animator anim;
    private ToastComponent lastToast;
    private ComponentListener resizeListener;
    private final int startDelay;

    private final Timer nextToastAuto;

    public ToastDisplayer() {
        this(3 * 1000);
    }

    public ToastDisplayer(int delayUp) {
        this.delayUp = delayUp;
        this.startDelay = DURATION + delayUp;

        setLayout(null);

        nextToastAuto = new javax.swing.Timer(DURATION, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayNextToast();
                nextToastAuto.stop();
            }
        });
        nextToastAuto.start();
    }

    public int getTotalTime() {
        return 2 * DURATION + delayUp;
    }

    public int getDelayUp() {
        return delayUp;
    }

    public void setDelayUp(int delayUp) {
        this.delayUp = delayUp;
    }

    /**
     * Displays the toast or queues it for display if another toast is already
     * displayed.
     *
     * @param toast toast
     */
    public void display(ToastComponent toast) {
        toasts.add(toast);
        if (toasts.size() == 1 && !animationRunning) {
            displayNextToast();
        }
    }

    @Override
    public boolean contains(int x, int y) {
        return animationRunning && super.contains(x, y);
    }

    private synchronized void displayNextToast() {
        if (animationRunning) {
            return;
        }

        lastToast = toasts.poll();
        if (lastToast != null) {
            add(lastToast);

            resizeListener = new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    repaint();
                }
            };
            addComponentListener(resizeListener);

            animationRunning = true;

            animToUp();
            animToDown(startDelay);
        }
    }

    public void dispose() {
        anim.cancel();
        animToDown(0);
    }

    private void animToDown(int delay) {
        //timer to down
        anim = new Animator.Builder(Utils.getSwingTimerTimingSource())
                .setStartDelay(delay, TimeUnit.MILLISECONDS)
                .setDuration(DURATION, TimeUnit.MILLISECONDS)
                .setInterpolator(new SplineInterpolator(0.55, 0, 0.9, 0.7))
                .addTarget(SafePropertySetter.getTarget(new SafePropertySetter.Setter<Integer>() {
                    @Override
                    public void setValue(Integer value) {
                        if (value != null && lastToast != null) {
                            lastToast.setYOffset(value);
                            repaint();
                        }
                    }
                }, 0, getHeight() + 1))
                .addTarget(new TimingTargetAdapter() {
                    @Override
                    public void end(Animator source) {
                        removeComponentListener(resizeListener);
                        remove(lastToast);

                        animationRunning = false;

                        nextToastAuto.start();
                    }
                }).build();
        anim.start();
    }

    private void animToUp() {
        //timer to up
        new Animator.Builder(Utils.getSwingTimerTimingSource())
                .setDuration(DURATION, TimeUnit.MILLISECONDS)
                .setInterpolator(new SplineInterpolator(0.1, 0.3, 0.45, 1))
                .addTarget(SafePropertySetter.getTarget(new SafePropertySetter.Setter<Integer>() {
                    @Override
                    public void setValue(Integer value) {
                        if (value != null && lastToast != null) {
                            lastToast.setYOffset(value);
                            repaint();
                        }
                    }
                }, getHeight(), 0))
                .addTarget(new TimingTargetAdapter() {
                    @Override
                    public void end(Animator source) {
                        lastToast.setYOffset(0);
                    }
                })
                .build().start();
    }
}
