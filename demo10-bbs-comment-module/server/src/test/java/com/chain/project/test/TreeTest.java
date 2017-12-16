package com.chain.project.test;

import java.util.*;

/**
 * 多叉树类
 */
public class TreeTest {

    public static void main(String[] args) {
        // 读取层次数据结果集列表
        List dataList = VirtualDataGenerator.getVirtualResult();

        // 节点列表（散列表，用于临时存储节点对象）
        HashMap nodeList = new HashMap();
        // 根节点
        Node root = null;
        // 根据结果集构造节点列表（存入散列表）
        for (Iterator it = dataList.iterator(); it.hasNext(); ) {
            Map dataRecord = (Map) it.next();
            Node node = new Node();
            node.id = (String) dataRecord.get("id");
            node.text = (String) dataRecord.get("text");
            node.parentId = (String) dataRecord.get("parentId");
            nodeList.put(node.id, node);
        }
        // 构造无序的多叉树
        Set entrySet = nodeList.entrySet();
        for (Iterator it = entrySet.iterator(); it.hasNext(); ) {
            Node node = (Node) ((Map.Entry) it.next()).getValue();
            if (node.parentId == null || node.parentId.equals("")) {
                root = node;
            } else {
                ((Node) nodeList.get(node.parentId)).addChild(node);
            }
        }
        // 输出无序的树形菜单的JSON字符串
        System.out.println(root.toString());
        // 对多叉树进行横向排序
        root.sortChildren();
        // 输出有序的树形菜单的JSON字符串
        System.out.println(root.toString());

        // 程序输出结果如下（无序的树形菜单）（格式化后的结果）：
        //  {
        //   id : '100000',
        //   text : '廊坊银行总行',
        //   children : [
        //     {
        //     id : '110000',
        //     text : '廊坊分行',
        //     children : [
        //       {
        //       id : '113000',
        //       text : '廊坊银行开发区支行',
        //       leaf : true
        //       },
        //       {
        //       id : '111000',
        //       text : '廊坊银行金光道支行',
        //       leaf : true
        //       },
        //       {
        //       id : '112000',
        //       text : '廊坊银行解放道支行',
        //       children : [
        //         {
        //         id : '112200',
        //         text : '廊坊银行三大街支行',
        //         leaf : true
        //         },
        //         {
        //         id : '112100',
        //         text : '廊坊银行广阳道支行',
        //         leaf : true
        //         }
        //       ]
        //       }
        //     ]
        //     }
        //   ]
        //  }

        // 程序输出结果如下（有序的树形菜单）（格式化后的结果）：
        //  {
        //   id : '100000',
        //   text : '廊坊银行总行',
        //   children : [
        //     {
        //     id : '110000',
        //     text : '廊坊分行',
        //     children : [
        //       {
        //       id : '111000',
        //       text : '廊坊银行金光道支行',
        //       leaf : true
        //       },
        //       {
        //       id : '112000',
        //       text : '廊坊银行解放道支行',
        //       children : [
        //         {
        //         id : '112100',
        //         text : '廊坊银行广阳道支行',
        //         leaf : true
        //         },
        //         {
        //         id : '112200',
        //         text : '廊坊银行三大街支行',
        //         leaf : true
        //         }
        //       ]
        //       },
        //       {
        //       id : '113000',
        //       text : '廊坊银行开发区支行',
        //       leaf : true
        //       }
        //     ]
        //     }
        //   ]
        //  }

    }

}


/**
 * 节点类
 */
class Node {
    /**
     * 节点编号
     */
    public String id;
    /**
     * 节点内容
     */
    public String text;
    /**
     * 父节点编号
     */
    public String parentId;
    /**
     * 孩子节点列表
     */
    private Children children = new Children();

    // 先序遍历，拼接JSON字符串
    public String toString() {
        String result = "{"
                + "id : '" + id + "'"
                + ", text : '" + text + "'";

        if (children != null && children.getSize() != 0) {
            result += ", children : " + children.toString();
        } else {
            result += ", leaf : true";
        }

        return result + "}";
    }

    // 兄弟节点横向排序
    public void sortChildren() {
        if (children != null && children.getSize() != 0) {
            children.sortChildren();
        }
    }

    // 添加孩子节点
    public void addChild(Node node) {
        this.children.addChild(node);
    }
}

/**
 * 孩子列表类
 */
class Children {
    private List list = new ArrayList();

    public int getSize() {
        return list.size();
    }

    public void addChild(Node node) {
        list.add(node);
    }

    // 拼接孩子节点的JSON字符串
    public String toString() {
        String result = "[";
        for (Iterator it = list.iterator(); it.hasNext(); ) {
            result += ((Node) it.next()).toString();
            result += ",";
        }
        result = result.substring(0, result.length() - 1);
        result += "]";
        return result;
    }

    // 孩子节点排序
    public void sortChildren() {
        // 对本层节点进行排序
        // 可根据不同的排序属性，传入不同的比较器，这里传入ID比较器
        Collections.sort(list, new NodeIDComparator());
        // 对每个节点的下一层节点进行排序
        for (Iterator it = list.iterator(); it.hasNext(); ) {
            ((Node) it.next()).sortChildren();
        }
    }
}

/**
 * 节点比较器
 */
class NodeIDComparator implements Comparator {
    // 按照节点编号比较
    public int compare(Object o1, Object o2) {
        int j1 = Integer.parseInt(((Node) o1).id);
        int j2 = Integer.parseInt(((Node) o2).id);
        return (j1 < j2 ? -1 : (j1 == j2 ? 0 : 1));
    }
}

/**
 * 构造虚拟的层次数据
 */
class VirtualDataGenerator {
    // 构造无序的结果集列表，实际应用中，该数据应该从数据库中查询获得；
    public static List getVirtualResult() {
        List dataList = new ArrayList();

        HashMap dataRecord1 = new HashMap();
        dataRecord1.put("id", "112000");
        dataRecord1.put("text", "廊坊银行解放道支行");
        dataRecord1.put("parentId", "110000");

        HashMap dataRecord2 = new HashMap();
        dataRecord2.put("id", "112200");
        dataRecord2.put("text", "廊坊银行三大街支行");
        dataRecord2.put("parentId", "112000");

        HashMap dataRecord3 = new HashMap();
        dataRecord3.put("id", "112100");
        dataRecord3.put("text", "廊坊银行广阳道支行");
        dataRecord3.put("parentId", "112000");

        HashMap dataRecord4 = new HashMap();
        dataRecord4.put("id", "113000");
        dataRecord4.put("text", "廊坊银行开发区支行");
        dataRecord4.put("parentId", "110000");

        HashMap dataRecord5 = new HashMap();
        dataRecord5.put("id", "100000");
        dataRecord5.put("text", "廊坊银行总行");
        dataRecord5.put("parentId", "");

        HashMap dataRecord6 = new HashMap();
        dataRecord6.put("id", "110000");
        dataRecord6.put("text", "廊坊分行");
        dataRecord6.put("parentId", "100000");

        HashMap dataRecord7 = new HashMap();
        dataRecord7.put("id", "111000");
        dataRecord7.put("text", "廊坊银行金光道支行");
        dataRecord7.put("parentId", "110000");

        dataList.add(dataRecord1);
        dataList.add(dataRecord2);
        dataList.add(dataRecord3);
        dataList.add(dataRecord4);
        dataList.add(dataRecord5);
        dataList.add(dataRecord6);
        dataList.add(dataRecord7);

        return dataList;
    }
}