public class MyHashTable extends HashTable {

    public int hashFunction (String key){
        int sum_of_values = 0;
        int this_value;
        for(int i =0; i < key.length();i++){
            this_value = key.charAt(i);
            sum_of_values = sum_of_values + this_value;
        }
        int buckets = storageArray.length;
        return sum_of_values % buckets;
    }
    public void resizeMap(int newSize){
        String[] tempArray = new String[storageArray.length];
        tempArray =  storageArray.clone();
        storageArray = new String[newSize];
        for (int i = 0; i < tempArray.length; i++){
            if (storageArray[i] !=null || storageArray[i] != getPlaceholder()){
                add(storageArray[i]);
            }
        }

    }
    public boolean add(String name){
        boolean added = false;
        int hash_val = hashFunction(name);
        if (storageArray[hash_val] == null){
            storageArray[hash_val] = name;
            added = true;
        }
        else{
            for(int i = hash_val +1; i < storageArray.length; i++){
                if (storageArray[i] ==null || storageArray[i] == getPlaceholder() ){
                    storageArray[i] = name;
                    added = true;
                    break;
                }
                else if (storageArray[i] == name){
                    return false;
                }
            }
            if (added == false){
                for(int i = 0; i < hash_val; i++){
                    if (storageArray[i] ==null || storageArray[i] == getPlaceholder()){
                        storageArray[i] = name;
                        added = true;
                        break;
                    }
                    else if (storageArray[i] == name){
                        return false;
                    }
                }

            }

        }

        if (added == true) {
            numItems ++;
            update_load();
            return true;
        }
        else{
            return false;
        }
    }

    public void update_load(){
        loadFactor=  numItems /  storageArray.length; //check this is a float
        if (loadFactor >= 0.7){
            resizeMap(storageArray.length *2);
        }
        else if ( loadFactor <= 0.2  &&  storageArray.length > 19) //length of array is an integer, so if >19 dividing it by 2 will give a length >- 10. Only needed if we have a start length of storage array =! 10
            resizeMap(storageArray.length / 2); //won't give a decimal result as we coerce to integer and floor
        {

        }
        //todo add thing to change length here
    }
    public boolean remove(String name){
        boolean removed = false;
        int hash_val = hashFunction(name);
        if (storageArray[hash_val] == name){
            storageArray[hash_val] = getPlaceholder();
            removed = true;
        }
        else{
            for(int i = hash_val +1; i < storageArray.length; i++){
                if (storageArray[i] ==name){
                    storageArray[i] = getPlaceholder();
                    removed = true;
                    break;
                }
                else if (storageArray[i] == null){
                    return false;
                }
            }
            if (removed  == false){
                for(int i = 0; i < hash_val; i++){
                    if (storageArray[i] ==name){
                        storageArray[i] = getPlaceholder();
                        removed = true;
                        break;
                    }
                    else if (storageArray[i] == null){
                        return false;
                    }
                }

            }

        }
        if (removed == true) {
            numItems --;
            update_load();
            return true;
        }
        else{
            return false;
        }
    }
    public boolean search(String name){
        int hash_val = hashFunction(name);
        if (storageArray[hash_val] == name){
            return true;
        }
        else{
            for(int i = hash_val +1; i < storageArray.length; i++){
                if (storageArray[i] ==name){
                    return true;
                }
                else if (storageArray[i] == null){
                    return false;
                }
            }

            for(int i = 0; i < hash_val; i++){
                if (storageArray[i] ==name){
                    return true;
                }
                else if (storageArray[i] == null){
                    return false;
                }
            }


        }

        return false;
    }

}