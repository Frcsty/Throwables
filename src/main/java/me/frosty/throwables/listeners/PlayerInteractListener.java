package me.frosty.throwables.listeners;

import com.codeitforyou.lib.api.item.ItemUtil;
import me.frosty.throwables.Throwables;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class PlayerInteractListener implements Listener
{

	private final Throwables plugin;
	private       Item       drop;

	public PlayerInteractListener(final Throwables plugin)
	{
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		final ItemStack item = event.getItem();
		final Player player = event.getPlayer();
		final Action action = event.getAction();
		final Location location = player.getLocation();
		final FileConfiguration config = plugin.getConfig();
		final ConfigurationSection section = config.getConfigurationSection("throwables");
		final String type = ItemUtil.getNBTString(item, "type");

		if (item == null)
		{
			return;
		}

		if (section == null)
		{
			return;
		}

		if (config.getString("throwables." + type + ".material") == null)
		{
			return;
		}

		if (action != Action.valueOf(config.getString("throwables." + type + ".click_action").toUpperCase()))
		{
			return;
		}

		if (location.getWorld() == null)
		{
			return;
		}

		List<String> actions = config.getStringList("throwables." + type + ".actions");
		location.setY(location.getY() + 1.2f);
		drop = location.getWorld().dropItemNaturally(location, item);

		double velocity = 1;

		if (config.getBoolean("throwable.enableCharge"))
		{
			if (player.isSneaking())
			{
				velocity = 2;
			}
		}

		drop.setVelocity(location.getDirection().multiply(velocity));

		if (item.getAmount() > 1)
		{
			item.setAmount(item.getAmount() - 1);
		}
		else
		{
			item.setType(Material.AIR);
		}

		plugin.getActionsManager().getActionManager().runActions(player, actions);
	}

	public final Item getDrop()
	{
		return drop;
	}

}
