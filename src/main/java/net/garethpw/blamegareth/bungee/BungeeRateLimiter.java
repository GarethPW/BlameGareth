package net.garethpw.BlameGareth;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.UUID;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public final class RateLimiter {

  private final HashMap<UUID,Instant> lastExecution;
  private final long delay;

  RateLimiter(final long delay) {
    lastExecution = new HashMap<UUID,Instant>();
    this.delay = delay;
  }

  public boolean canExecute(final ProxiedPlayer player) {
    final boolean verdict;

    final Instant now = Instant.now();
    final UUID uuid = player.getUniqueId();

    if (lastExecution.containsKey(uuid)) {
      Instant last = lastExecution.get(uuid);
      verdict = Duration.between(last, now).getSeconds() >= delay;
    } else {
      verdict = true;
    }

    if (verdict) {
      lastExecution.put(uuid, now);
    }

    return verdict;
  }

  public boolean canExecute(final CommandSender sender) {
    if (sender instanceof ProxiedPlayer) {
      return canExecute((ProxiedPlayer) sender);
    } else {
      return true;
    }
  }
}
