package android.luna.Data.module;

public class RecipeInfo {
	private int ingredientId;
	private float time;
	private float volume;

	public RecipeInfo(int ingredientId, float time, float volume) {
		super();
		this.time = time;
		this.volume = volume;
		this.ingredientId = ingredientId;
	}

	public float getTime() {
		return time;
	}

	public void setTime(float time) {
		this.time = time;
	}

	public float getVolume() {
		return volume;
	}

	public void setVolume(float volume) {
		this.volume = volume;
	}

	public int getIngredientId() {
		return ingredientId;
	}

	public void setIngredientId(int ingredientId) {
		this.ingredientId = ingredientId;
	}

	@Override
	public String toString() {
		return "RecipeInfo [time=" + time + ", volume=" + volume + "]";
	}
}
