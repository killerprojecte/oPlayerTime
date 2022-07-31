package flyproject.oplayertime;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class oCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("oplayertime.admin")){
            sender.sendMessage(oPlayerTime.color("&c你没有权限使用oPlayerTime的命令"));
            return false;
        }
        if (args.length==1){
            if (args[0].equals("clearall")){
                oDatabase.clear();
                sender.sendMessage(oPlayerTime.color("&c&l数据成功清空"));
            } else if (args[0].equals("reload")){
                oPlayerTime.getInstance().reloadConfig();
                sender.sendMessage(oPlayerTime.color("&a配置文件已重载"));
            }
        } else if (args.length==2){
            if (args[0].equals("clear")){
                oDatabase.remove(Bukkit.getOfflinePlayer(args[1]));
                sender.sendMessage(oPlayerTime.color("&a玩家" + args[1] + "的数据已被重置"));
            } else if (args[0].equals("get")){
                sender.sendMessage(oPlayerTime.color("&a玩家" + args[1] + " 的数据为: " + oDatabase.get(Bukkit.getOfflinePlayer(args[1]))));
            }
        } else if (args.length==3){
            if (args[0].equals("add")){
                oDatabase.add(Bukkit.getOfflinePlayer(args[1]),Long.parseLong(args[2]));
                sender.sendMessage(oPlayerTime.color("&a玩家" + args[1] + "的时长已被添加" + args[2]));
            } else if (args[0].equals("set")){
                oDatabase.set(Bukkit.getOfflinePlayer(args[1]),Long.parseLong(args[2]));
                sender.sendMessage(oPlayerTime.color("&a玩家" + args[1] + "的时长已被设置为" + args[2]));
            }
        } else {
            sender.sendMessage(oPlayerTime.color("" +
                    "&eoPlayerTime 由FlyProject维护" +
                    "&a/opt set <玩家> <数值> ———— 设置数值\n" +
                    "&a/opt add <玩家> <数值/-数值> ———— 添加数值\n" +
                    "&a/opt get <玩家> ———— 获取玩家数值\n" +
                    "&a/opt clear <玩家> ———— 清理玩家数值\n" +
                    "&a/opt clearall ———— 清空所有数值\n" +
                    "&a/opt reload ———— 重载插件\n" +
                    "&a/opt ———— 查看本帮助"));
        }
        return true;
    }
}
