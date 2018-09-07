package net.garethpw.BlameGareth.command;

import net.garethpw.BlameGareth.BlameGarethPlugin;
import net.md_5.bungee.api.plugin.Command;

public abstract class BaseCommand extends Command {

  protected static BlameGarethPlugin plugin = BlameGarethPlugin.getInstance();

  public BaseCommand(String name, String permission) {
    super(name, permission);
  }

  public BaseCommand(String name, String permission, String... aliases) {
    super(name, permission, aliases);
  }

}
