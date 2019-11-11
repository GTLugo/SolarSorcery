package gtlugo.solarsorcery.init.item.wand.wandparts;

public enum PartPosition implements IPartPosition {
    WOOD("wood", "wood", true),
    CORE("core", "core", false),
    DECO("deco", "deco", true);
/*
    public static final Map<PartPositions, WandPart> LITE_MODEL_LAYERS = ImmutableMap.<PartPositions, WandPart>builder()
            .put(ROD, WandPart.ROD)
            .put(GRIP, PartType.GRIP)
            .put(HEAD, PartType.MAIN)
            .put(TIP, PartType.TIP)
            .put(BOWSTRING, PartType.BOWSTRING)
            .build();
*/
    private final String texturePrefix;
    private final String modelKey;

    PartPosition(String texture, String model, boolean isRenderLayer) {
        this.texturePrefix = texture;
        this.modelKey = model;

        if (isRenderLayer) RENDER_LAYERS.add(this);
    }

    @Override
    public String getTexturePrefix() {
        return texturePrefix;
    }

    @Override
    public String getModelIndex() {
        return modelKey;
    }
}
