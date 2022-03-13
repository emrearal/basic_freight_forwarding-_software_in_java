package heanalikibin;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

import com.github.barismeral.dovizAPI.Currency;
import com.github.barismeral.dovizAPI.CurrencyFactory;
import com.github.barismeral.dovizAPI.Moneys;


public class Ontanimliveriler implements ActionListener {
	
	private enum actions {
		buttonekle,buttonduzenle,buttonsil,buttonsec,buttonbul,buttongoster
		 }

	static JFrame frame ;
	static JPanel panel4,panel3;
	static JButton buttonekle,buttonduzenle,buttonsil,buttonsec,buttonbul;
	static JScrollPane sp;
	static JTable tablo;
	static JLabel baslik2;
	static String secilikod ="",aranankelime="",secilikisaltma="",seciliuzaltma="";
	static boolean eklebasildi=false,silebasildi=false,duzenlebasildi=false;
	static String tip,jtalani,sqlkomut1,sqlkomut2;
	static String sutun[];
	static String gecerlikomut="";
	static String[] fatkal;

	public static void ontanimliveriler(String jtalan,String tipi) {
		
		jtalani=jtalan;
		tip=tipi;
		
		secilikod="";
		secilikisaltma="";
	    
		buttonekle = new JButton("Yeni");                                     //Düðmeler 
		buttonekle.addActionListener(new Ontanimliveriler());
		buttonekle.setActionCommand(actions.buttonekle.name());
		
		buttonduzenle = new JButton("Duzenle");
		buttonduzenle.addActionListener(new Ontanimliveriler());
		buttonduzenle.setActionCommand(actions.buttonduzenle.name());
		
		buttonsil = new JButton("Sil");
		buttonsil.addActionListener(new Ontanimliveriler());
		buttonsil.setActionCommand(actions.buttonsil.name());
				
		buttonsec = new JButton("Seç");
		buttonsec.addActionListener(new Ontanimliveriler());
		buttonsec.setActionCommand(actions.buttonsec.name());
		
		buttonbul = new JButton("Bul");
		buttonbul.addActionListener(new Ontanimliveriler());
		buttonbul.setActionCommand(actions.buttonbul.name());
		
		baslik2 = new JLabel("'Silme','Düzenleme',''Seçim' için tablo üzerinde satýrý týklayýn" );
			
		panel3=new JPanel();
		panel4=new JPanel();
		frame = new JFrame(tip.toUpperCase()+" SEÇÝMÝ");
		frame.setAlwaysOnTop( true );
		
		
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
    
       panel3.add(buttonbul);   
	   panel3.add(buttonekle);
	   
	   if (!tip.equals("Faturakalemleri")) {
		   panel3.add(buttonsil);
		   panel3.add(buttonduzenle);
	   }
	 
	   if (!jtalani.equals("anaekran")) {
		   panel3.add(buttonsec);
	   }
	  
	  
	   panel4.add(baslik2);
	   
	   sutun =new String[3]; 
	   sutun[0]="Kod";
	   
	   switch (tip) {
	   
	   case "Liman" :
		 sqlkomut1="SELECT * FROM limanlar WHERE limanadi LIKE '%" +aranankelime+ "%';";
	     sqlkomut2="select * from limanlar";
	     sutun[1]="Liman Adý";sutun[2]="Kent";
	     break;
	   case "T.Sekli" : 
		   sqlkomut1="SELECT * FROM teslimsekli WHERE teslimsekliuzaltmasi LIKE '%" +aranankelime+ "%';";
		     sqlkomut2="select * from teslimsekli";
		     sutun[1]="Kýsaltma";sutun[2]="Açýklama";
		     break;
	   case "Kent" : 
		   sqlkomut1="SELECT * FROM kentler WHERE kentadi LIKE '%" +aranankelime+ "%';";
		     sqlkomut2="select * from kentler";
		     sutun[1]="Kent Adý";sutun[2]="Ülke";
		     break;
	   case "Odeme" : 
		   sqlkomut1="SELECT * FROM odemesekli WHERE odemesekliuzaltmasi LIKE '%" +aranankelime+ "%';";
		     sqlkomut2="select * from odemesekli";
		     sutun[1]="Kýsaltma";sutun[2]="Açýklama";
		     break;
	   case "Gemi" : 
		   sqlkomut1="SELECT * FROM gemiler WHERE gemiadi LIKE '%" +aranankelime+ "%';";
		     sqlkomut2="select * from gemiler";
		     sutun[1]="Gemi Adý";sutun[2]="Yýl-Bayrak-Imo";
		     break;
	   case "Hat" :
		   sqlkomut1="SELECT * FROM hatlar WHERE hatadi LIKE '%" +aranankelime+ "%';";
		     sqlkomut2="select * from hatlar";
		     sutun[1]="Kýsaltma";sutun[2]="Hat Adý";
		     break;
		
	   case "Faturakalemleri" : 
		   sqlkomut1="SELECT * FROM faturakalemleri WHERE kalemadi LIKE '%" +aranankelime+ "%';";
		     sqlkomut2="select * from faturakalemleri";
		     sutun[1]="Kdv %";sutun[2]="Kalem Adý";
		     break;
		 	   
	   }
		int cnk=0;
	   
	     try{                                              
				Class.forName("com.mysql.cj.jdbc.Driver");    // ARANAN SONUÇTAN KAÇ TANE VAR
				Connection con=DriverManager.getConnection(
						"jdbc:mysql://"+Anasinif.sqlip+"/nal2000?useUnicode=true&characterEncoding=UTF-8",Anasinif.sqluser,Anasinif.sqlpass);
						
						Statement stmt=con.createStatement();  
						
						if (!aranankelime.equals("")) { 
							gecerlikomut=sqlkomut1;
						}else {
							gecerlikomut=sqlkomut2;
						}
				        ResultSet rs=stmt.executeQuery(gecerlikomut); 
				
				        while(rs.next()) {
							cnk++;
							}
							con.close();  
							
							}catch(Exception e){ System.out.println(e);}  
						
						 String[][] satir= new String[cnk][3];     // sonuç sayýsýna göre array boyutlama
						 fatkal = new String [cnk];
						 cnk=0;
						 
			
				try{                                              
				   Class.forName("com.mysql.cj.jdbc.Driver");    // dizine aktar
				 Connection con=DriverManager.getConnection(
						 "jdbc:mysql://"+Anasinif.sqlip+"/nal2000?useUnicode=true&characterEncoding=UTF-8",Anasinif.sqluser,Anasinif.sqlpass);
										
					Statement stmt=con.createStatement();  
					ResultSet rs=stmt.executeQuery(gecerlikomut); 
		   
					while(rs.next()) {
						 satir[cnk][0] = rs.getString(1);
						 satir[cnk][1] = rs.getString(2);
						 satir[cnk][2] = rs.getString(3);
						 if (tip.equals("Gemi")) {
							 satir[cnk][2] = rs.getString(3)+"-"+rs.getString(4)+"-"+rs.getString(5);	 
						 }
						 
							 fatkal[cnk] = rs.getString(1)+"-"+rs.getString(3)+"-"+rs.getString(2);	 
						 
						 cnk++;
						}
						 con.close();  
						 cnk=0;
						}catch(Exception e){ System.out.println(e);}  
				
				tablo= new JTable(satir,sutun);  
				tablo.setDefaultEditor(Object.class, null);	// tabloya elle düzeltme yapýlamasýn
				sp=new JScrollPane(tablo); 
				sp.setPreferredSize(new Dimension(320, 150));
				
				DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		        
				
		        tablo.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);     //tabloyu ortama
		        tablo.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		        tablo.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		        
		        tablo.setFocusable(false);
			      tablo.addMouseListener(new MouseAdapter() {   // mouse listener kýsmýsý
			         public void mouseClicked(MouseEvent me) {
			            if (me.getClickCount() == 2) { 
			            	
			            	if (!jtalani.equals("anaekran")) {
			            		veriyinereyegondereyim();
				    			frame.dispose();
				    		
			            	}
			            	
				          }
			         }
			      });
		      
		        
				TableColumnModel columnModel = tablo.getColumnModel();
				columnModel.getColumn(0).setMaxWidth(50);
				columnModel.getColumn(1).setMaxWidth(250);
				columnModel.getColumn(2).setMaxWidth(500);
			    
