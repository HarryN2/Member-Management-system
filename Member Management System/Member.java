import java.util.Objects;

public class Member {
    private String id;
    private String name;
    private String phone;
    private boolean achievedGoal; // monthly performance
    private double monthlyFee;

    public Member(String id, String name, String phone, boolean achievedGoal, double monthlyFee) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.achievedGoal = achievedGoal;
        this.monthlyFee = monthlyFee;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public boolean isAchievedGoal() { return achievedGoal; }
    public double getMonthlyFee() { return monthlyFee; }

    public void setName(String name) { this.name = name; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setAchievedGoal(boolean achievedGoal) { this.achievedGoal = achievedGoal; }
    public void setMonthlyFee(double monthlyFee) { this.monthlyFee = monthlyFee; }

    @Override
    public String toString() {
        return id + \",\" + name + \",\" + phone + \",\" + achievedGoal + \",\" + monthlyFee;
    }

    public static Member fromCSV(String csv) {
        String[] parts = csv.split(\",\");
        if (parts.length < 5) return null;
        return new Member(parts[0], parts[1], parts[2], Boolean.parseBoolean(parts[3]), Double.parseDouble(parts[4]));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Member)) return false;
        Member member = (Member) o;
        return Objects.equals(id, member.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
