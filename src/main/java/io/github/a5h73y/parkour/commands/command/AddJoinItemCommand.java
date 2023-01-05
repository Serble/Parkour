package io.github.a5h73y.parkour.commands.command;

import io.github.a5h73y.parkour.Parkour;
import io.github.a5h73y.parkour.commands.type.AllowedCommandSender;
import io.github.a5h73y.parkour.commands.type.BasicParkourCommand;
import io.github.a5h73y.parkour.utility.ValidationUtils;
import io.github.a5h73y.parkour.utility.permission.Permission;
import org.bukkit.command.CommandSender;

public class AddJoinItemCommand extends BasicParkourCommand {

	public AddJoinItemCommand(Parkour parkour) {
		super(parkour, "addjoinitem",
				AllowedCommandSender.ANY);
	}

	@Override
	protected Permission getRequiredPermission() {
		return Permission.ADMIN_COURSE;
	}

	@Override
	public boolean validatePlayerArguments(CommandSender commandSender, String[] args) {
		return ValidationUtils.validateArgs(commandSender, args, 1, 6);
	}

	@Override
	public boolean validateConsoleArguments(CommandSender commandSender, String[] args) {
		return ValidationUtils.validateArgs(commandSender, args, 4, 6);
	}

	@Override
	public void performAction(CommandSender commandSender, String[] args) {
		parkour.getCourseSettingsManager().addJoinItem(commandSender, args);
	}
}
