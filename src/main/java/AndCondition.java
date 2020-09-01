public class AndCondition implements Condition {
    private Condition expr1 = null;
    private Condition expr2 = null;

    public AndCondition(Condition expr1, Condition expr2) {
        this.expr1 = expr1;
        this.expr2 = expr2;
    }

    @Override
    public String getSqlDescription() {
        return expr1.getSqlDescription()+" and "+expr2.getSqlDescription();
    }
}