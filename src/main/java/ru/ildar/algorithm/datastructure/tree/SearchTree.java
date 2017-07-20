package ru.ildar.algorithm.datastructure.tree;

import ru.ildar.algorithm.datastructure.Iterator;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public class SearchTree<E extends Comparable<E>> implements Tree<E> {

    private Node<E> root = new Node<>();
    private int size = 0;

    @Override
    public void add(E e) {
        add(e, root);
        size++;
    }

    private void add(E e, Node<E> parent) {
        if (parent.data == null) {
            parent.data = e;
        } else if (e.compareTo(parent.data) < 0) {
            if (parent.left == null) {
                parent.left = new Node<>(parent);
            }
            add(e, parent.left);
        } else {
            if (parent.right == null) {
                parent.right = new Node<>(parent);
            }
            add(e, parent.right);
        }
    }

    protected Node<E> getMin(Node<E> parent) {
        if (parent.left == null) {
            return parent;
        } else {
            return getMin(parent.left);
        }
    }

    @Override
    public boolean contains(E e) {
        return search(e, root) != null;
    }

    protected Node<E> search(E e, Node<E> parent) {
        if(parent == null) {
            return null;
        }
        if (e.compareTo(parent.data) == 0) {
            return parent;
        } else if (e.compareTo(parent.data) < 0) {
            return search(e, parent.left);
        } else {
            return search(e, parent.right);
        }
    }

    protected Node<E> getMax(Node<E> parent) {
        if (parent.right == null) {
            return parent;
        } else {
            return getMax(parent.right);
        }
    }

    @Override
    public boolean remove(E e) {
        Node<E> node = search(e, root);
        if(node != null) {
            remove(node);
            return true;
        }
        return false;
    }

    protected void remove(Node<E> node) {
        if (node.left == null) {
            transplant(node, node.right);
        } else if (node.right == null) {
            transplant(node, node.left);
        } else {
            Node<E> min = getMin(node.right);
            if (min.parent != node) {
                transplant(min, min.right);
                min.right = node.right;
                min.right.parent = min;
            }
            transplant(node, min);
            min.left = node.left;
            min.left.parent = min;
        }
        size--;
    }

    private void transplant(Node<E> up, Node<E> down) {
        if (up.parent == null) {
            this.root = down;
        } else if (up.parent.left == up) {
            up.parent.left = down;
        } else {
            up.parent.right = down;
        }
        if (down != null) {
            down.parent = up.parent;
        }
    }

    protected Node<E> successor(Node<E> node) {
        if(node.right != null) {
            return getMin(node.right);
        } else {
            Node<E> current = node;
            Node<E> parent = node.parent;
            while (parent != null && current == parent.right) {
                current = parent;
                parent = parent.parent;
            }
            return parent;
        }
    }

    protected Node<E> predecessor(Node<E> node) {
        if(node.left != null) {
            return getMax(node.left);
        } else {
            Node<E> current = node;
            Node<E> parent = node.parent;
            while (parent != null && current == parent.left) {
                current = parent;
                parent = parent.parent;
            }
            return parent;
        }
    }

    protected Node<E> getRoot() {
        return root;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<E> iterator() {
        return new DescentOrderIterator<E>(this);
    }

    public static class Node<E extends Comparable<E>> {
        public E data;
        public Node<E> left;
        public Node<E> right;
        public Node<E> parent;

        Node() {
        }

        Node(Node<E> parent) {
            this.parent = parent;
        }
    }

    /**
     * Traverse through elements by descent order
     */
    protected static class DescentOrderIterator<E extends Comparable<E>> implements Iterator<E> {

        private SearchTree<E> tree;
        private Node<E> next;

        DescentOrderIterator(SearchTree<E> tree) {
            this.tree = tree;
            this.next = tree.getMax(tree.getRoot());
        }

        @Override
        public boolean hasNext() {
            return next != null && next.data != null;
        }

        @Override
        public E next() {
            Node<E> prev = next;
            if(prev != null) {
                next = tree.predecessor(next);
                return prev.data;
            }
            return null;
        }

    }


}
