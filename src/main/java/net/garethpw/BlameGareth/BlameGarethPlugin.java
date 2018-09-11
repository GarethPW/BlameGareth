package net.garethpw.BlameGareth;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.Math.toIntExact;
import java.lang.Runnable;
import java.util.concurrent.TimeUnit;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

import net.garethpw.BlameGareth.command.BlameGarethCommand;
import net.garethpw.BlameGareth.command.ForgiveGarethCommand;
import net.garethpw.BlameGareth.command.IsItGarethsFaultCommand;
import net.garethpw.BlameGareth.RateLimiter;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class BlameGarethPlugin extends Plugin {

  private static BlameGarethPlugin instance;
  private static RateLimiter rateLimiter;

  private int blameCount, forgiveCount;
  private boolean statsChanged = false;

  public static class Stats {
    public int blameCount, forgiveCount;

    protected Stats(int blames, int forgives) {
      blameCount = blames;
      forgiveCount = forgives;
    }
  }

  public static BlameGarethPlugin getInstance() { return instance; }

  public static RateLimiter getRateLimiter() { return rateLimiter; }

  public int incrementBlames() {
    statsChanged = true;
    return ++blameCount;
  }

  public int incrementForgives() {
    statsChanged = true;
    return ++forgiveCount;
  }

  public Stats getStats() { return new Stats(blameCount, forgiveCount); }

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
    File valuesFile = new File(getPluginFolder(), "values.json"); // get values store
    boolean valuesFileExisted = valuesFile.exists();

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
      JSONObject valuesObj = new JSONObject();
      valuesObj.put("blame_count", blameCount);
      valuesObj.put("forgive_count", forgiveCount);

      statsChanged = false;

      try (FileWriter writer = new FileWriter(valuesFile)) {
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
    rateLimiter = new RateLimiter(10L);

    getValues();

    getProxy().getScheduler().schedule(this, new Runnable() {
      public void run() {
        saveValues();
      }
    }, 60L, 60L, TimeUnit.SECONDS);

    PluginManager pluginManager = getProxy().getPluginManager();

    pluginManager.registerCommand(this, new BlameGarethCommand());
    pluginManager.registerCommand(this, new ForgiveGarethCommand());
    pluginManager.registerCommand(this, new IsItGarethsFaultCommand());
  }

  @Override
  public void onDisable() {
    disable();
    saveValues();
  }

}
