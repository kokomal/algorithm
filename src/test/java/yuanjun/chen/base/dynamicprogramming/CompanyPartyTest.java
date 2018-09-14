/**  
 * @Title: CompanyPartyTest.java   
 * @Package: yuanjun.chen.base.dynamicprogramming   
 * @Description: 测试公司聚会问题    
 * @author: 陈元俊     
 * @date: 2018年9月14日 下午2:10:15   
 * @version V1.0 
 * @Copyright: 2018 All rights reserved. 
 */
package yuanjun.chen.base.dynamicprogramming;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import yuanjun.chen.base.dynamicprogramming.companyParty.CompanyPartyConvivialityAlgo;
import yuanjun.chen.base.dynamicprogramming.companyParty.EmployeeTreeNode;

/**   
 * @ClassName: CompanyPartyTest   
 * @Description: 测试公司聚会问题  
 * @author: 陈元俊 
 * @date: 2018年9月14日 下午2:10:15  
 */
public class CompanyPartyTest {
    @Test
    public void testCompanyDP() {
        EmployeeTreeNode stuart = buildACompany();
        
        Map<String, Integer> happiness = fetchHappinessList();
        CompanyPartyConvivialityAlgo.init(happiness, stuart);
        CompanyPartyConvivialityAlgo.solve();
    }

    private static Map<String, Integer> fetchHappinessList() {
        Map<String, Integer> happiness = new HashMap<>();
        happiness.put("Stuart", 100);
        happiness.put("Henry", 80);
        happiness.put("Bob", 90);
        happiness.put("Alice", 30);
        happiness.put("Adam", 10);
        happiness.put("Lucas", 80);
        happiness.put("Jill", 10);
        return happiness;
    }

    /**
     *    Stuart(100) 
     *      /
     *     /
     *    Henry(80)-->Bob(90)-->Alice(30)
     *    /            /
     *   /            /
     *  Adam(10)     Lucas(80)-->Jill(10)
     */
    private static EmployeeTreeNode buildACompany() {
        EmployeeTreeNode stuart = new EmployeeTreeNode("Stuart");
        EmployeeTreeNode henry = new EmployeeTreeNode("Henry");
        EmployeeTreeNode bob = new EmployeeTreeNode("Bob");
        EmployeeTreeNode alice = new EmployeeTreeNode("Alice");
        EmployeeTreeNode adam = new EmployeeTreeNode("Adam");
        EmployeeTreeNode lucas = new EmployeeTreeNode("Lucas");
        EmployeeTreeNode jill = new EmployeeTreeNode("Jill");

        stuart.setChild(henry);
        henry.setSibling(bob);
        bob.setSibling(alice);
        henry.setChild(adam);
        bob.setChild(lucas);
        lucas.setSibling(jill);
        return stuart;
    }
}
