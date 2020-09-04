//package com.bebopze.jdk.algorithm;
//
//import java.util.LinkedList;
//import java.util.Stack;
//
///**
// * 二叉树 - 操作：翻转、最大深度、查找、路径
// *
// * @author bebopze
// * @date 2020/9/4
// */
//public class _21__BinaryTree {
//
//
//    /**
//     * 翻转二叉树
//     *
//     * @param root
//     * @return
//     */
//    public TreeNode invertTree(TreeNode root) {
//        if (root == null) {
//            return root;
//        }
//        TreeNode node = root;
//        Queue<TreeNode> queue = new LinkedList<>();
//        queue.add(node);
//        while (!queue.isEmpty()) {
//            node = queue.poll();
//            TreeNode tempNode = node.left;
//            node.left = node.right;
//            node.right = tempNode;
//            if (node.left != null) {
//                queue.offer(node.left);
//            }
//            if (node.right != null) {
//                queue.offer(node.right);
//            }
//        }
//        return root;
//    }
//
//
//    /**
//     * 二叉树的最大深度
//     *
//     * @param root
//     * @return
//     */
//    public int maxDepth(TreeNode root) {
//        if (root == null) {
//            return 0;
//        }
//        return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
//    }
//
//
//    /**
//     * 验证二叉查找树
//     *
//     * @param root
//     * @return
//     */
//    public boolean isValidBST(TreeNode root) {
//        if (root == null) {
//            return true;
//        }
//        Stack<TreeNode> stack = new Stack<>();
//        TreeNode node = root;
//        TreeNode preNode = null;
//        while (node != null || !stack.isEmpty()) {
//            stack.push(node);
//            node = node.left;
//            while (node == null && !stack.isEmpty()) {
//                node = stack.pop();
//                if (preNode != null) {
//                    if (preNode.val >= node.val) {
//                        return false;
//                    }
//                }
//                preNode = node;
//                node = node.right;
//            }
//        }
//        return true;
//    }
//
//
//    /**
//     * 路径总和
//     *
//     * @param root
//     * @param sum
//     * @return
//     */
//    public boolean hasPathSum(TreeNode root, int sum) {
//        if (root == null) {
//            return false;
//        }
//        return hasPathSum(root, root.val, sum);
//    }
//
//    private boolean hasPathSum(TreeNode root, int tmp, int sum) {
//        if (root == null) {
//            return false;
//        }
//        if (root.left == null && root.right == null) {
//            return tmp == sum;
//        }
//        if (root.left == null) {
//            return hasPathSum(root.right, root.right.val + tmp, sum);
//        }
//        if (root.right == null) {
//            return hasPathSum(root.left, root.left.val + tmp, sum);
//        }
//        return hasPathSum(root.left, root.left.val + tmp, sum) ||
//                hasPathSum(root.right, root.right.val + tmp, sum);
//    }
//
//}
