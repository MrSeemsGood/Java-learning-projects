import java.util.Scanner;

public class MinHeap {
    private int[] heapData;
    private int heapSize;
    private int heapMaxSize;

    private static final int FRONT = 1;

    //constructor to initialize heapData array;
    public MinHeap(int heapMaxSize) {
        this.heapMaxSize = heapMaxSize;
        this.heapSize = 0;
        heapData = new int[this.heapMaxSize + 1];
        heapData[0] = Integer.MIN_VALUE;
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
    private void minHeapify(int position) {
        if (!checkLeaf(position)) {
            if ((hasLeftChild(position) && heapData[position] > heapData[getLeftChildPos(position)])
                    || (hasRightChild(position) && heapData[position] > heapData[getRightChildPos(position)])) {
                //swap with the left child
                if (heapData[getRightChildPos(position)] > heapData[getLeftChildPos(position)]) {
                    swapNodes(position, getLeftChildPos(position));
                    minHeapify(getLeftChildPos(position));
                //swap with the right child
                } else {
                    swapNodes(position, getRightChildPos(position));
                    minHeapify(getRightChildPos(position));
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
        while (heapData[current] < heapData[getParentPos(current)]) {
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

    public void designMinHeap() {
        for (int pos = heapSize / 2; pos >= 1; pos--) {
            minHeapify(pos);
        }
    }

    //pop min element (root) out of the heap
    public int removeRoot() {
        int popElement = heapData[FRONT];
        heapData[FRONT] = heapData[heapSize--];
        minHeapify(FRONT);
        return popElement;
    }
}

class MinHeapJavaImplementation {
    public static void main(String[] arg) {
        int heapSize;

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the size of min heap: ");
        heapSize = sc.nextInt();

        MinHeap heapObj = new MinHeap(heapSize);

        for (int i = 1; i <= heapSize; i++) {
            System.out.print("Enter " + i + " element: ");
            int data = sc.nextInt();
            heapObj.insertNode(data);
        }

        sc.close();
        heapObj.designMinHeap();

        System.out.println("Resulting min heap is:");
        heapObj.displayHeap();

        System.out.println("After removing the minimum element (root node) " +
                heapObj.removeRoot() + ", the min heap is:");
        heapObj.displayHeap();
    }
}
