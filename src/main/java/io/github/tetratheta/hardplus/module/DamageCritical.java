package io.github.tetratheta.hardplus.module;

import io.github.tetratheta.hardplus.util.Perm;
import io.github.tetratheta.hardplus.util.PlayerUtil;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

@SuppressWarnings("unused")
public class DamageCritical implements Listener {
  @EventHandler
  public void onPlayerInflictCriticalDamage(EntityDamageByEntityEvent e) {
    if (e.getDamager() instanceof AbstractArrow arrow) {
      if (!(arrow.getShooter() instanceof Player p)) return;
      if (!PlayerUtil.checkPermGameMode(p, Perm.DAMAGE_CRITICAL.value)) return;
      arrow.setCritical(false);
    } else if (e.getDamager() instanceof Player p) {
      if (!PlayerUtil.checkPermGameMode(p, Perm.DAMAGE_CRITICAL.value)) return;
      // Can't cancel critical, so reduce damage instead
      e.setDamage(e.getDamage() / 3 * 2);
    }
  }
}
