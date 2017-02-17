/**
 * 
 */
package com.source.stored.procedures.postgresql;

import java.util.List;

/**##########################################################
 * @author ANICET ERIC KOUAME
 * @Date 17 févr. 2017 08:55:35
 * @Description: 
 * @BookI
 *#################################################################*/
public interface BookI {

	public Book addBook(Book b);
	public  List<Book> BookList();
}
