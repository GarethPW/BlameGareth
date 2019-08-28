package net.garethpw.BlameGareth.command;

import net.md_5.bungee.api.plugin.Command;

abstract class BaseCommand extends Command {

  protected BaseCommand(final String name, final String permission, final String... aliases) {
    super(name, permission, aliases);
  }

  protected BaseCommand(final String name, final String permission) {
    super(name, permission);
  }

}
