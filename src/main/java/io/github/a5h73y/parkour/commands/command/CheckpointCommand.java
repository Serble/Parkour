package io.github.a5h73y.parkour.commands.command;

import io.github.a5h73y.parkour.Parkour;
import io.github.a5h73y.parkour.commands.type.AllowedCommandSender;
import io.github.a5h73y.parkour.commands.type.BasicParkourCommand;
import io.github.a5h73y.parkour.utility.ValidationUtils;
import io.github.a5h73y.parkour.utility.permission.Permission;
import org.bukkit.command.CommandSender;

public class DELETEMECommand extends BasicParkourCommand {

	public DELETEMECommand(Parkour parkour) {
		super(parkour,
				AllowedCommandSender.,
				"SETME", "ANY?");
	}

	@Override
	protected Permission getRequiredPermission() {
		return Permission.;
	}

	@Override
	public boolean validatePlayerArguments(CommandSender commandSender, String[] args) {
		return
	}

	@Override
	public boolean validateConsoleArguments(CommandSender commandSender, String[] args) {
		return
	}

	@Override
	public void performAction(CommandSender commandSender, String[] args) {

	}
}
