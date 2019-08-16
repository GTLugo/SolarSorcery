package gtlugo.solarsorcery.handlers;

import gtlugo.solarsorcery.lib.Reference;
import gtlugo.solarsorcery.networking.LevelChangeMessage;
import gtlugo.solarsorcery.networking.ManaChangeMessage;
import net.minecraft.client.KeyboardListener;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.InputEvent.KeyInputEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

//import gtlugo.solarsorcery.networking.LevelChangeMessage;
//import gtlugo.solarsorcery.networking.ManaChangeMessage;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

public class KeybindsHandler {
    public static KeyBinding[] _keyBindings;

	public static void init() {
        _keyBindings = new KeyBinding[4]; 

        // instantiate the key bindings
        _keyBindings[0] = new KeyBinding("Add Aether", GLFW.GLFW_KEY_EQUAL, Reference.MOD_ID);
        _keyBindings[1] = new KeyBinding("Subtract Aether", GLFW.GLFW_KEY_MINUS, Reference.MOD_ID);

        _keyBindings[2] = new KeyBinding("Increase level", GLFW.GLFW_KEY_RIGHT_BRACKET, Reference.MOD_ID);
        _keyBindings[3] = new KeyBinding("Decrease level", GLFW.GLFW_KEY_LEFT_BRACKET, Reference.MOD_ID);
       
        // register all the key bindings
        for (int i = 0; i < _keyBindings.length; ++i) {
        	ClientRegistry.registerKeyBinding(_keyBindings[i]);
        }
	}

	@SubscribeEvent
	public void onEvent(KeyInputEvent event) {
		if (_keyBindings[0].isKeyDown()) { //("Add Aether", Keyboard.KEY_EQUALS, "Solar Sorcery")
			NetworkHandler.sendToServer(new ManaChangeMessage(1.0f));
		}

		if (_keyBindings[1].isKeyDown()) { //("Remove Aether", Keyboard.KEY_MINUS, "Solar Sorcery")
			NetworkHandler.sendToServer(new ManaChangeMessage(-1.0f));
		}

		if (_keyBindings[2].isKeyDown()) { //("Increase level", Keyboard.KEY_RBRACKET, "Solar Sorcery")
			NetworkHandler.sendToServer(new LevelChangeMessage(new LevelChangeMessage.LevelChangeData(1, true)));
		}

		if (_keyBindings[3].isKeyDown()) { //("Decrease level", Keyboard.KEY_LBRACKET, "Solar Sorcery")
			NetworkHandler.sendToServer(new LevelChangeMessage(new LevelChangeMessage.LevelChangeData(-1, true)));
		}
	}
}