			    aranankelime="";
	
	    Container contentPane = frame.getContentPane();
	  
	    contentPane.add(sp, BorderLayout.PAGE_END);
	    contentPane.add(panel3, BorderLayout.PAGE_START);
	    contentPane.add(panel4, BorderLayout.CENTER);
	   
	    frame.pack();
        frame.setLocationRelativeTo(null);
	    
        if (!tip.equals("Faturakalemleri") | jtalani.equals("anaekran")) {
        	 frame.setVisible(true);   // fatura kalemlerinden geliyorsa pencereyi açma
        }
	   
	     
	    ListSelectionModel cellSelectionModel = tablo.getSelectionModel();               // listeden seçileni dinleme kýsmý
        cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
          public void valueChanged(ListSelectionEvent e) {
           
        	  if(!e.getValueIsAdjusting())  {    						// bu IF iki defa yazmamasý için konuldu. 
      
        		  int selectedRow     = tablo.getSelectedRow();
          		secilikod = (String) tablo.getValueAt(selectedRow,0 );
          		secilikisaltma=(String) tablo.getValueAt(selectedRow,1 );
          		seciliuzaltma=(String) tablo.getValueAt(selectedRow,2 );
        	  }
          }
     });
	  	
	}
	
static public void veriyinereyegondereyim() {
		
		switch (tip) {
		case  "Liman":
			if (jtalani=="yukleme") {
				Yuk.yuklemelimanikodu=Integer.parseInt(Ontanimliveriler.secilikod);
		    	Yuk.yuklemelimanijt.setText(secilikisaltma);
		    	Yuk.yuklemelimanijt.setCaretPosition(0);
			}
			if (jtalani=="aktarma") {
				Yuk.aktarmalimanikodu=Integer.parseInt(Ontanimliveriler.secilikod);
		    	Yuk.aktarmalimanijt.setText(secilikisaltma);
		    	Yuk.aktarmalimanijt.setCaretPosition(0);
			}
			if (jtalani=="varis") {
				Yuk.varislimanikodu=Integer.parseInt(Ontanimliveriler.secilikod);
		    	Yuk.varislimanijt.setText(secilikisaltma);
		    	Yuk.varislimanijt.setCaretPosition(0);
			}
			if (jtalani=="Pozyukleme") {
				Denizyoluanaekran.yukLimanjt.setText(secilikisaltma);
				Denizyoluanaekran.yukLimanjt.setCaretPosition(0);
				Denizyoluanaekran.yuklimkod=Integer.parseInt(secilikod);
				
			}
			if (jtalani=="Pozvaris") {
				Denizyoluanaekran.bosLimanjt.setText(Ontanimliveriler.secilikisaltma);
				Denizyoluanaekran.bosLimanjt.setCaretPosition(0);
				Denizyoluanaekran.boslimkod=Integer.parseInt(Ontanimliveriler.secilikod);
			}
			if (jtalani=="pozkartyukleme") {
				Pozisyon.yuklemelimanikodu=Integer.parseInt(Ontanimliveriler.secilikod);
		    	Pozisyon.yuklemelimanijt.setText(secilikisaltma);
		    	Pozisyon.yuklemelimanijt.setCaretPosition(0);
			}
			if (jtalani=="pozkartaktarma") {
				Pozisyon.aktarmalimanikodu=Integer.parseInt(Ontanimliveriler.secilikod);
		    	Pozisyon.aktarmalimanijt.setText(secilikisaltma);
		    	Pozisyon.aktarmalimanijt.setCaretPosition(0);
			}
			if (jtalani=="pozkartvaris") {
				Pozisyon.varislimanikodu=Integer.parseInt(Ontanimliveriler.secilikod);
		    	Pozisyon.varislimanijt.setText(secilikisaltma);
		    	Pozisyon.varislimanijt.setCaretPosition(0);
			}
			
			if (jtalani=="yukaramayukleme") {
				Yukara.yuklimkodu=Integer.parseInt(Ontanimliveriler.secilikod);
		    	Yukara.yuklemelimanijt.setText(secilikisaltma);
		    	Yukara.yuklemelimanijt.setCaretPosition(0);
			}
			
			if (jtalani=="yukaramavaris") {
				Yukara.varlimkodu=Integer.parseInt(Ontanimliveriler.secilikod);
		    	Yukara.varislimanijt.setText(secilikisaltma);
		    	Yukara.varislimanijt.setCaretPosition(0);
			}
			
			if (jtalani=="pozaramayukleme") {
				Pozara.yuklimkodu=Integer.parseInt(Ontanimliveriler.secilikod);
		    	Pozara.yuklemelimanijt.setText(secilikisaltma);
		    	Pozara.yuklemelimanijt.setCaretPosition(0);
			}
			
			if (jtalani=="pozaramavaris") {
				Pozara.varlimkodu=Integer.parseInt(Ontanimliveriler.secilikod);
				Pozara.varislimanijt.setText(secilikisaltma);
				Pozara.varislimanijt.setCaretPosition(0);
			}
			
				frame.dispose();
			 break;
					 
		case  "T.Sekli":
			Yuk.teslimseklikodu=Integer.parseInt(Ontanimliveriler.secilikod);
	    	Yuk. teslimseklijt.setText(Ontanimliveriler.secilikisaltma);
	    	Yuk. teslimseklijt.setCaretPosition(0);
			frame.dispose();
			 break;
	
		case  "Kent":
			if (jtalani=="odeme") {
				Yuk.odemekentikodu=Integer.parseInt(Ontanimliveriler.secilikod);
		    	Yuk.odemekentijt.setText(secilikisaltma);
		    	Yuk.odemekentijt.setCaretPosition(0);
			}
			if (jtalani=="yukleme") {
				Yuk.yuklemekentikodu=Integer.parseInt(Ontanimliveriler.secilikod);
		    	Yuk.yuklemekentijt.setText(secilikisaltma);
		    	Yuk.yuklemekentijt.setCaretPosition(0);
			}
			if (jtalani=="sonvaris") {
				Yuk.sonvariskentikodu=Integer.parseInt(Ontanimliveriler.secilikod);
		    	Yuk.sonvariskentijt.setText(secilikisaltma);
		    	Yuk.sonvariskentijt.setCaretPosition(0);
		    	Yuk.varisulkesijt.setText(seciliuzaltma);
		    	Yuk.varisulkesijt.setCaretPosition(0);
		    }
			if (jtalani=="pozkartyukleme") {
				Pozisyon.yuklemekentikodu=Integer.parseInt(Ontanimliveriler.secilikod);
		    	Pozisyon.yuklemekentijt.setText(secilikisaltma);
		    	Pozisyon.yuklemekentijt.setCaretPosition(0);
		    }
			if (jtalani=="pozkartsonvaris") {
				Pozisyon.sonvariskentikodu=Integer.parseInt(Ontanimliveriler.secilikod);
		    	Pozisyon.sonvariskentijt.setText(secilikisaltma);
		    	Pozisyon.sonvariskentijt.setCaretPosition(0);
		    	Pozisyon.varisulkesijt.setText(seciliuzaltma);
		    	Pozisyon.varisulkesijt.setCaretPosition(0);
		    }
			
			if (jtalani=="yukaramayukleme") {
				Yukara.yukkentkodu=Integer.parseInt(Ontanimliveriler.secilikod);
		    	Yukara.yuklemekentijt.setText(secilikisaltma);
		    	Yukara.yuklemekentijt.setCaretPosition(0);
		    	
		    }
			
			if (jtalani=="yukaramasonvaris") {
				Yukara.sonvarkentkodu=Integer.parseInt(Ontanimliveriler.secilikod);
		    	Yukara.sonvariskentijt.setText(secilikisaltma);
		    	Yukara.sonvariskentijt.setCaretPosition(0);
		    	
		    }
			
			if (jtalani=="pozaramayukleme") {
				Pozara.yukkentkodu=Integer.parseInt(Ontanimliveriler.secilikod);
				Pozara.yuklemekentijt.setText(secilikisaltma);
				Pozara.yuklemekentijt.setCaretPosition(0);
		    	
		    }
			
			if (jtalani=="pozaramasonvaris") {
				Pozara.sonvarkentkodu=Integer.parseInt(Ontanimliveriler.secilikod);
				Pozara.sonvariskentijt.setText(secilikisaltma);
				Pozara.sonvariskentijt.setCaretPosition(0);
		    	
		    }
			
			frame.dispose();
			 break;
			 
		case  "Odeme":
			Yuk.odemeseklikodu=Integer.parseInt(Ontanimliveriler.secilikod);
	    	Yuk.odemeseklijt.setText(Ontanimliveriler.secilikisaltma);
	    	Yuk.odemeseklijt.setCaretPosition(0);
			frame.dispose();
			 break;
		
		case  "Gemi":
			if (jtalani=="pozkartyuklemegemisi") {
			Pozisyon.yuklemegemisikodu=Integer.parseInt(Ontanimliveriler.secilikod);
	    	Pozisyon.yuklemegemisijt.setText(Ontanimliveriler.secilikisaltma);
	    	Pozisyon.yuklemegemisijt.setCaretPosition(0);
			}
			if (jtalani=="pozkartaktarmagemisi") {
				Pozisyon.aktarmagemisikodu=Integer.parseInt(Ontanimliveriler.secilikod);
		    	Pozisyon.aktarmagemisijt.setText(Ontanimliveriler.secilikisaltma);
		    	Pozisyon.aktarmagemisijt.setCaretPosition(0);
				}
			
			if (jtalani=="pozaramayuklemegemisi") {
				Pozara.yuklemegemisikodu=Integer.parseInt(Ontanimliveriler.secilikod);
				Pozara.yuklemegemisijt.setText(Ontanimliveriler.secilikisaltma);
				Pozara.yuklemegemisijt.setCaretPosition(0);
				}
			frame.dispose();
			 break;	 
			 
		case  "Hat":
			if (jtalani=="pozhatlar") {
				Pozisyon.hatadijt.setText(Ontanimliveriler.seciliuzaltma);
				Pozisyon.hatadijt.setCaretPosition(0);
				Pozisyon.hatadikodu=Integer.parseInt(Ontanimliveriler.secilikod);
			}
			
			if (jtalani=="pozaramahatlar") {
				Pozara.hatjt.setText(Ontanimliveriler.seciliuzaltma);
				Pozara.hatjt.setCaretPosition(0);
				Pozara.hatkodu=Integer.parseInt(Ontanimliveriler.secilikod);
				
			}
			frame.dispose();
			 break;	
				
	}
		 return;
		}

