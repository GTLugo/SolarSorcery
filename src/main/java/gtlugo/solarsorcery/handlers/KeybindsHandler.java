package gtlugo.solarsorcery.handlers;

import org.lwjgl.input.Keyboard;

import gtlugo.solarsorcery.networking.messages.LevelChangeMessage;
import gtlugo.solarsorcery.networking.messages.ManaChangeMessage;
import gtlugo.solarsorcery.playerdata.IPlayerData;
import gtlugo.solarsorcery.playerdata.PlayerData;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class KeybindsHandler {
    public static KeyBinding[] _keyBindings;

	public static void init() {
        _keyBindings = new KeyBinding[4]; 

        // instantiate the key bindings
        _keyBindings[0] = new KeyBinding("Add Aether", Keyboard.KEY_EQUALS, "Solar Sorcery");
        _keyBindings[1] = new KeyBinding("Subtract Aether", Keyboard.KEY_MINUS, "Solar Sorcery");

        _keyBindings[2] = new KeyBinding("Increase level", Keyboard.KEY_RBRACKET, "Solar Sorcery");
        _keyBindings[3] = new KeyBinding("Decrease level", Keyboard.KEY_LBRACKET, "Solar Sorcery");
       
        // register all the key bindings
        for (int i = 0; i < _keyBindings.length; ++i) {
        	ClientRegistry.registerKeyBinding(_keyBindings[i]);
        }
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onEvent(KeyInputEvent event) {
		
		if (_keyBindings[0].isKeyDown()) { //("Add Aether", Keyboard.KEY_EQUALS, "Solar Sorcery")
			NetworkHandler.INSTANCE.sendToServer(new ManaChangeMessage(1.0f));
	    }
	    
	    if (_keyBindings[1].isKeyDown()) { //("Remove Aether", Keyboard.KEY_MINUS, "Solar Sorcery")
			NetworkHandler.INSTANCE.sendToServer(new ManaChangeMessage(-1.0f));
	    }
	    
	    if (_keyBindings[2].isKeyDown()) { //("Increase level", Keyboard.KEY_RBRACKET, "Solar Sorcery")
			NetworkHandler.INSTANCE.sendToServer(new LevelChangeMessage(1, true));
	    }
	    
	    if (_keyBindings[3].isKeyDown()) { //("Decrease level", Keyboard.KEY_LBRACKET, "Solar Sorcery")
			NetworkHandler.INSTANCE.sendToServer(new LevelChangeMessage(-1, true));
	    }
	}
}
