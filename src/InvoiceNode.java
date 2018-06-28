
public class InvoiceNode {
	private Invoice invoice;
	private InvoiceNode nextNode;
	
	public InvoiceNode() {
		this.nextNode = null;
		this.invoice = null;
	}
	
	public InvoiceNode(Invoice invoice, InvoiceNode nextNode) {
		this.invoice = invoice;
		this.nextNode = nextNode;
	}
	
	public void setNext(InvoiceNode nextNode) {
		this.nextNode = nextNode;
	}
	
	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}
	
	public InvoiceNode getNext() {
		return nextNode;
	}
	
	public Invoice getInvoice() {
		return invoice;
	}
}
