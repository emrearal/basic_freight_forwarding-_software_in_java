package heanalikibin;

import java.awt.Color;
import java.text.ParseException;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public class Anasinif {
		
	static String sqluser="root",sqlpass="123456",sqlip="localhost:3306";         // Sql sunucu giriþ bilgileri
	static String konteynertipleri[]= 
		{"40DC","40HC","20DC","40OT","40FR",
		 "20RF","40RF","45HC","20TC","45TIR"};
	
	static String istipi[]= {"CE01","CE02","CE03","CE04","CE05",
   		 "CE06","CE07","CE08","CE09","CE10","CE11","CE12"};
	
	static boolean nimbusOn=true;
	
	public static void main(String[] args) throws ParseException {
	
		if (nimbusOn) {
			
			UIManager.put("nimbusBlueGrey", new Color(120,170,140));
			UIManager.put("nimbusBase", new Color(150,70,200));
			UIManager.put("control", new Color(215,205,175));
			
			try {
			    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
			        if ("Nimbus".equals(info.getName())) {
			            UIManager.setLookAndFeel(info.getClassName());
			            break;
			        }
			    }
			} catch (Exception e) {	}
			
		}
	
	   Denizyoluanaekran.pozyukislemleri();
		
	
	 
	
	 
	}  

} 
