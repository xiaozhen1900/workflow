package cn.wsn.framework.workflow.dto;



/**
 * The persistent class for the procdef_group database table.
 * 
 */
public class ProcdefGroupDto {
	
	private int id;

	private String groupid;

	private String processdefinitionid;

	private String groupName;
	
	private String processDefinitionName;
	
    public ProcdefGroupDto() {
    }

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGroupid() {
		return this.groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	public String getProcessdefinitionid() {
		return this.processdefinitionid;
	}

	public void setProcessdefinitionid(String processdefinitionid) {
		this.processdefinitionid = processdefinitionid;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getProcessDefinitionName() {
		return processDefinitionName;
	}

	public void setProcessDefinitionName(String processDefinitionName) {
		this.processDefinitionName = processDefinitionName;
	}
}