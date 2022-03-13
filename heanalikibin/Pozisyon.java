package heanalikibin;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import org.jdesktop.swingx.JXDatePicker;

public class Pozisyon implements ActionListener  {
	
	static JFrame frame;
	static JLabel istipitxt,poznotxt,parsiyeltxt,mblnotxt,yuklemegemisitxt,aktarmagemisitxt,yurticigemiacentesitxt,aktarmaacentesitxt,yurtdisigemiacentesitxt,
	              ydacentetxt,yukadediaditxt,navluntutaritxt,yuklemekentitxt,yuklemelimanitxt,aktarmalimanitxt,varislimanitxt,sonvariskentitxt,varisulkesitxt,
	              toplambrutkgtxt,toplamnetkgtxt,toplamhacimtxt,toplamkaptxt,hataditxt,kalkistarihitxt,varistarihitxt,karsipozisyontxt,prepaid1txt,collect1txt,
	              prepaid2txt,collect2txt,prepaid3txt,collect3txt,navlunprepaidtxt,yimasraftxt,ydmasraftxt,hatreznotxt,parsiyelkonteynernotxt,listtxt,mblgondericitxt
	              ,mblalicitxt,mblnotifytxt,freetimetxt;
	
	static JTextField mblnojt,yuklemegemisijt,yuklemegemisiseferjt,aktarmagemisijt,aktarmagemisiseferjt,yurticigemiacentesijt,aktarmaacentesijt,yurtdisigemiacentesijt,
    ydacentejt,yukadediadijt, navluntutarijt,yuklemekentijt,yuklemelimanijt,aktarmalimanijt,varislimanijt,sonvariskentijt,varisulkesijt,freetimejt,
    toplambrutkgjt,toplamnetkgjt,toplamhacimjt,toplamkapjt,poznojt,hatadijt,karsipozisyonjt,hatreznojt,prefixjt,suffixjt,mblgondericijt,mblalicijt,mblnotifyjt;
	
	static JButton dugmekaydet,dugmesil,dugmeyeni,dugmeyukler,dugmegelirgider,dugmeyenile,dugmemblbas;
	
	static JTextArea area ;
	
	static JButton yuklemegemisiSECdugme,aktarmagemisiSECdugme,yurticigemiacentesiSECdugme,aktarmaacentesiSECdugme,yurtdisigemiacentesiSECdugme,
    ydacenteSECdugme,yuklemekentiSECdugme,yuklemelimaniSECdugme,aktarmalimaniSECdugme,varislimaniSECdugme,sonvariskentiSECdugme,hatadiSECdugme,
    mblaliciSECdugme,mblgondericiSECdugme,mblnotifySECdugme;
	
	static int yuklemegemisikodu ,aktarmagemisikodu ,aktarmaacentesikodu=0 ,yurtdisigemiacentesikodu  ,yurticigemiacentesikodu=0, 
	ydacentekodu  ,yuklemekentikodu , yuklemelimanikodu , aktarmalimanikodu , 
	varislimanikodu , sonvariskentikodu,hatadikodu,mblgondericikodu,mblalicikodu,mblnotifykodu;
	
	static JTable konttablosu;
	static JRadioButton navlunprepaid,navluncollect,yimasrafprepaid,yimasrafcollect,ydmasrafprepaid,ydmasrafcollect;
	static JCheckBox parsiyel;
	static JPanel panelalanlar;
	static JScrollPane yp;
	static ButtonGroup navlunodemegrubu,yimasrafgrubu,ydmasrafgrubu;
	static JXDatePicker picker,pickerkalkis,pickervaris;
	static String[][] tablodata;
	static String[] duzenlenecekpoz,duzenlenecekyuk,masterblicinkonteynermalcinsi;
	static String secilenkonteyner="";
	static TableModel tableModel;
	static Container aticine;
	@SuppressWarnings("rawtypes")
	static JComboBox istipicb,liste;
	static DateFormat df = new SimpleDateFormat("yyyy.MM.dd"); 
	static boolean pozbayrak=false,listebayrak=false;
	static Container cont ;
	
