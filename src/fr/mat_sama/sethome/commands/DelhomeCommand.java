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

public class DelhomeCommand implements CommandExecutor {

    private final Main main;

    public DelhomeCommand(Main main) {
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
                player.sendMessage(prefix + ChatColor.RED + "Tu dois définir un nom de home à supprimer: /delhome <nom>.");
            } else {

                final String key = "players." + uuid + "." + args[0];
                final ConfigurationSection configurationSection = configuration.getConfigurationSection(key + ".");

                if(configurationSection == null){
                    player.sendMessage(prefix + ChatColor.RED + "Ce home n'existe pas.");
                } else {

                    configuration.set(key, null);
                    try {
                        configuration.save(file);
                        player.sendMessage(prefix + ChatColor.GREEN + "Ton home '" + args[0] + "' à bien été supprimé.");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }

        return false;
    }
}