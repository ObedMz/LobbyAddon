package obed.me.lobbyaddon;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public final class LobbyAddon extends JavaPlugin implements CommandExecutor {
    private static LobbyAddon instance;
    private ConfigManager config;
    private String command;
    private String permission;
    public static LobbyAddon getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        config = new ConfigManager();
        config.registerConfig();
        command = config.getConfig().getString("config.lobby.command");
        permission = config.getConfig().getString("config.lobby.permission");
        Bukkit.getMessenger().registerOutgoingPluginChannel((Plugin)this, "BungeeCord");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Lobby Addon loaded correctly.");
        if(!Boolean.parseBoolean(config.getConfig().getString("config.lobby.enable"))){
            return;
        }
        try {
            Constructor<PluginCommand> constructor = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
            constructor.setAccessible(true);
            if(command == null){
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "El comando está vacio, revisa la config.yml.");
                Bukkit.shutdown();
            }
            PluginCommand pluginCommand = constructor.newInstance(command, this);
            pluginCommand.setDescription("Spigot command for lobby");
            pluginCommand.setPermission(permission);
            Field field = SimplePluginManager.class.getDeclaredField("commandMap");
            field.setAccessible(true);
            CommandMap commandMap = (CommandMap) field.get((SimplePluginManager) getServer().getPluginManager());
            commandMap.register(getDescription().getName(), pluginCommand);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException | NoSuchMethodException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }


    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase(command)) {
            if(sender instanceof Player){
                if(sender.hasPermission(permission)){
                    LobbyAddon.getInstance().LobbyTP((Player)sender);
                    sender.sendMessage(LobbyAddon.getInstance().sendMessage("config.lobby.message.sucess"));
                    return true;
                }
                sender.sendMessage(LobbyAddon.getInstance().sendMessage("config.lobby.message.denied"));
            }

        }

        return false;
    }




    public void LobbyTP(Player p){
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);
        try {
            out.writeUTF("LBTP");
        } catch (IOException ee) {
            ee.printStackTrace();
        }
        p.sendPluginMessage((Plugin)this, "BungeeCord", b.toByteArray());
    }


    private String sendMessage(String path) {
        return ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.getConfig().getString(path)));
    }
}
