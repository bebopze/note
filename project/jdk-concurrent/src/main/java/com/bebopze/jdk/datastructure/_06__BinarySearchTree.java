package com.bebopze.jdk.datastructure;

/**
 * 二叉查找树                ->  快速  查询、插入、删除
 *
 * @author bebopze
 * @date 2020/9/17
 */
public class _06__BinarySearchTree {

    private Node tree;


    /**
     * - 1、快速 查找：
     * -
     * -    1、先取根节点，如果它等于我们要查找的数据，那就返回
     * -    2、如果要查找的数据比根节点的值小，那就在左子树中递归查找
     * -    3、如果要查找的数据比根节点的值大，那就在右子树中递归查找
     *
     * @param data
     * @return
     */
    public Node find(int data) {
        Node p = tree;
        while (p != null) {
            if (data < p.data) {
                p = p.left;
            } else if (data > p.data) {
                p = p.right;
            } else {
                return p;
            }
        }
        return null;
    }


    /**
     * 2、快速 插入
     * -
     * -    1、从根节点开始，依次比较要插入的数据和节点的大小关系
     * -
     * -    2、如果要插入的数据比节点的数据大
     * -        并且节点的右子树为空，就将新数据直接插到右子节点的位置；
     * -        如果不为空，就再递归遍历右子树，查找插入位置。
     * -
     * -    3、同理，如果要插入的数据比节点数值小
     * -        并且节点的左子树为空，就将新数据插入到左子节点的位置；
     * -        如果不为空，就再递归遍历左子树，查找插入位置。
     *
     * @param data
     */
    public void insert(int data) {
        if (tree == null) {
            tree = new Node(data);
            return;
        }

        Node p = tree;
        while (p != null) {
            if (data > p.data) {
                if (p.right == null) {
                    p.right = new Node(data);
                    return;
                }
                p = p.right;
            } else { // data < p.data
                if (p.left == null) {
                    p.left = new Node(data);
                    return;
                }
                p = p.left;
            }
        }
    }


    /**
     * - 3、快速 删除
     * -
     * -    1、标记 删除：              // 取巧
     * -
     * -        将要删除的节点 标记为“已删除”，并不真正从树中将这个节点去掉
     * -
     * -            也并没有增加 插入、查找 操作代码实现的难度
     * -
     * -    2、物理 删除：
     * -
     * -        1、如果要删除的节点没有子节点，我们只需要直接将父节点中，指向要删除节点的指针置为 null
     * -
     * -            比如图中的删除节点 55
     * -
     * -        2、如果要删除的节点只有一个子节点（只有左子节点或者右子节点）
     * -
     * -            我们只需要更新父节点中，指向要删除节点的指针，让它指向要删除节点的子节点就可以了
     * -
     * -            比如图中的删除节点 13
     * -
     * -        3、如果要删除的节点有两个子节点，这就比较复杂了
     * -
     * -            我们需要找到这个节点的右子树中的最小节点，把它替换到要删除的节点上
     * -
     * -            然后再删除掉这个最小节点，因为最小节点肯定没有左子节点（如果有左子结点，那就不是最小节点了）
     * -
     * -            所以，我们可以应用上面两条规则来删除这个最小节点
     * -
     * -            比如图中的删除节点 18
     * -
     * -
     * -
     *
     * @param data
     */
    public void delete(int data) {

        // p指向要删除的节点，初始化指向根节点
        Node p = tree;
        // pp记录的是p的父节点
        Node pp = null;

        while (p != null && p.data != data) {
            pp = p;
            if (data > p.data) {
                p = p.right;
            } else {
                p = p.left;
            }
        }

        // 没有找到
        if (p == null) {
            return;
        }

        // ------- 要删除的节点有两个子节点
        // 查找右子树中最小节点
        if (p.left != null && p.right != null) {
            Node minP = p.right;
            // minPP表示minP的父节点
            Node minPP = p;
            while (minP.left != null) {
                minPP = minP;
                minP = minP.left;
            }

            // 将minP的数据替换到p中
            p.data = minP.data;

            // 下面就变成了删除minP了
            p = minP;

            pp = minPP;
        }


        // -------- 删除节点是叶子节点或者仅有一个子节点

        // p的子节点
        Node child;
        if (p.left != null) {
            child = p.left;
        } else if (p.right != null) {
            child = p.right;
        } else {
            child = null;
        }

        if (pp == null) {
            // 删除的是根节点
            tree = child;
        } else if (pp.left == p) {
            pp.left = child;
        } else {
            pp.right = child;
        }
    }


    public static class Node {
        private int data;
        private Node left;
        private Node right;

        public Node(int data) {
            this.data = data;
        }
    }
}
