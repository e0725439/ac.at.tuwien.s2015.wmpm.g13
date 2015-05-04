package ac.at.tuwien.s2015.wmpm.g13.model;

import java.util.List;

public class Inventory {
	
	private int size;
	private List<Product> products;
	
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public List<Product> getProducts() {
		return products;
	}
	public void setProducts(List<Product> products) {
		this.products = products;
	}	
}
