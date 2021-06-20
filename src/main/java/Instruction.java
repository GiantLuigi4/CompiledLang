public class Instruction {
	public final byte id;
	public final String ainfo0;
	public final String ainfo1;
	
	public Instruction(byte id, String ainfo0, String ainfo1) {
		this.id = id;
		this.ainfo0 = ainfo0;
		this.ainfo1 = ainfo1;
	}
	
	@Override
	public String toString() {
		return "Instruction{" +
				"id=" + id +
				", ainfo0='" + ainfo0 + '\'' +
				", ainfo1='" + ainfo1 + '\'' +
				'}';
	}
}
