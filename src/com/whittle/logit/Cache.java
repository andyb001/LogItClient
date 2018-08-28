package com.whittle.logit;

import java.util.List;

import com.whittle.logit.dto.ItemTypeDTO;

public class Cache {
	
	private static Cache cache;
	private List<ItemTypeDTO> itemTypes = null;
	private MainPanel mainPanel = null;
	
	private Cache() {
		itemTypes = Service.getItemTypes();
	}
	
	public static Cache getInstance() {
		if (cache == null) {
			cache = new Cache();
		}
		return cache;
	}

	public List<ItemTypeDTO> getItemTypes() {
		return itemTypes;
	}

	public void setItemTypes(List<ItemTypeDTO> itemTypes) {
		this.itemTypes = itemTypes;
	}

	public MainPanel getMainPanel() {
		return mainPanel;
	}

	public void setMainPanel(MainPanel mainPanel) {
		this.mainPanel = mainPanel;
	}

}
