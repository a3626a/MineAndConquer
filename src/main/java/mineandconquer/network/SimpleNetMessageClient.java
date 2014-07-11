package mineandconquer.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class SimpleNetMessageClient implements IMessage{

	public ByteBuf data;

	public SimpleNetMessageClient() {
		data = Unpooled.buffer();
	}
	public SimpleNetMessageClient(int index,int x, int y, int z) {
		data = Unpooled.buffer();
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
	
	public int getInt() {
		return data.getInt(16);
	}
	
	public int getInt(int index) throws Exception {
		if (index < 16) {
			throw new Exception("Can't get integer from index under 16");
		}
		return data.getInt(index);
	}
	
	public void setBoolean(boolean value) {
		data.setBoolean(16, value);
	}
	
	public boolean getBoolean() {
		// TODO Auto-generated method stub
		return data.getBoolean(16);
	}
	
	public void setString(String value) {
		char[] temp = ((String) value).toCharArray();
		data.setInt(16, (Integer) temp.length);
		for (int i = 0; i < temp.length; i++) {
			data.setChar(20 + 2 * i, temp[i]);
		}
	}
	
	public String getString() {
		int length = data.getInt(16);
		char[] ret = new char[length];
		for (int i = 0; i < length; i++) {
			ret[i] = data.getChar(20 + 2 * i);
		}
		return new String(ret);
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
