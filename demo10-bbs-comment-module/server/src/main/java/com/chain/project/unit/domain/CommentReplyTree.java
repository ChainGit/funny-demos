package com.chain.project.unit.domain;

import com.chain.project.common.utils.ChainProjectUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.*;

public class CommentReplyTree {

    public static Object make(List dataList) throws IOException {
        // 节点列表（散列表，用于临时存储节点对象）
        HashMap nodeMap = new HashMap();
        // 根节点
        Node root = null;
        // 根据结果集构造节点列表（存入散列表）
        for (Iterator it = dataList.iterator(); it.hasNext(); ) {
            Map dataRecord = (Map) it.next();
            Node node = new Node();
            node.commentId = String.valueOf((Long) dataRecord.get("commentId"));
            Long parentId = (Long) dataRecord.get("parentId");
            if (!ChainProjectUtils.isEmpty(parentId))
                node.parentId = String.valueOf(parentId);
            Map<String, Object> content = new HashMap<>();
            String text = (String) dataRecord.get("text");
            Date time = (Date) dataRecord.get("time");
            Long userId = (Long) dataRecord.get("userId");
            String userName = (String) dataRecord.get("userName");
            Integer userAvatarId = (Integer) dataRecord.get("userAvatarId");
            content.put("userId", userId);
            content.put("userName", userName);
            content.put("userAvatarId", userAvatarId);
            content.put("time", time.getTime());
            content.put("text", text);
            node.content = content;
            nodeMap.put(node.commentId, node);
        }
        // 构造无序的多叉树
        Set entrySet = nodeMap.entrySet();
        for (Iterator it = entrySet.iterator(); it.hasNext(); ) {
            Node node = (Node) ((Map.Entry) it.next()).getValue();
            if (node.parentId == null || node.parentId.equals("")) {
                root = node;
            } else {
                ((Node) nodeMap.get(node.parentId)).add(node);
            }
        }
        // 对多叉树进行横向排序
        root.sort();
        // 输出有序的树形菜单的JSON字符串
        String jsonString = root.toJsonString();
        //转为Json对象
        ObjectMapper objectMapper = new ObjectMapper();
        Map map = objectMapper.readValue(jsonString, Map.class);
        return map;
    }

}


/**
 * 节点类
 */
class Node {
    /**
     * 节点id
     */
    public String commentId;
    /**
     * 节点内容
     */
    public Map content;
    /**
     * 父节点id
     */
    public String parentId;
    /**
     * 孩子节点列表
     */
    private Children children = new Children();

    @Override
    public String toString() {
        return toJsonString();
    }

    // 先序遍历，拼接JSON字符串
    public String toJsonString() {
        String result = "{"
                + "\"commentId\":" + commentId
                + ",\"userId\":" + content.get("userId")
                + ",\"userName\":\"" + content.get("userName") + "\""
                + ",\"userAvatarId\":" + content.get("userAvatarId")
                + ",\"time\":" + content.get("time")
                + ",\"text\":\"" + content.get("text") + "\"";

        if (children != null && children.getSize() != 0) {
            result += ",\"hasChild\":true,\"children\":" + children.toJsonString();
        } else {
            result += ",\"hasChild\":false";
        }

        return result + "}";
    }

    // 兄弟节点横向排序
    public void sort() {
        if (children != null && children.getSize() != 0) {
            children.sort();
        }
    }

    // 添加孩子节点
    public void add(Node node) {
        this.children.add(node);
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

    public void add(Node node) {
        list.add(node);
    }

    @Override
    public String toString() {
        return toJsonString();
    }

    // 拼接孩子节点的JSON字符串(数组)
    public String toJsonString() {
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
    public void sort() {
        // 对本层节点进行排序
        // 可根据不同的排序属性，传入不同的比较器，这里传入ID比较器
        Collections.sort(list, new NodeIDComparator());
        // 对每个节点的下一层节点进行排序
        for (Iterator it = list.iterator(); it.hasNext(); ) {
            ((Node) it.next()).sort();
        }
    }
}

/**
 * 节点比较器
 */
class NodeIDComparator implements Comparator {
    // 按照节点编号比较
    public int compare(Object o1, Object o2) {
        int j1 = Integer.parseInt(((Node) o1).commentId);
        int j2 = Integer.parseInt(((Node) o2).commentId);
        return (j1 < j2 ? -1 : (j1 == j2 ? 0 : 1));
    }
}
