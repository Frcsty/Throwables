package me.frosty.throwables.util;

import me.frosty.throwables.Throwables;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.ConfigurationSection;
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
                return Collections.emptyList();
            }

            if (args.length == 1)
            {
                final List<String> commands = new ArrayList<>();
                for (final Map.Entry<String, Method> commandPair : plugin.getCommandsManager().getCommandManager().getCommands().entrySet())
                {
                    if (commandPair.getKey().toLowerCase().startsWith(args[0].toLowerCase()))
                    {
                        commands.add(commandPair.getKey());
                    }
                }

                return commands;
            }
            else if (args.length == 2)
            {
                ConfigurationSection section = plugin.getConfig().getConfigurationSection("throwables");
                if (section == null)
                {
                    return Collections.emptyList();
                }

                return new ArrayList<>(section.getKeys(false));
            }
            else if (args.length == 3)
            {
                final List<String> players = new ArrayList<>();
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
