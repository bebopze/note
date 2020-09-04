//package com.bebopze.jdk.datastructure;
//
//import java.util.Map;
//import java.util.TreeMap;
//import java.util.concurrent.ConcurrentHashMap;
//
//import static java.util.concurrent.ConcurrentHashMap.comparableClassFor;
//import static java.util.concurrent.ConcurrentHashMap.compareComparables;
//
///**
// * 21、红黑树       -->  ConcurrentHashMap.TreeNode
// *
// * @see TreeMap
// * @see ConcurrentHashMap.TreeNode
// * -
// * - @author bebopze
// * - @date 2020/9/4
// */
//public class _21__TreeNode {
//
//
//    /* ---------------- TreeNodes -------------- */
//
//    /**
//     * Nodes for use in TreeBins
//     */
//    public static final class TreeNode<K, V> extends Node<K, V> {
//        TreeNode<K, V> parent;  // red-black tree links
//        public TreeNode<K, V> left;
//        public TreeNode<K, V> right;
//        TreeNode<K, V> prev;    // needed to unlink next upon deletion
//        boolean red;
//
//        TreeNode(int hash, K key, V val, Node<K, V> next,
//                 TreeNode<K, V> parent) {
//            super(hash, key, val, next);
//            this.parent = parent;
//        }
//
//        @Override
//        Node<K, V> find(int h, Object k) {
//            return findTreeNode(h, k, null);
//        }
//
//        /**
//         * Returns the TreeNode (or null if not found) for the given key
//         * starting at given root.
//         */
//        final TreeNode<K, V> findTreeNode(int h, Object k, Class<?> kc) {
//            if (k != null) {
//                TreeNode<K, V> p = this;
//                do {
//                    int ph, dir;
//                    K pk;
//                    TreeNode<K, V> q;
//                    TreeNode<K, V> pl = p.left, pr = p.right;
//                    if ((ph = p.hash) > h) {
//                        p = pl;
//                    } else if (ph < h) {
//                        p = pr;
//                    } else if ((pk = p.key) == k || (pk != null && k.equals(pk))) {
//                        return p;
//                    } else if (pl == null) {
//                        p = pr;
//                    } else if (pr == null) {
//                        p = pl;
//                    } else if ((kc != null ||
//                            (kc = comparableClassFor(k)) != null) &&
//                            (dir = compareComparables(kc, k, pk)) != 0) {
//                        p = (dir < 0) ? pl : pr;
//                    } else if ((q = pr.findTreeNode(h, k, kc)) != null) {
//                        return q;
//                    } else {
//                        p = pl;
//                    }
//                } while (p != null);
//            }
//            return null;
//        }
//    }
//
//
//    /* ---------------- Nodes -------------- */
//
//    /**
//     * Key-value entry.  This class is never exported out as a
//     * user-mutable Map.Entry (i.e., one supporting setValue; see
//     * MapEntry below), but can be used for read-only traversals used
//     * in bulk tasks.  Subclasses of Node with a negative hash field
//     * are special, and contain null keys and values (but are never
//     * exported).  Otherwise, keys and vals are never null.
//     */
//    static class Node<K, V> implements Map.Entry<K, V> {
//        final int hash;
//        final K key;
//        volatile V val;
//        volatile Node<K, V> next;
//
//        Node(int hash, K key, V val, Node<K, V> next) {
//            this.hash = hash;
//            this.key = key;
//            this.val = val;
//            this.next = next;
//        }
//
//        @Override
//        public final K getKey() {
//            return key;
//        }
//
//        @Override
//        public final V getValue() {
//            return val;
//        }
//
//        @Override
//        public final int hashCode() {
//            return key.hashCode() ^ val.hashCode();
//        }
//
//        @Override
//        public final String toString() {
//            return key + "=" + val;
//        }
//
//        @Override
//        public final V setValue(V value) {
//            throw new UnsupportedOperationException();
//        }
//
//        @Override
//        public final boolean equals(Object o) {
//            Object k, v, u;
//            Map.Entry<?, ?> e;
//            return ((o instanceof Map.Entry) &&
//                    (k = (e = (Map.Entry<?, ?>) o).getKey()) != null &&
//                    (v = e.getValue()) != null &&
//                    (k == key || k.equals(key)) &&
//                    (v == (u = val) || v.equals(u)));
//        }
//
//        /**
//         * Virtualized support for map.get(); overridden in subclasses.
//         */
//        Node<K, V> find(int h, Object k) {
//            Node<K, V> e = this;
//            if (k != null) {
//                do {
//                    K ek;
//                    if (e.hash == h &&
//                            ((ek = e.key) == k || (ek != null && k.equals(ek)))) {
//                        return e;
//                    }
//                } while ((e = e.next) != null);
//            }
//            return null;
//        }
//    }
//
//}
