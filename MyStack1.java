package datastructures.listImplementation;

import java.util.Arrays;

/**
 * Stack class, build from scratch, array based, default size 10,
 * size doubles when default size rearch's its maximum, not that good but for example it will do
 * his work.
 *
 */
public class MyStack1<E> {

    private Object[] data;
    private static final int DEFAULT_SIZE = 10;
    private int size;

    public MyStack1(){
        data = new Object[DEFAULT_SIZE];
    }

    private void shrinkToSize(int capacity){
        int oldSize = data.length;
        int newSize = oldSize + oldSize;
        if (newSize - capacity < 0){
            newSize = capacity;
        }else{
           data =  Arrays.copyOf(data, newSize);
        }
    }
    private void ensureSize(int capacity){
        if (capacity - data.length>0){
            shrinkToSize(capacity);
        }
    }
    public E push(E value){
        if (size == data.length){
            ensureSize(size+1);
        }
        data[size++] = value;

        return value;
    }
    public synchronized E peek(){
        return elementData(size-1);
    }
    public synchronized E pop(){
        E value = peek();
        int temp = size;
        removeElementAtIndex(temp-1);
        return value;
    }
    public synchronized boolean empty(){
        return size == 0;
    }
    public synchronized int search(E value){
        int c = indexOf(value);
        if (c>=0) {
            return size - c;
        }
        return -1;
    }
    @SuppressWarnings("uncheked")
    private E elementData(int index){
        return (E)data[index];
    }
    private E removeElementAtIndex(int index) throws IndexOutOfBoundsException{
        if (index<0 || index> size){
            throw new IndexOutOfBoundsException(index + "");
        }
        E valueToReturn = elementData(index);
        int klm = size - index - 1;
        System.arraycopy(data, index+1, data, index, klm);
        data[--size] = null;

        return valueToReturn;
    }
    public String toString(){
        return Arrays.toString(Arrays.copyOf(data,size));
    }

    private synchronized int indexOf(E value){
        if (value == null){
            for (int i = 0; i<size; i++){
                if (data[i] == null)
                    return i;
            }
        }else{
            for (int c = 0; c<size; c++){
                if (data[c].equals(value))
                    return c;
            }
        }
        return -1;
    }

}
