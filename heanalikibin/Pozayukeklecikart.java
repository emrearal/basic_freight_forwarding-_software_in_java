package heanalikibin;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

public class Pozayukeklecikart implements ActionListener  {
	
	static JFrame frame;
		static JButton yukekle;
	static JTable konttablosu;
	static String[][] yuklenmisyuktablodata,adayyuklertablodata ;
	static String secilenadayyuk,secilenyuklenmisyuk,amahangipoza;
	static boolean yukeklecikarbayrak;
	
	
	static JPanel panelalanlar;
		
	
	public static void pozayukeklecikart () {
		
		yukeklecikarbayrak=true;
		secilenadayyuk=secilenyuklenmisyuk=amahangipoza="";
	     
		 
		 frame= new JFrame("POZÝSYONA YÜK EKLEME ÇIKARTMA VE EKLÝ YÜK GÖRÜNTÜLEME ÝÞLEMLERÝ");
		 frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		 frame.setLocation(50,10);
		 frame.setSize(750,475);
		 
		 frame.addWindowListener(new WindowAdapter()
	        {
	            @Override
	            public void windowClosing(WindowEvent e)
	            {
	            	yukeklecikarbayrak=false;
	                e.getWindow().dispose();
	            }
	        });
	
		 panelalanlar=new JPanel();
		 
		 yukekle=new JButton("Seçilen Yükü Pozisyona Ekle veya Çýkart");
		 yukekle.setBounds(20, 10, 100, 20);
		 yukekle.addActionListener(new Pozayukeklecikart());
		 panelalanlar.add(yukekle);
			
		 yuklenmisyuktablodata = new String [0][0];
		 yuklenmisyukdizinihazirla (Pozisyon.poznojt.getText());
		 adayyuklertablodata = new String [0][0];
		 adayyukdizinihazirla ();
		 
		 
		 
	     Yuklenmisyuklertablosu.tablemodeltablo();
	     Adayyuklertablosu.tablemodeltablo();
	     frame.add(panelalanlar, BorderLayout.NORTH);
	   
		 frame.setVisible(true);
		
	}   //  metodu sonu

	@Override
	public void actionPerformed(ActionEvent e) {
		 dugmeyukeklecikart();
		 		
		} 
	
