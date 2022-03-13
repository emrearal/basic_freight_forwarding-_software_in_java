package heanalikibin;

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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import org.jdesktop.swingx.JXDatePicker;


public class Pozgelirekle implements ActionListener {
	
	static JFrame frame ;
	static JPanel panel;
	static JComboBox<?> liste,fattipi,doviz,gelirhizmetkalemleri;
	static String [] pozyuklistesi;
	static JXDatePicker picker;
	static JLabel evraknotxt,aciklamatxt,toptuttxt,kdvtxt,kimetxt,kurtxt,satiskurundantxt;
	static JTextField tarihjt,evraknojt,aciklamajt,toptutjt,kdvjt,kimejt,kurjt;
	static JButton kaydet,kime;
	static boolean dovizbayrak=false,gelirgiderbayrak=false;
	static JCheckBox satiskurundan;
	static int sirketkodu;
	static DateFormat df = new SimpleDateFormat("yyyy.MM.dd");
	
	
	private enum dugmecevabi  { fatno,kaydet,kime,satiskurundan  }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void pozagelirekle () {
		
	Pozgelirgiderbakiyeler.banabagliacik=true;
		
		sirketkodu=0;
		frame = new JFrame("Gelir Ekleme Penceresi");
		frame.setBounds(100, 100,520,370);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		panel= new JPanel (); 
		panel.setLayout(null);
		
		 frame.addWindowListener(new WindowAdapter()
	        {
	            @Override
	            public void windowClosing(WindowEvent e)
	            {
	            	Pozgelirgiderbakiyeler.banabagliacik=false;
	                e.getWindow().dispose();
	            }
	        });
		
		Pozayukeklecikart.yuklenmisyukdizinihazirla(Pozisyon.poznojt.getText());
	    String no,gonderici,alici,kap,kg;
		pozyuklistesi = new String [Pozayukeklecikart.yuklenmisyuktablodata.length+1];
		pozyuklistesi[0]="0-Pozisyon Altýndan Fatura Kes";
				 
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
		
		liste= new JComboBox(pozyuklistesi);
		liste.setBounds(20, 60, 460, 30);
		panel.add(liste);
		
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
					  		if (satiskurundan.isSelected()) {
					  			kurjt.setText(String.valueOf(Ontanimliveriler.kurusoyle(doviz.getSelectedIndex())[1]));
					 		} else { kurjt.setText(String.valueOf(Ontanimliveriler.kurusoyle(doviz.getSelectedIndex())[0])); }
				
