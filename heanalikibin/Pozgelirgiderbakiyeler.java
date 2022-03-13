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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;

public class Pozgelirgiderbakiyeler implements ActionListener {
	
	private enum actions {
		buttongelirekle,buttongiderekle,buttonsil,faturabas;
	  }

	static JFrame frame ;
	static JPanel panel3,panel5;
	static JButton buttongelirekle,buttongiderekle,buttonsil,faturabas;
	
	static JLabel txtborc,txtalacak,txtbakiye,txtciftklik;
	static NumberFormat df;
	static String isaret="",selectedData ="",pozisyonno="",soruisaretikolonu,aciklamakolonu;
	static double borc;
	static double alacak;
	static double bakiye;
	static boolean gelgitpencereacikbayrak=false,banabagliacik=false;
	
	
	static String[][] satir;
	
	public static void pozgelirgiderbakiyeler(String pozno) {
		
		gelgitpencereacikbayrak=true;
		aciklamakolonu=soruisaretikolonu=selectedData=isaret="";
		alacak=borc=bakiye=0;
		pozisyonno=pozno;
		
		
		buttongelirekle = new JButton("   GELÝR EKLE   ");                                     //Düðmeler 
		buttongelirekle.addActionListener(new Pozgelirgiderbakiyeler());
		buttongelirekle.setActionCommand(actions.buttongelirekle.name());
			
		buttongiderekle = new JButton("   GÝDER EKLE   ");
		buttongiderekle.addActionListener(new Pozgelirgiderbakiyeler());
		buttongiderekle.setActionCommand(actions.buttongiderekle.name());
			
		buttonsil = new JButton("     SÝL    ");
		buttonsil.addActionListener(new Pozgelirgiderbakiyeler());
		buttonsil.setActionCommand(actions.buttonsil.name());
		
		faturabas = new JButton("Gelir Faturasý Bas");
		faturabas.addActionListener(new Pozgelirgiderbakiyeler());
		faturabas.setActionCommand(actions.faturabas.name());
								
		panel5=new JPanel();	
		panel3=new JPanel();
		
		txtciftklik=new JLabel("                           *Beklenen Gideri Düzenlemek Ýçin Çift Týklayýn*");
			
		frame = new JFrame("Poz Gelir Gider Ekraný");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		
		 frame.addWindowListener(new WindowAdapter()
	        {
	            @Override
	            public void windowClosing(WindowEvent e)
	            {
	            	gelgitpencereacikbayrak=false;
	                e.getWindow().dispose();
	            }
	        });
		
		panel3.add(buttongelirekle);
		panel3.add(buttongiderekle);
		panel3.add(buttonsil);
		panel3.add(faturabas);
		panel3.add(txtciftklik);
		
		veritablosunuduzenle (pozisyonno);
		Tabloyapalim.showFrame();
		
		frame.add(panel3, BorderLayout.PAGE_START);
		frame.add(Tabloyapalim.scrollPane,BorderLayout.CENTER);  
	    frame.add(panel5, BorderLayout.PAGE_END);
		
	    frame.pack();
	    frame.setLocationRelativeTo(null);
	    frame.setVisible(true);
	}
	
