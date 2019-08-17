package com.wmeimob.fastboot.starter.common.entity;

import java.io.Serializable;

public class ZTreeNodeArray implements Serializable {
    private ZTreeNode[] nodes;

    public ZTreeNodeArray() {
    }

    public ZTreeNode[] getNodes() {
        return this.nodes;
    }

    public void setNodes(ZTreeNode[] nodes) {
        this.nodes = nodes;
    }
}
