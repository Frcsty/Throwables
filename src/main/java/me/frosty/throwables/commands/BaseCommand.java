package me.frosty.throwables.commands;

import com.codeitforyou.lib.api.command.Command;
import com.codeitforyou.lib.api.command.CommandManager;
import com.codeitforyou.lib.api.general.StringUtil;
import me.frosty.throwables.Throwables;
import org.bukkit.entity.Player;

import java.util.List;

public class BaseCommand
{

	@Command(permission = "throwables.command.base", aliases = {"help"}, usage = "help")
	public static void execute(final Player player, final Throwables plugin, final String[] args)
	{
		final CommandManager manager = plugin.getCommandsManager().getCommandManager();
		final List<String> aliases = manager.getAliases();

		StringBuilder text = new StringBuilder();

		for (int i = 0; i < aliases.size(); i++)
		{
			text.append(aliases.get(i));
			if (i < aliases.size() - 1)
			{
				text.append(", ");
			}
		}

		for (String msg : plugin.getConfig().getStringList("messages.helpMessage"))
		{
			player.sendMessage(StringUtil.translate(msg.replace("%alias%", text)));
		}
	}

}
