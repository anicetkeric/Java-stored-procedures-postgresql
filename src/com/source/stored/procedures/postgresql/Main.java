/**
 * 
 */
package com.source.stored.procedures.postgresql;

import java.util.Iterator;
import java.util.List;

/**##########################################################
 * @author ANICET ERIC KOUAME
 * @Date 17 févr. 2017 09:55:58
 * @Description: 
 * @Main
 *#################################################################*/
public class Main {

	
	private static BookI metierBook;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		metierBook=new BookDao();
		
		Book b=new Book();
		b.setTitle("Amor");
		b.setAuthor("Anthony");
		b.setPrice(5000);
		
		System.out.println(metierBook.addBook(b).getId());
		
		
		List<Book> lb=metierBook.BookList();
		for (Book book : lb) {
			System.out.println(book.getId()+" | "+book.getTitle()+" | "+book.getAuthor()+" | "+book.getPrice());
		}
		
	}

}