	public static double veritablosunuduzenle (String hangipoz) {
		
		int cnk=0;
		
		borc=alacak=bakiye=0;
		
		try{                                              // MYSQL Veri tabanýnýn kaç satýr olduðunu bulup array boyutlama
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection(  
					"jdbc:mysql://"+Anasinif.sqlip+"/nal2000?useUnicode=true&characterEncoding=UTF-8",Anasinif.sqluser,Anasinif.sqlpass);
			Statement stmt=con.createStatement();  
			ResultSet rs=stmt.executeQuery  
	    ("select * from fiskayitlari where pozno='"+hangipoz+"' ");		
	 	
			while(rs.next()) {
			cnk++;
			}
			con.close();  
			
			}catch(Exception e){ System.out.println(e);}  
		
		 satir = new String[cnk][12];
		 
		 cnk=0;
		
		try{                                              //Veri tabanýndakileri array'e aktarma
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection(  
					"jdbc:mysql://"+Anasinif.sqlip+"/nal2000?useUnicode=true&characterEncoding=UTF-8",Anasinif.sqluser,Anasinif.sqlpass);
			
			Statement stmt=con.createStatement();  
  			ResultSet rs=stmt.executeQuery
  			 ("select * from fiskayitlari where pozno='"+hangipoz+"' order by yukno ASC");		
  		 	
					while(rs.next()) {
				double kur=Double.parseDouble(rs.getString(12));
				
				satir[cnk][0] = rs.getString(11); // hangiyükün altýnda
				satir[cnk][1] = rs.getString(1);  // fis no oku aktar
				satir[cnk][2] =Ontanimliveriler.sirketkoducoz(Integer.parseInt(rs.getString(3)))[1];// fatura hangi firma ile ilgili 
				satir[cnk][3] = rs.getString(4);  // tarih oku aktar
				
				 satir[cnk][5] = " ";
				
				String ilkyedi = rs.getString(5).substring(3,7);
				String yedisonrasi = rs.getString(5).substring(7);
				  if (ilkyedi.equals("borc")) {       // borç kaydý ise
					satir[cnk][4] = rs.getString(6); 
					borc=borc+(Double.parseDouble(rs.getString(6))*kur); 
					satir[cnk][6] = " ";
				    }
				  if (ilkyedi.equals("alac")) {      // alacak kaydý ise
					satir[cnk][4] =  " "; 
					satir[cnk][6] =rs.getString(6);
					alacak=alacak+(Double.parseDouble(rs.getString(6))*kur); 
				    }
				  satir[cnk][7]=Ontanimliveriler.parabirimikoducoz(rs.getString(9))+" / "+rs.getString(12) ; // para birimi ve kur
				  satir[cnk][8] = Ontanimliveriler.faturatipikodcoz(rs.getString(5).substring(0,7));  //fatura tipi
				  satir[cnk][9] =Ontanimliveriler.faturakalemikoducoz(Integer.parseInt(rs.getString(13))) ; // fatura hizmet kalemi nedir
				  satir[cnk][10] = yedisonrasi; // evrak no
				  satir[cnk][11] = rs.getString(7); // açýklama
				  
			
				  if (yedisonrasi.equals("BEKLENEN GÝDER") && ilkyedi.equals("alac")) {
					  satir[cnk][5] = " ?";  // beklenen gider ise ? koy
				  } 
				  
				  if (!yedisonrasi.equals("BEKLENEN GÝDER") && ilkyedi.equals("alac")) {
					  satir[cnk][5] = " +";  // beklenen gider deðil ama giderse  + koy
				  } 
					  cnk++;
			}
			 con.close();  
			 cnk=0;
			}catch(Exception e){ System.out.println(e);}  
		
		return (borc-alacak);
	
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand()==actions.buttongelirekle.name()) {
			
			if( Pozgelirgiderbakiyeler.banabagliacik==true) {
				return;
			}
			
			if (Pozisyon.tablodata.length==0) {
				Bilgipenceresi.anons("Boþ pozisyona fatura girilemez. Yük ekleyin");
				return;
			}
		
		
			Pozgelirekle.pozagelirekle();
		}
		
		if (e.getActionCommand()==actions.buttongiderekle.name()) {
			
			if( Pozgelirgiderbakiyeler.banabagliacik==true) {
				return;
			}
			
			if (Pozisyon.tablodata.length==0) {
				Bilgipenceresi.anons("Boþ pozisyona fatura girilemez. Yük ekleyin");
				return;
			}
			
			try {
				Pozgiderekle.pozgiderekle("yeni");
			} catch (ParseException e1) {
				
				e1.printStackTrace();
			}
		}
		
		if (e.getActionCommand()==actions.faturabas.name()) {
			if (soruisaretikolonu.equals("") && !selectedData.equals("")) {
				Fatyap.fatyap(selectedData);
	     	}
		}
			
		
				