public static String tarihiterscevir(String hangitarih) {
	
	String yenitarih=hangitarih.substring(8)+"/"+hangitarih.substring(5,7)+"/"+hangitarih.substring(0,4);
	
	return yenitarih;
	}


public static String[] sirketkoducoz(int musterikodu) {
	String[] musteribilgileri=new String [10];
	try{                                              
		Class.forName("com.mysql.cj.jdbc.Driver");  
		Connection con=DriverManager.getConnection(
				"jdbc:mysql://"+Anasinif.sqlip+"/nal2000?useUnicode=true&characterEncoding=UTF-8",Anasinif.sqluser,Anasinif.sqlpass);
				
				Statement stmt=con.createStatement();  
		        ResultSet rs=stmt.executeQuery("SELECT * FROM sirketkartlari where sirketkart_0kod='"+musterikodu+"';"); 
		
		while(rs.next()) { 
			
			for (int i=0; i<10;i++) {
				musteribilgileri [i]=rs.getString(i+1);
			}
		}
						
		con.close();  
		
		}catch(Exception e){ System.out.println(e);}
	
	return musteribilgileri;
}  

public static int yuknogondericisibul(int yukno) {
	int gonderenkodu=0;
	try{                                              
		Class.forName("com.mysql.cj.jdbc.Driver");  
		Connection con=DriverManager.getConnection(
				"jdbc:mysql://"+Anasinif.sqlip+"/nal2000?useUnicode=true&characterEncoding=UTF-8",Anasinif.sqluser,Anasinif.sqlpass);
				
				Statement stmt=con.createStatement();  
		        ResultSet rs=stmt.executeQuery("SELECT * FROM yukler where yukno='"+yukno+"';"); 
		
		while(rs.next()) { 
			
			gonderenkodu=Integer.parseInt(rs.getString(8));   
				
			}
						
		con.close();  
		
		}catch(Exception e){ System.out.println(e);}
	
	return gonderenkodu;
}  

