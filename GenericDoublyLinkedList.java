package datastructures.listImplementation;


import datastructures.adt_s.MyList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.NoSuchElementException;


/**
 *
 */
public class GenericDoublyLinkedList<T> implements MyList<T> {

    static class DoublyNode<T>{
        private T value;
        private DoublyNode<T> nextElement;
        private DoublyNode<T> previousElement;

        public DoublyNode(){

        }
        public DoublyNode(T value){
            this.value = value;
        }
        public DoublyNode(DoublyNode<T> previousElement, T value, DoublyNode<T> nextElement){
            this.previousElement = previousElement;
            this.value = value;
            this.nextElement=nextElement;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public DoublyNode<T> getNextElement() {
            return nextElement;
        }

        public void setNextElement(DoublyNode<T> nextElement) {
            this.nextElement = nextElement;
        }

        public DoublyNode<T> getPreviousElement() {
            return previousElement;
        }

        public void setPreviousElement(DoublyNode<T> previousElement) {
            this.previousElement = previousElement;
        }

    }
    private DoublyNode<T> root;
    private DoublyNode<T> current;
    private DoublyNode<T> tail;
    private static int size = 0;

    /**
     *
     * @param value
     * method populates node from both side. every node has pointer to next and previous node.
     * except root and tail., root has no previous node and tail has no next node.
     * TM O(1) constant
     */
    @Override
    public void add(T value) {
        DoublyNode<T> list = new DoublyNode<>(null, value, null);
        if(root == null){
            root = list;
            tail = list;
        }else{
            list.setNextElement(root);
            root.previousElement = list;
            root = list;

        }
        size++;

    }

    /**
     *
     * @param value
     * Method add's element after first entered element.
     * TM O(1)
     */
    public void addFirst(T value){
        DoublyNode<T> list = new DoublyNode<>(null, value, null);
        if(root == null && tail == null){
            root = list;
            tail = list;
        }else{
            tail.setNextElement(list);
            list.previousElement = tail;
            tail = list;

        }
        size++;
    }
    // O(1)
    public void removeFirst(){
        if(root !=null){
            root = root.getNextElement();
            root.previousElement = null;
        }
        size--;
    }
    // O(1)
    public void removeLast(){
        if(tail!=null){
            tail = tail.getPreviousElement();
            tail.nextElement = null;
        }
        size--;
    }

    /**
     * Removing Object from List, if list is empty simply print it out
     * @param value
     */
    public void remove(T value){
        if (root ==null){
            System.out.println("list is empty");
        }else {
            DoublyNode<T> temp  =root;
            while (temp!=null && !temp.getValue().equals(value)){
                current = temp;
                temp = temp.getNextElement();
            }
            if (root.getValue().equals(value)){
                root = root.getNextElement();
                root.previousElement = null;
            }else if(tail.getValue().equals(value)){
                tail = tail.getPreviousElement();
                tail.nextElement = null;

            }else if(temp!=null){
                DoublyNode<T> holder = temp.getNextElement();
                current.setNextElement(holder);
                holder.previousElement = current;

            }
        }
        size--;

    }

    /**
     * Msg, for exception
     * @param index
     * @return
     */
    private String outOfBoundMessage(int index){
        return " Index " + index + " Size " + size;
    }

    /**
     *
     * @param index
     * @return
     * @throws IndexOutOfBoundsException
     * get method iterates from both side, which means it's pretty fast
     * if entered index is less then zero or more then upper boud then we throw exception
     * else we start iteration if, entered index is less then or equals bstarting half of the size then
     * we iterate from root side, else we iterate from tail side.
     */
    @Override
    public T get(int index) throws IndexOutOfBoundsException {
        if(index<0){
            throw new IndexOutOfBoundsException(outOfBoundMessage(index));
        }else if(index> (size()-1)){
            throw new IndexOutOfBoundsException(outOfBoundMessage(index));
        }

        DoublyNode<T> temp = null;
        if(index<= (size()-1)/2){
            temp = root;
            for(int i=0; i<index; i++){
                temp = temp.getNextElement();
            }
        }else{
            temp = tail;

            for(int j=size()-1; j>=index;j--){
                if (j == index){
                    return temp.getValue();
                }else {
                    temp = temp.getPreviousElement();
                }

            }
        }
        return temp.getValue();
    }
    public T getLast() throws NoSuchElementException{
        if(root == null){
            throw new NoSuchElementException();
        }
        return root.getValue();
    }
    public T getFirst() throws NoSuchElementException{
        if (tail==null){
            throw new NoSuchElementException();
        }
        return tail.getValue();
    }

