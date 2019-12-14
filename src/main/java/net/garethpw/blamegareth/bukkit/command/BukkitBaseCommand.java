package net.garethpw.blamegareth.bukkit.command;

import net.md_5.bungee.api.plugin.Command;

abstract class BukkitBaseCommand extends Command {

  protected BukkitBaseCommand(final String name, final String permission, final String... aliases) {
    super(name, permission, aliases);
  }

  protected BukkitBaseCommand(final String name, final String permission) {
    super(name, permission);
  }

}
