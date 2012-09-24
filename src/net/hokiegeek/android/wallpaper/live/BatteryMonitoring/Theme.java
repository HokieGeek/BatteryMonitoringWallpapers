package net.hokiegeek.android.wallpaper.live.BatteryMonitoring;

public class Theme {
	private String name = null;
	private Integer id = -1;
    private int backgroundResource = -1;
    private int indicatorResource = -1;
    private int textPosition = -1;
    
    public Theme(String name, int background, int indicator) {
    	setName(name);
        setBackgroundResource(background);
        setIndicatorResource(indicator);
    }

	public void setBackgroundResource(int backgroundResource) {
		this.backgroundResource = backgroundResource;
	}

	public int getBackgroundResource() {
		return backgroundResource;
	}

	public void setIndicatorResource(int indicatorResource) {
		this.indicatorResource = indicatorResource;
	}

	public int getIndicatorResource() {
		return indicatorResource;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}
	
	@Override
	public String toString() {
		return this.name+" ("+this.id+")";
	}

	public void setTextPosition(int textPosition) {
		this.textPosition = textPosition;
	}

	public int getTextPosition() {
		return textPosition;
	}
}
