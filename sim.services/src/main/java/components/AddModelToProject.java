package components;

public class AddModelToProject {
	private int projectId;
	private int modelId;
	private String projectName;
	
	
	public AddModelToProject() {
		
	}
		
	public AddModelToProject(int projectId, String projectName) {
		this.projectId = projectId;
		this.projectName = projectName;
	}
	
	public int getProjectId() {
		return projectId;
	}
	
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	
	public int getModelId() {
		return modelId;
	}
	
	public void setModelId(int modelId) {
		this.modelId = modelId;
	}
	
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
}
