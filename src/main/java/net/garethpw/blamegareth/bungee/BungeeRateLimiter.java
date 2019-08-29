package net.garethpw.blamegareth.bungee;

import net.garethpw.blamegareth.common.RateLimiter;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.CommandSender;

public final class BungeeRateLimiter extends RateLimiter {

  BungeeRateLimiter(final long delay) {
    super(delay);
  }

  public boolean canExecute(final CommandSender sender) {
    if (sender instanceof ProxiedPlayer) {
      return canExecute(
        ((ProxiedPlayer) sender).getUniqueId()
      );
    } else {
      return true;
    }
  }

}
