package net.garethpw.blamegareth.common;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.UUID;

public abstract class RateLimiter {

  private final HashMap<UUID,Instant> lastExecution;
  private final long delay;

  protected RateLimiter(final long delay) {
    lastExecution = new HashMap<UUID,Instant>();
    this.delay = delay;
  }

  protected boolean canExecute(final UUID uuid) {
    final boolean verdict;
    final Instant now = Instant.now();

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
}
