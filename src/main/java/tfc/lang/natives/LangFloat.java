package tfc.lang.natives;

import tfc.lang.Executor;
import tfc.lang.LangClass;

public class LangFloat extends LangClass {
	public LangFloat(Executor executor) {
		super("ÿfloat".getBytes(), executor);
	}
	
	public boolean isInstance(Object o) {
		return o instanceof Float;
	}
	
	@Override
	public Object add(Object self, Object other) {
		if (other instanceof Long) return (float) self + (long) other;
		if (other instanceof Short) return (float) self + (short) other;
		if (other instanceof Integer) return (float) self + (int) other;
		if (other instanceof Byte) return (float) self + (byte) other;
		if (other instanceof Float) return (float) self + (float) other;
		if (other instanceof Double) return (float) self + (double) other;
		return null;
	}
	
	@Override
	public Object subtract(Object self, Object other) {
		if (other instanceof Long) return (float) self - (long) other;
		if (other instanceof Short) return (float) self - (short) other;
		if (other instanceof Integer) return (float) self - (int) other;
		if (other instanceof Byte) return (float) self - (byte) other;
		if (other instanceof Float) return (float) self - (float) other;
		if (other instanceof Double) return (float) self - (double) other;
		return null;
	}
	
	@Override
	public Object multiply(Object self, Object other) {
		if (other instanceof Long) return (float) self * (long) other;
		if (other instanceof Short) return (float) self * (short) other;
		if (other instanceof Integer) return (float) self * (int) other;
		if (other instanceof Byte) return (float) self * (byte) other;
		if (other instanceof Float) return (float) self * (float) other;
		if (other instanceof Double) return (float) self * (double) other;
		return null;
	}
	
	@Override
	public Object divide(Object self, Object other) {
		if (other instanceof Long) return (float) self / (long) other;
		if (other instanceof Short) return (float) self / (short) other;
		if (other instanceof Integer) return (float) self / (int) other;
		if (other instanceof Byte) return (float) self / (byte) other;
		if (other instanceof Float) return (float) self / (float) other;
		if (other instanceof Double) return (float) self / (double) other;
		return null;
	}
	
	@Override
	public Object modulus(Object self, Object other) {
		if (other instanceof Long) return (float) self % (long) other;
		if (other instanceof Short) return (float) self % (short) other;
		if (other instanceof Integer) return (float) self % (int) other;
		if (other instanceof Byte) return (float) self % (byte) other;
		if (other instanceof Float) return (float) self % (float) other;
		if (other instanceof Double) return (float) self % (double) other;
		return null;
	}
	
	@Override
	public Object lessThan(Object self, Object other) {
		if (other instanceof Long) return (float) self < (long) other;
		if (other instanceof Short) return (float) self < (short) other;
		if (other instanceof Integer) return (float) self < (int) other;
		if (other instanceof Byte) return (float) self < (byte) other;
		if (other instanceof Float) return (float) self < (float) other;
		if (other instanceof Double) return (float) self < (double) other;
		return null;
	}
	
	@Override
	public Object greaterThan(Object self, Object other) {
		if (other instanceof Long) return (float) self > (long) other;
		if (other instanceof Short) return (float) self > (short) other;
		if (other instanceof Integer) return (float) self > (int) other;
		if (other instanceof Byte) return (float) self > (byte) other;
		if (other instanceof Float) return (float) self > (float) other;
		if (other instanceof Double) return (float) self > (double) other;
		return null;
	}
	
	@Override
	public Object equalTo(Object self, Object other) {
		if (other instanceof Long) return (float) self == (long) other;
		if (other instanceof Short) return (float) self == (short) other;
		if (other instanceof Integer) return (float) self == (int) other;
		if (other instanceof Byte) return (float) self == (byte) other;
		if (other instanceof Float) return (float) self == (float) other;
		if (other instanceof Double) return (float) self == (double) other;
		return null;
	}
	
	@Override
	public Object notEqualTo(Object self, Object other) {
		if (other instanceof Long) return (float) self != (long) other;
		if (other instanceof Short) return (float) self != (short) other;
		if (other instanceof Integer) return (float) self != (int) other;
		if (other instanceof Byte) return (float) self != (byte) other;
		if (other instanceof Float) return (float) self != (float) other;
		if (other instanceof Double) return (float) self != (double) other;
		return null;
	}
	
	@Override
	public Object greaterThanOrEqual(Object self, Object other) {
		if (other instanceof Long) return (float) self >= (long) other;
		if (other instanceof Short) return (float) self >= (short) other;
		if (other instanceof Integer) return (float) self >= (int) other;
		if (other instanceof Byte) return (float) self >= (byte) other;
		if (other instanceof Float) return (float) self >= (float) other;
		if (other instanceof Double) return (float) self >= (double) other;
		return null;
	}
	
	@Override
	public Object lessThanOrEqual(Object self, Object other) {
		if (other instanceof Long) return (float) self <= (long) other;
		if (other instanceof Short) return (float) self <= (short) other;
		if (other instanceof Integer) return (float) self <= (int) other;
		if (other instanceof Byte) return (float) self <= (byte) other;
		if (other instanceof Float) return (float) self <= (float) other;
		if (other instanceof Double) return (float) self <= (double) other;
		return null;
	}
}