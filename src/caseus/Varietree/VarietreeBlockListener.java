package caseus.Varietree;

import java.util.Random;

import org.bukkit.TreeType;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.block.BlockRightClickEvent;
import org.bukkit.event.block.BlockListener;

import caseus.Varietree.Varietree;

public class VarietreeBlockListener extends BlockListener {
	public static Varietree plugin;
	public static final TreeType[] TREETYPES = TreeType.values();
	public static final int NUM_TREETYPES = TREETYPES.length;
	public static final Random RANDOM = new Random();
	
	public static TreeType randomTreeType(){
		return TREETYPES[RANDOM.nextInt(NUM_TREETYPES)];
	}
	
	public VarietreeBlockListener(Varietree instance){
		plugin = instance;
	}
	
	public void onBlockRightClick(BlockRightClickEvent event){
		Block block = event.getBlock();
		ItemStack item = event.getItemInHand();
		
		if(block.getType() == Material.SAPLING){
			if(item.getType() == Material.INK_SACK){
				if(item.getData().getData() == 0x0){
					item.setAmount(item.getAmount()-1);
					block.setType(Material.AIR);
					World world = block.getWorld();
					if(!world.generateTree(block.getLocation(), randomTreeType())){
						block.setType(Material.SAPLING);
					}
				}
			}
		}
	}
}