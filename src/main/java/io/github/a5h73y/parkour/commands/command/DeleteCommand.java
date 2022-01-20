package io.github.a5h73y.parkour.commands.command;

import io.github.a5h73y.parkour.Parkour;
import io.github.a5h73y.parkour.commands.type.BasicParkourCommand;
import io.github.a5h73y.parkour.utility.PluginUtils;
import io.github.a5h73y.parkour.utility.ValidationUtils;
import io.github.a5h73y.parkour.utility.permission.Permission;
import org.bukkit.command.CommandSender;

public class DeleteCommand extends BasicParkourCommand {

	public DeleteCommand(Parkour parkour) {
		super(parkour, AllowedSender.ANY, "delete");
	}

	@Override
	protected Permission getRequiredPermission() {
		return Permission.ADMIN_DELETE;
	}

	@Override
	protected boolean hasValidArguments(CommandSender commandSender, String[] args) {
		return ValidationUtils.validateArgs(commandSender, args, 3);
	}

	@Override
	public void performAction(CommandSender commandSender, String[] args) {
		PluginUtils.deleteCommand(commandSender, args[1], args[2]);
	}
}
