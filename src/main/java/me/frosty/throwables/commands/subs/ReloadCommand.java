package me.frosty.throwables.commands.subs;

import com.codeitforyou.lib.api.command.Command;
import com.codeitforyou.lib.api.general.StringUtil;
import me.frosty.throwables.Throwables;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.logging.Level;

public class ReloadCommand
{

	@Command(permission = "throwables.command.reload", aliases = {"reload"}, usage = "reload")
	public static void execute(final CommandSender sender, final Throwables plugin, final String[] args)
	{
		long startTime = System.currentTimeMillis();
		new BukkitRunnable()
		{
			@Override
			public void run()
			{
				try
				{
					plugin.reloadConfig();
				}
				catch (Exception ex)
				{
					plugin.getLogger().log(Level.SEVERE, "Failed to reload configuration!", ex);
				}
			}
		}.runTaskAsynchronously(plugin);
		String estimatedTime = String.valueOf(System.currentTimeMillis() - startTime);
		sender.sendMessage(StringUtil.translate(plugin.getConfig().getString("messages.reloadMessage").replace("%time%", estimatedTime)));
	}

}
