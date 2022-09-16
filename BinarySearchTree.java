import java.util.Scanner;

class Node {
    int key;
    Node left, right, parent;

    // Constructor
    Node(int key) {
        this.key = key;
        left = null;
        right = null;
        parent = null;
    }
}

public class BinarySearchTree {
    private Node root;

    public BinarySearchTree() {
        root = null;
    }

    public Node getRoot() {
        return root;
    }

    public void insertChildNode(Node node, Node childNode) {
        String displayDirection;

        if (node != null) {
            if (childNode.key > node.key) {
                //insert as the right child
                node.right = childNode;
                displayDirection = "right";
            } else {
                //insert as the left child
                node.left = childNode;
                displayDirection = "left";
            }
            childNode.parent = node;
        } else {
            //insert the root value
            root = childNode;
            displayDirection = "root";
        }

        System.out.println("Inserting node with a key " + childNode.key + " to the " + displayDirection);
    }

    public Node findInsertionNode(Node nodeToInsert) {
        Node current = root;

        //search until we find a node that is null (root insertion)
        while (current != null) {
            if (nodeToInsert.key > current.key) {
                if (current.right != null) {
                    //keep going to the right
                    current = current.right;
                } else {
                    //stop; we are sure we need to branch off of that node to the right
                    break;
                }
            } else if (nodeToInsert.key < current.key) {
                if (current.left != null) {
                    //keep going to the left
                    current = current.left;
                } else {
                    break;
                }
            }
        }

        if (current == null) {
            System.out.print("Root insertion. ");
        } else {
            System.out.print("Insertion at node with key " + current.key + ". ");
        }
        return current;
    }

    /* TREE TRAVERSALS:
        1. In-order: go left --> right, passing the root
        2. Pre-order: start from the root, then display the left subtree, then the right subtree
        3. Post-order: go right --> left, then to the root
     */
    public void inOrderTreeTraverse(Node node) {
        if (node != null) {
            inOrderTreeTraverse(node.left);
            System.out.print(node.key + " | ");
            inOrderTreeTraverse(node.right);
        }
    }

    public void preOrderTreeTraverse(Node node) {
        if (node != null) {
            System.out.print(node.key + " | ");
            preOrderTreeTraverse(node.left);
            preOrderTreeTraverse(node.right);
        }
    }

    public void postOrderTreeTraverse(Node node) {
        if (node != null) {
            postOrderTreeTraverse(node.left);
            postOrderTreeTraverse(node.right);
            System.out.print(node.key + " | ");
        }
    }

    /*
        To find min element, keep going to the left
        To find max element, keep going to the right
     */
    public int findMin() {
        if (root != null) {
            Node current = root;

            while (current.left != null) {
                current = current.left;
            }
            return current.key;
        } else {
            return -1;
        }
    }

    public int findMax() {
        if (root != null) {
            Node current = root;

            while (current.right != null) {
                current = current.right;
            }
            return current.key;
        } else {
            return -1;
        }
    }

    // search up the node
    public Node searchNode(int val) {
        Node current = root;

        while (current != null) {
            if (current.key == val) {
                return current;
            } else {
                if (current.right != null && current.key < val) {
                    current = current.right;
                } else if (current.left != null && current.key > val) {
                    current = current.left;
                } else {
                    return null;
                }
            }
        }

        return null;
    }
    /* findNextLargest()
        - if the given node's value is max, return itself
        - if the given node has a right subtree, advance to it and go all the way to the left, return a leftmost leaf
        - else, go up and AS SOON as you start going to the right, not left, return parent
     */
    public int findNextLargest(int value) {
        if (value == findMax()) {
            return value;
        }

        Node valueNode = searchNode(value);
        if (valueNode.right != null) {
            valueNode = valueNode.right;

            while (valueNode.left != null) {
                valueNode = valueNode.left;
            }

            return valueNode.key;
        } else {
            //go up to our parent; if we were to the right of it, keep going, otherwise stop and return
            while (valueNode.parent != null) {
                Node valueNodeParent = valueNode.parent;

                if (valueNodeParent.key < valueNode.key) {
                    valueNode = valueNodeParent;
                } else {
                    return valueNodeParent.key;
                }
            }
        }

        return -1;
    }

