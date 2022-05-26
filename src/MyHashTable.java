//I don't think it was made clear in the question or lecture slides whether you should loop back to the start of the list when linear probing, I have assumed you should as this is the implementation I am familiar with.

public class MyHashTable extends HashTable {
    /**
     * Hash function which takes the sum of ASCII values in a key, does modulo of the number of buckets and returns the result
     * @param key they key for the hash function
     * @return hashvalue
     */
    public int hashFunction (String key){
        int sum_of_values = 0;
        int this_value;
        for(int i =0; i < key.length();i++){ //for each Character in our string
            this_value = key.charAt(i);
            sum_of_values = sum_of_values + this_value;
        }
        int buckets = storageArray.length;
        return sum_of_values % buckets;
    }

    /**
     * Takes a new size of the map and resizes the hash table accordingly, then re-adds values from the hash table
     * @param newSize the size of the new map
     */
    public void resizeMap(int newSize){
        String[] tempArray = new String[storageArray.length]; //have to store the array as a temp variable so when we create the new one it isn't overwritten
        tempArray =  storageArray.clone();
        storageArray = new String[newSize];

        for (int i = 0; i < tempArray.length; i++){ //for each value in our original hash table < not <= temp array since length not zero indexed, but the array is
            if (tempArray[i] !=null && tempArray[i] != getPlaceholder()){
                if(add(tempArray[i]) == true){
                    numItems --;// we need to take away one from the number of items because we add one to this in the add() method. We cannot just reset the number of values because then when we add the values we may end up with a load factor < 0.2
                }
            }
        }

    }

    /**
     * Adds a value to our hash table
     * @param name name to be added to the hash table
     * @return true if added/false otherwise
     */
    public boolean add(String name){
        boolean added = false;
        int hash_val = hashFunction(name);
        if (search(name) == true){ //Cannot add a value to the hash table if it is already there
            return false;
        }
        if (storageArray[hash_val] == null){ //if location given by hash function is empty then we can add the name here

            storageArray[hash_val] = name;
            added = true;
        }
        else{
            for(int i = hash_val +1; i < storageArray.length; i++){ //going through the list from the hash location until we find an empty location or reach the end. < not <= Storage array since  length not zero indexed, but the array is
                if (storageArray[i] ==null || storageArray[i] == getPlaceholder() ){ // we can overwrite placeholders, they're just here so we can delete and don't have to move values
                    storageArray[i] = name;
                    added = true;
                    break;
                }

            }
            if (added == false){
                for(int i = 0; i < hash_val; i++){ //going through the list from the start until we find an empty location of have checked all values
                    if (storageArray[i] ==null || storageArray[i] == getPlaceholder()){ // we can overwrite placeholders, they're just here, so we can delete and don't have to move values
                        storageArray[i] = name;
                        added = true;
                        break;
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

    /**
     * works out if we need to resize the hash table, and calls the function to do this if neccesasry
     */
    public void resize(){
        if (loadFactor >= 0.7){ //if load factor is greater than 0.7 we double length of the list
            resizeMap(storageArray.length *2);
            update_load();
        }
        else if ( loadFactor <= 0.2  &&  storageArray.length > 19) { //length of array is an integer, so if >19 dividing it by 2 will give a length >- 10. Only needed if we have a start length of storage array =! 10
            resizeMap(storageArray.length / 2); //won't give a decimal result as we coerce to integer and floor
            update_load();
        }
    }

    /**
     * Works out the load factor, and updates the class variable
     */
    public void update_load(){
        loadFactor =  (double) numItems /  storageArray.length; //need to cast this to a double to ensure there is no coercion since numItems is an integer
        resize();
    }

    /**
     * removes a value from the hash table if it exists, and replaces it with a placeholder
     * @param name, the name to be removed from the hash table
     * @return true if removed, false otherwise
     */
    public boolean remove(String name){
        boolean removed = false;
        int hash_val = hashFunction(name);
        if (storageArray[hash_val] == name){
            storageArray[hash_val] = getPlaceholder();
            removed = true;
        }
        else{
            for(int i = hash_val +1; i < storageArray.length; i++){ //for items in the table after the hash value to the end. < not <= Storage array since  length not zero indexed, but the array is
                if (storageArray[i] ==name){
                    storageArray[i] = getPlaceholder();
                    removed = true;
                    break;
                }
                else if (storageArray[i] == null){ // no point continuing searching, since the value would have been added here if it was in the array
                    return false;
                }
            }
            if (removed  == false){
                for(int i = 0; i < hash_val; i++){ //for items from the start of the table to the hash value.
                    if (storageArray[i] ==name){
                        storageArray[i] = getPlaceholder();
                        removed = true;
                        break;
                    }
                    else if (storageArray[i] == null){ //no point continuing searching, since the value would have been added here if it was in the array
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

    /**
     * Returns whether the value exists the hash table
     * @param name
     * @return true if found, false otherwise
     */
    public boolean search(String name){
        int hash_val = hashFunction(name);
        if (storageArray[hash_val] == name){
            return true;
        }
        else{
            for(int i = hash_val +1; i < storageArray.length; i++){ //for items in the table after the hash value to the end. < not <= Storage array since  length not zero indexed, but the array is
                if (storageArray[i] ==name){
                    return true;
                }
                else if (storageArray[i] == null){ //no point continuing searching, since the value would have been added here if it was in the array
                    return false;
                }
            }

            for(int i = 0; i < hash_val; i++){//for items from the start of the table to the hash value.
                if (storageArray[i] ==name){
                    return true;
                }
                else if (storageArray[i] == null){//no point continuing searching, since the value would have been added here if it was in the array
                    return false;
                }
            }


        }

        return false;
    }
}