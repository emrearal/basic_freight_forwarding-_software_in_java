package heanalikibin;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

public class Faturaarama   {
	
	static int sirketkodu;
	static JTextField sirketadijt;
	static String [][]tablodata ;
	static Container aticine;
		
	public static void faturaarama () {
		
		sirketkodu=0;
	   
		 JFrame frame;
		 JLabel evraknotxt,sirketaditxt;
		 JButton dugmeara,sirketadiSECdugme;
		 JPanel panelalanlar;
		
		 tablodata = new String [0][4];
		 
		 frame= new JFrame("Fatura ve Dekont Arama Penceresi");
		 frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		 frame.setLocation(50,150);
		 frame.setSize(850, 350);
		 
		 panelalanlar=new JPanel();
		 panelalanlar.setLayout(null);
		 
		 JLabel faturatxt = new JLabel("Fatura ?");
		 faturatxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 faturatxt.setBounds(5, 75, 100, 26);
		 panelalanlar.add(faturatxt);
		 
		 JLabel dekonttxt = new JLabel("Dekont ?");
		 dekonttxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 dekonttxt.setBounds(95, 75, 100, 26);
		 panelalanlar.add(dekonttxt);
		 
		 JRadioButton fatura=new JRadioButton();
		 fatura.setBounds(110,80,20,20);
		 fatura.setSelected(true);
		 JRadioButton dekont= new JRadioButton();
		 dekont.setBounds(200,80,20,20);
		 ButtonGroup faturadekontgrubu=  new ButtonGroup();
		 faturadekontgrubu.add(fatura);
		 faturadekontgrubu.add(dekont);
		 panelalanlar.add(fatura);
		 panelalanlar.add(dekont); 
		 
		 JLabel alistxt = new JLabel("Alýþ ?");
		 alistxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 alistxt.setBounds(5, 35, 100, 26);
		 panelalanlar.add(alistxt);
		 
		 JLabel satistxt = new JLabel("Satýþ ?");
		 satistxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 satistxt.setBounds(95, 35, 100, 26);
		 panelalanlar.add(satistxt);
	 
		 JRadioButton alis=new JRadioButton();
		 alis.setBounds(110,40,20,20);
		 alis.setSelected(true);
		 JRadioButton satis= new JRadioButton();
		 satis.setBounds(200,40,20,20);
		 ButtonGroup alissatisgrubu=  new ButtonGroup();
		 alissatisgrubu.add(alis);
		 alissatisgrubu.add(satis);
		 panelalanlar.add(alis);
		 panelalanlar.add(satis); 
	    
		 evraknotxt = new JLabel("Fatura/Dekont No:");
		 evraknotxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 evraknotxt.setBounds(5, 115, 100, 26);
		 JTextField evraknojt=new JTextField();
		 evraknojt.setBounds(110,115, 230, 26);
		
		 panelalanlar.add(evraknotxt);
	     panelalanlar.add(evraknojt); 
		
	     sirketaditxt = new JLabel("Alýcý/Satýcý Adý :");
		 sirketaditxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 sirketaditxt.setBounds(5, 155, 100, 26);
		 sirketadijt=new JTextField();
		 sirketadijt.setBounds(110,155, 230, 26);
		 sirketadijt.setEditable(false);
		 sirketadiSECdugme=new JButton("Seç");
		 sirketadiSECdugme.setBounds(345, 155, 60, 25);
		 sirketadiSECdugme.addActionListener(new ActionListener() {     
	            public void actionPerformed(ActionEvent f) {
	            	 if (sirketkodu!=0) {
	            		 sirketkodu=0;
	            		 sirketadijt.setText("");
			    		 return;
			    	 }
						
	             Sirketkartlari.sirketkartlari("faturaaramasirketadi");
			     	 
	            }
	        });
		
		 panelalanlar.add(sirketaditxt);
	     panelalanlar.add(sirketadijt);
	     panelalanlar.add(sirketadiSECdugme);
	     
	     dugmeara=new JButton("ARA");
		 dugmeara.setBounds(160, 215, 120, 40);  
		 dugmeara.addActionListener(new ActionListener() {     
	            public void actionPerformed(ActionEvent f) {
	            	
	            	 bakbakalimvarmiymis(fatura.isSelected(),alis.isSelected(),evraknojt.getText(),sirketkodu);
	            }
	        });
		 panelalanlar.add(dugmeara);   
	    
	     aticine = frame.getContentPane();
	     aticine.add(panelalanlar, BorderLayout.CENTER);
	     Sonuclistesitablosu.sonuctablosu ();
		 frame.setVisible(true);
		
	}   // faturaarama metodu sonu

