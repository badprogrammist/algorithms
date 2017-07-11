package ru.ildar.algorithm.datastructure.tree;

import ru.ildar.algorithm.datastructure.stack.LinkedStack;
import ru.ildar.algorithm.datastructure.stack.Stack;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public class BinaryTree<E extends Comparable<E>> implements Tree<E> {

    private BinaryNode<E> root = new BinaryNode<>(null, null, null);
    private int size = 0;

    @Override
    public void add(E e) {
        add(e, root);
        size++;
    }

    private void add(E e, BinaryNode<E> parent) {
        if (parent.getData() == null) {
            parent.setData(e);
        } else if (e.compareTo(parent.getData()) < 0) {
            if (parent.getLeft() == null) {
                parent.setLeft(new BinaryNode<>(null, null, null));
            }
            add(e, parent.getLeft());
        } else {
            if (parent.getRight() == null) {
                parent.setRight(new BinaryNode<>(null, null, null));
            }
            add(e, parent.getRight());
        }
    }

    @Override
    public boolean remove(E e) {
        Stack<BinaryNode<E>> path = new LinkedStack<>();
        BinaryNode<E> node = makePath(e, root, path);
        if(node != null) {
            BinaryNode<E> parent = path.getLast();
            if(node.getLeft() == null && node.getRight() == null) {
                if(parent.getLeft() == node) {
                    parent.setLeft(null);
                } else if(parent.getRight() == node) {
                    parent.setRight(null);
                }

                size--;
                return true;
            } else if(node.getLeft() != null && node.getRight() == null) {
                if(parent.getLeft() == node) {
                    parent.setLeft(node.getLeft());
                } else if(parent.getRight() == node) {
                    parent.setRight(node.getLeft());
                }

                size--;
                return true;
            } else if(node.getRight() != null && node.getLeft() == null) {
                if(parent.getLeft() == node) {
                    parent.setLeft(node.getRight());
                } else if(parent.getRight() == node) {
                    parent.setRight(node.getRight());
                }
            } else if(node.getLeft() != null && node.getRight() != null) {
                BinaryNode<E> parentOfMinNode = findParentOfMin(node.getRight(), node);
                BinaryNode<E> minNode = parentOfMinNode.getLeft();

                parentOfMinNode.setLeft(null);
                minNode.setRight(node.getRight());
                minNode.setLeft(node.getLeft());
                node.setRight(minNode);
                if(parent.getLeft() == node) {
                    parent.setLeft(minNode);
                } else if(parent.getRight() == node) {
                    parent.setRight(minNode);
                }
                size--;
                return true;
            }
        }
        return false;
    }

    private BinaryNode<E> findParentOfMin(BinaryNode<E> node, BinaryNode<E> parent) {
        if(node.getLeft() != null) {
            return findParentOfMin(node.getLeft(), node);
        }
        return parent;
    }

    private BinaryNode<E> makePath(E e, BinaryNode<E> parent, Stack<BinaryNode<E>> path) {
        if (parent == null) {
            return null;
        }

        if (e.compareTo(parent.getData()) == 0) {
            return parent;
        }

        path.put(parent);
        if (e.compareTo(parent.getData()) < 0) {
            return makePath(e, parent.getLeft(), path);
        } else {
            return makePath(e, parent.getRight(), path);
        }
    }

    @Override
    public TreeIterator<E> iterator() {
        return new BinaryTreeIterator<>(root, size());
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean contains(E e) {
        return search(e, root) != null;
    }

    private BinaryNode<E> search(E e, BinaryNode<E> parent) {
        if (parent == null) {
            return null;
        }
        if (e.compareTo(parent.getData()) == 0) {
            return parent;
        }
        if (e.compareTo(parent.getData()) < 0) {
            return search(e, parent.getLeft());
        } else {
            return search(e, parent.getRight());
        }
    }

}
