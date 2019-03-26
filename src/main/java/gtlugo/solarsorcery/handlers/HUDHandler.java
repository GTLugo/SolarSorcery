package gtlugo.solarsorcery.handlers;

import gtlugo.solarsorcery.Reference;
import gtlugo.solarsorcery.items.tools.IWandBase;
import gtlugo.solarsorcery.playerdata.IPlayerData;
import gtlugo.solarsorcery.playerdata.PlayerDataProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public final class HUDHandler {
	
	//add resource locations here
	private static final ResourceLocation TAG_MANABAR = new ResourceLocation(Reference.MODID, "textures/gui/mana_globe.png");
	
	/*
	 * tex = texture size
	 * loc = location on texture sheet
	 * pos = position on screen
	 */
	private final int _tex_Width = 117, _tex_Height = 137;
	
	private final int _tex_globeWidth = 68, _tex_globeHeight = 68, 
					  _loc_globeX = 0, _loc_globeY = 0;
	
	private final int _tex_manaWidth = 56, _tex_manaHeight = 56, 
					  _loc_manaX = 6, _loc_manaY = 75;
	
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onDraw(RenderGameOverlayEvent.Post event) {
		Minecraft mc = Minecraft.getMinecraft();
		EntityPlayer player = mc.player;

		if (event.getType() == ElementType.EXPERIENCE) {
			ScaledResolution resolution = event.getResolution(); 
			float partialTicks = event.getPartialTicks();
			//Stuff here
			drawManaBar(resolution, partialTicks);
		}
	}

	public static boolean shouldDisplayMana(ItemStack stack) {
		boolean decision = false;
		if ((stack.getItem() instanceof IWandBase) && !(stack.isEmpty())) {
			decision = true;
		}

		return decision;
	}
	
	@SideOnly(Side.CLIENT)
	public void drawManaBar(ScaledResolution res, float pticks) {
		Minecraft mc = Minecraft.getMinecraft();
		EntityPlayer player = mc.player;

		if (!shouldDisplayMana(player.getHeldItemMainhand()) && !shouldDisplayMana(player.getHeldItemOffhand())) {
			return;
		}

		FontRenderer font = mc.fontRenderer;
		//OpenGlHelper glHelper;
		ScaledResolution scaledRes = new ScaledResolution(mc);
		IPlayerData data = player.getCapability(PlayerDataProvider.TAG_DATA, null);
		mc.renderEngine.bindTexture(TAG_MANABAR);
		

		//int scaleFactor = scaledRes.getScaleFactor();
        int maxX = scaledRes.getScaledWidth();
        int maxY = scaledRes.getScaledHeight();
        
		int pad = 3;

		String currentManaStr;
		String maxManaStr;

		double currMana = data.getCurrMana();
		currentManaStr = valueWithPrefix(currMana);
		
		double maxMana = data.getMaxMana();
		maxManaStr = valueWithPrefix(maxMana);
		
		String centerLineStr = "——————";
    	String levelStr = String.valueOf(data.getLevel());
    	
    	/*
    	 * Calculating the height of the mana fluid inside the globe
    	 */
    	double percentMana = data.getCurrMana() / data.getMaxMana();
    	
		//double fullRadius = 56.0 / 2.0;
		//double percentAngle = (2.0 * Math.PI) * ( 1.0 - percentMana); //angle goes from 0 - 360 degrees representing a half-circle
		
		int manaHeight = (int) Math.round( (double) _tex_manaHeight * heightFunc(percentMana));
		//int manaHeight = (int) (_tex_manaHeight * (data.getCurrMana() / data.getMaxMana()));
		int manaCalculatedVertOffset = _tex_manaHeight - manaHeight; //for finding the correct place to start rendering the mana fluid on the vertical axis
		
		int pos_globeX = (maxX - _tex_globeWidth) - pad;
		int pos_globeY = (maxY - _tex_globeHeight) - pad;
		
		
		/*
		 * Mana Fluid
		 */
		Gui.drawModalRectWithCustomSizedTexture(pos_globeX + 6, (pos_globeY + 6 + manaCalculatedVertOffset), _loc_manaX,( _loc_manaY + manaCalculatedVertOffset), _tex_manaWidth, manaHeight, _tex_Width, _tex_Height); //+4 offset is for correctly positioning the mana fluid
		
		/*
		 * Mana Globe
		 */
		Gui.drawModalRectWithCustomSizedTexture(pos_globeX, pos_globeY, _loc_globeX, _loc_globeY, _tex_globeWidth, _tex_globeHeight, _tex_Width, _tex_Height); //+4 offset is for correctly positioning the mana fluid
		
		int pos_globeCenterX = ( pos_globeX + (_tex_globeWidth / 2) );
		int pos_globeCenterY = ( pos_globeY + (_tex_globeHeight / 2) );
		
		/*
		 * Center Line
		 */
		int pos_centerLineX = ( pos_globeCenterX - (font.getStringWidth(centerLineStr) / 2) );
		int pos_centerLineY = ( ( pos_globeCenterY - (font.FONT_HEIGHT / 2) ) - 5); //the 5 is to raise the centerline to avoid the numbers spilling over the level indicator
		font.drawStringWithShadow(centerLineStr, pos_centerLineX, pos_centerLineY, Integer.parseInt("FFD800", 16)); //these - y offsets are to more properly center the text vertically on the bar
		/*
		 * Current Mana Number
		 */
		int pos_currManaNumX = ( pos_globeCenterX - (font.getStringWidth(currentManaStr) / 2) );
		int pos_currManaNumY = ( pos_centerLineY - (font.FONT_HEIGHT) + 3 ); //the 3 is to make the number closer to the center line
		font.drawStringWithShadow(currentManaStr, pos_currManaNumX, pos_currManaNumY, Integer.parseInt("FFD800", 16));
		/*
		 * Max Mana Number
		 */
		int pos_maxManaNumX = ( pos_globeCenterX - (font.getStringWidth(maxManaStr) / 2) );
		int pos_maxManaNumY = ( pos_centerLineY + (font.FONT_HEIGHT - 1) ); //the 1 is to make the number closer to the center line
		font.drawStringWithShadow(maxManaStr, pos_maxManaNumX, pos_maxManaNumY, Integer.parseInt("FFD800", 16));
		/*
		 * Level Number
		 */
		int pos_levelNumX = pos_globeX + 42 + (27 / 2) - (font.getStringWidth(levelStr) / 2);
		int pos_levelNumY = pos_globeY + 42 + (27 / 2) - (font.FONT_HEIGHT / 2);
		font.drawString(levelStr, pos_levelNumX, pos_levelNumY, Integer.parseInt("D73800", 16)); //these - y offsets are to more properly center the text vertically on the bar
    	
    	//}
	}
	
	/*
	 * Returns the height of a fluid in a spherical tank when given the volume of the fluid and the radius of the tank.
	 */
	public double heightFunc(double x) {
		double x3 = (1.04167 * Math.pow(x, 3.0));
		double x2 = (1.5625 * Math.pow(x, 2.0));
		double x1 = (1.52083 * x);
		return (x3 - x2 + x1);
	}
	
	public String valueWithPrefix(double value) {
		int count = 0;
		int decimalPos = 0;
		int digit0;
		int digit1;
		int digit2;
		String prefix;
		String display;
		
		while (value >= 1000.0) {
			value /= 1000.0;
			++count;
		}

		int beforeDecimal = (int) value;
		if (value < 100.0) {
			double afterDecimal = (value - beforeDecimal);
			
			if (value < 10.0) { // 1.00 - 9.99
				digit0 = (int) value;
				digit1 = (int) (afterDecimal * 10.0);
				digit2 = (int) ((afterDecimal * 100.0) - (digit1 * 10));
				decimalPos = 2;
			}
			else { // 10.0 - 99.9
				digit0 = (int) (value / 10);
				digit1 = (int) (value - (digit0 * 10));
				digit2 = (int) (afterDecimal * 10.0);
				decimalPos = 1;
			}
		}
		else { // 100 - 999
			digit0 = (int) (value / 100);
			digit1 = (int) ((value / 10) - (digit0 * 10));
			digit2 = (int) (value - (digit1 * 10) - (digit0 * 100));
			//decimalPos = 3;
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

		display = digit0 + ((decimalPos == 2) ? "." : "")
				+ digit1 + ((decimalPos == 1) ? "." : "")
				+ digit2;
		display += prefix;
		
		return display;
	}

	public static void tick() {

		//if (remainingTime > 0)
		//	--remainingTime;
	}

	//add methods
}
