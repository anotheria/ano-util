package net.anotheria.util.sorter;

class ListEntry<T extends IComparable>{
	public T value;

 	public ListEntry<T> next;

  	public ListEntry(){
   		next = null;
     	value = null;
    }

    public ListEntry(T value){
    	this.value = value;
    }

    public int length(){
    	if (next==null)
     		return 1;
       	return 1+next.length();
    }

	public void insert(ListEntry<T> new_entry, int method){
 		if (next == null){
   			next = new_entry;
      		return;
        }
        if (next.value.compareTo(new_entry.value, method)>=0){
        	new_entry.next = next;
         	next = new_entry;
          	return;
        }
        next.insert(new_entry, method);

  	}
}