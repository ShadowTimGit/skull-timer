package com.skulltimer;

import com.google.inject.Provides;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.Player;
import net.runelite.api.SkullIcon;
import net.runelite.api.events.GameTick;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@PluginDescriptor(
        name = "Skull Timer",
        description = "Tracks how long until your skull disappears",
        tags = {"skull", "pvp", "timer"}
)
public class SkullTimerPlugin extends Plugin
{
    private static final int SKULL_TICKS = 1200; // 20 minutes (game ticks)

    @Inject
    private Client client;

    @Inject
    private SkullTimerOverlay overlay;

    private int ticksRemaining = -1;

    @Override
    protected void startUp() throws Exception
    {
        ticksRemaining = -1;
    }

    @Override
    protected void shutDown() throws Exception
    {
        ticksRemaining = -1;
    }

    @Subscribe
    public void onGameTick(GameTick tick)
    {
        Player player = client.getLocalPlayer();
        if (player == null) return;

        SkullIcon skull = player.getSkullIcon();

        if (skull != null)
        {
            // If newly skulled
            if (ticksRemaining == -1 || ticksRemaining <= 0)
            {
                ticksRemaining = SKULL_TICKS;
            }
            else
            {
                ticksRemaining--;
            }
        }
        else
        {
            // Skull gone
            ticksRemaining = -1;
        }
    }

    public int getTicksRemaining()
    {
        return ticksRemaining;
    }

    @Provides
    SkullTimerConfig provideConfig(ConfigManager configManager)
    {
        return configManager.getConfig(SkullTimerConfig.class);
    }
}
