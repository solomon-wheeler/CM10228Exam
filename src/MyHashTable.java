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
        for (String this_value : tempArray){ //for each value in our original hash table. < not <= temp array since length not zero indexed, but the array is
            if (this_value !=null && !(this_value.equals(getPlaceholder()))){
                if(add(this_value)){
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
        if (name == null){ //need to ensure user does not add null, as this will cause an error when we try to call methods on the string object
            System.out.println("Cannot add a null value to the hash table");
            return false;
        }
        boolean added = false;
        int hash_val = hashFunction(name);
        if (search(name)){ //Cannot add a value to the hash table if it is already there
            return false;
        }
        if (storageArray[hash_val] == null){ //if location given by hash function is empty then we can add the name here
            storageArray[hash_val] = name;
            added = true;
        }
        else{ //if that location is not empty we have to use linear probing
            for(int i = hash_val +1; i < storageArray.length; i++){ //going through the list from the hash location until we find an empty location or reach the end. < not <= Storage array since  length not zero indexed, but the array is
                if (storageArray[i] ==null || storageArray[i].equals(getPlaceholder())){ // we can overwrite placeholders, they're just here so we can delete and don't have to move values
                    storageArray[i] = name;
                    added = true;
                    break;
                }

            }
            if (!added){
                for(int i = 0; i < hash_val; i++){ //going through the list from the start until we find an empty location of have checked all values
                    if (storageArray[i] ==null || storageArray[i].equals(getPlaceholder())){ // we can overwrite placeholders, they're just here so we don't have to move values when we delete
                        storageArray[i] = name;
                        added = true;
                        break;
                    }
                }

            }

        }

        if (added) {
            numItems ++;
            update_load();
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * works out if we need to resize the hash table, and calls the function to do this if necessary
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
        if (name == null){ //need to ensure user does not add null, as this will cause an error when we try to call methods on the string object
            System.out.println("Cannot add a null value to the hash table");
            return false;
        }
        boolean removed = false;
        int hash_val = hashFunction(name);
        if (storageArray[hash_val].equals(name)){
            storageArray[hash_val] = getPlaceholder();
            removed = true;
        }
        else{
            for(int i = hash_val +1; i < storageArray.length; i++){ //for items in the table after the hash value to the end. < not <= Storage array since  length not zero indexed, but the array is
                if (storageArray[i] == null){ // no point continuing searching, since the value would have been added here if it was in the array
                    return false;
                }
                else if (storageArray[i].equals(name)){
                    storageArray[i] = getPlaceholder();
                    removed = true;
                    break;
                }

            }
            if (!removed){
                for(int i = 0; i < hash_val; i++){ //for items from the start of the table to the hash value.
                    if (storageArray[i] == null){ //no point continuing searching, since the value would have been added here if it was in the array
                        return false;
                    }
                    else if (storageArray[i].equals(name)){
                        storageArray[i] = getPlaceholder();
                        removed = true;
                        break;
                    }

                }

            }

        }
        if (removed) {
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
     * @param name the name to search for in the hash table
     * @return true if found, false otherwise
     */
    public boolean search(String name){
        if (name == null){ //need to ensure user does not add null, as this will cause an error when we try to call methods on the string object
            System.out.println("Cannot add a null value to the hash table");
            return false;
        }
        int hash_val = hashFunction(name);
        if (storageArray[hash_val] == null){ // we can return false if this value is empty, no point checking the hash table
            return false;
        }
        else if (storageArray[hash_val].equals(name)){
            return true;
        }
        else{
            for(int i = hash_val +1; i < storageArray.length; i++){ //for items in the table after the hash value to the end. < not <= Storage array since  length not zero indexed, but the array is
               if (storageArray[i] == null){ //no point continuing searching, since the value would have been added here if it was in the array
                    return false;
                }
                else if (storageArray[i].equals(name)){
                    return true;
                }

            }

            for(int i = 0; i < hash_val; i++){//for items from the start of the table to the hash value.
                if (storageArray[i] == null){//no point continuing searching, since the value would have been added here if it was in the array
                    return false;
                }
                else if (storageArray[i].equals(name)){
                    return true;
                }
            }
        }
        return false;
    }
}