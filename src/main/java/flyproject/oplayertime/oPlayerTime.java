package flyproject.oplayertime;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.SimpleDateFormat;
import java.util.*;

public final class oPlayerTime extends JavaPlugin {

    private static oPlayerTime instance;

    public static oPlayerTime getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        instance = this;
        Bukkit.getPluginManager().registerEvents(new oListener(),this);
        getCommand("oplayertime").setExecutor(new oCommand());
        getCommand("opt").setExecutor(new oCommand());
        Bukkit.getScheduler().runTaskTimerAsynchronously(this,() -> {
            Date date = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0); calendar.set(Calendar.SECOND, 0);
            Date zero = calendar.getTime();
            if (date.after(zero)){
                getLogger().info("[数据] oPlayerTime 在线限制数据已于0:00自动清空");
                oDatabase.clear();
            }
            for (Player player : Bukkit.getOnlinePlayers()){
                if (!oDatabase.has(player)){
                    List<Long> map = new ArrayList<>();
                    for (String key : oPlayerTime.getInstance().getConfig().getConfigurationSection("permissions").getKeys(false)){
                        if (player.hasPermission("oplayertime." + key)){
                            map.add(oPlayerTime.getInstance().getConfig().getLong("permissions." + key));
                        }
                    }
                    map.sort(Comparator.reverseOrder());
                    if (map.size()==0) return;
                    oDatabase.set(player,map.get(0));
                } else {
                    oDatabase.add(player,-1);
                    if (oDatabase.get(player)==0){
                        player.kickPlayer(color("&c&loPlayerTime\n" +
                                "&e&l您已超出服务器规定您的在线时间\n" +
                                "&e&l系统已将您自动踢出\n" +
                                "&e&l数据将在0:00分自动更新 或联系服务器管理员清空"));
                    }
                }
            }
        },60L * 20L,60L *20L);
        // Plugin startup logic

    }

    public static String color(String text){
        return ChatColor.translateAlternateColorCodes('&',text);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
