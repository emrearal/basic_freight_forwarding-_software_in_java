package heanalikibin;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import org.jdesktop.swingx.JXDatePicker;

public class Pozgiderekle implements ActionListener {
	
	static JFrame frame ;
	static JPanel panel;
	static JComboBox<?> liste,fattipi,doviz,hizmetkalemleri;
	static String [] pozyuklistesi;
	static JXDatePicker picker;
	static JLabel evraknotxt,aciklamatxt,toptuttxt,kdvtxt,kimetxt,kurtxt,beklenentxt,beklenendeyuknobildirimitxt,toplugidertxt;
	static JTextField evraknojt,aciklamajt,toptutjt,kdvjt,kimejt,kurjt;
	static JButton kaydet,kime;
	static boolean dovizbayrak=false,gelirgiderbayrak=false;
	static JCheckBox beklenen,toplugider;
	static int sirketkodu,fisno;
	static DateFormat df = new SimpleDateFormat("yyyy.MM.dd");
	static  String [] duzenlenecekfis;
	
	private enum dugmecevabi  { kaydet,kime,beklenen,toplugider  }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void pozgiderekle (String nasilbirgider) throws ParseException {
		
		Pozgelirgiderbakiyeler.banabagliacik=true;
		
		sirketkodu=fisno=0;
		
