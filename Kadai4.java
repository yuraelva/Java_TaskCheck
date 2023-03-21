import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;


class Kadai4 extends JFrame{
  public static void main(String[] args) {
    Kadai4 frame = new Kadai4();

    frame.setSize(340,480);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setTitle("�ۑ�Ǘ��p");
    frame.setVisible(true);
  }

  private static int heightSize = 16;//���������̉ۑ藓�̐�+1�̒l
  private int[] yearSort = new int[heightSize];
  private int[] monthSort = new int[heightSize];
  private int[] daySort = new int[heightSize];
  private String[] kadaiSort = new String[heightSize];
  private int additionCount = 0;
  private JTextField year,month,day,schedule,command;
  private JButton addition,save,sort,open,renew,delete;
  private JLabel message = new JLabel("���b�Z�[�W�\��");
  private JLabel yearLabel = new JLabel("�N");
  private JLabel monthLabel = new JLabel("��");
  private JLabel dayLabel = new JLabel("��");
  private JTextArea textArea = new JTextArea(20,20);
  MessageThread mtt1 = new MessageThread();
  Thread mt1 = new Thread(mtt1);
  int count = 0;

  public class MessageThread extends JComponent implements Runnable {
    Color clr = new Color(0,0,0,255);
    public void run(){
    /*  try{
        Thread.sleep(1000);
        for(int i = 255; i>0;i--){
          clr = new Color(0,0,0,i);
          message.setForeground(clr);
          repaint();
          Thread.sleep(5);
        }
        message.setText("���b�Z�[�W�\��");
        for(int i = 0; i<255;i++){
          clr = new Color(0,0,0,i);
          message.setForeground(clr);
          repaint();
          Thread.sleep(1);
        }
      }catch(InterruptedException run){System.out.println("�I��");}
      */
      try{
        Thread.sleep(1000);
        message.setText("���b�Z�[�W�\��");
      }catch(Exception run){System.out.println("�I��");}
    }
    public void paintComponent(Graphics g){
      g.setColor(clr);
    }
  }

  public Kadai4(){
    EventListener el = new EventListener();

    year = new JTextField("2022",4);
    month = new JTextField("1",2);
    day = new JTextField("1",2);
    schedule = new JTextField("�ۑ�",10);
    command = new JTextField("test",10);

    addition = new JButton(" �ǉ�  ");
    sort = new JButton("�\�[�g");
    save = new JButton(" �ۑ�  ");
    open = new JButton(" �J��  ");
    renew = new JButton(" �V�K  ");
    delete = new JButton(" �폜  ");

    textArea.setEditable(false);//�ҏW�s��
    command.setAlignmentX(0.5f);
    addition.setAlignmentX(0.5f);
    sort.setAlignmentX(0.5f);
    save.setAlignmentX(0.5f);
    open.setAlignmentX(0.5f);
    renew.setAlignmentX(0.5f);
    delete.setAlignmentX(0.5f);

    JPanel panelInput = new JPanel();
    JPanel paneltextArea = new JPanel();
    JPanel panelNorth = new JPanel();
    JPanel panelCenter = new JPanel();
    JPanel panelWest = new JPanel();
    JPanel panelSouth = new JPanel();

    panelInput.setLayout(new FlowLayout());
    panelInput.add(year);
    panelInput.add(yearLabel);
    panelInput.add(month);
    panelInput.add(monthLabel);
    panelInput.add(day);
    panelInput.add(dayLabel);
    panelInput.add(schedule);

    panelNorth.setLayout(new FlowLayout());
    panelNorth.add(panelInput);

    panelCenter.setLayout(new FlowLayout());
    panelCenter.add(textArea);

    panelWest.setLayout(new BoxLayout(panelWest,BoxLayout.PAGE_AXIS));
    panelWest.add(command);
    panelWest.add(renew);
    panelWest.add(open);
    panelWest.add(addition);
    panelWest.add(sort);
    panelWest.add(save);
    panelWest.add(delete);
    command.setMaximumSize(new Dimension(60,20));

    panelSouth.setLayout(new FlowLayout());
    panelSouth.add(message);

    getContentPane().setLayout(new BorderLayout());
    getContentPane().add(panelNorth, BorderLayout.NORTH);
    getContentPane().add(panelCenter, BorderLayout.CENTER);
    getContentPane().add(panelWest, BorderLayout.WEST);
    getContentPane().add(panelSouth, BorderLayout.SOUTH);

    save.addActionListener(el);
    sort.addActionListener(el);
    addition.addActionListener(el);
    open.addActionListener(el);
    renew.addActionListener(el);
    delete.addActionListener(el);
  }

