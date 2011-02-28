package caseus.Varietree;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

import org.bukkit.plugin.Plugin;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;

public class Varietree extends JavaPlugin {
    private final VarietreeBlockListener blockListener = new VarietreeBlockListener(this);
    public static PermissionHandler Permissions = null;
    
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
	    // Register our events
	    PluginManager pm = getServer().getPluginManager();
	    pm.registerEvent(Event.Type.BLOCK_RIGHTCLICKED, blockListener, Priority.Normal, this);
	    // EXAMPLE: Custom code, here we just output some info so we can check all is well
	    PluginDescriptionFile pdfFile = this.getDescription();
	    System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
	    setupPermissions();
	}
}