		frame = new JFrame("GÝDER Ekleme Penceresi");
		frame.setBounds(100, 100,520,370);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
            	Pozgelirgiderbakiyeler.banabagliacik=false;
                e.getWindow().dispose();
            }
        });		
		
		panel= new JPanel (); 
		panel.setLayout(null);
		
		Pozayukeklecikart.yuklenmisyukdizinihazirla(Pozisyon.poznojt.getText());
	    String no,gonderici,alici,kap,kg;
		pozyuklistesi = new String [Pozayukeklecikart.yuklenmisyuktablodata.length+1];
		pozyuklistesi[0]="0-Pozisyon Altýna Gider Gir";
				 
		 for (int p=1; p<Pozayukeklecikart.yuklenmisyuktablodata.length+1; p++) {
	        	no=Pozayukeklecikart.yuklenmisyuktablodata[p-1][0];
	        	gonderici=Pozayukeklecikart.yuklenmisyuktablodata[p-1][1];
	        	if (gonderici.length()>(15)){
	        		gonderici=gonderici.substring(0,14);
	        	}
	        	alici =Pozayukeklecikart.yuklenmisyuktablodata[p-1][2];
	        	if (alici.length()>(15)){
	        		alici=alici.substring(0,14);
	        	}
	        	kap=Pozayukeklecikart.yuklenmisyuktablodata[p-1][3];
	        	kg=Pozayukeklecikart.yuklenmisyuktablodata[p-1][4];
	        	
	        	pozyuklistesi[p]=no+"-"+gonderici+" / "+alici+"  "+kap+" kap, "+kg+" kg";
	             }
		 beklenendeyuknobildirimitxt=new JLabel();
		 beklenendeyuknobildirimitxt.setBounds(20, 60, 460, 30);
		 
		liste= new JComboBox(pozyuklistesi);
		liste.setBounds(20, 60, 460, 30);
		
		String[] fattip={"Fatura","Dekont"};
		fattipi= new JComboBox(fattip);
		fattipi.setSelectedIndex(0);
		fattipi.setBounds(20, 100, 105, 30);
		panel.add(fattipi);
		
		String[] dovizz= {"TL","USD","EUR","GBP","SEK"};
		doviz= new JComboBox(dovizz);
		doviz.setBounds(130,100,105,30);
		doviz.setSelectedIndex(0);
		panel.add(doviz);
		
		doviz.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (dovizbayrak==false) {
					  		
					  			kurjt.setText(String.valueOf(Ontanimliveriler.kurusoyle(doviz.getSelectedIndex())[0]));
				
						dovizbayrak=true;
				} else { dovizbayrak=false;	}
				}
		});
		
		Ontanimliveriler.ontanimliveriler("bos","Faturakalemleri" );
		Ontanimliveriler.frame.dispose();
		
		hizmetkalemleri= new JComboBox(Ontanimliveriler.fatkal);
		hizmetkalemleri.setBounds(240,100,240,30);
		panel.add(hizmetkalemleri);
		hizmetkalemleri.setSelectedIndex(0);
		
		hizmetkalemleri.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent f) {
				if (gelirgiderbayrak==false) {
					 if (baskicrakamayikla(hizmetkalemleri.getSelectedItem().toString())[1]==0) {
						 kdvjt.setText("0");  // kalemde kdv sýfýr ise kaydý da sýfýr olarak gerçekleþtir.
						 kdvjt.setEditable(false);
					 }	else {
						 kdvjt.setEditable(true);
					 }
				
					 gelirgiderbayrak=true;
				} else { gelirgiderbayrak=false;	}
				}
		});
	
		kimejt= new JTextField();
		kimejt.setBounds(90,20,310,30);
		kimejt.setEditable(false);
		panel.add(kimejt);
		
		kime = new JButton("SEÇ");
		kime.setBounds(410,20, 70, 30);
		kime.addActionListener(new Pozgiderekle());
		kime.setActionCommand(dugmecevabi.kime.name());
		panel.add(kime);
		
		picker = new JXDatePicker();                        //Tarih Seçici
	    picker.setFormats(new SimpleDateFormat("yyyy.MM.dd"));
	    picker.setVisible(true);
		picker.setBounds(20,140,120,30);
		picker.getEditor().setEditable(false);
		picker.setDate(Calendar.getInstance().getTime());
		panel.add(picker);
		
		evraknojt= new JTextField();
		evraknojt.setBounds(92,180,258,30);
		panel.add(evraknojt);
		
		aciklamajt= new JTextField();
		aciklamajt.setBounds(92,220,388,30);
		panel.add(aciklamajt);
		
		kurjt=new JTextField("1");
		kurjt.setBounds(390,180,90,30);
		kurjt.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(kurjt);
		kurjt.addKeyListener(new java.awt.event.KeyAdapter() {    // kur alanýna sadece double girme izni
			public void keyReleased(java.awt.event.KeyEvent evt) {
             try {
       		
       		double miktar = Double.parseDouble(kurjt.getText());
       		if (miktar<1) {
       			kurjt.setText("1");
       		}
            } catch (Exception e) { kurjt.setText("1");}
        }
    });
		
		kdvjt=new JTextField("0");
		kdvjt.setBounds(390,140,90,30);
		kdvjt.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(kdvjt);
		kdvjt.addKeyListener(new java.awt.event.KeyAdapter() {    // kdv alanýna sadece double girme izni
				public void keyReleased(java.awt.event.KeyEvent evt) {
	             try {
	         	
					double miktar = Double.parseDouble(kdvjt.getText());
	            	if (miktar-(miktar/1.18)<0) {
	            		toptutjt.setText("0");
			        kdvjt.setText("0");
		            }
	            } catch (Exception e) {
	              
	            	kdvjt.setText("0");
	              
	            }
	        }
	    });
		
		toptutjt= new JTextField();
		toptutjt.setBounds(240,140,110,30);
		toptutjt.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(toptutjt);
		toptutjt.setText("1");
		
		toptutjt.addKeyListener(new java.awt.event.KeyAdapter() {    // tutar alanýna sadece double girme izni

	        public void keyReleased(java.awt.event.KeyEvent evt) {
	            try {
	            	
	            	DecimalFormat df = new DecimalFormat("#0.00");
	        	
	            	double miktar = Double.parseDouble(toptutjt.getText());
	            	
	            	kdvjt.setText((df.format(miktar-(miktar/1.18))));
					String tencere= kdvjt.getText();
					if (baskicrakamayikla(hizmetkalemleri.getSelectedItem().toString())[1]!=0) {
						kdvjt.setText(virgulunoktayap (tencere));
					} else {
						kdvjt.setText("0");
					}
					
					if (miktar<0 ) { 
						toptutjt.setText("0");
						kdvjt.setText("0");
	                }
					
	            } catch (Exception e) {
	            	toptutjt.setText("0");
	            	kdvjt.setText("0");
	              
	            }
	        }
	    });
			
		kimetxt= new JLabel("Kimden ?    :");
		kimetxt.setBounds(20,30,100,20);
		panel.add(kimetxt);
		
		evraknotxt= new JLabel("Evrak No :");
		evraknotxt.setBounds(20,190,100,20);
		panel.add(evraknotxt);
		
		kurtxt= new JLabel("KUR :");
		kurtxt.setBounds(360,192,75,20);
		panel.add(kurtxt);
		
		beklenentxt = new JLabel("Beklenen ?:");
		beklenentxt.setFont(new Font("Serif", Font.PLAIN, 12));
		beklenentxt.setBounds(20,176,120,20);
		panel.add(beklenentxt);
		
		toplugidertxt = new JLabel("Toplu Gider?:");
		toplugidertxt.setFont(new Font("Serif", Font.PLAIN, 12));
		toplugidertxt.setBounds(20,260,120,20);
		panel.add(toplugidertxt);
		
		beklenen = new JCheckBox();
		beklenen.setBounds(73,176,20,20);
		panel.add(beklenen);
		
		beklenen.addActionListener(new Pozgiderekle());
		beklenen.setActionCommand(dugmecevabi.beklenen.name());
		
		toplugider = new JCheckBox();
		toplugider.setBounds(93,260,20,20);
		panel.add(toplugider);
		
		toplugider.addActionListener(new Pozgiderekle());
		toplugider.setActionCommand(dugmecevabi.toplugider.name());
	
		aciklamatxt= new JLabel("Açýklama :");
		aciklamatxt.setBounds(20,230,100,20);
		panel.add(aciklamatxt);

		toptuttxt= new JLabel("TOPLAM TUTAR:");
		toptuttxt.setHorizontalAlignment(SwingConstants.RIGHT);
		toptuttxt.setBounds(135,150,100,20);
		panel.add(toptuttxt);
		
		kdvtxt= new JLabel("KDV:");
		kdvtxt.setHorizontalAlignment(SwingConstants.RIGHT);
		kdvtxt.setBounds(325,150,60,20);
		panel.add(kdvtxt);
		
		kaydet = new JButton("KAYDET");
		kaydet.setBounds(215, 270, 80, 35);
		kaydet.addActionListener(new Pozgiderekle());
		kaydet.setActionCommand(dugmecevabi.kaydet.name());
		panel.add(kaydet);
		
		if (!nasilbirgider.equals("yeni")) {
			liste.setVisible(false);
			 panel.add(beklenendeyuknobildirimitxt);
			fisno=Integer.parseInt(nasilbirgider);
			bekleneniekranagetir(nasilbirgider);
		} else {
			panel.add(liste);
		}
		
	
		frame.add(panel);
		frame.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		 if (e.getActionCommand()==dugmecevabi.beklenen.name())	{
			 
			 if (beklenen.isSelected()) {
		  			evraknojt.setText("BEKLENEN GÝDER");
		  			evraknojt.setEditable(false);
		  			kurjt.setEditable(false);
		  			
		 		} else { 
		 			evraknojt.setText("");
		 			evraknojt.setEditable(true);
		 			kurjt.setEditable(true);
		 			
		 		}
			 
		 }
		 
		 if (e.getActionCommand()==dugmecevabi.toplugider.name())	{
			 
			 if (toplugider.isSelected()) {
				    beklenen.setSelected(false);
				    beklenen.setEnabled(false);
				    evraknojt.setText("");
				    evraknojt.setEditable(true);
				    kurjt.setEditable(true);
		  			aciklamajt.setText("Toplu_Gider_Faturasi_Parcasi");
		  			aciklamajt.setEditable(false);
		  			
		 		} else { 
		 			aciklamajt.setEditable(true);
		 			aciklamajt.setText("");
		 			beklenen.setEnabled(true);
		 			aciklamajt.setText("");
		 			aciklamajt.setEditable(true);
		 			
		 		}
			 
		 }
		 
		 if (e.getActionCommand()==dugmecevabi.kime.name())	{
			 
			 Sirketkartlari.sirketkartlari("pozgiderekle");
		 }
		 
 if (e.getActionCommand()==dugmecevabi.kaydet.name())	{
	 
	 if(sirketkodu!=0 && Double.parseDouble(toptutjt.getText())>2 ) {
		 giderkaydet();
	 }else {
		 Bilgipenceresi.anons("Þirket Seçimi ve Tutar Giriþi Yapýn");
		 return;
	 }
 	
		 }
 
	} // override sonu 
	
