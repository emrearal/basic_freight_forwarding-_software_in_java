package heanalikibin;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Ontanimliveridegirtireklesil implements ActionListener {
	
	static JLabel baslik,lb1,lb2,lb3,lb4;
	static JButton yavrudugme ,yavrudugme2 ;
	static JDialog yavru ;
	static JTextField jt1,jt2,jt3,jt4;
	static String ekranj1,ekranj2,ekranj3,ekranj4,sqlkomutsil,sqlkomutupdate1,
	sqlkomutupdate2,sqlkomutupdate3,sqlkomutupdate4,
	sqlkomutekle,sqlkomutveriarama,alan1,alan2;
		
	private enum evethayir {
		basvazgec,baskaydet;
	}

	public static void sqlkomutbelirleme() {
	
	switch (Ontanimliveriler.tip) {
	   
	   case "Liman" :
		   sqlkomutsil="DELETE FROM limanlar WHERE limankodu ='" +Ontanimliveriler.secilikod+ "';";
		   sqlkomutupdate1="update limanlar set limanadi=? where limankodu=? ";
		   sqlkomutupdate2="update limanlar set limankenti=? where limankodu=? ";	   
		   sqlkomutekle="INSERT INTO limanlar (limanadi,limankenti) VALUES ('"+ekranj1+"','"+ekranj2+"')";
		   sqlkomutveriarama="SELECT * FROM limanlar WHERE limankodu =" +Ontanimliveriler.secilikod + ";";
		   alan1="Liman Adý:" ; alan2="Kent:";
		  
	     break;
	   case "T.Sekli" : 
		   sqlkomutsil="DELETE FROM teslimsekli WHERE teslimseklikodu ='" +Ontanimliveriler.secilikod+ "';";
		   sqlkomutupdate1="update teslimsekli set teslimseklikisaltmasi=? where teslimseklikodu=? ";
		   sqlkomutupdate2="update teslimsekli set teslimsekliuzaltmasi=? where teslimseklikodu=? ";	   
		   sqlkomutekle="INSERT INTO teslimsekli (teslimseklikisaltmasi,teslimsekliuzaltmasi) VALUES ('"+ekranj1+"','"+ekranj2+"')";
		   sqlkomutveriarama="SELECT * FROM teslimsekli WHERE teslimseklikodu =" +Ontanimliveriler.secilikod + ";";
		   alan1="Kýsaltma:" ; alan2="Açýklama:";
		   
		     break;
	   case "Kent" : 
		   sqlkomutsil="DELETE FROM kentler WHERE kentkodu ='" +Ontanimliveriler.secilikod+ "';";
		   sqlkomutupdate1="update kentler set kentadi=? where kentkodu=? ";
		   sqlkomutupdate2="update kentler set kentinulkesi=? where kentkodu=? ";	   
		   sqlkomutekle="INSERT INTO kentler (kentadi,kentinulkesi) VALUES ('"+ekranj1+"','"+ekranj2+"');";
		   sqlkomutveriarama="SELECT * FROM kentler WHERE kentkodu =" +Ontanimliveriler.secilikod + ";";
		   alan1="Kent Adý:" ; alan2="Ülke:";
		  
		     break;
	   case "Odeme" : 
		   sqlkomutsil="DELETE FROM odemesekli WHERE odemeseklikodu ='" +Ontanimliveriler.secilikod+ "';";
		   sqlkomutupdate1="update odemesekli set odemeseklikisaltmasi=? where odemeseklikodu=? ";
		   sqlkomutupdate2="update odemesekli set odemesekliuzaltmasi=? where odemeseklikodu=? ";	   
		   sqlkomutekle="INSERT INTO odemesekli (odemeseklikisaltmasi,odemesekliuzaltmasi) VALUES ('"+ekranj1+"','"+ekranj2+"')";
		   sqlkomutveriarama="SELECT * FROM odemesekli WHERE odemeseklikodu =" +Ontanimliveriler.secilikod + ";";
		   alan1="Kýsaltma:" ; alan2="Açýklama:";
		   break;
	
	   case "Gemi" : 
		   sqlkomutsil="DELETE FROM gemiler WHERE gemikodu ='" +Ontanimliveriler.secilikod+ "';";
		   sqlkomutupdate1="update gemiler set gemiadi=? where gemikodu=? ";
		   sqlkomutupdate2="update gemiler set gemiyapimyili=? where gemikodu=? ";	   
		   sqlkomutupdate3="update gemiler set gemibayrak=? where gemikodu=? ";
		   sqlkomutupdate4="update gemiler set gemiimo=? where gemikodu=? ";
		   sqlkomutekle="INSERT INTO gemiler (gemiadi,gemiyapimyili,gemibayrak,gemiimo) VALUES ('"+ekranj1+"','"+ekranj2+"','"+ekranj3+"',' "+ekranj4+"')";
		   sqlkomutveriarama="SELECT * FROM gemiler WHERE gemikodu =" +Ontanimliveriler.secilikod + ";";
		   alan1="Gemi Adý:" ; alan2="Yapým Yýlý:";	   
	          break;
	    
	   case "Hat" : 
	  	   sqlkomutsil="DELETE FROM hatlar WHERE hatkodu ='" +Ontanimliveriler.secilikod+ "';";
		   sqlkomutupdate1="update hatlar set hatkisaltmasi=? where hatkodu=? ";
		   sqlkomutupdate2="update hatlar set hatuzaltmasi=? where hatkodu=? ";	   
		   sqlkomutekle="INSERT INTO hatlar (hatkisaltmasi,hatuzaltmasi) VALUES ('"+ekranj1+"','"+ekranj2+"')";
		   sqlkomutveriarama="SELECT * FROM hatlar WHERE hatkodu =" +Ontanimliveriler.secilikod + ";";
		   alan1="Kýsaltma:" ; alan2="Hat Adý:";
		   break;
	    
	   case "Faturakalemleri" : 
		  
	  	   sqlkomutsil="DELETE FROM faturakalemleri WHERE kalemkodu ='" +Ontanimliveriler.secilikod+ "';";
		   sqlkomutupdate1="update faturakalemleri set kdvorani=? where kalemkodu=? ";
		   sqlkomutupdate2="update faturakalemleri set kalemadi=? where kalemkodu=? ";	   
		   sqlkomutekle="INSERT INTO faturakalemleri (kdvorani,kalemadi) VALUES ('"+ekranj1+"','"+ekranj2+"')";
		   sqlkomutveriarama="SELECT * FROM faturakalemleri WHERE kalemkodu =" +Ontanimliveriler.secilikod + ";";
		   alan1="Kdv %:" ; alan2="Kalem Adý:";
		   break;
		   
		   
		   
	   }
}
	
public static void ontanimliveridegirtireklesil() {
	
	sqlkomutbelirleme();
	
	yavrudugme= new JButton("VAZGEÇ");
	yavrudugme2 = new JButton("ONAY");
	yavru = new JDialog(Ontanimliveriler.frame,"Veri Giriþi",true); 
	yavru.setSize(320,200); 
	yavru.setResizable(false);
	
	yavru.setLayout(null);
		
	baslik= new JLabel("");
	baslik.setBounds(100,5,120,20);
	
	if (Ontanimliveriler.eklebasildi==true) {
		baslik.setText(Ontanimliveriler.tip+" EKLE");
	}
	
	if (Ontanimliveriler.duzenlebasildi==true) {
		baslik.setText(Ontanimliveriler.tip+" DÜZENLE");
	}

	if (Ontanimliveriler.silebasildi==true) {
		baslik.setText(Ontanimliveriler.tip+" SÝL");
	}
		
	yavru.setSize(300,200); 
	yavru.setLocation(550,50);
	yavru.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	
	yavru.add(yavrudugme);
	yavru.add(yavrudugme2);
	yavru.add(baslik);
	
	yavrudugme.setVisible(true);
	yavrudugme.addActionListener(new Ontanimliveridegirtireklesil());
	yavrudugme.setActionCommand(evethayir.basvazgec.name());
	
	
	yavrudugme2.setVisible(true);
	yavrudugme2.addActionListener(new Ontanimliveridegirtireklesil());
	yavrudugme2.setActionCommand(evethayir.baskaydet.name());
		
	jt1= new JTextField();
	jt1.setBounds(90,35,130,26);
	yavru.add(jt1);
		
	jt2= new JTextField();
	jt2.setBounds(90,60,130,26);
	yavru.add(jt2);
	
	lb1= new JLabel(alan1);
	lb1.setBounds(20,35,100,26);
	yavru.add(lb1);
	
	lb2= new JLabel(alan2);
	lb2.setBounds(20,60,100,26);
	yavru.add(lb2);
	
		
	if (Ontanimliveriler.tip.equals("Gemi")) {
		yavru.setSize(300,250);
	
		jt3= new JTextField("");
		jt3.setBounds(90,85,130,26);
		yavru.add(jt3);
		
		jt4= new JTextField("");
		jt4.setBounds(90,110,130,26);
		yavru.add(jt4);
		
		lb3= new JLabel("Bayrak");
		lb3.setBounds(20,85,100,26);
		yavru.add(lb3);
		
		lb4= new JLabel("Ýmo");
		lb4.setBounds(20,110,100,26);
		yavru.add(lb4);
		
		yavrudugme.setBounds(40,150,90,26);
		yavrudugme2.setBounds(150,150,90,26);
	} else {
		
		yavrudugme.setBounds(40,100,90,26);
		yavrudugme2.setBounds(150,100,90,26);
	}

	if (Ontanimliveriler.duzenlebasildi==true | Ontanimliveriler.silebasildi==true ) {
	     	veriarama(Ontanimliveriler.secilikod); 
		}
		
	if (Ontanimliveriler.silebasildi==true) {
		baslik.setText(Ontanimliveriler.secilikisaltma.toUpperCase()+" Kaydýný Sil");
		jt1.setEditable(false);
		jt2.setEditable(false);
		
		if (Ontanimliveriler.tip.equals("Gemi")) {
			jt3.setEditable(false);
			jt4.setEditable(false);
		}
	}
	
	    yavru.setVisible(true); 
		}
	
@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getActionCommand()==evethayir.basvazgec.name()) {
			yavru.dispose();
			
		}
		
		if (e.getActionCommand()==evethayir.baskaydet.name()) {
			
			if (Ontanimliveriler.eklebasildi==true) {
				veriekle();
			    }
			
			if (Ontanimliveriler.duzenlebasildi==true) {
				veridegistir(Ontanimliveriler.secilikod);
			    }
			
			if (Ontanimliveriler.silebasildi==true) {
				
				firmaverisil(Ontanimliveriler.secilikod);
			    }
			
			return;
			
		}
		
	}

