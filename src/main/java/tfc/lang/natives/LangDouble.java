package tfc.lang.natives;

import tfc.lang.LangClass;

public class LangDouble extends LangClass {
	public LangDouble() {
		super("ÿdouble".getBytes());
	}
	
	public boolean isInstance(Object o) {
		return o instanceof Double;
	}
	
	@Override
	public Object add(Object self, Object other) {
		if (other instanceof Long) return (double) self + (long) other;
		if (other instanceof Short) return (double) self + (short) other;
		if (other instanceof Integer) return (double) self + (int) other;
		if (other instanceof Byte) return (double) self + (byte) other;
		if (other instanceof Float) return (double) self + (float) other;
		if (other instanceof Double) return (double) self + (double) other;
		return null;
	}
	
	@Override
	public Object subtract(Object self, Object other) {
		if (other instanceof Long) return (double) self - (long) other;
		if (other instanceof Short) return (double) self - (short) other;
		if (other instanceof Integer) return (double) self - (int) other;
		if (other instanceof Byte) return (double) self - (byte) other;
		if (other instanceof Float) return (double) self - (float) other;
		if (other instanceof Double) return (double) self - (double) other;
		return null;
	}
	
	@Override
	public Object multiply(Object self, Object other) {
		if (other instanceof Long) return (double) self * (long) other;
		if (other instanceof Short) return (double) self * (short) other;
		if (other instanceof Integer) return (double) self * (int) other;
		if (other instanceof Byte) return (double) self * (byte) other;
		if (other instanceof Float) return (double) self * (float) other;
		if (other instanceof Double) return (double) self * (double) other;
		return null;
	}
	
	@Override
	public Object divide(Object self, Object other) {
		if (other instanceof Long) return (double) self / (long) other;
		if (other instanceof Short) return (double) self / (short) other;
		if (other instanceof Integer) return (double) self / (int) other;
		if (other instanceof Byte) return (double) self / (byte) other;
		if (other instanceof Float) return (double) self / (float) other;
		if (other instanceof Double) return (double) self / (double) other;
		return null;
	}
	
	@Override
	public Object modulus(Object self, Object other) {
		if (other instanceof Long) return (double) self % (long) other;
		if (other instanceof Short) return (double) self % (short) other;
		if (other instanceof Integer) return (double) self % (int) other;
		if (other instanceof Byte) return (double) self % (byte) other;
		if (other instanceof Float) return (double) self % (float) other;
		if (other instanceof Double) return (double) self % (double) other;
		return null;
	}
	
	@Override
	public Object lessThan(Object self, Object other) {
		if (other instanceof Long) return (double) self < (long) other;
		if (other instanceof Short) return (double) self < (short) other;
		if (other instanceof Integer) return (double) self < (int) other;
		if (other instanceof Byte) return (double) self < (byte) other;
		if (other instanceof Float) return (double) self < (float) other;
		if (other instanceof Double) return (double) self < (double) other;
		return null;
	}
	
	@Override
	public Object greaterThan(Object self, Object other) {
		if (other instanceof Long) return (double) self > (long) other;
		if (other instanceof Short) return (double) self > (short) other;
		if (other instanceof Integer) return (double) self > (int) other;
		if (other instanceof Byte) return (double) self > (byte) other;
		if (other instanceof Float) return (double) self > (float) other;
		if (other instanceof Double) return (double) self > (double) other;
		return null;
	}
	
	@Override
	public Object equalTo(Object self, Object other) {
		if (other instanceof Long) return (double) self == (long) other;
		if (other instanceof Short) return (double) self == (short) other;
		if (other instanceof Integer) return (double) self == (int) other;
		if (other instanceof Byte) return (double) self == (byte) other;
		if (other instanceof Float) return (double) self == (float) other;
		if (other instanceof Double) return (double) self == (double) other;
		return null;
	}
	
	@Override
	public Object notEqualTo(Object self, Object other) {
		if (other instanceof Long) return (double) self != (long) other;
		if (other instanceof Short) return (double) self != (short) other;
		if (other instanceof Integer) return (double) self != (int) other;
		if (other instanceof Byte) return (double) self != (byte) other;
		if (other instanceof Float) return (double) self != (float) other;
		if (other instanceof Double) return (double) self != (double) other;
		return null;
	}
	
	@Override
	public Object greaterThanOrEqual(Object self, Object other) {
		if (other instanceof Long) return (double) self >= (long) other;
		if (other instanceof Short) return (double) self >= (short) other;
		if (other instanceof Integer) return (double) self >= (int) other;
		if (other instanceof Byte) return (double) self >= (byte) other;
		if (other instanceof Float) return (double) self >= (float) other;
		if (other instanceof Double) return (double) self >= (double) other;
		return null;
	}
	
	@Override
	public Object lessThanOrEqual(Object self, Object other) {
		if (other instanceof Long) return (double) self <= (long) other;
		if (other instanceof Short) return (double) self <= (short) other;
		if (other instanceof Integer) return (double) self <= (int) other;
		if (other instanceof Byte) return (double) self <= (byte) other;
		if (other instanceof Float) return (double) self <= (float) other;
		if (other instanceof Double) return (double) self <= (double) other;
		return null;
	}
}