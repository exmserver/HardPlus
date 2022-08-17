package xyz.tetratheta.hardplus;

import org.bukkit.scheduler.BukkitTask;
import xyz.tetratheta.hardplus.module.*;

import java.util.HashSet;
import java.util.Set;

public class HardplusConfig extends BaseConfig {
  Hardplus plugin;
  Set<BukkitTask> tasks = new HashSet<>();

  String strColdDamage = "module.cold-damage.";
  String strCreeperCharge = "module.creeper-charge.";
  String strCreeperExplode = "module.creeper-explode.";
  String strDamageCritical = "module.damage-critical.";
  String strDamageGive = "module.damage-give.";
  String strDamageTake = "module.damage-take.";
  String strFastAir = "module.fast-air.";
  String strFireForever = "module.fire-forever.";
  String strHungerDebuff = "module.hunger-debuff.";
  String strLavaDeath = "module.lava-death.";
  String strMobTarget = "module.mob-target.";
  String strNoFireResistance = "module.no-fire-resistance.";
  String strNoSweep = "module.no-sweep.";
  String strNoWater = "module.no-water.";
  String strSharpCut = "module.sharp-cut.";
  String strUncookedPoison = "module.uncooked-poison.";

  public HardplusConfig(Hardplus provided) {
    super(provided);
    plugin = provided;
    initialize();
  }

  public void initialize() {
    // Cold Damage
    if (getBoolean(strColdDamage + "enable", true)) {
      long delay = getLong(strColdDamage + "timer.delay", 10);
      long period = getLong(strColdDamage + "timer.period", 10);

      ColdDamage coldDamage = new ColdDamage();
      registerListeners(coldDamage);
      tasks.add(coldDamage.getTask().runTaskTimer(plugin, delay, period));
    }
    // Creeper Charge
    if (getBoolean(strCreeperCharge + "enable", true)) {
      double radius = getDouble(strCreeperCharge + "radius", 16);
      long delay = getLong(strCreeperCharge + "timer.delay", 10);
      long period = getLong(strCreeperCharge + "timer.period", 10);

      tasks.add(new CreeperCharge(radius).getTask().runTaskTimer(plugin, delay, period));
    }
    // Creeper Explode
    if (getBoolean(strCreeperExplode + "enable", true)) {
      double radius = getDouble(strCreeperExplode + "radius", 2.5);
      long delay = getLong(strCreeperExplode + "timer.delay", 10);
      long period = getLong(strCreeperExplode + "timer.period", 10);

      tasks.add(new CreeperExplode(radius).getTask().runTaskTimer(plugin, delay, period));
    }
    // Damage Critical
    if (getBoolean(strDamageCritical + "enable", true)) {
      registerListeners(new DamageCritical());
    }
    // Damage Give
    if (getBoolean(strDamageGive + "enable", true)) {
      double modifier = getDouble(strDamageGive + "modifier", 0.4, 0, 1);

      registerListeners(new DamageGive(modifier));
    }
    // Damage Take
    if (getBoolean(strDamageTake + "enable", true)) {
      // this is pure pain :(
      double defModifier = getDouble(strDamageTake + "modifier.default", 2.5, 1, 100);
      double playerModifier = getDouble(strDamageTake + "modifier.player", defModifier, 1, 100);
      double rangedModifier = getDouble(strDamageTake + "modifier.melee", defModifier, 1, 100);
      double fallModifier = getDouble(strDamageTake + "modifier.fall", defModifier, 1, 100);
      double magicModifier = getDouble(strDamageTake + "modifier.magic", defModifier, 1, 100);
      double envModifier = getDouble(strDamageTake + "modifier.environment", defModifier, 1, 100);
      double passiveMobModifier = getDouble(strDamageTake + "modifier.mob.passive", defModifier, 1, 100);
      double hostileMobDefModifier = getDouble(strDamageTake + "modifier.mob.hostile.default", defModifier, 1, 100);
      double zombieModifier = getDouble(strDamageTake + "modifier.mob.hostile.zombie", defModifier, 1, 100);
      double skeletonModifier = getDouble(strDamageTake + "modifier.mob.hostile.skeleton", defModifier, 1, 100);
      double endermanModifier = getDouble(strDamageTake + "modifier.mob.hostile.enderman", defModifier, 1, 100);

      registerListeners(new DamageTake(
          defModifier,
          playerModifier,
          rangedModifier,
          fallModifier,
          magicModifier,
          envModifier,
          passiveMobModifier,
          hostileMobDefModifier,
          zombieModifier,
          skeletonModifier,
          endermanModifier));
    }
    // Fast Air
    if (getBoolean(strFastAir + "enable", true)) {
      int modifier = getInt(strFastAir + "modifier", 40);

      registerListeners(new FastAir(modifier));
    }
    // Fire Forever
    if (getBoolean(strFireForever + "enable", true)) {
      registerListeners(new FireForever());
    }
    // Hunger Debuff
    if (getBoolean(strHungerDebuff + "enable", true)) {
      int foodLevel = getInt(strHungerDebuff + "food-level", 6, 0, 20);

      registerListeners(new HungerDebuff(foodLevel));
    }
    // Lava Death
    if (getBoolean(strLavaDeath + "enable", true)) {
      registerListeners(new LavaDeath(plugin));
    }
    // Mob Target
    if (getBoolean(strMobTarget + "enable", true)) {
      double range = getDouble(strMobTarget + "range", 16);
      long delay = getLong(strMobTarget + "timer.delay", 10);
      long period = getLong(strMobTarget + "timer.period", 10);

      tasks.add(new MobTarget(range).getTask().runTaskTimer(plugin, delay, period));
    }
    // No Fire Resistance
    if (getBoolean(strNoFireResistance + "enable", true)) {
      registerListeners(new NoFireResistance());
    }
    // No Sweep
    if (getBoolean(strNoSweep + "enable", true)) {
      registerListeners(new NoSweep());
    }
    // No Water
    if (getBoolean(strNoWater + "enable", true)) {
      long delay = getLong(strNoWater + "timer.delay", 10);
      long period = getLong(strNoWater + "timer.period", 10);

      NoWater noWater = new NoWater(plugin);
      registerListeners(noWater);
      tasks.add(noWater.getTask().runTaskTimer(plugin, delay, period));
    }
    // Sharp Cut
    if (getBoolean(strSharpCut + "enable", true)) {
      boolean checkJump = getBoolean(strSharpCut + "condition.jump", true);
      boolean checkRun = getBoolean(strSharpCut + "condition.run", true);
      boolean checkSword = getBoolean(strSharpCut + "check.sword", true);
      boolean checkAxe = getBoolean(strSharpCut + "check.axe", true);
      boolean checkShear = getBoolean(strSharpCut + "check.shear", true);

      registerListeners(new SharpCut(checkJump, checkRun, checkSword, checkAxe, checkShear));
    }
    // Uncooked Poison
    if (getBoolean(strUncookedPoison + "enable", true)) {
      int duration = getInt(strUncookedPoison + "duration", 30);

      registerListeners(new UncookedPoison(duration));
    }
  }

  public void terminate() {
    unregisterAllListeners();
    for (BukkitTask task : tasks) {
      task.cancel();
    }
  }
}
