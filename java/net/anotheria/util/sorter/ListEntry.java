package net.anotheria.util.sorter;

class ListEntry<T extends IComparable> {
	public T value;

 	public ListEntry<T> next;

  	public ListEntry(){
   		next = null;
     	value = null;
    }

    public ListEntry(T aValue){
    	this.value = aValue;
    }

    public int length(){
    	if (next==null)
     		return 1;
       	return 1+next.length();
    }

	public void insert(ListEntry<T> aNewEntry, int aMethod){
 		if (next == null){
   			next = aNewEntry;
      		return;
        }
        if (next.value.compareTo(aNewEntry.value, aMethod)>=0){
        	aNewEntry.next = next;
         	next = aNewEntry;
          	return;
        }
        next.insert(aNewEntry, aMethod);

  	}
}