public class TerminalCondition implements Condition {
    private String data;

    public TerminalCondition(String data){
        this.data = data;
    }

    @Override
    public String getSqlDescription() {
        return data;
    }
}
