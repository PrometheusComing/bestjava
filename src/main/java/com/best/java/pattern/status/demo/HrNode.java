package com.best.java.pattern.status.demo;

/**
 * @Author: xjxu3
 * @Date: 2020/5/18 21:07
 * @Description:
 */
public class HrNode extends Node {

	@Override
	public void nodeHandle(FlowContext context) {
		//先判断流程是否结束
		if(!context.isFlag()){
			// 根据当前流程的状态，来控制流程的走向
			if (context != null &&
					0 == context.getStatus()) { //只有上一级审核通过后才能轮到HR审核
				// 设置当前节点的名称
				setName("HR李");
				//读取上一级的审核内容并加上自己的意见
				System.out.println(context.getMessage()+getName()+"审核通过");
				// 审核通过
				context.setStatus(0); //HR审核通过并指向下一个节点 ,如果没有下一个节点就把状态设置为终结
				context.setFlag(true);

			}
		}else{
			System.out.println("流程已经结束");
		}
	}
}

