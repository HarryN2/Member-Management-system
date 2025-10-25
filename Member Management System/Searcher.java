import java.util.*;

public class Searcher {
    // Binary search by ID. Assumes list is sorted by ID.
    public static int binarySearchById(List<Member> list, String id) {
        int lo = 0, hi = list.size() - 1;
        while (lo <= hi) {
            int mid = (lo + hi) >>> 1;
            int cmp = list.get(mid).getId().compareTo(id);
            if (cmp == 0) return mid;
            if (cmp < 0) lo = mid + 1;
            else hi = mid - 1;
        }
        return -1;
    }

    // Linear search by name (case-insensitive, substring)
    public static List<Member> searchByName(List<Member> list, String query) {
        List<Member> result = new ArrayList<>();
        String q = query.toLowerCase();
        for (Member m : list) if (m.getName().toLowerCase().contains(q)) result.add(m);
        return result;
    }
}
