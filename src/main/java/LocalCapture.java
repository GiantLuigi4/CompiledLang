import java.util.ArrayList;

// TODO: types
public class LocalCapture {
	// TODO: convert to Object[]
	public ArrayList<Object> locals = new ArrayList<>();
	public ArrayList<LangClass> types = new ArrayList<>();
	public ArrayList<Integer> pushPoints = new ArrayList<>();
	
	public Object getLocal(int element) {
//		System.out.println("Getting local " + element);
		return locals.get(element);
	}
	
	public Object setLocal(int element, Object o) {
//		System.out.println("Setting local " + element + " to " + o);
		if (types.get(element).isInstance(o)) return locals.set(element, o);
		else if (o != null) throw new RuntimeException(o + " is not an instance of " + types.get(element));
		return null;
	}
	
	public void addLocal(LangClass clazz) {
//		System.out.println("Adding a new local of type null");//TODO
		locals.add(null);
		types.add(clazz);
	}
	
	public void push() {
//		System.out.println("Pushing locals");
		pushPoints.add(locals.size());
	}
	
	public void pop() {
		int num = pushPoints.remove(pushPoints.size() - 1);
//		System.out.println("Popping " + (locals.size() - num) + " locals");
		while (locals.size() != num) {
			locals.remove(locals.size() - 1);
			if (types.size() >= locals.size()) types.remove(locals.size());
		}
	}
}
