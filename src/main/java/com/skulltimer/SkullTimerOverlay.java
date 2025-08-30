package com.skulltimer;

import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;

import javax.inject.Inject;
import java.awt.*;

public class SkullTimerOverlay extends Overlay
{
    private final SkullTimerPlugin plugin;

    @Inject
    public SkullTimerOverlay(SkullTimerPlugin plugin)
    {
        this.plugin = plugin;
        setPosition(OverlayPosition.TOP_LEFT);
        setLayer(OverlayLayer.ABOVE_SCENE);
    }

    @Override
    public Dimension render(Graphics2D g)
    {
        int ticks = plugin.getTicksRemaining();
        if (ticks > 0)
        {
            int seconds = (ticks * 600) / 1000; // convert ticks (0.6s) to seconds
            int minutes = seconds / 60;
            int secs = seconds % 60;

            g.setColor(Color.WHITE);
            g.drawString(String.format("Skull: %d:%02d", minutes, secs), 10, 50);
        }
        return null;
    }
}
