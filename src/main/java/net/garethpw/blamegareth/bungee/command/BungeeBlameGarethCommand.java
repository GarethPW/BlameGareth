package net.garethpw.BlameGareth.command;

import net.garethpw.BlameGareth.BlameGarethPlugin;
import net.garethpw.BlameGareth.command.BaseBlameCommand;

public final class BlameGarethCommand extends BaseBlameCommand {

  public BlameGarethCommand() {
    super("blamegareth", "blamegareth.blame", "blamed");
  }

  @Override
  protected int increment() {
    return BlameGarethPlugin.getInstance().incrementBlames();
  }

}
