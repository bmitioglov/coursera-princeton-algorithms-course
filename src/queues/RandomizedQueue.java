package queues;

import java.util.Iterator;

/**
 * Created by Boris Mitioglov on 23/10/2018.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    Item[] array;
    int numberOfItems = 0;

    public RandomizedQueue() {
        array = (Item[]) new Object[1];
    }                 // construct an empty randomized queue
    public boolean isEmpty() {
        return numberOfItems == 0;
    }                 // is the randomized queue empty?
    public int size() {
        return numberOfItems;
    }                   // return the number of items on the randomized queue
    public void enqueue(Item item) {
        if (array.length == numberOfItems) {
            resize(array.length * 2);
        }
    }           // add the item
    public Item dequeue() {
        if (numberOfItems > 0 && numberOfItems == array.length / 4) {
            resize(array.length / 2);
        }
    }                    // remove and return a random item
    public Item sample() {

    }                     // return a random item (but do not remove it)
    public Iterator<Item> iterator() {

    }         // return an independent iterator over items in random order

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < array.length; i++) {
            copy[i] = array[i];
        }
        array = copy;
    }


    public static void main(String[] args) {

    }   // unit testing (optional)
}