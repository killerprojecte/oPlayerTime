package flyproject.oplayertime;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import static flyproject.oplayertime.oPlayerTime.color;

public class oListener implements Listener {
    @EventHandler
    public void onPreJoin(PlayerJoinEvent event){
        if (oDatabase.has(event.getPlayer())) return;
        if (oDatabase.get(event.getPlayer())==0){
            event.getPlayer().kickPlayer(color("&c&loPlayerTime\n" +
                    "&e&l您已超出服务器规定您的在线时间\n" +
                    "&e&l系统已将您自动踢出\n" +
                    "&e&l数据将在0:00分自动更新 或联系服务器管理员清空"));
        }
        List<Long> map = new ArrayList<>();
        for (String key : oPlayerTime.getInstance().getConfig().getConfigurationSection("permissions").getKeys(false)){
            if (event.getPlayer().hasPermission("oplayertime." + key)){
                map.add(oPlayerTime.getInstance().getConfig().getLong("permissions." + key));
            }
        }
        map.sort(Comparator.reverseOrder());
        if (map.size()==0) return;
        oDatabase.set(event.getPlayer(),map.get(0));
    }
}
