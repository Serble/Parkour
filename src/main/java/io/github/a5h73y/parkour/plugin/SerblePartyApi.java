package io.github.a5h73y.parkour.plugin;

import io.github.a5h73y.parkour.Parkour;
import io.github.a5h73y.parkour.type.player.session.ParkourSession;
import io.github.a5h73y.parkour.utility.PluginUtils;
import net.serble.serblenetworkplugin.API.PartyService;
import net.serble.serblenetworkplugin.API.Schemas.WarpEvent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

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
			RegisteredServiceProvider<PartyService> serbleProvider =
					getServer().getServicesManager().getRegistration(PartyService.class);

			if (serbleProvider == null) {
				PluginUtils.log("[Serble] Failed to connect to Serble's party service. Disabling party service.", 2);
				setEnabled(false);
				return;
			}

			partyService = serbleProvider.getProvider();

			partyService.registerWarpListener(this::onWarp);
			return;
		}
		PluginUtils.log("Failed to connect Serble party service. isEnabled() was false.", 2);
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

	private boolean onWarp(WarpEvent event) {
		// Is the leader in a Parkour course?
		if (!parkour.getParkourSessionManager().isPlayingParkourCourse(event.getPartyLeader())) {
			return false;  // We cannot handle this event because the leader is not in a course.
		}

		ParkourSession leaderSession = parkour.getParkourSessionManager().getParkourSession(event.getPartyLeader());
		assert leaderSession != null;
		if (!parkour.getPlayerManager().canJoinCourse(event.getTarget(), leaderSession.getCourse())) {
			event.getTarget().sendMessage(ChatColor.RED + "You cannot join this course.");
			return true;  // Don't let the target get warped
		}

		parkour.getPlayerManager().joinCourse(event.getTarget(), leaderSession.getCourse());
		return true;
	}

}