public static String teslimseklikoducoz (int teslimseklikodu) {
	String teslimsekliadi="";
	try{                                              
		Class.forName("com.mysql.cj.jdbc.Driver");  
		Connection con=DriverManager.getConnection(
				"jdbc:mysql://"+Anasinif.sqlip+"/nal2000?useUnicode=true&characterEncoding=UTF-8",Anasinif.sqluser,Anasinif.sqlpass);
				
				Statement stmt=con.createStatement();  
		        ResultSet rs=stmt.executeQuery("SELECT * FROM teslimsekli where teslimseklikodu='"+teslimseklikodu+"';"); 
		
		while(rs.next()) { 
			
			teslimsekliadi=rs.getString(2);
				
			}
						
		con.close();  
		
		}catch(Exception e){ System.out.println(e);}
	
	return teslimsekliadi;
}

public static String odemeseklikoducoz (int odemeseklikodu) {
	String odemesekliadi="";
	try{                                              
		Class.forName("com.mysql.cj.jdbc.Driver");  
		Connection con=DriverManager.getConnection(
				"jdbc:mysql://"+Anasinif.sqlip+"/nal2000?useUnicode=true&characterEncoding=UTF-8",Anasinif.sqluser,Anasinif.sqlpass);
				
				Statement stmt=con.createStatement();  
		        ResultSet rs=stmt.executeQuery("SELECT * FROM odemesekli where odemeseklikodu='"+odemeseklikodu+"';"); 
		
		while(rs.next()) { 
			
			odemesekliadi=rs.getString(2);
				
			}
						
		con.close();  
		
		}catch(Exception e){ System.out.println(e);}
	
	return odemesekliadi;
}

