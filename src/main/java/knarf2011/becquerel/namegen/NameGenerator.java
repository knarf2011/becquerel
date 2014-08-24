package knarf2011.becquerel.namegen;

import java.util.Random;

public class NameGenerator
{
	//All this code is setting up the letters and groups of letters to be arranged into names.
	public static NameElement ie2 = new NameElement(new NameElement[]{new NameElement(null
			,new String[]{"o","a","er","or","","","",""})}
	,new String[]{"t","n","d","p","ck","ch","ng","x","ns","r"});
	public static NameElement ie = new NameElement(new NameElement[]{
			ie2}
		,new String[]{"i","e"});
	
	public static NameElement[] start = new NameElement[]{
			//starting syllables
			new NameElement(new NameElement[]{
					ie
			},new String[]{"Ex","Ax","Ox","Ix","Z","Z","C","C","Equ","Aqu"}),
			new NameElement(new NameElement[]{
			new NameElement(new NameElement[]{
					new NameElement(new NameElement[]{
							ie,ie,new NameElement(null,new String[]{"o","a","er","or","","","",""})
						},new String[]{"bb","nn","nk","dd","t","tt","pp","ck","ll"}), ie
					},new String[]{"o","a","i"})
			},new String[]{"F","Fr","H","B","Y"})
	};
	
	public static String getName()
	{
		Random r = new Random();
		return getName(start[r.nextInt(start.length)], r);
	}
	
	public static String getName(NameElement e, Random r)
	{
		String ret = e.parts[r.nextInt(e.parts.length)];
		if(e.next == null)
			return ret;
		if(e.next.length == 0)
			return ret;
		return ret + getName(e.next[r.nextInt(e.next.length)], r);
	}
}