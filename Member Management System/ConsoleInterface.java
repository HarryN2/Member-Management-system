import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class ConsoleInterface {
    private MemberManager manager;
    private Scanner scanner = new Scanner(System.in);

    public ConsoleInterface(MemberManager manager) {
        this.manager = manager;
    }

    public void start() {
        try { manager.load(); } catch (IOException e) { System.out.println("No data yet."); }
        boolean running = true;
        while (running) {
            System.out.println("\n=== Member Management System (Console) ===");
            System.out.println("1. List members\n2. Add member\n3. Delete member\n4. Search by ID\n5. Search by name\n6. Sort by name\n7. Sort by ID\n8. Save\n9. Exit");
            System.out.print("Choose: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1": listMembers(); break;
                case "2": addMember(); break;
                case "3": deleteMember(); break;
                case "4": searchById(); break;
                case "5": searchByName(); break;
                case "6": Sorter.quickSortByName(manager.getMembers()); System.out.println("Sorted by name."); break;
                case "7": Sorter.sortById(manager.getMembers()); System.out.println("Sorted by ID."); break;
                case "8": saveData(); break;
                case "9": running = false; saveData(); break;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    private void listMembers() {
        List<Member> ms = manager.getMembers();
        if (ms.isEmpty()) { System.out.println("No members."); return; }
        for (Member m : ms) System.out.println(m.getId()+" | "+m.getName()+" | "+m.getPhone()+" | Achieved:"+m.isAchievedGoal()+" | Fee:"+m.getMonthlyFee());
    }

    private void addMember() {
        System.out.print("ID: "); String id = scanner.nextLine().trim();
        if (id.isEmpty()) { System.out.println("ID cannot be empty."); return; }
        if (manager.findById(id) != null) { System.out.println("ID already exists."); return; }
        System.out.print("Name: "); String name = scanner.nextLine();
        System.out.print("Phone: "); String phone = scanner.nextLine();
        System.out.print("Achieved goal this month? (true/false): "); boolean g = Boolean.parseBoolean(scanner.nextLine());
        System.out.print("Monthly Fee: "); double fee = Double.parseDouble(scanner.nextLine());
        manager.addMember(new Member(id, name, phone, g, fee));
        System.out.println("Added.");
    }

    private void deleteMember() {
        System.out.print("Enter ID to delete: "); String id = scanner.nextLine();
        boolean ok = manager.removeMemberById(id);
        System.out.println(ok ? "Deleted." : "Not found.");
    }

    private void searchById() {
        System.out.print("Enter ID to search: "); String id = scanner.nextLine();
        Sorter.sortById(manager.getMembers());
        int idx = Searcher.binarySearchById(manager.getMembers(), id);
        if (idx >= 0) System.out.println("Found: " + manager.getMembers().get(idx));
        else System.out.println("Not found.");
    }

    private void searchByName() {
        System.out.print("Enter name query: "); String q = scanner.nextLine();
        List<Member> res = Searcher.searchByName(manager.getMembers(), q);
        if (res.isEmpty()) System.out.println("No results.");
        else res.forEach(m -> System.out.println(m));
    }

    private void saveData() {
        try { manager.save(); System.out.println("Saved."); } catch (IOException e) { System.out.println("Save failed: " + e.getMessage()); }
    }
}
