package net.anotheria.util;

import net.anotheria.util.sorter.SortType;

public abstract class LabeledSortType extends SortType{
	public LabeledSortType(int aMethod){
		super(aMethod);
	}
	
	public LabeledSortType(int aMethod, boolean order){
		super(aMethod, order);
	}

	public abstract String[] getDescriptions();
	
}