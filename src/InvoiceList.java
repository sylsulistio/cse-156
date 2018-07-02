import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InvoiceList<T> implements Iterable<T>{
	static final Logger log = LoggerFactory.getLogger(DatabaseReader.class);
	private int size;
	private InvoiceNode start;
	private InvoiceNode end;
	private T t;
	
	public InvoiceList() {
		start = null;
		end = null;
		size = 0;
	}

	public InvoiceList(InvoiceList<Invoice> list) {
		Iterator<Invoice> iterator = list.iterator();
		InvoiceNode start = new InvoiceNode((T) iterator.next(), null);
		this.start = start;
		size++;
		while (iterator.hasNext()) {
			this.addToEnd((T) iterator.next());
			size++;
		}
		this.end = findNode(this.size);
	}

	public boolean isEmpty() {
		return start == null;
	}
	
	public int getSize() {
		return size;
	}

	// Add to start
	public void addToStart(T inv) {
		InvoiceNode insert = new InvoiceNode(inv, null);
		size++;
		if (start == null) {
			start = insert;
			end = start;
		}
		/**
		 * Newly added node becomes the start node and sets 
		 * the 'next' pointer to the previous start node
		 */
		else {
			insert.setNext(start);
			start = insert;
		}
		
		log.info("Item added: " + ((Invoice) insert.getInvoice()).getInvoiceCode());
	}
	
	// Add to end
	public void addToEnd(T inv) {
		InvoiceNode insert = new InvoiceNode(inv, null);
		size++;
		if (start == null) {
			start = insert;
			end = start;
		}
		/**
		 * Newly added node becomes the end node and sets 
		 * the 'next' pointer to the previous start node
		 */
		else {
			end.setNext(insert);
			end = insert;
		}
		
		log.info("Item added: " + ((Invoice) insert.getInvoice()).getInvoiceCode());
	}
	
	// Add to index
	public boolean addToIndex(T inv, int index) {
		InvoiceNode insert = new InvoiceNode(inv, null);
		if (index < 1 || index > size) {
				log.error("Error at addInvoiceNode: Index out of bounds");
				return false;
			}
			
			InvoiceNode previousNode = start;
			for (int i  = 1; i < index-1; i++) {
				if (previousNode.getNext() == null) {
					log.error("Error at addInvoiceNode: Only one item in list!");
					return false;
				}
				// if next node is not null
				previousNode = previousNode.getNext();
			}
			
			InvoiceNode nextNode = previousNode.getNext();
			previousNode.setNext(insert);
			insert.setNext(nextNode);
			
			log.info("Item added: " + ((Invoice) insert.getInvoice()).getInvoiceCode());
			return true;
	}
	
	// Find by invoice code
	public T find(String invCode) {
		ListIterator iterator = new ListIterator();
		while (iterator.hasNext()) {
			
			// Gets current invoice as an invoice
			T currentInv = iterator.current.getInvoice();
			
			// Compares invoice code from arguments to current invoice code
			if (((Invoice) currentInv).getInvoiceCode().equalsIgnoreCase(invCode)) {
				return currentInv;
			}
			currentInv = iterator.next();
		}
		log.error("Invoice not found!");
		return null;
	}

	// Find by index
	public T get(int index) {
		InvoiceNode search = start;
		
		for (int i = 0; i < index; i++) {
			if (search.getNext() == null) {
				log.error("Invoice not found!");
				return null;
			}
			
			search = search.getNext();
		}
		
		return search.getInvoice();
	}
	
	// Find node by index
	public InvoiceNode findNode(int index) {
		InvoiceNode search = start;
		
		for (int i = 0; i < index; i++) {
			if (search.getNext() == null) {
				log.error("Invoice not found!");
				return null;
			}
			
			search = search.getNext();
		}
		
		return search;
	}
	
	// Delete from beginning
	public void removeFromStart() {
		start = start.getNext();
		size--;
		return;
	}
	
	// Delete from end
	public void removeFromEnd() {
		InvoiceNode currentNode = findNode(size-1);
		currentNode.setNext(null);
		end = currentNode;
		size--;
		return;
	}
	// Delete by index
	public boolean removeAtIndex(int index) {
		InvoiceNode previousNode = start;
		
		if (index < 1 || index > size) {
			log.error("RemoveAtIndex error: Index out of bounds!");
			return false;
		}
		
		for (int i = 0; i < index-1; i++) {
			if (previousNode.getNext() == null) {
				log.error("RemoveAtIndex error: No node to delete!");
				return false;
			}
			previousNode = previousNode.getNext();
		}
		
		InvoiceNode removedNode = previousNode.getNext();
		InvoiceNode nextNode = removedNode.getNext();
		previousNode.setNext(nextNode);
		log.info("Node at index " + index + " removed successfully");
		size--;
		return true;
	}
	
	// Set by index
	public void alterAtIndex(int index, T data) {
		InvoiceNode changedNode = findNode(index);
		if (changedNode == null) {
			log.error("Node not found!");
			return;
		}
		changedNode.setInvoice(data);
		log.info("Data at index changed");
		return;
	}
	
	// Iterator class
	@Override
	public Iterator<T> iterator() {
		return new ListIterator();
	}
	
	private class ListIterator implements Iterator<T> {
		InvoiceNode current = start;
		
		@Override
		public boolean hasNext() {
			return current.getNext() != null;
		}

		@Override
		public T next() {
			if (!hasNext()) {
				log.error("No elements left in list!");
				return null;
			}
			t = current.getInvoice();
			return t;
		}
	}
	
	// InvoiceNode class
	public class InvoiceNode {
		private T invoice;
		private InvoiceNode nextNode;
		
		public InvoiceNode() {
			this.nextNode = null;
			this.invoice = null;
		}
		
		public InvoiceNode(T invoice, InvoiceNode nextNode) {
			this.invoice = invoice;
			this.nextNode = nextNode;
		}
		
		public void setNext(InvoiceNode nextNode) {
			this.nextNode = nextNode;
		}
		
		public void setInvoice(T invoice) {
			this.invoice = invoice;
		}
		
		public InvoiceNode getNext() {
			return nextNode;
		}
		
		public T getInvoice() {
			return invoice;
		}
	}

	public void sort() {
		SortingAlgorithm.quickSort((InvoiceList<Invoice>)this);
	}
}