private void firmaverisil(String ekrankod) {
	Sileyimmi.sonkarar("ontanimli");
	if (Sileyimmi.cevap.equals("evet")) {
	
		try {     // veri silme   
     
		Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection
        		("jdbc:mysql://"+Anasinif.sqlip+"/nal2000?useUnicode=true&characterEncoding=UTF-8",Anasinif.sqluser,Anasinif.sqlpass);
        PreparedStatement st = connection.prepareStatement(sqlkomutsil);
        st.executeUpdate(); 
  
	} catch(Exception e) {
        System.out.println(e);
    }

		Ontanimliveriler.frame.dispose();
		Ontanimliveriler.ontanimliveriler(Ontanimliveriler.jtalani,Ontanimliveriler.tip);
	    yavru.dispose();
	}
	yavru.dispose();
}

private void veridegistir(String ekrankod) {
	
	ekranioku();
	
	if ((ekranj1.trim().length()<1)) {
		baslik.setText("EN AZ 1 KARAKTER");
		return;
	  }
	 Connection con = null;
     PreparedStatement ps = null;
     try {
        con = DriverManager.getConnection("jdbc:mysql://"+Anasinif.sqlip+"/nal2000?useUnicode=true&characterEncoding=UTF-8",Anasinif.sqluser,Anasinif.sqlpass); 
      
        ps = con.prepareStatement(sqlkomutupdate1);
        ps.setString(1, ekranj1);
        ps.setString(2, ekrankod);
        ps.executeUpdate();
       
        ps = con.prepareStatement(sqlkomutupdate2);
        ps.setString(1, ekranj2);
        ps.setString(2, ekrankod);
        ps.executeUpdate();
        
        if(Ontanimliveriler.tip.equals("Gemi")) {
        	ps = con.prepareStatement(sqlkomutupdate3);
            ps.setString(1, ekranj3);
            ps.setString(2, ekrankod);
            ps.executeUpdate();
           
            ps = con.prepareStatement(sqlkomutupdate4);
            ps.setString(1, ekranj4);
            ps.setString(2, ekrankod);
            ps.executeUpdate();
        }
       
        } catch (Exception e) {
           e.printStackTrace();
     }	
     
     Ontanimliveriler.frame.dispose();
 	Ontanimliveriler.ontanimliveriler(Ontanimliveriler.jtalani,Ontanimliveriler.tip);
     yavru.dispose();
}

