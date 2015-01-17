package com.lohasle.baseframe.s4m3.pojo;

/**
 * 拖动配置条目pojo
 * @author ganmin
 * @value dragId 拖动条目ID
 * @value configId 配置ID
 * @value type 操作类型 (add-添加；del-删除)
 * @value staffId 操作工号
 */
public class DragConfig {
	private String dragId;
	private String configId;
	private String type;
	private String staffId;

	/** default constructor */
	public DragConfig() {
	}

	/** full constructor */
	public DragConfig(String dragId, String configId, String type,
			String staffId) {
		super();
		this.dragId = dragId;
		this.configId = configId;
		this.type = type;
		this.staffId = staffId;
	}

	public String getDragId() {
		return dragId;
	}

	public void setDragId(String dragId) {
		this.dragId = dragId;
	}

	public String getConfigId() {
		return configId;
	}

	public void setConfigId(String configId) {
		this.configId = configId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
}
