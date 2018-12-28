package tenebris.solarsorcery.cap.wandtype;

public class WandType implements IWandType {
	
	private String _wood = null;
	private String _core = null;
	private String _cap = null;

	@Override
	public String getWood() {
		return this._wood;
	}

	@Override
	public String getCore() {
		return this._core;
	}

	@Override
	public String getCap() {
		return this._cap;
	}

	@Override
	public void setWood(String wood) {
		if (this._wood == null) { //Only apply if the player doesn't already have it set
			this._wood = wood;
		}
	}

	@Override
	public void setCore(String core) {
		if (this._core == null) { //Only apply if the player doesn't already have it set
			this._core = core;
		}
	}

	@Override
	public void setCap(String cap) {
		if (this._cap == null) { //Only apply if the player doesn't already have it set
			this._cap = cap;
		}
	}

}
