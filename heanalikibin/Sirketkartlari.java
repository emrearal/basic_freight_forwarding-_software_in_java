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
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

public class Sirketkartlari implements ActionListener {
	
	private enum actions {
		buttonekle,buttonduzenle,buttonsil,buttonsec,buttoncaribul,buttoncarigoster
		 }

	static JFrame frame ;
	static JPanel panel4,panel3;
	static JButton buttonekle,buttonduzenle,buttonsil,buttonsec,buttoncaribul,buttoncarigoster;
	static JMenuItem firmahesaplari,yedekal,geriyukle,notdefteri,hesapmakinesi
					,hakkinda,yardimyardim,mizan,alacaklar,borclar;
	static JScrollPane sp;
	static JTable tablo;
	static JLabel baslik2;
	static String selectedData ="",aranankelime="",secilifirmaadi="",secilifirmatel="",sqlkomut1="",sqlkomut2="";
	
	static boolean eklebasildi=false,silebasildi=false,duzenlebasildi=false,gosterbasildi=false;
	static String kimsoruyor;
	static String gecerlikomut="";
				
	public static void sirketkartlari(String sorankim) {
		
		kimsoruyor=sorankim;
		selectedData="";
		secilifirmaadi="";
	    
		buttonekle = new JButton("   Þirket Kartý Ekle    ");                                     //Düðmeler 
		buttonekle.addActionListener(new Sirketkartlari());
		buttonekle.setActionCommand(actions.buttonekle.name());
		
		buttonduzenle = new JButton("Þirket Kartý Düzenle");
		buttonduzenle.addActionListener(new Sirketkartlari());
		buttonduzenle.setActionCommand(actions.buttonduzenle.name());
		
		buttonsil = new JButton("   Þirket Kartý Sil     ");
		buttonsil.addActionListener(new Sirketkartlari());
		buttonsil.setActionCommand(actions.buttonsil.name());
				
		buttonsec = new JButton("Bu Þirketi Seç");
		buttonsec.addActionListener(new Sirketkartlari());
		buttonsec.setActionCommand(actions.buttonsec.name());
		
		buttoncaribul = new JButton("    Þirket Kartý Bul      ");
		buttoncaribul.addActionListener(new Sirketkartlari());
		buttoncaribul.setActionCommand(actions.buttoncaribul.name());
		
		buttoncarigoster=new JButton ("Þirket Kartý Göster");
		buttoncarigoster.addActionListener(new Sirketkartlari());
		buttoncarigoster.setActionCommand(actions.buttoncarigoster.name());
		
		baslik2 = new JLabel("'Silme','Düzenleme',''Seçim' iþlemleri için tablo üzerinde þirket adýný týklayýn" );
			
		panel3=new JPanel();
		panel4=new JPanel();
		frame = new JFrame("ÞÝRKET KARTLARI PENCERESÝ ");
		
		if (kimsoruyor.equals("anaekran")) {
			frame = new JFrame("                                                                             *** ÞÝRKET KARTLARI PENCERESÝ ***");	
		}
		
		
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		       
       panel3.add(buttoncarigoster);
       panel3.add(buttoncaribul);   
	   panel3.add(buttonekle);
	   //panel3.add(buttonsil);  Þirkat kartý silmeyi þimdilik iptal ettik. ileride sýkýntý çýkartabilir
	   panel3.add(buttonduzenle);
	   if (!kimsoruyor.equals("anaekran")) {
		   panel3.add(buttonsec);   
	   }
	   panel4.add(baslik2);
	   
	   sqlkomut1="SELECT * FROM sirketkartlari WHERE sirketkart_1unvan LIKE '%" +aranankelime+ "%';";
	   sqlkomut2="select * from sirketkartlari";
	   
	   String[] sutun = { "KOD","Firma Ünvaný","Þehir","Telefon"};                      // Tablo oluþturma 
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
						
						 String[][] satir= new String[cnk][4];     // sonuç sayýsýna göre array boyutlama
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
						 satir[cnk][2] = rs.getString(5);
						 satir[cnk][3] = rs.getString(3);
						 cnk++;
						}
						 con.close();  
						 cnk=0;
						}catch(Exception e){ System.out.println(e);}  
				
				tablo= new JTable(satir,sutun);  
				tablo.setDefaultEditor(Object.class, null);	// tabloya elle düzeltme yapýlamasýn
				sp=new JScrollPane(tablo); 
				sp.setPreferredSize(new Dimension(700, 350));
				
				DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
	
		        tablo.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);     //tabloyu ortama
		        tablo.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		        tablo.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		        tablo.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
		        
		        tablo.setFocusable(false);
			      tablo.addMouseListener(new MouseAdapter() {   // mouse listener kýsmýsý
			         public void mouseClicked(MouseEvent me) {
			            if (me.getClickCount() == 2) {     
			            	
			            	 if (kimsoruyor.equals("anaekran")) {
			            		 gosterbasildi=true;
			                	 Sirketkartlaridegirtireklesil.ekle();
			        			
			          	   }else {
			          		 sirketikimegondereyim();
				    			frame.dispose();
			          	   }
			            	
				          }
			         }
			      });
		        
				TableColumnModel columnModel = tablo.getColumnModel();
				columnModel.getColumn(0).setMaxWidth(50);
				columnModel.getColumn(1).setMaxWidth(550);
				columnModel.getColumn(2).setMaxWidth(100);
				columnModel.getColumn(3).setMaxWidth(200);
			    
			    aranankelime="";
	
	    Container contentPane = frame.getContentPane();
	  
	    contentPane.add(sp, BorderLayout.PAGE_END);
	    contentPane.add(panel3, BorderLayout.PAGE_START);
	    contentPane.add(panel4, BorderLayout.CENTER);
	   
	    frame.pack();
        frame.setLocationRelativeTo(null);
	    
        frame.setVisible(true);
	    
	     
	    ListSelectionModel cellSelectionModel = tablo.getSelectionModel();               // listeden seçileni dinleme kýsmý
        cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
          public void valueChanged(ListSelectionEvent e) {
           
        	  if(!e.getValueIsAdjusting())  {    						// bu IF iki defa yazmamasý için konuldu. 
      
        		  int selectedRow     = tablo.getSelectedRow();
          		selectedData = (String) tablo.getValueAt(selectedRow,0 );
          		secilifirmaadi=(String) tablo.getValueAt(selectedRow,1 );
          		secilifirmatel=(String) tablo.getValueAt(selectedRow,3 );
        	  }
          }
     });
	  	
	}
	
