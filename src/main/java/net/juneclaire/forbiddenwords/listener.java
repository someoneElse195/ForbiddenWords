package net.juneclaire.forbiddenwords;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitScheduler;

import net.md_5.bungee.api.ChatColor;

public class listener implements Listener {
    
    BukkitScheduler scheduler = Bukkit.getScheduler();
    private final App plugin = App.getPlugin(App.class);

    final String[] deathMessages = {"faced the wrath of some god...", "blew up and acted like they don't know nobody..", "got shit on", "yee'd their last haw", "combusted", "got canceled"};
    
    Words words = new Words();



    @EventHandler
    public void deathByWords(AsyncPlayerChatEvent event) throws IllegalArgumentException, IOException, URISyntaxException {
        Player player = event.getPlayer();
        String message = event.getMessage();
        String word = words.chooseWord();

        if(player.hasPermission("forbiddenwords" + "." + "killable") && message.contains(word) && !player.hasPermission("forbiddenwords" + "." + "admin")) {
            scheduler.runTask(plugin, () -> {
                player.spawnParticle(Particle.EXPLOSION_NORMAL, player.getLocation(), 100);
                player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1, 0);
                player.setHealth(0);
            });
        }
    }
    @EventHandler 
    public void deathMessage(PlayerDeathEvent event) {
        if(event.getDeathMessage().contains("died")) {
            Random random = new Random();
            int index = random.nextInt(deathMessages.length);
            event.setDeathMessage(ChatColor.DARK_RED + event.getEntity().getDisplayName() + " " + deathMessages[index]);
        }
    }
}