	private enum dugmecevabi {
		dugmekaydet,dugmesil,dugmeyeni,dugmeyukler,dugmemblbas,yuklemegemisiSECdugme,
		aktarmagemisiSECdugme,yurticigemiacentesiSECdugme,aktarmaacentesiSECdugme,yurtdisigemiacentesiSECdugme,notify2SECdugme,hatadiSECdugme,
	    ydacenteSECdugme,yuklemekentiSECdugme,yuklemelimaniSECdugme,aktarmalimaniSECdugme,varislimaniSECdugme,sonvariskentiSECdugme,radyoparsiyel,dugmegelirgider
	    ,dugmeyenile,mblgondericiSECdugme,mblaliciSECdugme,mblnotifySECdugme }
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void pozisyon(String neyapam) throws ParseException {
		pozbayrak=true;
		
		yuklemegemisikodu=aktarmagemisikodu=aktarmaacentesikodu=yurtdisigemiacentesikodu=yurticigemiacentesikodu= 
		ydacentekodu=yuklemekentikodu=yuklemelimanikodu=aktarmalimanikodu=varislimanikodu=sonvariskentikodu
		=hatadikodu=mblgondericikodu=mblalicikodu=mblnotifykodu=0;
		
		panelalanlar=new JPanel();
		panelalanlar.setLayout(null);
		
		 parsiyelkonteynernotxt= new JLabel("Parsiyel Konteyner No:");
		 parsiyelkonteynernotxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 parsiyelkonteynernotxt.setBounds(295, 45, 200, 20);
		 panelalanlar.add(parsiyelkonteynernotxt);
		
		prefixjt=new JTextField();
		prefixjt.setBounds(500, 45, 50, 26);
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
		suffixjt.setBounds(555, 45, 50, 26);
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
		 
		 frame= new JFrame("Pozisyon Penceresi");
		 frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		 frame.setLocation(50,150);
		 frame.setSize(1250, 575);
		 
		 frame.addWindowListener(new WindowAdapter()
	        {
	            @Override
	            public void windowClosing(WindowEvent e)
	            {
	                pozbayrak=false;
	                e.getWindow().dispose();
	            }
	        });
			 
		 dugmegelirgider=new JButton("Gelir-Gider");
		 dugmegelirgider.setBounds(320, 10, 100, 20);
		 dugmegelirgider.addActionListener(new Pozisyon());
		 dugmegelirgider.setActionCommand(dugmecevabi.dugmegelirgider.name());
		 panelalanlar.add(dugmegelirgider);   
		 
		 dugmeyukler=new JButton("Yük Ekle-Çýkar");
		 dugmeyukler.setBounds(430,10,120, 20);
		 dugmeyukler.addActionListener(new Pozisyon());
		 dugmeyukler.setActionCommand(dugmecevabi.dugmeyukler.name());
		 panelalanlar.add(dugmeyukler);
		 
		 dugmemblbas=new JButton("MBL Bas");
		 dugmemblbas.setBounds(560,10,90, 20);
		 dugmemblbas.addActionListener(new Pozisyon());
		 dugmemblbas.setActionCommand(dugmecevabi.dugmemblbas.name());
		 panelalanlar.add(dugmemblbas);
		 
		 dugmeyenile=new JButton("Yenile");
		 dugmeyenile.setBounds(890,10,70, 35);
		 dugmeyenile.addActionListener(new Pozisyon());
		 dugmeyenile.setActionCommand(dugmecevabi.dugmeyenile.name());
		 panelalanlar.add(dugmeyenile);
		 
		 dugmekaydet=new JButton("Kaydet");
		 dugmekaydet.setBounds(120,10, 90, 20);
		 dugmekaydet.addActionListener(new Pozisyon());
		 dugmekaydet.setActionCommand(dugmecevabi.dugmekaydet.name());
		 panelalanlar.add(dugmekaydet);
		 
		 dugmeyeni=new JButton("Yeni");
		 dugmeyeni.setBounds(20, 10, 90, 20);
		 dugmeyeni.addActionListener(new Pozisyon());
		 dugmeyeni.setActionCommand(dugmecevabi.dugmeyeni.name());
		 panelalanlar.add(dugmeyeni);
		 
		 dugmesil=new JButton("Sil");
		 dugmesil.setBounds(220,10, 90, 20);
		 dugmesil.addActionListener(new Pozisyon());
		 dugmesil.setActionCommand(dugmecevabi.dugmesil.name());
		 panelalanlar.add(dugmesil);
		 
		 parsiyel= new JCheckBox();
		 parsiyel.setBounds(110,45,20,20);
		 parsiyel.addActionListener(new Pozisyon());
		 parsiyel.setActionCommand(dugmecevabi.radyoparsiyel.name());
		 panelalanlar.add(parsiyel);
		 parsiyeltxt = new JLabel("Parsiyel:");
		 parsiyeltxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 parsiyeltxt.setBounds(5, 45, 100, 20);
		 panelalanlar.add(parsiyeltxt);
		 
		 istipitxt = new JLabel("Grup:");
		 istipitxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 istipitxt.setBounds(100, 45, 100, 20);
		 panelalanlar.add(istipitxt);
	    	
		 istipicb = new JComboBox(Anasinif.istipi);
	     istipicb.setBounds(205,45, 100, 20);
	     istipicb.setSelectedIndex(0);
		 panelalanlar.add(istipicb);
	    
	     poznotxt = new JLabel("Poz. No:");
		 poznotxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 poznotxt.setBounds(100, 70, 100, 26);
		 poznojt=new JTextField();
		 poznojt.setBounds(205,70, 100, 26);
		 poznojt.setEditable(false);
		 panelalanlar.add(poznotxt);
	     panelalanlar.add(poznojt);
		 
		 picker = new JXDatePicker();                        //MBL için Tarih Seçici
		 picker.setDate(Calendar.getInstance().getTime()); 	
		 picker.setFormats(new SimpleDateFormat("yyyy.MM.dd"));
		 picker.setVisible(true);
		 picker.setBounds(350,100,120,26);
		 picker.getEditor().setEditable(false);
		 panelalanlar.add(picker);

		 mblnotxt = new JLabel("MBL No ve Tarihi:");
		 mblnotxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 mblnotxt.setBounds(5, 100, 100, 26);
		 panelalanlar.add(mblnotxt);
		 
		 mblnojt=new JTextField();
		 mblnojt.setBounds(110,100, 230, 26);
		 panelalanlar.add(mblnojt);
		 
		 hataditxt = new JLabel("Hat Adý:");
		 hataditxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 hataditxt.setBounds(5, 125, 100, 26);
		 hatadijt=new JTextField();
		 hatadijt.setBounds(110,125, 350, 26);
		 hatadijt.setEditable(false);
		 hatadiSECdugme=new JButton("Seç");
		 hatadiSECdugme.addActionListener(new Pozisyon());
		 hatadiSECdugme.setActionCommand(dugmecevabi.hatadiSECdugme.name());
		 hatadiSECdugme.setBounds(470,125,75,26);
		 panelalanlar.add(hataditxt);
	     panelalanlar.add(hatadijt);
	     panelalanlar.add(hatadiSECdugme);
	     
	     hatreznotxt = new JLabel("Hat Rez No:");
	     hatreznotxt.setHorizontalAlignment(SwingConstants.RIGHT);
	     hatreznotxt.setBounds(5, 150, 100, 26);
		 hatreznojt=new JTextField();
		 hatreznojt.setBounds(110,150, 350, 26);
		 panelalanlar.add(hatreznotxt);
	     panelalanlar.add(hatreznojt);
		 
		 yuklemegemisitxt = new JLabel("Yükleme Gemisi:");
		 yuklemegemisitxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 yuklemegemisitxt.setBounds(5, 175, 100, 26);
		 yuklemegemisijt=new JTextField();
		 yuklemegemisijt.setBounds(110,175, 250, 26);
		 yuklemegemisijt.setEditable(false);
		 yuklemegemisiSECdugme=new JButton("Seç");
		 yuklemegemisiSECdugme.addActionListener(new Pozisyon());
		 yuklemegemisiSECdugme.setActionCommand(dugmecevabi.yuklemegemisiSECdugme.name());
		 yuklemegemisiSECdugme.setBounds(470,175,75,26);
		 panelalanlar.add(yuklemegemisitxt);
	     panelalanlar.add(yuklemegemisijt);
	     panelalanlar.add(yuklemegemisiSECdugme);
	     
	     yuklemegemisiseferjt=new JTextField("Sefer no");
		 yuklemegemisiseferjt.setBounds(370,175, 90, 26);
		 panelalanlar.add(yuklemegemisiseferjt);
		 
		 aktarmagemisitxt = new JLabel("Aktarma Gemisi:");
		 aktarmagemisitxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 aktarmagemisitxt.setBounds(5, 200, 100, 26);
		 aktarmagemisijt=new JTextField();
		 aktarmagemisijt.setEditable(false);
		 aktarmagemisijt.setBounds(110,200, 250, 26);
		 aktarmagemisiSECdugme =new JButton("Seç");
		 aktarmagemisiSECdugme.addActionListener(new Pozisyon());
		 aktarmagemisiSECdugme.setActionCommand(dugmecevabi.aktarmagemisiSECdugme.name());
		 aktarmagemisiSECdugme.setBounds(470,200,75,26);
		 panelalanlar.add(aktarmagemisitxt);
	     panelalanlar.add(aktarmagemisijt);
		 panelalanlar.add(aktarmagemisiSECdugme);
		 
		 aktarmagemisiseferjt=new JTextField("Sefer no");
		 aktarmagemisiseferjt.setBounds(370,200, 90, 26);
		 panelalanlar.add(aktarmagemisiseferjt);
		 
		 yurticigemiacentesitxt = new JLabel("T.C.Gemi Acente:");
		 yurticigemiacentesitxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 yurticigemiacentesitxt.setBounds(5, 225, 100, 26);
		 yurticigemiacentesijt=new JTextField();
		 yurticigemiacentesijt.setEditable(false);
		 yurticigemiacentesijt.setBounds(110,225, 350, 26);
		 yurticigemiacentesiSECdugme =new JButton("Seç");
		 yurticigemiacentesiSECdugme.addActionListener(new Pozisyon());
		 yurticigemiacentesiSECdugme.setActionCommand(dugmecevabi.yurticigemiacentesiSECdugme.name());
		 yurticigemiacentesiSECdugme.setBounds(470,225,75,26);
		 panelalanlar.add(yurticigemiacentesitxt);
	     panelalanlar.add(yurticigemiacentesijt);
		 panelalanlar.add(yurticigemiacentesiSECdugme);
		 
		 aktarmaacentesitxt = new JLabel("Aktarma Acente:");
		 aktarmaacentesitxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 aktarmaacentesitxt.setBounds(5, 250, 100, 26);
		 aktarmaacentesijt=new JTextField();
		 aktarmaacentesijt.setEditable(false);
		 aktarmaacentesijt.setBounds(110,250, 350, 26);
		 aktarmaacentesiSECdugme =new JButton("Seç");
		 aktarmaacentesiSECdugme.addActionListener(new Pozisyon());
		 aktarmaacentesiSECdugme.setActionCommand(dugmecevabi.aktarmaacentesiSECdugme.name());
		 aktarmaacentesiSECdugme.setBounds(470,250,75,26);
		 panelalanlar.add(aktarmaacentesitxt);
	     panelalanlar.add(aktarmaacentesijt);
		 panelalanlar.add(aktarmaacentesiSECdugme);
		
		 yurtdisigemiacentesitxt = new JLabel("Y.D.Gemi Acente:");
		 yurtdisigemiacentesitxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 yurtdisigemiacentesitxt.setBounds(5, 275, 100, 26);
		 yurtdisigemiacentesijt=new JTextField();
		 yurtdisigemiacentesijt.setEditable(false);
		 yurtdisigemiacentesijt.setBounds(110,275, 350, 26);
		 yurtdisigemiacentesiSECdugme =new JButton("Seç");
		 yurtdisigemiacentesiSECdugme.addActionListener(new Pozisyon());
		 yurtdisigemiacentesiSECdugme.setActionCommand(dugmecevabi.yurtdisigemiacentesiSECdugme.name());
		 yurtdisigemiacentesiSECdugme.setBounds(470,275,75,26);
		 panelalanlar.add(yurtdisigemiacentesitxt);
	     panelalanlar.add(yurtdisigemiacentesijt);
		 panelalanlar.add(yurtdisigemiacentesiSECdugme);
		 
		 ydacentetxt = new JLabel("Yurt Dýþý Acente:");
		 ydacentetxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 ydacentetxt.setBounds(5, 300, 100, 26);
		 ydacentejt=new JTextField();
		 ydacentejt.setEditable(false);
		 ydacentejt.setBounds(110,300, 350, 26);
		 ydacenteSECdugme =new JButton("Seç");
		 ydacenteSECdugme.addActionListener(new Pozisyon());
		 ydacenteSECdugme.setActionCommand(dugmecevabi.ydacenteSECdugme.name());
		 ydacenteSECdugme.setBounds(470,300,75,26);
		 panelalanlar.add(ydacentetxt);
	     panelalanlar.add(ydacentejt);
		 panelalanlar.add(ydacenteSECdugme);
		 
		 karsipozisyontxt = new JLabel("Karþý Poz. No:");
		 karsipozisyontxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 karsipozisyontxt.setBounds(5, 325, 100, 26);
		 karsipozisyonjt=new JTextField();
		 karsipozisyonjt.setBounds(110,325, 350, 26);
		 panelalanlar.add(karsipozisyontxt);
	     panelalanlar.add(karsipozisyonjt);
	    
	     kalkistarihitxt = new JLabel("Kalkýþ tarihi:");
	     kalkistarihitxt.setHorizontalAlignment(SwingConstants.RIGHT);
	     kalkistarihitxt.setBounds(5, 350, 100, 26);
	     pickerkalkis = new JXDatePicker();                       
		 pickerkalkis.setDate(Calendar.getInstance().getTime()); 	
		 pickerkalkis.setFormats(new SimpleDateFormat("yyyy.MM.dd"));
		 pickerkalkis.setVisible(true);
		 pickerkalkis.setBounds(110,350,120,26);
		 pickerkalkis.getEditor().setEditable(false);
		 panelalanlar.add(pickerkalkis);
		 panelalanlar.add(kalkistarihitxt);
		 
		 varistarihitxt = new JLabel("Varýþ tarihi:");
		 varistarihitxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 varistarihitxt.setBounds(5, 375, 100, 26);
		 pickervaris = new JXDatePicker();                       
		 pickervaris.setDate(Calendar.getInstance().getTime()); 	
		 pickervaris.setFormats(new SimpleDateFormat("yyyy.MM.dd"));
		 pickervaris.setVisible(true);
		 pickervaris.setBounds(110,375,120,26);
		 pickervaris.getEditor().setEditable(false);
		 panelalanlar.add(pickervaris);
		 panelalanlar.add(varistarihitxt);
	
		 navluntutaritxt = new JLabel("Navlun Tutarý:");
		 navluntutaritxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 navluntutaritxt.setBounds(5, 400, 100, 26);
		 navluntutarijt=new JTextField();
		 navluntutarijt.setBounds(110,400, 110, 26);
		 panelalanlar.add(navluntutaritxt);
	     panelalanlar.add(navluntutarijt);
	     
	     navlunprepaidtxt = new JLabel("Navlun Ödemesi:");
	     navlunprepaidtxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 navlunprepaidtxt.setBounds(5, 425, 100, 26);
		 panelalanlar.add(navlunprepaidtxt);
	     
	     prepaid1txt = new JLabel("Prepaid");
		 prepaid1txt.setBounds(140, 425, 100, 26);
		 panelalanlar.add(prepaid1txt);
		 collect1txt = new JLabel("Collect");
	     collect1txt.setBounds(220, 425, 100, 26);
		 panelalanlar.add(collect1txt);
	     
	     navlunprepaid=new JRadioButton();
	     navlunprepaid.setBounds(120,425,20,20);
	     navlunprepaid.setSelected(true);
		 navluncollect= new JRadioButton();
		 navluncollect.setBounds(200,425,20,20);
		 navlunodemegrubu=  new ButtonGroup();
		 navlunodemegrubu.add(navlunprepaid);
		 navlunodemegrubu.add(navluncollect);
		 panelalanlar.add(navlunprepaid);
		 panelalanlar.add(navluncollect); 
		 
		 yimasraftxt = new JLabel("Yerel Masraflar:");
		 yimasraftxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 yimasraftxt.setBounds(5, 450, 100, 26);
		 panelalanlar.add(yimasraftxt);
	     
	     prepaid2txt = new JLabel("Prepaid");
		 prepaid2txt.setBounds(140, 450, 100, 26);
		 panelalanlar.add(prepaid2txt);
		 collect2txt = new JLabel("Collect");
	     collect2txt.setBounds(220, 450, 100, 26);
		 panelalanlar.add(collect2txt);
	     
	     yimasrafprepaid=new JRadioButton();
	     yimasrafprepaid.setBounds(120,450,20,20);
	     yimasrafprepaid.setSelected(true);
		 yimasrafcollect= new JRadioButton();
		 yimasrafcollect.setBounds(200,450,20,20);
		 yimasrafgrubu=  new ButtonGroup();
		 yimasrafgrubu.add(yimasrafprepaid);
		 yimasrafgrubu.add(yimasrafcollect);
		 panelalanlar.add(yimasrafprepaid);
		 panelalanlar.add(yimasrafcollect);
	
		 ydmasraftxt = new JLabel("Y.Dýþý Masraflar:");
		 ydmasraftxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 ydmasraftxt.setBounds(5, 475, 100, 26);
		 panelalanlar.add(ydmasraftxt);
	     
	     prepaid3txt = new JLabel("Prepaid");
		 prepaid3txt.setBounds(140, 475, 100, 26);
		 panelalanlar.add(prepaid3txt);
		 collect3txt = new JLabel("Collect");
	     collect3txt.setBounds(220, 475, 100, 26);
		 panelalanlar.add(collect3txt);
	     
	     ydmasrafprepaid=new JRadioButton();
	     ydmasrafprepaid.setBounds(120,475,20,20);
	     ydmasrafcollect= new JRadioButton();
		 ydmasrafcollect.setBounds(200,475,20,20);
		 ydmasrafcollect.setSelected(true);
		 ydmasrafgrubu=  new ButtonGroup();
		 ydmasrafgrubu.add(ydmasrafprepaid);
		 ydmasrafgrubu.add(ydmasrafcollect);
		 panelalanlar.add(ydmasrafprepaid);
		 panelalanlar.add(ydmasrafcollect);
		 
		 freetimetxt = new JLabel("FreeTime(Max90):");
		 freetimetxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 freetimetxt.setBounds(5, 500, 100, 26);
		 freetimejt=new JTextField();
		 freetimejt.setBounds(120,500, 30, 26);
		 panelalanlar.add(freetimetxt);
	     panelalanlar.add(freetimejt);
	     
	     freetimejt.addKeyListener(new java.awt.event.KeyAdapter() {    
		       public void keyReleased(java.awt.event.KeyEvent emr) {
			            try {
			   			
							int miktar = Integer.parseInt(freetimejt.getText());
							if (miktar>90) {
								freetimejt.setText("");
							}
				            } catch (Exception emr1) {
				            	freetimejt.setText("");
			            }
			        }
			    });
		
		 yuklemekentitxt = new JLabel("Yükleme Kenti:");
		 yuklemekentitxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 yuklemekentitxt.setBounds(575, 100, 100, 26);
		 yuklemekentijt=new JTextField();
		 yuklemekentijt.setEditable(false);
		 yuklemekentijt.setBounds(680,100, 200, 26);
		 yuklemekentiSECdugme =new JButton("Seç");
		 yuklemekentiSECdugme.addActionListener(new Pozisyon());
		 yuklemekentiSECdugme.setActionCommand(dugmecevabi.yuklemekentiSECdugme.name());
		 yuklemekentiSECdugme.setBounds(890,100,75,26);
		 panelalanlar.add(yuklemekentitxt);
	     panelalanlar.add(yuklemekentijt);
		 panelalanlar.add(yuklemekentiSECdugme);
		 
		 yuklemelimanitxt = new JLabel("Yükleme Limaný:");
		 yuklemelimanitxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 yuklemelimanitxt.setBounds(575, 125, 100, 26);
		 yuklemelimanijt=new JTextField();
		 yuklemelimanijt.setEditable(false);
		 yuklemelimanijt.setBounds(680,125, 200, 26);
		 yuklemelimaniSECdugme =new JButton("Seç");
		 yuklemelimaniSECdugme.addActionListener(new Pozisyon());
		 yuklemelimaniSECdugme.setActionCommand(dugmecevabi.yuklemelimaniSECdugme.name());
		 yuklemelimaniSECdugme.setBounds(890,125,75,26);
		 panelalanlar.add(yuklemelimanitxt);
	     panelalanlar.add(yuklemelimanijt);
		 panelalanlar.add(yuklemelimaniSECdugme);
		 
		 aktarmalimanitxt = new JLabel("Aktarma Limaný:");
		 aktarmalimanitxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 aktarmalimanitxt.setBounds(575, 150, 100, 26);
		 aktarmalimanijt=new JTextField();
		 aktarmalimanijt.setEditable(false);
		 aktarmalimanijt.setBounds(680,150, 200, 26);
		 aktarmalimaniSECdugme =new JButton("Seç");
		 aktarmalimaniSECdugme.addActionListener(new Pozisyon());
		 aktarmalimaniSECdugme.setActionCommand(dugmecevabi.aktarmalimaniSECdugme.name());
		 aktarmalimaniSECdugme.setBounds(890,150,75,26);
		 panelalanlar.add(aktarmalimanitxt);
	     panelalanlar.add(aktarmalimanijt);
		 panelalanlar.add(aktarmalimaniSECdugme);
		 
		 varislimanitxt = new JLabel("Varýþ Limaný:");
		 varislimanitxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 varislimanitxt.setBounds(575, 175, 100, 26);
		 varislimanijt=new JTextField();
		 varislimanijt.setEditable(false);
		 varislimanijt.setBounds(680,175, 200, 26);
		 varislimaniSECdugme =new JButton("Seç");
		 varislimaniSECdugme.addActionListener(new Pozisyon());
		 varislimaniSECdugme.setActionCommand(dugmecevabi.varislimaniSECdugme.name());
		 varislimaniSECdugme.setBounds(890,175,75,26);
		 panelalanlar.add(varislimanitxt);
	     panelalanlar.add(varislimanijt);
		 panelalanlar.add(varislimaniSECdugme);
		 
		 sonvariskentitxt = new JLabel("Son Varýþ Kenti:");
		 sonvariskentitxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 sonvariskentitxt.setBounds(575, 200, 100, 26);
		 sonvariskentijt=new JTextField();
		 sonvariskentijt.setEditable(false);
		 sonvariskentijt.setBounds(680,200, 200, 26);
		 sonvariskentiSECdugme =new JButton("Seç");
		 sonvariskentiSECdugme.addActionListener(new Pozisyon());
		 sonvariskentiSECdugme.setActionCommand(dugmecevabi.sonvariskentiSECdugme.name());
		 sonvariskentiSECdugme.setBounds(890,200,75,26);
		 panelalanlar.add(sonvariskentitxt);
	     panelalanlar.add(sonvariskentijt);
		 panelalanlar.add(sonvariskentiSECdugme);
		 
		 varisulkesitxt = new JLabel("Son Varýþ Ülkesi:");
		 varisulkesitxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 varisulkesitxt.setBounds(575, 225, 100, 26);
		 varisulkesijt=new JTextField();
		 varisulkesijt.setBounds(680,225, 200, 26);
		 varisulkesijt.setEditable(false);
		 panelalanlar.add(varisulkesitxt);
	     panelalanlar.add(varisulkesijt);
		 
	     toplambrutkgtxt = new JLabel("Toplam Brut Kg:");
		 toplambrutkgtxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 toplambrutkgtxt.setBounds(575, 275, 100, 26);
		 toplambrutkgjt=new JTextField();
		 toplambrutkgjt.setBounds(680,275, 100, 26);
		 toplambrutkgjt.setEditable(false);
		 panelalanlar.add(toplambrutkgtxt);
	     panelalanlar.add(toplambrutkgjt);
	     
	     toplamnetkgtxt = new JLabel("Toplam Net Kg:");
		 toplamnetkgtxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 toplamnetkgtxt.setBounds(575, 300, 100, 26);
		 toplamnetkgjt=new JTextField();
		 toplamnetkgjt.setEditable(false);
		 toplamnetkgjt.setBounds(680,300, 100, 26);
		 panelalanlar.add(toplamnetkgtxt);
	     panelalanlar.add(toplamnetkgjt);
		
	     toplamhacimtxt = new JLabel("Toplam Hacim:");
		 toplamhacimtxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 toplamhacimtxt.setBounds(575, 325, 100, 26);
		 toplamhacimjt=new JTextField();
		 toplamhacimjt.setBounds(680,325, 100, 26);
		 toplamhacimjt.setEditable(false);
		 panelalanlar.add(toplamhacimtxt);
	     panelalanlar.add(toplamhacimjt);
	     
	     toplamkaptxt = new JLabel("Toplam Kap:");
		 toplamkaptxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 toplamkaptxt.setBounds(575, 350, 100, 26);
		 toplamkapjt=new JTextField();
		 toplamkapjt.setBounds(680,350, 100, 26);
		 toplamkapjt.setEditable(false);
		 panelalanlar.add(toplamkaptxt);
	     panelalanlar.add(toplamkapjt);
	     
	     yukadediaditxt = new JLabel("Yük Adedi:");
		 yukadediaditxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 yukadediaditxt.setBounds(575,375, 100, 26);
		 yukadediadijt=new JTextField();
		 yukadediadijt.setBounds(680,375, 100, 26);
		 yukadediadijt.setEditable(false);
		 panelalanlar.add(yukadediaditxt);
	     panelalanlar.add(yukadediadijt);
	     
	     mblgondericitxt = new JLabel("MBL'de Gönderici:");
	     mblgondericitxt.setHorizontalAlignment(SwingConstants.RIGHT);
	     mblgondericitxt.setBounds(575,425, 100, 26);
	     mblgondericijt=new JTextField();
	     mblgondericijt.setBounds(680,425, 200, 26);
	     mblgondericijt.setEditable(false);
	     mblgondericiSECdugme =new JButton("Seç");
	     mblgondericiSECdugme.addActionListener(new Pozisyon());
	     mblgondericiSECdugme.setActionCommand(dugmecevabi.mblgondericiSECdugme.name());
	     mblgondericiSECdugme.setBounds(890,425,75,26);
	     panelalanlar.add(mblgondericitxt);
	     panelalanlar.add(mblgondericijt);
	     panelalanlar.add(mblgondericiSECdugme);
	     
	     mblalicitxt = new JLabel("MBL'de Alýcý:");
	     mblalicitxt.setHorizontalAlignment(SwingConstants.RIGHT);
	     mblalicitxt.setBounds(575,450, 100, 26);
	     mblalicijt=new JTextField();
	     mblalicijt.setBounds(680,450, 200, 26);
	     mblalicijt.setEditable(false);
	     mblaliciSECdugme =new JButton("Seç");
	     mblaliciSECdugme.addActionListener(new Pozisyon());
	     mblaliciSECdugme.setActionCommand(dugmecevabi.mblaliciSECdugme.name());
	     mblaliciSECdugme.setBounds(890,450,75,26);
	     panelalanlar.add(mblalicitxt);
	     panelalanlar.add(mblalicijt);
	     panelalanlar.add(mblaliciSECdugme);
	     
	     mblnotifytxt = new JLabel("MBL'de Notify:");
	     mblnotifytxt.setHorizontalAlignment(SwingConstants.RIGHT);
	     mblnotifytxt.setBounds(575,475, 100, 26);
	     mblnotifyjt=new JTextField();
	     mblnotifyjt.setBounds(680,475, 200, 26);
	     mblnotifyjt.setEditable(false);
	     mblnotifySECdugme =new JButton("Seç");
	     mblnotifySECdugme.addActionListener(new Pozisyon());
	     mblnotifySECdugme.setActionCommand(dugmecevabi.mblnotifySECdugme.name());
	     mblnotifySECdugme.setBounds(890,475,75,26);
	     panelalanlar.add(mblnotifytxt);
	     panelalanlar.add(mblnotifyjt);
	     panelalanlar.add(mblnotifySECdugme);
	     
	     
	     tablodata = new String [0][0];	 // yükü olmayan pozisyonda tablo boþ olmalý
	     
	     area = new JTextArea();
	        area.setLineWrap(true);
	        area.setWrapStyleWord(true);
	        yp = new JScrollPane(area);
	        yp.setPreferredSize(new Dimension(250,125));
	            
		    area.setText("         **Bu Pozisyon ile ilgili NOTLAR *** \r\n");  
	  	 
		 if(neyapam.equals("düzenle")) {
			 pozuekranagetir(); 
		 }
		 
		 if (!parsiyel.isSelected()) {
			 prefixjt.setVisible(false);
			 suffixjt.setVisible(false);
			 parsiyelkonteynernotxt.setVisible(false);
		 }
		 
		 Pozayukeklecikart.yuklenmisyukdizinihazirla(Pozisyon.poznojt.getText());	    
		 
			    String nox,gondericix,alicix,kapx,kgx;
				String [] pozyuklistesi = new String [Pozayukeklecikart.yuklenmisyuktablodata.length];
						 
				 for (int p=0; p<Pozayukeklecikart.yuklenmisyuktablodata.length; p++) {
			        	nox=Pozayukeklecikart.yuklenmisyuktablodata[p][0];
			        	gondericix=Pozayukeklecikart.yuklenmisyuktablodata[p][1];
			        	if (gondericix.length()>(15)){
			        		gondericix=gondericix.substring(0,14);
			        	}
			        	alicix =Pozayukeklecikart.yuklenmisyuktablodata[p][2];
			        	if (alicix.length()>(15)){
			        		alicix=alicix.substring(0,14);
			        	}
			        	kapx=Pozayukeklecikart.yuklenmisyuktablodata[p][3];
			        	kgx=Pozayukeklecikart.yuklenmisyuktablodata[p][4];
			        	
			        	pozyuklistesi[p]=nox+"-"+gondericix+" / "+alicix+"  "+kapx+" kap, "+kgx+" kg";
			             }
				
				liste= new JComboBox(pozyuklistesi);
				liste.setBounds(525, 74, 440, 20);
				liste.setSelectedItem(null);
				panelalanlar.add(liste);
				
				liste.addItemListener(new ItemListener() {
					@Override
					public void itemStateChanged(ItemEvent e) {
						if (listebayrak==false) {
							if( Yuk.yukbayrak==true) {
					    		return;
					    	}
				    		try {
								Yuk.yuk(String.valueOf(baskicrakamayikla(liste.getSelectedItem().toString())[0]),"düzenle");
							} catch (ParseException e1) {e1.printStackTrace();}
					
							  		listebayrak=true;
						} else { listebayrak=false;	}
						}
				});
				
             listtxt= new JLabel("Gitmek Ýstediðiniz Ekli Yükü Aþaðýdaki Listeden Seçiniz :"); 	
             listtxt.setBounds(650, 50, 440, 20);
			 panelalanlar.add(listtxt);
	       
	        
	       cont  = new Container();
	      // cont.add(yp);  BU ekleme tablo bölümünde yapýldý.Notlar altta çýksýn diye
	       cont.setLayout(new GridLayout(0,1));
	 
	     aticine = frame.getContentPane();
	     Pozdakikonteynertablosu.pozdakikonteynertablosu();
	     aticine.add(panelalanlar, BorderLayout.CENTER);
	     aticine.add(cont, BorderLayout.EAST);
		 frame.setVisible(true);
		
	}   // pozisyon metodu sonu
	
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


