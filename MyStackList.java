package datastructures.listImplementation;



/**
 * this is a stack implementation which is node based.
 * you can find all the fife methods below.
 * Stack follows LIFO principle. last element pushed to stack will left it first.
 * List Based implementaiton of Stack is Much faster then Array based, 4 from 5 methods work in constant time
 * O(1)
 */
public class MyStackList<E> {

    private static class StackNode<E>{
        private E value;
        private StackNode<E> nextValue;

        public StackNode(){

        }
        public StackNode(E value){
            this.value = value;
        }
        public StackNode(E value, StackNode<E> nextValue){
            this.value = value;
            this.nextValue = nextValue;
        }

        public E getValue() {
            return value;
        }

        public void setValue(E value) {
            this.value = value;
        }

        public StackNode<E> getNextValue() {
            return nextValue;
        }

        public void setNextValue(StackNode<E> nextValue) {
            this.nextValue = nextValue;
        }
    }
    private StackNode<E> root;
    private StackNode<E> temporary;
    private StackNode<E> previous;
    private int size;

    /**
     * Method push's element in stack
     * @param value
     * @return
     */
    public synchronized E push(E value){
        StackNode<E> list = new StackNode<>(value, null);
        if (root == null){
            root = list;
        }else{
            list.setNextValue(root);
            root = list;
        }
        size++;
        return list.getValue();
    }

    /**
     * method returns the last pushed element value
     * @return
     */
    public synchronized E peek(){
        return root.getValue();
    }

    /**
     * method deletes last pushed element. returns element which have been deleted
     * @return
     */
    public synchronized E pop(){
        E valueToReturn = root.getValue();
        root = root.getNextValue();
        size--;
        return valueToReturn;
    }

    /**
     * empty method checks if stack is empty
     * @return
     */
    public synchronized boolean empty(){
        return root == null;
    }

    /**
     * search method returns the index posiiton of element is stack.
     * last element pushed in should be at index 1
     * @param value
     * @return
     */
    public synchronized int search(E value){
        temporary = root;
        int g = 0;
        while (temporary.getNextValue() != null) {
            previous = temporary;
            temporary = temporary.getNextValue();
            g++;
            if (previous.getValue().equals(value))
                return g;

        }
        previous = temporary;
        g++;
        if (previous.getValue().equals(value)){
            return g;
        }

        return -1;
    }

    public void compileStackList(){
        temporary = root;
        while (temporary.getNextValue() != null){
            System.out.println(temporary.getValue());
            temporary = temporary.getNextValue();
        }
        System.out.println(temporary.getValue());
    }

}
