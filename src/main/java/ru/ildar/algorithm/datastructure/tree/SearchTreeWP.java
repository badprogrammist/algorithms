package ru.ildar.algorithm.datastructure.tree;

import ru.ildar.algorithm.datastructure.Iterator;
import ru.ildar.algorithm.datastructure.stack.LinkedStack;
import ru.ildar.algorithm.datastructure.stack.Stack;

import java.util.NoSuchElementException;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public class SearchTreeWP<E extends Comparable<E>> implements Tree<E> {

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
                parent.left = new Node<>();
            }
            add(e, parent.left);
        } else {
            if (parent.right == null) {
                parent.right = new Node<>();
            }
            add(e, parent.right);
        }
    }

    @Override
    public boolean remove(E e) {
        Stack<Node<E>> path = new LinkedStack<>();
        Node<E> node = makePath(e, root, path);
        if(node != null) {
            Node<E> parent = path.peek();
            if(node.left == null && node.right == null) {
                if(parent.left == node) {
                    parent.left = null;
                } else if(parent.right == node) {
                    parent.right = null;
                }

                size--;
                return true;
            } else if(node.left != null && node.right == null) {
                if(parent.left == node) {
                    parent.left = node.left;
                } else if(parent.right == node) {
                    parent.right = node.left;
                }

                size--;
                return true;
            } else if(node.left == null) {
                if(parent.left == node) {
                    parent.left = node.right;
                } else if(parent.right == node) {
                    parent.right = node.right;
                }
            } else {
                Node<E> parentOfMinNode = findParentOfMin(node.right, node);
                Node<E> minNode = parentOfMinNode.left;

                parentOfMinNode.left = null;
                minNode.right = node.right;
                minNode.left = node.left;
                node.right = minNode;
                if(parent.left == node) {
                    parent.left = minNode;
                } else if(parent.right == node) {
                    parent.right = minNode;
                }
                size--;
                return true;
            }
        }
        return false;
    }

    private Node<E> findParentOfMin(Node<E> node, Node<E> parent) {
        if(node.left != null) {
            return findParentOfMin(node.left, node);
        }
        return parent;
    }

    private Node<E> makePath(E e, Node<E> parent, Stack<Node<E>> path) {
        if (parent == null) {
            return null;
        }

        if (e.compareTo(parent.data) == 0) {
            return parent;
        }

        path.push(parent);
        if (e.compareTo(parent.data) < 0) {
            return makePath(e, parent.left, path);
        } else {
            return makePath(e, parent.right, path);
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new SearchTreeWPIterator<>(root, size());
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean contains(E e) {
        return search(e, root) != null;
    }

    private Node<E> search(E e, Node<E> parent) {
        if (parent == null) {
            return null;
        }
        if (e.compareTo(parent.data) == 0) {
            return parent;
        }
        if (e.compareTo(parent.data) < 0) {
            return search(e, parent.left);
        } else {
            return search(e, parent.right);
        }
    }

    protected class Node<D extends Comparable<D>> {

        D data;
        Node<D> left;
        Node<D> right;

        Node() {
        }
    }

    protected class SearchTreeWPIterator<D extends Comparable<D>> implements Iterator<D> {

        private Stack<Node<D>> path = new LinkedStack<>();
        private int traversedSize = 0;
        private int treeSize;
        private Node<D> root;

        public SearchTreeWPIterator(Node<D> root, int treeSize) {
            this.root = root;
            this.treeSize = treeSize;
        }

        @Override
        public boolean hasNext() {
            return traversedSize < treeSize;
        }

        @Override
        public D next() throws NoSuchElementException {
            D next = getNext(null);
            traversedSize++;
            return next;
        }

        private D getNext(Node<D> last) throws NoSuchElementException {
            if (path.size() == 0) {
                path.push(root);
                return root.data;
            } else if ((last != null && path.peek().left != last && path.peek().right != last)
                    || (last == null && path.peek().left != null)) {
                D data = path.peek().left.data;
                path.push(path.peek().left);
                return data;
            } else if (path.peek().right != null && path.peek().right != last) {
                D data = path.peek().right.data;
                path.push(path.peek().right);
                return data;
            } else {
                return getNext(path.pop());
            }
        }


    }

}
