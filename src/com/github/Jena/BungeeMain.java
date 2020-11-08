package com.github.Jena;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.logging.Logger;

public class BungeeMain extends Plugin {
    public static Plugin plugin;
    public static Configuration configuration;
    public static Logger logger;

    @Override
    public void onEnable() {
        super.onEnable();
        saveDefaultConfig();
        setPlugin();
        setConfig();
        setLogger();

        for (String string : configuration.getString("commands").split(", ")) {
            BungeeCord.getInstance().pluginManager.registerCommand(this, new BungeeExecutor(string));
        }
    }

    public void saveDefaultConfig() {
        if (!getDataFolder().exists())
            getDataFolder().mkdir();

        File file = new File(getDataFolder(), "config.yml");

        if (!file.exists()) {
            try (InputStream in = getResourceAsStream("config.yml")) {
                Files.copy(in, file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setPlugin() {
        plugin = this;
    }

    public void setConfig() {
        try {
            configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setLogger() {
        logger = this.getLogger();
    }

    public Logger getLogger() {
        return logger;
    }
}
