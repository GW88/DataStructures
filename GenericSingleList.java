package datastructures.listImplementation;

import datastructures.adt_s.MyList;
import java.util.NoSuchElementException;

/**
 * this example is simply for demostration parposes,
 * Many usefull things  can be added to methods, as well as many things can be deleted,
 * but when i started creating it from scratch my goal was to keep class readable,
 * structured and iterativ. so i think my goal is achieved,
 * you can change anything you want after cloning it from github.
 * @param <E>
 */


public class GenericSingleList<E> implements MyList<E>{

    static class GenericNode<E>{
        private E value;
        private GenericNode<E> nextElement;

        public GenericNode(){

        }
        public GenericNode(E value){
            this.value=value;

        }
        public GenericNode(E value, GenericNode<E> nextElement){
            this.value = value;
            this.nextElement= nextElement;
        }

        public E getValue() {
            return value;
        }

        public void setValue(E value) {
            this.value = value;
        }

        public GenericNode<E> getNextElement() {
            return nextElement;
        }

        public void setNextElement(GenericNode<E> nextElement) {
            this.nextElement = nextElement;
        }

    }
    private GenericNode<E> root;
    private GenericNode<E>previous;
    private GenericNode<E> temporary;


    /**
     * Method somply addes element to node
     * @param value
     */
    @Override
    public void add(E value) {
        GenericNode<E> temp = new GenericNode<>(value, null);
        if(root == null){
            root = temp;
        }else{
            temp.setNextElement(root);
            root = temp;
        }

    }

    /**
     * Method returns a value of index provided by user, if root is null or index is less then 0
     * throw exception. elseretrive the data.
     * count starts from root, and first element is at index 0; better to redefine index and force
     * it to start from 1.
     * @param index
     * @return
     * @throws IndexOutOfBoundsException
     */
    @Override
    public E get(int index) throws IndexOutOfBoundsException {
        temporary = root;
        if(root == null || index< 0){
            throw new IndexOutOfBoundsException();
        }else{
            for(int i=0; i<index; i++){
                temporary = temporary.getNextElement();
            }
            previous = temporary;
        }
        return previous.getValue();
    }

    /**
     *
     * @return
     * @throws NoSuchElementException
     * meThod return value of Last element added to structure.
     *
     */

    public E getFirst() throws NoSuchElementException{
        if(root == null){
            throw new NoSuchElementException();
        }
        return root.getValue();
    }

    /**
     * method returns first element added to structure,
     * @return
     * @throws NoSuchElementException
     */
    public E getLast() throws NoSuchElementException{
        if (root == null){
            throw new NoSuchElementException();
        }else{
            temporary = root;
            while (temporary.getNextElement()!=null){
                previous = temporary;
                temporary = temporary.getNextElement();
            }
            previous = previous.getNextElement();
        }
        return previous.getValue();
    }

    /**
     *
     * @param index
     * @param value
     * @return
     * @throws IndexOutOfBoundsException
     *
     * method changes excisting value in structrue, root cant be null and index can not be
     * out of bounds, method can be rewritten easily to add element to structure if index is out of bound,
     * for ex: if index is out of bounds then add element at the begening ot at last possition
     * deppending where entered index is.
     */
    @Override
    public E set(int index, E value) throws IndexOutOfBoundsException {
        int size = size();
        temporary = root;
        if(root == null ||index<0 || index> size){
            throw  new IndexOutOfBoundsException();
        }else{
            for(int i=0; i<index; i++){
                temporary = temporary.getNextElement();
            }
            previous = temporary;
            previous.setValue(value);

        }
        return previous.getValue();

    }


    /**
     * i know it's worst implementation of size method. just declare size variable and inc it
     * on every step whould be more efficient. my advice is to rewrite it and use it as adviced.
     * @return
     */
    @Override
    public int size() {
        int count = 0;
        temporary = root;
        while(temporary.getNextElement()!=null){
            temporary = temporary.getNextElement();
            count++;
        }
        return count;
    }


    /**
     *
     * @param value
     * @return
     * iterate until needed elements is not found.
     */
    @Override
    public boolean contains(E value) {
        temporary = root;
        while(temporary.getNextElement()!=null){
            temporary = temporary.getNextElement();
            previous = temporary;
            if(previous.getValue().equals(value))
                return true;
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    /**
     *  simply add element at the end of list, end is begening of list, first entered element
     *  is last.
     * @param value
     */
    public void addLast(E value){
        GenericNode<E> list = new GenericNode<>(value, null);
        if(root == null){
            root = list;
        }else{
            temporary = root;
            while (temporary.getNextElement()!=null){
                temporary = temporary.getNextElement();
                previous = temporary;
            }
            previous.setNextElement(list);
        }
    }

    /**
     * just remove pointer to root and move it one step a head
     */
    public void removeFirst(){
        if(root != null){
            root = root.getNextElement();
        }
    }

    /**
     * find it null it. :)))
     */
    public void removeLast(){
        if(root==null){
            temporary = root;
        }else{
            while(temporary.getNextElement()!=null){
                previous = temporary;
                temporary = temporary.getNextElement();
            }
            previous.setNextElement(null);
        }
    }

    /**
     * To Turn this method in Boolean type will be much more cooler,
     * @param value
     */
    public void remove(E value){
        if(root == null){
            System.out.println("list is empty");
        }else{
            temporary = root;
            while(temporary!=null && !temporary.getValue().equals(value)){
                previous = temporary;
                temporary = temporary.getNextElement();
            }
            if(root.getValue().equals(value)){
                root = root.getNextElement();

            }else if(temporary!=null){
                previous.setNextElement(temporary.getNextElement());
            }

        }
    }

    /**
     * count starts from zero, element is added after provided index,
     * for example: if you want to add eleemnt at 0 index, it will be inserted after 0, at index 1
     * if you want to add element after last index it will be added after last.
     * @param index
     * @param value
     * @throws IndexOutOfBoundsException
     */
    public void insertAfter(int index, E value) throws IndexOutOfBoundsException{
        GenericNode<E> list = new GenericNode<>(value);
        temporary = root;
        if(root == null || index<0){
            throw new IndexOutOfBoundsException();
        }
        for(int i=0; i<=index; i++){
            previous = temporary;
            temporary = temporary.getNextElement();
        }
        previous.setNextElement(list);
//        previous.nextElement = list;
        list.nextElement = temporary;

    }

    /**
     * if you want to find how much dup's concrit element has,
     * path it to method and provide with object ref.
     * @param value
     * @param list
     * @return
     */
    public int duplicateValues(E value, GenericSingleList<E> list){
        int count = 0;
        int i = 0;
        for( i=0; i<=list.size(); i++){
            if(list.get(i).equals(value))
                count++;
        }

        return count;
    }


    /**
     * ibetter practice is to delete them one by one
     */
    @Override
    public void clear() {
        root = null;


    }
    public void compileList(){
        GenericNode<E> temp = root;
        while (temp.getNextElement() !=null){
            System.out.println(temp.getValue());
            temp = temp.getNextElement();
        }
        System.out.println(temp.getValue());
    }


}