		if (e.getActionCommand()==actions.buttonsil.name()) {
			
			if( Pozgelirgiderbakiyeler.banabagliacik==true) {
				return;
			}
			
			
			if(!selectedData.equals("")) {
				
			
				Sileyimmi.sonkarar("pozgelirgider");
				if (Sileyimmi.cevap.equals("evet")) {
				
					try {     // veri silme   
			     
					Class.forName("com.mysql.cj.jdbc.Driver");
			        Connection connection = DriverManager.getConnection
			        		("jdbc:mysql://"+Anasinif.sqlip+"/nal2000?useUnicode=true&characterEncoding=UTF-8",Anasinif.sqluser,Anasinif.sqlpass);
			        PreparedStatement st = connection.prepareStatement("DELETE FROM fiskayitlari WHERE fisno ='" +selectedData+ "';");
			        st.executeUpdate(); 
			  
				} catch(Exception z) {
			        System.out.println(z); }
	               
	               Pozgelirgiderbakiyeler.veritablosunuduzenle (pozisyonno);
	               

					if (aciklamakolonu.equals("Toplu_Gider_Faturasi_Parcasi")) {
						Bilgipenceresi.anons(" Toplu Gider Faturasýnýn bu parçasý silindi. "
								+ "Faturanýn daðýtýlmýþ olduðu tüm yük ve poz'lardan silmek için ana menüdeki toplu fatura silme menüsünü kullanýn" );
						} else {
							Bilgipenceresi.anons("Fatura Silindi");
						}
	               
	               if (satir.length==0) {
	            	   gelgitpencereacikbayrak=false;
	            	   frame.dispose();
	            	   pozgelirgiderbakiyeler(pozisyonno);
	            	 return;	   
	               }
	               Pozgelirgiderbakiyeler.Tabloyapalim.tableModel.fireTableDataChanged();
	               Pozgelirgiderbakiyeler.Tabloyapalim.bakiyenedir();
	               selectedData=""; 
	              
				}  // sileyimmi if bloðu sonu
			} // boþ deðilse if bloðu sonu
	    }// actioncommand if  bloðu sonu
    			
		
		
		
} // duðmeler override sonu
	
	@SuppressWarnings("serial")
	public static class Tabloyapalim extends JPanel {
		
		static Tablomodeli tableModel;
		static JScrollPane scrollPane;

		public Tabloyapalim() {
			tableModel = new Tablomodeli();

	        JTable table = new JTable(tableModel);
	        
	        table.setFillsViewportHeight(true);
	        table.setPreferredScrollableViewportSize(new Dimension(600,100));
	            
	    	TableColumnModel columnModel = table.getColumnModel();
	   	 	columnModel.getColumn(0).setMaxWidth(45);
	    	columnModel.getColumn(1).setMaxWidth(40);
	    	columnModel.getColumn(2).setMaxWidth(400);
	   		columnModel.getColumn(3).setMaxWidth(75);
	   		columnModel.getColumn(4).setMaxWidth(100);
	   		columnModel.getColumn(5).setMaxWidth(25);
	   		columnModel.getColumn(6).setMaxWidth(100);
	   		columnModel.getColumn(7).setMaxWidth(175);
	   		columnModel.getColumn(8).setMaxWidth(170);
	   		columnModel.getColumn(9).setMaxWidth(400);
	   		columnModel.getColumn(10).setMaxWidth(250);
	   		columnModel.getColumn(11).setMaxWidth(350);
	     		
	   	 ListSelectionModel cellSelectionModel = table.getSelectionModel();               // listeden seçileni dinleme kýsmý
	        cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

	        cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
	          public void valueChanged(ListSelectionEvent e) {
	           
	        	  if(!e.getValueIsAdjusting())  {    						// bu IF iki defa yazmamasý için konuldu. 
	      
	        		  int selectedRow     = table.getSelectedRow();
	          		
	        		  selectedData=(String) table.getValueAt(selectedRow,1 );
	        		  soruisaretikolonu=((String) table.getValueAt(selectedRow,5 )).trim();
	        		  aciklamakolonu=((String) table.getValueAt(selectedRow,11 )).trim();
	        	  }
	          }
	     });
	        
	        table.setFocusable(false);
		      table.addMouseListener(new MouseAdapter() {   // mouse listener kýsmýsý
		         public void mouseClicked(MouseEvent me) {
		            if (me.getClickCount() == 2) {     
		           if (soruisaretikolonu.equals("?") && Pozgelirgiderbakiyeler.banabagliacik==false) {
		        	   try {
						Pozgiderekle.pozgiderekle(selectedData);
					} catch (ParseException e) {
					
						e.printStackTrace();
					}
		           }
		            }
		         }
		      });
		   	
	   	    df = NumberFormat.getInstance(Locale.ENGLISH);      //bindelik ayracý için yeni format
	   	    txtborc=new JLabel();
	   		txtalacak=new JLabel();
	   		txtbakiye=new JLabel();
	   	
	   		bakiyenedir();
	   		
	   		
	   		panel5.add(txtborc);
	   		panel5.add(txtalacak);
	   		panel5.add(txtbakiye);
		   
		    scrollPane = new JScrollPane(table);
	        scrollPane.setPreferredSize(new Dimension(1150,350));
	        this.setLayout(new BorderLayout());
	        this.add(scrollPane, BorderLayout.CENTER);
	    }
		
		public static void bakiyenedir() {
			bakiye= alacak-borc;
	   		txtborc.setText("Toplam Gelir: "+df.format(borc)+"   ");
	   		txtalacak.setText("Toplam Gider: "+df.format(alacak)+"   ");
	  	if (borc-alacak>0) {
	   			isaret=" Kar";
	   	} else {
	   		isaret=" Zarar";
	   	}
	   		if (borc==alacak) {
	   			isaret="Sýfýr";
	   	}
	   	bakiye=Math.abs(bakiye);
	   	
	   	int dolarbakiye=(int) (bakiye/Ontanimliveriler.kurusoyle(1)[0]);
	   	
	   		txtbakiye.setText("*BAKÝYE: "+df.format(bakiye)+" TL "+isaret+"  =  "+dolarbakiye+" USD "+isaret+" (Güncel kur ile)");
	   		
	   		alacak=borc=bakiye=0;
		}

	   
	    public static void showFrame() {
	        JPanel panel = new Tabloyapalim();
	        panel.setOpaque(true);
	       
	    }

	    class Tablomodeli extends AbstractTableModel {
	     
	        private  String[] columnNames = {"YÜK#", "FÝÞ#","ÞÝRKET","TARÝH","GELÝR","+/?","GÝDER","DÖVÝZ / KUR","FÝÞ TÝPÝ","FATURA KALEMÝ","EVRAK NO","AÇIKLAMA"} ;  

	        public int getRowCount() {
	           
	        	return Pozgelirgiderbakiyeler.satir.length;
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
	        		rowIndex=Pozgelirgiderbakiyeler.satir.length-1;
	        		        		
	        	}
	            return Pozgelirgiderbakiyeler.satir[rowIndex][columnIndex];
	        }
	    }
	
}
}