    // findNextSmallest()
    public int findNextSmallest(int value) {
        if (value == findMin()) {
            return value;
        }

        Node valueNode = searchNode(value);
        if (valueNode.left != null) {
            valueNode = valueNode.left;

            while (valueNode.right != null) {
                valueNode = valueNode.right;
            }

            return valueNode.key;
        } else {
            //go up to our parent; if we were to the right of it, keep going, otherwise stop and return
            while (valueNode.parent != null) {
                Node valueNodeParent = valueNode.parent;

                if (valueNodeParent.key > valueNode.key) {
                    valueNode = valueNodeParent;
                } else {
                    return valueNodeParent.key;
                }
            }
        }

        return -1;
    }

    // deleteNode()
    public void deleteNode(int key) {
        Node nodeToDelete = searchNode(key);

        if (nodeToDelete.right == null && nodeToDelete.left == null) {
            // leaf; just delete it
            nodeToDelete = null;
        } else if (nodeToDelete.right != null && nodeToDelete.left == null) {
            // node with only right child
            if (nodeToDelete.parent != null) {
                // change parent's pointer to point at the node's right child
                nodeToDelete.parent.right = nodeToDelete.right;
            }
            nodeToDelete = null;
        } else if (nodeToDelete.right == null) {
            // node with only left child
            if (nodeToDelete.parent != null) {
                // change parent's pointer to point at the node's left child
                // if it has no parents, it's a root, and we can safely remove it
                nodeToDelete.parent.left = nodeToDelete.left;
            }
            nodeToDelete = null;
        } else {
            /* node has both children:
                find next largest (successor) and copy its contents into the node, deleting it
             */
            Node nodeToReplace = searchNode(findNextLargest(key));
            nodeToDelete.key = nodeToReplace.key;
            if (nodeToReplace.right != null) {
                nodeToDelete.right = nodeToReplace.right;
            }
            if (nodeToReplace.left != null) {
                nodeToDelete.left = nodeToReplace.left;
            }
            Node p = nodeToReplace.parent;
            if (p.key > nodeToReplace.key) {
                p.left = null;
            } else {
                p.right = null;
            }
            nodeToReplace = null;
        }
    }
}

class BstJavaImplementation {
    public static void main(String[] arg) {
        int bstSize;
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the size of binary search tree: ");
        bstSize = sc.nextInt();

        BinarySearchTree bstObj = new BinarySearchTree();

        for (int i = 1; i <= bstSize; i++) {
            System.out.print("Enter " + i + " element: ");
            int data = sc.nextInt();
            Node newNode = new Node(data);
            bstObj.insertChildNode(bstObj.findInsertionNode(newNode), newNode);
        }

        System.out.println("In-order traversal of the tree:");
        bstObj.inOrderTreeTraverse(bstObj.getRoot());
        System.out.println("\nPre-order traversal of the tree:");
        bstObj.preOrderTreeTraverse(bstObj.getRoot());
        System.out.println("\nPost-order traversal of the tree:");
        bstObj.postOrderTreeTraverse(bstObj.getRoot());
        // print out maximum and minimum element
        System.out.println("\nMaximum element in the tree is " + bstObj.findMax());
        System.out.println("Minimum element in the tree is " + bstObj.findMin());
        // search up the node value, print predecessor (next smallest) and successor (next largest)
        System.out.println("Search up the node in the tree (if it's in the tree, will also print next largest and next smallest):");
        int value = sc.nextInt();
        System.out.println("Node " + value + " is" + ((bstObj.searchNode(value) != null) ? "" : " not") + " in the tree!");
        if (bstObj.searchNode(value) != null) {
            System.out.println("Next largest is " + bstObj.findNextLargest(value));
            System.out.println("Next smallest is " + bstObj.findNextSmallest(value));
        }
        // delete the node
        System.out.println("Delete the node by its value:");
        int deleteValue = sc.nextInt();
        bstObj.deleteNode(deleteValue);
        System.out.println("Node " + deleteValue + " is deleted. New in-order traversal:");
        bstObj.inOrderTreeTraverse(bstObj.getRoot());

        sc.close();
    }
}
