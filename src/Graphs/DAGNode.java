package Graphs;

import java.util.ArrayList;

/**
 * Directed acyclic graph containing data of type T.
 * The graph is only checked on integrity if enforceDirectedAcyclic is true
 * @param <T> Type of data carried by the nodes
 */
public class DAGNode<T> {
    public static boolean enforceDirectedAcyclic = false;
    protected ArrayList<DAGNode<T>> parents = new ArrayList<>();
    protected ArrayList<DAGNode<T>> children = new ArrayList<>();

    protected T data;

    public DAGNode(T data){
        this();
        this.data = data;
    }

    public DAGNode(){
    }

    public void addChild(DAGNode<T> child) throws DAGViolation {
        if(enforceDirectedAcyclic){
            if(children.contains(child)) {
                throw new DAGViolation("Passed child object is already a child:" + child.toString());
            }
            if(child.hasChild(this)){
                throw new DAGViolation("Cycle found containing " + child.toString() + " and " + this.toString());
            }
        }
        children.add(child);
        child.addParent(this);
    }

    protected void addParent(DAGNode<T> parent) throws DAGViolation {
        if(enforceDirectedAcyclic){
            if(parents.contains(parent)) {
                throw new DAGViolation("Passed parent object is already a parent:" + parent.toString());
            }
            if(!parent.children.contains(this)){
                throw new DAGViolation("Trying to add a parent, without the child being in " + parent.toString());
            }
        }
        parents.add(parent);
    }

    public boolean hasChild(DAGNode<T> child){
        for(DAGNode<T> c : children){
            if(c == child)
                return true;
            else if(c.hasChild(child))
                return true;
        }
        return false;
    }

    public ArrayList<DAGNode<T>> getParents() {
        return parents;
    }

    public ArrayList<DAGNode<T>> getChildren() {
        return children;
    }

    public T getData() {
        return data;
    }

    public void setData(T data){
        this.data = data;
    }

    public boolean isLeaf(){
        return this.children.size() == 0;
    }
    public boolean isRoot(){
        return this.parents.size() == 0;
    }
}
