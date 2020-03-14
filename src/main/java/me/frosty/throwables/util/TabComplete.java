package me.frosty.throwables.util;

import me.frosty.throwables.Throwables;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class TabComplete implements TabCompleter
{

	private final Throwables plugin;

	public TabComplete(final Throwables plugin)
	{
		this.plugin = plugin;
	}

	@Override
	public List<String> onTabComplete(CommandSender s, Command cmd, String alias, String[] args)
	{
		if (cmd.getName().equals("throwables"))
		{
			if (args.length > 3)
			{
				return Collections.singletonList("");
			}

			if (args.length == 1)
			{
				return new ArrayList<String>()
				{{
					for (Map.Entry<String, Method> commandPair : plugin.getCommandsManager().getCommandManager().getCommands().entrySet())
					{
						if (commandPair.getKey().toLowerCase().startsWith(args[0].toLowerCase()))
						{
							add(commandPair.getKey());
						}
					}
				}};
			}
			else if (args.length == 2)
			{
				List<String> types = new ArrayList<>();
				for (String throwable : plugin.getConfig().getConfigurationSection("throwables").getKeys(false))
				{
					types.add(throwable);
				}

				return types;
			}
			else if (args.length == 3)
			{
				List<String> players = new ArrayList<>();
				for (Player plr : plugin.getServer().getOnlinePlayers())
				{
					players.add(plr.getName());
				}

				return players;
			}

		}
		return Collections.emptyList();
	}

}
