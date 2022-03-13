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

public class Yukara   {
	
	static int musterikodu,ureticikodu,gondericikodu,alicikodu,ydacentekodu,fatkesfirkodu,yukkentkodu,yuklimkodu,varlimkodu,sonvarkentkodu;
	
	 static JTextField hblnojt,musterijt,ureticijt,gondericijt,alicijt,
	  ydacentejt,faturakesilenfirmajt,yuklemekentijt,yuklemelimanijt,varislimanijt,sonvariskentijt,
	  prefixjt,suffixjt;
	
	static String [][]tablodata ;
	static Container aticine;
	boolean tarihlimi;
	String tarih1,tarih2,numara1,numara2,komutset;
	
	public Yukara(Boolean tarihlimi,String tarih1,String tarih2,String numara1,String numara2) {
			
			this.tarihlimi=tarihlimi;
			this.tarih1=tarih1;
			this.tarih2=tarih2;
			this.numara1=numara1;
			this.numara2=numara2;
			
		}
	
	void yukarama () {
		
		musterikodu=ureticikodu=gondericikodu=alicikodu=ydacentekodu=fatkesfirkodu=yukkentkodu=yuklimkodu=varlimkodu=sonvarkentkodu=0;
	   
		JFrame frame;
		 
		 JLabel hblnotxt,musteritxt,ureticitxt,gondericitxt,alicitxt,
		              ydacentetxt,faturakesilenfirmatxt,yuklemekentitxt,yuklemelimanitxt,varislimanitxt,sonvariskentitxt,
		              konteynernotxt,basliktxt;
		 
		 JButton dugmeara,musteriSECdugme,ureticiSECdugme,gondericiSECdugme,aliciSECdugme,ydacenteSECdugme,fatkesfirSECdugme,
		         yukkentSECdugme,yuklimSECdugme,varlimSECdugme,sonvarkentSECdugme;
		 
		 JPanel panelalanlar;
		
		 tablodata = new String [0][3];
		 frame= new JFrame("Yük Arama Penceresi");
		 frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		 frame.setLocation(50,150);
		 frame.setSize(850, 430);
		 panelalanlar=new JPanel();
		 panelalanlar.setLayout(null);
		 
		 basliktxt=new JLabel ();
		 basliktxt.setBounds(50, 40, 350, 26);
		 if (tarihlimi) {
			 basliktxt.setText(tarih1+" ile "+tarih2+" Tarihleri Arasýnda Yük Arama");
		 } else {
			 basliktxt.setText(numara1+" ile "+numara2+" Numaralar Arasýnda Yük Arama");
		 }
		 panelalanlar.add(basliktxt);
	    
	     hblnotxt = new JLabel("HBL No :");
		 hblnotxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 hblnotxt.setBounds(5, 75, 100, 26);
		 panelalanlar.add(hblnotxt);
		 hblnojt=new JTextField();
		 hblnojt.setBounds(110,75, 230, 26);
		
		 panelalanlar.add(hblnojt);
		 
		
		 
		 musteritxt = new JLabel("Müþteri Adý:");
		 musteritxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 musteritxt.setBounds(5, 100, 100, 26);
		 musterijt=new JTextField();
		 musterijt.setBounds(110,100, 230, 26);
		 musterijt.setEditable(false);
		 musteriSECdugme=new JButton("Seç");
		 musteriSECdugme.setBounds(345, 100, 60, 25);
		 musteriSECdugme.addActionListener(new ActionListener() {     
	            public void actionPerformed(ActionEvent f) {
	            	 if (musterikodu!=0) {
	            		 musterikodu=0;
	            		 musterijt.setText("");
			    		 return;
			    	 }
						
			    	 Sirketkartlari.sirketkartlari("yukaramamusteri");
			     	 
	            }
	        });
		
		 panelalanlar.add(musteritxt);
	     panelalanlar.add(musterijt);
	     panelalanlar.add(musteriSECdugme);
	  	 
		 ureticitxt = new JLabel("Üretici Adý:");
		 ureticitxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 ureticitxt.setBounds(5, 125, 100, 26);
		 ureticijt=new JTextField();
		 ureticijt.setBounds(110,125, 230, 26);
		 ureticijt.setEditable(false);
		 ureticiSECdugme=new JButton("Seç");
		 ureticiSECdugme.setBounds(345, 125, 60, 25);
		 ureticiSECdugme.addActionListener(new ActionListener() {     
	            public void actionPerformed(ActionEvent f) {
	            	if (ureticikodu!=0) {
	            		 ureticikodu=0;
	            		 ureticijt.setText("");
			    		 return;
			    	 }
						
			    	 Sirketkartlari.sirketkartlari("yukaramauretici");
	            }
	        });
		 panelalanlar.add(ureticiSECdugme);
		 panelalanlar.add(ureticitxt);
	     panelalanlar.add(ureticijt);
		 
		 gondericitxt = new JLabel("Gönderici Adý:");
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
						
			    	 Sirketkartlari.sirketkartlari("yukaramagonderici");
	            }
	        });
		 panelalanlar.add(gondericiSECdugme);
		 
		 panelalanlar.add(gondericitxt);
	     panelalanlar.add(gondericijt);
	 
		 alicitxt = new JLabel("Alýcý Adý:");
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
						
			    	 Sirketkartlari.sirketkartlari("yukaramaalici");
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
						
			    	 Sirketkartlari.sirketkartlari("yukaramaydacente");
	            }
	        });
		 panelalanlar.add(ydacenteSECdugme);
		 
		 panelalanlar.add(ydacentetxt);
	     panelalanlar.add(ydacentejt);
		 
		 faturakesilenfirmatxt = new JLabel("Fat.Kesilen Firma:");
		 faturakesilenfirmatxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 faturakesilenfirmatxt.setBounds(5, 225, 100, 26);
		 faturakesilenfirmajt=new JTextField();
		 faturakesilenfirmajt.setBounds(110,225, 230, 26);
		 faturakesilenfirmajt.setEditable(false);
		 fatkesfirSECdugme=new JButton("Seç");
		 fatkesfirSECdugme.setBounds(345, 225, 60, 25);
		 fatkesfirSECdugme.addActionListener(new ActionListener() {     
	            public void actionPerformed(ActionEvent f) {
	            	if (fatkesfirkodu!=0) {
	            		 fatkesfirkodu=0;
	            		 faturakesilenfirmajt.setText("");
			    		 return;
			    	 }
						
			    	 Sirketkartlari.sirketkartlari("yukaramafatkesfir");
	            }
	        });
		 panelalanlar.add(fatkesfirSECdugme);
		
		 panelalanlar.add(faturakesilenfirmatxt);
	     panelalanlar.add(faturakesilenfirmajt);
		
	     yuklemekentitxt = new JLabel("Yükleme Kenti:");
		 yuklemekentitxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 yuklemekentitxt.setBounds(5, 250, 100, 26);
		 yuklemekentijt=new JTextField();
		 yuklemekentijt.setBounds(110,250, 230, 26);
		 yuklemekentijt.setEditable(false);
		 yukkentSECdugme=new JButton("Seç");
		 yukkentSECdugme.setBounds(345, 250, 60, 25);
		 yukkentSECdugme.addActionListener(new ActionListener() {     
	            public void actionPerformed(ActionEvent f) {
	            	if (yukkentkodu!=0) {
	            		yukkentkodu=0;
			    		 yuklemekentijt.setText("");
			    		 return;
			    	 }
						
			    	 Ontanimliveriler.ontanimliveriler("yukaramayukleme", "Kent");
	            }
	        });
		 panelalanlar.add(yukkentSECdugme);
		 
		 panelalanlar.add(yuklemekentitxt);
	     panelalanlar.add(yuklemekentijt);
		 
		 yuklemelimanitxt = new JLabel("Yükleme Limaný:");
		 yuklemelimanitxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 yuklemelimanitxt.setBounds(5, 275, 100, 26);
		 yuklemelimanijt=new JTextField();
		 yuklemelimanijt.setBounds(110,275, 230, 26);
		 yuklemelimanijt.setEditable(false);
		 yuklimSECdugme=new JButton("Seç");
		 yuklimSECdugme.setBounds(345, 275, 60, 25);
		 yuklimSECdugme.addActionListener(new ActionListener() {     
	            public void actionPerformed(ActionEvent f) {
	            	if (yuklimkodu!=0) {
	            		yuklimkodu=0;
			    		 yuklemelimanijt.setText("");
			    		 return;
			    	 }
						
			    	 Ontanimliveriler.ontanimliveriler("yukaramayukleme", "Liman");
	            }
	        });
		 panelalanlar.add(yuklimSECdugme);
		 
		 panelalanlar.add(yuklemelimanitxt);
	     panelalanlar.add(yuklemelimanijt);
		 
		 varislimanitxt = new JLabel("Varýþ Limaný:");
		 varislimanitxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 varislimanitxt.setBounds(5, 300, 100, 26);
		 varislimanijt=new JTextField();
		 varislimanijt.setBounds(110,300, 230, 26);
		 varislimanijt.setEditable(false);
		 varlimSECdugme=new JButton("Seç");
		 varlimSECdugme.setBounds(345, 300, 60, 25);
		 varlimSECdugme.addActionListener(new ActionListener() {     
	            public void actionPerformed(ActionEvent f) {
	            	if (varlimkodu!=0) {
	            		varlimkodu=0;
			    		 varislimanijt.setText("");
			    		 return;
			    	 }
						
			    	 Ontanimliveriler.ontanimliveriler("yukaramavaris", "Liman");
	            }
	        });
		 panelalanlar.add(varlimSECdugme);
		
		 panelalanlar.add(varislimanitxt);
	     panelalanlar.add(varislimanijt);
		 
		 sonvariskentitxt = new JLabel("Son Varýþ Kenti:");
		 sonvariskentitxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 sonvariskentitxt.setBounds(5, 325, 100, 26);
		 sonvariskentijt=new JTextField();
		 sonvariskentijt.setBounds(110,325, 230, 26);
		 sonvariskentijt.setEditable(false);
		 sonvarkentSECdugme=new JButton("Seç");
		 sonvarkentSECdugme.setBounds(345, 325, 60, 25);
		 sonvarkentSECdugme.addActionListener(new ActionListener() {     
	            public void actionPerformed(ActionEvent f) {
	            	if (sonvarkentkodu!=0) {
	            		sonvarkentkodu=0;
			    		 sonvariskentijt.setText("");
			    		 return;
			    	 }
						
			    	 Ontanimliveriler.ontanimliveriler("yukaramasonvaris", "Kent");
	            }
	        });
		 panelalanlar.add(sonvarkentSECdugme);
		 
		 panelalanlar.add(sonvariskentitxt);
	     panelalanlar.add(sonvariskentijt);
	     
	     konteynernotxt= new JLabel("Konteyner No:");
	     konteynernotxt.setHorizontalAlignment(SwingConstants.RIGHT);
	     konteynernotxt.setBounds(5, 350, 100, 26);
		 panelalanlar.add(konteynernotxt);
		
		prefixjt=new JTextField();
		prefixjt.setBounds(110, 350, 60, 26);
		panelalanlar.add(prefixjt);
		prefixjt.addKeyListener(new java.awt.event.KeyAdapter() {    
		       public void keyReleased(java.awt.event.KeyEvent evt) {
			            String a=prefixjt.getText().toUpperCase();
			            prefixjt.setText(a);
		    	   		int uzunluk = prefixjt.getText().length();
		    	   		if (uzunluk>4) {
			            	prefixjt.setText("");
			            }
			        }
			    });
	
		suffixjt=new JTextField();
		suffixjt.setBounds(170, 350, 60, 26);
		panelalanlar.add(suffixjt);
		suffixjt.addKeyListener(new java.awt.event.KeyAdapter() {    
		       public void keyReleased(java.awt.event.KeyEvent evt) {
			            try {
			   			
							int miktar = Integer.parseInt(suffixjt.getText());
							if (miktar>999999) {
								suffixjt.setText("");
							}
				            } catch (Exception e) {
			               suffixjt.setText("");
			            }
			        }
			    });
	     
	     dugmeara=new JButton("ARA");
		 dugmeara.setBounds(150, 15, 90, 20);  
		 dugmeara.addActionListener(new ActionListener() {     
	            public void actionPerformed(ActionEvent f) {
	            	
	            	bakbakalimvarmiymis(hblnojt.getText(),prefixjt.getText()+suffixjt.getText());
	            }
	        });
		 panelalanlar.add(dugmeara);   
	    
	     aticine = frame.getContentPane();
	     aticine.add(panelalanlar, BorderLayout.CENTER);
	     Sonuclistesitablosu.sonuctablosu ();
		 frame.setVisible(true);
		
	}   // yukarama metodu sonu

	protected  void bakbakalimvarmiymis(String hblno, String konteynerno) {
		
		boolean enazbirisecildi=false;
		
		String sqlkomut="SELECT * FROM yukler WHERE ";
		try{                                              
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection(
					"jdbc:mysql://"+Anasinif.sqlip+"/nal2000?useUnicode=true&characterEncoding=UTF-8",Anasinif.sqluser,Anasinif.sqlpass);
			
			if (!hblno.equals("")) {
				
				sqlkomut=sqlkomut+" hblno LIKE '%" +hblno+ "%' ";
				enazbirisecildi=true;
				
			}
			
            if (!konteynerno.equals("")) {
            	if(enazbirisecildi==true) {
					sqlkomut=sqlkomut+" and ";
				}
				
				sqlkomut=sqlkomut+" konteynerbilgileri LIKE '%" +konteynerno+ "%' ";
				enazbirisecildi=true;
			}
           
            if (musterikodu!=0) {
            	if(enazbirisecildi==true) {
					sqlkomut=sqlkomut+" and ";
				}
            	sqlkomut=sqlkomut+" musterikodu=" +musterikodu+"  ";
            	enazbirisecildi=true;
            }
            
            if (ureticikodu!=0) {
            	if(enazbirisecildi==true) {
					sqlkomut=sqlkomut+" and ";
				}
            	sqlkomut=sqlkomut+" ureticikodu=" +ureticikodu+"  ";
            	enazbirisecildi=true;
            }
			
            if (gondericikodu!=0) {
            	
            	if(enazbirisecildi==true) {
					sqlkomut=sqlkomut+" and ";
				}
            	sqlkomut=sqlkomut+" gondericikodu=" +gondericikodu+"  ";
            	enazbirisecildi=true;
            	
            }
             
            if (alicikodu!=0) {
            	
            	if(enazbirisecildi==true) {
					sqlkomut=sqlkomut+" and ";
				}
            	sqlkomut=sqlkomut+" alicikodu=" +alicikodu+"  ";
            	enazbirisecildi=true;
            	
            }
            
            if (ydacentekodu!=0) {
            	
            	if(enazbirisecildi==true) {
					sqlkomut=sqlkomut+" and ";
				}
            	sqlkomut=sqlkomut+" ydacentekodu=" +ydacentekodu+"  ";
            	enazbirisecildi=true;
            	
            }
            
            if (fatkesfirkodu!=0) {
            	
            	if(enazbirisecildi==true) {
					sqlkomut=sqlkomut+" and ";
				}
            	sqlkomut=sqlkomut+" faturakesilenfirmakodu=" +fatkesfirkodu+"  ";
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
            	sqlkomut=sqlkomut+" and hbltarihi between '"+tarih1+"' and '"+tarih2+"'"; 
            }else {
            	sqlkomut=sqlkomut+" and yukno between '"+numara1+"' and '"+numara2+"'";
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
				tablodata [sayac][1]=Ontanimliveriler.sirketkoducoz(Integer.parseInt(rs.getString(8)))[1];
				tablodata [sayac][2]=Ontanimliveriler.sirketkoducoz(Integer.parseInt(rs.getString(9)))[1];
				
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
		String secilenyuk="";
	
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
		                 	if( Yuk.yukbayrak==true) {
    			    		return;
    			    	} else {
    			    		try {
    			    			Yuk.yuk(secilenyuk,"düzenle");
    						} catch (ParseException e) { e.printStackTrace();
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
	          		secilenyuk = (String) table.getValueAt(selectedRow,0 );
	          		          		
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

	   
	     Yukara.aticine.add(scrollPane, BorderLayout.EAST);  
	    }

	    class Tablomodeli extends AbstractTableModel {
	     
	        private  String[] columnNames = { "YÜK NO ", "GÖNDEREN","ALICI" };

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