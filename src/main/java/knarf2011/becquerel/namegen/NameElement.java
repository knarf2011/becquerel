package knarf2011.becquerel.namegen;

public class NameElement
{
	public NameElement[] next;
	public String[] parts;
	
	public NameElement(NameElement[] subEls, String[] syls)
	{
		this.next = subEls;
		this.parts = syls;
	}
}
