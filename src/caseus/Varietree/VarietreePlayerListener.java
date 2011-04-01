package caseus.Varietree;

import java.util.Random;

import org.bukkit.TreeType;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;

import caseus.Varietree.Varietree;

public class VarietreePlayerListener extends PlayerListener {
	public static Varietree plugin;
	public static final Random RANDOM = new Random();
	
	public static TreeType randomTreeType(){
		int rand = RANDOM.nextInt(Varietree.maxChance);
		
		for(int i = 0; i < Varietree.treeTypes.size(); i ++){
			if(rand < Varietree.chance.get(i)){
				return Varietree.treeTypes.get(i);
			}
		}
		
		return Varietree.treeTypes.get(0);
	}
	
	public VarietreePlayerListener(Varietree instance){
		plugin = instance;
	}
	
	private boolean hasPermission(Player player, String permission){
		if(Varietree.Permissions == null){
			return true;
		}
		
		return Varietree.Permissions.has(player, permission);
	}
	
	public void onPlayerInteract(PlayerInteractEvent event){
		if(!event.hasBlock() || !event.hasItem()){
			return;
		}
		
		Block block = event.getClickedBlock();
		ItemStack item = event.getItem();
		
		if(block.getType() == Material.SAPLING){
			if(item.getType() == Material.INK_SACK){
				if(item.getData().getData() == 0x0){
					
					event.setCancelled(true);
					
					Player player = event.getPlayer();
					
					TreeType t = TreeType.TREE;
					
					if(hasPermission(player, "varietree.random")){
						t = randomTreeType();
					}
					
					int amt = item.getAmount();
					if(amt == 1){
						player.getInventory().remove(item);
					}else{
						item.setAmount(amt-1);
					}
					
					block.setType(Material.AIR);
					World world = block.getWorld();
					if(!world.generateTree(block.getLocation(), t)){
						block.setType(Material.SAPLING);
					}
				}
			}
		}
	}
}
