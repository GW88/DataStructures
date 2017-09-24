package datastructures.listImplementation;

import java.util.EmptyStackException;

/**
 * Stack class, inherit's MyVector, it takes all logic from its super class,
 * follows LIFO pcinciple's, if we trust Java documentation better to use deque interface when we want to
 * use stack.  methods will be without comments, they are pretty straight forward.
 */
public class MyStack<E> extends MyVector<E> {

    public MyStack(){
         //default constructor inherits MyVector default one,
        // if you want all for constructor inheritance just super them.
    }
    public E push(E value){
        super.addElement(value);

        return value;
    }
    public synchronized E pop(){
        E value;
        int items = super.size();
        value = peek();
        removeElementAt(items - 1);
        return value;
    }
    public synchronized E peek(){
        int items = super.size();

        if (items == 0){
            throw new EmptyStackException();
        }
        return super.elementAt(items-1);
    }
    public synchronized boolean empty(){
        return super.size() == 0;
    }
    public synchronized int search_s(E value){
        int items = super.indexOf(value);
        if (items>=0){
            return super.size()-items;
        }
        return -1;
    }
}

