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
import java.text.ParseException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

public class Pozara   {
	
	static int hatkodu,yuklemegemisikodu,gondericikodu,alicikodu,ydacentekodu,yukkentkodu,yuklimkodu,varlimkodu,sonvarkentkodu;
	
	static JTextField mblnojt,hatjt,yuklemegemisijt,gondericijt,alicijt,
	  ydacentejt,yuklemekentijt,yuklemelimanijt,varislimanijt,sonvariskentijt;
	 
	
	static String [][]tablodata ;
	static Container aticine;
	
	static boolean tarihlimi;
	static String tarih1,tarih2,numara1,numara2,komutset;
	
	public Pozara(Boolean tarihlimi,String tarih1,String tarih2,String numara1,String numara2) {
		
		Pozara.tarihlimi=tarihlimi;
		Pozara.tarih1=tarih1;
		Pozara.tarih2=tarih2;
		Pozara.numara1=numara1;
		Pozara.numara2=numara2;
		
	}
	
	public void pozarama () {
		
		hatkodu=yuklemegemisikodu=gondericikodu=alicikodu=ydacentekodu=yukkentkodu=yuklimkodu=varlimkodu=sonvarkentkodu=0;
	   
		 JFrame frame;
		 
		 JLabel mblnotxt,hattxt,yuklemegemisitxt,gondericitxt,alicitxt,basliktxt,
		              ydacentetxt,yuklemekentitxt,yuklemelimanitxt,varislimanitxt,sonvariskentitxt;
		 
		 JButton dugmeara,hatSECdugme,yuklemegemisiSECdugme,gondericiSECdugme,aliciSECdugme,ydacenteSECdugme,
		         yukkentSECdugme,yuklimSECdugme,varlimSECdugme,sonvarkentSECdugme;
		 
		 JPanel panelalanlar;
		
		 tablodata = new String [0][3];
		 
		 frame= new JFrame("Pozisyon Arama Penceresi");
		 frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		 frame.setLocation(50,150);
		 frame.setSize(850, 390);
		 
		 panelalanlar=new JPanel();
		 panelalanlar.setLayout(null);
		 
		 basliktxt=new JLabel ();
		 basliktxt.setBounds(50, 40, 350, 26);
		 if (tarihlimi) {
			 basliktxt.setText(tarih1+" ile "+tarih2+" Tarihleri Arasýnda Pozisyon Arama");
		 } else {
			 basliktxt.setText(numara1+" ile "+numara2+" Numaralar Arasýnda Pozisyon Arama");
		 }
		 panelalanlar.add(basliktxt);
		 
	    
	     mblnotxt = new JLabel("MBL No :");
		 mblnotxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 mblnotxt.setBounds(5, 75, 100, 26);
		 mblnojt=new JTextField();
		 mblnojt.setBounds(110,75, 230, 26);
		
		 panelalanlar.add(mblnotxt);
	     panelalanlar.add(mblnojt); 
		
		 hattxt = new JLabel("Hat Adý:");
		 hattxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 hattxt.setBounds(5, 100, 100, 26);
		 hatjt=new JTextField();
		 hatjt.setBounds(110,100, 230, 26);
		 hatjt.setEditable(false);
		 hatSECdugme=new JButton("Seç");
		 hatSECdugme.setBounds(345, 100, 60, 25);
		 hatSECdugme.addActionListener(new ActionListener() {     
	            public void actionPerformed(ActionEvent f) {
	            	 if (hatkodu!=0) {
	            		 hatkodu=0;
	            		 hatjt.setText("");
			    		 return;
			    	 }
						
	            	 Ontanimliveriler.ontanimliveriler("pozaramahatlar", "Hat");
			     	 
	            }
	        });
		
		 panelalanlar.add(hattxt);
	     panelalanlar.add(hatjt);
	     panelalanlar.add(hatSECdugme);
	  	 
		 yuklemegemisitxt = new JLabel("Yükleme Gemisi:");
		 yuklemegemisitxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 yuklemegemisitxt.setBounds(5, 125, 100, 26);
		 yuklemegemisijt=new JTextField();
		 yuklemegemisijt.setBounds(110,125, 230, 26);
		 yuklemegemisijt.setEditable(false);
		 yuklemegemisiSECdugme=new JButton("Seç");
		 yuklemegemisiSECdugme.setBounds(345, 125, 60, 25);
		 yuklemegemisiSECdugme.addActionListener(new ActionListener() {     
	            public void actionPerformed(ActionEvent f) {
	            	if (yuklemegemisikodu!=0) {
	            		 yuklemegemisikodu=0;
	            		 yuklemegemisijt.setText("");
			    		 return;
			    	 }
						
			    	 Ontanimliveriler.ontanimliveriler("pozaramayuklemegemisi", "Gemi");
	            }
	        });
		 
		 panelalanlar.add(yuklemegemisiSECdugme);
		 panelalanlar.add(yuklemegemisitxt);
	     panelalanlar.add(yuklemegemisijt);
		 
		 gondericitxt = new JLabel("MBL'de Gönderici:");
		 gondericitxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 gondericitxt.setBounds(5, 150, 100, 26);
		 gondericijt=new JTextField();
		 gondericijt.setBounds(110,150, 230, 26);
		 gondericijt.setEditable(false);
		 gondericiSECdugme=new JButton("Seç");
		 gondericiSECdugme.setBounds(345, 150, 60, 25);
		 gondericiSECdugme.addActionListener(new ActionListener() {     
	            public void actionPerformed(ActionEvent f) {
	            	if (gondericikodu!=0) {
	            		 gondericikodu=0;
	            		 gondericijt.setText("");
			    		 return;
			    	 }
						
			     Sirketkartlari.sirketkartlari("pozaramagonderici");
	            }
	        });
		 panelalanlar.add(gondericiSECdugme);
		 panelalanlar.add(gondericitxt);
	     panelalanlar.add(gondericijt);
	 
		 alicitxt = new JLabel("MBL'de Alýcý:");
		 alicitxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 alicitxt.setBounds(5, 175, 100, 26);
		 alicijt=new JTextField();
		 alicijt.setBounds(110,175, 230, 26);
		 alicijt.setEditable(false);
		 aliciSECdugme=new JButton("Seç");
		 aliciSECdugme.setBounds(345, 175, 60, 25);
		 aliciSECdugme.addActionListener(new ActionListener() {     
	            public void actionPerformed(ActionEvent f) {
	            	if (alicikodu!=0) {
	            		 alicikodu=0;
	            		 alicijt.setText("");
			    		 return;
			    	 }
						
			    Sirketkartlari.sirketkartlari("pozaramaalici");
	            }
	        });
		 panelalanlar.add(aliciSECdugme);
		 panelalanlar.add(alicitxt);
	     panelalanlar.add(alicijt);
		 
		 ydacentetxt = new JLabel("Yurt Dýþý Acente:");
		 ydacentetxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 ydacentetxt.setBounds(5, 200, 100, 26);
		 ydacentejt=new JTextField();
		 ydacentejt.setBounds(110,200, 230, 26);
		 ydacentejt.setEditable(false);
		 ydacenteSECdugme=new JButton("Seç");
		 ydacenteSECdugme.setBounds(345, 200, 60, 25);
		 ydacenteSECdugme.addActionListener(new ActionListener() {     
	            public void actionPerformed(ActionEvent f) {
	            	if (ydacentekodu!=0) {
	            		 ydacentekodu=0;
	            		 ydacentejt.setText("");
			    		 return;
			    	 }
						
			    Sirketkartlari.sirketkartlari("pozaramaydacente");
	            }
	        });
		 panelalanlar.add(ydacenteSECdugme);
		 
		 panelalanlar.add(ydacentetxt);
	     panelalanlar.add(ydacentejt);
		
	     yuklemekentitxt = new JLabel("Yükleme Kenti:");
		 yuklemekentitxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 yuklemekentitxt.setBounds(5, 225, 100, 26);
		 yuklemekentijt=new JTextField();
		 yuklemekentijt.setBounds(110,225, 230, 26);
		 yuklemekentijt.setEditable(false);
		 yukkentSECdugme=new JButton("Seç");
		 yukkentSECdugme.setBounds(345, 225, 60, 25);
		 yukkentSECdugme.addActionListener(new ActionListener() {     
	            public void actionPerformed(ActionEvent f) {
	            	if (yukkentkodu!=0) {
	            		yukkentkodu=0;
			    		 yuklemekentijt.setText("");
			    		 return;
			    	 }
						
			  	 Ontanimliveriler.ontanimliveriler("pozaramayukleme", "Kent");
	            }
	        });
		 panelalanlar.add(yukkentSECdugme);
		 panelalanlar.add(yuklemekentitxt);
	     panelalanlar.add(yuklemekentijt);
		 
		 yuklemelimanitxt = new JLabel("Yükleme Limaný:");
		 yuklemelimanitxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 yuklemelimanitxt.setBounds(5, 250, 100, 26);
		 yuklemelimanijt=new JTextField();
		 yuklemelimanijt.setBounds(110,250, 230, 26);
		 yuklemelimanijt.setEditable(false);
		 yuklimSECdugme=new JButton("Seç");
		 yuklimSECdugme.setBounds(345, 250, 60, 25);
		 yuklimSECdugme.addActionListener(new ActionListener() {     
	            public void actionPerformed(ActionEvent f) {
	            	if (yuklimkodu!=0) {
	            		yuklimkodu=0;
			    		 yuklemelimanijt.setText("");
			    		 return;
			    	 }
						
			   Ontanimliveriler.ontanimliveriler("pozaramayukleme", "Liman");
	            }
	        });
		 panelalanlar.add(yuklimSECdugme);
		 panelalanlar.add(yuklemelimanitxt);
	     panelalanlar.add(yuklemelimanijt);
		 
		 varislimanitxt = new JLabel("Varýþ Limaný:");
		 varislimanitxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 varislimanitxt.setBounds(5, 275, 100, 26);
		 varislimanijt=new JTextField();
		 varislimanijt.setBounds(110,275, 230, 26);
		 varislimanijt.setEditable(false);
		 varlimSECdugme=new JButton("Seç");
		 varlimSECdugme.setBounds(345, 275, 60, 25);
		 varlimSECdugme.addActionListener(new ActionListener() {     
	            public void actionPerformed(ActionEvent f) {
	            	if (varlimkodu!=0) {
	            		varlimkodu=0;
			    		 varislimanijt.setText("");
			    		 return;
			    	 }
						
			    Ontanimliveriler.ontanimliveriler("pozaramavaris", "Liman");
	            }
	        });
		 panelalanlar.add(varlimSECdugme);
		 panelalanlar.add(varislimanitxt);
	     panelalanlar.add(varislimanijt);
		 
		 sonvariskentitxt = new JLabel("Son Varýþ Kenti:");
		 sonvariskentitxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 sonvariskentitxt.setBounds(5, 300, 100, 26);
		 sonvariskentijt=new JTextField();
		 sonvariskentijt.setBounds(110,300, 230, 26);
		 sonvariskentijt.setEditable(false);
		 sonvarkentSECdugme=new JButton("Seç");
		 sonvarkentSECdugme.setBounds(345, 300, 60, 25);
		 sonvarkentSECdugme.addActionListener(new ActionListener() {     
	            public void actionPerformed(ActionEvent f) {
	            	if (sonvarkentkodu!=0) {
	            		sonvarkentkodu=0;
			    		 sonvariskentijt.setText("");
			    		 return;
			    	 }
						
			    	Ontanimliveriler.ontanimliveriler("pozaramasonvaris", "Kent");
	            }
	        });
		 panelalanlar.add(sonvarkentSECdugme);
		 panelalanlar.add(sonvariskentitxt);
	     panelalanlar.add(sonvariskentijt);
	     
	     dugmeara=new JButton("ARA");
		 dugmeara.setBounds(150, 15, 90, 20);  
		 dugmeara.addActionListener(new ActionListener() {     
	            public void actionPerformed(ActionEvent f) {
	            	
	            	bakbakalimvarmiymis(mblnojt.getText());
	            }
	        });
		 panelalanlar.add(dugmeara);   
	    
	     aticine = frame.getContentPane();
	     aticine.add(panelalanlar, BorderLayout.CENTER);
	     Sonuclistesitablosu.sonuctablosu ();
		 frame.setVisible(true);
		
	}   // yukarama metodu sonu

