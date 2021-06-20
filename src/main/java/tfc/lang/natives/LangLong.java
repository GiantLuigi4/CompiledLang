package tfc.lang.natives;

import tfc.lang.LangClass;

public class LangLong extends LangClass {
	public LangLong() {
		super("ÿlong".getBytes());
	}
	
	public boolean isInstance(Object o) {
		return o instanceof Long;
	}
	
	@Override
	public Object add(Object self, Object other) {
		if (other instanceof Long) return (long) self + (long) other;
		if (other instanceof Short) return (long) self + (short) other;
		if (other instanceof Integer) return (long) self + (int) other;
		if (other instanceof Byte) return (long) self + (byte) other;
		if (other instanceof Float) return (long) self + (float) other;
		if (other instanceof Double) return (long) self + (double) other;
		return null;
	}
	
	@Override
	public Object subtract(Object self, Object other) {
		if (other instanceof Long) return (long) self - (long) other;
		if (other instanceof Short) return (long) self - (short) other;
		if (other instanceof Integer) return (long) self - (int) other;
		if (other instanceof Byte) return (long) self - (byte) other;
		if (other instanceof Float) return (long) self - (float) other;
		if (other instanceof Double) return (long) self - (double) other;
		return null;
	}
	
	@Override
	public Object multiply(Object self, Object other) {
		if (other instanceof Long) return (long) self * (long) other;
		if (other instanceof Short) return (long) self * (short) other;
		if (other instanceof Integer) return (long) self * (int) other;
		if (other instanceof Byte) return (long) self * (byte) other;
		if (other instanceof Float) return (long) self * (float) other;
		if (other instanceof Double) return (long) self * (double) other;
		return null;
	}
	
	@Override
	public Object divide(Object self, Object other) {
		if (other instanceof Long) return (long) self / (long) other;
		if (other instanceof Short) return (long) self / (short) other;
		if (other instanceof Integer) return (long) self / (int) other;
		if (other instanceof Byte) return (long) self / (byte) other;
		if (other instanceof Float) return (long) self / (float) other;
		if (other instanceof Double) return (long) self / (double) other;
		return null;
	}
	
	@Override
	public Object modulus(Object self, Object other) {
		if (other instanceof Long) return (long) self % (long) other;
		if (other instanceof Short) return (long) self % (short) other;
		if (other instanceof Integer) return (long) self % (int) other;
		if (other instanceof Byte) return (long) self % (byte) other;
		if (other instanceof Float) return (long) self % (float) other;
		if (other instanceof Double) return (long) self % (double) other;
		return null;
	}
	
	@Override
	public Object lessThan(Object self, Object other) {
		if (other instanceof Long) return (long) self < (long) other;
		if (other instanceof Short) return (long) self < (short) other;
		if (other instanceof Integer) return (long) self < (int) other;
		if (other instanceof Byte) return (long) self < (byte) other;
		if (other instanceof Float) return (long) self < (float) other;
		if (other instanceof Double) return (long) self < (double) other;
		return null;
	}
	
	@Override
	public Object greaterThan(Object self, Object other) {
		if (other instanceof Long) return (long) self > (long) other;
		if (other instanceof Short) return (long) self > (short) other;
		if (other instanceof Integer) return (long) self > (int) other;
		if (other instanceof Byte) return (long) self > (byte) other;
		if (other instanceof Float) return (long) self > (float) other;
		if (other instanceof Double) return (long) self > (double) other;
		return null;
	}
	
	@Override
	public Object equalTo(Object self, Object other) {
		if (other instanceof Long) return (long) self == (long) other;
		if (other instanceof Short) return (long) self == (short) other;
		if (other instanceof Integer) return (long) self == (int) other;
		if (other instanceof Byte) return (long) self == (byte) other;
		if (other instanceof Float) return (long) self == (float) other;
		if (other instanceof Double) return (long) self == (double) other;
		return null;
	}
	
	@Override
	public Object notEqualTo(Object self, Object other) {
		if (other instanceof Long) return (long) self != (long) other;
		if (other instanceof Short) return (long) self != (short) other;
		if (other instanceof Integer) return (long) self != (int) other;
		if (other instanceof Byte) return (long) self != (byte) other;
		if (other instanceof Float) return (long) self != (float) other;
		if (other instanceof Double) return (long) self != (double) other;
		return null;
	}
	
	@Override
	public Object greaterThanOrEqual(Object self, Object other) {
		if (other instanceof Long) return (long) self >= (long) other;
		if (other instanceof Short) return (long) self >= (short) other;
		if (other instanceof Integer) return (long) self >= (int) other;
		if (other instanceof Byte) return (long) self >= (byte) other;
		if (other instanceof Float) return (long) self >= (float) other;
		if (other instanceof Double) return (long) self >= (double) other;
		return null;
	}
	
	@Override
	public Object lessThanOrEqual(Object self, Object other) {
		if (other instanceof Long) return (long) self <= (long) other;
		if (other instanceof Short) return (long) self <= (short) other;
		if (other instanceof Integer) return (long) self <= (int) other;
		if (other instanceof Byte) return (long) self <= (byte) other;
		if (other instanceof Float) return (long) self <= (float) other;
		if (other instanceof Double) return (long) self <= (double) other;
		return null;
	}
}