	@Override
	public void actionPerformed(ActionEvent e) {
		
		 if (e.getActionCommand()==dugmecevabi.dugmeyenile.name())	{
	    	 
	    	 frame.dispose();
	    	 try {
				pozisyon("düzenle");
			} catch (ParseException e1) {
				
				e1.printStackTrace();
			}
	    	 
	     }
		
		
		     if (e.getActionCommand()==dugmecevabi.yuklemegemisiSECdugme.name())	{
		    	 
		    	 if (yuklemegemisikodu!=0) {
		    		 yuklemegemisikodu=0;
		    		 yuklemegemisijt.setText("");
		    		 return;
		    	 }
		    	 Ontanimliveriler.ontanimliveriler("pozkartyuklemegemisi", "Gemi");
		     }
		     
		     if (e.getActionCommand()==dugmecevabi.aktarmagemisiSECdugme.name())	{
		    	 
		    	 if (aktarmagemisikodu!=0) {
		    		 aktarmagemisikodu=0;
		    		 aktarmagemisijt.setText("");
		    		 return;
		    	 }
		    	 Ontanimliveriler.ontanimliveriler("pozkartaktarmagemisi", "Gemi");
		     }
		     
		     if (e.getActionCommand()==dugmecevabi.yurticigemiacentesiSECdugme.name())	{
		    	 
		    	 if (yurticigemiacentesikodu!=0) {
		    		 yurticigemiacentesikodu=0;
		    		 yurticigemiacentesijt.setText("");
		    		 return;
		    	 }
					
		   	 Sirketkartlari.sirketkartlari("pozkartyurticigemiacentesi");
		     	 
		     }
		     
		     if (e.getActionCommand()==dugmecevabi.aktarmaacentesiSECdugme.name())	{
		    	 
		    	 if (aktarmaacentesikodu!=0) {
		    		 aktarmaacentesikodu=0;
		    		 aktarmaacentesijt.setText("");
		    		 return;
		    	 }
					
		     Sirketkartlari.sirketkartlari("pozkartaktarmaacentesi");
		     	 
		     }
		     
		     if (e.getActionCommand()==dugmecevabi.yurtdisigemiacentesiSECdugme.name())	{
		    	 
		    	 if (yurtdisigemiacentesikodu!=0) {
		    		 yurtdisigemiacentesikodu=0;
		    		 yurtdisigemiacentesijt.setText("");
		    		 return;
		    	 }
					
		    	 Sirketkartlari.sirketkartlari("pozkartyurtdisigemiacentesi");
		     	 
		     }
		     
		   		     
		     if (e.getActionCommand()==dugmecevabi.ydacenteSECdugme.name())	{
		    	 
		    	 if (ydacentekodu!=0) {
		    		 ydacentekodu=0;
		    		 ydacentejt.setText("");
		    		 return;
		    	 }
					
		    	 Sirketkartlari.sirketkartlari("pozkartydacente");
		     } 
		     
		     if (e.getActionCommand()==dugmecevabi.hatadiSECdugme.name())	{
		    	 
		    	 if (hatadikodu!=0) {
		    		 hatadikodu=0;
		    		 hatadijt.setText("");
		    		 return;
		    	 }
					
		    	 Ontanimliveriler.ontanimliveriler("pozhatlar", "Hat");
		     } 
		     
		     if (e.getActionCommand()==dugmecevabi.yuklemekentiSECdugme.name())	{
		    	 
		    	 if (yuklemekentikodu!=0) {
		    		 yuklemekentikodu=0;
		    		 yuklemekentijt.setText("");
		    		 return;
		    	 }
		    	 Ontanimliveriler.ontanimliveriler("pozkartyukleme", "Kent");
		     }
		     
		     if (e.getActionCommand()==dugmecevabi.sonvariskentiSECdugme.name())	{
		    	 
		    	 if (sonvariskentikodu!=0) {
		    		 sonvariskentikodu=0;
		    		 sonvariskentijt.setText("");
		    		 varisulkesijt.setText("");
		    		 return;
		    	 }
		    	 Ontanimliveriler.ontanimliveriler("pozkartsonvaris", "Kent");
		     }
		     
		     if (e.getActionCommand()==dugmecevabi.yuklemelimaniSECdugme.name())	{
		    	 
		    	 if (yuklemelimanikodu!=0) {
		    		 yuklemelimanikodu=0;
		    		 yuklemelimanijt.setText("");
		    		 return;
		    	 }
		    	 Ontanimliveriler.ontanimliveriler("pozkartyukleme", "Liman");
		     }
		     
		     if (e.getActionCommand()==dugmecevabi.aktarmalimaniSECdugme.name())	{
		    	 
		    	 if (aktarmalimanikodu!=0) {
		    		 aktarmalimanikodu=0;
		    		 aktarmalimanijt.setText("");
		    		 return;
		    	 }
		    	 Ontanimliveriler.ontanimliveriler("pozkartaktarma", "Liman");
		     	 
		     }
		     
		     if (e.getActionCommand()==dugmecevabi.varislimaniSECdugme.name())	{
		    	 if (varislimanikodu!=0) {
		    		 varislimanikodu=0;
		    		 varislimanijt.setText("");
		    		 return;
		    	 }
		  	 Ontanimliveriler.ontanimliveriler("pozkartvaris", "Liman");
		     }
		     
		    
		     
		     if (e.getActionCommand()==dugmecevabi.mblgondericiSECdugme.name())	{
		    	 
		    	 if (mblgondericikodu!=0) {
		    		 mblgondericikodu=0;
		    		 mblgondericijt.setText("");
		    		 return;
		    	 }
					
		    	 Sirketkartlari.sirketkartlari("pozmblgonderici");
		     	 
		     }
		 
		     if (e.getActionCommand()==dugmecevabi.mblaliciSECdugme.name())	{
		    	 
		    	 if (mblalicikodu!=0) {
		    		 mblalicikodu=0;
		    		 mblalicijt.setText("");
		    		 return;
		    	 }
					
		    	 Sirketkartlari.sirketkartlari("pozmblalici");
		     	 
		     }
		     
		     if (e.getActionCommand()==dugmecevabi.mblnotifySECdugme.name())	{
		    	 
		    	 if (mblnotifykodu!=0) {
		    		 mblnotifykodu=0;
		    		 mblnotifyjt.setText("");
		    		 return;
		    	 }
					
		    	 Sirketkartlari.sirketkartlari("pozmblnotify");
		     	 
		     }
		     
		     if (e.getActionCommand()==dugmecevabi.dugmekaydet.name())	{
		    	 
		    	 if (parsiyel.isSelected()) {
		    		 if (prefixjt.getText().length()!=4 | suffixjt.getText().length()!=6 ) {
		    			 Bilgipenceresi.anons("Parsiyel Ýþaretlenmiþ Pozisyonun Konteyner Bilgileri Tam Girilmeden Kayýt Yapýlamaz");
		    			 return;
		    		 }
		 		    	
		    	 }
		    	     if(poznojt.getText().equals("")) {
		    	    	 
		    	    	 if(yuklemegemisikodu==0 | yuklemelimanikodu==0 | varislimanikodu==0 ) {
		    	    		 Bilgipenceresi.anons("Yükleme gemisi, Yükleme Limaný, Varýþ Limaný alanlarý mutlaka doldurulmalýdýr");
		    	    		 return;
		    	    		 
		    	    	 }
		    	    	 veriekle();
		    	    Denizyoluanaekran.pozokuma("yenile");
		    	    Denizyoluanaekran.Pozlistesitablosu.tableModel.fireTableDataChanged();
		    		 pozbayrak=false;
		    		 frame.dispose();
		    		 Denizyoluanaekran.secilenpozisyon=sonpoznumarasinedir();
		    		 try { pozisyon("düzenle");	} catch (ParseException e1) { e1.printStackTrace();	}	
		    	 		 
		    	     } else {
		    	    	pozveriduzenle();
		    	    	Denizyoluanaekran.Pozlistesitablosu.tableModel.fireTableDataChanged();
			    		 pozbayrak=false;
			    		 frame.dispose();
			    	 Denizyoluanaekran.secilenpozisyon=poznojt.getText();
			    		 try { pozisyon("düzenle");	} catch (ParseException e1) { e1.printStackTrace();	}	
		    	      }
		    	  
		    	     
		    	
		       }
		     
		        
		     if (e.getActionCommand()==dugmecevabi.dugmeyukler.name())	{
		    	 
		    	 if (Pozayukeklecikart.yukeklecikarbayrak==true) {
		    		 return;
		    	 }
		    
		    	 
		    	 if (parsiyel.isSelected()) {
		    		 if (duzenlenecekpoz[2].length()!=4 | duzenlenecekpoz[3].length()!=6 ) {
		    			 Bilgipenceresi.anons("Parsiyel Ýþaretlenmiþ Pozisyonun Konteyner Bilgileri Tam Girilmeden Yük Ýþlemi Yapýlamaz");
		    			 return;
		    		 }
		 		    	
		    	 }
		    	 
			     	Pozayukeklecikart.pozayukeklecikart(); 
			     }
		     
		    
		     
		     if (e.getActionCommand()==dugmecevabi.radyoparsiyel.name())	{
		    	 
		    	    if (!parsiyel.isSelected()) {
		    	 	 prefixjt.setText("");suffixjt.setText("");
		    		 prefixjt.setVisible(false);
		    		 suffixjt.setVisible(false);
		    		 parsiyelkonteynernotxt.setVisible(false);
		    	 }  else {
		    		 prefixjt.setVisible(true);
		    		 suffixjt.setVisible(true);
		    		 parsiyelkonteynernotxt.setVisible(true);
		    	 }
		     }
		     
		     if (e.getActionCommand()==dugmecevabi.dugmesil.name())	{
		    	 if (tablodata.length>0) {
		    		 Bilgipenceresi.anons("Ýçinde Yük Bulunan Pozisyon Silinemez");
		    		 return;
		    	 }
		    	 
		    	 if (bupozunaltindafaturavarmi(Integer.parseInt(poznojt.getText()))) {
		    		 Bilgipenceresi.anons("Ýçinde Fatura Bulunan Pozisyon Silinemez");
		    		 return;
		    	 }
		    	 
		    	 
		    	 
		    	 Sileyimmi.sonkarar("yuk");
		    	if (Sileyimmi.cevap.equals("hayýr")) {
		    		 return;
		    	 }else {
		    		 pozverisil();
		    		 Denizyoluanaekran.pozokuma("yenile");
		    		 Denizyoluanaekran.Pozlistesitablosu.tableModel.fireTableDataChanged();
		    		 Denizyoluanaekran.secilenpozisyon="";
				     pozbayrak=false;
				     frame.dispose();
		    	 }
			     }
		     
		     if (e.getActionCommand()==dugmecevabi.dugmeyeni.name())	{
		    	 
		    	 if(poznojt.getText().equals("") ) {
		    		 return;		    		 
		    	 }
		    	 pozbayrak=false;
		    	 frame.dispose();
		    	 try {
					Pozisyon.pozisyon("yeni");
				} catch (ParseException e1) {e1.printStackTrace();}
				
		     }
		     
             if (e.getActionCommand()==dugmecevabi.dugmegelirgider.name())	{
            	 
            	 if ( Pozgelirgiderbakiyeler.gelgitpencereacikbayrak==true) {
            		 return;
            	 }
            	
		    	 Pozgelirgiderbakiyeler.pozgelirgiderbakiyeler(duzenlenecekpoz[0]);
			    	 
			     } 
             
             if (e.getActionCommand()==dugmecevabi.dugmemblbas.name())	{
            	 
            	 if (tablodata.length==0) {
		    		 Bilgipenceresi.anons("Ýçinde Yük Bulunmayan Pozisyonun MBL'i Basýlamaz");
		    		 return;
		    	 }
            	 
            	
		    	 Blyap.blyap(poznojt.getText(), "0");
			    	 
			     } 
		} //dugme override sonu