	protected static void bakbakalimvarmiymis(String mblno) {
		
		boolean enazbirisecildi=false;
		
		String sqlkomut="SELECT * FROM pozisyonlar WHERE ";
		try{                                              
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection(
					"jdbc:mysql://"+Anasinif.sqlip+"/nal2000?useUnicode=true&characterEncoding=UTF-8",Anasinif.sqluser,Anasinif.sqlpass);
			
			if (!mblno.equals("")) {
				
				sqlkomut=sqlkomut+" mblno LIKE '%" +mblno+ "%' ";
				enazbirisecildi=true;
				
			}
	        
            if (hatkodu!=0) {
            	if(enazbirisecildi==true) {
					sqlkomut=sqlkomut+" and ";
				}
            	sqlkomut=sqlkomut+" hatadikodu=" +hatkodu+"  ";
            	enazbirisecildi=true;
            }
            
            if (yuklemegemisikodu!=0) {
            	if(enazbirisecildi==true) {
					sqlkomut=sqlkomut+" and ";
				}
            	sqlkomut=sqlkomut+" yuklemegemisikodu =" +yuklemegemisikodu+"  ";
            	enazbirisecildi=true;
            }
			
            if (gondericikodu!=0) {
            	
            	if(enazbirisecildi==true) {
					sqlkomut=sqlkomut+" and ";
				}
            	sqlkomut=sqlkomut+" mblgondericikodu =" +gondericikodu+"  ";
            	enazbirisecildi=true;
            	
            }
             
            if (alicikodu!=0) {
            	
            	if(enazbirisecildi==true) {
					sqlkomut=sqlkomut+" and ";
				}
            	sqlkomut=sqlkomut+" mblalicikodu=" +alicikodu+"  ";
            	enazbirisecildi=true;
            	
            }
            
            if (ydacentekodu!=0) {
            	
            	if(enazbirisecildi==true) {
					sqlkomut=sqlkomut+" and ";
				}
            	sqlkomut=sqlkomut+" yurtdisigemiacentesikodu=" +ydacentekodu+"  ";
            	enazbirisecildi=true;
            	
            }
            
            if (yukkentkodu!=0) {
            	
            	if(enazbirisecildi==true) {
					sqlkomut=sqlkomut+" and ";
				}
            	sqlkomut=sqlkomut+" yuklemekentikodu=" +yukkentkodu+"  ";
            	enazbirisecildi=true;
            	
            }
            
            if (yuklimkodu!=0) {
            	
            	if(enazbirisecildi==true) {
					sqlkomut=sqlkomut+" and ";
				}
            	sqlkomut=sqlkomut+" yuklemelimanikodu=" +yuklimkodu+"  ";
            	enazbirisecildi=true;
            	
            }
            
            if (varlimkodu!=0) {
            	
            	if(enazbirisecildi==true) {
					sqlkomut=sqlkomut+" and ";
				}
            	sqlkomut=sqlkomut+" varislimanikodu=" +varlimkodu+"  ";
            	enazbirisecildi=true;
            	
            }
            
            if (sonvarkentkodu!=0) {
            	
            	if(enazbirisecildi==true) {
					sqlkomut=sqlkomut+" and ";
				}
            	sqlkomut=sqlkomut+" sonvariskentikodu=" +sonvarkentkodu+"  ";
            	enazbirisecildi=true;
            	
            }
            
            if(enazbirisecildi==false) {
				return;
			}
            
            if(tarihlimi) {
            	sqlkomut=sqlkomut+" and mbltarih between '"+tarih1+"' and '"+tarih2+"'"; 
            }else {
            	sqlkomut=sqlkomut+" and pozno between '"+numara1+"' and '"+numara2+"'";
            }
    
			Statement stmt=con.createStatement();  
			ResultSet rs=stmt.executeQuery(sqlkomut); 
			   		
			int sayac=0;
			while(rs.next()) {
				sayac++;    // kaç tane aradýðýmýz veriden var;
			}
		    
			if (sayac>100) {  // en fazla 100 kayýta izin var
				sayac=100;
			}
			
			tablodata = new String [sayac][3];
			con.close();  
			
			}catch(Exception e){ System.out.println(e);}
		
		try{          // bulunanlarý tablodata dizinine aktar                                      
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection(
					"jdbc:mysql://"+Anasinif.sqlip+"/nal2000?useUnicode=true&characterEncoding=UTF-8",Anasinif.sqluser,Anasinif.sqlpass);
			
			Statement stmt=con.createStatement();  
			ResultSet rs=stmt.executeQuery(sqlkomut); 
			   		
			int sayac=0;
			
			while(rs.next()) {
				tablodata [sayac][0]=rs.getString(1);
				tablodata [sayac][1]=Ontanimliveriler.sirketkoducoz(Integer.parseInt(rs.getString(31)))[1];
				tablodata [sayac][2]=Ontanimliveriler.sirketkoducoz(Integer.parseInt(rs.getString(32)))[1];
				
			sayac++;	
			    if (sayac>99) {
			    	break;
			    }
			}
		
			con.close();  
			
			}catch(Exception e){ System.out.println(e);}
			
		Sonuclistesitablosu.tableModel.fireTableDataChanged();
		
	}// bakbakalim metodu sonu
	
