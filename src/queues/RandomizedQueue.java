package queues;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Boris Mitioglov on 23/10/2018.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] array;
    private int numberOfItems = 0;

    private final int startElement = 0;
    private int endElement = -1;

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
        if (item == null) throw new IllegalArgumentException("item is null");
        if (array.length == numberOfItems) {
            resize(array.length * 2);
        }
        array[endElement + 1] = item;
        numberOfItems++;
        endElement++;
    }           // add the item

    public Item dequeue() {
        if (numberOfItems == 0) throw new NoSuchElementException("queue is empty");
        if (numberOfItems > 0 && numberOfItems == array.length / 4) {
            resize(array.length / 2);
        }
        int randomIndex = StdRandom.uniform(startElement, endElement + 1);
        Item value = array[randomIndex];
        array[randomIndex] = array[endElement];
        array[endElement] = null;
        endElement--;
        numberOfItems--;
        return value;
    }                    // remove and return a random item


    public Item sample() {
        if (numberOfItems == 0) throw new NoSuchElementException("queue is empty");
        int randomIndex = StdRandom.uniform(startElement, endElement + 1);
        return array[randomIndex];
    }                     // return a random item (but do not remove it)


    public Iterator<Item> iterator() {
        return new Iterator<Item>() {

            private int currentIndex = 0;
            private int[] randomIndices = new int[numberOfItems];

            {
                for (int i = 0; i < randomIndices.length; i++) {
                    randomIndices[i] = i;
                }
                StdRandom.shuffle(randomIndices);
            }

            @Override
            public boolean hasNext() {
                return currentIndex < numberOfItems;
            }

            @Override
            public Item next() {
                if (!hasNext()) throw new NoSuchElementException("queue is empty");
                Item nextVal = array[randomIndices[currentIndex]];
                currentIndex++;
                return nextVal;
            }
        };
    }         // return an independent iterator over items in random order

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < numberOfItems; i++) {
            copy[i] = array[i];
        }
        array = copy;
    }


    public static void main(String[] args) {
        RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<>();
        randomizedQueue.enqueue(1);
        randomizedQueue.enqueue(2);
        randomizedQueue.enqueue(3);
        randomizedQueue.enqueue(4);
        randomizedQueue.enqueue(5);
        randomizedQueue.enqueue(6);
        randomizedQueue.enqueue(7);

        System.out.println("dequed element = " + randomizedQueue.dequeue());
        System.out.println("---------");
        System.out.println("Iterator #1");
        for (Integer i : randomizedQueue) {
            System.out.println("element = " + i);
        }
        System.out.println("---------");
        System.out.println("Iterator #2");
        for (Integer i : randomizedQueue) {
            System.out.println("element = " + i);
        }
    }   // unit testing (optional)
}