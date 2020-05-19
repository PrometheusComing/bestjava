package com.best.java.pattern.status.demo;

/**
 * @Author: xjxu3
 * @Date: 2020/5/18 21:07
 * @Description:
 */
public class LeadNode extends Node {
	@Override
	public void nodeHandle(FlowContext context) {
		//根据当前流程处理情况 ，来控制流程的走向
		//先判断流程是否结束
		if (!context.isFlag()) {
			System.out.println(context.getMessage()); //先读取申请的内容
			if (context != null && 3 == context.getStatus()) { //只有出于已经申请的状态才又部门领导审核
				//设置当前节点的名称
				setName("张经理");
				//加上审核意见
				context.setMessage(context.getMessage() + ";" + getName() + "审核通过;");
				//审核通过
				context.setStatus(0); //审核通过并指向下一个节点
				// 由于现在FlowContext要做其他处理，则改变状态，让hr处理
				context.setNode(new HrNode());
				context.getNode().nodeHandle(context);
			}
		} else {
			System.err.println("流程已经结束");
		}
	}
}
