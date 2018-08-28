package com.hyk.app.ipclearndemo.use_aidl.manager;

import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;

import com.hyk.app.ipclearndemo.use_aidl.Book;

import java.util.List;

public interface IBookManager  extends IInterface {

    static final String DESCRIPTOR = "com.hyk.app.ipclearndemo.use_aidl.IBookManager";

    static final int TRANSACTION_getBookList = IBinder.FIRST_CALL_TRANSACTION + 0;


    static final int TRANSACTION_addBook= IBinder.FIRST_CALL_TRANSACTION + 1;

    public List<Book> getBookList()  throws RemoteException;

    public void addBook(Book book)  throws RemoteException;
}

