package obed.me.lobbyaddon;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.Objects;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;



public class ConfigManager {
    private LobbyAddon plugin = LobbyAddon.getPlugin(LobbyAddon.class);
    private FileConfiguration config = null;
    private File configFile = null;

    // config
    public FileConfiguration getConfig(){
        if(config == null) {
            reloadConfig();
        }
        return config;
    }

    public void reloadConfig() {
        if(config == null) {
            configFile = new File(plugin.getDataFolder(), "config.yml");
        }
        config = YamlConfiguration.loadConfiguration(configFile);
        Reader defConfigStream;
        try {
            defConfigStream = new InputStreamReader(Objects.requireNonNull(plugin.getResource("config.yml")), "UTF8");
            if(defConfigStream != null) {
                YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
                config.setDefaults(defConfig);
            }
        }catch(UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void saveConfig() {
        try {
            config.save(configFile);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void registerConfig() {
        configFile = new File(plugin.getDataFolder(), "config.yml");
        if(!configFile.exists()) {
            this.getConfig().options().copyDefaults(true);
            saveConfig();
        }
    }
}