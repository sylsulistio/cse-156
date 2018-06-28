import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InvoiceList {
	static final Logger log = LoggerFactory.getLogger(DatabaseReader.class);
	private int size;
	private InvoiceNode start;
	private InvoiceNode end;
	
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
	
	public void add(Invoice inv, int index) {
		InvoiceNode insert = new InvoiceNode(inv, null);
		size++;
		if (index < 1) {
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
		}
		else if (index < size-1) {
			boolean itemAdded = false;
			
			if (index < 1 || index > size) {
				log.error("Error at addInvoiceNode: Index out of bounds");
			}
			
			InvoiceNode previousNode = start;
			
			
		}
		
	}
}
