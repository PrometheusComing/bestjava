package com.best.java.pattern.command;

/**
 * @Author: xjxu3
 * @Date: 2020/5/19 11:45
 * @Description: 　 ●　　更松散的耦合
 * <p>
 * 　　命令模式使得发起命令的对象——客户端，和具体实现命令的对象——接收者对象完全解耦，也就是说发起命令的对象完全不知道具体实现对象是谁，也不知道如何实现。
 * <p>
 * 　　●　　更动态的控制
 * <p>
 * 　　命令模式把请求封装起来，可以动态地对它进行参数化、队列化和日志化等操作，从而使得系统更灵活。
 * <p>
 * 　　●　　很自然的复合命令
 * <p>
 * 　　命令模式中的命令对象能够很容易地组合成复合命令，也就是宏命令，从而使系统操作更简单，功能更强大。
 * <p>
 * 　　●　　更好的扩展性
 * <p>
 * 　　由于发起命令的对象和具体的实现完全解耦，因此扩展新的命令就很容易，只需要实现新的命令对象，然后在装配的时候，
 * 把具体的实现对象设置到命令对象中，然后就可以使用这个命令对象，已有的实现完全不用变化
 */
public class Julia {

	public static void main(String[] args) {
		//创建接收者对象
		AudioPlayer audioPlayer = new AudioPlayer();
		//创建命令对象
		Command playCommand = new PlayCommand(audioPlayer);
		Command rewindCommand = new RewindCommand(audioPlayer);
		Command stopCommand = new StopCommand(audioPlayer);
		//创建请求者对象
		Keypad keypad = new Keypad();
		keypad.setPlayCommand(playCommand);
		keypad.setRewindCommand(rewindCommand);
		keypad.setStopCommand(stopCommand);
		//测试
		keypad.play();
		keypad.rewind();
		keypad.stop();

		// 改进
		KeyPadImprove keyPadImprove = new KeyPadImprove();
		keyPadImprove.add("playCommand", playCommand);
		keyPadImprove.add("rewindCommand", rewindCommand);
		keyPadImprove.add("stopCommand", stopCommand);
		// 宏命令
		MacroCommand marco = new MacroAudioCommand();
		marco.add(playCommand);
		marco.add(rewindCommand);
		marco.add(stopCommand);

		keyPadImprove.add("marco", marco);


		keyPadImprove.execute("playCommand");
		keyPadImprove.execute("rewindCommand");
		keyPadImprove.execute("stopCommand");
		keyPadImprove.execute("marco");
	}
}
