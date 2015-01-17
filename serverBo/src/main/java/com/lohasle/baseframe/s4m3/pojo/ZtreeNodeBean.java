package com.lohasle.baseframe.s4m3.pojo;

import java.util.List;

/**
 * Z - tree 树 节点对象
 * simple json
 * @author 付乐
 * @createTime 2013-7-1
 */
public class ZtreeNodeBean {
    // 节点名称
    private String name;
    // 是否展开 默认不展开
    private boolean         open = false;
    // 没有子节点的parent
    private boolean         isParent;
    
    
    // 相对根目录的路径
    private String path;
    
    
    //图标路径
    private String icon;
    
    
    
    // 子节点
    private List<ZtreeNodeBean> children;
    
    
    
    
    public ZtreeNodeBean(){}
    
    
    public String getIcon() {
        return icon;
    }
    
    





    public void setIcon(String icon) {
        this.icon = icon;
    }





    public String getPath() {
        return path;
    }
    
    public void setPath(String path) {
        this.path = path;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public boolean isOpen() {
        return open;
    }
    
    public void setOpen(boolean open) {
        this.open = open;
    }
    
    public boolean getisParent() {
        return isParent;
    }
    
    public void setisParent(boolean isParent) {
        this.isParent = isParent;
    }
    
    public List<ZtreeNodeBean> getChildren() {
        return children;
    }
    
    public void setChildren(List<ZtreeNodeBean> children) {
        this.children = children;
    }
    
    
    
}
