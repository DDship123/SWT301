package org.example.be.dataBuilder;
import org.example.be.entity.MembershipPlan;
import java.math.BigDecimal;

public class MembershipPlanTestDataBuilder {
    public static MembershipPlan createDefaultMembershipPlan() {
        MembershipPlan plan = new MembershipPlan();
        plan.setPlanId(1);
        plan.setName("Gold Plan");
        plan.setPrice(new BigDecimal("1000000"));
        plan.setPriority("ACTIVE");
        return plan;
    }
    public static MembershipPlan createMembershipPlanWithId(int id) {
        MembershipPlan plan = createDefaultMembershipPlan();
        plan.setPlanId(id);
        return plan;
    }
}
