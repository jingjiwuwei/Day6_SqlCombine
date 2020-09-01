import org.junit.Assert;
import org.junit.Test;

import java.util.EmptyStackException;

public class UnitTest {
    @Test(expected = EmptyStackException.class)
    public void test_error(){
        AnalysisSql.parse("())(");
    }


    @Test
    public void test_single(){
        Assert.assertEquals("select * from table where (Country=CHINA)",
                AnalysisSql.parse("(Country=CHINA)"));
    }

    //and语句单独查询
    @Test
    public void test_and_simple(){
        Assert.assertEquals("select * from table where " +
                        "(CompanyName=HTSC) and (Country=CHINA)",
                AnalysisSql.parse("(CompanyName=HTSC) and (Country=CHINA)"));
    }

    @Test
    public void test_and_multi(){
        Assert.assertEquals("select * from table where " +
                        "((CompanyName=HTSC) and (Country=CHINA)) and (Age>30)",
                AnalysisSql.parse("((CompanyName=HTSC) and (Country=CHINA)) and (Age>30)"));
    }


    //or语句单独查询
    @Test
    public void test_or_simple(){
        Assert.assertEquals("select * from table where " +
                        "(CompanyName=HTSC) or (Country=CHINA)",
                AnalysisSql.parse("(CompanyName=HTSC) or (Country=CHINA)"));
    }

    @Test
    public void test_or_multi(){
        Assert.assertEquals("select * from table where " +
                        "((CompanyName=HTSC) or (Country=CHINA)) or (Age>30)",
                AnalysisSql.parse("((CompanyName=HTSC) or (Country=CHINA)) or (Age>30)"));
    }

    //and,or组合语句查询
    @Test
    public void test_and_with_or(){
        Assert.assertEquals("select * from table where " +
                        "(CompanyName=HTSC) and ((Country=CHINA) or (Age>30))",
                AnalysisSql.parse("(CompanyName=HTSC) " +
                        "and ((Country=CHINA) or (Age>30))"));
    }

    //and or not 嵌套语句查询
    @Test
    public void test_and_or_not(){
        Assert.assertEquals("select * from table where " +
                        "(((CompanyName=HTSC) or (Age>30)) or (Sex=Male)) " +
                        "and !(!(Country=USA) or (Country=CHINA))",
                AnalysisSql.parse("(((CompanyName=HTSC) or (Age>30)) or (Sex=Male)) " +
                        "and !(!(Country=USA) or (Country=CHINA))"));

    }

    //not语句单独查询
    @Test
    public void test_not_simple(){
        Assert.assertEquals("select * from table where " +
                        "!(CompanyName=HTSC)",
                AnalysisSql.parse("!(CompanyName=HTSC)"));
    }
    
    // not 语句和 and ，or 组合查询
    @Test
    public void test_not_and_or_combine(){
        Assert.assertEquals("select * from table where " +
                        "(CompanyName=HTSC) or (!(Country=CHINA) and !(Country=USA))",
                AnalysisSql.parse("(CompanyName=HTSC) " +
                        "or (!(Country=CHINA) and !(Country=USA))"));
    }




}
