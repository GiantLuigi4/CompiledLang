package tfc.lang.natives;

import tfc.lang.LangClass;

public class LangByte extends LangClass {
	public LangByte() {
		super("ÿbyte".getBytes());
	}
	
	public boolean isInstance(Object o) {
		return o instanceof Byte;
	}
	
	@Override
	public Object add(Object self, Object other) {
		if (other instanceof Long) return (byte) self + (long) other;
		if (other instanceof Short) return (byte) self + (short) other;
		if (other instanceof Integer) return (byte) self + (int) other;
		if (other instanceof Byte) return (byte) self + (byte) other;
		if (other instanceof Float) return (byte) self + (float) other;
		if (other instanceof Double) return (byte) self + (double) other;
		return null;
	}
	
	@Override
	public Object subtract(Object self, Object other) {
		if (other instanceof Long) return (byte) self - (long) other;
		if (other instanceof Short) return (byte) self - (short) other;
		if (other instanceof Integer) return (byte) self - (int) other;
		if (other instanceof Byte) return (byte) self - (byte) other;
		if (other instanceof Float) return (byte) self - (float) other;
		if (other instanceof Double) return (byte) self - (double) other;
		return null;
	}
	
	@Override
	public Object multiply(Object self, Object other) {
		if (other instanceof Long) return (byte) self * (long) other;
		if (other instanceof Short) return (byte) self * (short) other;
		if (other instanceof Integer) return (byte) self * (int) other;
		if (other instanceof Byte) return (byte) self * (byte) other;
		if (other instanceof Float) return (byte) self * (float) other;
		if (other instanceof Double) return (byte) self * (double) other;
		return null;
	}
	
	@Override
	public Object divide(Object self, Object other) {
		if (other instanceof Long) return (byte) self / (long) other;
		if (other instanceof Short) return (byte) self / (short) other;
		if (other instanceof Integer) return (byte) self / (int) other;
		if (other instanceof Byte) return (byte) self / (byte) other;
		if (other instanceof Float) return (byte) self / (float) other;
		if (other instanceof Double) return (byte) self / (double) other;
		return null;
	}
	
	@Override
	public Object modulus(Object self, Object other) {
		if (other instanceof Long) return (byte) self % (long) other;
		if (other instanceof Short) return (byte) self % (short) other;
		if (other instanceof Integer) return (byte) self % (int) other;
		if (other instanceof Byte) return (byte) self % (byte) other;
		if (other instanceof Float) return (byte) self % (float) other;
		if (other instanceof Double) return (byte) self % (double) other;
		return null;
	}
	
	@Override
	public Object lessThan(Object self, Object other) {
		if (other instanceof Long) return (byte) self < (long) other;
		if (other instanceof Short) return (byte) self < (short) other;
		if (other instanceof Integer) return (byte) self < (int) other;
		if (other instanceof Byte) return (byte) self < (byte) other;
		if (other instanceof Float) return (byte) self < (float) other;
		if (other instanceof Double) return (byte) self < (double) other;
		return null;
	}
	
	@Override
	public Object greaterThan(Object self, Object other) {
		if (other instanceof Long) return (byte) self > (long) other;
		if (other instanceof Short) return (byte) self > (short) other;
		if (other instanceof Integer) return (byte) self > (int) other;
		if (other instanceof Byte) return (byte) self > (byte) other;
		if (other instanceof Float) return (byte) self > (float) other;
		if (other instanceof Double) return (byte) self > (double) other;
		return null;
	}
	
	@Override
	public Object equalTo(Object self, Object other) {
		if (other instanceof Long) return (byte) self == (long) other;
		if (other instanceof Short) return (byte) self == (short) other;
		if (other instanceof Integer) return (byte) self == (int) other;
		if (other instanceof Byte) return (byte) self == (byte) other;
		if (other instanceof Float) return (byte) self == (float) other;
		if (other instanceof Double) return (byte) self == (double) other;
		return null;
	}
	
	@Override
	public Object notEqualTo(Object self, Object other) {
		if (other instanceof Long) return (byte) self != (long) other;
		if (other instanceof Short) return (byte) self != (short) other;
		if (other instanceof Integer) return (byte) self != (int) other;
		if (other instanceof Byte) return (byte) self != (byte) other;
		if (other instanceof Float) return (byte) self != (float) other;
		if (other instanceof Double) return (byte) self != (double) other;
		return null;
	}
	
	@Override
	public Object greaterThanOrEqual(Object self, Object other) {
		if (other instanceof Long) return (byte) self >= (long) other;
		if (other instanceof Short) return (byte) self >= (short) other;
		if (other instanceof Integer) return (byte) self >= (int) other;
		if (other instanceof Byte) return (byte) self >= (byte) other;
		if (other instanceof Float) return (byte) self >= (float) other;
		if (other instanceof Double) return (byte) self >= (double) other;
		return null;
	}
	
	@Override
	public Object lessThanOrEqual(Object self, Object other) {
		if (other instanceof Long) return (byte) self <= (long) other;
		if (other instanceof Short) return (byte) self <= (short) other;
		if (other instanceof Integer) return (byte) self <= (int) other;
		if (other instanceof Byte) return (byte) self <= (byte) other;
		if (other instanceof Float) return (byte) self <= (float) other;
		if (other instanceof Double) return (byte) self <= (double) other;
		return null;
	}
}