	@SuppressWarnings("serial")
	public static class Sonuclistesitablosu extends JPanel {
		static Tablomodeli tableModel;
		 static JScrollPane scrollPane;
		String secilenpoz="";
	
		public Sonuclistesitablosu() {
	        initializePanel();
	    }

	    private void initializePanel() {
	       
	       tableModel = new Tablomodeli();

	        JTable table = new JTable(tableModel);
	        
	        table.setFillsViewportHeight(true);
	       table.setPreferredScrollableViewportSize(new Dimension(500,500));
			   
		    table.getColumnModel().getColumn(0).setMaxWidth(100);    
		    
		    table.setFocusable(false);
		      table.addMouseListener(new MouseAdapter() {   // mouse listener kýsmýsý
		         public void mouseClicked(MouseEvent me) {
		        	
		            if (me.getClickCount() == 2) {     
		            	if( Pozisyon.pozbayrak==true) {
				    		return;
				    	} else {
				    		try {
				    			Denizyoluanaekran.secilenpozisyon=secilenpoz;
								Pozisyon.pozisyon("düzenle");
							} catch (ParseException e) {	e.printStackTrace();
							}
				    	}
			        }
		         }
		      });
		    
		    ListSelectionModel cellSelectionModel = table.getSelectionModel();               // listeden seçileni dinleme kýsmý
	        cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

	        cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
	          public void valueChanged(ListSelectionEvent e) {
	           
	        	  if(!e.getValueIsAdjusting())  {    						// bu IF iki defa yazmamasý için konuldu. 
	      
	        		  int selectedRow     = table.getSelectedRow();
	          		secilenpoz = (String) table.getValueAt(selectedRow,0 );
	          		          		
	        	  }
	          }
	     });
	        
		    scrollPane = new JScrollPane(table);
	        scrollPane.setPreferredSize(new Dimension(420,400));
	        this.setLayout(new BorderLayout());
	        this.add(scrollPane, BorderLayout.CENTER);
	    }

	    public static void showFrame() {
	        JPanel panel = new Sonuclistesitablosu();
	        panel.setOpaque(true);

	   
	     Pozara.aticine.add(scrollPane, BorderLayout.EAST);  
	    }

	    class Tablomodeli extends AbstractTableModel {
	     
	        private  String[] columnNames = { "POZ NO ", "MBL'de GÖNDEREN","MBL'de ALICI" };

	        public int getRowCount() {
	           
	        	return tablodata.length;
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
	        		rowIndex=tablodata.length-1;
	        		        		
	        	}
	            return tablodata[rowIndex][columnIndex];
	        }
	    }
	   public static void sonuctablosu () {
	         SwingUtilities.invokeLater(new Runnable() {
	          public void run() {
	                showFrame();
	            }
	        });
	    }
	}
	
	} // sinif sonu ;