	private static boolean buyukunaltindafaturavarmi(int yukno) {
		
		int kacfatura=0;
		
		try{                                              
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection(
					"jdbc:mysql://"+Anasinif.sqlip+"/nal2000?useUnicode=true&characterEncoding=UTF-8",Anasinif.sqluser,Anasinif.sqlpass);
					
					Statement stmt=con.createStatement();  
			        ResultSet rs=stmt.executeQuery("select * from fiskayitlari where yukno="+yukno); 
			
			while(rs.next()) {
				kacfatura++;
						}
			con.close();  
			
			}catch(Exception e){ System.out.println(e);}
		
		if (kacfatura>0) {
			return true;
		} else {
			return false;
		}
		
	}
	

	
	static public void dugmeyukeklecikart() {
		String komut1="Update yukler SET pozaalindi=1 , yuklendigipoz="+amahangipoza+" where yukno="+secilenadayyuk;
	    String komut2="Update yukler SET pozaalindi=0 , yuklendigipoz=0 where yukno="+secilenyuklenmisyuk;
	    
	   
	
		if (!secilenadayyuk.equals("")) {  // aday yuk iþaretlendiyse demek ki yüke alacaðýz
			
			 if (Pozisyon.duzenlenecekpoz[1].equals("1") ) {
			        String yenikontno="01"+Pozisyon.duzenlenecekpoz[2]+Pozisyon.duzenlenecekpoz[3]+yuknoverbenhamvereyim(secilenadayyuk).substring(12);
			         komut1="Update yukler SET pozaalindi=1 ,konteynerbilgileri='"+yenikontno+"' , yuklendigipoz="+amahangipoza+" where yukno="+secilenadayyuk;
			       
			    }
			
			try{                                             
				Class.forName("com.mysql.cj.jdbc.Driver");  
				Connection con=DriverManager.getConnection(  
		"jdbc:mysql://"+Anasinif.sqlip+"/nal2000?useUnicode=true&characterEncoding=UTF-8",Anasinif.sqluser,Anasinif.sqlpass);	
				Statement stmt=con.createStatement();  
		
				stmt.executeUpdate(komut1); 
				con.close();  
				
				}catch(Exception e){ System.out.println(e);} 
			
			Pozisyon.istipicb.setEnabled(false);
	    	Pozisyon.parsiyel.setEnabled(false);
	    	Pozisyon.suffixjt.setEditable(false);
	    	Pozisyon.prefixjt.setEditable(false);
	    	
		}
		
		if (!secilenyuklenmisyuk.equals("")) {  // yuklu yuk iþaretlendiyse yükten çýkartacaðýz
			
			
			 if (buyukunaltindafaturavarmi (Integer.parseInt(secilenyuklenmisyuk))) {
				 Bilgipenceresi.anons("Faturasý bulunan yük pozisyondan çýkartýlamaz");
				 return;
			 }
			
			 if (Pozisyon.duzenlenecekpoz[1].equals("1") ) {
			         String eskikontno="01RZVN000000"+yuknoverbenhamvereyim(secilenyuklenmisyuk).substring(12);
			        komut2="Update yukler SET pozaalindi=0 ,konteynerbilgileri='"+eskikontno+"' , yuklendigipoz=0 where yukno="+secilenyuklenmisyuk;
			    }
			 
			try{                                             
				Class.forName("com.mysql.cj.jdbc.Driver");  
				Connection con=DriverManager.getConnection(  
		"jdbc:mysql://"+Anasinif.sqlip+"/nal2000?useUnicode=true&characterEncoding=UTF-8",Anasinif.sqluser,Anasinif.sqlpass);	
				Statement stmt=con.createStatement();  
		
				stmt.executeUpdate(komut2); 
				con.close();  
				
				}catch(Exception e){ System.out.println(e);}  
			
		}
		
		yuklenmisyukdizinihazirla (Pozisyon.poznojt.getText());
		adayyukdizinihazirla ();
		Pozisyon.pozdakikonteynerleribul(Pozisyon.poznojt.getText());
		Pozisyon.Pozdakikonteynertablosu.gostermeligidegistir();
				
		if(yuklenmisyuktablodata.length==0 ) {
			Pozisyon.istipicb.setEnabled(true);
	    	Pozisyon.parsiyel.setEnabled(true);
	    	Pozisyon.suffixjt.setEditable(true);
	    	Pozisyon.prefixjt.setEditable(true);
			
		}

		if(yuklenmisyuktablodata.length==0 | adayyuklertablodata.length==0) {
			
				Pozisyon.frame.dispose();
				try {
					Pozisyon.pozisyon("düzenle");
				} catch (ParseException e) {e.printStackTrace();}
				
			yukeklecikarbayrak=false;
			frame.dispose();
			
			return;
		}else {
			Adayyuklertablosu.tableModel.fireTableDataChanged();
			Yuklenmisyuklertablosu.tableModel.fireTableDataChanged();
			Pozisyon.Pozdakikonteynertablosu.tableModel.fireTableDataChanged();	
			String neleryuklenmis="                *POZÝSYONA EKLÝ YÜKLER*\r\n\r\n",no,gonderici,alici,kap,kg;
			 
			 for (int p=0; p<Pozayukeklecikart.yuklenmisyuktablodata.length; p++) {
		        	no=Pozayukeklecikart.yuklenmisyuktablodata[p][0];
		        	gonderici=Pozayukeklecikart.yuklenmisyuktablodata[p][1];
		        	if (gonderici.length()>(40)){
		        		gonderici=gonderici.substring(0,39)+"...";
		        	}
		        	alici =Pozayukeklecikart.yuklenmisyuktablodata[p][2];
		        	if (alici.length()>(40)){
		        		alici=alici.substring(0,39)+"...";
		        	}
		        	kap=Pozayukeklecikart.yuklenmisyuktablodata[p][3];
		        	kg=Pozayukeklecikart.yuklenmisyuktablodata[p][4];
		        	
		        	neleryuklenmis=neleryuklenmis+no+"- "+gonderici+"  /  "+alici+"    "+kap+" kap, "+kg+" kg \r\n \r\n";
		             }
		       Pozisyon. area.setText(neleryuklenmis);  
		       Pozisyon.frame.revalidate();
			}
		
	}

