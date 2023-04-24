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
	//���һ��ʼ�������������
	//************************************************************************************
	//����һ��frame����ΪF1veMember DifferChecker
	Frame frame=new Frame("F1veMember DifferChecker");
	
	//�������ҵ�BOX��� ����hBox��tA
	Box hBox=Box.createHorizontalBox();
	
	//�������µ�BOX��� �����ļ�һ�ļ���
	Box vBox=Box.createVerticalBox();
	
	//�������ҵ�BOX��� ����bt3 bt4
	Box hBox2=Box.createHorizontalBox();
	
	//����һ��FileDialog,����ѡ���ļ�
	FileDialog fd1=new FileDialog(frame,"��ѡ��ԭ���ļ�",FileDialog.LOAD);
	FileDialog fd2=new FileDialog(frame,"��ѡ��ȶ��ļ�",FileDialog.LOAD);
	
	//�������ļ���ť
	Button bt1=new Button("�ļ�һ");
	Button bt2=new Button("�ļ���");
	
	//�����Ƿ���İ�ť
	Button bt3=new Button("��ʼ");
	Button bt4=new Button("����");
	
	//�����Ҳ��ı���
	TextArea ta=new TextArea("��ӭʹ�ã�");
	//***************************************************************************************
	//�����Եڶ�����������д���
	//***************************************************************************************
	
	//��������ģʽ�Ի���
	Dialog d1=new Dialog(frame,"�����ĵ�",false);
	
	//�����ĵ�
	TextArea ta2=new TextArea("����ļ�һ��ť������ѡ��ԭ�ļ����ٵ���ļ�����ť��ѡ����Ҫ�ȶԵİ�ť��\n"
			+ "�������ļ���ѡ�����֮������ʼ");
	
	//ˮӡ
	TextField tf=new TextField("����һ��ˮӡ:F1veMember�ĵ�һ���ļ��ȶ���");
	
	//�������ذ�ť
	Button bt5=new Button("����");
	
	//����ˮƽBox���� ����ˮӡ�ͷ��ذ�ť
	Box box21=Box.createHorizontalBox();
	
	
	//�������ģʽ�Ի���
	Dialog d2=new Dialog(frame,"DifferChecker",false);

	//����ˮƽBox���������޸ĺ��ı�
	Box box221=Box.createHorizontalBox();

	//�����޸ĺ��ı�TextArea
	TextArea ta221Left=new TextArea();

	Box box221right=Box.createVerticalBox();
	TextField taUp=new TextField("�Ƿ�ɾ����");
	TextField taDown=new TextField("�Ƿ����ӣ�");

	ScrollPane sp1=new ScrollPane();
	ScrollPane sp2=new ScrollPane();
	Box Vsp1=Box.createVerticalBox();
	Box Vsp2=Box.createVerticalBox();

	Button start=new Button("��ʼת��");
	Button exit=new Button("�˳�");

	public FrameWorkDemo1() throws IOException {

	}


	//����checkBox ��Ϊ��֪���м�����ͬ������Ҫдһ���������������������Ϊ������ͬ
	public ArrayList<Checkbox[]> createCheckbox(int numDelete,int numAdd) {
		ArrayList<Checkbox[]> list=new ArrayList<Checkbox[]>();
		Checkbox [] DeleteCheckboxs=new Checkbox[numDelete];
		Checkbox [] AddCheckboxs=new Checkbox[numAdd];
		for(int i =0;i<numDelete;i++) {
			DeleteCheckboxs[i]=new Checkbox(getDeleteString(i),false);  //ɾ���ð�װ�õķ�����װ
		}
		for(int i =0;i<numAdd;i++) {
			AddCheckboxs[i]=new Checkbox(getAddString(i),false);    //�����ð�װ�õķ�����װ
		}
		list.add(DeleteCheckboxs);
		list.add(AddCheckboxs);
		return list;
		
	}
	
	//��ȡ��ɾString
	public String getAddString(int i) {
		return txTchecker.getAdds().get(i);
	}

	public String getDeleteString(int i) {
		return txTchecker.getDeletes().get(i);
	}

	
	public void init() {
		/*
		 * ***************************************���°�װ����� ���һ
		 */
		
		//���ļ�һ�ļ�����װ��verticalBox����
		vBox.add(bt1);
		vBox.add(bt2);
		
		//��verticalBox��װ��HorizontalBox����
		hBox.add(vBox);
		hBox.add(ta);
		
		//��bt3 bt4��װ��vBox2��
		hBox2.add(bt3);
		hBox2.add(bt4);
		
		//���hBox��frame��
		frame.add(hBox);
		frame.add(hBox2,BorderLayout.SOUTH);
		
		 //****************************************���°�װ����� ����
		//����ģʽ�Ի����С
		d1.setBounds(300, 300, 600, 400);
		//����TextArea�����ĵ�
		d1.add(ta2);
		box21.add(tf);
		box21.add(bt5);
		//�������ĵ����룬���ҽ�Box��������southλ��
		d1.add(box21,BorderLayout.SOUTH);
		
		
		d2.setBounds(600, 0, 1200, 800);
        taUp.disable();
        taDown.disable();
		
		//������ɾ���ı���
		box221right.add(taUp);
		
		//createCheckbox �ķ��������в���Ҫ��װ��������,�˴���(100��100)���в���
		for(int i=0;i<createCheckbox(txTchecker.getDeletes().size(),txTchecker.getAdds().size()).get(0).length;i++) {
			Vsp1.add(createCheckbox(txTchecker.getDeletes().size(),txTchecker.getAdds().size()).get(0)[i]);
		}
		//���ּ���Vsp1Box
		sp1.add(Vsp1);
		box221right.add(sp1);
		
		//�����������ı���
		box221right.add(taDown);
		
		//createCheckbox �ķ��������в���Ҫ��װ��������,�˴���(5��5)���в���
		for(int i=0;i<createCheckbox(txTchecker.getDeletes().size(),txTchecker.getAdds().size()).get(1).length;i++) {
			Vsp2.add(createCheckbox(txTchecker.getDeletes().size(),txTchecker.getAdds().size()).get(1)[i]);
		}
		//���ּ���Vsp2Box
		sp2.add(Vsp2);
		box221right.add(sp2);
		box221right.add(start);
		
	    //��װbox221
		box221.add(ta221Left);
		box221.add(box221right);
	    //��box221����Dialog2��
		d2.add(box221);
		d2.add(exit,BorderLayout.SOUTH);
		
		
		
		
		
		
		

		
		
		
		/*
		 * ***************************************���¼��������
		 */
		
		//���ô��ļ�һ�ļ���
		bt1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//��FilaDialog1������Ϊ�ɼ�
				fd1.setVisible(true);
				
				//�˴�directionΪ����·�����ڴ˴�����IO����&&&&&&&&&&&&&&&&&&&&&&&&&
				String direction=fd1.getDirectory();
				//�˴�fileΪ�ļ���
				String file=fd1.getFile();

				txTchecker.setPathSrc(direction + file);
			}
			
		});
		
		//���ô��ļ����ļ���
		bt2.addActionListener(new ActionListener() {
					@Override
				public void actionPerformed(ActionEvent e) {
					//��FilaDialog1������Ϊ�ɼ�
					fd2.setVisible(true);
						
					//�˴�directionΪ����·�����ڴ˴�����IO����&&&&&&&&&&&&&&&&&&&&&&&&&
					String direction=fd2.getDirectory();
					//�˴�fileΪ�ļ���
					String file=fd2.getFile();

					txTchecker.setPathCmp(direction + file);
				}
					
		});
		
		//��ʼ ��ť���Ӽ������޸Ŀ��ӻ�����
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
				//���Ĵ���ʵ�ֲ������˴�����IO����&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
			}
			
		});
		
		//������ť�򿪷�ģʽ�Ի��򣨾��ǿɾ۽���ܣ�
		bt4.addActionListener(new ActionListener() {
			@Override
		public void actionPerformed(ActionEvent e) {
				d1.setVisible(true);
			}
			
		});
		
		//������鷵�ذ�ť�ļ���
		bt5.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				d1.setVisible(false);
			}
		 	
		});
		
		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//���������ɾ�߼�����

			}
			
		});
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {	
				System.exit(0);
			}
			
		});
		
		
		// ���ü����ر�window
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		//��װframeʹframe����
		frame.pack();
		frame.setVisible(true);
	}
	
	public static void main(String[] args) throws IOException {
		new FrameWorkDemo1().init();
	}

}


