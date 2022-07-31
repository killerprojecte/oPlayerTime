package flyproject.oplayertime;

import org.bukkit.OfflinePlayer;

import java.util.HashMap;
import java.util.Map;

public class oDatabase {
    private static Map<OfflinePlayer,Long> data_map = new HashMap<>();

    public static void set(OfflinePlayer player,long data){
        data_map.put(player,data);
    }

    public static void remove(OfflinePlayer player){
        data_map.remove(player);
    }

    public static void add(OfflinePlayer player,long data){
        Long old = get(player);
        if (old==null){
            data_map.put(player,data);
        } else {
            data_map.put(player,old + data);
        }
    }

    public static Long get(OfflinePlayer player){
        if (data_map.containsKey(player)){
            return data_map.get(player);
        }
        return null;
    }

    public static void clear(){
        data_map = new HashMap<>();
    }

    public static boolean has(OfflinePlayer player){
        return data_map.containsKey(player);
    }
}
