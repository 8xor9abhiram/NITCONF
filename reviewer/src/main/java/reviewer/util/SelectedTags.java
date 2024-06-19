package reviewer.util;

import java.util.ArrayList;

public class SelectedTags {
	
	private ArrayList<String> selectedTags = new ArrayList<String>();;
	
	
	public SelectedTags()
	{
		
	}

	public ArrayList<String> getSelectedTags() {
		return selectedTags;
	}

	public void setSelectedTags(ArrayList<String> selectedTags) {
		this.selectedTags = selectedTags;
	}
	
	

	@Override
	public String toString() {
		return "SelectedTags [selectedTags=" + selectedTags + "]";
	}

	
}
