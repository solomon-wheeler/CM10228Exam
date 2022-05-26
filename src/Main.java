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
        table.add("agsdf");
        table.add("fds");
        table.add("三");

        table.add("William");
        table.add("WILLIAM");
        table.add("Theodore");
        table.add(" ");
        System.out.println(table.print());
        table.search("Solly");
        table.search("Steve");
        table.search("Oliver");
        table.search("Elijah");
        table.search("Oliver");
        table.search("James");
        table.search("William");
        table.search("WILLIAM");
        table.search("Theodore");
        table.remove("Solly");
        table.remove("Steve");
        table.remove("Oliver");
        table.remove("Elijah");
        table.remove("Oliver");
        table.remove("James");
        table.remove("William");
        table.remove("WILLIAM");
        table.remove("Theodore");
        table.add("Theodore");
        table.add("fds");
        table.add("fdsa");
        table.add("jkl");
        table.add("fds");
        table.add("agsdf");
        table.add("fds");
        table.add("sdf f");
        table.add("sdf  f");
        table.add("sdf  f");
        table.add("sdf f ");
        table.add(null);
        table.add("三");
        table.add("5768");
        table.add("57681");

        System.out.println(table.print());
        System.out.println(table.search("jkl"));

    }
}

