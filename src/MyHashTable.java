// This is a blank file, please complete your solution here.
public class myHashTable extends HashTable {

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
                if (storageArray[i] ==null){
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
                    if (storageArray[i] ==null){
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
        //todo add thing to change length here
    }
    public boolean remove(String name){
        boolean removed = false;
        int hash_val = hashFunction(name);
        if (storageArray[hash_val] == name){
            storageArray[hash_val] = null;
            removed = true;
        }
        else{
            for(int i = hash_val +1; i < storageArray.length; i++){
                if (storageArray[i] ==name){
                    storageArray[i] = null;
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
                        storageArray[i] = null;
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