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
