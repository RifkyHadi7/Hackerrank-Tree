import java.util.*;

class Folder{
    String name;

    public Folder(String name) {
        this.name = name;
    }
}
class File extends Folder{
    public File(String name) {
        super(name);
    }
}
class Node{
    Folder data;
    Node parent;
    ArrayList<Node> children;
    Node(Folder data, Node parent){
        this.data = data;
        this.parent = parent;
        children = new ArrayList<>();
    }
    Node(File data, Node parent){
        this.data = data;
        this.parent = parent;
        children = null;
    }

}

class Tree{
    Node root = new Node(new Folder("root"),null);
    Node pointer;
    public Tree(){
        pointer = root;
    }

    public void mkdir(String name){
        Folder newNode = new Folder(name);
        Node folder = new Node(newNode,pointer);
        if (pointer.children != null){
            for (int i = 0; i < pointer.children.size(); i++) {
                boolean check = pointer.children.get(i).data instanceof File;
                if (name.equals(pointer.children.get(i).data.name) && !check){
                    System.out.println("folder already exist");
                    return;
                }
            }
        }
        pointer.children.add(folder);
    }
    public void touch(String name){
        File newNode = new File(name);
        Node file = new Node(newNode,pointer);
        if (pointer.children != null){
            for (int i = 0; i < pointer.children.size(); i++) {
                boolean check = pointer.children.get(i).data instanceof File;
                if (name.equals(pointer.children.get(i).data.name) && check){
                    System.out.println("file already exist");
                    return;
                }
            }
        }
        pointer.children.add(file);

    }
    public void cd(String name){
        if (name.equals("..")) {
            if (pointer.parent != null) {
                pointer = pointer.parent;
            }
        } else {
            if (pointer.children != null){
                for (int i = 0; i < pointer.children.size(); i++) {
                    boolean check = pointer.children.get(i).data instanceof File;
                    if (pointer.children.get(i).data.name.equals(name) && !check){
                        pointer = pointer.children.get(i);
                        return;
                    }
                }
            }
            System.out.println("folder not found");
        }
    }
    public void ls(){
        Node[] list = pointer.children.toArray(new Node[0]);
        for(int i = 0; i < list.length - 1; i++) {
            for (int j = 0; j < list.length - i - 1; j++) {
                if(list[j].data.name.compareTo(list[j + 1].data.name) > 0) {
                    Node temp = list[j];
                    list[j] = list[j + 1];
                    list[j + 1] = temp;
                }
            }
        }
        for (int i = 0; i < list.length; i++) {
            System.out.print(list[i].data.name + " ");
        }
        System.out.println();
    }
    public void rm(String name){
        if (pointer.children != null){
            for (int i = 0; i < pointer.children.size(); i++) {
                if (pointer.children.get(i).data.name.equals(name)){
                    pointer.children.remove(i);
                    return;
                }
            }
        }
        System.out.println("target not found");
    }
    public void tree(){
        System.out.println(root.data.name);
        recurTree(root,1);

    }
    public void recurTree(Node node, int space){
        if (node.children == null){
            return;
        }
        for (int i = 0; i < node.children.size(); i++) {
            for (int j = 0; j < space; j++) {
                System.out.print("  ");
            }
            System.out.print("-- ");
            System.out.println(node.children.get(i).data.name);
            boolean check = node.children.get(i).data instanceof File;
            if (!check){
                recurTree(node.children.get(i),space + 1);
            }
        }
    }
}

public class Solution {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Tree mekanikDirSys = new Tree();
        String command = scan.nextLine();
        while (!command.equals("exit")){
            String[] commands = command.split(" ",2);
            if (commands[0].equals("mkdir")){
                mekanikDirSys.mkdir(commands[1]);
            } else if (commands[0].equals("touch")){
                mekanikDirSys.touch(commands[1]);
            } else if (commands[0].equals("cd")) {
                mekanikDirSys.cd(commands[1]);
            } else if (command.equals("ls")){
                mekanikDirSys.ls();
            } else if (commands[0].equals("rm")) {
                mekanikDirSys.rm(commands[1]);
            } else if (command.equals("tree")) {
                mekanikDirSys.tree();
            }
            command = scan.nextLine();
        }
    }
}
