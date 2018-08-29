package com.hyk.app.ipclearndemo.use_aidl;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.hyk.app.ipclearndemo.R;

import java.util.List;

public class BookManagerClientActivity extends Activity {
    protected static final String TAG = "BookManagerClientActivity";
    private static final int MESSAGE_NEW_BOOK_ARRIVED = 1;

    private IBookManager mRemoteBookManager;

    private Handler mHandler = new Handler() {
        @SuppressLint("LongLogTag")
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_NEW_BOOK_ARRIVED:
                    Log.d(TAG, "receiver new book: " + msg.obj);
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    };

    private ServiceConnection mConnection = new ServiceConnection() {
        @SuppressLint("LongLogTag")
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            IBookManager bookManager = IBookManager.Stub.asInterface(service);
            try {
                mRemoteBookManager = bookManager;
                List<Book> list = bookManager.getBookList();
                Log.d(TAG, "query book list :" + list.toString());
                Book book = new Book(3, "Android开发艺术探索");
                bookManager.addBook(book);
                List<Book> newList = bookManager.getBookList();
                Log.d(TAG, "query book list :" + newList.toString());
                bookManager.registerListener(mOnNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    private IOnNewBookArrivedListener mOnNewBookArrivedListener = new IOnNewBookArrivedListener.Stub() {
        @Override
        public void onNewBookArrived(Book book) throws RemoteException {
            //运行在Binder线程 无法访问UI
            mHandler.obtainMessage(MESSAGE_NEW_BOOK_ARRIVED, book).sendToTarget();
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_manager_client);
        Intent intent = new Intent(this, BookManagerService.class);
        bindService(intent, mConnection, BIND_AUTO_CREATE);
    }


    @SuppressLint("LongLogTag")
    @Override
    protected void onDestroy() {
        if (mRemoteBookManager != null &&
                mRemoteBookManager.asBinder().isBinderAlive()){
            try {
                Log.d(TAG, "unregister listener :"  + mOnNewBookArrivedListener);
                mRemoteBookManager.unregisterListener(mOnNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        unbindService(mConnection);
        super.onDestroy();
    }
}