private static String virgulunoktayap (String tencere) {
		
		int yeri=tencere.indexOf(",");
			
		String parca1= tencere.substring(0, yeri);
		String parca2= tencere.substring(yeri+1);
		String tava=parca1+"."+parca2;
		
		return tava;
    }
	
private static  int[] baskicrakamayikla (String dilimlebeni) {
	int donderbunu[]= {-1,-1};
	int ilktireninyeri=dilimlebeni.indexOf("-");
	int sontireninyeri=dilimlebeni.indexOf("-",ilktireninyeri+1);
	
	donderbunu[0]=Integer.parseInt(dilimlebeni.substring(0,ilktireninyeri)); //baþtaki rakam
	
	if (sontireninyeri!=-1) {
		donderbunu[1]=Integer.parseInt(dilimlebeni.substring(sontireninyeri+1,dilimlebeni.length()));// sondaki rakam
	}

	return donderbunu;
}

private static void bekleneniekranagetir(String fisno) throws ParseException {
	   	
		duzenlenecekfis = new String[13];
		
		try{                                              
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection(
					"jdbc:mysql://"+Anasinif.sqlip+"/nal2000?useUnicode=true&characterEncoding=UTF-8",Anasinif.sqluser,Anasinif.sqlpass);
			
					Statement stmt=con.createStatement();  
			   		ResultSet rs=stmt.executeQuery("select * from fiskayitlari where fisno='"+fisno+"'"); 
			
			while(rs.next()) {
				for (int j=0; j<13; j++ ) {
				duzenlenecekfis[j]=rs.getString(j+1);
					}
			}
			con.close();  
				}catch(Exception e){ System.out.println(e);}
		
		    kimejt.setText(Ontanimliveriler.sirketkoducoz(Integer.parseInt(duzenlenecekfis[2]))[1]);
		    sirketkodu=Integer.parseInt(duzenlenecekfis[2]);
		    
		   // liste.setSelectedItem(Integer.parseInt(duzenlenecekfis[10]));
		   
		    
		    if (!duzenlenecekfis[10].equals("0")) {
		    	beklenendeyuknobildirimitxt.setText(duzenlenecekfis[10]+" numaralý yük - "+Ontanimliveriler.sirketkoducoz
			    		(Ontanimliveriler.yuknogondericisibul(Integer.parseInt(duzenlenecekfis[10]))));
		    }  else {
		    	beklenendeyuknobildirimitxt.setText("0- Pozisyon Altýndan ");
		    }
		    
		    if (duzenlenecekfis[4].substring(0, 7).equals("fatalac")) {
		    	 fattipi.setSelectedIndex(0);
		    } else {
		    	fattipi.setSelectedIndex(1);
		    }
		 
		    doviz.setSelectedIndex((Ontanimliveriler.pbdenteklikoda(duzenlenecekfis[8])));
		    hizmetkalemleri.setSelectedIndex(0);
		
		    Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(duzenlenecekfis[3]);   
	 	    picker.setDate(date1);    
	 	    
	 	    toptutjt.setText(duzenlenecekfis[5]);
	 	    kdvjt.setText(duzenlenecekfis[7]);
	 	    evraknojt.setText(duzenlenecekfis[4].substring(7)) ;
	 	    kurjt.setText(duzenlenecekfis[11]);
	 	    
	 	    aciklamajt.setText(duzenlenecekfis[6]);
	 	    beklenen.setSelected(true);
	 	    evraknojt.setEditable(false);
	 	    kurjt.setEditable(false);
	 	    
	 	    kimejt.setEditable(false);
	 	    kime.setVisible(false);
	 	  
		    frame.validate();
	
} // metod sonu 

