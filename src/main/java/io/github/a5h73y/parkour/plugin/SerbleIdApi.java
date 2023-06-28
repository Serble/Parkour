package io.github.a5h73y.parkour.plugin;

import io.github.a5h73y.parkour.Parkour;
import io.github.a5h73y.parkour.utility.PluginUtils;
import io.github.a5h73y.parkour.utility.TranslationUtils;
import io.github.a5h73y.parkour.utility.ValidationUtils;
import net.milkbowl.vault.economy.EconomyResponse;
import net.serble.serblenetworkplugin.API.IdService;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.UUID;

import static io.github.a5h73y.parkour.other.ParkourConstants.*;
import static org.bukkit.Bukkit.getServer;

/**
 * Serble Network Integration
 * When the SerbleIdApi class is initialised, an attempt is made to connect to Serble's ID Service.
 */
public class SerbleIdApi extends PluginWrapper {

	private IdService idService;

	public SerbleIdApi(Parkour parkour) {
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
			RegisteredServiceProvider<IdService> economyProvider =
					getServer().getServicesManager().getRegistration(IdService.class);

			if (economyProvider == null) {
				PluginUtils.log("[Economy] Failed to connect to Serble's ID service. Disabling ID service.", 2);
				setEnabled(false);
				return;
			}

			idService = economyProvider.getProvider();
		}
	}

	/**
	 * Reward the Player with an amount.
	 *
	 * @param player target player
	 * @return The user's profile UUID
	 */
	public UUID getIdOfPlayer(Player player) {
		return idService.getPlayerUuid(player);
	}

	public UUID getIdOfPlayer(OfflinePlayer player) {
		return idService.getPlayerUuid(player.getUniqueId());
	}

	public UUID reverseGetIdOfPlayer(UUID profile) {
		UUID id = idService.getPlayerFromProfile(profile);
		if (id == null) {
			return profile;
		}
		return id;
	}

}
