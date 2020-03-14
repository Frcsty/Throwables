package me.frosty.throwables.managers;

import com.codeitforyou.lib.api.command.CommandManager;
import me.frosty.throwables.Throwables;
import me.frosty.throwables.commands.BaseCommand;
import me.frosty.throwables.commands.subs.GiveCommand;
import me.frosty.throwables.commands.subs.ReloadCommand;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Arrays;

public class CommandsManager
{

	private final Throwables            plugin;
	private final FileConfiguration     config;
	private final CommandManager        manager;
	private final CommandManager.Locale locale;

	public CommandsManager(final Throwables plugin)
	{
		this.plugin = plugin;
		this.config = plugin.getConfig();

		this.manager = new CommandManager(Arrays.asList(
				ReloadCommand.class,
				GiveCommand.class
		), "throwables", plugin);

		this.locale = manager.getLocale();
	}

	public void registerCommand()
	{
		// Assign attributes to before created Command Manager
		this.createCommandAttributes();

		// Register Command Manager after all attributes have been applied
		this.manager.register();
	}

	private void createCommandAttributes()
	{
		// Set the main plugin command
		this.manager.setMainCommand(BaseCommand.class);

		// Assign command aliases
		for (final String cmd : plugin.getConfig().getStringList("settings.alias"))
		{
			this.manager.addAlias(cmd);
		}

		// Set command messages handled by the libs Command Manager
		this.locale.setUsage(defaultMessage(this.config.getString("messages.usageMessage")));
		this.locale.setUnknownCommand(defaultMessage(this.config.getString("messages.unknownCommandMessage")));
		this.locale.setPlayerOnly(defaultMessage(this.config.getString("messages.playerOnlyMessage")));
		this.locale.setNoPermission(defaultMessage(this.config.getString("messages.noPermissionMessage")));
	}

	private String defaultMessage(final String message)
	{
		if (message == null)
		{
			return "&cNo message set!";
		}
		return message;
	}

	public final CommandManager getCommandManager()
	{
		return this.manager;
	}

}
