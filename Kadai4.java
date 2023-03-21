import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;


class Kadai4 extends JFrame{
  public static void main(String[] args) {
    Kadai4 frame = new Kadai4();

    frame.setSize(340,480);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setTitle("課題管理用");
    frame.setVisible(true);
  }

  private static int heightSize = 16;//いい感じの課題欄の数+1の値
  private int[] yearSort = new int[heightSize];
  private int[] monthSort = new int[heightSize];
  private int[] daySort = new int[heightSize];
  private String[] kadaiSort = new String[heightSize];
  private int additionCount = 0;
  private JTextField year,month,day,schedule,command;
  private JButton addition,save,sort,open,renew,delete;
  private JLabel message = new JLabel("メッセージ表示");
  private JLabel yearLabel = new JLabel("年");
  private JLabel monthLabel = new JLabel("月");
  private JLabel dayLabel = new JLabel("日");
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
        message.setText("メッセージ表示");
        for(int i = 0; i<255;i++){
          clr = new Color(0,0,0,i);
          message.setForeground(clr);
          repaint();
          Thread.sleep(1);
        }
      }catch(InterruptedException run){System.out.println("終了");}
      */
      try{
        Thread.sleep(1000);
        message.setText("メッセージ表示");
      }catch(Exception run){System.out.println("終了");}
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
    schedule = new JTextField("課題",10);
    command = new JTextField("test",10);

    addition = new JButton(" 追加  ");
    sort = new JButton("ソート");
    save = new JButton(" 保存  ");
    open = new JButton(" 開く  ");
    renew = new JButton(" 新規  ");
    delete = new JButton(" 削除  ");

    textArea.setEditable(false);//編集不可
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
      if(e.getSource() == open){//openが押されたら
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
            textArea.setText(textArea.getText() + yearSort[k] + "年" + monthSort[k] + "月" +
            daySort[k] + "日 " + kadaiSort[k] + "\n");
            additionCount++;
          }
        }
        catch(IOException te){message.setText("ファイルが見つかりません");}
        message.setText(commandFile + ".txt を開きました");
      }
      else if(e.getSource() == save){//保存が押されたら
        try(PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("Folder/" + commandFile + ".txt")))){
          for(int i = 0;i < additionCount;i++)
            pw.println(yearSort[i] + "/" + monthSort[i] + "/" +
            daySort[i] + "/" + kadaiSort[i] + "/");
            pw.close();
            message.setText("保存しました");
          }
        catch(IOException ex){
          message.setText(" 書き込むファイルが見つからない");
        }
      }
      else if(e.getSource() == renew){//新規が押されたら
        textArea.setText("");
        additionCount=0;
        message.setText("新規作成");
      }
      else if(e.getSource() == addition){//追加が押されたら
        try{
          if(additionCount<15){//15個目が入ったら抜ける
            yearSort[additionCount] = Integer.parseInt(year.getText());
            monthSort[additionCount] = Integer.parseInt(month.getText());
            daySort[additionCount] = Integer.parseInt(day.getText());
            kadaiSort[additionCount] = schedule.getText();
            if(yearSort[additionCount]>0 && monthSort[additionCount] <= 12 &&  monthSort[additionCount] > 0 &&
            daySort[additionCount] <= 31 && daySort[additionCount] > 0 ){//入力された数値の条件
              textArea.setText(textArea.getText() + year.getText() + "年" + month.getText() +
              "月" + day.getText() + "日 " + schedule.getText() + "\n");
              additionCount++;
              message.setText(additionCount + "/" + (heightSize-1));
            }
            else {
              message.setText("値が違います");


            }
          }
          else message.setText("一杯です");//15個目の値が入って、さらに追加されたら
        }
        catch(NumberFormatException er){
          message.setText("エラー: 年月日欄が空白または数字以外が入っています。");
        }
      }
      else if(e.getSource() == delete){//デリートが押されたら
        int delNum = Integer.parseInt(commandFile);
        int count = 0;
        try{
          if(additionCount != delNum-1 && delNum != 0){
            textArea.setText("");
            for(int k = delNum-1;k < additionCount;k++){//一つ消し、それ以降を上書き
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
            kadaiSort[additionCount]=null;//最後を初期化

            for(int i=0;i<additionCount;i++)
              textArea.setText(textArea.getText() + yearSort[i] + "年" + monthSort[i] + "月" +
              daySort[i] + "日 " + kadaiSort[i] + "\n");
            message.setText(additionCount + "/" + (heightSize-1));
          }
          else message.setText("それは削除できません。");
        }
        catch(NumberFormatException delete_e){
          message.setText("エラー:コマンドに数字を入力してください");
        }
      }
      else{//ソートが押されたら
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
          textArea.setText(textArea.getText() + yearSort[k] + "年" + monthSort[k] + "月" +
          daySort[k] + "日 " + kadaiSort[k] + "\n");
        message.setText("ソートしました");
      }
      //message.setOpaque(true);
      //message.setBackground(backColor);
      mt1 = new Thread(mtt1);
    //  mt2 = new Thread(mtt2);
      //if(count % 2 == 0){
        mt1.start();
      //  System.out.println("スレッド1を実行");
      //}
      //if(count % 2 == 1){
        //mt2.start();
      //  System.out.println("スレッド2を実行");
      //}
      //count++;
    }
  }
}
