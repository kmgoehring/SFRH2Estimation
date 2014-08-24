import java.text.DecimalFormat;
import javax.swing.JOptionPane;

/**
 * 
 * @author Kevin M. Goehring 
 * 		   A brief class/UI that runs calculations off user
 *         input and returns various useful values for astronomical research
 *         concerning star formation rates and molecular hydrogen masses.
 *         Applies distance conversions, deprojected area and axis caluclations,
 *         and gas and star formation rate densities.
 */

public class SFRH2Estimation {

	
	public static void main(String[] args) {
		
		// String variables for user input
		String  dAuthor, // Object distance from associated reference paper, in megaparsecs
				dYou, // Object distance per user, in megaparsecs
				LAuthor, // Hydrogen-alpha luminosity from referenced paper, in ergs per second
				mH1,  // Neutral hydrogen gas mass, in solar masses
				RC3Diameter, // RC3 de Vaucouleurs galactic diameter, in arcseconds
				axisRatio, // Ratio between a galaxy's semi-major and semi-minor axes
				sfr;  // Star formation rate, in solar masses per year
		
		/**
		 * Prompts user for various data and parses the entered strings in to doubles. 
		 */
				dYou = 
						JOptionPane.showInputDialog ( "Enter the distance to your object in megaparsecs" );
				double distanceYou = Double.parseDouble(dYou);
		
				dAuthor = 
						JOptionPane.showInputDialog ( "Enter the literature distance to your object in megaparsecs" );
				double distanceAuthor = Double.parseDouble(dAuthor);
				
				LAuthor = 
						JOptionPane.showInputDialog ( "Enter the literature H-alpha luminosity for your object" );
				double luminosityAuthor = Double.parseDouble(LAuthor);
				
				RC3Diameter = 
						JOptionPane.showInputDialog ( "Enter the RC3 diameter for your object in arcseconds" );
				double RC3DIAMETER = Double.parseDouble(RC3Diameter);
				
				axisRatio = 
						JOptionPane.showInputDialog ( "Enter the axis ratio for your object" );
				double AxisRatio = Double.parseDouble(axisRatio);
				
				mH1 = 
						JOptionPane.showInputDialog ( "Enter the H1 mass for your object" );
				double H1MASS = Double.parseDouble(mH1);	
 
				sfr = 
						JOptionPane.showInputDialog ( "Enter the corrected SFR for your object" );
				double SFR = Double.parseDouble(sfr);
			
			/**
			 * The mathematical calculations. 
			 */
			double 
			LYou = luminosityAuthor*(distanceYou / distanceAuthor)*(distanceYou / distanceAuthor)*2.8,  // Luminosity/distance conversion
			semiMajor = ((RC3DIAMETER*1000000*distanceYou)/206265)/2, // Semi-major axis, in arcseconds. 
			semiMinor = (((RC3DIAMETER*AxisRatio)*1000000*distanceYou)/206265)/2,  //Semi-minor axis, in arcseconds
			AreaPC = Math.PI*semiMajor*semiMinor, // Area, in square parsecs
			AreaKPC = AreaPC / 1000000, // Area, in square kiloparsecs
			sigmaSFR = SFR / AreaKPC, // Star formation rate density, in solar masses per yer per square kiloparsecs
			sigmaGAS = Math.pow((sigmaSFR / 0.00025), (1 / 1.28)), // Gas mass density, in solar masses per square parsec
			GAS = sigmaGAS * AreaPC, // Neutral hydrogen mass, in solar masses 
			mH2 = GAS - H1MASS; // Molecular hydrogen mass estimate, in solar masses
			
			// Decimal formatting for presenting results. 
			DecimalFormat df = new DecimalFormat("0.00E0");
			DecimalFormat DF = new DecimalFormat("0.0000");
			
			/**
			 * Display the final results. 
			 */
			JOptionPane.showMessageDialog ( 
					null, "L(H alpha) for your object is " + df.format(LYou) + "." 
					+"\n"
					+"\nThe star formation rate is " + DF.format(SFR) + "."
					+"\n"
					+"\nThe star formation rate density is " + DF.format(sigmaSFR) + "."
					+"\n"
					+"\nThe total gas density is " + DF.format(sigmaGAS) + "."
					+"\n"
					+"\nThe total gas mass estimate is " + df.format(GAS)
					+"\n"
					+"\nThe H2 mass estimate is " + df.format(mH2) + "."
							, "Results", 
					JOptionPane.PLAIN_MESSAGE); 
	}

}

