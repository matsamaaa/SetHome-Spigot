package fr.mat_sama.sethome.commands;

import fr.mat_sama.sethome.Main;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class SethomeCommand  implements CommandExecutor {

    private final Main main;

    public SethomeCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        final String prefix = main.prefix;

        if(sender instanceof Player) {

            final Player player = (Player)sender;
            final File file = new File(main.getDataFolder(), "/homes.yml");
            final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

            final UUID uuid = player.getUniqueId();
            final Location location = player.getLocation();

            if(args.length < 1) {
                player.sendMessage(prefix + ChatColor.RED + "Tu dois définir un nom à ton home: /sethome <nom>.");
            } else {

                final String key = "players." + uuid + "." + args[0] + ".";
                final ConfigurationSection configurationSection = configuration.getConfigurationSection(key);

                if(configurationSection == null){

                    configuration.set(key + ".world", location.getWorld().getName());
                    configuration.set(key + ".x", location.getX());
                    configuration.set(key + ".y", location.getY());
                    configuration.set(key + ".z", location.getZ());
                    configuration.set(key + ".yaw", location.getYaw());
                    configuration.set(key + ".pitch", location.getPitch());

                    try {
                        configuration.save(file);
                        player.sendMessage(prefix + ChatColor.GREEN + "Ton home '" + args[0] + "' à bien été sauvegardé.");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    player.sendMessage(prefix + ChatColor.RED + "Ce home est déjà existant.");
                }
            }
        }

        return false;
    }
}