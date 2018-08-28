package com.hyk.app.ipclearndemo.use_aidl.manager;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.hyk.app.ipclearndemo.use_aidl.Book;

import java.util.List;

public class BookManagerImpl extends Binder implements IBookManager {

    public BookManagerImpl() {
        this.attachInterface(this, DESCRIPTOR);
    }


    /*
       将IBinder对象转换为IBookManager 接口，如果有需要的话，生成代理
     */

    public static IBookManager asInterface(IBinder obj) {
        if (obj == null) {
            return null;
        }

        IInterface iInterface = obj.queryLocalInterface(DESCRIPTOR);
        if ((iInterface != null) && (iInterface instanceof IBookManager)) {
            return (IBookManager) iInterface;
        }
        return new BookManagerImpl.Proxy(obj);

    }

    @Override
    public IBinder asBinder() {
        return this;
    }

    @Override
    protected boolean onTransact(int code, @NonNull Parcel data, @Nullable Parcel reply, int flags) throws RemoteException {
        switch (code) {
            case INTERFACE_TRANSACTION: {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            case TRANSACTION_getBookList: {
                data.enforceInterface(DESCRIPTOR);
                List<Book> result = this.getBookList();
                reply.writeNoException();
                reply.writeTypedList(result);
                return true;
            }

            case TRANSACTION_addBook: {
                data.enforceInterface(DESCRIPTOR);
                Book arg0;
                if (0 != data.readInt()) {
                    arg0 = Book.CREATOR.createFromParcel(data);
                } else {
                    arg0 = null;
                }
                this.addBook(arg0);
                reply.writeNoException();
                return true;
            }
        }

        return super.onTransact(code, data, reply, flags);
    }


    private static class Proxy implements IBookManager {
        private IBinder mRemote;

        Proxy(IBinder remote) {
            mRemote = remote;
        }

        @Override
        public List<Book> getBookList() throws RemoteException {
            Parcel data = Parcel.obtain();
            Parcel replay = Parcel.obtain();
            List<Book> result;

            try {
                data.writeInterfaceToken(DESCRIPTOR);
                mRemote.transact(TRANSACTION_getBookList,data,replay,0);
                replay.readException();
                result = replay.createTypedArrayList(Book.CREATOR);
            }finally {
                replay.recycle();
                data.recycle();
            }
            return result;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            Parcel data = Parcel.obtain();
            Parcel replay = Parcel.obtain();

            try {
                data.writeInterfaceToken(DESCRIPTOR);
                if (book != null){
                    data.writeInt(1);
                    book.writeToParcel(data,0);
                }else {
                    data.writeInt(0);
                }
                mRemote.transact(TRANSACTION_addBook,data,replay,0);
                replay.readException();
            }finally {
                replay.recycle();
                data.recycle();
            }
        }

        @Override
        public IBinder asBinder() {
            return mRemote;
        }

        private String getInterfaceDescriptor(){
            return DESCRIPTOR;
        }

    }


    @Override
    public List<Book> getBookList() throws RemoteException {
        // TODO: 2018/8/28 待实现
        return null;
    }

    @Override
    public void addBook(Book book) throws RemoteException {
        // TODO: 2018/8/28 待实现
    }


}