  public class EventListener implements ActionListener{
    public void actionPerformed(ActionEvent e){
      String commandFile = command.getText();
      if(e.getSource() == open){//open�������ꂽ��
        String line = null;
        String[] script = new String[300];
        int sortCount = 0;
        textArea.setText("");
        try{
          BufferedReader br = new BufferedReader(new FileReader("Folder/" + commandFile+".txt"));
          while((line=br.readLine()) != null){
            script = line.split("/");
            for(int i = 0;i < script.length;i++){
              if(i % 4 == 0)yearSort[sortCount] = Integer.parseInt(script[i]);
              if(i % 4 == 1)monthSort[sortCount] = Integer.parseInt(script[i]);
              if(i % 4 == 2)daySort[sortCount] = Integer.parseInt(script[i]);
              if(i % 4 == 3)kadaiSort[sortCount] = script[i];
            }
            sortCount++;
          }
          for(int k = 0;k < sortCount;k++){
            textArea.setText(textArea.getText() + yearSort[k] + "�N" + monthSort[k] + "��" +
            daySort[k] + "�� " + kadaiSort[k] + "\n");
            additionCount++;
          }
        }
        catch(IOException te){message.setText("�t�@�C����������܂���");}
        message.setText(commandFile + ".txt ���J���܂���");
      }
      else if(e.getSource() == save){//�ۑ��������ꂽ��
        try(PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("Folder/" + commandFile + ".txt")))){
          for(int i = 0;i < additionCount;i++)
            pw.println(yearSort[i] + "/" + monthSort[i] + "/" +
            daySort[i] + "/" + kadaiSort[i] + "/");
            pw.close();
            message.setText("�ۑ����܂���");
          }
        catch(IOException ex){
          message.setText(" �������ރt�@�C����������Ȃ�");
        }
      }
      else if(e.getSource() == renew){//�V�K�������ꂽ��
        textArea.setText("");
        additionCount=0;
        message.setText("�V�K�쐬");
      }
      else if(e.getSource() == addition){//�ǉ��������ꂽ��
        try{
          if(additionCount<15){//15�ڂ��������甲����
            yearSort[additionCount] = Integer.parseInt(year.getText());
            monthSort[additionCount] = Integer.parseInt(month.getText());
            daySort[additionCount] = Integer.parseInt(day.getText());
            kadaiSort[additionCount] = schedule.getText();
            if(yearSort[additionCount]>0 && monthSort[additionCount] <= 12 &&  monthSort[additionCount] > 0 &&
            daySort[additionCount] <= 31 && daySort[additionCount] > 0 ){//���͂��ꂽ���l�̏���
              textArea.setText(textArea.getText() + year.getText() + "�N" + month.getText() +
              "��" + day.getText() + "�� " + schedule.getText() + "\n");
              additionCount++;
              message.setText(additionCount + "/" + (heightSize-1));
            }
            else {
              message.setText("�l���Ⴂ�܂�");


            }
          }
          else message.setText("��t�ł�");//15�ڂ̒l�������āA����ɒǉ����ꂽ��
        }
        catch(NumberFormatException er){
          message.setText("�G���[: �N���������󔒂܂��͐����ȊO�������Ă��܂��B");
        }
      }
      else if(e.getSource() == delete){//�f���[�g�������ꂽ��
        int delNum = Integer.parseInt(commandFile);
        int count = 0;
        try{
          if(additionCount != delNum-1 && delNum != 0){
            textArea.setText("");
            for(int k = delNum-1;k < additionCount;k++){//������A����ȍ~���㏑��
              yearSort[delNum+count-1]=yearSort[delNum+count];
              monthSort[delNum+count-1]=monthSort[delNum+count];
              daySort[delNum+count-1]=daySort[delNum+count];
              kadaiSort[delNum+count-1]=kadaiSort[delNum+count];
              count++;
            }
            additionCount--;
            yearSort[additionCount]=0;
            monthSort[additionCount]=0;
            daySort[additionCount]=0;
            kadaiSort[additionCount]=null;//�Ō��������

            for(int i=0;i<additionCount;i++)
              textArea.setText(textArea.getText() + yearSort[i] + "�N" + monthSort[i] + "��" +
              daySort[i] + "�� " + kadaiSort[i] + "\n");
            message.setText(additionCount + "/" + (heightSize-1));
          }
          else message.setText("����͍폜�ł��܂���B");
        }
        catch(NumberFormatException delete_e){
          message.setText("�G���[:�R�}���h�ɐ�������͂��Ă�������");
        }
      }
      else{//�\�[�g�������ꂽ��
        textArea.setText("");
        int tmpY;
        int tmpM;
        int tmpD;
        String tmpS;

        for(int i = 0; i < heightSize - (heightSize - additionCount); i++){
          for(int j = heightSize - (heightSize-additionCount) - 1; j > i; j--){
    	      if(yearSort[j] < yearSort[j-1]){
              tmpY = yearSort[j];
    	        tmpM = monthSort[j];
              tmpD = daySort[j];
              tmpS = kadaiSort[j];

              yearSort[j] = yearSort[j-1];
    	        monthSort[j] = monthSort[j-1];
              daySort[j] = daySort[j-1];
              kadaiSort[j] = kadaiSort[j-1];

              yearSort[j-1] = tmpY;
    	        monthSort[j-1] = tmpM;
              daySort[j-1] = tmpD;
              kadaiSort[j-1] = tmpS;
    	      }
          }
        }
        for(int i = 0; i < heightSize - (heightSize - additionCount); i++){
          for(int j = heightSize - (heightSize-additionCount) - 1; j > i; j--){
    	      if(yearSort[j] == yearSort[j-1] && monthSort[j] < monthSort[j-1]){
    	        tmpM = monthSort[j];
              tmpD = daySort[j];
              tmpS = kadaiSort[j];

    	        monthSort[j] = monthSort[j-1];
              daySort[j] = daySort[j-1];
              kadaiSort[j] = kadaiSort[j-1];

    	        monthSort[j-1] = tmpM;
              daySort[j-1] = tmpD;
              kadaiSort[j-1] = tmpS;
    	      }
          }
        }
        for(int i = 0; i < heightSize - (heightSize - additionCount); i++){
          for(int j = heightSize - (heightSize-additionCount) - 1; j > i; j--){
    	      if(yearSort[j] == yearSort[j-1] && monthSort[j] == monthSort[j-1] && daySort[j] < daySort[j-1]){
              tmpD = daySort[j];
              tmpS = kadaiSort[j];

              daySort[j] = daySort[j-1];
              kadaiSort[j] = kadaiSort[j-1];

              daySort[j-1] = tmpD;
              kadaiSort[j-1] = tmpS;
    	      }
          }
        }
        for(int k = 0; k < additionCount; k++)
          textArea.setText(textArea.getText() + yearSort[k] + "�N" + monthSort[k] + "��" +
          daySort[k] + "�� " + kadaiSort[k] + "\n");
        message.setText("�\�[�g���܂���");
      }
      //message.setOpaque(true);
      //message.setBackground(backColor);
      mt1 = new Thread(mtt1);
    //  mt2 = new Thread(mtt2);
      //if(count % 2 == 0){
        mt1.start();
      //  System.out.println("�X���b�h1�����s");
      //}
      //if(count % 2 == 1){
        //mt2.start();
      //  System.out.println("�X���b�h2�����s");
      //}
      //count++;
    }
  }
}
