public class Main {
    public static void main(String[] args) {
        MyHashTable table = new MyHashTable();
        table.search("Solly");
        table.add("Solly");
        table.add("Steve");
        table.add("Oliver");
        table.add("Elijah");
        table.add("Oliver");
        table.add("James");
        table.add("William");
        table.add("WILLIAM");
        table.add("Theodore");
        table.add("");
        System.out.println(table.print());
        table.remove("Solly");
        System.out.println(table.print());
        System.out.println(table.search("William"));

    }
}

