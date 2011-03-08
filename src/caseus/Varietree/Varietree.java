package caseus.Varietree;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

import org.bukkit.TreeType;
import org.bukkit.plugin.Plugin;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.util.config.Configuration;

public class Varietree extends JavaPlugin {
    private final VarietreeBlockListener blockListener = new VarietreeBlockListener(this);
    private boolean usePermissions;
    
    public static PermissionHandler Permissions = null;
    
    public static int maxChance = 0;
    public static List<Integer> chance = new ArrayList<Integer>();
    public static List<TreeType> treeTypes = new ArrayList<TreeType>(Arrays.asList(TreeType.values()));
    
    public void setupConfig(){
    	try	{
			File file = new File (getDataFolder(), "config.yml");
			if (!file.exists())	{
				file.getParentFile().mkdirs();
				FileWriter writer = new FileWriter(file);
				String crlf = System.getProperty("line.separator");
				
				String defaultConfig = "permissions: false" + crlf
									+  "chance:";
				for(int i = 0; i < treeTypes.size(); i++){
					defaultConfig += crlf + "  " + treeTypes.get(i).name() + ": 1";
				}
				
				writer.write(defaultConfig);
				writer.close();
			}
			Configuration config = getConfiguration();
			config.load();
			usePermissions = config.getBoolean("permissions", false);
			
			for(int i=0; i < treeTypes.size(); i++){
				maxChance += config.getInt("chance."+treeTypes.get(i).name(), 0);
				chance.add(maxChance);
			}
        } catch (Exception e) {
			getServer().getLogger().log(Level.SEVERE, "Varietree: exception while reading config.yml", e);
        	getServer().getPluginManager().disablePlugin(this);
		}
    }
    
    public void setupPermissions(){
    	Plugin test = this.getServer().getPluginManager().getPlugin("Permissions");
    	
    	if(Varietree.Permissions == null){
    		if(test != null){
    			this.getServer().getPluginManager().enablePlugin(test);
    			Varietree.Permissions = ((Permissions) test).getHandler();
    		}
    	}
    	
    	if(Varietree.Permissions != null){
	    	PluginDescriptionFile pdfFile = this.getDescription();
		    System.out.println( pdfFile.getName() + ": Permissions support enabled" );
    	}
    }
    
	public void onDisable() {
	    System.out.println("Goodbye Varietree!");
	}
	
	public void onEnable() {
		setupConfig();
		if(usePermissions){
			setupPermissions();
		}
	    // Register our events
	    PluginManager pm = getServer().getPluginManager();
	    pm.registerEvent(Event.Type.BLOCK_RIGHTCLICKED, blockListener, Priority.Normal, this);
	    // EXAMPLE: Custom code, here we just output some info so we can check all is well
	    PluginDescriptionFile pdfFile = this.getDescription();
	    System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
	}
}