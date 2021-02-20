import java.util.Iterator;

public class CircularLinkedList<E> implements Iterable<E> {
	protected static class Node<E> {
		protected E data;
		protected Node<E> next;

		public Node(E data) {
			this.data = data;
			this.next = null;
		}
	}

	// Pointers
	Node<E> head;
	Node<E> tail;
	int size;

	public CircularLinkedList() {
		head = null;
		tail = null;
		size = 0;
	}

	protected Node<E> getNode(int index) {
		if (index < 0 || index >= size)
			throw new IndexOutOfBoundsException("Attempted to get index " + index + " when size is " + size);

		Node<E> current = head;

		for (int i = 0; i < index; i++)
			current = current.next;

		return current;
	}

	public void shiftNodeRight(int index) {
		if (index < 0 || index >= size)
			throw new IndexOutOfBoundsException("Attempted to get index " + index + " when size is " + size);

		if (size == 2) { // Swap head and tail
			head = head.next;
			tail = tail.next;
		} else if (index == 0) { // Shift head
				Node<E> nextNode = head.next;

				head.next = nextNode.next;
				tail.next = nextNode;
				nextNode.next = head;
				head = nextNode;
			} else {
				Node<E> previousNode = getNode(index - 1);

				if (index == size - 1) { // Shift tail
					previousNode.next = head;
					tail.next = head.next;
					head.next = tail;
					tail = head;
					head = tail.next;
				} else { // Shift in-between
					Node<E> currentNode = previousNode.next;
					previousNode.next = currentNode.next;
					currentNode.next = currentNode.next.next;
					previousNode.next.next = currentNode;

					// Ensures tracking the tail if the node landed after the tail
					if (tail.next == currentNode) {
						tail = currentNode;
						tail.next = head;
					}
				}
			}
//		}
	}

	public E getData(int index) {
		return getNode(index).data;
	}

	public E setData(int index, E data) { // Given from template
		if (index < 0 || index >= size)
			throw new IndexOutOfBoundsException("Attempted to get index " + index + " when size is " + size);

		Node<E> target = getNode(index);
		E oldData = target.data;
		target.data = data;

		return oldData;
	}

	public void add(E item) {
		add(size, item);
	}

	public void add(int index, E item) {
		if (index < 0 || index > size)
			throw new IndexOutOfBoundsException("Attempted to add to index " + index + " when size is " + size);

		Node<E> createdNode = new Node<>(item);

		if (head == null) {
			head = tail = createdNode.next = createdNode;
		} else if (index == 0) { // New head
			createdNode.next = head;
			head = createdNode;
			tail.next = head;
		} else if (index == size) { // New tail
			createdNode.next = head;
			tail.next = createdNode;
			tail = createdNode;
		} else { // New in-betweener
			Node<E> beforeDesired = getNode(index - 1);

			createdNode.next = beforeDesired.next;
			beforeDesired.next = createdNode;
		}

		size++;
	}

	public E remove(int index) {
		if (index < 0 || index >= size)
			throw new IndexOutOfBoundsException("Attempted to remove index " + index + " when size is " + size);

		E removedData;

		if (size == 1) { // Remove only node
			removedData = head.data;
			head = tail = null;
		} else if (index == 0) { // Remove head
			removedData = head.data;
			head = head.next;
			tail.next = head;
		} else {
			Node<E> beforeDeleted = getNode(index - 1);

			if (index == size - 1) { // Remove tail
				removedData = tail.data;
				tail = beforeDeleted;
				tail.next = head;
			} else { // Remove something in-between
				removedData = beforeDeleted.next.data;
				beforeDeleted.next = beforeDeleted.next.next;
			}
		}

		size--;
		return removedData;
	}

	public String toString() {
		Node<E> current = head;
		StringBuilder result = new StringBuilder();

		if (size == 0)
			return "";
		else {
			do {
				result.append(current.data);

				if ((current = current.next) != head)
					result.append(" --> ");
			} while (current != head);
		}

		return result.toString();
	}

	public Iterator<E> iterator() { // Given from template
		return new ListIterator<>();
	}

	@SuppressWarnings("all")
	private class ListIterator<E> implements Iterator<E> { // Given from template
		Node<E> nextItem;
		Node<E> prev;
		int index;

		public ListIterator() {
			nextItem = (Node<E>) head;
			index = 0;
		}

		public boolean hasNext() {
			return size != 0;
		}

		public E next() {
			prev = nextItem;
			nextItem = nextItem.next;
			index = (index + 1) % size;
			return prev.data;
		}
	}
}