	public static void yuklenmisyukdizinihazirla (String amahangipoza) {
		
		String komutseti="",komutsetiek="",kapsayisi="",kgmiktari="";
		int toplamkap=0,toplamkg=0;
		
    	komutseti="SELECT * FROM yukler where ";
    	komutsetiek="pozaalindi=1 and yuklendigipoz='"+amahangipoza+"';";
		
		int c=0;
		try{                                              
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection(
					"jdbc:mysql://"+Anasinif.sqlip+"/nal2000?useUnicode=true&characterEncoding=UTF-8",Anasinif.sqluser,Anasinif.sqlpass);
					
					Statement stmt=con.createStatement();  
			        ResultSet rs=stmt.executeQuery(komutseti+komutsetiek); 
			
			while(rs.next()) {
				 
				c++;
						}
			con.close();  
			
			}catch(Exception e){ System.out.println(e);}
		
		yuklenmisyuktablodata = new String [c][6];
		
	      int y=0;
			
			try{                                              
				Class.forName("com.mysql.cj.jdbc.Driver");  
				Connection con=DriverManager.getConnection(
						"jdbc:mysql://"+Anasinif.sqlip+"/nal2000?useUnicode=true&characterEncoding=UTF-8",Anasinif.sqluser,Anasinif.sqlpass);
				
						Statement stmt=con.createStatement();  
				   		ResultSet rs=stmt.executeQuery(komutseti+komutsetiek); 
				
				while(rs.next()) {
					
					yuklenmisyuktablodata[y][0]=rs.getString(1);
					yuklenmisyuktablodata[y][1]=Ontanimliveriler.sirketkoducoz(Integer.parseInt(rs.getString(8)))[1];
					yuklenmisyuktablodata[y][2]=Ontanimliveriler.sirketkoducoz(Integer.parseInt(rs.getString(9)))[1];
					yuklenmisyuktablodata[y][3]=rs.getString(25);
						y++;	
					}
				con.close();  
				
				}catch(Exception e){ System.out.println(e);}

			for (int i=0 ;i<c; i++) {
			
			int konthamveriuzunlugu=yuklenmisyuktablodata[i][3].length();
			String kontsayisi=yuklenmisyuktablodata[i][3].substring(konthamveriuzunlugu-24, konthamveriuzunlugu-22);
				
			
			for (int g=1 ; g<Integer.parseInt(kontsayisi)+1; g++) {
				
				
				kapsayisi=yuklenmisyuktablodata[i][3].substring(((g*24)-12),((g*24))-6);  //Kont kap
				toplamkap=toplamkap+Integer.parseInt(kapsayisi.trim()); 
				kgmiktari=yuklenmisyuktablodata[i][3].substring(((g*24)-6),(g*24)-1); //kont kg
				toplamkg=toplamkg+Integer.parseInt(kgmiktari.trim()); 
				
			}
			yuklenmisyuktablodata[i][3]=String.valueOf(toplamkap); // kont sayisi
			yuklenmisyuktablodata[i][4]=String.valueOf(toplamkg); // kont sayisi
			yuklenmisyuktablodata[i][5]=kontsayisi; // kont sayisi
			
			toplamkg=0;toplamkap=0;
			
			
		        
		       
		}
			
	}
	
private static void adayyukdizinihazirla () {
		
		String komutseti="",komutsetiek="",kapsayisi="",kgmiktari="";
		int toplamkap=0,toplamkg=0;
		int komplemi=0;
		if (Pozisyon.parsiyel.isSelected()) {
			komplemi=0;
		}else {
			komplemi=1;
		}
		
    	komutseti="SELECT * FROM yukler where ";
    	komutsetiek="pozaalindi=0 and komple="+komplemi+" and istipi="+Pozisyon.istipicb.getSelectedIndex();	
    	
		int c=0;
		try{                                              
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection(
					"jdbc:mysql://"+Anasinif.sqlip+"/nal2000?useUnicode=true&characterEncoding=UTF-8",Anasinif.sqluser,Anasinif.sqlpass);
					
					Statement stmt=con.createStatement();  
			        ResultSet rs=stmt.executeQuery(komutseti+komutsetiek); 
			       
			
			while(rs.next()) {
				 
				c++;
						}
			con.close();  
			
			}catch(Exception e){ System.out.println(e);}
		
		adayyuklertablodata = new String [c][6];
		
	      int y=0;
			
			try{                                              
				Class.forName("com.mysql.cj.jdbc.Driver");  
				Connection con=DriverManager.getConnection(
						"jdbc:mysql://"+Anasinif.sqlip+"/nal2000?useUnicode=true&characterEncoding=UTF-8",Anasinif.sqluser,Anasinif.sqlpass);
				
						Statement stmt=con.createStatement();  
				   		ResultSet rs=stmt.executeQuery(komutseti+komutsetiek); 
				
				while(rs.next()) {
					
					adayyuklertablodata[y][0]=rs.getString(1);
					adayyuklertablodata[y][1]=Ontanimliveriler.sirketkoducoz(Integer.parseInt(rs.getString(8)))[1];
					adayyuklertablodata[y][2]=Ontanimliveriler.sirketkoducoz(Integer.parseInt(rs.getString(9)))[1];
					adayyuklertablodata[y][3]=rs.getString(25);
					
						y++;	
					}
				con.close();  
				
				}catch(Exception e){ System.out.println(e);}

			for (int i=0 ;i<c; i++) {
			
			int konthamveriuzunlugu=adayyuklertablodata[i][3].length();
			String kontsayisi=adayyuklertablodata[i][3].substring(konthamveriuzunlugu-24, konthamveriuzunlugu-22);
				
			
			for (int g=1 ; g<Integer.parseInt(kontsayisi)+1; g++) {
				
				
				kapsayisi=adayyuklertablodata[i][3].substring(((g*24)-12),((g*24))-6);  //Kont kap
				toplamkap=toplamkap+Integer.parseInt(kapsayisi.trim()); 
				kgmiktari=adayyuklertablodata[i][3].substring(((g*24)-6),(g*24)-1); //kont kg
				toplamkg=toplamkg+Integer.parseInt(kgmiktari.trim()); 
				
			}
			adayyuklertablodata[i][3]=String.valueOf(toplamkap); // kont sayisi
			adayyuklertablodata[i][4]=String.valueOf(toplamkg); // kont sayisi
			adayyuklertablodata[i][5]=kontsayisi; // kont sayisi
			
			toplamkg=0;toplamkap=0;
		}
			
	}

private static String yuknoverbenhamvereyim (String yuknonedir) {
	String alsanahamkontverisi="";
	try{                                              
		Class.forName("com.mysql.cj.jdbc.Driver");  
		Connection con=DriverManager.getConnection(
				"jdbc:mysql://"+Anasinif.sqlip+"/nal2000?useUnicode=true&characterEncoding=UTF-8",Anasinif.sqluser,Anasinif.sqlpass);
		
				Statement stmt=con.createStatement();  
		   		ResultSet rs=stmt.executeQuery("select konteynerbilgileri from yukler where yukno='"+yuknonedir+"';"); 
		
		while(rs.next()) {
			alsanahamkontverisi=rs.getString(1);	
			}
		con.close();  
		
		}catch(Exception e){ System.out.println(e);}
	
	return alsanahamkontverisi;
	
}

@SuppressWarnings("serial")
public static class Adayyuklertablosu extends JPanel {
	static Tablomodeli tableModel;
	static JScrollPane scrollPane;
	static  JTable table;

