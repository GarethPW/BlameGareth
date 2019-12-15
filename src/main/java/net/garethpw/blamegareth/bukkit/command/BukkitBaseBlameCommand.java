package net.garethpw.blamegareth.bukkit.command;

import static net.garethpw.blamegareth.common.command.BaseBlameCommand.*;

import net.garethpw.blamegareth.bukkit.BukkitBlameGarethPlugin;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

abstract class BukkitBaseBlameCommand implements CommandExecutor {

  private final String participle;

  protected BukkitBaseBlameCommand(final String participle) {
    super();
    this.participle = participle;
  }

  protected abstract int increment();

  @Override
  public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
    if (BukkitBlameGarethPlugin.getRateLimiter().canExecute(sender)) {
      final int number = increment();

      final String username = ChatColor.RED + sender.getName();

      final String action = ChatColor.GOLD + String.format(
        " has %s Gareth for the %d%s time!",
        participle, number, indicator(number)
      );

      sender.getServer().broadcastMessage(username + action);
    } else {
      sender.sendMessage(ChatColor.RED + "You're doing that too quickly!");
    }

    return true;
  }

}
