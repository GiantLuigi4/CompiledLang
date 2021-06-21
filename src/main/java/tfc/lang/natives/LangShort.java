package tfc.lang.natives;

import tfc.lang.Executor;
import tfc.lang.LangClass;

public class LangShort extends LangClass {
	public LangShort(Executor executor) {
		super("ÿshort".getBytes(), executor);
	}
	
	public boolean isInstance(Object o) {
		return o instanceof Short;
	}
	
	@Override
	public Object add(Object self, Object other) {
		if (other instanceof Long) return (short) self + (long) other;
		if (other instanceof Short) return (short) self + (short) other;
		if (other instanceof Integer) return (short) self + (int) other;
		if (other instanceof Byte) return (short) self + (byte) other;
		if (other instanceof Float) return (short) self + (float) other;
		if (other instanceof Double) return (short) self + (double) other;
		return null;
	}
	
	@Override
	public Object subtract(Object self, Object other) {
		if (other instanceof Long) return (short) self - (long) other;
		if (other instanceof Short) return (short) self - (short) other;
		if (other instanceof Integer) return (short) self - (int) other;
		if (other instanceof Byte) return (short) self - (byte) other;
		if (other instanceof Float) return (short) self - (float) other;
		if (other instanceof Double) return (short) self - (double) other;
		return null;
	}
	
	@Override
	public Object multiply(Object self, Object other) {
		if (other instanceof Long) return (short) self * (long) other;
		if (other instanceof Short) return (short) self * (short) other;
		if (other instanceof Integer) return (short) self * (int) other;
		if (other instanceof Byte) return (short) self * (byte) other;
		if (other instanceof Float) return (short) self * (float) other;
		if (other instanceof Double) return (short) self * (double) other;
		return null;
	}
	
	@Override
	public Object divide(Object self, Object other) {
		if (other instanceof Long) return (short) self / (long) other;
		if (other instanceof Short) return (short) self / (short) other;
		if (other instanceof Integer) return (short) self / (int) other;
		if (other instanceof Byte) return (short) self / (byte) other;
		if (other instanceof Float) return (short) self / (float) other;
		if (other instanceof Double) return (short) self / (double) other;
		return null;
	}
	
	@Override
	public Object modulus(Object self, Object other) {
		if (other instanceof Long) return (short) self % (long) other;
		if (other instanceof Short) return (short) self % (short) other;
		if (other instanceof Integer) return (short) self % (int) other;
		if (other instanceof Byte) return (short) self % (byte) other;
		if (other instanceof Float) return (short) self % (float) other;
		if (other instanceof Double) return (short) self % (double) other;
		return null;
	}
	
	@Override
	public Object lessThan(Object self, Object other) {
		if (other instanceof Long) return (short) self < (long) other;
		if (other instanceof Short) return (short) self < (short) other;
		if (other instanceof Integer) return (short) self < (int) other;
		if (other instanceof Byte) return (short) self < (byte) other;
		if (other instanceof Float) return (short) self < (float) other;
		if (other instanceof Double) return (short) self < (double) other;
		return null;
	}
	
	@Override
	public Object greaterThan(Object self, Object other) {
		if (other instanceof Long) return (short) self > (long) other;
		if (other instanceof Short) return (short) self > (short) other;
		if (other instanceof Integer) return (short) self > (int) other;
		if (other instanceof Byte) return (short) self > (byte) other;
		if (other instanceof Float) return (short) self > (float) other;
		if (other instanceof Double) return (short) self > (double) other;
		return null;
	}
	
	@Override
	public Object equalTo(Object self, Object other) {
		if (other instanceof Long) return (short) self == (long) other;
		if (other instanceof Short) return (short) self == (short) other;
		if (other instanceof Integer) return (short) self == (int) other;
		if (other instanceof Byte) return (short) self == (byte) other;
		if (other instanceof Float) return (short) self == (float) other;
		if (other instanceof Double) return (short) self == (double) other;
		return null;
	}
	
	@Override
	public Object notEqualTo(Object self, Object other) {
		if (other instanceof Long) return (short) self != (long) other;
		if (other instanceof Short) return (short) self != (short) other;
		if (other instanceof Integer) return (short) self != (int) other;
		if (other instanceof Byte) return (short) self != (byte) other;
		if (other instanceof Float) return (short) self != (float) other;
		if (other instanceof Double) return (short) self != (double) other;
		return null;
	}
	
	@Override
	public Object greaterThanOrEqual(Object self, Object other) {
		if (other instanceof Long) return (short) self >= (long) other;
		if (other instanceof Short) return (short) self >= (short) other;
		if (other instanceof Integer) return (short) self >= (int) other;
		if (other instanceof Byte) return (short) self >= (byte) other;
		if (other instanceof Float) return (short) self >= (float) other;
		if (other instanceof Double) return (short) self >= (double) other;
		return null;
	}
	
	@Override
	public Object lessThanOrEqual(Object self, Object other) {
		if (other instanceof Long) return (short) self <= (long) other;
		if (other instanceof Short) return (short) self <= (short) other;
		if (other instanceof Integer) return (short) self <= (int) other;
		if (other instanceof Byte) return (short) self <= (byte) other;
		if (other instanceof Float) return (short) self <= (float) other;
		if (other instanceof Double) return (short) self <= (double) other;
		return null;
	}
}