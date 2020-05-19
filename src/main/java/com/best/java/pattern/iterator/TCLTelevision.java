package com.best.java.pattern.iterator;

/**
 * @Author: xjxu3
 * @Date: 2020/5/19 10:58
 * @Description: TCL电视
 *
 * 提供一种方法顺序访问聚合对象中的元素，而不暴露其内部表示
 * 聚合类中保留聚合对象存储内部数据的职责，而将遍历内部数据的职责交给迭代器来完成，以实现“单一职责”的原则
 */
public class TCLTelevision implements Television {
	//储存的节目单
	private final Object[] obj = {"CCTV1","CCTV2","CCTV3","CCTV4","CCTV5","CCTV6","CCTV7","CCTV8","CCTV9","CCTV10","CCTV11","CCTV12","CCTV13","CCTVnews"};

	@Override
	public TVIterator createIterator() {
		return new TCLIterator();
	}

	/**
	 * @Author: Mr.Liu
	 * @Date: 13:52 2018/6/8
	 * @Modified by:
	 * @Description: TCL遥控器(TCLIterator)
	 */
	class TCLIterator implements TVIterator{
		//当前频道
		private int currentIndex = 0;

		@Override
		public void setChannel(int i) {
			this.currentIndex = i;
		}

		@Override
		public Object currentChannel() {
			return obj[currentIndex];
		}

		@Override
		public void next() {
			if(currentIndex<obj.length){
				currentIndex++;
			}
		}

		@Override
		public void prevoius() {
			if(currentIndex>-1){
				currentIndex--;
			}
		}

		@Override
		public boolean isLast() {
			return currentIndex >= 14;
		}

		@Override
		public boolean isFirst() {
			return currentIndex <= -1;
		}
	}

	public static void main(String[] args) {
		TCLTelevision tv = new TCLTelevision();
		TVIterator tvIterator = tv.createIterator();
		System.out.println("正向输出节目表：");
		while(!tvIterator.isLast()){
			System.out.println(tvIterator.currentChannel().toString());
			tvIterator.next();
		}

		TVIterator tvIterator2 = tv.createIterator();
		tvIterator2.setChannel(13);
		System.out.println("逆向输出节目表：");
		while(!tvIterator2.isFirst()){
			System.out.println(tvIterator2.currentChannel().toString());
			tvIterator2.prevoius();
		}
	}
}
