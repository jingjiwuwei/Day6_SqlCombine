public class OrCondition implements Condition {
    private Condition expr1 = null;
    private Condition expr2 = null;

    public OrCondition(Condition expr1, Condition expr2) {
        this.expr1 = expr1;
        this.expr2 = expr2;
    }

    @Override
    public String getSqlDescription() {
        return expr1.getSqlDescription()+" or "+expr2.getSqlDescription();
    }
}