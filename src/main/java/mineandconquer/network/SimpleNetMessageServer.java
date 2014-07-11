package mineandconquer.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class SimpleNetMessageServer implements IMessage {

	public ByteBuf data;

	public SimpleNetMessageServer() {
		data = Unpooled.buffer(256);
	}

	public SimpleNetMessageServer(int index,int x, int y, int z) {
		data = Unpooled.buffer(256);
		data.setInt(0, index);
		data.setInt(4, x);
		data.setInt(8, y);
		data.setInt(12, z);
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		// TODO Auto-generated method stub
		data = Unpooled.buffer(256);
		int i = 0;
		while (i < 256 && i < buf.capacity()) {
			data.setByte(i, buf.getByte(i));
			i++;
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		// TODO Auto-generated method stub
		int i = 0;
		while (i < 256 && i < buf.capacity()) {
			buf.writeByte(data.getByte(i));
			i++;
		}
	}

	
	public int setInt(int value) {
		data.setInt(16, value);
		return 20;
	}
	
	public int setInt(int index, int value) {
		if (index < 16) {
			return -1;
		}
		data.setInt(index, value);
		return index+4;
	}
	
	public int setIntArray(int[] array) {
		this.setInt(16, array.length);
		int lastPointer = 20;
		for (int i : array) {
			lastPointer = this.setInt(lastPointer, i);
		}
		return lastPointer;
	}
	
	public int getInt() {
		return data.getInt(16);
	}
	
	public int getInt(int index){
		return data.getInt(index);
	}
	
	public int[] getIntArray(){
		int length = this.getInt(16);
		int[] ret = new int[length];
		for (int i = 0 ; i < length; i++) {
			ret[i] = this.getInt(20+4*i);
		}
		return ret;
	}
	
	public int setBoolean(boolean value) {
		data.setBoolean(16, value);
		return 17;
	}
	
	public int setBoolean(int index, boolean value) {
		if (index < 16) {
			return -1;
		}
		data.setBoolean(index, value);
		return index+1;
	}
	
	public int setBooleanArray(boolean[] array) {
		this.setInt(16, array.length);
		int lastPointer = 20;
		for (boolean i : array) {
			lastPointer = this.setBoolean(lastPointer, i);
		}
		return lastPointer;
	}
	
	public boolean getBoolean() {
		// TODO Auto-generated method stub
		return data.getBoolean(16);
	}
	public boolean getBoolean(int index){
		return data.getBoolean(index);
	}
	
	public boolean[] getBooleanArray(){
		int length = this.getInt(16);
		boolean[] ret = new boolean[length];
		for (int i = 0 ; i < length; i++) {
			ret[i] = this.getBoolean(20+i);
		}
		return ret;
	}
	
	public int setString(String value) {
		char[] temp = ((String) value).toCharArray();
		data.setInt(16, (Integer) temp.length);
		for (int i = 0; i < temp.length; i++) {
			data.setChar(20 + 2 * i, temp[i]);
		}
		return 20+2*temp.length;
	}
	
	public int setString(int index, String value) {
		
		if (index < 16) {
			return -1;
		} 
		
		char[] temp = ((String) value).toCharArray();
		data.setInt(index, (Integer) temp.length);
		for (int i = 0; i < temp.length; i++) {
			data.setChar(index+4 + 2 * i, temp[i]);
		}
		return index+4+2*temp.length;
	}
	
	public int setStringArray(String[] array) {
		this.setInt(16, array.length);
		int lastPointer = 20;
		for (String i : array) {
			lastPointer = this.setString(lastPointer, i);
		}
		return lastPointer;
	}
	
	public String getString() {
		int length = data.getInt(16);
		char[] ret = new char[length];
		for (int i = 0; i < length; i++) {
			ret[i] = data.getChar(20 + 2 * i);
		}
		return new String(ret);
	}
	
	public String getString(int index){
		int length = data.getInt(index);
		char[] ret = new char[length];
		for (int i = 0; i < length; i++) {
			ret[i] = data.getChar(index+4 + 2 * i);
		}
		return new String(ret);
	}
	
	public String[] getStringArray(){
		int lengthString = this.getInt(16);
		String[] retString = new String[lengthString];
		
		int index = 20;
		
		for (int i = 0 ; i < lengthString; i++) {
			
			int length = data.getInt(index);
			char[] ret = new char[length];
			for (int j = 0; j < length; j++) {
				ret[j] = data.getChar(index+4 + 2 * j);
			}
			
			retString[i] = new String(ret);
			index = index+4 + 2 * length;
		}
		return retString;
	}
	
	public int getIndex() {
		return data.getInt(0);
	}
	public int getX() {
		return data.getInt(4);
	}
	public int getY() {
		return data.getInt(8);
	}
	public int getZ() {
		return data.getInt(12);
	}

}