private void giderkaydet() {
	
	if (fisno!=0) {
		beklenenidegistiripkaydet ();
		return;
	}
	
	String tip;

		 if (fattipi.getSelectedIndex()==0)  {
		 tip="fatalac"; } else { tip="dekalac"; }
	
	try{                                             
		Class.forName("com.mysql.cj.jdbc.Driver");  
		Connection con=DriverManager.getConnection(  
				"jdbc:mysql://"+Anasinif.sqlip+"/nal2000?useUnicode=true&characterEncoding=UTF-8",Anasinif.sqluser,Anasinif.sqlpass);		
		Statement stmt=con.createStatement();  
		String emir1 = "INSERT INTO fiskayitlari";
		String emir2 ="(firmalarim_0kod,sirketkart_0kod,tarih,tip,tutar,aciklama,kdv,parabirimi,pozno,yukno,tlkuru,faturakalemikodu)";
		String emir3 =	"VALUES ('1','"+sirketkodu+"','"+df.format(picker.getDate())+"','"+tip+evraknojt.getText()
				+"','"+toptutjt.getText()+"','"+aciklamajt.getText()+"','"+kdvjt.getText()+"','"+Ontanimliveriler.pbsine(doviz.getSelectedIndex())
				+ "','"+Pozisyon.poznojt.getText()+"','"+baskicrakamayikla(liste.getSelectedItem().toString())[0]+"','"+kurjt.getText()                                           
				+ "','"+baskicrakamayikla(hizmetkalemleri.getSelectedItem().toString())[0]+ "')";
		stmt.executeUpdate(emir1+emir2+emir3);
		con.close();  
		
		}catch(Exception e){ System.out.println(e);} 
	
	  Pozgelirgiderbakiyeler.veritablosunuduzenle (Pozgelirgiderbakiyeler.pozisyonno);
      Bilgipenceresi.anons("Gider Kaydedildi");	
      Pozgelirgiderbakiyeler.Tabloyapalim.tableModel.fireTableDataChanged();
      Pozgelirgiderbakiyeler.Tabloyapalim.bakiyenedir();
      Pozgelirgiderbakiyeler.banabagliacik=false;
      frame.dispose();
     		
}

