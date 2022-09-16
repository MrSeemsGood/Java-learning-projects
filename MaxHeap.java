import java.util.Scanner;

public class MaxHeap {
    private int[] heapData;
    private int heapSize;
    private int heapMaxSize;

    private static final int FRONT = 1;

    //constructor to initialize heapData array;
    public MaxHeap(int heapMaxSize) {
        this.heapMaxSize = heapMaxSize;
        this.heapSize = 0;
        heapData = new int[this.heapMaxSize + 1];
        heapData[0] = Integer.MAX_VALUE;
    }

    //return parent position of the node
    private int getParentPos(int position) {
        return position / 2;
    }

    //return positions of left and right children
    private int getLeftChildPos(int position) {
        return position * 2;
    }

    private boolean hasLeftChild(int position) {
        return getLeftChildPos(position) <= heapSize;
    }

    private int getRightChildPos(int position) {
        return position * 2 + 1;
    }

    private boolean hasRightChild(int position) {
        return getRightChildPos(position) <= heapSize;
    }

    //checks whether given node is a leaf
    private boolean checkLeaf(int position) {
        return (position >= (heapSize / 2) && position <= heapSize);
    }

    //swap nodes
    private void swapNodes(int first, int second) {
        int temp;
        temp = heapData[first];
        heapData[first] = heapData[second];
        heapData[second] = temp;
    }

    //min heapify
    private void maxHeapify(int position) {
        if (!checkLeaf(position)) {
            if ((hasLeftChild(position) && heapData[position] < heapData[getLeftChildPos(position)])
                    || (hasRightChild(position) && heapData[position] < heapData[getRightChildPos(position)])) {
                //swap with the left child
                if (heapData[getRightChildPos(position)] < heapData[getLeftChildPos(position)]) {
                    swapNodes(position, getLeftChildPos(position));
                    maxHeapify(getLeftChildPos(position));
                    //swap with the right child
                } else {
                    swapNodes(position, getRightChildPos(position));
                    maxHeapify(getRightChildPos(position));
                }
            }
        }
    }

    public void insertNode(int data) {
        if (heapSize >= heapMaxSize) {
            return;
        }
        heapData[++heapSize] = data;
        int current = heapSize;

        //move the node with data up to put it on the correct level
        while (heapData[current] > heapData[getParentPos(current)]) {
            swapNodes(current, getParentPos(current));
            current = getParentPos(current);
        }
    }

    public void displayHeap() {
        System.out.println("PARENT NODE" + "\t" + "LEFT CHILD NODE" +
                "\t" + "RIGHT CHILD NODE");
        for (int k = 1; k <= heapSize / 2 + 1; k++) {
            if (hasRightChild(k)) {
                System.out.println(" \t" + heapData[k] + "\t\t" + heapData[k * 2] +
                        "\t\t" + heapData[k * 2 + 1]);
            } else if (hasLeftChild(k)) {
                System.out.println(" \t" + heapData[k] + "\t\t" + heapData[k * 2]);
            }
        }
    }

    public void designMaxHeap() {
        for (int pos = heapSize / 2; pos >= 1; pos--) {
            maxHeapify(pos);
        }
    }

    //pop max element (root) out of the heap
    public int removeRoot() {
        int popElement = heapData[FRONT];
        heapData[FRONT] = heapData[heapSize--];
        maxHeapify(FRONT);
        return popElement;
    }
}

class MaxHeapJavaImplementation {
    public static void main(String[] arg) {
        int heapSize;

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the size of max heap: ");
        heapSize = sc.nextInt();

        MaxHeap heapObj = new MaxHeap(heapSize);

        for (int i = 1; i <= heapSize; i++) {
            System.out.print("Enter " + i + " element: ");
            int data = sc.nextInt();
            heapObj.insertNode(data);
        }

        sc.close();
        heapObj.designMaxHeap();

        System.out.println("Resulting max heap is:");
        heapObj.displayHeap();

        System.out.println("After removing the maximum element (root node) " +
                heapObj.removeRoot() + ", the max heap is:");
        heapObj.displayHeap();
    }
}