	public Adayyuklertablosu() {
        initializePanel();
    }

    private void initializePanel() {
       
       tableModel = new Tablomodeli();

        table = new JTable(tableModel);
        
        table.setFillsViewportHeight(true);
        table.setPreferredScrollableViewportSize(new Dimension(500,100));
		   
	    table.getColumnModel().getColumn(0).setPreferredWidth(60);
	    table.getColumnModel().getColumn(1).setPreferredWidth(200);
	    table.getColumnModel().getColumn(2).setPreferredWidth(200);
	    table.getColumnModel().getColumn(3).setPreferredWidth(55);
	    table.getColumnModel().getColumn(4).setPreferredWidth(50);
	    table.getColumnModel().getColumn(5).setPreferredWidth(35);
	    
	    table.setFocusable(false);
	      table.addMouseListener(new MouseAdapter() {   // mouse listener kýsmýsý
	         public void mouseClicked(MouseEvent me) {
	        	
	            if (me.getClickCount() == 2) {     
	            	Pozayukeklecikart.dugmeyukeklecikart();
	            	
	            }
	         }
	      });
	    
	    
	    ListSelectionModel cellSelectionModel = table.getSelectionModel();               // listeden seçileni dinleme kýsmý
        cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
          public void valueChanged(ListSelectionEvent e) {
        	  
        	  if(!e.getValueIsAdjusting())  {    						// bu IF iki defa yazmamasý için konuldu. 
      
        		 int selectedRow     = table.getSelectedRow();
        		 Yuklenmisyuklertablosu.table.getSelectionModel().clearSelection();
        		 Pozayukeklecikart.secilenyuklenmisyuk="";
          	     Pozayukeklecikart.secilenadayyuk = (String) table.getValueAt(selectedRow,0 );
          		
        	  }
          }
     });
	    
