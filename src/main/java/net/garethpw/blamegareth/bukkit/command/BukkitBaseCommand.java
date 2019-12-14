package net.garethpw.blamegareth.bungee.command;

import net.md_5.bungee.api.plugin.Command;

abstract class BungeeBaseCommand extends Command {

  protected BungeeBaseCommand(final String name, final String permission, final String... aliases) {
    super(name, permission, aliases);
  }

  protected BungeeBaseCommand(final String name, final String permission) {
    super(name, permission);
  }

}
