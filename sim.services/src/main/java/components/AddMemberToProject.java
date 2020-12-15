package components;

public class AddMemberToProject {
	private int projectId;
	private int memberId;
	private String projectName;
	
	
	public AddMemberToProject() {
		
	}
		
	public AddMemberToProject(int projectId, String projectName) {
		this.projectId = projectId;
		this.projectName = projectName;
	}
	
	public int getProjectId() {
		return projectId;
	}
	
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	
	public int getMemberId() {
		return memberId;
	}
	
	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}
	
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	
	
}
