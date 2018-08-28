// IBookManager.aidl
package com.hyk.app.ipclearndemo.use_aidl;

// Declare any non-default types here with import statements
import com.hyk.app.ipclearndemo.use_aidl.Book;

interface IBookManager {
   List<Book> getBookList();
   void addBook(in Book book);
}