	private void veriekle() {
	
		String sqlkomut="INSERT INTO pozisyonlar "
		 		
		 		+ "(   parsiyel ,"
		 		+ "   prefix  ,"
		 		+ "   suffix ,"
		 		+ "   istipi  ,"
		 		+ "   mblno ,"
		 		+ "   mbltarih,"
		 		+ "   hatadikodu ,"
		 		+ "   yuklemegemisikodu  ,"
		 		+ "   yuklemegemisisefer ,"
		 		+ "   aktarmagemisikodu  ,"
		 		+"    aktarmagemisisefer ,"
		 		+ "   yurticigemiacentesikodu  ,"
		 		+ "   aktarmaacentesikodu ,"
		 		+ "   yurtdisigemiacentesikodu ,"
		 		+ "   karsipozisyon ,"
		 		+ "   kalkistarihi ,"
		 		+ "   varistarihi ,"
		 		+ "   navluntutari  ,"
		 		+ "   navlunprepaid,"
		 		+ "   yimasrafprepaid ,"
		 		+ "   ydmasrafprepaid ,"
		 		+ "   yuklemekentikodu ,"
		 		+ "   yuklemelimanikodu ,"
		 		+ "   aktarmalimanikodu ,"
		 		+ "   varislimanikodu ,"
		 		+ "   sonvariskentikodu ,"
		 		+ "   hatrezno        ,"
		 		+ "  ydacentekodu     ,"
		 		+ "  mblgondericikodu ,"
		 		+ "  mblalicikodu     ,"
		 		+ "  mblnotifykodu    ,"
		 		+ "  notlar           ,"
		 		+ "  freetime   ) VALUES ( "
		  		
		 		+parsiyel.isSelected()+","  //parsiyel 
		 		+ "'"+prefixjt.getText()+"',"  //prefix
		 		+ "'"+suffixjt.getText()+"',"  //  suffix 
		 		+ "'"+istipicb.getSelectedIndex()+"',"  //istipi
		 		+ "'"+mblnojt.getText()+"',"     // mblno
		 		+ "'"+df.format(picker.getDate())+"',"   // mbltarih
		 		+ "'"+hatadikodu+"',"        // hatadikodu
		 		+ "'"+yuklemegemisikodu+"',"       //yuklemegemisikodu
		 		+ "'"+yuklemegemisiseferjt.getText()+"'," // yuk gem sefer kodu
		 		+ "'"+aktarmagemisikodu+"',"   //aktarmagemisikodu
		 		+ "'"+aktarmagemisiseferjt.getText()+"'," // aktarma gem sefer kodu
		 		+ "'"+yurticigemiacentesikodu+"',"  // yurticigemiacentesikodu
		 		+ "'"+aktarmaacentesikodu+"',"  // aktarmaacentesikodu
		 		+ "'"+yurtdisigemiacentesikodu+"',"    //yurtdisigemiacentesikodu 
		 		+ "'"+karsipozisyonjt.getText()+"',"  // karsipozisyon
		 		+ "'"+df.format(pickerkalkis.getDate())+"',"        //  kalkistarihi
		 		+ "'"+df.format(pickervaris.getDate())+"',"      //  varistarihi
		 		+ "'"+navluntutarijt.getText()+"',"             // navluntutari
		 		+navlunprepaid.isSelected()+","     // navlunprepaid
		 		+yimasrafprepaid.isSelected()+","    //  yimasrafprepaid
		 		+ydmasrafprepaid.isSelected()+","   // ydmasrafprepaid
		 		+ "'"+yuklemekentikodu+"',"   // yuklemekentikodu
		 		+ "'"+yuklemelimanikodu+"',"   //  yuklemelimanikodu
		 		+ "'"+aktarmalimanikodu+"',"    // aktarmalimanikodu
		 		+ "'"+varislimanikodu+"',"	// varislimanikodu
		        + "'"+sonvariskentikodu+"',"       // //sonvariskentikodu                    
		        + "'"+hatreznojt.getText()+"',"  
		        + "'"+ydacentekodu+"',"                                 //   
		        + "'"+mblgondericikodu+"',"
		        + "'"+mblalicikodu+"',"
		        + "'"+mblnotifykodu+"',"
		        + "'"+area.getText()+"'," 
		          + "'"+freetimejt.getText()+"' );"; 
		try{                                             
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection(  
	"jdbc:mysql://"+Anasinif.sqlip+"/nal2000?useUnicode=true&characterEncoding=UTF-8",Anasinif.sqluser,Anasinif.sqlpass);	
			Statement stmt=con.createStatement();  
	
			stmt.executeUpdate(sqlkomut);
			con.close();  
			
			}catch(Exception e){ System.out.println(e);} 
		
		Bilgipenceresi.anons("Yeni Pozisyon Kaydedildi");
		
		
			 	 
		
	}
	
