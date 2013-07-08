package cn.wsn.framework.workflow.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the procdef_group database table.
 * 
 */
@Entity
@Table(name="wsn_procdef_group")
public class ProcdefGroup implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(nullable=false, length=64)
	private String groupid;

	@Column(nullable=false, length=64)
	private String processdefinitionid;

    public ProcdefGroup() {
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

}