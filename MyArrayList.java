package datastructures.listImplementation;

import datastructures.adt_s.MyList;


import java.util.Arrays;


/**
 * This is ArrayList Implementation From scratch. Class contains basic operations
 *
 */
public class MyArrayList<E> implements MyList<E> {
    private int size = 0;
    private static final int DEFAULT_SIZE = 5;
    private Object[] elements;
   // protected transient int modificationCounter; use it if you need to know how much mod did class made


    /**
     * Two constructors, one with default size 5, another can be defined by user.
     * Arraylist class grows size with 50 %, this implementation doubles the size.
     * it can be changed to grow more efficiently easily
     */
    public MyArrayList(){
        elements =  new Object[DEFAULT_SIZE];
    }
    public MyArrayList(int numberOfElements){
        if (numberOfElements<0){
            throw new IllegalArgumentException("Ilegal lenght " + numberOfElements);
        }
        elements =  new Object[numberOfElements];
    }

    /**
     * method add;s element to array, while is has enough size method works in constant time
     * but when it need to reshrink time complexity is O(n)
     * @param value
     */
    @Override
    public void add(E value) {
        if (size == elements.length){
            checkForSize();
        }
        elements[size] = value;
        size++;

    }

    /**
     * Element can not be instered after Last entered Element,  it can be added after last
     * element but let's it keep that way.
     * @param index
     * @param value
     * @throws IndexOutOfBoundsException
     */
    public  void insertAfter(int index, E value) throws IndexOutOfBoundsException{
        if (index<0 || index>size ){
            throw new IndexOutOfBoundsException(outMesage(index));
        }
        checkForSize();
        System.arraycopy(elements, index, elements, index+1, size++);
        elements[index] = value;
        trimToSize();

    }


    /**
     * hellper method which checks for size in Add method
     */
    private void checkForSize(){
        int newSize = elements.length * 2;
        elements = Arrays.copyOf(elements, newSize);
    }

    /**
     * method returns array in size of elements, not to allocate to mucn space
     */
    public void trimToSize(){
        int currentLength = elements.length;
        if (size<currentLength){
            elements = Arrays.copyOf(elements, size);
        }
    }

    /**
     * method reads from the begening of the array and find's the index of object
     * @param value
     * @return
     */
    public int indexOf(E value){
        if (value==null){
            for (int i=0; i<size;i++){
                if (elements[i] == null)
                    return i;
            }
        }else{
            for (int c =0; c<size; c++ ){
                if (elements[c].equals(value))
                    return c;
            }
        }
        return -1;
    }

    /**
     * method reads from the end of the array, and returns the index of objcet.
     * use this methods if you know in which part of the array your element actually is.
     * @param value
     * @return
     */
    public int lastIndexOf(E value){
        if (value == null){
            for (int i = size-1; i>=0; i--){
                if (elements[i] == null)
                    return i;
            }
        }else{
            for (int c = size-1; c>=0;c--){
                if (elements[c].equals(value))
                    return c;
            }
        }
        return -1;
    }

    /**
     * returns element at index, throws exception if entered index is out of bounds
     * @param index
     * @return
     * @throws IndexOutOfBoundsException
     */
    @Override
    public E get(int index) throws IndexOutOfBoundsException {
        if (index<0 || index>size){
            throw new IndexOutOfBoundsException(outMesage(index));
        }
        return (E)elements[index];
    }
    private String outMesage(int index ){
        return "Size " + size + " Index " + index;
    }

    /**
     * replace the element in array and return replaced object to user
     * @param index
     * @param value
     * @return
     * @throws IndexOutOfBoundsException
     */
    @Override
    public E set(int index, E value) throws IndexOutOfBoundsException {
        if (index<0 || index>size){
            throw new IndexOutOfBoundsException(outMesage(index));
        }
        E temporary = (E)elements[index];
        elements[index] = value;

        return temporary;
    }

    /**
     * method searchs element in array
     * @param value
     * @return
     */
    @Override
    public boolean contains(E value) {
        for (int i=0; i<size(); i++){
            if (elements[i].equals(value))
                return true;
        }
        return false;
    }

    /**
     * remove element at index
     * @param index
     * @return
     * @throws ArrayIndexOutOfBoundsException
     */
    public E remove(int index) throws ArrayIndexOutOfBoundsException{
        if(index<0 &&index>size){
            throw new ArrayIndexOutOfBoundsException(index);
        }
        @SuppressWarnings("unchecked")
        E saveValue = (E)elements[index];
        int count  = size -index- 1;
        System.arraycopy(elements, index+1,elements, index, count);
        elements[--size] = null;
        trimToSize();
        return saveValue;
    }

    /**
     * remove element directly
     * @param value
     * @return
     */
    public boolean removeObject(E value){
        int val = this.indexOf(value);
        boolean bo = false;
        if (val>=0){
            this.remove(val);
            bo = true;
        }
        return bo;
    }

    /**
     * convert structure to array
     * @return
     */
    public Object[] toArray() {
        return Arrays.copyOf(elements, size);
    }

    /**
     * convert structure to array
     * @param array
     * @param <E>
     * @return
     */
    public <E> E[] toArrays( E[] array){
        if(array.length<size){

            return (E[]) Arrays.copyOf(elements, size);
        }else{
            System.arraycopy(elements,0, array, 0, size);
        }
        if (array.length>size){
            array[size] = null;
        }
        return array;
    }

    /**
     * method adds structure to structure. as a parameter i'h used my interface
     * you can use yours, or replace MyList with Collection Interface to achieve bigger abstraction
     * @param list
     * @return
     */
    public boolean addAll(MyList<? extends E> list){
        Object[] holder = ((MyArrayList<E>)list).toArray();
        int newSize = holder.length;
        checkForSize();
        System.arraycopy(holder, 0, elements, size, newSize);
        size += newSize;
        trimToSize();
        return newSize !=0;
    }
    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public void clear() {
        elements = null;

    }

    /**
     * lazy print and to string methods, they print elements, but if structure is empty
     * they throw exception, that can be fixed easily, ill left it for you
     *
     */
    public void printArray(){
        for (int i=0; i<elements.length; i++){
            System.out.print("["+elements[i] + ']');
        }
    }

    @Override
    public String toString() {
        trimToSize();
        return  Arrays.toString(elements);

    }
}
