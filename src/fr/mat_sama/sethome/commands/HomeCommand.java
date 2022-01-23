package fr.mat_sama.sethome.commands;

import fr.mat_sama.sethome.Main;
import org.bukkit.*;
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

public class HomeCommand implements CommandExecutor {

    private final Main main;

    public HomeCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        final String prefix = main.prefix;

        if(sender instanceof Player){

            final Player player = (Player)sender;
            final File file = new File(main.getDataFolder(), "/homes.yml");
            final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

            final UUID uuid = player.getUniqueId();

            if(args.length < 1){
                player.sendMessage(prefix + ChatColor.RED + "Tu dois définir un nom de home: /home <nom>.");
            } else {

                final String key = "players." + uuid + "." + args[0] + ".";
                final ConfigurationSection configurationSection = configuration.getConfigurationSection(key);

                if(configurationSection == null){
                    player.sendMessage(prefix + ChatColor.RED + "Ce home n'existe pas.");
                } else {

                    final World world = Bukkit.getWorld(configurationSection.getString("world"));
                    final double x = configurationSection.getDouble("x");
                    final double y = configurationSection.getDouble("y");
                    final double z = configurationSection.getDouble("z");
                    final float yaw = (float)configurationSection.getDouble("yaw");
                    final float pitch = (float)configurationSection.getDouble("pitch");
                    final Location location = new Location(world, x, y, z, yaw, pitch);

                    player.teleport(location);
                    player.sendMessage(prefix + ChatColor.GREEN + "Tu viens d'être téléporté à " + args[0]);

                }
            }
        }

        return false;
    }
}