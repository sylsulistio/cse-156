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
	
	public boolean isEmpty() {
		return start == null;
	}
	
	public int getSize() {
		return size;
	}

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
	
	public void addToEnd(T inv) {
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
			end.setNext(insert);
			end = insert;
		}
		
		log.info("Item added: " + ((Invoice) insert.getInvoice()).getInvoiceCode());
	}
	
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
		InvoiceNode search = this.start;
		ListIterator iterator = new ListIterator();
		while (iterator.hasNext()) {
			// Gets current invoice as an invoice
			Invoice currentInv = (Invoice) iterator.current.getInvoice();
			// Compares invoice code from arguments to current invoice code
			if (currentInv.getInvoiceCode().equals(invCode)) {
				return search.getInvoice();
			}
		}
		log.error("Invoice not found!");
		return null;
	}

	// Find by index
	public T find(int index) {
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
}