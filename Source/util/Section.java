package util;
/**
 * 
 * @author Enrique Fernandez Blanco
 * 
 * Created: 16-02-2009
 *
 * @param <E>
 */
public class Section<E,F,G> implements Cloneable{
	
	
	private E name;
	private F value1;
	private G value2;
	
	public Section(E e, F f, G g){
		name = e;
		value1 = f;
		value2 = g;
	}
	
	public E getFirst(){
		return name;
	}
	
	public F getSecond(){
		return value1;
	}
	
	public G getThird(){
		return value2;
	}
	
	public Object clone(){
		return new Section<E, F, G>(this.name,this.value1, this.value2);
	}

}