public static String[] kentkoducoz(int kentkodu) {
	String[] kentadi={"",""};
	try{                                              
		Class.forName("com.mysql.cj.jdbc.Driver");  
		Connection con=DriverManager.getConnection(
				"jdbc:mysql://"+Anasinif.sqlip+"/nal2000?useUnicode=true&characterEncoding=UTF-8",Anasinif.sqluser,Anasinif.sqlpass);
				
				Statement stmt=con.createStatement();  
		        ResultSet rs=stmt.executeQuery("SELECT * FROM kentler where kentkodu='"+kentkodu+"';"); 
		
		while(rs.next()) { 
			
			kentadi[0]=rs.getString(2);
			kentadi[1]=rs.getString(3);	
			}
		con.close();  
		}catch(Exception e){ System.out.println(e);}
	
	return kentadi;
}  

public static String limankoducoz(int limankodu) {
	String limanadi= "";
	try{                                              
		Class.forName("com.mysql.cj.jdbc.Driver");  
		Connection con=DriverManager.getConnection(
				"jdbc:mysql://"+Anasinif.sqlip+"/nal2000?useUnicode=true&characterEncoding=UTF-8",Anasinif.sqluser,Anasinif.sqlpass);
				
				Statement stmt=con.createStatement();  
		        ResultSet rs=stmt.executeQuery("SELECT * FROM limanlar where limankodu='"+limankodu+"';"); 
		
		while(rs.next()) { 
			
			limanadi=rs.getString(2);
			
			}
		con.close();  
		
		}catch(Exception e){ System.out.println(e);}
	
	return limanadi;
}  

public static String gemikoducoz(int gemikodu) {
	String gemiadi= "";
	try{                                              
		Class.forName("com.mysql.cj.jdbc.Driver");  
		Connection con=DriverManager.getConnection(
				"jdbc:mysql://"+Anasinif.sqlip+"/nal2000?useUnicode=true&characterEncoding=UTF-8",Anasinif.sqluser,Anasinif.sqlpass);
				
				Statement stmt=con.createStatement();  
		        ResultSet rs=stmt.executeQuery("SELECT * FROM gemiler where gemikodu='"+gemikodu+"';"); 
		
		while(rs.next()) { 
			
			gemiadi=rs.getString(2);
			
			}
		con.close();  
		
		}catch(Exception e){ System.out.println(e);}
	
	return gemiadi;
}  