    /**
     *
     * @param index
     * @param value
     * @return
     * @throws IndexOutOfBoundsException
     * Set method is a like of get method, iteration works from both side.
     *
     */
    @Override // uketesi iqneba tu am metods void metodad gadaaketebt.
    public T set(int index, T value) throws IndexOutOfBoundsException {
        int size = size()-1;
        if(index < 0){
            throw new IndexOutOfBoundsException(outOfBoundMessage(index));
        }else if (index > size){
            throw  new IndexOutOfBoundsException(outOfBoundMessage(index));
        }
        DoublyNode<T> temp = null;
        if(index<=(size/2)){
            temp = root;
            for(int i=0; i< index; i++){
                temp = temp.getNextElement();
            }
            current = temp;
            current.setValue(value);
        }else{
            temp = tail;
            for(int k = size; k>=index; k--){
                if (index == k){
                    current = temp;
                    current.setValue(value);
                }else{
                    temp = temp.getPreviousElement();
                }
            }
            current = temp;
            current.setValue(value);
        }
        return current.getValue();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean contains(T value) {
        DoublyNode<T> temp = root;
        if (value.equals(root.value)){
            return true;
        }else{
            while (temp.getNextElement()!=null){
                temp = temp.getNextElement();
                current = temp;
                if(current.getValue().equals(value))
                    return true;
            }

        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * we have to null all nodes, because they have 2 pointers and deletion have to be achieved
     * this way, not like singly linkedlist where we null the root and losing main pointer
     *
     */
    @Override
    public void clear() {
        DoublyNode<T> temp = null;
       while (root.getNextElement()!=null && tail.getPreviousElement()!=null){
           current = root;
           temp = tail;
           root = root.getNextElement();
           tail = tail.getPreviousElement();
           temp = null;
           current =null;
           size--;
       }
        if(root!=null && tail!=null){
            root = null;
            tail = null;
            size--;
        }
    }

    /**
     *
     * @param index
     * @param value
     * @throws IndexOutOfBoundsException
     * method insterts element after provided index, for example: if we enter 0 at index field,
     * element will be instered after 0 index.
     */
    public void insertAfter(int index, T value) throws IndexOutOfBoundsException{
        DoublyNode<T> element = new DoublyNode<>(value);
        int elementSize = size()-1;
        if(index<0 && index>elementSize ){
            throw new IndexOutOfBoundsException(outOfBoundMessage(index));
        }
        DoublyNode<T> temp = root;
        for (int i=0; i<=index; i++){
            current = temp;
            temp = temp.getNextElement();
        }
        if (temp !=null){
            current.nextElement = element;
            element.nextElement = temp;
            temp.previousElement = element;
            element.previousElement = current;
        }else{
             current.nextElement = element;
             element.previousElement = current;
             tail = element;
        }

        size++;
    }

    /**
     *
     * @param value
     * @param list
     * @param <T>
     * find duplicates, thats all. getElement(int index) is a hellper method
     *
     */
    public static<T>  void duplicateValues(T value, GenericDoublyLinkedList<T> list){
        LinkedList<T> linkedList = new LinkedList<>();
        int count =0;
        int i=0;
        for(i=0; i<list.size(); i++){
            if(list.getElement(i).equals(value)) {
                count++;
                linkedList.add(value);
            }
        }
        System.out.println("\nDuplicates : " + count + "! Elements: " + linkedList);

    }
    private T getElement(int index) throws IndexOutOfBoundsException{
        int elements = size()-1;
        if (index<0 && index>elements){
            throw new IndexOutOfBoundsException(outOfBoundMessage(index));
        }else{
            DoublyNode<T> temp = root;
            for (int i =0; i<=index;i++){
                current = temp;
                temp = temp.getNextElement();
            }

        }
        return current.getValue();
    }


    /**
     * iterate from root
     */
    public void iterateForward(){
        System.out.println("\nIterating Forward");
        DoublyNode<T> temp = root;
        while(temp.getNextElement() !=null){
            System.out.print(""+temp.getValue()+ ',' + "\t");
            temp = temp.getNextElement();
        }
        System.out.print(""+temp.getValue()+ ',' + "\t");

    }

    /**
     * iterate from tale
     */
    public void iterateBackward(){
        System.out.println("\nIterating Backward");
        DoublyNode<T> temp = tail;
        while(temp.getPreviousElement() !=null){
            System.out.print(""+temp.getValue()+ ',' + "\t");
            temp = temp.getPreviousElement();
        }
        System.out.print(""+temp.getValue()+ ',' + "\t");
    }

    /**
     * toString method for lazy felas.
     * @return
     */
    public String toString(){
        Object[] elements = new Object[size];
        int i = 0;
        for (DoublyNode<T> x = root; x!=null; x= x.getNextElement()){
            elements[i++] = x.getValue();
        }
        return Arrays.toString(elements);
    }

}
