package com.ndi.model;

public class Node {
    private String id;
    private Node child;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Node getChild() {
        return child;
    }

    public void setChild(Node child) {
        this.child = child;
    }
}