public static String hatkoducoz(int hatkodu) {
	String hatadi= "";
	try{                                              
		Class.forName("com.mysql.cj.jdbc.Driver");  
		Connection con=DriverManager.getConnection(
				"jdbc:mysql://"+Anasinif.sqlip+"/nal2000?useUnicode=true&characterEncoding=UTF-8",Anasinif.sqluser,Anasinif.sqlpass);
				
				Statement stmt=con.createStatement();  
		        ResultSet rs=stmt.executeQuery("SELECT * FROM hatlar where hatkodu='"+hatkodu+"';"); 
		
		while(rs.next()) { 
			
			hatadi=rs.getString(2);
			
			
			}
		con.close();  
		
		}catch(Exception e){ System.out.println(e);}
	
	return hatadi;
}
public static String faturakalemikoducoz (int kalemkodu) {
	String kalemadi= "";
	try{                                              
		Class.forName("com.mysql.cj.jdbc.Driver");  
		Connection con=DriverManager.getConnection(
				"jdbc:mysql://"+Anasinif.sqlip+"/nal2000?useUnicode=true&characterEncoding=UTF-8",Anasinif.sqluser,Anasinif.sqlpass);
				
				Statement stmt=con.createStatement();  
		        ResultSet rs=stmt.executeQuery("SELECT * FROM faturakalemleri where kalemkodu='"+kalemkodu+"';"); 
		
		while(rs.next()) { 
			kalemadi=rs.getString(3);
			}
		
		con.close();  
		
		}catch(Exception e){ System.out.println(e);}
	
	return kalemadi;
}


    public static String parabirimikoducoz(String parabirimikodu) {
    String parabirimi="";	
    	switch(parabirimikodu) {
    	case "PB2":
    		  parabirimi="USD";
    		  break;
    	case "PB3":
    		  parabirimi="EUR";
    		  break;
    	case "PB4":
    		  parabirimi="GBP";
    		  break;	  
    	case "PB5":
    		  parabirimi="SEK";
    		 break;
    	default:
    	    parabirimi="TL";
    	}
    	return parabirimi;
    }
    
    public static String faturatipikodcoz(String kod) {
		switch (kod) {
		case "fatborc":
				kod="FATURA BORC";
		break;	
		case "fatalac":
			kod="FATURA ALACAK";
		break;
		case "dekborc":
			kod="DEKONT BORC";
			break;
		case "dekalac":
			kod="DEKONT ALACAK";
			break;
		case "odeborc":
			kod="ODEME BORC";
			break;
		case "odealac":
			kod="ODEME ALACAK";
			break;
		case "acýborc":
			kod="ACILIS BORC";
			break;
		case "acýalac":
			kod="ACILIS ALACAK";
			break;
		}
		return kod;
	}
    
    public static float[] kurusoyle (int secilendovizkodu) {
    	
		CurrencyFactory doviz = new CurrencyFactory(Moneys.US_DOLLAR);
	    Currency kur;
	    float gonderilecekkur[]= {1,1}; // TL default deðer, alýþ kuru 1 ,satýþ kuru 1
		
		switch (secilendovizkodu) {
		case 2 : doviz = new CurrencyFactory(Moneys.EURO); break;
		case 3 : doviz = new CurrencyFactory(Moneys.POUND_STERLING); break;
		case 4 : doviz = new CurrencyFactory(Moneys.SWEDISH_KRONA); break;
		}
		
		if (secilendovizkodu!=0) { // Seçilen döviz TL'den farklý ise... 
			kur = doviz.getCurrency(); 
			
     		try {
				gonderilecekkur[0]=kur.getBuyingPrice(); // alýþ kuru
				gonderilecekkur[1]=kur.getSellingPrice(); // satýþ kuru
			} catch (Exception e) {
				Bilgipenceresi.anons("Merkez Bankasýndan Kur Alým Hatasý");
				
			}
     		
		}
		
		return gonderilecekkur ;
	 }
    
    public static String pbsine (int dovizkodu) {
    	String pb="PB1";
    		switch (dovizkodu) {
    		case 1: pb="PB2"; break;
    		case 2: pb="PB3"; break;
    		case 3: pb="PB4"; break;
    		case 4: pb="PB5"; break;
    		}
    	return pb;
    	}
    
    public static int pbdenteklikoda (String pbneymis) {
    	int teklikod=0;
    		switch (pbneymis) {
    		case "USD": teklikod=1; break;
    		case "EUR": teklikod=2; break;
    		case "GBP": teklikod=3; break;
    		case "SEK": teklikod=4; break;
    		}
    	return teklikod;
    	}
    
