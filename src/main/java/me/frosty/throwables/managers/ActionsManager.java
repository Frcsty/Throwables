package me.frosty.throwables.managers;

import com.codeitforyou.lib.api.actions.ActionManager;
import me.frosty.throwables.Throwables;

public class ActionsManager
{

	private final ActionManager manager;
	private final Throwables    plugin;

	public ActionsManager(final Throwables plugin)
	{
		this.plugin = plugin;
		this.manager = new ActionManager(plugin);
	}

	public void registerActions()
	{
		// Add default actions provided by the CIFYLib
		this.manager.addDefaults();

		// Create custom actions
		this.manager.addAction("action", (player, data) -> plugin.getActions().executeCustomAction(player, data));

	}

	public final ActionManager getActionManager()
	{
		return manager;
	}

}