						dovizbayrak=true;
				} else { dovizbayrak=false;	}
				}
		});
		
		Ontanimliveriler.ontanimliveriler("bos","Faturakalemleri" );
		Ontanimliveriler.frame.dispose();
		gelirhizmetkalemleri= new JComboBox(Ontanimliveriler.fatkal);
		gelirhizmetkalemleri.setBounds(240,100,240,30);
		panel.add(gelirhizmetkalemleri);
		gelirhizmetkalemleri.setSelectedIndex(0);
		
		gelirhizmetkalemleri.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent f) {
				if (gelirgiderbayrak==false) {
					 if (baskicrakamayikla(gelirhizmetkalemleri.getSelectedItem().toString())[1]==0) {
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
		kime.addActionListener(new Pozgelirekle());
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
		evraknojt.setBounds(90,180,203,30);
		evraknojt.setEditable(false);
		panel.add(evraknojt);
		
		aciklamajt= new JTextField();
		aciklamajt.setBounds(90,220,390,30);
		panel.add(aciklamajt);
		
		kurjt=new JTextField();
		kurjt.setBounds(390,180,90,30);
		kurjt.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(kurjt);
		kurjt.addKeyListener(new java.awt.event.KeyAdapter() {    // kur alanýna sadece double girme izni
			public void keyReleased(java.awt.event.KeyEvent evt) {
             try {
       		@SuppressWarnings("unused")
			double miktar = Double.parseDouble(kurjt.getText());
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
					if (baskicrakamayikla(gelirhizmetkalemleri.getSelectedItem().toString())[1]!=0) {
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
			
		kimetxt= new JLabel("Kime ?    :");
		kimetxt.setBounds(20,30,100,20);
		panel.add(kimetxt);
		
		evraknotxt= new JLabel("Evrak No :");
		evraknotxt.setBounds(20,190,100,20);
		panel.add(evraknotxt);
		
		kurtxt= new JLabel("KUR :");
		kurtxt.setBounds(360,192,75,20);
		panel.add(kurtxt);
		
		satiskurundantxt = new JLabel("s");
		satiskurundantxt.setBounds(362,176,20,20);
		panel.add(satiskurundantxt);
		
		satiskurundan = new JCheckBox();
		satiskurundan.setBounds(368,176,20,20);
		panel.add(satiskurundan);
		
		satiskurundan.addActionListener(new Pozgelirekle());
		satiskurundan.setActionCommand(dugmecevabi.satiskurundan.name());
		
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
		kaydet.addActionListener(new Pozgelirekle());
		kaydet.setActionCommand(dugmecevabi.kaydet.name());
		panel.add(kaydet);
		
		kurjt.setText(String.valueOf(Ontanimliveriler.kurusoyle(0)[0]));
	
		frame.add(panel);
		frame.setVisible(true);
	}
	
	private String satisfaturasinumarasiver () {
		String alsanafaturanumarasi;
		Calendar calendar = Calendar.getInstance();
		int yilimiz= calendar.get(Calendar.YEAR);
		int sonkayitliyil=0,sonfark=0,c=0;		
		try{                                              
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection(
					"jdbc:mysql://"+Anasinif.sqlip+"/nal2000?useUnicode=true&characterEncoding=UTF-8",Anasinif.sqluser,Anasinif.sqlpass);
					
					Statement stmt=con.createStatement();  
			        ResultSet rs=stmt.executeQuery("select * from faturasayaci " ); 
			
			while(rs.next()) {
			    c++;	   // kaç kayýt var ve son yýl kaydý neymiþ. 
			    sonkayitliyil=Integer.parseInt(rs.getString(3));
			    sonfark=Integer.parseInt(rs.getString(4));
					}
			   	con.close();  
			}catch(Exception e){ System.out.println(e);}  
		
		if(yilimiz-1==sonkayitliyil) {  // eðer yýl deðiþti ise sayaçtan bir önceki yýlýn sayacýný çýkartarak 1 den baþlat 
			sonfark=c-1;
			sonkayitliyil=yilimiz;
		}
		
		try{              // son satýrý kaydediyoruz.                                    
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection(  
	"jdbc:mysql://"+Anasinif.sqlip+"/nal2000?useUnicode=true&characterEncoding=UTF-8",Anasinif.sqluser,Anasinif.sqlpass);	
			Statement stmt=con.createStatement();  
			String sqlkomut="INSERT INTO faturasayaci (yil,cikart) VALUES ('"+sonkayitliyil+"','"+sonfark+"')";
			stmt.executeUpdate(sqlkomut);
			con.close();  
			
			}catch(Exception e){ System.out.println(e);} 
		
		
		String faturasirano=String.valueOf(c-sonfark);
		int boyu=String.valueOf(faturasirano).length();
		if (boyu<6) {  // fatura numarasý 6 basamaktan azsa önüne eksik kadar sýfýr koy
			
			for (int i=0; i<6-boyu; i++) {
				faturasirano="0"+faturasirano;
			}
		}
		alsanafaturanumarasi="HEA"+yilimiz+faturasirano;
		evraknojt.setText(alsanafaturanumarasi);
		return alsanafaturanumarasi;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		 if (e.getActionCommand()==dugmecevabi.satiskurundan.name())	{
			 
			 if (satiskurundan.isSelected()) {
		  			kurjt.setText(String.valueOf(Ontanimliveriler.kurusoyle(doviz.getSelectedIndex())[1]));
		 		} else { kurjt.setText(String.valueOf(Ontanimliveriler.kurusoyle(doviz.getSelectedIndex())[0])); }
			
			 if (doviz.getSelectedIndex()==0) {
				 kurjt.setText("1.0");	    	 
			     }  
		 }
		 if (e.getActionCommand()==dugmecevabi.kime.name())	{
			 
			 Sirketkartlari.sirketkartlari("pozgelirekle");
		 }
		 
 if (e.getActionCommand()==dugmecevabi.kaydet.name())	{
	 
	 if(sirketkodu!=0 && Double.parseDouble(toptutjt.getText())>2 ) {
		 gelirkaydet();
	 }else {
		 Bilgipenceresi.anons("Þirket Seçimi ve Tutar Giriþi Yapýn");
		 return;
	 }
 	// System.out.println(baskicrakamayikla(liste.getSelectedItem().toString())[0]);
	// System.out.println(baskicrakamayikla(liste.getSelectedItem().toString())[1]);
	// System.out.println(baskicrakamayikla(gelirhizmetkalemleri.getSelectedItem().toString())[0]);
	// System.out.println(baskicrakamayikla(gelirhizmetkalemleri.getSelectedItem().toString())[1]);
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



private void gelirkaydet() {
	
	String tip;
	 String fatno=satisfaturasinumarasiver() ;
	 if (fattipi.getSelectedIndex()==0)  {
		 tip="fatborc"; } else { tip="dekborc"; }
	
	
	try{                                             
		Class.forName("com.mysql.cj.jdbc.Driver");  
		Connection con=DriverManager.getConnection(  
				"jdbc:mysql://"+Anasinif.sqlip+"/nal2000?useUnicode=true&characterEncoding=UTF-8",Anasinif.sqluser,Anasinif.sqlpass);		
		Statement stmt=con.createStatement();  
		String emir1 = "INSERT INTO fiskayitlari";
		String emir2 ="(firmalarim_0kod,sirketkart_0kod,tarih,tip,tutar,aciklama,kdv,parabirimi,pozno,yukno,tlkuru,faturakalemikodu)";
		String emir3 =	"VALUES ('1','"+sirketkodu+"','"+df.format(picker.getDate())+"','"+tip+fatno
				+"','"+toptutjt.getText()+"','"+aciklamajt.getText()+"','"+kdvjt.getText()+"','"+Ontanimliveriler.pbsine(doviz.getSelectedIndex())
				+ "','"+Pozisyon.poznojt.getText()+"','"+baskicrakamayikla(liste.getSelectedItem().toString())[0]+"','"+kurjt.getText()                                           
				+ "','"+baskicrakamayikla(gelirhizmetkalemleri.getSelectedItem().toString())[0]+ "')";
		stmt.executeUpdate(emir1+emir2+emir3);
		con.close();  
		
		}catch(Exception e){ System.out.println(e);} 
	
	  Pozgelirgiderbakiyeler.veritablosunuduzenle (Pozgelirgiderbakiyeler.pozisyonno);
      Bilgipenceresi.anons("Fatura Kaydedildi");	
      Pozgelirgiderbakiyeler.Tabloyapalim.tableModel.fireTableDataChanged();
      Pozgelirgiderbakiyeler.Tabloyapalim.bakiyenedir();
      Pozgelirgiderbakiyeler.banabagliacik=false;
      frame.dispose();
     		
}  		
	
	
}
