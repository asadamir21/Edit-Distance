
package editdistance;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;



public class EditDistance {
    public static Path match(String a, String b){
        Path pth = new Path(b,a);
        return pth; 
    }
    public static void main(String args[]) { 
        JFrame frame1 = new JFrame("Edit Operation"); //frame_name("label")
        frame1.setBounds(500,300,500,300); //(x_crd,y_crd,width,height)
        JMenuBar jmb = new JMenuBar();

        JLabel select = new JLabel("Select Source");  
        select.setBounds(75,25, 200,30);  
        
        JMenu jmFile = new JMenu ("File");
        JMenuItem jmiExit = new JMenuItem ("Exit");
            
        jmFile.add(jmiExit);
        jmb.add(jmFile);

        frame1.add(jmb);
        frame1.setJMenuBar(jmb);
        
        
        final JTextField filepath = new JTextField();  
        filepath.setBounds(150,150, 200,20);  
        
        JRadioButton FileButton   = new JRadioButton("From File", true);
        JRadioButton TextButton    = new JRadioButton("From TextField");
        
        FileButton.setBounds(75,50,100,25);    
        TextButton.setBounds(75,75,150,50);
        
        //... Create a button group and add the buttons.
        ButtonGroup bgroup = new ButtonGroup();
        bgroup.add(FileButton);
        bgroup.add(TextButton);
        
        frame1.add(select);
        frame1.add(FileButton);
        frame1.add(TextButton);
        
        JButton file = new JButton("Choose File");  
        file.setBounds(30,150,100,20);  
        file.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){  
                JFileChooser chooser = new JFileChooser();
                chooser.setCurrentDirectory(new java.io.File("."));
                chooser.setDialogTitle("choosertitle");
                chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                chooser.setAcceptAllFileFilterUsed(false);

                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    File f = chooser.getSelectedFile();
                    filepath.setText(f.getPath());
                }
            } 
        });
        JLabel hor = new JLabel("First String");  
        hor.setBounds(50,200, 200,30);  
        final JTextField rows = new JTextField();  
        rows.setBounds(25,225, 335,20);  
        rows.setEnabled(false);
        rows.setEditable(false);
        
        JLabel vert = new JLabel("Second String");  
        vert.setBounds(40,275, 200,30);
        final JTextField column = new JTextField();  
        column.setBounds(25,300, 335,20);  
        column.setEnabled(false);
        column.setEditable(false);
        
        JButton b = new JButton("Compare Strings");  
        b.setBounds(125,350,150,20);  
        b.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){  
                Path pth = null; 
                boolean flag = false;
                if(file.isEnabled()){
                    if(filepath.getText().equals("")){
                        JFrame parent = new JFrame();
                        JOptionPane.showMessageDialog(parent, "No File Path");
                    }
                    else{
                        String path = filepath.getText();
                        boolean ext = false;

                        String extension = "";
                        int k = path.lastIndexOf('.');
                        int p = Math.max(path.lastIndexOf('/'), path.lastIndexOf('\\'));

                        if (k > p) {
                            extension = path.substring(k+1);
                        }

                        if(extension.equalsIgnoreCase("TXT")){
                            ext = true;
                        }
                        else{
                            JFrame parent = new JFrame();
                            JOptionPane.showMessageDialog(parent, "Only txt File Allowed");
                        }

                        if(ext){
                            File file = new File(path);
                            FileReader fr = null;
                            
                            LinkedList<String> list = new LinkedList<String>();
                            
                            try {
                                fr = new FileReader(file);
                            } catch (FileNotFoundException ex) {
                                Logger.getLogger(EditDistance.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            BufferedReader br = new BufferedReader(fr);
                            String line;
                            try {
                                while((line = br.readLine()) != null){
                                    list.add(line);
                                }
                            } catch (IOException ex) {
                                Logger.getLogger(EditDistance.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            
                            if(list.size() == 2){
                                pth = match(list.get(0), list.get(1));
                                flag = true;
                            }
                            else if(list.size() == 1){
                                pth = match(list.get(0), "");
                                flag = true;
                            }
                            else if(list.size() == 0){
                                JFrame parent = new JFrame();
                                JOptionPane.showMessageDialog(parent, "No String Found");
                            }
                            else{
                                JFrame parent = new JFrame();
                                JOptionPane.showMessageDialog(parent, "More Than Two String are Present");
                            }
                        }
                    }
                }
                else if(rows.isEnabled()){
                    if(rows.getText().equals("")){
                        JFrame parent = new JFrame();
                        JOptionPane.showMessageDialog(parent, "First String Cannot be Empty");
                    }
                    else{
                        String str1 = rows.getText();
                        String str2 = column.getText();
                        pth = match(str1, str2);
                        flag = true;
                    }
                }
                
                if(flag){
                    JFrame frame2 = new JFrame("Edit Operation Details"); //frame_name("label")
                    frame2.setBounds(300,300,500,300); //(x_crd,y_crd,width,height)        
                    JMenuBar jmb2 = new JMenuBar();

                    JMenu jmFile2 = new JMenu ("File");
                    JMenuItem jmiExit2 = new JMenuItem ("Exit");
                    jmFile2.add(jmiExit2);
                    jmb2.add(jmFile2);
                    frame2.add(jmb2);
                    frame2.setJMenuBar(jmb2);
                    jmiExit2.addActionListener(new ActionListener(){ //Exit
                        public void actionPerformed (ActionEvent ae){
                            frame2.dispose();
                        }
                    });

                    JLabel distance = new JLabel("Distance : ");  
                    distance.setBounds(50,40, 200,30);  
                    final JTextField distfield = new JTextField();  
                    distfield.setBounds(200,45, 150,20);  
                    distfield.setEditable(false);
                    distfield.setText(Integer.toString(pth.distance));

                    JLabel seq = new JLabel("Sequence : ");  
                    seq.setBounds(50,100, 200,30);
                    JTextArea seqArea =new JTextArea();  
                    seqArea.setBounds(200,105, 550,75);  
                    seqArea.setEditable(false);
                    seqArea.setText(pth.editSequence);

                    JLabel Str1A = new JLabel("First String Allignment: ");  
                    Str1A.setBounds(50,200, 200,30);
                    JTextArea FArea =new JTextArea();  
                    FArea.setBounds(200,205, 550,75);  
                    FArea.setEditable(false);
                    FArea.setText(pth.bottomAlignmentRow);

                    JLabel Str2A = new JLabel("Second String Allignment: ");  
                    Str2A.setBounds(50,300, 200,30);
                    JTextArea SArea =new JTextArea();  
                    SArea.setBounds(200,305, 550,75);  
                    SArea.setEditable(false);
                    SArea.setText(pth.topAlignmentRow);

                    final JTextField r = new JTextField();  
                    r.setBounds(1000,1000, 150,20);  
                    r.setEditable(false);

                    String S = "\n      -  -  ";
                    for (int i = 0; i < pth.str1.length(); i++) {
                        S = S + pth.str1.charAt(i) + "  ";
                    }
                    S = S + "\n";

                    for (int i = 1; i < pth.arrange.length; i++) {
                        if(i != 1){
                            S = S + "      " + pth.str2.charAt(i - 2) + "  ";
                        }
                        else{
                            S = S + "      -  ";
                        }
                        for (int j = 1; j < pth.arrange[i].length; j++) {
                            S = S + pth.arrange[i][j] + "  ";
                        }
                        S = S + "\n";
                    }
                    final String SX = S;
                    
                    JButton see = new JButton("Show Cost Matrix");  
                    see.setBounds(300,400,200,20);  
                    see.addActionListener(new ActionListener(){  
                        public void actionPerformed(ActionEvent e){  
                            JFrame frame3 = new JFrame("Cost Matrix"); //frame_name("label")
                            frame3.setBounds(500,500,500,400); //(x_crd,y_crd,width,height)        
                            JMenuBar jmb3 = new JMenuBar();

                            JMenu jmFile3 = new JMenu ("File");
                            JMenuItem jmiExit3 = new JMenuItem ("Exit");
                            jmFile3.add(jmiExit3);
                            jmb3.add(jmFile3);
                            frame3.add(jmb3);
                            frame3.setJMenuBar(jmb3);
                            jmiExit3.addActionListener(new ActionListener(){ //Exit
                                public void actionPerformed (ActionEvent ae){
                                    frame3.dispose();
                                }
                            });
                            
                            JPanel contentPane = new JPanel();
                            contentPane.setSize(200, 200);
                            contentPane.setBorder(new EmptyBorder(20, 10, 10, 10));
                            frame3.setContentPane(contentPane);
                            
                            JScrollPane scrollPane = new JScrollPane();
                            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                            contentPane.add(scrollPane, BorderLayout.CENTER);

                            JScrollPane scrollPane2 = new JScrollPane();
                            scrollPane2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                            contentPane.add(scrollPane2, BorderLayout.CENTER);
                            
                            JTextArea textArea = new JTextArea(10, 20);
                            textArea.setText(SX);
                            scrollPane.setViewportView(textArea);
                            
                            frame3.pack();
                            
                           
                            frame3.add(r);
                            frame3.setSize(300, 300);
                            frame3.setVisible(true);
                        }
                    });
                        
                    frame2.add(distfield);
                    frame2.add(seq);
                    frame2.add(seqArea);
                    frame2.add(Str1A);
                    frame2.add(Str2A);
                    frame2.add(FArea);
                    frame2.add(SArea);
                    frame2.add(distance);
                    frame2.add(see);
                    frame2.add(r);

                    frame2.setSize(800, 500);
                    frame1.setLayout(null);  
                    frame2.setVisible(true);
                }
            }  
        });  
        
        FileButton.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){ 
                if(FileButton.isSelected()){
                    file.setEnabled(true);
                    filepath.setEnabled(true);
                    rows.setEnabled(false);
                    rows.setEditable(false);
                    column.setEnabled(false);
                    column.setEditable(false);
                }
                else{
                    file.setEnabled(false);
                    filepath.setEnabled(false);
                    rows.setEnabled(true);
                    rows.setEditable(true);
                    column.setEnabled(true);
                    column.setEditable(true);
                }
            }
        });
        
        TextButton.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){ 
                if(FileButton.isSelected()){
                    file.setEnabled(true);
                    filepath.setEnabled(true);
                    rows.setEnabled(false);
                    rows.setEditable(false);
                    column.setEnabled(false);
                    column.setEditable(false);
                }
                else{
                    file.setEnabled(false);
                    filepath.setEnabled(false);
                    rows.setEnabled(true);
                    rows.setEditable(true);
                    column.setEnabled(true);
                    column.setEditable(true);
                }
            }
        });
        
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.add(file);
        frame1.add(filepath);
        frame1.add(hor);
        frame1.add(vert);
        frame1.add(rows);  
        frame1.add(column);  
        frame1.add(b);
        frame1.setSize(400, 500);  
        frame1.setLayout(null);  
         
        jmiExit.addActionListener(new ActionListener(){ //Exit
            public void actionPerformed (ActionEvent ae){
               System.exit(0);
            }
        });
        frame1.setVisible(true); 
    }

    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }   
}
