package me.frosty.throwables.util;

import com.codeitforyou.lib.api.xseries.XMaterial;
import me.frosty.throwables.Throwables;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Actions
{

    private final Throwables plugin;

    public Actions(final Throwables plugin)
    {
        this.plugin = plugin;
    }

    public void executeCustomAction(final Player player, final String data)
    {
        if (data == null)
        {
            return;
        }

        final Location location = player.getLocation();
        final World world = location.getWorld();
        final Item drop = plugin.getDrop();
        final DataParser parser = new DataParser(data);

        if (world == null)
        {
            return;
        }

        if (drop == null)
        {
            return;
        }

        drop.setPickupDelay(parser.getTimer() + 20);
        drop.setInvulnerable(true);
        drop.setCustomNameVisible(true);

        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                switch (parser.getType())
                {
                    case "LIGHTNING":
                        world.strikeLightning(drop.getLocation());
                        break;
                    case "EXPLOSION":
                        world.createExplosion(drop.getLocation(), parser.getModifier());
                        break;
                    case "SPAWN_ENTITY":
                        world.spawnEntity(drop.getLocation(), EntityType.valueOf(parser.getExtensiveModifier()));
                        break;
                    case "TELEPORT":
                        player.teleport(drop.getLocation());
                        break;
                    case "CHANGE_BLOCK":
                        world.getBlockAt(drop.getLocation()).setType(XMaterial.valueOf(parser.getExtensiveModifier()).parseMaterial());
                        break;
                }
                drop.remove();
            }
        }.runTaskLater(plugin, parser.getTimer() * 20);

        for (int i = parser.getTimer(); i >= 0; --i)
        {
            final int time = i;
            new BukkitRunnable()
            {
                @Override
                public void run()
                {
                    drop.setCustomName(parser.getText().replace("%timer%", String.valueOf(time)));
                }
            }.runTaskLaterAsynchronously(plugin, (parser.getTimer() - i) * 20);
        }
    }

}