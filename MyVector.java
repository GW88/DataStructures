package datastructures.listImplementation;

import datastructures.adt_s.MyList;

import java.util.Arrays;
import java.util.Collection;
import java.util.NoSuchElementException;


/**
 *
 * if you ever fill like using Vector, Better to use CompyOnWriteArrayList, or Arraylist and then synchronize them
 * using Collections syncronize method.
 * this class has 4 constructors, when class is instantiated empty constructor is allocating 10 spots for you,
 * second constuctor lets you define what size of vector do you want, third constructor lets you define the size and
 * incrementing number, forth constructor simply takes entire data from any class which is in Collection hierarchy
 * allmost every method has twin, one from Collection and one from Vector class.
 * duplicates are allowed, as well as null's
 */
public class MyVector<E> implements MyList<E> {

    private Object[] elements;
    private int size;
    private int capacitySize;
    private transient int modCount =0 ;

    public MyVector(){
        this(10);

    }
    public MyVector(int initialCapacity){
       this(initialCapacity,0);
    }
    public MyVector(int startingCapacity, int incrementCapacity){
        if(startingCapacity<0){
            throw new IllegalArgumentException("Illegal Length " + startingCapacity);
        }
        elements = new Object[startingCapacity];
        capacitySize = incrementCapacity;
    }
    public MyVector(Collection<? extends E> list){
        elements = list.toArray();
        size = elements.length;
        elements = Arrays.copyOf(elements, size);
    }


    @Override
    public void add(E value) {
        addElement(value);

    }
    public synchronized void addElement(E value){
        if (size == elements.length){
            checkForSize(size+1);
        }
        elements[size++] = value;
    }

    /**
     * if user enters value which will increase size capacitySize will work, else size will double
     * @param inSize
     */
    private void shrink(int inSize){
        int oldSize = elements.length;
        int newSize = oldSize + ((capacitySize>0)? capacitySize : oldSize);
        if (newSize - inSize < 0){
            newSize = inSize;
        }
        elements = Arrays.copyOf(elements, newSize);
    }

    /**
     * method checks for size. if not enough space, then it shrinks
     * @param newSize
     */
    private void checkForSize(int newSize){
        modCount++;
        if(newSize - elements.length >0){
            shrink(newSize);
        }
    }

    /**
     * method returns aloccated space to element size
     */
    public synchronized void trimToSize(){
        modCount++;
        int oldValue = elements.length;
        if (size<oldValue){
            elements = Arrays.copyOf(elements,size);
        }
    }

    /**
     * returns object at index,
     * @param index
     * @return
     * @throws IndexOutOfBoundsException
     */
    @Override
    public E get(int index) throws IndexOutOfBoundsException {

        return elementAt(index);
    }

    /**
     * method returns element at index, get method is from colleciton elementAt is from Vector class.
     * @param index
     * @return
     * @throws IndexOutOfBoundsException
     */
    public synchronized E elementAt(int index) throws IndexOutOfBoundsException{
        if (index<0 || index > size){
            throw new IndexOutOfBoundsException(outMSG(index));
        }
        return elementData(index);
    }
    private String outMSG(int index){
        return "Size "  + size + " Index " + index;
    }

    /**
     * set method sets element at index, it uses a inserElementAt method which is Vector original method
     * @param index
     * @param value
     * @return
     * @throws IndexOutOfBoundsException
     */
    @Override
    public E set(int index, E value) throws IndexOutOfBoundsException {
        return insertElementAt(index, value);
    }
    public synchronized E insertElementAt(int index, E value) throws IndexOutOfBoundsException{
        if (index<0 || index > size){
            throw new IndexOutOfBoundsException(outMSG(index));
        }
        E oldValue = elementData(index);
        elements[index] = value;
        modCount++;


        return oldValue;
    }

    /**
     * method inserts element after some index.
     * @param index
     * @param value
     * @throws IndexOutOfBoundsException
     */
    public synchronized void insertElementAfter(int index, E value)throws IndexOutOfBoundsException{

        if (index<0 || index> size){
            throw new IndexOutOfBoundsException(outMSG(index));
        }
        checkForSize(size + 1);
        int gg = size -index;
        System.arraycopy(elements, index, elements, index+1, gg);
        elements[index] = value;
        size++;
        modCount++;

    }

    /**
     * returns first element if its empty throw an exception
     * @return
     */
    public synchronized E firstElement(){
        if (size == 0){
            throw new NoSuchElementException();
        }
        return elementData(0);
    }

