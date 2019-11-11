package gtlugo.solarsorcery.init.item.wand.wandparts;


import java.util.ArrayList;
import java.util.List;

public interface IPartPosition {
    List<IPartPosition> RENDER_LAYERS = new ArrayList<>();

    String getTexturePrefix();

    String getModelIndex();
}