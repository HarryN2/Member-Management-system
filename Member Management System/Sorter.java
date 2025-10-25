import java.util.*;

public class Sorter {
    // QuickSort by name
    public static void quickSortByName(List<Member> list) {
        if (list == null || list.size() < 2) return;
        quickSort(list, 0, list.size() - 1);
    }

    private static void quickSort(List<Member> a, int lo, int hi) {
        if (lo >= hi) return;
        int p = partition(a, lo, hi);
        quickSort(a, lo, p - 1);
        quickSort(a, p + 1, hi);
    }

    private static int partition(List<Member> a, int lo, int hi) {
        String pivot = a.get(hi).getName().toLowerCase();
        int i = lo;
        for (int j = lo; j < hi; j++) {
            if (a.get(j).getName().toLowerCase().compareTo(pivot) <= 0) {
                Collections.swap(a, i, j);
                i++;
            }
        }
        Collections.swap(a, i, hi);
        return i;
    }

    // Sort by ID using Collections.sort
    public static void sortById(List<Member> list) {
        list.sort(Comparator.comparing(Member::getId));
    }
}
