import java.io.*;
import java.util.*;

public class MemberManager {
    private List<Member> members = new ArrayList<>();
    private final String dataFile = "members.csv";

    public List<Member> getMembers() { return members; }

    public void addMember(Member m) {
        // avoid duplicate IDs
        if (!members.contains(m)) members.add(m);
    }

    public boolean removeMemberById(String id) {
        return members.removeIf(m -> m.getId().equals(id));
    }

    public Member findById(String id) {
        for (Member m : members) if (m.getId().equals(id)) return m;
        return null;
    }

    public void load() throws IOException {
        members.clear();
        File f = new File(dataFile);
        if (!f.exists()) return; // nothing to load yet
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                Member m = Member.fromCSV(line);
                if (m != null) members.add(m);
            }
        }
    }

    public void save() throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(dataFile))) {
            for (Member m : members) bw.write(m.toString() + "\\n");
        }
    }
}
