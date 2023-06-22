package io.github.a5h73y.parkour.plugin;

import io.github.a5h73y.parkour.Parkour;
import io.github.a5h73y.parkour.utility.PluginUtils;
import net.serble.serblenetworkplugin.API.IdService;
import net.serble.serblenetworkplugin.API.PartyService;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.UUID;

import static org.bukkit.Bukkit.getServer;

/**
 * Serble Network Integration
 * When the SerblePartyApi class is initialised, an attempt is made to connect to Serble's Party Service.
 */
public class SerblePartyApi extends PluginWrapper {

	private PartyService partyService;

	public SerblePartyApi(Parkour parkour) {
		super(parkour);
	}

	@Override
	public String getPluginName() {
		return "SerbleNetworkPlugin";
	}

	@Override
	protected void initialise() {
		super.initialise();

		if (isEnabled()) {
			RegisteredServiceProvider<PartyService> economyProvider =
					getServer().getServicesManager().getRegistration(PartyService.class);

			if (economyProvider == null) {
				PluginUtils.log("[Economy] Failed to connect to Serble's party service. Disabling party service.", 2);
				setEnabled(false);
				return;
			}

			partyService = economyProvider.getProvider();
		}
	}

	/**
	 * Trigger a party warp on the party leader.
	 * @param player The leader of the party
	 */
	public void triggerPartyWarp(Player player) {
		partyService.triggerWarp(player);
	}

	public boolean canPlayGameAlertOrWarp(Player player) {
		return partyService.canJoinGameAndAlertOrWarp(player);
	}

}
