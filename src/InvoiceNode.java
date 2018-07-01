public class InvoiceNode<T> {
		private T invoice;
		private InvoiceNode<T> nextNode;
		
		public InvoiceNode() {
			this.nextNode = null;
			this.invoice = null;
		}
		
		public InvoiceNode(T invoice, InvoiceNode<T> nextNode) {
			this.invoice = invoice;
			this.nextNode = nextNode;
		}
		
		public void setNext(InvoiceNode<T> nextNode) {
			this.nextNode = nextNode;
		}
		
		public void setInvoice(T invoice) {
			this.invoice = invoice;
		}
		
		public InvoiceNode<T> getNext() {
			return nextNode;
		}
		
		public T getInvoice() {
			return invoice;
		}
	}