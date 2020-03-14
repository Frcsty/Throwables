package me.frosty.throwables.commands.subs;

import com.codeitforyou.lib.api.command.Command;
import com.codeitforyou.lib.api.general.StringUtil;
import com.codeitforyou.lib.api.item.ItemBuilder;
import com.codeitforyou.lib.api.xseries.XMaterial;
import me.frosty.throwables.Throwables;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public class GiveCommand
{

	@Command(permission = "throwables.command.give", aliases = {"give"}, usage = "give [type] <player> (amount)", requiredArgs = 3)
	public static void execute(final CommandSender sender, final Throwables plugin, final String[] args)
	{

		final Player target = Bukkit.getPlayerExact(args[1]);
		final String type = args[0];

		final FileConfiguration config = plugin.getConfig();
		final ConfigurationSection section = config.getConfigurationSection("throwables");

		if (target == null)
		{
			sender.sendMessage(StringUtil.translate(config.getString("messages.invalidPlayer")));
			return;
		}

		if (section == null)
		{
			sender.sendMessage(StringUtil.translate(config.getString("messages.emptySection")));
			return;
		}

		int amount = 1;
		if (args[2] != null)
		{
			amount = Integer.parseInt(args[2]);
		}

		if (config.getString("throwables." + type + ".material") == null)
		{
			sender.sendMessage(StringUtil.translate(config.getString("messages.invalidThrowable")));
			return;
		}

		final String material = config.getString("throwables." + type + ".material").toUpperCase();
		final String display_name = config.getString("throwables." + type + ".display_name");
		final List<String> lore = config.getStringList("throwables." + type + ".lore");

		ItemBuilder itemBuilder = new ItemBuilder(XMaterial.valueOf(material).parseMaterial())
				.withName(display_name)
				.withLore(lore)
				.withNBTString("type", type);

		for (int i = 1; i <= amount; i++)
		{
			target.getInventory().addItem(itemBuilder.getItem());
		}
	}

}