	public static void pozuekranagetir() throws ParseException  {
		//
		duzenlenecekpoz= new String[35];
		
		try{                                              
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection(
					"jdbc:mysql://"+Anasinif.sqlip+"/nal2000?useUnicode=true&characterEncoding=UTF-8",Anasinif.sqluser,Anasinif.sqlpass);
			
					Statement stmt=con.createStatement();  
			   		ResultSet rs=stmt.executeQuery("select * from pozisyonlar where pozno='"+Denizyoluanaekran.secilenpozisyon+"'"); 
			
			while(rs.next()) {
				for (int j=0; j<35; j++ ) {
					duzenlenecekpoz[j]=rs.getString(j+1);
					}
			}
			con.close();  
			
			}catch(Exception e){ System.out.println(e);}
	
	    poznojt.setText(duzenlenecekpoz[0]);   // pozno 1
	    
	    if (duzenlenecekpoz[1].equals("1")) {   // parsiyel kutusu 2
	    	parsiyel.setSelected(true);
	    } else {
	    	parsiyel.setSelected(false);
	    }
	    
	    	    
	    prefixjt.setText(duzenlenecekpoz[2]);  // prefix 3
	    suffixjt.setText(duzenlenecekpoz[3]);  // suffix 4
	    istipicb.setSelectedIndex(Integer.parseInt(duzenlenecekpoz[4])); // grup istipi 5 
	    mblnojt.setText(duzenlenecekpoz[5]);  // mbl no 6 
	    mblnojt.setCaretPosition(0);
	    
	    Date mbltarihi=new SimpleDateFormat("yyyy-MM-dd").parse(duzenlenecekpoz[6]);
	    picker.setDate(mbltarihi);             // mbl tarihi 7
	    
	    hatadikodu=Integer.parseInt(duzenlenecekpoz[7]);  // hat adý 8 
	    hatadijt.setText(Ontanimliveriler.hatkoducoz(Integer.parseInt(duzenlenecekpoz[7])));
	    hatadijt.setCaretPosition(0);
	    
	    yuklemegemisikodu=Integer.parseInt(duzenlenecekpoz[8]);  // yukleme gemisi 9
	    yuklemegemisijt.setText(Ontanimliveriler.gemikoducoz(Integer.parseInt(duzenlenecekpoz[8])));
	    yuklemegemisijt.setCaretPosition(0);
	    
	    yuklemegemisiseferjt.setText(duzenlenecekpoz[9]); // yuk sefer 10
	    yuklemegemisiseferjt.setCaretPosition(0);
	    
	    aktarmagemisikodu=Integer.parseInt(duzenlenecekpoz[10]);  // aktarma gemisi 11
	    aktarmagemisijt.setText(Ontanimliveriler.gemikoducoz(Integer.parseInt(duzenlenecekpoz[10])));
	    aktarmagemisijt.setCaretPosition(0);
	    
	    aktarmagemisiseferjt.setText(duzenlenecekpoz[11]); // yuk sefer 12
	    aktarmagemisiseferjt.setCaretPosition(0);
	    
	    yurticigemiacentesikodu=Integer.parseInt(duzenlenecekpoz[12]);  //  13 
	    yurticigemiacentesijt.setText(Ontanimliveriler.sirketkoducoz(Integer.parseInt(duzenlenecekpoz[12]))[1]);
	    yurticigemiacentesijt.setCaretPosition(0);
	    
	    aktarmaacentesikodu=Integer.parseInt(duzenlenecekpoz[13]);  //  14
	    aktarmaacentesijt.setText(Ontanimliveriler.sirketkoducoz(Integer.parseInt(duzenlenecekpoz[13]))[1]);
	    aktarmaacentesijt.setCaretPosition(0);
	    
	    yurtdisigemiacentesikodu=Integer.parseInt(duzenlenecekpoz[14]);  //  15 
	    yurtdisigemiacentesijt.setText(Ontanimliveriler.sirketkoducoz(Integer.parseInt(duzenlenecekpoz[14]))[1]);
	    yurtdisigemiacentesijt.setCaretPosition(0);
	    
	    karsipozisyonjt.setText(duzenlenecekpoz[15]);
	    
	    Date kalkistarihi=new SimpleDateFormat("yyyy-MM-dd").parse(duzenlenecekpoz[16]);
	    Date varistarihi=new SimpleDateFormat("yyyy-MM-dd").parse(duzenlenecekpoz[17]);
	    pickerkalkis.setDate(kalkistarihi) ; pickervaris.setDate(varistarihi);
	    
	    navluntutarijt.setText(duzenlenecekpoz[18]);
	    navluntutarijt.setCaretPosition(0);
		    
	    if (duzenlenecekpoz[19].equals("1")) {   // navlun prepaid
	    	navlunprepaid.setSelected(true);
	    } else {
	    	navluncollect.setSelected(true);
	    }
	    
	    if (duzenlenecekpoz[20].equals("1")) {   // yi masraf prepaid
	    	yimasrafprepaid.setSelected(true);
	    } else {
	    	yimasrafcollect.setSelected(true);
	    }
	    
	    if (duzenlenecekpoz[21].equals("1")) {   // yd masraf prepaid
	    	ydmasrafprepaid.setSelected(true);
	    } else {
	    	ydmasrafcollect.setSelected(true);
	    }
	    
	    yuklemekentikodu=Integer.parseInt(duzenlenecekpoz[22]);  
	    yuklemekentijt.setText(Ontanimliveriler.kentkoducoz(Integer.parseInt(duzenlenecekpoz[22]))[0]);
	    yuklemekentijt.setCaretPosition(0);
	    
	    yuklemelimanikodu=Integer.parseInt(duzenlenecekpoz[23]); 
	    yuklemelimanijt.setText(Ontanimliveriler.limankoducoz(Integer.parseInt(duzenlenecekpoz[23])));
	    yuklemelimanijt.setCaretPosition(0);
	    
	    aktarmalimanikodu=Integer.parseInt(duzenlenecekpoz[24]);  
	    aktarmalimanijt.setText(Ontanimliveriler.limankoducoz(Integer.parseInt(duzenlenecekpoz[24])));
	    aktarmalimanijt.setCaretPosition(0);
	    
	    varislimanikodu=Integer.parseInt(duzenlenecekpoz[25]); 
	    varislimanijt.setText(Ontanimliveriler.limankoducoz(Integer.parseInt(duzenlenecekpoz[25])));
	    varislimanijt.setCaretPosition(0);
	    
	    sonvariskentikodu=Integer.parseInt(duzenlenecekpoz[26]);  
	    sonvariskentijt.setText(Ontanimliveriler.kentkoducoz(Integer.parseInt(duzenlenecekpoz[26]))[0]);
	    sonvariskentijt.setCaretPosition(0);
	    
	    varisulkesijt.setText(Ontanimliveriler.kentkoducoz(Integer.parseInt(duzenlenecekpoz[26]))[1]);
	    varisulkesijt.setCaretPosition(0);
	   
	    // duzenlenecekpoz[27] alaný kullanýlmýyor. son varýþ ülkesi. bu alaný son varýþ kentinden çekiyoruz.
	    
	    hatreznojt.setText(duzenlenecekpoz[28]);
	    hatreznojt.setCaretPosition(0);
	     
	    ydacentekodu=Integer.parseInt(duzenlenecekpoz[29]);  
	    ydacentejt.setText(Ontanimliveriler.sirketkoducoz(Integer.parseInt(duzenlenecekpoz[29]))[1]);
	    ydacentejt.setCaretPosition(0);
	    
	    mblgondericikodu=Integer.parseInt(duzenlenecekpoz[30]);
	    mblgondericijt.setText(Ontanimliveriler.sirketkoducoz(Integer.parseInt(duzenlenecekpoz[30]))[1]);
	    mblgondericijt.setCaretPosition(0);
	    
	    mblalicikodu=Integer.parseInt(duzenlenecekpoz[31]);
	    mblalicijt.setText(Ontanimliveriler.sirketkoducoz(Integer.parseInt(duzenlenecekpoz[31]))[1]);
	    mblalicijt.setCaretPosition(0);
	    
	    mblnotifykodu=Integer.parseInt(duzenlenecekpoz[32]);
	    mblnotifyjt.setText(Ontanimliveriler.sirketkoducoz(Integer.parseInt(duzenlenecekpoz[32]))[1]);
	    mblnotifyjt.setCaretPosition(0);
	    
	    area.setText(duzenlenecekpoz[33]);
	    
	    freetimejt.setText(duzenlenecekpoz[34]);
	    
	    if (pozdakikonteynerleribul(poznojt.getText())[4]!=0) { // eðer poza yük alýnmýþ ise istipi ve parsiyel deðiþtirilemesin 
	    	istipicb.setEnabled(false);
	    	parsiyel.setEnabled(false);
	    	suffixjt.setEditable(false);
	    	prefixjt.setEditable(false);
	    	
	    }
	    
	    
	} // pozuekrana getir metodu sonu
	
private void pozverisil() {
		
		try {  
		     
			Class.forName("com.mysql.cj.jdbc.Driver");
	        Connection connection = DriverManager.getConnection("jdbc:mysql://"+Anasinif.sqlip+"/nal2000?useUnicode=true&characterEncoding=UTF-8",Anasinif.sqluser,Anasinif.sqlpass);
	        PreparedStatement st = connection.prepareStatement("DELETE FROM pozisyonlar WHERE pozno ='" +poznojt.getText() + "';");
	        st.executeUpdate(); 
	  
			} catch(Exception e) {
	        System.out.println(e);
			}
		Bilgipenceresi.anons("Pozisyon Silindi");
	}

private  void pozveriduzenle ()  {
	
		
	String sqlkomut="Update pozisyonlar set "
		+ "   parsiyel ="+parsiyel.isSelected()+","
		+"   prefix  ='"+prefixjt.getText()+"',"
		+"   suffix ='"+suffixjt.getText()+"',"
		+"   istipi ='"+istipicb.getSelectedIndex()+"',"
		+"   mblno ='"+mblnojt.getText()+"',"
		+"   mbltarih ='"+df.format(picker.getDate())+"',"
		+"   hatadikodu ='"+hatadikodu+"',"
		+"   yuklemegemisikodu ='"+yuklemegemisikodu+"',"
		+"   yuklemegemisisefer ='"+yuklemegemisiseferjt.getText()+"',"
		+ "  aktarmagemisikodu ='"+aktarmagemisikodu+"',"
		+"   aktarmagemisisefer='"+aktarmagemisiseferjt.getText()+"',"
		+"   yurticigemiacentesikodu='"+yurticigemiacentesikodu+"',"
		+"   aktarmaacentesikodu='"+aktarmaacentesikodu+"',"
		+"   yurtdisigemiacentesikodu='"+yurtdisigemiacentesikodu+"',"
		+"   karsipozisyon='"+karsipozisyonjt.getText()+"',"
		+"   kalkistarihi='"+df.format(pickerkalkis.getDate())+"',"
		+"   varistarihi='"+df.format(pickervaris.getDate())+"',"
		+"   navluntutari='"+navluntutarijt.getText()+"',"
		+"   navlunprepaid="+navlunprepaid.isSelected()+"," 
		+"   yimasrafprepaid="+yimasrafprepaid.isSelected()+"," 
		+"   ydmasrafprepaid ="+ydmasrafprepaid.isSelected()+","
		+"   yuklemekentikodu ='"+yuklemekentikodu+"',"
		+"   yuklemelimanikodu='"+yuklemelimanikodu+"',"
		+"   aktarmalimanikodu ='"+aktarmalimanikodu+"' ,"
		+"   sonvariskentikodu ='"+sonvariskentikodu+"', "
		+"   hatrezno ='"+hatreznojt.getText()+"', "
		+"   ydacentekodu ='"+ydacentekodu+"', "
		+"   mblgondericikodu ='"+mblgondericikodu+"', "
		+"   mblalicikodu ='"+mblalicikodu+"' ,"
		+"   mblnotifykodu ='"+mblnotifykodu+"' ,"
		+"   notlar ='"+area.getText()+"' ,"
		+"   freetime ='"+freetimejt.getText()+"' "
		
		+" where pozno='"+poznojt.getText()+"'";
	
	
	
	try{                                             
		Class.forName("com.mysql.cj.jdbc.Driver");  
		Connection con=DriverManager.getConnection(  
"jdbc:mysql://"+Anasinif.sqlip+"/nal2000?useUnicode=true&characterEncoding=UTF-8",Anasinif.sqluser,Anasinif.sqlpass);	
		Statement stmt=con.createStatement();  

		stmt.executeUpdate(sqlkomut);
		con.close();  
		
		}catch(Exception e){ System.out.println(e);}  
	
	Denizyoluanaekran.pozokuma("acilis");
	Denizyoluanaekran.Pozlistesitablosu.tableModel.fireTableDataChanged();
	
	Bilgipenceresi.anons("Deðiþiklikler Kaydedildi");
}

public static int []pozdakikonteynerleribul(String hangipoz) {

	String konteynerhamveri="",konteyneryukcinsi="";
	int yuktekikonteynersayisi=0;
	int pozdakikonteynersayisi=0;
	int pozdakiyukadedi=0;
	int pozdakiteu=0;
	
	try{                                              
		Class.forName("com.mysql.cj.jdbc.Driver");  
		Connection con=DriverManager.getConnection(
	"jdbc:mysql://"+Anasinif.sqlip+"/nal2000?useUnicode=true&characterEncoding=UTF-8",Anasinif.sqluser,Anasinif.sqlpass);
				
				Statement stmt=con.createStatement();  
		        ResultSet rs=stmt.executeQuery("SELECT * FROM yukler where yuklendigipoz='"+hangipoz+"';"); 
		
		while(rs.next()) {
			konteynerhamveri=rs.getString(25);
			Fatyap.konteynerno=konteynerhamveri.substring(2,12);
			String kontsayisi=konteynerhamveri.substring(konteynerhamveri.length()-24, konteynerhamveri.length()-22);
			pozdakikonteynersayisi=pozdakikonteynersayisi+Integer.parseInt(kontsayisi);
			pozdakiyukadedi++;
		}
		con.close();  
		
		}catch(Exception e){ System.out.println(e);}  
	
	tablodata = new String [pozdakikonteynersayisi][5]; 
	masterblicinkonteynermalcinsi =new String[pozdakikonteynersayisi]; 
	
	int yuktoplamkg=0,yuktoplamkap=0,yuktoplamhacim=0,yuktoplamnet=0,
		poztoplamkg=0,poztoplamkap=0,poztoplamhacim=0,poztoplamnet=0,j=0;
	
	try{                                              
		Class.forName("com.mysql.cj.jdbc.Driver");  
		Connection con=DriverManager.getConnection(
	"jdbc:mysql://"+Anasinif.sqlip+"/nal2000?useUnicode=true&characterEncoding=UTF-8",Anasinif.sqluser,Anasinif.sqlpass);
				
				Statement stmt=con.createStatement();  
		        ResultSet rs=stmt.executeQuery("SELECT * FROM yukler where yuklendigipoz='"+hangipoz+"';"); 
		
		while(rs.next()) { // her yük için ayrý döngüye giriyoruz.. 
		konteynerhamveri=rs.getString(25);
		
	    konteyneryukcinsi=rs.getString(15);
		
		yuktoplamhacim=Integer.parseInt(rs.getString(27));
	    yuktoplamnet=Integer.parseInt(rs.getString(26));
		yuktekikonteynersayisi=konteynerhamveri.length()/24;
	        for (int i=1; i<yuktekikonteynersayisi+1; i++) {// 
	        	tablodata[j+i-1][0]=String.valueOf(j+i); 
	        	tablodata[j+i-1][1]=konteynerhamveri.substring(((i*24)-22),((i*24))-12);   // Kont No
	        	tablodata[j+i-1][2]=konteynerhamveri.substring(((i*24)-12),((i*24))-6);  //Kont kap
	        	yuktoplamkap=yuktoplamkap+Integer.parseInt(tablodata[j+i-1][2].trim());
	        	tablodata[j+i-1][3]=konteynerhamveri.substring(((i*24)-6),(i*24)-1); //kont kg
	        	yuktoplamkg=yuktoplamkg+Integer.parseInt(tablodata[j+i-1][3].trim()); 
	        	tablodata[j+i-1][4]=konteynerhamveri.substring((i*24)-1,i*24); // kont tipi
	        	String kaclikkont=Anasinif.konteynertipleri[Integer.parseInt(tablodata[j+i-1][4])].substring(0, 2);
	        	if (kaclikkont.equals("20")) {
	        		pozdakiteu=pozdakiteu+1;  // 20 kont 1 TEU
	        	} else {
	        		pozdakiteu=pozdakiteu+2;   // diðerleri 2 TEU
	        	}
	        		
	        	masterblicinkonteynermalcinsi[j+i-1]=konteyneryukcinsi;
	        
	            }
	        j=j+yuktekikonteynersayisi;
	        poztoplamkg=poztoplamkg+yuktoplamkg;
	        poztoplamkap=poztoplamkap+yuktoplamkap;
	        poztoplamhacim=poztoplamhacim+yuktoplamhacim;
	        poztoplamnet=poztoplamnet+yuktoplamnet;
	        yuktoplamkg=yuktoplamkap=yuktoplamhacim=yuktoplamnet=0;
	       
		} // while döngüsü sonu
		con.close();  
		
		}catch(Exception e){ System.out.println(e);} 
	
	  try {
		toplambrutkgjt.setText(String.valueOf(poztoplamkg));
		toplamnetkgjt.setText(String.valueOf(poztoplamnet));
		toplamhacimjt.setText(String.valueOf(poztoplamhacim));
		toplamkapjt.setText(String.valueOf(poztoplamkap));
		yukadediadijt.setText(String.valueOf(pozdakiyukadedi));
	} catch (java.lang.NullPointerException e) { }
	 
	  int pozdonus[]= {poztoplamkg,poztoplamnet,poztoplamhacim,poztoplamkap,pozdakiyukadedi,pozdakiteu,tablodata.length};
	 
	  return pozdonus;
 }  //  pozdakikonteynerleribul() sonu

public static String sonpoznumarasinedir() {
	//
	String sonpoznumarasi="";
		try{                                              
		Class.forName("com.mysql.cj.jdbc.Driver");  
		Connection con=DriverManager.getConnection(
				"jdbc:mysql://"+Anasinif.sqlip+"/nal2000?useUnicode=true&characterEncoding=UTF-8",Anasinif.sqluser,Anasinif.sqlpass);
		
				Statement stmt=con.createStatement();  
		   		ResultSet rs=stmt.executeQuery("select * from pozisyonlar "); 
		
		while(rs.next()) {
		sonpoznumarasi=rs.getString(1);
		}
		con.close();  
		}catch(Exception e){ System.out.println(e);}
	
	return sonpoznumarasi;
	}

private static boolean bupozunaltindafaturavarmi(int pozno) {
	
	int kacfatura=0;
	
	try{                                              
		Class.forName("com.mysql.cj.jdbc.Driver");  
		Connection con=DriverManager.getConnection(
				"jdbc:mysql://"+Anasinif.sqlip+"/nal2000?useUnicode=true&characterEncoding=UTF-8",Anasinif.sqluser,Anasinif.sqlpass);
				
				Statement stmt=con.createStatement();  
		        ResultSet rs=stmt.executeQuery("select * from fiskayitlari where pozno="+pozno); 
		
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

@SuppressWarnings("serial")
public static class Pozdakikonteynertablosu extends JPanel {
	static Tablomodeli tableModel;
	static JScrollPane scrollPane;
	static String gostermeliktablodata[][] ;

	public Pozdakikonteynertablosu() {
        initializePanel();
    }

    private void initializePanel() {
       
       tableModel = new Tablomodeli();

        JTable table = new JTable(tableModel);
        
        table.setFillsViewportHeight(true);
        table.setPreferredScrollableViewportSize(new Dimension(500,100));
        table.setRowSelectionAllowed(false);
        table.setFocusable(false);
		   
	    table.getColumnModel().getColumn(0).setPreferredWidth(25);
	    table.getColumnModel().getColumn(1).setPreferredWidth(150);
	    table.getColumnModel().getColumn(2).setPreferredWidth(90);
	    
	  
	      scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(265,400));
        this.setLayout(new BorderLayout());
        this.add(scrollPane, BorderLayout.CENTER);
    }

    public static void showFrame() {
        JPanel panel = new Pozdakikonteynertablosu();
        panel.setOpaque(true);

     Pozisyon.cont.add(scrollPane);
     Pozisyon.cont.add(yp);
      
    }

    class Tablomodeli extends AbstractTableModel {
     
        private  String[] columnNames = { "#", "KONTEYNER#","KAP","KG","TÝP" };

        public int getRowCount() {
           
        	return gostermeliktablodata.length;
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
        		rowIndex=gostermeliktablodata.length-1;
        		        		
        	}
            return gostermeliktablodata[rowIndex][columnIndex];
        }
    }
    
    public static void gostermeligidegistir () {  // konteyner tablosunun kaynaðýný da güncelle 
  	 
    	gostermeliktablodata= new String[Pozisyon.tablodata.length][5];
    	for (int i=0; i<Pozisyon.tablodata.length ;i++) {
    	
    		Pozdakikonteynertablosu.gostermeliktablodata[i][0]=Pozisyon.tablodata[i][0];
    		Pozdakikonteynertablosu.gostermeliktablodata[i][1]=Pozisyon.tablodata[i][1];
    		Pozdakikonteynertablosu.gostermeliktablodata[i][2]=Pozisyon.tablodata[i][2];
    		Pozdakikonteynertablosu.gostermeliktablodata[i][3]=Pozisyon.tablodata[i][3];
    		String neymiskonteyner=Anasinif.konteynertipleri[Integer.parseInt(Pozisyon.tablodata[i][4])];
    		Pozdakikonteynertablosu.gostermeliktablodata[i][4]=neymiskonteyner;
    		    		
    	}
    }

    public static void pozdakikonteynertablosu () {
    	gostermeligidegistir ();
    	    	
        SwingUtilities.invokeLater(new Runnable() {
          public void run() {
                showFrame();
            }
        });
    }
}

} // sinif sonu ;