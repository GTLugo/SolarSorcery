package gtlugo.solarsorcery.handlers;

import gtlugo.solarsorcery.SolarSorcery;
import gtlugo.solarsorcery.lib.Reference;
import gtlugo.solarsorcery.playerdata.IPlayerData;
import gtlugo.solarsorcery.playerdata.PlayerData;
import gtlugo.solarsorcery.playerdata.PlayerDataProvider;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public final class HUDHandler {
	
	//add resource locations here
	private static final ResourceLocation TAG_MANABAR = SolarSorcery.getId("textures/gui/mana_globe.png");
	
	/*
	 * tex = texture size
	 * loc = location on texture sheet
	 * pos = position on screen
	 */
	private final int _tex_Width = 71, _tex_Height = 85;
	
	private final int _tex_globeWidth = 71, _tex_globeHeight = 45,
					  _loc_globeX = 0, _loc_globeY = 0;
	
	private final int _tex_manaWidth = 39, _tex_manaHeight = 39,
					  _loc_manaX = 0, _loc_manaY = 46;
	
	
	@SubscribeEvent
	public void onDraw(RenderGameOverlayEvent.Pre event) {
		World world = Minecraft.getInstance().world;
		if (world.isRemote) {
			if (event.getType() == ElementType.HOTBAR) {

				MainWindow window = event.getWindow();
				float partialTicks = event.getPartialTicks();
				//Stuff here
				drawManaBar(window, partialTicks);
			}
		}
	}

	public void drawManaBar(MainWindow window, float pticks) {
		Minecraft mc = Minecraft.getInstance();
		World world = mc.world;
		if (!world.isRemote) return;
		PlayerEntity player = mc.player;
		FontRenderer font = mc.fontRenderer;
		//OpenGlHelper glHelper;
		PlayerData.getPlayerData(player).ifPresent(data -> {

			mc.getTextureManager().bindTexture(TAG_MANABAR);


			//int scaleFactor = scaledRes.getScaleFactor();
			int maxX = window.getScaledWidth();
			int maxY = window.getScaledHeight();

			int pad = 3;

			String currentManaStr;
			String maxManaStr;

			double currMana = data.getCurrMana();
			currentManaStr = valueWithPrefix(currMana);

			double maxMana = data.getMaxMana();
			maxManaStr = valueWithPrefix(maxMana);

			String centerLineStr = "------";
			String levelStr = String.valueOf(data.getLevel());

			/*
			 * Calculating the height of the mana fluid inside the globe
			 */
			double percentMana = data.getCurrMana() / data.getMaxMana();

			//double fullRadius = 56.0 / 2.0;
			//double percentAngle = (2.0 * Math.PI) * ( 1.0 - percentMana); //angle goes from 0 - 360 degrees representing a half-circle

			int manaHeight = (int) Math.round((double) _tex_manaHeight * heightFunc(percentMana));
			//int manaHeight = (int) (_tex_manaHeight * (data.getCurrMana() / data.getMaxMana()));
			int manaCalculatedVertOffset = _tex_manaHeight - manaHeight; //for finding the correct place to start rendering the mana fluid on the vertical axis


			int pos_globeX = ((maxX / 2) + (_tex_globeWidth / 2)) + 100;
			int pos_globeY = (maxY - _tex_globeHeight) /*- pad*/;


			/*
			 * Mana Fluid
			 */
			AbstractGui.blit(pos_globeX + 29, (pos_globeY + 0 + manaCalculatedVertOffset) + 2, _loc_manaX, (_loc_manaY + manaCalculatedVertOffset), _tex_manaWidth, manaHeight, _tex_Width, _tex_Height); //+6 offset is for correctly positioning the mana fluid

			/*
			 * Mana Globe
			 */
			AbstractGui.blit(pos_globeX, pos_globeY, _loc_globeX, _loc_globeY, _tex_globeWidth, _tex_globeHeight, _tex_Width, _tex_Height);

			int pos_globeCenterX = (pos_globeX + (_tex_globeWidth - 23)); // 23 is the radius of the actual orb
			int pos_globeCenterY = (pos_globeY + (_tex_globeHeight / 2));

			/*
			 * Center Line
			 */
			//int pos_centerLineX = (pos_globeCenterX - (font.getStringWidth(centerLineStr) / 2));
			//int pos_centerLineY = ((pos_globeCenterY - (font.FONT_HEIGHT / 2)) - 5); //the - 5 is to raise the centerline to avoid the numbers spilling over the level indicator
			//font.drawStringWithShadow(centerLineStr, pos_centerLineX, pos_centerLineY, Integer.parseInt("FFD800", 16)); //these - y offsets are to more properly center the text vertically on the bar
			/*
			 * Current Mana Number
			 */
			int pos_currManaNumX = (pos_globeCenterX - (font.getStringWidth(currentManaStr) / 2));
			int pos_currManaNumY = (pos_globeCenterY - (font.FONT_HEIGHT) /*+ 3*/); //the 3 is to make the number closer to the center line
			font.drawStringWithShadow(currentManaStr, pos_currManaNumX, pos_currManaNumY, Integer.parseInt("FFD800", 16));
			/*
			 * Max Mana Number
			 */
			int pos_maxManaNumX = (pos_globeCenterX - (font.getStringWidth(maxManaStr) / 2));
			int pos_maxManaNumY = (pos_globeCenterY + (font.FONT_HEIGHT - 8)); //the 1 is to make the number closer to the center line
			font.drawStringWithShadow(maxManaStr, pos_maxManaNumX, pos_maxManaNumY, Integer.parseInt("FFD800", 16));
			/*
			 * Level Number
			 */
			int pos_levelNumX = pos_globeX
					+ 6 /*top left corner of the level "screen" section relative to texture*/
					+ (22 / 2) /*center of level "screen" section relative to itself*/
					- (font.getStringWidth(levelStr) / 2);
			int pos_levelNumY = pos_globeY
					+ 25  /*top left corner of the level "screen" section relative to texture*/
					+ (17 / 2) /*center of level "screen" section relative to itself*/
					- (font.FONT_HEIGHT / 2);
			font.drawString(levelStr, pos_levelNumX, pos_levelNumY, Integer.parseInt("D73800", 16)); //these - y offsets are to more properly center the text vertically on the bar

			//}

		});
	}
	
	/*
	 * Returns the height of a fluid in a spherical tank when given the volume of the fluid and the radius of the tank.
	 */
	public double heightFunc(double x) {
		double x3 = (1.04167 * Math.pow(x, 3.0));
		double x2 = (1.5625 * Math.pow(x, 2.0));
		double x1 = (1.52083 * x);
		double h = (x3 - x2 + x1);
		return h;
	}
	
	public String valueWithPrefix(double value) {
		int count = 0;
		int beforeDecimal;
		int afterDecimal1;
		int afterDecimal2;
		String prefix;
		String display;
		
		while (value >= 1000.0) {
			value /= 1000.0;
			++count;
		}

		beforeDecimal = (int) value;
		if (value < 100.0) {
			double temp;
			temp = (value - beforeDecimal);
			
			if (value < 10.0) { // if 1.00 to 9.99
				afterDecimal1 = (int) (temp * 10.0);
				temp = ((temp * 10.0) - afterDecimal1);
				afterDecimal2 = (int) (temp * 10.0);
			}
			else { // if 10.0 to 99.9
				afterDecimal1 = (int) (temp * 10.0);
				afterDecimal2 = -1;
			}
		}
		else { // if 100 to 999
			afterDecimal1 = -1;
			afterDecimal2 = -1;
		}
		
		switch (count) {
		case 0:
			prefix = "";
			break;
		case 1:
			prefix = "K";
			break;
		case 2:
			prefix = "M";
			break;
		default:
			prefix = "G?";
		}
		
		display = String.valueOf(beforeDecimal);
		if (afterDecimal1 >= 0) display += ("." + String.valueOf(afterDecimal1));
		if (afterDecimal2 >= 0) display += String.valueOf(afterDecimal2);
		display += prefix;
		
		return display;
	}

	public static void tick() {

		//if (remainingTime > 0)
		//	--remainingTime;
	}

	//add methods
}
