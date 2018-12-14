package com.netty.demo3;

/**
 * Created by Andy on 2016/10/9.
 */
public enum MessageType {
    TYPE_AUTH((byte)0),TYPE_LOGOUT((byte)1),TYPE_TEXT((byte)2),TYPE_EMPTY((byte)3);
    private byte value;
    MessageType(byte value){
        this.value = value;
    }
    public byte value(){
        return this.value;
    }
}