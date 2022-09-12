package me.antonitoo.broadcaster;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Main extends JavaPlugin {

    public String ruta;

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&cBroadcaster&8] &aThe plugin has been activated."));
        insertConfig();
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&cBroadcaster&8] &cThe plugin has been desactivated."));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(command.getName().equalsIgnoreCase("broadcast")){
            String cs = "prefix-broadcast";
            String cst = "prefix-plugin";
            String prefix = ChatColor.translateAlternateColorCodes('&', getConfig().getString(cs));
            String prefixplugin = ChatColor.translateAlternateColorCodes('&', getConfig().getString(cst));

            if(sender.hasPermission("broadcaster.broadcast")){
                if(args.length == 0){
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("Usage-Message")));
                } else if(args.length== 1){
                    String argss = args[0];

                    Bukkit.broadcastMessage(prefix + ChatColor.translateAlternateColorCodes('&', argss));
                    Bukkit.getConsoleSender().sendMessage(prefixplugin + ChatColor.translateAlternateColorCodes('&', getConfig().getString("BroadcastBy-Message").replace("%broadcastby%", sender.getName())));
                } else {

                    StringBuilder builder = new StringBuilder();

                    for(int i = 0; i < args.length; i++){

                        builder.append(args[i]);
                        builder.append(" ");
                    }

                    String finalMessage = builder.toString();
                    finalMessage = finalMessage.stripTrailing();

                    Bukkit.broadcastMessage(prefix + ChatColor.translateAlternateColorCodes('&', finalMessage));
                    Bukkit.getConsoleSender().sendMessage(prefixplugin + ChatColor.translateAlternateColorCodes('&', getConfig().getString("BroadcastBy-Message").replace("%broadcastby%", sender.getName())));
                }
            } else {
                sender.sendMessage(prefixplugin + ChatColor.translateAlternateColorCodes('&', getConfig().getString("NoPerms-Message")));
            }

        }

        if(command.getName().equalsIgnoreCase("broadcast-reload")){
            String cst = "prefix-plugin";
            String prefixplugin = ChatColor.translateAlternateColorCodes('&', getConfig().getString(cst));
            if(sender.hasPermission("broadcaster.reload")){
                sender.sendMessage(prefixplugin + ChatColor.translateAlternateColorCodes('&', getConfig().getString("Reload-Message")));

                reloadConfig();
            } else {
                sender.sendMessage(prefixplugin + ChatColor.translateAlternateColorCodes('&', getConfig().getString("NoPerms-Message")));
            }
        }

        return true;
    }

    public void insertConfig(){

        File config = new File(this.getDataFolder(),"config.yml");
        ruta = config.getPath();

        if(!config.exists()) {
            this.getConfig().options().copyDefaults(true);
            saveDefaultConfig();
        }
    }
}