private void beklenenidegistiripkaydet () {
	String tip;
 
	 if (fattipi.getSelectedIndex()==0)  {
		 tip="fatalac"; } else { tip="dekalac"; }
	
	String sqlkomut="Update fiskayitlari set "
			+ "   sirketkart_0kod ="+duzenlenecekfis[2]+","
			+"   tarih  ='"+df.format(picker.getDate())+"',"
			+"   tip ='"+tip+evraknojt.getText()+"',"
			+"   tutar ='"+toptutjt.getText()+"',"
			+"   aciklama ='"+aciklamajt.getText()+"',"
			+"   kdv ='"+kdvjt.getText()+"',"
			+"   parabirimi ='"+Ontanimliveriler.pbsine(doviz.getSelectedIndex())+"',"
			+"   pozno ='"+duzenlenecekfis[9]+"',"
			+"   yukno ='"+duzenlenecekfis[10]+"',"
			+ "  tlkuru ='"+kurjt.getText()+"',"
			+"   faturakalemikodu='"+baskicrakamayikla(hizmetkalemleri.getSelectedItem().toString())[0]+"'"
			+" where fisno='"+fisno+"'";
		
	try{                                             
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection(  
	"jdbc:mysql://"+Anasinif.sqlip+"/nal2000?useUnicode=true&characterEncoding=UTF-8",Anasinif.sqluser,Anasinif.sqlpass);	
			Statement stmt=con.createStatement();  

			stmt.executeUpdate(sqlkomut);
			con.close();  
			
			}catch(Exception e){ System.out.println(e);}  
		
		Pozgelirgiderbakiyeler.veritablosunuduzenle (Pozgelirgiderbakiyeler.pozisyonno);
	      Bilgipenceresi.anons("Gider Kaydedildi");	
	      Pozgelirgiderbakiyeler.Tabloyapalim.tableModel.fireTableDataChanged();
	      Pozgelirgiderbakiyeler.Tabloyapalim.bakiyenedir();
	      Pozgelirgiderbakiyeler.banabagliacik=false;
	      frame.dispose();
		
}
	
}