/**
 * 
 */
package com.source.stored.procedures.postgresql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;





/**##########################################################
 * @author ANICET ERIC KOUAME
 * @Date 17 févr. 2017 08:55:18
 * @Description: 
 * @BookDao
 *#################################################################*/
public class BookDao implements BookI {

	
	@Override
	public Book addBook(Book b) {
		
		String query = "{call sp_book_add(?, ?, ?)}";		
        Connection con = null;
		        
		        try{
		        	con = Manager.getConnection();
		        	CallableStatement c= con.prepareCall(query); 
		        	
		        
		        	c.setString(1, b.getTitle());
		            c.setString(2, b.getAuthor());
		            c.setDouble(3, b.getPrice());
		           
		            c.executeUpdate();      
		            
		        }catch(SQLException e){
			            e.printStackTrace();  
			            
			          
			        }finally{
			            try {
			                con.close();
			            } catch (SQLException e) {
			                e.printStackTrace();
			            }
			        }     
		       
		      
	return  b  ;
	}
	
	
	@Override
	public  List<Book> BookList(){
		// TODO Auto-generated method stub
		String query = "{ ? = call sp_show_book() }";
		 List<Book> bist = new ArrayList<Book>();
	        Connection con = null;
	        ResultSet rs = null;	
	    	CallableStatement proc =null;
        try{
        		con = Manager.getConnection();
        		con.setAutoCommit(false);
        		
        		  proc = con.prepareCall(query);
        	      proc.registerOutParameter(1,Types.OTHER);
        	      proc.execute();
        	       rs = (ResultSet) proc.getObject(1);
        	       
        	       
	           while(rs.next()){             	 
	        	   Book b = new Book();            	
	            	 b.setId((rs.getObject("id")!=null)?Integer.parseInt(rs.getObject("id").toString()):0);	            	
	            	 b.setTitle((rs.getObject("title")!=null)?rs.getObject("title").toString():null);
	            	b.setAuthor((rs.getObject("author")!=null)?rs.getObject("author").toString():null);
	            	b.setPrice((rs.getObject("price")!=null)?Double.parseDouble(rs.getObject("price").toString()):0);
	            
	            		            	
	            	bist.add(b);
	                 
	             }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            try {
                rs.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return bist;
	}
	
}