    /**
     * returns last element, if structure is empty throw exception
     * @return
     */
    public synchronized E lastElement(){
        if (size==0){
            throw new NoSuchElementException();
        }
        return elementData(size-1);
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * this method tells us how much spots are allocated fro elements.
     * @return
     */
    public synchronized int capacity(){
        return elements.length;
    }

    /**
     * contains and search does same thing, one is from colleciton another belongs to vector class.
     * @param value
     * @return
     */
    @Override
    public boolean contains(E value)
    {
        return search(value);
    }

    public synchronized boolean search(E value){
        for (int i=0; i<size; i++){
            if (elements[i].equals(value))
                return true;
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * clead and removeAllElements does same thing, one from collection another from vector.
     */
    @Override
    public void clear() {
        removeAllEelements();
    }
    public synchronized void removeAllEelements(){
        for (int i=0; i<size; i++){
            elements[i] = null;
            modCount++;
        }
        size = 0;
    }

    /**
     * copy elements to array
     * @return
     */
    public Object[] toArray(){
        return Arrays.copyOf(elements, size);
    }


    /**
     * coppy elemenets to array
     * @param array
     */
    public synchronized void copyInto(Object[] array){
        System.arraycopy(elements, 0, array, 0, size);
    }

    /**
     * copy elements to array and return array
     * @param array
     * @return
     */
    public synchronized E[] toArrays(E[] array){
        if (array.length<size){
            return (E[])Arrays.copyOf(elements,size);
        }else{
            System.arraycopy(elements, 0, array, 0, size);
        }
        if (array.length>size){
            array[size] = null;
        }
        return array;
    }


    /**
     * first occurrence
     * @param value
     * @return
     */
    public synchronized int indexOf(E value){
        if (value == null){
            for (int i=0; i<size; i++){
                if (elements[i] == null)
                    return i;
            }
        }else{
            for (int g =0; g<=size; g++){
                if (elements[g].equals(value))
                    return g;
            }
        }
        return -1;
    }

    /**
     * last  occurrence
     * @param value
     * @return
     */
    public synchronized int lastIndexOf(E value){
        if (value==null){
            for (int i = size-1; i>=0; i--){
                if (elements[i] == null)
                    return i;
            }
        }else{
            for (int f = size-1; f>=0; f--){
                if (elements[f].equals(value))
                    return f;
            }
        }
        return -1;
    }

    @SuppressWarnings("unchecked")
    private E elementData(int index){
        return (E)elements[index];
    }

    /**
     * removes element at index. using method removeElementAt w
     * @param index
     * @return
     */
    public synchronized E remove(int index){
        return removeElementAt(index);
    }
    public synchronized E removeElementAt(int index) throws IndexOutOfBoundsException{
        if (index<0 || index>size){
            throw new IndexOutOfBoundsException(outMSG(index));
        }
        modCount++;
        E oldValue = elementData(index);
        int kk = size - index - 1;
        System.arraycopy(elements, index+1, elements, index, kk);
        elements[--size] = null;

        return oldValue;
    }

    /**
     * remove Object directly
     * @param value
     * @return
     */
    public synchronized boolean removeElement(E value){
        int v = indexOf(value);
        boolean bo = false;
        if (v>=0){
            removeElementAt(v);
            modCount++;
            bo = true;
        }
        modCount++;
        return bo;
    }
    public synchronized boolean removeO(E value){
        return removeElement(value);
    }

    /**
     * adds all method simply takes entire informaiton and adds them to structure,
     * in my case class should be of type MyList which is my interface. you can use yours, or simply
     * change it with collection to achieve bigger abstraction
     * @param list
     * @return
     */
    public synchronized boolean addAll(MyList<? extends E> list){
        Object[] newArray = ((MyVector<E>)list).toArray();
        int newSize = newArray.length;
        checkForSize(size+newSize);
        System.arraycopy(newArray, 0, elements, size, newSize);
        size+= newSize;
        modCount++;

        return size!=0;
    }


    /**
     * to string method for lazy felas. which lvoe to compile object directly
     * @return
     */
    @Override
    public String toString()
    {
        return  Arrays.toString(Arrays.copyOf(elements,size));
    }
    public void iterateForward(){
        System.out.println("Forward");
        for (int i= 0; i<size; i++){
            System.out.println(elements[i]);
        }
    }
    public void iterateBackWard(){
        System.out.println("BackWard");
        for(int i = size-1; i>=0; i--){
            System.out.println(elements[i]);
        }
    }

    /**
     * method returns how many modificaiton ahs been made in structure if i have not misssed modCount
     * in some methods. if yes simply add it and use it.
     * @return
     */
    public  int modificationsMade(){
        return modCount;
    }
}
