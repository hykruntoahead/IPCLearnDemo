// IOnNewBookArrivedListener.aidl
package com.hyk.app.ipclearndemo.use_aidl;

// Declare any non-default types here with import statements
import com.hyk.app.ipclearndemo.use_aidl.Book;

interface IOnNewBookArrivedListener {
    void onNewBookArrived(in Book book);
}
