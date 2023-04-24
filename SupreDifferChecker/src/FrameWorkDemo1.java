import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.Box;

public class FrameWorkDemo1 {
	TXTchecker txTchecker = new TXTchecker();
	//板块一初始化界面所需组件
	//************************************************************************************
	//设置一个frame名字为F1veMember DifferChecker
	Frame frame=new Frame("F1veMember DifferChecker");
	
	//建立左右的BOX框架 收容hBox和tA
	Box hBox=Box.createHorizontalBox();
	
	//建立上下的BOX框架 收容文件一文件二
	Box vBox=Box.createVerticalBox();
	
	//建立左右的BOX框架 收容bt3 bt4
	Box hBox2=Box.createHorizontalBox();
	
	//建立一个FileDialog,用来选择文件
	FileDialog fd1=new FileDialog(frame,"请选择原则文件",FileDialog.LOAD);
	FileDialog fd2=new FileDialog(frame,"请选择比对文件",FileDialog.LOAD);
	
	//建立打开文件按钮
	Button bt1=new Button("文件一");
	Button bt2=new Button("文件二");
	
	//建立是否更改按钮
	Button bt3=new Button("开始");
	Button bt4=new Button("帮助");
	
	//建立右侧文本框
	TextArea ta=new TextArea("欢迎使用！");
	//***************************************************************************************
	//板块二对第二界面组件进行创建
	//***************************************************************************************
	
	//创建帮助模式对话框
	Dialog d1=new Dialog(frame,"帮助文档",false);
	
	//帮助文档
	TextArea ta2=new TextArea("点击文件一按钮，并且选择原文件。再点击文件二按钮，选择需要比对的按钮。\n"
			+ "当两个文件都选择完毕之后点击开始");
	
	//水印
	TextField tf=new TextField("这是一个水印:F1veMember的第一版文件比对器");
	
	//建立返回按钮
	Button bt5=new Button("返回");
	
	//建立水平Box容器 加入水印和返回按钮
	Box box21=Box.createHorizontalBox();
	
	
	//创建完成模式对话框
	Dialog d2=new Dialog(frame,"DifferChecker",false);

	//创建水平Box容器加入修改后文本
	Box box221=Box.createHorizontalBox();

	//创建修改后文本TextArea
	TextArea ta221Left=new TextArea();

	Box box221right=Box.createVerticalBox();
	TextField taUp=new TextField("是否删除？");
	TextField taDown=new TextField("是否增加？");

	ScrollPane sp1=new ScrollPane();
	ScrollPane sp2=new ScrollPane();
	Box Vsp1=Box.createVerticalBox();
	Box Vsp2=Box.createVerticalBox();

	Button start=new Button("开始转换");
	Button exit=new Button("退出");

	public FrameWorkDemo1() throws IOException {

	}


	//创建checkBox 因为不知道有几处不同所以需要写一个方法来创建传入的整数为几处异同
	public ArrayList<Checkbox[]> createCheckbox(int numDelete,int numAdd) {
		ArrayList<Checkbox[]> list=new ArrayList<Checkbox[]>();
		Checkbox [] DeleteCheckboxs=new Checkbox[numDelete];
		Checkbox [] AddCheckboxs=new Checkbox[numAdd];
		for(int i =0;i<numDelete;i++) {
			DeleteCheckboxs[i]=new Checkbox(getDeleteString(i),false);  //删除用包装好的方法封装
		}
		for(int i =0;i<numAdd;i++) {
			AddCheckboxs[i]=new Checkbox(getAddString(i),false);    //增加用包装好的方法封装
		}
		list.add(DeleteCheckboxs);
		list.add(AddCheckboxs);
		return list;
		
	}
	
	//获取增删String
	public String getAddString(int i) {
		return txTchecker.getAdds().get(i);
	}

