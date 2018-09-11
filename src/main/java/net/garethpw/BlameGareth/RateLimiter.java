package net.garethpw.BlameGareth;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.UUID;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class RateLimiter {

  private HashMap<UUID,Instant> instants;
  private long delay;

  protected RateLimiter(long delay) {
    instants = new HashMap<UUID,Instant>();
    this.delay = delay;
  }

  public boolean canExecute(ProxiedPlayer player) {
    boolean verdict;

    Instant now = Instant.now();
    UUID uuid = player.getUniqueId();

    if (instants.containsKey(uuid)) {
      Instant last = instants.get(uuid);
      verdict = Duration.between(last, now).getSeconds() >= delay;
    } else {
      verdict = true;
    }

    if (verdict) {
      instants.put(uuid, now);
    }

    return verdict;
  }

  public boolean canExecute(CommandSender sender) {
    if (sender instanceof ProxiedPlayer) {
      return canExecute((ProxiedPlayer) sender);
    } else {
      return true;
    }
  }
}
