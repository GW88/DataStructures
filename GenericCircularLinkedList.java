package datastructures.listImplementation;

import datastructures.adt_s.MyList;

import java.util.Arrays;


/**
 * Circular Linked List, Big + of this structure is it does not keep null's
 * We will Make some Random Methods.
 *
 */
public class GenericCircularLinkedList<A> implements MyList<A> {

    private static class CircularNode<A>{
        private A value;
        private CircularNode<A> nextElement;

        public CircularNode(){

        }
        public CircularNode(A value){
            this.value = value;
        }
        public CircularNode(A value, CircularNode<A>nextElement){
            this.value = value;
            this.nextElement=nextElement;
        }

        public A getValue() {
            return value;
        }

        public void setValue(A value) {
            this.value = value;
        }

        public CircularNode<A> getNextElement() {
            return nextElement;
        }

        public void setNextElement(CircularNode<A> nextElement) {
            this.nextElement = nextElement;
        }
    }
    private static int size;
    private CircularNode<A> root;
    private CircularNode<A> tail;
    private CircularNode<A> current;

    public GenericCircularLinkedList(){
        this.root = null;
        this.tail = null;
        GenericCircularLinkedList.size = 0;
    }


    /**
     * Method add's element to structure, if root is empty then we have to set as next element just
     * entered value, and assign it to root, second element set root as next element and root set new eleemnt as next
     * element, that;s hwo it move to circle
     * @param value
     */
    @Override
    public void add(A value) {
        CircularNode<A> list = new CircularNode<>(value);
        if(root == null){
            list.setNextElement(list);
            root = list;
        }else{
            list.setNextElement(root.getNextElement());
            root.setNextElement(list);

        }
        size++;
    }

    /**
     * add element at first position.
     * @param value
     */
    public void addFirst(A value){
        CircularNode<A> temp = new CircularNode<>(value);
        CircularNode<A> holder = root.getNextElement();
        while (holder.getNextElement()!=root){
            holder = holder.getNextElement();
        }
        holder = holder.getNextElement();
        tail = root.getNextElement();
        holder.setNextElement(temp);
        temp.nextElement = tail;
        root = temp;
        size++;

    }
    private String outOfBoundsMsg(int index){
        return "index " + index + " Size " + (size() - 1);
    }

    /**
     * get element at index, if index is out of upper or lower bound Exception is thrown
     * @param index
     * @return
     * @throws IndexOutOfBoundsException
     */
    @Override
    public A get(int index) throws IndexOutOfBoundsException {
        int i = size() - 1;
        if(index<0 ){
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }else if (index> i){
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
        CircularNode<A> temp = root.getNextElement();
        for (int c=i; c>=index; c--){
            current = temp;
            temp = temp.getNextElement();
        }
        return current.getValue();
    }

    /**
     * getLast, getFirst, getLAstElement, getFirstElement, All 4 method does same thing,
     * first 2 is void, second 2 returns value.
     */
    public void getLast() {
        current = root.getNextElement();
        System.out.println(current.getValue());
    }
    public void getFirst(){
        System.out.println(root.getValue());
    }
    public  A getLastElement(){
        current = root.getNextElement();
        return current.getValue();
    }
    public  A getFirstElement(){
        return root.getValue();
    }

    /**
     * set the element at given position and return deleted value
     * @param index
     * @param value
     * @return
     * @throws IndexOutOfBoundsException
     */
    @Override
    public A set(int index, A value) throws IndexOutOfBoundsException {
        int h = size() - 1;
        A valueToReturn = null;
        if (index<0){
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }else if(index>h){
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
        if(index == h){
            current = root.getNextElement();
            valueToReturn = current.getValue();
            current.setValue(value);
        }else if( index == 0){
            current = root;
            valueToReturn = current.getValue();
            current.setValue(value);
        }else{
            CircularNode<A> temp = root.getNextElement();
            for (int i = h; i>=index; i--){
                current = temp;
                temp = temp.getNextElement();
            }
            valueToReturn = current.getValue();
            current.setValue(value);
        }
        return valueToReturn;
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * if element is in the structure we return true, else false.
     * @param value
     * @return
     */
    @Override
    public boolean contains(A value) {
        CircularNode<A> temp = root.getNextElement();
        boolean bo = false;
        if (getLastElement().equals(value)){
            bo = true;
        }else if(getFirstElement().equals(value)){
            bo = true;
        }else {
            while (temp.getNextElement()!=root){
                temp = temp.getNextElement();
                current = temp;
                if (current.getValue().equals(value))
                    bo=true;
            }
        }

        return bo;
    }
    public void removeLast(){
        if (root!=null){
            current = root.getNextElement();
            root.setNextElement(current.getNextElement());
        }
        size--;
    }
    public void removeFirst(){
        if (root!=null){
            CircularNode<A> temp = root.getNextElement();
            while (temp.getNextElement()!=root){
                temp = temp.getNextElement();
                current = temp;
            }
            current.setNextElement(root.getNextElement());
            root = current;
        }
        size--;
    }
    public void remove(A value){
        CircularNode<A> temp = root.getNextElement();
        if (isEmpty()){
            System.out.println("List is empty");
        }else if(getLastElement().equals(value)){
            this.removeLast();
        }else if (getFirstElement().equals(value)){
            this.removeFirst();
        }else {
            while (temp!=root && !temp.getValue().equals(value)){
                current = temp;
                temp = temp.getNextElement();
            }
            if(temp!=root){
                current.setNextElement(temp.getNextElement());
                size--;
            }
        }
        if(temp ==root){
            System.out.println("No such element in the list");
        }

    }


    public void insertAfter(int index, A value) throws IndexOutOfBoundsException{
        CircularNode<A> list = new CircularNode<>(value);
        CircularNode<A> temp = root.getNextElement();
        int k = (size -1);
        if (index<0){
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }else if(index>k){
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
        for (int i = 0; i<=index; i++){
            current = temp;
            temp = temp.getNextElement();
        }
        if (current.equals(root)){
            current.setNextElement(list);
            list.nextElement = temp;
            root = list;
        }else{
            current.setNextElement(list);
            list.setNextElement(temp);
        }
        size++;
    }



    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public void clear() {
        CircularNode<A> temp = root.getNextElement();
        while (temp.getNextElement()!=root){
            current = temp;
            temp = temp.getNextElement();
            current = null;
            size--;

        }
        current = temp;
        temp = temp.getNextElement();
        current = null;
        size--;
        temp =null;
        root = null;
        size--;

    }
    public void iterateFromRoot(){
        if (root == null){
            System.out.println( "[ ]");
        }else {
            CircularNode<A> temp = root.getNextElement();
            while (temp.getNextElement() != root) {
                System.out.println(temp.getValue());
                temp = temp.getNextElement();
            }
            System.out.println(temp.getValue());
            temp = temp.getNextElement();
            System.out.println(temp.getValue());
        }

    }
    public String toString(){
        Object[] elements = new Object[GenericCircularLinkedList.size];
        CircularNode<A> temp = root.getNextElement();
        int i = 0;
        for (CircularNode<A> x = temp; x!=root; x= x.getNextElement()){
            elements[i++] = x.getValue();
        }
        elements[i++] = root.getValue();
        return Arrays.toString(elements);
    }

}