	public String getDeleteString(int i) {
		return txTchecker.getDeletes().get(i);
	}

	
	public void init() {
		/*
		 * ***************************************以下包装组件块 板块一
		 */
		
		//将文件一文件二包装到verticalBox里面
		vBox.add(bt1);
		vBox.add(bt2);
		
		//将verticalBox包装到HorizontalBox里面
		hBox.add(vBox);
		hBox.add(ta);
		
		//将bt3 bt4包装到vBox2里
		hBox2.add(bt3);
		hBox2.add(bt4);
		
		//添加hBox至frame中
		frame.add(hBox);
		frame.add(hBox2,BorderLayout.SOUTH);
		
		 //****************************************以下包装组件块 板块二
		//设置模式对话框大小
		d1.setBounds(300, 300, 600, 400);
		//加入TextArea帮助文档
		d1.add(ta2);
		box21.add(tf);
		box21.add(bt5);
		//将帮助文档加入，并且将Box容器定在south位置
		d1.add(box21,BorderLayout.SOUTH);
		
		
		d2.setBounds(600, 0, 1200, 800);
        taUp.disable();
        taDown.disable();
		
		//加入上删除文本框
		box221right.add(taUp);
		
		//createCheckbox 的方法两个行参需要封装方法返回,此处以(100，100)进行测试
		for(int i=0;i<createCheckbox(txTchecker.getDeletes().size(),txTchecker.getAdds().size()).get(0).length;i++) {
			Vsp1.add(createCheckbox(txTchecker.getDeletes().size(),txTchecker.getAdds().size()).get(0)[i]);
		}
		//滚轮加入Vsp1Box
		sp1.add(Vsp1);
		box221right.add(sp1);
		
		//加入下增加文本框
		box221right.add(taDown);
		
		//createCheckbox 的方法两个行参需要封装方法返回,此处以(5，5)进行测试
		for(int i=0;i<createCheckbox(txTchecker.getDeletes().size(),txTchecker.getAdds().size()).get(1).length;i++) {
			Vsp2.add(createCheckbox(txTchecker.getDeletes().size(),txTchecker.getAdds().size()).get(1)[i]);
		}
		//滚轮加入Vsp2Box
		sp2.add(Vsp2);
		box221right.add(sp2);
		box221right.add(start);
		
	    //封装box221
		box221.add(ta221Left);
		box221.add(box221right);
	    //将box221加入Dialog2中
		d2.add(box221);
		d2.add(exit,BorderLayout.SOUTH);
		
		
		
		
		
		
		

		
		
		
		/*
		 * ***************************************以下监听区域快
		 */
		
		//设置打开文件一的监听
		bt1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//将FilaDialog1窗口设为可见
				fd1.setVisible(true);
				
				//此处direction为绝对路径，在此处引入IO代码&&&&&&&&&&&&&&&&&&&&&&&&&
				String direction=fd1.getDirectory();
				//此处file为文件名
				String file=fd1.getFile();

				txTchecker.setPathSrc(direction + file);
			}
			
		});
		
		//设置打开文件二的监听
		bt2.addActionListener(new ActionListener() {
					@Override
				public void actionPerformed(ActionEvent e) {
					//将FilaDialog1窗口设为可见
					fd2.setVisible(true);
						
					//此处direction为绝对路径，在此处引入IO代码&&&&&&&&&&&&&&&&&&&&&&&&&
					String direction=fd2.getDirectory();
					//此处file为文件名
					String file=fd2.getFile();

					txTchecker.setPathCmp(direction + file);
				}
					
		});
		
		//开始 按钮增加监听打开修改可视化界面
		bt3.addActionListener(new ActionListener() {
			@Override
		public void actionPerformed(ActionEvent e) {
				try {
					txTchecker.compare();
					init();
				} catch (IOException ex) {
					throw new RuntimeException(ex);
				}
				ta221Left.append(txTchecker.getText());
				d2.setVisible(true);
				//更改代码实现操作，此处引入IO代码&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
			}
			
		});
		
		//帮助按钮打开非模式对话框（就是可聚焦框架）
		bt4.addActionListener(new ActionListener() {
			@Override
		public void actionPerformed(ActionEvent e) {
				d1.setVisible(true);
			}
			
		});
		
		//帮助板块返回按钮的监听
		bt5.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				d1.setVisible(false);
			}
		 	
		});
		
		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//这里加入增删逻辑方法

			}
			
		});
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {	
				System.exit(0);
			}
			
		});
		
		
		// 设置监听关闭window
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		//包装frame使frame可视
		frame.pack();
		frame.setVisible(true);
	}
	
	public static void main(String[] args) throws IOException {
		new FrameWorkDemo1().init();
	}

}


