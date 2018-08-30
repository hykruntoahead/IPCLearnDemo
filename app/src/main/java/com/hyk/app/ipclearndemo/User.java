package com.hyk.app.ipclearndemo;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class User implements Parcelable, Serializable {
    public int age;
    public String name;
    public boolean male;


    public User() {
    }

    public User(int age, String name, boolean male) {
        this.age = age;
        this.name = name;
        this.male = male;
    }


    protected User(Parcel in) {
        age = in.readInt();
        name = in.readString();
        male = in.readByte() != 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(age);
        dest.writeString(name);
        dest.writeByte((byte) (male ? 1 : 0));
    }


    @Override
    public String toString() {
        return "User{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", male=" + male +
                '}';
    }
}