private void veriekle() {
	
	ekranioku();
	
	if ((ekranj1.trim().length()<1)) {
		baslik.setText("EN AZ 1 KARAKTER");
		return;
	  }
	
	try{                                             
		Class.forName("com.mysql.cj.jdbc.Driver");  
		Connection con=DriverManager.getConnection(  
"jdbc:mysql://"+Anasinif.sqlip+"/nal2000?useUnicode=true&characterEncoding=UTF-8",Anasinif.sqluser,Anasinif.sqlpass);	
		Statement stmt=con.createStatement();  
		System.out.println(sqlkomutekle);
		stmt.executeUpdate(sqlkomutekle);
		con.close();  
		
		}catch(Exception e){ System.out.println(e);}  
	
	Ontanimliveriler.frame.dispose();
	Ontanimliveriler.ontanimliveriler(Ontanimliveriler.jtalani,Ontanimliveriler.tip);
    yavru.dispose();
 	 
}
private void ekranioku() {
	
	
	
	ekranj1=jt1.getText().trim();
	ekranj2=jt2.getText().trim();
	
	if (Ontanimliveriler.tip.equals("Gemi")) {
		ekranj3=jt3.getText().trim();
		ekranj4=jt4.getText().trim();	
	}
	sqlkomutbelirleme();
	
}

private static void veriarama(String ekrankod) {
	sqlkomutbelirleme();
	try{                                              
		Class.forName("com.mysql.cj.jdbc.Driver");  
		Connection con=DriverManager.getConnection(
				"jdbc:mysql://"+Anasinif.sqlip+"/nal2000?useUnicode=true&characterEncoding=UTF-8",Anasinif.sqluser,Anasinif.sqlpass);
				
				Statement stmt=con.createStatement();  
		        ResultSet rs=stmt.executeQuery(sqlkomutveriarama); 
		
		while(rs.next()) {
			
			 jt1.setText( rs.getString(2));
			 jt1.setCaretPosition(0);
			 jt2.setText( rs.getString(3));
			 jt2.setCaretPosition(0);
			 if (Ontanimliveriler.tip.equals("Gemi")) {
					jt3.setText( rs.getString(4));
					jt3.setCaretPosition(0);
					jt4.setText( rs.getString(5));
					jt4.setText( rs.getString(5));
				}
			
	          
		}
		con.close();  
		
		}catch(Exception e){ System.out.println(e);}  
	
}
}