static public void sirketikimegondereyim() {
		
		switch (kimsoruyor) {
		case  "musteri":
			Yuk.musterikodu=Integer.parseInt(Sirketkartlari.selectedData);
	    	Yuk. musterijt.setText(Sirketkartlari.secilifirmaadi);
	    	Yuk. musterijt.setCaretPosition(0);
			frame.dispose();
			 break;
		case  "uretici":
			Yuk.ureticikodu=Integer.parseInt(Sirketkartlari.selectedData);
	    	Yuk. ureticijt.setText(Sirketkartlari.secilifirmaadi);
	    	Yuk. ureticijt.setCaretPosition(0);
			frame.dispose();
			 break;
		case  "gonderici":
			Yuk.gondericikodu=Integer.parseInt(Sirketkartlari.selectedData);
	    	Yuk. gondericijt.setText(Sirketkartlari.secilifirmaadi);
	    	Yuk. gondericijt.setCaretPosition(0);
			frame.dispose();
			 break;
		case  "alici":
			Yuk.alicikodu=Integer.parseInt(Sirketkartlari.selectedData);
	    	Yuk. alicijt.setText(Sirketkartlari.secilifirmaadi);
	    	Yuk. alicijt.setCaretPosition(0);
			frame.dispose();
			 break;
		case  "notify1":
			Yuk.notify1kodu=Integer.parseInt(Sirketkartlari.selectedData);
	    	Yuk. notify1jt.setText(Sirketkartlari.secilifirmaadi);
	    	Yuk. notify1jt.setCaretPosition(0);
			frame.dispose();
			 break;
		case  "notify2":
			Yuk.notify2kodu=Integer.parseInt(Sirketkartlari.selectedData);
	    	Yuk. notify2jt.setText(Sirketkartlari.secilifirmaadi);
	    	Yuk. notify2jt.setCaretPosition(0);
			frame.dispose();
			 break;
		case  "ydacente":
			Yuk.ydacentekodu=Integer.parseInt(Sirketkartlari.selectedData);
	    	Yuk. ydacentejt.setText(Sirketkartlari.secilifirmaadi);
	    	Yuk. ydacentejt.setCaretPosition(0);
			frame.dispose();
			 break;
		case  "faturakesilenfirma":
			Yuk.faturakesilenfirmakodu=Integer.parseInt(Sirketkartlari.selectedData);
	    	Yuk. faturakesilenfirmajt.setText(Sirketkartlari.secilifirmaadi);
	    	Yuk. faturakesilenfirmajt.setCaretPosition(0);
			frame.dispose();
		     break;
		case  "pozkartyurticigemiacentesi":
			Pozisyon.yurticigemiacentesikodu=Integer.parseInt(Sirketkartlari.selectedData);
	    	Pozisyon.yurticigemiacentesijt.setText(Sirketkartlari.secilifirmaadi);
	    	Pozisyon.yurticigemiacentesijt.setCaretPosition(0);
			frame.dispose();
		     break;   //
		case  "pozkartaktarmaacentesi":
			Pozisyon.aktarmaacentesikodu=Integer.parseInt(Sirketkartlari.selectedData);
	    	Pozisyon.aktarmaacentesijt.setText(Sirketkartlari.secilifirmaadi);
	    	Pozisyon.aktarmaacentesijt.setCaretPosition(0);
			frame.dispose();
		     break;     
		case  "pozkartydacente":
			Pozisyon.ydacentekodu=Integer.parseInt(Sirketkartlari.selectedData);
	    	Pozisyon.ydacentejt.setText(Sirketkartlari.secilifirmaadi);
	    	Pozisyon.ydacentejt.setCaretPosition(0);
			frame.dispose();
		     break;    
		case  "pozkartyurtdisigemiacentesi":
			Pozisyon.yurtdisigemiacentesikodu=Integer.parseInt(Sirketkartlari.selectedData);
	    	Pozisyon.yurtdisigemiacentesijt.setText(Sirketkartlari.secilifirmaadi);
	    	Pozisyon.yurtdisigemiacentesijt.setCaretPosition(0);
			frame.dispose();
		     break;     
		case  "pozkarthatadi":
			Pozisyon.hatadikodu=Integer.parseInt(Sirketkartlari.selectedData);
	    	Pozisyon.hatadijt.setText(Sirketkartlari.secilifirmaadi);
	    	Pozisyon.hatadijt.setCaretPosition(0);
			frame.dispose();
		     break;
		case  "pozgelirekle":
			Pozgelirekle.sirketkodu=Integer.parseInt(Sirketkartlari.selectedData);
			Pozgelirekle.kimejt.setText(Sirketkartlari.secilifirmaadi);
			Pozgelirekle.kimejt.setCaretPosition(0);
			frame.dispose();
		     break;  
		case  "pozgiderekle":
			Pozgiderekle.sirketkodu=Integer.parseInt(Sirketkartlari.selectedData);
			Pozgiderekle.kimejt.setText(Sirketkartlari.secilifirmaadi);
			Pozgiderekle.kimejt.setCaretPosition(0);
			frame.dispose();
		     break;  
		     
		case  "pozmblgonderici":
			Pozisyon.mblgondericikodu=Integer.parseInt(Sirketkartlari.selectedData);
	    	Pozisyon.mblgondericijt.setText(Sirketkartlari.secilifirmaadi);
	    	Pozisyon.mblgondericijt.setCaretPosition(0);
			frame.dispose();
		     break;      
		     
		case  "pozmblalici":
			Pozisyon.mblalicikodu=Integer.parseInt(Sirketkartlari.selectedData);
	    	Pozisyon.mblalicijt.setText(Sirketkartlari.secilifirmaadi);
	    	Pozisyon.mblalicijt.setCaretPosition(0);
			frame.dispose();
		     break;  
		     
		case  "pozmblnotify":
			Pozisyon.mblnotifykodu=Integer.parseInt(Sirketkartlari.selectedData);
	    	Pozisyon.mblnotifyjt.setText(Sirketkartlari.secilifirmaadi);
	    	Pozisyon.mblnotifyjt.setCaretPosition(0);
			frame.dispose();
		     break; 
		     
		case "yukaramamusteri":
			Yukara.musterikodu=Integer.parseInt(Sirketkartlari.selectedData);
			Yukara.musterijt.setText(Sirketkartlari.secilifirmaadi);
			Yukara.musterijt.setCaretPosition(0);
			frame.dispose();
		     break; 
		     
		case "yukaramauretici":
			Yukara.ureticikodu=Integer.parseInt(Sirketkartlari.selectedData);
			Yukara.ureticijt.setText(Sirketkartlari.secilifirmaadi);
			Yukara.ureticijt.setCaretPosition(0);
			frame.dispose();
		     break; 
		     
		case "yukaramagonderici":
			Yukara.gondericikodu=Integer.parseInt(Sirketkartlari.selectedData);
			Yukara.gondericijt.setText(Sirketkartlari.secilifirmaadi);
			Yukara.gondericijt.setCaretPosition(0);
			frame.dispose();
		     break; 
		     
		case "yukaramaalici":
			Yukara.alicikodu=Integer.parseInt(Sirketkartlari.selectedData);
			Yukara.alicijt.setText(Sirketkartlari.secilifirmaadi);
			Yukara.alicijt.setCaretPosition(0);
			frame.dispose();
		     break; 
		     
		case "yukaramaydacente":
			Yukara.ydacentekodu=Integer.parseInt(Sirketkartlari.selectedData);
			Yukara.ydacentejt.setText(Sirketkartlari.secilifirmaadi);
			Yukara.ydacentejt.setCaretPosition(0);
			frame.dispose();
		     break; 
		     
		case "yukaramafatkesfir":
			Yukara.fatkesfirkodu=Integer.parseInt(Sirketkartlari.selectedData);
			Yukara.faturakesilenfirmajt.setText(Sirketkartlari.secilifirmaadi);
			Yukara.faturakesilenfirmajt.setCaretPosition(0);
			frame.dispose();
		     break; 
		     
		case "pozaramagonderici":
			Pozara.gondericikodu=Integer.parseInt(Sirketkartlari.selectedData);
			Pozara.gondericijt.setText(Sirketkartlari.secilifirmaadi);
			Pozara.gondericijt.setCaretPosition(0);
			frame.dispose();
		     break; 
		     
		case "pozaramaalici":
			Pozara.alicikodu=Integer.parseInt(Sirketkartlari.selectedData);
			Pozara.alicijt.setText(Sirketkartlari.secilifirmaadi);
			Pozara.alicijt.setCaretPosition(0);
			frame.dispose();
		     break; 
		     
		case "pozaramaydacente":
			Pozara.ydacentekodu=Integer.parseInt(Sirketkartlari.selectedData);
			Pozara.ydacentejt.setText(Sirketkartlari.secilifirmaadi);
			Pozara.ydacentejt.setCaretPosition(0);
			frame.dispose();
		     break; 
		     
		case "toplufaturasilme":
			Denizyoluanaekran.topfatsilsirketkodu=Integer.parseInt(Sirketkartlari.selectedData);
			Denizyoluanaekran.sirketadicey.setText(Sirketkartlari.secilifirmaadi);
			Denizyoluanaekran.sirketadicey.setCaretPosition(0);
			frame.dispose();
		     break; 
		     
		case "faturaaramasirketadi":
			Faturaarama.sirketkodu=Integer.parseInt(Sirketkartlari.selectedData);
			Faturaarama.sirketadijt.setText(Sirketkartlari.secilifirmaadi);
			Faturaarama.sirketadijt.setCaretPosition(0);
			frame.dispose();
		     break; 
				     
	}
		 return;
		}
	@Override
	public void actionPerformed(ActionEvent e) {
		
         
		if (e.getActionCommand()==actions.buttonsec.name()) {
			
			if (selectedData.equals("")) {
				
				return;
			}
			
			sirketikimegondereyim();
			frame.dispose();
		
		}
		
		if (e.getActionCommand()==actions.buttonekle.name()) {
			eklebasildi=true;
			
			Sirketkartlaridegirtireklesil.ekle();
				
		}
		
		if (e.getActionCommand()==actions.buttonduzenle.name()) {
			
			if (selectedData.equals("")) {
				return;
			}
		
			duzenlebasildi=true;
			Sirketkartlaridegirtireklesil.ekle();
			
		}

		if (e.getActionCommand()==actions.buttonsil.name()) {
	
			if (selectedData.equals("")) {
				return;
			}
			silebasildi=true;
			Sirketkartlaridegirtireklesil.ekle();
	
		}
	
		if (e.getActionCommand()==actions.buttoncaribul.name()) {
			
			Sirketbul.nebulayim();
			
		}
		
        if (e.getActionCommand()==actions.buttoncarigoster.name()) {
        	if (selectedData.equals("")) {
				return;
			}
        	
        	 gosterbasildi=true;
        	 Sirketkartlaridegirtireklesil.ekle();
			
		}
		eklebasildi=false;
		duzenlebasildi=false;
		silebasildi=false;
		gosterbasildi=false;
		}
	
	public static class Sirketbul implements ActionListener {
		
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
					yavru = new JDialog(Sirketkartlari.frame,"Þirket Arama Ekraný",true); 
					yavru.setResizable(false);
					
					alan=new JTextField() ;
					alan.setBounds(30,37,240,26);
						
					baslik2 = new JLabel("Aranacak Firma Adýný Yazýn " );
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
					yavrudugme.addActionListener(new Sirketbul());
					yavrudugme.setActionCommand(evethayir.basevet.name());
					
					yavrudugme2.setBounds(180,65,80,20);
					yavrudugme2.setVisible(true);
					yavrudugme2.addActionListener(new Sirketbul());
					yavrudugme2.setActionCommand(evethayir.bashayir.name());
					
					yavru.setVisible(true); 
				}

				@Override
				public void actionPerformed(ActionEvent e) {
					
					if (e.getActionCommand()==evethayir.basevet.name())	{
						Sirketkartlari.aranankelime=alan.getText().trim();
						
						if (Sirketkartlari.aranankelime.equals("")) {
							Sirketkartlari.frame.dispose();
							Sirketkartlari.sirketkartlari(Sirketkartlari.kimsoruyor);
						    yavru.dispose();
						}
						
						if (Sirketkartlari.aranankelime.length()<3) {
							Sirketkartlari.aranankelime="";
							alan.setText("");
							baslik2.setText("En Az 3 Karakter Girmelisiniz");
							return;
						}
						Sirketkartlari.frame.dispose();
						Sirketkartlari.sirketkartlari(Sirketkartlari.kimsoruyor);
					    yavru.dispose();
					}
			    
					if (e.getActionCommand()==evethayir.bashayir.name())	{
						Sirketkartlari.aranankelime="";
					    yavru.dispose();
					}
		}

	}
   }