public static String sayiyiyaziyacevir (String cevrileceksayi, String dil ) {
		
		String[] birler= new String [10];
		String[] onlar= new String [10];
		String[] yuzler= new String [10];
		String[] binler= new String [10];
				
		birler[0]="";birler[1]="bir";birler[2]="iki";birler[3]="üç";birler[4]="dört";
		birler[5]="beþ";birler[6]="altý";birler[7]="yedi";birler[8]="sekiz";birler[9]="dokuz";
		
		onlar[0]="";onlar[1]="on";onlar[2]="yirmi";onlar[3]="otuz";onlar[4]="kýrk";
		onlar[5]="elli";onlar[6]="atmýþ";onlar[7]="yetmiþ";onlar[8]="seksen";onlar[9]="doksan";
		
		yuzler[0]="";yuzler[1]="yüz";yuzler[2]="ikiyüz";yuzler[3]="üçyüz";yuzler[4]="dörtyüz";
		yuzler[5]="beþyüz";yuzler[6]="altýyüz";yuzler[7]="yediyüz";yuzler[8]="sekizyüz";yuzler[9]="dokuzyüz";
		
		binler[0]="";binler[1]="bin";binler[2]="ikibin";binler[3]="üçbin";binler[4]="dörtbin";
		binler[5]="beþbin";binler[6]="altýbin";binler[7]="yedibin";binler[8]="sekizbin";binler[9]="dokuzbin";
		
		if (dil.equals("UK")) {
			
			birler[0]="";birler[1]="one";birler[2]="two";birler[3]="three";birler[4]="four";
			birler[5]="five";birler[6]="six";birler[7]="seven";birler[8]="eight";birler[9]="nine";
			
			onlar[0]="";onlar[1]="ten";onlar[2]="twenty";onlar[3]="thirty";onlar[4]="fourty";
			onlar[5]="fifty";onlar[6]="sixty";onlar[7]="seventy";onlar[8]="eighty";onlar[9]="ninety";
			
			yuzler[0]="";yuzler[1]="hundred";yuzler[2]="twohundred";yuzler[3]="threehundred";yuzler[4]="fourhundred";
			yuzler[5]="fivehundred";yuzler[6]="sixhundred";yuzler[7]="sevenhundred";yuzler[8]="eighthundred";yuzler[9]="ninehundred";
			
			binler[0]="";binler[1]="thousand";binler[2]="twothousand";binler[3]="threethousand";binler[4]="fourthousand";
			binler[5]="fivethousand";binler[6]="sixthousand";binler[7]="seventhousand";binler[8]="eightthousand";binler[9]="ninethousand";
			
		}
		String yaziyla="";
		int basamaksayisi=cevrileceksayi.length();
		char[] parcala = new char [basamaksayisi]; //sayi uzunluðu kadar boyutla 
		
		for (int i=0; i<basamaksayisi; i++) {  // sayiyi basamak basamak ayirdik
			parcala[i]= cevrileceksayi.charAt(i);
	     	}
				
		switch (basamaksayisi) {
		
		case 1 : 
			yaziyla = birler [Character.getNumericValue(parcala[0])];
			break;
			
		case 2 :  // iki basamaklýysa 
			yaziyla = onlar [Character.getNumericValue(parcala[0])];
			yaziyla = yaziyla+birler [Character.getNumericValue(parcala[1])];
		    break;
		    
		case 3 : 
			yaziyla = yuzler [Character.getNumericValue(parcala[0])];
			if (yaziyla.equals("hundred")) {
				yaziyla="one"+yaziyla;
			}
			if (dil.equals("UK")) {
				yaziyla = yaziyla+"and"+ onlar [Character.getNumericValue(parcala[1])];	
			} else {
				yaziyla = yaziyla + onlar [Character.getNumericValue(parcala[1])];
			}
			yaziyla = yaziyla+birler [Character.getNumericValue(parcala[2])];
			break;
		
		case 4 :
			yaziyla = binler [Character.getNumericValue(parcala[0])];
			if (yaziyla.equals("thousand")) {
				yaziyla="one"+yaziyla;
			}
			yaziyla =yaziyla+ yuzler [Character.getNumericValue(parcala[1])];
			if (dil.equals("UK")) {
				yaziyla = yaziyla+"and"+ onlar [Character.getNumericValue(parcala[2])];	
			} else {
				yaziyla = yaziyla + onlar [Character.getNumericValue(parcala[2])];
			}
			yaziyla = yaziyla+birler [Character.getNumericValue(parcala[3])];
			break;
		
		case 5 :
			yaziyla =onlar [Character.getNumericValue(parcala[0])];
			yaziyla =yaziyla+ binler [Character.getNumericValue(parcala[1])];
			yaziyla =yaziyla+ yuzler [Character.getNumericValue(parcala[2])];
			if (dil.equals("UK")) {
				yaziyla = yaziyla+"and"+ onlar [Character.getNumericValue(parcala[3])];	
			} else {
				yaziyla = yaziyla + onlar [Character.getNumericValue(parcala[3])];
			}
			
			yaziyla = yaziyla+birler [Character.getNumericValue(parcala[4])];
			break;
		
		case 6 : 
			yaziyla =yuzler [Character.getNumericValue(parcala[0])];
			yaziyla =yaziyla+onlar [Character.getNumericValue(parcala[1])];
			yaziyla =yaziyla+ binler [Character.getNumericValue(parcala[2])];
			yaziyla =yaziyla+ yuzler [Character.getNumericValue(parcala[3])];
			if (dil.equals("UK")) {
				yaziyla = yaziyla+"and"+ onlar [Character.getNumericValue(parcala[4])];	
			} else {
				yaziyla = yaziyla + onlar [Character.getNumericValue(parcala[4])];
			}
			
			yaziyla = yaziyla+birler [Character.getNumericValue(parcala[5])];
            break;
		
		case 7 : 
			yaziyla=birler [Character.getNumericValue(parcala[0])]+"milyon";
			if (dil.equals("UK")) {
				yaziyla=birler [Character.getNumericValue(parcala[0])]+"million";	
			}
			yaziyla =yaziyla+yuzler [Character.getNumericValue(parcala[1])];
			yaziyla =yaziyla+onlar [Character.getNumericValue(parcala[2])];
			yaziyla =yaziyla+ binler [Character.getNumericValue(parcala[3])];
			yaziyla =yaziyla+ yuzler [Character.getNumericValue(parcala[4])];
			if (dil.equals("UK")) {
				yaziyla = yaziyla+"and"+ onlar [Character.getNumericValue(parcala[5])];	
			} else {
				yaziyla = yaziyla + onlar [Character.getNumericValue(parcala[5])];
			}
			
			yaziyla = yaziyla+birler [Character.getNumericValue(parcala[6])];
		  break;
		
		} // switch sonu
		
		if (yaziyla.contains("tenone")) {yaziyla= yaziyla.replaceAll("tenone","eleven");}
		if (yaziyla.contains("tentwo")) {yaziyla= yaziyla.replaceAll("tentwo","twelve");}
		if (yaziyla.contains("tenthree")) {yaziyla= yaziyla.replaceAll("tenthree","thirteen");}
		if (yaziyla.contains("tenfour")) {yaziyla= yaziyla.replaceAll("tenfour","fourteen");}
		if (yaziyla.contains("tenfive")) {yaziyla= yaziyla.replaceAll("tenfive","fifteen");}
		if (yaziyla.contains("tensix")) {yaziyla= yaziyla.replaceAll("tensix","sixteen");}
		if (yaziyla.contains("tenseven")) {yaziyla= yaziyla.replaceAll("tenseven","seventeen");}
		if (yaziyla.contains("teneight")) {yaziyla= yaziyla.replaceAll("teneight","eighteen");}
		if (yaziyla.contains("tennine")) {yaziyla= yaziyla.replaceAll("tennine","nineteen");}
		
		if (cevrileceksayi.equals("0") | cevrileceksayi.equals("00") ) {
			yaziyla="sýfýr" ;
		    if (dil.equals("UK")) {
		    yaziyla="zero" ;	
		    }
		}
		
			return yaziyla;
	} // metod sonu 

 public static String yukunbrutkilosunubul(String yukno) {
	 String konteynerhamveri="";	  
	 Double toplamkg=0.00;
	 
	 try{               // ilgili aramanýn verilerinin sayýsýný öðrenelim             
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection(
		"jdbc:mysql://"+Anasinif.sqlip+"/nal2000?useUnicode=true&characterEncoding=UTF-8",Anasinif.sqluser,Anasinif.sqlpass);
					
					Statement stmt=con.createStatement();  
			        ResultSet rs=stmt.executeQuery("select * from yukler where yukno="+yukno); 
		
			while(rs.next()) {
				konteynerhamveri=rs.getString(25);     
					}
			con.close();  
			
			}catch(Exception e){ System.out.println(e);}  
	 
	 for (int i=0; i<konteynerhamveri.length()/24;i++) {
		 toplamkg=toplamkg+Double.parseDouble(konteynerhamveri.substring(12+(i*24),18+(i*24))) ; 
	 }
		
	 return String.valueOf(toplamkg) ;
	 
 }


	@Override
	public void actionPerformed(ActionEvent e) {
		
         
		if (e.getActionCommand()==actions.buttonsec.name()) {
			
			if (secilikod.equals("")) {
				
				return;
			}
			
			veriyinereyegondereyim();
			frame.dispose();
		
		}
		
		if (e.getActionCommand()==actions.buttonekle.name()) {
			eklebasildi=true;
			
			Ontanimliveridegirtireklesil.ontanimliveridegirtireklesil();
				
		}
		
		if (e.getActionCommand()==actions.buttonduzenle.name()) {
			
			if (secilikod.equals("")) {
				return;
			}
		
			duzenlebasildi=true;
			Ontanimliveridegirtireklesil.ontanimliveridegirtireklesil();
			
		}

		if (e.getActionCommand()==actions.buttonsil.name()) {
	
			if (secilikod.equals("")) {
				return;
			}
			silebasildi=true;
			Ontanimliveridegirtireklesil.ontanimliveridegirtireklesil();
	
		}
	
		if (e.getActionCommand()==actions.buttonbul.name()) {
			
			Ontanimliveribul.nebulayim();
			
		}
		eklebasildi=false;
		duzenlebasildi=false;
		silebasildi=false;
	}
	
	
		
	public static class Ontanimliveribul implements ActionListener {
		
		static JButton yavrudugme ;
		static JButton yavrudugme2 ;
		static JDialog yavru ;
		static JLabel baslik2,baslik ;
		static JTextField alan;
		
		private enum evethayir {
			basevet,bashayir;
		}
					public static void nebulayim() {
					yavrudugme= new JButton("Tamam");
					yavrudugme2 = new JButton("Ýptal");
					yavru = new JDialog(Ontanimliveriler.frame,"Ön-Tanýmlý Arama Ekraný",true); 
					yavru.setResizable(false);
					
					alan=new JTextField() ;
					alan.setBounds(30,37,240,26);
						
					baslik2 = new JLabel("Aranacak Veri Adýný Yazýn " );
					baslik2.setBounds(70,8,270,20);
					
					baslik = new JLabel("(*)Tüm Kayýtlar Ýçin Boþ Býrakýp Tamam'a Basýn" );
					baslik.setBounds(15,90,270,20);
					
					yavru.setSize(310,150); 
					yavru.setLayout(null);
					yavru.setLocation(800,50);
					yavru.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					yavru.add(yavrudugme);
					yavru.add(yavrudugme2);
					yavru.add(baslik2);
					yavru.add(alan);
					yavru.add(baslik);
					
					yavrudugme.setBounds(40,65,80,20);
					yavrudugme.setVisible(true);
					yavrudugme.addActionListener(new Ontanimliveribul());
					yavrudugme.setActionCommand(evethayir.basevet.name());
					
					yavrudugme2.setBounds(180,65,80,20);
					yavrudugme2.setVisible(true);
					yavrudugme2.addActionListener(new Ontanimliveribul());
					yavrudugme2.setActionCommand(evethayir.bashayir.name());
					
					yavru.setVisible(true); 
				}

				@Override
				public void actionPerformed(ActionEvent e) {
					
					if (e.getActionCommand()==evethayir.basevet.name())	{
						Ontanimliveriler.aranankelime=alan.getText().trim();
						
						if (Ontanimliveriler.aranankelime.equals("")) {
							Ontanimliveriler.frame.dispose();
							Ontanimliveriler.ontanimliveriler(Ontanimliveriler.jtalani,Ontanimliveriler.tip);
						    yavru.dispose();
						}
						
						if (Ontanimliveriler.aranankelime.length()<3) {
							Ontanimliveriler.aranankelime="";
							alan.setText("");
							baslik2.setText("En Az 3 Karakter Girmelisiniz");
							return;
						}
						Ontanimliveriler.frame.dispose();
						Ontanimliveriler.ontanimliveriler(Ontanimliveriler.jtalani,Ontanimliveriler.tip);
					    yavru.dispose();
					}
			    
					if (e.getActionCommand()==evethayir.bashayir.name())	{
						Ontanimliveriler.aranankelime="";
					    yavru.dispose();
					}
		}
	}
	
	
}