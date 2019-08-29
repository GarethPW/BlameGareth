package net.garethpw.blamegareth.bungee;

import static java.lang.Math.toIntExact;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Runnable;
import java.util.concurrent.TimeUnit;

import net.md_5.bungee.api.plugin.Plugin;

import net.garethpw.blamegareth.bungee.command.BungeeBlameGarethCommand;
import net.garethpw.blamegareth.bungee.command.BungeeForgiveGarethCommand;
import net.garethpw.blamegareth.bungee.command.BungeeIsItGarethsFaultCommand;
import net.garethpw.blamegareth.common.Stats;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public final class BungeeBlameGarethPlugin extends Plugin {

  private static BungeeBlameGarethPlugin instance;
  private static BungeeRateLimiter rateLimiter;

  private int blameCount, forgiveCount;
  private boolean statsChanged = false;

  public static BungeeBlameGarethPlugin getInstance() { return instance; }

  public static BungeeRateLimiter getRateLimiter() { return rateLimiter; }

  public Stats getStats() { return new Stats(blameCount, forgiveCount); }

  public int incrementBlames() {
    statsChanged = true;
    return ++blameCount;
  }

  public int incrementForgives() {
    statsChanged = true;
    return ++forgiveCount;
  }

  private void disable() {
    // "disable" plugin
    getLogger().info("Disabling BlameGareth...");
    getProxy().getPluginManager().unregisterCommands(this);
    getProxy().getScheduler().cancel(this);
  }

  private void disable(Exception e) {
    e.printStackTrace();
    disable();
  }

  private File getPluginFolder() {
    File pluginFolder = getDataFolder(); // get plugin folder

    if (!pluginFolder.exists()) {
      pluginFolder.mkdir();
    }

    return pluginFolder;
  }

  private void getValues() {
    File valuesFile = new File(getPluginFolder(), "values.json"); // get values store

    if (valuesFile.exists()) {
      try (FileReader reader = new FileReader(valuesFile)) {
        JSONParser parser = new JSONParser();

        JSONObject valuesObj = (JSONObject) parser.parse(reader);
        blameCount = toIntExact((Long) valuesObj.get("blame_count"));
        forgiveCount = toIntExact((Long) valuesObj.get("forgive_count"));

      } catch (Exception e) {
        // "disable" plugin if unable to create values store
        disable(e);
        return;
      }
    } else {
      blameCount = 0; // default values
      forgiveCount = 0;

      saveValues();
    }
  }

  public void saveValues() {
    final File valuesFile = new File(getPluginFolder(), "values.json"); // get values store
    final boolean valuesFileExisted = valuesFile.exists();

    if (!valuesFileExisted) {
      try {
        valuesFile.createNewFile();
      } catch (IOException e) {
        // "disable" plugin if unable to create values store
        disable(e);
        return;
      }
    }

    if (statsChanged || !valuesFileExisted) {
      final JSONObject valuesObj = new JSONObject();
      valuesObj.put("blame_count", blameCount);
      valuesObj.put("forgive_count", forgiveCount);

      statsChanged = false;

      try (final FileWriter writer = new FileWriter(valuesFile)) {
        writer.write(valuesObj.toJSONString());
      } catch (IOException e) {
        e.printStackTrace();
        return;
      }
    }
  }

  @Override
  public void onEnable() {
    instance = this;
    rateLimiter = new BungeeRateLimiter(10L);

    getValues();

    getProxy().getScheduler().schedule(this, new Runnable() {
      @Override
      public void run() {
        saveValues();
      }
    }, 60L, 60L, TimeUnit.SECONDS);

    getProxy().getPluginManager().registerCommand(this, new BungeeBlameGarethCommand());
    getProxy().getPluginManager().registerCommand(this, new BungeeForgiveGarethCommand());
    getProxy().getPluginManager().registerCommand(this, new BungeeIsItGarethsFaultCommand());
  }

  @Override
  public void onDisable() {
    disable();
    saveValues();
  }

}
