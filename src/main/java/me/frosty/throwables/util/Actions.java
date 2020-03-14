package me.frosty.throwables.util;

import com.codeitforyou.lib.api.general.StringUtil;
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

		final String[] arguments = data.split(";");
		final String[] params = arguments[0].split(",");
		final String type = params[0].toUpperCase();
		final int timer = Integer.parseInt(params[1]);

		final Item drop = plugin.getDrop();
		final Location location = player.getLocation();
		final World world = location.getWorld();

		if (world == null)
		{
			return;
		}

		drop.setPickupDelay(timer + 20);
		drop.setInvulnerable(true);
		drop.setCustomNameVisible(true);

		new BukkitRunnable()
		{
			@Override
			public void run()
			{
				switch (type)
				{
					case "LIGHTNING":
						world.strikeLightning(drop.getLocation());
						break;
					case "EXPLOSION":
						final int power = Integer.parseInt(params[2]);
						world.createExplosion(drop.getLocation(), power);
						break;
					case "SPAWN_ENTITY":
						final EntityType entity = EntityType.valueOf(arguments[2]);
						world.spawnEntity(drop.getLocation(), entity);
						break;
					case "TELEPORT":
						player.teleport(drop.getLocation());
						break;
				}
				drop.remove();
			}
		}.runTaskLaterAsynchronously(plugin, timer * 20);

		for (int i = timer; i >= 0; --i)
		{
			final int time = i;
			new BukkitRunnable()
			{
				@Override
				public void run()
				{
					drop.setCustomName(StringUtil.translate(arguments[1].replace("%timer%", String.valueOf(time))));
				}
			}.runTaskLaterAsynchronously(plugin, (timer - i) * 20);
		}
	}

}
