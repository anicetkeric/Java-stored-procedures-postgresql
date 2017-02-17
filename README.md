# Java-stored-procedures-postgresql
This example an example how to use postgresql Stored Procedure in jdbc.


# Overview

A stored procedure and user-defined function (UDF) is a set of SQL and procedural statements (declarations, assignments, loops, flow-of-control etc.) that stored on the database server and can be invoked using the SQL interface. 
stored procedures run in the DBMS itself, they can help to reduce latency in applications. Rather than executing four or five SQL statements in your Java code, you just execute one stored procedure that does the operations for you on the server side by Reducing the number of network trips. 


In PostgreSQL, both stored procedures and user-defined functions are created with CREATE FUNCTION statement. There are differences between the notion of stored procedures and functions in database systems.

# PostgreSQL
connect to a running instance of a PostgreSQL server. The sample was tested using PostgreSQL v9.5. 

### Create database and tables

`-- Database: book_db` 
```sql
 DROP DATABASE book_db;

CREATE DATABASE book_db
  WITH OWNER = postgres
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'French_France.1252'
       LC_CTYPE = 'French_France.1252'
       CONNECTION LIMIT = -1;    
   ```
```sql   
   CREATE TABLE public.book
(
  "id" serial,
  "title" character varying(35), 
  "author" character varying(35),
  "price" double precision,
  CONSTRAINT book_pkey PRIMARY KEY ("id")
)
WITH (
  OIDS=FALSE
);
```
### Stored Procedures
###### No Value Returned
If a stored procedure does not return any value, you can specify void as the return type: 
```sql 
CREATE OR REPLACE FUNCTION public.sp_book_add(
    title character varying,
    author character varying,
    price double precision)
  RETURNS void AS
$BODY$Begin

    INSERT INTO public.book(title, author, price) VALUES (title, author, price);

End;

$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.sp_book_add(character varying, character varying, double precision)
  OWNER TO postgres;
  ```
     
###### Return a Single Result Set - Return a Cursor
To return a result set from a PostgreSQL procedure, you have to specify refcursor return type, open and return a cursor: 
```sql
CREATE OR REPLACE FUNCTION sp_show_book() RETURNS refcursor AS $$
    DECLARE
      ref refcursor;
    BEGIN
      OPEN ref FOR SELECT id, title, author, price FROM public.book;
      RETURN ref;
    END;
    $$ LANGUAGE plpgsql;
```

### JAVA
Create new project java in eclipse

[Convert Java Project into a Maven Java Project](http://crunchify.com/how-to-convert-existing-java-project-to-maven-in-eclipse/)

```xml
    <!-- https://mvnrepository.com/artifact/org.postgresql/postgresql -->
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <version>9.4.1212.jre6</version>
</dependency>
```
Call PostgreSQL stored procedures with JDBC
```java
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
``` 
 ```java           

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
	``` 
