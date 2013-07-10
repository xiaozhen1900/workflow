package cn.wsn.framework.workflow.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the jsxmsp database table.
 * 
 */
@Entity
@Table(name="wsn_jsxmsp")
public class Jsxmsp implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private String xmbh;

	@Column(nullable=false, length=200)
	private String xmmc;
	
	@Column(nullable=false, length=200)
	private String jsdw;
	
	@Column(nullable=false, length=200)
	private String jsdd;
	
	@Column(length=50)
	private String processinstanceid;
	
	@Column(nullable=false, length=50)
	private String cjr;

	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="cjsj", nullable=false)
	private Date cjsj;

	@Column(length=50)
	private String xgr;

	private Date xgsj;

    public Jsxmsp() {
    }

	public String getXmbh() {
		return this.xmbh;
	}

	public void setXmbh(String xmbh) {
		this.xmbh = xmbh;
	}

	public String getCjr() {
		return this.cjr;
	}

	public void setCjr(String cjr) {
		this.cjr = cjr;
	}

	public Date getCjsj() {
		return this.cjsj;
	}

	public void setCjsj(Date cjsj) {
		this.cjsj = cjsj;
	}

	public String getJsdd() {
		return this.jsdd;
	}

	public void setJsdd(String jsdd) {
		this.jsdd = jsdd;
	}

	public String getJsdw() {
		return this.jsdw;
	}

	public void setJsdw(String jsdw) {
		this.jsdw = jsdw;
	}

	public String getProcessinstanceid() {
		return this.processinstanceid;
	}

	public void setProcessinstanceid(String processinstanceid) {
		this.processinstanceid = processinstanceid;
	}

	public String getXgr() {
		return this.xgr;
	}

	public void setXgr(String xgr) {
		this.xgr = xgr;
	}

	public Date getXgsj() {
		return this.xgsj;
	}

	public void setXgsj(Date xgsj) {
		this.xgsj = xgsj;
	}

	public String getXmmc() {
		return this.xmmc;
	}

	public void setXmmc(String xmmc) {
		this.xmmc = xmmc;
	}

}