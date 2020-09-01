import java.util.Stack;

public class AnalysisSql {
    //输入的每条单独表达式用()括起来。
    //!符号放在括号前
    //and和or符号均采用小写。

    public static String parse(String condition){
        //处理括号
        Stack<String> bracketStack = new Stack<String>();
        //存储and和or
        Stack<String> operationStack = new Stack<String>();
        //存储表达式
        Stack<String> conditionStack = new Stack<String>();

        String prefix = "select * from table where ";

        String sql = "";

        String tempCondition;
        String temConditionLeft;
        String tempBracket;
        String tempOperation;

        int index = 0;
        condition = condition.replace(" ","");


        for (int i = 0; i < condition.length(); i++){
            char c = condition.charAt(i);
            if (c =='('){
                if(!bracketStack.isEmpty() && bracketStack.peek().equals("!")){
                    bracketStack.pop();
                    bracketStack.push("!(");
                }else {
                    bracketStack.push(Character.toString(c));
                }
                //读到下一个右括号）
                if(i+1<condition.length() && Character.isLetterOrDigit(condition.charAt(i+1))){
                    index = i+1;
                    while(condition.charAt(i) !=')'){
                        i++;
                    }
                    conditionStack.push(condition.substring(index,i));
                    i=i-1;
                }
            }
            if(c==')'){
                tempBracket = bracketStack.pop();
                tempCondition = conditionStack.pop();
                if(tempCondition.contains(")")){
                    tempOperation = operationStack.pop();
                    temConditionLeft = conditionStack.pop();
                    tempCondition = combineSQL(temConditionLeft,tempCondition,tempOperation);
                }
                conditionStack.push(tempBracket + tempCondition + ")");

                //有括号右面如果有and和or的操作符，将其push到operationStack
                if(i+1<condition.length()){
                    if(condition.charAt(i+1)=='a'&&condition.substring(i+1,i+4).equals("and")){
                        operationStack.push(condition.substring(i+1,i+4));
                        i=i+3;
                    }else if(condition.charAt(i+1)=='o'&&condition.substring(i+1,i+3).equals("or")){
                        operationStack.push(condition.substring(i+1,i+3));
                        i=i+2;
                    }
                }

            }
            if(c=='!'){
                bracketStack.push(Character.toString(c));
            }
        }

        while(!operationStack.isEmpty()){
            tempOperation = operationStack.pop();
            tempCondition = conditionStack.pop();
            temConditionLeft = conditionStack.pop();
            tempCondition= combineSQL(temConditionLeft,tempCondition,tempOperation);
            if(operationStack.isEmpty()){
                conditionStack.push(tempCondition);
            }else{
                conditionStack.push("("+tempCondition+")");}
        }
        sql = conditionStack.pop();
        return prefix+sql;

    }

    static String combineSQL(String expr1,String expr2,String operation){
        String result="";

        Condition expersion1 = new TerminalCondition(expr1);
        Condition expersion2 = new TerminalCondition(expr2);

        if(operation.equals("and")){
            result = new AndCondition(expersion1,expersion2).getSqlDescription();
        }
        if(operation.equals("or")){
            result = new OrCondition(expersion1,expersion2).getSqlDescription();
        }

        return result;
    }




}