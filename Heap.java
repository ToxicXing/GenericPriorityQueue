package Practise11;
import java.util.*;
public class Heap<E> {
	private static final int INITIAL_CAPACITY = 11;
	private final Comparator<? super E> comparator;
	private Object[] queue;
	private int size;
	public Heap() {
		this(INITIAL_CAPACITY, null);
	}
	public Heap(int intialCapacity) {
		this(intialCapacity, null);
	}

	public Heap(int initialCapacity, Comparator<? super E> c) {
		if (initialCapacity < 1) {
			throw new IllegalArgumentException("capacity cannot be less than 0");
		}
		this.comparator = c;
//		this.queue = new E[initialCapacity]; --> 泛型不能创建数组！
		this.queue = new Object[initialCapacity];
	}
	public Heap(Object[] array) {
		if (queue == null || queue.length == 0) {
			throw new IllegalArgumentException("Input array can not be null or empty");
		}
		this.queue = Arrays.copyOf(array, array.length);
		this.size = queue.length;
		this.comparator = null;
		heapify();
	}
	@SuppressWarnings("unchecked")
	public E poll() {
		if (size == 0) {
			return null;
		}
		int s = size--;
		E res = (E)queue[0];
		E last = (E)queue[s];
		queue[s] = null;
		siftDown(0, last);
		return res;
	}
	public boolean offer(E ele) {
		if (ele == null) {
			throw new NullPointerException();
		}
		int s = size++;
		if (s == 0) {
			queue[0] = ele;
		} else {
			siftUp(s, ele);
		}
		return true;
	}
	@SuppressWarnings("unchecked")
	public E peek() {
		if (size == 0) {
			return null;
		}
		return (E) queue[0];
	}
	public E update(int index, E ele) {
		if (comparator != null) {
			return updateComparator(index, ele);
		} else {
			return updateComparable(index, ele);
		}
	}
	public int size() {
		return size;
	}
	public boolean isEmpty() {
		return size == 0;
	}
	public boolean isFull() {
		return size == queue.length;
	}
	@SuppressWarnings("unchecked")
	private E updateComparator(int index, E ele) {
		Object target = queue[index];
		queue[index] = ele;
		if (comparator.compare((E)target, ele) < 0) {
			siftUp(index, ele);
		} else {
			siftDown(index, ele);
		}
		return (E)target;
	}
	
	@SuppressWarnings("unchecked")
	private E updateComparable(int index, E ele) {
		Object target = queue[index];
		queue[index] = ele;
		if (((Comparable<? super E>) ele).compareTo((E)target) <0) {
			siftUp(index, ele);
		} else {
			siftDown(index, ele);
		}
		return (E)target;
	}
	@SuppressWarnings("unchecked")
	private void heapify() {
		// last index = size - 1. 
		// parent of last index = (size - 1 - 1) / 2
		for (int i = size >>> 1 - 1 ; i >= 0; i--) {
			siftDown(i, (E)queue[i]);
		}
	}
	@SuppressWarnings("unchecked")
	private void siftUp(int k, Object x) {
		if (comparator != null) {
			siftUpComparator(k, (E)x);
		} else {
			siftUpComparable(k, (E)x);
		}
	}
	private void siftDown(int k, E x) {
		if (comparator != null) {
			// percolate up based on comparator
			siftDownComparator(k, x);
		} else {
			// percolate down based on natural order
			siftDownComparable(k, x);
		}
	}
	@SuppressWarnings("unchecked")
	private void siftDownComparator(int pos, E ele) {
		int half = size >>> 1;
		while (pos < half) {
			int leftChild = (pos << 1) + 1;
			int rightChild = leftChild + 1;
			int child = leftChild;
			if (rightChild < size && comparator.compare((E)queue[leftChild], (E)queue[rightChild]) > 0) {
				child = rightChild;
			}
			if (comparator.compare((E)queue[child], (E)ele) >= 0) {
				break;
			}
			queue[pos] = queue[child];
			pos = child;
		}
		// put inserted element to the correct position
		queue[pos] = ele;
	}
	@SuppressWarnings("unchecked")
	private void siftDownComparable(int pos, E ele) {
		int half = size >>> 1;
		while (pos < half) {
			int leftChild = (pos << 1) + 1;
			int child = leftChild;
			int rightChild = leftChild + 1;
			Object ch = queue[leftChild];
			// find the least priority child
			if (rightChild < size && ((Comparable<? super E>) ch).compareTo((E) queue[rightChild]) > 0) {
				child = rightChild;
			}
			if (((Comparable<? super E>) ele).compareTo((E) queue[child]) <= 0) {
				break;
			}
			queue[pos] = queue[child];
			pos = child;
		}
		queue[pos] = ele;
	}
	@SuppressWarnings("unchecked")
	private void siftUpComparator(int pos, E ele) {
		while (pos > 0) {
			int parent = (pos - 1) >>> 2;
			if (comparator.compare(ele, (E)queue[parent]) >= 0) {
				break;
			}
			queue[pos] = queue[parent];
			pos = parent;
		}
		queue[pos] = ele;
	}
	@SuppressWarnings("unchecked")
	private void siftUpComparable(int pos, E ele) {
		while (pos > 0) {
			int parent = (pos - 1) >>> 2;
			if (((Comparable<? super E>) ele).compareTo((E)queue[parent]) >= 0) {
				break;
			}
			queue[pos] = queue[parent];
			pos = parent;
		}
		queue[pos] = ele;
	}
}
