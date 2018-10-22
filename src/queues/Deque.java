package queues;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node head = null;
    private Node tail = null;
    private int size  = 0;

    public Deque() {

    }

    public boolean isEmpty() {
        return head == null;
    }  // is the deque empty?


    public int size() {
        return size;
    }                       // return the number of items on the deque
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException("value is null");
        Node node = new Node(item);
        node.next = head;
        if (head != null) head.prev = node;
        head = node;
        if (tail == null) {
            tail = node;
        }
        size++;
    }          // add the item to the front
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException("value is null");
        Node node = new Node(item);
        if (tail != null) tail.next = node;
        node.prev = tail;
        tail = node;
        if (head == null) {
            head = node;
        }
        size++;
    }           // add the item to the end
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("deque is empty");
        Item valueForReturn = head.value;
        head = head.next;
        if (head == null) {
            tail = null;
        } else {
            head.prev = null;
        }
        size--;
        return valueForReturn;
    }                // remove and return the item from the front
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("deque is empty");
        Item valueForReturn = tail.value;
        tail = tail.prev;
        if (tail == null) {
            head = null;
        } else {
            tail.next = null;
        }
        size--;
        return valueForReturn;
    }                 // remove and return the item from the end
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {
            private Node currentElement = head;

            @Override
            public boolean hasNext() {
                return currentElement != null;
            }

            @Override
            public Item next() {
                if (currentElement == null) throw new NoSuchElementException("no more items to return");
                Item value = currentElement.value;
                currentElement = currentElement.next;
                return value;
            }
        };
    }         // return an iterator over items in order from front to end


    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        deque.addFirst(1);
        deque.addFirst(2);
        deque.addFirst(3);
        deque.removeFirst();

        for (Integer value: deque) {
            System.out.println(value);
        }
    }  // unit testing (optional)

    private class Node {

        Node(Item value) {
            this.value = value;
        }

        private Item value;
        private Node next;
        private Node prev;
    }
}