package me.frosty.throwables;

import me.frosty.throwables.listeners.PlayerInteractListener;
import me.frosty.throwables.managers.ActionsManager;
import me.frosty.throwables.managers.CommandsManager;
import me.frosty.throwables.util.Actions;
import me.frosty.throwables.util.TabComplete;
import org.bukkit.entity.Item;
import org.bukkit.plugin.java.JavaPlugin;

public final class Throwables extends JavaPlugin
{

    private final CommandsManager        commandsManager        = new CommandsManager(this);
    private final ActionsManager         actionsManager         = new ActionsManager(this);
    private final PlayerInteractListener playerInteractListener = new PlayerInteractListener(this);
    private final Actions                actions                = new Actions(this);
    private final TabComplete            completer              = new TabComplete(this);

    @Override
    public void onEnable()
    {
        saveDefaultConfig();

        // Register plugin's commands
        commandsManager.registerCommand();

        // Register actions
        actionsManager.registerActions();

        // Register Player Interact Listener
        getServer().getPluginManager().registerEvents(playerInteractListener, this);

        // Load tab completer
        getCommand("throwables").setTabCompleter(completer);

    }

    @Override
    public void onDisable()
    {
        reloadConfig();
    }

    public final ActionsManager getActionsManager()
    {
        return actionsManager;
    }

    public final Item getDrop()
    {
        return playerInteractListener.getDrop();
    }

    public final Actions getActions()
    {
        return actions;
    }

    public final CommandsManager getCommandsManager()
    {
        return commandsManager;
    }

}