	    scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(400,200));

        this.setLayout(new BorderLayout());
        this.add(scrollPane, BorderLayout.CENTER);
    }

    public static void showFrame() {
        JPanel panel = new Adayyuklertablosu();
        panel.setOpaque(true);

        Pozayukeklecikart.frame.add(scrollPane,BorderLayout.SOUTH); 
    }

    class Tablomodeli extends AbstractTableModel {
     
        private  String[] columnNames = { "ADAY YÜK#", "Gönderici","Alýcý","Kap","Kg","K.Adet" };

        public int getRowCount() {
           
        	return Pozayukeklecikart.adayyuklertablodata.length;
        }

        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
        
            return getValueAt(0, columnIndex).getClass();
        }

        public Object getValueAt(int rowIndex, int columnIndex) {// tabloda seçim yapýnca nedense hata veriyor. 
        	if (rowIndex==-1) {
        		rowIndex=Pozayukeklecikart.adayyuklertablodata.length-1;
        		        		
        	}
            return Pozayukeklecikart.adayyuklertablodata[rowIndex][columnIndex];
        }
    }

    public static void tablemodeltablo () {
    	    	
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                showFrame();
            }
        });
    }
}
	
@SuppressWarnings("serial")
public static class Yuklenmisyuklertablosu extends JPanel {
	static Tablomodeli tableModel;
	static JScrollPane scrollPane;
	static  JTable table;

	public Yuklenmisyuklertablosu() {
        initializePanel();
    }

    private void initializePanel() {
       
       tableModel = new Tablomodeli();

        table = new JTable(tableModel);
        table.setRowSelectionAllowed(true);
        table.setColumnSelectionAllowed(false);
        
        table.setFillsViewportHeight(true);
        table.setPreferredScrollableViewportSize(new Dimension(500,100));
		   
	    table.getColumnModel().getColumn(0).setPreferredWidth(60);
	    table.getColumnModel().getColumn(1).setPreferredWidth(200);
	    table.getColumnModel().getColumn(2).setPreferredWidth(200);
	    table.getColumnModel().getColumn(3).setPreferredWidth(55);
	    table.getColumnModel().getColumn(4).setPreferredWidth(50);
	    table.getColumnModel().getColumn(5).setPreferredWidth(35);
	    
	    table.setFocusable(false);
	      table.addMouseListener(new MouseAdapter() {   // mouse listener kýsmýsý
	         public void mouseClicked(MouseEvent me) {
	        	
	            if (me.getClickCount() == 2) {     
	            	Pozayukeklecikart.dugmeyukeklecikart();
	            	
	            }
	         }
	      });
	    
	    
	    ListSelectionModel cellSelectionModel = table.getSelectionModel();               // listeden seçileni dinleme kýsmý
        cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
          public void valueChanged(ListSelectionEvent e) {
        	  
        	  if(!e.getValueIsAdjusting())  {    						// bu IF iki defa yazmamasý için konuldu. 
      
        		  int selectedRow     = table.getSelectedRow();
        		  Adayyuklertablosu.table.getSelectionModel().clearSelection();
        		  Pozayukeklecikart.secilenadayyuk="";
        		  Pozayukeklecikart.secilenyuklenmisyuk = (String) table.getValueAt(selectedRow,0 );
        		  
          	      
        	  }
          }
     });
	    
	    scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(400,200));

        this.setLayout(new BorderLayout());
        this.add(scrollPane, BorderLayout.CENTER);
    }

    public static void showFrame() {
        JPanel panel = new Yuklenmisyuklertablosu();
        panel.setOpaque(true);

        Pozayukeklecikart.frame.add(scrollPane,BorderLayout.CENTER); 
    }

    class Tablomodeli extends AbstractTableModel {
     
        private  String[] columnNames = { "EKLÝ YÜK#", "Gönderici","Alýcý","Kap","Kg","K.Adet" };

        public int getRowCount() {
           
        	return Pozayukeklecikart.yuklenmisyuktablodata.length;
        }

        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
        
            return getValueAt(0, columnIndex).getClass();
        }

        public Object getValueAt(int rowIndex, int columnIndex) {// tabloda seçim yapýnca nedense hata veriyor. 
        	if (rowIndex==-1) {
        		rowIndex=Pozayukeklecikart.yuklenmisyuktablodata.length-1;
        			
        	}
            return Pozayukeklecikart.yuklenmisyuktablodata[rowIndex][columnIndex];
        }
    }

    public static void tablemodeltablo () {
    	    	
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                showFrame();
            }
        });
    }
}
	
	} // sinif sonu ;