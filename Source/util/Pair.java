package util;
/**
 * 
 * @author Enrique Fernandez Blanco
 * 
 * Created: 16-02-2009
 *
 * @param <E>
 */
public class Pair<E,F> implements Cloneable{
	
	
	private E name;
	private F value;
	
	public Pair(E e, F f){
		name = e;
		value = f;
	}
	
	public E getFirst(){
		return name;
	}
	
	public F getSecond(){
		return value;
	}
	
	public Object clone(){
		return new Pair<E, F>(this.name,this.value);
	}

}
