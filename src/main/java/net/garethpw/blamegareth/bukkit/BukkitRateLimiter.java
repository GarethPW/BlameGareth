package net.garethpw.blamegareth.bukkit;

import net.garethpw.blamegareth.common.RateLimiter;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class BukkitRateLimiter extends RateLimiter {

  BukkitRateLimiter(final long delay) {
    super(delay);
  }

  public boolean canExecute(final CommandSender sender) {
    if (sender instanceof Player) {
      return canExecute(
        ((Player) sender).getUniqueId()
      );
    } else {
      return true;
    }
  }

}
