package net.garethpw.BlameGareth.command;

import net.garethpw.BlameGareth.command.BaseBlameCommand;

public class BlameGarethCommand extends BaseBlameCommand {

  public BlameGarethCommand() {
    super("blamegareth", "blamegareth.blame", "blamed");
  }

  protected int increment() { return plugin.incrementBlames(); }

}