	protected static void bakbakalimvarmiymis(boolean faturami,boolean alismi, String evrakno, int sirketkodu) {
		
		String fatdek="",alissatis="";
		
		if (faturami) {
			fatdek="fat";
		}else {
			fatdek="dek";
		}
		
		if (alismi) {
			alissatis="alac";
		} else {
			alissatis="borc";
		}
		
		String sqlkomut="SELECT * FROM fiskayitlari WHERE tip='"+fatdek+alissatis+evrakno+"' and sirketkart_0kod="+sirketkodu;
		try{                                              
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection(
					"jdbc:mysql://"+Anasinif.sqlip+"/nal2000?useUnicode=true&characterEncoding=UTF-8",Anasinif.sqluser,Anasinif.sqlpass);
			
			Statement stmt=con.createStatement();  
			ResultSet rs=stmt.executeQuery(sqlkomut); 
			   		
			int sayac=0;
			while(rs.next()) {
				sayac++;    // kaç tane aradýðýmýz veriden var;
			}
						
			if (sayac==0) {
				Bilgipenceresi.anons("Sonuç Bulunamadý !");
				return;
			}
			
			tablodata = new String [sayac][4];
			con.close();  
			
			}catch(Exception e){ System.out.println(e);}
		
		
		try{          // bulunanlarý tablodata dizinine aktar                                      
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection(
					"jdbc:mysql://"+Anasinif.sqlip+"/nal2000?useUnicode=true&characterEncoding=UTF-8",Anasinif.sqluser,Anasinif.sqlpass);
			
			Statement stmt=con.createStatement();  
			ResultSet rs=stmt.executeQuery(sqlkomut); 
			   		
			int sayac=0;
			
			double toplam=0;
			while(rs.next()) {
				tablodata [sayac][0]=Ontanimliveriler.tarihiterscevir(rs.getString(4));
				tablodata [sayac][1]=rs.getString(6);
				toplam=toplam+rs.getDouble(6);
				tablodata [sayac][2]=Ontanimliveriler.parabirimikoducoz(rs.getString(9));
				tablodata [sayac][3]=rs.getString(10)+"/"+rs.getString(11);
				
			
			    if (sayac>0 && rs.getString(7).equals("Toplu_Gider_Faturasi_Parcasi") ) {  // eðer toplu gider faturasý ise. 
			    	tablodata[0][1]=String.valueOf(toplam);
			    	tablodata [sayac][0]=" ";
					tablodata [sayac][1]=" ";
					tablodata [sayac][2]=" ";
			    }
			    sayac++; 
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
	        table.setPreferredScrollableViewportSize(new Dimension(400,400));
			   
		    table.getColumnModel().getColumn(0).setMaxWidth(100);
		    table.getColumnModel().getColumn(1).setMaxWidth(150);  
		    table.getColumnModel().getColumn(2).setMaxWidth(50); 
		    table.getColumnModel().getColumn(3).setMaxWidth(150); 
		    
		    table.setFocusable(false);
		     		    
		    scrollPane = new JScrollPane(table);
	        scrollPane.setPreferredSize(new Dimension(400,400));
	        this.setLayout(new BorderLayout());
	        this.add(scrollPane, BorderLayout.CENTER);
	    }

	    public static void showFrame() {
	        JPanel panel = new Sonuclistesitablosu();
	        panel.setOpaque(true);

	   
	     Faturaarama.aticine.add(scrollPane, BorderLayout.EAST);  
	    }

	    class Tablomodeli extends AbstractTableModel {
	     
	        private  String[] columnNames = { "Tarih ", "Toplam ","Döviz","Ýlgili Poz/Yük" };

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